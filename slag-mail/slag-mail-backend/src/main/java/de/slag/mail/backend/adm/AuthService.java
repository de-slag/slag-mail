package de.slag.mail.backend.adm;

public interface AuthService {

	String getToken(String username, String password);

	String getUsername(String token);

	boolean isValid(String token);

}
