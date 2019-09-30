package de.slag.mail.reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileReporterTest {

	FileReporter fileReporter;

	File tempFile;

	@Before
	public void setUp() throws IOException {
		tempFile = File.createTempFile("FileReporterTest", ".txt");
		fileReporter = new FileReporter(tempFile.getPath());
	}

	@Test
	public void testAccept() {
		fileReporter.accept("this");
		fileReporter.accept("is");
		fileReporter.accept("a");
		fileReporter.accept("test");
	}

	@After
	public void shutDown() throws IOException {
		List<String> allLines = Files.readAllLines(Paths.get(tempFile.getAbsolutePath()));
		for (String line : allLines) {
			System.out.println(line);
		}
	}

}
