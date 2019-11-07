package appcloud.common.service;

import appcloud.common.errorcode.NCEC;
import appcloud.rpc.ampq.annotation.RoutingKeyAnnotation;
import appcloud.rpc.tools.RpcException;

public interface NginxControllerService {
	
	/**
	 * Notice a NginxController to update all routing table configure files 
	 * 
	 * @param 	routingKey 
	 * 			define which NginxController should be noticed
	 * @return	NO_ERROR if update routing table successfully, or others if not
	 */
	@RoutingKeyAnnotation(index=0)
	NCEC updateRoutingTable(String routingKey) throws RpcException;
	
//	@RoutingKeyAnnotation(index=0)
//	Boolean addRoutingEntry(String routingKey, String type, String urlSrc, List<UrlConf> urlConfs);
//	
//	@RoutingKeyAnnotation(index=0)
//	Boolean deleteRoutingEntry(String routingKey, String type, String urlSrc);
}
