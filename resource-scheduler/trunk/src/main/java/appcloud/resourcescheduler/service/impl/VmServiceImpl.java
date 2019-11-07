/**
 * File: VmServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.*;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmInstance.TaskStatusEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeTypeEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.proxy.*;
import appcloud.common.service.NetworkProviderService;
import appcloud.common.service.VMSchedulerService;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.transaction.ContextHandler;
import appcloud.common.transaction.TTask;
import appcloud.common.transaction.Transaction;
import appcloud.common.util.AlertUtil;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.UuidUtil;
import appcloud.common.util.query.Filter.FilterLogic;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.resourcescheduler.ResourceScheduler;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.resourcescheduler.common.Constants;
import appcloud.resourcescheduler.service.NetworkService;
import appcloud.resourcescheduler.service.VmService;
import appcloud.resourcescheduler.transaction.rollback.resource.*;
import appcloud.resourcescheduler.vmdisk.VmDiskManager;
import appcloud.rpc.tools.RpcException;
import appcloud.rpc.tools.RpcTimeoutException;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weed
 * 
 */
public class VmServiceImpl implements VmService {
	private static Logger logger = Logger.getLogger(VmServiceImpl.class);
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = ResourceScheduler.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.RESOURCE_SCHEDULER, VmServiceImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	private static VMSchedulerService vmScheduler = (VMSchedulerService) ConnectionManager
			.getInstance().getAMQPService(VMSchedulerService.class,
					RoutingKeys.VM_SCHEDULER);
	private static VolumeSchedulerService volumeScheduler = (VolumeSchedulerService) ConnectionManager
			.getInstance().getAMQPService(VolumeSchedulerService.class,
					RoutingKeys.VOLUME_SCHEDULER);
	private static NetworkProviderService networkProvider = (NetworkProviderService) ConnectionManager
			.getInstance().getAMQPService(NetworkProviderService.class,
					RoutingKeys.NETWORK_SCHEDULER);
	private static VmZoneProxy vmZoneProxy = (VmZoneProxy) ConnectionManager
			.getInstance().getDBProxy(VmZoneProxy.class);
	private static ClusterProxy vmClusterProxy = (ClusterProxy) ConnectionManager
			.getInstance().getDBProxy(ClusterProxy.class);
	private static VmImageProxy vmImageProxy = (VmImageProxy) ConnectionManager
			.getInstance().getDBProxy(VmImageProxy.class);
	private static VmInstanceProxy vmInstanceProxy = (VmInstanceProxy) ConnectionManager
			.getInstance().getDBProxy(VmInstanceProxy.class);
	private static VmInstanceTypeProxy vmInstanceTypeProxy = (VmInstanceTypeProxy) ConnectionManager
			.getInstance().getDBProxy(VmInstanceTypeProxy.class);
	private static VmInstanceMetadataProxy vmInstanceMetadataProxy = (VmInstanceMetadataProxy) ConnectionManager
			.getInstance().getDBProxy(VmInstanceMetadataProxy.class);
	private static VmVirtualInterfaceProxy vmVirtualInterfaceProxy = (VmVirtualInterfaceProxy) ConnectionManager
			.getInstance().getDBProxy(VmVirtualInterfaceProxy.class);
	private static VmSecurityGroupProxy vmSecurityGroupProxy = (VmSecurityGroupProxy) ConnectionManager
			.getInstance().getDBProxy(VmSecurityGroupProxy.class);
	private static VmVolumeProxy vmVolumeProxy = (VmVolumeProxy) ConnectionManager
			.getInstance().getDBProxy(VmVolumeProxy.class);
	private static VmImageBackProxy vmImageBackProxy = (VmImageBackProxy)ConnectionManager
			.getInstance().getDBProxy(VmImageBackProxy.class);
	private static HostProxy hostProxy = (HostProxy) ConnectionManager.getInstance().getDBProxy(HostProxy.class);


	private List<Integer> checkSelectedClusters(List<Integer> selectedClusters, RpcExtra rpcExtra) throws RpcException {
		List<Integer> validClusterIds = new ArrayList<Integer>();
		if(selectedClusters != null || selectedClusters.size() != 0) {
			for(Integer clusterId : selectedClusters) {
				try {
					Cluster clusterTmp = vmClusterProxy.getById(clusterId, false, false, false);
					if(clusterTmp != null) {
						validClusterIds.add(clusterId);
					} 
				} catch(Exception e) {
					logger.error("connect to db-proxy failed when get cluster");
				}
			}
		}
		return validClusterIds;
	}
	
/*	public static void main(String[] args) throws RpcException {
		VmServiceImpl ppp = new VmServiceImpl();
		List<String> list = ppp.getMacAddress(2, new RpcExtra());
		System.out.println(list.size());
	}*/
	/**
	 * 获取MAC地址
	 */
	private List<String> getMacAddress(Integer clusterId, RpcExtra rpcExtra) throws RpcException {
		if(clusterId == null || clusterId<=0) {
			logger.error("get mac address failed, cluster id :" + clusterId);
			return null;
		}
		logger.info("start to get mac address, cluster id : " + clusterId);
		List<String> macAddressList = new ArrayList<String>();
		try {
			macAddressList = networkProvider.getNewMacAddress(clusterId, rpcExtra);
			if(macAddressList != null && macAddressList.size() == 2) {
				logger.info("Get Mac address succeed, mac address:" + macAddressList);
				return macAddressList;
			}
			return null;
		} catch(RpcException e) {
			if(e instanceof RpcTimeoutException) {
				logger.error("no responce from network-provider");
				loller.error(LolLogUtil.GET_NEW_MAC_ADDRESS, "no responce from network-provider", rpcExtra);
			} else {
				logger.error(e.getMessage(), e);
				loller.error(LolLogUtil.GET_NEW_MAC_ADDRESS, "get MAC address failed : " + e.getMessage(), rpcExtra);
			}
			return null;
		}
	}
	/*
	 * 获取IP地址
	 */
	private Map<String, NetworkAddress> getIPAddresses(String bandWidth, Integer clusterId, List<String> macAddressList, RpcExtra rpcExtra) throws RpcException{
		int maxBandWidth = 0;
		try {
			maxBandWidth = Integer.valueOf(bandWidth);
		} catch(NumberFormatException e) {
			logger.error("format bandwidth failed");
		}
		
		Map<String, NetworkAddress> ips = new HashMap<String, NetworkAddress>();
		NetworkAddress publicIpAddr = null;
		NetworkAddress privateIpAddr = null;
		//if mac address is null or it only one, return 
		if(macAddressList == null || macAddressList.size()<2) {
			logger.info("Mac address is null or only one");
			return ips;
		}
		logger.info("Mac address : " + macAddressList);
		String publicMacAddr = macAddressList.get(0);
		String privateMacAddr = macAddressList.get(1);
		String errorType = "";
		try {
			errorType = "public";
			if(maxBandWidth > 0) {
				publicIpAddr = networkProvider.getNewPublicIpAddress(clusterId, publicMacAddr, rpcExtra);
				if(publicIpAddr != null) {
					logger.info("publicIpAddr: " + publicIpAddr);
					ips.put("publicIpAddr", publicIpAddr);
				} else {
					ips.put("publicIpAddr", null);
					throw new RpcException("Get public ip failed in cluster " + clusterId);
				}
			} else {
				ips.put("publicIpAddr", new NetworkAddress(publicMacAddr,null));
			}
			
			errorType = "private";
			privateIpAddr = networkProvider.getNewPrivateIpAddress(clusterId, privateMacAddr, rpcExtra);
			if(privateIpAddr != null) {
				logger.info("privateIpAddr: " + privateIpAddr);
				ips.put("privateIpAddr", privateIpAddr);
			} else {
				ips.put("privateIpAddr", null);
				throw new RpcException("Get private ip failed in cluster " + clusterId);
			}
		} catch(RpcException e) {
			if(e instanceof RpcTimeoutException) {
				logger.error("no responce from network-provider");
				if(errorType.equals("public"))
					loller.error(LolLogUtil.GET_NEW_PUBLIC_IPADDRESS, "no responce from network-provider", rpcExtra);
				if(errorType.equals("private"))
					loller.error(LolLogUtil.GET_NEW_PRIVATE_IPADDRESS, "no responce from network-provider", rpcExtra);
			} else {
				logger.error(e.getMessage(), e);
				if(errorType.equals("public"))
					loller.error(LolLogUtil.GET_NEW_PUBLIC_IPADDRESS, "get IP address failed : " + e.getMessage(), rpcExtra);
				if(errorType.equals("private"))
					loller.error(LolLogUtil.GET_NEW_PRIVATE_IPADDRESS, "get IP address failed : " + e.getMessage(), rpcExtra);
			}
		}
	
		return ips;
	}
	
	/*
	 * 创建硬盘
	 */
	private List<VmVolume> createVolumes(String vmUuid, Integer userId,  String imageUUID, 
			Integer avalibilityZoneId, Host host, Integer localGb, Integer imageSizeGb, 
			ContextHandler context, RpcExtra rpcExtra) throws RpcException {
		VmZone zone = null;
		try {
			zone = vmZoneProxy.getById(avalibilityZoneId);
			if (zone == null) {
				throw new Exception("No zone with uuid " + avalibilityZoneId);
			} else {
				logger.info("zone: " + zone);
			}
		} catch(Exception e) {
			logger.error("Create volumes failed ," + e.getMessage());
			loller.error(LolLogUtil.CREATE_VOLUME, "Create volumes failed ," + e.getMessage(), rpcExtra);
			return null;
		}
		
		List<VmVolume> volumes = null;
		try {
			volumes = VmDiskManager.getInstance().createDisk(vmUuid,
					userId, imageUUID, zone, host, localGb, imageSizeGb, context, rpcExtra);
			if (volumes != null) {
				logger.info("volumes: " + volumes);
				return volumes;
			} else {
				throw new RpcException("return null when creating volumes ");
			}
		}catch (Exception e) {
			if(e instanceof RpcTimeoutException) {
				logger.error("no response from volume-scheduler");
				loller.error(LolLogUtil.CREATE_VOLUME, "no response from volume-scheduler", rpcExtra);
			} else {
				logger.error("Create volumes failed ," + e.getMessage(), e);
				loller.error(LolLogUtil.CREATE_VOLUME, "Create volumes failed ," + e.getMessage(), rpcExtra);
			}
			return null;
		}
	}
	
	/*
	 * 获取镜像，在不指定镜像情况下，新建一个镜像，并将其大小指定为硬盘大小
	 */
	private VmImage getImage(String imageUuid, VmInstanceType instanceType, RpcExtra rpcExtra) throws RpcException{
		try {
			VmImage image = null;
			if (imageUuid == null || imageUuid.isEmpty()) {
				logger.info("Image: " + "Empty image with size " + instanceType.getLocalGb() + "GB");
				image = new VmImage();
				image.setSize(instanceType.getLocalGb());
			} else {
				image = vmImageProxy.getByUuid(imageUuid);
				if(image == null) {
					throw new RpcException("Return null when get image with id " + imageUuid);
				}else {
					logger.info("image: " + image);
				}
			}
			return image;
			
		}catch(Exception e) {
			logger.error("get image failed, " + e.getMessage(), e);
			loller.error(LolLogUtil.CREATE_VM, "get image failed, " + e.getMessage(), rpcExtra);
			return null;
		}
	}
	
	/*
	 * 选择符合配要求的host
	 */
	private List<Host> selectHosts(String instanceUUID, Integer instanceTypeID, 
			Integer availabilityZoneID, Integer clusterId, RpcExtra rpcExtra) throws RpcException {
		List<Host> hosts = null;
		try {
			hosts = vmScheduler.selectHost(instanceUUID, instanceTypeID, availabilityZoneID, clusterId, rpcExtra);

			logger.info("hosts: " + hosts);
			if (hosts == null) {
				throw new RpcException("return null when select hosts");
			}
			return hosts;
		}catch (RpcException e) {
			if(e instanceof RpcTimeoutException) {
				logger.error("no response from vm-scheduler");
				loller.error(LolLogUtil.CREATE_VM, "no response from vm-scheduler", rpcExtra);
			} else {
				logger.error("get hosts failed, " + e.getMessage(), e);
				loller.error(LolLogUtil.CREATE_VM, "get hosts failed, " + e.getMessage(), rpcExtra);
			}
			return null;
		}		
	}

	private List<Host> selectSpecificHost(String hostUuid, RpcExtra rpcExtra) throws RpcException{
		List<Host> hosts = null;
		try {
			hosts = vmScheduler.selectSpecificHost(hostUuid, rpcExtra);
			logger.info("hosts: " + hosts);
			if (hosts == null) {
				throw new RpcException("return null when select hosts");
			}
			return hosts;
		}catch (RpcException e) {
			if(e instanceof RpcTimeoutException) {
				logger.error("no response from vm-scheduler");
				loller.error(LolLogUtil.CREATE_VM, "no response from vm-scheduler", rpcExtra);
			} else {
				logger.error("get hosts failed, " + e.getMessage(), e);
				loller.error(LolLogUtil.CREATE_VM, "get hosts failed, " + e.getMessage(), rpcExtra);
			}
			return null;
		}
	}
	
	/*
	 * 创建一个线程，该线程用于测试指定的host能否创建虚拟
	 */
	private Transaction tryHostTransaction(final String instanceUuid, final Integer flavorId,
			final Map<String, String> metadata, final String newPassword,final VmImage image, final Host host, final RpcExtra rpcExtra) throws Exception{
		Transaction tryHostTransaction = new Transaction(new TTask(){
			boolean timeout = false;
			@Override
			public boolean preProcess() throws Exception {
				return true;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void process() throws Exception {
				if(timeout) {
					return;
				}
				
				logger.info("create vm in host " + host.getIp());
				VmInstance instance = vmInstanceProxy.getByUuid(
						instanceUuid, false, false, false, false, false,
						false, false, false);
				
				instance.setHostUuid(host.getUuid());
				instance.setAvailabilityClusterId(host.getClusterId());
				vmInstanceProxy.update(instance);
				logger.info("instance:" + instance);
				
				// Stage 4 Create Volume
				VmInstanceType instanceType = vmInstanceTypeProxy.getById(flavorId);
				if (instanceType == null) {
					return;
				} 
				List<VmVolume> volumes = createVolumes(instanceUuid, instance.getUserId(),
								instance.getImageUuid(), host.getAvailabilityZoneId(), host, 
								instanceType.getLocalGb(), image.getSize(), 
								context, rpcExtra);

				if (volumes == null || volumes.size() == 0) {

					return;
				} else {
					if (newPassword != null)
						volumeScheduler.modVmPasswd(volumes.get(0).getVolumeUuid(), "root", newPassword, null, rpcExtra);
				}
						
				List<VmVirtualInterface> vifs = (List<VmVirtualInterface>) vmVirtualInterfaceProxy
						.getByInstanceUuid(instance.getUuid());
				logger.info("vifs: " + vifs);

				// Stage 5 Save Metadatas
				List<VmInstanceMetadata> instanceMetadatas = null;
				context.addRollbackResource(new VmMetadataResource(instance.getId()));
				for (String key : metadata.keySet()) {
					vmInstanceMetadataProxy.save(new VmInstanceMetadata(null,
									instance.getId(), key, metadata.get(key)));
				}

				instanceMetadatas = (List<VmInstanceMetadata>) vmInstanceMetadataProxy
						.getByVmInstanceId(instance.getId(), false);
				
				// Stage 8 Save Metadatas
				VmSecurityGroup vmSecurityGroup = vmSecurityGroupProxy.getById(instance.getSecurityGroupId(), false);
				
				// Stage 9 Create VM
				try {	
					vmScheduler.createVM(instanceUuid, instanceType.getFlavorUuid(),
							instanceMetadatas, volumes,
							vmSecurityGroup, vifs, rpcExtra);
					context.complete();
				}catch (Exception e) {
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from volume-scheduler");
						loller.error(LolLogUtil.CREATE_VM, "no response from volume-scheduler", rpcExtra);
						throw new RpcTimeoutException("timeout Exception");
					} else {
						logger.error("create vm in host " + host.getIp() + " failed " + e.getMessage(), e);
						loller.error(LolLogUtil.CREATE_VM, "create vm in host " + host.getIp() + " failed " + e.getMessage(), rpcExtra);
						throw new RpcException("create vm in one of the hosts failed");
					}
				}
			}

			@Override
			public void postProcess() throws Exception {
				
			}

			@Override
			public void onError() {
				timeout = true;
				VmInstance instance;
				try {
					instance = vmInstanceProxy.getByUuid(
							instanceUuid, false, false, false, false, false,
							false, false, false);
					instance.setVmStatus(VmStatusEnum.ERROR);
					vmInstanceProxy.update(instance);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

			@Override
			public void onCompleted() {
			
			}
		});
		return tryHostTransaction;
	}

	private Transaction tryCreateVmTransaction(final String instanceUuid, final Integer flavorId,
										   final Map<String, String> metadata, final String newPassword,final VmImageBack image, final Host host, final RpcExtra rpcExtra) throws Exception{
		Transaction tryHostTransaction = new Transaction(new TTask(){
			boolean timeout = false;
			@Override
			public boolean preProcess() throws Exception {
				return true;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void process() throws Exception {
				if(timeout) {
					return;
				}

				logger.info("create vm in host " + host.getIp());
				VmInstance instance = vmInstanceProxy.getByUuid(
						instanceUuid, false, false, false, false, false,
						false, false, false);

				instance.setHostUuid(host.getUuid());
				instance.setAvailabilityClusterId(host.getClusterId());
				vmInstanceProxy.update(instance);
				logger.info("instance:" + instance);

				// Stage 4 Create Volume
				VmInstanceType instanceType = vmInstanceTypeProxy.getById(flavorId);
				if (instanceType == null) {
					return;
				}

				VmZone zone = vmZoneProxy.getById(host.getAvailabilityZoneId());

				List<VmVolume> volumes =new ArrayList<VmVolume>();

				try {
					VmVolume tmpVolume0 = volumeScheduler.defineVolumeOnImageBack(VmVolumeUsageTypeEnum.SYSTEM, instance.getUserId(), image.getVolumeSize(), zone, image.getVolumeUuid(), host, rpcExtra);
					tmpVolume0 = volumeScheduler.createVolumeOnImageBack(tmpVolume0.getVolumeUuid(),image, rpcExtra);
					if (tmpVolume0 != null) {
						try {
							VmVolume savedVolume = vmVolumeProxy.getByUUID(tmpVolume0.getVolumeUuid(), false, false, false, false);
							savedVolume.setImageUuid("only-one");
							vmVolumeProxy.update(savedVolume);

							tmpVolume0.setImageUuid("only-one");
						} catch (Exception e) {
							e.printStackTrace();
						}

						volumes.add(tmpVolume0);
						context.addRollbackResource(new VmVolumeResource(tmpVolume0));
					} else {
						throw new RpcException("return null when creating volumes ");
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
					throw new RpcException("return null when creating volumes ");
				}
//				List<VmVolume> volumes = createVolumes(instanceUuid, instance.getUserId(),
//						instance.getImageUuid(), host.getAvailabilityZoneId(), host,
//						instanceType.getLocalGb(), image.getSize(),
//						context, rpcExtra);
				if (volumes == null || volumes.size() == 0) {
					return;
				} else {
					if (newPassword != null)
						volumeScheduler.modVmPasswd(volumes.get(0).getVolumeUuid(), "root", newPassword, null, rpcExtra);
				}

				List<VmVirtualInterface> vifs = (List<VmVirtualInterface>) vmVirtualInterfaceProxy
						.getByInstanceUuid(instance.getUuid());
				logger.info("vifs: " + vifs);

				// Stage 5 Save Metadatas
				List<VmInstanceMetadata> instanceMetadatas = null;
				context.addRollbackResource(new VmMetadataResource(instance.getId()));
				for (String key : metadata.keySet()) {
					vmInstanceMetadataProxy.save(new VmInstanceMetadata(null, instance.getId(), key, metadata.get(key)));
				}

				instanceMetadatas = (List<VmInstanceMetadata>) vmInstanceMetadataProxy.getByVmInstanceId(instance.getId(), false);

				// Stage 8 Save Metadatas
				VmSecurityGroup vmSecurityGroup = vmSecurityGroupProxy.getById(instance.getSecurityGroupId(), false);

				// Stage 9 Create VM
				try {
					vmScheduler.createVM(instanceUuid, instanceType.getFlavorUuid(),
							instanceMetadatas, volumes,
							vmSecurityGroup, vifs, rpcExtra);
					context.complete();
				}catch (Exception e) {
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from volume-scheduler");
						loller.error(LolLogUtil.CREATE_VM, "no response from volume-scheduler", rpcExtra);
						throw new RpcTimeoutException("timeout Exception");
					} else {
						logger.error("create vm in host " + host.getIp() + " failed " + e.getMessage(), e);
						loller.error(LolLogUtil.CREATE_VM, "create vm in host " + host.getIp() + " failed " + e.getMessage(), rpcExtra);
						throw new RpcException("create vm in one of the hosts failed");
					}
				}
			}

			@Override
			public void postProcess() throws Exception {

			}

			@Override
			public void onError() {
				timeout = true;
				VmInstance instance;
				try {
					instance = vmInstanceProxy.getByUuid(
							instanceUuid, false, false, false, false, false,
							false, false, false);
					instance.setVmStatus(VmStatusEnum.ERROR);
					vmInstanceProxy.update(instance);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onCompleted() {

			}
		});
		return tryHostTransaction;
	}

	@Override
	public String createVM(final String name, final String userId,
			String imageUuid, final Integer flavorId, final Integer sgId,
			final Integer avalibilityZoneId, List<Integer> clusterIds,final String hostUuid, final String newPassword,
			final Map<String, String> metadata, final RpcExtra rpcExtra) throws RpcException {
		
		String paramInfos = "createVM: " + name + ", " + userId + ", "
				+ imageUuid + ", " + flavorId + ", " + sgId + ", "
				+ avalibilityZoneId + ", " + metadata + " ";
		logger.debug(paramInfos);
		
		if (name == null || userId == null || imageUuid == null || flavorId == null || sgId == null
				|| avalibilityZoneId == null){
			logger.error("Failed to create VM, parameter error");
			loller.error(LolLogUtil.CREATE_VM, "Failed to create VM, parameter error", rpcExtra);
			return null;
		}
		
		//检查每个指定clusterId的合法性
		final List<Integer> validClusterIds = checkSelectedClusters(clusterIds, rpcExtra);
		if(validClusterIds == null || validClusterIds.size() == 0) {
			logger.error("Selected clusters are not available");
			loller.error(LolLogUtil.CREATE_VM, "Selected clusters are not available", rpcExtra);
			return null;
		}
	
		// Stage 0 创建虚拟机的UUID
		final String uuid = UuidUtil.getRandomUuid();
		try {
			long startTime = System.currentTimeMillis();
			
			//获取配置信息
			final VmInstanceType instanceType = vmInstanceTypeProxy.getById(flavorId);
			if (instanceType == null) {
				return null;
			} else {
				logger.info("instanceType: " + flavorId);
			}
			
			// 获取镜像
			final VmImage image = getImage(imageUuid, instanceType, rpcExtra);
			if(image == null) {
				return null;
			} 

			//将虚拟机信息写入数据库
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			
			vmInstanceProxy.save(new VmInstance(null, uuid, name, Integer
					.parseInt(userId), null, null, avalibilityZoneId,
					timeStamp, timeStamp, timeStamp, 10, VmStatusEnum.BUILDING,
					TaskStatusEnum.READY, imageUuid, instanceType.getId(),
					null, sgId, image.getOsMode(), image.getOsArch(), image
							.getOsType(), null, null,0));
			logger.debug("cost: " + (System.currentTimeMillis() - startTime));
			
			Transaction createTransaction = new Transaction(new TTask(){
				@Override
				public void process() throws Exception {
					context.addRollbackResource(new VMInstanceResource(uuid));
					for(Integer clusterId : validClusterIds) {
						VmInstance instance = vmInstanceProxy.getByUuid(
								uuid, false, false, false, false, false,
								false, false, false);
						if (instance == null) {
							logger.error("No instance with UUID " + uuid);
							loller.error(LolLogUtil.CREATE_VM, "No instance with UUID " + uuid, rpcExtra);
							throw new Exception("return null");
						} else {
							logger.info("instance: " + instance);
						}

						// Stage 1 Select available host
						List<Host> hosts = null;
						if(hostUuid!=null){
							hosts = selectSpecificHost(hostUuid,rpcExtra);
							logger.info("create instance on specific host : " + hostUuid);
						} else {
							hosts = selectHosts(uuid, flavorId, avalibilityZoneId, clusterId, rpcExtra);
						}
						if(hosts == null) {
							throw new Exception("return null");
						}
						// Stage 6 Create network
						//get mac address
						List<String> macAddressList = getMacAddress(clusterId, rpcExtra);
						String bandWidthStr = metadata.get("maxBandwidth");
						Map<String, NetworkAddress> ips = getIPAddresses(bandWidthStr, clusterId, macAddressList, rpcExtra);
						if(Integer.valueOf(bandWidthStr) > 0) {
							if(ips.get("publicIpAddr") != null && ips.get("privateIpAddr") != null) {
								context.addRollbackResource(new PublicIpAddrResource(clusterId, ips.get("publicIpAddr").getIp()));
								context.addRollbackResource(new PrivateIpAddrResource(clusterId, ips.get("privateIpAddr").getIp()));
							} else {
								validClusterIds.remove(clusterId);
								continue;
							}
						} else {
							if(ips.get("privateIpAddr") != null) {
								context.addRollbackResource(new PrivateIpAddrResource(clusterId, ips.get("privateIpAddr").getIp()));
							} else {
								validClusterIds.remove(clusterId);
								continue;
							}
						}
						// Stage 7 Save VirtualInterface
						context.addRollbackResource(new VmVirtualInterfaceResource(instance.getUuid()));

						vmVirtualInterfaceProxy.save(new VmVirtualInterface(null, instance.getUuid(), 
								ips.get("publicIpAddr").getIp(), ips.get("publicIpAddr").getMac(), "public", null));
						vmVirtualInterfaceProxy.save(new VmVirtualInterface(null, instance.getUuid(), 
								ips.get("privateIpAddr").getIp(), ips.get("privateIpAddr").getMac(), "private",null));
						
						for (final Host host : hosts) {
							if(!host.getClusterId().equals(clusterId)) {
								continue;
							}
							
							Transaction tryHostTransaction = tryHostTransaction(uuid, flavorId, metadata, newPassword,image, host, rpcExtra);
							try{
								tryHostTransaction.execute(rpcExtra);
								context.complete();
								return;
							}catch (Exception e) {
								if(e instanceof RpcTimeoutException ) {
									throw new RpcTimeoutException("timeout");
								} else {
									continue;
								}
							}
						}
						throw new Exception("no hosts to create VM");
					}
				}

				@Override
				public void onCompleted() {
					resumeVmTaskStatus(uuid);
				}

				@Override
				public boolean preProcess() throws Exception {
					return true;
				}

				@Override
				public void postProcess() throws Exception {
					setMaxBandwidth(uuid, rpcExtra);
					createVncFile(uuid);
				}

				@Override
				public void onError() {
					
				}
			});
			createTransaction.asyncExecute(rpcExtra);
			return uuid;
		} catch (Exception e) {
			if(e.getMessage().equals("return null") || 
			   e.getMessage().equals("no hosts to create VM") || 
			   e instanceof RpcTimeoutException) {
				setVmStatus(uuid, VmStatusEnum.ERROR);
			}
			
			String error = "createVM failed! " + paramInfos;
			logger.error(error, e);
			
			//db error
			if(e.getMessage()!= null && e.getMessage().startsWith("TIP Client")) {
				loller.error(LolLogUtil.CREATE_VM, "connect to db-proxy failed", rpcExtra);
			}else {
				loller.error(LolLogUtil.CREATE_VM, error+e.getMessage(), rpcExtra);
			}
		}
		return null;
	}

	@Override
	public String recoveryVM(final String name, final String userId,
							 final String imageBackUuid, final Integer flavorId, final Integer sgId,
							 final Integer avalibilityZoneId,final String hostUuid, final String newPassword,
							 final Map<String, String> metadata, final RpcExtra rpcExtra) throws RpcException {

		String paramInfos = "createVM: " + name + ", " + userId + ", "
				+ imageBackUuid + ", " + flavorId + ", " + sgId + ", "
				+ avalibilityZoneId + ", " + metadata + " ";
		logger.info(paramInfos);

		if (name == null || userId == null || imageBackUuid == null || flavorId == null || sgId == null
				|| avalibilityZoneId == null){
			logger.error("Failed to recovery VM, parameter error");
			loller.error(LolLogUtil.CREATE_VM, "Failed to recovery VM, parameter error", rpcExtra);
			return null;
		}

		// Stage 0 创建虚拟机的UUID
		final String uuid = UuidUtil.getRandomUuid();
		try {
			long startTime = System.currentTimeMillis();

			//获取配置信息
			final VmInstanceType instanceType = vmInstanceTypeProxy.getById(flavorId);
			if (instanceType == null) {
				return null;
			} else {
				logger.info("instanceType: " + flavorId);
			}

			// 获取镜像
			// TODO 修改镜像
//			final VmImage image = getImage(imageUuid, instanceType, rpcExtra);
			VmImageBack imageBack = vmImageBackProxy.getByUuid(imageBackUuid);
			String imageId = metadata.get("imageId");
			final VmImage image = vmImageProxy.getByUuid(imageId);
			if(imageBack == null || image == null) {
				return null;
			}

			//将虚拟机信息写入数据库
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

			// TODO 原始镜像，增加字段
			vmInstanceProxy.save(new VmInstance(null, uuid, name, Integer
					.parseInt(userId), null, null, avalibilityZoneId,
					timeStamp, timeStamp, timeStamp, 10, VmStatusEnum.BUILDING,
					TaskStatusEnum.READY, imageId, instanceType.getId(),
					null, sgId, image.getOsMode(), image.getOsArch(), image
					.getOsType(), null, null,1));
			logger.info("cost: " + (System.currentTimeMillis() - startTime));

			Transaction createTransaction = new Transaction(new TTask(){
				@Override
				public void process() throws Exception {
					context.addRollbackResource(new VMInstanceResource(uuid));
					VmInstance instance = vmInstanceProxy.getByUuid(
							uuid, false, false, false, false, false,
							false, false, false);
					if (instance == null) {
						logger.error("No instance with UUID " + uuid);
						loller.error(LolLogUtil.CREATE_VM, "No instance with UUID " + uuid, rpcExtra);
						throw new Exception("return null");
					} else {
						logger.info("instance: " + instance);
					}

					Host host = hostProxy.getByUuid(hostUuid, false, false, false);
					Integer clusterId = host.getClusterId();
					// Stage 6 Create network
					List<String> macAddressList = new ArrayList<String>();
					macAddressList.add(metadata.get("vmMacPub"));
					macAddressList.add(metadata.get("vmMacPri"));
					String bandWidthStr = metadata.get("maxBandwidth");
					logger.info("public mac is "+metadata.get("vmMacPub")+" and the private mac is "+metadata.get("vmMacPri"));
					Map<String, NetworkAddress> ips = getIPAddresses(bandWidthStr, clusterId, macAddressList, rpcExtra);
					if(Integer.valueOf(bandWidthStr) > 0) {
						if(ips.get("publicIpAddr") != null && ips.get("privateIpAddr") != null) {
							context.addRollbackResource(new PublicIpAddrResource(clusterId, ips.get("publicIpAddr").getIp()));
							context.addRollbackResource(new PrivateIpAddrResource(clusterId, ips.get("privateIpAddr").getIp()));
						}
					} else {
						if(ips.get("privateIpAddr") != null) {
							context.addRollbackResource(new PrivateIpAddrResource(clusterId, ips.get("privateIpAddr").getIp()));
						}
					}
					// Stage 7 Save VirtualInterface
					// TODO 未存入网卡数据库
					context.addRollbackResource(new VmVirtualInterfaceResource(instance.getUuid()));

					vmVirtualInterfaceProxy.save(new VmVirtualInterface(null, instance.getUuid(),
							ips.get("publicIpAddr").getIp(), ips.get("publicIpAddr").getMac(), "public", null));
					vmVirtualInterfaceProxy.save(new VmVirtualInterface(null, instance.getUuid(),
							ips.get("privateIpAddr").getIp(), ips.get("privateIpAddr").getMac(), "private",null));

					VmImageBack imageBack = vmImageBackProxy.getByUuid(imageBackUuid);
					Transaction tryCreateVmTransaction = tryCreateVmTransaction(uuid, flavorId, metadata, newPassword,imageBack, host, rpcExtra);
					try{
						tryCreateVmTransaction.execute(rpcExtra);
						context.complete();
						return;
					}catch (Exception e) {
						if(e instanceof RpcTimeoutException ) {
							throw new RpcTimeoutException("timeout");
						}
					}
				}

				@Override
				public void onCompleted() {
					resumeVmTaskStatus(uuid);
				}

				@Override
				public boolean preProcess() throws Exception {
					return true;
				}

				@Override
				public void postProcess() throws Exception {
					setMaxBandwidth(uuid, rpcExtra);
					createVncFile(uuid);
				}

				@Override
				public void onError() {

				}
			});
			createTransaction.asyncExecute(rpcExtra);
			return uuid;
		} catch (Exception e) {
			if(e.getMessage().equals("return null") ||
					e.getMessage().equals("no hosts to create VM") ||
					e instanceof RpcTimeoutException) {
				setVmStatus(uuid, VmStatusEnum.ERROR);
			}

			String error = "createVM failed! " + paramInfos;
			logger.error(error, e);

			//db error
			if(e.getMessage()!= null && e.getMessage().startsWith("TIP Client")) {
				loller.error(LolLogUtil.CREATE_VM, "connect to db-proxy failed", rpcExtra);
			}else {
				loller.error(LolLogUtil.CREATE_VM, error+e.getMessage(), rpcExtra);
			}
		}
		return null;
	}

	@Override
	public void startVM(final String uuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "startVM uuid: " + uuid;
		logger.debug(paramInfos);
		
		if (uuid == null){
			logger.error("Can not start VM, uuid is null");
			loller.error(LolLogUtil.START_VM, "Can not start VM, uuid is null", rpcExtra);
			return;
		}
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.STARTING);
			}

			@Override
			public void process() throws Exception {
				try {
					VmInstance instance = vmInstanceProxy.getByUuid(uuid,
							true, false, false, false, false, false, false,
							false);

					if (instance == null) {
						logger.error("Illegall VM instance UUID " + uuid);
						loller.error(LolLogUtil.START_VM, "Illegall VM instance uuid: " + uuid, rpcExtra);
						return;
					}
					vmScheduler.startVM(instance, rpcExtra);
				} catch (Exception e) {
					String error = "startVM failed! " + paramInfos;
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler" + error);
						loller.error(LolLogUtil.START_VM, "no response from vm-scheduler " + error, rpcExtra);
					} else {
						logger.error(error, e);
						loller.error(LolLogUtil.START_VM, error+e.getMessage(), rpcExtra);
					}
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
				setMaxBandwidth(uuid, rpcExtra);
				createVncFile(uuid);
			}

			@Override
			public void onError() {
				
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void stopVM(final String uuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "stopVM uuid: " + uuid;
		logger.debug(paramInfos);
		
		if (uuid == null){
			logger.error("Can not stop VM, uuid is null");
			loller.error(LolLogUtil.STOP_VM, "Can not stop VM, uuid is null", rpcExtra);
			return;
		}

		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.STOPPING);
			}

			@Override
			public void process() throws Exception {
				try {
					vmScheduler.stopVM(uuid, rpcExtra);
					Thread.sleep(1000);
				} catch (Exception e) {
					String error = "stopVM failed! " + paramInfos;
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler" + error);
						loller.error(LolLogUtil.STOP_VM, "no response from vm-scheduler " + error, rpcExtra);
					} else {
						logger.error(error, e);
						loller.error(LolLogUtil.STOP_VM, error+e.getMessage(), rpcExtra);
					}
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
				
			}

			@Override
			public void onError() {
				
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void forceStopVM(final String uuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "forceStopVM uuid: " + uuid;
		logger.debug(paramInfos);
		
		if (uuid == null){
			logger.error("Can not force stop VM, uuid is null");
			loller.error(LolLogUtil.FORCE_STOP_VM, "Can not force stop VM, uuid is null", rpcExtra);
			return;
		}

		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.STOPPING);
			}

			@Override
			public void process() throws Exception {
				try {
					vmScheduler.forceStopVM(uuid, rpcExtra);
					Thread.sleep(1000);
				} catch (Exception e) {
					String error = "forceStopVM failed! " + paramInfos;
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler" + error);
						loller.error(LolLogUtil.FORCE_STOP_VM, "no response from vm-scheduler " + error, rpcExtra);
					} else {
						logger.error(error, e);
						loller.error(LolLogUtil.FORCE_STOP_VM, error+e.getMessage(), rpcExtra);
					}
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
				
			}

			@Override
			public void onError() {
				
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void resumeVM(final String uuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "resumeVM uuid: " + uuid;
		logger.debug(paramInfos);

		if (uuid == null){
			logger.error("Can not resume VM, uuid is null");
			loller.error(LolLogUtil.RESUME_VM, "Can not resume VM, uuid is null", rpcExtra);
			return;
		}
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.RESUMING);
			}

			@Override
			public void process() throws Exception {
				try {
					vmScheduler.resumeVM(uuid, rpcExtra);
				} catch (Exception e) {
					String error = "resumeVM failed! " + paramInfos;
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler" + error);
						loller.error(LolLogUtil.RESUME_VM, "no response from vm-scheduler " + error, rpcExtra);
					} else {
						logger.error(error, e);
						loller.error(LolLogUtil.RESUME_VM, error+e.getMessage(), rpcExtra);
					}
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
				
			}

			@Override
			public void onError() {
				
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void rebootVM(final String uuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "rebootVM uuid: " + uuid;
		logger.debug(paramInfos);

		if (uuid == null){
			logger.error("Can not reboot VM, uuid is null");
			loller.error(LolLogUtil.REBOOT_VM, "Can not reboot VM, uuid is null", rpcExtra);
			return;
		}
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.REBOOTING);
			}

			@Override
			public void process() throws Exception {
				try {
					vmScheduler.rebootVM(uuid, rpcExtra);
				} catch (Exception e) {
					String error = "rebootVM failed! " + paramInfos;
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler" + error);
						loller.error(LolLogUtil.REBOOT_VM, "no response from vm-scheduler " + error, rpcExtra);
					} else {
						logger.error(error, e);
						loller.error(LolLogUtil.REBOOT_VM, error+e.getMessage(), rpcExtra);
					}
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
				setMaxBandwidth(uuid, rpcExtra);
				createVncFile(uuid);
			}

			@Override
			public void onError() {
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	/**
	 * 在线迁移
	 */
	@Override
	public void onlineMigrateVM(final String uuid, final String hostUuid, final RpcExtra rpcExtra)
			throws RpcException {
		
		final String paramInfos = "onlineMigrateVM(" + uuid + ", " + hostUuid + ")";
		logger.debug(paramInfos);
		loller.debug(LolLogUtil.ONLINE_MIGRATION, paramInfos, rpcExtra);
		try {
			//获取虚拟机实例
			final VmInstance instance = vmInstanceProxy.getByUuid(uuid, false, false, false, false, true, false, false, false);
			if(instance == null)
				throw new Exception("return null when get instance in online migrating VM because of empty instanceUuid");
			if(hostUuid == null)
				throw new Exception("return null when get instance in online migrating VM because of empty hostUuid");
			//如果指定的host就是原instance所在host,则直接返回
			if(instance.getHostUuid().equals(hostUuid))
				return ;
			
			Transaction createTransaction = new Transaction(new TTask() {
				@Override
				public boolean preProcess() throws Exception {
					return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.MIGRATING);
				}

				@Override
				public void process() throws Exception {
					boolean success = false;
					String oldHostUUID = instance.getHostUuid();
					try {
						logger.info("[before vmschduler.onlineMigrate]uuid:" + uuid
								+ ", destHostUuid:" + hostUuid);
						vmScheduler.onlineMigrate(uuid, hostUuid, rpcExtra);
						//先把hostUUID恢复成原来的老的值，以便删除流表，设置带宽
						VmInstance tempInstance = vmInstanceProxy.getByUuid(uuid, false, false, false, false, true, false, false, false);
						tempInstance.setHostUuid(oldHostUUID);
						vmInstanceProxy.update(tempInstance);
						//delete private-net FlowTable
						networkProvider.deleteFlowTable(uuid, NetSegment.PRIVATE, rpcExtra);
						//delete public-net FlowTable
						List<VmInstanceMetadata> list = instance.getVmInstanceMetadatas();
						for (VmInstanceMetadata v : list) {
							if(v.getKey().equals("maxBandwidth")) {
								int maxBw = Integer.valueOf(v.getValue());
								if(maxBw > 0) {
									networkProvider.deleteFlowTable(uuid, NetSegment.PUBLIC, rpcExtra);
								}
							}
						}
						//setMaxBandwidth
						new VmServiceImpl().setMaxBandwidth(uuid, rpcExtra);
						//update instance's hostUuid
						tempInstance = vmInstanceProxy.getByUuid(uuid, false, false, false, false, true, false, false, false);
						tempInstance.setHostUuid(hostUuid);
						vmInstanceProxy.update(tempInstance);
						success = true;
						//vnc配置文件修改,以便可以通过网页noVNC访问云主机
						createVncFile(uuid);
						logger.info("online migrate done successfully,uuid:" + uuid);
					} catch(Exception e) {
						if(e instanceof RpcTimeoutException) {
							logger.error("no response from vm-scheduler");
							loller.error(LolLogUtil.ONLINE_MIGRATION, "no response from vm-scheduler", rpcExtra);
						} else {
							logger.error("create vm in host " + hostUuid +" failed, caused by" + e.getMessage());
							loller.error(LolLogUtil.ONLINE_MIGRATION, "create vm in host " + hostUuid +" failed, caused by " + e.getMessage(), rpcExtra);
						}
					}
						
					if(!success) {
						logger.error("create vm in all selected hosts failed");
						loller.error(LolLogUtil.ONLINE_MIGRATION, "create vm in all selected hosts failed", rpcExtra);
					} 
				}

				@Override
				public void postProcess() throws Exception {
				}

				@Override
				public void onError() {
				}

				@Override
				public void onCompleted() {
					resumeVmTaskStatus(uuid);					
				}
			});
			createTransaction.asyncExecute(rpcExtra);
			
		} catch (Exception e) {
			logger.error("something wrong occured while online migrating server " + uuid +", caused by " + e.getMessage());
			loller.error(LolLogUtil.ONLINE_MIGRATION, "something wrong occured while online migrating server " + uuid +", caused by " + e.getMessage(), rpcExtra);
		}
		
	}
	public static void main(String[] args) {
		try {
			networkProvider.deleteFlowTable("4334ed39a9534b1b857ec1d8f3f3d823", NetSegment.PRIVATE, new RpcExtra());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 离线迁移
	 */
	@Override
	public void migrateVM(final String uuid, final String hostUuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "migrateVM(" + uuid + ", " + hostUuid + ")";
		logger.debug(paramInfos);
		loller.debug(LolLogUtil.OFFLINE_MIGRATION, paramInfos, rpcExtra);

		try {
			//获取虚拟机实例
			final VmInstance instance = vmInstanceProxy.getByUuid(uuid, false, false, false, false, true, false, false, false);
			if(instance == null)
				throw new Exception("return null when get instance in magrating VM");
			
			//如果指定的host就是原instance所在host,则直接返回
			if(instance.getHostUuid().equals(hostUuid))
				return ;
			
			/**
			 * 获取符合要求的宿主机列表hosts
			 * 如果hosts列表为空，则返回资源不足异常
			 * 如果指定的hostUuid不为空，且指定的hostUuid在hosts中，则hosts中只留下指定的host
			 * 如果指定的hostUuid不为空，但指定的hostUuid不在hosts中，则抛出资源不足的异常
			 */
			List<Host> hosts = selectHosts(uuid, instance.getInstanceTypeId(), 
					instance.getAvailabilityZoneId(), instance.getAvailabilityClusterId(), rpcExtra);
			if(hosts == null || hosts.size() == 0)
				throw new Exception("resource is not enough for new instance while migrating instance " + uuid);
			
			boolean contained = false;
			Host selectedHost = null;
			if(hostUuid != null && !hostUuid.equals("")) {
				for(Host host : hosts) {
					if(host.getUuid().equals(hostUuid)) {
						contained = true;
						selectedHost = host;
						break;
					}
				}
				
				if(contained) {
					hosts.clear();
					hosts.add(selectedHost);
				} else {
					throw new Exception("selected host " + hostUuid + " is not suitable for migrating instance "+ uuid);
				}
			}
			
			final List<Host> selectedHosts = hosts;
			Transaction createTransaction = new Transaction(new TTask() {
				@Override
				public boolean preProcess() throws Exception {
					return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.MIGRATING);
				}

				@Override
				public void process() throws Exception {
					boolean success = false;
					for(Host host : selectedHosts) {
						if(host.getUuid().equals(instance.getHostUuid()))
							continue;
						try {
							vmScheduler.offlineMigrate(uuid, host.getUuid(), rpcExtra);
//							vmScheduler.createVM(uuid, 
//									instance.getInstanceTypeId().toString(), 
//									instance.getVmInstanceMetadatas(), 
//									instance.getVmVolumes(), 
//									instance.getVmSecurityGroup(), 
//									instance.getVmVirtualInterfaces(), rpcExtra);
							instance.setHostUuid(host.getUuid());
							vmInstanceProxy.update(instance);
							success = true;
							break;
						} catch(Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no response from vm-scheduler");
								loller.error(LolLogUtil.OFFLINE_MIGRATION, "no response from vm-scheduler", rpcExtra);
								break;
							} else {
								logger.error("create vm in host " + host.getUuid() +" failed, caused by" + e.getMessage());
								loller.error(LolLogUtil.OFFLINE_MIGRATION, "create vm in host " + host.getUuid() +" failed, caused by " + e.getMessage(), rpcExtra);
							}
						}
						
					} 
					if(!success) {
						logger.error("create vm in all selected hosts failed");
						loller.error(LolLogUtil.OFFLINE_MIGRATION, "create vm in all selected hosts failed", rpcExtra);
					} 
				}

				@Override
				public void postProcess() throws Exception {
				}

				@Override
				public void onError() {
				}

				@Override
				public void onCompleted() {
					resumeVmTaskStatus(uuid);					
				}
			});
			createTransaction.asyncExecute(rpcExtra);
		} catch (Exception e) {
			logger.error("something wrong occured while migrating server " + uuid +", caused by " + e.getMessage());
			loller.error(LolLogUtil.OFFLINE_MIGRATION, "something wrong occured while migrating server " + uuid +", caused by " + e.getMessage(), rpcExtra);
		}
	}
	
	@Override
	public void rebuildVM(final String uuid, final Integer userId, final Integer flavorId,
			final Integer sgId, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "rebuildVM(" + uuid + ", " + userId 
				+ ", " + flavorId + ", " + sgId + ")";
		logger.info(paramInfos);
		loller.info(LolLogUtil.REBUILD_VM, paramInfos, rpcExtra);
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.BUILDING);
			}

			@Override
			public void process() throws Exception {
				Integer oldInstanceTypeId = null;
				VmInstance instance = null;
				boolean dataChanged = false;
				try {
					instance = vmInstanceProxy.getByUuid(uuid,
							false, false, false, false, false, false, false,
							false);
					if (instance == null) {
						throw new Exception("return null when get instance in rebuilding VM");
					}
					if(userId != null) {
						instance.setUserId(userId);
					}
					if (sgId != null) {
						instance.setSecurityGroupId(sgId);
						VmSecurityGroup securityGroup = vmSecurityGroupProxy.getById(sgId, false);
						if(securityGroup != null) {
							vmScheduler.attachSecurityGroup(uuid, securityGroup, rpcExtra);
						} else {
							logger.error("return null when get security group in rebuilding VM");
							loller.error(LolLogUtil.REBUILD_VM, "return null when get security group in rebuilding VM", rpcExtra);
						}
					}
					if (flavorId != null) {
						VmInstanceType vmInstanceType = vmInstanceTypeProxy.getById(flavorId);
						if (vmInstanceType != null) {
							oldInstanceTypeId = instance.getInstanceTypeId();
							instance.setVmInstanceType(vmInstanceType);
							instance.setInstanceTypeId(flavorId);
						} else {
							logger.error("return null when get instance type in rebuilding VM");
							loller.error(LolLogUtil.REBUILD_VM, "return null when get instance type in rebuilding VM", rpcExtra);
						}
					}
					
					logger.debug("instance: " + instance);
					vmInstanceProxy.update(instance);
					vmScheduler.stopVM(uuid, rpcExtra);
					Thread.sleep(1000);

					vmScheduler.resizeVM(instance.getUuid(), instance
							.getVmInstanceType().getFlavorUuid(), instance
							.getVmInstanceMetadatas(), rpcExtra);
					try {
						vmInstanceTypeProxy.deleteById(oldInstanceTypeId);
					} catch (Exception e) {
						logger.error("delete old instance type failed: " + e.getMessage());
						loller.error(LolLogUtil.REBUILD_VM, "delete old instance type failed: " + e.getMessage(), rpcExtra);
					}
				} catch (Exception e) {
					if(e.getMessage().equals("update metadata failed")) {
					}
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler");
						loller.error(LolLogUtil.REBUILD_VM, "no response from vm-scheduler", rpcExtra);
					} else {
						logger.error("rebuild VM failed: " + e.getMessage(), e);
						loller.error(LolLogUtil.REBUILD_VM, "rebuild VM failed: " + e.getMessage(), rpcExtra);
					}
					if(dataChanged) {
						if (flavorId != null) {
							try {
								instance.setInstanceTypeId(oldInstanceTypeId);
								vmInstanceProxy.update(instance);
								vmInstanceTypeProxy.deleteById(flavorId);
								logger.info("rollback instance type success");
							} catch (Exception ex) {
								logger.info("rollback instance type failed");
								loller.error(LolLogUtil.REBUILD_VM, "rollback instance type failed", rpcExtra);
							}
						}
					}
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
				setMaxBandwidth(uuid, rpcExtra);
				createVncFile(uuid);
			}

			@Override
			public void onError() {
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}
	
	@Override
	public void suspendVM(final String uuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "suspendVM uuid: " + uuid;
		logger.debug(paramInfos);
		
		if (uuid == null){
			logger.error("Can not suspend VM, uuid is null");
			loller.error(LolLogUtil.SUSPEND_VM, "Can not suspend VM, uuid is null", rpcExtra);
			return;
		}
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.SUSPENDING);
			}

			@Override
			public void process() throws Exception {
				try {
					vmScheduler.suspendVM(uuid, rpcExtra);
				} catch (Exception e) {
					String error = "suspendVM failed! " + paramInfos;
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler" + error);
						loller.error(LolLogUtil.SUSPEND_VM, "no response from vm-scheduler " + error, rpcExtra);
					} else {
						logger.error(error, e);
						loller.error(LolLogUtil.SUSPEND_VM, error+e.getMessage(), rpcExtra);
					}
//					AlertUtil.alert("虚拟机 " + uuid + "挂起失败", "输入参数为" + paramInfos, e);
					// throw new RpcException(error);
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
			}

			@Override
			public void onError() {
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void forceDeleteVM(final String uuid, RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "forceDeleteVM uuid: " + uuid;
		logger.debug(paramInfos);
		forceterminateVM(uuid, rpcExtra);
	}
	
	public void forceterminateVM(final String uuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "force terminateVM uuid: " + uuid;
		logger.debug(paramInfos);
		
		if (uuid == null){
			logger.error("Can not force delete VM, uuid is null");
			loller.error(LolLogUtil.FORCE_DELETE_VM, "Can not force delete VM, uuid is null", rpcExtra);
			return;
		}
		
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.DELETING);
			}

			@Override
			public void process() throws Exception {
				VmInstance instance = null;

				instance = vmInstanceProxy.getByUuid(uuid, false, false,
						false, false, false, false, false, false);
				if(instance == null) {
					throw new RpcException("return null when get instance by uuid " + uuid);
				}
				if(instance.getTaskStatus().compareTo(TaskStatusEnum.DELETING) != 0) {
					throw new RpcException("instance status is not DELETING");
				}
				
				//先将虚拟机关机
				try {
					if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
						vmScheduler.stopVM(uuid, rpcExtra);
						logger.debug("terminateVM.vmScheduler.stopVM succeed!");
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler");
						loller.error(LolLogUtil.TERMINATE_VM, "no response from vm-scheduler", rpcExtra);
					} else {
						logger.error("stop vm failed", e);
						loller.error(LolLogUtil.TERMINATE_VM, "stop vm failed " + e.getMessage(), rpcExtra);
					}
				}
				
				//查询虚拟机所有分区
				QueryObject<VmVolume> volumeQON = new QueryObject<VmVolume>();
				volumeQON.addFilterBean(new FilterBean<VmVolume>("instanceUuid",
						uuid, FilterBeanType.EQUAL));
				List<? extends VmVolume> vmVolumesN = vmVolumeProxy
						.searchAll(volumeQON, false, false, false, false);
				
				//卸载网盘和ISO盘
				for (VmVolume vmVolume : vmVolumesN) {
					if (vmVolume.getUsageType() == null
							|| vmVolume.getUsageType().compareTo(
									VmVolumeUsageTypeEnum.NETWORK) == 0) {
						try {
							vmScheduler.detachVolume(uuid, vmVolume, rpcExtra);
							logger.info("detach volume " + vmVolume.getVolumeUuid() + " from instance " + uuid + " succeed!");
							loller.info(LolLogUtil.TERMINATE_VM, "detach volume " + vmVolume.getVolumeUuid() + " from instance "
									 + uuid + " succeed!", rpcExtra);
						} catch (Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no responce from vm-scheduler");
								loller.error(LolLogUtil.TERMINATE_VM, "no responce from vm-scheduler", rpcExtra);
							} else {
								logger.error("detach volume " + vmVolume.getVolumeUuid() + "failed", e);
								loller.error(LolLogUtil.TERMINATE_VM, 
										"detach volume " + vmVolume.getVolumeUuid() + " failed " + e.getMessage() , rpcExtra);
							}
						}
					}

					else if(vmVolume.getUsageType().compareTo(VmVolumeUsageTypeEnum.ISO) == 0) {
						try {
							vmScheduler.detachVolume(uuid, vmVolume, rpcExtra);
						} catch(Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no responce from vm-scheduler");
								loller.error(LolLogUtil.TERMINATE_VM, "no responce from vm-scheduler", rpcExtra);
							} else {
								logger.error("detach volume " + vmVolume.getVolumeUuid() + "failed", e);
								loller.error(LolLogUtil.TERMINATE_VM, 
										"detach volume " + vmVolume.getVolumeUuid() + " failed " + e.getMessage() , rpcExtra);
							}
						}
						
						try {
							volumeScheduler.destroyVolume(vmVolume.getVolumeUuid(), rpcExtra);
							logger.info("detach iso " + vmVolume.getVolumeUuid() + " from instance "
									 + uuid + " succeed!");
							loller.info(LolLogUtil.TERMINATE_VM, "detach iso " + vmVolume.getVolumeUuid() + " from instance "
									 + uuid + " succeed!", rpcExtra);
						} catch (Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no responce from volume-scheduler");
								loller.error(LolLogUtil.TERMINATE_VM, "no responce from volume-scheduler", rpcExtra);
							} else {
								logger.error("destroy volume " + vmVolume.getVolumeUuid() + "failed", e);
								loller.error(LolLogUtil.TERMINATE_VM, 
										"destroy volume " + vmVolume.getVolumeUuid() + " failed " + e.getMessage() , rpcExtra);
							}
						}
					}
				}
				
				
				try {
					vmScheduler.deleteVM(uuid, rpcExtra);
				} catch (Exception e) {
					if(e instanceof RpcTimeoutException) {
						logger.error("no responce from vm-scheduler");
						loller.error(LolLogUtil.TERMINATE_VM, "no responce from vm-scheduler", rpcExtra);
					} else {
						logger.error("deleteVM " + uuid + " failed", e);
						loller.error(LolLogUtil.TERMINATE_VM, "deleteVM " + uuid + " failed " + e.getMessage(), rpcExtra);
					}
				}
				Thread.sleep(1000);

				
				instance = vmInstanceProxy.getByUuid(uuid, false, false,
						false, false, false, false, false, false);
				if (instance.getVmStatus().compareTo(
								VmStatusEnum.ERROR) == 0
						|| instance.getVmStatus().compareTo(
								VmStatusEnum.DELETED) == 0) {
					// Update status
					instance.setVmStatus(VmStatusEnum.DELETED);
					instance.setTaskStatus(TaskStatusEnum.DELETING);
					vmInstanceProxy.update(instance);

					
					@SuppressWarnings("unchecked")
					List<VmVirtualInterface> vifs = (List<VmVirtualInterface>) vmVirtualInterfaceProxy
							.getByInstanceUuid(instance.getUuid());
					try {
						vmVirtualInterfaceProxy.deleteByInstanceUuid(instance.getUuid());
					} catch (Exception e) {
						logger.error("delete vm virtual interface failed", e);
						loller.error(LolLogUtil.TERMINATE_VM, 
								"delete vm virtual interface failed " + e.getMessage(), rpcExtra);
					}
					
					for (VmVirtualInterface vif : vifs) {
						try {
							if (vif.getNetworkType().equals("public")) {
								networkProvider.releasePublicIpAddress(
										instance.getAvailabilityClusterId(),
										vif.getAddress(), rpcExtra);
								
								
							} else if (vif.getNetworkType().equals("private")) {
								networkProvider.releasePrivateIpAddress(
										instance.getAvailabilityClusterId(),
										vif.getAddress(), rpcExtra);
							}
						} catch(Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no response from network-provider");
								loller.error(LolLogUtil.TERMINATE_VM, "no response from network-provider", rpcExtra);
							} else {
								logger.error("delete ip failed", e);
								loller.error(LolLogUtil.TERMINATE_VM, "delete ip failed " + e.getMessage(), rpcExtra);
							}
						}
					}

					QueryObject<VmVolume> volumeQO = new QueryObject<VmVolume>();
					volumeQO.addFilterBean(new FilterBean<VmVolume>("instanceUuid",
							uuid, FilterBeanType.EQUAL));
					List<? extends VmVolume> vmVolumes = vmVolumeProxy
							.searchAll(volumeQO, false, false, false, false);
					
					//删除系统盘和数据盘
					for (VmVolume vmVolume : vmVolumes) {
						if (vmVolume.getUsageType() == null
								|| vmVolume.getUsageType().compareTo(
										VmVolumeUsageTypeEnum.SYSTEM) == 0
								|| vmVolume.getUsageType().compareTo(
										VmVolumeUsageTypeEnum.DATA) == 0) {
							if (vmVolume.getVolumeUuid() != null) {
								try {
									volumeScheduler.destroyVolume(vmVolume
											.getVolumeUuid(), rpcExtra);
								} catch (Exception e) {
									if(e instanceof RpcTimeoutException) {
										logger.error("no response from volume-scheduler");
										loller.error(LolLogUtil.TERMINATE_VM, "no response from volume-scheduler", rpcExtra);
									} else {
										logger.error("destroy system volume failed", e);
										loller.error(LolLogUtil.TERMINATE_VM,
												"destroy system volume failed "+e.getMessage() , rpcExtra);
									}
								}
								
							}
						}
					}
					//删除镜像链
					List<? extends VmImageBack> imageBacks = vmImageBackProxy.getByInstanceUuid(uuid);
					for (VmImageBack imageBack : imageBacks) {
						volumeScheduler.destroyVolumeImageBack(imageBack.getVolumeUuid(),rpcExtra);
					}
				}
				
				//delete instance_type
				try {
					vmInstanceMetadataProxy.deleteByVmInstanceId(instance.getId());
					vmInstanceTypeProxy.deleteById(instance.getInstanceTypeId());
					vmInstanceProxy.deleteByUuid(instance.getUuid());
				} catch (Exception e) {
					logger.error("delete instance type and metadata failed", e);
					loller.error(LolLogUtil.TERMINATE_VM, "delete instance type and metadata failed " + e.getMessage(), rpcExtra);
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
			}

			@Override
			public void onError() {
			}
		});

		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void terminateVM(final String uuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "terminateVM uuid: " + uuid;
		logger.debug(paramInfos);
		
		if (uuid == null){
			logger.error("instance: " + "uuid can not be null");
			return;
		}
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(uuid, TaskStatusEnum.DELETING);
			}

			@Override
			public void process() throws Exception {
				VmInstance instance = null;

				instance = vmInstanceProxy.getByUuid(uuid, false, false,
						false, false, false, false, false, false);
				if (instance == null) {
					logger.error("return null when get instance by uuid " + uuid);
					loller.error(LolLogUtil.TERMINATE_VM, "return null when get instance by uuid " + uuid, rpcExtra);
					return;
				}
				if(instance.getTaskStatus().compareTo(TaskStatusEnum.DELETING) != 0) {
					logger.error("instance status is not DELETING");
					loller.error(LolLogUtil.TERMINATE_VM, "instance status is not DELETING", rpcExtra);
					return;
				}
				
				if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
					try {
						vmScheduler.stopVM(uuid, rpcExtra);
						Thread.sleep(1000);
					} catch(Exception e) {
						if(e instanceof RpcTimeoutException) {
							logger.error("no responce from vm-scheduler");
							loller.error(LolLogUtil.TERMINATE_VM, "no responce from vm-scheduler", rpcExtra);
						} else {
							logger.error("stop vm failed ", e);
							loller.error(LolLogUtil.TERMINATE_VM, "stop vm failed "+e.getMessage(), rpcExtra);
						}
						return;
					}
				}
				
				//卸载网盘
				QueryObject<VmVolume> volumeQON = new QueryObject<VmVolume>();
				volumeQON.addFilterBean(new FilterBean<VmVolume>("instanceUuid",
						uuid, FilterBeanType.EQUAL));
				List<? extends VmVolume> vmVolumesN = vmVolumeProxy
						.searchAll(volumeQON, false, false, false, false);
				
				for (VmVolume vmVolume : vmVolumesN) {
					if (vmVolume.getUsageType() == null
							|| vmVolume.getUsageType().compareTo(
									VmVolumeUsageTypeEnum.NETWORK) == 0) {
						try {
							vmScheduler.detachVolume(uuid, vmVolume, rpcExtra);
							logger.info("detach volume " + vmVolume.getVolumeUuid() + " from instance "
									 + uuid + " succeed!");
							loller.info(LolLogUtil.TERMINATE_VM, "detach volume " + vmVolume.getVolumeUuid() + " from instance "
									 + uuid + " succeed!", rpcExtra);
						} catch(Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no response from vm-scheduler");
								loller.error(LolLogUtil.TERMINATE_VM, "no response from vm-scheduler", rpcExtra);
							}else {
								logger.error("detach volume " + vmVolume.getVolumeUuid() + " failed ", e);
								loller.error(LolLogUtil.TERMINATE_VM, 
										"detach volume " + vmVolume.getVolumeUuid() + " failed " + e.getMessage(), rpcExtra);
							}
						}
					}

					else if(vmVolume.getUsageType().compareTo(
							VmVolumeUsageTypeEnum.ISO) == 0) {
						try {
							vmScheduler.detachVolume(uuid, vmVolume, rpcExtra);
						} catch(Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no response from vm-scheduler");
								loller.error(LolLogUtil.TERMINATE_VM, "no response from vm-scheduler", rpcExtra);
							}else {
								logger.error("detach volume " + vmVolume.getVolumeUuid() + " failed ", e);
								loller.error(LolLogUtil.TERMINATE_VM, 
										"detach volume " + vmVolume.getVolumeUuid() + " failed " + e.getMessage(), rpcExtra);
							}
							return ;
						}
						
						try {
							volumeScheduler.destroyVolume(vmVolume.getVolumeUuid(), rpcExtra);
							logger.info("detach iso " + vmVolume.getVolumeUuid() + " from instance "
									 + uuid + " succeed!");
							loller.info(LolLogUtil.TERMINATE_VM, "detach iso " + vmVolume.getVolumeUuid() + " from instance "
									 + uuid + " succeed!", rpcExtra);
						} catch(Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no response from volume-scheduler");
								loller.error(LolLogUtil.TERMINATE_VM, "no response from volume-scheduler", rpcExtra);
							}else {
								logger.error("destroy volume " + vmVolume.getVolumeUuid() + " failed ", e);
								loller.error(LolLogUtil.TERMINATE_VM, 
										"destroy volume " + vmVolume.getVolumeUuid() + " failed " + e.getMessage(), rpcExtra);
							}
							return;
						}
						
					}
				}
				
				try {
					vmScheduler.deleteVM(uuid, rpcExtra);
					Thread.sleep(1000);
				} catch(Exception e) {
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler");
						loller.error(LolLogUtil.TERMINATE_VM, "no response from vm-scheduler", rpcExtra);
					}else {
						logger.error("delete VM " + uuid + " failed ", e);
						loller.error(LolLogUtil.TERMINATE_VM, 
								"delete VM " + uuid + " failed " + e.getMessage(), rpcExtra);
					}
					return ;
				}

				instance = vmInstanceProxy.getByUuid(uuid, false, false,
						false, false, false, false, false, false);

				
				if (instance.getVmStatus().compareTo(
								VmStatusEnum.ERROR) == 0
						|| instance.getVmStatus().compareTo(
								VmStatusEnum.DELETED) == 0) {
					// Update status
					instance.setVmStatus(VmStatusEnum.DELETED);
					instance.setTaskStatus(TaskStatusEnum.DELETING);
					vmInstanceProxy.update(instance);

					@SuppressWarnings("unchecked")
					List<VmVirtualInterface> vifs = (List<VmVirtualInterface>) vmVirtualInterfaceProxy
							.getByInstanceUuid(instance.getUuid());
					vmVirtualInterfaceProxy.deleteByInstanceUuid(instance
							.getUuid());

					for (VmVirtualInterface vif : vifs) {
						try {
							if (vif.getNetworkType().equals("public")) {
								networkProvider.releasePublicIpAddress(
										instance.getAvailabilityClusterId(),
										vif.getAddress(), rpcExtra);
							} else if (vif.getNetworkType().equals("private")) {
								networkProvider.releasePrivateIpAddress(
										instance.getAvailabilityClusterId(),
										vif.getAddress(), rpcExtra);
							}
						} catch(Exception e) {
							if(e instanceof RpcTimeoutException) {
								logger.error("no response from network-provider");
								loller.error(LolLogUtil.TERMINATE_VM, "no response from network-provider", rpcExtra);
							} else {
								logger.error("release ip failed ", e);
								loller.error(LolLogUtil.TERMINATE_VM, "release ip failed " + e.getMessage(), rpcExtra);
							}
							return;
						}
						
					}

					QueryObject<VmVolume> volumeQO = new QueryObject<VmVolume>();
					volumeQO.addFilterBean(new FilterBean<VmVolume>("instanceUuid",
							uuid, FilterBeanType.EQUAL));
					List<? extends VmVolume> vmVolumes = vmVolumeProxy
							.searchAll(volumeQO, false, false, false, false);
					for (VmVolume vmVolume : vmVolumes) {
						if (vmVolume.getUsageType() == null
								|| vmVolume.getUsageType().compareTo(
										VmVolumeUsageTypeEnum.SYSTEM) == 0
								|| vmVolume.getUsageType().compareTo(
										VmVolumeUsageTypeEnum.DATA) == 0) {
							if (vmVolume.getVolumeUuid() != null) {
								try {
									volumeScheduler.destroyVolume(vmVolume
											.getVolumeUuid(), rpcExtra);
									//删除镜像链接
									List<? extends VmImageBack> imageBacks = vmImageBackProxy.getByInstanceUuid(uuid);
									for (VmImageBack imageBack : imageBacks) {
										volumeScheduler.destroyVolumeImageBack(imageBack.getVolumeUuid(),rpcExtra);
									}
								} catch(Exception e) {
									if(e instanceof RpcTimeoutException) {
										logger.error("no response from volume-scheduler");
										loller.error(LolLogUtil.TERMINATE_VM, "no response from volume-scheduler", rpcExtra);
									} else {
										logger.error("destroy volume " + vmVolume.getVolumeUuid() + " failed", e);
										loller.error(LolLogUtil.TERMINATE_VM, 
												"destroy volume " + vmVolume.getVolumeUuid() + " failed " + e.getMessage(), rpcExtra);
									}
									return;
								}
								
							}
						}
					}
				}
				
				//delete instance_type
				try {
					vmInstanceMetadataProxy.deleteByVmInstanceId(instance.getId());
					vmInstanceTypeProxy.deleteById(instance.getInstanceTypeId());
					vmInstanceProxy.deleteByUuid(instance.getUuid());
				} catch (Exception ex) {
					logger.info("delete instance type and metadata failed");
					loller.error(LolLogUtil.TERMINATE_VM, "delete instance type and metadata failed", rpcExtra);
					return;
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(uuid);
			}

			@Override
			public void postProcess() throws Exception {
			}

			@Override
			public void onError() {
			}
		});

		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void bootFromISO(final String imageUuid, final String instanceUuid, final RpcExtra rpcExtra)
			throws RpcException {
		final String paramInfos = "bootFromISO imageUuid: " + imageUuid
				+ " instanceUuid: " + instanceUuid;
		logger.debug(paramInfos);
		
		if (imageUuid == null || instanceUuid == null){
			logger.error("failed to boot from ISO, imageUuid OR instanceUuid is null");
			loller.error(LolLogUtil.BOOT_FROM_ISO, "failed to boot from ISO, imageUuid OR instanceUuid is null", rpcExtra);
			return;
		}
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(instanceUuid, TaskStatusEnum.ATTACHING);
			}

			@Override
			public void process() throws Exception {
				try {

					VmImage image = vmImageProxy.getByUuid(imageUuid);
					
					if (image == null) {
						logger.error("No image with UUID " + imageUuid);
						loller.error(LolLogUtil.BOOT_FROM_ISO, "No image with UUID " + imageUuid, rpcExtra);
						return;
					}
					logger.info("image: " + image);
					
					VmInstance instance = vmInstanceProxy.getByUuid(instanceUuid,
							false, false, false, false, false, true, false, false);
					if (instance == null) {
						logger.error("No instance with UUID " + instanceUuid);
						loller.error(LolLogUtil.BOOT_FROM_ISO, "No instance with UUID " + instanceUuid, rpcExtra);
						return;
					}
					logger.info("instance: " + instance);
					
					VmZone zone = vmZoneProxy.getById(instance.getAvailabilityZoneId());
					Integer clusterId = instance.getAvailabilityClusterId();
					if (zone == null) {
						logger.error("No zone with UUID " + instance.getAvailabilityZoneId());
						loller.error(LolLogUtil.BOOT_FROM_ISO, "No zone with UUID " + instance.getAvailabilityZoneId(), rpcExtra);
						return;
					}  
					logger.info("zone: " + zone);
					
					Cluster cluster = vmClusterProxy.getById(clusterId, false, false, false);
					List<Host> volumeHosts = volumeScheduler.selectHost(cluster, image.getSize(), null, rpcExtra);
					if (volumeHosts == null || volumeHosts.size() == 0) {
						logger.error("volume select host fail");
						loller.error(LolLogUtil.BOOT_FROM_ISO, "volume select host fail ", rpcExtra);
						return;
					}
					Host volumeHost = volumeHosts.get(0);
					
					VmVolume volume = volumeScheduler.defineVolume(
							VmVolumeUsageTypeEnum.ISO, image.getUserId(),
							image.getSize(), zone, imageUuid, volumeHost, rpcExtra);
					volume = volumeScheduler.createVolume(volume.getVolumeUuid(), rpcExtra);
					vmScheduler.attachVolume(instanceUuid, volume, rpcExtra);
					
					if (instance.getVmVolumes() != null){
						for (VmVolume vmVolume : instance.getVmVolumes()){
							if (vmVolume.getImageUuid().equals("only-one")){
								VmVolume emptyVmVolume = vmVolumeProxy.getById(vmVolume.getId(), false, false, false, false);
								emptyVmVolume.setImageUuid(imageUuid);
								vmVolumeProxy.update(emptyVmVolume);
								logger.info("Update volume imageUuid only-one succeed!");
								break;
							}
						}
					}
				} catch (Exception e) {
					String error = "bootFromISO failed! " + paramInfos;
					logger.error(error, e);
					loller.error(LolLogUtil.BOOT_FROM_ISO, error+e.getMessage(), rpcExtra);
					AlertUtil.alert("虚拟机 " + instanceUuid + "光盘启动失败", "输入参数为" + paramInfos, e);
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(instanceUuid);
			}

			@Override
			public void postProcess() throws Exception {
				setMaxBandwidth(instanceUuid, rpcExtra);
				createVncFile(instanceUuid);
			}

			@Override
			public void onError() {
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void detachISO(final String imageUuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "detachISO uuid: " + imageUuid;
		logger.debug(paramInfos);
		
		if (imageUuid == null){
			logger.error("Can not detach ISO, imageUuid is null");
			loller.error(LolLogUtil.DETACH_ISO, "Can not detach ISO, imageUuid is null", rpcExtra);
			return;
		}
		
		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(imageUuid, TaskStatusEnum.DETACHING);
			}

			@Override
			public void process() throws Exception {
				QueryObject<VmVolume> queryObject = new QueryObject<VmVolume>();
				queryObject.addFilterBean(new FilterBean<VmVolume>("instanceUuid", imageUuid,
						FilterBeanType.EQUAL));
//				queryObject.addFilterBean(new FilterBean("usageType",
//						VmVolumeUsageTypeEnum.ISO, FilterBeanType.EQUAL), FilterLogic.AND);
				queryObject.addFilterBean(new FilterBean<VmVolume>("volumeType",
						VmVolumeTypeEnum.ISO, FilterBeanType.EQUAL), FilterLogic.AND);

				try {
					@SuppressWarnings("unchecked")
					List<VmVolume> volumes = (List<VmVolume>) vmVolumeProxy.searchAll(
							queryObject, false, false, false, false);
					for (VmVolume volume : volumes) {
						vmScheduler.detachVolume(imageUuid, volume, rpcExtra);
					}

					for (VmVolume volume : volumes) {
						volumeScheduler.destroyVolume(volume.getVolumeUuid(), rpcExtra);
					}
				} catch (Exception e) {
					String error = "detachISO failed! " + paramInfos;
					logger.error(error, e);
					loller.error(LolLogUtil.DETACH_ISO, error+e.getLocalizedMessage(), rpcExtra);
					AlertUtil.alert("虚拟机 " + imageUuid + "光盘启动失败", "输入参数为" + paramInfos, e);
					// throw new RpcException(error);
				}
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(imageUuid);
			}

			@Override
			public void postProcess() throws Exception {
			}

			@Override
			public void onError() {
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public VmStatusEnum getVMState(String uuid, RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "getVMState uuid: " + uuid;
		logger.debug(paramInfos);
		try{
			return vmScheduler.getVMState(uuid, rpcExtra);
		}
		catch (Exception e){
			String error = "getVMState uuid: " + uuid + "failed! " + paramInfos;
			if(e instanceof RpcTimeoutException) {
				logger.error("no response from volume-scheduler " + error);
				loller.error(LolLogUtil.GET_VM_STATE, "no response from volume-scheduler" + error, rpcExtra);
			} else {
				logger.error(error, e);
				loller.error(LolLogUtil.GET_VM_STATE, error+e.getMessage(), rpcExtra);
			}
//			AlertUtil.alert("虚拟机 " + uuid + "状态获取失败", "输入参数为" + paramInfos, e);
		}
		return null;
	}
	
	private boolean setVmStatus(String uuid, VmStatusEnum status) {
		try {
			VmInstance instance = vmInstanceProxy.getByUuid(
					uuid, false, false, false, false, false,
					false, false, false);
			if(instance == null) {
				logger.error("No instance with UUID " + uuid);
				return false;
			} else {
				logger.info("instance: " + instance);
				instance.setVmStatus(status);
				vmInstanceProxy.update(instance);
				return true;
			}
		} catch(Exception e) {
			logger.error("setVmStatus failed uuid:" + uuid);
			return false;
		}
	}
	
	private boolean checkAndSetVmTaskStatus(String uuid, TaskStatusEnum status){
		try{
			VmInstance instance = vmInstanceProxy.getByUuid(
					uuid, false, false, false, false, false,
					false, false, false);
			
			if (instance == null || instance.getTaskStatus().compareTo(TaskStatusEnum.READY) != 0) {
				logger.error("No instance with UUID " + uuid + " OR task status is not READY");
				return false;
			} else {
				logger.info("instance: " + instance);
				instance.setTaskStatus(status);
				vmInstanceProxy.update(instance);
				return true;
			}
		}
		catch (Exception e){
			logger.error("checkAndSetVmTaskStatus failed uuid:" + uuid, e);
			return false;
		}
	}
	
	private void resumeVmTaskStatus(String uuid){
		try{
			VmInstance instance = vmInstanceProxy.getByUuid(
					uuid, false, false, false, false, false,
					false, false, false);
			
			if (instance == null) {
				logger.error("No instance with UUID " + uuid);
				return;
			} else {
				logger.info("instance: " + instance);
				instance.setTaskStatus(TaskStatusEnum.READY);
				vmInstanceProxy.update(instance);
				return;
			}
		}
		catch (Exception e){
			logger.error("resumeVmTaskStatus failed uuid:" + uuid, e);
			AlertUtil.alert("虚拟机" + uuid +"TaskStatus恢复失败！", e);
			return;
		}
	}
	
	private void createVncFile(String uuid) {
		VmInstance instance;
		try {
			instance = vmInstanceProxy.getByUuid(uuid, true, false, false, false, false, false, false, false);

			if(instance == null) {
				logger.error("No instance with uuid " + uuid);
				return;
			}
			
			String path = Constants.TOKENS_DIRECTION;
			if(path.substring(path.length()-1).equals("/")) {
				path = path.substring(0, path.length()-1);
			}
			File f = new File(path+"/"+instance.getUuid());
			if(f.exists()) {
				f.delete();
			} 
			DataOutputStream bos = new DataOutputStream(new FileOutputStream(f));
			String str = instance.getUuid() + ": " + instance.getHost().getIp() + ":" + instance.getVncPort();
			bos.writeBytes(str);
			bos.flush();
			bos.close();
			
			logger.info("create vnc token file " + uuid + " succeed");
		}catch(Exception e) {
			logger.error("create vnc token file " + uuid + " failed", e);
		}
	}
	
	@Override
	public Boolean modVmHostName(final String instanceUuid, 
			final String newHostName, final VmImageOSTypeEnum OSType, final RpcExtra rpcExtra)
			throws RpcException {
		logger.info("resource-scheduler modify hostname, instanceUuid:"+instanceUuid+", newHostName:"+newHostName);

		final VmVolume volume = null;
		
		Transaction transaction = new Transaction(new TTask() {

			@Override
			public boolean preProcess() throws Exception {
				return checkAndSetVmTaskStatus(instanceUuid, TaskStatusEnum.REBOOTING);
			}

			@SuppressWarnings("null")
			@Override
			public void process() throws Exception {
				VmInstance instance;
				try {
					QueryObject<VmVolume> queryObject = new QueryObject<VmVolume>();
					FilterBean<VmVolume> fb1 = new FilterBean<VmVolume>("instanceUuid", instanceUuid, FilterBeanType.EQUAL);
					FilterBean<VmVolume> fb2 = new FilterBean<VmVolume>("usageType", VmVolumeUsageTypeEnum.SYSTEM, FilterBeanType.EQUAL);
					queryObject.addFilterBean(fb1);
					queryObject.addFilterBean(fb2);
					
					@SuppressWarnings("unchecked")
					List<VmVolume> volumes = (List<VmVolume>) vmVolumeProxy.searchAll(queryObject, false, false, false, false);
					if (volumes == null || volumes.size() == 0) {
						String message = "resource-scheduler can't get volume by instanceuuid";
						logger.error(message);
						loller.error(LolLogUtil.MOD_VM_HOSTNAME, message, rpcExtra);
						throw new RpcException(message);
					}
					String volumeUuid = volumes.get(0).getVolumeUuid();
					instance = vmInstanceProxy.getByUuid(instanceUuid, false, false, false, false, false, false, false, false);
	
					if (instance == null) {
						logger.error("No instance with UUID " + instanceUuid);
						loller.error(LolLogUtil.MOD_VM_HOSTNAME, "No instance with UUID " + instanceUuid, rpcExtra);
						throw new RpcException("No instance with UUID " + instanceUuid);
					}
					
					if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
						vmScheduler.stopVM(instanceUuid, rpcExtra);
						logger.debug("modVmHostName.vmScheduler.stopVM succeed!");
						Thread.sleep(1000);
					}
					
					volumeScheduler.modVmHostName(volumeUuid, newHostName, OSType, rpcExtra);
					logger.info("modify vm hostname succeed!");
				} catch(Exception e) {
					logger.error("connect to db-proxy failed", e);
					loller.error(LolLogUtil.MOD_VM_HOSTNAME, "connect to db-proxy failed " + e.getMessage(), rpcExtra);
					throw new Exception("connect to db-proxy failed"+e.getMessage());
				}
				
			}

			@Override
			public void postProcess() throws Exception {
			}

			@Override
			public void onError() {
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(instanceUuid);
			}
			
		});
		
		transaction.asyncExecute(rpcExtra);
		return true;
	}
	
	
	@Override
	public Boolean modVmPasswd(final String instanceUuid, final String name,
			final String newPasswd, final VmImageOSTypeEnum OSType, final RpcExtra rpcExtra)
			throws RpcException {
		logger.info("resource-scheduler modify password, instanceUuid:"+instanceUuid+", name:"+name+", newPasswd:"+newPasswd);

		final VmVolume volume = null;
		
		Transaction transaction = new Transaction(new TTask() {

			@Override
			public boolean preProcess() throws Exception {
				return checkAndSetVmTaskStatus(instanceUuid, TaskStatusEnum.REBOOTING);
			}

			@SuppressWarnings("null")
			@Override
			public void process() throws Exception {
				VmInstance instance;
				try {
					QueryObject<VmVolume> queryObject = new QueryObject<VmVolume>();
					FilterBean<VmVolume> fb1 = new FilterBean<VmVolume>("instanceUuid", instanceUuid, FilterBeanType.EQUAL);
					FilterBean<VmVolume> fb2 = new FilterBean<VmVolume>("usageType", VmVolumeUsageTypeEnum.SYSTEM, FilterBeanType.EQUAL);
					queryObject.addFilterBean(fb1);
					queryObject.addFilterBean(fb2);
					
					@SuppressWarnings("unchecked")
					List<VmVolume> volumes = (List<VmVolume>) vmVolumeProxy.searchAll(queryObject, false, false, false, false);
					if (volumes == null || volumes.size() == 0) {
						String message = "resource-scheduler can't get volume by instanceuuid";
						logger.error(message);
						loller.error(LolLogUtil.MOD_VM_PASSWD, message, rpcExtra);
						throw new RpcException(message);
					}
					String volumeUuid = volumes.get(0).getVolumeUuid();
					instance = vmInstanceProxy.getByUuid(instanceUuid, false, false, false, false, false, false, false, false);
	
					if (instance == null) {
						logger.error("No instance with UUID " + instanceUuid);
						loller.error(LolLogUtil.MOD_VM_PASSWD, "No instance with UUID " + instanceUuid, rpcExtra);
						throw new RpcException("No instance with UUID " + instanceUuid);
					}
					
					if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
						vmScheduler.stopVM(instanceUuid, rpcExtra);
						logger.debug("modVmPasswd.vmScheduler.stopVM succeed!");
						Thread.sleep(1000);
					}
					
					volumeScheduler.modVmPasswd(volumeUuid, name, newPasswd, OSType, rpcExtra);
					logger.info("modify vm password succeed!");
				} catch(Exception e) {
					logger.error("connect to db-proxy failed", e);
					loller.error(LolLogUtil.MOD_VM_PASSWD, "connect to db-proxy failed " + e.getMessage(), rpcExtra);
					throw new Exception("connect to db-proxy failed"+e.getMessage());
				}
				
			}

			@Override
			public void postProcess() throws Exception {
			}

			@Override
			public void onError() {
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(instanceUuid);
			}
			
		});
		
		transaction.asyncExecute(rpcExtra);
		return true;
	}
	
	private void setMaxBandwidth(String instanceUuid, RpcExtra rpcExtra) throws Exception {
		try {
			NetworkService networkService = new NetworkServiceImpl();
			logger.info("setting max bandwidth instanceUuid=" + instanceUuid);
			VmInstance instance = vmInstanceProxy.getByUuid(instanceUuid, false, false, false, false, true, false, false, false);
			List<VmInstanceMetadata> metadatas = instance.getVmInstanceMetadatas();
			Map<String, String> metas = new HashMap<String, String>();
			for(VmInstanceMetadata meta : metadatas) {
				if(meta.getKey().equals("maxBandwidth"))
					metas.put("maxBandwidth", meta.getValue());
				else if(meta.getKey().equals("priBandwidth"))
					metas.put("priBandwidth", meta.getValue());
			}
			
			networkService.setVmMaxBandwidth(instanceUuid, metas, true, rpcExtra);//这个方法原来是释放IP的，所以此处用TRUE
			logger.info("set max bandwidth success!!");
		} catch(RpcException rpcEx) {
			logger.error("set max Bandwidth of vm=" + instanceUuid + " failed! Caused by " + rpcEx.getMessage());
			loller.error(LolLogUtil.SET_VM_MAX_BANDWIDTH, 
					"set max Bandwidth of vm=" + instanceUuid + " failed! " + rpcEx.getMessage(), rpcExtra);
		}
	}

	@Override
	public String KeepAlive() throws Exception {
//		try{
			return vmScheduler.KeepAlive();
//		}
//		catch(RpcException rpcEx) {
//			return "fail";
//		}
	}


}