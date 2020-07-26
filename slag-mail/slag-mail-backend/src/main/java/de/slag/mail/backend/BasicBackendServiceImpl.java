package de.slag.mail.backend;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.basic.backend.api.BasicBackendService;
import de.slag.basic.model.ConfigProperty;
import de.slag.basic.model.Token;
import de.slag.mail.commons2.filter.MailFilter;
import de.slag.mail.commons2.filter.MailFilterBuilder;

@Service
public class BasicBackendServiceImpl implements BasicBackendService {
	
	@Resource
	private MailFilterService mailFilterService;

	@Override
	public Token getLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BackendState putConfigProperty(String token, ConfigProperty configProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String runDefault(String token) {
		MailFilter mailFilter = new MailFilterBuilder().build();
		// TODO Auto-generated method stub
		return null;
	}

}
