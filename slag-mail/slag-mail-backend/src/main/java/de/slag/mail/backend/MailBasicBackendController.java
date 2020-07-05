package de.slag.mail.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

@RestController
public class MailBasicBackendController implements BasicBackendController {

	private static final Log LOG = LogFactory.getLog(MailBasicBackendController.class);

	private Map<String, Properties> propertyMap = new HashMap<>();

	@Override
	@GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON)
	public Token getLogin(@RequestParam(required = false) String username,
			@RequestParam(required = false) String password) {
		Token token = new Token();
		String tokenString = String.valueOf(System.currentTimeMillis() * -1);
		propertyMap.put(tokenString, new Properties());
		token.setTokenString(tokenString);
		return token;
	}

	@Override
	@GetMapping(path = "/rundefault", produces = MediaType.APPLICATION_JSON)
	public String runDefault(@RequestParam String token) {
		if (!propertyMap.containsKey(token)) {
			return "no configuration found";
		}
		final Properties properties = propertyMap.get(token);
		return properties + "\nall done";
	}

	@Override
	@PutMapping(path = "/configproperty", produces = MediaType.TEXT_PLAIN)
	public Response putConfigProperty(@RequestParam String token, @RequestBody ConfigProperty configProperty) {
		Properties properties = propertyMap.get(token);
		if (properties == null) {
			throw new RuntimeException("properties not found");
		}
		String value = configProperty.getValue();
		String key = configProperty.getKey();
		properties.put(key, value);
		LOG.info(String.format("putted property for token: %s, %s, %s", token, key, value));
		return null;
	}

}