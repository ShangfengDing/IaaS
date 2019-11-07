package appcloud.api.manager.real;

import appcloud.api.beans.Image;
import appcloud.api.checkutils.AcGroupChecker;
import appcloud.api.checkutils.StringChecker;
import appcloud.api.checkutils.VolumeChecker;
import appcloud.api.constant.ImageMetadata;
import appcloud.api.enums.VolumeOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.ImageManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmImage.VmImageDiskFormatEnum;
import appcloud.common.model.VmImage.VmImageStatusEnum;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.UuidUtil;
import appcloud.common.util.query.Filter.FilterLogic;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.rpc.tools.RpcException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author jianglei
 *
 */
public class RealImageManager implements ImageManager {
	private static Logger logger = Logger.getLogger(RealImageManager.class);
	
	private VmImageProxy imageProxy ;
	private BeansGenerator generator;
	private ResourceSchedulerService scheduler;
	private VmUserProxy userProxy;
	
	private static RealImageManager manager = new RealImageManager();

	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealImageManager.class);
	
	public static RealImageManager getInstance() {
		return manager;
	}
	
	private RealImageManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		generator = BeansGenerator.getInstance();
		imageProxy = (VmImageProxy) ConnectionFactory.getTipProxy(
				VmImageProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		userProxy = (VmUserProxy) ConnectionFactory.getTipProxy(VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

//	@Override
//	public List<Image> getList(String tenantId, boolean detailed, String groupId, String type, String status) throws Exception {
//		logger.info(String.format("User %s request to GET IMAGES", tenantId));
//		return getImages(tenantId, detailed, groupId, type, status);
//	}

	@Override
	public List<Image> getList(String tenantId, boolean detailed, String groupId, String diskFormat, String type, String email, String status, String imageName, String software, String description) throws Exception {
		logger.info(String.format("admin %s request to GET IMAGES", tenantId));
		return getImages(tenantId, true, groupId,diskFormat, type, email, status,imageName,software,description);
	}

	@Override
	public Image get(String tenantId, String imageId) throws Exception {
		logger.info(String.format("User %s request to GET IMAGE %s", tenantId, imageId));
		VmImage vmImage = imageProxy.getByUuid(imageId);
		if (vmImage == null) {
			throw new ItemNotFoundException("image does not exist");
		}
		// can get admin's image
		return generator.VPIToImage(vmImage, true);
		
	}
	
	
	public static void main(String[] args) throws Exception{
		/*HashMap<String, String> metadata = new HashMap<String, String>();
		metadata.put(ImageMetadata.SERVER_ID, "ed65fd176eaf4bdd856bb615cd9af9d6");
		metadata.put(ImageMetadata.VOLUME_ID, "9b7346ac21cc463391cea8435db3f43d");
		metadata.put(ImageMetadata.DISPLAY_NAME, "publishTest");
		metadata.put(ImageMetadata.DISPLAY_DESCRIPTION, "发布镜像测试");
		metadata.put(ImageMetadata.GROUP_ID_LIST, "3,4");
		
		Image image = new Image();
		image.metadata = metadata;
		image.distribution = "Windows";
		try{
			Image imageTest = new RealImageManager().create("775", image);
				System.out.println("出来了！");
				System.out.println("imageTest id : " + imageTest.id);
		}catch(Exception e){
			System.out.println("异常啦！！！");
			e.printStackTrace();
		}*/
		new RealImageManager().delete("10", "d6fadc8148a94b2ab0eeb3d6ce312d87", "4");
	}
	
	@Override
	/*public Image create(String tenantId, Image image) throws Exception {*/
	public Image create(String tenantId, Image image) throws Exception {
		String message = "";
		if(!AcGroupChecker.checkPublishImage(tenantId)) {
			message = "user " + tenantId + " want to publish image while he does not have the authority";
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
			logger.warn(message);
			loller.warn(LolLogUtil.CREATE_IMG, message, rpcExtra);
			throw new OperationFailedException("user does not have the authority to publish image");
		}
		
		HashMap<String, String> metadata = image.metadata;
		Integer userId = Integer.valueOf(tenantId);
		String serverUuid = metadata.get(ImageMetadata.SERVER_ID);
		String volumeUuid = metadata.get(ImageMetadata.VOLUME_ID);
		String account = metadata.get(ImageMetadata.ACCOUNT);
		String displayName = metadata.get(ImageMetadata.DISPLAY_NAME);
		String displayDescription = metadata.get(ImageMetadata.DISPLAY_DESCRIPTION);
		String software = metadata.get(ImageMetadata.SOFTWARE);
		/*Boolean isPublic = Boolean.valueOf(metadata.get(ImageMetadata.IS_PUBLIC));*/
		String groupIdList = metadata.get(ImageMetadata.GROUP_ID_LIST);
		String distribution = "";
		if(image.distribution != null)
			distribution = image.distribution;
		
		logger.info(String.format("User %s request to CREATE IMAGE %s", tenantId, displayName));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.CREATE_IMG, 
				String.format("User %s request to CREATE IMAGE %s", tenantId, displayName), rpcExtra);
		try {
			VmVolume vmVolume = VolumeChecker.checkOwner(tenantId, volumeUuid);
			VolumeChecker.checkInstanceStatus(tenantId, vmVolume.getInstanceUuid(), VolumeOperationEnum.CREATE_BACKUP);
		}catch (Exception e) {
			loller.warn(LolLogUtil.CREATE_IMG, "create image :"+e.getMessage(), rpcExtra);
			throw e;
		}

		String uuid = "";    //镜像发布成功后返回的镜像uuid
		List<Image> newImageList = new ArrayList<Image>();    //将要返回的创建成功的镜像列表
		try {
			/*uuid = scheduler.createImage(userId, serverUuid, volumeUuid, displayName, displayDescription, isPublic, distribution, rpcExtra);*/
			uuid = scheduler.createImage(userId, serverUuid, volumeUuid, account, displayName, displayDescription, groupIdList, distribution, software, rpcExtra);
			logger.info("return uuid : " + uuid);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_IMG, rpcExtra);
			return new Image();
		}
		
		VmImage vmImage = imageProxy.getByUuid(uuid);
		logger.info(String.format("IMAGE created successfully, uuid is %s", uuid));
		
		return generator.VPIToImage(vmImage, true);
	}

	@Override
	public boolean authorizeImage(String tenantId, String imageUuid) throws Exception {
		VmImage imageToDel = imageProxy.getByUuid(imageUuid);

		if (imageToDel.getType().equals(ImageMetadata.IMAGE_PUBLIC)) {
			return false;
		}

		String uuid = UuidUtil.getRandomUuid();
		imageToDel.setUuid(uuid);
		imageToDel.setUserId(Integer.valueOf(tenantId));
		imageToDel.setId(null);  // 为null的情况下，才会自动生成id
		imageToDel.setType(ImageMetadata.IMAGE_PUBLIC);

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);

		try {
			String result = scheduler.authorizeImage(imageToDel,rpcExtra);

			logger.info(String.format("authorize the image for all of the group", uuid));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(String tenantId, String imageId,String groupId) throws Exception {
		logger.info(String.format("User %s request to DELETE IMAGE %s", tenantId, imageId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.DELETE_IMG, 
				String.format("User %s request to DELETE IMAGE %s", tenantId, imageId), rpcExtra);
		
		VmImage vmImage = imageProxy.getByUuid(imageId);
		if (vmImage == null) {
			throw new ItemNotFoundException("image does not exist");
		}
		//FIXME anyone can delete image?
		
		boolean ok = false;
		try {
			ok = scheduler.deleteImage(imageId, groupId,rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DELETE_IMG, rpcExtra);
			return false;
		}
		//FIXME check if succeed
		if(ok){
			logger.info(String.format("IMAGE deleted successfully, uuid is %s", imageId));
			return true;
		}
		else {
			logger.info(String.format("delete IMAGE failed, uuid is %s", imageId));
			throw new OperationFailedException("delete image failed");
		}
	}
	
	@SuppressWarnings("unchecked")
	/*private List<Image> getImages(String tenantId, boolean detailed, String type, String status) throws Exception {*/
	private List<Image> getImages(String tenantId, boolean detailed, String groupId, String diskFormat, String type, String email, String status, String imageName, String software, String description) throws Exception {
		QueryObject<VmImage> query = new QueryObject<VmImage>();
		if (!StringChecker.empty(imageName)) {
			query.addFilterBean(new FilterBean<VmImage>("displayName", imageName, FilterBeanType.BOTH_LIKE), FilterLogic.OR);
		}
		if (!StringChecker.empty(software)) {
			query.addFilterBean(new FilterBean<VmImage>("software", software, FilterBeanType.BOTH_LIKE), FilterLogic.OR);
		}
		if (!StringChecker.empty(description)) {
			query.addFilterBean(new FilterBean<VmImage>("displayDescription",description, FilterBeanType.BOTH_LIKE), FilterLogic.OR);
		}

		if(!groupId.equalsIgnoreCase("all") && !StringChecker.empty(groupId)) {
			query.addFilterBean(new FilterBean<VmImage>("groupId", Integer.valueOf(groupId), FilterBeanType.EQUAL));
		}
		if (!status.equalsIgnoreCase("all")) {
			query.addFilterBean(new FilterBean<VmImage>("status", VmImageStatusEnum.valueOf(status), FilterBeanType.EQUAL));
		} else {
			query.addFilterBean(new FilterBean<VmImage>("status", VmImageStatusEnum.DELETING, FilterBeanType.NOTEQUAL));
		}
		if (!diskFormat.equalsIgnoreCase("all")) {
			query.addFilterBean(new FilterBean<VmImage>("diskFormat", VmImageDiskFormatEnum.valueOf(diskFormat), FilterBeanType.EQUAL));
		}
		if (!StringChecker.empty(tenantId)) {
			query.addFilterBean(new FilterBean<VmImage>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		}
		if (!StringChecker.empty(email)) {
			QueryObject<VmUser> userQueryObject = new QueryObject<VmUser>();
			userQueryObject.addFilterBean(new FilterBean<VmUser>("userEmail", email , FilterBeanType.BOTH_LIKE));
			List<VmUser> users = (List<VmUser>) userProxy.searchAll(userQueryObject);
			List<Integer> userIds = new ArrayList<Integer>();
			for (VmUser user: users) {
				userIds.add(user.getUserId());
			}
			query.addFilterBean(new FilterBean<VmImage>("userId", userIds, FilterBeanType.IN));
		}
		if (!type.equalsIgnoreCase("all")) {
			query.addFilterBean(new FilterBean<VmImage>("type", type, FilterBeanType.EQUAL));
		}

		List<VmImage> images = (List<VmImage>) imageProxy.searchAll(query);
		
		List<Image> imgs = new ArrayList<Image>();
		for (VmImage image : images) {
			Image temp = generator.VPIToImage(image, detailed);
				imgs.add(temp);
		}
		return imgs;
	}

	@Override
	public Image update(String tenantId, String imageId, Image image)
			throws Exception {

		logger.info(String.format("User %s request to UPDATE IMAGE %s", tenantId, imageId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.UPDATE_IMAGE, 
				String.format("User %s request to UPDATE IMAGE %s", tenantId, imageId), rpcExtra);
		
		VmImage vmImage = imageProxy.getByUuid(imageId);
		if (vmImage == null) {
			throw new ItemNotFoundException("image does not exist");
		}
		HashMap<String, String> metadata = image.metadata;
		String displayName = metadata.get(ImageMetadata.DISPLAY_NAME);
		String displayDescription = metadata.get(ImageMetadata.DISPLAY_DESCRIPTION);
		/*Boolean isPublic = Boolean.valueOf(metadata.get(ImageMetadata.IS_PUBLIC));*/
		String groupIdList = metadata.get(ImageMetadata.GROUP_ID_LIST);
		try {
			/*scheduler.updateImage(imageId, displayName, displayDescription, isPublic, rpcExtra);*/
			scheduler.updateImage(imageId, displayName, displayDescription, null, null, groupIdList, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.UPDATE_IMAGE, rpcExtra);
			return new Image();
		}
			//FIXME check if succeed

		logger.info(String.format("IMAGE updated successfully, uuid is %s", imageId));
		return generator.VPIToImage(vmImage, true);
	}
	


}
