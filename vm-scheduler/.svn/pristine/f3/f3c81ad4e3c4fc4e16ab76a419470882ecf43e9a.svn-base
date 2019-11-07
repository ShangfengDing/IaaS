package appcloud.vmscheduler.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import appcloud.common.model.Cluster;
import appcloud.common.model.HostLoad;
import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceStatus;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.HostLoadProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.ResourceStrategyProxy;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.proxy.VmInstanceMetadataProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.proxy.VmSecurityGroupRuleProxy;
import appcloud.common.proxy.VmVirtualInterfaceProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

public class DBAgent {
	private static DBAgent dbAgent;
	private static Logger logger = Logger.getLogger(DBAgent.class);
	
	//database proxy 封装
	private VmInstanceProxy instanceProxy = null;
	private VmInstanceTypeProxy instanceTypeProxy = null;
	private VmVolumeProxy volumeProxy = null;
	//添加1：private HostProxy
	private ServiceProxy serviceProxy = null;
	private HostLoadProxy hostLoadProxy = null;
	private VmSecurityGroupRuleProxy SGRuleProxy = null;
	private VmSecurityGroupProxy SGProxy = null;
	private VmInstanceMetadataProxy MDProxy = null;
	private VmVirtualInterfaceProxy VIProxy = null;
	
	private ClusterProxy clusterProxy = null;
	private ResourceStrategyProxy resourceStrategyProxy = null;
	
	private HostProxy hostProxy=null; 
	
	public static DBAgent getInstance() {
		if (dbAgent == null) {
			dbAgent = new DBAgent();
		}
		return dbAgent;
	}
	
	public DBAgent() {
		hostProxy=(HostProxy) ConnectionFactory.getDBProxy(HostProxy.class);
		instanceProxy = (VmInstanceProxy) ConnectionFactory.getDBProxy(VmInstanceProxy.class);
		volumeProxy = (VmVolumeProxy) ConnectionFactory.getDBProxy(VmVolumeProxy.class);
		instanceTypeProxy = (VmInstanceTypeProxy) ConnectionFactory.getDBProxy(VmInstanceTypeProxy.class);
		hostLoadProxy = (HostLoadProxy) ConnectionFactory.getDBProxy(HostLoadProxy.class);
		serviceProxy = (ServiceProxy) ConnectionFactory.getDBProxy(ServiceProxy.class);
		SGRuleProxy = (VmSecurityGroupRuleProxy) ConnectionFactory.getDBProxy(VmSecurityGroupRuleProxy.class);
		SGProxy = (VmSecurityGroupProxy) ConnectionFactory.getDBProxy(VmSecurityGroupProxy.class);
		MDProxy = (VmInstanceMetadataProxy) ConnectionFactory.getDBProxy(VmInstanceMetadataProxy.class);
		VIProxy = (VmVirtualInterfaceProxy) ConnectionFactory.getDBProxy(VmVirtualInterfaceProxy.class);
		
		clusterProxy = (ClusterProxy) ConnectionFactory.getDBProxy(ClusterProxy.class);
		resourceStrategyProxy = (ResourceStrategyProxy) ConnectionFactory.getDBProxy(ResourceStrategyProxy.class);
	}
	
	public Cluster getClusterById(Integer id) {
		Cluster cluster = null;
		try {
			cluster = clusterProxy.getById(id, true, false, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cluster;
	}
	
	public Host getHostByUuid(String uuid){
		Host it=null;
		try{
			it=hostProxy.getByUuid(uuid,false, false, false);
		}catch (Exception e) {
			logger.info("can not get host", e);
		}
		return it;
	}
	
	public VmInstance getVmInstance(String instanceUUID) {
		VmInstance it = null;
		try {
			/** 
		    * @Title: 通过uuid获取某个实例信息
		    * @param uuid
		    * @param withHost   是否同时获得实例所属主机的信息
		    * @param withCluster   是否同时获得实例所属集群的信息
		    * @param withVmPublicImage   是否同时获得实例所属公共镜像的信息
		    * @param withVmInstanceType    是否同时获得实例所属套餐的信息
		    * @param withVmInstanceMetadata		是否同时获得元数据列表信息
		    * @param withVolume		是否同时获得卷信息
		    * @param withVmNetWork  是否同时获得实例所属网络相关的信息
		    * @param withVmSecurityGroup  是否同时获得实例所属防火墙的信息
		    * @return
		    * @throws Exception
		     */
			it = instanceProxy.getByUuid(instanceUUID, true, false, false, false, false, false, false, false);
		} catch (Exception e) {
			logger.info("can not get vm instance", e);
		}
		return it;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<VmInstance> getVmInstanceByHostUuid(String hostUuid) {
		List<VmInstance> instances = null;
		try {
			QueryObject queryObject = new QueryObject<VmInstance>();
			FilterBean hostFilter = new FilterBean<VmInstance>("hostUuid", hostUuid, FilterBeanType.EQUAL);
			List<VmStatusEnum> statusList = Arrays.asList(VmStatusEnum.ACTIVE,
					VmStatusEnum.BUILDING, VmStatusEnum.REBUILDING,
					VmStatusEnum.PAUSED, VmStatusEnum.SUSPENDED,
					VmStatusEnum.RESCUED, VmStatusEnum.STOPPED,
					VmStatusEnum.RESIZING);
			FilterBean statusFilter1 = new FilterBean<VmInstance>("vmStatus", statusList, FilterBeanType.IN);
//			FilterBean statusFilter2 = new FilterBean<VmInstance>("vmStatus", VmStatusEnum.MIGRATING, FilterBeanType.NOTEQUAL);
//			FilterBean statusFilter3 = new FilterBean<VmInstance>("vmStatus", VmStatusEnum.ERROR, FilterBeanType.NOTEQUAL);
			queryObject.addFilterBean(hostFilter);
			queryObject.addFilterBean(statusFilter1);
//			queryObject.addFilterBean(statusFilter2);
//			queryObject.addFilterBean(statusFilter3);
			instances = (List<VmInstance>) instanceProxy.searchAll(queryObject, false, false, false, false, false, false, false, false);
		} catch (Exception e) {
			logger.info("can not get vm instance", e);
		}
		return instances;
	}
	
	//虚拟机创建时调用，获取虚拟机创建时用到的全部实例
	public VmInstance getVmInstanceWithAll(String instanceUUID) {
		VmInstance it = null;
		try {
			/*
			 * 获取：
			 * @param withVmInstanceType    是否同时获得实例所属套餐的信息
			 * @param withVmInstanceMetadata		是否同时获得元数据列表信息
			 * @param withVolume		是否同时获得卷信息
			 * @param withVmNetWork  是否同时获得实例所属网络相关的信息
			 * @param withVmSecurityGroup  是否同时获得实例所属防火墙的信息
			 */
			it = instanceProxy.getByUuid(instanceUUID, false, false, false, true, true, true, true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return it;
	}
	
	public VmInstanceType getVmInstanceType(String instanceTypeUUID) {
		VmInstanceType it = null;
		try {
			it = instanceTypeProxy.getByUuid(instanceTypeUUID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return it;
	}
	
	public VmInstanceType getVmInstanceTypeByID(Integer instanceTypeID) {
		VmInstanceType it = null;
		try {
			it = instanceTypeProxy.getById(instanceTypeID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return it;
	}
	@SuppressWarnings("unchecked")
	public List<VmInstanceMetadata> getMDListByVmInstanceId(Integer instanceID) {
		List<VmInstanceMetadata> it = null;
		try {
			it = (List<VmInstanceMetadata>) MDProxy.getByVmInstanceId(instanceID, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return it;
	}
	
	/**
	 * 获取security group
	 * @param instanceUUID
	 * @return
	 */
	public VmSecurityGroup getVmSecurityGroup(Integer groupId) {
		VmSecurityGroup result = null;
		
		try {
			result = SGProxy.getById(groupId, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 获取security group等于某个id的全部instance信息
	 * @param securityGroup
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VmInstance> getVmInstanceListForSG(VmSecurityGroup securityGroup) {
		QueryObject<VmInstance> queryObject = new QueryObject<VmInstance>();
		FilterBean<VmInstance> fb = new FilterBean<VmInstance>("securityGroupId", 
															   securityGroup.getId(), 
															   FilterBeanType.EQUAL);
		
		queryObject.addFilterBean(fb);
		FilterBean<VmInstance> notDeleted = new FilterBean<VmInstance>("vmStatus", 
				   VmStatusEnum.DELETED, 
				   FilterBeanType.NOTEQUAL);
		queryObject.addFilterBean(notDeleted);
		List<VmInstance> instanceList = null;
		try {
			instanceList = (List<VmInstance>) instanceProxy.searchAll(queryObject, false, false, false, false, false, false, false, false);
		} catch (Exception e) {
			instanceList = new ArrayList<VmInstance>();
			logger.error("search instance table error");	
		}
		
		return instanceList;
	}
	
	/**
	 * 传入某个security group，获取该group下的所有规则
	 * 使用security group id做关联，int
	 * @param securityGroup
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VmSecurityGroupRule> getSGRuleList(VmSecurityGroup securityGroup) {
		List<VmSecurityGroupRule> SGRuleList = null;
		
		try {
			SGRuleList = (List<VmSecurityGroupRule>) SGRuleProxy.getByGroupId(securityGroup.getId(), false);
		} catch (Exception e) {
			logger.error("security group rule Proxy getByGroupId error");
			e.printStackTrace();
		}
		
		return SGRuleList;
	}
	
	public VmVolume getVmVolume(String volueUUID) {
		VmVolume it = null;
		try {
			it = volumeProxy.getByUUID(volueUUID, false, false, false, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return it;
	}
	
	@SuppressWarnings("unchecked")
	public List<VmVolume> getVolumeListByInstanceUUID(String instanceUUID) throws Exception {
		//获取属于某个instance的全部volume信息
		QueryObject<VmVolume> queryObject = new QueryObject<VmVolume>();
		FilterBean<VmVolume> fb = new FilterBean<VmVolume>("instanceUuid", instanceUUID, FilterBeanType.EQUAL);
		queryObject.addFilterBean(fb);
		
		List<VmVolume> volumeList = (List<VmVolume>) volumeProxy.searchAll(queryObject, false, false, false, false);
		return volumeList;
	}
	
	public List<VmVolume> getAttachedVolumeList(String instanceUUID) throws Exception {
		List<VmVolume> volumeList = getVolumeListByInstanceUUID(instanceUUID);
		List<VmVolume> newVolumes = new ArrayList<VmVolume>();
		for (VmVolume vo : volumeList) {
			/*
			 * 筛选符合条件的vo：
			 * 1）已经被绑定的
			 * 2）mountpoint不为空的
			 */
			if(vo.getAttachStatus() != null){
				if (vo.getAttachStatus().equals(VmVolumeAttachStatusEnum.ATTACHED) && 
					vo.getMountPoint() != null && 
					(!vo.getStatus().equals(VmVolumeStatusEnum.DELETED))) {
					newVolumes.add(vo);
				}
			}
		}
		
		return newVolumes;
	}
	
	/**
	 * 获取主机列表，给定一串instance列表，返回这些instance的host列表
	 * @param instanceList
	 * @return
	 */
	public List<String> getHostUUIDList(List<VmInstance> instanceList) {
		HashMap<String, Integer> hostMap = new HashMap<String, Integer>();
		List<String> hostList = new ArrayList<String>();
		
		int value = 1;  //先赋予默认值即可
		
		if (instanceList != null) {
			for (VmInstance vi : instanceList) {
				//如果当前host list中尚未包含hostUUID
				String key = vi.getHostUuid();
				if (key != null && ! hostMap.containsKey(key)) {
					hostMap.put(key, value);
					hostList.add(key);
				}
			}			
		} 
		
		return hostList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> getRunningServiceList(int zoneID, Service.ServiceTypeEnum hostType) {
		//获取某种类型的、属于某个zone的host List
		QueryObject<Service> queryObject = new QueryObject<Service>();
		FilterBean<Service> fb1 = new FilterBean<Service>("zoneId", zoneID, FilterBeanType.EQUAL);
		FilterBean<Service> fb2 = new FilterBean<Service>("type", hostType, FilterBeanType.EQUAL);
		FilterBean<Service> fb3 = new FilterBean<Service>("serviceStatus", ServiceStatus.RUNNING, FilterBeanType.EQUAL);
		
		queryObject.addFilterBean(fb3);
		queryObject.addFilterBean(fb2);
		queryObject.addFilterBean(fb1);
		
		List<Service> hostList = (List<Service>) serviceProxy.searchAll(queryObject, true);
		
		return hostList;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> getRunningServiceListAllCluster(int zoneID, Service.ServiceTypeEnum hostType) {
		//获取某种类型的、属于某个zone的host List
		QueryObject<Service> queryObject = new QueryObject<Service>();
		FilterBean<Service> fb1 = new FilterBean<Service>("zoneId", zoneID, FilterBeanType.EQUAL);
		FilterBean<Service> fb2 = new FilterBean<Service>("type", hostType, FilterBeanType.EQUAL);
		FilterBean<Service> fb3 = new FilterBean<Service>("serviceStatus", ServiceStatus.RUNNING, FilterBeanType.EQUAL);
		
		queryObject.addFilterBean(fb3);
		queryObject.addFilterBean(fb2);
		queryObject.addFilterBean(fb1);
		
		List<Service> quaryHostList = (List<Service>) serviceProxy.searchAll(queryObject, true);
		List<Service> tempHostList = new ArrayList<Service>();
		
		List<Service> hostList = new ArrayList<Service>();
		Set<Integer> tempSet = new HashSet<Integer>();
		if(quaryHostList.size() != 0){
			Random random = new Random();
			int size = random.nextInt(quaryHostList.size());
			int tempSize=size;
			for(; size < quaryHostList.size(); size++){
				tempHostList.add(quaryHostList.get(size));
			}
			size=tempSize;
			for(size = size -1 ; size >= 0; size--){
				tempHostList.add(quaryHostList.get(size));
			}
			for(Service service : tempHostList){
				if(tempSet.add(service.getClusterId())){
					hostList.add(service);
				}
			}
		}
		
		return hostList;		
	}
	public HostLoad getFreshHostLoad(String hostUUID) {
		HostLoad hostLoad = hostLoadProxy.getLatestLoad(hostUUID);		
		return hostLoad;
	}
	
	/**
	 * 更新操作：将新的instance写入数据库，覆盖原有数据
	 * @param instance
	 */
	public void updateVmInstance(VmInstance instance) {
		try {
			instanceProxy.update(instance);
		} catch (Exception e) {
			logger.error("update vm instance error");
			e.printStackTrace();
		}
	}
	
	public void updateVmVolume(VmVolume vo) {
		try {
			volumeProxy.update(vo);
		} catch (Exception e) {
			logger.error("update vm volume error");
			e.printStackTrace();
		}
	}
	
	public void setVMStatus(String instanceUUID, VmStatusEnum status) {
		VmInstance instance = getVmInstance(instanceUUID);
		instance.setVmStatus(status);
		updateVmInstance(instance);		
	}
	
	public void setVMStatus(VmInstance instance, VmStatusEnum status) {
		instance.setVmStatus(status);
		updateVmInstance(instance);		
	}
	
	public void setVMRootDevice(VmInstance instance, String rootDevice) {
		instance.setRootDeviceLocation(rootDevice);
		updateVmInstance(instance);		
	}
	
	public void setVMRootDevice(String instanceUUID, String rootDevice) {
		VmInstance instance = dbAgent.getVmInstance(instanceUUID);
		instance.setRootDeviceLocation(rootDevice);
		updateVmInstance(instance);		
	}
	
	/**
	 * 设置数据库中的volume attachment信息
	 * @param volumeUUID
	 * @param bus
	 * @param dev
	 * @param instanceUUID
	 * @param attachStatus
	 * @return
	 */
	public VmVolume setVolumeAttachment(String volumeUUID, 
										String bus, 
										String dev,
										String instanceUUID,
										VmVolumeAttachStatusEnum attachStatus) {
		//获取当前最新的volume
		VmVolume newVolume = getVmVolume(volumeUUID);
		/*
		 * 设置attach相关的数据：
		 * dev、bus、instanceUUID、attachStatus
		 */
		newVolume.setBus(bus);
		newVolume.setMountPoint(dev);
		newVolume.setInstanceUuid(instanceUUID);
		newVolume.setAttachStatus(attachStatus);
		//持久化进数据库
		updateVmVolume(newVolume);
		
		return newVolume;
	}
	
	/**
	 * 设置数据库中的security group attachment信息
	 * @param instanceUUID
	 * @param securityGroup
	 * @return
	 */
	public VmInstance setSecurityGroupAttachment(String instanceUUID,
												 VmSecurityGroup securityGroup) {
		VmInstance instance = dbAgent.getVmInstance(instanceUUID);
		instance.setSecurityGroupId(securityGroup.getId());
		dbAgent.updateVmInstance(instance);
		
		return instance;
	}

	@SuppressWarnings("unchecked")
    public List<VmVirtualInterface> getVmVirtualInterfaceListByUuid(String uuid) throws Exception {
        return (List<VmVirtualInterface>) VIProxy.getByInstanceUuid(uuid);
    }
	
	@SuppressWarnings("unchecked")
	public List<Host> getHostList ()  throws Exception  { 
		return (List<Host>)hostProxy.findAll(false, false, false);
	}
	
}
