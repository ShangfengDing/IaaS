package appcloud.vmscheduler.vmservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.rpc.tools.RpcException;
import appcloud.vmschduler.utils.VSUtil;
import appcloud.vmscheduler.vmservice.AbstractVMService;
import appcloud.vmscheduler.vmservice.VMControllerAgent;
import appcloud.common.exception.IllegalRpcArgumentException;


public class SecurityGroupService extends AbstractVMService {
	private static Logger logger = Logger.getLogger(SecurityGroupService.class);
	
	private String methodName;
	private VmSecurityGroup securityGroup;
	private List<VmSecurityGroupRule> rules;
	
	private List<String> defineResult = new ArrayList<String>();
	
	public List<String> getDefineResult() {
		return defineResult;
	}
	
	public SecurityGroupService(String instanceUUID, 
								VmSecurityGroup securityGroup,
								List<VmSecurityGroupRule> securityGroupRules,
								String name) {
		super(instanceUUID);
		
		this.methodName = name;
		this.securityGroup = securityGroup;
		this.rules = securityGroupRules;
	}

	@Override
	public void checkParam() throws IllegalRpcArgumentException {
		if (VSUtil.isEmpty(securityGroup)) {
			String why = "securityGroup is null" + securityGroup;
			paramErrorHandler(why);
		} else if (!VSUtil.isEmpty(instanceUUID)) {	//instanceUUID不为空：则调用attach，判断instance中的host uuid是否符合
			if (instance.getHostUuid() == null) {
				String why = "host uuid is null: " + instanceUUID;
				paramErrorHandler(why);
			}
		} else if (!VSUtil.isEmpty(rules)) {
			//null check 
		}
	}

	@Override
	public void process(RpcExtra rpcExtra) throws Exception {
		if (methodName.equals("defineSecurityGroup")) {
			defineSecurityGroup(rpcExtra);
		} else if (methodName.equals("attachSecurityGroup")) {
			attachSecurityGroup(rpcExtra);
		} else if (methodName.equals("defineSGToHost")) {
			defineSGToHost(rpcExtra);
		} else {
			throw new Exception("can not provide the method: " + methodName);
		}
	}
	
	private void defineSGToHost(RpcExtra rpcExtra) throws Exception  {
		VMControllerAgent.service.defineNetworkFilter(vmcService.getRoutingKey(instanceUUID), 
													  securityGroup, 
													  rules, rpcExtra);
    	logger.info("define SG successful: " + instanceUUID);
    	logger.info("securityGroup: " + securityGroup);
	}
	
	private void defineSecurityGroup(RpcExtra rpcExtra) throws Exception  {
		//instance list use the security group
		List<VmInstance> instanceList = dbAgent.getVmInstanceListForSG(securityGroup);
		logger.info("instance list apply SG: " + securityGroup);
		logger.info("instance list: " + instanceList);
		
		//尚未有虚拟机使用该规则
		if (instanceList == null || instanceList.size() == 0) {
    		logger.info("no instance use the security group");
    		return;
    	} else {	//获取instanceList的所有host的UUID List
    		List<String> hostUUIDList = dbAgent.getHostUUIDList(instanceList);
    		logger.info("find all host use security group");
    		logger.info("host uuid list: " + hostUUIDList);
    		
    		//apply security group to all the hosts
        	for (String hostUUID : hostUUIDList) {
            	try {
            		VMControllerAgent.service.defineNetworkFilter(
            				RoutingKeyGenerator.getRoutingKey(RoutingKeys.VM_CONTROLLER_PRE, hostUUID), 
            				securityGroup, 
            				rules,rpcExtra);
	            	logger.info("define SG successful: " + hostUUID);
	            	logger.info("securityGroup: " + securityGroup);
            	} catch (RpcException e) {
        			logger.error("define security group error: " + e.getMessage());
        			logger.error("host: " + hostUUID);
        			logger.info("securityGroup: " + securityGroup);
        			//add to failed result
        			defineResult.add(hostUUID);
				}
        	}
    	}
	}
	
	private void attachSecurityGroup(RpcExtra rpcExtra) throws Exception  {
		//set security group attachment
		instance.setSecurityGroupId(securityGroup.getId());
		dbAgent.updateVmInstance(instance);
		logger.info("update vm instance: " + instance);
		logger.info("instance SG: " + instance.getSecurityGroupId());
		
		//apply security group to vm's host
		this.rules = dbAgent.getSGRuleList(securityGroup); 
		defineSGToHost(rpcExtra);
		logger.info("defineSGToHost successful: " + securityGroup);
		logger.info("rules: " + rules);
		
		//restart vm: apply security group
		AbstractVMService startService = new VMBasicOperation(instanceUUID, "startVM", rpcExtra);
		startService.VMOperationProcess(rpcExtra);
		logger.info("restart vm successful: " + instanceUUID);
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}
