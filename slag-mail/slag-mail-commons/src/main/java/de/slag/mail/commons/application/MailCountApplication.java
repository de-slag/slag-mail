package de.slag.mail.commons.application;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.slag.mail.commons.MailApplication;
import de.slag.mail.commons.model.MailFolder;
import de.slag.mail.commons.model.MailMessage;

public class MailCountApplication implements MailApplication {

	public static final String name = "count";

	private Predicate<MailMessage> filter;
	private MailFolder folder;

	@Override
	public String call() throws Exception {
		List<MailMessage> collect = getMessages().stream()
				.filter(filter)
				.collect(Collectors.toList());
		return collect.size() + " messages found.";
	}

	private Collection<MailMessage> getMessages() {
		return folder.getMessages()
				.stream()
				.map(m -> new MailMessage(m))
				.collect(Collectors.toList());
	}

	@Override
	public void setFilter(Predicate<MailMessage> filter) {
		this.filter = filter;
	}

	@Override
	public void setFolder(MailFolder folder) {
		this.folder = folder;

	}
}
