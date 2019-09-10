package de.slag.mail;

import java.util.Objects;
import java.util.Properties;

import javax.mail.Session;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.Builder;

public class SessionBuilder implements Builder<Session> {

	private Boolean ssl;
	
	private Integer port;

	public SessionBuilder ssl(boolean ssl) {
		this.ssl = ssl;
		return this;
	}
	
	public SessionBuilder port(Integer port) {
		this.port = port;
		return this;
	}
	
	

	@Override
	public Session build() {
		final Properties properties = new Properties();

		if (!BooleanUtils.isTrue(ssl)) {
			return Session.getDefaultInstance(properties);
		}
		
		Objects.requireNonNull(port, "port is not setted");

		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true"); // If you need to authenticate
		// Use the following if you need SSL
		properties.put("mail.smtp.socketFactory.port", port);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		return Session.getDefaultInstance(properties);
	}

}
