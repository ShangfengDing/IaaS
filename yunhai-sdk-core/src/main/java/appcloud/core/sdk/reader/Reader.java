package appcloud.core.sdk.reader;

import java.util.Map;

import appcloud.core.sdk.exceptions.ClientException;

public interface Reader {

	public Map<String, String> read(String response, String endpoint) throws ClientException;
}
