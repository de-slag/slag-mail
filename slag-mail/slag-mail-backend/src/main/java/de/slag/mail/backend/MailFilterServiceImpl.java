package de.slag.mail.backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.mail.backend.adm.AdmConfigAdvancedService;
import de.slag.mail.backend.adm.AdmConfigPropertyUtils;
import de.slag.mail.commons2.filter.MailFilter;
import de.slag.mail.commons2.filter.MailFilter.Field;
import de.slag.mail.commons2.filter.MailFilter.Operator;
import de.slag.mail.commons2.filter.MailFilterBuilder;

@Service
public class MailFilterServiceImpl implements MailFilterService {

	@Resource
	private AdmConfigAdvancedService admConfigAdvancedService;

	@Override
	public List<MailFilter> createMailFilters(String username) {
		String userPrefix = username + "#";
		String userFilterPrefix = userPrefix + "filter";
		Map<String, String> properties = admConfigAdvancedService.getProperties(userFilterPrefix);
		Collection<Long> filterIds = AdmConfigPropertyUtils.findIdsFor(userFilterPrefix, properties.keySet());

		final List<FilterTemplate> filterTemplates = filterIds.stream()
				.map(id -> {
					String nameKey = String.format("%sfilter.%s.name", userPrefix, id);
					String configKey = String.format("%sfilter.%s.config", userPrefix, id);

					String filterName = properties.get(nameKey);
					String configString = properties.get(configKey);
					String[] split = configString.split(";");
					String fieldString = split[0];
					String operatorString = split[1];
					String referenceValueString = split[2];

					Field field = Field.valueOf(fieldString.toUpperCase());
					Operator operator = Operator.valueOf(operatorString.toUpperCase());
					Object referenceValue;
					switch (field) {
					case SUBJECT:
						referenceValue = referenceValueString;
						break;
					case SENT_DATE:
						final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						final Date parsedDate;
						try {
							parsedDate = simpleDateFormat.parse(referenceValueString);
						} catch (ParseException e) {
							throw new RuntimeException(e);
						}
						referenceValue = parsedDate.toInstant()
								.atZone(ZoneId.systemDefault())
								.toLocalDateTime();
						break;

					default:
						throw new RuntimeException("not supported: " + field);
					}

					FilterTemplate filterTemplate = new FilterTemplate(filterName, field, operator, referenceValue);
					return filterTemplate;
				})
				.collect(Collectors.toList());

		return filterTemplates.stream()
				.map(template -> {
					return new MailFilterBuilder().withName(template.getFilterName())
							.withField(template.getField())
							.withOperator(template.getOperator())
							.withReferenceValue(template.getReferenceValue())
							.build();
				})
				.collect(Collectors.toList());

	}

	private class FilterTemplate {

		private String filterName;
		private Field field;
		private Operator operator;
		private Object referenceValue;

		public FilterTemplate(String filterName, Field field, Operator operator, Object referenceValue) {
			this.filterName = filterName;
			this.field = field;
			this.operator = operator;
			this.referenceValue = referenceValue;
		}

		public String getFilterName() {
			return filterName;
		}

		public Field getField() {
			return field;
		}

		public Operator getOperator() {
			return operator;
		}

		public Object getReferenceValue() {
			return referenceValue;
		}

	}
}
