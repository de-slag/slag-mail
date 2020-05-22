package de.slag.mail.filters;

import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

import de.slag.mail.commons.model.MailMessage;

public class TestFilter implements Predicate<MailMessage>{

	@Override
	public boolean test(MailMessage message) {
		final String subject = message.getSubject();
		if (StringUtils.isEmpty(subject)) {
			return false;
		}
		return subject.contains("TEST");
	}

}
