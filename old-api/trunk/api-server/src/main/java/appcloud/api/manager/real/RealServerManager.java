package appcloud.api.manager.real;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.api.beans.Load;
import appcloud.api.beans.Meta;
import appcloud.api.beans.Metadata;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.Server;
import appcloud.api.beans.server.ServerActionReboot;
import appcloud.api.beans.server.ServerActionRebuild;
import appcloud.api.beans.server.ServerCreateReq;
import appcloud.api.beans.server.ServerUpdateReq;
import appcloud.api.checkutils.AcGroupChecker;
import appcloud.api.checkutils.InstanceChecker;
import appcloud.api.constant.ServerMetadata;
import appcloud.api.enums.ServerOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.ServerManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.HostLoad;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.TaskStatusEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.proxy.CommonLoadProxy;
import appcloud.common.proxy.DailyLoadProxy;
import appcloud.common.proxy.MonthLoadProxy;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmInstanceMetadataProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.common.proxy.VmLoadProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmVirtualInterfaceProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.rpc.tools.RpcException;
/**
 * @author jianglei
 *
 */
public class RealServerManager implements ServerManager {
	private static Logger logger = Logger.getLogger(RealServerManager.class);
	
	private BeansGenerator generator = BeansGenerator.getInstance();
	private static VmInstanceProxy instanceProxy;
	private static VmInstanceTypeProxy instanceTypeProxy;
	private static VmVolumeProxy volumeProxy;
	private static VmVirtualInterfaceProxy interfaceProxy;
	private static VmLoadProxy vmLoadProxy;
	private static DailyLoadProxy dailyLoadProxy;
	private static MonthLoadProxy monthLoadProxy;

	private VmUserProxy userProxy;
	private VmGroupProxy groupProxy;
	private static VmInstanceMetadataProxy vmInstanceMetadataProxy ;
	private VmInstanceTypeProxy typeProxy;
	private ResourceSchedulerService scheduler;
	
	private static RealServerManager manager = new RealServerManager();
	
	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealServerManager.class);

	public static RealServerManager getInstance() {
		return manager;
	}
	
	private RealServerManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		generator = BeansGenerator.getInstance();
		generator = BeansGenerator.getInstance();
		instanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
				VmInstanceProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		instanceTypeProxy = (VmInstanceTypeProxy) ConnectionFactory.getTipProxy(
				VmInstanceTypeProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		interfaceProxy = (VmVirtualInterfaceProxy) ConnectionFactory.getTipProxy(
				VmVirtualInterfaceProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmLoadProxy = (VmLoadProxy) ConnectionFactory.getTipProxy(
				VmLoadProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		dailyLoadProxy = (DailyLoadProxy) ConnectionFactory.getTipProxy(
				DailyLoadProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		monthLoadProxy = (MonthLoadProxy) ConnectionFactory.getTipProxy(
				MonthLoadProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(
				VmVolumeProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		

		userProxy = (VmUserProxy) ConnectionFactory.getTipProxy(VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		groupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(VmGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		
		vmInstanceMetadataProxy = (VmInstanceMetadataProxy)ConnectionFactory.getTipProxy(VmInstanceMetadataProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		typeProxy = (VmInstanceTypeProxy) ConnectionFactory.getTipProxy(
				VmInstanceTypeProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Server> getList(String tenantId, boolean detailed, boolean allTenants) throws Exception {
		String logStr = null;
		if(allTenants)
			logStr = String.format("User %s request to get all tenants SERVERS", tenantId);
		else
			logStr = String.format("User %s request to get SERVERS", tenantId);
		if(detailed)
			logStr += ", detailed";
		
		logger.info(logStr);
		
		List<VmInstance> instances;
		QueryObject<VmInstance> query = new QueryObject<VmInstance>();
		if(!allTenants)
			query.addFilterBean(new FilterBean<VmInstance>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		if (detailed) {
			// with metadata, network
			instances = (List<VmInstance>) instanceProxy.searchAll(query, false, false, false, false, true, false, true, true);
		} else {
			instances = (List<VmInstance>) instanceProxy.searchAll(query, false, false, false, false, false, false, false, false);
		}

		List<Server> servers = new ArrayList<Server>();
		
		Server server = null;
		for (VmInstance instance : instances) {
			server = generator.instanceToServer(instance, detailed);
			if(!instance.getTaskStatus().equals(TaskStatusEnum.READY)) {
				server.status = "other";
			}
			servers.add(server);
		}
		return servers;
	}

	@Override
	public Server getDetail(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to GET SERVER %s", tenantId, serverId));
		VmInstance instance;
		boolean detailed = true;
		if (detailed) {
			// with metadata, network  + SecurityGroup
			instance = instanceProxy.getByUuid(serverId, false, false, false, false, true, false, true, true);
		} else {
			instance = instanceProxy.getByUuid(serverId, false, false, false, false, false, false, false, false);
		}
		if (instance == null) {
			throw new ItemNotFoundException("server does not exist");
		} 
		Server server = generator.instanceToServer(instance, detailed);
		if(!instance.getTaskStatus().equals(TaskStatusEnum.READY)) {
			server.status = "other";
		}
		return server;
	}

	/* (non-Javadoc)
	 * @see appcloud.api.manager.ServerManager#create(java.lang.String, appcloud.api.beans.server.ServerCreateReq)
	 */
	@Override
	public Server create(String tenantId, ServerCreateReq req) throws Exception {
		logger.info(String.format("User %s request to CREATE SERVER %s", tenantId, req.name));
		//TODO: check auth
		
		// FIXME: image ref, flavor ref, security group: now 0
		SecurityGroup sg = req.securityGroup;
		Integer sgId = 0;
		if (sg != null) {
			sgId = sg.id;
		}
		
		logger.info(String.format("Security Group Id: %d", sgId));
		
		Integer zoneId = null;
		try {
			zoneId = Integer.valueOf(req.availabilityZone);
		} catch (Exception e) {
			zoneId = 0;
		}
		Integer aggregateId = null;
		try {
			aggregateId = Integer.valueOf(req.availabilityAggregate);
		} catch (Exception e) {
		}
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.CREATE_VM, String.format("User %s request to CREATE SERVER %s", tenantId, req.name),
				rpcExtra);

		String uuid = null;
		try {
			List<Integer> aggregateIds = new ArrayList<Integer>();
			if(aggregateId != null)
				aggregateIds.add(aggregateId);
			else {
				QueryObject<VmUser> query = new QueryObject<VmUser>();
				query.addFilterBean(new FilterBean<VmUser>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
				@SuppressWarnings("unchecked")
				List<VmUser> vmUsers = (List<VmUser>) userProxy.searchAll(query);
				if(vmUsers == null || vmUsers.size() == 0)
					throw new ItemNotFoundException("tenant does not exist");
				
				VmGroup vmGroup = groupProxy.getById(vmUsers.get(0).getGroupId());
				if(vmGroup == null)
					throw new ItemNotFoundException("group does not exist");
				
				aggregateIds = generator.groupToAcGroup(vmGroup).availableClusters;
			}
			
			if(!AcGroupChecker.checkInstanceCount(tenantId)) {
				String message = "user " + tenantId + " request to create vm while his vm number reaches the upper limit";
				logger.warn(message);
				loller.warn(LolLogUtil.CREATE_VM, message, rpcExtra);
				throw new OperationFailedException("user's vm number reaches the upper limit");
			}
				
			if(!AcGroupChecker.checkSelectedCluster(tenantId, aggregateIds)) {		
				String message = "user " + tenantId + " request to create vm in a cluster not allowed";
				logger.warn(message);
				loller.warn(LolLogUtil.CREATE_VM, message, rpcExtra);
				throw new OperationFailedException("selected aggregate is not allowed for user");
			}
			try {
				uuid = scheduler.createVM(req.name, tenantId, req.imageRef,
					Integer.valueOf(req.flavorRef), sgId, zoneId, aggregateIds, null, req.metadata, rpcExtra);
			} catch (RpcException e) {
				DealException.isRSTimeoutException(e, LolLogUtil.CREATE_VM, rpcExtra);
				return new Server();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RpcException e) {
			throw e;
		}
		if(uuid == null)
			throw new OperationFailedException("create server failed");
		VmInstance instance = new VmInstance();
		instance.setName(req.name);
		instance.setUuid(uuid);
		instance.setUserId(Integer.valueOf(tenantId));
		
		logger.info(String.format("SERVER created successfully, uuid is %s", uuid));
		
		return generator.instanceToServer(instance, false);
	}

	@Override
	public void osStart(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to START SERVER %s", tenantId, serverId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.START_VM, String.format("User %s request to START SERVER %s", tenantId, serverId), rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.OS_START);
		} catch (Exception e) {
			loller.warn(LolLogUtil.START_VM, "start vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.startVM(serverId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.START_VM, rpcExtra);
			return;
		}
		
		logger.info(String.format("SERVER %s started successfully", serverId));
	}

	@Override
	public void osStop(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to STOP SERVER %s", tenantId, serverId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.STOP_VM, String.format("User %s request to STOP SERVER %s", tenantId, serverId), rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.OS_STOP);
		} catch (Exception e) {
			loller.warn(LolLogUtil.STOP_VM, "stop vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.stopVM(serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.STOP_VM, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s stoped successfully", serverId));
	}

	@Override
	public void resume(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to RESUME SERVER %s", tenantId, serverId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.RESUME_VM, String.format("User %s request to RESUME SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.OS_RESUME);
		} catch (Exception e) {
			loller.warn(LolLogUtil.RESUME_VM, "resume vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.resumeVM(serverId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.RESUME_VM, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s resumed successfully", serverId));
	}

	@Override
	public void reboot(String tenantId, String serverId,
			ServerActionReboot reboot) throws Exception {
		logger.info(String.format("User %s request to REBOOT SERVER %s", tenantId, serverId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.REBOOT_VM, String.format("User %s request to REBOOT SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.REBOOT);
		} catch (Exception e) {
			loller.warn(LolLogUtil.REBOOT_VM, "reboot vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.rebootVM(serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REBOOT_VM, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s rebooted successfully", serverId));
	}

	@Override
	public Server rebuild(String tenantId, String serverId,
			ServerActionRebuild rebuild) throws Exception {

		if(serverId == null || serverId.equals("")) {
			logger.error(String.format("User %s request to REBUILD SERVER %s Failed! ", tenantId, serverId));
		}
		if(rebuild == null) {
			logger.error(String.format("User %s request to REBUILD SERVER %s Failed! rebuildReq is %s", tenantId, serverId, rebuild));
		}
		logger.info(String.format("User %s request to REBUILD SERVER %s ", tenantId, serverId));
		Integer sgId = null;
		if (rebuild.securityGroup == null) {
			sgId = null;
			logger.info("Security Group not updated");
		} else {
			sgId = rebuild.securityGroup.id;
			logger.info("Security Group Updated to " + sgId);
		}

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		if (sgId != null){
			logger.info(String.format("Security Group change req, serverId:%s, sgId:%s",
					serverId, sgId));
			loller.info(LolLogUtil.REBUILD_VM, String.format("User %s request to REBUILD SERVER %s, " +
					" sgId:%s",	tenantId, serverId, sgId), rpcExtra);
		}

		Integer flavorId = null;
		if (rebuild.flavorRef != null) {
			flavorId = Integer.valueOf(rebuild.flavorRef);
			logger.info(String.format("Server flavor change req, serverId : %s, new flavor: %s ",
					serverId, rebuild.flavorRef));
			loller.info(LolLogUtil.REBUILD_VM, String.format("User %s request to REBUILD SERVER %s, " +
					" new flavor:%s", tenantId, serverId, rebuild.flavorRef), rpcExtra);
		}
		
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.REBUILD);
		} catch (Exception e) {
			loller.warn(LolLogUtil.REBUILD_VM, "rebuild vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.rebuildVM(serverId, Integer.parseInt(tenantId), flavorId, sgId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REBUILD_VM, rpcExtra);
			return new Server();
		}
		
		VmInstance instance = new VmInstance();
		instance.setName(rebuild.name);
		instance.setUuid(serverId);
		instance.setUserId(Integer.valueOf(tenantId));

		logger.info(String.format("SERVER %s rebuilt successfully", serverId));
		
		return generator.instanceToServer(instance, false);
	}

	@Override
	public void resize(String tenantId, String serverId, String flavorRef) throws Exception {
		logger.info(String.format("User %s request to RESIZE SERVER %s, new flavor:%s", 
				tenantId, serverId, flavorRef));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.RESIZE_VM, String.format("User %s request to RESIZE SERVER %s, new flavor:%s", 
				tenantId, serverId, flavorRef),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.RESIZE);
		} catch (Exception e) {
			loller.warn(LolLogUtil.RESIZE_VM, "resize vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.rebuildVM(serverId, Integer.valueOf(tenantId), Integer.valueOf(flavorRef), null, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REBUILD_VM, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s resized successfully", serverId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String reset(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to RESET SERVER %s", tenantId, serverId));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.REVERT_VOLUME, String.format("User %s request to RESET SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.AC_RESET);
		} catch (Exception e) {
			loller.warn(LolLogUtil.REVERT_VOLUME, "reset vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		List<VmVolume> vmVolumes;
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		query.addFilterBean(new FilterBean<VmVolume>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("instanceUuid", serverId, FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("usageType", VmVolumeUsageTypeEnum.SYSTEM, FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("attachStatus", VmVolumeAttachStatusEnum.ATTACHED, FilterBeanType.EQUAL));
		
		try {
			vmVolumes = (List<VmVolume>) volumeProxy.searchAll(query, false, false, false, false);
			if(vmVolumes == null || vmVolumes.size()!=1){
				logger.info("server " + serverId+ " has no system volume");
				loller.warn(LolLogUtil.REVERT_VOLUME, "server " + serverId+ " has no system volume", rpcExtra);
				throw new OperationFailedException("server has no system volume");
				
			}
		}catch (Exception ex) {
			throw ex;
		}
		String volumeUuid = vmVolumes.get(0).getVolumeUuid();
		logger.info("volumeUuid is " + volumeUuid);
		try {
			scheduler.revertVolume(volumeUuid, serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REVERT_VOLUME, rpcExtra);
			return "";
		}
		logger.info("reset successfully");
		return "success";
	}
	@Override
	public void suspend(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to SUSPEND SERVER %s", tenantId, serverId));

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.SUSPEND_VM, String.format("User %s request to SUSPEND SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.OS_SUSPEND);
		} catch (Exception e) {
			loller.warn(LolLogUtil.SUSPEND_VM, "suspend vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.suspendVM(serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.SUSPEND_VM, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s suspended successfully", serverId));
	}

	@Override
	public void forceDelete(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to FORCE DELETE SERVER %s", tenantId, serverId));

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.FORCE_DELETE_VM, String.format("User %s request to FORCE DELETE SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.FORCE_DELETE);
		} catch (Exception e) {
			loller.warn(LolLogUtil.FORCE_DELETE_VM, "force delete vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.forceDeleteVM(serverId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.FORCE_DELETE_VM, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s deleted successfully", serverId));
	}

	@Override
	public void terminate(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to TERMINATE SERVER %s", tenantId, serverId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.TERMINATE_VM, String.format("User %s request to TERMINATE SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.DELETE);
		} catch (Exception e) {
			loller.warn(LolLogUtil.TERMINATE_VM, "terminate vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.terminateVM(serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.TERMINATE_VM, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s terminated successfully", serverId));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Load> getMonitorData(String tenantId, String serverId, String type, Timestamp startTime, Timestamp endTime) {
		logger.info(String.format("Monitor request: serverId:%s, type:%s, start:%s, end:%s",
				serverId, type, startTime, endTime));
		logger.info(String.format("User %s request to GET MONITOR DATA: server:%s type:%s, start:%s, end:%s",
				tenantId, serverId, type, startTime, endTime));
		CommonLoadProxy proxy;
		
		if (type.equalsIgnoreCase("day")) {
			proxy = vmLoadProxy;
		} else if (type.equalsIgnoreCase("month")) {
			proxy = dailyLoadProxy;
		} else {
			proxy = monthLoadProxy;
		}
		
		List<HostLoad> vmLoads = (List<HostLoad>) proxy.getLoads(serverId, startTime, endTime);
		
		List<Load> loads = new ArrayList<Load>();
		
		for (HostLoad vmLoad : vmLoads) {
			loads.add(generator.vmLoadToLoad(vmLoad));
		}
		
		return loads;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Meta updateMetadataItem(String tenantId, String serverId, String key,boolean release, Meta meta)
			throws Exception {
		if(serverId==null || serverId.equals("")) {
			logger.error(String.format("User %s request to update the metadata of the SERVER failed! Server id is %s !", tenantId, serverId));
		}

		if(key==null || key.equals("")) {
			logger.error(String.format("User %s request to update the metadata of the SERVER %s failed!! metadata key is %s !", tenantId, serverId, key));
			return null;
		}

		if(meta==null) {
			logger.error(String.format("User %s request to update the metadata of the SERVER %s failed!! new metadata is %s !", tenantId, serverId, meta));
			return null;
		}

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		logger.info(String.format("User %s request to update the metadata of the SERVER %s ", tenantId, serverId));
		
		try {
			VmInstance instance = instanceProxy.getByUuid(serverId, false, false, false, false, false, false, false, false);
			if(instance == null) {
				logger.error(String.format("User %s request to update the name of the SERVER %s failed!! vminstance is null!", tenantId, serverId));
				loller.error(LolLogUtil.UPDATE_SERVER_DESC, String.format("User %s request to UPDATE SERVER %s failed! vminstance is null", tenantId, serverId), rpcExtra);
				return null;
			}
			List<VmInstanceMetadata> vmMetadataList = (List<VmInstanceMetadata>) vmInstanceMetadataProxy.getByVmInstanceId(instance.getId(), false);
			if(vmMetadataList == null) {
				logger.error(String.format("User %s request to update the name of the SERVER %s failed!! Get vminstance metadata failed!", tenantId, serverId));
				loller.error(LolLogUtil.UPDATE_SERVER_DESC, String.format("User %s request to UPDATE SERVER %s failed! Get vminstance metadata failed!", tenantId, serverId), rpcExtra);
				return null;
			}

			VmInstanceMetadata newMetadata = null;
			for(VmInstanceMetadata metadata : vmMetadataList) {//找出和key值相同的那一个条目
				if(metadata.getKey().equalsIgnoreCase(key)) {
					if(key.equals("oldPriBandNum") || key.equals("oldMaxBandNum")){//如果是这两个，则特殊输出到日志提示一下
						logger.info("---------------------"+key+"---------------------");
					}
					newMetadata = metadata;
					break;
				}
			}
			if(newMetadata == null) {//说明旧带宽这个条目不存在，因为2015-6-6之前没有旧带宽这个概念 
				Integer vmInstanceId = vmMetadataList.get(0).getVmInstanceId();
				logger.info("修改的vmInstanceId===="+vmInstanceId);
				if(key.equals("oldPriBandNum")){
					logger.info("新增旧私网带宽");
					VmInstanceMetadata pMetadata = new VmInstanceMetadata();
					pMetadata.setKey(meta.key);
					pMetadata.setValue(meta.value);
					pMetadata.setVmInstanceId(vmInstanceId);
					vmInstanceMetadataProxy.save(pMetadata);
					return meta;
				}else if(key.equals("oldMaxBandNum")){
					logger.info("新增旧公网带宽");
					VmInstanceMetadata mMetadata = new VmInstanceMetadata();
					mMetadata.setKey(meta.key);
					mMetadata.setValue(meta.value);
					mMetadata.setVmInstanceId(vmInstanceId);
					vmInstanceMetadataProxy.save(mMetadata);
					return meta;
				}else{
					logger.error(String.format("User %s request to update the name of the SERVER %s failed! The KEY %s Metadata is not found!", tenantId, serverId, key));
					loller.error(LolLogUtil.UPDATE_SERVER_DESC, String.format("User %s request to UPDATE SERVER %s failed! The KEY %s Metadata is not found!", tenantId, serverId, key), rpcExtra);
					return null;
				}
			}
			
			if(ServerMetadata.DISPLAY_DESCRIPTION.equalsIgnoreCase(key)) {//修改描述
				logger.info("修改描述");
				return updateVmDescription(tenantId, serverId, newMetadata, meta, rpcExtra);
			}
			if(ServerMetadata.DISPLAY_NAME.equalsIgnoreCase(key)) {//修改名称
				logger.info("修改名称");
				//return updateServerName(tenantId, );
				return null;
			}
			
			if(!ServerMetadata.DISPLAY_DESCRIPTION.equalsIgnoreCase(key)) {
				if(key.equals("oldPriBandNum")){
					logger.info("更新旧私网带宽 value==="+meta.value);
					newMetadata.setValue(meta.value);
					vmInstanceMetadataProxy.update(newMetadata);
					return meta;
				}else if(key.equals("oldMaxBandNum")){
					logger.info("更新旧公网带宽 value==="+meta.value);
					newMetadata.setValue(meta.value);
					vmInstanceMetadataProxy.update(newMetadata);
					return meta;
				}else{//在这里只可能修改公网带宽或者私网带宽
					logger.info("修改带宽时 更新"+key+" 为："+meta.value);
					return updateVmBandwidth(tenantId, serverId, newMetadata, meta, release,rpcExtra);
				}
			}
		} catch (Exception e) {
			if(ServerMetadata.DISPLAY_DESCRIPTION.equalsIgnoreCase(key)) {
				loller.warn(LolLogUtil.UPDATE_SERVER_DESC, "update vm description warn:"+e.getMessage(), rpcExtra);
			}else {
				loller.warn(LolLogUtil.UPDATE_BANDWIDTH, "update vm bandwidth warn:"+e.getMessage(), rpcExtra);
			}
			throw e;
		}
		return null;
	}

	@Override
	public Metadata getMetadata(String tenantId, String serverId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metadata updateMetadata(String tenantId, String serverId, Metadata metadata)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metadata setMetadata(String tenantId, String serverId, Metadata metadata)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meta getMetadataItem(String tenantId, String serverId, String key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meta setMetadataItem(String tenantId,String serverId,  String key, Meta meta)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bootFromISO(String tenantId, String serverId, String imgRef)
			throws Exception {
		logger.info(String.format("User %s request to BOOT SERVER %s FROM ISO %s",
				tenantId, serverId, imgRef));

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.BOOT_FROM_ISO, String.format("User %s request to BOOT SERVER %s FROM ISO %s",
				tenantId, serverId, imgRef),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.AC_ISO_BOOT);
		} catch (Exception e) {
			loller.warn(LolLogUtil.BOOT_FROM_ISO, "boot from iso warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.bootFromISO(imgRef, serverId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.BOOT_FROM_ISO, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s booted from iso successfully", serverId));
	}

	@Override
	public void detachISO(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to detach ISO from SERVER %s", tenantId, serverId));

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.DETACH_ISO, String.format("User %s request to detach ISO from SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.AC_ISO_DETACH);
		} catch (Exception e) {
			loller.warn(LolLogUtil.DETACH_ISO, "detach iso warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.detachISO(serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DETACH_ISO, rpcExtra);
			return;
		}
		logger.info(String.format("ISO detached from SERVER %s successfully", serverId));
	}

	@Override
	public void forceStop(String tenantId, String serverId) throws Exception {
		logger.info(String.format("User %s request to FORCE STOP SERVER %s", tenantId, serverId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.FORCE_STOP_VM, String.format("User %s request to FORCE STOP SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.AC_FORCE_STOP);
		} catch (Exception e) {
			loller.warn(LolLogUtil.FORCE_STOP_VM, "force stop vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.forceStopVM(serverId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.FORCE_STOP_VM, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s stoped successfully", serverId));
	}

	@Override
	public String forceRefresh(String tenantId, String serverId)
			throws Exception {
		logger.info(String.format("User %s request to FORCE REFRESH SERVER %s", tenantId, serverId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.GET_VM_STATE, String.format("User %s request to FORCE REFRESH SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.AC_FORCE_REFRESH);
		} catch (Exception e) {
			loller.warn(LolLogUtil.GET_VM_STATE, "force refresh vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		VmStatusEnum status = null;
		try {
			status = scheduler.getVMState(serverId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.GET_VM_STATE, rpcExtra);
			return "";
		}
		if(status == null) {
			throw new OperationFailedException("froce refresh failed");
		}
		logger.info(String.format("SERVER %s refreshed successfully", serverId));
		return status.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Server> searchByProperties(String tenantId, String userId,
			String serverId, String serverName, String status, Integer zoneId,
			Integer aggregateId, String hostId, String serverIp,
			Date startDate, Date endDate, Integer page, Integer size)
			throws Exception {
		String logStr = null;
		logStr = String.format("User %s request to search SERVERS", tenantId);
		
		List<VmInstance> instances;
		QueryObject<VmInstance> query = new QueryObject<VmInstance>();
		
		if(userId != null){
			query.addFilterBean(new FilterBean<VmInstance>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if(serverId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("uuid", serverId, FilterBeanType.EQUAL));
			logStr += ", serverId:" + serverId;
		}
		if(serverName != null){
			query.addFilterBean(new FilterBean<VmInstance>("name", serverName, FilterBeanType.BOTH_LIKE));
			logStr += ", serverName:" + serverName;
		}
		if(status != null)
			try {
				query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status;
			}catch (Exception e) {
				logger.info("status illegal");
				throw new OperationFailedException("staus illegal");
			}
		else 
			query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		//zone id
		if(zoneId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityZoneId", zoneId, FilterBeanType.EQUAL));
			logStr += ", zoneId:" + zoneId;
		}
		//aggregate id
		if(aggregateId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityClusterId", aggregateId, FilterBeanType.EQUAL));
			logStr += ", aggregateId: " + aggregateId;
		}
		//host id
		if(hostId != null) {
			logStr += ", hostId:" + hostId;
			query.addFilterBean(new FilterBean<VmInstance>("hostUuid", hostId, FilterBeanType.EQUAL));
		}
		
		//server ip
		if(serverIp != null) {
			List<VmVirtualInterface> vifs = (List<VmVirtualInterface>)interfaceProxy.getByIp(serverIp);
			logStr += ", severIP:" + serverIp;
			if(vifs.size() == 0){
				logger.info(logStr);
				return null;
			}
			List<String> instanceUuids = new ArrayList<String>();
			for(VmVirtualInterface vf : vifs)
				instanceUuids.add(vf.getInstanceUuid());
			query.addFilterBean(new FilterBean<VmInstance>("uuid", instanceUuids, FilterBeanType.IN));
			
		}
		if(startDate != null){
			query.addFilterBean(new FilterBean<VmInstance>("scheduledTime", startDate, FilterBeanType.MORE_THAN));
			logStr += ", startDate:" + startDate;
		}
		if(endDate != null){
			query.addFilterBean(new FilterBean<VmInstance>("launchedTime", endDate, FilterBeanType.LESS_THAN));
			logStr += ", endDate:" + endDate;
		}
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page != 0)
			page -= 1;
		logger.info(logStr);
		// with metadata, network
		instances = (List<VmInstance>) instanceProxy.searchAll(query, false, false, false, false, true, false, true, true, page, size);
	
		List<Server> servers = new ArrayList<Server>();
		
		for (VmInstance instance : instances) {
			servers.add(generator.instanceToServer(instance, true));
		}
		
		return servers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long countByProperties(String tenantId, String userId,
			String serverId, String serverName, String status, Integer zoneId,
			Integer aggregateId, String hostId, String serverIp,
			Date startDate, Date endDate) throws Exception {
		String logStr = String.format("User %s request to count SERVERS", tenantId);
		
		QueryObject<VmInstance> query = new QueryObject<VmInstance>();
		
		if(userId != null){
			query.addFilterBean(new FilterBean<VmInstance>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if(serverId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("uuid", serverId, FilterBeanType.EQUAL));
			logStr += ", serverId:" + serverId;
		}
		if(serverName != null){
			query.addFilterBean(new FilterBean<VmInstance>("name", serverName, FilterBeanType.BOTH_LIKE));
			logStr += ", serverName:" + serverName;
		}
		if(status != null)
			try {
				query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status;
			}catch (Exception e) {
				logger.info("status illegal");
				throw new OperationFailedException("staus illegal");
			}
		else 
			query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		//zone id
		if(zoneId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityZoneId", zoneId, FilterBeanType.EQUAL));
			logStr += ", zoneId:" + zoneId;
		}
		//aggregate id
		if(aggregateId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityClusterId", aggregateId, FilterBeanType.EQUAL));
			logStr += ", aggregateId: " + aggregateId;
		}
		//host id
		if(hostId != null) {
			logStr += ", hostId:" + hostId;
			query.addFilterBean(new FilterBean<VmInstance>("hostUuid", hostId, FilterBeanType.EQUAL));
		}

		//server ip
		if(serverIp != null) {
			List<VmVirtualInterface> vifs = (List<VmVirtualInterface>)interfaceProxy.getByIp(serverIp);
			logStr += ", severIP:" + serverIp;
			if(vifs.size() == 0){
				logger.info(logStr);
				return null;
			}
			List<String> instanceUuids = new ArrayList<String>();
			for(VmVirtualInterface vf : vifs)
				instanceUuids.add(vf.getInstanceUuid());
			query.addFilterBean(new FilterBean<VmInstance>("uuid", instanceUuids, FilterBeanType.IN));
			
		}

		if(startDate != null){
			query.addFilterBean(new FilterBean<VmInstance>("scheduledTime", startDate, FilterBeanType.MORE_THAN));
			logStr += ", startDate:" + startDate;
		}
		if(endDate != null){
			query.addFilterBean(new FilterBean<VmInstance>("launchedTime", endDate, FilterBeanType.LESS_THAN));
			logStr += ", endDate:" + endDate;
		}

		logger.info(logStr);
		Long c = instanceProxy.countSearch(query);
		logger.info(c);
		return c;
	
	}

	/**
	 *将用户虚拟机充公给企业
	 */
	@Override
	public Server update(String tenantId, String serverId, Server server)
			throws Exception {
		logger.info(String.format("User %s request to UPDATE SERVER %s", tenantId, serverId));
		
		VmInstance vmInstance = null;
		try {
			vmInstance = InstanceChecker.checkOwner(tenantId, serverId);
		} catch(Exception e) {
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
			loller.info(LolLogUtil.UPDATE_SERVER, String.format("User %s request to UPDATE SERVER %s", tenantId, serverId), rpcExtra);
			loller.warn(LolLogUtil.UPDATE_SERVER, "update server :"+e.getMessage(), rpcExtra);
			throw e;
		}

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, vmInstance.getUuid());
		loller.info(LolLogUtil.UPDATE_SERVER, String.format("User %s request to UPDATE SERVER %s", tenantId, serverId),rpcExtra);
		try {
			/*scheduler.rebuildVM(vmInstance.getUuid(), server.name, new Integer(server.tenantId), null, null, null, null, rpcExtra);*/
			vmInstance.setUserId(new Integer(server.tenantId));
			vmInstance.setName(server.name);
			instanceProxy.update(vmInstance);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REBUILD_VM, rpcExtra);
			return new Server();
		}
		vmInstance = instanceProxy.getByUuid(serverId, false, false, false, false, false, false, false, false);

		logger.info(String.format("Server %s updated successfully", serverId));
		return generator.instanceToServer(vmInstance, false);
	}

	@Override
	public void modPasswd(String tenantId, String serverId, String userName,
			String passwd, String type) throws Exception {
		logger.info(String.format("User %s request to modify SERVER %s's password", tenantId, serverId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.MOD_VM_PASSWD, String.format("User %s request to modify SERVER %s's password", tenantId, serverId),
				rpcExtra);
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.AC_MODIFYPASSWORD);
		} catch (Exception e) {
			loller.warn(LolLogUtil.FORCE_STOP_VM, "force stop vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		VmImageOSTypeEnum osType = null;
		if(type!= null && (type.equalsIgnoreCase("linux") || type.equalsIgnoreCase("windows")))
			osType = VmImageOSTypeEnum.valueOf(type.toUpperCase());
		try {
			scheduler.modVmPasswd(serverId, userName, passwd, osType, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.MOD_VM_PASSWD, rpcExtra);
			return;
		}
		logger.info(String.format("SERVER %s 's password changed successfully", serverId));
	}
/*
 * 查询多个user_id
 */
	@Override
	public List<Server> searchByProperties(String tenantId,
			List<Integer> userIds, String serverId, String serverName,
			String status, Integer zoneId, Integer aggregateId, String hostId,
			String serverIp, Date startdate, Date endDate, Integer page,
			Integer size) throws Exception {
		String logStr = null;
		logStr = String.format("User %s request to search SERVERS", tenantId);

		List<VmInstance> instances;
		QueryObject<VmInstance> query = new QueryObject<VmInstance>();

		if(userIds != null){
			query.addFilterBean(new FilterBean<VmInstance>("userId", userIds, FilterBeanType.IN));
			logStr += ", userIds:" + userIds;
		}
		if(serverId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("uuid", serverId, FilterBeanType.EQUAL));
			logStr += ", serverId:" + serverId;
		}
		if(serverName != null){
			query.addFilterBean(new FilterBean<VmInstance>("name", serverName, FilterBeanType.BOTH_LIKE));
			logStr += ", serverName:" + serverName;
		}
		if(status != null)
			try {
				query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status;
			}catch (Exception e) {
				logger.info("status illegal");
				throw new OperationFailedException("staus illegal");
			}
		else 
			query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		//zone id
		if(zoneId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityZoneId", zoneId, FilterBeanType.EQUAL));
			logStr += ", zoneId:" + zoneId;
		}
		//aggregate id
		if(aggregateId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityClusterId", aggregateId, FilterBeanType.EQUAL));
			logStr += ", aggregateId: " + aggregateId;
		}
		//host id
		if(hostId != null) {
			logStr += ", hostId:" + hostId;
			query.addFilterBean(new FilterBean<VmInstance>("hostUuid", hostId, FilterBeanType.EQUAL));
		}

		//server ip
		if(serverIp != null) {
			List<VmVirtualInterface> vifs = (List<VmVirtualInterface>)interfaceProxy.getByIp(serverIp);
			logStr += ", severIP:" + serverIp;
			if(vifs.size() == 0){
				logger.info(logStr);
				return null;
			}
			List<String> instanceUuids = new ArrayList<String>();
			for(VmVirtualInterface vf : vifs)
				instanceUuids.add(vf.getInstanceUuid());
			query.addFilterBean(new FilterBean<VmInstance>("uuid", instanceUuids, FilterBeanType.IN));

		}
		if(startdate != null){
			query.addFilterBean(new FilterBean<VmInstance>("scheduledTime", startdate, FilterBeanType.MORE_THAN));
			logStr += ", startDate:" + startdate;
		}
		if(endDate != null){
			query.addFilterBean(new FilterBean<VmInstance>("launchedTime", endDate, FilterBeanType.LESS_THAN));
			logStr += ", endDate:" + endDate;
		}
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page != 0)
			page -= 1;
		logger.info(logStr);
		// with metadata, network
		instances = (List<VmInstance>) instanceProxy.searchAll(query, false, false, false, false, true, false, true, true, page, size);

		List<Server> servers = new ArrayList<Server>();

		for (VmInstance instance : instances) {
			servers.add(generator.instanceToServer(instance, true));
		}

		return servers;
	} 	

/*
 * 查询多个user_id
 */
	public Long countByProperties(String tenantId, List<Integer> userIds,
			String serverId, String serverName, String status, Integer zoneId,
			Integer aggregateId, String hostId, String serverIp,
			Date startDate, Date endDate) throws Exception {
		String logStr = String.format("User %s request to count SERVERS", tenantId);

		QueryObject<VmInstance> query = new QueryObject<VmInstance>();

		if(userIds != null){
			query.addFilterBean(new FilterBean<VmInstance>("userId", userIds, FilterBeanType.IN));
			logStr += ", userId:" + userIds;
		}
		if(serverId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("uuid", serverId, FilterBeanType.EQUAL));
			logStr += ", serverId:" + serverId;
		}
		if(serverName != null){
			query.addFilterBean(new FilterBean<VmInstance>("name", serverName, FilterBeanType.BOTH_LIKE));
			logStr += ", serverName:" + serverName;
		}
		if(status != null)
			try {
				query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status;
			}catch (Exception e) {
				logger.info("status illegal");
				throw new OperationFailedException("staus illegal");
			}
		else 
			query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		//zone id
		if(zoneId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityZoneId", zoneId, FilterBeanType.EQUAL));
			logStr += ", zoneId:" + zoneId;
		}
		//aggregate id
		if(aggregateId != null) {
			query.addFilterBean(new FilterBean<VmInstance>("availabilityClusterId", aggregateId, FilterBeanType.EQUAL));
			logStr += ", aggregateId: " + aggregateId;
		}
		//host id
		if(hostId != null) {
			logStr += ", hostId:" + hostId;
			query.addFilterBean(new FilterBean<VmInstance>("hostUuid", hostId, FilterBeanType.EQUAL));
		}

		//server ip
		if(serverIp != null) {
			List<VmVirtualInterface> vifs = (List<VmVirtualInterface>)interfaceProxy.getByIp(serverIp);
			logStr += ", severIP:" + serverIp;
			if(vifs.size() == 0){
				logger.info(logStr);
				return null;
			}
			List<String> instanceUuids = new ArrayList<String>();
			for(VmVirtualInterface vf : vifs)
				instanceUuids.add(vf.getInstanceUuid());
			query.addFilterBean(new FilterBean<VmInstance>("uuid", instanceUuids, FilterBeanType.IN));
			
		}

		if(startDate != null){
			query.addFilterBean(new FilterBean<VmInstance>("scheduledTime", startDate, FilterBeanType.MORE_THAN));
			logStr += ", startDate:" + startDate;
		}
		if(endDate != null){
			query.addFilterBean(new FilterBean<VmInstance>("launchedTime", endDate, FilterBeanType.LESS_THAN));
			logStr += ", endDate:" + endDate;
		}

		logger.info(logStr);
		Long c = instanceProxy.countSearch(query);
		logger.info(c);
		return c;
	
	}

	@Override
	public Server migrate(String tenantId, String serverId, String hostUuid)
			throws Exception {
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		
		if(hostUuid == null || hostUuid.equals("")) {
			logger.info(String.format("User %s request to migrate SERVER %s by resource dispatch", tenantId, serverId));
			loller.info(LolLogUtil.OFFLINE_MIGRATION, String.format("User %s request to MIGRATE SERVER %s by resource dispatch", tenantId, serverId), rpcExtra);
		} else { 
			logger.info(String.format("User %s request to migrate SERVER %s to HOST %s", tenantId, serverId, hostUuid));
			loller.info(LolLogUtil.OFFLINE_MIGRATION, String.format("User %s request to MIGRATE SERVER %s to HOST %s", tenantId, serverId, hostUuid), rpcExtra);
		}
		
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.AC_MIGRATE);
		} catch (Exception e) {
			loller.warn(LolLogUtil.OFFLINE_MIGRATION, "migrate vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		try {
			scheduler.migrateVM(serverId, hostUuid, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.OFFLINE_MIGRATION, rpcExtra);
			return new Server();
		}
		VmInstance instance = instanceProxy.getByUuid(serverId, false, false, false, false, false, false, false, false);
		logger.info(String.format("SERVER %s migrate to HOST %s successfully", serverId, instance.getHostUuid()));
		return generator.instanceToServer(instance, false);
	}

	
	@Override
	public Server onlineMigrate(String tenantId, String serverId, String hostUuid)
			throws Exception {
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		
		if(hostUuid == null || hostUuid.equals("")) {
			logger.info(String.format("User %s request to online migrate SERVER %s by resource dispatch", tenantId, serverId));
			loller.info(LolLogUtil.ONLINE_MIGRATION, String.format("User %s request to ONLINE MIGRATE SERVER %s by resource dispatch", tenantId, serverId), rpcExtra);
		} else { 
			logger.info(String.format("User %s request to online migrate SERVER %s to HOST %s", tenantId, serverId, hostUuid));
			loller.info(LolLogUtil.ONLINE_MIGRATION, String.format("User %s request to ONLINE MIGRATE SERVER %s to HOST %s", tenantId, serverId, hostUuid), rpcExtra);
		}
		
		try {
			InstanceChecker.checkOperation(tenantId, serverId, ServerOperationEnum.AC_ONLINE_MIGRATE);
		} catch (Exception e) {
			loller.warn(LolLogUtil.ONLINE_MIGRATION, "online migrate vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		try {
			scheduler.onlineMigrateVM(serverId, hostUuid, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.ONLINE_MIGRATION, rpcExtra);
			return new Server();
		}
		VmInstance instance = instanceProxy.getByUuid(serverId, false, false, false, false, false, false, false, false);
		logger.info(String.format("SERVER %s migrate to HOST %s online successfully", serverId, instance.getHostUuid()));
		return generator.instanceToServer(instance, false);
	}
	
	@Override
	public Server updateServerName(String tenantId, ServerUpdateReq uReq) throws Exception {
		if(uReq==null || uReq.serverId==null || uReq.serverId.equals("")) {
			logger.error(String.format("User %s request to update the name of the SERVER failed! server id is null!", tenantId));
		}
		
		if(uReq.name==null || uReq.name.equals("")) {
			logger.error(String.format("User %s request to update the name of the SERVER %s failed!! new server name is null!", tenantId, uReq.serverId));
			return null;
		}
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, uReq.serverId);
		logger.info(String.format("User %s request to update the name of the SERVER %s ", tenantId, uReq.serverId));
		loller.info(LolLogUtil.UPDATE_SERVER_NAME, String.format("User %s request to UPDATE SERVER %s ", tenantId, uReq.serverId), rpcExtra);
		
		try {
			VmInstance instance = instanceProxy.getByUuid(uReq.serverId, false, false, false, false, false, false, false, false);
			if(instance == null) {
				logger.error(String.format("User %s request to update the name of the SERVER %s failed!! vminstance is null!", tenantId, uReq.serverId));
				loller.error(LolLogUtil.UPDATE_SERVER_NAME, String.format("User %s request to UPDATE SERVER %s failed! vminstance is null", tenantId, uReq.serverId), rpcExtra);
				return null;
			}

			instance.setName(uReq.name);
			VmInstanceType instanceType = instanceTypeProxy.getById(instance.getInstanceTypeId());
			instanceType.setName(uReq.name + "-flavor");
			instance.setVmInstanceType(instanceType);
			instanceProxy.update(instance);

			logger.info(String.format("User %s request to update the name of the SERVER %s SUCCESS! ", tenantId, uReq.serverId));
			loller.info(LolLogUtil.UPDATE_SERVER_NAME, String.format("User %s request to UPDATE SERVER %s SUCCESS!", tenantId, uReq.serverId), rpcExtra);
			instance.setUserId(Integer.valueOf(tenantId));
			return generator.instanceToServer(instance, false);
		} catch (Exception e) {
			logger.error(String.format("User %s request to update the desc of the SERVER %s Failed! ", tenantId, uReq.serverId));
			loller.warn(LolLogUtil.UPDATE_SERVER_NAME, "update vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		
	}
	/**
	 *修改虚拟机描述
	 *@param  tenantId  操作者Id； serverId  虚拟机uuid， 
	 *        metadata  当前虚拟机的metadata； meta 新的meta 
	 *@return meta
	 *@author hgm
	 * @throws Exception 
	 */
	Meta updateVmDescription(String tenantId, String serverId, 
			VmInstanceMetadata metadata, Meta meta, RpcExtra rpcExtra) throws Exception {
		logger.info(String.format("User %s request to update the desc of the SERVER %s ! ", tenantId, serverId));
		metadata.setValue(meta.value);
		try {
			vmInstanceMetadataProxy.update(metadata);
			logger.info(String.format("User %s request to update the desc of the SERVER %s SUCCESS! ", tenantId, serverId));
			loller.info(LolLogUtil.UPDATE_SERVER_DESC, String.format("User %s request to UPDATE SERVER %s SUCCESS!", tenantId, serverId), rpcExtra);
			return meta;
		}catch (Exception e) {
			logger.error(String.format("User %s request to update the desc of the SERVER %s Failed! ", tenantId, serverId));
			loller.warn(LolLogUtil.UPDATE_SERVER_DESC, "update vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
	}
	
	/**
	 *修改虚拟机带宽
	 *@param  tenantId  操作者Id； serverId  虚拟机uuid， 
	 *        metadata  当前虚拟机的metadata； meta 新的meta 
	 *@return meta
	 *@author hgm
	 * @throws Exception 
	 */
	Meta updateVmBandwidth(String tenantId, String serverId, 
			VmInstanceMetadata metadata, Meta meta, boolean release,RpcExtra rpcExtra) throws Exception {
		logger.info(String.format("User %s request to set the bandwidth of the SERVER %s ! ", tenantId, serverId));
		Map<String,String> metadatas = new HashMap<String, String>();
		metadatas.put(meta.key, meta.value);
		try {
			int returnBand = scheduler.setVmMaxBandwidth(serverId, metadatas,release, rpcExtra);
			if(returnBand==-1) {
				logger.info(String.format("User %s request to set the bandwidth of the SERVER %s Failed! ", tenantId, serverId));
				loller.info(LolLogUtil.UPDATE_BANDWIDTH, String.format("User %s request to SET SERVER %s bandwidth Failed!", tenantId, serverId), rpcExtra);
				return null;
			}
			logger.info(String.format("User %s request to set the bandwidth of the SERVER %s SUCCESS! bandwidth is %s ", tenantId, serverId, returnBand+""));
			loller.info(LolLogUtil.UPDATE_BANDWIDTH, String.format("User %s request to SET SERVER %s bandwidth SUCCESS!", tenantId, serverId), rpcExtra);
			meta.value = returnBand + "";
			return meta;
		}catch (Exception e) {
			logger.error(String.format("User %s request to update the bandwidth of the SERVER %s Failed! ", tenantId, serverId));
			loller.warn(LolLogUtil.UPDATE_BANDWIDTH, "update vm warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
	}
	
	public void test(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("priBandwidth", "50");
		try {
			scheduler.setVmMaxBandwidth("c949112c82cf4f0a90e2d3ab52fa2c9e", map, true,new RpcExtra());
			logger.info("changed success");
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//该函数只用于主函数的测试
	public Server testRebuild() throws Exception{
		ServerActionRebuild rebuild = new ServerActionRebuild();
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), "775", "688c9ff6780f419ca964d5252f9232e8");
		int id = scheduler.createFlavor("test-band-floaver", 2, 20, 2, rpcExtra);
		rebuild.flavorRef = id + "";
		return rebuild("775", "688c9ff6780f419ca964d5252f9232e8", rebuild);
				
	}
	
	public static void main(String[] args) throws Exception {
		/*new RealServerManager().test();*/
		ServerUpdateReq uReq = new ServerUpdateReq("688c9ff6780f419ca964d5252f9232e8", "mytest",null);
		Server server = new RealServerManager().testRebuild();
		System.out.println(server.flavor);
	}
}

