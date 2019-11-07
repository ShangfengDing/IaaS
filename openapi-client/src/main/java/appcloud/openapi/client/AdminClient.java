package appcloud.openapi.client;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.profile.DefaultProfile;
import appcloud.core.sdk.profile.YhaiClientProfile;
import appcloud.core.sdk.utils.DefaultYhaiClient;
import appcloud.core.sdk.utils.YhaiClient;
import appcloud.iaas.sdk.admin.*;
import appcloud.openapi.response.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lizhenhao on 2016/11/17.
 */
public class AdminClient {
    public static Logger logger = Logger.getLogger(AdminClient.class);

    public DescribeInstancesResponse AdminDescribeInstances(
            String regionId, String zoneId, String instanceIds, String instanceType,
            String innerIpAddresses, String publicIpAddresses,
            String userId,String hostId, String securityGroupId, String instanceName, String status,
            String imageId, String pageNumber, String pageSize,
            String appkeyId, String appkeySecret, String userEmail) {
        AdminDescribeInstancesRequest request = new AdminDescribeInstancesRequest();
        request.setRegionId(regionId);

        if(!StringUtils.isEmpty(userId)) {
            request.setUserId(userId);
        }
        if(! StringUtils.isEmpty(hostId)) {
            request.setHostId(hostId);
        }
        if (! StringUtils.isEmpty(zoneId)) {
            request.setZoneId(zoneId);
        }
        if (! StringUtils.isEmpty(instanceIds)) {
            request.setInstanceIds(instanceIds);
        }
        if (! StringUtils.isEmpty(instanceType)) {
            request.setInstanceType(instanceType);
        }
        if (! StringUtils.isEmpty(innerIpAddresses)) {
            request.setInnerIpAddresses(innerIpAddresses);
        }
        if (! StringUtils.isEmpty(publicIpAddresses)) {
            request.setPublicIpAddresses(publicIpAddresses);
        }
        if (! StringUtils.isEmpty(securityGroupId)) {
            request.setSecurityGroupId(securityGroupId);
        }
        if (! StringUtils.isEmpty(instanceName)) {
            request.setInstanceName(instanceName);
        }
        if (! StringUtils.isEmpty(status)) {
            request.setStatus(status);
        }
        if (! StringUtils.isEmpty(imageId)) {
            request.setImageId(imageId);
        }
        if (! StringUtils.isEmpty(pageNumber)) {
            request.setPageNumber(pageNumber);
        }
        if (! StringUtils.isEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        if (null != userEmail && userEmail.length() > 0) {
            request.setUserEmail(userEmail);
        }

        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        DescribeInstancesResponse response;

        try {
            response = client.getYhaiResponse(request,DescribeInstancesResponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new DescribeInstancesResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new DescribeInstancesResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }


    public DisksDetailReponse AdminDescribeDisks(
            String regionId,String zoneId,String diskIds,String instanceId,
            String diskType,String diskName,String description,
            String status,String diskAttachStatus,
            String pageNumber,String pageSize,
            String userId,String hostId,
            String appkeyId,String appkeySecret,String userEmail) {

        AdminDescribeDisksRequest request = new AdminDescribeDisksRequest();

        request.setRegionId(regionId);
        if (!StringUtils.isEmpty(zoneId))
            request.setZoneId(zoneId);
        if (!StringUtils.isEmpty(diskIds))
            request.setDiskIds(diskIds);
        if (!StringUtils.isEmpty(instanceId))
            request.setInstanceId(instanceId);
        if (!StringUtils.isEmpty(diskType))
            request.setDiskType(diskType);
        if (!StringUtils.isEmpty(diskName))
            request.setDiskName(diskName);
        if (!StringUtils.isEmpty(description))
            request.setDescription(description);
        if (!StringUtils.isEmpty(status))
            request.setDiskStatus(status);
        if (!StringUtils.isEmpty(diskAttachStatus))
            request.setDiskAttachStatus(diskAttachStatus);
        if (!StringUtils.isEmpty(pageNumber))
            request.setPageNumber(pageNumber);
        if (!StringUtils.isEmpty(pageSize))
            request.setPageSize(pageSize);
        if (!StringUtils.isEmpty(userEmail))
            request.setUserEmail(userEmail);

        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        DisksDetailReponse response = null;
        try {
            response = client.getYhaiResponse(request, DisksDetailReponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new DisksDetailReponse(null, e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new DisksDetailReponse(null, e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }

    public HostsResponse AdminDescribeHosts(
            String regionId, String zoneId, String hostIp, String hostUuid,
            String hostType, String hostStatus,
            String pageNumber, String pageSize,
            String appkeyId, String appkeySecret) {

        AdminDescribeHostsRequest request = new AdminDescribeHostsRequest();

        request.setRegionId(regionId);
        if (!StringUtils.isEmpty(zoneId))
            request.setZoneId(zoneId);
        if (!StringUtils.isEmpty(hostIp))
            request.setHostIp(hostIp);
        if (!StringUtils.isEmpty(hostUuid))
            request.setHostUuid(hostUuid);
        if (!StringUtils.isEmpty(hostType))
            request.setHostType(hostType);
        if (!StringUtils.isEmpty(hostStatus))
            request.setHostStatus(hostStatus);
        if (!StringUtils.isEmpty(pageNumber))
            request.setPageNumber(pageNumber);
        if (!StringUtils.isEmpty(pageSize))
            request.setPageSize(pageSize);

        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        HostsResponse response = null;
        try {
            response = client.getYhaiResponse(request, HostsResponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new HostsResponse(null, e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new HostsResponse(null, e.getErrorCode(), e.getErrorMessage());
        }
        return response;

    }

    public ServicesResponse AdminDescribeServices(
            String regionId, String zoneId, String hostIp, String hostUuids,
            String serviceType, String serviceStatus,
            String pageNumber, String pageSize,
            String appkeyId, String appkeySecret) {

        AdminDescribeServicesRequest request = new AdminDescribeServicesRequest();

        request.setRegionId(regionId);
        if (!StringUtils.isEmpty(zoneId))
            request.setZoneId(zoneId);
        if (!StringUtils.isEmpty(hostIp))
            request.setHostIp(hostIp);
        if (!StringUtils.isEmpty(hostUuids))
            request.setHostUuids(hostUuids);
        if (!StringUtils.isEmpty(serviceType))
            request.setServiceType(serviceType);
        if (!StringUtils.isEmpty(serviceStatus))
            request.setServiceStatus(serviceStatus);
        if (!StringUtils.isEmpty(pageNumber))
            request.setPageNumber(pageNumber);
        if (!StringUtils.isEmpty(pageSize))
            request.setPageSize(pageSize);

        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        ServicesResponse response = null;
        try {
            response = client.getYhaiResponse(request, ServicesResponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new ServicesResponse(null, e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new ServicesResponse(null, e.getErrorCode(), e.getErrorMessage());
        }
        return response;

    }

    /**
     * 虚拟机的动态迁移
     * @param regionId
     * @param zoneId
     * @param hostId
     * @param instanceId
     * @param appkeyId
     * @param appkeySecret
     * @return
     */
    public BaseResponse AdminOnlineMigrate(String regionId, String zoneId,
           String hostId, String instanceId, String appkeyId, String appkeySecret) {
        AdminOnlineMigrateRequest request = new AdminOnlineMigrateRequest();

        request.setRegionId(regionId);
        request.setHostId(hostId);
        request.setInstanceId(instanceId);
        if (!StringUtils.isEmpty(zoneId))
            request.setZoneId(zoneId);

        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        BaseResponse response = null;
        try {
            response = client.getYhaiResponse(request, BaseResponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }

    public InstancesMonitorInfoReponse AdminDescribeInstancesMonitorData(String regionId, String zoneId, String hostId, String instanceIds,
                                                                       String startTime, String endTime, String appkeyId, String appkeySecret){
        AdminDescribeMonitorRequest request = new AdminDescribeMonitorRequest();

        request.setRegionId(regionId);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        if (!StringUtils.isEmpty(zoneId)) {
            request.setZoneId(zoneId);
        }
        if (!StringUtils.isEmpty(instanceIds)) {
            request.setInstanceIds(instanceIds);
        }
        if (!StringUtils.isEmpty(hostId)) {
            request.setHostId(hostId);
        }
        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        InstancesMonitorInfoReponse response = null;
        try {
            response = client.getYhaiResponse(request, InstancesMonitorInfoReponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new InstancesMonitorInfoReponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new InstancesMonitorInfoReponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }

//    public static void main(String args[]) {
//
//        //free账号
//        String appkeyId = "f00577d2c1764688be5e483a21f4fe92";
//        String appkeySecrect = "1KS2AMYX0UEjvQA6sVYK1AXfRpvrtArQ";
//        String regionId = "beijing";
//        String userId = "10";
//        String hostId = "0026B94CDF54";//B82A72DA49EF
//        String instanceId = "9519544265594260890b981d929e0aa7";
//        String mysqlserverId = "b5e91983b8414d0083da396870b4a768";
//        AdminClient adminClient = new AdminClient();
//
//        //开始时间和结束时间
//        String startTime = null;
//        String endTime = null;
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat matterT = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat matterZ = new SimpleDateFormat("HH:mm:ss");
//        String dateT = matterT.format(cal.getTime());
//        String dateZ = matterZ.format(cal.getTime());
//        endTime = dateT+"T"+dateZ+"Z";
//        cal.add(Calendar.HOUR_OF_DAY, -1);
//        dateT = matterT.format(cal.getTime());
//        dateZ = matterZ.format(cal.getTime());
//        startTime = dateT+"T"+dateZ+"Z";
//        //测试管理员查询监控信息
//        InstancesMonitorInfoReponse response = adminClient.AdminDescribeInstancesMonitorData(regionId,null,
//                null,"["+mysqlserverId+"]",startTime,endTime,appkeyId,appkeySecrect);

//
//        HostsResponse hostsResponse = adminClient.AdminDescribeHosts(regionId,null,null,
//                null,null,null,null,null,appkeyId,appkeySecrect);
//
//        ServicesResponse servicesResponse = adminClient.AdminDescribeServices(regionId,null,null,null,null,null,null,null,appkeyId,appkeySecrect);
//        BaseResponse onlineMigrateBaseResponse = adminClient.AdminOnlineMigrate(regionId, null, hostId, instanceId, appkeyId, appkeySecrect );
//        onlineMigrateBaseResponse.getMessage();
//        DescribeInstancesResponse describeInstancesResponse = adminClient.AdminDescribeInstances(
//                regionId, null, null, null,
//                null, null,
//                null,null,null, null,null,
//                null, null, null,
//                 appkeyId,  appkeySecrect, null);
//        DisksDetailReponse describeDisksResponse = adminClient.AdminDescribeDisks(
//                 regionId,null,null,null,
//                null,null,null,
//                null,null,
//                null,null,
//                null,null,
//                 appkeyId, appkeySecrect,null);
//    }
}
