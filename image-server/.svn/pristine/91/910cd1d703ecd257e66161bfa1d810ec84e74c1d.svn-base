package appcloud.imageserver.impl;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.MessageLog;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.Service;
import appcloud.common.model.VmVolume;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmImage.VmImageDiskFormatEnum;
import appcloud.common.model.VmImage.VmImageStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.service.ImageServerService;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.UuidUtil;
import appcloud.imageserver.util.Config;
import appcloud.imageserver.util.NfsLocationTool;
import appcloud.rpc.tools.RpcException;

/**
 * ImageServer的接口实现
 * @author lzc
 *
 */
public class ImageServerImpl implements ImageServerService{

	
	
	VmImageProxy imageProxy = (VmImageProxy)ConnectionFactory.getDBProxy(VmImageProxy.class);
	ServiceProxy serviceProxy = (ServiceProxy)ConnectionFactory.getDBProxy(ServiceProxy.class);
	private VmGroupProxy vmGroupProxy;
	private static Logger logger = Logger.getLogger(ImageServerImpl.class);
	
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = ImageServerManage.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.IMAGE_SERVER, ImageServerImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 *创建新镜像前，先new一个镜像，设置必要的参数用于上传镜像的构建和上传镜像文件
	 *@return  设置后的新镜像,preImage设置的字段包括uuid，directory， services
	 *      service为之后volumeScheduler要将镜像传到的地址
	 *注意：先上传镜像文件，上传完成后修改镜像状态status，若上传成功，再将其保存到数据库中
	 */
	public VmImage preCreateImage() {
		logger.info("pre_create image...");
		VmImage preImage = new VmImage();
		//设置uuid和directory
		String uuid = UuidUtil.getRandomUuid();
		String directory = NfsLocationTool.getNfsLocation(uuid+".img");  // 类似 /a/z/……
		preImage.setUuid(uuid);
		preImage.setDirectory(directory);
		//设置要上传到的service
		preImage.setServices(getUploadServices());
		return preImage;
	}

	public VmImage preCreateImage(VmImage vmImage) {
		logger.info("pre_create image...");
		//设置uuid和directory
		String uuid = vmImage.getUuid();
		String directory = NfsLocationTool.getNfsLocation(uuid+".img");
		vmImage.setDirectory(directory);
		//设置要上传到的service
		vmImage.setServices(getUploadServices());
		return vmImage;
	}

	/**
	 * 在数据库保存一个新的镜像记录
	 * @param newImage	要保存的镜像
	 * 		newImage中必须要设置的字段有：
	 * 			userId,displayName,displayDescription,
	 * 			isPublic,osType,osArch,osMode
	 * @return	新保存的镜像记录，填充了部分字段：
	 * 			uuid,directory,diskFormat,createdTime,status,
	 * 			service为之后volumeScheduler要将镜像传到的地址
	 * 注意：先保存数据库记录，然后上传镜像文件，上传完成后修改镜像状态status
	 */
	public VmImage createImage(VmImage newImage, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException{
		logger.info("start to create:" + newImage);
		try {
			//检查参数是否合法
			if (!checkParamsCreate(newImage)) {
				loller.error(LolLogUtil.CREATE_IMG, "create new image params not valid!", rpcExtra);
				throw new IllegalRpcArgumentException("创建镜像的参数不合法");
			}

			//设置创建时间和状态
			newImage.setDiskFormat(VmImageDiskFormatEnum.IMAGE);
			newImage.setCreatedTime(new Timestamp(System.currentTimeMillis()));
			newImage.setStatus(VmImageStatusEnum.CREATING);

			//保存到数据库
			imageProxy.save(newImage);
	
			//设置上newImage的id
			logger.info("new image id : " + imageProxy.getByUuid(newImage.getUuid()).getId());
			newImage.setId(imageProxy.getByUuid(newImage.getUuid()).getId());
			logger.info("create image finished!!! image : " + newImage);
			loller.info(LolLogUtil.CREATE_IMG, "create new image success! " + newImage, rpcExtra);
			return newImage;
		} catch (Exception e) {
			String why = "镜像创建失败";
			logger.warn(why,e);
			loller.error(LolLogUtil.CREATE_IMG, "create new image failed!", rpcExtra);
			throw new RpcException(why, e);
		}
	}

	public VmImage authorizeImage(VmImage newImage, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException{
		logger.info("start to create:" + newImage);
		try {
			//检查参数是否合法
			if (!checkParamsCreate(newImage)) {
				loller.error(LolLogUtil.CREATE_IMG, "create new image params not valid!", rpcExtra);
				throw new IllegalRpcArgumentException("创建镜像的参数不合法");
			}

			//设置创建时间和状态
			newImage.setCreatedTime(new Timestamp(System.currentTimeMillis()));
			newImage.setStatus(VmImageStatusEnum.CREATING);

			//保存到数据库
			imageProxy.save(newImage);

			//设置上newImage的id
			logger.info("new image id : " + imageProxy.getByUuid(newImage.getUuid()).getId());
			newImage.setId(imageProxy.getByUuid(newImage.getUuid()).getId());
			loller.info(LolLogUtil.AUTHORIZE_IMAGE, "authorize new image success! " + newImage, rpcExtra);
			return newImage;
		} catch (Exception e) {
			String why = "镜像创建失败";
			logger.warn(why,e);
			loller.error(LolLogUtil.AUTHORIZE_IMAGE, "authorize new image failed!", rpcExtra);
			throw new RpcException(why, e);
		}
	}

	public VmImage updateAuthorizeImage(VmImage updateImage, RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException {
		logger.info("start to update:" + updateImage);
		try {
			//设置修改时间和状态
			updateImage.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
			updateImage.setStatus(VmImageStatusEnum.AVAILABLE);

			//更新数据库
			imageProxy.update(updateImage);

			logger.info("update image id : " + imageProxy.getByUuid(updateImage.getUuid()).getId());
			logger.info("authorize image finished!!! image : " + updateImage);
			loller.info(LolLogUtil.AUTHORIZE_IMAGE, "update authorize image success! " + updateImage, rpcExtra);
			return updateImage;
		} catch (Exception e) {
			String why = "镜像修改失败";
			logger.warn(why,e);
			loller.error(LolLogUtil.AUTHORIZE_IMAGE, "update authorize image failed!", rpcExtra);
			throw new RpcException(why, e);
		}
	}

	/**
	 * 取得某个要下载的镜像，供Volume读取下载
	 * @param uuid	镜像uuid
	 * @return	数据库中保存的镜像记录，List<Service>中为ImageServer记录
	 */
	public VmImage getDownloadImage(String uuid, RpcExtra rpcExtra) throws IllegalRpcArgumentException,RpcException {
		try {
			VmImage image = imageProxy.getByUuid(uuid);
			
			if (image == null) {
				logger.warn("uuid："+uuid+",没有找到image");
				loller.error(LolLogUtil.GET_DOWNLOAD_IMG, "found image by uuid failed! uuid:"+uuid, rpcExtra);
				throw new IllegalRpcArgumentException("uuid："+uuid+",没有找到image");
			}
			image.setServices(getDownloadService(uuid));
			loller.info(LolLogUtil.GET_DOWNLOAD_IMG, "found image success by uuid:" + uuid, rpcExtra);
			return image;
		} catch (Exception e) {
			String why = "数据库操作失败";
			logger.warn(why,e);
			loller.error(LolLogUtil.GET_DOWNLOAD_IMG, "database connect failed", rpcExtra);
			throw new RpcException(why, e);
		}
	}
	
	/**
	 * Description 
	 * @param imageUuid
	 * @param groupId
	 * @param rpcExtra
	 * @return
	 * @author GongLingpu
	 */
	public boolean deleteImage(String imageUuid,String groupId,RpcExtra rpcExtra){
		try {
			String imagePath = judgeUsed(imageUuid, groupId,rpcExtra);//判断该image是否有其他群组在使用，没有返回改image物理机路径
			logger.info("judge done get image path:" + imagePath);
			if(imagePath != null ){
				logger.info("clear to deleteImageOnServer");
				boolean flag = deleteImageOnServer(imageUuid,imagePath);
				if(flag){
					logger.info("delete image on server succeeded: " + imageUuid);
					loller.info(LolLogUtil.DELETE_IMG, "delete image on server succeeded: " + imageUuid, rpcExtra);
					if(deleteImageOnTable(imageUuid, groupId, false, rpcExtra)){
						logger.info("delete image on table succeeded: " + imageUuid);
					}
					
				}
			}else{
				if(deleteImageOnTable(imageUuid, groupId, true, rpcExtra)){
					logger.info("only delete image on table succeeded: " + imageUuid);
				}
			}
			logger.info("delete image succeeded: " + imageUuid);
			loller.info(LolLogUtil.DELETE_IMG, "delete image succeeded: " + imageUuid, rpcExtra);
			return true;
		} catch (Exception e) {
			logger.error("delete image error: " + imageUuid);
			try {
				loller.info(LolLogUtil.DELETE_IMG, "delete image error: " + imageUuid, rpcExtra);
			} catch (RpcException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		
	}
	/**
	 * Description 判断是否还有群组使用该镜像 ，是返回null，否返回该image在物理机上的路径，删除用
	 * @param uuid
	 * @param groupId
	 * @param rpcExtra
	 * @return
	 * @author GongLingpu
	 */
	public String judgeUsed(String uuid, String groupId,RpcExtra rpcExtra){
		if(uuid.equals("") || groupId.equals("")){
			logger.error("judge image used error "+ uuid +";"+ groupId);
			return null;
		}
		logger.info("judge used:"+ uuid +";"+ groupId );
		boolean used = false;
		String imagePath = null;
		try {
			String md5 = imageProxy.getByUuid(uuid).getMd5sum();
			logger.info("get image md5sum:"+ md5 );
			List<? extends VmImage> vmimagelist = imageProxy.getByMd5(md5);
			List<Integer> deletingGroupList = new ArrayList<Integer>();//用来存那些已经置为deleting的group
			for( VmImage t:vmimagelist){
				Integer gId = t.getGroupId();
				String status = t.getStatus().toString();
				if(!gId.equals(Integer.valueOf(groupId))){
					if(status.equals(VmImageStatusEnum.DELETING.toString())){
						deletingGroupList.add(gId);
					}else if(status.equals(VmImageStatusEnum.AVAILABLE.toString())){
						used = true;
						break;
					}
				}
			}
			if(!used){
				logger.info("used is false");
				imagePath = imageProxy.getByUuidAndGroupId(uuid, Integer.valueOf(groupId)).getDirectory();
			}else{
				logger.info("used is true");
			}
		} catch (Exception e) {
			logger.error("数据库操作失败");
		}
		logger.info("judge done!used="+used+" imagePath="+imagePath);
		return imagePath;
		
	}
	/**
	 * 删除某个image记录，设标记为status为DELETING,此处仅仅为数据库操作
	 * @throws RpcException 
	 */
	public boolean deleteImageOnTable(String uuid, String groupId,boolean used,RpcExtra rpcExtra) throws IllegalRpcArgumentException,RpcException {
		logger.info("****deleteImageOnTable******"+ uuid +";"+ groupId+"used:"+used );
		try {
			if(groupId == null){//供发布镜像错误使用，删除所有此次uuid的条目
				VmImage temp = imageProxy.getByUuid(uuid);
				List< VmImage> deletelist = new ArrayList<VmImage>();
				while(temp != null){
					deletelist.add(temp);
					temp = imageProxy.getByUuid(uuid);
				}
				for(VmImage i : deletelist){
					imageProxy.deleteById(i.getId());
				}
				return true;
			}
			
			if(!used){
				logger.info("not used delete on table");
				
				String md5 = imageProxy.getByUuid(uuid).getMd5sum();
				List<? extends VmImage> vmimagelist = imageProxy.getByMd5(md5);
				List<Integer> deletingGroupList = new ArrayList<Integer>();//用来存那些已经置为deleting的group
				for(VmImage t:vmimagelist){
					Integer gId = t.getGroupId();
					String status = t.getStatus().toString();
					if(!t.getUuid().equals(uuid)){
						if(status.equals(VmImageStatusEnum.DELETING.toString())){
							deletingGroupList.add(gId);
						}else if(status.equals(VmImageStatusEnum.AVAILABLE.toString())){
							logger.error("jugde used error "+t.getId()+" is using!");
							throw new Exception("jugde used error "+t.getId()+" is using!");
						}
					}
				}
				
				//删除表项
				imageProxy.deleteById(imageProxy.getByUuidAndGroupId(uuid, Integer.valueOf(groupId)).getId());
				if(deletingGroupList.size() != 0){
					for(Integer i : deletingGroupList){
						imageProxy.deleteById(imageProxy.getByMd5AndGroupId(md5, i).getId());
					}
				}
				loller.info(LolLogUtil.DELETE_IMG, "delete image on table and set deleting success by uuid:" + uuid, rpcExtra);
				
				
			}else{//还有群组使用该镜像，不能将物理机上的image删除，只是逻辑的断开连接，设置标记为deleting
				logger.info("still used");
				VmImage image = imageProxy.getByUuidAndGroupId(uuid, Integer.valueOf(groupId));
				image.setStatus(VmImageStatusEnum.DELETING);
				imageProxy.update(image);
				logger.info("删除image成功，设置标志位");
				loller.info(LolLogUtil.DELETE_IMG, "delete image only on table success by uuid:" + uuid, rpcExtra);
				
			}
			return true;//返回地址,若为null：不删除物理image。若非null：按此路径删除image
			
		} catch (Exception e) {
			String why = "数据库操作失败";
			logger.warn(why,e);
			loller.error(LolLogUtil.DELETE_IMG, "database connect failed", rpcExtra);
			throw new RpcException(why, e);
		}
	}
	/**
	 * Description 通过绝对路径删除一个image
	 * @param path
	 * @return
	 * @throws IOException
	 * @author GongLingpu
	 */
	public boolean deleteImageOnServer(String routingkey,String path) throws IOException {
		path =Config.IMG_PATH+path;
		logger.info("delete Image path :"+path);
		File f = new File(path);
		DataOutputStream dos;
		dos = new DataOutputStream(new FileOutputStream(f));
		dos.close();
		if (f.exists()) {
			logger.info(f.getAbsoluteFile());
			if (!f.delete()) {
				logger.error("delete image error: "+path);
			} else {
				logger.info("delete on server done!");
				return true;
			}
		}
		return false;
		
	}
	
	//取得要将image上传到的service列表
	private List<Service> getUploadServices() {
		List<Service> services = (List<Service>) serviceProxy.getServiceByType(ServiceTypeEnum.IMAGE_SERVER, false);
		return services;
	}
	
	/*
	 * 取得uuid代表的Image的下载地址
	 * TODO:可以根据不同service的主机网络负载来决定从哪个进行下载
	 */
	private List<Service> getDownloadService(String uuid){
		List<Service> imageServers = (List<Service>) serviceProxy.getServiceByType(ServiceTypeEnum.IMAGE_SERVER, false);
		List<Service> downloadServices = new ArrayList<Service>();
		downloadServices.add(imageServers.get(0));
		return downloadServices;
	}
	
	//检查createImage方法的参数是否合法
	private boolean checkParamsCreate(VmImage image){
		if (image == null) {
			logger.warn("image is null");
			return false;
		}
		if (image.getUserId() == null) {
			logger.warn("image.userId is null");
			return false;
		}
		if (image.getDisplayName() == null) {
			logger.warn("image.displayName is null");
			return false;
		}
		if (image.getDisplayDescription() == null) {
			logger.warn("image.displayDescription is null");
			return false;
		}
		/*if (image.getIsPublic() == null) {
			logger.warn("image.isPublic is null");
			return false;
		}	*/	
		if (image.getGroupId() == null) {
			logger.warn("image.groupId is null");
			return false;
		}
		return true;
	}

	public String KeepAlive() throws Exception {
		logger.info(String.format("---------------------keepalive-------------------"));
		logger.info(String.format("---------------------database-------------------"));	
		imageProxy.findAll();		
		logger.info(String.format("---------------------keepalive-------------------"));
		logger.info(String.format("---------------------"+this.getClass()+":success-------------------"));	
		return "success";
	}

}
