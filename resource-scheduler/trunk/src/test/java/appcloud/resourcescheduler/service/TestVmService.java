/**
 * File: TestVmService.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service;

import java.util.HashMap;
import java.util.Map;

import appcloud.common.util.ConnectionFactory;
import appcloud.resourcescheduler.service.impl.VmServiceImpl;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class TestVmService {

	public static void main(String[] args) throws RpcException{
		VmService vmService = new VmServiceImpl();
//		 Map<String, String> metadata = new HashMap<String, String>();
//		 metadata.put("max_bandwidth", "0");
//		 metadata.put("is_high_available", "true");
//		 vmService.createVM("xqlvm4", "125", "ImageTestReserved1", 1, 0, 1, metadata);
		
		vmService.terminateVM("fe334db7c3c04cf18a43fe6cb6fd7b09", null);
		
		 ConnectionFactory.close();
	}
}
