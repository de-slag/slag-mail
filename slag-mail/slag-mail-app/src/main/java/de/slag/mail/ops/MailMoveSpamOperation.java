package de.slag.mail.ops;

import java.util.Collection;

import javax.mail.Folder;
import javax.mail.Message;

import de.slag.mail.MailOperation;
import de.slag.mail.model.MailFolder;
import de.slag.mail.model.MailStore;

public class MailMoveSpamOperation extends MailOperation<String> {

	public MailMoveSpamOperation(MailStore mailStore) {
		super(mailStore);
	}

	@Override
	public String getIdentifier() {
		return "move_spam";
	}

	@Override
	protected String call0() throws Exception {
		final MailStore mailStore = getMailStore();
		final MailFolder inboxFolder = mailStore.getInboxFolder();
		inboxFolder.open(Folder.READ_WRITE);
		
		final Collection<Message> messages = inboxFolder.getMessages();
		
		messages.
		
	
	}
}
