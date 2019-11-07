/**
 * File: SecurityGroupServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.errorcode.VMSEC;
import appcloud.common.model.MessageLog;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.proxy.VmSecurityGroupRuleProxy;
import appcloud.common.service.VMSchedulerService;
import appcloud.common.util.AlertUtil;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.UuidUtil;
import appcloud.resourcescheduler.ResourceScheduler;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.resourcescheduler.service.SecurityGroupService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class SecurityGroupServiceImpl implements SecurityGroupService {

	private static Logger logger = Logger.getLogger(SecurityGroupServiceImpl.class);
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = ResourceScheduler.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.RESOURCE_SCHEDULER, SecurityGroupServiceImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	private static VMSchedulerService vmScheduler = (VMSchedulerService) ConnectionManager.getInstance()
			.getAMQPService(VMSchedulerService.class, RoutingKeys.VM_SCHEDULER);
	
	VmSecurityGroupProxy vmSecurityGroupProxy = (VmSecurityGroupProxy) ConnectionManager.getInstance().getDBProxy(VmSecurityGroupProxy.class);
	VmSecurityGroupRuleProxy vmSecurityGroupRuleProxy = (VmSecurityGroupRuleProxy) ConnectionManager.getInstance().getDBProxy(VmSecurityGroupRuleProxy.class);
	
	@Override
	public Integer createSecurityGroup(String userId, String name,
			String description, RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "createSecurityGroup: " + userId + ", " + name + ", "
				+ description;
		logger.debug(paramInfos);
		
		try {
			String uuid = UuidUtil.getRandomUuid();
			vmSecurityGroupProxy.save(new VmSecurityGroup(null, uuid, name, description, Integer.parseInt(userId)));
			VmSecurityGroup securityGroup = vmSecurityGroupProxy.getByUuid(uuid, false);
			loller.info(LolLogUtil.CREATE_SECURITY_GROUP, paramInfos, rpcExtra);
			return securityGroup.getId();
		}catch (Exception e) {
			String error = "createSecurityGroup failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.CREATE_SECURITY_GROUP, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("用户" + userId + "创建安全组失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
		return null;
	}

	@Override
	public Boolean deleteSecurityGroup(Integer sgId, RpcExtra rpcExtra)
			throws RpcException {
		String paramInfos = "deleteSecurityGroup: " + sgId;
		logger.debug(paramInfos);
		
		try {
			VmSecurityGroup securityGroup = vmSecurityGroupProxy.getById(sgId, false);
			boolean canBeDelete = vmScheduler.delSecurityGroup(securityGroup, rpcExtra).compareTo(VMSEC.DELSG_PERMISSION) == 0;
			if (canBeDelete){
				vmSecurityGroupProxy.deleteById(sgId);
			}
			loller.info(LolLogUtil.DELETE_SECURITY_GROUP, paramInfos, rpcExtra);
			return canBeDelete;
			
		}catch (Exception e) {
			String error = "deleteSecurityGroup failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.DELETE_SECURITY_GROUP, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("安全组" + sgId +"删除失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
		return null;
	}

	@Override
	public Integer createSecurityGroupRule(Integer sgId, Integer fromPort,
			Integer toPort, String protocol, String range, RpcExtra rpcExtra) throws RpcException {
//		vmSecurityGroupRuleProxy.save(new VmSecurityGroupRule());
		String paramInfos = "createSecurityGroupRule: " + sgId + ", " + fromPort+ ", " + toPort+ ", " + protocol+ ", " + range;
		logger.debug(paramInfos);
		
		try {
			String uuid = UuidUtil.getRandomUuid();
			vmSecurityGroupRuleProxy.save(new VmSecurityGroupRule(null, sgId, uuid, protocol, fromPort, toPort, range, null, null, null, null, null, null, null));
			
			VmSecurityGroupRule securityGroupRule = vmSecurityGroupRuleProxy.getByUuid(uuid, true);
			
			VmSecurityGroup securityGroup = securityGroupRule.getVmSecurityGroup();
			securityGroupRule.setVmSecurityGroup(null);
			
			@SuppressWarnings("unchecked")
			List<VmSecurityGroupRule> securityGroupRules = (List<VmSecurityGroupRule>) vmSecurityGroupRuleProxy.getByGroupId(sgId, false);
			
			logger.debug("securityGroup: " + securityGroup + " securityGroupRule: " + securityGroupRule + " securityGroupRules: " + securityGroupRules);
			vmScheduler.defineSecurityGroup(securityGroup, securityGroupRules, rpcExtra);
			loller.info(LolLogUtil.CREATE_SECURITY_GROUP_RULE, paramInfos, rpcExtra);
			return securityGroupRule.getId();
		}catch (Exception e) {
			String error = "createSecurityGroupRule failed! " + paramInfos;
			logger.error(error, e);
			loller.info(LolLogUtil.CREATE_SECURITY_GROUP_RULE, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("安全组" + sgId +"创建规则失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
		return null;
	}

	@Override
	public void deleteSecurityGroupRule(Integer ruleId, RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "deleteSecurityGroupRule: " + ruleId ;
		logger.debug(paramInfos);
		
		try {
			VmSecurityGroupRule securityGroupRule = vmSecurityGroupRuleProxy.getById(ruleId, true);
			logger.debug("securityGroupRule" + securityGroupRule);
			vmSecurityGroupRuleProxy.deleteById(ruleId);
			
			VmSecurityGroup securityGroup = securityGroupRule.getVmSecurityGroup();
			securityGroupRule.setVmSecurityGroup(null);
			
			@SuppressWarnings("unchecked")
			List<VmSecurityGroupRule> securityGroupRules = (List<VmSecurityGroupRule>) vmSecurityGroupRuleProxy.getByGroupId(securityGroupRule.getGroupId(), false);
			
			vmScheduler.defineSecurityGroup(securityGroup, securityGroupRules, rpcExtra);
			loller.info(LolLogUtil.DELETE_SECURITY_GROUP_RULE, paramInfos, rpcExtra);
		}catch (Exception e) {
			String error = "deleteSecurityGroupRule failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.DELETE_SECURITY_GROUP_RULE, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("安全组规则" + ruleId +"删除失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
	}

	@Override
	public void updateSecurityGroup(Integer sgId, String name,
			String description, Integer userId, RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "updateSecurityGroup: " + sgId + ", " + name + ", " + description;
		logger.debug(paramInfos);
		
		try {
			VmSecurityGroup  securityGroup = vmSecurityGroupProxy.getById(sgId, false);
			securityGroup.setName(name);
			securityGroup.setDescription(description);
			securityGroup.setUserId(userId);
			vmSecurityGroupProxy.update(securityGroup);
			loller.info(LolLogUtil.UPDATE_SECURITY_GROUP, paramInfos, rpcExtra);
		}catch (Exception e) {
			String error = "updateSecurityGroup failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.UPDATE_SECURITY_GROUP, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("安全组" + sgId +"更新失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
	}
}
