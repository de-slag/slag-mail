package de.slag.mail;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

import javax.mail.Session;

import de.slag.mail.model.MailFolder;
import de.slag.mail.model.MailStore;
import de.slag.mail.model.MailStoreBuilder;

public class MailApp {

	public static void main(String[] args) {
		final MailCommandLine mailCommandLine = new MailCommandLineBuilder().arguments(args).build();
		final File configFile = mailCommandLine.getConfigFile();

		final MailProperties mailProperties = new MailProperties(configFile);
		final Collection<String> ids = mailProperties.getIds();

		ids.forEach(id -> processMailId(mailProperties, id));
	}

	private static void processMailId(final MailProperties mailProperties, String id) {
		final Session session = new SessionBuilder().build();

		final String host = mailProperties.getHost(id);
		final String password = mailProperties.getPassword(id);
		final String user = mailProperties.getUser(id);

		final MailStore mailStore = new MailStoreBuilder().session(session).protocol("imap").host(host)
				.password(password).user(user).build();

		final MailFolder inboxFolder = mailStore.getInboxFolder();
		System.out.println(id + ", message count: "+ inboxFolder.getMessageCount());
	}

}
