package de.slag.mail.commons;

import java.util.Arrays;
import java.util.List;

import de.slag.mail.commons.filter.SpamFilter;

public class MailFilterSupport {

	public static MailFilter find(String mailFilterName) {
		switch (mailFilterName) {
		case SpamFilter.name:
			return new SpamFilter();

		default:
			throw new MailException("not supported MailFilter: " + mailFilterName);
		}
	}

	public static List<String> getFilterNames() {
		return Arrays.asList(SpamFilter.name);
	}

}
