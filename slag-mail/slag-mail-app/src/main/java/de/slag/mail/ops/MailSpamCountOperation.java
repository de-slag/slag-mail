package de.slag.mail.ops;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.slag.mail.MailOperation;
import de.slag.mail.model.MailFolder;
import de.slag.mail.model.MailMessage;
import de.slag.mail.model.MailStore;

public class MailSpamCountOperation extends MailOperation<String> {

	public MailSpamCountOperation(MailStore mailStore) {
		super(mailStore);
	}

	@Override
	public String call() throws Exception {
		MailStore mailStore = getMailStore();

		final MailFolder inboxFolder = mailStore.getInboxFolder();
		inboxFolder.open();
		final Collection<MailMessage> messages = inboxFolder.getMessages().stream().map(m -> new MailMessage(m))
				.collect(Collectors.toList());

		final long spamCount = messages.stream().filter(m -> {
			final String subject = m.getSubject();
			if (StringUtils.isEmpty(subject)) {
				return false;
			}
			return subject.contains("SPAM");
		}).count();

		return "Spam count: " + spamCount;
	}

	@Override
	public String getIdentifier() {
		return COUNT_SPAM;
	}

}
