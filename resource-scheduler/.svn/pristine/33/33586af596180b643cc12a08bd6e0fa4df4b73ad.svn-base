/**
 * File: VmService.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service;

import java.util.List;
import java.util.Map;

import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public interface VmService {
	/**
	 * @param name 虚拟机的名字
	 * @param userId
	 * @param imgUuid 镜像uuid
	 * @param flavorId
	 * @param sgId  security group的Id
	 * @param avalibilityZoneId
	 * @param clusterIds
	 * @param newPassword
	 * @param metadata
	 * @param rpcExtra
	 * @return VM生成的uuid
	 * @throws RpcException
	 */
	public String createVM(String name, String userId, String imgUuid, Integer flavorId,
			Integer sgId,  Integer avalibilityZoneId, List<Integer> clusterIds, String hostUuid,final String newPassword, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException ;

	public String recoveryVM(String name, String userId, String imgUuid, Integer flavorId,
							 Integer sgId,  Integer avalibilityZoneId, String hostUuid, final String newPassword, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void startVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void stopVM(String uuid, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void forceStopVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void resumeVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void rebootVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @Description
	 * 		修改虚拟机计算资源配置，包括CPU、内存防火墙，需要重启虚拟机
	 * @param flavorId
	 *      配置的Id，为空不更改
	 * @param sgId
	 *      security group的Id 为空不更改
	 * @return 不return，但是需要更新数据库相应信息后返回；
	 */
	public void rebuildVM(String uuid, Integer userId, Integer flavorId,
			Integer sgId, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * 在线迁移
	 * @param uuid 
	 * 			虚拟机的UUID
	 * @param hostUuid
	 * 			迁移到的宿主机UUID
	 * @return void,需更新数据库 
	 */
	public void onlineMigrateVM(String uuid, String hostUuid, RpcExtra rpcExtra) throws RpcException;	
	
	/**
	 * 离线迁移
	 * @param uuid 
	 * 			虚拟机的UUID
	 * @param hostUuid
	 * 			迁移到的宿主机UUID,若为空，则由系统自动选择迁移节点 
	 * @return void,需更新数据库 
	 */
	public void migrateVM(String uuid, String hostUuid, RpcExtra rpcExtra) throws RpcException;
	
	
//	/**
//	 * @param flavorId
//	 *            新的配置的Id
//	 */
//	public void resizeVM(String uuid, Integer flavorId) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void suspendVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void forceDeleteVM(String uuid, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void terminateVM(String uuid, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param imageUuid iso的UUID
	 * @param instanceUuid instance的UUID
	 */
	void bootFromISO(String imageUuid, String instanceUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param uuid instance的UUID
	 */
	void detachISO(String uuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param uuid instance的UUID
	 * @return 刷新后instance的状态
	 * @throws RpcException
	 */
	public VmStatusEnum getVMState(String uuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * modify vm hostname
	 * @param instanceUUID
	 * @param newHostName
	 * @param OSType
	 * @param rpcExtra
	 * @return
	 * @throws RpcException
	 */
	public Boolean modVmHostName(String instanceUUID, String newHostName, VmImageOSTypeEnum OSType, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * 
	 * @param name
	 * @param newPasswd
	 * @param OSType
	 * @param rpcExtra
	 * @return
	 * @throws RpcException
	 */
	public Boolean modVmPasswd(String instanceUUID, String name, String newPasswd, VmImageOSTypeEnum OSType, RpcExtra rpcExtra) throws RpcException;

	public String KeepAlive() throws Exception ;
}
