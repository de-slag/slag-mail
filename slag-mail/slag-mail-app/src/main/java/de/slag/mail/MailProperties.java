package de.slag.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

+public class MailProperties {

	private static final String ACCOUNT = "account";

	private static final String DOT = ".";

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

	private boolean bool(String key) {
		return BooleanUtils.isTrue(Boolean.valueOf(properties.getProperty(key)));
	}

	public Collection<String> getIds() {
		return collection(ACCOUNT + DOT + "ids");
	}

	public String getHost(String id) {
		return properties.getProperty(ACCOUNT + DOT + id + DOT + "host");
	}

	public String getUser(String id) {
		return properties.getProperty(ACCOUNT + DOT + id + DOT + "user");
	}

	public String getPassword(String id) {
		return properties.getProperty(ACCOUNT + DOT + id + DOT + "password");
	}

	public Integer getPort(String id) {
		String property = properties.getProperty(ACCOUNT + DOT + id + DOT + "port");
		if (StringUtils.isBlank(property)) {
			return null;
		}
		return Integer.valueOf(property);
	}

	public boolean isSsl(String id) {
		return bool(ACCOUNT + DOT + id + DOT + "ssl");
	}

}
