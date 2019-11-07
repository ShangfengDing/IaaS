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

import appcloud.common.util.UuidUtil;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.billing.BillingManager;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.BillingUnifiedCheckAndAuth;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.response.DescribeInstanceTypesResponse;

@RestController
public class BillingResource extends BaseController{
	
	private static Logger logger = Logger.getLogger(BillingResource.class);
	
	@Autowired
	private BillingManager billingOperate;
	@Autowired
	private BillingUnifiedCheckAndAuth billingUnifiedCheckAndAuth;
	@Autowired
	private ConstructResponse constructResponse;
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_INSTANCE_TYPES, produces = { "application/json","application/xml"})
	@ResponseBody
	public DescribeInstanceTypesResponse DescribeInstanceTypes(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
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
				Map<String, String> resultMap = billingUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				Map<String, Object> resultMap2 = billingOperate.DescribeInstanceTypes(paramsMap, requestId);
				if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
		
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
				logger.info("Get describeInstanceTypes success !");
				return new DescribeInstanceTypesResponse(requestId,resultMap2);
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new DescribeInstanceTypesResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
	}
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_DISK_TYPES, produces = { "application/json","application/xml"})
	@ResponseBody
	public DescribeInstanceTypesResponse DescribeDiskTypes(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
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
				Map<String, String> resultMap = billingUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				Map<String, Object> resultMap2 = billingOperate.DescribeDiskTypes(paramsMap, requestId);
				if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
		
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
				logger.info("Get describeDiskTypes success !");
				return new DescribeInstanceTypesResponse(requestId,resultMap2);
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new DescribeInstanceTypesResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
	}
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_INTERNET_TYPES, produces = { "application/json","application/xml"})
	@ResponseBody
	public DescribeInstanceTypesResponse DescribeInternetTypes(@RequestParam(Constants.ACTION) String action,
		    @RequestParam(value = Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
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
				Map<String, String> resultMap = billingUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				Map<String, Object> resultMap2 = billingOperate.DescribeInternetTypes(paramsMap, requestId);
				if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
		
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
				logger.info("Get describeInternetTypes success !");
				return new DescribeInstanceTypesResponse(requestId,resultMap2);
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new DescribeInstanceTypesResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
	}
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_CPU_TYPES, produces = { "application/json","application/xml"})
	@ResponseBody
	public DescribeInstanceTypesResponse DescribeCpuTypes(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
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
				Map<String, String> resultMap = billingUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				Map<String, Object> resultMap2 = billingOperate.DescribeCpuTypes(paramsMap, requestId);
				if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
		
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
				logger.info("Get describeCpuTypes success !");
				return new DescribeInstanceTypesResponse(requestId,resultMap2);
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new DescribeInstanceTypesResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
	}
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_MEMORY_TYPES, produces = { "application/json","application/xml"})
	@ResponseBody
	public DescribeInstanceTypesResponse DescribeMemoryTypes(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
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
				Map<String, String> resultMap = billingUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				Map<String, Object> resultMap2 = billingOperate.DescribeMemoryTypes(paramsMap, requestId);
				if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new DescribeInstanceTypesResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
		
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
				logger.info("Get describeMemoryTypes success !");
				return new DescribeInstanceTypesResponse(requestId,resultMap2);
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new DescribeInstanceTypesResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
	}
}
