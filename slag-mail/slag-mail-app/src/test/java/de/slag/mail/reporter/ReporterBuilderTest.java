package de.slag.mail.reporter;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.slag.mail.commons.MailException;

public class ReporterBuilderTest {

	ReporterBuilder reporterBuilder;
	
	File tempFile;

	@Before
	public void setUp() throws IOException {
		reporterBuilder = new ReporterBuilder();
		tempFile = File.createTempFile("ReporterBuilderTest", ".tmp");
	}

	@Test(expected = IllegalStateException.class)
	public void testBuildFailsWrongType() {
		reporterBuilder.type("xxx");
		reporterBuilder.outputFileName(tempFile.getPath());
		reporterBuilder.build();
	}

	@Test(expected = MailException.class)
	public void testBuildFailsParentFolderNotExists() {
		reporterBuilder.type(ReporterBuilder.TYPE_FILE);
		reporterBuilder.outputFileName("xy");
		reporterBuilder.build();
	}

	@Test(expected = MailException.class)
	public void testBuildFailsNoOutputfileConfigured() {
		reporterBuilder.type(ReporterBuilder.TYPE_FILE);
		reporterBuilder.build();
	}

	@Test(expected = MailException.class)
	public void testBuildFailsNotConfigured() {
		reporterBuilder.build();
	}

}
