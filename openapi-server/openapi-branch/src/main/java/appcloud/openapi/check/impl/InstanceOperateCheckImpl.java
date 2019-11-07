package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.api.enums.ServerOperationEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.InstanceOperateCheck;
import appcloud.openapi.checkutils.AcGroupChecker;
import appcloud.openapi.checkutils.InstanceChecker;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;

@Component
public class InstanceOperateCheckImpl implements InstanceOperateCheck{
	private static Logger logger = Logger.getLogger(InstanceOperateCheckImpl.class);
	private static InstanceOperateCheckImpl instanceOperateCheck = new InstanceOperateCheckImpl();
	public static InstanceOperateCheckImpl getInstance(){
		return instanceOperateCheck;
	}
	private static CommonGenerator commonGenerator  = new CommonGenerator();
	private static VmUserProxy vmUserProxy;
	private static VmInstanceProxy vmInstanceProxy;
	private static HostProxy hostProxy;
	
	private InstanceOperateCheckImpl() {
		super();
		vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(
				VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmInstanceProxy = (VmInstanceProxy)ConnectionFactory.getTipProxy(
				VmInstanceProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		hostProxy = (HostProxy)ConnectionFactory.getTipProxy(
				HostProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	public Map<String, String> checkCreateInstance(Map<String, String> paramsMap)
			throws Exception {
		//首先检查当前用户的所有云主机实例是否已达上限
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		if(!AcGroupChecker.checkInstanceCount(user.getUserId()+"")) {
			logger.warn("user " + user.getUserId() + " request to create vm while his vm number reaches the upper limit");
			String message = "User living instances quota exceeded.";
			return commonGenerator.operationDeny(message, null);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	public Map<String, String> checkStartInstance(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态，只有云主机处于关机状态时，才能进行启动操作
		logger.info("Check operation permission before starting instance");
		return checkOperatePermission(paramsMap, ActionConstants.START_INSTANCE);
	}

	public Map<String, String> checkRecoveryInstance(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态，只有云主机处于关机状态时，才能进行启动操作
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	public Map<String, String> checkStopInstance(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态，只有云主机处于运行状态时，才能进行停止操作
		logger.info("Check operation permission before stopping instance");
		if(null!=paramsMap.get(Constants.FORCE_STOP) && paramsMap.get(Constants.FORCE_STOP).equals("true")) {
			logger.info("Check operation permission before stopping instance forced.");
			return checkOperatePermission(paramsMap, ActionConstants.FORCE_STOP_INSTANCE);
		}
		return checkOperatePermission(paramsMap, ActionConstants.STOP_INSTANCE);
	}

	public Map<String, String> checkRebootInstance(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态
		logger.info("Check operation permission before rebootting instance");
		return checkOperatePermission(paramsMap, ActionConstants.REBOOT_INSTANCE);
	}

	public Map<String, String> checkDeleteInstance(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态
		logger.info("Check operation permission before deleteing instance");
		if(null!=paramsMap.get(Constants.FORCE_DELETE) && paramsMap.get(Constants.FORCE_DELETE).equals("true")) {
			logger.info("Check operation permission before deleteing instance forced.");
			return checkOperatePermission(paramsMap, ActionConstants.FORCE_STOP_INSTANCE);
		}
		return checkOperatePermission(paramsMap, ActionConstants.DELETE_INSTANCE);
	}
	
	public Map<String, String> checkResetInstance(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态
		logger.info("Check operation permission before resetting instance");
		return checkOperatePermission(paramsMap, ActionConstants.RESET_INSTANCE);
	}
	@Override
	public Map<String, String> checkIsoDetach(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态
		logger.info("Check operation permission before iso detach");
		return checkOperatePermission(paramsMap, ActionConstants.ISO_DETACH);
	}

	@Override
	public Map<String, String> checkIsoBoot(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态
		logger.info("Check operation permission before iso boot");
		return checkOperatePermission(paramsMap, ActionConstants.ISO_BOOT);
	}
	
	@Override
	public Map<String, String> checkMigrate(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态
		logger.info("Check operation permission before migrate instance");
		if(vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), false, true, false, false, false, false, false, false).getAvailabilityClusterId().intValue()
				!= hostProxy.getByUuid(paramsMap.get(Constants.HOST_ID), true, false, false).getClusterId().intValue()){
			logger.warn("Instance and host are not be in the same cluster !");
			return commonGenerator.internalError(null, null);
		}
		return checkOperatePermission(paramsMap, ActionConstants.MIGRATE_INSTANCE);
	}

	@Override
	public Map<String, String> checkOnlineMigrate(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态
		logger.info("Check operation permission before online migrate instance");
		if(vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), false, true, false, false, false, false, false, false).getAvailabilityClusterId().intValue()
				!= hostProxy.getByUuid(paramsMap.get(Constants.HOST_ID), true, false, false).getClusterId().intValue()){
			logger.warn("Instance and host are not be in the same cluster !");
			return commonGenerator.internalError(null, null);
		}
		return checkOperatePermission(paramsMap, ActionConstants.ONLINE_MIGRATE_INSTANCE);
	}
	@Override
	public Map<String, String> checkSuspend(Map<String, String> paramsMap)
			throws Exception {
		logger.info("Check suspend permission before iso detach");
		return checkOperatePermission(paramsMap, ActionConstants.SUSPEND_INSTANCE);
	}

	@Override
	public Map<String, String> checkResume(Map<String, String> paramsMap)
			throws Exception {
		logger.info("Check resume permission before iso detach");
		return checkOperatePermission(paramsMap, ActionConstants.RESUME_INSTANCE);
	}
	Map<String, String> checkOperatePermission(Map<String, String> paramsMap, String operationAction)
			throws Exception {
		//检查当前云主机的状态，根据云主机所处的状态，对云主机进行相应的操作
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		ServerOperationEnum operation = null;
		switch (operationAction) {
		case ActionConstants.START_INSTANCE :
			operation = ServerOperationEnum.OS_START;
			break;
		case ActionConstants.STOP_INSTANCE :
			operation = ServerOperationEnum.OS_STOP;
			break;
		case ActionConstants.FORCE_STOP_INSTANCE :
			operation = ServerOperationEnum.AC_FORCE_STOP;
			break;
		case ActionConstants.DELETE_INSTANCE :
			operation = ServerOperationEnum.DELETE;
			break;
		case ActionConstants.FORCE_DELETE_INSTANCE :
			operation = ServerOperationEnum.FORCE_DELETE;
			break;
		case ActionConstants.REBOOT_INSTANCE :
			operation = ServerOperationEnum.REBOOT;
			break;
		case ActionConstants.RESET_INSTANCE :
			operation = ServerOperationEnum.AC_RESET;
			break;
		case ActionConstants.ISO_DETACH :
			operation = ServerOperationEnum.AC_ISO_DETACH;
			break;
		case ActionConstants.ISO_BOOT :
			operation = ServerOperationEnum.AC_ISO_BOOT;
			break;
		case ActionConstants.MIGRATE_INSTANCE :
			operation = ServerOperationEnum.AC_MIGRATE;
			break;
		case ActionConstants.ONLINE_MIGRATE_INSTANCE :
			operation = ServerOperationEnum.AC_ONLINE_MIGRATE;
			break;
		case ActionConstants.SUSPEND_INSTANCE :
			operation = ServerOperationEnum.OS_SUSPEND;
			break;
		case ActionConstants.RESUME_INSTANCE :
			operation = ServerOperationEnum.OS_RESUME;
			break;
		default :
			break;
		}
		if(null==operation) {
			String message = "Find operation action failed, the internal error.";
			logger.warn(message);
			return commonGenerator.internalError(message, Constants.INTERNAL_ERROR);
		}
		if(!InstanceChecker.checkOperation(user.getUserId()+"", paramsMap.get(Constants.INSTANCE_ID), operation)){
			String message = "The current status of the instance does not support this operation.";
			logger.warn(message);
			return commonGenerator.operationDeny(message, Constants.INSTANCE_ID);
		}
		logger.info("Operation permission is OK .");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkModifyInstanceAttribute(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Check modify permission before modify instance attribute");
		return checkModifyPermission(paramsMap, ActionConstants.MODIFY_INSTANCE_ATTRIBUTE);
	}

	@Override
	public Map<String, String> checkModifyInstanceResource(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Check modify permission before modify instance resource");
		return checkModifyPermission(paramsMap, ActionConstants.MODIFY_INSTANCE_RESOURCE);
	}

	@Override
	public Map<String, String> checkModifyInstanceSecurityGroup(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Check modify permission before modify instance security group");
		return checkModifyPermission(paramsMap, ActionConstants.MODIFY_INSTANCE_SECURITYGROUP);
	}

	@Override
	public Map<String, String> checkModifyInstanceChargeType(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Check modify permission before modify instance charge type");
		return checkModifyPermission(paramsMap, ActionConstants.MODIFY_INSTANCE_CHARGETYPE);
	}

	Map<String, String> checkModifyPermission(Map<String, String> paramsMap, String operationAction)
			throws Exception {
		//检查当前云主机的状态，根据云主机所处的状态，对云主机进行相应的操作
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		ServerOperationEnum operation = null;
		switch (operationAction) {
		case ActionConstants.MODIFY_INSTANCE_ATTRIBUTE :
			if( (null == paramsMap.get(Constants.PASSWORD)) && (null == paramsMap.get(Constants.HOST_NAME)))
				break;
			else
			{
				operation = ServerOperationEnum.AC_MODIFYPASSWORD;
				break;
			}
		case ActionConstants.MODIFY_INSTANCE_RESOURCE :
			operation = ServerOperationEnum.REBUILD;
			break;
		case ActionConstants.MODIFY_INSTANCE_SECURITYGROUP :
			operation = ServerOperationEnum.REBUILD;
			break;
		case ActionConstants.MODIFY_INSTANCE_CHARGETYPE :
			break;
		case ActionConstants.RENEW_INSTANCE :
			break;
		default :
			break;
		}
		if(!InstanceChecker.checkModify(user.getUserId()+"", paramsMap.get(Constants.INSTANCE_ID), operation)){
			String message = "The current status of the instance does not support this modify.";
			logger.warn(message);
			return commonGenerator.operationDeny(message, Constants.INSTANCE_ID);
		}
		logger.info("modify permission is OK .");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkMonitor(Map<String, String> paramsMap)
			throws Exception {
		logger.info("Check operation permission before describe instance monitor info");
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		VmInstance instance = InstanceChecker.checkReady(user.getUserId()+"", paramsMap.get(Constants.INSTANCE_ID));
		VmStatusEnum vmStatus = instance.getVmStatus();
		if (!vmStatus.equals(VmStatusEnum.ACTIVE)) {
			String message = "The current status of the instance does not support this operation.";
			return commonGenerator.operationDeny(message, Constants.INSTANCE_ID);
		}
		logger.info("describe monitor info permission is OK .");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkDescInstancesParams(Map<String, String> paramsMap) throws Exception {
		logger.info("describe instances info permission is OK .");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkRenewInstance(Map<String, String> paramsMap)
			throws Exception {
		//检查当前云主机的状态
		logger.info("Check operation permission before renew instance");
		return checkModifyPermission(paramsMap, ActionConstants.RENEW_INSTANCE);
	}
}
