package de.slag.mail;

import java.util.Collection;

import javax.mail.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.mail.commons.model.MailStore;
import de.slag.mail.commons.model.MailStoreBuilder;
import de.slag.mail.reporter.Reporter;
import de.slag.mail.reporter.ReporterBuilder;

public class MailAccountHandler implements Runnable {

	private static final Log LOG = LogFactory.getLog(MailAccountHandler.class);

	private final String id;

	private final Reporter reporter;

	private final MailProperties mailProperties;

	public MailAccountHandler(String id, MailProperties mailProperties) {
		this.id = id;
		this.reporter = new ReporterBuilder().type("file")
				.outputFileName(mailProperties.getProperty("reporter.outputFileName").get()).build();
		this.mailProperties = mailProperties;
	}

	@Override
	public void run() {
		LOG.info("process id: " + id);
		final Session session = new SessionBuilder().ssl(mailProperties.isSsl(id)).port(mailProperties.getPort(id))
				.build();

		final String host = mailProperties.getHost(id);
		final String password = mailProperties.getPassword(id);
		final String user = mailProperties.getUser(id);

		final MailStore mailStore = new MailStoreBuilder().session(session).protocol("imap").host(host)
				.password(password).user(user).build();

		final Collection<MailOperation<?>> ops = MailOperationsUtils.determineOperations(mailProperties, mailStore, id);

		ops.forEach(op -> report(op.callSafe()));
	}

	private void report(Object o) {
		reporter.accept(id + ": " + o.toString());
		// System.out.println(o);
	}

	public Reporter getReporter() {
		return reporter;
	}

}
