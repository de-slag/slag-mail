package de.slag.mail;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailApp {

	private static final Log LOG = LogFactory.getLog(MailApp.class);

	public static void main(String[] args) {
		LOG.info("start");
		final MailCommandLine mailCommandLine = new MailCommandLineBuilder().arguments(args).build();
		final File configFile = mailCommandLine.getConfigFile();

		final MailProperties mailProperties = new MailProperties(configFile);
		final Collection<String> ids = mailProperties.getIds();

		if (ids.isEmpty()) {
			LOG.warn(String.format("no configured ids found. Set parameter '%s' to configure ids. Config-file: %s",
					String.join(MailProperties.DOT, MailProperties.ACCOUNT, MailProperties.IDS),
					configFile.getAbsolutePath()));
			return;
		}

		final List<MailAccountHandler> handlers = ids.stream().map(id -> new MailAccountHandler(id, mailProperties))
				.collect(Collectors.toList());
		
		handlers.forEach(h -> h.run());
		
		final List<MailReporter> reporters = handlers.stream().map(h -> h.getReporter()).collect(Collectors.toList());
		reporters.forEach(r -> System.out.println(r));
		
		
	}

}
