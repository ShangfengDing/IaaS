/**
 * File: TestVmService.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service;

import java.util.HashMap;
import java.util.Map;

import appcloud.resourcescheduler.service.impl.VmServiceImpl;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class TestVmService {

	public static void main(String[] args) throws RpcException{
		VmService vmService = new VmServiceImpl();
		 Map<String, String> metadata = new HashMap<String, String>();
		 metadata.put("max_bandwidth", "10M");
		 metadata.put("is_high_available", "true");
		 vmService.createVM("test1", "125", "1", 1, 0, 1, metadata);
	}
}
