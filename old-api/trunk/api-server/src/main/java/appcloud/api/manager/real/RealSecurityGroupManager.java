package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.IpRange;
import appcloud.api.beans.Rule;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.securitygroup.RuleCreateReq;
import appcloud.api.beans.securitygroup.SecurityGroupCreateReq;
import appcloud.api.checkutils.SecurityGroupChecker;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.SecurityGroupManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.rpc.tools.RpcException;

public class RealSecurityGroupManager implements SecurityGroupManager {

	private static Logger logger = Logger.getLogger(RealSecurityGroupManager.class);
	
	private VmSecurityGroupProxy groupProxy;
	private BeansGenerator generator = BeansGenerator.getInstance();
	private ResourceSchedulerService scheduler;
	
	private static RealSecurityGroupManager manager =  new RealSecurityGroupManager();
	
	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealAddressPoolManager.class);
	
	public static RealSecurityGroupManager getInstance() {
		return manager;
	}
	
	private RealSecurityGroupManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		generator = BeansGenerator.getInstance();
		groupProxy = (VmSecurityGroupProxy) ConnectionFactory.getTipProxy(
				VmSecurityGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroup> getList(String tenantId, boolean detailed)
			throws Exception {
		if(detailed)
			logger.info(String.format("User %s request to get SECURITYGROUPS, detailed", tenantId));
		else
			logger.info(String.format("User %s request to get SECURITYGROUPS", tenantId));
		
		List<SecurityGroup> apiGroups = new ArrayList<SecurityGroup>();
		QueryObject<VmSecurityGroup> query = new QueryObject<VmSecurityGroup>();
		query.addFilterBean(new FilterBean<VmSecurityGroup>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		
		List<VmSecurityGroup> vmGroups = (List<VmSecurityGroup>) groupProxy.searchAll(query, detailed);
		for(VmSecurityGroup vmGroup : vmGroups) {
			apiGroups.add(generator.VmSecurityGroupToSecurityGroup(vmGroup, detailed));
		}
		return apiGroups;
	}

	@Override
	public SecurityGroup get(String tenantId, Integer groupId) throws Exception {
		logger.info(String.format("User %s request to GET SECURITYGROUP %s", tenantId, groupId));
		VmSecurityGroup vmGroup = SecurityGroupChecker.checkOwner(tenantId, groupId, true);
				groupProxy.getById(groupId, true);

		return generator.VmSecurityGroupToSecurityGroup(vmGroup, true);
	}

	@Override
	public SecurityGroup create(String tenantId, SecurityGroupCreateReq cReq)
			throws Exception {
		logger.info(String.format("User %s request to CREATE SECURITYGROUP %s", tenantId, cReq.name));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.CREATE_SECURITY_GROUP, 
						String.format("User %s request to CREATE SECURITYGROUP %s", tenantId, cReq.name), 
						rpcExtra);
		Integer id = null;
		try {
			id = scheduler.createSecurityGroup(tenantId, cReq.name, cReq.description, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_SECURITY_GROUP, rpcExtra);
			return new SecurityGroup();
		}
		if(id == null)
			throw new OperationFailedException("create security group failed");
		VmSecurityGroup sg = groupProxy.getById(id, false);
		logger.info(String.format("SECURITYGROUP created successfully, id is %s", id));
		return generator.VmSecurityGroupToSecurityGroup(sg, false);
	}

	@Override
	public void delete(String tenantId, Integer groupId) throws Exception {
		logger.info(String.format("User %s request to DELETE SECURITYGROUP %s", tenantId, groupId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.DELETE_SECURITY_GROUP, 
				String.format("User %s request to DELETE SECURITYGROUP %s", tenantId, groupId), rpcExtra);
		try {
			SecurityGroupChecker.checkOwner(tenantId, groupId, false);
		}catch (Exception e) {
			loller.warn(LolLogUtil.DELETE_SECURITY_GROUP, "delete security group:"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		
		Boolean result = false;
		try{
			result = scheduler.deleteSecurityGroup(groupId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DEL_SECURITY_GROUP, rpcExtra);
			return;
		}
		if (result!=null && result) {
			logger.info(String.format("SECURITYGROUP %s deleted successfully", groupId));
		} else {
			logger.info(String.format("Security group %s not deleted", groupId));
			throw new OperationFailedException("delete security group failed");
		}
	}

	@Override
	public Rule createSecurityGroupRule(String tenantId, RuleCreateReq cReq) throws Exception {
		logger.info(String.format("User request to CREATE SECURITYGROUPRULE for group %s", cReq.parentGroupId));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.CREATE_SECURITY_GROUP_RULE, 
				String.format("User request to CREATE SECURITYGROUPRULE for group %s", cReq.parentGroupId),
				rpcExtra);
		
		Integer id = null;
		try {
			id=scheduler.createSecurityGroupRule(cReq.parentGroupId, cReq.fromPort, cReq.toPort, cReq.ipProtocal, cReq.cidr, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_SECURITY_GROUP_RULE, rpcExtra);
			return new Rule();
		}
		if(id == null)
			throw new OperationFailedException("create security group rule failed");
		Rule rule = new Rule();
		
		rule.id = id;
		rule.fromPort = cReq.fromPort;
		rule.toPort = cReq.toPort;
		rule.parentGroupId = cReq.parentGroupId;
		rule.ipProtocal = cReq.ipProtocal;
		rule.ipRange = new IpRange();
		rule.ipRange.cidr = cReq.cidr;
		logger.info(String.format("SECURITYGROUPRULE created successfully, id is %s", id));
		return rule;
	}

	@Override
	public void deleteSecurityGroupRule(String tenantId, Integer ruleId)
			throws Exception {
		logger.info(String.format("User %s request to DELETE SECURITYGROUPRULE %s", tenantId, ruleId));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.DELETE_SECURITY_GROUP_RULE, 
				String.format("User %s request to DELETE SECURITYGROUPRULE %s", tenantId, ruleId), rpcExtra);
		try {
			SecurityGroupChecker.checkRule(tenantId, ruleId);
		}catch (Exception e) {
			loller.warn(LolLogUtil.DELETE_SECURITY_GROUP_RULE, "delete security group rule:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.deleteSecurityGroupRule(ruleId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DELETE_SECURITY_GROUP_RULE, rpcExtra);
			return;
		}
			//FIXME check if succeed
		logger.info(String.format("SECURITYGROUPRULE %s deleted successfully", ruleId));
	}

	@Override
	public SecurityGroup updateSecurityGroup(String tenantId, Integer groupId,
			SecurityGroup group) throws Exception {
		logger.info(String.format("User %s request to UPDATE SECURITYGROUP %s, name:%s description:%s userId:%s",
				tenantId, groupId, group.name, group.description, group.tenantId));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.UPDATE_SECURITY_GROUP, 
				String.format("User %s request to UPDATE SECURITYGROUP %s, name:%s description:%s userId:%s",
						tenantId, groupId, group.name, group.description, group.tenantId), rpcExtra);
		try{
			scheduler.updateSecurityGroup(groupId, group.name, group.description, new Integer(group.tenantId), rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.UPDATE_SECURITY_GROUP, rpcExtra);
			return new SecurityGroup();
		}
		VmSecurityGroup sg = groupProxy.getById(groupId, false);
		SecurityGroup updatedGroup = generator.VmSecurityGroupToSecurityGroup(sg, false);
		logger.info(String.format("SECURITYGROUP %s deleted successfully", groupId));
		return updatedGroup;
	}
}
