package de.slag.mail.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.slag.basic.backend.api.BasicBackendController;
import de.slag.basic.model.ConfigProperty;
import de.slag.basic.model.Token;
import de.slag.mail.backend.adm.AdmConfigService;
import de.slag.mail.backend.adm.AuthService;

@RestController
public class MailBasicBackendController implements BasicBackendController {

	private static final Log LOG = LogFactory.getLog(MailBasicBackendController.class);

	private Map<String, Properties> propertyMap = new HashMap<>();

	@Resource
	private AdmConfigService admConfigService;

	@Resource
	private AuthService authService;

	@Override
	@GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON)
	public Token getLogin(@RequestParam(required = false) String username,
			@RequestParam(required = false) String password) {

		Token token = new Token();
		token.setTokenString(authService.getToken(username, password));
		propertyMap.put(username, new Properties());
		return token;
	}

	@Override
	@GetMapping(path = "/rundefault", produces = MediaType.APPLICATION_JSON)
	public String runDefault(@RequestParam String token) {
		if (!authService.isValid(token)) {
			return "token invalid";
		}
		final String username = authService.getUsername(token);
		if (!propertyMap.containsKey(username)) {
			return "no configuration found";
		}
		final Properties properties = propertyMap.get(username);
		return properties + "\nall done";
	}

	@Override
	@PutMapping(path = "/configproperty", produces = MediaType.TEXT_PLAIN)
	public Response putConfigProperty(@RequestParam String token, @RequestBody ConfigProperty configProperty) {
		if (!authService.isValid(token)) {
			throw new RuntimeException("token invalid");
		}
		final String username = authService.getUsername(token);

		Properties properties = propertyMap.get(username);
		if (properties == null) {
			throw new RuntimeException("properties not found");
		}
		String value = configProperty.getValue();
		String key = configProperty.getKey();
		properties.put(key, value);
		LOG.info(String.format("putted property for token: %s, %s, %s", username, key, value));
		return null;
	}

}
