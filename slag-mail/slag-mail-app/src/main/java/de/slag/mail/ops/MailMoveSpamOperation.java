package de.slag.mail.ops;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.Folder;
import javax.mail.Message;

import de.slag.mail.MailMessageMoveUtils;
import de.slag.mail.MailOperation;
import de.slag.mail.filters.SpamFilter;
import de.slag.mail.model.MailFolder;
import de.slag.mail.model.MailMessage;
import de.slag.mail.model.MailStore;

public class MailMoveSpamOperation extends MailOperation<String> {

	public MailMoveSpamOperation(MailStore mailStore) {
		super(mailStore);
	}

	@Override
	public String getIdentifier() {
		return MOVE_SPAM;
	}

	@Override
	protected String call0() throws Exception {
		final MailStore mailStore = getMailStore();
		final MailFolder inboxFolder = mailStore.getInboxFolder();
		inboxFolder.open(Folder.READ_WRITE);

		final Collection<Message> messages = inboxFolder.getMessages();

		final SpamFilter spamFilter = new SpamFilter();
		final List<MailMessage> spamMessages = messages.stream().map(m -> new MailMessage(m))
				.filter(m -> spamFilter.test(m)).collect(Collectors.toList());

		final MailFolder spamFolder = new MailFolder(inboxFolder.getSubFolder("spam"));
		spamFolder.open(Folder.READ_WRITE);

		final MailMessage spamMessage = spamMessages.get(0);

		final Collection<MailMessage> spamMessagesToMove = new ArrayList<>();
		spamMessagesToMove.add(spamMessage);

		final List<String> messagesInfos = new ArrayList<>();

		spamMessagesToMove.forEach(m -> {
			MailMessageMoveUtils.move(m, inboxFolder, spamFolder);
			messagesInfos
					.add(String.format("spam messages moved: SUBJECT: %s, DATE: %s", m.getSubject(), m.getSentDate()));
		});

		spamFolder.getFolder().close(true);
		inboxFolder.getFolder().close(true);

		return String.join("\n", messagesInfos);
	}
}
