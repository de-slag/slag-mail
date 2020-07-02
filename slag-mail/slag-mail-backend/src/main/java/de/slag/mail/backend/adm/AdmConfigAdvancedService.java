package de.slag.mail.backend.adm;

import java.util.Map;

public interface AdmConfigAdvancedService extends AdmConfigService {

	Map<String, String> getProperties(String key);

}
