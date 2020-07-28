package de.slag.mail.backend;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.basic.backend.api.BasicBackendService;
import de.slag.basic.backend.impl.adm.AdmConfigService;
import de.slag.basic.backend.impl.auth.AuthService;
import de.slag.basic.model.ConfigProperty;
import de.slag.basic.model.Token;
import de.slag.mail.commons2.filter.MailFilter;
import de.slag.mail.commons2.filter.MailFilterBuilder;

@Service
public class BasicBackendServiceImpl implements BasicBackendService {

	@Resource
	private MailFilterService mailFilterService;

	@Resource
	private AuthService authService;

	@Resource
	private AdmConfigService admConfigService;

	@Override
	public Token getLogin(String username, String password) {
		String tokenString = authService.getToken(username, password);
		Token token = new Token();
		token.setTokenString(tokenString);
		return token;
	}

	@Override
	public BackendState putConfigProperty(String token, ConfigProperty configProperty) {
		String username = authService.getUsername(token);
		admConfigService.putProperty(username + "." + configProperty.getKey(), configProperty.getValue());
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
