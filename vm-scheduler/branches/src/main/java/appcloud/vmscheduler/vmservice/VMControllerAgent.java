package appcloud.vmscheduler.vmservice;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.service.VMControllerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.vmscheduler.impl.DBAgent;
import appcloud.vmscheduler.impl.VMSchedulerImpl;

/**
 * @author liyuan
 * 提供VM Controller操作接口
 * 封装基本的原子操作
 */
public class VMControllerAgent {
	private static Logger logger = Logger.getLogger(VMSchedulerImpl.class);
	private DBAgent dbAgent = DBAgent.getInstance();
	
	public static VMControllerService service = (VMControllerService) ConnectionFactory.getAMQPService(
		   VMControllerService.class, RoutingKeys.VM_CONTROLLER_PRE);
	
	
	public String getRoutingKey(VmInstance instance){
		String hostUUID = instance.getHostUuid();
		return RoutingKeyGenerator.getRoutingKey(RoutingKeys.VM_CONTROLLER_PRE, hostUUID);
	}
	
	public String getRoutingKey(String instanceUUID){
		VmInstance instance = dbAgent.getVmInstance(instanceUUID);
		String hostUUID = instance.getHostUuid();
		return RoutingKeyGenerator.getRoutingKey(RoutingKeys.VM_CONTROLLER_PRE, hostUUID);
	}

	/**
	 * 调用VM controller，获取vnc port
	 * @param instanceUUID
	 * @throws Exception 
	 */
	public String getVNCInfo(String instanceUUID, RpcExtra rpcExtra) throws Exception {
		//获取虚拟机VNC port
		String vncPort = null;
		VmInstance newInstance = dbAgent.getVmInstance(instanceUUID);
		if (! newInstance.getVmStatus().equals(VmStatusEnum.DELETED)) {
			String routingKey = getRoutingKey(instanceUUID);
			vncPort = service.getVncPort(routingKey, instanceUUID, rpcExtra);
			logger.info("get VM VNC port: " + vncPort);	
		} else {
			logger.info("vm has been deleted");
		}
		return vncPort;
	}
	
	/**
	 * 调用VM controller，获取vm status
	 * @param instanceUUID
	 * @throws Exception 
	 */
	public VmStatusEnum getVMStatus(String instanceUUID, RpcExtra rpcExtra) throws Exception {
		//获取虚拟机状态
		String routingKey = getRoutingKey(instanceUUID);
		VmStatusEnum status = service.getDomainState(routingKey, instanceUUID, rpcExtra);
		logger.info("get vm status: " + status.toString());
		return status;
	}
	
	
}
