package appcloud.nodeagent.impl;

import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.common.errorcode.NAEC;
import appcloud.common.model.Instance;
import appcloud.common.model.Instance.InstanceTypeEnum;
import appcloud.common.service.NodeAgentService;
import appcloud.common.util.ConnectionFactory;
import appcloud.nodeagent.adaptor.JavaGCAdaptor;
import appcloud.nodeagent.adaptor.VmGCAdaptor;
import appcloud.nodeagent.info.NodeAgent;

/**
 * @author jianglei
 *
 */
public class NodeAgentImpl implements NodeAgentService {
	
	private static Logger logger = Logger.getLogger(NodeAgentImpl.class);
	private NodeAgent agent = NodeAgent.getInstance();
	private JavaGCAdaptor javaGC = new JavaGCAdaptor();
	private VmGCAdaptor vmGC = new VmGCAdaptor();

	public Instance StartJ2EEInstance(String routingKey, String appId,
			String warLocation, int xms, int xmx) {	
		
		logger.info("receiving a START instance request with appid: " + appId
				+ ", warlocation: " + warLocation + ", xms: " + xms + "xmx"
				+ xmx);
		
		Instance instance = null;
		
		// step 0. 验证是否有此能力
		if (!agent.containsJ2ee()) {
			logger.warn("此Host不包含对于JSP容器调度的能力");
			return instance;
		}
		
		// step 1. 产生uuid并且启动instance
		String uuid = UUID.randomUUID().toString().replace("-", "");
		instance = javaGC.startJ2EEInstance(appId, uuid, warLocation, xms, xmx);
		
		if (instance != null) {
			if (saveInstanceInfo(instance).equals(NAEC.NO_ERROR)) {
				logger.info("SUCCESS in deploying instance" + instance);
			} else {
				// failed saving
				instance = null;
			}
		} else {
			logger.warn("FAIL to handle start app request, appId:" + appId);
		}
		
		return instance;
	}

	public NAEC StopJ2EEInstance(String routingKey, String appId, String uuid, int waitingTime) {
		logger.info("receiving a DELETE instance request with appid: " + appId
				+ ", uuid: " + uuid + ", watingTime: " + waitingTime);
		NAEC result = NAEC.NO_ERROR;
		
		// step 0. 验证是否有此能力
		if (!agent.containsJ2ee()) {
			logger.warn("此Host不包含对于JSP容器调度的能力");
			result = NAEC.OVER_CAP;
		}
		
		// step 1. 删除实例		
		if(result.equals(NAEC.NO_ERROR) && javaGC.deleteJ2EEInstance(appId, uuid, waitingTime) == 0) {
			logger.info("SUCCESS in handling delete request for instance: " + uuid
					+ ", appUUID: " + appId);
		} else {
			logger.warn("FAIL to handle delete request, instance uuid" + uuid);
			result = NAEC.GC_ERROR;
		}
		
		// step 2. 删除instance信息
		if (result.equals(NAEC.NO_ERROR)) {
			result = deleteInstanceInfo(uuid);
		}
		
		return result;
	}

	public Instance createVM(String routingKey, String uuid, Integer templateId, String mac, String ip) {
		logger.info("receiving a START vm instance request with uuid: " + uuid
				+ ", templatedId: " + templateId + ", mac: " + mac + ",ip: " + ip);
		
		Instance instance = null;
		
		// step 0. 验证是否有此能力
		if (!agent.containsVm()) {
			logger.warn("此Host不包含对于VM容器调度的能力");
			return instance;
		}
		
		// step 1. 产生uuid并且启动instance
		instance = vmGC.createVMInstance(uuid, templateId, mac, ip);
		if (instance != null) {
			logger.info("SUCCESS in deploying instance" + instance);
			logger.info("WAIT!!!!, let me save it, let hope it's saved");
			if (saveInstanceInfo(instance).equals(NAEC.NO_ERROR)) {
				logger.info("really SUCCESS in deploying instance" + instance);
				return instance;
			} else {
				this.deleteVM(null, uuid);
				return null;
			}
		} else {
			logger.warn("FAIL to handle start vm request, vm:" + uuid);
			return null;
		}
	}

	public NAEC deleteVM(String routingKey, String uuid) {
		logger.info("receiving a DELETE VM instance request with uuid: " + uuid);
		
		// step 0. 验证是否有此能力
		if (!agent.containsVm()) {
			logger.warn("此Host不包含对于JSP容器调度的能力");
			return NAEC.OVER_CAP;
		}
		
		// step 1. 删除实例		
		if(vmGC.deleteVmInstance(uuid) == 0) {
			logger.info("SUCCESS in handling delete request for instance: " + uuid);
		} else {
			logger.warn("FAIL to handle delete request, instance uuid" + uuid);
			return NAEC.GC_ERROR;
		}
		
		// step 2. 删除instance信息
		return deleteInstanceInfo(uuid);
	}

	public NAEC dealVM(String routingKey, String uuid, String action, Integer templateId) {
		logger.info("receiving a DEAL VM instance request with uuid: " + uuid
				+ ", action:" + action + ", templateId:" + templateId);
		
		// step 0. 验证是否有此能力
		if (!agent.containsVm()) {
			logger.warn("此Host不包含对于JSP容器调度的能力");
			return NAEC.OVER_CAP;
		}
		
		// step 1. 转发请求		
		if(vmGC.dealVmInstance(uuid, action, templateId) == 0) {
			logger.info("SUCCESS in handling deal request for instance: " + uuid + ", action" + action);
		} else {
			logger.warn("FAIL to handle deal request, instance uuid:" + uuid);
			return NAEC.GC_ERROR;
		}
		
		return NAEC.NO_ERROR;
	}
	
	/**
	 * using double check to save instance info
	 * if failed, the only way is to roll back.
	 * 
	 * @param instance
	 * @return
	 */
	private NAEC saveInstanceInfo(Instance instance) {
		try {
			ConnectionFactory.getInstanceProxy().save(instance);
		} catch (Exception e) {
			// 一般情况下，不会走到下面这一步，下面是存储失败的回滚代码
			logger.info("FAILED #1 saving instance info, have to delete it");
			try {
				ConnectionFactory.getInstanceProxy().save(instance);
			} catch (Exception e2) {
				logger.info("FAILED #2 saving instance info, have to delete it");
				if (instance.getType().equals(InstanceTypeEnum.J2EE)
						&& this.StopJ2EEInstance(null, instance.getAppUuid(),
								instance.getUuid(), 1000).equals(NAEC.NO_ERROR)) {
					logger.info("instance deleted, egg hurts");
					return NAEC.DB_ERROR;
				} else if (instance.getType().equals(InstanceTypeEnum.VM)
						&& this.deleteVM(null, instance.getUuid()).equals(
								NAEC.NO_ERROR)) {
					logger.info("instance deleted, egg hurts");
					return NAEC.DB_ERROR;
				} else {
					logger.error("instance not deleted, MUST be deleted by admin, uuid:"
							+ instance.getUuid());
					return NAEC.GC_ERROR;
				}
			}
		}
		
		return NAEC.NO_ERROR;
	}
	
	private NAEC deleteInstanceInfo(String uuid) {
		try {
			ConnectionFactory.getInstanceProxy().deleteByUuid(uuid);
		} catch (Exception e) {
			logger.warn("info not deleted, instance uuid: " + uuid);
			e.printStackTrace();
			return NAEC.INFO_NOT_DEL;
		}
		
		return NAEC.NO_ERROR;
	}

}
