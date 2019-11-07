package appcloud.openapi.resources;

import appcloud.common.model.VmImage;
import appcloud.common.util.UuidUtil;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.constant.ResultConstants;
import appcloud.openapi.datatype.InstanceMonitorDataType;
import appcloud.openapi.manager.util.AdminUnifiedCheckAndAuth;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.operate.AdminOperate;
import appcloud.openapi.operate.InstanceOperate;
import appcloud.openapi.operate.impl.AdminOperateImpl;
import appcloud.openapi.response.*;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ManagerResource extends BaseController{

    private static Logger logger = Logger.getLogger(ManagerResource.class);

    //@Autowired
    private AdminOperate adminOperate = new AdminOperateImpl();
    //@Autowired
    private ConstructResponse constructResponse = new ConstructResponse();
    //@Autowired
    private InstanceOperate instanceOperate;


    private AdminUnifiedCheckAndAuth adminUnifiedCheckAndAuth = new AdminUnifiedCheckAndAuth();


    @RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.ADMIN_DESCRIBE_INSTANCES, produces = { "application/json","application/xml"})
    @ResponseBody
    public DescribeInstancesResponse AdminDescribeInstances(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.REGION_ID) String regionId,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(value = Constants.USER_ID, required = false) String userId,
            @RequestParam(value = Constants.HOST_ID, required = false) String hostId,
            @RequestParam(value = Constants.ZONE_ID, required = false) String zoneId,
            @RequestParam(value = Constants.INSTANCE_IDS, required = false) String instanceIds,
            @RequestParam(value = Constants.INNER_IP_ADDRESSES, required = false) String innerIpAddresses,
            @RequestParam(value = Constants.PUBLIC_IP_ADDRESSES, required = false) String publicIpAddresses,
            @RequestParam(value = Constants.SECURITY_GROUP_ID, required = false) String securityGroupid,
            @RequestParam(value = Constants.INSTANCE_NAME, required = false) String instanceName,
            @RequestParam(value = Constants.INSTANCE_STATUS, required = false) String status,
            @RequestParam(value = Constants.IMAGE_ID, required = false) String imageId,
            @RequestParam(value = Constants.PAGE_NUMBER, required = false) String pageNumber,
            @RequestParam(value = Constants.PAGE_SIZE, required = false) String pageSize,
            @RequestParam(value = Constants.FORMAT, required = false) String format,
            @RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) throws Exception {
        /**
         * 绑定接口参数并进行检查，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名认证检查
         * paramsMap中存放的参数与请求中的参数一致，defaultParamsMap存放有默认值的参数，若接口
         * 中没有默认的参数值，则defaultParamsMap为null
         */
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put(Constants.ACTION, action);
        paramsMap.put(Constants.REGION_ID, regionId);
        if (null != format) {
            paramsMap.put(Constants.FORMAT, format);
        }
        paramsMap.put(Constants.VERSION, version);
        paramsMap.put(Constants.APPKEY_ID, appKeyId);
        paramsMap.put(Constants.SIGNATURE, signature);
        paramsMap.put(Constants.TIMESTAMP, timestamp);
        if (null != zoneId) {
            paramsMap.put(Constants.ZONE_ID, zoneId);
        }
        if (null != userId) {
            paramsMap.put(Constants.USER_ID, userId);
        }
        if (null != hostId) {
            paramsMap.put(Constants.HOST_ID, hostId);
        }
        if (null != instanceIds) {
            paramsMap.put(Constants.INSTANCE_IDS, instanceIds);
        }
        if (null != innerIpAddresses) {
            paramsMap.put(Constants.INNER_IP_ADDRESSES, innerIpAddresses);
        }
        if (null != publicIpAddresses) {
            paramsMap.put(Constants.PUBLIC_IP_ADDRESSES, publicIpAddresses);
        }
        if (null != securityGroupid) {
            paramsMap.put(Constants.SECURITY_GROUP_ID, securityGroupid);
        }
        if (null != instanceName) {
            paramsMap.put(Constants.INSTANCE_NAME, instanceName);
        }
        if (null != status) {
            paramsMap.put(Constants.INSTANCE_STATUS, status);
        }
        if (null != imageId) {
            paramsMap.put(Constants.IMAGE_ID, imageId);
        }
        if (null != pageNumber) {
            paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
        }
        if (null != pageSize) {
            paramsMap.put(Constants.PAGE_SIZE, pageSize);
        }
        if (null != userEmail) {
            paramsMap.put(Constants.USER_EMAIL, userEmail);
        }
        //将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
        Map<String, String> defaultParamsMap = new HashMap<String, String>();
        if (null == paramsMap.get(Constants.FORMAT)) {
            defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
        }
        if (null == paramsMap.get(Constants.PAGE_NUMBER)) {
            defaultParamsMap.put(Constants.PAGE_NUMBER, "1");
        }
        if (null == paramsMap.get(Constants.PAGE_SIZE)) {
            defaultParamsMap.put(Constants.PAGE_SIZE, Constants.DEFAULT_ADMIN_PAGE_SIZE.toString());
        }
        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        String requestId = UuidUtil.getRandomUuid();
        //resultMap : 检查后的返回结果
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            //首先对请求参数和操作权限进行统一检查和认证
            resultMap = adminUnifiedCheckAndAuth.adminCheckAndAuth(paramsMap, defaultParamsMap);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new DescribeInstancesResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            //返回多个参数，而且类型不同，故此处返回一个Object的Map
            DescribeInstancesResponse describeInstances = adminOperate.AdminDescribeInstances(paramsMap, requestId);
            logger.info("Describe instances successfully!");
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
            return describeInstances;
        } catch (Exception e) {
            logger.warn("执行操作抛出异常",e);
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
            return new DescribeInstancesResponse(requestId, Constants.INTERNAL_ERROR,
                    Constants.DEFAULT_ERROR_MESSAGE);
        }
    }

    @RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.ADMIN_DESCRIBE_DISKS, produces = { "application/json","application/xml"})
    @ResponseBody
    public DisksDetailReponse AdminDescribeDisks(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.REGION_ID) String regionId,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(value = Constants.HOST_ID,required = false) String hostId,
            @RequestParam(value = Constants.USER_ID,required = false) String userId,
            @RequestParam(value = Constants.DISK_IDS, required = false) String diskIds,
            @RequestParam(value = Constants.PAGE_NUMBER, required = false) String pageNumber,
            @RequestParam(value = Constants.PAGE_SIZE, required = false) String pageSize,
            @RequestParam(value = Constants.ZONE_ID, required = false) String zoneId,
            @RequestParam(value = Constants.INSTANCE_ID, required = false) String instanceId,
            @RequestParam(value = Constants.DISK_TYPE, required = false) String diskType,
            @RequestParam(value = Constants.DISK_NAME, required = false) String diskName,
            @RequestParam(value = Constants.DESCRIPTION, required = false) String description,
            @RequestParam(value = Constants.DISK_STATUS, required = false) String diskStatus,
            @RequestParam(value = Constants.DISK_ATTACH_STATUS, required = false) String diskAttachStatus,
            @RequestParam(value = Constants.FORMAT, required = false) String format,
            @RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
        //检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
        Map<String, String> paramsMap = new HashMap<String, String>();
        //添加必要参数
        paramsMap.put(Constants.ACTION, action);
        paramsMap.put(Constants.REGION_ID, regionId);
        paramsMap.put(Constants.VERSION, version);
        paramsMap.put(Constants.APPKEY_ID, appKeyId);
        paramsMap.put(Constants.SIGNATURE, signature);
        paramsMap.put(Constants.TIMESTAMP, timestamp);
        //添加非必要参数
        if (null != userId)
            paramsMap.put(Constants.USER_ID,userId);
        if (null != hostId)
            paramsMap.put(Constants.HOST_ID,hostId);
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
            defaultParamsMap.put(Constants.PAGE_SIZE, Constants.DEFAULT_ADMIN_PAGE_SIZE.toString());
        }
        try {
            Map<String, String> resultMap = adminUnifiedCheckAndAuth.adminCheckAndAuth(paramsMap, defaultParamsMap);
            if (null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new DisksDetailReponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }

            DisksDetailReponse result = adminOperate.AdminDescribeDisks(paramsMap,requestId);

            return result;

        } catch (Exception e) {
            logger.warn("执行操作抛出异常", e);
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
            return new DisksDetailReponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
        }
    }

    @RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.ADMIN_DESCRIBE_SERVICES, produces = { "application/json","application/xml"})
    @ResponseBody
    public ServicesResponse AdminDescribeServices(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.REGION_ID) String regionId,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(value = Constants.PAGE_NUMBER, required = false) String pageNumber,
            @RequestParam(value = Constants.PAGE_SIZE, required = false) String pageSize,
            @RequestParam(value = Constants.HOST_IP, required = false) String hostIp,
            @RequestParam(value = Constants.ZONE_ID, required = false) String zoneId,
            @RequestParam(value = Constants.HOST_IDS, required = false) String hostUuids,
            @RequestParam(value = Constants.SERVICE_TYPE, required = false) String serverType,
            @RequestParam(value = Constants.SERVICE_STATUS, required = false) String serverStatus,
            @RequestParam(value = Constants.FORMAT, required = false) String format) throws Exception {

        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put(Constants.ACTION,action);
        paramsMap.put(Constants.REGION_ID,regionId);
        paramsMap.put(Constants.VERSION,version);
        paramsMap.put(Constants.APPKEY_ID,appKeyId);
        paramsMap.put(Constants.SIGNATURE,signature);
        paramsMap.put(Constants.TIMESTAMP,timestamp);
        //添加非必要参数
        if (null != hostIp)
            paramsMap.put(Constants.HOST_IP,hostIp);
        if (null != hostUuids)
            paramsMap.put(Constants.HOST_IDS,hostUuids);
        if (null != serverType)
            paramsMap.put(Constants.SERVICE_TYPE, serverType);
        if (null != serverStatus)
            paramsMap.put(Constants.SERVICE_STATUS, serverStatus);
        if (null != pageSize)
            paramsMap.put(Constants.PAGE_SIZE, pageSize);
        if (null != pageNumber)
            paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
        if (null != zoneId)
            paramsMap.put(Constants.ZONE_ID, zoneId);
        if (null != format) {
            paramsMap.put(Constants.FORMAT, format);
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
            defaultParamsMap.put(Constants.PAGE_SIZE, Constants.DEFAULT_ADMIN_PAGE_SIZE.toString());
        }
        try {
            Map<String, String> resultMap = adminUnifiedCheckAndAuth.adminCheckAndAuth(paramsMap, defaultParamsMap);
            if (null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new ServicesResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }

            ServicesResponse result = adminOperate.AdminDescribeServices(paramsMap,requestId);

            return result;

        } catch (Exception e) {
            logger.warn("执行操作抛出异常", e);
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
            return new ServicesResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
        }
    }

    @RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.ADMIN_DESCRIBE_HOSTS, produces = { "application/json","application/xml"})
    @ResponseBody
    public HostsResponse AdminDescribeHosts(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.REGION_ID) String regionId,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(value = Constants.PAGE_NUMBER, required = false) String pageNumber,
            @RequestParam(value = Constants.PAGE_SIZE, required = false) String pageSize,
            @RequestParam(value = Constants.HOST_IP, required = false) String hostIp,
            @RequestParam(value = Constants.ZONE_ID, required = false) String zoneId,
            @RequestParam(value = Constants.HOST_UUID, required = false) String hostUuid,
            @RequestParam(value = Constants.HOST_TYPE, required = false) String hostType,
            @RequestParam(value = Constants.HOST_STATUS, required = false) String hostStatus,
            @RequestParam(value = Constants.FORMAT, required = false) String format) throws Exception {

        Map<String,String> paramsMap = new HashMap<>();

        paramsMap.put(Constants.ACTION,action);
        paramsMap.put(Constants.REGION_ID,regionId);
        paramsMap.put(Constants.VERSION,version);
        paramsMap.put(Constants.APPKEY_ID,appKeyId);
        paramsMap.put(Constants.SIGNATURE,signature);
        paramsMap.put(Constants.TIMESTAMP,timestamp);
        //添加非必要参数
        if (null != hostIp)
            paramsMap.put(Constants.HOST_IP,hostIp);
        if (null != hostUuid)
            paramsMap.put(Constants.HOST_UUID,hostUuid);
        if (null != hostType)
            paramsMap.put(Constants.HOST_TYPE, hostType);
        if (null != hostStatus)
            paramsMap.put(Constants.HOST_STATUS, hostStatus);
        if (null != pageSize)
            paramsMap.put(Constants.PAGE_SIZE, pageSize);
        if (null != pageNumber)
            paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
        if (null != zoneId)
            paramsMap.put(Constants.ZONE_ID, zoneId);
        if (null != format) {
            paramsMap.put(Constants.FORMAT, format);
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
            defaultParamsMap.put(Constants.PAGE_SIZE, Constants.DEFAULT_ADMIN_PAGE_SIZE.toString());
        }
        try {
            Map<String, String> resultMap = adminUnifiedCheckAndAuth.adminCheckAndAuth(paramsMap, defaultParamsMap);
            if (null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new HostsResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }

            HostsResponse result = adminOperate.AdminDescribeHosts(paramsMap,requestId);

            return result;

        } catch (Exception e) {
            logger.warn("执行操作抛出异常", e);
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
            return new HostsResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
        }

    }

    /**
     * 在线迁移虚拟机
     * @author zrain
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, params = "Action="+ActionConstants.ADMIN_ONLINE_MIGRATE, produces = {"application/json","application/xml"})
    @ResponseBody
    public BaseResponse AdminOnlineMigrate(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(Constants.REGION_ID) String regionId,
            @RequestParam(Constants.HOST_ID) String hostId,
            @RequestParam(Constants.INSTANCE_ID) String instanceId,
            @RequestParam(value = Constants.FORMAT, required = false) String format,
            @RequestParam(value = Constants.ZONE_ID, required = false) String zoneId
            ) throws Exception {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(Constants.ACTION, action);
        paramsMap.put(Constants.VERSION,version);
        paramsMap.put(Constants.APPKEY_ID,appKeyId);
        paramsMap.put(Constants.SIGNATURE,signature);
        paramsMap.put(Constants.TIMESTAMP,timestamp);
        paramsMap.put(Constants.HOST_ID, hostId);
        paramsMap.put(Constants.INSTANCE_ID, instanceId);
        paramsMap.put(Constants.REGION_ID,regionId);
        //不必要的参数
        if (null != zoneId) {
            paramsMap.put(Constants.ZONE_ID, zoneId);
        }
        if(null!=format) {
            paramsMap.put(Constants.FORMAT, format);
        }
        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        //resultMap : 每次操作的返回结果
        Map<String, String> defaultParamsMap = new HashMap<String, String>();
        if(null==paramsMap.get(Constants.FORMAT)) {
            defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
        }
        String requestId = UuidUtil.getRandomUuid();
        Map<String, String> resultMap = new HashMap<>();
        try {
            //首先对请求参数和操作权限进行统一检查和认证
            resultMap = adminUnifiedCheckAndAuth.adminCheckAndAuth(paramsMap,defaultParamsMap);
            if (null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return constructResponse.getBaseResponse(requestId, resultMap);
            }
            //线上迁移虚拟机
            resultMap = adminOperate.AdminOnlineMigrate(paramsMap, requestId);
//            resultMap = instanceOperate.OnlineMigrateInstance(paramsMap,requestId);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return constructResponse.getBaseResponse(requestId, resultMap);
            }
            logger.info("online migrate instance successfully!");
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
            return new BaseResponse(requestId);
        } catch (Exception e) {
            logger.warn("执行操作抛出异常",e);
            response.setStatus(constructResponse.getResponseStatus(null));
            return constructResponse.getBaseResponse(requestId, null);
        }
    }

    /**
     * 获取虚拟机的监控信息
     * @author zrain
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, params = "Action="+ActionConstants.ADMIN_MONITOR_INSTANCE_DATA, produces = { "application/json","application/xml"})
    @ResponseBody
    public InstancesMonitorInfoReponse AdminDescribeInstancesMonitorData(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.REGION_ID) String regionId,
            @RequestParam(value=Constants.START_TIME) String startTime,
            @RequestParam(value=Constants.END_TIME) String endTime,
            @RequestParam(value = Constants.ZONE_ID,required = false) String zoneId,
            @RequestParam(value = Constants.HOST_ID, required = false) String hostId,
            @RequestParam(value = Constants.INSTANCE_IDS, required = false) String instanceIds,
            @RequestParam(value = Constants.FORMAT, required=false) String format,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp) throws Exception{
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put(Constants.ACTION, action);
        paramsMap.put(Constants.REGION_ID, regionId);
        paramsMap.put(Constants.START_TIME, startTime);
        paramsMap.put(Constants.END_TIME, endTime);
        if (null != instanceIds) {
            paramsMap.put(Constants.INSTANCE_IDS,instanceIds);
        }
        if (null != format) {
            paramsMap.put(Constants.FORMAT, format);
        }
        if (null != zoneId) {
            paramsMap.put(Constants.ZONE_ID, zoneId);
        }
        if (null != hostId) {
            paramsMap.put(Constants.HOST_ID, hostId);
        }
        paramsMap.put(Constants.VERSION, version);
        paramsMap.put(Constants.APPKEY_ID, appKeyId);
        paramsMap.put(Constants.SIGNATURE, signature);
        paramsMap.put(Constants.TIMESTAMP, timestamp);
        Map<String, String> defaultParamsMap = new HashMap<String, String>();
        if(null==paramsMap.get(Constants.FORMAT)) {
            defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
        }
        String requestId = UuidUtil.getRandomUuid();
        Map<String, String> resultMap = new HashMap<>();
        try {
            //首先对请求参数和操作权限进行统一检查和认证
            resultMap = adminUnifiedCheckAndAuth.adminCheckAndAuth(paramsMap,defaultParamsMap);
            if (null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new InstancesMonitorInfoReponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            //获取虚拟机的监控信息
            Map<String, Object> monitorData = adminOperate.AdminDescribeMonitorData(paramsMap, requestId);
            if(null==monitorData || null==monitorData.get(Constants.HTTP_CODE) ||
                    !monitorData.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new InstancesMonitorInfoReponse(requestId,resultMap.get(Constants.ERRORCODE),
                        resultMap.get(Constants.ERRORMESSAGE));
            }
            logger.info("describe instances monitor data successfully!");
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
            return new InstancesMonitorInfoReponse((List<InstanceMonitorDataType>) monitorData.get(ResultConstants.INSTANCE_MONITOR_SET), requestId);
        } catch (Exception e) {
            logger.warn("执行操作抛出异常",e);
            response.setStatus(constructResponse.getResponseStatus(null));
            return new InstancesMonitorInfoReponse(requestId,resultMap.get(Constants.ERRORCODE),
                   "The request processing has failed due to some unknown error.");
        }
    }

    /**
     * @author zrain
     * Description: authorize the image for all of the group
     * 授权为平台公共镜像
     */
    @RequestMapping(method = RequestMethod.POST,  params="Action="+ActionConstants.ADMIN_AUTHORIZE_IMAGE, produces = { "application/json","application/xml"})
    @ResponseBody
    public BaseResponse AuthorizeImage (
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(Constants.IMAGE_UUID) String imageUuid,
            @RequestParam(value=Constants.FORMAT, required=false) String format,
            @RequestParam(Constants.VERSION) String version,
            @RequestParam(Constants.APPKEY_ID) String appKeyId,
            @RequestParam(Constants.SIGNATURE) String signature,
            @RequestParam(Constants.TIMESTAMP) String timestamp,
            @RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail ) throws Exception {
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
        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        String requestId = UuidUtil.getRandomUuid();
        //resultMap : 每次操作的返回结果
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            //检查接口公共请求参数
            resultMap = adminUnifiedCheckAndAuth.adminCheckAndAuth(paramsMap, defaultParamsMap);
            if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return constructResponse.getBaseResponse(requestId, resultMap);
            }

            logger.info(String.format("User request to delete IMAGE %s", imageUuid));
            resultMap = adminOperate.AdminAuthorizeImage(paramsMap);
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
}
