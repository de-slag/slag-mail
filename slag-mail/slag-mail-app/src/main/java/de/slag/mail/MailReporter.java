package de.slag.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MailReporter implements Consumer<String> {

	private final String id;

	private final List<String> messageList = new ArrayList<>();

	public MailReporter(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public void accept(String s) {
		messageList.add(s);
	}

	@Override
	public String toString() {
		return String.join("\n", messageList);
	}

}
