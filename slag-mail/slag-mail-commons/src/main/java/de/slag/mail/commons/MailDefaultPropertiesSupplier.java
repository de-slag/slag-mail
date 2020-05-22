package de.slag.mail.commons;

import java.util.Properties;
import java.util.function.Supplier;

public class MailDefaultPropertiesSupplier implements Supplier<Properties> {

	@Override
	public Properties get() {
		final Properties properties = new Properties();

//		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true"); // If you need to authenticate
		// Use the following if you need SSL
		properties.put("mail.smtp.socketFactory.port", "143");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");

		return properties;
	}

}
