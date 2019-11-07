package appcloud.resourcescheduler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.common.errorcode.AREC;
import appcloud.common.errorcode.ErrorCode;
import appcloud.common.errorcode.NAEC;
import appcloud.common.errorcode.RSEC;
import appcloud.common.model.Host;
import appcloud.common.model.Instance;
import appcloud.common.model.J2EEApp;
import appcloud.common.model.ResrcStrategy;
import appcloud.common.model.RoutingEntry;
import appcloud.common.model.Instance.InstanceTypeEnum;
import appcloud.common.model.RoutingEntry.RETypeEnum;
import appcloud.common.model.VMApp;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.InstanceProxy;
import appcloud.common.proxy.J2EEAppProxy;
import appcloud.common.proxy.ResrcStrategyProxy;
import appcloud.common.proxy.VMAppProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.service.AppRouterService;
import appcloud.common.service.NodeAgentService;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.common.util.WarLocationTool;
import appcloud.resourcescheduler.model.Resource;
import appcloud.resourcescheduler.service.AggregateService;
import appcloud.resourcescheduler.service.FlavorService;
import appcloud.resourcescheduler.service.SecurityGroupService;
import appcloud.resourcescheduler.service.VmService;
import appcloud.resourcescheduler.service.VolumeService;
import appcloud.resourcescheduler.service.impl.AggregateServiceImpl;
import appcloud.resourcescheduler.service.impl.FlavorServiceImpl;
import appcloud.resourcescheduler.service.impl.SecurityGroupServiceImpl;
import appcloud.resourcescheduler.service.impl.VmServiceImpl;
import appcloud.resourcescheduler.service.impl.VolumeServiceImpl;
import appcloud.resourcescheduler.strategy.HostSelector;
import appcloud.rpc.tools.RpcException;

public class ResourceSchedulerImpl implements ResourceSchedulerService {

	private static final int DEFAULT_WAITING_TIME = 2000;

	private NodeAgentService nodeAgentService = ConnectionFactory.getNodeAgent();
	private AppRouterService appRouterService = ConnectionFactory.getAppRouter();

	private ResrcStrategyProxy resrcStrategyProxy = ConnectionFactory.getResrcStrategyProxy();
	private J2EEAppProxy j2eeAppProxy = ConnectionFactory.getJ2EEAppProxy();
	private VMAppProxy vmAppProxy = ConnectionFactory.getVMAppProxy();
	private HostProxy hostProxy = ConnectionFactory.getHostProxy();
	private InstanceProxy instanceProxy = ConnectionFactory.getInstanceProxy();

	private VmInstanceProxy vmInstanceProxy = ConnectionFactory.getVmInstanceProxy();
	
	private static Logger logger = Logger
			.getLogger(ResourceSchedulerImpl.class);

	public RSEC createJ2EEApp(J2EEApp app, int copyNum, int resrcStrategyId) {
		RSEC result = RSEC.NO_ERROR;

		logger.info("app create request got: " + app + ", copyNum: " + copyNum
				+ "strategyId: " + resrcStrategyId);

		// step 0. check the uuid.
		/*J2EEApp tmpApp;
		try {
			tmpApp = j2eeAppProxy.getByUuid(app.getUuid(), false, false,
					true);
			if (tmpApp != null && tmpApp.getInstanceNum() > 0) {
				logger.warn("An app of the same uuid:"
						+ tmpApp.getUuid() + " already exists, "
						+ "use IncreaseInstanceCopy() if you want to increase instances");
				result = RSEC.APP_EXIST;
			}
		} catch (Exception e1) {
			logger.warn("db error");
			result = RSEC.DB_ERROR;
		}
		
		if (!result.equals(RSEC.NO_ERROR)) {
			return result;
		}*/		

		// step 1. select the host to deploy
		ResrcStrategy strategy;
		List<Host> hosts = new ArrayList<Host>();
		try {
			strategy = resrcStrategyProxy.getById(resrcStrategyId);
			
			Resource resource = new Resource();
			resource.setMemoryMb(app.getMinMemory());
			hosts = HostSelector.selectNodes(strategy, copyNum, resource, InstanceTypeEnum.J2EE);
			
			if (hosts.size() < copyNum) {
				result = RSEC.RSR_STG;
				logger.warn("hosts " + hosts + "doesn't meet the requirements");
			} else {
				logger.info("hosts selected are: " + hosts);
			}
		} catch (Exception e) {
			logger.warn("db error");
			e.printStackTrace();
			result = RSEC.DB_ERROR;
		}
		

		// step 2. deploy apps on the hosts
		List<Instance> instances = new ArrayList<Instance>();
		if (result.equals(RSEC.NO_ERROR)) {
			int counter = copyNum;  // to deploy such app.
			for (Host host : hosts) {
				if (counter > 0) {
					logger.info("notifying " + host + " to deploy instance");
					Instance instance = startJavaInstance(host, app);
					if (instance != null) {
						instances.add(instance);
						logger.info("instance" + instance + "from " + host
								+ " deployed");
						counter--;
					} else {
						logger.info(host.getIp() + " deploy app failed");
					}
				}
			}

			if (counter > 0) {
				logger.warn("only ( " + instances.size() + "/" + copyNum
						+ " ) copies deployed");
				result = RSEC.LESS_DEPLOY;
			}
		}

		// step 3. notify the routers
		if (result.equals(RSEC.NO_ERROR) || result.equals(RSEC.LESS_DEPLOY)) {
			List<RoutingEntry> routings = new ArrayList<RoutingEntry>();
			for (Instance instance : instances) {
				RoutingEntry rt = genRoutingEntry(app, instance);
				routings.add(rt);
			}

			logger.info("notifying routing server to add routing info:"
					+ routings);

			AREC routeResults;
			try {
				routeResults = appRouterService.addRoutingEntries(routings);
				if (!routeResults.equals(AREC.NO_ERROR)) {
					logger.warn("FAILED adding routing info: \n" +
							routings + "\n, error:"+ ErrorCode.getMessage(routeResults));
					result = RSEC.RT_ERROR;
				}
			} catch (RpcException e) {
				logger.warn("RPC Exception:" + e);
				e.printStackTrace();
				result = RSEC.RPC_ERROR;
			}			
		}

		if (result.equals(RSEC.NO_ERROR)) {
			logger.info("successfully deployed: " + app);
		}
		// step 4. return to caller
		return result;
	}

	public RSEC stopJ2EEApp(String appUuid, int waitingTime) {
		logger.info("receive stop request of appUuid:" + appUuid
				+ "waiting time:" + waitingTime);

		J2EEApp app = null;
		try {
			app = j2eeAppProxy.getByUuid(appUuid, false, true, true);
		} catch (Exception e) {
			return RSEC.DB_ERROR;
		}

		if (app != null) {
			logger.info("trying to stop app:" + app);
			for (Instance instance : app.getInstances()) {
				if (!stopInstance(instance, waitingTime)) {
					return RSEC.NA_ERROR;
				} else {
					logger.info("instance: " + instance.getUuid() + " stopped");
				}
			}
		} else {
			logger.warn("FAILD finding instance of appUuid:" + appUuid);
			return RSEC.APP_NOT_EXIST;
		}

		return RSEC.NO_ERROR;
	}

	public RSEC deleteJ2EEApp(String appUuid, int waitingTime,
			String warLocation) {
		// TODO delete not implemented
		return stopJ2EEApp(appUuid, waitingTime);
	}

	public RSEC deleteJ2EEInstance(String instanceUuid) {
		// TODO todo what?
		Instance instance;
		try {
			instance = instanceProxy.getByUuid(instanceUuid, false, false, false);
		} catch (Exception e) {
			logger.warn("db error" + e.getMessage());
			e.printStackTrace();
			return RSEC.DB_ERROR;
		}
		
		stopInstance(instance, DEFAULT_WAITING_TIME);

		return RSEC.NO_ERROR;
	}

	public RSEC increaseInstanceCopy(String appId, int num) {
		// TODO Auto-generated method stub
		return RSEC.UNKNOWN_ERROR;
	}

	private Instance startJavaInstance(Host host, J2EEApp app) {
		try {
			return nodeAgentService.StartJ2EEInstance(
					RoutingKeyGenerator.getNodeAgentRoutingKey(host),
					app.getUuid(), WarLocationTool.getWarLocation(app.getUuid()),
					app.getMinMemory(), app.getMaxMemory());
		} catch (RpcException e) {
			logger.warn("RPC Exception:" + e);
			e.printStackTrace();
			return null;
		}
	}

	private boolean stopInstance(Instance instance, int waitingTime) {
		Host host = instance.getHost();

		if (host == null) {
			String hostId = instance.getHostUuid();
			try {
				host = hostProxy.getByUuid(hostId, false, false, false);
			} catch (Exception e) {
				logger.warn("db error" + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}

		if (host == null) {
			logger.warn("could not found host info of intance: " + instance);
			return false;
		}

		logger.info("stopping the instance--" + instance + "on host--" + host);
		// now, stop the instance;
		NAEC stopped;
		try {
			stopped = nodeAgentService.StopJ2EEInstance(
					RoutingKeyGenerator.getNodeAgentRoutingKey(host),
					instance.getAppUuid(), instance.getUuid(), waitingTime);
		} catch (RpcException e) {
			logger.warn("RPC Exception:" + e);
			e.printStackTrace();
			return false;
		}
		
		// delete routing entry if instance is stopped
		if (stopped.equals(NAEC.NO_ERROR)) {
			RoutingEntry rt = genRoutingEntry(null, instance);	// only used to display error info
			logger.info("deleting rt: " + rt);
			AREC rtResult;
			try {
				rtResult = appRouterService.deleteDestRoutingEntries(instance.getServiceIP(), instance.getServicePort());
			} catch (RpcException e) {
				logger.warn("RPC Exception:" + e);
				e.printStackTrace();
				return false;
			}			
			if (!rtResult.equals(AREC.NO_ERROR)) {
				logger.warn("FAILED deleting rt: " + rt + ", error:" + ErrorCode.getMessage(rtResult));
				return false;
			}
		} else {
			logger.warn("FAILED stopping instance--" + instance + "on host" + host.getIp());
			return false;
		}
		return true;
	}

	private RoutingEntry genRoutingEntry(J2EEApp app, Instance instance) {
		RoutingEntry rt = new RoutingEntry();

		rt.setDestPrefix(instance.getServiceIP());
		rt.setDestSuffix(instance.getServicePort());
		rt.setIsValid(1);
		if (app != null) {
			rt.setSrcPrefix(app.getDomainPrefix());
			rt.setSrcSuffix(app.getDomainSuffix());
		}
		rt.setType(RETypeEnum.IP);
		rt.setWeight(1);

		return rt;
	}

	public RSEC createVM(String uuid, Integer templateId, String mac, String ip) {
		RSEC result = RSEC.NO_ERROR;
		// TODO 没有这个id
		int resrcStrategyId = 1;
		int copyNum = 1;

		logger.info("VM create request got: " + uuid + ", templateId: " + templateId
				+ ", mac: " + mac + ", ip:" + ip);

		// step 0. check the uuid.
		/*VMApp tmpApp;
		try {
			tmpApp = vmAppProxy.getByUuid(uuid, false, false);
			if (tmpApp != null) {
				logger.warn("An app of the same uuid:"
						+ tmpApp.getUuid() + " already exists");
				result = RSEC.APP_EXIST;
			}
		} catch (Exception e1) {
			logger.warn("db error");
			result = RSEC.DB_ERROR;
		}
		
		if (!result.equals(RSEC.NO_ERROR)) {
			return result;
		}*/

		// step 1. select the host to deploy
		ResrcStrategy strategy;
		List<Host> hosts = new ArrayList<Host>();
		try {
			strategy = resrcStrategyProxy.getById(resrcStrategyId);
			
			Resource resource = new Resource();
			hosts = HostSelector.selectNodes(strategy, copyNum, resource, InstanceTypeEnum.VM);
			
			if (hosts.size() < copyNum) {
				result = RSEC.RSR_STG;
				logger.warn("hosts " + hosts + "doesn't meet the requirements");
			} else {
				logger.info("hosts selected are: " + hosts);
			}
		} catch (Exception e) {
			logger.warn("db error");
			e.printStackTrace();
			result = RSEC.DB_ERROR;
		}
		

		// step 2. deploy apps on the hosts
		List<Instance> instances = new ArrayList<Instance>();
		if (result.equals(RSEC.NO_ERROR)) {
			int counter = copyNum;  // to deploy such app.
			for (Host host : hosts) {
				if (counter > 0) {
					logger.info("notifying " + host + " to deploy instance");
					Instance instance = startVmInstance(host, uuid, templateId, mac, ip);
					if (instance != null) {
						instances.add(instance);
						logger.info("instance" + instance + "from " + host
								+ " deployed");
						counter--;
					} else {
						logger.info(host.getIp() + " deploy app failed");
					}
				}
			}

			if (counter > 0) {
				logger.warn("only ( " + instances.size() + "/" + copyNum
						+ " ) copies deployed");
				result = RSEC.LESS_DEPLOY;
			}
		}
		

		if (result.equals(RSEC.NO_ERROR)) {
			logger.info("successfully deployed vm: " + uuid);
		}
		// step 3. return to caller
		return result;
	}

	public RSEC deleteVM(String uuid) {
		logger.info("receving DELETE VM request: " + uuid);
		
		// step 0. find which host it is on
		Instance instance;
		try {
			instance = instanceProxy.getByUuid(uuid, false, true, false);
		} catch (Exception e) {
			logger.warn("db error" + e.getMessage());
			e.printStackTrace();
			return RSEC.DB_ERROR;
		}
		Host host  = instance.getHost();
		
		// step 1. notify nodeagent to delete vm
		try {
			logger.info("trying to delete vm[" + uuid + "] on host " + host.getIp());
			NAEC error = nodeAgentService.deleteVM(RoutingKeyGenerator.getNodeAgentRoutingKey(host), uuid);
			
			if (error.equals(NAEC.NO_ERROR)) {
				return RSEC.NO_ERROR;
			} else {
				logger.warn("Get NodeAgent Error: " + ErrorCode.getMessage(error));
				return RSEC.NA_ERROR;
			}
		} catch (RpcException e) {
			logger.warn("RPC Error");
			e.printStackTrace();
			return RSEC.NA_ERROR;
		}
		
		
	}

	public RSEC dealVM(String uuid, String action, Integer templateId) {
		logger.info("receving DEAL VM request, uuid:" + uuid + ", action:" + action
				+ ", templateId:" + templateId);
		
		// step 0. find which host it is on
		Instance instance;
		try {
			instance = instanceProxy.getByUuid(uuid, false, true, false);
		} catch (Exception e) {
			logger.warn("db error" + e.getMessage());
			e.printStackTrace();
			return RSEC.DB_ERROR;
		}
		
		if (instance == null) {
			logger.warn("instance with uuid: " + uuid + " not exist!");
			return RSEC.APP_NOT_EXIST;
		}
		
		Host host  = instance.getHost();
		
		NAEC error;
		try {
			logger.info("trying to deal vm[" + uuid + "] on host " + host.getIp());
			error = nodeAgentService.dealVM(RoutingKeyGenerator.getNodeAgentRoutingKey(host), uuid, action, templateId);
			
			if (error.equals(NAEC.NO_ERROR)) {
				return RSEC.NO_ERROR;
			} else {
				logger.warn("Get NodeAgent Error: " + ErrorCode.getMessage(error));
				return RSEC.RPC_ERROR;
			}
		} catch (RpcException e) {
			logger.warn("RPC Exception:" + e);
			e.printStackTrace();
			return RSEC.NA_ERROR;
		}
	}
	
	private Instance startVmInstance(Host host, String uuid, Integer templateId, String mac, String ip) {
		// routingKey, uuid, templateId, mac, ip)
		try {
			return nodeAgentService.createVM(
					RoutingKeyGenerator.getNodeAgentRoutingKey(host),
					uuid, templateId, mac, ip);
		} catch (RpcException e) {
			logger.warn("RPC Exception:" + e);
			e.printStackTrace();
			return null;
		}
	}


	
	
	VmService vmService = new VmServiceImpl();
	VolumeService volumeService = new VolumeServiceImpl();
	AggregateService aggregateService = new AggregateServiceImpl();
	FlavorService flavorService = new FlavorServiceImpl();
	SecurityGroupService securityGroupService = new SecurityGroupServiceImpl();
	
	@Override
	public String createVM(String name, String userId, String imgId,
			Integer flavorId, Integer sgId, Integer avalibilityZoneId,
			Map<String, String> metadata) throws RpcException {

		return vmService.createVM(name, userId, imgId, flavorId, sgId, avalibilityZoneId, metadata);
	}

	@Override
	public void startVM(String uuid) throws RpcException {
		vmService.startVM(uuid);
	}

	@Override
	public void stopVM(String uuid) throws RpcException {
		vmService.stopVM(uuid);
	}

	@Override
	public void resumeVM(String uuid) throws RpcException {
		vmService.resumeVM(uuid);
	}

	@Override
	public void rebootVM(String uuid) throws RpcException {
		vmService.rebootVM(uuid);
	}

	@Override
	public void rebuildVM(String name, String imgId, Integer flavorId,
			Integer sgId, Map<String, String> metadata) throws RpcException {
		vmService.rebuildVM(name, imgId, flavorId, sgId, metadata);
	}

	@Override
	public void resizeVM(String uuid, Integer flavorId) throws RpcException {
		vmService.resizeVM(uuid, flavorId);
	}

	@Override
	public void suspendVM(String uuid) throws RpcException {
		vmService.suspendVM(uuid);
	}

	@Override
	public void forceDeleteVM(String uuid) throws RpcException {
		vmService.forceDeleteVM(uuid);
	}

	@Override
	public void terminateVM(String uuid) throws RpcException {
		vmService.terminateVM(uuid);
	}

	@Override
	public Integer createVolume(String name, String displayName, Integer size,
			String type, Map<String, String> metadata) throws RpcException {
		return volumeService.createVolume(name, displayName, size, type, metadata);
	}

	@Override
	public String attachVolume(String volumeId, String instanceId) throws RpcException {
		return volumeService.attachVolume(volumeId, instanceId);
	}

	@Override
	public void detachVolume(String volumeId, String instanceId) throws RpcException {
		volumeService.detachVolume(volumeId, instanceId);
	}

	@Override
	public String createSnapshot(String displayName, String displayDescription,
			String volumeId, Boolean force) throws RpcException {
		return volumeService.createSnapshot(displayName, displayDescription, volumeId, force);
	}
	
	@Override
	public Integer createAggregate(String name, Integer availabilityZoneId) throws RpcException {
		return aggregateService.createAggregate(name, availabilityZoneId);
	}

	@Override
	public Integer addHostToAggregate(String hostId, Integer aggregateId) throws RpcException {
		return aggregateService.addHostToAggregate(hostId, aggregateId);
	}
	
	@Override
	public Integer removeHostFromAggregate(String hostId, Integer aggregateId) throws RpcException {
		return aggregateService.removeHostFromAggregate(hostId, aggregateId);
	}

	@Override
	public Integer createFlavor(String name, Integer ram, Integer disk,
			Integer vcpus) throws RpcException{
		return flavorService.createFlavor(name, ram, disk, vcpus);
	}
	
	@Override
	public Integer createSecurityGroup(String userId, String name,
			String description) throws RpcException {
		return securityGroupService.createSecurityGroup(userId, name, description);
	}

	@Override
	public Integer createSecurityGroup(Integer sgId, String userId) throws RpcException {
		return securityGroupService.createSecurityGroup(sgId, userId);
	}

	@Override
	public Integer createSecurityGroupRule(Integer sgId, Integer fromPort,
			Integer toPort, String protocal, String range) throws RpcException {
		return securityGroupService.createSecurityGroupRule(sgId, fromPort, toPort, protocal, range);
	}

	@Override
	public void deleteSecurityGroupRule(Integer ruleId) throws RpcException {
		securityGroupService.deleteSecurityGroupRule(ruleId);
	}

}
