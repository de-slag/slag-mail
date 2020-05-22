package de.slag.mail;

import java.io.File;

import de.slag.mail.commons.MailException;

public interface MailCommandLine {

	String getConfigFileName();

	default File getConfigFile() {
		final File file = new File(getConfigFileName());
		if (!file.exists()) {
			throw new MailException("file does not exist:" + getConfigFileName());
		}
		return file;
	}

}
