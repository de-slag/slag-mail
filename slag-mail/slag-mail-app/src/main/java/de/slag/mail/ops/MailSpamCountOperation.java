package de.slag.mail.ops;

import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

import de.slag.mail.MailOperation;
import de.slag.mail.commons.model.MailMessage;
import de.slag.mail.commons.model.MailStore;
import de.slag.mail.filters.SpamFilter;

public class MailSpamCountOperation extends MailOperation<String> {

	public MailSpamCountOperation(MailStore mailStore) {
		super(mailStore);
	}

	@Override
	protected String call0() throws Exception {
		return new MailCountOperation(getMailStore(), new SpamFilter()).callSafe();
	}

	@Override
	public String getIdentifier() {
		return COUNT_SPAM;
	}

}
