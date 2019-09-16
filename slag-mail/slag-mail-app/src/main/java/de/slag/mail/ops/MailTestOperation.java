package de.slag.mail.ops;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.Folder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.mail.MailMessageMoveUtils;
import de.slag.mail.MailOperation;
import de.slag.mail.filters.TestFilter;
import de.slag.mail.model.MailFolder;
import de.slag.mail.model.MailMessage;
import de.slag.mail.model.MailStore;

public class MailTestOperation extends MailOperation<String> {

	private static final Log LOG = LogFactory.getLog(MailTestOperation.class);

	public MailTestOperation(MailStore mailStore) {
		super(mailStore);

	}

	@Override
	public String getIdentifier() {
		return TEST;
	}

	@Override
	protected String call0() throws Exception {
		final MailStore mailStore = getMailStore();

		final String countResult = new MailCountOperation(mailStore, new TestFilter()).callSafe();
		LOG.info(countResult);
		
		final MailFolder inboxFolder = mailStore.getInboxFolder();
		inboxFolder.open(Folder.READ_WRITE);
		final List<MailMessage> collect = inboxFolder.getMessages().stream()
				.map(m -> new MailMessage(m))
				.filter(new TestFilter())
				.collect(Collectors.toList());
		LOG.info(collect);
		
		MailFolder to = new MailFolder(inboxFolder.getSubFolder("test"));
		to.open(Folder.READ_WRITE);
		
		if(collect.isEmpty()) {
			return "nothing to move";
		}
		
		MailMessageMoveUtils.move(collect.get(0), inboxFolder, to);
		
		inboxFolder.getFolder().close(true);
		to.getFolder().close(true);
		
		
		return new MailCountOperation(mailStore, new TestFilter()).callSafe();

	}

}
