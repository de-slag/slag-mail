package de.slag.mail.commons2.filter;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.mail.commons2.filter.MailFilter.Field;
import de.slag.mail.commons2.filter.MailFilter.Operator;
import de.slag.mail.commons2.model.MessagePropertiesProvideSupport;

public class MailFilterBuilder implements Builder<MailFilter> {

	private static final Log LOG = LogFactory.getLog(MailFilterBuilder.class);

	private Field field;

	private Operator operator;

	private Object referenceValue;

	private String name;

	public MailFilterBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public MailFilterBuilder withField(MailFilter.Field field) {
		this.field = field;
		return this;
	}

	public MailFilterBuilder withOperator(MailFilter.Operator operator) {
		this.operator = operator;
		return this;
	}

	public MailFilterBuilder withReferenceValue(Object referenceValue) {
		this.referenceValue = referenceValue;
		return this;
	}

	@Override
	public MailFilter build() {

		if (StringUtils.isEmpty(name)) {
			throw new RuntimeException("no name setted");
		}

		return new MailFilter() {

			@Override
			public boolean test(MessagePropertiesProvideSupport t) {
				if (field == null) {
					LOG.warn(String.format("field not setted, do not filter", field));
					return true;
				}
				switch (field) {
				case SUBJECT:
					return filterSubject(t);
				case SENT_DATE:
					return filterSentDate(t);
				default:
					LOG.warn(String.format("field '%s' not supported, do not filter", field));
					return true;
				}
			}

			private boolean filterSentDate(MessagePropertiesProvideSupport t) {
				return filterDate(t.getSentDate());
			}

			private boolean filterDate(final LocalDateTime sentDate) {
				LocalDateTime referenceTimestamp = (LocalDateTime) referenceValue;
				switch (operator) {
				case EQUALS:
					return sentDate.equals(referenceTimestamp);
				case GREATER:
					return sentDate.isAfter(referenceTimestamp);
				case LOWER:
					return sentDate.isBefore(referenceTimestamp);
				default:
					throw new RuntimeException("not supported for type DATE: " + operator);
				}
			}

			private boolean filterSubject(MessagePropertiesProvideSupport t) {
				return filterString(t.getSubject());
			}

			private boolean filterString(String string) {
				switch (operator) {
				case CONTAINS:
					return string.contains((String) referenceValue);
				case EQUALS:
					return string.equals(referenceValue);
				default:
					throw new RuntimeException("not supported for type STRING: " + operator);
				}
			}

			@Override
			public String getName() {
				return name;
			}
		};
	}

}
