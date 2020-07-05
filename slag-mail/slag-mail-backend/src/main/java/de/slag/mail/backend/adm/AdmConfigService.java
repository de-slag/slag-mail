package de.slag.mail.backend.adm;

public interface AdmConfigService {

	String getProperty(String key);

	void putProperty(String key, String value);

}
