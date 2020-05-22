package de.slag.mail.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MailConfigController {

	private String key;

	private String value;

	private final List<ConfigEntry> configurationEntries = new ArrayList<>();

	private MailConfigurationSupport mailConfigurationSupport = MailConfigurationSupport.getInstance();

	@PostConstruct
	public void init() {
		resetConfig();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getState() {
		return "nix";
	}

	public List<ConfigEntry> getConfigurationEntries() {
		return configurationEntries;
	}

	public void saveKeyValue() {
		mailConfigurationSupport.put(key, value);
		key = null;
		value = null;
		resetConfig();
	}

	public void resetConfig() {
		configurationEntries.clear();
		configurationEntries.addAll(mailConfigurationSupport.keySet()
				.stream()
				.map(key -> {
					ConfigEntry configEntry = new ConfigEntry();
					configEntry.setKey(key);
					configEntry.setValue(mailConfigurationSupport.get(key));
					return configEntry;
				})
				.collect(Collectors.toList()));
	}

	public class ConfigEntry {
		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
