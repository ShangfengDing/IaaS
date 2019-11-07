/**
 * File: VolumeService.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service;

import java.util.Map;

import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public interface VolumeService {
	/**
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
	 * @return return Volume的Id，需要固化(availabilityZone, createdAt信息)后返回
	 */
	public Integer createVolume(String name, String displayName, Integer size,
			String type, Map<String, String> metadata) throws RpcException ;

	/**
	 * @param volumeId 对应Volume的UUID
	 * @param instanceId 对应Instance的UUID
	 *
	 * @retrun 挂载点，如"/dev/vdc" 
	 */
	public String attachVolume(String volumeId, String instanceId) throws RpcException ;
	
	/**
	 * @param volumeId 对应Volume的UUID
	 * @param instanceId 对应Instance的UUID
	 */
	public void detachVolume(String volumeId, String instanceId) throws RpcException ;
	
	/**
	 * @param displayName 显示名字
	 * @param displayDescription 描述
	 * @param volumeId 被snapshot的volume
	 * @param force 是否强制
	 *
	 * @return 创建的Snapshot的UUID
	 */
	public String createSnapshot(String displayName, String displayDescription, String volumeId, Boolean force) throws RpcException ;

}
