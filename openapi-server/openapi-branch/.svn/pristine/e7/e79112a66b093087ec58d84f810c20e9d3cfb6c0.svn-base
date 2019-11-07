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

import com.free4lab.utils.recommend.Constant;

import appcloud.api.beans.SecurityGroup;
import appcloud.common.util.UuidUtil;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.manager.util.SecurityGroupUnifiedCheckAndAuth;
import appcloud.openapi.operate.SecurityGroupOperate;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateSecurityGroupResponse;
import appcloud.openapi.response.SecurityGroupRulesReponse;
import appcloud.openapi.response.SecurityGroupsDetailReponse;

@RestController
public class SecurityGroupResource extends BaseController{
	private static Logger logger = Logger.getLogger(SecurityGroupResource.class);
	
	@Autowired
	private SecurityGroupUnifiedCheckAndAuth sgUnifiedCheckAndAuth;
	@Autowired
	private ConstructResponse constructResponse;
	@Autowired
	private SecurityGroupOperate securityGroupOperate;
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.CREATE_SECURITY_GROUP, produces = { "application/json","application/xml"})
	@ResponseBody
	public CreateSecurityGroupResponse create(@RequestParam(value = Constants.ACTION) String action, 
			@RequestParam(value = Constants.REGION_ID, required = false) String regionId,
			@RequestParam(value = Constants.SECURITY_GROUP_NAME, required = false) String securityGroupName,
			@RequestParam(value = Constants.DESCRIPTION, required = false) String description,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(value = Constants.VERSION) String version,
			@RequestParam(value = Constants.APPKEY_ID) String appKeyId,
			@RequestParam(value = Constants.SIGNATURE) String signature,
			@RequestParam(value = Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			if(null!=regionId) {
				paramsMap.put(Constants.REGION_ID, regionId);
			}
			if(null!=securityGroupName) {
				paramsMap.put(Constants.SECURITY_GROUP_NAME, securityGroupName);
			}
			if(null!=description) {
				paramsMap.put(Constants.DESCRIPTION, description);
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
			defaultParamsMap.put(Constants.SECURITY_GROUP_NAME, "unknow-name");
			defaultParamsMap.put(Constants.DESCRIPTION, null);
			
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = sgUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new CreateSecurityGroupResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				String uuid = securityGroupOperate.create(appKeyId, regionId, paramsMap.get(Constants.SECURITY_GROUP_NAME), paramsMap.get(Constants.DESCRIPTION), requestId);
				
				return new CreateSecurityGroupResponse(requestId, String.valueOf(uuid));
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new CreateSecurityGroupResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DELETE_SECURITY_GROUP, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse delete(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.SECURITY_GROUP_ID) String securityGroupId,
			@RequestParam(value=Constants.REGION_ID) String regionId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.SECURITY_GROUP_ID, securityGroupId);
			paramsMap.put(Constants.REGION_ID, regionId);
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
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = sgUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = securityGroupOperate.delete(appKeyId, securityGroupId, requestId);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "删除失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.AUTHORIZE_SECURITY_GROUP, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse authorizeSecurityGroup(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.SECURITY_GROUP_ID) String securityGroupId,
			@RequestParam(value=Constants.REGION_ID) String regionId,
			@RequestParam(value=Constants.IP_PROTOCOL) String ipProtocol,
			@RequestParam(value=Constants.PORT_RANGE) String portRange,
			@RequestParam(value=Constants.SOURCE_CIDR_IP,required =false) String sourceCidrIp,
			@RequestParam(value=Constants.POLICY,required =false) String policy,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.SECURITY_GROUP_ID, securityGroupId);
			paramsMap.put(Constants.REGION_ID, regionId);
			paramsMap.put(Constants.IP_PROTOCOL, ipProtocol);
			paramsMap.put(Constants.PORT_RANGE, portRange);
			if(null!=sourceCidrIp) {
				paramsMap.put(Constants.SOURCE_CIDR_IP, sourceCidrIp);
			}
			if(null!=policy) {
				paramsMap.put(Constants.POLICY, policy);
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
			defaultParamsMap.put(Constants.SOURCE_CIDR_IP, "0.0.0.0/0");
			defaultParamsMap.put(Constants.POLICY, "accept");
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = sgUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = securityGroupOperate.createRule(appKeyId, securityGroupId,
						paramsMap.get(Constants.IP_PROTOCOL), paramsMap.get(Constants.PORT_RANGE), 
						paramsMap.get(Constants.SOURCE_CIDR_IP), paramsMap.get(Constants.POLICY), 
						requestId);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "授权安全组规则失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_SECURITY_GROUPS, produces = { "application/json","application/xml"})
	@ResponseBody
	public SecurityGroupsDetailReponse describeSecurityGroups(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(value=Constants.REGION_ID) String regionId,
			@RequestParam(value=Constants.PAGE_NUMBER, required=false) String pageNumber,
			@RequestParam(value=Constants.PAGE_SIZE, required=false) String pageSize,
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
			if(null!=pageNumber) {
				paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
			}
			if(null!=pageSize) {
				paramsMap.put(Constants.PAGE_SIZE, pageSize);
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
			
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.PAGE_NUMBER)) {
				defaultParamsMap.put(Constants.PAGE_NUMBER, "1");
			}
			if(null==paramsMap.get(Constants.PAGE_SIZE)) {
				defaultParamsMap.put(Constants.PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE.toString());
			}
			
			try {
				Map<String, String> resultMap = sgUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new SecurityGroupsDetailReponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				SecurityGroupsDetailReponse result = securityGroupOperate.describe(appKeyId, regionId, 
						paramsMap.get(Constants.PAGE_NUMBER), paramsMap.get(Constants.PAGE_SIZE), requestId);
				
				return result;
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new SecurityGroupsDetailReponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.REVOKE_SECURITY_GROUP, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse revokeSecurityGroup(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.SECURITY_GROUP_ID) String securityGroupId,
			@RequestParam(value=Constants.REGION_ID) String regionId,
			@RequestParam(value=Constants.IP_PROTOCOL) String ipProtocol,
			@RequestParam(value=Constants.PORT_RANGE) String portRange,
			@RequestParam(value=Constants.SOURCE_CIDR_IP,required =false) String sourceCidrIp,
			@RequestParam(value=Constants.POLICY,required =false) String policy,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.SECURITY_GROUP_ID, securityGroupId);
			paramsMap.put(Constants.REGION_ID, regionId);
			paramsMap.put(Constants.IP_PROTOCOL, ipProtocol);
			paramsMap.put(Constants.PORT_RANGE, portRange);
			if(null!=sourceCidrIp) {
				paramsMap.put(Constants.SOURCE_CIDR_IP, sourceCidrIp);
			}
			if(null!=policy) {
				paramsMap.put(Constants.POLICY, policy);
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
			defaultParamsMap.put(Constants.SOURCE_CIDR_IP, "0.0.0.0/0");
			defaultParamsMap.put(Constants.POLICY, "accept");
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = sgUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = securityGroupOperate.deleteRules(appKeyId, paramsMap.get("ruleIds") ,requestId);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "撤销安全组规则失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_SECURITY_GROUP_ATTRIBUTE, produces = { "application/json","application/xml"})
	@ResponseBody
	public SecurityGroupRulesReponse describeSGAttitude(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.SECURITY_GROUP_ID) String securityGroupId,
			@RequestParam(value=Constants.REGION_ID) String regionId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.SECURITY_GROUP_ID, securityGroupId);
			paramsMap.put(Constants.REGION_ID, regionId);
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
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = sgUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new SecurityGroupRulesReponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				SecurityGroupRulesReponse result = securityGroupOperate.describeSGAttitude(appKeyId, securityGroupId, requestId);
				
				return result;
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new SecurityGroupRulesReponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.MODIFY_SECURITY_GROUP_ATTRIBUTE, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse modifySG(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.SECURITY_GROUP_ID) String securityGroupId,
			@RequestParam(value=Constants.REGION_ID) String regionId,
			@RequestParam(value = Constants.SECURITY_GROUP_NAME, required = false) String securityGroupName,
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
			paramsMap.put(Constants.SECURITY_GROUP_ID, securityGroupId);
			paramsMap.put(Constants.REGION_ID, regionId);
			if(null!=securityGroupName) {
				paramsMap.put(Constants.SECURITY_GROUP_NAME, securityGroupName);
			}
			if(null!=description) {
				paramsMap.put(Constants.DESCRIPTION, description);
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
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = sgUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = securityGroupOperate.modifyAttitude(appKeyId, securityGroupId, paramsMap.get(Constants.SECURITY_GROUP_NAME),
						paramsMap.get(Constants.DESCRIPTION), requestId);
				
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
}
