package com.appcloud.vm.action.vm;


import aliyun.openapi.client.AliInstanceClient;
import aliyun.openapi.client.AliVolumeClient;
import appcloud.openapi.client.CommonClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.client.VolumeClient;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.response.DescribeInstancesResponse;
import appcloud.openapi.response.DisksDetailReponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse.Disk;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.DiskInventory;
import com.appcloud.vm.fe.entity.InstanceDetail;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VmDetailAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(VmDetailAction.class);
    private CommonClient commonClient = OpenClientFactory.getCommonClient();
    private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
    private AliInstanceClient aliInstanceClient = OpenClientFactory
            .getAliInstanceClient();
    private VolumeClient volumeClient = OpenClientFactory.getVolumeClient();
    private AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
    private InstanceDetail details;
    private AppkeyManager appkeyManager = new AppkeyManager();
    private Integer userId = this.getSessionUserID();
    private TransformAttribute transform = new TransformAttribute();
    private HashMap<String, String> statusMap = new HashMap<String, String>();
    private List<DiskInventory> diskInventories = new ArrayList<DiskInventory>();
    private List<String> diskSnapshotIds = new ArrayList<String>();
    private Appkey appkey;

    //参数
    private String provider;//英文的厂商
    private String appname;//appName用于发起请求的
    private String regionId;//英文的regionId
    private String zoneId;//英文的zoneId
    private String instanceId;
    private String userEmail;
    private String result = "1";//result 为1：表示正常，0：表示参数出错

    private List<Integer> instanceType = new ArrayList<Integer>();//主机的规格，Cpu、内存、硬盘、带宽

    public String execute() {
        logger.info(provider + regionId + zoneId + instanceId + userEmail);
        appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        switch (appkey.getProvider()) {
            case Constants.YUN_HAI:
                showYunhaiDetails(appkey);
                break;
            case Constants.ALI_YUN:
                showAliyunDetails(appkey);
                break;
            case Constants.AMAZON:
                showAmazonDetails();
                break;
            default:
                break;
        }
        return SUCCESS;
    }

    private void showYunhaiDetails(Appkey appkey) {

        //用instanceId获得实例列表
        DescribeInstancesResponse instanceList = instanceClient.DescribeInstances(regionId.trim(), appkey.getZone(),
                "[" + instanceId.trim() + "]", null, null, null, null, null, null, null, null, null,
                appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
        InstanceAttributes instance = instanceList.getInstances().get(0);
        String systemDiskId = null;
        String systemDiskName = null;
        Integer systemDiskSize = null;
        String systemDiskSnapshotId = null;
        String osType = null;
        String publicIpAddress = null;
        String description = null;
        String securityGroupId = instance.getSecurityGroupId();
        String securityGroupName = instance.getSecurityGroupName();
        List<String> systemDiskSnapshotIds = new ArrayList<String>();
        statusMap.put(instance.getStatus(), transform.transformStatus(instance.getStatus()));
        //获得主机相关的硬盘
        DisksDetailReponse diskList = volumeClient.DescribeDisks(regionId.trim(), appkey.getZone(), null,
                instanceId.trim(), null, null, null, null, null, null, null, null, null, null,
                appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
        List<DiskDetailItem> disks = diskList.getDisks();
        logger.info("diskList");
        for (DiskDetailItem disk : disks) {
            if ("SYSTEM".equals(disk.getDiskType())) {
                systemDiskId = disk.getDiskId();
                systemDiskName = disk.getDiskName();
                systemDiskSize = disk.getSize();
                systemDiskSnapshotId = disk.getSnapshotId();
                systemDiskSnapshotIds.add(systemDiskSnapshotId);
            } else {
                diskSnapshotIds.add(disk.getSnapshotId());
//				TODO
                diskInventories.add(new DiskInventory(disk.getDiskId(), disk.getDiskName(), disk.getDiskType(), disk.getSize(), diskSnapshotIds));
            }
        }
        osType = instance.getOstype();
        publicIpAddress = instance.getPublicIpAddress();
        if (publicIpAddress == null || "".equals(publicIpAddress)) publicIpAddress = "null";
        description = instance.getDescription();
        if (description == null || "".equals(description)) description = "null";
//		TODO

        details = new InstanceDetail(instanceId.trim(), instance.getInstanceName(), Constants.YUN_HAI,
                transform.transformProvider(Constants.YUN_HAI), regionId.trim(), transform.transformRegion(regionId.trim()),
                instance.getZoneId(), instance.getStatus(), description, instance.getImageId(), osType, "主机规格", instance.getVcpus(),
                instance.getMemoryMb(), 0, publicIpAddress, instance.getPrivateIpAddress(),
                instance.getInstanceChargeType(), instance.getCreationTime(), instance.getExpiredTime(), systemDiskId, systemDiskName, systemDiskSize, systemDiskSnapshotIds, diskInventories, String.valueOf(securityGroupId), String.valueOf(securityGroupName), appkey.getUserEmail());

        //获得主机规格
        logger.info("Cup:" + instance.getVcpus() + "Memory:" + instance.getMemoryMb() + "Gb:" + instance.getLocalGb());
        //获得主机的规格
        try {
            instanceType.add(instance.getVcpus());
            instanceType.add(instance.getMemoryMb());
            instanceType.add(instance.getLocalGb());
            instanceType.add(Integer.parseInt(instance.getInternetMaxBandwidth()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAliyunDetails(Appkey appkey) {
        //用instanceId获得实例列表
        List<Instance> instanceList = aliInstanceClient.DescribeInstances(
                regionId.trim(), appkey.getZone(), "[\"" + instanceId.trim() + "\"]", null, null, null,
                null, null, null, null, null, null,
                appkey.getAppkeyId(), appkey.getAppkeySecret(),
                appkey.getUserEmail()).getInstances();
        Instance instance = instanceList.get(0);
        String systemDiskId = null;
        String systemDiskName = null;
        Integer systemDiskSize = null;
        Integer localGb = 0;
        String systemDiskSnapshotId = null;
        String osType = "null";
        String publicIpAddress = null;
        String description = null;
        List<String> systemDiskSnapshotIds = new ArrayList<String>();
        statusMap.put(instance.getStatus().getStringValue(), transform.transformStatus(instance.getStatus().getStringValue()));
        //获得主机相关的硬盘
        DescribeDisksResponse diskList = aliVolumeClient.DescribeDisks(regionId.trim(), appkey.getZone(), null,
                instanceId.trim(), null, null, null, null, null, null, null, null, null,
                appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
        List<Disk> disks = diskList.getDisks();
        logger.info("diskList");
        for (Disk disk : disks) {
            if ("SYSTEM".equals(disk.getType()) || "system".equals(disk.getType().getStringValue())) {
                systemDiskId = disk.getDiskId();
                systemDiskName = disk.getDiskName();
                systemDiskSize = disk.getSize();
                systemDiskSnapshotId = disk.getSourceSnapshotId();
                systemDiskSnapshotIds.add(systemDiskSnapshotId);
            } else {
                diskSnapshotIds.add(disk.getSourceSnapshotId());
//				TODO
                diskInventories.add(new DiskInventory(disk.getDiskId(), disk.getDiskName(), disk.getType().getStringValue(), disk.getSize(), diskSnapshotIds));
            }
            localGb += disk.getSize();
        }
        //osType = instance.getOstype();
        if (instance.getPublicIpAddress().size() == 0) {
            publicIpAddress = "null";
        } else {
            publicIpAddress = instance.getPublicIpAddress().get(0);
        }
        description = instance.getDescription();
        if (description == null || "".equals(description)) description = "null";
//		TODO
        String securityGroupId = "";
        for (String security : instance.getSecurityGroupIds()) {
            securityGroupId = securityGroupId+security+",";
        }
        //主机规格、安全组Id和名称、挂载的硬盘
        details = new InstanceDetail(instanceId.trim(), instance.getInstanceName(), Constants.ALI_YUN,
                transform.transformProvider(Constants.ALI_YUN), regionId.trim(), transform.transformRegion(regionId.trim()),
                instance.getZoneId(), instance.getStatus().getStringValue(), description, instance.getImageId(), osType, "主机规格", instance.getCpu(),
                instance.getMemory(), 0, publicIpAddress, instance.getInnerIpAddress().get(0),
                instance.getInstanceChargeType(), instance.getCreationTime(), instance.getExpiredTime(), systemDiskId, systemDiskName, systemDiskSize, systemDiskSnapshotIds, diskInventories, instance.getSecurityGroupIds().get(0),securityGroupId, appkey.getUserEmail());

        //获得主机规格
        logger.info("Cup:" + instance.getCpu() + "Memory:" + instance.getMemory() + "Gb:" + localGb);
        //获得主机的规格
        try {
            instanceType.add(instance.getCpu());
            instanceType.add(instance.getMemory());
            instanceType.add(localGb);
            instanceType.add(instance.getInternetMaxBandwidthOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAmazonDetails() {
    }

    public HashMap<String, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(HashMap<String, String> statusMap) {
        this.statusMap = statusMap;
    }

    public InstanceDetail getDetails() {
        return details;
    }

    public void setDetails(InstanceDetail details) {
        this.details = details;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Integer> getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(List<Integer> instanceType) {
        this.instanceType = instanceType;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
