/**
 * File: VmService.java
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
public interface VmService {
	/**
	 * @param name
	 *            虚拟机的名字
	 * @param imgId
	 *            镜像uuid
	 * @param flavorId
	 *            配置的Id
	 * @param sgId
	 *            security group的Id
	 * @return VM生成的uuid
	 */
	public String createVM(String name, String userId, String imgId, Integer flavorId,
			Integer sgId,  Integer avalibilityZoneId, Map<String, String> metadata) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void startVM(String uuid) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void stopVM(String uuid) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void resumeVM(String uuid) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void rebootVM(String uuid) throws RpcException ;

	/**
	 * @param name
	 *            虚拟机的名字，为空不更改
	 * @param imgId
	 *            镜像uuid，为空不更改
	 * @param flavorId
	 *            配置的Id，为空不更改
	 * @param sgId
	 *            security group的Id 为空不更改
	 * @return 不return，但是需要更新数据库相应信息后返回；
	 */
	public void rebuildVM(String name, String imgId, Integer flavorId,
			Integer sgId, Map<String, String> metadata) throws RpcException ;

	/**
	 * @param flavorId
	 *            新的配置的Id
	 */
	public void resizeVM(String uuid, Integer flavorId) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void suspendVM(String uuid) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void forceDeleteVM(String uuid) throws RpcException ;

	/**
	 * @param uuid
	 *            VM的UUID
	 */
	public void terminateVM(String uuid) throws RpcException ;
}
