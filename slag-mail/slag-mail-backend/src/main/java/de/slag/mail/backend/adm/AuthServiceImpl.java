package de.slag.mail.backend.adm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	private static final Log LOG = LogFactory.getLog(AuthServiceImpl.class);

	@Resource
	private AdmConfigAdvancedService admConfigAdvancedService;

	private Map<String, Collection<AuthToken>> tokenMap = new HashMap<>();

	private Collection<User> users = new ArrayList<>();

	@PostConstruct
	public void setUp() {
		users.addAll(setUpUsers());
		LOG.info("users: " + users);
	}

	@Override
	public String getToken(String username, String password) {
		final User user = users.stream()
				.filter(u -> u.getUsername()
						.equals(username))
				.findAny()
				.get();
		if (!user.getPassword()
				.equals(password)) {
			throw new RuntimeException("password wrong");
		}

		if (!tokenMap.containsKey(username)) {
			tokenMap.put(username, new ArrayList<>());
		}
		final Collection<AuthToken> collection = tokenMap.get(username);
		final AuthToken authToken = new AuthToken();
		collection.add(authToken);
		return authToken.getToken();
	}

	private Collection<User> setUpUsers() {
		final Map<String, String> properties = admConfigAdvancedService.getProperties("adm.user.");
		LOG.info(properties);
		final Set<String> userIds = properties.keySet()
				.stream()
				.map(k -> (String) k)
				.map(k -> k.split("\\.")[2])
				.collect(Collectors.toSet());

		return userIds.stream()
				.map(id -> {
					String key = "adm.user." + id + ".username";
					LOG.debug(key);
					String key2 = "adm.user." + id + ".password";
					String username = properties.get(key);
					String password = properties.get(key2);

					return new User(Long.valueOf(id), username, password);

				})
				.collect(Collectors.toList());

	}

	private class AuthToken {
		private final String token;

		private final LocalDateTime created = LocalDateTime.now();

		public AuthToken() {
			super();
			this.token = String.valueOf(System.currentTimeMillis());
		}

		public String getToken() {
			return token;
		}

		public LocalDateTime getCreated() {
			return created;
		}

	}

	private class User {

		private final Long id;

		private final String username;

		private final String password;

		public User(Long id, String username, String password) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
		}

		public Long getId() {
			return id;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		@Override
		public String toString() {
			return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
		}

	}

	@Override
	public String getUsername(String token) {
		return tokenMap.keySet()
				.stream()
				.filter(username -> {
					return tokenMap.get(username)
							.stream()
							.anyMatch(authToken -> authToken.getToken()
									.equals(token));
				})
				.findAny()
				.get();
	}

	@Override
	public boolean isValid(String token) {
		return getUsername(token) != null;
	}
}
