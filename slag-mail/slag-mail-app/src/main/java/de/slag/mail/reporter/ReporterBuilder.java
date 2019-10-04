package de.slag.mail.reporter;

import java.io.File;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;

import de.slag.mail.MailException;

public class ReporterBuilder implements Builder<Reporter> {

	private String type;

	private String outputFileName;

	@Override
	public Reporter build() {

		Objects.requireNonNull(type, "reporter type not setted");

		switch (type) {
		case "file":
			if (StringUtils.isBlank(outputFileName)) {
				throw new MailException("output file name not setted");
			}
			final File file = new File(outputFileName);

			if (!file.exists()) {
				final File parentFolder = file.getParentFile();
				if (!parentFolder.exists()) {
					throw new MailException(
							String.format("parent folder '%s' does not exists", parentFolder.getAbsolutePath()));
				}
			}

			final FileReporter fileReporter = new FileReporter();
			fileReporter.setFile(file);
			return fileReporter;
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