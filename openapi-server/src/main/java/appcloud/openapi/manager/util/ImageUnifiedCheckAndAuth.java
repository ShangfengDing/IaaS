package appcloud.openapi.manager.util;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import appcloud.openapi.check.CommonParamsCheck;
import appcloud.openapi.check.ImageOperateCheck;
import appcloud.openapi.check.ImageParamsCheck;
import appcloud.openapi.check.impl.CommonParamsCheckImpl;
import appcloud.openapi.check.impl.ImageOperateCheckImpl;
import appcloud.openapi.check.impl.ImageParamsCheckImpl;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.CommonManager;
import appcloud.openapi.manager.impl.CommonManagerImpl;

@Component
public class ImageUnifiedCheckAndAuth {
    /**
     * 该类主要用于在对镜像具体操作前进行统一的认证鉴权管理，主要检查请求参数的合理性和有效性
     */
    private CommonManager commonManager = CommonManagerImpl.getInstance();
    private CommonParamsCheck commonParamsCheck = CommonParamsCheckImpl.getInstance();
    private ImageParamsCheck imageParamsCheck = ImageParamsCheckImpl.getInstance();
    private ImageOperateCheck imageOperateCheck = ImageOperateCheckImpl.getInstance();
    private final Logger logger = Logger.getLogger(InstanceUnifiedCheckAndAuth.class);
    
    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description:  
     * @param paramsMap
     * @param defaultParamsMap
     * @return
     */
    public Map<String, String> ImageCheckAndAuth(Map<String, String> paramsMap, Map<String, String> defaultParamsMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
      
        try {
            //检查接口公共请求参数
            resultMap = commonParamsCheck.checkCommonParams(paramsMap);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("The request common params of image API was not authenticated successfully!");
                return resultMap;
            }
            
            //检查接口自身参数
            switch(paramsMap.get(Constants.ACTION)) {
                case ActionConstants.CREATE_IMAGE :
                    resultMap = imageParamsCheck.checkCreateParams(paramsMap);
                    break;
                case ActionConstants.DELETE_IMAGE :
                    resultMap = imageParamsCheck.checkDeleteParams(paramsMap);
                    break;
                case ActionConstants.UPDATE_IMAGE:
                    resultMap = imageParamsCheck.checkUpdateParams(paramsMap);
                    break;
                case ActionConstants.GET_IMAGE_DETAIL :
                    resultMap = imageParamsCheck.checkShowImageDetailParams(paramsMap);
                    break;
                case ActionConstants.GET_IMAGE_LIST_WITH_DETAIL:
                    resultMap = imageParamsCheck.checkGetListParams(paramsMap);
                    break;
//                case ActionConstants.IMAGE_INDEX :
//                    resultMap = imageParamsCheck.check(paramsMap);
//                    break;
                default :
                    resultMap = null;
                    break;
            }
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("The request specific params of image API was not authenticated successfully!");
                return resultMap;
            }
            
           //签名认证
            resultMap = commonManager.getAuthenticate(paramsMap);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("The request signature was not authenticated successfully!");
                return resultMap;
            }
            //将默认参数加入到参数列表中
            if(null!=defaultParamsMap && !defaultParamsMap.isEmpty()) {
                logger.info("Transfer default params to paramsMap.");
                for(Map.Entry<String, String> entry : defaultParamsMap.entrySet()) {
                    if (StringUtil.isEmpty(paramsMap.get(entry.getKey()))) {
                        paramsMap.put(entry.getKey(), entry.getValue());
                    }
                }
                logger.info("Transfer default params successfully.");
            }
            
            //检查操作权限
            switch(paramsMap.get(Constants.ACTION)) {
                case ActionConstants.CREATE_IMAGE :
                    resultMap = imageOperateCheck.checkCreateImage(paramsMap);
                    break;
                case ActionConstants.DELETE_IMAGE :
                    resultMap = imageOperateCheck.checkDeleteImage(paramsMap);
                    break;
                case ActionConstants.UPDATE_IMAGE :
                    resultMap = imageOperateCheck.checkUpdateImage(paramsMap);
                    break;
                case ActionConstants.GET_IMAGE_DETAIL :
                    resultMap = imageOperateCheck.checkGetImageDetail(paramsMap);
                    break;
                case ActionConstants.GET_IMAGE_LIST_WITH_DETAIL :
                    resultMap = imageOperateCheck.checkGetImageList(paramsMap);
                    break;
//                case ActionConstants.IMAGE_INDEX :
//                    resultMap = imageParamsCheck.check(paramsMap);
//                    break;
                default :
                    resultMap = null;
                    break;
            }
            
            return resultMap;
        } catch (Exception e) {
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
            return resultMap;
        }
        
    }
}
