/**
 * File: TestAlert.java
 * Author: weed
 * Create Time: 2013-6-20
 */
package appcloud.resourcescheduler.impl;

import appcloud.common.util.AlertUtil;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class TestAlert {
	public static void main(String[] args) throws RpcException {
		AlertUtil.alert("alert message test1.", "alert message detail test1.", new Exception());
		AlertUtil.setMODULE_NAME("ResourceScheduler");
		AlertUtil.alert("alert message test2.", "alert message detail test2.");
	}
}
