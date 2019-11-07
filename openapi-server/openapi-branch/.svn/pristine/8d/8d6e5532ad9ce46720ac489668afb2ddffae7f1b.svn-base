package appcloud.openapi.check.impl;

import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.*;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.AdminParamsCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;
import appcloud.openapi.manager.util.StringUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by lizhenhao on 2016/11/16.
 */
public class AdminParamsCheckImpl implements AdminParamsCheck{

    private static Logger logger = Logger.getLogger(AdminParamsCheckImpl.class);

    private static AdminParamsCheckImpl adminParamsCheck = new AdminParamsCheckImpl();
    private static CommonGenerator commonGenerator  = new CommonGenerator();

    private static VmZoneProxy vmZoneProxy;
    private static VmUserProxy vmUserProxy;
    private static VmInstanceProxy vmInstanceProxy;
    private static HostProxy hostProxy;
    private static ServiceProxy serviceProxy;
    private static VmSecurityGroupProxy vmSecurityGroupProxy;
    private static VmImageProxy vmImageProxy;

    public static AdminParamsCheckImpl getInstance() {
        return adminParamsCheck;
    }

    private AdminParamsCheckImpl() {
        vmZoneProxy = (VmZoneProxy) ConnectionFactory.getTipProxy(
                VmZoneProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(
                VmUserProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        serviceProxy = (ServiceProxy) ConnectionFactory.getTipProxy(
                ServiceProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmSecurityGroupProxy = (VmSecurityGroupProxy) ConnectionFactory.getTipProxy(
                VmSecurityGroupProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmInstanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
                VmInstanceProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        hostProxy = (HostProxy) ConnectionFactory.getTipProxy(
                HostProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmImageProxy = (VmImageProxy) ConnectionFactory.getTipProxy(VmImageProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    }

    @Override
    public Map<String, String> checkAdminVolumeDescribeParams(Map<String, String> paramsMap) throws Exception {
        if (null == paramsMap.get(Constants.REGION_ID)) {
            return commonGenerator.missing(null, Constants.REGION_ID);
        } else {
            if (paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
                    null == vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
                return commonGenerator.inValid(null, Constants.REGION_ID);
            }
        }

        if (null != paramsMap.get(Constants.ZONE_ID)) {
            if (paramsMap.get(Constants.ZONE_ID).equalsIgnoreCase("") ||
                    null == vmZoneProxy.getByZoneId(paramsMap.get(Constants.ZONE_ID))) {
                return commonGenerator.inValid(null, Constants.ZONE_ID);
            }
        }

        if (null != paramsMap.get(Constants.USER_ID)) {
            if (paramsMap.get(Constants.USER_ID).equalsIgnoreCase("") ||
                    null == vmUserProxy.getByUserId(Integer.parseInt(paramsMap.get(Constants.USER_ID)))) {
                return commonGenerator.inValid(null, Constants.ZONE_ID);
            }
        }

        if (null != paramsMap.get(Constants.HOST_ID)) {
            if (paramsMap.get(Constants.HOST_ID).equalsIgnoreCase("")||
                    null == serviceProxy.getHostServicesByUuid(paramsMap.get(Constants.HOST_ID))) {
                return commonGenerator.inValid(null,Constants.HOST_ID);
            }
        }

        if(!StringUtil.isEmpty(paramsMap.get(Constants.DISK_IDS))) {
            try {
                JSONArray array = new JSONArray(paramsMap.get(Constants.DISK_IDS));
                for (int i = 0; i < array.length(); i++) {
                    String diskId = (String) array.get(i);
                    if (!StringUtil.isUUID(diskId))
                        return commonGenerator.inValid("diskId="+diskId+" not valid", Constants.DISK_IDS);
                }
            } catch (JSONException e) {
                logger.warn("json format is wrong!",e);
                return commonGenerator.inValid(null, Constants.DISK_IDS);
            }
        }

        if (!StringUtil.isEmpty(paramsMap.get(Constants.INSTANCE_ID)) &&
                !StringUtil.isUUID(paramsMap.get(Constants.INSTANCE_ID)))
            return commonGenerator.inValid(null, Constants.INSTANCE_ID);

        if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_TYPE))) {
            try {
                VmVolume.VmVolumeUsageTypeEnum.valueOf(paramsMap.get(Constants.DISK_TYPE).toUpperCase());
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.DISK_TYPE);
            }
        }

        if (!StringUtil.isEmpty(paramsMap.get(Constants.DESCRIPTION)) &&
                !StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DESCRIPTION),
                        Constants.MIN_DESCRIPTION, Constants.MAX_DESCRIPTION))
            return commonGenerator.inValid(null, Constants.DESCRIPTION);

        if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_NAME)) &&
                !StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DISK_NAME),
                        Constants.MIN_NAME, Constants.MAX_NAME))
            return commonGenerator.inValid(null, Constants.DISK_NAME);

        if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_STATUS))) {
            try {
                VmVolume.VmVolumeStatusEnum.valueOf(paramsMap.get(Constants.DISK_STATUS));
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.DISK_STATUS);
            }
        }

        if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_ATTACH_STATUS))) {
            try {
                VmVolume.VmVolumeAttachStatusEnum.valueOf(paramsMap.get(Constants.DISK_ATTACH_STATUS));
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.DISK_ATTACH_STATUS);
            }
        }

        if (!StringUtil.isEmpty(paramsMap.get(Constants.PAGE_NUMBER))) {
            if(!StringUtil.isNumeric(paramsMap.get(Constants.PAGE_NUMBER))) {
                return commonGenerator.inValid(null, Constants.PAGE_NUMBER);
            }

            if(Integer.parseInt(paramsMap.get(Constants.PAGE_NUMBER)) < 1) {
                return commonGenerator.inValid(null, Constants.PAGE_NUMBER);
            }
        }

        if (!StringUtil.isEmpty(paramsMap.get(Constants.PAGE_SIZE))) {
            if(!StringUtil.isNumeric(paramsMap.get(Constants.PAGE_SIZE))) {
                return commonGenerator.inValid(null, Constants.PAGE_SIZE);
            }

            if(Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)) < 1) {
                return commonGenerator.inValid(null, Constants.PAGE_NUMBER);
            }

        }

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        logger.info("Check Describe Disks params successfully!");
        return resultMap;
    }


    @Override
    public Map<String, String> checkServicesParams(Map<String, String> paramsMap) throws Exception {
        if(null == paramsMap.get(Constants.REGION_ID)) {
            return commonGenerator.missing(null, Constants.REGION_ID);
        }else {
            if(paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
                return commonGenerator.inValid(null, Constants.REGION_ID);
            }
        }
        if(null!=paramsMap.get(Constants.ZONE_ID)) {
            if(paramsMap.get(Constants.ZONE_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByZoneId(paramsMap.get(Constants.ZONE_ID))) {
                return commonGenerator.inValid(null, Constants.ZONE_ID);
            }
        }
        if(null!=paramsMap.get(Constants.HOST_IDS)) {
            try {
                JSONArray array = new JSONArray(paramsMap.get(Constants.HOST_IDS));
                for (int i = 0; i < array.length(); i++) {
                    String hostId = (String) array.get(i);
                    if (!Pattern.matches("\\w{12}",paramsMap.get(Constants.HOST_IDS)))
                        return commonGenerator.inValid("hostUuid="+hostId+" not valid", Constants.HOST_IDS);
                }
            } catch (JSONException e) {
                logger.warn("json format is wrong!",e);
                return commonGenerator.inValid(null, Constants.INSTANCE_IDS);
            }
        }

        if(null!=paramsMap.get(Constants.SERVICE_STATUS)) {
            try {
                Service.ServiceStatus.valueOf(paramsMap.get(Constants.SERVICE_STATUS));
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.SERVICE_STATUS);
            }
        }
        if(null!=paramsMap.get(Constants.SERVICE_TYPE)) {
            try {
                Service.ServiceTypeEnum.valueOf(paramsMap.get(Constants.SERVICE_TYPE));
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.SERVICE_TYPE);
            }
        }

        logger.info("admin describe instances info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;


    }

    @Override
    public Map<String, String> checkAdminInstanceDescribeParams(Map<String, String> paramsMap) throws Exception {
        if(null == paramsMap.get(Constants.REGION_ID)) {
            return commonGenerator.missing(null, Constants.REGION_ID);
        }else {
            if(paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
                return commonGenerator.inValid(null, Constants.REGION_ID);
            }
        }
        if(null!=paramsMap.get(Constants.ZONE_ID)) {
            if(paramsMap.get(Constants.ZONE_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByZoneId(paramsMap.get(Constants.ZONE_ID))) {
                return commonGenerator.inValid(null, Constants.ZONE_ID);
            }
        }
        if(null!=paramsMap.get(Constants.INSTANCE_IDS)) {
            try {
                JSONArray array = new JSONArray(paramsMap.get(Constants.INSTANCE_IDS));
                for (int i = 0; i < array.length(); i++) {
                    String instanceId = (String) array.get(i);
                    if (!StringUtil.isUUID(instanceId))
                        return commonGenerator.inValid("instanceId="+instanceId+" not valid", Constants.INSTANCE_IDS);
                }
            } catch (JSONException e) {
                logger.warn("json format is wrong!",e);
                return commonGenerator.inValid(null, Constants.INSTANCE_IDS);
            }
        }
        if(null!=paramsMap.get(Constants.INNER_IP_ADDRESSES)) {
            try {
                JSONArray array = new JSONArray(paramsMap.get(Constants.INNER_IP_ADDRESSES));
                for (int i = 0; i < array.length(); i++) {
                    String ip = (String) array.get(i);
                    if (!StringUtil.isIpv4(ip))
                        return commonGenerator.inValid("ip="+ip+" not valid", Constants.INNER_IP_ADDRESSES);
                }
            } catch (JSONException e) {
                logger.warn("json format is wrong!",e);
                return commonGenerator.inValid(null, Constants.INNER_IP_ADDRESSES);
            }
        }
        if(null!=paramsMap.get(Constants.PUBLIC_IP_ADDRESSES)) {
            try {
                JSONArray array = new JSONArray(paramsMap.get(Constants.PUBLIC_IP_ADDRESSES));
                for (int i = 0; i < array.length(); i++) {
                    String ip = (String) array.get(i);
                    if (!StringUtil.isIpv4(ip))
                        return commonGenerator.inValid("ip="+ip+" not valid", Constants.PUBLIC_IP_ADDRESSES);
                }
            } catch (JSONException e) {
                logger.warn("json format is wrong!",e);
                return commonGenerator.inValid(null, Constants.PUBLIC_IP_ADDRESSES);
            }
        }
        if(null!=paramsMap.get(Constants.SECURITY_GROUP_ID)) {
            if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.SECURITY_GROUP_ID)) ){
                return commonGenerator.inValid(null, Constants.SECURITY_GROUP_ID);
            }else if(null==vmSecurityGroupProxy.getByUuid(paramsMap.get(Constants.SECURITY_GROUP_ID), false)){
                return commonGenerator.notFound(null, Constants.SECURITY_GROUP_ID);
            }
        }
        if(null!=paramsMap.get(Constants.INSTANCE_NAME) &&
                !Pattern.matches("((\\w|[\u4e00-\u9fa5])+(\\.|_|-|[\u4e00-\u9fa5])?){"+Constants.MIN_NAME+","+
                        Constants.MAX_NAME + "}", paramsMap.get(Constants.INSTANCE_NAME))) {
            return commonGenerator.inValid(null, Constants.INSTANCE_NAME);
        }
        if(null!=paramsMap.get(Constants.INSTANCE_STATUS)) {
            try {
                VmInstance.VmStatusEnum.valueOf(paramsMap.get(Constants.INSTANCE_STATUS));
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.INSTANCE_STATUS);
            }
        }
        if(null!=paramsMap.get(Constants.IMAGE_ID)) {
            if(!Pattern.matches("\\w{32}", paramsMap.get(Constants.IMAGE_ID))) {
                return commonGenerator.inValid(null, Constants.IMAGE_ID);
            }
        }

        if(null!=paramsMap.get(Constants.USER_ID)) {
            if(paramsMap.get(Constants.USER_ID).equalsIgnoreCase("") ||
                    null==vmUserProxy.getByUserId(Integer.parseInt(paramsMap.get(Constants.USER_ID)))) {
                return commonGenerator.inValid(null, Constants.USER_ID);
            }
        }
        if(null!=paramsMap.get(Constants.HOST_ID)) {
            if(paramsMap.get(Constants.HOST_ID).equalsIgnoreCase("") ||
                    null==serviceProxy.getHostServicesByUuid(paramsMap.get(Constants.HOST_ID))) {
                return commonGenerator.inValid(null, Constants.HOST_ID);
            }
        }
        logger.info("admin describe instances info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;

    }

    @Override
    public Map<String, String> checkAdminHostsParams(Map<String, String> paramsMap) throws Exception {
        if(null == paramsMap.get(Constants.REGION_ID)) {
            return commonGenerator.missing(null, Constants.REGION_ID);
        }else {
            if(paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
                return commonGenerator.inValid(null, Constants.REGION_ID);
            }
        }
        if(null!=paramsMap.get(Constants.ZONE_ID)) {
            if(paramsMap.get(Constants.ZONE_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByZoneId(paramsMap.get(Constants.ZONE_ID))) {
                return commonGenerator.inValid(null, Constants.ZONE_ID);
            }
        }
        if(null!=paramsMap.get(Constants.HOST_UUID)) {
            if (!Pattern.matches("\\w{12}",paramsMap.get(Constants.HOST_UUID)))
                return commonGenerator.inValid("hostUuid="+paramsMap.get(Constants.HOST_UUID)+" not valid", Constants.HOST_UUID);
        }
        if(null != paramsMap.get(Constants.HOST_IP)) {
            if (!StringUtil.isIpv4(paramsMap.get(Constants.HOST_IP))) {
                logger.info("ip is wrong");
                return commonGenerator.inValid("ip is not valid", Constants.HOST_IP);
            }
        }

        if(null!=paramsMap.get(Constants.HOST_STATUS)) {
            try {
                Host.HostStatusEnum.valueOf(paramsMap.get(Constants.HOST_STATUS));
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.HOST_STATUS);
            }
        }
        if(null!=paramsMap.get(Constants.HOST_TYPE)) {
            try {
                Host.HostTypeEnum.valueOf(paramsMap.get(Constants.HOST_TYPE));
            } catch (Exception e) {
                return commonGenerator.inValid(null, Constants.HOST_TYPE);
            }
        }

        logger.info("admin describe instances info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;

    }

    @Override
    public Map<String, String> checkAdminOnlineMigrateParams(Map<String, String> paramsMap) throws Exception {
        if(null == paramsMap.get(Constants.REGION_ID)) {
            return commonGenerator.missing(null, Constants.REGION_ID);
        }else {
            if(paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
                return commonGenerator.inValid(null, Constants.REGION_ID);
            }
        }
        //物理机hostId检查
        if(null == paramsMap.get(Constants.HOST_ID)) {
            return commonGenerator.missing(null, Constants.HOST_ID);
        }else {
            if(paramsMap.get(Constants.HOST_ID).equalsIgnoreCase("") ||
                    null==hostProxy.getByUuid(paramsMap.get(Constants.HOST_ID),false,false,false)) {
                return commonGenerator.inValid(null, Constants.HOST_ID);
            }
        }
        //instanceId检查
        if(null == paramsMap.get(Constants.INSTANCE_ID)) {
            return commonGenerator.missing(null, Constants.INSTANCE_ID);
        }else {
            if(paramsMap.get(Constants.INSTANCE_ID).equalsIgnoreCase("") ||
                    null==vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID),
                            false, false, false, false, false, false, false, false)) {
                return commonGenerator.inValid(null, Constants.INSTANCE_ID);
            }
        }
        if(null!=paramsMap.get(Constants.ZONE_ID)) {
            if(paramsMap.get(Constants.ZONE_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByZoneId(paramsMap.get(Constants.ZONE_ID))) {
                return commonGenerator.inValid(null, Constants.ZONE_ID);
            }
        }
        logger.info("admin describe instances info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;

    }

    @Override
    public Map<String, String> checkAdminDescribeMonitorDataParams(Map<String, String> paramsMap) throws Exception {
        if(null == paramsMap.get(Constants.REGION_ID)) {
            return commonGenerator.missing(null, Constants.REGION_ID);
        }else {
            if(paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
                return commonGenerator.inValid(null, Constants.REGION_ID);
            }
        }
        if(null!=paramsMap.get(Constants.ZONE_ID)) {
            if(paramsMap.get(Constants.ZONE_ID).equalsIgnoreCase("") ||
                    null==vmZoneProxy.getByZoneId(paramsMap.get(Constants.ZONE_ID))) {
                return commonGenerator.inValid(null, Constants.ZONE_ID);
            }
        }
        //物理机hostId检查
        if(null == paramsMap.get(Constants.HOST_ID)) {
            return commonGenerator.missing(null, Constants.HOST_ID);
        }else {
            //TODO hostMac检查
            if(paramsMap.get(Constants.HOST_ID).equalsIgnoreCase("") ||
                    null==hostProxy.getByUuid(paramsMap.get(Constants.HOST_ID),false,false,false)) {
                return commonGenerator.inValid(null, Constants.HOST_ID);
            }
        }
        //instanceIds检查
        if(null!=paramsMap.get(Constants.INSTANCE_IDS)) {
            try {
                JSONArray array = new JSONArray(paramsMap.get(Constants.INSTANCE_IDS));
                for (int i = 0; i < array.length(); i++) {
                    String instanceId = (String) array.get(i);
                    if (!StringUtil.isUUID(instanceId))
                        return commonGenerator.inValid("instanceId="+instanceId+" not valid", Constants.INSTANCE_IDS);
                }
            } catch (JSONException e) {
                logger.warn("json format is wrong!",e);
                return commonGenerator.inValid(null, Constants.INSTANCE_IDS);
            }
        }

        if(null==paramsMap.get(Constants.START_TIME)) {
            return commonGenerator.missing(null, Constants.START_TIME);
        }
        if(null==paramsMap.get(Constants.END_TIME)) {
            return commonGenerator.missing(null, Constants.END_TIME);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            dateFormat.parse(paramsMap.get(Constants.START_TIME));
        } catch (Exception e) {
            return commonGenerator.inValid(null, Constants.START_TIME);
        }
        try {
            dateFormat.parse(paramsMap.get(Constants.END_TIME));
        } catch (Exception e) {
            return commonGenerator.inValid(null, Constants.END_TIME);
        }

        logger.info("admin describe instances info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkAdminAuthorizeImageParams(Map<String, String> paramsMap) throws Exception {
        //物理机imageUuid检查
        if(null == paramsMap.get(Constants.IMAGE_UUID)) {
            return commonGenerator.missing(null, Constants.IMAGE_UUID);
        }else {
            //TODO hostMac检查
            if(paramsMap.get(Constants.IMAGE_UUID).equalsIgnoreCase("") ||
                    null==vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_UUID))) {
                return commonGenerator.inValid(null, Constants.IMAGE_UUID);
            }
        }

        logger.info("admin describe instances info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }
}
