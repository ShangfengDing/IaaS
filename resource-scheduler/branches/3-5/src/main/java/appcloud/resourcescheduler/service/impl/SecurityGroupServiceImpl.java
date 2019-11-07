/**
 * File: SecurityGroupServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import appcloud.resourcescheduler.service.SecurityGroupService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class SecurityGroupServiceImpl implements SecurityGroupService {

	@Override
	public Integer createSecurityGroup(String userId, String name,
			String description) throws RpcException {
		return null;
	}

	@Override
	public Integer createSecurityGroup(Integer sgId, String userId)
			throws RpcException {
		return null;
	}

	@Override
	public Integer createSecurityGroupRule(Integer sgId, Integer fromPort,
			Integer toPort, String protocal, String range) throws RpcException {
		return null;
	}

	@Override
	public void deleteSecurityGroupRule(Integer ruleId) throws RpcException {

	}

}
