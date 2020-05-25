package de.slag.mail.commons.filter;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;

import de.slag.mail.commons.MailException;
import de.slag.mail.commons.MailFilter;
import de.slag.mail.commons.model.MailMessage;

public class CustomMailFilterBuilder implements Builder<MailFilter> {

	private List<String> validComparers = Arrays.asList("=", "~");

	private String propertyName;

	private String comparer;

	private String searchString;

	public CustomMailFilterBuilder withProperty(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}

	public CustomMailFilterBuilder withSearchString(String searchString) {
		this.searchString = searchString;
		return this;
	}

	public CustomMailFilterBuilder withComparer(String comparer) {
		this.comparer = comparer;
		return this;
	}

	@Override
	public MailFilter build() {

		if (!validComparers.contains(comparer)) {
			throw new MailException("no valid comparer: " + comparer);
		}

		return new MailFilter() {

			@Override
			public boolean test(MailMessage t) {

				String property = getProperty(propertyName, t);
				if (StringUtils.isEmpty(property)) {
					return false;
				}

				if ("~".equals(comparer)) {
					return property.contains(searchString);
				}

				if ("=".equals(comparer)) {
					return property.equals(searchString);
				}
				return false;
			}

			private String getProperty(String name, MailMessage m) {
				if ("SUBJECT".equals(name)) {
					return m.getSubject();
				}
				throw new MailException("not supported");
			}
		};

	}
}
