package appcloud.openapi.resources;

import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.securitygroup.RuleCreateReq;
import appcloud.api.beans.securitygroup.SecurityGroupCreateReq;
import appcloud.api.client.RuleClient;
import appcloud.api.client.SecurityGroupClient;
import appcloud.common.util.UuidUtil;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.manager.util.UserUnifiedCheckAndAuth;
import appcloud.openapi.operate.impl.AccountOperateImpl;
import appcloud.openapi.response.GroupCreateResponse;
import appcloud.openapi.response.UserCreateForDisResponse;
import appcloud.openapi.response.UserCreateResponse;
import com.appcloud.vm.fe.util.ClientFactory;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Idan on 2017/10/22.
 * 账户相关的server服务
 * 1. 添加用户
 * 2. 添加群组
 */
@RestController
public class AccountResource extends BaseController{

    private static Logger logger = Logger.getLogger(AccountResource.class);
    private UserUnifiedCheckAndAuth userUnifiedCheckAndAuth = new UserUnifiedCheckAndAuth();
    private ConstructResponse constructResponse = new ConstructResponse();
    private AccountOperateImpl accountOperate = AccountOperateImpl.getInstance();
    private SecurityGroupClient securityGroupClient = ClientFactory.getSecurityGroupClient();
    private RuleClient ruleClient = ClientFactory.getRuleClient();

    @RequestMapping(method = RequestMethod.GET,  params="Action="+ ActionConstants.USER_CREATE, produces = { "application/json","application/xml"})
    @ResponseBody
    public UserCreateResponse CreateUser(@RequestParam(Constants.ACTION) String action,
                                             @RequestParam(value=Constants.NEW_USER_EMAIL, required=false) String newUserEmail,
                                             @RequestParam(value=Constants.GROUP_SECRET_KEY, required=false) String groupSecretKey,
                                             @RequestParam(value=Constants.FORMAT, required=false) String format,
                                             @RequestParam(Constants.VERSION) String version,
                                             @RequestParam(Constants.APPKEY_ID) String appKeyId,
                                             @RequestParam(Constants.SIGNATURE) String signature,
                                             @RequestParam(Constants.TIMESTAMP) String timestamp,
                                             @RequestParam(value=Constants.USER_EMAIL,required=false) String userEmail) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put(Constants.ACTION, action);

        if (null != newUserEmail) {
            paramsMap.put(Constants.NEW_USER_EMAIL, newUserEmail );
        }
        if (null != groupSecretKey) {
            paramsMap.put(Constants.GROUP_SECRET_KEY, groupSecretKey);
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
        if(null==paramsMap.get(Constants.FORMAT)) {
            defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
        }
        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        String requestId = UuidUtil.getRandomUuid();
        //resultMap : 每次操作的返回结果
        Map<String, String> resultMap = new HashMap<String, String>();

        try {
            // 参数检查
            resultMap = userUnifiedCheckAndAuth.userCheckAndAuth(paramsMap, defaultParamsMap);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new UserCreateResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            UserCreateResponse userCreateResponse = accountOperate.UserCreate(paramsMap,requestId);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new UserCreateResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            logger.info("Start instance successfully!");
            logger.info("start add security rule ……");
            String userId = userCreateResponse.getUserDetailItem().getUserId();
            addNewSecurityGroup(userId);
            return userCreateResponse;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("执行操作抛出异常",e);
            response.setStatus(constructResponse.getResponseStatus(null));
            return new UserCreateResponse(requestId,Constants.INTERNAL_ERROR,
                    " The request processing has failed due to some unknown error");
        }
    }

    @RequestMapping(method = RequestMethod.GET,  params="Action="+ ActionConstants.USER_CREATE_FOR_DIS, produces = { "application/json","application/xml"})
    @ResponseBody
    public UserCreateForDisResponse CreateUserForDis(@RequestParam(Constants.ACTION) String action,
                                                     @RequestParam(value=Constants.ACCOUNT_NAME, required=false) String accountName,
                                                     @RequestParam(value=Constants.REGION_ID, required=false) String regionId,
                                                     @RequestParam(value=Constants.ZONE_ID, required=false) String zoneId,
                                                     @RequestParam(value=Constants.NEW_USER_EMAIL, required=false) String newUserEmail,
                                                     @RequestParam(value=Constants.GROUP_SECRET_KEY, required=false) String groupSecretKey,
                                                     @RequestParam(value=Constants.FORMAT, required=false) String format,
                                                     @RequestParam(Constants.VERSION) String version,
                                                     @RequestParam(Constants.APPKEY_ID) String appKeyId,
                                                     @RequestParam(Constants.SIGNATURE) String signature,
                                                     @RequestParam(Constants.TIMESTAMP) String timestamp,
                                                     @RequestParam(value=Constants.USER_EMAIL,required=false) String userEmail) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put(Constants.ACTION, action);
        paramsMap.put(Constants.REGION_ID, regionId);
        paramsMap.put(Constants.ZONE_ID, zoneId);
        paramsMap.put(Constants.ACCOUNT_NAME,accountName);

        if (null != newUserEmail) {
            paramsMap.put(Constants.NEW_USER_EMAIL, newUserEmail );
        }
        if (null != groupSecretKey) {
            paramsMap.put(Constants.GROUP_SECRET_KEY, groupSecretKey);
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
        if(null==paramsMap.get(Constants.FORMAT)) {
            defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
        }
        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        String requestId = UuidUtil.getRandomUuid();
        //resultMap : 每次操作的返回结果
        Map<String, String> resultMap = new HashMap<String, String>();

        try {
            // 参数检查
            resultMap = userUnifiedCheckAndAuth.userCheckAndAuth(paramsMap, defaultParamsMap);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new UserCreateForDisResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            UserCreateForDisResponse userCreateResponse = accountOperate.UserCreateForDis(paramsMap,requestId);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new UserCreateForDisResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            logger.info("Start instance successfully!");
            logger.info("start add security rule ……");
            String userId = userCreateResponse.getVmUserItem().getUserId().toString();
            addNewSecurityGroup(userId);
            return userCreateResponse;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("执行操作抛出异常",e);
            response.setStatus(constructResponse.getResponseStatus(null));
            return new UserCreateForDisResponse(requestId,Constants.INTERNAL_ERROR,
                    " The request processing has failed due to some unknown error");
        }
    }


    @RequestMapping(method = RequestMethod.GET,  params="Action="+ ActionConstants.GROUP_CREATE, produces = { "application/json","application/xml"})
    @ResponseBody
    public GroupCreateResponse CreateGroup (@RequestParam(Constants.ACTION) String action,
//                                              @RequestParam(Constants.REGION_ID) String regionId,
                                              @RequestParam(value=Constants.GROUP_NAME, required=false) String name,
                                              @RequestParam(value=Constants.GROUP_DESCRIPTION, required=false) String description,
                                              @RequestParam(value=Constants.FORMAT, required=false) String format,
                                              @RequestParam(Constants.VERSION) String version,
                                              @RequestParam(Constants.APPKEY_ID) String appKeyId,
                                              @RequestParam(Constants.SIGNATURE) String signature,
                                              @RequestParam(Constants.TIMESTAMP) String timestamp,
                                              @RequestParam(value=Constants.USER_EMAIL,required=false) String userEmail) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put(Constants.ACTION, action);
//        paramsMap.put(Constants.REGION_ID, regionId);

        //用户相关参数
        if (null != name) {
            paramsMap.put(Constants.GROUP_NAME, name );
        }
        if (null != description) {
            paramsMap.put(Constants.GROUP_DESCRIPTION, description);
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
        if(null==paramsMap.get(Constants.FORMAT)) {
            defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
        }
        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        String requestId = UuidUtil.getRandomUuid();
        //resultMap : 每次操作的返回结果
        Map<String, String> resultMap = new HashMap<String, String>();

        try {
            // 参数检查
            resultMap = userUnifiedCheckAndAuth.userCheckAndAuth(paramsMap, defaultParamsMap);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new GroupCreateResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            GroupCreateResponse groupCreateResponse = accountOperate.GroupCreate(paramsMap,requestId);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new GroupCreateResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            logger.info("Start instance successfully!");

            return groupCreateResponse;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("执行操作抛出异常",e);
            response.setStatus(constructResponse.getResponseStatus(null));
            return new GroupCreateResponse(requestId,Constants.INTERNAL_ERROR,
                    " The request processing has failed due to some unknown error");
        }
    }

    private void addNewSecurityGroup(String userId) {
        SecurityGroupCreateReq SecurityGroupEmptyRules = new SecurityGroupCreateReq("关闭所有端口", "默认关闭所有端口");
        SecurityGroupCreateReq SecurityGroupFullRules = new SecurityGroupCreateReq("开放所有端口", "默认开放所有端口");
        SecurityGroupCreateReq SecurityGroupOnly22Rules = new SecurityGroupCreateReq("仅开放SSH端口", "默认开放22端口");
        securityGroupClient.createSecurityGroup(userId, SecurityGroupEmptyRules);
        securityGroupClient.createSecurityGroup(userId, SecurityGroupFullRules);
        securityGroupClient.createSecurityGroup(userId, SecurityGroupOnly22Rules);

        List<SecurityGroup>  groups = securityGroupClient.getSecurityGroups(userId);
        RuleCreateReq newRules = null;
        for(SecurityGroup sg : groups) {
            if(sg.name.equals("开放所有端口")) {
                //开放所有端口
                newRules = new RuleCreateReq("TCP", new Integer(0), new Integer(65535),
                        "0.0.0.0/0", "", sg.id);
                ruleClient.createRule(userId, newRules);
                newRules = new RuleCreateReq("ICMP", null, null, "0.0.0.0/0", "", sg.id);
                ruleClient.createRule(userId, newRules);
            } else if(sg.name.equals("仅开放SSH端口")) {
                //仅开放ssh端口
                newRules = new RuleCreateReq("TCP", new Integer(22), new Integer(22),
                        "0.0.0.0/0", "", sg.id);
                ruleClient.createRule(userId, newRules);
            }
        }
    }
}
