package de.slag.mail.webapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MailConfigurationSupport {

	private static MailConfigurationSupport instance;

	private Map<String, String> configurations = new HashMap<>();

	private MailConfigurationSupport() {

	}

	public static MailConfigurationSupport getInstance() {
		if (instance == null) {
			instance = new MailConfigurationSupport();
		}
		return instance;
	}

	public String put(String key, String value) {
		return configurations.put(key, value);
	}

	public Set<String> keySet() {
		return configurations.keySet();
	}

	public String get(String key) {
		return configurations.get(key);
	}

	public List<String> getAll(Predicate<String> keyFilter) {
		return configurations.keySet()
				.stream()
				.filter(keyFilter)
				.map(key -> configurations.get(key))
				.collect(Collectors.toList());
	}

	public String remove(String key) {
		return configurations.remove(key);
	}

}
