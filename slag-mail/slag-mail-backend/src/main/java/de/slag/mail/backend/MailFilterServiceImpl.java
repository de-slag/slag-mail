package de.slag.mail.backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.mail.backend.adm.AdmConfigAdvancedService;
import de.slag.mail.backend.adm.AdmConfigPropertyUtils;
import de.slag.mail.backend.filter.MessageFilterConfigValidator;
import de.slag.mail.backend.filter.MessageFilterTemplate;
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

		final List<String> invalidMessages = new ArrayList<>();

		final List<String> keys = properties.keySet()
				.stream()
				.map(key -> key.substring(userPrefix.length()))
				.collect(Collectors.toList());

		boolean anyMatch = keys.stream()
				.anyMatch(key -> {
					final MessageFilterConfigValidator messageFilterConfigValidator = new MessageFilterConfigValidator(
							key);
					boolean valid = messageFilterConfigValidator.isValid();
					if (valid) {
						return false;
					}
					invalidMessages.add(messageFilterConfigValidator.getReason());
					return true;
				});

		if (anyMatch) {
			throw new RuntimeException(String.join("\n", invalidMessages));
		}

		Collection<Long> filterIds = AdmConfigPropertyUtils.findIdsFor(userFilterPrefix, properties.keySet());

		final List<MessageFilterTemplate> filterTemplates = filterIds.stream()
				.map(id -> {
					String nameKey = String.format("%sfilter.%s.name", userPrefix, id);
					String configKey = String.format("%sfilter.%s.config", userPrefix, id);

					String filterName = properties.get(nameKey);
					if (filterName == null) {
						throw new RuntimeException(String.format("key '%s' is null", nameKey));
					}
					String configValue = properties.get(configKey);
					if (configValue == null) {
						throw new RuntimeException(String.format("key '%s' is null", configKey));
					}
					String[] split = configValue.split(";");
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

					MessageFilterTemplate filterTemplate = new MessageFilterTemplate(filterName, field, operator,
							referenceValue);
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
}
