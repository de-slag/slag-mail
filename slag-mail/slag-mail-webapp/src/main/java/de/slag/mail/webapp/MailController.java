package de.slag.mail.webapp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.StringUtils;

import de.slag.mail.webapp.config.MailConfigurationSupport;

@ManagedBean
@SessionScoped
public class MailController extends PageController {

	private final List<String> stateMessages = new ArrayList<>();

	private String password;

	private String login;

	private String host;

	private String application;

	private String filter;

	private MailConfigurationSupport mailConfigurationSupport = MailConfigurationSupport.getInstance();

	public void submit() throws Exception {
		stateMessages.clear();
		stateMessages.add(LocalDateTime.now()
				.toString());
		if (StringUtils.isEmpty(login)) {
			stateMessages.add("'login' not setted");
			return;
		}

		if (StringUtils.isEmpty(password)) {
			stateMessages.add("'password' not setted");
			return;
		}

		if (StringUtils.isEmpty(application)) {
			stateMessages.add("'application' not setted");
			return;
		}

		stateMessages.add(String.format("apply: '%s'", application));

		final MailApplicationRunner mailApplicationRunner = new MailApplicationRunner(password, login, application,
				host);
		mailApplicationRunner.run();

		stateMessages.add(mailApplicationRunner.getResult());
		application = null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<String> getApplications() {
		return mailConfigurationSupport.getAll(key -> key.startsWith("application."));
	}

	public List<String> getFilters() {
		final String filterPrefix = "filter.";
		return mailConfigurationSupport.findBy(entry -> entry.getKey()
				.startsWith(filterPrefix))
				.stream()
				.map(entry -> entry.getKey()
						.substring(filterPrefix.length()))
				.collect(Collectors.toList());
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public List<String> getStateMessages() {
		return stateMessages;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
}
