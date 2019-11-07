package appcloud.openapi.operate.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.api.beans.Rule;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.openapi.checkutils.SecurityGroupChecker;
import appcloud.openapi.datatype.SecurityGroupDetailItem;
import appcloud.openapi.datatype.SecurityGroupRuleDetailItem;
import appcloud.openapi.manager.util.BeansGenerator;
import appcloud.openapi.manager.util.DealException;
import appcloud.openapi.manager.util.LolHelper;
import appcloud.openapi.manager.util.StringUtil;
import appcloud.openapi.operate.SecurityGroupOperate;
import appcloud.openapi.response.SecurityGroupRulesReponse;
import appcloud.openapi.response.SecurityGroupsDetailReponse;
import appcloud.rpc.tools.RpcException;

@Component
public class SecurityGroupOperateImpl implements SecurityGroupOperate {

	private static Logger logger = Logger.getLogger(SecurityGroupOperateImpl.class);
	private static LolLogUtil loller = LolHelper
			.getLolLogUtil(SecurityGroupOperateImpl.class);
	
	private ResourceSchedulerService scheduler;
	private VmUserProxy vmUserProxy;
	private VmSecurityGroupProxy securityGroupProxy;
	
	private BeansGenerator generator;
	
	public SecurityGroupOperateImpl() {
		scheduler = (ResourceSchedulerService) ConnectionFactory
				.getAMQPService(ResourceSchedulerService.class,
						RoutingKeys.RESOUCE_SCHEDULER);
		vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
				VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		securityGroupProxy = (VmSecurityGroupProxy) ConnectionFactory.getTipProxy(VmSecurityGroupProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		generator = BeansGenerator.getInstance();
	}

	@Override
	public String create(String appkeyId, String groupId, String displayName,
			String description, String requestId) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		loller.info(LolLogUtil.CREATE_VOLUME, String.format("User %s request to CREATE SECURITY_GROUP %s", userId, displayName),
				rpcExtra);
		logger.info(String.format("User %s request to CREATE SECURITY_GROUP %s with requestId %s", userId, displayName, requestId));
		
		Integer id = null;
		try {
			id = scheduler.createSecurityGroup(userId, displayName, description, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_SECURITY_GROUP, rpcExtra);
			return "null";
		}
		if(id == null)
			throw new OperationFailedException("create security group failed");
		VmSecurityGroup sg = securityGroupProxy.getById(id, false);
		logger.info(String.format("SECURITYGROUP created successfully, id is %s", id));
		return sg.getUuid();
	}

	@Override
	public Boolean delete(String appkeyId, String securityGroupId,
			String requestId) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		
		Boolean result = false;
		try{
			VmSecurityGroup vmSecurityGroup = securityGroupProxy.getByUuid(securityGroupId, false);
			result = scheduler.deleteSecurityGroup(vmSecurityGroup.getId(), rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DEL_SECURITY_GROUP, rpcExtra);
			return false;
		}
		if (result!=null && result) {
			logger.info(String.format("SECURITYGROUP %s deleted successfully", securityGroupId));
		} else {
			logger.info(String.format("Security group %s not deleted", securityGroupId));
			throw new OperationFailedException("delete security group failed");
		}
		return true;
	}

	@Override
	public Boolean createRule(String appkeyId,
			String securityGroupId, String ipProtocol, String portRange,
			String sourceCidrIp, String policy, String requestId)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		
		Integer id = null;
		try {
			String[] split = portRange.split("/");
			VmSecurityGroup vmSecurityGroup = securityGroupProxy.getByUuid(securityGroupId, false);
			id=scheduler.createSecurityGroupRule(vmSecurityGroup.getId(), Integer.valueOf(split[0]), Integer.valueOf(split[1]),
					ipProtocol, sourceCidrIp, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_SECURITY_GROUP_RULE, rpcExtra);
			return false;
		}
		if(id == null)
			throw new OperationFailedException("create security group rule failed");
		
		return true;
	}

	@Override
	public SecurityGroupsDetailReponse describe(String appkeyId,
			String RegionId, String pageNum, String pageSize, String requestId)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		List<SecurityGroupDetailItem> apiSecurityGroups = new ArrayList<SecurityGroupDetailItem>();
		
		String logStr = String.format("User %s request to search SecurityGroupS", userId);
		
		QueryObject<VmSecurityGroup> query = new QueryObject<VmSecurityGroup>();
		if(userId != null) {
			query.addFilterBean(new FilterBean<VmSecurityGroup>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		
		int page = 0;
		if (StringUtil.isNumeric(pageNum)) {
			page = Integer.parseInt(pageNum);
			if(page > 0)
				page -= 1;
		}
		
		int size =10;
		if (StringUtil.isNumeric(pageSize)) {
			size = Integer.parseInt(pageSize);
		}
		
		logger.info(logStr);
		
		List<? extends VmSecurityGroup> vmSecurityGroups = securityGroupProxy.searchAll(query, false, page, size);
		for(VmSecurityGroup vmSecurityGroup: vmSecurityGroups) {
			apiSecurityGroups.add(generator.vmSecurityGroupToSecurityGroupDetailItem(vmSecurityGroup));
		}
		
		long totalCount = securityGroupProxy.countSearch(query);
		
		SecurityGroupsDetailReponse securityGroupsDetailReponse = new SecurityGroupsDetailReponse(requestId,apiSecurityGroups,totalCount, pageNum, 
				pageSize);
		return securityGroupsDetailReponse;
	}

	@Override
	public Boolean deleteRules(String appkeyId, String ruleIds, String requestId)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		
		try {
			for(String id:ruleIds.split(":")) {
				scheduler.deleteSecurityGroupRule(Integer.valueOf(id), rpcExtra);
			}
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DELETE_SECURITY_GROUP_RULE, rpcExtra);
			return false;
		}
		return true;
	}

	@Override
	public SecurityGroupRulesReponse describeSGAttitude(String appkeyId, String securityGroupId,
			String requestId) throws Exception {
		
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		List<SecurityGroupRuleDetailItem> rules = new ArrayList<SecurityGroupRuleDetailItem>();
		
		VmSecurityGroup vmGroup = SecurityGroupChecker.checkOwnerByUuid(userId, securityGroupId, true);
		SecurityGroup securityGroup = generator.VmSecurityGroupToSecurityGroup(vmGroup, true);
		
		for (Rule rule : securityGroup.rules) {
			rules.add(generator.ruleToSecurityGroupRuleDetailItem(rule));
		}
		
		long totalCount = securityGroup.rules.size();
		
		SecurityGroupRulesReponse securityGroupRulesReponse = new SecurityGroupRulesReponse(requestId, rules, totalCount);
		securityGroupRulesReponse.setSecurityGroupId(securityGroupId);
		securityGroupRulesReponse.setSecurityGroupName(securityGroup.name);
		securityGroupRulesReponse.setDescription(securityGroup.description);
		return securityGroupRulesReponse;
	}

	@Override
	public Boolean modifyAttitude(String appkeyId, String securityGroupId,
			String SecurityGroupName, String description, String requestId)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		
		try{
			VmSecurityGroup vmSecurityGroup = securityGroupProxy.getByUuid(securityGroupId, false);
			scheduler.updateSecurityGroup(vmSecurityGroup.getId(), SecurityGroupName, description, vmUser.getUserId(), rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.UPDATE_SECURITY_GROUP, rpcExtra);
			return false;
		}
		return true;
	}

}
