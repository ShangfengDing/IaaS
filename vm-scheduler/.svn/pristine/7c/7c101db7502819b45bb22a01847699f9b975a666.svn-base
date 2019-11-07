package appcloud.vmscheduler.vmservice.impl;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.vmschduler.utils.VSUtil;
import appcloud.vmscheduler.vmservice.AbstractVMService;
import appcloud.vmscheduler.vmservice.VMControllerAgent;


/**
 * @author liyuan
 * 实现基本操作
 */
public class VMBasicOperation extends AbstractVMService{
	private static Logger logger = Logger.getLogger(VMBasicOperation.class);
	
	private String methodName;
	
	private RpcExtra rpcExtra;
	
	private VmStatusEnum statusResult;
	public VmStatusEnum getStatusResult() {
		return statusResult;
	}
	
	/**
	 * @param UUID
	 * @param op: startVM, deleteVM, stopVM, forceStopVM, resumeVM, rebootVM, suspendVM, resizeVM, getVMState
	 */
	public VMBasicOperation(String instanceUUID, String op, RpcExtra rpcExtra) {
		super(instanceUUID);
		
		methodName = op;
		this.rpcExtra = rpcExtra;
	}
	
	public void resetOP(String op) {
		methodName = op;
	}

	@Override
	public void checkParam() throws IllegalRpcArgumentException {
		if (VSUtil.isEmpty(instanceUUID) || VSUtil.isEmpty(methodName)) {
			String why = "params is null" + instanceUUID + methodName;
			paramErrorHandler(why);
		} else if (instance.getHostUuid() == null) {
			String why = "host uuid is null: " + instanceUUID;
			paramErrorHandler(why);
		}
		logger.info("check param OK!");
	}
	
	@Override
	public void process(RpcExtra rpcExtra) throws Exception {
		if (methodName.equals("startVM")) {
			start();
		} else if (methodName.equals("deleteVM")) {
			delete();
		} else if (methodName.equals("stopVM")) {
			stop();
		} else if (methodName.equals("forceStopVM")) {
			forceStop();
		} else if (methodName.equals("resumeVM")) {
			resume();
		} else if (methodName.equals("rebootVM")) {
			reboot();
		} else if (methodName.equals("suspendVM")) {
			suspend();
		} else if (methodName.equals("resizeVM")) {
			resize();
		} else if (methodName.equals("getVMState")) {
			getState();
		} 
		else {
			throw new Exception("can not provide the method: " + methodName);
		}
	}
	
	public void start() throws Exception {
		//参数准备
		VmInstance newInstance = dbAgent.getVmInstanceWithAll(instanceUUID);
		VmInstanceType instanceType = dbAgent.getVmInstanceTypeByID(newInstance.getInstanceTypeId());
		List<VmInstanceMetadata> mds = dbAgent.getMDListByVmInstanceId(newInstance.getId());
		List<VmVolume> volumes = dbAgent.getAttachedVolumeList(instanceUUID);
		VmSecurityGroup securityGroup =  dbAgent.getVmSecurityGroup(newInstance.getSecurityGroupId());
		List<VmVirtualInterface> vifs = newInstance.getVmVirtualInterfaces();
		
		logger.info("start VM: " + newInstance);
		logger.info("instanceType: " + instanceType);
		logger.info("instanceMetadata: " + mds);
		logger.info("volumes: " + volumes);
		logger.info("securityGroup: " + securityGroup);
		logger.info("vifs: " + vifs);
		
		//参数检查
		checkParam(newInstance, instanceType, mds, volumes, securityGroup, vifs);
		
		//start
		VMControllerAgent.service.startVM(vmcService.getRoutingKey(newInstance), 
					    				  newInstance,		//instance 
					    				  instanceType,   //instance type 
					    				  mds, 	//instance metadata list
					    				  volumes,    //attached volume list 
					    				  securityGroup,	//security group
					    				  vifs, rpcExtra);	//vif list
		logger.info("start vm successful: " + newInstance);
	}
	
	public void delete() throws Exception {
		logger.info("get routing key: " + vmcService.getRoutingKey(instanceUUID));
		VMControllerAgent.service.deleteVM(vmcService.getRoutingKey(instanceUUID), 
										   instanceUUID, rpcExtra);
		logger.info("delete VM successful: " + instance);
		dbAgent.setVMStatus(instance, VmStatusEnum.DELETED);
	}
	
	public void reboot() throws Exception {
		logger.info("get routing key: " + vmcService.getRoutingKey(instanceUUID));
		VMControllerAgent.service.rebootVM(vmcService.getRoutingKey(instanceUUID), 
										   instanceUUID, rpcExtra);
		logger.info("reboot VM successful: " + instance);
	}
	
	public void stop() throws Exception {
		logger.info("get routing key: " + vmcService.getRoutingKey(instanceUUID));
		VMControllerAgent.service.stopVM(vmcService.getRoutingKey(instanceUUID), 
										 instanceUUID, rpcExtra);
		logger.info("stop VM successful: " + instance);	
	}
	
	public void forceStop() throws Exception {
		logger.info("get routing key: " + vmcService.getRoutingKey(instanceUUID));
		VMControllerAgent.service.forceStopVM(vmcService.getRoutingKey(instanceUUID), 
				 							  instanceUUID, rpcExtra);
		logger.info("forceStop VM successful: " + instance);
	}
	
	public void resume() throws Exception {
		logger.info("get routing key: " + vmcService.getRoutingKey(instanceUUID));
		VMControllerAgent.service.resumeVM(vmcService.getRoutingKey(instanceUUID), 
				  						   instanceUUID, rpcExtra);
		logger.info("resume VM successful: " + instance);
	}
	
	public void suspend() throws Exception {
		logger.info("get routing key: " + vmcService.getRoutingKey(instanceUUID));
		VMControllerAgent.service.suspendVM(vmcService.getRoutingKey(instanceUUID), 
				   							instanceUUID, rpcExtra);
		logger.info("suspend VM successful: " + instance);
	}
	
	public void resize() throws Exception {
		VmInstance instance = dbAgent.getVmInstanceWithAll(instanceUUID);
		List<VmInstanceMetadata> instanceMetadata = dbAgent.getMDListByVmInstanceId(instance.getId());
		VmInstanceType instanceType = dbAgent.getVmInstanceTypeByID(instance.getInstanceTypeId());
		List<VmVirtualInterface> vifs = dbAgent.getVmVirtualInterfaceListByUuid(instance.getUuid());
		VmSecurityGroup securityGroup = dbAgent.getVmSecurityGroup(instance.getSecurityGroupId());
		
		VmInstanceMetadata md = null;
		for(VmInstanceMetadata  instanceMd : instanceMetadata){
			if (instanceMd.getKey().equals("maxBandwidth")) {
				md = instanceMd;
				break;
			}
		}
		
		logger.info("get routing key: " + vmcService.getRoutingKey(instanceUUID));
		logger.info("new instance config --- ");
		logger.info("instanceMetadatas: " + instanceMetadata);
		logger.info("instanceMetadata: " + md);
		logger.info("instanceType: " + instanceType);
		logger.info("vifs: " + vifs); 
		
		VMControllerAgent.service.resizeVM(vmcService.getRoutingKey(instanceUUID), 
										   instanceUUID, 
										   instanceType, 
										   md, vifs, securityGroup, rpcExtra);
		
		logger.info("resize VM successful: " + instance);
	}
	
	public void getState() throws Exception {
		statusResult = instance.getVmStatus();
		
		if (!statusResult.equals(VmStatusEnum.DELETED) &&
			!statusResult.equals(VmStatusEnum.BUILDING) && 
			!statusResult.equals(VmStatusEnum.ERROR)) {
			//get vm status, call vm controller
			statusResult = vmcService.getVMStatus(instanceUUID, rpcExtra);
			
			//reset vm instance db
			instance.setVmStatus(statusResult);
			dbAgent.updateVmInstance(instance);
		}
		
		logger.info("vm status: " + statusResult);
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}
