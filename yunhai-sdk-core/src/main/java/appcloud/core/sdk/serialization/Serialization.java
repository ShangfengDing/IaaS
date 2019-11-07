package appcloud.core.sdk.serialization;

import appcloud.core.sdk.exceptions.ClientException;

public interface Serialization {

	public <T> T read(String content, Class<T> type) throws ClientException;
}
