package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Component;

import com.appcloud.vm.fe.manager.BillingrateManager;
import com.appcloud.vm.fe.model.Billingrate;
import com.free4lab.utils.recommend.Constant;

import appcloud.common.model.VmImage.VmImageDiskFormatEnum;
import appcloud.common.model.VmImage.VmImageStatusEnum;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.ImageParamsCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;

/**
 * @author Gong Lingpu
 * @2015年12月1日
 */
@Component
public class ImageParamsCheckImpl implements ImageParamsCheck{
    
    private static Logger logger = Logger.getLogger(InstanceParamsCheckImpl.class);
    private static CommonGenerator commonGenerator  = new CommonGenerator();
    private  VmImageProxy vmImageProxy ;
    
 
    private static ImageParamsCheckImpl imageParamCheck = new ImageParamsCheckImpl();
    private  ImageParamsCheckImpl() {
        super();
        vmImageProxy = (VmImageProxy)ConnectionFactory.getTipProxy(VmImageProxy.class, 
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    }
    
    public static ImageParamsCheckImpl getInstance() {
        return imageParamCheck;
    }
   
    @Override
    public Map<String, String> checkCreateParams(Map<String, String> paramsMap) throws Exception {
        logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check create image params, ZoneId=" + paramsMap.get(Constants.ZONE_ID) + 
                "; ImageId=" + paramsMap.get(Constants.IMAGE_ID) + "; InstanceType=" + paramsMap.get(Constants.INSTANCE_TYPE) );
        
        
        if(!paramsMap.containsKey(Constants.SERVER_ID) || null==paramsMap.get(Constants.SERVER_ID)) {
            return commonGenerator.missing(null, Constants.SERVER_ID);
        }else {
            if(""==paramsMap.get(Constants.SERVER_ID) || paramsMap.get(Constants.SERVER_ID).equalsIgnoreCase("") ) {
                return commonGenerator.inValid(null, Constants.SERVER_ID);
            }
        }
        
        if(!paramsMap.containsKey(Constants.VOLUME_ID) || null==paramsMap.get(Constants.VOLUME_ID)) {
            return commonGenerator.missing(null, Constants.VOLUME_ID);
        }else {
            if(""==paramsMap.get(Constants.VOLUME_ID) || paramsMap.get(Constants.VOLUME_ID).equalsIgnoreCase("") ) {
                return commonGenerator.inValid(null, Constants.VOLUME_ID);
            }
        }
        
        if(!paramsMap.containsKey(Constants.GROUP_ID_LIST) || null==paramsMap.get(Constants.GROUP_ID_LIST)) {
            return commonGenerator.missing(null, Constants.GROUP_ID_LIST);
        }else {
            if(""==paramsMap.get(Constants.GROUP_ID_LIST) || paramsMap.get(Constants.GROUP_ID_LIST).equalsIgnoreCase("") ) {
                return commonGenerator.inValid(null, Constants.GROUP_ID_LIST);
            }
        }
       
        //判断非必须参数
        if(paramsMap.containsKey(Constants.DISPLAY_NAME)) {
            if(vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID)).getOsType().equals(Constants.OS_WINDOWS)) {
                if(paramsMap.get(Constants.DISPLAY_NAME).length()<Constants.MIN_HOSTNAME || 
                        paramsMap.get(Constants.DISPLAY_NAME).length()>Constants.MAX_WINDOWS_HOSTNAME ||
                        Pattern.matches("\\d+", paramsMap.get(Constants.DISPLAY_NAME)) ||
                        !Pattern.matches("(\\w+-?\\w*)+", paramsMap.get(Constants.DISPLAY_NAME))) {
                    return commonGenerator.inValid(null, Constants.DISPLAY_NAME);
                }
            }else if(vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID)).getOsType().equals(Constants.OS_LINUX)){
                if(paramsMap.get(Constants.DISPLAY_NAME).length()<Constants.MIN_HOSTNAME || 
                        paramsMap.get(Constants.DISPLAY_NAME).length()>Constants.MAX_LINUX_HOSTNAME ||
                        !Pattern.matches("(\\w+(\\.|-)?\\w*)+", paramsMap.get(Constants.DISPLAY_NAME))) {
                    return commonGenerator.inValid(null, Constants.DISPLAY_NAME);
                }
            }else {
                String message = "The specified image is not found OS type";
                return commonGenerator.internalError(message, Constants.DISPLAY_NAME);
            }
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkDeleteParams(Map<String, String> paramsMap) throws JSONException {
        logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check delete image params, "+
                "; ImageId=" + paramsMap.get(Constants.IMAGE_UUID) + "; groupId=" + paramsMap.get(Constants.GROUP_ID) );
        
        
        if(!paramsMap.containsKey(Constants.IMAGE_UUID) || null==paramsMap.get(Constants.IMAGE_UUID)) {
            return commonGenerator.missing(null, Constants.IMAGE_UUID);
        }else {
            if(""==paramsMap.get(Constants.IMAGE_UUID) || paramsMap.get(Constants.IMAGE_UUID).equalsIgnoreCase("") ) {
                return commonGenerator.inValid(null, Constants.IMAGE_UUID);
            }
        }
        
        if(!paramsMap.containsKey(Constants.GROUP_ID) || null==paramsMap.get(Constants.GROUP_ID)) {
            return commonGenerator.missing(null, Constants.GROUP_ID);
        }else {
            if(""==paramsMap.get(Constants.GROUP_ID) || paramsMap.get(Constants.GROUP_ID).equalsIgnoreCase("") ) {
                return commonGenerator.inValid(null, Constants.GROUP_ID);
            }
        }
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkUpdateParams(Map<String, String> paramsMap) throws Exception {
        logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check update image params, "  + 
                "; ImageId=" + paramsMap.get(Constants.IMAGE_UUID) + "; groupId=" + paramsMap.get(Constants.GROUP_ID) );
        
        if(!paramsMap.containsKey(Constants.IMAGE_UUID) || null==paramsMap.get(Constants.IMAGE_UUID)) {
            return commonGenerator.missing(null, Constants.IMAGE_UUID);
        }else {
            if(""==paramsMap.get(Constants.IMAGE_UUID) || paramsMap.get(Constants.IMAGE_UUID).equalsIgnoreCase("") ) {
                return commonGenerator.inValid(null, Constants.IMAGE_UUID);
            }
        }
        
        if(!paramsMap.containsKey(Constants.DISPLAY_NAME) || null==paramsMap.get(Constants.DISPLAY_NAME)) {
            return commonGenerator.missing(null, Constants.GROUP_ID);
        }else {
            if(vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID)).getOsType().equals(Constants.OS_WINDOWS)) {
                if(paramsMap.get(Constants.DISPLAY_NAME).length()<Constants.MIN_HOSTNAME || 
                        paramsMap.get(Constants.DISPLAY_NAME).length()>Constants.MAX_WINDOWS_HOSTNAME ||
                        Pattern.matches("\\d+", paramsMap.get(Constants.DISPLAY_NAME)) ||
                        !Pattern.matches("(\\w+-?\\w*)+", paramsMap.get(Constants.DISPLAY_NAME))) {
                    return commonGenerator.inValid(null, Constants.DISPLAY_NAME);
                }
            }else if(vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_ID)).getOsType().equals(Constants.OS_LINUX)){
                if(paramsMap.get(Constants.DISPLAY_NAME).length()<Constants.MIN_HOSTNAME || 
                        paramsMap.get(Constants.DISPLAY_NAME).length()>Constants.MAX_LINUX_HOSTNAME ||
                        !Pattern.matches("(\\w+(\\.|-)?\\w*)+", paramsMap.get(Constants.DISPLAY_NAME))) {
                    return commonGenerator.inValid(null, Constants.DISPLAY_NAME);
                }
            }else {
                String message = "The specified image is not found OS type";
                return commonGenerator.internalError(message, Constants.DESCRIPTION);
            }
        }
        
        if(!paramsMap.containsKey(Constants.DISPLAY_DESCRIPTION) || null==paramsMap.get(Constants.DISPLAY_DESCRIPTION)) {
            return commonGenerator.missing(null, Constants.DISPLAY_DESCRIPTION);
        }else {
            if(""==paramsMap.get(Constants.DISPLAY_DESCRIPTION) || paramsMap.get(Constants.DISPLAY_DESCRIPTION).equalsIgnoreCase("") ) {
                return commonGenerator.inValid(null, Constants.DISPLAY_DESCRIPTION);
            }
        }
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkGetListParams(Map<String, String> paramsMap) throws JSONException {
        logger.info("Action: "+paramsMap.get(Constants.ACTION)+" get image list params,  "  + 
                "; groupIds=" + paramsMap.get(Constants.GROUP_ID) + "; ImageType=" + paramsMap.get(Constants.IMAGE_TYPE) + 
                "; imagestatus=" + paramsMap.get(Constants.IMAGE_STATUS));
        
      //判断非必须参数
        if(paramsMap.containsKey(Constants.GROUP_ID)) {
            String groupId = paramsMap.get(Constants.GROUP_ID);
            try {
                Integer.parseInt(groupId);
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.GROUP_ID);
            }
            
        }
        if(paramsMap.containsKey(Constants.IMAGE_TYPE)) {
            String iT = paramsMap.get(Constants.IMAGE_TYPE);
            if(!(iT.equals(VmImageDiskFormatEnum.ISO.toString()) || iT.equals(VmImageDiskFormatEnum.IMAGE.toString())))
                return commonGenerator.inValid(null, Constants.IMAGE_TYPE);
        }
        if(paramsMap.containsKey(Constants.IMAGE_STATUS)) {
            String iS = paramsMap.get(Constants.IMAGE_STATUS);
            if(!(iS.equals(VmImageStatusEnum.AVAILABLE.toString()) || iS.equals(VmImageStatusEnum.CREATING.toString()) 
                    || iS.equals(VmImageStatusEnum.DELETING.toString()) || iS.equals(VmImageStatusEnum.ERROR.toString())
                    || iS.equals(VmImageStatusEnum.ERROR_DELETING.toString())) )
                return commonGenerator.inValid(null, Constants.IMAGE_STATUS);
            
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkShowImageDetailParams(Map<String, String> paramsMap) throws JSONException {
        logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check show image detail params, "+
                "; ImageId=" + paramsMap.get(Constants.IMAGE_UUID)  );
        
        if(!paramsMap.containsKey(Constants.IMAGE_UUID) || null==paramsMap.get(Constants.IMAGE_UUID)) {
            return commonGenerator.missing(null, Constants.IMAGE_UUID);
        }else {
            if(""==paramsMap.get(Constants.IMAGE_UUID) || paramsMap.get(Constants.IMAGE_UUID).equalsIgnoreCase("") ) {
                return commonGenerator.inValid(null, Constants.IMAGE_UUID);
            }
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

}
