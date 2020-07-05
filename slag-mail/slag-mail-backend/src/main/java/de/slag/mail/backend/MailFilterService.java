package de.slag.mail.backend;

import java.util.Collection;

import de.slag.mail.commons2.filter.MailFilter;

public interface MailFilterService {

	Collection<MailFilter> createMailFilters(String username);

}
