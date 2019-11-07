/**
 * File: SecurityGroupService.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service;

import appcloud.common.model.RpcExtra;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public interface SecurityGroupService {
	/**
	 * @param name 名字
	 * @param description 描述
	 *
	 * @return 创建的SecurityGroup的Id
	 */
	public Integer createSecurityGroup(String userId, String name, String description, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param sgId SG的id
	 * @param userId 用户名字
	 *
	 * @return 删除的SecurityGroup的Id
	 */
	public Boolean deleteSecurityGroup(Integer sgId, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param sgId 所属SG的id
	 * @param fromPort 来源端口
	 * @param toPort 目的端口
	 * @param protocal 协议
	 * @param range ip范围，CIDR，如"10.0.0.0/8"
	 *
	 * @return 创建的SecurityGroupRule的Id
	 */
	public Integer createSecurityGroupRule(Integer sgId, Integer fromPort, Integer toPort, String protocal, String range, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param ruleId rule的Id
	 *
	 * @return 创建的SecurityGroupRule的Id
	 */
	public void deleteSecurityGroupRule(Integer ruleId, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param sgId SG的id
	 * @param name
	 * @param description
	 */
	public void updateSecurityGroup(Integer sgId, String name, String description, Integer userId, RpcExtra rpcExtra) throws RpcException;
}
