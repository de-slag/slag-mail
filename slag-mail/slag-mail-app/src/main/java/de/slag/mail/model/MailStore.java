package de.slag.mail.model;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import de.slag.mail.MailException;

public class MailStore {

	private Store store;
	
	MailStore(Store store) {
		this.store = store;
	}
	
	public MailFolder getInboxFolder() {
		return new MailFolder(store, "INBOX");
	}

}
