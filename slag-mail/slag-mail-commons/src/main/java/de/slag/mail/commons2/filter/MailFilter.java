package de.slag.mail.commons2.filter;

import java.util.function.Predicate;

import de.slag.mail.commons2.model.MailPropertiesSupport;

public interface MailFilter extends Predicate<MailPropertiesSupport> {

	String getName();

	public enum Field {
		SUBJECT,

		SENT_DATE
	}

	public enum Operator {
		CONTAINS,

		EQUALS
	}

}
