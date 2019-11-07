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

import appcloud.api.beans.Volume;
import appcloud.common.util.UuidUtil;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.manager.util.VolumeUnifiedCheckAndAuth;
import appcloud.openapi.operate.VolumeOperate;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateDiskResponse;
import appcloud.openapi.response.DisksDetailReponse;

@RestController
public class VolumeResource extends BaseController {

	private static Logger logger = Logger.getLogger(VolumeResource.class);

	@Autowired
	private VolumeOperate volumeOperate;
	@Autowired
	private VolumeUnifiedCheckAndAuth volumeUnifiedCheckAndAuth;
	@Autowired
	private ConstructResponse constructResponse;
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.CREATE_DISK, produces = { "application/json","application/xml"})
	@ResponseBody
	public CreateDiskResponse create(@RequestParam(value = Constants.ACTION) String action, 
			@RequestParam(value = Constants.REGION_ID, required = false) String regionId,
			@RequestParam(value = Constants.ZONE_ID) String zoneId,
			@RequestParam(value = Constants.DISK_NAME, required = false) String diskName,
			@RequestParam(value = Constants.DESCRIPTION, required = false) String description,
			@RequestParam(value = Constants.DISK_CATEGORY, required = false) String type,
			@RequestParam(value = Constants.DISK_SIZE) String size,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(value = Constants.VERSION) String version,
			@RequestParam(value = Constants.APPKEY_ID) String appKeyId,
			@RequestParam(value = Constants.SIGNATURE) String signature,
			@RequestParam(value = Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.REGION_ID, regionId);
			paramsMap.put(Constants.ZONE_ID, zoneId);
			paramsMap.put(Constants.DISK_NAME, diskName);
			paramsMap.put(Constants.DESCRIPTION, description);
			paramsMap.put(Constants.DISK_CATEGORY, type);
			paramsMap.put(Constants.DISK_SIZE, size);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			defaultParamsMap.put(Constants.DISK_NAME, "unknow-name");
			defaultParamsMap.put(Constants.DISK_CATEGORY, null);
			defaultParamsMap.put(Constants.DESCRIPTION, null);
			
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = volumeUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new CreateDiskResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				Volume volume = volumeOperate.create(appKeyId, regionId, zoneId, Integer.valueOf(size), paramsMap.get(Constants.DISK_NAME), paramsMap.get(Constants.DESCRIPTION), paramsMap.get(Constants.DISK_CATEGORY), requestId);
				
				return new CreateDiskResponse(requestId, volume.id);
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new CreateDiskResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.ATTACH_DISK, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse attach(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(Constants.DISK_ID) String volumeId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.INSTANCE_ID, instanceId);
			paramsMap.put(Constants.DISK_ID, volumeId);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = volumeUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = volumeOperate.attach(appKeyId, instanceId, volumeId, requestId);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "挂载失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DETACH_DISK, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse detach(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(Constants.DISK_ID) String volumeId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.INSTANCE_ID, instanceId);
			paramsMap.put(Constants.DISK_ID, volumeId);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = volumeUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = volumeOperate.detach(appKeyId, instanceId, volumeId, requestId);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "挂载失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DELETE_DISK, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse delete(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.DISK_ID) String volumeId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.DISK_ID, volumeId);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = volumeUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = volumeOperate.delete(appKeyId, volumeId, requestId);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "挂载失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_DISKS, produces = { "application/json","application/xml"})
	@ResponseBody
	public DisksDetailReponse describeDisks(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(value=Constants.DISK_IDS, required=false) String diskIds,
			@RequestParam(value=Constants.PAGE_NUMBER, required=false) String pageNumber,
			@RequestParam(value=Constants.PAGE_SIZE, required=false) String pageSize,
			@RequestParam(value=Constants.ZONE_ID, required=false) String zoneId,
			@RequestParam(value=Constants.INSTANCE_ID, required=false) String instanceId,
			@RequestParam(value=Constants.DISK_TYPE, required=false) String diskType,
			@RequestParam(value=Constants.DISK_NAME, required=false) String diskName,
			@RequestParam(value=Constants.DESCRIPTION, required=false) String description,
			@RequestParam(value=Constants.DISK_STATUS, required=false) String diskStatus,
			@RequestParam(value=Constants.DISK_ATTACH_STATUS, required=false) String diskAttachStatus,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			
			paramsMap.put(Constants.DISK_IDS, diskIds);
			paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
			paramsMap.put(Constants.PAGE_SIZE, pageSize);
			paramsMap.put(Constants.ZONE_ID, zoneId);
			paramsMap.put(Constants.INSTANCE_ID, instanceId);
			paramsMap.put(Constants.DISK_NAME, diskName);
			paramsMap.put(Constants.DESCRIPTION, description);
			paramsMap.put(Constants.DISK_TYPE, diskType);
			paramsMap.put(Constants.DISK_STATUS, diskStatus);
			paramsMap.put(Constants.DISK_ATTACH_STATUS, diskAttachStatus);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			if(null==paramsMap.get(Constants.PAGE_NUMBER)) {
				defaultParamsMap.put(Constants.PAGE_NUMBER, "1");
			}
			if(null==paramsMap.get(Constants.PAGE_SIZE)) {
				defaultParamsMap.put(Constants.PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE.toString());
			}
			
			try {
				Map<String, String> resultMap = volumeUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DisksDetailReponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				DisksDetailReponse result = volumeOperate.describe(appKeyId, diskIds, requestId, 
						paramsMap.get(Constants.ZONE_ID), paramsMap.get(Constants.INSTANCE_ID),
						paramsMap.get(Constants.DISK_NAME), paramsMap.get(Constants.DESCRIPTION),
						paramsMap.get(Constants.DISK_TYPE), paramsMap.get(Constants.DISK_STATUS),
						paramsMap.get(Constants.DISK_ATTACH_STATUS),paramsMap.get(Constants.PAGE_NUMBER), 
						paramsMap.get(Constants.PAGE_SIZE));
				
				return result;
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new DisksDetailReponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.RESET_DISK, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse reset(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.SNAPSHOT_ID) String snapshotId,
			@RequestParam(Constants.DISK_ID) String diskId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.SNAPSHOT_ID, snapshotId);
			paramsMap.put(Constants.DISK_ID, diskId);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = volumeUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = volumeOperate.reset(appKeyId, snapshotId, diskId, requestId);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "回滚失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	

}
