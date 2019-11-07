package appcloud.vmscheduler.vmservice;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.rpc.tools.RpcException;
import appcloud.rpc.tools.RpcTimeoutException;
import appcloud.vmschduler.utils.VSUtil;
import appcloud.vmscheduler.impl.DBAgent;
import appcloud.common.exception.IllegalRpcArgumentException;


/**
 * @author liyuan
 * 抽象的虚拟机操作基类
 */
public abstract class AbstractVMService implements SchedulerService{
	
	/*
	 * 基类维护基本参数，不维护具体业务相关的参数
	 */
	private static Logger logger = Logger.getLogger(AbstractVMService.class);
	public DBAgent dbAgent = DBAgent.getInstance();
	public VMControllerAgent vmcService = new VMControllerAgent();
	
	public String instanceUUID;
	public VmInstance instance;
	
	public AbstractVMService(String instanceUUID) {
		this.instanceUUID = instanceUUID;
		this.instance = dbAgent.getVmInstance(instanceUUID);
	}
	
	
	@Override
	public void VMOperationProcess(RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException {
		try {
			//check parameters, throws IllegalRpcArgumentException
			logger.debug("begin check param");
			checkParam();
			
			/*
			 * process operation
			 * throws Exception
			 */
			logger.debug("begin process");
			process(rpcExtra);
			
			//process success result			
			logger.debug("process success");
			processSuccess(rpcExtra);
			
		
		} catch (Exception e) {
			if(e instanceof IllegalRpcArgumentException) {
				logger.debug("check param error", e);
				throw new RpcException(e.getMessage());
			} else if (e instanceof RpcTimeoutException){
				logger.debug("no response from vm-controller"+instance.getHost().getIp(), e);
				throw new RpcException("vm-scheduler receive no resopnse from vm-contrller");
			} else {
			//process error result, throws Exception
			logger.error("process error", e);
			processError(rpcExtra);
			throw new RpcException(e.getMessage());
			}
		}
	}
	
	public abstract void checkParam() throws IllegalRpcArgumentException;
	
	public abstract void process(RpcExtra rpcExtra) throws Exception;
	
	
	/**
	 * 基类提供的结果处理方法，需必要的instanceUUID参数
	 * @param instanceUUID
	 * @return
	 */
	@Override
	public void processSuccess(RpcExtra rpcExtra) {
		/*
		 * 处理成功：
		 * 1.status：active
		 * 2.获取并设置虚拟机的vnc port
		 */
		if (instanceUUID != null) {
			setVMStatus(rpcExtra);
			setVNCInfo(rpcExtra);	
		}
	}
	
	/**
	 * @param instanceUUID
	 * @return
	 */
	@Override
	public void processError(RpcExtra rpcExtra) {
		/*
		 * 处理失败：
		 * 1.set status：
		 * 2。set vnc info
		 */
		if (instanceUUID != null) {
			if (instance.getHostUuid() != null) {
				setVMStatus(rpcExtra);
				setVNCInfo(rpcExtra);	
			} else {
				dbAgent.setVMStatus(instance, VmStatusEnum.ERROR);
			}	
		}
	}
	
	/**
	 * 调用VM controller，获取vnc port，持久化进数据库
	 * @param instanceUUID
	 */
	private void setVNCInfo(RpcExtra rpcExtra) {
		try {
			//deleted vm
			if (instance.getVmStatus().equals(VmStatusEnum.DELETED)) {
				instance.setVncPort(null);
				dbAgent.updateVmInstance(instance);
				logger.info("vm deleted, set VM VNC port: null");
			} else if (instance.getVmStatus().equals(VmStatusEnum.ACTIVE)){
				String vncPort = vmcService.getVNCInfo(instanceUUID, rpcExtra);
				//更新数据库
				instance.setVncPort(vncPort);
				dbAgent.updateVmInstance(instance);
				logger.info("set VM VNC port: " + vncPort);
			}
		} catch (Exception e) {
			logger.error("set VM VNC port error", e);
		}
	}
	
	/**
	 * 调用VM controller，获取vnc port，持久化进数据库
	 * @param instanceUUID
	 */
	private void setVMStatus(RpcExtra rpcExtra) {
		try {
			if (!instance.getVmStatus().equals(VmStatusEnum.DELETED)) {
				VmStatusEnum status = vmcService.getVMStatus(instanceUUID, rpcExtra);
				//设置数据库状态			
				dbAgent.setVMStatus(instance, status);	
				logger.debug("[setVMStatus]: " + instance);
			}
			
		} catch (Exception e) {
			String why = "[setVMStatus] process error: " + instanceUUID;
			logger.error(why, e);
		}
	}
    
    public void paramErrorHandler(String message) throws IllegalRpcArgumentException {
    	Logger logger = getLogger();
    	logger.error(message);
    	throw new IllegalRpcArgumentException(message);
    }
    
    public abstract Logger getLogger();
    
	public void checkParam(VmInstance instance,
		    			   VmInstanceType instanceType, List<VmInstanceMetadata> mds,
		    			   List<VmVolume> volumes, VmSecurityGroup securityGroup,
		    			   List<VmVirtualInterface> vifs) throws Exception {
		logger.info("check vm config: ");

		//参数是否为空
		if (VSUtil.isEmpty(instance) || VSUtil.isEmpty(instanceType)
			|| VSUtil.isEmpty(volumes) || VSUtil.isEmpty(vifs)) {
			String why = "check Params error, instance or type or volumes or vifs is null";
			paramErrorHandler(why);
		} else if (instance.getHostUuid() == null) {	//instance的host是否为空
			String why = "host uuid is null: " + instance;
			paramErrorHandler(why);
		} else {	//volume是否合法
			for (VmVolume vo : volumes) {
				if (vo.getProviderLocation() == null) {
					String why = "volume provider location is null: " + vo;
					paramErrorHandler(why);
				}
			}	
		} 
		
		logger.info("check vm config OK!");
	}

}
