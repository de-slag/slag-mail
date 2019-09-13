package de.slag.mail;

import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.mail.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.mail.model.MailStore;
import de.slag.mail.model.MailStoreBuilder;
import de.slag.mail.ops.MailCountOperation;

public class MailApp {

	private static final Log LOG = LogFactory.getLog(MailApp.class);

	public static void main(String[] args) {
		LOG.info("start");
		final MailCommandLine mailCommandLine = new MailCommandLineBuilder().arguments(args).build();
		final File configFile = mailCommandLine.getConfigFile();

		final MailProperties mailProperties = new MailProperties(configFile);
		final Collection<String> ids = mailProperties.getIds();

		ids.forEach(id -> processMailId(mailProperties, id));
	}

	private static void processMailId(final MailProperties mailProperties, String id) {
		LOG.info("process id: " + id);
		final Session session = new SessionBuilder().ssl(mailProperties.isSsl(id)).port(mailProperties.getPort(id))
				.build();

		final String host = mailProperties.getHost(id);
		final String password = mailProperties.getPassword(id);
		final String user = mailProperties.getUser(id);

		final MailStore mailStore = new MailStoreBuilder().session(session).protocol("imap").host(host)
				.password(password).user(user).build();

		final Collection<MailOperation<?>> ops = determineOperations(mailProperties, mailStore, id);

		ops.forEach(op -> System.out.println(op.callSafe()));

	}

	private static Collection<MailOperation<?>> determineOperations(MailProperties mailProperties, MailStore mailStore,
			String accountId) {
		return mailProperties.getOperationIds(accountId).stream().map(opId -> determineOperation(opId, mailStore))
				.collect(Collectors.toList());

	}

	private static MailOperation<?> determineOperation(String operationIdentifier, MailStore mailStore) {
		switch (operationIdentifier) {
		case MailOperation.COUNTMESSAGES:
			return new MailCountOperation(mailStore);

		default:
			throw new MailException("no valid ops id: " + operationIdentifier);
		}
	}

}
