package de.slag.mail.commons;

import java.util.Arrays;
import java.util.List;

import de.slag.mail.commons.application.MailCountApplication;

public class MailApplicationSupport {

	public static MailApplication find(String mailApplicationName) {
		switch (mailApplicationName) {
		case MailCountApplication.name:
			return new MailCountApplication();

		default:
			throw new MailException("not supported MailApplication: " + mailApplicationName);
		}
	}

	public static List<String> getApplicationNames() {
		return Arrays.asList(MailCountApplication.name);
	}

}
