package de.slag.mail.commons.filter;

import de.slag.mail.commons.MailFilter;
import de.slag.mail.commons.model.MailMessage;

public class NoFilter implements MailFilter {

	public static final String NAME = "no-filter";

	@Override
	public boolean test(MailMessage t) {
		return true;
	}

}
