package de.slag.mail.backend.filter;

import de.slag.mail.commons2.filter.MailFilter.Field;
import de.slag.mail.commons2.filter.MailFilter.Operator;

public class MessageFilterTemplate {

	private String filterName;
	private Field field;
	private Operator operator;
	private Object referenceValue;

	public MessageFilterTemplate(String filterName, Field field, Operator operator, Object referenceValue) {
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
