package de.slag.mail.commons;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

import de.slag.mail.commons.model.MailFolder;
import de.slag.mail.commons.model.MailMessage;

public interface MailApplication extends Callable<String> {

	void setFilter(Predicate<MailMessage> filter);

	void setFolder(MailFolder folder);

}
