package de.slag.mail.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.StringUtils;

@ManagedBean
@SessionScoped
public class MailConfigController extends PageController {

	private final List<String> stateMessages = new ArrayList<>();

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

	public List<ConfigEntry> getConfigurationEntries() {
		return configurationEntries;
	}

	public void saveKeyValue() {
		if (StringUtils.isEmpty(value)) {
			mailConfigurationSupport.remove(key);

		} else {
			mailConfigurationSupport.put(key, value);
		}

		key = null;
		value = null;
		resetConfig();
	}

	public void applyConfiguration() {
		stateMessages.clear();
		stateMessages.add("configuration applied");
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

	@Override
	public List<String> getStateMessages() {
		return stateMessages;
	}

}
