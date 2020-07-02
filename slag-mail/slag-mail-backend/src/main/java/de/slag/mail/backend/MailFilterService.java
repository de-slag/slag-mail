package de.slag.mail.backend;

import java.util.Collection;
import java.util.Properties;

import de.slag.mail.commons.MailFilter;

public interface MailFilterService {

	Collection<MailFilter> createMailFilters(Properties properties);

}
