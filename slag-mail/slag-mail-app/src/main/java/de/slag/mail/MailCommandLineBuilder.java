package de.slag.mail;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;

public class MailCommandLineBuilder implements Builder<MailCommandLine> {

	private static final String CONFIG_FILE = "c";

	private String[] arguments;

	public MailCommandLineBuilder arguments(String[] arguments) {
		this.arguments = arguments;
		return this;
	}

	public MailCommandLine build() {
		final Options options = new Options();
		options.addOption(CONFIG_FILE, true, "config file");
		final DefaultParser defaultParser = new DefaultParser();
		CommandLine commandLine;
		try {
			commandLine = defaultParser.parse(options, arguments);
		} catch (ParseException e) {
			throw new MailException(e);
		}
		final String configFileName = commandLine.getOptionValue(CONFIG_FILE);
		if (StringUtils.isBlank(configFileName)) {
			throw new MailException("config file name is not setted");
		}
		return new MailCommandLine() {
			
			public String getConfigFileName() {
				return configFileName;
			}
		};
	}

}
