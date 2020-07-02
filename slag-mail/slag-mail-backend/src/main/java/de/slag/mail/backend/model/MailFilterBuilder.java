package de.slag.mail.backend.model;

import java.util.Date;

import org.apache.commons.lang3.builder.Builder;

import de.slag.mail.backend.model.MailFilter.Field;
import de.slag.mail.backend.model.MailFilter.Operator;
import de.slag.mail.commons.model.MailMessage;

public class MailFilterBuilder implements Builder<MailFilter> {

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
		return new MailFilter() {

			@Override
			public boolean test(MailMessage t) {
				switch (field) {
				case SUBJECT:
					return filterSubject(t);
				case SENT_DATE:
					return filterSentDate(t);
				default:
					throw new RuntimeException("not supported: " + field);
				}
			}

			private boolean filterSentDate(MailMessage t) {
				return filterDate(t.getSentDate());
			}

			private boolean filterDate(final Date sentDate) {
				switch (operator) {
				case EQUALS:
					return sentDate.equals(referenceValue);
				case CONTAINS:
					throw new RuntimeException("not supported for type DATE: " + operator);

				default:
					throw new RuntimeException("not supported: " + operator);
				}
			}

			private boolean filterSubject(MailMessage t) {
				return filterString(t.getSubject());
			}

			private boolean filterString(String string) {
				switch (operator) {
				case CONTAINS:
					return string.contains((String) referenceValue);
				case EQUALS:
					return string.equals(referenceValue);
				default:
					throw new RuntimeException("not supported: " + operator);
				}
			}

			@Override
			public String getName() {
				return name;
			}
		};
	}

}
