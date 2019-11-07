package appcloud.openapi.manager;

import java.util.Map;

public interface CommonManager {

	public Map<String, String> getAuthenticate(Map<String, String> paramsMap) throws Exception;
	
	public Map<String, String> getAppKeyPair(Map<String, String> paramsMap) throws Exception;
}
