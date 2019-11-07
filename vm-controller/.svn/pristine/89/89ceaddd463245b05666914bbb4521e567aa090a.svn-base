package appcloud.vmcontroller.impl;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.vmcontroller.util.VMCUtil;

public class ErrorHandler {
	private static Logger logger = Logger.getLogger(ErrorHandler.class);
	private final static int MAX_CPU = 8;
	private final static int MIN_CPU = 1;
	
	public static void checkParam(VmInstance instance,
								  VmInstanceType instanceType, List<VmInstanceMetadata> mds,
								  List<VmVolume> volumes, VmSecurityGroup securityGroup,
								  List<VmVirtualInterface> vifs) throws Exception {
		
		if (VMCUtil.isEmpty(instance) || VMCUtil.isEmpty(instance.getUuid()) || 
				VMCUtil.isEmpty(instance.getName()) || VMCUtil.isEmpty(instance.getRootDeviceLocation())
				|| VMCUtil.isEmpty(mds) || VMCUtil.isEmpty(volumes)
				|| VMCUtil.isEmpty(securityGroup) || VMCUtil.isEmpty(vifs)) {
			String why = "check Params error";
			logger.error(why + " instance = " + instance + " instance uuid = " + instance.getUuid()
					+" instance name = " + instance.getName() + " instance rootDeviceLocation = " + instance.getRootDeviceLocation());
			logger.error("mds = " + mds);
			logger.error("volumes = " + volumes);
			logger.error("securityGroup = " + securityGroup);
			logger.error("vifs = " + vifs);
			throw new Exception(why);
		}	
		if(VMCUtil.isEmpty(instanceType)||VMCUtil.isEmpty(instanceType.getMemoryMb())||
				VMCUtil.isEmpty(instanceType.getVcpus())||(instanceType.getVcpus() > MAX_CPU)
				|| instanceType.getVcpus() < MIN_CPU){
			String why = "check Params error";
			logger.error(why + " instanceType = " + instanceType + " instanceType cpus = " + instanceType.getVcpus() 
					+ " instanceType mem = " + instanceType.getMemoryMb());
			throw new Exception(why);
		}
	}
	
	
	public static void checkParam(String instanceUUID) throws Exception {
		if (VMCUtil.isEmpty(instanceUUID)) {
			String why = "check Params error";
			logger.error(why + instanceUUID);
			throw new Exception(why);
		}	
	}
	
	public static void checkParam(VmSecurityGroup securityGroup) throws Exception {
		if (VMCUtil.isEmpty(securityGroup)) {
			String why = "check Params error";
			logger.error(why + securityGroup);
			throw new Exception(why);
		}	
	}
	

}
