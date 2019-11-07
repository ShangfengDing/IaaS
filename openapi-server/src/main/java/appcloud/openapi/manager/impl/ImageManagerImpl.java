package appcloud.openapi.manager.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.free4lab.utils.recommend.Constant;

import appcloud.api.beans.Image;
import appcloud.api.constant.ImageMetadata;
import appcloud.api.enums.VolumeOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmImage.VmImageDiskFormatEnum;
import appcloud.common.model.VmImage.VmImageStatusEnum;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.Filter.FilterLogic;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.openapi.checkutils.AcGroupChecker;
import appcloud.openapi.checkutils.VolumeChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.manager.ImageManager;

@Component
public class ImageManagerImpl implements ImageManager{

    private static Logger logger = Logger.getLogger(CommonManagerImpl.class);
    private static VmUserProxy vmUserProxy;
    private VmImageProxy imageProxy;
    private static ImageManagerImpl imageManager = new ImageManagerImpl();
    
    private ImageManagerImpl() {
        super();
        imageProxy = (VmImageProxy) ConnectionFactory.getTipProxy(
                VmImageProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    }
   
    @Override
    public Image create(Map<String, String> paramsMap) throws Exception {
        String message = "";
        String tenantId = paramsMap.get(Constants.TENANT_ID);
        if(!AcGroupChecker.checkPublishImage(paramsMap.get(Constants.TENANT_ID))) {
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
        metadata.put(ImageMetadata.GROUP_ID_LIST, paramsMap.get(Constants.GROUP_ID_LIST));
        newImage.metadata = metadata;
        newImage.distribution = paramsMap.get(Constants.DISTRIBUTION);
        logger.info(String.format("User %s request to CREATE IMAGE %s", tenantId, paramsMap.get(Constants.DISPLAY_NAME)));
       
        return newImage;
    }

    @Override
    public VmImage deleteImage(Map<String, String> paramsMap) throws Exception {
        String tenantId = paramsMap.get(Constants.TENANT_ID);
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
        Image image = new Image();
        image.tenantId = paramsMap.get(Constants.TENANT_ID);
        image.id = paramsMap.get(Constants.IMAGE_UUID);
        image.metadata = metadata;
        return image;
    }

    @Override
    public QueryObject<VmImage> getImageList(Map<String, String> paramsMap) {
        QueryObject<VmImage> query = new QueryObject<VmImage>();
        String groupId = paramsMap.get(Constants.GROUP_ID);
        String tenantId = paramsMap.get(Constants.TENANT_ID);
        String imageType = paramsMap.get(Constants.IMAGE_TYPE);
        String imageStatus = paramsMap.get(Constants.IMAGE_STATUS);
        if(!groupId.equalsIgnoreCase("all")) {
            query.addFilterBean(new FilterBean<VmImage>("groupId", Integer.valueOf(groupId), FilterBeanType.EQUAL));
        }
        query.addFilterBean(new FilterBean<VmImage>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL), FilterLogic.OR);
        if (!imageStatus.equalsIgnoreCase("all")) {
            query.addFilterBean(new FilterBean<VmImage>("status", VmImageStatusEnum.valueOf(imageStatus), FilterBeanType.EQUAL), FilterLogic.AND);
        }
        else {
            query.addFilterBean(new FilterBean<VmImage>("status", VmImageStatusEnum.DELETING, FilterBeanType.NOTEQUAL), FilterLogic.AND);
        }
        if (!imageType.equalsIgnoreCase("all")) {
            query.addFilterBean(new FilterBean<VmImage>("diskFormat", VmImageDiskFormatEnum.valueOf(imageType), FilterBeanType.EQUAL), FilterLogic.AND);
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
