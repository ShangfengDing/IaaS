package appcloud.openapi.resources;

import appcloud.api.beans.Volume;
import appcloud.common.util.UuidUtil;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.billing.BillingManager;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.manager.util.VolumeUnifiedCheckAndAuth;
import appcloud.openapi.operate.VolumeOperate;
import appcloud.openapi.response.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class VolumeResource extends BaseController {

	private static Logger logger = Logger.getLogger(VolumeResource.class);

	@Autowired
	private VolumeOperate volumeOperate;
	@Autowired
	private VolumeUnifiedCheckAndAuth volumeUnifiedCheckAndAuth;
	@Autowired
	private ConstructResponse constructResponse;
	@Autowired
	private BillingManager billingmanager;
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.CREATE_DISK, produces = { "application/json","application/xml"})
	@ResponseBody
	public CreateDiskResponse create(@RequestParam(value = Constants.ACTION) String action,
			@RequestParam(value = Constants.REGION_ID) String regionId,
			@RequestParam(value = Constants.ZONE_ID) String zoneId,
			@RequestParam(value = Constants.DISK_NAME, required = false) String diskName,
			@RequestParam(value = Constants.DESCRIPTION, required = false) String description,
			@RequestParam(value = Constants.DISK_CATEGORY, required = false) String type,
			@RequestParam(value = Constants.DISK_SIZE) String size,
			@RequestParam(value = Constants.DISK_CHARGE_TYPE, required = false) String diskChargeType,
			@RequestParam(value = Constants.DISK_CHARGE_LENGTH, required = false) String diskChargeLength,
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
			if(null!=diskName) {
				paramsMap.put(Constants.DISK_NAME, diskName);
			}
			if(null!=description) {
				paramsMap.put(Constants.DESCRIPTION, description);
			}
			if(null!=type) {
				paramsMap.put(Constants.DISK_CATEGORY, type);
			}
			paramsMap.put(Constants.DISK_SIZE, size);
			if(null!=diskChargeType) {
				paramsMap.put(Constants.DISK_CHARGE_TYPE, diskChargeType);
			}
			if(null!=diskChargeLength) {
				paramsMap.put(Constants.DISK_CHARGE_LENGTH, diskChargeLength);
			}
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
			if(null==paramsMap.get(Constants.DISK_CHARGE_TYPE)) {
				defaultParamsMap.put(Constants.DISK_CHARGE_TYPE, Constants.PAY_BY_HOUR);
			}
			if(null==paramsMap.get(Constants.DISK_CHARGE_LENGTH)) {
				defaultParamsMap.put(Constants.DISK_CHARGE_LENGTH, "1");
			}
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
				
				//扣钱代码
				/*resultMap = billingmanager.pay(paramsMap, defaultParamsMap);
				if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new CreateDiskResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}*/
				
				Volume volume = volumeOperate.create(appKeyId, regionId, zoneId, Integer.valueOf(size), paramsMap.get(Constants.DISK_NAME), paramsMap.get(Constants.DESCRIPTION), paramsMap.get(Constants.DISK_CATEGORY), requestId);
				resultMap.put(Constants.DISK_ID, volume.id);
				
				//期限代码
				Map<String, String> resultMap2 = billingmanager.AddEndtime(paramsMap, defaultParamsMap,resultMap);
				if(null==resultMap2 || null==resultMap2.get(Constants.HTTP_CODE) ||
						!resultMap2.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap2));
					return new CreateDiskResponse(requestId, 
							resultMap2.get(Constants.ERRORCODE), resultMap2.get(Constants.ERRORMESSAGE));
				}
				
				return new CreateDiskResponse(requestId, volume.id);
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new CreateDiskResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
	}


	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.CREATE_DISK_IMAGEBACK, produces = { "application/json","application/xml"})
	@ResponseBody
	public CreateDiskImageBackResponse createDiskImageBack(@RequestParam(value = Constants.ACTION) String action,
									 @RequestParam(value = Constants.REGION_ID) String regionId,
									 @RequestParam(value = Constants.ZONE_ID) String zoneId,
									 @RequestParam(value = Constants.DISK_NAME, required = false) String displayName,
									 @RequestParam(value = Constants.DESCRIPTION, required = false) String description,
									 @RequestParam(value = Constants.DISK_ID) String volumeUuid,
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
		if(null!=displayName) {
			paramsMap.put(Constants.DISK_NAME, displayName);
		}
		if(null!=description) {
			paramsMap.put(Constants.DESCRIPTION, description);
		}
		if (null!=volumeUuid) {
			paramsMap.put(Constants.DISK_ID,volumeUuid);
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
				return new CreateDiskImageBackResponse(requestId,
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}

			String uuid = volumeOperate.createImageBack(appKeyId,paramsMap.get(Constants.DISK_NAME), paramsMap.get(Constants.DESCRIPTION),paramsMap.get(Constants.DISK_ID), requestId);
			resultMap.put(Constants.DISK_ID, uuid);

			return new CreateDiskImageBackResponse(requestId, uuid);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
			return new CreateDiskImageBackResponse(requestId, Constants.INTERNAL_ERROR,
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
			@RequestParam(value = Constants.REGION_ID) String regionId,
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
			paramsMap.put(Constants.REGION_ID, regionId);
			
			if (null != diskIds)
				paramsMap.put(Constants.DISK_IDS, diskIds);
			if (null != pageNumber)
				paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
			if (null != pageSize)
				paramsMap.put(Constants.PAGE_SIZE, pageSize);
			if (null != zoneId)
				paramsMap.put(Constants.ZONE_ID, zoneId);
			if (null != instanceId)
				paramsMap.put(Constants.INSTANCE_ID, instanceId);
			if (null != diskName)
				paramsMap.put(Constants.DISK_NAME, diskName);
			if (null != description)
				paramsMap.put(Constants.DESCRIPTION, description);
			if (null != diskType)
				paramsMap.put(Constants.DISK_TYPE, diskType);
			if (null != diskStatus)
				paramsMap.put(Constants.DISK_STATUS, diskStatus);
			if (null != diskAttachStatus)
				paramsMap.put(Constants.DISK_ATTACH_STATUS, diskAttachStatus);
			if (null != format) {
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

	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_DISK_IMAGEBACK, produces = { "application/json","application/xml"})
	@ResponseBody
	public DescribeDiskImageBackResponse describeDiskImageBack(@RequestParam(Constants.ACTION) String action,
											@RequestParam(value = Constants.REGION_ID) String regionId,
											@RequestParam(value=Constants.ZONE_ID, required=false) String zoneId,
											@RequestParam(value=Constants.INSTANCE_ID, required=false) String instanceUuid,
											@RequestParam(value=Constants.VOLUME_ID, required=false) String volumeUuid,
											@RequestParam(value=Constants.ACTIVE_VOLUME_ID, required=false) String activeVolumeUuid,
											@RequestParam(value=Constants.IMAGEBACK_STATUS, required=false) String status,
											@RequestParam(value=Constants.VOLUME_TYPE, required=false) String volumeType,
											@RequestParam(value=Constants.VOLUME_USAGE_TYPE, required=false) String volumeUsageType,
											@RequestParam(value=Constants.IS_BACK_UP, required=false) String backUp,
											@RequestParam(value=Constants.IS_TOP, required=false) String top,
											@RequestParam(value=Constants.DISK_NAME, required=false) String displayName,

											@RequestParam(value=Constants.FORMAT, required=false) String format,
											@RequestParam(Constants.VERSION) String version,
											@RequestParam(Constants.APPKEY_ID) String appKeyId,
											@RequestParam(Constants.SIGNATURE) String signature,
											@RequestParam(Constants.TIMESTAMP) String timestamp,
											@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {

		//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.REGION_ID, regionId);

		if (null != zoneId)
			paramsMap.put(Constants.ZONE_ID, zoneId);
		if (null != volumeUuid)
			paramsMap.put(Constants.VOLUME_ID, volumeUuid);
		if (null != activeVolumeUuid)
			paramsMap.put(Constants.ACTIVE_VOLUME_ID, activeVolumeUuid);
		if (null != instanceUuid)
			paramsMap.put(Constants.INSTANCE_ID, instanceUuid);
		if (null != status)
			paramsMap.put(Constants.IMAGEBACK_STATUS, status);
		if (null != volumeType)
			paramsMap.put(Constants.VOLUME_TYPE, volumeType);
		if (null != volumeUsageType)
			paramsMap.put(Constants.VOLUME_USAGE_TYPE, volumeUsageType);
		if (null != backUp)
			paramsMap.put(Constants.IS_BACK_UP, backUp);
		if (null != top)
			paramsMap.put(Constants.IS_TOP, top);
		if (null != displayName)
			paramsMap.put(Constants.DISK_NAME, displayName);
		if (null != format) {
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
			// TODO 或许要完善
			Map<String, String> resultMap = volumeUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
			if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return new DescribeDiskImageBackResponse(requestId,
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}

			DescribeDiskImageBackResponse result = volumeOperate.describeImageBack(requestId, appKeyId, volumeUuid,
					activeVolumeUuid, zoneId, instanceUuid, displayName,status, volumeType, volumeUsageType, backUp, top);

			return result;

		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
			return new DescribeDiskImageBackResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
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
	
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.MODIFY_DISK_ATTRIBUTE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse modifyDisk(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.DISK_ID) String diskId,
			@RequestParam(value = Constants.DISK_NAME, required = false) String diskName,
			@RequestParam(value = Constants.DESCRIPTION, required = false) String description,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			
			if(null!=diskName)
				paramsMap.put(Constants.DISK_NAME, diskName);
			if(null!=description)
				paramsMap.put(Constants.DESCRIPTION, description);
			
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
				
				
				Boolean result = volumeOperate.modify(appKeyId, diskId, diskName, description);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "修改失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}

	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.MODIFY_DISK_IMAGEBACK, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse modifyDiskImageBack(@RequestParam(Constants.ACTION) String action,
															 @RequestParam(value = Constants.REGION_ID) String regionId,
															 @RequestParam(value=Constants.VOLUME_ID, required=false) String volumeUuid,
															 @RequestParam(value=Constants.IMAGEBACK_STATUS, required=false) String status,  //状态
															 @RequestParam(value=Constants.VOLUME_TYPE, required=false) String volumeType,  //磁盘类型
															 @RequestParam(value=Constants.VOLUME_USAGE_TYPE, required=false) String volumeUsageType, //磁盘系统类型
															 @RequestParam(value=Constants.IS_BACK_UP, required=false) String backUp,  //是否有备份
															 @RequestParam(value=Constants.IS_TOP, required=false) String top,
															 @RequestParam(value=Constants.DISK_NAME, required=false) String displayName,

															 @RequestParam(value=Constants.FORMAT, required=false) String format,
															 @RequestParam(Constants.VERSION) String version,
															 @RequestParam(Constants.APPKEY_ID) String appKeyId,
															 @RequestParam(Constants.SIGNATURE) String signature,
															 @RequestParam(Constants.TIMESTAMP) String timestamp,
															 @RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {

		//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.REGION_ID, regionId);

		if (null != status)
			paramsMap.put(Constants.IMAGEBACK_STATUS, status);
		if (null != volumeType)
			paramsMap.put(Constants.VOLUME_TYPE, volumeType);
		if (null != volumeUsageType)
			paramsMap.put(Constants.VOLUME_USAGE_TYPE, volumeUsageType);
		if (null != backUp)
			paramsMap.put(Constants.IS_BACK_UP, backUp);
		if (null != top)
			paramsMap.put(Constants.IS_TOP, top);
		if (null != displayName)
			paramsMap.put(Constants.DISK_NAME, displayName);
		if (null != format) {
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
				return new BaseResponse(requestId,
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}

			Boolean result = volumeOperate.modifyImageBack(appKeyId, volumeUuid,
					displayName,status, volumeType, volumeUsageType, backUp, top);

			if (result)
				return new BaseResponse(requestId);
			else {
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "修改失败，请稍后尝试!");
			}

		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
			return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
		}
	}

	/**
	 * 云硬盘续费
	 * @author luofan
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.RENEW_DISK, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse RenewDisk(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.DISK_ID) String diskId,
			@RequestParam(value = Constants.DISK_CHARGE_TYPE, required = false) String diskChargeType,
			@RequestParam(value = Constants.DISK_CHARGE_LENGTH, required = false) String diskChargeLength,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL, required=false) String userEmail) throws Exception {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.DISK_ID, diskId);
		if(null!=diskChargeType) {
			paramsMap.put(Constants.DISK_CHARGE_TYPE, diskChargeType);
		}
		if(null!=diskChargeLength) {
			paramsMap.put(Constants.DISK_CHARGE_LENGTH, diskChargeLength);
		}
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
		if(null==diskChargeLength) {
			defaultParamsMap.put(Constants.DISK_CHARGE_LENGTH, "1");
		}
		if(null==paramsMap.get(Constants.FORMAT)) {
			defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
		}
		//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
		String requestId = UuidUtil.getRandomUuid();
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = volumeUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = billingmanager.renew(paramsMap,defaultParamsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Instance resume successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}

}
