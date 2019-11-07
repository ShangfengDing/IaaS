package appcloud.openapi.operate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.api.beans.Flavor;
import appcloud.api.beans.server.ServerCreateReq;
import appcloud.api.constant.ServerMetadata;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmInstanceMetadataProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.openapi.checkutils.AcGroupChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.constant.ResultConstants;
import appcloud.openapi.datatype.InstanceStatusItem;
import appcloud.openapi.datatype.InstanceStatusSet;
import appcloud.openapi.manager.util.BeansGenerator;
import appcloud.openapi.manager.util.DealException;
import appcloud.openapi.manager.util.LolHelper;
import appcloud.openapi.operate.InstanceOperate;
import appcloud.rpc.tools.RpcException;

import com.appcloud.vm.fe.manager.BillingrateManager;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.Billingrate;
import com.appcloud.vm.fe.model.VmHdEndtime;

@Component
public class InstanceOperateImpl implements InstanceOperate {

	private static Logger logger = Logger.getLogger(InstanceOperateImpl.class);
	private static LolLogUtil loller = LolHelper
			.getLolLogUtil(InstanceOperateImpl.class);
	private BeansGenerator generator = BeansGenerator.getInstance();
	private ResourceSchedulerService scheduler;
	private VmVolumeProxy volumeProxy;
	private VmInstanceProxy vmInstanceProxy;
	private VmUserProxy vmUserProxy;
	private VmGroupProxy vmGroupProxy;
	private VmInstanceTypeProxy vmInstanceTypeProxy;
	private VmInstanceMetadataProxy vmInstanceMetadataProxy;
	private VmSecurityGroupProxy vmSecurityGroupProxy;

	private static InstanceOperateImpl instanceOperate = new InstanceOperateImpl();

	public static InstanceOperateImpl getInstance() {
		return instanceOperate;
	}

	public InstanceOperateImpl() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory
				.getAMQPService(ResourceSchedulerService.class,
						RoutingKeys.RESOUCE_SCHEDULER);
		vmInstanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
				VmInstanceProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
				VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmGroupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(
				VmGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmInstanceTypeProxy = (VmInstanceTypeProxy) ConnectionFactory
				.getTipProxy(
						VmInstanceTypeProxy.class,
						appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmInstanceMetadataProxy = (VmInstanceMetadataProxy) ConnectionFactory
				.getTipProxy(
						VmInstanceMetadataProxy.class,
						appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmSecurityGroupProxy = (VmSecurityGroupProxy) ConnectionFactory
				.getTipProxy(
						VmSecurityGroupProxy.class,
						appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(
				VmVolumeProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	// 创建Flavor，在数据库中插入一条instance-type数据
	public Flavor createFlavor(Integer userId, String instanceTypeName,
			Integer dataDisk, String imageId) throws Exception {
		// TODO Auto-generated method stub
		List<Billingrate> billingrateList = BillingrateManager
				.findBillingRateByNameAndPType(instanceTypeName,
						Constants.INSTANCE_TYPE);
		Billingrate billingrate = null;
		if (null != billingrateList && billingrateList.size() > 0) {
			billingrate = billingrateList.get(0);
		} else {
			return null;
		}
		Integer flavorDisk = billingrate.getHarddisk() + dataDisk;
		logger.info(String.format(
				"User %s request to CREATE FLAVOR, flavor disk is %s",
				userId.toString(), flavorDisk));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(),
				userId.toString());
		loller.info(
				LolLogUtil.CREATE_FLAVOR,
				String.format(
						"User %s request to CREATE FLAVOR by third-party, flavor disk is %s",
						userId.toString(), flavorDisk), rpcExtra);

		Integer typeId = null;
		try {
			typeId = scheduler.createFlavor(userId + "-flavor",
					billingrate.getMemory(), flavorDisk, billingrate.getCpu(),
					rpcExtra);
		} catch (RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_FLAVOR,
					rpcExtra);
			return new Flavor();
		}
		if (typeId == null) {
			throw new OperationFailedException("create flavor failed");
		}
		logger.info(String.format("FLAVOR created successfully, id is %s",
				typeId));
		VmInstanceType type = vmInstanceTypeProxy.getById(typeId);
		if (type == null) {
			throw new ItemNotFoundException("flavor does not exist");
		}
		// Flavor is all public
		return new Flavor(typeId, type.getName(), "public", type.getLocalGb(),
				type.getMemoryMb(), type.getVcpus());
	}

	// 创建云主机实例
	public Map<String, String> createInstance(ServerCreateReq serverCreateReq,
			Map<String, String> paramsMap, String requestId) throws Exception {
		// TODO Auto-generated method stub
		//由于下面会涉及到RPC调用，并将requestId作为RPC的transactionId，所以优先检查requestId
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap
				.get(Constants.APPKEY_ID));
		String userId = vmUser.getUserId() + "";
		logger.info(String.format("User %s request to CREATE SERVER %s",
				userId, serverCreateReq.name));
		// TODO: check auth

		// FIXME: image ref, flavor ref, security group: now 0
		// 目前防火墙失效，暂时不处理
		/*
		 * SecurityGroup sg = serverCreateReq.securityGroup; Integer sgId = 0;
		 * if (sg != null) { sgId = sg.id; }
		 */
		Integer sgId = 171;

		Integer zoneId = null;
		try {
			zoneId = Integer.valueOf(serverCreateReq.availabilityZone);
		} catch (Exception e) {
			zoneId = 0;
		}
		Integer aggregateId = null;
		try {
			aggregateId = Integer
					.valueOf(serverCreateReq.availabilityAggregate);
		} catch (Exception e) {
			throw new NumberFormatException ("transfer aggregatedId failed!");
		}

		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		loller.info(LolLogUtil.CREATE_VM, String.format(
				"User %s request to CREATE SERVER %s", userId,
				serverCreateReq.name), rpcExtra);
		
		String uuid = null;
		try {
			List<Integer> aggregateIds = new ArrayList<Integer>();
			if (aggregateId != null)
				aggregateIds.add(aggregateId);
			else {
				QueryObject<VmUser> query = new QueryObject<VmUser>();
				query.addFilterBean(new FilterBean<VmUser>("userId", Integer
						.valueOf(userId), FilterBeanType.EQUAL));
				@SuppressWarnings("unchecked")
				List<VmUser> vmUsers = (List<VmUser>) vmUserProxy
						.searchAll(query);
				if (vmUsers == null || vmUsers.size() == 0) {
					throw new ItemNotFoundException("user does not exist");
				}
				VmGroup vmGroup = vmGroupProxy.getById(vmUsers.get(0)
						.getGroupId());
				if (vmGroup == null) {
					throw new ItemNotFoundException("group does not exist");
				}
				aggregateIds = generator.groupToAcGroup(vmGroup).availableClusters;
			}
			// 检查当前用户是否有权在所选集群上申请云主机
			if (!AcGroupChecker.checkSelectedCluster(userId, aggregateIds)) {
				String message = "user " + userId
						+ " request to create vm in a cluster not allowed";
				logger.warn(message);
				loller.warn(LolLogUtil.CREATE_VM, message, rpcExtra);
				throw new OperationFailedException(
						"selected aggregate is not allowed for user");
			}
			try {
				uuid = scheduler.createVM(serverCreateReq.name, userId,
						serverCreateReq.imageRef,
						Integer.valueOf(serverCreateReq.flavorRef), sgId, zoneId,
						aggregateIds, paramsMap.get(Constants.PASSWORD), serverCreateReq.metadata, rpcExtra);
				if (paramsMap.containsKey(Constants.PASSWORD)) {
					logger.info("修改密码" + uuid);
				}
			} catch (RpcException e) {
				DealException.isRSTimeoutException(e, LolLogUtil.CREATE_VM,
						rpcExtra);
				logger.info("RequestId is null or is error!");
				resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
				resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to RPC exception.");
				resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
				return resultMap;
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.error("Create instance failed!");
			throw e;
		} catch (RpcException e) {
			logger.error("Create instance failed! Rpc exception!");
			throw e;
		}
		if (uuid == null) {
			String message = "user " + userId + " request to create vm failed";
			logger.warn(message);
			loller.warn(LolLogUtil.CREATE_VM, message, rpcExtra);
			throw new OperationFailedException("create instance failed");
		}
		// 由于接口中的name和description是选填字段，所以在云主机申请成功后，要根据接口中字段是否为空对创建云主机前设置的临时name和description进行修改
		if (paramsMap.get(Constants.INSTANCE_NAME) == null
				|| paramsMap.get(Constants.INSTANCE_NAME).equalsIgnoreCase("")) {
			VmInstanceType instanceType = vmInstanceTypeProxy.getById(Integer
					.parseInt(serverCreateReq.flavorRef));
			instanceType.setName(uuid.substring(0, Constants.MAX_NAME)
					+ "-flavor");
			vmInstanceTypeProxy.update(instanceType);
		}
		if (paramsMap.get(Constants.DESCRIPTION) == null
				|| paramsMap.get(Constants.DESCRIPTION).equalsIgnoreCase("")) {
			VmInstance instance = vmInstanceProxy.getByUuid(uuid, false, false,
					false, false, false, false, false, false);
			List<? extends VmInstanceMetadata> instanceMetadataList = vmInstanceMetadataProxy
					.getByVmInstanceId(instance.getId(), false);
			for (VmInstanceMetadata instanceMetadata : instanceMetadataList) {
				if (instanceMetadata.getKey().equals(
						ServerMetadata.DISPLAY_DESCRIPTION)) {
					instanceMetadata.setValue(uuid);
					vmInstanceMetadataProxy.update(instanceMetadata);
					break;
				}
			}
		}
		String message = "user " + userId
				+ " request to create vm successfully!";
		logger.warn(message);
		loller.warn(LolLogUtil.CREATE_VM, message, rpcExtra);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		resultMap.put(Constants.INSTANCE_ID, uuid);
		return resultMap;
	}

	public Map<String, String> startInstance(Map<String, String> paramsMap, String requestId)
			throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String userId = vmUserProxy.getByAppKeyId(
				paramsMap.get(Constants.APPKEY_ID)).getUserId()
				+ "";
		String instanceId = paramsMap.get(Constants.INSTANCE_ID);
		String message = String.format("User %s request to START SERVER %s",
				userId, instanceId);
		logger.info(message);
		RpcExtra rpcExtra = new RpcExtra(requestId, userId,
				instanceId);
		loller.info(LolLogUtil.START_VM, message, rpcExtra);
		try {
			scheduler.startVM(instanceId, rpcExtra);
		} catch (RpcException e) {
			loller.warn(LolLogUtil.START_VM, message + " failed!!!", rpcExtra);
			DealException
					.isRSTimeoutException(e, LolLogUtil.START_VM, rpcExtra);
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		logger.info(String.format("SERVER %s started successfully", instanceId));
		return resultMap;
	}

	public Map<String, String> stopInstance(Map<String, String> paramsMap, String requestId)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String userId = vmUserProxy.getByAppKeyId(
				paramsMap.get(Constants.APPKEY_ID)).getUserId() + "";
		String instanceId = paramsMap.get(Constants.INSTANCE_ID);
		String message = String.format("User %s request to STOP SERVER %s",	userId, instanceId);
		String operation = LolLogUtil.STOP_VM; // 记录当前的操作
		if ("true".equals(paramsMap.get(Constants.FORCE_STOP))) {
			message = String.format("User %s request to FORCE STOP SERVER %s",
					userId, instanceId);
			operation = LolLogUtil.FORCE_STOP_VM;
		}
		logger.info(message);
		RpcExtra rpcExtra = new RpcExtra(requestId, userId,
				instanceId);
		loller.info(operation, message, rpcExtra);
		try {
			if (LolLogUtil.FORCE_STOP_VM.equals(operation) ) {
				scheduler.forceStopVM(instanceId, rpcExtra);
			} else {
				scheduler.stopVM(instanceId, rpcExtra);
			}
		} catch (RpcException e) {
			loller.warn(operation, message + " failed!!!", rpcExtra);
			DealException.isRSTimeoutException(e, operation, rpcExtra);
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		logger.info(String.format("SERVER %s is stoped successfully", instanceId));
		return resultMap;
	}

	public Map<String, String> rebootInstance(Map<String, String> paramsMap, String requestId)
			throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String userId = vmUserProxy.getByAppKeyId(
				paramsMap.get(Constants.APPKEY_ID)).getUserId()
				+ "";
		String instanceId = paramsMap.get(Constants.INSTANCE_ID);
		String message = String.format("User %s request to REBOOT SERVER %s", userId, instanceId);
		logger.info(message);
		RpcExtra rpcExtra = new RpcExtra(requestId, userId,	instanceId);
		loller.info(LolLogUtil.REBOOT_DOMAIN, message, rpcExtra);
		try {
			scheduler.rebootVM(instanceId, rpcExtra);
		} catch (RpcException e) {
			loller.warn(LolLogUtil.START_VM, message + " failed!!!", rpcExtra);
			DealException
					.isRSTimeoutException(e, LolLogUtil.START_VM, rpcExtra);
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		logger.info(String.format("SERVER %s started successfully", instanceId));
		return resultMap;
	}

	@Override
	public Map<String, Object> describeInstanceStatus(
			Map<String, String> paramsMap, String requestId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String userId = vmUserProxy.getByAppKeyId(
				paramsMap.get(Constants.APPKEY_ID)).getUserId()	+ "";
		String message = String.format("User %s request to  DESCRIBE SERVER STATUS.",	userId);
		logger.info(message);
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		loller.info(LolLogUtil.GET_VM_STATE, message, rpcExtra);
		
		String logStr = String.format("User %s request to get SERVERS STATUS", userId);
		logger.info(logStr);

		QueryObject<VmInstance> query = new QueryObject<VmInstance>();
		query.addFilterBean(new FilterBean<VmInstance>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		try {
			//获取实例总数，用于接口返回
			Integer instanceTotal = (int) vmInstanceProxy.countSearch(query);
			@SuppressWarnings("unchecked")
			/**
			 * 首先根据页码和每页总数获取实例  (with metadata, network)
			 * 注：由于searchAll的page参数是从0开始的，所以此处应该对传进来的PageNumber参数进行减1处理
			 */
			List<VmInstance> instances = (List<VmInstance>) vmInstanceProxy.searchAll(query, false, false, false, 
				false, true, false, true, false, Integer.parseInt(paramsMap.get(Constants.PAGE_NUMBER))-1, 
				Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)));
			//new一个List列表，用于构造InstanceStatusSet对象
			List<InstanceStatusItem> instanceStatusList = new ArrayList<InstanceStatusItem>();
			logger.info(instances);
			if(null!=instances && instances.size()>0) {
				for(int index=0; index<instances.size(); index++) {
					VmInstance instance = instances.get(index);
					//获取Instance对应的metadata参数，从而获取Instance的description
					List<VmInstanceMetadata> vmInstanceMetadatas = instance.getVmInstanceMetadatas();
					String instanceDescription = "";
					if(null!=vmInstanceMetadatas) {
						for(VmInstanceMetadata metadata : vmInstanceMetadatas) {
							if(metadata.getKey().equalsIgnoreCase(Constants.METADATA_DISCRIPTION)) {
								instanceDescription = metadata.getValue();
								break;
							}
						}
					}
					//获取Instance对应的网卡参数，从而获取Instance的IP 
					List<VmVirtualInterface>  vmInterfaceList = instance.getVmVirtualInterfaces();
					String pubAddress = "";
					String priAddress = "";
					if(null!=vmInterfaceList) {
						for(VmVirtualInterface vmInterface : vmInterfaceList) {
							if(vmInterface.getNetworkType().equalsIgnoreCase(Constants.PUB_NETWORK_TYPE)){
								pubAddress = vmInterface.getAddress();
								continue;
							}
							priAddress = vmInterface.getAddress();
						}
					}
					//生成InstanceStatusItem对象，进而构造InstanceStatusSet对象，用于页面展示
					InstanceStatusItem instanceStatusItem = new InstanceStatusItem(instance.getUuid(),
							instance.getVmStatus().toString(), instance.getName(), instanceDescription,
							pubAddress, priAddress, instance.getLaunchedTime().toString());
					instanceStatusList.add(instanceStatusItem);
				}  //# end for
			}  //end if
			logger.info(String.format("DESCRIBE SERVER STATUS successfully"));
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
			resultMap.put(ResultConstants.TOTAL_COUNT, instanceTotal);
			resultMap.put(ResultConstants.PAGE_NUMBER, Integer.parseInt(paramsMap.get(Constants.PAGE_NUMBER)));
			resultMap.put(ResultConstants.PAGE_SIZE, Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)));
			resultMap.put(ResultConstants.INSTANCE_STATUS_SET, new InstanceStatusSet(instanceStatusList));
			return resultMap;
		} catch (RpcException e) {
			loller.warn(LolLogUtil.GET_VM_STATE, message + " failed!!!", rpcExtra);
			DealException
					.isRSTimeoutException(e, LolLogUtil.GET_VM_STATE, rpcExtra);
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
	}

	@Override
	public Map<String, String> deleteInstance(Map<String, String> paramsMap,
			String requestId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String userId = vmUserProxy.getByAppKeyId(
				paramsMap.get(Constants.APPKEY_ID)).getUserId() + "";
		String instanceId = paramsMap.get(Constants.INSTANCE_ID);
		String message = String.format("User %s request to STOP SERVER %s",	userId, instanceId);
		String operation = LolLogUtil.DELETE_VM; // 记录当前的操作
		if ("true".equals(paramsMap.get(Constants.FORCE_DELETE))) {
			message = String.format("User %s request to FORCE DELETE SERVER %s",
					userId, instanceId);
			operation = LolLogUtil.FORCE_DELETE_VM;
		}
		logger.info(message);
		RpcExtra rpcExtra = new RpcExtra(requestId, userId,
				instanceId);
		loller.info(operation, message, rpcExtra);
		try {
			if (LolLogUtil.FORCE_DELETE_VM.equals(operation) ) {
				scheduler.forceDeleteVM(instanceId, rpcExtra);
			} else {
				scheduler.terminateVM(instanceId, rpcExtra);
			}
		} catch (RpcException e) {
			loller.warn(operation, message + " failed!!!", rpcExtra);
			DealException.isRSTimeoutException(e, operation, rpcExtra);
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		logger.info(String.format("SERVER %s is stoped successfully", instanceId));
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> resetInstance(Map<String, String> paramsMap,
			String requestId) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		String serverId = paramsMap.get(Constants.INSTANCE_ID);
		int userId = vmuser.getUserId();
		String tenantId = userId+"";
		
		logger.info(String.format("User %s request to RESET SERVER %s", tenantId, serverId));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.REVERT_VOLUME, String.format("User %s request to RESET SERVER %s", tenantId, serverId),
				rpcExtra);
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
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String volumeUuid = vmVolumes.get(0).getVolumeUuid();
		logger.info("volumeUuid is " + volumeUuid);
		try {
			scheduler.revertVolume(volumeUuid, serverId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REVERT_VOLUME, rpcExtra);
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		logger.info("reset successfully");
		
		
		
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		return resultMap;
	}
	
	@Override
	public Map<String, String> isoDetach(Map<String, String> paramsMap,
			String requestId) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		String serverId = paramsMap.get(Constants.INSTANCE_ID);
		int userId = vmuser.getUserId();
		String tenantId = userId+"";
		
		logger.info(String.format("User %s request to detach ISO from SERVER %s", tenantId, serverId));

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.DETACH_ISO, String.format("User %s request to detach ISO from SERVER %s", tenantId, serverId),
				rpcExtra);
		try {
			scheduler.detachISO(serverId, rpcExtra);
		}catch(RpcException e) {
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		logger.info(String.format("ISO detached from SERVER %s successfully", serverId));
		
		
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		return resultMap;
	}

	@Override
	public Map<String, String> isoBoot(Map<String, String> paramsMap,
			String requestId) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		String serverId = paramsMap.get(Constants.INSTANCE_ID);
		int userId = vmuser.getUserId();
		String tenantId = userId+"";
		String imgRef = paramsMap.get(Constants.IMAGE_ID);
		
		logger.info(String.format("User %s request to BOOT SERVER %s FROM ISO %s",
				tenantId, serverId, imgRef));

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, serverId);
		loller.info(LolLogUtil.BOOT_FROM_ISO, String.format("User %s request to BOOT SERVER %s FROM ISO %s",
				tenantId, serverId, imgRef),
				rpcExtra);
		try {
			scheduler.bootFromISO(imgRef, serverId, rpcExtra);
		} catch(RpcException e) {
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}		
		logger.info(String.format("SERVER %s booted from iso successfully", serverId));
		
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		return resultMap;
	}
	
	@Override
	public Map<String, String> modifyInstanceAttribute(
			Map<String, String> paramsMap,Map<String, String> defaultParamsMap, String requestId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		Integer userId = vmuser.getUserId();
		String tenantId = userId.toString();
		String instanceId = paramsMap.get(Constants.INSTANCE_ID);
		VmInstance instance = vmInstanceProxy.getByUuid(instanceId, false, false, false, false, false, false, false, false);
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, instanceId);
		if (null != paramsMap.get(Constants.INSTANCE_NAME))
		{
			String instanceName = paramsMap.get(Constants.INSTANCE_NAME);
			logger.info(String.format("User %s request to update the name of the SERVER %s ", tenantId, instanceId));
			loller.info(LolLogUtil.UPDATE_SERVER_NAME, String.format("User %s request to UPDATE SERVER %s ", tenantId, instanceId), rpcExtra);
			instance.setName(instanceName);
			VmInstanceType instanceType = vmInstanceTypeProxy.getById(instance.getInstanceTypeId());
			instanceType.setName(instanceName + "-flavor");
			instance.setVmInstanceType(instanceType);
			vmInstanceProxy.update(instance);
			logger.info(String.format("User %s request to update the name of the SERVER %s SUCCESS! ", tenantId, instanceId));
			loller.info(LolLogUtil.UPDATE_SERVER_NAME, String.format("User %s request to UPDATE SERVER %s SUCCESS!", tenantId, instanceId), rpcExtra);
		}
		if (null != paramsMap.get(Constants.DESCRIPTION))
		{
			String description = paramsMap.get(Constants.DESCRIPTION);
			String key = Constants.DISPLAY_DESCRIPTION;
			List<VmInstanceMetadata> vmMetadataList = (List<VmInstanceMetadata>) vmInstanceMetadataProxy.getByVmInstanceId(instance.getId(), false);
			if(vmMetadataList == null) {
				logger.error(String.format("User %s request to update the name of the SERVER %s failed!! Get vminstance metadata failed!", tenantId, instanceId));
				loller.error(LolLogUtil.UPDATE_SERVER_DESC, String.format("User %s request to UPDATE SERVER %s failed! Get vminstance metadata failed!", tenantId, instanceId), rpcExtra);
				resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
				resultMap.put(Constants.ERRORMESSAGE,"vmMetadataList is null.");
				resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
				return resultMap;
			}
			VmInstanceMetadata newMetadata = null;
			for(VmInstanceMetadata metadata : vmMetadataList) {//找出和key值相同的那一个条目
				if(metadata.getKey().equalsIgnoreCase(key)) {
					newMetadata = metadata;
					break;
				}
			}
			logger.info("修改描述");
			logger.info(String.format("User %s request to update the desc of the SERVER %s ! ", tenantId, instanceId));
			newMetadata.setValue(description);
			vmInstanceMetadataProxy.update(newMetadata);
			logger.info(String.format("User %s request to update the desc of the SERVER %s SUCCESS! ", tenantId, instanceId));
			loller.info(LolLogUtil.UPDATE_SERVER_DESC, String.format("User %s request to UPDATE SERVER %s SUCCESS!", tenantId, instanceId), rpcExtra);
		}
		if (null != paramsMap.get(Constants.PASSWORD))
		{
			String newPasswd = paramsMap.get(Constants.PASSWORD);
			String name = "";
			if(null != paramsMap.get(Constants.HOST_NAME))
				name = paramsMap.get(Constants.HOST_NAME);
			else
				name = defaultParamsMap.get(Constants.HOST_NAME);
			scheduler.modVmPasswd(instanceId, name, newPasswd, null, rpcExtra);
		}
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		return resultMap;
	}

	@Override
	public Map<String, String> modifyInstanceResource(
			Map<String, String> paramsMap, String requestId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		Integer uid = vmuser.getUserId();
		String userId = uid.toString();
		String serverId = paramsMap.get(Constants.INSTANCE_ID);
		VmInstance instance = vmInstanceProxy.getByUuid(serverId, false, true, false, true, true, true, false, false);
		int oldCpuNum = instance.getVmInstanceType().getVcpus();
		int oldMemNum = instance.getVmInstanceType().getMemoryMb();
		int oldHdNum = instance.getVmInstanceType().getLocalGb();
		int oldBandNum = -1;
		String serverName = instance.getName();
		List<VmInstanceMetadata> bandNumlist = instance.getVmInstanceMetadatas();
		for(VmInstanceMetadata md : bandNumlist) {
			if(md.getKey().equals(Constants.PUBLIC_BANDWIDTH)) {
				oldBandNum = Integer.valueOf(md.getValue());
				break;
			}
		}
		if(-1 == oldBandNum)
		{
			logger.info("带宽获取失败！");
    		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"This instance do not have band");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
    		return resultMap;
		}
		int cpuNum = oldCpuNum ;
		int memNum = oldMemNum ;
		int bandNum = oldBandNum ;
		if (null != paramsMap.get(Constants.CPU_NUM))
			cpuNum = Integer.valueOf(paramsMap.get(Constants.CPU_NUM));
		if (null != paramsMap.get(Constants.RAM_SIZE))
			memNum = Integer.valueOf(paramsMap.get(Constants.RAM_SIZE));		
		if (null != paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT))
			bandNum = Integer.valueOf(paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT));	
		if( (cpuNum != oldCpuNum) || (memNum != oldMemNum) ){
			//创建flavor
			Flavor flavor = new Flavor(null, serverName+"-flavor", userId, memNum, oldHdNum, cpuNum);
			logger.info(String.format("User %s request to CREATE FLAVOR, flavor disk is %s", userId, flavor.disk));
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), userId);
			loller.info(LolLogUtil.CREATE_FLAVOR, 
					String.format("User %s request to CREATE FLAVOR, flavor disk is %s", userId, flavor.disk),
					rpcExtra);
			Integer id = null;
			id = scheduler.createFlavor(flavor.name, flavor.ram, flavor.disk, flavor.vcpus, rpcExtra);
			if (id == null)
				throw new OperationFailedException("create flavor failed");
			logger.info(String.format("FLAVOR created successfully, id is %s", id));
			logger.info("创建flavor成功");
			logger.info(String.format("User %s request to REBUILD SERVER %s ", userId, serverId));
			RpcExtra rpcExtra2 = new RpcExtra(RpcExtra.genTranctionId(), userId, serverId);
			Integer flavorId = id;
			logger.info(String.format("Server flavor change req, serverId : %s, new flavor: %d ",
					serverId, flavorId));
			loller.info(LolLogUtil.REBUILD_VM, String.format("User %s request to REBUILD SERVER %s, " +
					" new flavor:%d", userId, serverId, flavorId), rpcExtra2);		
			scheduler.rebuildVM(serverId, Integer.parseInt(userId), flavorId, null, rpcExtra2);
			logger.info(String.format("SERVER %s rebuilt successfully", serverId));						
        }
		if(bandNum!=oldBandNum)
		{
			logger.info("发送修改网络到后台。。。"+uid+","+serverId);
		//	serverClient.updateMetadataItem(uid.toString(), serverId, "maxBandwidth", bandNum.toString(),true);
			logger.info(String.format("User %s request to set the bandwidth of the SERVER %s ! ", userId, serverId));
			Map<String,String> metadatas = new HashMap<String, String>();
			metadatas.put("maxBandwidth", bandNum+"");
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), userId, serverId);
			int returnBand = scheduler.setVmMaxBandwidth(serverId, metadatas,true, rpcExtra);
			if(returnBand==-1) {
				logger.info(String.format("User %s request to set the bandwidth of the SERVER %s Failed! ", userId, serverId));
				loller.info(LolLogUtil.UPDATE_BANDWIDTH, String.format("User %s request to SET SERVER %s bandwidth Failed!", userId, serverId), rpcExtra);
	    		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
				resultMap.put(Constants.ERRORMESSAGE,"set the bandwidth failed!");
				resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
	    		return resultMap;
			}
			logger.info(String.format("User %s request to set the bandwidth of the SERVER %s SUCCESS! bandwidth is %s ", userId, serverId, returnBand+""));
			loller.info(LolLogUtil.UPDATE_BANDWIDTH, String.format("User %s request to SET SERVER %s bandwidth SUCCESS!", userId, serverId), rpcExtra);
		}
		VmHdEndtimeManager vmHdEndtimeManager = new VmHdEndtimeManager();
		vmHdEndtimeManager.updateVmHdEndtimeType(uid, serverId, VmHdEndtime.TYPE_VM);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		return resultMap;
	}

	@Override
	public Map<String, String> modifyInstanceSecurityGroup(
			Map<String, String> paramsMap, String requestId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==requestId || requestId.length()<1) {
			logger.info("RequestId is null or is error!");
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		String uuid = paramsMap.get(Constants.INSTANCE_ID);
		int userId = vmuser.getUserId();
		String tenantId = userId+"";
		String securityGroupId = paramsMap.get(Constants.SECURITY_GROUP_ID);
		
		logger.info(String.format("User %s request to REBUILD SERVER %s ", tenantId, uuid));
		Integer sgId = null;
		VmSecurityGroup  group = vmSecurityGroupProxy.getByUuid(securityGroupId, false);
		sgId = group.getId();
		int groupuserid = group.getUserId();
		if(groupuserid!=userId)
		{
			logger.info("防火墙规则权限不足。");
    		resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"This is not your firewall！");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
    		return resultMap;
		}
		logger.info("Security Group Updated to " + sgId);
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, uuid);
		logger.info(String.format("Security Group change req, serverId:%s, sgId:%s",
				uuid, sgId));
		loller.info(LolLogUtil.REBUILD_VM, String.format("User %s request to REBUILD SERVER %s, " +
				" sgId:%s",	tenantId, uuid, sgId), rpcExtra);
		scheduler.rebuildVM(uuid, userId, null, sgId, rpcExtra);
		logger.info(String.format("SERVER %s rebuilt successfully", uuid));
		logger.info(String.format("SERVER %s modify instance security  group successfully", uuid));
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
		return resultMap;
	}
}
