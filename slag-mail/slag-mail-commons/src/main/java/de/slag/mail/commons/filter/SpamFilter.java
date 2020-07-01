package de.slag.mail.commons.filter;

import org.apache.commons.lang3.StringUtils;

import de.slag.mail.commons.MailFilter;
import de.slag.mail.commons.model.MailMessage;

public class SpamFilter implements MailFilter {

	public static final String name = "spam";

	@Override
	public boolean test(MailMessage message) {
		final String subject = message.getSubject();
		if (StringUtils.isEmpty(subject)) {
			return false;
		}
		return subject.contains("SPAM");
	}

}
