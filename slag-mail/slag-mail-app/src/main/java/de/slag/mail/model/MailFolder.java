package de.slag.mail.model;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import de.slag.mail.MailException;

public class MailFolder {
	
	private Folder folder;

	public MailFolder(Store store, String folderName) {
		try {
			this.folder = store.getFolder(folderName);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}
	
	public int getMessageCount() {
		try {
			return folder.getMessageCount();
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

}
