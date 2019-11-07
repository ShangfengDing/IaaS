package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.Map;

import appcloud.api.beans.Image;
import appcloud.common.model.VmImage;
import appcloud.common.proxy.VmImageProxy;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.api.enums.VolumeOperationEnum;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.ImageOperateCheck;
import appcloud.openapi.checkutils.AcGroupChecker;
import appcloud.openapi.checkutils.VolumeChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;

/**
 * check the operation authority
 * @author Gong Lingpu
 * @2016年1月19日
 */
@Component
public class ImageOperateCheckImpl implements ImageOperateCheck {
    private static Logger logger = Logger.getLogger(ImageOperateCheckImpl.class);
    private static ImageOperateCheckImpl imageOperateCheck = new ImageOperateCheckImpl();
    private static CommonGenerator commonGenerator  = new CommonGenerator();
    private  VmUserProxy vmUserProxy ;
    private String message;
    private ImageOperateCheckImpl() {
        super();
        vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(VmUserProxy.class, 
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    }
    public static ImageOperateCheck getInstance() {
        return imageOperateCheck;
    }

    @Override
    public Map<String, String> checkCreateImage(Map<String, String> paramsMap) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        
        //检查用户是否存在
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }
        //检查用户是否有发布镜像的权力
        String tenantId = user.getUserId().toString();
        if(!AcGroupChecker.checkPublishImage(tenantId)) {
            message = "user " + tenantId + " want to publish image while he does not have the authority";
            RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
            logger.error(message);
            //loller.warn(LolLogUtil.CREATE_IMG, message, rpcExtra);
            return commonGenerator.operationDeny(message, null);
        }
        //检查要发布的卷的状态
        try {
            VmVolume vmVolume = VolumeChecker.checkOwner(tenantId, paramsMap.get(Constants.VOLUME_ID));
            VolumeChecker.checkInstanceStatus(tenantId, vmVolume.getInstanceUuid(), VolumeOperationEnum.CREATE_BACKUP);
            message += "volume check failed!";
        }catch (Exception e) {
            //loller.warn(LolLogUtil.CREATE_IMG, "create image :"+e.getMessage(), rpcExtra);
            logger.error(message);
            return commonGenerator.operationDeny(message, null);
        }
        
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkAuthorizeImage(Map<String, String> paramsMap) throws Exception {
        //检查用户是否存在
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    /**
     * 目前只有在发布镜像时有权限检测，其余操作没有。接口中只检测该用户是否存在
     * 日后也许会添加以下功能的权限检查
     * */
    
    @Override
    public Map<String, String> checkDeleteImage(Map<String, String> paramsMap) throws Exception {
      //检查用户是否存在
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkUpdateImage(Map<String, String> paramsMap) throws Exception {
      //检查用户是否存在
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkGetImageList(Map<String, String> paramsMap) throws Exception {
      //检查用户是否存在
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkGetImageDetail(Map<String, String> paramsMap) throws Exception {
      //检查用户是否存在
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

}
