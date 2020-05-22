package de.slag.mail.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.mail.webapp.config.MailConfigurationSupport;

@ManagedBean
@SessionScoped
public class MailConfigController extends PageController {

	private static final Log LOG = LogFactory.getLog(MailConfigController.class);

	private final List<String> stateMessages = new ArrayList<>();

	private String key;

	private String value;

	private MailConfigurationSupport mailConfigurationSupport = MailConfigurationSupport.getInstance();

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
		return mailConfigurationSupport.keySet()
				.stream()
				.map(key -> {
					ConfigEntry configEntry = new ConfigEntry();
					configEntry.setKey(key);
					configEntry.setValue(mailConfigurationSupport.get(key));
					return configEntry;
				})
				.collect(Collectors.toList());
	}

	public void saveKeyValue() {
		stateMessages.clear();
		if (StringUtils.isEmpty(value)) {
			mailConfigurationSupport.remove(key);
		} else {
			mailConfigurationSupport.put(key, value);
			stateMessages.add("added: " + key + "=" + value);
		}

		key = null;
		value = null;
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
