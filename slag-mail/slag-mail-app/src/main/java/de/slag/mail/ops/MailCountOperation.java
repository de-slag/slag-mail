package de.slag.mail.ops;

import java.util.function.Predicate;

import de.slag.mail.MailOperation;
import de.slag.mail.commons.model.MailFolder;
import de.slag.mail.commons.model.MailMessage;
import de.slag.mail.commons.model.MailStore;

public class MailCountOperation extends MailOperation<String> {

	private Predicate<MailMessage> filter;

	public MailCountOperation(MailStore mailStore, Predicate<MailMessage> filter) {
		super(mailStore);
		this.filter = filter;
	}

	public MailCountOperation(MailStore mailStore) {
		this(mailStore, null);
	}

	@Override
	protected String call0() throws Exception {
		final long messageCount;
		final MailFolder inboxFolder = getMailStore().getInboxFolder();
		if (filter == null) {
			messageCount = inboxFolder.getMessageCount();
		} else {
			inboxFolder.open();
			messageCount = inboxFolder.getMessages().stream().map(m -> new MailMessage(m))
					.filter(filter).count();
		}
		return "message count: " + messageCount;
	}

	@Override
	public String getIdentifier() {
		return COUNTMESSAGES;
	}

}
