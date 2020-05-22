package de.slag.mail.commons.model;

import javax.mail.Store;

public class MailStore {

	private Store store;
	
	MailStore(Store store) {
		this.store = store;
	}
	
	public MailFolder getInboxFolder() {
		return new MailFolder(store, "INBOX");
	}
	
	public MailFolder getFolder(String folderName) {
		return new MailFolder(store, folderName);
	}

}
