package appcloud.vmscheduler.vmservice.impl;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.MessageLog;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.util.LolLogUtil;
import appcloud.rpc.tools.RpcException;
import appcloud.rpc.tools.RpcTimeoutException;
import appcloud.vmschduler.utils.VSUtil;
import appcloud.vmscheduler.VMSchedulerServer;
import appcloud.vmscheduler.disk.VolumeAttachManager;
import appcloud.vmscheduler.impl.VMSchedulerImpl;
import appcloud.vmscheduler.vmservice.AbstractVMService;
import appcloud.vmscheduler.vmservice.VMControllerAgent;


public class CreateVMService extends AbstractVMService{
	private static Logger logger = Logger.getLogger(CreateVMService.class);
	private static LolLogUtil loller = null;

	private String instanceTypeID; 
	private List<VmInstanceMetadata> instanceMetadata;
	private List<VmVolume> volumes;
	private VmSecurityGroup securityGroup;	
	private List<VmVirtualInterface> vifs;
	
	static {
		try {
			String ipAddress = VMSchedulerServer.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.VM_SCHDULER, VMSchedulerImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 处理函数参数
	 * @param UUIDPara
	 * @param instanceTypeIDPara
	 * @param instanceMetadatasPara
	 * @param volumesPara
	 * @param securityGroupPara
	 * @param vifsPara
	 */
	public CreateVMService(String instanceUUID, 
						   String instanceType, 
						   List<VmInstanceMetadata> instanceMetadatas,
						   List<VmVolume> volumes, 
						   VmSecurityGroup securityGroup,	
						   List<VmVirtualInterface> vifs) {
		super(instanceUUID);
		
		this.instanceTypeID = instanceType;
		this.instanceMetadata = instanceMetadatas;
		this.volumes = volumes;
		this.securityGroup = securityGroup;
		this.vifs = vifs;
		
		logger.info("instanceMetadata" + instanceMetadata);
		logger.info("securityGroup" + securityGroup);
	}
	
	@Override
	public void checkParam() throws IllegalRpcArgumentException {
		logger.info("create VM: begin check param");
		
		if (VSUtil.isEmpty(instanceUUID) || VSUtil.isEmpty(instanceTypeID)
			|| VSUtil.isEmpty(volumes) || VSUtil.isEmpty(vifs)) {
			String why = "check Params error";
			paramErrorHandler(why);
		} else if (VSUtil.isEmpty(instance)) {
			String why = "instance is null: " + instance;
			paramErrorHandler(why);
		} else if (instance.getHostUuid() == null) {
			String why = "host uuid is null: " + instanceUUID;
			paramErrorHandler(why);
		} else {
			for (VmVolume vo : volumes) {
				if (vo.getProviderLocation() == null) {
					String why = "volume provider location is null: " + vo;
					paramErrorHandler(why);
				}
			}	
		}
		logger.info("check param OK!");
	}

	@Override
	public void process(RpcExtra rpcExtra) throws Exception {
		logger.info("create VM: begin process");
		
		List<VmVolume> newVolumes = VolumeAttachManager.attachVolumes(instance, volumes);
		logger.debug("update new volume：" + newVolumes);	
		
		instance = dbAgent.getVmInstance(instanceUUID);
		String routingKey = vmcService.getRoutingKey(instance);
		
		/*
		 * define security group: vm scheduler impl 的接口
		 * 每次create 都先调用一次define接口
		 * TODO:在vm controller上增加接口，existNetworkFilter，或者增加数据表，知悉host和security group的对应关系
		 */
		logger.info("begin define network filter: " + securityGroup);	
		List<VmSecurityGroupRule> SGRules = dbAgent.getSGRuleList(securityGroup);
		try{
			VMControllerAgent.service.defineNetworkFilter(routingKey, securityGroup, SGRules, rpcExtra);
		}catch(RpcException e){
			if(e instanceof RpcTimeoutException){
				logger.error("no response from vm-controller", e);
				loller.error(LolLogUtil.DEFINE_NETWORK_FILTER, "define network filter: " + "no response from vm-controller", rpcExtra);
				throw new RpcException("vm-scheduler receive no resopnse from vm-contrller");
			}
			else{
			String why = "define NwFilter error!";
			logger.error(why);
			throw new RpcException(why, e);
			}
		}
		
		/*
		 * instanceType, 网卡vif
		 * 暂时都采用resource scheduler传入的参数，不加判断 
		 */
		logger.info("begin create VM: " + instanceUUID);
		logger.info("VM config --- ");
		logger.info("begin create VM: " + instance);
		logger.info("instanceType: " + dbAgent.getVmInstanceType(instanceTypeID));
		logger.info("instanceMetadata: " + instanceMetadata);
		logger.info("volumes: " + newVolumes);
		logger.info("securityGroup: " + securityGroup);
		logger.info("vifs: " + vifs);
		try{
			VMControllerAgent.service.createVM(routingKey, 
										   instance, 
										   dbAgent.getVmInstanceType(instanceTypeID), 
										   instanceMetadata, 
										   newVolumes, 
										   securityGroup, 
										   vifs, rpcExtra);
			logger.info("create VM successful: " + instanceUUID);
			
		}catch(RpcException e){
			if(e instanceof RpcTimeoutException){
				logger.error("no response from vm-controller", e);
				loller.error(LolLogUtil.CREATE_VM, "create VM: " + "no response from vm-controller", rpcExtra);
				throw new RpcException("vm-scheduler receive no resopnse from vm-contrller");
			}
			else{
			String why = "create error: " + instance.getUuid();
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
