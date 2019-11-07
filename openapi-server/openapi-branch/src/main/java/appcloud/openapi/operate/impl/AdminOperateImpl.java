package appcloud.openapi.operate.impl;

import appcloud.api.beans.Server;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.*;
import appcloud.common.proxy.*;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.UuidUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.constant.ResultConstants;
import appcloud.openapi.datatype.*;
import appcloud.openapi.manager.util.BeansGenerator;
import appcloud.openapi.manager.util.DealException;
import appcloud.openapi.manager.util.LolHelper;
import appcloud.openapi.manager.util.StringUtil;
import appcloud.openapi.operate.AdminOperate;
import appcloud.openapi.query.LolQuery;
import appcloud.openapi.response.*;
import appcloud.rpc.tools.RpcException;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lizhenhao on 2016/11/16.
 */
@Component
public class AdminOperateImpl implements AdminOperate {

    private static Logger logger = Logger.getLogger(AdminOperateImpl.class);
    private static LolLogUtil loller = LolHelper.getLolLogUtil(AdminOperateImpl.class);

    private VmHdEndtimeManager vmHdEndtimeManager;

    private BeansGenerator generator = BeansGenerator.getInstance();

    private VmVirtualInterfaceProxy interfaceProxy;
    private VmInstanceProxy vmInstanceProxy;
    private VmVolumeProxy volumeProxy;
    private ServiceProxy serviceProxy;
    private HostProxy hostProxy;
    private VmUserProxy vmUserProxy;
    private ResourceSchedulerService scheduler;
    private VmImageProxy vmImageProxy;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public AdminOperateImpl() {
        super();
        scheduler = (ResourceSchedulerService) ConnectionFactory
                .getAMQPService(ResourceSchedulerService.class,
                        RoutingKeys.RESOUCE_SCHEDULER);
        vmInstanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
                VmInstanceProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        interfaceProxy = (VmVirtualInterfaceProxy) ConnectionFactory.getTipProxy(
                VmVirtualInterfaceProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(
                VmVolumeProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        serviceProxy = (ServiceProxy) ConnectionFactory.getTipProxy(
                ServiceProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        hostProxy = (HostProxy) ConnectionFactory.getTipProxy(
                HostProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
                VmUserProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmImageProxy = (VmImageProxy) ConnectionFactory.getTipProxy(
                VmImageProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmHdEndtimeManager = new VmHdEndtimeManager();

    }
    @Override
    public DescribeInstancesResponse AdminDescribeInstances(Map<String, String> paramsMap, String requestId) throws Exception {

        String appKeyId = paramsMap.get(Constants.APPKEY_ID);//no use
        String zoneId = paramsMap.get(Constants.ZONE_ID);  //no use
        String regionId = paramsMap.get(Constants.REGION_ID); //no use

        String instanceIds = paramsMap.get(Constants.INSTANCE_IDS);
        String innerIpAddresses = paramsMap.get(Constants.INNER_IP_ADDRESSES);
        String publicIpAddresses = paramsMap.get(Constants.PUBLIC_IP_ADDRESSES);
        String securityGroupId = paramsMap.get(Constants.SECURITY_GROUP_ID);
        String instanceName = paramsMap.get(Constants.INSTANCE_NAME);
        String status = paramsMap.get(Constants.INSTANCE_STATUS);
        String imageId = paramsMap.get(Constants.IMAGE_ID);
        String userId = paramsMap.get(Constants.USER_ID);
        String hostId = paramsMap.get(Constants.HOST_ID);

        Integer page = Integer.parseInt(paramsMap.get(Constants.PAGE_NUMBER));
        Integer size = Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE));


        String logStr = null;
        logStr = String.format("User %s request to search instances detail info", userId);

        List<VmInstance> instances;
        QueryObject<VmInstance> query = new QueryObject<VmInstance>();
        List<String> instanceUuids = new ArrayList<String>();
        logger.info("instanceIds ：" + instanceIds);
        logStr += ", instanceIds:" + instanceIds;
        if (instanceIds != null) {
            JSONArray array = new JSONArray(instanceIds);
            for (int i = 0; i < array.length(); i++) {
                String instanceId = (String) array.get(i);
                instanceUuids.add(instanceId);
            }
        }

        if (userId != null) {
            query.addFilterBean(new FilterBean<VmInstance>("userId", Integer.valueOf(userId), FilterBean.FilterBeanType.EQUAL));
            logStr += ", userId:" + userId;
        }

        if (instanceName != null) {
            query.addFilterBean(new FilterBean<VmInstance>("name", instanceName, FilterBean.FilterBeanType.BOTH_LIKE));
            logStr += ", serverName:" + instanceName;
        }
        if (status != null)
            try {
                query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmInstance.VmStatusEnum.valueOf(status.toUpperCase()), FilterBean.FilterBeanType.EQUAL));
                logStr += ", status:" + status;
            } catch (Exception e) {
                logger.info("status illegal");
                throw new OperationFailedException("staus illegal");
            }
        else
            query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmInstance.VmStatusEnum.DELETED, FilterBean.FilterBeanType.NOTEQUAL));

        //server ip
        if (innerIpAddresses != null) {
            JSONArray array = new JSONArray(innerIpAddresses);
            logStr += ", innerIpAddresses:" + innerIpAddresses;
            Set<String> newInstanceIds = new TreeSet<>();
            for (int i = 0; i < array.length(); i++) {
                List<VmVirtualInterface> vifs = (List<VmVirtualInterface>) interfaceProxy.getByIp(array.getString(i));
                if (vifs.size() == 0) {
                    logger.info("no instance in this ip" + array.getString(i));
                }
                for (VmVirtualInterface vf : vifs) {
                    newInstanceIds.add(vf.getInstanceUuid());
                }
            }

            if (instanceUuids.isEmpty()) {
                instanceUuids.addAll(newInstanceIds);
            } else {
                for (Iterator iterator = instanceUuids.iterator(); iterator.hasNext(); ) {
                    String uuid = (String) iterator.next();
                    if (!newInstanceIds.contains(uuid)) {
                        iterator.remove();
                    }
                }
            }
        }

        if (publicIpAddresses != null) {
            JSONArray array = new JSONArray(publicIpAddresses);
            logStr += ", publicIpAddresses:" + publicIpAddresses;
            Set<String> newInstanceIds = new TreeSet<>();
            for (int i = 0; i < array.length(); i++) {
                List<VmVirtualInterface> vifs = (List<VmVirtualInterface>) interfaceProxy.getByIp(array.getString(i));
                if (vifs.size() == 0) {
                    logger.info("no instance in this ip" + array.getString(i));
                }
                for (VmVirtualInterface vf : vifs) {
                    newInstanceIds.add(vf.getInstanceUuid());
                }
            }

            if (instanceUuids.isEmpty()) {
                instanceUuids.addAll(newInstanceIds);
            } else {
                for (Iterator iterator = instanceUuids.iterator(); iterator.hasNext(); ) {
                    String uuid = (String) iterator.next();
                    if (!newInstanceIds.contains(uuid)) {
                        iterator.remove();
                    }
                }
            }
        }

        if (!instanceUuids.isEmpty()) {
            query.addFilterBean(new FilterBean<VmInstance>("uuid", instanceUuids, FilterBean.FilterBeanType.IN));
            logStr += ", instanceUuids:" + instanceUuids;
        } else if (publicIpAddresses != null || innerIpAddresses != null) {
            return new DescribeInstancesResponse(requestId, null, 0L, page, size);
        }


        if (null != securityGroupId) {
            query.addFilterBean(new FilterBean<VmInstance>("securityGroupId", securityGroupId, FilterBean.FilterBeanType.EQUAL));
            logStr += ", securityGroupId:" + securityGroupId;
        }
        if (null != imageId) {
            query.addFilterBean(new FilterBean<VmInstance>("imageUuid", imageId, FilterBean.FilterBeanType.EQUAL));
            logStr += ", imageId:" + imageId;
        }
        if (null != hostId) {
            query.addFilterBean(new FilterBean<VmInstance>("hostUuid", hostId, FilterBean.FilterBeanType.EQUAL));
            logStr += ", hostUuid:" + hostId;
        }

        logStr += ", page:" + page;
        logStr += ", size:" + size;
        if (page != 0)
            page -= 1;
        logger.info(logStr);
        // with metadata, network
        instances = (List<VmInstance>) vmInstanceProxy.searchAll(query, false, false, false, true, true, false, true, true, page, size);
        Long totalCount = vmInstanceProxy.countSearch(query);

        List<InstanceAttributes> servers = new ArrayList<InstanceAttributes>();

        logger.info(instances.size());

        for (VmInstance instance : instances) {
            InstanceAttributes instanceAttributes = generator.instanceToInstanceAttributes(instance);
            logger.info(instanceAttributes.getInstanceId());
            VmHdEndtime endtime = vmHdEndtimeManager.getVmEndtimeByUuid(instanceAttributes.getInstanceId());
            if (endtime != null) {
                instanceAttributes.setExpiredTime(dateFormat.format(endtime.getEndTime()));
                instanceAttributes.setInstanceChargeType(endtime.getPayType());
            }
            servers.add(instanceAttributes);
        }

        DescribeInstancesResponse describeInstancesResponse = new DescribeInstancesResponse(requestId, servers, totalCount, page + 1, size);
        return describeInstancesResponse;
    }



    @Override
    public DisksDetailReponse AdminDescribeDisks(Map<String, String> paramsMap, String requestId) throws Exception {
        String userId = paramsMap.get(Constants.USER_ID);
        String hostId = paramsMap.get(Constants.HOST_ID);
        String zoneId = paramsMap.get(Constants.ZONE_ID);
        String diskIds = paramsMap.get(Constants.DISK_IDS);
        String instanceId = paramsMap.get(Constants.INSTANCE_ID);
        String diskName = paramsMap.get(Constants.DISK_NAME);
        String description = paramsMap.get(Constants.DESCRIPTION);
        String diskType = paramsMap.get(Constants.DISK_TYPE);
        String diskStatus = paramsMap.get(Constants.DISK_STATUS);
        String diskAttachStatus = paramsMap.get(Constants.DISK_ATTACH_STATUS);
        String pageNum = paramsMap.get(Constants.PAGE_NUMBER);
        String pageSize = paramsMap.get(Constants.PAGE_SIZE);

        List<DiskDetailItem> apiDisks = new ArrayList<DiskDetailItem>();
        QueryObject<VmVolume> query = new QueryObject<VmVolume>();

        if (!StringUtil.isEmpty(userId)) {
            query.addFilterBean(new FilterBean<VmVolume>("userId", userId, FilterBean.FilterBeanType.EQUAL));
        }
        if (!StringUtil.isEmpty(hostId)) {
            query.addFilterBean(new FilterBean<VmVolume>("hostUuid",hostId, FilterBean.FilterBeanType.EQUAL));
        }
        if (!StringUtil.isEmpty(zoneId)) {
            query.addFilterBean(new FilterBean<VmVolume>("availabilityZoneId", zoneId, FilterBean.FilterBeanType.EQUAL));
        }

        if (null != diskIds) {
            List<String> volumeUuids = new ArrayList<String>();
            JSONArray array = new JSONArray(diskIds);
            for (int i = 0; i < array.length(); i++) {
                String diskId = (String) array.get(i);
                volumeUuids.add(diskId);
            }
            if (!volumeUuids.isEmpty()) {
                query.addFilterBean(new FilterBean<VmVolume>("volumeUuid", volumeUuids, FilterBean.FilterBeanType.IN));
            }
        }

        if (!StringUtil.isEmpty(instanceId)) {
            query.addFilterBean(new FilterBean<VmVolume>("instanceUuid", instanceId, FilterBean.FilterBeanType.EQUAL));
        }

        if (!StringUtil.isEmpty(diskName)) {
            query.addFilterBean(new FilterBean<VmVolume>("displayName", diskName, FilterBean.FilterBeanType.BOTH_LIKE));
        }

        if (!StringUtil.isEmpty(description)) {
            query.addFilterBean(new FilterBean<VmVolume>("displayDescription", description, FilterBean.FilterBeanType.EQUAL));
        }

        if (!StringUtil.isEmpty(diskType)) {
            query.addFilterBean(new FilterBean<VmVolume>("usageType", VmVolume.VmVolumeUsageTypeEnum.valueOf(diskType.toUpperCase()), FilterBean.FilterBeanType.EQUAL));
        }

        if (!StringUtil.isEmpty(diskStatus)) {
            query.addFilterBean(new FilterBean<VmVolume>("status", VmVolume.VmVolumeStatusEnum.valueOf(diskStatus.toUpperCase()), FilterBean.FilterBeanType.EQUAL));
        }

        if (!StringUtil.isEmpty(diskAttachStatus)) {
            query.addFilterBean(new FilterBean<VmVolume>("attachStatus", VmVolume.VmVolumeAttachStatusEnum.valueOf(diskAttachStatus.toUpperCase()), FilterBean.FilterBeanType.EQUAL));
        }

        int page = 0;
        if (StringUtil.isNumeric(pageNum)) {
            page = Integer.parseInt(pageNum);
            if(page > 0)
                page -= 1;
        }

        int size =10;
        if (StringUtil.isNumeric(pageSize)) {
            size = Integer.parseInt(pageSize);
        }

        List<? extends VmVolume> vmVolumes = volumeProxy.searchAll(query, false, false, false, false, page, size);

        for(VmVolume vmVolume : vmVolumes) {
            DiskDetailItem diskDetailItem = generator.volumeToDiskDetailItem(vmVolume);
            VmHdEndtime hdEndtime = vmHdEndtimeManager.getHdEndtimeByUuid(vmVolume.getVolumeUuid());
            if (hdEndtime != null) {
                diskDetailItem.setChargeType(hdEndtime.getPayType());
                diskDetailItem.setExpiredTime(dateFormat.format(hdEndtime.getEndTime()));
            } else {
                logger.warn("can't find hdEndTime with volumeUuis:"+vmVolume.getVolumeUuid());
            }
            apiDisks.add(diskDetailItem);
        }
        logger.info("disk size "+apiDisks.size());

        long totalCount = volumeProxy.countSearch(query);

        DisksDetailReponse disksDetailReponse = new DisksDetailReponse(requestId, apiDisks,
                totalCount, pageNum, String.valueOf(size));
        return disksDetailReponse;
    }

    @Override
    public ServicesResponse AdminDescribeServices(Map<String, String> paramsMap, String requestId) throws Exception {
        String hostIp = paramsMap.get(Constants.HOST_IP);
        String hostUuids = paramsMap.get(Constants.HOST_IDS);
        String serverType = paramsMap.get(Constants.SERVICE_TYPE);
        String serverStatus = paramsMap.get(Constants.SERVICE_STATUS);
        String pageNum = paramsMap.get(Constants.PAGE_NUMBER); //no use
        String pageSize = paramsMap.get(Constants.PAGE_SIZE); //no use

        QueryObject<Service> query = new QueryObject<>();


        if(!StringUtil.isEmpty(hostIp)) {
            query.addFilterBean(new FilterBean<Service>("hostIp",hostIp, FilterBean.FilterBeanType.EQUAL));
        }
        if(!StringUtil.isEmpty(serverType)) {
            query.addFilterBean(new FilterBean<Service>("type",Service.ServiceTypeEnum.valueOf(serverType.toUpperCase()), FilterBean.FilterBeanType.EQUAL));
        }
        if(!StringUtil.isEmpty(serverStatus)) {
            query.addFilterBean(new FilterBean<Service>("serviceStatus",Service.ServiceStatus.valueOf(serverStatus.toUpperCase()),FilterBean.FilterBeanType.EQUAL));
        }
        if(null != hostUuids) {
            List<String> hosts = new ArrayList<String>();
            JSONArray array = new JSONArray(hostUuids);
            for (int i = 0; i < array.length(); i++) {
                String hostUuid = (String) array.get(i);
                hosts.add(hostUuid);
            }
            if (!hosts.isEmpty()) {
                query.addFilterBean(new FilterBean<Service>("hostUuid", hosts, FilterBean.FilterBeanType.IN));
            }
        }

        List<? extends Service> services = serviceProxy.searchAll(query, false);
        List<ServiceDetailItem> newServices = new ArrayList<>();
        for(Service service: services) {
            ServiceDetailItem serviceDetailItem = generator.serviceToServiceDetailItem(service);
            newServices.add(serviceDetailItem);
        }
        ServicesResponse servicesResponse = new ServicesResponse(newServices,0,requestId,null,null);
        return servicesResponse;
    }

    @Override
    public HostsResponse AdminDescribeHosts(Map<String, String> paramsMap, String requestId) throws Exception {
        String hostIp = paramsMap.get(Constants.HOST_IP);
        String hostUuid = paramsMap.get(Constants.HOST_UUID);
        String hostType = paramsMap.get(Constants.HOST_TYPE);
        String hostStatus = paramsMap.get(Constants.HOST_STATUS);
        String pageNum = paramsMap.get(Constants.PAGE_NUMBER); //no use
        String pageSize = paramsMap.get(Constants.PAGE_SIZE); //no use

        QueryObject<Host> query = new QueryObject<>();


        if(!StringUtil.isEmpty(hostIp)) {
            query.addFilterBean(new FilterBean<Host>("hostIp",hostIp, FilterBean.FilterBeanType.EQUAL));
        }
        if(!StringUtil.isEmpty(hostType)) {
            query.addFilterBean(new FilterBean<Host>("type",Host.HostTypeEnum.valueOf(hostType.toUpperCase()), FilterBean.FilterBeanType.EQUAL));
        }
        if(!StringUtil.isEmpty(hostStatus)) {
            query.addFilterBean(new FilterBean<Host>("serviceStatus",Host.HostStatusEnum.valueOf(hostStatus.toUpperCase()),FilterBean.FilterBeanType.EQUAL));
        }
        if(null != hostUuid) {
            query.addFilterBean(new FilterBean<Host>("hostUuid", hostUuid, FilterBean.FilterBeanType.EQUAL));
        }

        int page = 0;
        if (StringUtil.isNumeric(pageNum)) {
            page = Integer.parseInt(pageNum);
            if(page > 0)
                page -= 1;
        }

        int size =10;
        if (StringUtil.isNumeric(pageSize)) {
            size = Integer.parseInt(pageSize);
        }

        List<? extends Host> Hosts = hostProxy.searchAll(query,false,false,false,page,size);
        long totalCount = hostProxy.countSearch(query);

        List<HostDetailItem> newHosts = new ArrayList<>();
        for(Host host: Hosts) {
            HostDetailItem hostDetailItem = generator.hostToHostDetailItem(host);
            newHosts.add(hostDetailItem);
        }
        HostsResponse hostsResponse = new HostsResponse(newHosts,totalCount,requestId,String.valueOf(page),String.valueOf(size));
        return hostsResponse;
    }

    @Override
    public Map<String, String> AdminOnlineMigrate(Map<String, String> paramsMap, String requestId) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        if(null==requestId || requestId.length()<1) {
            logger.info("RequestId is null or is error!");
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
            return resultMap;
        }
        String adminUserId = vmUserProxy.getByAppKeyId(
                paramsMap.get(Constants.APPKEY_ID)).getUserId()
                + "";
//        String userId = paramsMap.get(Constants.USER_ID);
        String hostId = paramsMap.get(Constants.HOST_ID);
        String instanceId = paramsMap.get(Constants.INSTANCE_ID);
        String message = String.format("admin %s request online migrate vm %s", adminUserId, instanceId);
        logger.info(message);
        RpcExtra rpcExtra = new RpcExtra(requestId, adminUserId, instanceId);
        loller.info(LolLogUtil.START_VM, message, rpcExtra);
        try {
            scheduler.onlineMigrateVM(instanceId, hostId, rpcExtra);
        } catch(RpcException e) {
            DealException.isRSTimeoutException(e, LolLogUtil.ONLINE_MIGRATION, rpcExtra);
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
            return resultMap;
        }
        logger.info(String.format("SERVER %s migrate to HOST %s online successfully", instanceId, hostId));

        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
        return resultMap;
    }

    @Override
    public Map<String, Object>  AdminDescribeMonitorData(
            Map<String, String> paramsMap, String requestId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if(null==requestId || requestId.length()<1) {
            logger.info("RequestId is null or is error!");
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
            return resultMap;
        }
        String hostId = paramsMap.get(Constants.HOST_ID);
        String instanceIds = paramsMap.get(Constants.INSTANCE_IDS);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date st = dateFormat.parse(paramsMap.get(Constants.START_TIME));
        Date et = dateFormat.parse(paramsMap.get(Constants.END_TIME));
        String btime = st.getTime()+ "";
        String etime = et.getTime() + "";

        List<InstanceMonitorDataType> loads = new ArrayList<InstanceMonitorDataType>();
        List<String> instanceUuids = new ArrayList<String>();
        if (instanceIds!=null) {
            JSONArray array = new JSONArray(instanceIds);
            for (int i = 0; i < array.length(); i++) {
                String instanceId = (String) array.get(i);
                logger.info(instanceId);
                Map<String,String> queryMap = new HashMap<>();
                queryMap.put("btime",btime);
                queryMap.put("etime",etime);
                queryMap.put("timeasc","no");
                queryMap.put("vm_uuid",instanceId);
                queryMap.put("size","5");
                String jsonString = LolQuery.QueryLog(queryMap);
                logger.info(jsonString);
                JSONObject jsonObject = new JSONObject(jsonString.substring(1,jsonString.length()-1)); //得到json数据
                JSONArray queryResult = jsonObject.getJSONArray("result");
                for (int index=0; index<queryResult.length();index++) {
                    loads.add(generator.adminInstanceMonitorData(queryResult.get(index).toString()));
                }
            }
        } else if (hostId!=null) {
            QueryObject<VmInstance> query = new QueryObject<>();
            query.addFilterBean(new FilterBean<VmInstance>("hostUuid",hostId, FilterBean.FilterBeanType.EQUAL));
            List<? extends VmInstance> vmInstances = vmInstanceProxy.searchAll(query,false,
                    false,false,false,false,false,false,false);
            for (VmInstance vmInstance:vmInstances){
                String instanceId = vmInstance.getUuid();
                logger.info(instanceId);
                Map<String,String> queryMap = new HashMap<>();
                queryMap.put("btime",btime);
                queryMap.put("etime",etime);
                queryMap.put("timeasc","no");
                queryMap.put("vm_uuid",instanceId);
                queryMap.put("size","1");
                String jsonString = LolQuery.QueryLog(queryMap);
                logger.info(jsonString);
                JSONObject jsonObject = new JSONObject(jsonString.substring(1,jsonString.length()-1)); //得到json数据
                JSONArray queryResult = jsonObject.getJSONArray("result");
                for (int index=0; index<queryResult.length();index++) {
                    loads.add(generator.adminInstanceMonitorData(queryResult.get(index).toString()));
                }
            }
        } else {
            logger.info("instanceIds和hostId同时为空");
            resultMap.put(ResultConstants.INSTANCE_MONITOR_SET, loads);
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_BAD_REQUEST+"");
            return resultMap;
        }

        resultMap.put(ResultConstants.INSTANCE_MONITOR_SET, loads);
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
        return resultMap;
    }


    @Override
    public Map<String, String> AdminAuthorizeImage(Map<String, String> paramsMap) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        VmImage imageToDel = vmImageProxy.getByUuid(paramsMap.get(Constants.IMAGE_UUID));
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));

        if (imageToDel.getType().equals(Constants.IMAGE_PUBLIC)) {
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"update the image failed.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
            return resultMap;
        }

        String uuid = UuidUtil.getRandomUuid();
        imageToDel.setUuid(uuid);
        imageToDel.setUserId(user.getUserId());
        imageToDel.setId(null);  // 为null的情况下，才会自动生成id
        imageToDel.setType(Constants.IMAGE_PUBLIC);
        try {
            vmImageProxy.save(imageToDel);
            logger.info(String.format("authorize the image for all of the group", uuid));
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK+"");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"update the image failed.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
        }

        return resultMap;
    }
}

