package com.appcloud.vm.action.image;


import aliyun.openapi.client.AliImageClient;
import aliyun.openapi.client.AliInstanceClient;
import appcloud.openapi.client.ImageClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.client.RegionClient;
import appcloud.openapi.datatype.ImageDetailItem;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.datatype.RegionItem;
import appcloud.openapi.response.DescribeInstancesResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeImagesResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.Instance;
import com.appcloud.vm.fe.entity.InstanceStatus;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stone on 2016/6/2.
 */
public class RelativeInstanceAction extends BaseAction {
    private static final long serialVersionId = 1L;
    private Logger logger = Logger.getLogger(RelativeInstanceAction.class);

    private AppkeyManager appkeyManager = new AppkeyManager();
    private Appkey appkey;

    private Integer userId = this.getSessionUserID();
    private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
    private RegionClient regionClient = OpenClientFactory.getRegionClient();
    private ImageClient imageClient = OpenClientFactory.getImageClient();
    private AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
    private AliImageClient aliImageClient = OpenClientFactory.getAliImageClient();
    private TransformAttribute transform = new TransformAttribute();

    private List<InstanceStatus> result = new ArrayList<InstanceStatus>();
    private List<RegionItem> regionList;

    private String imageId;
    private String appname;
    private String regionId;
    private String zoneId =com.appcloud.vm.common.Constants.ZONE_ID;;
    private Long totalPage;
    private int currentPage;
    private final String itemEachPage = "10";
    private int page;
    private Long totalItem;

    public List<InstanceStatus> getResult() {
        return result;
    }

    public String imgGetFirstPage() {
        getRelativeInstance(1);
        return SUCCESS;
    }

    public String imgGetDivPage() {
        getRelativeInstance(page);
        return SUCCESS;
    }

    private void getRelativeInstance(int page) {
        List<InstanceStatus> instanceList = new ArrayList<InstanceStatus>();
        appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        switch (appkey.getProvider()) {
            case Constants.YUN_HAI:
                addYunhaiInstance(appkey, page);
                break;
            case Constants.ALI_YUN:
                addAliyunInstance(appkey, page);
                break;
            case Constants.AMAZON:
                addAmazonInstance(appkey, page);
                break;
            default:
                break;
        }

    }

    private void addAliyunInstance(Appkey appkey, int page) {
        com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse instancesResponse = aliInstanceClient.DescribeInstances(regionId,
                null, null, null, null, null, null, null, null, imageId, String.valueOf(page), itemEachPage,
                appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
        List<com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance> instanceAttributes = instancesResponse.getInstances();
        //获取更多关于主机的信息
        Map<String, String> imageMap = new HashMap<String, String>();
        for (com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance instance : instanceAttributes) {
            if (!imageMap.containsKey(instance.getImageId())) {
                logger.info("image is:" + instance.getImageId());
                List<DescribeImagesResponse.Image> imageList = aliImageClient.DescribeImages(
                        regionId, instance.getImageId(),
                        null, null, null, null, null, null, null, null,
                        null, appkey.getAppkeyId(),
                        appkey.getAppkeySecret(), null)
                        .getImages();
                logger.info("image result is:" + imageList.size());
                if (null != imageList && imageList.size() > 0)
                    imageMap.put(instance.getImageId(), imageList.get(0)
                            .getOSName());
                else
                    imageMap.put(instance.getImageId(), null);
            }
            // 阿里云的云主机状态表示是大写，所以在此要进行转换
            Integer expiring = 0;
            String expiredTime = null;
            if (null != instance.getExpiredTime()) {
                expiredTime = instance.getExpiredTime().substring(2,
                        instance.getExpiredTime().length() - 3);
            }
            String aliStatus = instance.getStatus().toString().toLowerCase();
            result.add(new InstanceStatus(instance.getInstanceId(),
                    instance.getInstanceName(), Constants.ALI_YUN, transform.transformProvider(Constants.ALI_YUN),appkey.getAppname(), regionId, transform.transformRegion(regionId),
                    transform.transformZone(instance.getZoneId()), aliStatus, transform.transformStatus(aliStatus),
                    imageMap.get(instance.getImageId()), instance.getCpu(), instance.getMemory(), instance.getInternetMaxBandwidthOut(),
                    instance.getPublicIpAddress().isEmpty() ? null : instance.getPublicIpAddress().get(0),
                    instance.getInnerIpAddress().get(0),
                    instance.getInstanceChargeType(), instance.getExpiredTime(), null, expiring));
        }
        totalItem = Long.valueOf(instancesResponse.getTotalCount());
        totalPage = totalItem / instancesResponse.getPageSize();
        if (totalItem > totalPage * instancesResponse.getPageSize()) totalPage++;
        currentPage = instancesResponse.getPageNumber();
    }

    private void addYunhaiInstance(Appkey appkey, int page) {
        DescribeInstancesResponse instancesResponse = instanceClient.DescribeInstances(regionId,
                null, null, null, null, null, null, null, null, imageId, String.valueOf(page), itemEachPage,
                appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
        List<InstanceAttributes> instanceAttributes = instancesResponse.getInstances();
        //获取更多关于主机的信息
        Map<String, String> imageMap = new HashMap<String, String>();
        for (InstanceAttributes instance : instanceAttributes) {
            logger.info("vmId=" + instance.getInstanceId() + " ; name=" + instance.getInstanceName());
            if (!imageMap.containsKey(instance.getImageId())) {
                try {
                    ImageDetailItem imageItem = imageClient.GetImageDetail(regionId,zoneId, instance.getImageId(),
                            appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail()).getImageDetailItem();
                    imageMap.put(instance.getImageId(), imageItem.getDistribution());
                } catch (Exception e) {
                    logger.warn("imageItem is null");
                    imageMap.put(instance.getImageId(), null);
                }
            }
            String expiredTime = null==instance.getExpiredTime()?null:instance.getExpiredTime().substring(2, instance.getExpiredTime().length() - 3);
            result.add(new InstanceStatus(instance.getInstanceId(), instance.getInstanceName(), Constants.YUN_HAI,
                    transform.transformProvider(Constants.YUN_HAI), appkey.getAppname(), regionId, transform.transformRegion(regionId),
                    transform.transformZone(instance.getZoneId()), instance.getStatus(), transform.transformStatus(instance.getStatus()),
                    imageMap.get(instance.getImageId()), instance.getVcpus(), instance.getMemoryMb(), 0, instance.getPublicIpAddress(),
                    instance.getPrivateIpAddress(), instance.getInstanceChargeType(), expiredTime, appkey.getUserEmail(), null));
        }
        totalItem = instancesResponse.getTotalCount();
        totalPage = totalItem / instancesResponse.getPageSize();
        if (totalItem > totalPage * instancesResponse.getPageSize()) totalPage++;
        currentPage = instancesResponse.getPageNumber();
    }

    private void addAmazonInstance(Appkey appkey, int page) {}

    public long getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
