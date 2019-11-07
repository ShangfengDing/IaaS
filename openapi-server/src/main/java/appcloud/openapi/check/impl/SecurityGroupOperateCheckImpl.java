package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.proxy.VmSecurityGroupRuleProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.openapi.check.SecurityGroupOperateCheck;
import appcloud.openapi.checkutils.SecurityGroupChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;

@Component
public class SecurityGroupOperateCheckImpl implements SecurityGroupOperateCheck{
	private static Logger logger = Logger.getLogger(SecurityGroupOperateCheckImpl.class);
	
	private static SecurityGroupOperateCheckImpl securityGroupOperateCheck = new SecurityGroupOperateCheckImpl();
	public static SecurityGroupOperateCheckImpl getInstance(){
		return securityGroupOperateCheck;
	}
	
	private static CommonGenerator commonGenerator  = new CommonGenerator();
	private static VmUserProxy vmUserProxy;
	private static VmSecurityGroupRuleProxy vmSecurityGroupRuleProxy;
	
	private SecurityGroupOperateCheckImpl() {
		super();
		vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(
				VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmSecurityGroupRuleProxy = (VmSecurityGroupRuleProxy)ConnectionFactory.getTipProxy(
				VmSecurityGroupRuleProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}


	@Override
	public Map<String, String> checkCreate(Map<String, String> paramsMap)
			throws Exception {
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
			
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}




	@Override
	public Map<String, String> checkDelete(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			SecurityGroupChecker.checkOwner(userId, Integer.valueOf(paramsMap.get(Constants.SECURITY_GROUP_ID)), false);
		}catch (Exception e) {
			return commonGenerator.operationDeny(e.getMessage(), null);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkAuthorize(Map<String, String> paramsMap)
			throws Exception {
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
			
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkDescribeSGsParams(
			Map<String, String> paramsMap) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkRevoke(Map<String, String> paramsMap)
			throws Exception {
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = user.getUserId()+"";
		try {
			SecurityGroupChecker.checkOwner(userId, Integer.valueOf(paramsMap.get(Constants.SECURITY_GROUP_ID)), false);
		}catch (Exception e) {
			return commonGenerator.operationDeny(e.getMessage(), null);
		}
		
		QueryObject<VmSecurityGroupRule> query = new QueryObject<VmSecurityGroupRule>();
		/*if(userId != null) {
			query.addFilterBean(new FilterBean<VmSecurityGroupRule>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
		}*/
		if(paramsMap.get(Constants.SECURITY_GROUP_ID) != null) {
			query.addFilterBean(new FilterBean<VmSecurityGroupRule>("groupId", Integer.valueOf(paramsMap.get(Constants.SECURITY_GROUP_ID)), FilterBeanType.EQUAL));
		}
		if(paramsMap.get(Constants.SOURCE_CIDR_IP) != null) {
			query.addFilterBean(new FilterBean<VmSecurityGroupRule>("ipRange", paramsMap.get(Constants.SOURCE_CIDR_IP), FilterBeanType.EQUAL));
		}
		if(paramsMap.get(Constants.PORT_RANGE) != null) {
			String[] ports = paramsMap.get(Constants.PORT_RANGE).split("/");
			query.addFilterBean(new FilterBean<VmSecurityGroupRule>("portStart", Integer.valueOf(ports[0]), FilterBeanType.EQUAL));
			query.addFilterBean(new FilterBean<VmSecurityGroupRule>("portEnd", Integer.valueOf(ports[1]), FilterBeanType.EQUAL));
		}
		if(paramsMap.get(Constants.POLICY) != null) {
			//TODO 目前云海还没有此选项，全部为accept
		}
		List<? extends VmSecurityGroupRule> rules = vmSecurityGroupRuleProxy.searchAll(query, false);
		if(rules.size() == 0) {
			return commonGenerator.notFound(null, "rules");
		} else {
			StringBuilder builder = new StringBuilder();
			for (VmSecurityGroupRule vmSecurityGroupRule : rules) {
				builder.append(vmSecurityGroupRule.getId()).append(":");
			}
			paramsMap.put("ruleIds", builder.toString());
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkDescribeSGAttitudes(
			Map<String, String> paramsMap) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			SecurityGroupChecker.checkOwner(userId, Integer.valueOf(paramsMap.get(Constants.SECURITY_GROUP_ID)), false);
		}catch (Exception e) {
			return commonGenerator.operationDeny(e.getMessage(), null);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkModifyAttitude(Map<String, String> paramsMap) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			SecurityGroupChecker.checkOwner(userId, Integer.valueOf(paramsMap.get(Constants.SECURITY_GROUP_ID)), false);
		}catch (Exception e) {
			return commonGenerator.operationDeny(e.getMessage(), null);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
		
	}


}
