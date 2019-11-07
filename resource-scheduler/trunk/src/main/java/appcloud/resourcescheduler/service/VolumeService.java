/**
 * File: VolumeService.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appcloud.common.model.RpcExtra;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
/**
 * @author weed
 *
 */
public interface VolumeService {
	/**
	 * @param userId 
	 *            用户Id
	 * @param avalibilityZoneId
	 *            可用的ZoneId
	 * @param name
	 *            名字
	 * @param displayName
	 *            名字
	 * @param size
	 *            大小，单位：GB
	 * @param type
	 *            qcow2 or 其他;
	 * @param metadata
	 *            metadata
	 * 
	 * @return return Volume的Uuid，需要固化(availabilityZone, createdAt信息)后返回
	 */
	public String createVolume(Integer userId, Integer avalibilityZoneId, List<Integer> clusterIds, String name, String displayName, String discription, Integer size,
			String type, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException ;


	public String createVolumeImageBack(Integer userId, String displayName, String discription, String volumeUuid,RpcExtra rpcExtra) throws RpcException ;

	public String revertVolume(String volumeUuid, String instanceUuid, RpcExtra rpcExtra) throws RpcException ;
	/**
	 * @param volumeUuid 对应Volume的UUID
	 * @param instanceUuid 对应Instance的UUID
	 *
	 * @retrun 挂载点，如"/dev/vdc" 
	 */
	public String attachVolume(String volumeUuid, String instanceUuid, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param volumeUuid 对应Volume的UUID
	 * @param instanceUuid 对应Instance的UUID
	 */
	public void detachVolume(String volumeUuid, String instanceUuid, RpcExtra rpcExtra) throws RpcException ;
	
	/**
	 * @param volumeUuid Volume的UUID
	 * @param displayName 新的名称
	 * @param discription 新的描述
	 */
	public void updateVolume(String volumeUuid, String displayName, String discription, HashMap<String, String> metadata, RpcExtra rpcExtra) throws RpcException;

	/**
	 * @param volumeUuid  Volume的UUID
	 * @throws RpcException
	 */
	public void deleteVolume(String volumeUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param volumeUuid  Volume的UUID
	 * @throws RpcException
	 */
	public void forceDeleteVolume(String volumeUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param displayName 显示名字
	 * @param displayDescription 描述
	 * @param volumeId 被snapshot的volume
	 * @param force 是否强制
	 *
	 * @return 创建的Snapshot的UUID
	 */
	public String createSnapshot(String displayName, String displayDescription, String volumeUuid, Boolean force, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param snapshotUuid
	 * 
	 * @throws RpcException
	 */
	public void deleteSnapshot(String snapshotUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param snapshotUuid
	 * 
	 * @throws RpcException
	 */
	public void revertSnapshot(String snapshotUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param snapshotUuid Snapshot的UUID
	 * @param displayName 新的名称
	 * @param discription 新的描述
	 */
	public void updateSnapshot(String snapshotUuid, String displayName, String discription, Integer userId, RpcExtra rpcExtra) throws RpcException;

	/**
	 * @param displayName 显示名字
	 * @param displayDescription 描述
	 * @param volumeUuid 被BackUp的volume
	 * @param force 是否强制
	 *
	 * @return 创建的BackUp的UUID
	 */
	public String createBackUp(String displayName, String displayDescription, String volumeUuid, Boolean force, RpcExtra rpcExtra) throws RpcException ;

	/**
	 * @param BackUpUuid s
	 * 
	 * @throws RpcException
	 */
	public void deleteBackUp(String BackUpUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param BackUpUuid 
	 * 
	 * @throws RpcException
	 */
	public void revertBackUp(String BackUpUuid, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param BackUpUuid BackUp的UUID
	 * @param displayName 新的名称
	 * @param discription 新的描述
	 */
	public void updateBackUp(String BackUpUuid, String displayName, String discription, RpcExtra rpcExtra) throws RpcException;

	public String KeepAlive() throws Exception ;
}
