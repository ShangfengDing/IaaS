package appcloud.openapi.manager.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcGroup;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AcMailConf;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.beans.AcService;
import appcloud.api.beans.AcUser;
import appcloud.api.beans.AcVlan;
import appcloud.api.beans.AddressPool;
import appcloud.api.beans.AddressPool.IPUsage;
import appcloud.api.beans.Aggregate;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.beans.Backup;
import appcloud.api.beans.Enterprise;
import appcloud.api.beans.EnterpriseInvitation;
import appcloud.api.beans.Flavor;
import appcloud.api.beans.IP;
import appcloud.api.beans.Image;
import appcloud.api.beans.IpRange;
import appcloud.api.beans.Load;
import appcloud.api.beans.Network;
import appcloud.api.beans.Resource;
import appcloud.api.beans.Rule;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.Snapshot;
import appcloud.api.beans.Volume;
import appcloud.api.beans.VolumeAttachment;
import appcloud.api.constant.ImageMetadata;
import appcloud.api.constant.VolumeMetadata;
import appcloud.api.enums.AcHostStatusEnum;
import appcloud.api.enums.AcHostTypeEnum;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.api.enums.AcServiceStatusEnum;
import appcloud.api.enums.AcServiceTypeEnum;
import appcloud.api.enums.AcStrategyTypeEnum;
import appcloud.api.enums.AcVlanTypeEnum;
import appcloud.api.enums.AcVolumeTypeEnum;
import appcloud.api.enums.AttachStatusEnum;
import appcloud.common.model.Cluster;
import appcloud.common.model.HostLoad;
import appcloud.common.model.MailConf;
import appcloud.common.model.MessageLog;
import appcloud.common.model.MessageLog.LogLevelEnum;
import appcloud.common.model.MessageLog.ModuleEnum;
import appcloud.common.model.ResourceStrategy;
import appcloud.common.model.Service;
import appcloud.common.model.VmEnterprise;
import appcloud.common.model.VmEnterpriseInvitation;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmIpSegMent;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmSnapshot;
import appcloud.common.model.VmUsedIp;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVlan;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmZone;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.datatype.SecurityGroupDetailItem;
import appcloud.openapi.datatype.SecurityGroupRuleDetailItem;
import appcloud.openapi.datatype.SnapshotDetailItem;

/**
 * @author hgm
 *
 */
public class BeansGenerator {
	private static BeansGenerator generator = new BeansGenerator();
	private static Map<String, String> ipServerMap = new ConcurrentHashMap<String, String>();
	
	public static BeansGenerator getInstance() {
		return generator;
	}
	
	private BeansGenerator() {
		super();
	}
	
	private void updateServerIpCache(String name,
			List<VmVirtualInterface> vmVirtualInterfaces) {
		for (VmVirtualInterface vif : vmVirtualInterfaces) {
			if(vif.getAddress() != null && !vif.getAddress().equals(""))
				ipServerMap.put(vif.getAddress().trim(), name);
		}
		
	}

	public Aggregate clusterToAggregate(Cluster cluster, boolean withHosts) {
		if(cluster == null)
			return null;
		Aggregate agt = new Aggregate();
		agt.createdAt = cluster.getCreatedTime();
		agt.deleted = false;
		agt.updatedAt = cluster.getUpdatedTime();
		agt.id = cluster.getId();
		agt.name = cluster.getName();
		//FIXME: fake zone
		agt.availabilityZone = "beijing";
		if (withHosts) {
			for (appcloud.common.model.Host host : cluster.getHosts()) {
				agt.hosts.add(host.getIp());
			}
		}
		
		return agt;
	}
	
	public Image VPIToImage(VmImage image, boolean detailed) {
		if(image == null)
			return null;
		Image img = new Image();
		img.name = image.getName();
		img.tenantId = image.getUserId().toString();
		img.id = image.getUuid();
		if (detailed) {
			img.created = image.getCreatedTime();
			img.minDisk = image.getMinDisk();
			img.minRam = image.getMinRam();
			img.status = image.getStatus().toString();
			img.progress = 100;
			img.distribution = image.getDistribution();
			img.md5sum = image.getMd5sum();
			img.metadata = new HashMap<String, String>();
			img.metadata.put(ImageMetadata.DISPLAY_NAME, image.getDisplayName());
			img.metadata.put(ImageMetadata.DISPLAY_DESCRIPTION, image.getDisplayDescription());
			img.metadata.put(ImageMetadata.GROUP_ID, String.valueOf(image.getGroupId()));
			img.metadata.put(ImageMetadata.SIZE, String.valueOf(image.getSize()));
		}
		
		return img;
	}
	
	public Flavor instanceTypeToFlavor(VmInstanceType type) {
		Flavor flavor = new Flavor();
		flavor.id = type.getId();
		flavor.name = type.getName();
		//TODO Flavor is all public
		flavor.tenantId = "public";
		flavor.disk = type.getLocalGb();
		flavor.ram = type.getMemoryMb();
		flavor.vcpus = type.getVcpus();
		
		return flavor;
	}

	private HashMap<String, String> buildMetadata(List<VmInstanceMetadata> metas) {
		if (metas == null) {
			return null;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for (VmInstanceMetadata meta : metas) {
			map.put(meta.getKey(), meta.getValue());
		}
		
		return map;
	}
	
	private List<Network> vifToNetworkInfo(List<VmVirtualInterface> vifs) {
		List<Network> networks = new ArrayList<Network>();

		if (vifs == null) {
			return networks;
		}
				
		Network ntPublic = new Network();
		Network ntPrivate = new Network();
		for (VmVirtualInterface vif : vifs) {
			IP ip = new IP();
			ip.addr = vif.getAddress();
			ip.versions = 4;
			if (vif.getNetworkType().equalsIgnoreCase("private")) {
				ntPrivate.ips.add(ip);
			} else {
				ntPublic.ips.add(ip);
			}
		}
		
		ntPublic.id = "public";
		ntPrivate.id = "private";
		
		networks.add(ntPublic);
		networks.add(ntPrivate);
		
		return networks;		
	}
	
	private Flavor genFlavorWithIdOnly(Integer id, String tenantId) {
		Flavor flavor = new Flavor();
		flavor.id = id;
		flavor.tenantId = tenantId;
		
		return flavor;
	}
	
	private Image genImageWithIdOnly(String id, String tenantId) {
		Image image = new Image();
		image.id = id;
		image.tenantId = tenantId;
		
		return image;
	}
	
	
	
	
	public appcloud.api.beans.Host hostToHost(appcloud.common.model.Host vmHost, boolean detailed) {
		if(vmHost == null)
			return null;
		
		appcloud.api.beans.Host apiHost = new appcloud.api.beans.Host();
		apiHost.hostName = vmHost.getUuid();//.getName();
		apiHost.zone = "1";//Integer.toString(vmHost.getAvailabilityZoneId());
		//apiHost.service = vmHost.getCapability();vmHost.get
		if(detailed){
			List<Resource> resources = new ArrayList<Resource>();
			resources.add(new  Resource("(total)", vmHost.getMemoryMb(), vmHost.getName(), vmHost.getCpuCore(), vmHost.getDiskMb()/1024));
			apiHost.resource = resources;
		}
		return apiHost;
	}
	
	
	public Snapshot vmSnapshotToSnapshot(VmSnapshot vmSnapshot, String tenantId, boolean detailed) {
		if(vmSnapshot == null)
			return null;
		
		Snapshot apiSnapshot = new Snapshot(vmSnapshot.getUuid(), vmSnapshot.getName(), tenantId, 
				vmSnapshot.getDisplayDescription(), vmSnapshot.getVolumeUuid(), 
				vmSnapshot.getStatus().toString(), vmSnapshot.getVolumeSize(), vmSnapshot.getCreatedTime(), null, null);;
		return apiSnapshot;
	}
	
	public Volume vmVolumeToVolume(VmVolume vmVolume, boolean detailed) {
		if(vmVolume == null)
			return null;
		
		Volume apiVolume = null;
		VolumeAttachment attachemnt = null;
		if(detailed){
			HashMap<String, String> metadata = new HashMap<String, String>();
			attachemnt = new VolumeAttachment("dev/" + vmVolume.getMountPoint(), vmVolume.getInstanceUuid(),
					vmVolume.getVolumeUuid(), vmVolume.getVolumeUuid());
			
			AcVolumeTypeEnum acVolumeType = null;
			if(vmVolume.getBackupVolumeUuid() != null)
				acVolumeType = AcVolumeTypeEnum.BACKUP;
			else if(vmVolume.getUsageType() != null)
				acVolumeType = AcVolumeTypeEnum.valueOf(vmVolume.getUsageType().name());
			metadata.put(VolumeMetadata.AGGREGATE_ID, vmVolume.getAvailabilityClusterId().toString());
			
			metadata.put("userId", vmVolume.getUserId().toString());
			metadata.put("hostUuid",vmVolume.getHostUuid().toString());
			
			apiVolume = new Volume(vmVolume.getVolumeUuid(), vmVolume.getDisplayName(), vmVolume.getDisplayDescription(),
					String.valueOf(vmVolume.getStatus()), vmVolume.getSize(), String.valueOf(vmVolume.getVolumeType()),
					Integer.toString(vmVolume.getAvailabilityZoneId()), metadata,
					"None", attachemnt, vmVolume.getCreatedTime(), acVolumeType) ;
		}
		else {
			apiVolume = new Volume(vmVolume.getVolumeUuid(), vmVolume.getDisplayName(), 
					attachemnt,new HashMap<String, String>()) ;
		}
		return apiVolume;
	}
	
	public VolumeAttachment volumeToVolumeAttachment(VmVolume vmVolume, boolean detailed) {
		VolumeAttachment attachment = new VolumeAttachment("dev/" + vmVolume.getMountPoint(), vmVolume.getInstanceUuid(),
				vmVolume.getVolumeUuid(), vmVolume.getVolumeUuid());
		return attachment;
	}
	
	public Load vmLoadToLoad(HostLoad vmLoad) {
		return new Load(vmLoad.getTime(), vmLoad.getCpuPercent(), vmLoad.getAvgLoad(),
				vmLoad.getMemPercent(), vmLoad.getDiskPercent(), vmLoad.getNetInPercent(),
				vmLoad.getNetOutPercent(), vmLoad.getDiskReadRate(), vmLoad.getDiskWriteRate());
	}
	public SecurityGroup VmSecurityGroupToSecurityGroup(VmSecurityGroup vmGroup, boolean withRules) {
		if(vmGroup == null)
			return null;
		List<Rule> apiRules = new ArrayList<Rule>();
		if(withRules){
			for(VmSecurityGroupRule vmRule : vmGroup.getVmSecurityGroupRules()){
				apiRules.add(VmSecurityGroupRuleToRule(vmRule, false));
			}
		}
		SecurityGroup apiGroup = new SecurityGroup(vmGroup.getId(), vmGroup.getName(), 
				Integer.toString(vmGroup.getUserId()), apiRules, vmGroup.getDescription());
		return apiGroup;
	}
	
	public Rule VmSecurityGroupRuleToRule(VmSecurityGroupRule vmRule, boolean withGroup) {
		if(vmRule == null)
			return null;
		SecurityGroup apiGroup;
		if(withGroup) {
			apiGroup = VmSecurityGroupToSecurityGroup(vmRule.getVmSecurityGroup(), false);
		}
		else
			apiGroup = new SecurityGroup();
		Integer portStart = null;
		if(vmRule.getPortStart() != null)
			portStart = Integer.valueOf(vmRule.getPortStart());
		Integer portEnd = null;
		if (vmRule.getPortEnd() != null)
			portEnd = Integer.valueOf(vmRule.getPortEnd());
		Rule apiRule = new Rule(vmRule.getId(), vmRule.getGroupId(), portStart,
				portEnd, vmRule.getProtocol(), apiGroup, new IpRange(vmRule.getIpRange()));
		return apiRule;
	}

	public AvailabilityZone VMZoneToAVZone(VmZone zone) {
		if (zone == null) return null;
		
		AvailabilityZone aZone = new AvailabilityZone();
		
		aZone.createdAt = zone.getCreatedTime();
		aZone.updatedAt = zone.getUpdatedTime();
		aZone.id = zone.getId();
		aZone.name = zone.getName();
		
		return aZone;
	}

	public AddressPool ipSegmentToAddressPool(VmIpSegMent segment, List<VmUsedIp> usedIps) {
		AddressPool pool = new AddressPool();
		
		//FIXME pool.aggregateId = segment.get
		pool.dns = segment.getDns();
		pool.gateway = segment.getRouter();
		if (segment.getSegment() == 0) {
			pool.type = "private";
		} else {
			pool.type = "public";
		}
		pool.ipStart = segment.getIpStartRange();
		pool.ipEnd = segment.getIpEndRange();
		pool.netmask = segment.getNetMask();
		pool.subnet = segment.getIpSeg();
		pool.tenantId = "admin";
		pool.id = segment.getId();
		pool.aggregateId = segment.getDhcpId();
		
		if (usedIps != null) {
			List<IPUsage> ips = new ArrayList<IPUsage>();
			
			for (VmUsedIp usedIp : usedIps) {
				IPUsage ip = new IPUsage();
				ip.ipAddress = usedIp.getIpAddr();
				ip.macAddress = usedIp.getMac();
				ip.status = "USED";
				if (ipServerMap.containsKey(ip.ipAddress.trim())) {
					ip.serverName = ipServerMap.get(ip.ipAddress.trim());
				} else {
					ip.serverName = "Unknown";
				}
				
				ips.add(ip);
			}
			
			pool.ips = ips;
		}
		
		return pool;
	}

	public Backup volumeToBackup(VmVolume vol) {
		if(vol == null)
			return null;
		Backup backup = new Backup();
		backup.createdAt = vol.getCreatedTime();
		backup.displayDescription = vol.getDisplayDescription();
		backup.displayName = vol.getDisplayName();
		backup.size = vol.getSize();
		backup.status = String.valueOf(vol.getStatus());
		backup.tenantId = String.valueOf(vol.getUserId());
		
		// attention to thoses ids
		backup.id = vol.getVolumeUuid();
		backup.volumeId = vol.getBackupVolumeUuid();
		
		// added 2013.6.4 attention to instance name
		if(vol.getAttachStatus() != null)
		backup.attachStatus = AttachStatusEnum.valueOf(vol.getAttachStatus().name());
		backup.instanceId = vol.getInstanceUuid();
		
		return backup;
	}

	public AcHost hostToAcHost(appcloud.common.model.Host vmHost) {
		if(vmHost == null)
			return null;
		AcHostTypeEnum hostType = null;
		try{
			hostType = AcHostTypeEnum.valueOf(vmHost.getType().name());
		}catch(Exception ex){
			;
		}
		
		AcHostStatusEnum hostStatus = null;
		try{
			hostStatus = AcHostStatusEnum.valueOf(vmHost.getStatus().name());
		}catch(Exception ex){
			;
		}
		
		AcHost acHost = new AcHost(vmHost.getUuid(), vmHost.getIp(),
				hostType, hostStatus);
		
		acHost.location = vmHost.getLocation();
		if(vmHost.getPrivateVLAN() == null)
			acHost.privateVlanId = -1;
		else
			acHost.privateVlanId = vmHost.getPrivateVLAN();
		if(vmHost.getPublicVLAN() == null)
			acHost.publicVlanId = -1;
		else
			acHost.publicVlanId = vmHost.getPublicVLAN();
		acHost.aggregateId = vmHost.getClusterId();
		acHost.availabilityZoneId = vmHost.getAvailabilityZoneId();
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(new  Resource("(total)", vmHost.getMemoryMb(), vmHost.getName(), vmHost.getCpuCore(), vmHost.getDiskMb()/1024));
		acHost.resources = resources;
		return acHost;
	}
	
	public AcAggregate clusterToAcAggregate(Cluster cluster) {
		if(cluster == null)
			return null;
		AcAggregate acAggregate = new AcAggregate();
		acAggregate.createdAt = cluster.getCreatedTime();
		acAggregate.deleted = false;
		acAggregate.updatedAt = cluster.getUpdatedTime();
		acAggregate.id = cluster.getId();
		acAggregate.name = cluster.getName();
		acAggregate.resrcStrategyUuid = cluster.getResrcStrategyUuid();
		acAggregate.cpu_oversell = cluster.getCpuOversell();
		acAggregate.memory_oversell = cluster.getMemoryOversell();
		acAggregate.disk_oversell = cluster.getDiskOversell();
		
		acAggregate.privateVlanId = cluster.getPrivateVLAN();
		acAggregate.publicVlanId = cluster.getPublicVLAN();
		
		return acAggregate;
	}
	
	public AcService serviceToAcService(Service service) {
		if(service == null)
			return null;
		AcServiceTypeEnum serviceType = null;
		try{
			serviceType = AcServiceTypeEnum.valueOf(service.getType().name());
		}catch(Exception ex){
			;
		}
		AcServiceStatusEnum status = null;
		try{
			status = AcServiceStatusEnum.valueOf(service.getServiceStatus().name());
		}catch(Exception ex){
			;
		}
		AcService acService = new AcService(service.getId(), service.getHostId(), service.getHostUuid(),
				service.getZoneId(), service.getHostIp(), service.getMonitorPort(),
				serviceType, service.getUpdateTime(),
				service.getLastStartTime() , service.getLastStopTime() , status);
		return acService;
	}
	
	public AcGroup vmGroupToAcGroup(VmGroup vmGroup){
		if(vmGroup == null)
			return null;
		String[] clusters = vmGroup.getAvailableClusters().split(",");
		List<Integer> availableClusters = new ArrayList<Integer>();
		for(int i = 0; i < clusters.length; i++) { 
			availableClusters.add(new Integer(clusters[i]));
		}
		AcGroup acGroup = new AcGroup(vmGroup.getId(), vmGroup.getName(),
				vmGroup.getSelectCluster(), vmGroup.getPublishiImage(),
				vmGroup.getMaxNumberOfInstance(), vmGroup.getMaxNumberOfDisk(),
				vmGroup.getMaxNumberOfBackup(), vmGroup.getMaxNumberOfSnapshot(),
				availableClusters, vmGroup.getDescription());
		return acGroup;
	}
	
	public AcResourceStrategy resourceStrategyToAcResourceStrategy(ResourceStrategy resourceStrategy) {
		if(resourceStrategy == null)
			return null;
		AcStrategyTypeEnum strategyType = null;
		try {
			strategyType = AcStrategyTypeEnum.valueOf(resourceStrategy.getType().name());
		} catch (Exception ex) {
			;
		}
		AcResourceStrategy acResourceStrategy = new AcResourceStrategy(resourceStrategy.getId(), resourceStrategy.getUuid(),
				strategyType, resourceStrategy.getName(), resourceStrategy.getDescription(), resourceStrategy.getClazzs(),
				resourceStrategy.getParams(), resourceStrategy.getTime());
		return acResourceStrategy;
	}
	
	public AcVlan vmVlanToAcVlan(VmVlan vmVlan) {
		if(vmVlan == null)
			return null;
		AcVlanTypeEnum vlanType = null;
		try {
			vlanType = AcVlanTypeEnum.valueOf(vmVlan.getType().name());
		}
		catch(Exception ex){
			;
		}
		AcVlan acVlan = new AcVlan(vmVlan.getId(), vmVlan.getName(), 
				vlanType, vmVlan.getDescription()) ;
		return acVlan;
	}
	
	public AcMessageLog MessageLogToAcMessageLog(MessageLog messageLog) {
		if(messageLog == null)
			return null;
		AcModuleEnum module = null;
		if(messageLog.getModule() != null)
			module = AcModuleEnum.valueOf(messageLog.getModule().name());
		AcLogLevelEnum logLevel = null;
		if(messageLog.getLogLevel() != null)
			logLevel = AcLogLevelEnum.valueOf(messageLog.getLogLevel().name());
		return new AcMessageLog(module, messageLog.getTranctionId(), 
				messageLog.getUserId(),messageLog.getVmUuid(),
				messageLog.getOperateDrpt(), messageLog.getLogContent(),
				messageLog.getSourceClass(),messageLog.getIpAddress(), 
				logLevel, messageLog.getLogTime());
	}
	
	public MessageLog AcMessageLogToMessageLog(AcMessageLog log) {
		if(log == null)
			return null;
		ModuleEnum module = null;
		if(log.module != null)
			module = ModuleEnum.valueOf(log.module.name());
		LogLevelEnum logLevel = null;
		if(log.logLevel != null)
			logLevel = LogLevelEnum.valueOf(log.logLevel.name());
		return new MessageLog(module, log.transactionId, log.userId, log.vmHdUuid, 
				log.operateDrpt, log.logContent, log.sourceClass,
				log.ipAddress, logLevel);
	}
	
	public AcMailConf mailConfToAcMailConf(MailConf mailConf) {
		if(mailConf == null)
			return null;
		return new AcMailConf(mailConf.getLolEmail(), mailConf.getEmailHost(), mailConf.getEmailFrom(),
				mailConf.getEmailPassword(), mailConf.getEmailSubject(),
				mailConf.getModuleInCharge());
	}
	
	public MailConf AcMailConfToMailConf(AcMailConf acMailConf) {
		if(acMailConf == null)
			return null;
		MailConf mailConf =  new MailConf();
		
		if(acMailConf.emailFrom != null && acMailConf.emailHost != null 
				&& acMailConf.emailPassword != null && acMailConf.emailSubject != null) {	
			mailConf.setEmailFrom(acMailConf.emailFrom);
			mailConf.setEmailHost(acMailConf.emailHost);
			mailConf.setEmailPassword(acMailConf.emailPassword);
			mailConf.setEmailSubject(acMailConf.emailSubject);
		}
		mailConf.setLolEmail(acMailConf.lolEmail);
		mailConf.setModuleInCharge(acMailConf.moduleInCharge);
		return mailConf;
	}
	
	public AcGroup groupToAcGroup(VmGroup group) {
		if (group == null)
			return null;
		AcGroup acGroup = new AcGroup(group.getId(), group.getName(), group.getSelectCluster(),  group.getPublishiImage(),
				group.getMaxNumberOfInstance(), group.getMaxNumberOfDisk(),
				group.getMaxNumberOfBackup() , group.getMaxNumberOfSnapshot() ,
				null, group.getDescription() );
		String[] clusterIdStr = group.getAvailableClusters().split(",");
		List<Integer> clusterIds = new ArrayList<Integer> ();
		try {
			for(String clusterId : clusterIdStr)
				clusterIds.add(Integer.valueOf(clusterId));
		}catch (Exception ex) {
			;
		}
		acGroup.availableClusters = clusterIds;
		return acGroup;
	}
	
	public VmGroup acGroupToVmGroup(AcGroup acGroup) {
		if(acGroup == null)
			return null;
		String availableClusters = null;
		List<Integer> clusterIds = acGroup.availableClusters;
		if( clusterIds == null || clusterIds.size() == 0)
			availableClusters = "";
		else if(clusterIds.size() == 1) {
			availableClusters = clusterIds.get(0).toString();
		}
		else {
			availableClusters = clusterIds.get(0).toString();
			for(int i = 1; i < clusterIds.size(); i ++)
				availableClusters += "," + clusterIds.get(i).toString();
		}
		VmGroup vmGroup = new VmGroup(acGroup.id, acGroup.name, acGroup.selectCluster, acGroup.publishImage,
				acGroup.maxNumberOfInstance, acGroup.maxNumberOfDisk,
				acGroup.maxNumberOfBackup, acGroup.maxNumberOfSnapshot,
				availableClusters, acGroup.description);
		
		return vmGroup;
	}
	
	public Enterprise vmEnterpriseToEnterprise(VmEnterprise vmEnterprise)  {
		if(vmEnterprise == null)
			return null;
		Enterprise enterprise = new Enterprise(
				vmEnterprise.getId(), vmEnterprise.getOwnerId(), vmEnterprise.getActive(),
				vmEnterprise.getName(), vmEnterprise.getDescription(), vmEnterprise.getPhone(),
				vmEnterprise.getEmail(), vmEnterprise.getAddress(), vmEnterprise.getPostcode(),
				vmEnterprise.getHomepage(), vmEnterprise.getParentCompanyId());
		return enterprise;
	}
	
	public EnterpriseInvitation vmEnterpriseInvitationToEnterpriseInvitation(VmEnterpriseInvitation vmEnterpriseInvitation) {
		if(vmEnterpriseInvitation == null) 
			return null;
		EnterpriseInvitation enterpriseInvitation = new EnterpriseInvitation(
				vmEnterpriseInvitation.getId(), vmEnterpriseInvitation.getEnterpriseId(),
				vmEnterpriseInvitation.getUserId(), vmEnterpriseInvitation.getInformation(),
				vmEnterpriseInvitation.getStatus());
		return enterpriseInvitation;
	}
	
	public AcUser userToAcUser(VmUser user) {
		if(user == null)
			return null;
		return new AcUser(user.getUserId().toString(), user.getUserEmail(), user.getActive(), user.getGroupId(), user.getEnterpriseId());
	}
	
	
	public DiskDetailItem volumeToDiskDetailItem (Volume volume) {
		DiskDetailItem disk = new DiskDetailItem();
		disk.setDiskId(volume.id);
		disk.setDiskName(volume.displayName);
		disk.setDescription(volume.displayDescription);
		disk.setSize(volume.size);
		disk.setCreateTime(volume.createdAt);
		disk.setDiskCategory(volume.volumeType);
		disk.setDiskType(volume.acVolumeType.toString());
		disk.setSnapshotId(volume.snapshotId);
		disk.setStatus(volume.status);
		disk.setZoneId(volume.availabilityZone);
		
		if (volume.attachments != null) {
			disk.setInstanceId(volume.attachments.serverId);
			disk.setDevice(volume.attachments.device);
		}
		
		return disk;
	}
	
	public SnapshotDetailItem snapshotToSnapshotDetailItem (Snapshot snapshot) {
		SnapshotDetailItem snapshotDetailItem = new SnapshotDetailItem();
		snapshotDetailItem.setSnapshotId(snapshot.id);
		snapshotDetailItem.setSnapshotName(snapshot.displayName);
		snapshotDetailItem.setDescription(snapshot.displayDescription);
		snapshotDetailItem.setDiskId(snapshot.volumeId);
		snapshotDetailItem.setStatus(snapshot.status);
		snapshotDetailItem.setCreateTime(snapshot.createdAt);
		snapshotDetailItem.setSize(snapshot.size);
		
		return snapshotDetailItem;
		
	}

	public SecurityGroupDetailItem securityGroupToSecurityGroupDetailItem(
			SecurityGroup securityGroup) {
		SecurityGroupDetailItem securityGroupDetailItem = new SecurityGroupDetailItem();
		securityGroupDetailItem.setSecurityGroupId(String.valueOf(securityGroup.id));
		securityGroupDetailItem.setSecurityGroupName(securityGroup.name);
		securityGroupDetailItem.setDescription(securityGroup.description);
		return securityGroupDetailItem;
	}
	
	public SecurityGroupRuleDetailItem ruleToSecurityGroupRuleDetailItem(
			Rule rule) {
		SecurityGroupRuleDetailItem ruleDetailItem = new SecurityGroupRuleDetailItem();
		ruleDetailItem.setIpProtocol(rule.ipProtocal);
		ruleDetailItem.setSourceCidrIp(rule.ipRange.cidr);
		ruleDetailItem.setPortRange(rule.fromPort+"/"+rule.toPort);
		ruleDetailItem.setPolicy("accept");
		return ruleDetailItem;
	}
}
