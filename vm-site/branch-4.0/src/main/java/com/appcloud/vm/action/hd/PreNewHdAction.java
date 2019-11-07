package com.appcloud.vm.action.hd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.appcloud.vm.fe.manager.AppkeyManager;
import org.apache.log4j.Logger;

import appcloud.openapi.client.CommonClient;
import appcloud.openapi.client.RegionClient;
import appcloud.openapi.datatype.InstanceTypeItem;
import appcloud.openapi.datatype.RegionItem;
import appcloud.openapi.datatype.ZoneItem;
import appcloud.openapi.response.DescribeInstanceTypesResponse;
import appcloud.openapi.response.DescribeRegionsResponse;
import appcloud.openapi.response.DescribeZonesResponse;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.action.vm.VmInfoCache;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.InstanceProduct;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;

public class PreNewHdAction extends BaseAction{
	private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(PreNewHdAction.class);

    private AppkeyManager appkeyManager = new AppkeyManager();
    private CommonClient commonClient = OpenClientFactory.getCommonClient();
    private RegionClient regionClient = OpenClientFactory.getRegionClient();
    private TransformAttribute transform = new TransformAttribute();
    private Appkey appkeyMan = appkeyManager.getAppkeyByUserEmail(Constants.MANAGER_EMAIL);
    private Integer userId = this.getSessionUserID();
    private String appName;

    //异步线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private List<FutureTask<Object>> futureTasks = new ArrayList<>();
    //可以返回的数据
    private Map<String, Object> result;
    //根据区域信息获取zone信息
    List<Map<String, String>> hdZoneList = new ArrayList<>();
    List<Map<String, String>> hdHardDiskList = new ArrayList<>();
    public Map<String, Object> getResult() {    	
        return result;
    }

    public String hdGetInfo() {
        //获取区域信息
        List<Map<String, String>> providerList = getProvider();
        List<Map<String, String>> regionList = getRegion();

        for (Map<String, String> region : regionList) {
            String regionIdtmp = region.get("regionId");
            FutureTask<Object> futureTaskGetZone = (FutureTask<Object>) executorService.submit(new getZone(regionIdtmp));
            futureTasks.add(futureTaskGetZone);
            FutureTask<Object> futureTaskGetDiskType = (FutureTask<Object>) executorService.submit(new getHardDiskType(regionIdtmp));
            futureTasks.add(futureTaskGetDiskType);
        }

        for (FutureTask futureTask:futureTasks){
            try {
                futureTask.get();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        result = new HashMap<>();
        result.put("providerList", providerList);
        result.put("regionList", regionList);
        result.put("zoneList", hdZoneList);
        result.put("hardDiskList", hdHardDiskList);
        result.put("userEmail", Constants.MANAGER_EMAIL);
        return SUCCESS;
    }

    private List<Map<String, String>> getProvider() {
        List<Map<String, String>> providerList = new ArrayList<>();
        Map<String, String> yunhai = new HashMap<>();
        yunhai.put("providerId", "yunhai");
        yunhai.put("providerName", "云海");
        providerList.add(yunhai);
        Map<String, String> ailiyun = new HashMap<>();
        ailiyun.put("providerId", "aliyun");
        ailiyun.put("providerName", "阿里云");
        providerList.add(ailiyun);
        Map<String, String> amazon = new HashMap<>();
        amazon.put("providerId", "amozon");
        amazon.put("providerName", "亚马逊");
        providerList.add(amazon);

        return providerList;
    }

    private List<Map<String, String>> getRegion() {
        List<Map<String, String>> regionList = new ArrayList<>();
        DescribeRegionsResponse regionsResponse = regionClient.DescribeRegions(appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), Constants.MANAGER_EMAIL);
        List<RegionItem> regionResult = regionsResponse.getRegionItems();
        for (RegionItem regionItem : regionResult) {
            //basic info
            Map<String, String> item = new HashMap<>();
            item.put("providerId", "yunhai");
            item.put("regionId", regionItem.getRegionId());
            item.put("regionName", transform.transformRegion(regionItem.getRegionId()));
            regionList.add(item);
        }


        return regionList;
    }

    class getZone implements Callable<Object> {
        private String regionId;
        public getZone(String regionId){
            this.regionId = regionId;
        }
        public List<Map<String, String>> call() throws Exception{
            DescribeZonesResponse zonesResponse = regionClient.DescribeZones(regionId, appkeyMan.getAppkeyId(), appkeyMan.getAppkeySecret(), Constants.MANAGER_EMAIL);
            List<ZoneItem> result = zonesResponse.getZoneItems();
            for (ZoneItem zoneItem : result) {
                Map<String, String> item = new HashMap<>();
                item.put("regionId", regionId);
                item.put("zoneId", zoneItem.getZoneId());
                item.put("zoneName", transform.transformZone(zoneItem.getZoneId()));
                hdZoneList.add(item);
            }
            return null;
        }
    }

    class getHardDiskType implements Callable<Object>{
        private String regionId;
        public getHardDiskType(String regionId){
            this.regionId = regionId;
        }
        public List<Map<String, String>> call() throws Exception{
            Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appName);
            DescribeInstanceTypesResponse response = commonClient.DescribeDiskTypes(regionId, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
            List<InstanceTypeItem> instanceTypeItems = response.getInstanceTypes().getInstanceTypeItems();
            for (InstanceTypeItem instanceItem : instanceTypeItems) {
                Map<String, String> item = new HashMap<>();
                item.put("regionId", regionId);
                item.put("hourPrice", String.valueOf(instanceItem.getHourprice()));
                item.put("dayPrice", String.valueOf(instanceItem.getDayPrice()));
                item.put("monthPrice", String.valueOf(instanceItem.getMonthPrice()));
                item.put("yearPrice", String.valueOf(instanceItem.getYearPrice()));
                item.put("hardDisk", String.valueOf(instanceItem.getHardDisk()));

                hdHardDiskList.add(item);
            }
            return null;
        }
    }


    private void addAliyunProduct(String aliYun, List<InstanceProduct> products2) {
        // TODO Auto-generated method stub

    }

    private void addAmazonProduct(String amazon, List<InstanceProduct> products2) {
        // TODO Auto-generated method stub

    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
