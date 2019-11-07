package appcloud.vmscheduler.vmservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.vmschduler.utils.VSUtil;
import appcloud.vmscheduler.disk.VolumeAttachManager;
import appcloud.vmscheduler.vmservice.AbstractVMService;


public class VolumeService extends AbstractVMService {
	private static Logger logger = Logger.getLogger(VolumeService.class);
	
	private String methodName;
	private VmVolume volume;
	
	public VolumeService(String instanceUUID, VmVolume volume, String name) {
		super(instanceUUID);
		
		this.methodName = name;
		this.volume = volume;
	}

	@Override
	public void checkParam() throws IllegalRpcArgumentException {
		if (VSUtil.isEmpty(instanceUUID) || VSUtil.isEmpty(methodName) || VSUtil.isEmpty(volume)) {
			String why = "params is null" + instanceUUID + methodName + volume;
			paramErrorHandler(why);
		} else if (volume.getProviderLocation() == null) {
			String why = "provider location is null" + volume;
			paramErrorHandler(why);
		} else if (instance.getHostUuid() == null) {
			String why = "host uuid is null: " + instanceUUID;
			paramErrorHandler(why);
		}
		
		if (methodName.equals("attachVolume") && (!volume.getStatus().equals(VmVolumeStatusEnum.AVAILABLE))) {
			String why = "attachVolume, volume status is not available: " + volume;
			paramErrorHandler(why);
		}
	}

	@Override
	public void process(RpcExtra rpcExtra) throws Exception {
		if (methodName.equals("attachVolume")) {
			attach(rpcExtra);
		} else if (methodName.equals("detachVolume")) {
			detach(rpcExtra);
		}
		else {
			throw new Exception("can not provide the method: " + methodName);
		}
		
	}
	
	public void attach(RpcExtra rpcExtra) throws Exception {
		//STOP?
		if (!instance.getVmStatus().equals(VmStatusEnum.STOPPED)) {
			AbstractVMService service = new VMBasicOperation(instanceUUID, "stopVM", null);
			service.VMOperationProcess(rpcExtra);	
		}
		
		//generate volumes, add the new attached volume
		List<VmVolume> volumes = new ArrayList<VmVolume>();
		volumes.add(volume);
		
		//attach volume
		volumes = VolumeAttachManager.attachVolumes(instance, volumes);
		//instance = dbAgent.getVmInstance(instanceUUID);
		
		logger.info("attached volume: " + volumes);
	}
	
	public void detach(RpcExtra rpcExtra) throws Exception {
		//continue check
		if (!instanceUUID.equals(volume.getInstanceUuid())) {
			String why = "volume's instance uuid is not equal: " + instanceUUID + volume;
			paramErrorHandler(why);
		}

		//STOP?
		if (!instance.getVmStatus().equals(VmStatusEnum.STOPPED)) {
			AbstractVMService service = new VMBasicOperation(instanceUUID, "stopVM", null);
			service.VMOperationProcess(rpcExtra);	
		}

		//detach volume: update volume attachment info
		List<VmVolume> attachedVolumes = VolumeAttachManager.detachVolume(instance, volume);
		instance = dbAgent.getVmInstance(instanceUUID);
		
		//VMC上的配置文件还没有修改，下次启动时候，才能生效
		logger.info("detach volume successful: " + volume);
		logger.info("attached volume: " + attachedVolumes);
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}
