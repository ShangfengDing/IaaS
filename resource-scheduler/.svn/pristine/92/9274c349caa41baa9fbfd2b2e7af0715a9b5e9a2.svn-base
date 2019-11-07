/**
 * File: VmServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroupIngressRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmInstance.TaskStatusEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmInstanceMetadataProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.common.proxy.VmVirtualInterfaceProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.service.NetworkProviderService;
import appcloud.common.service.VMSchedulerService;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.util.UuidUtil;
import appcloud.dbproxy.mysql.MySQLClusterProxy;
import appcloud.dbproxy.mysql.MySQLHostProxy;
import appcloud.dbproxy.mysql.MySQLVmImageProxy;
import appcloud.dbproxy.mysql.MySQLVmInstanceMetadataProxy;
import appcloud.dbproxy.mysql.MySQLVmInstanceProxy;
import appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy;
import appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy;
import appcloud.dbproxy.mysql.MySQLVmZoneProxy;
import appcloud.resourcescheduler.service.VmService;
import appcloud.resourcescheduler.vmdisk.VmDiskManager;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class VmServiceImpl implements VmService {

	private static final String ROOT_DEVICE_LOCATION = "/dev/sda";
	
	private static Logger logger = Logger
			.getLogger(VmServiceImpl.class);

	VMSchedulerService vmScheduler;
	NetworkProviderService networkProvider;

	HostProxy hostProxy = new MySQLHostProxy();
	
	VmImageProxy vmImageProxy = new MySQLVmImageProxy();
	VmInstanceProxy vmInstanceProxy = new MySQLVmInstanceProxy();
	VmInstanceTypeProxy vmInstanceTypeProxy = new MySQLVmInstanceTypeProxy();
	VmInstanceMetadataProxy vmInstanceMetadataProxy = new MySQLVmInstanceMetadataProxy();
	VmVirtualInterfaceProxy vmVirtualInterfaceProxy = new MySQLVmVirtualInterfaceProxy();
	
	ClusterProxy clusterProxy = new MySQLClusterProxy();
	VmZoneProxy vmZoneProxy = new MySQLVmZoneProxy();
	
	@Override
	public String createVM(String name, String userId, String imgId,
			Integer flavorId, Integer sgId, Integer avalibilityZoneId,
			Map<String, String> metadata) throws RpcException {

		logger.info(name + ", " + userId + ", " + imgId);
		
		// Stage 0 Generate uuid
		String uuid = UuidUtil.getRandomUuid();

		// Stage 1 Select available host
		VmInstanceType instanceType = null;
		try {
			instanceType = vmInstanceTypeProxy.getById(flavorId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		List<Host> hosts = null;
//		try {
//			hosts = (List<Host>) hostProxy.findAll(false, false, false);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		
		List<Host> hosts = vmScheduler.selectHost(uuid, instanceType.getFlavorUuid(),
				avalibilityZoneId.toString());

		logger.info("hosts: " + hosts);
		
		// Stage 2 Get Image
		Integer imageId = Integer.parseInt(imgId);
		VmImage image = null;
		try {
			image = vmImageProxy.getById(imageId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Stage 3 Save VM instance
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		VmInstance instance = null;
		try {
			vmInstanceProxy.save(new VmInstance(null, uuid, name, Integer
					.parseInt(userId), hosts.get(0).getUuid(), 
					hosts.get(0).getClusterId(),
					avalibilityZoneId,
					timeStamp, timeStamp, timeStamp, 10,
					VmStatusEnum.BUILDING, TaskStatusEnum.SCHEDULING, imgId,
					instanceType.getId(), ROOT_DEVICE_LOCATION, sgId,
					image.getOsMode(), image.getOsArch(),
					image.getOsType()));

			instance = vmInstanceProxy.getByUuid(uuid, false, false, false,
					false, false, false, false, false);
			System.out.println(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Stage 4 Create Volume
		
		VmZone zone = null;
		try {
			zone = vmZoneProxy.getById(avalibilityZoneId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<VmVolume> volumes = VmDiskManager.getInstance().createDisk(Integer.parseInt(userId), image.getUuid(), zone, hosts.get(0), instanceType.getLocalGb(), image.getSize());
		logger.info("volumes: " + volumes);
		
		// Stage 5 Save Metadatas

		List<VmInstanceMetadata> instanceMetadatas = null;
		try {
			for (String key : metadata.keySet()) {
				vmInstanceMetadataProxy.save(new VmInstanceMetadata(null,
						instance.getId(), key, metadata.get(key)));
			}

			instanceMetadatas = (List<VmInstanceMetadata>) vmInstanceMetadataProxy
					.getByVmInstanceId(instance.getId(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Stage 6 Create network
		Entry<String, String> publicIpAddr = networkProvider
				.getNewPublicIpAddress(hosts.get(0).getClusterId());
		Entry<String, String> privateIpAddr = networkProvider
				.getNewPrivateIpAddress(hosts.get(0).getClusterId());

		logger.info("publicIpAddr: " + publicIpAddr);
		logger.info("privateIpAddr: " + privateIpAddr);
		
		try {
			vmVirtualInterfaceProxy.save(new VmVirtualInterface(null, instance.getUuid(), publicIpAddr.getValue(), publicIpAddr.getKey(), "public", 1));
			vmVirtualInterfaceProxy.save(new VmVirtualInterface(null, instance.getUuid(), privateIpAddr.getValue(), privateIpAddr.getKey(), "private", 1));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<VmVirtualInterface> vifs = null;
		try {
			vifs = (List<VmVirtualInterface>) vmVirtualInterfaceProxy.getByInstanceUuid(instance.getUuid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Stage 7 Save Metadatas
		List<VmSecurityGroupIngressRule> securityGroupRules = null;
		// Stage 8 Create VM 
		vmScheduler.createVM(uuid, instanceType.getFlavorUuid(), instanceMetadatas,
				volumes, securityGroupRules, vifs);

		return uuid;
	}

	@Override
	public void startVM(String uuid) throws RpcException {
		VmInstance instance = null;
		try {
			instance = vmInstanceProxy.getByUuid(uuid, false, false,
					false, true, true, true, true, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		VmInstanceType instanceType = instance.getVmInstanceType();
		instance.setVmInstanceType(null);

		List<VmInstanceMetadata> instanceMetadatas = instance.getVmInstanceMetadatas();
		instance.setVmInstanceMetadata(null);

		List<VmVolume> volume = instance.getVmVolumes();

		List<VmSecurityGroupIngressRule> securityGroupRules = instance
				.getVmSecurityGroupIngressRules();
		instance.setVmSecurityGroupIngressRules(null);

		List<VmVirtualInterface> vifs = instance.getVmVirtualInterfaces();

		vmScheduler.startVM(instance, instanceType, instanceMetadatas, volume,
				securityGroupRules, vifs);
	}

	@Override
	public void stopVM(String uuid) throws RpcException {
		vmScheduler.stopVM(uuid);
	}

	@Override
	public void resumeVM(String uuid) throws RpcException {
		vmScheduler.resumeVM(uuid);
	}

	@Override
	public void rebootVM(String uuid) throws RpcException {
		vmScheduler.rebootVM(uuid);
	}

	@Override
	public void rebuildVM(String name, String imgId, Integer flavorId,
			Integer sgId, Map<String, String> metadata) throws RpcException {
		// vmScheduler.
		// !!!Need to be done.
//		createVM(name, imgId, flavorId, sgId, avalibilityZoneId, metadata);
	}

	@Override
	public void resizeVM(String uuid, Integer flavorId){
		// vmScheduler.
		// !!!Need to be done.
	}

	@Override
	public void suspendVM(String uuid) throws RpcException {
		vmScheduler.suspendVM(uuid);
	}

	@Override
	public void forceDeleteVM(String uuid) throws RpcException {
		vmScheduler.deleteVM(uuid);
	}

	@Override
	public void terminateVM(String uuid) throws RpcException {
		// vmScheduler.
		// !!!Need to be done.
	}
}
