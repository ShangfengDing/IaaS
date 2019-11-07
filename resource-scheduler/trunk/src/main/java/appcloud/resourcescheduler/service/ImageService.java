/**
 * File: ImageService.java
 * Author: weed
 * Create Time: 2013-4-28
 */
package appcloud.resourcescheduler.service;

import java.util.List;

import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmImage;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public interface ImageService {
	/**
	 * @param userId
	 * @param serverUuid
	 * @param volumeUuid
	 * @param displayName
	 * @param displayDescription
	 * @param groupIdList
	 */
	/*public String createImage(Integer userId, String serverUuid, String volumeUuid, 
			String displayName, String displayDescription, Boolean isPublic, String distri31bution, RpcExtra rpcExtra) throws RpcException;*/
	public String createImage(Integer userId, String serverUuid, String volumeUuid,String account,String displayName, String displayDescription, String groupIdList, String distribution, String software, RpcExtra rpcExtra) throws RpcException;



	public String authorizeImage(VmImage vmImage, RpcExtra rpcExtra) throws RpcException;
		/**
         * @param imageUuid
         */
	public boolean deleteImage(String imageUuid, String groupId, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @param imageUuid
	 * @param name
	 * @param description
	 * @param isPublic
	 */
	/*void updateImage(String imageUuid, String name, String description, Boolean isPublic, RpcExtra rpcExtra) throws RpcException;*/
	void updateImage(String imageUuid, String name, String description, String account, String software, String groupIdList, RpcExtra rpcExtra) throws RpcException;

	public String KeepAlive() throws Exception ;
}
