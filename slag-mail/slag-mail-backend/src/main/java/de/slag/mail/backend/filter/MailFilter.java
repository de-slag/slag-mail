package de.slag.mail.backend.filter;

import java.util.function.Predicate;

import de.slag.mail.commons.model.MailMessage;

public interface MailFilter extends Predicate<MailMessage> {

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
