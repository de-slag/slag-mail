package de.slag.mail.webapp.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.slag.mail.commons.MailApplicationSupport;

public class MailConfigurationSupport {

	private static MailConfigurationSupport instance;

	private Map<String, String> configurations = new HashMap<>();

	private MailConfigurationSupport() {
		MailApplicationSupport.getApplicationNames()
				.forEach(name -> configurations.put("application." + name, name));
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

	public List<MailConfigurationEntry> findBy(Predicate<MailConfigurationEntry> filter) {
		return getAll().stream()
				.filter(filter)
				.collect(Collectors.toList());
	}

	public List<String> getAll(Predicate<String> keyFilter) {
		return configurations.keySet()
				.stream()
				.filter(keyFilter)
				.map(key -> configurations.get(key))
				.collect(Collectors.toList());
	}

	public List<MailConfigurationEntry> getAll() {
		return configurations.keySet()
				.stream()
				.map(key -> {
					final MailConfigurationEntry mailConfigurationEntry = new MailConfigurationEntry();

					mailConfigurationEntry.setKey(key);
					mailConfigurationEntry.setValue(configurations.get(key));

					return mailConfigurationEntry;
				})
				.collect(Collectors.toList());
	}

	public String remove(String key) {
		return configurations.remove(key);
	}

	public void clear() {
		configurations.clear();
	}
}
