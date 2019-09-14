package de.slag.mail.model;

import java.util.Arrays;
import java.util.Collection;

import javax.mail.Folder;
import javax.mail.Message;
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
	
	public MailFolder(Folder folder) {
		this.folder = folder;
	}

	public int getMessageCount() {
		try {
			return folder.getMessageCount();
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	public void open() {
		open(Folder.READ_ONLY);
	}

	public void open(int mode) {
		try {
			folder.open(mode);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	public Collection<Message> getMessages() {
		try {
			return Arrays.asList(folder.getMessages());
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	public Folder getFolder() {
		return folder;
	}

	public Folder getSubFolder(String subfolderName) {
		final Folder subfolder;
		try {
			subfolder = folder.getFolder(subfolderName);
		} catch (MessagingException e) {
			throw new MailException(e);
		}

		boolean subfolderExists;
		try {
			subfolderExists = subfolder.exists();
		} catch (MessagingException e) {
			throw new MailException(e);
		}

		if (!subfolderExists) {
			throw new MailException(
					String.format("subfolder '%s' of folder '%s' does not exists", subfolderName, folder.getName()));
		}
		return subfolder;
	}

}
