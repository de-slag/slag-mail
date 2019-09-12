package de.slag.mail.ops;

import de.slag.mail.MailOperation;
import de.slag.mail.model.MailStore;

public class MailCountOperation extends MailOperation<String> {

	public MailCountOperation(MailStore mailStore) {
		super(mailStore);
	}

	@Override
	public String call() throws Exception {
		return "message count: " + getMailStore().getInboxFolder().getMessageCount();
	}

	@Override
	public String getIdentifier() {
		return "countmessages";
	}

}
