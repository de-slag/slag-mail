package de.slag.mail.commons;

import java.util.Arrays;
import java.util.List;

import de.slag.mail.commons.filter.CustomMailFilterBuilder;
import de.slag.mail.commons.filter.NoFilter;
import de.slag.mail.commons.filter.SpamFilter;

public class MailFilterSupport {

	public static MailFilter find(String mailFilterName) {
		switch (mailFilterName) {
		case SpamFilter.name:
			return new SpamFilter();
		case NoFilter.NAME:
			return new NoFilter();

		default:
			throw new MailException("not supported MailFilter: " + mailFilterName);
		}
	}

	public static MailFilter buildCustom(String propertyName, String comparer, String searchString) {
		return new CustomMailFilterBuilder().withProperty(propertyName)
				.withComparer(comparer)
				.withSearchString(searchString)
				.build();
	}

	public static List<String> getFilterNames() {
		return Arrays.asList(SpamFilter.name, NoFilter.NAME);
	}

}
