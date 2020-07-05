package de.slag.mail.commons2.filter;

import java.util.function.Predicate;

import de.slag.mail.commons2.model.MessagePropertiesProvideSupport;

public interface MailFilter extends Predicate<MessagePropertiesProvideSupport> {

	String getName();

	public enum Field {
		SUBJECT,

		SENT_DATE
	}

	public enum Operator {
		CONTAINS,

		EQUALS,

		GREATER,

		LOWER
	}

}
