package de.slag.mail;

import java.util.concurrent.Callable;

import de.slag.mail.model.MailStore;

public abstract class MailOperation<V> implements Callable<V> {

	public static final String COUNTMESSAGES = "countmessages";
	
	private final MailStore mailStore;

	public abstract String getIdentifier();

	public MailOperation(MailStore mailStore) {
		this.mailStore = mailStore;
	}

	protected MailStore getMailStore() {
		return mailStore;
	}

	public V callSafe() {
		try {
			return call();
		} catch (Exception e) {
			throw new MailException(e);
		}
	}

}
