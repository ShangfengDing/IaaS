package com.appcloud.vm.action.vm.newvm;

import aliyun.openapi.client.*;
import appcloud.openapi.response.*;
import com.aliyuncs.ecs.model.v20140526.*;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceTypesResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeRegionsResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeZonesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.action.vm.VmInfoCache;
import com.appcloud.vm.common.AliYunInstanceSourceCompareTable;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.entity.AliYunAvailableResource;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.ArrayUtil;
import com.appcloud.vm.util.CollectionUtil;
import com.appcloud.vm.util.ResultUtils;
import com.appcloud.vm.util.StringUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.*;
import java.util.concurrent.*;

/**
 * 申请阿里云主机之前的Action，可以返回阿里云所有能配置的参数，进行设置。
 *
 * @author 包鑫彤
 * @create 2016-07-20-11:21
 * @since 1.0.0
 */
public class PreNewAliYunVmAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(PreNewAliYunVmAction.class);
    private static final String PROVIDER = Constants.ALI_YUN;
    private AliRegionClient aliRegionClient = OpenClientFactory.getAliRegionClient();
    private Integer userId = this.getSessionUserID();
    private String appname;
    private AppkeyManager appkeyManager = new AppkeyManager();
    private Appkey appkeyMan;
    private String appkeyId;
    private String appkeySecret;
    private String userEmail;
    private String regionId = null;
//    private VmInfoCache cache = VmInfoCache.getCacheInstance();

    //异步线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private List<FutureTask<Object>> futureTasks = new ArrayList<>();
    private ExecutorService executorServiceResource = Executors.newFixedThreadPool(3);
    private List<FutureTask<Object>> futureTasksResource = new ArrayList<>();
    private List<DescribeRegionsResponse.Region> regions;//获取区域信息
    private List<DescribeZonesResponse.Zone> zoneList = new ArrayList<>();
    private Map<String, Object> imageInfo = new HashMap<>();
    private List<Map<String, String>> securityGroupList = new ArrayList<>();//需要一个新的线程池
    private List<Map<String, Object>> availableResourceList = new ArrayList<>();

    public String AliInfo() {
        appkeyMan = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        appkeyId = appkeyMan.getAppkeyId();
        appkeySecret = appkeyMan.getAppkeySecret();
        userEmail = appkeyMan.getUserEmail();
        regions = getRegions();
        if (CollectionUtil.isNotEmpty(regions) && StringUtil.isEmpty(regionId)) {
            regionId = regions.get(0).getRegionId();
        }
        FutureTask<Object> futureTaskGetZones = (FutureTask<Object>) executorService.submit(new getZones(regionId));
        futureTasks.add(futureTaskGetZones);
        FutureTask<Object> futureTaskGetSecurity = (FutureTask<Object>) executorService.submit(new getSecurityGroupId(regionId));
        futureTasks.add(futureTaskGetSecurity);
        FutureTask<Object> futureTaskGetImages = (FutureTask<Object>) executorService.submit(new getImages(regionId));
        futureTasks.add(futureTaskGetImages);
        for (FutureTask futureTask:futureTasks) {
            try {
                futureTask.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (DescribeZonesResponse.Zone zone : zoneList) {
            FutureTask<Object> futureTaskGetRsource = (FutureTask<Object>) executorServiceResource.submit(new getAvailableResources(zone));
            futureTasksResource.add(futureTaskGetRsource);
        }

        for (FutureTask futureTaskRes:futureTasksResource) {
            try {
                futureTaskRes.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("regionList", regions);
        result.put("securityGroupList", securityGroupList);
        result.put("zoneList", zoneList);
        result.put("imageInfo", imageInfo);
        result.put("availableResourceList", availableResourceList);
        ResultUtils.toJson(ServletActionContext.getResponse(), result);
        return null;
    }


    /**
     * 获取阿里云ECS的地域列表
     *
     * @return 返回阿里云下所有的地域列表
     */
    private List<DescribeRegionsResponse.Region> getRegions() {
        AliRegionClient aliRegionClient = OpenClientFactory.getAliRegionClient();
        DescribeRegionsResponse regionsResponse = aliRegionClient.DescribeRegions(appkeyId, appkeySecret, userEmail);
        List<DescribeRegionsResponse.Region> regions = regionsResponse.getRegions();
        if (CollectionUtil.isNotEmpty(regions)) {
            logger.info("获取阿里云ECS Region 列表成功");
            for (DescribeRegionsResponse.Region region : regions) {
                logger.info("regionName = " + region.getLocalName() + "regionId = " + region.getRegionId());
            }
            return regions;
        } else {
            logger.info("阿里云ECS没有可有地域");
        }
        return null;

    }

    /**
     * 根据提供的regionId获取可用zone列表
     *
     * @return 返回该地域下的所有的Zone 列表
     */
    class getZones implements Callable<Object> {
        private String regionId;

        public getZones(String regionId) {
            this.regionId = regionId;
        }

        public Object call() throws Exception {
            DescribeZonesResponse zonesResponse = aliRegionClient.DescribeZones(regionId, appkeyId, appkeySecret, userEmail);
            List<DescribeZonesResponse.Zone> zones = zonesResponse.getZones();
            logger.info(ToStringBuilder.reflectionToString(zonesResponse));
            if (CollectionUtil.isNotEmpty(zones)) {
                logger.info("获取阿里云ECS Zone 列表成功");
                logger.info(zones.size());
                for (int i = 0; i < zones.size(); i++) {
                    logger.info(zones.get(i).getAvailableResources());
                    if (CollectionUtil.isEmpty(zones.get(i).getAvailableResources())) {
                        zones.remove(i);
                    }
                }
            } else {
                logger.info("阿里云ECS " + regionId + "没有可有地域");
            }
            zoneList.addAll(zones);
            logger.info(zoneList);
            return null;
        }
    }


    /**
     * 通过当前zone信息来获取可用的资源列表信息
     * 阿里云官方的API返回的只有实例规格型号，没有具体规格说明，所以将具体规格写到了配置文件中
     * @return 返回经过一系列拼装获得当前所有zone区的可用资源信息
     */
    class getAvailableResources implements Callable<Object> {
        private DescribeZonesResponse.Zone zone;

        public getAvailableResources(DescribeZonesResponse.Zone zone) {
            this.zone = zone;
        }

        public Object call() throws Exception{
            List<Map<String, Object>> availableResource = new ArrayList<>();
            List<DescribeZonesResponse.Zone.ResourcesInfo> resourcesInfoList = zone.getAvailableResources();
            // TODO 华东1有一个zone的availableResource为空
//            if (CollectionUtil.isNotEmpty(resourcesInfoList)) {
                Map<String, Object> zoneAvailableResources = new HashMap<>();
                List<AliYunAvailableResource> aliYunResources = new ArrayList<>();
                for (DescribeZonesResponse.Zone.ResourcesInfo resourcesInfo : resourcesInfoList) {
                    AliYunAvailableResource aliYunAvailableResource = new AliYunAvailableResource();
                    aliYunAvailableResource.setIoOptimized(resourcesInfo.getIoOptimized());

                    //获取阿里云可用实例的规格列表
                    for (String instanceType : resourcesInfo.getInstanceTypes()) {
                        //每个资源对应的CPU数量和内存数量 均在配置文件中，需要时请去resource/AliYunInstanceSourceCompareTableFile.properties查看
                        //逗号前面的是CPU数量，后面是内存大小，单位GB
                        String list = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(instanceType);
                        String[] resources = null;
                        if (StringUtil.isNotEmpty(list)) {
                            resources = list.split(",");
                        }
                        if (ArrayUtil.isNotEmpty(resources)) {
                            AliYunAvailableResource.InstanceType instance = aliYunAvailableResource.new InstanceType(instanceType, resources[0], resources[1]);
                            aliYunAvailableResource.getInstanceTypes().add(instance);
                        }
                    }

                    //设置阿里云资源规格族
                    aliYunAvailableResource.setInstanceTypeFamilies(resourcesInfo.getInstanceTypeFamilies());
                    //获取阿里云可用资源的系列
                    for (String instanceGeneration : resourcesInfo.getInstanceGenerations()) {
                        aliYunAvailableResource.setInstanceGeneration(instanceGeneration);
                    }

                    //获取阿里云系统磁盘类型
                    for (String systemDataDiskCategory : resourcesInfo.getSystemDiskCategories()) {
                        String diskName = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(systemDataDiskCategory);
                        if (StringUtil.isNotEmpty(systemDataDiskCategory)) {
                            AliYunAvailableResource.DiskCategory diskCategory = aliYunAvailableResource.new DiskCategory(systemDataDiskCategory, diskName);
                            aliYunAvailableResource.getSystemDiskCategories().add(diskCategory);
                        }
                    }

                    //获取阿里云数据磁盘类型
                    for (String dataDiskCategory : resourcesInfo.getDataDiskCategories()) {
                        String diskName = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(dataDiskCategory);
                        if (StringUtil.isNotEmpty(dataDiskCategory)) {
                            AliYunAvailableResource.DiskCategory diskCategory = aliYunAvailableResource.new DiskCategory(dataDiskCategory, diskName);
                            aliYunAvailableResource.getDataDiskCategories().add(diskCategory);
                        }
                    }

                    if (!aliYunResources.contains(aliYunAvailableResource)) {
                        aliYunResources.add(aliYunAvailableResource);
                    }

                }
                zoneAvailableResources.put(zone.getZoneId(), aliYunResources);
                availableResourceList.add(zoneAvailableResources);
//            }
            return null;
        }
    }

    /**
     * 根据地域获取所有可用的镜像
     *
     * @return 返回由Map封装的操作系统列表和镜像列表
     */
    class getImages implements Callable<Object> {
        private String regionId;

        public getImages(String regionId) {
            this.regionId = regionId;
        }

        public Object call() throws Exception {
            List<Map<String, String>> imageList = new ArrayList<>();
            List<String> osTypeList = new ArrayList<>();
            AliImageClient aliImageClient = OpenClientFactory.getAliImageClient();
            DescribeImagesResponse imagesResponse = aliImageClient.DescribeImages(regionId,
                    null, null, null, null, null, null, null, null, null, null, appkeyId,
                    appkeySecret, null);
            List<DescribeImagesResponse.Image> images = imagesResponse.getImages();
            if (CollectionUtil.isNotEmpty(images)) {
                logger.info("获取 地域id=" + regionId + "的镜像列表成功，个数为" + images.size());
                for (DescribeImagesResponse.Image image : images) {
                    Map<String, String> item = new HashMap<>();
                    item.put("id", image.getImageId());
                    item.put("os", image.getOSType());
                    item.put("name", image.getImageName());
                    imageList.add(item);
                    if (StringUtil.isNotEmpty(image.getOSType()) && !osTypeList.contains(image.getOSType())) {
                        osTypeList.add(image.getOSType());
                    }
                }
            } else {
                logger.info("获取 地域id=" + regionId + "的镜像列表失败");
            }
            imageInfo.put("imageList", imageList);
            imageInfo.put("osList", osTypeList);
            logger.info("imageInfo:"+imageInfo);
            return null;
        }
    }


    /**
     * 根据regionId来获取阿里云的主机规格列表
     *
     * @param regionId
     * @return 返回拼接好的主机规格列表
     */
    private List<Map<String, String>> getInstanceType(String regionId) {
        List<Map<String, String>> instanceTypeList = null;
        if (CollectionUtil.isEmpty(instanceTypeList)) {
            instanceTypeList = new ArrayList<>();
        }
        instanceTypeList.addAll(getInstanceTypeByFamily(regionId, "ecs-1"));
        instanceTypeList.addAll(getInstanceTypeByFamily(regionId, "ecs-2"));
        return instanceTypeList;
    }

    private List<Map<String, String>> getInstanceTypeByFamily(String regionId, String instanceTypeFamily) {
        List<Map<String, String>> instanceTypeList = null;
        if (CollectionUtil.isEmpty(instanceTypeList)) {
            instanceTypeList = new ArrayList<>();
        }
        AliCommonClient aliCommonClient = OpenClientFactory.getAliCommonClient();
        DescribeInstanceTypesResponse instanceTypesResponse = aliCommonClient.DescribeInstanceTypes(regionId, instanceTypeFamily, appkeyId, appkeySecret, userEmail);
        List<DescribeInstanceTypesResponse.InstanceType> instanceTypes = instanceTypesResponse.getInstanceTypes();
        if (CollectionUtil.isNotEmpty(instanceTypes)) {
            logger.info("获取 regionId = " + regionId + "family = " + instanceTypeFamily + " 的实例列表成功 数量为 " + instanceTypes.size());
            for (DescribeInstanceTypesResponse.InstanceType instanceType : instanceTypes) {
                Map<String, String> item = new HashMap<>();
                item.put("regionId", regionId);
                item.put("instanceTypeId", instanceType.getInstanceTypeId());
                item.put("cpuCoreCount", String.valueOf(instanceType.getCpuCoreCount()));
                item.put("memorySize", String.valueOf(instanceType.getMemorySize()));
                item.put("instanceTypeFamily", String.valueOf(instanceType.getInstanceTypeFamily()));
                instanceTypeList.add(item);
            }
        } else {
            logger.info("获取 regionId = " + regionId + " 的实例列表失败 数量为 " + instanceTypes.size());

        }
        return instanceTypeList;
    }

    /**
     * 根据regionId来获取阿里云的安全组列表
     *
     * @return 返回阿里云的安全规则列表
     */
    class getSecurityGroupId implements Callable<Object> {
        private String regionId;

        public getSecurityGroupId(String regionId) {
            this.regionId = regionId;
        }

        public Object call() throws Exception {
            AliSecurityGroupClient aliSecurityGroupClient = OpenClientFactory.getAliSecurityGroupClient();
            DescribeSecurityGroupsResponse securityGroupsResponse = null;
            try {
                securityGroupsResponse = aliSecurityGroupClient.DescribeSecurityGroups(regionId, null, String.valueOf(50), null, appkeyId, appkeySecret, userEmail);
            } catch (ClientException e) {
                e.printStackTrace();
            }
            List<DescribeSecurityGroupsResponse.SecurityGroup> securityGroups = securityGroupsResponse.getSecurityGroups();
            if (CollectionUtil.isNotEmpty(securityGroups)) {
                logger.info("获取 regionId = " + regionId + " 的安全组列表成功 数量为 " + securityGroups.size());
                for (DescribeSecurityGroupsResponse.SecurityGroup securityGroup : securityGroups) {
                    Map<String, String> item = new HashMap<>();
                    item.put("regionId", regionId);
                    item.put("groupId", securityGroup.getSecurityGroupId());
                    item.put("groupName", securityGroup.getSecurityGroupName());
                    item.put("description", securityGroup.getDescription());
                    securityGroupList.add(item);
                }
            } else {
                logger.info("获取 regionId = " + regionId + " 的安全组列表失败数量为 " + securityGroups.size());

            }
            return null;
        }
    }

    public String getRegionId() {
        return (regionId).trim();
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }
}
