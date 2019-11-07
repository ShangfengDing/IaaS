package appcloud.openapi.manager.impl;

import appcloud.api.beans.Image;
import appcloud.api.constant.ImageMetadata;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmImage.VmImageDiskFormatEnum;
import appcloud.common.model.VmImage.VmImageStatusEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.Filter.FilterLogic;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.openapi.checkutils.AcGroupChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.manager.ImageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImageManagerImpl implements ImageManager{

    private static Logger logger = Logger.getLogger(CommonManagerImpl.class);
    private static VmInstanceProxy instanceProxy;
    private VmImageProxy imageProxy;
    private static VmUserProxy vmUserProxy;
    
    private ImageManagerImpl() {
        super();
        imageProxy = (VmImageProxy) ConnectionFactory.getTipProxy(
                VmImageProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        instanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
                VmInstanceProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
				VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    }
   
    @Override
    public Image create(Map<String, String> paramsMap) throws Exception {
        String message = "";
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		VmUser vmuser = vmUserProxy.getByAppKeyId(appKeyId);
		int userId = vmuser.getUserId();
		String tenantId = userId+"";
        if(!AcGroupChecker.checkPublishImage(tenantId)) {
            message = "user " + tenantId + " want to publish image while he does not have the authority";
            logger.warn(message);
            throw new OperationFailedException("user does not have the authority to publish image");
        }
        
        Image newImage = new Image();
        HashMap<String, String> metadata = new HashMap<String, String>();
        metadata.put(ImageMetadata.SERVER_ID, paramsMap.get(Constants.SERVER_ID));
        metadata.put(ImageMetadata.VOLUME_ID, paramsMap.get(Constants.VOLUME_ID));
        metadata.put(ImageMetadata.DISPLAY_NAME, paramsMap.get(Constants.DISPLAY_NAME));
        metadata.put(ImageMetadata.DISPLAY_DESCRIPTION, paramsMap.get(Constants.DISPLAY_DESCRIPTION));
        metadata.put(ImageMetadata.SOFTWARE, paramsMap.get(Constants.SOFTWARE));
        metadata.put(ImageMetadata.ACCOUNT, paramsMap.get(Constants.ACCOUNT));
        metadata.put(ImageMetadata.GROUP_ID_LIST, vmuser.getGroupId().toString());
        VmInstance instance = instanceProxy.getByUuid(paramsMap.get(Constants.SERVER_ID), false, 
        		false, false, false, false, false, false, false);
        metadata.put(ImageMetadata.OS_TYPE, (null == instance.getOsType())?null:instance.getOsType().toString());
        metadata.put(ImageMetadata.OS_ARCH,(null == instance.getOsArch())?null:instance.getOsArch().toString());
        metadata.put(ImageMetadata.OS_MODE, (null == instance.getOsMode())?null:instance.getOsMode().toString());
        newImage.metadata = metadata;
        newImage.distribution = paramsMap.get(Constants.DISTRIBUTION);
        logger.info(String.format("User %s request to CREATE IMAGE %s", tenantId, paramsMap.get(Constants.DISPLAY_NAME)));
       
        return newImage;
    }

    @Override
    public VmImage authorizeImage(Map<String, String> paramsMap) throws Exception {
        VmImage vmImage = imageProxy.getByUuid(paramsMap.get(Constants.IMAGE_UUID));
        if (vmImage == null) {
            throw new ItemNotFoundException("image does not exist");
        }
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if (!vmImage.getGroupId().equals(user.getGroupId()) || !vmImage.getType().equals(Constants.IMAGE_PRIVATE)) {
            logger.warn("not in the same group or image of type is not ready");
            throw new ItemNotFoundException("not in the same group or image of type is not ready");
        }
        vmImage.setUserId(user.getUserId());
        return vmImage;
    }

    @Override
    public VmImage deleteImage(Map<String, String> paramsMap) throws Exception {
        //String tenantId = paramsMap.get(Constants.TENANT_ID);
        //TODO current version we do not check the tenant permision by id.
        //Maybe we add this function later
        VmImage vmImage = imageProxy.getByUuid(paramsMap.get(Constants.IMAGE_UUID));
        if (vmImage == null) {
            throw new ItemNotFoundException("image does not exist");
        }
        
        return vmImage;
    }

    @Override
    public Image updateImage(Map<String, String> paramsMap) throws Exception {
        VmImage vmImage = imageProxy.getByUuid(paramsMap.get(Constants.IMAGE_UUID));
        if (vmImage == null) {
            throw new ItemNotFoundException("image does not exist");
        }
        HashMap<String, String> metadata = new HashMap<String, String>();
        metadata.put(ImageMetadata.DISPLAY_NAME, paramsMap.get(Constants.DISPLAY_NAME));
        metadata.put(ImageMetadata.DISPLAY_DESCRIPTION, paramsMap.get(Constants.DISPLAY_DESCRIPTION));
        metadata.put(ImageMetadata.SOFTWARE, paramsMap.get(Constants.SOFTWARE));
        metadata.put(ImageMetadata.ACCOUNT, paramsMap.get(Constants.ACCOUNT));
        List<VmImage> vmImagelist = (List<VmImage>) imageProxy.getByMd5(vmImage.getMd5sum());
        String groupIdlist = null;
        if(null!=vmImagelist && vmImagelist.size()>0)
        {
        	groupIdlist = vmImagelist.get(0).getGroupId().toString();
        	for(int i=1;i<vmImagelist.size();i++)
        		groupIdlist+=","+vmImagelist.get(i).getGroupId();
        }
        metadata.put(ImageMetadata.GROUP_ID_LIST, groupIdlist);
        Image image = new Image();
        image.tenantId = paramsMap.get(Constants.TENANT_ID);
        image.id = paramsMap.get(Constants.IMAGE_UUID);
        image.metadata = metadata;
        return image;
    }

    @Override
    public QueryObject<VmImage> getImageList(Map<String, String> paramsMap) {
        QueryObject<VmImage> query = new QueryObject<VmImage>();
        VmUser user = null;
		try {
			user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String groupId  =user.getGroupId().toString(); //groupId
        String tenantId = user.getUserId().toString(); //用户Id
        String imageType = paramsMap.get(Constants.IMAGE_TYPE);  //镜像类型
        String imageStatus = paramsMap.get(Constants.IMAGE_STATUS);  //镜像状态
        String imageName = paramsMap.get(Constants.IMAGE_NAME);  // 镜像的名称
        String imageDescription = paramsMap.get(Constants.DISPLAY_DESCRIPTION);
        String type = paramsMap.get(Constants.TYPE); // 类型，All、public、group、private
        String distribution = paramsMap.get(Constants.DISTRIBUTION);
        String software = paramsMap.get(Constants.SOFTWARE);

        if(!StringUtils.isEmpty(imageName)) {
            query.addFilterBean(new FilterBean<VmImage>("displayName", imageName, FilterBeanType.BOTH_LIKE), FilterLogic.OR);
        }
        if(!StringUtils.isEmpty(software)) {
            query.addFilterBean(new FilterBean<VmImage>("software", software, FilterBeanType.BOTH_LIKE), FilterLogic.OR);
        }
        if (!StringUtils.isEmpty(imageDescription)) {
            query.addFilterBean(new FilterBean<VmImage>("displayDescription", imageDescription, FilterBeanType.BOTH_LIKE), FilterLogic.OR);
        }
        // 根据type区分
        query.addFilterBean(new FilterBean<VmImage>("type", type, FilterBeanType.EQUAL));
        switch (type) {
            case Constants.IMAGE_ALL:
                query.addFilterBean(new FilterBean<VmImage>("groupId", Integer.valueOf(groupId), FilterBeanType.EQUAL));
                query.addFilterBean(new FilterBean<VmImage>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
                break;
            case Constants.IMAGE_PUBLIC:
                break;
            case Constants.IMAGE_GROUP:
                query.addFilterBean(new FilterBean<VmImage>("groupId", Integer.valueOf(groupId), FilterBeanType.EQUAL));
                break;
            case Constants.IMAGE_PRIVATE:
                query.addFilterBean(new FilterBean<VmImage>("groupId", Integer.valueOf(groupId), FilterBeanType.EQUAL));
                query.addFilterBean(new FilterBean<VmImage>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
                break;
        }
        if (!imageStatus.equalsIgnoreCase("all")) {
            query.addFilterBean(new FilterBean<VmImage>("status", VmImageStatusEnum.valueOf(imageStatus.toUpperCase()), FilterBeanType.EQUAL));
        }
        else {
            query.addFilterBean(new FilterBean<VmImage>("status", VmImageStatusEnum.DELETING, FilterBeanType.NOTEQUAL));
        }
        if (!distribution.equalsIgnoreCase("all")) {
            query.addFilterBean(new FilterBean<VmImage>("distribution", distribution, FilterBeanType.EQUAL));
        }
        if (!imageType.equalsIgnoreCase("all")) {
            query.addFilterBean(new FilterBean<VmImage>("diskFormat", VmImageDiskFormatEnum.valueOf(imageType.toUpperCase()), FilterBeanType.EQUAL));
        }
        return query;
    }

    @Override
    public VmImage getImageDetail(Map<String, String> paramsMap) throws Exception {
        String tenantId = paramsMap.get(Constants.TENANT_ID);
        String imageUuid = paramsMap.get(Constants.IMAGE_UUID);
        logger.info(String.format("User %s request to GET IMAGE %s detail", tenantId, imageUuid));
        VmImage vmImage = imageProxy.getByUuid(imageUuid);
        if (vmImage == null) {
            throw new ItemNotFoundException("image does not exist");
        }
        return vmImage;
    }
    
}
