package appcloud.common.service;

import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmImage;
import appcloud.rpc.tools.RpcException;
import appcloud.common.exception.IllegalRpcArgumentException;

public interface ImageServerService {

	/**
	 *创建新镜像前，先new一个镜像，设置必要的参数用于上传镜像的构建和上传镜像文件
	 *@return  设置后的新镜像,preImage设置的字段包括uuid，directory， services
	 *      service为之后volumeScheduler要将镜像传到的地址
	 *注意：先上传镜像文件，上传完成后修改镜像状态status，若上传成功，再将其保存到数据库中
	 */
	VmImage preCreateImage() throws IllegalRpcArgumentException, RpcException;


	VmImage preCreateImage(VmImage vmImage) throws IllegalRpcArgumentException, RpcException;
	/**
	 * 创建新镜像记录
	 * @param newImage	要保存的镜像
	 * 	newImage中必须要设置的字段有：
	 * 		userId,
	 * 		displayName,
	 * 		displayDescription,
	 * 		isPublic,
	 * 		osType,(ResourceScheduler设置的，实际不进行检查，可以为null)
	 * 		osArch,(ResourceScheduler设置的，实际不进行检查，可以为null)
	 * 		osMode(ResourceScheduler设置的，实际不进行检查，可以为null)
	 * @return	新保存的镜像记录
	 * 	VmImage中填充了以下字段：
	 * 		id,
	 * 		uuid,
	 * 		directory,
	 * 		diskFormat,
	 * 		createdTime,
	 * 		status,
	 * 		List<Service>：之后volumeScheduler要将镜像传到的地址
	 * @throws RpcException
	 * 
	 * 注意：先保存数据库记录，然后由volumeScheduler上传镜像文件，上传完成后修改镜像状态status
	 */
	VmImage createImage(VmImage newImage, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException;

	/**
	 * 授权镜像为群组镜像，将新的数据存入数据库，状态为创建中
	 * @param newImage
	 * @param rpcExtra
	 * @return
	 * @throws IllegalRpcArgumentException
	 * @throws RpcException
	 */
	VmImage authorizeImage(VmImage newImage, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException;

	/**
	 * 将创建中的群组镜像状态修改为已完成
	 * @param updateImage
	 * @param rpcExtra
	 * @return
	 * @throws IllegalRpcArgumentException
	 * @throws RpcException
	 */
	VmImage updateAuthorizeImage(VmImage updateImage, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException;

	/**
	 * 取得某个要下载的镜像信息，供Volume读取下载
	 * @param uuid	镜像uuid
	 * @return	数据库中保存的镜像记录，
	 * 			同时设置List<Service>为可以提供下载服务的ImageServer service
	 * @throws RpcException
	 */
	VmImage getDownloadImage(String uuid, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException;
	
	/**
	 * 删除某个image记录，目前仅仅设标记，status改为DELETING
	 * @param uuid	镜像uuid alone 
	 * @throws RpcException
	 */
	boolean deleteImageOnTable(String uuid,String groupId, boolean used,RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException;

	/**
	 * Description 判断是否还有群组使用该镜像 ，是返回null，否返回该image在物理机上的路径，删除用
	 * @param imageUuid
	 * @param groupId
	 * @param rpcExtra
	 * @return
	 * @author GongLingpu
	 */
	String judgeUsed(String imageUuid, String groupId, RpcExtra rpcExtra);
	
	/**
	 * Description 删除镜像方法
	 * @param imageUuid
	 * @param groupId
	 * @param rpcExtra
	 * @return
	 * @author GongLingpu
	 */
	public boolean deleteImage(String imageUuid,String groupId,RpcExtra rpcExtra);

	public String KeepAlive() throws Exception ;
}
