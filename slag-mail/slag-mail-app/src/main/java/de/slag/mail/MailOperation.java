package de.slag.mail;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.mail.commons.MailException;
import de.slag.mail.commons.model.MailStore;

public abstract class MailOperation<V> implements Callable<V> {

	private static final Log LOG = LogFactory.getLog(MailOperation.class);

	public static final String COUNTMESSAGES = "countmessages";

	public static final String COUNT_SPAM = "count_spam";
	
	public static final String MOVE_SPAM = "move_spam";

	protected static final String TEST = "test";

	private final MailStore mailStore;

	public abstract String getIdentifier();

	public MailOperation(MailStore mailStore) {
		this.mailStore = mailStore;
	}

	protected MailStore getMailStore() {
		return mailStore;
	}

	protected abstract V call0() throws Exception;

	@Override
	public V call() throws Exception {
		LOG.info(String.format("calling '%s'...", getIdentifier()));
		return call0();
	}

	public V callSafe() {
		try {
			return call();
		} catch (Exception e) {
			throw new MailException(e);
		}
	}

}
