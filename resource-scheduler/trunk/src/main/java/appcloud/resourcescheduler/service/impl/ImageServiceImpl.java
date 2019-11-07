/**
 * File: ImageServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-28
 */
package appcloud.resourcescheduler.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import appcloud.common.util.UuidUtil;
import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.MessageLog;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmImage.VmImageStatusEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.TaskStatusEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.service.ImageServerService;
import appcloud.common.service.VMSchedulerService;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.transaction.TTask;
import appcloud.common.transaction.Transaction;
import appcloud.common.util.AlertUtil;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.NfsLocationTool;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.resourcescheduler.ResourceScheduler;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.resourcescheduler.service.ImageService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 * 
 */
public class ImageServiceImpl implements ImageService {
	private static Logger logger = Logger.getLogger(VmServiceImpl.class);
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = ResourceScheduler.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.RESOURCE_SCHEDULER, ImageServiceImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	private static VmImageProxy vmImageProxy = (VmImageProxy) ConnectionManager
			.getInstance().getDBProxy(VmImageProxy.class);
	
	private static VmInstanceProxy vmInstanceProxy = (VmInstanceProxy) ConnectionManager
			.getInstance().getDBProxy(VmInstanceProxy.class);
	
	private static ImageServerService imageServer = (ImageServerService) ConnectionManager
			.getInstance().getAMQPService(ImageServerService.class,
					RoutingKeys.IMAGE_SERVER);

	private static VMSchedulerService vmScheduler = (VMSchedulerService) ConnectionManager
			.getInstance().getAMQPService(VMSchedulerService.class,
					RoutingKeys.VM_SCHEDULER);
	
	private static VolumeSchedulerService volumeScheduler = (VolumeSchedulerService) ConnectionManager
			.getInstance().getAMQPService(VolumeSchedulerService.class,
					RoutingKeys.VOLUME_SCHEDULER);

	@Override
	/*public void updateImage(String imageUuid, String name, String description,
			Boolean isPublic, RpcExtra rpcExtra) throws RpcException {*/
	public void updateImage(String imageUuid, String name, String description, String account, String software,
			String groupIdList, RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "updateImage: " + imageUuid + ", " + name + ", "
				+ description + ", " + groupIdList;;
		logger.debug(paramInfos);

		try {
			VmImage vmImage = vmImageProxy.getByUuid(imageUuid);
			vmImage.setDisplayName(name);
			vmImage.setDisplayDescription(description);
			if (account != null && account != "") {   //兼容旧版
				vmImage.setAccount(account);
			}
			if (software != null && software!="") {
				vmImage.setSoftware(software);
			}
			/*vmImage.setIsPublic(isPublic);*/
			vmImageProxy.update(vmImage);
			loller.info(LolLogUtil.UPDATE_IMAGE, paramInfos, rpcExtra);
		} catch (Exception e) {
			String error = "updateImage failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.UPDATE_IMAGE, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("镜像" + imageUuid + "更新失败", "输入参数为" + paramInfos, e);
			// throw new RpcException(error);
		}
	}

	@Override
	/*public String createImage(Integer userId, final String serverUuid,
			final String volumeUuid, String displayName, String displayDescription,
			Boolean isPublic, String distribution, final RpcExtra rpcExtra) throws RpcException {*/
	public String createImage(Integer userId, final String serverUuid,
							  final String volumeUuid,final String account,final String displayName, final String displayDescription,
							  String groupIdList, String distribution, String software, final RpcExtra rpcExtra) throws RpcException {
		logger.info("resource-scheduler start create image...");
		final String paramInfos = "createImage: " + userId + ", " + serverUuid + ", "
				+ volumeUuid + ", " + displayName + ", " + displayDescription
				+ ", " + groupIdList + ", " + distribution+","+software;
		logger.info(paramInfos);

		final List<VmImage> createImages = new ArrayList<VmImage>();
		try {
			final VmInstance instance = vmInstanceProxy.getByUuid(serverUuid, false, false, false, false, false, false, false, false);
			if (instance == null) {
				logger.error("instance not found when create image");
				return null;
			}
			final List<Integer> allGroupIds = new ArrayList<Integer>();
			for (String groupId : groupIdList.split(",")) {
				allGroupIds.add(Integer.parseInt(groupId));
			}
			//新new一个image对象，首先获取该image的uuid、directory和services
			final VmImage tmpImage = imageServer.preCreateImage();
			logger.info("new image uuid: " + tmpImage.getUuid());
			//先存入数据库，显示创建进度
			tmpImage.setUserId(userId);
			tmpImage.setName(volumeUuid + ".img");
			tmpImage.setDisplayName(displayName);
			tmpImage.setDisplayDescription(displayDescription);
			tmpImage.setOsMode(instance.getOsMode());
			tmpImage.setOsArch(instance.getOsArch());
			tmpImage.setOsType(instance.getOsType());
			tmpImage.setDistribution(distribution);
			tmpImage.setType(VmImage.VmImageTypeEnum.PRIVATE.toString());
			tmpImage.setSoftware(software);
			tmpImage.setAccount(account);
			tmpImage.setSignal(UuidUtil.getRandomUuid());
			tmpImage.setMd5sum("0");//数据库里md5sum不能为空
			for (Integer groupId : allGroupIds) {
				logger.info("groupId : " + groupId);
				tmpImage.setGroupId(groupId);
				VmImage image = imageServer.createImage(tmpImage, rpcExtra);
				if (image.getStatus() != VmImageStatusEnum.CREATING) {
					String why = "db create image failed!";
					logger.error(why);
					loller.error(LolLogUtil.CREATE_IMG, why + " image uuid:" + tmpImage.getUuid(), rpcExtra);
					throw new RpcException(why);
				}
				createImages.add(image);
			}

			Transaction transaction = new Transaction( new TTask() {

				@Override
				public boolean preProcess() {
					return checkAndSetVmTaskStatus(serverUuid, TaskStatusEnum.REBOOTING);
				}

				@Override
				public void process() throws Exception {
					createImage(serverUuid, volumeUuid, instance, paramInfos, tmpImage, createImages, allGroupIds, rpcExtra);
				}

				@Override
				public void onCompleted() {
					resumeVmTaskStatus(serverUuid);
				}

				@Override
				public void postProcess() throws Exception {}

				@Override
				public void onError() {}

			});

			transaction.asyncExecute(rpcExtra);

			if(tmpImage != null) {
				logger.info("db create image finished, image uuid : " + tmpImage.getUuid());
				return tmpImage.getUuid();
			}
			return "";
		} catch (Exception e) {
			String error = "createImage failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.CREATE_IMG, error + e.getMessage(), rpcExtra);
			AlertUtil.alert("虚拟机" + serverUuid + "创建镜像模版失败", "输入参数为" + paramInfos, e);
			return "";
		}
	}

	//异步调用实际的镜像创建过程，正常返回镜像的uuid，异常返回null
	private String createImage(final String serverUuid, final String volumeUuid, final VmInstance instance, final String paramInfos,
							   VmImage tmpImage, List<VmImage> createImages, List<Integer> allGroupIds, final RpcExtra rpcExtra) throws RpcException {
		try {
			//整合所要发布的镜像，获取整合后的镜像的md5值
			//在获得md5值时进行了备份（convert）
			String imageMd5sum = volumeScheduler.gainImageMd5sum(volumeUuid, tmpImage, rpcExtra);
			tmpImage.setMd5sum(imageMd5sum);
			//根据md5获取所要发布镜像所在的所有群组Id
			List<Integer> imageGroupIds = volumeScheduler.gainImageGroupIds(imageMd5sum, rpcExtra);
			//输出镜像所在群组Id，仅用于维护时查看。
			String ids = "";
			for(Integer i : imageGroupIds)
				ids += (i + ", ");
			logger.info("this image exist in : " + ids);
			//在数据库中存储md5sum
			Iterator<VmImage> createImagesIterator = createImages.iterator();
			while (createImagesIterator.hasNext()) {
				VmImage image = createImagesIterator.next();
				image.setMd5sum(imageMd5sum);
			}
			volumeScheduler.updateImage(createImages, volumeUuid, VmImageStatusEnum.CREATING, rpcExtra);
			//根据镜像所在的群组Id过滤并获取要发布镜像的群组Id
			List<Integer> selectGroupIds = new ArrayList<Integer>();
			if (imageGroupIds == null || imageGroupIds.size() == 0) {
				selectGroupIds.addAll(allGroupIds);
			} else {
				Iterator<Integer> allGroupIdsIterator = allGroupIds.iterator();
				while (allGroupIdsIterator.hasNext()) {
					Integer groupId = allGroupIdsIterator.next();
					if (!imageGroupIds.contains(groupId)) {
						selectGroupIds.add(groupId);
					} else {
						//镜像已经在群组中，没有必要再次发布，删除刚存入的数据库信息
						imageServer.deleteImageOnTable(tmpImage.getUuid(), groupId.toString(), true, rpcExtra);
						createImagesIterator = createImages.iterator();
						while (createImagesIterator.hasNext()) {
							if (createImagesIterator.next().getGroupId() == groupId) {
								createImagesIterator.remove();
							}
						}
					}
				}
			}
			if(selectGroupIds == null || selectGroupIds.size()<=0) {
				logger.debug("image already exists in selected groups");
				volumeScheduler.deleteImageVolume(volumeUuid, tmpImage, rpcExtra);
				return null;
			}
			logger.info("the size of the group that need to publish image is " + selectGroupIds.size());
			//将发布的镜像上传到镜像服务器,只有当原来没有发过该镜像时，才向image server发送该镜像
			if(imageGroupIds == null || imageGroupIds.size() == 0) {
				volumeScheduler.uploadImage(volumeUuid, tmpImage, rpcExtra);
			} else {
				logger.info("do not upload image, image exist in image server");
			}
			//保存成功后，删除当前的镜像
			volumeScheduler.deleteImageVolume(volumeUuid, tmpImage, rpcExtra);
			//更新镜像状态
			String directory = NfsLocationTool.getNfsLocation(imageMd5sum+".img");
			createImagesIterator = createImages.iterator();
			while (createImagesIterator.hasNext()) {
				VmImage image = createImagesIterator.next();
				image.setDirectory(directory);
			}
			volumeScheduler.updateImage(createImages, volumeUuid, VmImageStatusEnum.AVAILABLE, rpcExtra);
			final VmImage newImage = createImages.get(0);
			if (newImage == null){
				return null;
			}else{
				logger.debug("newImage: " + newImage);
			}
			//根据镜像所要发布的群组Id过滤并获取镜像所要发布的集群Id
			final List<Integer> selectClusterIds = volumeScheduler.gainClusterIdList(imageGroupIds, selectGroupIds, rpcExtra);
			if(selectClusterIds == null || selectClusterIds.size() < 1){
				logger.debug("the image is already exist, createImag.publishImage succeed!");
				return null;
			}
			logger.info("total cluster that need to publish image is :" + selectClusterIds.size());
			try {
				if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
					vmScheduler.stopVM(serverUuid, rpcExtra);
					logger.debug("createImage.vmScheduler.stopVM succeed!");
				}
				/*VmImage image = volumeScheduler.publishImage(volumeUuid, newImage, rpcExtra);*/
				for(Integer clusterId : selectClusterIds){
					VmImage image = volumeScheduler.publishImage(volumeUuid, newImage, clusterId, rpcExtra);
					if(image != null){
						logger.debug("createImag.publishImage succeed!");
					}else{
						logger.debug("the image is already exist, createImag.publishImage succeed!");
					}
				}
			} catch (RpcException e) {
				String error = "createImag.publishImage failed! " + paramInfos;
				logger.error(error, e);
				try {
					imageServer.deleteImageOnTable(newImage.getUuid(), null, false, rpcExtra);
				} catch (Exception e1) {
					logger.error(e1.getMessage());
				}
				AlertUtil.alert("虚拟机" + serverUuid + "创建镜像模版失败", "输入参数为" + paramInfos, e);
			}

			if(tmpImage !=null){
				logger.info("create image finished, image uuid : " + tmpImage.getUuid());
				return tmpImage.getUuid();
			}
			return null;
		} catch (Exception e) {
			volumeScheduler.updateImage(createImages, volumeUuid, VmImageStatusEnum.ERROR_DELETING, rpcExtra);
			for (VmImage image : createImages) {
                volumeScheduler.deleteImageVolume(volumeUuid, image, rpcExtra);
            }
			return null;
		}
	}

	//授权镜像为公开镜像
	// 1. save 数据库
	// 2. 然后从一个imageService所在结点复制到所有的imageServer中
	@Override
	public String authorizeImage(VmImage vmImage, RpcExtra rpcExtra) throws RpcException {
		logger.info("authorize image info …… ");
		logger.debug(vmImage.toString());
		try {
			String sourceDir = vmImage.getDirectory();
			vmImage = imageServer.preCreateImage(vmImage); //带有service
			vmImage = imageServer.authorizeImage(vmImage, rpcExtra);
			vmImage = volumeScheduler.authorizeImage(sourceDir,vmImage,rpcExtra);
			vmImage = imageServer.updateAuthorizeImage(vmImage, rpcExtra);
			return vmImage.getUuid();
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}


	@Override
	public boolean deleteImage(String imageUuid, String groupId,RpcExtra rpcExtra) throws RpcException {
		logger.info("*****deleteImage******"+ imageUuid +";"+ groupId );
		try {
				logger.info("delete image on server succeeded: " + imageUuid);
				loller.info(LolLogUtil.DELETE_IMG, "delete image on server succeeded: " + imageUuid, rpcExtra);
				if(imageServer.deleteImage(imageUuid, groupId , rpcExtra)){
					logger.info("in resourcesheduler delete image succeeded: " + imageUuid);
					loller.info(LolLogUtil.DELETE_IMG, "delete image succeeded: " + imageUuid, rpcExtra);
					return true;
				}
			
		}catch (Exception e) {
			String error = "deleteImage failed! exception occured" + imageUuid;
			logger.error("deleteImage failed! exception occured" + imageUuid, e);
			loller.error(LolLogUtil.DELETE_IMG, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("删除镜像模版发生异常", imageUuid, e);
			return false;
		}
		return false;
		
	}

	private boolean checkAndSetVmTaskStatus(String uuid, TaskStatusEnum status){
		try{
			VmInstance instance = vmInstanceProxy.getByUuid(
					uuid, false, false, false, false, false,
					false, false, false);
			logger.info("instance: " + instance);
			if (instance == null || instance.getTaskStatus().compareTo(TaskStatusEnum.READY) != 0) {
				return false;
			} else {
				instance.setTaskStatus(status);
				vmInstanceProxy.update(instance);
				return true;
			}
		}
		catch (Exception e){
			logger.error("checkAndSetVmTaskStatus failed uuid:" + uuid, e);
			return false;
		}
	}

	private void resumeVmTaskStatus(String uuid){
		try{
			VmInstance instance = vmInstanceProxy.getByUuid(
					uuid, false, false, false, false, false,
					false, false, false);
			logger.info("instance: " + instance);
			if (instance == null) {
				return;
			} else {
				instance.setTaskStatus(TaskStatusEnum.READY);
				vmInstanceProxy.update(instance);
				return;
			}
		}
		catch (Exception e){
			AlertUtil.alert("虚拟机" + uuid +"TaskStatus恢复失败！", e);
			return;
		}
	}

	@Override
	public String KeepAlive() throws Exception {
//		try{
			return imageServer.KeepAlive();
//		}
//		catch(RpcException rpcEx) {
//			return "fail";
//		}
	}
}
