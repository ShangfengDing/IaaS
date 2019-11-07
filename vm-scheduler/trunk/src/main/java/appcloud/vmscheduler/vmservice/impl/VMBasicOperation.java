package appcloud.vmscheduler.vmservice.impl;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.MessageLog;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.service.VMControllerService;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.rpc.tools.RpcException;
import appcloud.rpc.tools.RpcTimeoutException;
import appcloud.vmschduler.utils.VSUtil;
import appcloud.vmscheduler.VMSchedulerServer;
import appcloud.vmscheduler.impl.VMSchedulerImpl;
import appcloud.vmscheduler.vmservice.AbstractVMService;
import appcloud.vmscheduler.vmservice.VMControllerAgent;


/**
 * @author liyuan
 * 实现基本操作
 */
public class VMBasicOperation extends AbstractVMService{
	private static Logger logger = Logger.getLogger(VMBasicOperation.class);
	private static LolLogUtil loller = null;

	
	private String methodName;
	private String anotherHostUUID;
	private RpcExtra rpcExtra;
	
	private VmStatusEnum statusResult;
	public VmStatusEnum getStatusResult() {
		return statusResult;
	}
	
	static {
		try {
			String ipAddress = VMSchedulerServer.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.VM_SCHDULER, VMSchedulerImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
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
	
	public VMBasicOperation(String instanceUUID, String op, String anotherHostUUID, RpcExtra rpcExtra) {
		this(instanceUUID, op, rpcExtra);
		this.anotherHostUUID = anotherHostUUID;
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
		} else if (methodName.equals("offlineMigrate")) {
			offlineMigrate();
		} else if (methodName.equals("onlineMigrate")) {
			onlineMigrate();
		} else {
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
		List<VmVirtualInterface> vmVirtualInterfaces = dbAgent.getVmVirtualInterfaceListByUuid(instanceUUID);
		VMControllerAgent.service.rebootVM(vmcService.getRoutingKey(instanceUUID), 
										   instanceUUID, vmVirtualInterfaces,  rpcExtra);
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

	public void offlineMigrate() throws Exception {
		logger.info("starting offline migrate");
		String toRoutingKey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.VM_CONTROLLER_PRE, anotherHostUUID);
		String fromRoutingKey = vmcService.getRoutingKey(instanceUUID);
		
		logger.info("toRoutingKey: "+toRoutingKey);
		logger.info("fromRoutingKey:"+fromRoutingKey);
		
		try{
			VmInstance instance = dbAgent.getVmInstanceWithAll(instanceUUID);
			
			//在新宿主机上，定义一个新的防火墙
			VmSecurityGroup securityGroup = dbAgent.getVmSecurityGroup(instance.getSecurityGroupId());
			List<VmSecurityGroupRule> SGRules = dbAgent.getSGRuleList(securityGroup);
			VMControllerAgent.service.defineNetworkFilter(toRoutingKey, securityGroup, SGRules, rpcExtra);
			
			//参数准备
			VmInstanceType instanceType = dbAgent.getVmInstanceTypeByID(instance.getInstanceTypeId());
			List<VmInstanceMetadata> mds = dbAgent.getMDListByVmInstanceId(instance.getId());
			List<VmVolume> volumes = dbAgent.getAttachedVolumeList(instanceUUID);
			List<VmVirtualInterface> vifs = instance.getVmVirtualInterfaces();
			
			//检测参数合法性
			checkParam(instance, instanceType, mds, volumes, securityGroup, vifs);
			//调用vm-controller离线迁移
			VMControllerAgent.service.offlineMigrate(toRoutingKey, instance, instanceType, mds, volumes, securityGroup, vifs, rpcExtra);
			//删除之前宿主机的虚拟机相关信息
			VMControllerAgent.service.deleteVM(fromRoutingKey, instanceUUID, rpcExtra);
			String message = "vm-scheduler offline migrate success!!!";
			logger.info(message);
			loller.info(LolLogUtil.OFFLINE_MIGRATION, message, rpcExtra);
		}catch(RpcException e){
			if(e instanceof RpcTimeoutException){
				logger.error("no response from vm-controller", e);
				loller.error(LolLogUtil.OFFLINE_MIGRATION, "offline migrate: " + "no response from vm-controller", rpcExtra);
				throw new RpcException("vm-scheduler receive no resopnse from vm-contrller");
			} else {
				String why = "vm-scheduler offline migrate error!";
				logger.error(why);
				throw new RpcException(why, e);
			}
		}
		
	}
	
	/**
	 * 在线迁移
	 * @throws Exception
	 */
	public void onlineMigrate() throws Exception {
		logger.info("starting online migrate");
		String toRoutingKey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.VM_CONTROLLER_PRE, anotherHostUUID);
		String fromRoutingKey = vmcService.getRoutingKey(instanceUUID);
		
		logger.info("toRoutingKey: "+toRoutingKey);
		logger.info("fromRoutingKey:"+fromRoutingKey);
		
		try{
			//TODO 参数合法性
			//调用vm-controller在线迁移
			String destURI1 = "qemu+tcp://" + dbAgent.getHostByUuid(anotherHostUUID).getIp() + "/system";
			String destURI2 = "qemu+ssh://" + dbAgent.getHostByUuid(anotherHostUUID).getIp() + "/system";
			
			long flags = 538;
			String dname = instance.getUuid() + "-" + instance.getName();
			logger.info("dname:" + dname +", ");
			logger.info("First,we try online migration by qemu+tcp");
			//first online migrate by qemu+tcp
			int result = VMControllerAgent.service.migrateVM(fromRoutingKey, instance.getUuid(), destURI1, dname, flags, rpcExtra);
			logger.info("Fonline migration by qemu+tcp result: " + result);
			//if it failed, we try by qemu+ssh
			if(result != 0) {
				logger.info("online migrate by qemu+tcp failed! try qemu+ssh");
				result = VMControllerAgent.service.migrateVM(fromRoutingKey, instance.getUuid(), destURI2, dname, flags, rpcExtra);
			}
			
			if (result == 0) {
				String message = "vm-scheduler online migrate success!!!";
				logger.info(message);
				loller.info(LolLogUtil.ONLINE_MIGRATION, message, rpcExtra);
				//先更新hostUuid,以便可以获取到vncPort
				//返回到R-S之后，会将UUID设置成旧的值，以便删除流表，设置带宽
				instance.setHostUuid(anotherHostUUID);
				dbAgent.updateVmInstance(instance);
				logger.info("temporarily update hostUuid:" + anotherHostUUID);
				//更新vncPort
				instance = dbAgent.getVmInstanceWithAll(instanceUUID);
				logger.info("Before update vncPort,instance.hostUUID:" + instance.getHostUuid());
				String vncPort = vmcService.getVNCInfo(instanceUUID, rpcExtra);
				instance.setVncPort(vncPort);
				dbAgent.updateVmInstance(instance);
				logger.info("After instance:" + instanceUUID +
						" onlineMigrate,update vncPort: " + vncPort);
			} else {
				String message = "vm-scheduler online migrate failed!!! result= " + result;
				logger.info(message);
				loller.info(LolLogUtil.ONLINE_MIGRATION, message, rpcExtra);
				throw new RpcException(message);
			}
		}catch(RpcException e){
			if(e instanceof RpcTimeoutException){
				logger.error("no response from vm-controller", e);
				loller.error(LolLogUtil.ONLINE_MIGRATION, "online migrate: " + "no response from vm-controller", rpcExtra);
				throw new RpcException("vm-scheduler receive no resopnse from vm-contrller");
			} else {
				String why = "vm-scheduler online migrate error!";
				logger.error(why);
				throw new RpcException(why, e);
			}
		}
	}
	
	@Override
	public Logger getLogger() {
		return logger;
	}

}
