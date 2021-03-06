package de.slag.mail.reporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.slag.mail.commons.MailException;

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
			synchronized (this) {
				accept0(getDateAndTime() + " " + t);				
			}
		} catch (IOException e) {
			throw new MailException(e);
		}
	}
	
	private String getDateAndTime() {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date());
	}

	private void accept0(String t) throws IOException {
		final FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(t);
		fileWriter.write(System.lineSeparator());
		fileWriter.close();
	}

}
