package de.slag.mail.webapp;

import java.util.List;

public abstract class PageController {

	public String getState() {
		return String.join("\n", getStateMessages());
	}

	abstract List<String> getStateMessages();

	public String getAppVersion() {
		return PageController.class.getPackage()
				.getImplementationVersion();
	}

}
