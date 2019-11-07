package appcloud.common.util;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.Host;
import appcloud.common.model.Nginx;

public class RoutingKeyGenerator {
	public static String getRoutingKey(String prefix, String uuid){
		return prefix + "." + uuid;
	}
	public static String getRoutingKey(String prefix, Host host){
		return prefix + "." + host.getUuid();
	}
	public static String getNodeAgentRoutingKey(Host host) {
		return RoutingKeys.NODE_AGENT_PRE + "." + host.getUuid();		
	}

	public static String getNginxControllerRoutingKey(Nginx nginx) {
		return RoutingKeys.NGINX_CONTROLLER_PRE + "." + nginx.getInnerIp();		
	}
}
