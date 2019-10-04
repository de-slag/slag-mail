package de.slag.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailProperties {

	private static final Log LOG = LogFactory.getLog(MailProperties.class);

	public static final String IDS = "ids";

	private static final String SSL = "ssl";

	private static final String PORT = "port";

	private static final String PASSWORD = "password";

	private static final String USER = "user";

	private static final String HOST = "host";

	public static final String ACCOUNT = "account";

	private static final String IMAP = "imap";

	public static final String DOT = ".";

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

	private String getProperty0(String key) {
		final String string = properties.getProperty(key);
		LOG.debug(String.format("key: '%s', value '%s'", key, string != null));
		return string;
	}

	private String join(String... strings) {
		return String.join(DOT, strings);
	}

	private Collection<String> collection(String key) {
		final String property = getProperty0(key);
		if (property == null) {
			return Collections.emptyList();
		}

		Objects.requireNonNull(property, "property not found: " + key);
		final String[] split = property.split(",");
		return Arrays.asList(split);
	}

	private boolean getBoolean(String key) {
		return BooleanUtils.isTrue(Boolean.valueOf(getProperty0(key)));
	}

	private Integer getInteger(String key) {
		String property = getProperty0(key);
		if (StringUtils.isBlank(property)) {
			return null;
		}
		return Integer.valueOf(property);
	}

	public Collection<String> getIds() {
		return collection(ACCOUNT + DOT + IDS);
	}

	public String getHost(String id) {
		return getProperty0(join(ACCOUNT, id, IMAP, HOST));
	}

	public String getUser(String id) {
		return getProperty0(join(ACCOUNT, id, IMAP, USER));
	}

	public String getPassword(String id) {
		return getProperty0(join(ACCOUNT, id, IMAP, PASSWORD));
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

	public boolean isTest() {
		return getBoolean("test");
	}

	public Optional<String> getProperty(String key) {
		LOG.info("try to get property key: " + key);
		return Optional.of(properties.getProperty(key));
	}

}
