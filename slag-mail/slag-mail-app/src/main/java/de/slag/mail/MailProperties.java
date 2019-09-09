package de.slag.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

public class MailProperties {

	private Properties properties;

	public MailProperties(File configFile) {
		final Properties properties = new Properties();
		try (final FileInputStream inStream = new FileInputStream(configFile)) {
			properties.load(inStream);
		} catch (IOException e) {
			throw new MailException(e);
		}
		this.properties = properties;

	}

	public MailProperties(Properties properties) {
		this.properties = properties;
	}

	private Collection<String> collection(String key) {
		final String property = properties.getProperty(key);
		Objects.requireNonNull(property, "property not found: " + key);
		final String[] split = property.split(";");
		return Arrays.asList(split);
	}

	public Collection<String> getIds() {
		return collection("mail.ids");
	}

	public String getHost(String id) {
		return properties.getProperty("mail." + id + ".host");
	}
	
	public String getUser(String id) {
		return properties.getProperty("mail." + id + ".user");
	}
	
	public String getPassword(String id) {
		return properties.getProperty("mail." + id + ".password");
	}

}
