package appcloud.openapi.checkutils;

import org.apache.log4j.Logger;

import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.proxy.VmSecurityGroupRuleProxy;
import appcloud.common.util.ConnectionFactory;

public class SecurityGroupChecker {
	
	private static Logger logger = Logger.getLogger(SecurityGroupChecker.class);
	private static VmSecurityGroupProxy groupProxy = (VmSecurityGroupProxy) ConnectionFactory.getTipProxy(
			VmSecurityGroupProxy.class,
			appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	
	private static VmSecurityGroupRuleProxy ruleProxy = (VmSecurityGroupRuleProxy) ConnectionFactory.getTipProxy(
			VmSecurityGroupRuleProxy.class,
			appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	
	public static VmSecurityGroup checkOwnerByUuid(String tenantId, String groupUuid, Boolean withRules)
			throws Exception {
		VmSecurityGroup vmGroup = groupProxy.getByUuid(groupUuid, withRules);
		if (vmGroup == null) {
			logger.info("security group does not exist");
			throw new ItemNotFoundException("security group does not exist");
		}
		
		boolean isAdmin = false;
		try {
			Integer.parseInt(tenantId);
		} catch(NumberFormatException e) {
			isAdmin = true;
		}
		if(!isAdmin) {
			if (vmGroup.getUserId() == null) {
				logger.info("in vm_securiy_group : user_id is NULL");
				throw new OperationFailedException("tenant id is NULL");
			}
			if (!vmGroup.getUserId().equals(Integer.valueOf(tenantId))) {
				logger.info("security group does not belong to the tenant");
				throw new OperationFailedException("check tenant id");
			}
		}
		return vmGroup;
	}
	
	public static VmSecurityGroup checkOwner(String tenantId, Integer groupId, Boolean withRules)
			throws Exception {
		VmSecurityGroup vmGroup = groupProxy.getById(groupId, withRules);
		if (vmGroup == null) {
			logger.info("security group does not exist");
			throw new ItemNotFoundException("security group does not exist");
		}
		
		boolean isAdmin = false;
		try {
			Integer.parseInt(tenantId);
		} catch(NumberFormatException e) {
			isAdmin = true;
		}
		if(!isAdmin) {
			if (vmGroup.getUserId() == null) {
				logger.info("in vm_securiy_group : user_id is NULL");
				throw new OperationFailedException("tenant id is NULL");
			}
			if (!vmGroup.getUserId().equals(Integer.valueOf(tenantId))) {
				logger.info("security group does not belong to the tenant");
				throw new OperationFailedException("check tenant id");
			}
		}
		return vmGroup;
	}
	
	public static VmSecurityGroupRule checkRule(String tenantId, Integer ruleId) throws Exception{
		VmSecurityGroupRule rule = ruleProxy.getById(ruleId, false);
		if (rule == null) {
			logger.info("security group rule does not exist");
			throw new ItemNotFoundException("security group rule does not exist");
		}
		if (rule.getUserId() == null) {
			return rule;
			//logger.info("in vm_securiy_group_rule : user_id is NULL");
			//throw new OperationFailedException("tenant id is NULL");
		}
		if (!rule.getUserId().equals(Integer.valueOf(tenantId))) {
			logger.info("security group rule does not belong to the tenant");
			throw new OperationFailedException("check tenant id");
		}
		return rule;
	}
}
