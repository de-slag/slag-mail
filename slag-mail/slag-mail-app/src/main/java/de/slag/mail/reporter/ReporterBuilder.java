package de.slag.mail.reporter;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;

import de.slag.mail.MailException;

public class ReporterBuilder implements Builder<Reporter> {

	public static final String TYPE_FILE = "file";

	private String type;

	private String outputFileName;

	@Override
	public Reporter build() {

		if (StringUtils.isEmpty(type)) {
			throw new MailException("type not setted");
		}

		switch (type) {
		case TYPE_FILE:
			if (StringUtils.isEmpty(outputFileName)) {
				throw new MailException("output file name not setted");
			}
			final File file = new File(outputFileName);
			if (!file.exists()) {
				final File parentFile = file.getParentFile();
				if (parentFile == null || !parentFile.exists()) {
					throw new MailException("parent folder does not exist, file: " + outputFileName);
				}
			}

			return new FileReporter(outputFileName);

		default:
			throw new IllegalStateException(this.toString());
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