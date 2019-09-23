package de.slag.mail.reporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.slag.mail.MailException;

public class FileReporter implements Reporter {

	private File file;

	FileReporter(String outputFileName) {
		this.file = new File(outputFileName);
	}

	@Override
	public void accept(String t) {
		try {
			accept0(t);
		} catch (IOException e) {
			throw new MailException(e);
		}
	}

	private void accept0(String t) throws IOException {
		final FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(t);
		fileWriter.write("\n");
		fileWriter.close();
	}

}
