package appcloud.openapi.operate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.api.beans.Image;
import appcloud.api.constant.ImageMetadata;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.service.ImageServerService;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.QueryObject;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.constant.ResultConstants;
import appcloud.openapi.datatype.ImageDetailItem;
import appcloud.openapi.datatype.ImageDetailSet;
import appcloud.openapi.datatype.InstanceStatusSet;
import appcloud.openapi.manager.util.BeansGenerator;
import appcloud.openapi.manager.util.DealException;
import appcloud.openapi.operate.ImageOperate;
import appcloud.rpc.tools.RpcException;

@Component
public class ImageOperateImpl implements ImageOperate{
    private static Logger logger = Logger.getLogger(ImageOperateImpl.class);
    private VmImageProxy imageProxy = (VmImageProxy) ConnectionFactory.getTipProxy(VmImageProxy.class,
            appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);;
    private ResourceSchedulerService scheduler;
    private BeansGenerator generator;
    private  VmUserProxy vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(VmUserProxy.class, 
            appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);;
    
    @Override
    public Map<String, String> createImage(String appKeyId, Image newImage, String transactionId) throws Exception {
        VmUser user = vmUserProxy.getByAppKeyId(appKeyId);
        HashMap<String, String> metadata = newImage.metadata;
        Integer userId = user.getUserId();
        String serverUuid = metadata.get(ImageMetadata.SERVER_ID);
        String volumeUuid = metadata.get(ImageMetadata.VOLUME_ID);
        String displayName = metadata.get(ImageMetadata.DISPLAY_NAME);
        String displayDescription = metadata.get(ImageMetadata.DISPLAY_DESCRIPTION);
        String groupIdList = metadata.get(ImageMetadata.GROUP_ID_LIST);
        String distribution = "";
        RpcExtra rpcExtra = new RpcExtra(transactionId, userId.toString());
        if(newImage.distribution != null)
            distribution = newImage.distribution;
        String uuid = "";    //镜像发布成功后返回的镜像uuid
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            /*uuid = scheduler.createImage(userId, serverUuid, volumeUuid, displayName, displayDescription, isPublic, distribution, rpcExtra);*/
            uuid = scheduler.createImage(userId, serverUuid, volumeUuid, displayName, displayDescription, groupIdList, distribution, rpcExtra);
            logger.info("return uuid : " + uuid);
            VmImage vmImage = imageProxy.getByUuid(uuid);
            logger.info(String.format("IMAGE created successfully, uuid is %s", uuid));
            generator.VPIToImage(vmImage, true);
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
            resultMap.put(Constants.IMAGE_UUID, uuid);
        } catch(RpcException e) {
            DealException.isRSTimeoutException(e, LolLogUtil.CREATE_IMG, rpcExtra);
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to RPC exception.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
        }
        return resultMap;
    }


    @Override
    public Map<String, String> deleteImage(String appKeyId, VmImage imageToDel, String transactionId) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        VmUser user = vmUserProxy.getByAppKeyId(appKeyId);
        Integer userId = user.getUserId();
        RpcExtra rpcExtra = new RpcExtra(transactionId, userId.toString());
        String imageUuid = imageToDel.getUuid();
        String groupId = imageToDel.getGroupId().toString();
        boolean ok = false;
        try {
            ok = scheduler.deleteImage(imageUuid, groupId,rpcExtra);
            if(ok){
                logger.info(String.format("IMAGE deleted successfully, uuid is %s", imageUuid));
                resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
            }else {
                logger.info(String.format("delete IMAGE failed, uuid is %s", imageUuid));
                resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
                resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to RPC exception.");
                resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
                throw new OperationFailedException("delete image failed");
            }
        } catch(RpcException e) {
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to RPC exception.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
        }
        return resultMap;
    }


    @Override
    public Map<String, String> updateImage(Image imageToUpdate, String transactionId) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        RpcExtra rpcExtra = new RpcExtra(transactionId, imageToUpdate.tenantId);
        HashMap<String, String> metadata = imageToUpdate.metadata;
        String displayName = metadata.get(ImageMetadata.DISPLAY_NAME);
        String displayDescription = metadata.get(ImageMetadata.DISPLAY_DESCRIPTION);
        /*Boolean isPublic = Boolean.valueOf(metadata.get(ImageMetadata.IS_PUBLIC));*/
        String groupIdList = metadata.get(ImageMetadata.GROUP_ID_LIST);
        String imageUuid = imageToUpdate.id;
        try {
            /*scheduler.updateImage(imageId, displayName, displayDescription, isPublic, rpcExtra);*/
            scheduler.updateImage(imageUuid, displayName, displayDescription, groupIdList, rpcExtra);
            logger.info(String.format("IMAGE updated successfully, uuid is %s", imageUuid));
            VmImage vmImage = imageProxy.getByUuid(imageUuid);
            generator.VPIToImage(vmImage, true);
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
        } catch(RpcException e) {
            DealException.isRSTimeoutException(e, LolLogUtil.UPDATE_IMAGE, rpcExtra);
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to RPC exception.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
        }
        
        return resultMap;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getImageList(QueryObject<VmImage> query, Map<String, String> paramsMap, boolean detailed, String transactionId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        /**
         * 首先根据页码和每页总数获取实例  (with metadata, network)
         * 注：由于searchAll的page参数是从0开始的，所以此处应该对传进来的PageNumber参数进行减1处理
         */
        List<VmImage> images = (List<VmImage>) imageProxy.searchAll(query, Integer.parseInt(paramsMap.get(Constants.PAGE_NUMBER))-1,
                Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)));
        List<Image> imgs = new ArrayList<Image>();
        List<ImageDetailItem> imageDetailItems = new ArrayList<ImageDetailItem>();
        if(null==transactionId || transactionId.length()<1) {
            logger.info("RequestId is null or is error!");
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
            return resultMap;
        }
        for (VmImage image : images) {
            ImageDetailItem tmp = new ImageDetailItem(generator.VPIToImage(image, detailed)); 
            imageDetailItems.add(tmp);
        }
        
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        resultMap.put(ResultConstants.PAGE_NUMBER, Integer.parseInt(paramsMap.get(Constants.PAGE_NUMBER)));
        resultMap.put(ResultConstants.PAGE_SIZE, Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)));
        resultMap.put(ResultConstants.IMAGE_DETAIL_SET, new ImageDetailSet(imageDetailItems));
        logger.info("show Image detail successfully");
        return resultMap;
    }


    @Override
    public Map<String, Object> getImageDetail(VmImage imageToShow, String transactionId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Image image =  generator.VPIToImage(imageToShow, true);
        ImageDetailItem imageDetailItem = new ImageDetailItem(image);
        logger.info("show Image detail successfully");
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        resultMap.put(ResultConstants.IMAGE_DETAIL_ITEM, imageDetailItem);
        return resultMap;
    }
}
