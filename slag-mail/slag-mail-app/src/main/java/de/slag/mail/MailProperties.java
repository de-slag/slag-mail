package de.slag.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

public class MailProperties {

	private static final String IDS = "ids";

	private static final String SSL = "ssl";

	private static final String PORT = "port";

	private static final String PASSWORD = "password";

	private static final String USER = "user";

	private static final String HOST = "host";

	private static final String ACCOUNT = "account";

	private static final String IMAP = "imap";

	private static final String DOT = ".";

	private static final String OPERATION = "operation";

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

	private String join(String... strings) {
		return String.join(DOT, strings);
	}

	private Collection<String> collection(String key) {
		final String property = properties.getProperty(key);
		if (property == null) {
			return Collections.emptyList();
		}

		Objects.requireNonNull(property, "property not found: " + key);
		final String[] split = property.split(";");
		return Arrays.asList(split);
	}

	private boolean getBoolean(String key) {
		return BooleanUtils.isTrue(Boolean.valueOf(properties.getProperty(key)));
	}

	private Integer getInteger(String key) {
		String property = properties.getProperty(key);
		if (StringUtils.isBlank(property)) {
			return null;
		}
		return Integer.valueOf(property);
	}

	public Collection<String> getIds() {
		return collection(ACCOUNT + DOT + IDS);
	}

	public String getHost(String id) {
		return properties.getProperty(join(ACCOUNT, id, IMAP, HOST));
	}

	public String getUser(String id) {
		return properties.getProperty(join(ACCOUNT, id, IMAP, USER));
	}

	public String getPassword(String id) {
		return properties.getProperty(join(ACCOUNT, id, IMAP, PASSWORD));
	}

	public Integer getPort(String id) {
		return getInteger(join(ACCOUNT, id, IMAP, PORT));
	}

	public boolean isSsl(String id) {
		return getBoolean(join(ACCOUNT, id, IMAP, SSL));
	}

	public Collection<String> getOperationIds(String id) {
		return collection(join(ACCOUNT, id, OPERATION, IDS));
	}

}
