package de.slag.mail.webapp;

import java.util.Objects;
import java.util.Properties;

import javax.mail.Session;

import de.slag.mail.commons.MailApplication;
import de.slag.mail.commons.MailApplicationSupport;
import de.slag.mail.commons.MailException;
import de.slag.mail.commons.MailFilter;
import de.slag.mail.commons.MailFilterSupport;
import de.slag.mail.commons.model.MailFolder;
import de.slag.mail.commons.model.MailStore;
import de.slag.mail.commons.model.MailStoreBuilder;

public class MailApplicationRunner implements Runnable {

	private String password;
	private String user;
	private String result;
	private String application;
	private String filter;
	private String filterCustomization;
	private String host;

	public MailApplicationRunner(String password, String user, String application, String host, String filter,
			String filterCustomization) {
		super();
		this.password = password;
		this.user = user;
		this.application = application;
		this.host = host;
		this.filter = filter;
		this.filterCustomization = filterCustomization;
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

		MailFilter mailFilter;
		if (!filter.startsWith("custom")) {
			mailFilter = MailFilterSupport.find(filter);
		} else {
			final String comparer;
			if (filterCustomization.contains("~")) {
				comparer = "~";
			} else if (filterCustomization.contains("=")) {
				comparer = "~";
			} else {
				throw new IllegalArgumentException(filterCustomization);
			}
			final String[] split = filterCustomization.split(comparer);
			final String searchString = split[1];
			final String propertyName = split[0];
			mailFilter = MailFilterSupport.buildCustom(propertyName, comparer, searchString);
		}

		Objects.requireNonNull(mailFilter, "mail filter not created");

		mailApplication.setFilter(mailFilter);
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
