package de.slag.mail.ops;

import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

import de.slag.mail.MailOperation;
import de.slag.mail.model.MailMessage;
import de.slag.mail.model.MailStore;

public class MailSpamCountOperation extends MailOperation<String> {

	private Predicate<MailMessage> SPAM_FILTER = m -> {
		final String subject = m.getSubject();
		if (StringUtils.isEmpty(subject)) {
			return false;
		}
		return subject.contains("SPAM");
	};

	public MailSpamCountOperation(MailStore mailStore) {
		super(mailStore);
	}

	@Override
	public String call() throws Exception {
		return new MailCountOperation(getMailStore(),SPAM_FILTER).callSafe();
	}

	@Override
	public String getIdentifier() {
		return COUNT_SPAM;
	}

}
