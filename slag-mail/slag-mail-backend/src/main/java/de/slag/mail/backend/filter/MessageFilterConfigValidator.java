package de.slag.mail.backend.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageFilterConfigValidator {

	private static final Log LOG = LogFactory.getLog(MessageFilterConfigValidator.class);

	private final String validationString;

	private String reason;

	public MessageFilterConfigValidator(String validationString) {
		super();
		this.validationString = validationString;
	}

	public boolean isValid() {
		if (!validationString.startsWith("filter.")) {
			final String message = "not a valid 'filter' property: " + validationString;
			reason = message;
			LOG.info(message);
			return false;
		}
		String[] split = validationString.split("\\.");
		final String longAsString = split[1];
		Long valueOf = longOf(longAsString);
		if (valueOf == null) {
			reason = String.format("'%s', not a valid long: '%s'", validationString, longAsString);
			return false;
		}
		return true;
	}

	private Long longOf(String longAsString) {
		try {
			return Long.valueOf(longAsString);
		} catch (NumberFormatException e) {
			LOG.trace("error parsing to long: " + longAsString, e);
		}
		return null;
	}

	public String getReason() {
		return reason;
	}

}
