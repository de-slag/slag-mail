package de.slag.mail.reporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.slag.mail.MailException;

public class FileReporter implements Reporter {

	private File file;

	public FileReporter() {

	}

	void setFile(File file) {
		this.file = file;
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
		final String date = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss.SSS").format(new Date());
		final FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(date + ": " + t);
		fileWriter.write(System.lineSeparator());
		fileWriter.close();
	}

}
