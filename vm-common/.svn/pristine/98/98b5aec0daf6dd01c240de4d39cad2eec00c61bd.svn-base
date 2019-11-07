package appcloud.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appcloud.common.errorcode.RSEC;
import appcloud.common.model.*;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.rpc.ampq.annotation.RPCTimeout;
import appcloud.rpc.tools.RpcException;

/**
 * @author jianglei
 * 
 */
public interface ResourceSchedulerService {

	/* ======================================================================== */
	/* ============================ J2EE Operations =========================== */
	/* ======================================================================== */

	/**
	 * @param app
	 *            app instance to deploy, the instance list will be ignored.
	 *            required fileds are: uuid, max/minMemory, name
	 * @param copyNum
	 *            num of instances to be deployed
	 * @param resrcStrategyId
	 *            id of an specific strategy
	 * @return RSEC.NO_ERROR if all instances were deployed and routing entries
	 *         were added
	 */
	public RSEC createJ2EEApp(J2EEApp app, int copyNum, int resrcStrategyId)
			throws RpcException;

	/**
	 * stop a j2ee app, that's stop all instances and deleting routing entries
	 * 
	 * @param appUuid
	 *            the UUID of app to be stopped
	 * @param waitingTime
	 *            time to wait before actually stop it, not implemented yet.
	 * @return RSEC.NO_ERROR if all instances were stopped and routing entries
	 *         was deleted
	 */
	public RSEC stopJ2EEApp(String appUuid, int waitingTime)
			throws RpcException;

	/**
	 * delete a j2ee app, stop it and delete the war file.
	 * 
	 * @param appUuid
	 *            the UUID of app to be deleted
	 * @param waitingTime
	 *            time to wait before actually stop it, not implemented yet
	 * @param warLocation
	 *            location of the war package of this app
	 * @return RSEC.NO_ERROR if all instances were stopped and routing entries
	 *         was deleted
	 */
	public RSEC deleteJ2EEApp(String appUuid, int waitingTime,
			String warLocation) throws RpcException;

	/**
	 * delete a j2ee instance and associated routing entry.
	 * 
	 * @param instanceUuid
	 *            the UUID of the instance to be deleted
	 * @return RSEC.NO_ERROR if instance was deleted and routing entry was
	 *         removed
	 */
	public RSEC deleteJ2EEInstance(String instanceUuid) throws RpcException;

	/**
	 * @param appId
	 *            id of the app to increase instance num
	 * @param num
	 *            num of new instances to be deployed
	 * @return RSEC.NO_ERROR if all new instances were deployed and routing
	 *         entries were added
	 */
	public RSEC increaseInstanceCopy(String appId, int num) throws RpcException;

	/* ======================================================================== */
	/* ============================= VM Operations ============================ */
	/* ======================================================================== */

	/**
	 * @param name
	 *            虚拟机的名字   (Not null)
	 * @param imgUuid
	 *            镜像uuid  (Not null, 且在数据库中存在该数据项)
	 * @param flavorId
	 *            配置的Id  (Not null, 且在数据库中存在该数据项)
	 * @param sgId
	 *            security group的Id  (Not null, 且在数据库中存在该数据项)
	 * @param avalibilityZoneId
	 *            域id (Not null, 且在数据库中存在该数据项)
	 * @param            
	 *            
	 * @param metadata
	 *            元数据  (Not null)
	 * @return VM生成的uuid
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 60)
	public String createVM(String name, String userId, String imgUuid, Integer flavorId,
			Integer sgId, Integer avalibilityZoneId, List<Integer> clusterIds,String hostUuid, String newPassword, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException ;

	@RPCTimeout(timeout = 60)
	public String recoveryVM(String name, String userId, String imgUuid, Integer flavorId,
						   Integer sgId, Integer avalibilityZoneId, String hostUuid, String newPassword, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException ;


	/**
	 * 在线迁移虚拟机
	 * @param uuid
	 * @param hostUuid
	 * @param rpcExtra
	 * @throws RpcException
	 */
	public void onlineMigrateVM(String uuid, String hostUuid, RpcExtra rpcExtra) throws RpcException;

	
	/**
	 * 离线迁移
	 * @param uuid
	 * @param hostUuid
	 * @param rpcExtra
	 * @throws RpcException
	 */
	public void migrateVM(String uuid, String hostUuid, RpcExtra rpcExtra) throws RpcException;

	/**
	 * @param uuid
	 *            VM的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	public void startVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	public void stopVM(String uuid, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param uuid
	 *            VM的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	public void forceStopVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	public void resumeVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	public void rebootVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param flavorId
	 *            配置的Id，为空不更改
	 * @param sgId
	 *            security group的Id 为空不更改
	 * @return 不return，但是需要更新数据库相应信息后返回；
	 */
	@RPCTimeout(timeout = 60)
	public void rebuildVM(String uuid, Integer userId, Integer flavorId,
			Integer sgId, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	public void suspendVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	@RPCTimeout(timeout = 60)
	public void forceDeleteVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	@RPCTimeout(timeout = 60)
	public void terminateVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	// !!!!!!!!!!! Old Methods !!!!!!!!!!!!!! Need to delete !!!!!!!!!!!
	public RSEC createVM(String uuid, Integer templateId, String mac, String ip)
			throws RpcException;

	public RSEC deleteVM(String uuid) throws RpcException;

	public RSEC dealVM(String uuid, String action, Integer templateId)
			throws RpcException;

	// !!!!!!!!!!! Old Methods !!!!!!!!!!!!!! Need to delete !!!!!!!!!!!

	/**
	 * 
	 * @param uuid instance的uuid
	 * @return 刷新后instance的状态
	 * @throws RpcException
	 */
	public VmStatusEnum getVMState(String uuid, RpcExtra rpcExtra) throws RpcException;
	
	/* ======================================================================== */
	/* ========================== Volume Operations =========================== */
	/* ======================================================================== */

	/**
	 * @param userId 
	 *            用户Id  (Not null, 且在数据库中存在该数据项)
	 * @param avalibilityZoneId
	 *            可用的ZoneId  (Not null, 且在数据库中存在该数据项)
	 * @param name
	 *            名字  (Not null)
	 * @param displayName
	 *            名字  (Not null)
	 * @param size
	 *            大小，单位：GB  (Not null)
	 * @param type
	 *            qcow2 or 其他;  (Not null)
	 * @param metadata
	 *            metadata  (Not null)
	 * 
	 * @return return Volume的Uuid，需要固化(availabilityZone, createdAt信息)后返回
	 */
	@RPCTimeout(timeout = 60)
	public String createVolume(Integer userId, Integer avalibilityZoneId, List<Integer> clusterIds, String name, String displayName, String discription, Integer size,
			String type, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException ;


	@RPCTimeout(timeout = 60)
	public String createVolumeImageBack(Integer userId, String displayName, String discription,String volumeUuid,RpcExtra rpcExtra) throws RpcException;


	@RPCTimeout(timeout = 60)
	public String revertVolume(String volumeUuid, String instanceId, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param volumeUuid 对应Volume的UUID  (Not null, 且在数据库中存在该数据项)
	 * @param instanceId 对应Instance的UUID  (Not null, 且在数据库中存在该数据项)
	 *
	 * @retrun 挂载点，如"/dev/vdc" 
	 */
	@RPCTimeout(timeout = 60)
	public String attachVolume(String volumeUuid, String instanceId, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param volumeUuid 对应Volume的UUID  (Not null, 且在数据库中存在该数据项)
	 * @param instanceId 对应Instance的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	@RPCTimeout(timeout = 60)
	public void detachVolume(String volumeUuid, String instanceId, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param volumeUuid Volume的UUID  (Not null, 且在数据库中存在该数据项)
	 * @param displayName 新的名称  (Not null)
	 * @param discription 新的描述  (Not null)
	 */
	public void updateVolume(String volumeUuid, String displayName, String discription, HashMap<String, String> metadata, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param volumeUuid  Volume的UUID  (Not null, 且在数据库中存在该数据项)
	 * @throws RpcException
	 */
	public void deleteVolume(String volumeUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param volumeUuid  Volume的UUID  (Not null, 且在数据库中存在该数据项)
	 * @throws RpcException
	 */
	public void forceDeleteVolume(String volumeUuid, RpcExtra rpcExtra) throws RpcException;
	/* ======================================================================== */
	/* ========================== Snapshot Operations ========================= */
	/* ======================================================================== */
	/**
	 * @param displayName 显示名字  (Not null)
	 * @param displayDescription 描述  (Not null)
	 * @param volumeUuid 被snapshot的volume  (Not null, 且在数据库中存在该数据项)
	 * @param force 是否强制  (Not null)
	 *
	 * @return 创建的Snapshot的UUID
	 */
	@RPCTimeout(timeout = 90)
	public String createSnapshot(String displayName, String displayDescription, String volumeUuid, Boolean force, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param snapshotUuid  (Not null, 且在数据库中存在该数据项)
	 * 
	 * @throws RpcException
	 */
	public void deleteSnapshot(String snapshotUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param snapshotUuid  (Not null, 且在数据库中存在该数据项)
	 * 
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 60)
	public void revertSnapshot(String snapshotUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param snapshotUuid Snapshot的UUID  (Not null, 且在数据库中存在该数据项)
	 * @param displayName 新的名称  (Not null)
	 * @param discription 新的描述  (Not null)
	 */
	public void updateSnapshot(String snapshotUuid, String displayName, String discription, Integer userId, RpcExtra rpcExtra) throws RpcException;
	
	/* ======================================================================== */
	/* ========================== BackUp Operations ========================= */
	/* ======================================================================== */
	/**
	 * @param displayName 显示名字  (Not null)
	 * @param displayDescription 描述  (Not null)
	 * @param volumeUuid 被BackUp的volume  (Not null, 且在数据库中存在该数据项)
	 * @param force 是否强制  (Not null)
	 *
	 * @return 创建的BackUp的UUID
	 */
	@RPCTimeout(timeout = 90)
	public String createBackUp(String displayName, String displayDescription, String volumeUuid, Boolean force, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param BackUpUuid  (Not null, 且在数据库中存在该数据项)
	 * 
	 * @throws RpcException
	 */
	public void deleteBackUp(String backUpUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param BackUpUuid  (Not null, 且在数据库中存在该数据项)
	 * 
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 60)
	public void revertBackUp(String backUpUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param BackUpUuid BackUp的UUID  (Not null, 且在数据库中存在该数据项)
	 * @param displayName 新的名称  (Not null)
	 * @param discription 新的描述  (Not null)
	 */
	public void updateBackUp(String backUpUuid, String displayName, String discription, RpcExtra rpcExtra) throws RpcException;

	
	/* ======================================================================== */
	/* ========================= Aggregate Operations ========================= */
	/* ======================================================================== */
	/**
	 * @param name 名字  (Not null)
	 *
	 * @return 创建的数据中心的Id
	 */
	public Integer createVmZone(String name, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param zoneId id  (Not null)
	 * @param name 名字  (Not null)
	 *
	 * @return 更新的数据中心的Id
	 */
	public Integer updateVmZone(Integer zoneId, String name, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param zoneId id  (Not null)
	 *
	 */
	public void deleteVmZone(Integer zoneId, RpcExtra rpcExtra) throws RpcException;
	
	
	/**
	 * @param name 名字  (Not null)
	 * @param availabilityZoneId  (Not null, 且在数据库中存在该数据项)
	 *
	 * @return 创建的集群的Id
	 */
	public Integer createAggregate(String name, Integer availabilityZoneId, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param hostId host uuid  (Not null, 且在数据库中存在该数据项)
	 * @param aggregateId  (Not null, 且在数据库中存在该数据项)
	 *
	 * @return 创建的集群的Id
	 */
	public Integer addHostToAggregate(String hostId, Integer aggregateId, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param hostId host uuid  (Not null, 且在数据库中存在该数据项)
	 * @param aggregateId  (Not null, 且在数据库中存在该数据项)
	 *
	 * @return 创建的集群的Id
	 */
	public Integer removeHostFromAggregate(String hostId, Integer aggregateId, RpcExtra rpcExtra) throws RpcException ;
	
	
	/* ======================================================================== */
	/* =========================== Flavor Operations ========================== */
	/* ======================================================================== */
	/**
	 * @param name 名称   (Not null)
	 * @param ram 内存   (Not null)
	 * @param disk 硬盘   (Not null)
	 * @param vcpus cpu  (Not null)
	 * @return 创建的虚拟机配置的Id
	 * @throws RpcException
	 */
	public Integer createFlavor(String name, Integer ram, Integer disk, Integer vcpus, RpcExtra rpcExtra) throws RpcException ;
	
	
	/* ======================================================================== */
	/* ======================= SecurityGroup Operations ======================= */
	/* ======================================================================== */
	/**
	 * @param userId 用户名字  (Not null, 且在数据库中存在该数据项)
	 * @param name 名字  (Not null)
	 * @param description 描述  (Not null)
	 *
	 * @return 创建的SecurityGroup的Id
	 */
	@RPCTimeout(timeout = 90)
	public Integer createSecurityGroup(String userId, String name, String description, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param sgId SG的id  (Not null, 且在数据库中存在该数据项)
	 *
	 * @return 是否删除成功
	 */
	public Boolean deleteSecurityGroup(Integer sgId, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param sgId SG的id  (Not null, 且在数据库中存在该数据项)
	 * @param name  (Not null)
	 * @param description  (Not null)
	 */
	public void updateSecurityGroup(Integer sgId, String name, String description, Integer userId, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param sgId 所属SG的id  (Not null, 且在数据库中存在该数据项)
	 * @param fromPort 来源端口  (Not null)
	 * @param toPort 目的端口  (Not null)
	 * @param protocal 协议  (Not null)
	 * @param range ip范围，CIDR，如"10.0.0.0/8"  (Not null)
	 *
	 * @return 创建的SecurityGroupRule的Id
	 */
	@RPCTimeout(timeout = 60)
	public Integer createSecurityGroupRule(Integer sgId, Integer fromPort, Integer toPort, String protocal, String range, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param ruleId rule的Id  (Not null, 且在数据库中存在该数据项)
	 *
	 * @return 创建的SecurityGroupRule的Id
	 */
	@RPCTimeout(timeout = 60)
	public void deleteSecurityGroupRule(Integer ruleId, RpcExtra rpcExtra) throws RpcException;
	
	/* ======================================================================== */
	/* ========================= Network Operations ========================= */
	/* ======================================================================== */
	/**
	 * @param type 0 私网， 1 公网 (Not null)
	 * @param clusterId 集群Id (Not null)
	 * @param ipsegment ip分段 (Not null)
	 */
	public void createIpSegment(Integer type, Integer clusterId, VmIpSegMent ipSegment, RpcExtra rpcExtra) throws RpcException;
	
	 /**
     * 根据ipsegment的id删除某一段公网ip，如果这段中有ip被占用，删除失败
     * @param ipSegId  (Not null, 且在数据库中存在该数据项)
     * @throws RpcException
     */
	boolean delNetIpSegment(Integer ipSegId, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * 设置VM指定网卡的最大带宽
	 * @param instanceUuid
	 * @param netType	网卡类型（private or public）
	 * @param flowType	流量类型（IN or OUT）
	 * @param maxBandwidth
	 * @param rpcExtra
	 * @return
	 * @throws RpcException
	 */
	@RPCTimeout(timeout=60)
	int setVmMaxBandwidth(String instanceUuid, Map<String, String> metadatas, boolean release, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * 取消VM指定网卡的最大带宽限制
	 * @param instanceUuid
	 * @param netType	网卡类型（private or public）
	 * @param flowType	流量类型（IN or OUT）
	 * @param rpcExtra
	 * @return
	 * @throws RpcException
	 */
	boolean cancelVmMaxBandwidth(String instanceUuid, NetSegment netType, FlowType flowType, RpcExtra rpcExtra) throws RpcException;
	
	/* ======================================================================== */
	/* =========================== Image Operations =========================== */
	/* ======================================================================== */
	@RPCTimeout(timeout = 6000)
	public String authorizeImage(VmImage vmImage, RpcExtra rpcExtra) throws RpcException;


	/**
	 * @param userId  (Not null, 且在数据库中存在该数据项)
	 * @param serverUuid  (Not null, 且在数据库中存在该数据项)
	 * @param volumeUuid  (Not null, 且在数据库中存在该数据项)
	 * @param displayName (Not null)
	 * @param displayDescription (Not null)
	 * @param isPublic (Not null)
	 */
	@RPCTimeout(timeout = 6000)
	/*public String createImage(Integer userId, String serverUuid, String volumeUuid,
			String displayName, String displayDescription, Boolean isPublic, String distribution, RpcExtra rpcExtra) throws RpcException;*/
	public String createImage(Integer userId, String serverUuid, String volumeUuid, String account,
			String displayName, String displayDescription, String groupIdList, String distribution, String software, RpcExtra rpcExtra) throws RpcException;



	/**
	 * @param imageUuid  (Not null, 且在数据库中存在该数据项)
	 */
	@RPCTimeout(timeout = 6000)
	public boolean deleteImage(String imageUuid, String groupId,RpcExtra rpcExtra) throws RpcException;

	/**
	 * @param imageUuid  (Not null, 且在数据库中存在该数据项)
	 * @param name (Not null)
	 * @param description (Not null)
	 * @param isPublic (Not null)
	 */
	/*public void updateImage(String imageUuid, String name, String description, Boolean isPublic, RpcExtra rpcExtra) throws RpcException;*/
	public void updateImage(String imageUuid, String name, String description, String account, String software, String groupIdList, RpcExtra rpcExtra) throws RpcException;
	
	/* ======================================================================== */
	/* =========================== ISO Operations =========================== */
	/* ======================================================================== */
	/**
	 * @param imageUuid iso的UUID  (Not null, 且在数据库中存在该数据项)
	 * @param instanceUuid instance的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	public void bootFromISO(String imageUuid, String instanceUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param uuid instance的UUID  (Not null, 且在数据库中存在该数据项)
	 */
	public void detachISO(String uuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * modify vm hostname
	 * @param instanceUuid
	 * @param newHostName
	 * @param OSType
	 * @param rpcExtra
	 * @return
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 120)
	public Boolean modVmHostName(String instanceUuid, String newHostName, VmImageOSTypeEnum OSType, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * 修改密码
	 * @param instanceUuid
	 * @param name
	 * @param newPasswd
	 * @param OSType
	 * @param rpcExtra
	 * @return
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 120)
	public Boolean modVmPasswd(String instanceUuid, String  name, String newPasswd, VmImageOSTypeEnum OSType, RpcExtra rpcExtra) throws RpcException;
	
	@RPCTimeout(timeout = 5)
	public String KeepAlive() throws Exception ;
}

