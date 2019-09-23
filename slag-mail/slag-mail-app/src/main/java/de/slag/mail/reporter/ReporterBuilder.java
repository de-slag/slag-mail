package de.slag.mail.reporter;

import org.apache.commons.lang3.builder.Builder;

import de.slag.mail.MailException;

public class ReporterBuilder implements Builder<Reporter> {

	private String type;

	private String outputFileName;

	@Override
	public Reporter build() {

		switch (type) {
		case "file":
			return new FileReporter(outputFileName);

		default:
			throw new MailException("builder type not known: " + type);
		}

	}

	public ReporterBuilder type(String type) {
		this.type = type;
		return this;
	}

	public ReporterBuilder outputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
		return this;
	}

}