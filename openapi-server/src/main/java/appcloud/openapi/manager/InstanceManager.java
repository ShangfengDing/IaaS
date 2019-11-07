package appcloud.openapi.manager;

import java.util.Map;

import appcloud.api.beans.server.ServerCreateReq;

public interface InstanceManager {

	public ServerCreateReq gainServerCreateReq(String appKeyId, Map<String, String> paramsMap) throws Exception;
}
