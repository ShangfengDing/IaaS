package appcloud.openapi.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import appcloud.api.beans.Image;
import appcloud.common.model.VmImage;
import appcloud.common.util.UuidUtil;
import appcloud.common.util.query.QueryObject;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.ImageManager;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.manager.util.ImageUnifiedCheckAndAuth;
import appcloud.openapi.operate.ImageOperate;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateImageResponse;
import appcloud.openapi.response.GetImageListResponse;
import appcloud.openapi.response.ShowImageDetailResponse;


/**
 * IaaS open API related to Image
 * @author Gong Lingpu
 * @2015年11月29日
 */

@RestController
public class ImageResource extends BaseController{
    private static Logger logger = Logger.getLogger(ImageResource.class);
  
    @Autowired
    private ImageManager imageManager;
    @Autowired
    private ImageOperate imageOperate;
    @Autowired
    private ImageUnifiedCheckAndAuth volumeUnifiedCheckAndAuth;
    @Autowired
    private ConstructResponse constructResponse;
    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description openAPI to a createImage
     * @param action
     * @param zoneId
     * @param imageId
     * @param instanceType
     * @param tenantId
     * @param serverId
     * @param volumeId
     * @param displayName
     * @param description
     * @param groupIdList
     * @param distribution
     * @return
     * @throws Exception 
     */
    @RequestMapping(method = RequestMethod.POST,  params="Action="+ActionConstants.CREATE_IMAGE, produces = { "application/json","application/xml"})
    @ResponseBody
    public CreateImageResponse createImage(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.ZONE_ID) String zoneId,
            @RequestParam(Constants.IMAGE_ID) String imageId, 
            @RequestParam(Constants.INSTANCE_TYPE) String instanceType,
            @RequestParam(Constants.SERVER_ID) String serverId,
            @RequestParam(Constants.VOLUME_ID) String volumeId,
            @RequestParam(value=Constants.DISPLAY_NAME, required=false) String displayName,
            @RequestParam(value=Constants.DISPLAY_DESCRIPTION, required=false) String displayDescription,
            @RequestParam(Constants.GROUP_ID_LIST) String groupIdList,
            @RequestParam(value=Constants.DISTRIBUTION, required=false) String distribution,
            @RequestParam(value=Constants.FORMAT, required=false) String format,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(Constants.USER_EMAIL) String userEmail)  {
        //检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put(Constants.ACTION, action);
        paramsMap.put(Constants.ZONE_ID, zoneId);
        paramsMap.put(Constants.IMAGE_ID, imageId);
        paramsMap.put(Constants.INSTANCE_TYPE, instanceType);
        paramsMap.put(Constants.SERVER_ID, serverId);
        paramsMap.put(Constants.VOLUME_ID, volumeId);
        paramsMap.put(Constants.GROUP_ID_LIST, groupIdList);
        if(null != displayName) {
            paramsMap.put(Constants.DISPLAY_NAME, displayName);
        }
        if(null != displayDescription) {
            paramsMap.put(Constants.DISPLAY_DESCRIPTION, displayDescription);
        }
        if(null != distribution) {
            paramsMap.put(Constants.DISPLAY_NAME, distribution);
        }
        if(null!=format) {
			paramsMap.put(Constants.FORMAT, format);
		}
        paramsMap.put(Constants.VERSION, version);
        paramsMap.put(Constants.APPKEY_ID, appKeyId);
        paramsMap.put(Constants.SIGNATURE, signature);
        paramsMap.put(Constants.TIMESTAMP, timestamp);
        if(null!=userEmail ) {
            paramsMap.put(Constants.USER_EMAIL, userEmail);
        }
        //将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
        Map<String, String> defaultParamsMap = new HashMap<String, String>();
        
        //创建镜像前，将所有的默认参数值放到defaultParamsMap中
  	    if(null==paramsMap.get(Constants.FORMAT)) {
  		    defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
  	    }
        if(null==paramsMap.get(Constants.DISPLAY_NAME)) {//添加一个默认imageName:"appkID_img_xxx
            String defaultImageName = appKeyId + "_img_" + UuidUtil.getRandomUuid().substring(0, 3);
            defaultParamsMap.put(Constants.DISPLAY_NAME, defaultImageName);
        }
        if(null == distribution) {
            defaultParamsMap.put(Constants.DISTRIBUTION, "");
        }
        
        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        String requestId = UuidUtil.getRandomUuid();
        //resultMap : 每次操作的返回结果
        Map<String, String> resultMap = new HashMap<String, String>(); 
        try {
            //检查参数和鉴权
            resultMap = volumeUnifiedCheckAndAuth.ImageCheckAndAuth(paramsMap, defaultParamsMap);
            if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new CreateImageResponse(requestId, 
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            
            logger.info(String.format("User request to CREATE IMAGE %s",  displayName));
            
            Image newImage = imageManager.create(paramsMap);
            resultMap = imageOperate.createImage(appKeyId, newImage, requestId);
            if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new CreateImageResponse(requestId, 
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            response.setStatus(constructResponse.getResponseStatus(resultMap));
            return new CreateImageResponse(requestId, resultMap.get(Constants.IMAGE_UUID));
        } catch (Exception e) {
            response.setStatus(constructResponse.getResponseStatus(resultMap));
            return new CreateImageResponse(requestId, Constants.INTERNAL_ERROR, 
                    "The request processing has failed due to some unknown error.");
        }
        
    }
    
    
    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description openAPI to delete a image
     * @param action
     * @param tenantId
     * @param imageUuid
     * @param groupId
     * @param version
     * @param appKeyId
     * @param signature
     * @param timestamp
     * @param userEmail
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.DELETE,  params="Action="+ActionConstants.DELETE_IMAGE, produces = { "application/json","application/xml"})
    @ResponseBody
    public BaseResponse deleteImage (
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.IMAGE_UUID) String imageUuid, 
            @RequestParam(Constants.GROUP_ID) String groupId,
            @RequestParam(value=Constants.FORMAT, required=false) String format,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(Constants.USER_EMAIL) String userEmail ) throws Exception {
      //检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
      Map<String, String> paramsMap = new HashMap<String, String>();
      Map<String, String> defaultParamsMap = new HashMap<String, String>();
      paramsMap.put(Constants.ACTION, action);
      paramsMap.put(Constants.IMAGE_UUID, imageUuid);
      paramsMap.put(Constants.GROUP_ID, groupId);
      if(null!=format) {
			paramsMap.put(Constants.FORMAT, format);
		}
      paramsMap.put(Constants.VERSION, version);
      paramsMap.put(Constants.APPKEY_ID, appKeyId);
      paramsMap.put(Constants.SIGNATURE, signature);
      paramsMap.put(Constants.TIMESTAMP, timestamp);
      if(null!=userEmail ) {
          paramsMap.put(Constants.USER_EMAIL, userEmail);
      }
      //将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
	  if(null==paramsMap.get(Constants.FORMAT)) {
		  defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
	  }
      //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
      String requestId = UuidUtil.getRandomUuid();
      //resultMap : 每次操作的返回结果
      Map<String, String> resultMap = new HashMap<String, String>();
      try {
          //检查接口公共请求参数
          resultMap = volumeUnifiedCheckAndAuth.ImageCheckAndAuth(paramsMap, defaultParamsMap);
          if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                  !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
              response.setStatus(constructResponse.getResponseStatus(resultMap));
              return constructResponse.getBaseResponse(requestId, resultMap);
          }
          
          logger.info(String.format("User request to delete IMAGE %s", imageUuid));
          VmImage imageToDel = imageManager.deleteImage(paramsMap);
          resultMap = imageOperate.deleteImage(appKeyId, imageToDel, requestId);
          if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                  !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
              response.setStatus(constructResponse.getResponseStatus(resultMap));
              return constructResponse.getBaseResponse(requestId, resultMap);
          }
          response.setStatus(constructResponse.getResponseStatus(resultMap));
          return new BaseResponse(requestId);
      } catch (Exception e) {
          logger.warn("执行操作抛出异常",e);
          response.setStatus(constructResponse.getResponseStatus(null));
          return constructResponse.getBaseResponse(requestId, null);
      }
   }
    
    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description openAPI to update a image
     * @param action
     * @param tenantId
     * @param imageUuid
     * @param name
     * @param description
     * @param version
     * @param appKeyId
     * @param signature
     * @param timestamp
     * @param userEmail
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT,  params="Action="+ActionConstants.UPDATE_IMAGE, produces = { "application/json","application/xml"})
    @ResponseBody
    public BaseResponse updateImage(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.IMAGE_UUID) String imageUuid, 
            @RequestParam(Constants.DISPLAY_NAME) String name,
            @RequestParam(Constants.DISPLAY_DESCRIPTION) String description,
            @RequestParam(value=Constants.FORMAT, required=false) String format,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(Constants.USER_EMAIL) String userEmail ) throws Exception {
      //检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
      Map<String, String> paramsMap = new HashMap<String, String>();
      Map<String, String> defaultParamsMap = new HashMap<String, String>();
      paramsMap.put(Constants.ACTION, action);
      paramsMap.put(Constants.IMAGE_UUID, imageUuid);
      paramsMap.put(Constants.DISPLAY_NAME, name);
      paramsMap.put(Constants.DISPLAY_DESCRIPTION, description);
      if(null!=format) {
			paramsMap.put(Constants.FORMAT, format);
		}
      paramsMap.put(Constants.VERSION, version);
      paramsMap.put(Constants.APPKEY_ID, appKeyId);
      paramsMap.put(Constants.SIGNATURE, signature);
      paramsMap.put(Constants.TIMESTAMP, timestamp);
      if(null!=userEmail ) {
          paramsMap.put(Constants.USER_EMAIL, userEmail);
      }
      //将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
	  if(null==paramsMap.get(Constants.FORMAT)) {
		  defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
	  }
      String requestId = UuidUtil.getRandomUuid();
      //resultMap : 每次操作的返回结果
      Map<String, String> resultMap = new HashMap<String, String>();
      try {
          //检查接口公共请求参数
          resultMap = volumeUnifiedCheckAndAuth.ImageCheckAndAuth(paramsMap, defaultParamsMap);
          if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                  !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
              response.setStatus(constructResponse.getResponseStatus(resultMap));
              return constructResponse.getBaseResponse(requestId, resultMap);
          }
          
          logger.info(String.format("User request to update IMAGE %s", imageUuid));
          Image imageToUpdate = imageManager.updateImage(paramsMap);
          resultMap = imageOperate.updateImage(imageToUpdate, requestId);
          if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                  !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
              response.setStatus(constructResponse.getResponseStatus(resultMap));
              return constructResponse.getBaseResponse(requestId, resultMap);
          }
          response.setStatus(constructResponse.getResponseStatus(resultMap));
          return new BaseResponse(requestId);
      } catch (Exception e) {
          logger.warn("执行操作抛出异常",e);
          response.setStatus(constructResponse.getResponseStatus(null));
          return constructResponse.getBaseResponse(requestId, null);
      }
    }
    
    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description openAPI to show the detail of a specific image
     * @param action
     * @param tenantId
     * @param imageUuid
     * @param version
     * @param appKeyId
     * @param signature
     * @param timestamp
     * @param userEmail
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.GET_IMAGE_DETAIL, produces = { "application/json","application/xml"})
    @ResponseBody
    public ShowImageDetailResponse getImageDetail(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.IMAGE_UUID) String imageUuid,
            @RequestParam(value=Constants.FORMAT, required=false) String format,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(Constants.USER_EMAIL) String userEmail ) throws Exception {
        
      //检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
        Map<String, String> paramsMap = new HashMap<String, String>();
        Map<String, String> defaultParamsMap = new HashMap<String, String>();
        paramsMap.put(Constants.ACTION, action);
        paramsMap.put(Constants.IMAGE_UUID, imageUuid);
        if(null!=format) {
			paramsMap.put(Constants.FORMAT, format);
		}
        paramsMap.put(Constants.VERSION, version);
        paramsMap.put(Constants.APPKEY_ID, appKeyId);
        paramsMap.put(Constants.SIGNATURE, signature);
        paramsMap.put(Constants.TIMESTAMP, timestamp);
        if(null!=userEmail ) {
            paramsMap.put(Constants.USER_EMAIL, userEmail);
        }
      //将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
  		if(null==paramsMap.get(Constants.FORMAT)) {
  			defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
  		}
        Map<String, String> resultMap = new HashMap<String, String>();
        String requestId = UuidUtil.getRandomUuid();
        //检查接口公共请求参数
        resultMap = volumeUnifiedCheckAndAuth.ImageCheckAndAuth(paramsMap, defaultParamsMap);
        if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
            response.setStatus(constructResponse.getResponseStatus(resultMap));
            return new ShowImageDetailResponse(requestId, 
                    resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
        }
        
        //开始查询镜像细节
        logger.info(String.format("User  request to show IMAGE %s detail", imageUuid));
        VmImage imageToShow = imageManager.getImageDetail(paramsMap);
        Map<String, Object> resultMap2 = imageOperate.getImageDetail(imageToShow, requestId);
        response.setStatus(constructResponse.getResponseStatus(resultMap));
        return new ShowImageDetailResponse(requestId, resultMap2);
            
    }
    

    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description openAPI to get images details which belong to a specific tenant
     * @param action
     * @param tenantId
     * @param groupId
     * @param imageType
     * @param imageStatus
     * @param version
     * @param appKeyId
     * @param signature
     * @param timestamp
     * @param userEmail
     * @return
     * @throws Exception
     */
    
    @RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.GET_IMAGE_LIST_WITH_DETAIL, 
            produces = { "application/json","application/xml"})
    @ResponseBody
    public GetImageListResponse getImageListWithDetail(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.TENANT_ID) String tenantId, 
            @RequestParam(value=Constants.GROUP_ID, required=false) String groupId, 
            @RequestParam(value=Constants.IMAGE_TYPE, required=false) String imageType,
            @RequestParam(value=Constants.IMAGE_STATUS, required=false) String imageStatus,
            @RequestParam(value=Constants.PAGE_NUMBER, required=false) String pageNumber,
            @RequestParam(value=Constants.PAGE_SIZE, required=false) String pageSize,
            @RequestParam(value=Constants.FORMAT, required=false) String format,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(Constants.USER_EMAIL) String userEmail ) throws Exception {
            return commonGetImageList( action, groupId, imageType, imageStatus, pageNumber, pageSize,
                     format, version, appKeyId, signature, timestamp, userEmail, true);
        
    }
    
    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description  getImageDetail or others that search all images of a user function 
     * will both call this function.The difference is the param "detailed"
     * @param action
     * @param tenantId
     * @param groupId
     * @param imageType
     * @param imageStatus
     * @param version
     * @param appKeyId
     * @param signature
     * @param timestamp
     * @param userEmail
     * @param detailed
     * @return
     * @throws Exception
     */
    public GetImageListResponse commonGetImageList(String action, String groupId, String imageType, String imageStatus,
            String pageNumber, String pageSize, String format, String version, String appKeyId, String signature, 
            String timestamp,  String userEmail, boolean detailed) throws Exception {
        Map<String, String> paramsMap = new HashMap<String, String>();
        Map<String, String> defaultParamsMap = new HashMap<String, String>();
        paramsMap.put(Constants.ACTION, action);
        if(null != groupId) {
            paramsMap.put(Constants.GROUP_ID, groupId);
        }
        if(null != imageType) {
            paramsMap.put(Constants.IMAGE_TYPE, imageType);
        }
        if(null != imageStatus) {
            paramsMap.put(Constants.IMAGE_STATUS, imageStatus);
        }
        if(null != pageNumber) {
            paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
        }
        if(null != pageSize) {
            paramsMap.put(Constants.PAGE_SIZE, pageSize);
        }
        if(null!=format) {
			paramsMap.put(Constants.FORMAT, format);
		}
        paramsMap.put(Constants.VERSION, version);
        paramsMap.put(Constants.APPKEY_ID, appKeyId);
        paramsMap.put(Constants.SIGNATURE, signature);
        paramsMap.put(Constants.TIMESTAMP, timestamp);
        if(null!=userEmail ) {
            paramsMap.put(Constants.USER_EMAIL, userEmail);
        }
        
        //查找镜像前，将所有的默认参数值放到paramsMap中
  		if(null==paramsMap.get(Constants.FORMAT)) {
  			defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
  		}
        if(null == paramsMap.get(Constants.GROUP_ID)) {
            defaultParamsMap.put(Constants.GROUP_ID, "all");
        }
        if(null == paramsMap.get(Constants.IMAGE_TYPE)) {
            defaultParamsMap.put(Constants.IMAGE_TYPE, "all");
        }
        if(null == paramsMap.get(Constants.IMAGE_STATUS)) {
            defaultParamsMap.put(Constants.IMAGE_STATUS, "all");
        }
        if(null==paramsMap.get(Constants.PAGE_NUMBER)) {
            defaultParamsMap.put(Constants.PAGE_NUMBER, "1");
        }
        if(null==paramsMap.get(Constants.PAGE_SIZE)) {
            defaultParamsMap.put(Constants.PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE.toString());
        }
        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        String requestId = UuidUtil.getRandomUuid();
        //resultMap : 检查后的返回结果
        Map<String, String> resultMap = new HashMap<String, String>();
        //检查接口公共请求参数
        resultMap = volumeUnifiedCheckAndAuth.ImageCheckAndAuth(paramsMap, defaultParamsMap);
        if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
            response.setStatus(constructResponse.getResponseStatus(resultMap));
            return new GetImageListResponse(requestId, 
                    resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
        }
        Map<String, Object> resultMap2 = new HashMap<String, Object>();
        logger.info(String.format("User  request to get IMAGE detail with groupId:%s imageType:%s imageStatus:%s ", groupId, imageType, imageStatus));
        QueryObject<VmImage> query = imageManager.getImageList(paramsMap);
        resultMap2 = imageOperate.getImageList(query, paramsMap, detailed, requestId);
        response.setStatus(constructResponse.getResponseStatus(resultMap));
        return new GetImageListResponse(requestId, resultMap2);
    }
    
//  /**
//  * @author Gong Lingpu (gonglingpu@foxmail.com)
//  * Description default function at path /images in GET 
//  * @param action
//  * @param tenantId
//  * @param groupId
//  * @param imageType
//  * @param imageStatus
//  * @param version
//  * @param appKeyId
//  * @param signature
//  * @param timestamp
//  * @param userEmail
//  * @return
//  * @throws Exception
//  */
// @GET
// @Produces(MediaType.APPLICATION_JSON)
// public CreateImageResponse index(
//         @RequestParam(Constants.ACTION) String action,
//         @RequestParam(Constants.GROUP_ID) String groupId, 
//         @RequestParam(Constants.IMAGE_TYPE) String imageType,
//         @RequestParam(Constants.IMAGE_STATUS) String imageStatus,
//         @RequestParam(Constants.VERSION) String version,
//         @RequestParam(Constants.APPKEY_ID) String appKeyId,
//         @RequestParam(Constants.SIGNATURE) String signature,
//         @RequestParam(Constants.TIMESTAMP) String timestamp,
//         @RequestParam(Constants.USER_EMAIL) String userEmail ) throws Exception {
//         return commonGetDetail( action, tenantId, groupId, imageType, imageStatus,
//                  version, appKeyId, signature, timestamp, userEmail, false);
//     
// }
}
