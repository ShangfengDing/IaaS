package com.appcloud.vm.action.vm.newvm;

import appcloud.openapi.client.*;
import appcloud.openapi.datatype.*;
import appcloud.openapi.response.*;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.InstanceProduct;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class PreNewYunHaiVmAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(PreNewYunHaiVmAction.class);
    private static final String PROVIDER = "yunhai";
    private AppkeyManager appkeyManager = new AppkeyManager();
    private CommonClient commonClient = OpenClientFactory.getCommonClient();
    private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
    private RegionClient regionClient = OpenClientFactory.getRegionClient();
    private ImageClient imageClient = OpenClientFactory.getImageClient();
    private SecurityGroupClient securityGroupClient = OpenClientFactory.getSecurityGroupClient();
    private TransformAttribute transform = new TransformAttribute();
    private Integer userId = this.getSessionUserID();
//    private RegionManager regionManager = RegionManager.getInstance();
    //可以返回的数据
    private Map<String, Object> result;
    private String appname;
    private Appkey appkeyMan;
    private String zoneId =com.appcloud.vm.common.Constants.ZONE_ID;;
//    private VmInfoCache cache = VmInfoCache.getCacheInstance();

    //异步线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(10);  //暂时只有两个地域
    private List<FutureTask<Object>> futureTasks = new ArrayList<FutureTask<Object>>();

    //全局变量的参数,根据区域信息获取
    private List<Map<String, String>> zoneList = new ArrayList<>();
    private List<Map<String, String>> instanceTypeList = new ArrayList<>();
    private List<Map<String, String>> bandWidthList = new ArrayList<>();
    private List<Map<String, String>> hardDiskList = new ArrayList<>();
    private List<Map<String, String>> securityGroupList = new ArrayList<>();
    private List<Map<String,String>> imagePrivateList = new ArrayList<>();
    private List<Map<String,String>> imagePublicList = new ArrayList<>();
    private List<Map<String,String>> imageGroupList = new ArrayList<>();

    public Map<String, Object> getResult() {
        return result;
    }

    public String yunhaiGetInfo() {
        appkeyMan = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        //获取区域信息
        List<Map<String, String>> providerList = getProvider();
        List<Map<String, String>> regionList = getRegion();


//        Map<String, Object> imageInfo = getImage(regionList);
        for (Map<String, String> region : regionList) {
//            zoneList.addAll(getZone(region.get("regionId")));
            FutureTask<Object> futureTaskGetZone = (FutureTask<Object>) executorService.submit(new getZone(region.get("regionId")));
            futureTasks.add(futureTaskGetZone);
            FutureTask<Object> futureTaskInstanceType = (FutureTask<Object>) executorService.submit(new getInstanceType(region.get("regionId")));
            futureTasks.add(futureTaskInstanceType);
            FutureTask<Object> futureTaskBandWidth = (FutureTask<Object>) executorService.submit(new getBandWidth(region.get("regionId")));
            futureTasks.add(futureTaskBandWidth);
            FutureTask<Object> futureTaskGetSecurityGroup = (FutureTask<Object>) executorService.submit(new getSecurityGroupId(region.get("regionId")));
            futureTasks.add(futureTaskGetSecurityGroup);
            FutureTask<Object> futureTaskDiskType = (FutureTask<Object>) executorService.submit(new getHardDiskType(region.get("regionId")));
            futureTasks.add(futureTaskDiskType);
            FutureTask<Object> futureTaskPrivateImage = (FutureTask<Object>) executorService.submit(new getPrivateImage(region.get("regionId")));
            futureTasks.add(futureTaskPrivateImage);
            FutureTask<Object> futureTaskPublicImage = (FutureTask<Object>) executorService.submit(new getPublicImage(region.get("regionId")));
            futureTasks.add(futureTaskPublicImage);
            FutureTask<Object> futureTaskGroupImage = (FutureTask<Object>) executorService.submit(new getGroupImage(region.get("regionId")));
            futureTasks.add(futureTaskGroupImage);
        }

        for (FutureTask futureTask:futureTasks){
            try {
                if(futureTask!=null) {
                    futureTask.get();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result = new HashMap<>();
        result.put("providerList", providerList);
        result.put("regionList", regionList);
        result.put("zoneList", zoneList);
        result.put("securityGroupList", securityGroupList);
        result.put("imagePrivateList", imagePrivateList);
        result.put("imagePublicList", imagePublicList);
        result.put("imageGroupList", imageGroupList);
        result.put("instanceTypeList", instanceTypeList);
        result.put("bandWidthList", bandWidthList);
        result.put("hardDiskList", hardDiskList);
        return SUCCESS;
    }

    private List<Map<String, String>> getProvider() {
        List<Map<String, String>> providerList = new ArrayList<>();
        Map<String, String> yunhai = new HashMap<>();
        yunhai.put("providerId", "yunhai");
        yunhai.put("providerName", "云海");
        providerList.add(yunhai);
        Map<String, String> ailiyun = new HashMap<>();
        ailiyun.put("providerId", "ALIYUN");
        ailiyun.put("providerName", "阿里云");
        providerList.add(ailiyun);
        Map<String, String> amazon = new HashMap<>();
        amazon.put("providerId", "AMAZON");
        amazon.put("providerName", "亚马逊");
        providerList.add(amazon);

        return providerList;
    }

    private List<Map<String, String>> getRegion() {
        List<Map<String, String>> regionList = new ArrayList<>();
            DescribeRegionsResponse regionsResponse = regionClient.DescribeRegions(appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            List<RegionItem> regionResult = regionsResponse.getRegionItems();
            for (RegionItem regionItem : regionResult) {
                Map<String, String> item = new HashMap<>();
                item.put("providorId", "yunhai");
                item.put("regionId", regionItem.getRegionId());
                item.put("regionName", transform.transformRegion(regionItem.getRegionId()));
                regionList.add(item);
            }

        return regionList;
    }

//    public Map<String, Object> getImage(List<Map<String, String>> regions) {
////        List<Map<String, String>> imageList = (List<Map<String, String>>) cache.get("imageList");
////        Set<String> osList = (Set<String>) cache.get("osList");
//        List<Map<String,String>> imageList = new ArrayList<>();
//        Set<String> osList = new HashSet<>();
////        if (imageList == null || osList == null) {
////            imageList = new ArrayList<>();
////            osList = new HashSet<>();
//            for (Map<String, String> region : regions) {
//                GetImageListResponse getImageListResponse = imageClient.DescribeImages(region.get("regionId"),
//                        null, null, null, null, null, null, null, null, null, null, "100",
//                        appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
//                ImageDetailSet imageDetailSet = getImageListResponse.getImageDetails();
//                List<ImageDetailItem> list = imageDetailSet.getImageDetailItems();
//                for (ImageDetailItem image : list) {
//                    Map<String, String> item = new HashMap<>();
//                    item.put("id", image.getImageUuid());
//                    item.put("name", image.getImageName());
//                    item.put("os", image.getDistribution());
//                    item.put("description", image.getDescription());
//                    imageList.add(item);
//                    if (image.getDistribution() != null) osList.add(image.getDistribution());
//                }
//            }
//        Map<String, Object> result = new HashMap();
//        result.put("imageList", imageList);
//        result.put("osList", osList);
//        return result;
//    }


    class getPrivateImage implements Callable<Object> {
        private String regionId;

        public getPrivateImage(String regionId) {
            this.regionId = regionId;
        }

        @Override
        public Object call() throws Exception {
            GetImageListResponse getImageListResponse = imageClient.DescribeImages(regionId, zoneId,
                    null, null, null, null, null, null, null, "PRIVATE", null, null, null, null, null, null, "100",
                    appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            ImageDetailSet imageDetailSet = getImageListResponse.getImageDetails();
            List<ImageDetailItem> list = imageDetailSet.getImageDetailItems();
            if (list != null){
                for (ImageDetailItem image : list) {
                    if (image.getDiskFormat().equalsIgnoreCase("IMAGE")) {
                        Map<String, String> item = new HashMap<>();
                        item.put("id", image.getImageUuid());
                        item.put("name", image.getImageName());
                        item.put("os", image.getDistribution());
                        item.put("description", image.getDescription());
                        item.put("software", image.getSoftware());
                        imagePrivateList.add(item);
                    }
                }
        }
            return null;
        }
    }
    class getPublicImage implements Callable<Object> {
        private String regionId;

        public getPublicImage(String regionId) {
            this.regionId = regionId;
        }

        @Override
        public Object call() throws Exception {
            GetImageListResponse getImageListResponse = imageClient.DescribeImages(regionId,zoneId,
                    null, null, null, null, null, null, null,"PUBLIC", null,null, null, null, null, null, "100",
                    appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            ImageDetailSet imageDetailSet = getImageListResponse.getImageDetails();
            List<ImageDetailItem> list = imageDetailSet.getImageDetailItems();
            if(list!=null) {
                for (ImageDetailItem image : list) {
                    if (image.getDiskFormat().equalsIgnoreCase("IMAGE")) {
                        Map<String, String> item = new HashMap<>();
                        item.put("id", image.getImageUuid());
                        item.put("name", image.getImageName());
                        item.put("os", image.getDistribution());
                        item.put("description", image.getDescription());
                        item.put("software", image.getSoftware());
                        item.put("code", image.getAccount());
                        imagePublicList.add(item);
                    }
                }
            }
            return null;
        }
    }
    class getGroupImage implements Callable<Object> {
        private String regionId;

        public getGroupImage(String regionId) {
            this.regionId = regionId;
        }

        @Override
        public Object call() throws Exception {
            GetImageListResponse getImageListResponse = imageClient.DescribeImages(regionId,zoneId,
                    null, null, null, null, null, null, null,"GROUP", null, null, null, null, null, null, "100",
                    appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            ImageDetailSet imageDetailSet = getImageListResponse.getImageDetails();
            List<ImageDetailItem> list = imageDetailSet.getImageDetailItems();
            if(list!=null) {
                for (ImageDetailItem image : list) {
                    if (image.getDiskFormat().equalsIgnoreCase("IMAGE")) {
                        Map<String, String> item = new HashMap<>();
                        item.put("id", image.getImageUuid());
                        item.put("name", image.getImageName());
                        item.put("os", image.getDistribution());
                        item.put("description", image.getDescription());
                        item.put("software", image.getSoftware());
                        imageGroupList.add(item);
                    }
                }
            }
            return null;
        }
    }


    class getZone implements Callable<Object> {
        private String regionId;

        public getZone(String regionId) {
            this.regionId = regionId;
        }

        public Object call() throws Exception {
            DescribeZonesResponse zonesResponse = regionClient.DescribeZones(regionId, appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            List<ZoneItem> result = zonesResponse.getZoneItems();
            List<Map<String, String>> zones = new ArrayList<>();
            for (ZoneItem zoneItem : result) {
                Map<String, String> item = new HashMap<>();
                item.put("regionId", regionId);
                item.put("zoneId", zoneItem.getZoneId());
                item.put("zoneName", transform.transformZone(zoneItem.getZoneId()));
                zones.add(item);
            }
            zoneList.addAll(zones);
            logger.info(zoneList.size());
            return null;
        }
    }

    class getSecurityGroupId implements Callable<Object> {
        private String regionId;

        public getSecurityGroupId(String regionId) {
            this.regionId = regionId;
        }

        public Object call() throws Exception {
            securityGroupList = new ArrayList<>();
            SecurityGroupsDetailReponse response = securityGroupClient.DescribeSecurityGroups(regionId, zoneId,null, "20", null, appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            if (response.getErrorCode() == null) {
                List<SecurityGroupDetailItem> items = response.getSecurityGroups();
                for (SecurityGroupDetailItem item : items) {
                    if (item.getSecurityGroupId() == null) break;
                    Map<String, String> securityGroup = new HashMap<>();
                    securityGroup.put("providor", PROVIDER);
                    securityGroup.put("regionId", regionId);
                    securityGroup.put("groupId", item.getSecurityGroupId());
                    securityGroup.put("groupName", item.getSecurityGroupName());
                    securityGroup.put("description", item.getDescription());

                    securityGroupList.add(securityGroup);
                }
            }
            logger.info("sg size :"+securityGroupList.size());
            return null;
        }
    }

    class getInstanceType implements Callable<Object> {

        private String regionId;

        public getInstanceType(String regionId) {
            this.regionId = regionId;
        }

        public Object call() throws Exception {
            DescribeInstanceTypesResponse typesResponse = commonClient.DescribeInstanceTypes(regionId,zoneId, appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            List<InstanceTypeItem> result = typesResponse.getInstanceTypes().getInstanceTypeItems();
            for (InstanceTypeItem instanceType : result) {
                Map<String, String> item = new HashMap<>();
                item.put("regionId", regionId);
                item.put("instanceTypeId", instanceType.getInstanceTypeId());
                item.put("cpuCoreCount", String.valueOf(instanceType.getCpuCoreCount()));
                item.put("memorySize", String.valueOf(instanceType.getMemorySize()));
                item.put("hardDisk", String.valueOf(instanceType.getHardDisk()));
                item.put("yearPrice", String.valueOf(instanceType.getYearPrice()));
                item.put("monthPrice", String.valueOf(instanceType.getMonthPrice()));
                item.put("dayPrice", String.valueOf(instanceType.getDayPrice()));
                item.put("hourprice", String.valueOf(instanceType.getHourprice()));
                instanceTypeList.add(item);
            }
            return null;
        }
    }

    class getHardDiskType implements Callable<Object> {
        private String regionId;

        public getHardDiskType(String regionId) {
            this.regionId = regionId;
        }

        public Object call() throws Exception {
            DescribeInstanceTypesResponse response = commonClient.DescribeDiskTypes(regionId,zoneId, appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            List<InstanceTypeItem> instanceTypeItems = response.getInstanceTypes().getInstanceTypeItems();
            for (InstanceTypeItem instanceItem : instanceTypeItems) {
                Map<String, String> item = new HashMap<>();
                item.put("regionId", regionId);
                item.put("hourPrice", String.valueOf(instanceItem.getHourprice()));
                item.put("dayPrice", String.valueOf(instanceItem.getDayPrice()));
                item.put("monthPrice", String.valueOf(instanceItem.getMonthPrice()));
                item.put("yearPrice", String.valueOf(instanceItem.getYearPrice()));
                item.put("hardDisk", String.valueOf(instanceItem.getHardDisk()));
                hardDiskList.add(item);
            }
            return null;
        }
    }

    class getBandWidth implements Callable<Object> {
        private String regionId;

        public getBandWidth(String regionId) {
            this.regionId = regionId;
        }

        public Object call() throws Exception {
            DescribeInstanceTypesResponse response = commonClient.DescribeInternetTypes(regionId,zoneId, appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
            List<InstanceTypeItem> instanceTypeItems = response.getInstanceTypes().getInstanceTypeItems();
            for (InstanceTypeItem instanceItem : instanceTypeItems) {
                Map<String, String> item = new HashMap<>();
                item.put("regionId", regionId);
                item.put("hourPrice", String.valueOf(instanceItem.getHourprice()));
                item.put("dayPrice", String.valueOf(instanceItem.getDayPrice()));
                item.put("monthPrice", String.valueOf(instanceItem.getMonthPrice()));
                item.put("yearPrice", String.valueOf(instanceItem.getYearPrice()));
                item.put("bandWidth", String.valueOf(instanceItem.getBandWidth()));
                bandWidthList.add(item);
            }
            return null;
        }
    }

//    private List<Map<String, String>> getBandWidth(String regionId) {
//        List<Map<String, String>> bandWidthType = (List<Map<String, String>>) cache.get("bandWidth");
//        if (bandWidthType == null) {
//            bandWidthType = new ArrayList<>();
//            DescribeInstanceTypesResponse response = commonClient.DescribeInternetTypes(regionId, appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), appkeyMan.getUserEmail());
//            List<InstanceTypeItem> instanceTypeItems = response.getInstanceTypes().getInstanceTypeItems();
//            for (InstanceTypeItem instanceItem : instanceTypeItems) {
//                Map<String, String> item = new HashMap<>();
//                item.put("regionId", regionId);
//                item.put("hourPrice", String.valueOf(instanceItem.getHourprice()));
//                item.put("dayPrice", String.valueOf(instanceItem.getDayPrice()));
//                item.put("monthPrice", String.valueOf(instanceItem.getMonthPrice()));
//                item.put("yearPrice", String.valueOf(instanceItem.getYearPrice()));
//                item.put("bandWidth", String.valueOf(instanceItem.getBandWidth()));
//
//                bandWidthType.add(item);
//            }
//            cache.cache("bandWidth", bandWidthType);
//        }
//        return bandWidthType;
//    }


    private void addAliyunProduct(String aliYun, List<InstanceProduct> products2) {
        // TODO Auto-generated method stub

    }

    private void addAmazonProduct(String amazon, List<InstanceProduct> products2) {
        // TODO Auto-generated method stub

    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }
}
