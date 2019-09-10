package de.slag.mail.model;

import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.lang3.builder.Builder;

import de.slag.mail.MailException;

public class MailStoreBuilder implements Builder<MailStore> {

	private Session session;

	private String protocol;

	private String host;

	private String user;

	private String password;

	public MailStoreBuilder host(String host) {
		this.host = host;
		return this;
	}

	public MailStoreBuilder password(String password) {
		this.password = password;
		return this;
	}

	public MailStoreBuilder user(String user) {
		this.user = user;
		return this;
	}

	public MailStoreBuilder protocol(String protocol) {
		this.protocol = protocol;
		return this;
	}

	public MailStoreBuilder session(Session session) {
		this.session = session;
		return this;
	}

	@Override
	public MailStore build() {
		Objects.requireNonNull(session, "session not setted");

		Store store;
		try {
			store = session.getStore(protocol);
		} catch (NoSuchProviderException e) {
			throw new MailException(e);
		}
		try {
			store.connect(host, user, password);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
		return new MailStore(store);
	}

}
