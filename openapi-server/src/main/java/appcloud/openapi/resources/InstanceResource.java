package appcloud.openapi.resources;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import appcloud.api.beans.server.ServerCreateReq;
import appcloud.common.util.UuidUtil;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.billing.BillingManager;
import appcloud.openapi.billing.impl.BillingManagerImpl;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.InstanceManager;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.manager.util.InstanceUnifiedCheckAndAuth;
import appcloud.openapi.operate.InstanceOperate;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateInstanceResponse;
import appcloud.openapi.response.DescribeInstanceStatusResponse;
@RestController
public class InstanceResource extends BaseController{
	
	private static Logger logger = Logger.getLogger(InstanceResource.class);
	@Autowired
	private InstanceManager instanceManager;
	@Autowired
	private InstanceOperate instanceOperate;
	private ConstructResponse constructResponse = new ConstructResponse();
	private InstanceUnifiedCheckAndAuth instanceCheckAndAuth = new InstanceUnifiedCheckAndAuth();	
	//扣钱代码
	private BillingManager billingmanager= new BillingManagerImpl();
	/**
	 * 开放API创建云主机
	 * @author hgm 
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.CREATE_INSTANCE, produces = { "application/json","application/xml"})
	@ResponseBody
	public CreateInstanceResponse CreateInstance(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.REGION_ID) String regionId,
			@RequestParam(value=Constants.ZONE_ID, required=false) String zoneId,
			@RequestParam(Constants.IMAGE_ID) String imageId, 
			@RequestParam(Constants.INSTANCE_TYPE) String instanceType,
			@RequestParam(Constants.SECURITY_GROUP_ID) String securityGroupId,
			@RequestParam(value=Constants.INSTANCE_NAME, required=false) String instanceName,
			@RequestParam(value=Constants.DESCRIPTION, required=false) String description,
			@RequestParam(value=Constants.INSTANCE_CHARGE_TYPE, required=false) String instanceChargeType,
			@RequestParam(value=Constants.INSTANCE_CHARGE_LENGTH, required=false) String instanceChargeLength,
			@RequestParam(value=Constants.INTERNET_CHARGE_TYPE, required=false) String internetChargeType,
			@RequestParam(value=Constants.INTERNET_MAX_BANDWIDTH_OUT, required=false) String maxBandwidthOut,
			@RequestParam(value=Constants.HOST_NAME, required=false) String hostName,
			@RequestParam(value=Constants.PASSWORD, required=false) String password,
			@RequestParam(value=Constants.SYSTEM_DISK_CATEGORY, required=false) String systemDiskCategory,
			@RequestParam(value=Constants.DATA_DISK1_CATEGORY, required=false) String dataDisk_1_Category,
			@RequestParam(value=Constants.DATA_DISK1_SIZE, required=false) String dataDisk_1_Size,
			@RequestParam(value=Constants.DATA_DISK1_DELETE_WITHINSTANCE, required=false) String dataDisk_1_SizeWithInstance,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL,required=false) String userEmail) {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.REGION_ID, regionId);
		paramsMap.put(Constants.IMAGE_ID, imageId);
		paramsMap.put(Constants.INSTANCE_TYPE, instanceType);
		paramsMap.put(Constants.SECURITY_GROUP_ID, securityGroupId);
		if(null!=zoneId) {
			paramsMap.put(Constants.ZONE_ID, zoneId);
		}
		if(null!=instanceName) {
			paramsMap.put(Constants.INSTANCE_NAME, instanceName);
		}
		if(null!=description) {
			paramsMap.put(Constants.DESCRIPTION, description);
		}
		if(null!=instanceChargeType) {
			paramsMap.put(Constants.INSTANCE_CHARGE_TYPE, instanceChargeType);
		}
		if(null!=instanceChargeLength) {
			paramsMap.put(Constants.INSTANCE_CHARGE_LENGTH, instanceChargeLength);
		}
		if(null!=internetChargeType) {
			paramsMap.put(Constants.INTERNET_CHARGE_TYPE, internetChargeType);
		}
		if(null!=maxBandwidthOut) {
			paramsMap.put(Constants.INTERNET_MAX_BANDWIDTH_OUT, maxBandwidthOut);
		}
		if(null!=hostName) {
			paramsMap.put(Constants.HOST_NAME, hostName);
		}
		if(null!=password) {
			paramsMap.put(Constants.PASSWORD, password);
		}
		if(null!=systemDiskCategory) {
			paramsMap.put(Constants.SYSTEM_DISK_CATEGORY, systemDiskCategory);
		}
		if(null!=dataDisk_1_Category) {
			paramsMap.put(Constants.DATA_DISK1_CATEGORY, dataDisk_1_Category);
		}
		if(null!=dataDisk_1_Size) {
			paramsMap.put(Constants.DATA_DISK1_SIZE, dataDisk_1_Size);
		}
		if(null!=dataDisk_1_SizeWithInstance) {
			paramsMap.put(Constants.DATA_DISK1_DELETE_WITHINSTANCE, dataDisk_1_SizeWithInstance);
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
		if(null==paramsMap.get(Constants.INSTANCE_CHARGE_TYPE)) {
			defaultParamsMap.put(Constants.INSTANCE_CHARGE_TYPE, Constants.PAY_BY_HOUR);
		}
		if(null==paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH)) {
			defaultParamsMap.put(Constants.INSTANCE_CHARGE_LENGTH, "1");
		}
		if(null==paramsMap.get(Constants.INTERNET_CHARGE_TYPE)) {
			defaultParamsMap.put(Constants.INTERNET_CHARGE_TYPE, Constants.PAY_BY_BANDWIDTH);
		}
		if(null==paramsMap.get(Constants.INTERNET_MAX_BANDWIDTH_OUT)) {
			defaultParamsMap.put(Constants.INTERNET_MAX_BANDWIDTH_OUT, "0");
		}
		if(null==paramsMap.get(Constants.SYSTEM_DISK_CATEGORY)) {
			defaultParamsMap.put(Constants.SYSTEM_DISK_CATEGORY, Constants.DISK_CLOUD);
		}
		if(null==paramsMap.get(Constants.DATA_DISK1_CATEGORY)) {
			defaultParamsMap.put(Constants.DATA_DISK1_CATEGORY, Constants.DISK_CLOUD);
		}
		if(null==paramsMap.get(Constants.DATA_DISK1_SIZE)) {
			defaultParamsMap.put(Constants.DATA_DISK1_SIZE, "0");
		}
		if(null==paramsMap.get(Constants.DATA_DISK1_DELETE_WITHINSTANCE)) {
			defaultParamsMap.put(Constants.DATA_DISK1_DELETE_WITHINSTANCE, "true");
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
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return new CreateInstanceResponse(requestId, 
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}
			
			//扣钱代码
			resultMap = billingmanager.pay(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return new CreateInstanceResponse(requestId, 
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}
			
			//获取创建云主机请求类
			ServerCreateReq serverCreateReq = instanceManager.gainServerCreateReq(appKeyId, paramsMap);
			resultMap = instanceOperate.createInstance(serverCreateReq, paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return new CreateInstanceResponse(requestId, 
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}
			
			//期限代码
			resultMap = billingmanager.AddEndtime(paramsMap, defaultParamsMap,resultMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return new CreateInstanceResponse(requestId, 
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}
			
			response.setStatus(constructResponse.getResponseStatus(resultMap));
			return new CreateInstanceResponse(requestId, resultMap.get(Constants.INSTANCE_ID));
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(resultMap));
			return new CreateInstanceResponse(requestId, Constants.INTERNAL_ERROR, 
					"The request processing has failed due to some unknown error.");
		}
	}
	/**
	 * 开放API启动云主机实例
	 * @author hgm 
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.START_INSTANCE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse StartInstance(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL, required=false) String userEmail) {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
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
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			//启动云主机实例
			resultMap = instanceOperate.startInstance(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Start instance successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}

	/**
	 * 开放API停止云主机实例
	 * @author hgm 
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.STOP_INSTANCE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse StopInstance(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(value=Constants.FORCE_STOP,required=false) String forceStop,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL,required=false) String userEmail) throws Exception {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
		if(null!=forceStop) {
			paramsMap.put(Constants.FORCE_STOP, forceStop);
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
		if(null==paramsMap.get(Constants.FORMAT)) {
			defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
		}
		//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
		String requestId = UuidUtil.getRandomUuid();
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			//关闭云主机实例
			resultMap = instanceOperate.stopInstance(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Stop instance successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	
	/**
	 * 重新启动云主机实例
	 * @param action
	 * @param instanceId
	 * @param version
	 * @param appKeyId
	 * @param signature
	 * @param timestamp
	 * @param userEmail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.REBOOT_INSTANCE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse RebootInstance(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
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
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
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
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = instanceOperate.rebootInstance(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Reboot instance successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	/**
	 * 查询云主机实例状态
	 * @param action
	 * @param instanceId
	 * @param version
	 * @param appKeyId
	 * @param signature
	 * @param timestamp
	 * @param userEmail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_INSTANCE_STATUS, produces = { "application/json","application/xml"})
	@ResponseBody
	public DescribeInstanceStatusResponse DescribeInstanceStatus(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.REGION_ID) String regionId,
			@RequestParam(value=Constants.ZONE_ID, required=false) String zoneId,
			@RequestParam(value=Constants.PAGE_NUMBER, required=false) String pageNumber,
			@RequestParam(value=Constants.PAGE_SIZE, required=false) String pageSize,
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
		paramsMap.put(Constants.REGION_ID, regionId);
		if(null!=format) {
			paramsMap.put(Constants.FORMAT, format);
		}
		paramsMap.put(Constants.VERSION, version);
		paramsMap.put(Constants.APPKEY_ID, appKeyId);
		paramsMap.put(Constants.SIGNATURE, signature);
		paramsMap.put(Constants.TIMESTAMP, timestamp);
		if(null!=zoneId) {
			paramsMap.put(Constants.ZONE_ID, zoneId);
		}
		if(null!=pageNumber) {
			paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
		}
		if(null!=pageSize) {
			paramsMap.put(Constants.PAGE_SIZE, pageSize);
		}
		if(null!=userEmail) {
			paramsMap.put(Constants.USER_EMAIL, userEmail);
		}
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
		//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
		String requestId = UuidUtil.getRandomUuid();
		//resultMap : 检查后的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return new DescribeInstanceStatusResponse(requestId, 
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}
			//返回多个参数，而且类型不同，故此处返回一个Object的Map
			Map<String, Object> resultMap2 = instanceOperate.describeInstanceStatus(paramsMap, requestId);
			if(null==resultMap2 || null==resultMap2.get(Constants.HTTP_CODE) ||
					!(resultMap2.get(Constants.HTTP_CODE).toString()).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new DescribeInstanceStatusResponse(requestId, 
						resultMap2.get(Constants.ERRORCODE).toString(), resultMap2.get(Constants.ERRORMESSAGE).toString());
			}
			logger.info("Describe instance status successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new DescribeInstanceStatusResponse(requestId, resultMap2);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
			return new DescribeInstanceStatusResponse(requestId, Constants.INTERNAL_ERROR,
					Constants.DEFAULT_ERROR_MESSAGE);
		}
	}
	
	/**
	 * 开放API删除云主机实例
	 * @author hgm 
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DELETE_INSTANCE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse DeleteInstance(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(value=Constants.FORCE_DELETE, required=false) String forceDelete,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL, required=false) String userEmail) {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
		if(null!=forceDelete) {
			paramsMap.put(Constants.FORCE_DELETE, forceDelete);	
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
		if(null==paramsMap.get(Constants.FORMAT)) {
			defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
		}
		//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
		String requestId = UuidUtil.getRandomUuid();
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			//删除云主机实例
			resultMap = instanceOperate.deleteInstance(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Delete instance successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	/**
	 * 云主机系统重置
	 * @author luofan
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.RESET_INSTANCE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse ResetInstance(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
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
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
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
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = instanceOperate.resetInstance(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Reset instance successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	/**
	 * 云主机iso弹出
	 * @author luofan
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.ISO_DETACH, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse IsoDetach(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
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
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
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
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = instanceOperate.isoDetach(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Iso detach successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	/**
	 * 云主机iso安装
	 * @author luofan
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.ISO_BOOT, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse IsoBoot(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(Constants.IMAGE_ID) String imageId,
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
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
		paramsMap.put(Constants.IMAGE_ID, imageId);
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
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = instanceOperate.isoBoot(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Iso boot successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	/**
	 * 修改云主机属性
	 * @author luofan
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.MODIFY_INSTANCE_ATTRIBUTE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse ModifyInstanceAttribute(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(value=Constants.INSTANCE_NAME, required=false) String instanceName,
			@RequestParam(value=Constants.DESCRIPTION, required=false) String description,
			@RequestParam(value=Constants.PASSWORD, required=false) String password,
			@RequestParam(value=Constants.HOST_NAME, required=false) String hostName,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL, required=false) String userEmail ) {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
		if(null!=instanceName) {
			paramsMap.put(Constants.INSTANCE_NAME, instanceName);
		}
		if(null!=description) {
			paramsMap.put(Constants.DESCRIPTION, description);
		}
		if(null!=password) {
			paramsMap.put(Constants.PASSWORD, password);
		}
		if(null!=hostName) {
			paramsMap.put(Constants.HOST_NAME, hostName);
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
		if(null==paramsMap.get(Constants.HOST_NAME))
		{
			defaultParamsMap.put(Constants.HOST_NAME,"root");
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
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = instanceOperate.modifyInstanceAttribute(paramsMap,defaultParamsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Modify instance attribute successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	
	/**
	 * 修改云主机资源配置
	 * @author luofan
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.MODIFY_INSTANCE_RESOURCE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse ModifyInstanceResource(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(value=Constants.CPU_NUM, required=false) String cpuNum,
			@RequestParam(value=Constants.RAM_SIZE, required=false) String ramSize,
			@RequestParam(value=Constants.INTERNET_MAX_BANDWIDTH_OUT, required=false) String maxBandwidthOut,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL, required=false) String userEmail ) {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
		if(null!=cpuNum) {
			paramsMap.put(Constants.CPU_NUM, cpuNum);
		}
		if(null!=ramSize) {
			paramsMap.put(Constants.RAM_SIZE, ramSize);
		}
		if(null!=maxBandwidthOut) {
			paramsMap.put(Constants.INTERNET_MAX_BANDWIDTH_OUT, maxBandwidthOut);
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
		if(null==paramsMap.get(Constants.FORMAT)) {
			defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
		}
		//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
		String requestId = UuidUtil.getRandomUuid();
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = billingmanager.payForModifyInstanceResource(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = instanceOperate.modifyInstanceResource(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Modify instance resource successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	
	/**
	 * 修改云主机防火墙规则
	 * @author luofan
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.MODIFY_INSTANCE_SECURITYGROUP, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse ModifyInstanceSecurityGroup(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(value=Constants.SECURITY_GROUP_ID)String securitygroupid,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL, required=false) String userEmail ) {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
		paramsMap.put(Constants.SECURITY_GROUP_ID, securitygroupid);
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
		//resultMap : 每次操作的返回结果
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//首先对请求参数和操作权限进行统一检查和认证
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = instanceOperate.modifyInstanceSecurityGroup(paramsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Modify instance security group successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	
	/**
	 * 修改云主机付费方式
	 * @author luofan
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.MODIFY_INSTANCE_CHARGETYPE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse ModifyInstanceChargeType(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.INSTANCE_ID) String instanceId,
			@RequestParam(value=Constants.INSTANCE_CHARGE_TYPE, required=false) String instanceChargeType,
			@RequestParam(value=Constants.INSTANCE_CHARGE_LENGTH, required=false) String instanceChargeLength,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value=Constants.USER_EMAIL, required=false) String userEmail ) {
		/**
		 * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
		 * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
		 * 中没有默认的参数值，则defaultParamsMap为null
		 */
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.ACTION, action);
		paramsMap.put(Constants.INSTANCE_ID, instanceId);
		if(null!=instanceChargeType) {
			paramsMap.put(Constants.INSTANCE_CHARGE_TYPE, instanceChargeType);
		}
		if(null!=instanceChargeLength) {
			paramsMap.put(Constants.INSTANCE_CHARGE_LENGTH, instanceChargeLength);
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
		if(null==paramsMap.get(Constants.INSTANCE_CHARGE_TYPE)) {
			defaultParamsMap.put(Constants.INSTANCE_CHARGE_TYPE, Constants.PAY_BY_HOUR);
		}
		if(null==paramsMap.get(Constants.INSTANCE_CHARGE_LENGTH)) {
			defaultParamsMap.put(Constants.INSTANCE_CHARGE_LENGTH, "1");
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
			resultMap = instanceCheckAndAuth.InstanceCheckAndAuth(paramsMap, defaultParamsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			resultMap = billingmanager.modifyInstanceChargeType(paramsMap,defaultParamsMap, requestId);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return constructResponse.getBaseResponse(requestId, resultMap);
			}
			logger.info("Modify instance charge type successfully!");
			response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
			return new BaseResponse(requestId);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return constructResponse.getBaseResponse(requestId, null);
		}
	}
	
	public InstanceManager getInstanceManager() {
		return instanceManager;
	}

	public void setInstanceManager(InstanceManager instanceManager) {
		this.instanceManager = instanceManager;
	}

	public InstanceOperate getInstanceOperate() {
		return instanceOperate;
	}

	public void setInstanceOperate(InstanceOperate instanceOperate) {
		this.instanceOperate = instanceOperate;
	}
	
}
