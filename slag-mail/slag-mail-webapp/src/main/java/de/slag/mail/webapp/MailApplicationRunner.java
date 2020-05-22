package de.slag.mail.webapp;

import java.util.Properties;

import javax.mail.Session;

import de.slag.mail.commons.MailApplication;
import de.slag.mail.commons.MailApplicationSupport;
import de.slag.mail.commons.MailException;
import de.slag.mail.commons.filter.SpamFilter;
import de.slag.mail.commons.model.MailFolder;
import de.slag.mail.commons.model.MailStore;
import de.slag.mail.commons.model.MailStoreBuilder;

public class MailApplicationRunner implements Runnable {

	private String password;
	private String user;
	private String result;
	private String application;
	private String host;

	public MailApplicationRunner(String password, String user, String application, String host) {
		super();
		this.password = password;
		this.user = user;
		this.application = application;
		this.host = host;
	}

	@Override
	public void run() {
		Session session = Session.getDefaultInstance(new Properties(), null);

		MailStoreBuilder mailStoreBuilder = new MailStoreBuilder();
		MailStore mailStore = mailStoreBuilder.password(password)
				.user(user)
				.session(session)
				.protocol("imap")
				.host(host)
				.build();

		MailFolder inboxFolder = mailStore.getFolder("INBOX");
		inboxFolder.open();

		MailApplication mailApplication = MailApplicationSupport.find(application);
		mailApplication.setFilter(new SpamFilter());
		mailApplication.setFolder(inboxFolder);
		try {
			result = mailApplication.call();
		} catch (Exception e) {
			throw new MailException(e);
		}

	}

	public String getResult() {
		return result;
	}

}
