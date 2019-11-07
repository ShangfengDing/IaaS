package com.appcloud.vm.action.hd;

import aliyun.openapi.client.AliVolumeClient;
import appcloud.openapi.client.VolumeClient;
import appcloud.openapi.response.CreateDiskResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.ResultUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.HashMap;
import java.util.Map;

public class NewHdAction extends BaseAction {


    private Logger logger = Logger.getLogger(NewHdAction.class);

    private AppkeyManager appkeyManager = new AppkeyManager();
    private Integer userId;
    private Appkey appkey;

    private String providerEn;//厂商


    //公共创建的参数 表示 三个提供商都具有的参数

    private String regionId;//地域
    private String zoneId =com.appcloud.vm.common.Constants.ZONE_ID;;//区域
    private String diskSize;//硬盘大小
    private Integer diskNum = 1;//硬盘的数量

    //云海云硬盘参数
    private String diskChargeType;//付费类型
    private String diskChargeLength;//付费时长
    private String diskName;//硬盘名称
    private String appname;


    //阿里云创建参数
    private String diskCategory;//阿里云硬盘类型

    private String result = "1";//1:正确，0:参数错误

    public String execute() {
        zoneId=com.appcloud.vm.common.Constants.ZONE_ID;
        logger.info("创建硬盘:" + regionId + "," + zoneId + "," + diskSize + "," + diskChargeType+","+appname);
        logger.info(diskChargeLength + "," + diskNum + "," + providerEn + "," + diskName);
        userId = this.getSessionUserID();
        appkey = getAppkey();
        switch (providerEn.trim()) {
            case Constants.YUN_HAI:
                newYunHaiDisk();
                break;
            case Constants.ALI_YUN:
                newAliYunDisk();
                break;
            case Constants.AMAZON:
                newAmazonDisk();
                break;
            default:
                newYunHaiDisk();
        }
        ResultUtils.toJson(ServletActionContext.getResponse(),result);
        return null;
    }

    //云海创建硬盘
    private void newYunHaiDisk() {
        zoneId=com.appcloud.vm.common.Constants.ZONE_ID;
         VolumeClient diskClient = OpenClientFactory.getVolumeClient();
        for (int i = 0; i < diskNum; i++) {
            CreateDiskResponse createDiskResponse = diskClient.CreateDisk(regionId.trim(), zoneId.trim(), diskSize.trim(),
                    diskChargeType, diskChargeLength.trim(), diskName.trim(), null, null, null, appkey.getAppkeyId(),
                    appkey.getAppkeySecret(), appkey.getUserEmail());
            logger.info(ToStringBuilder.reflectionToString(createDiskResponse));
            if (null == createDiskResponse.getErrorCode()) {
                result = "1";
                LogInfo(createDiskResponse.getDiskId(), "success,"+toString(), "info");
            } else {
                result = "0";
                LogInfo("","fail,"+createDiskResponse.getMessage(),"error");
            }
        }

    }

    private void newAliYunDisk() {
        AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
        com.aliyuncs.ecs.model.v20140526.CreateDiskResponse createDiskResponse;
        try {
            createDiskResponse = aliVolumeClient.CreateDisk(regionId, zoneId, diskSize, null, null,
                    null, diskCategory, null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
            if(createDiskResponse!=null){
                ResultUtils.toAliYunJson(ServletActionContext.getResponse(), createDiskResponse);
            }
            LogInfo(createDiskResponse.getDiskId(), "success,"+toString(), "info");
        } catch (ClientException e) {
            ResultUtils.toAliYunJson(ServletActionContext.getResponse(), e);
            LogInfo("","fail,"+e.getErrMsg(),"error");
        }
    }

    public void LogInfo(String diskId, String result, String logType) {
        Map<String, String> mapLog = new HashMap<>();
        mapLog.put("userId", userId.toString());
        mapLog.put("device", "vm");
        mapLog.put("provider", appkey.getProvider());
        mapLog.put("operateType", "create a disk");
        mapLog.put("result", result);
        mapLog.put("deviceId",diskId);
        if (logType.equals("info")) {
            Log.INFO(mapLog, appkey, regionId);
        } else {
            Log.ERROR(mapLog, appkey, regionId);
        }
    }

    private void newAmazonDisk() {
    }

    private Appkey getAppkey() {
        return appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
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

    public String getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    public String getDiskChargeType() {
        return diskChargeType;
    }

    public void setDiskChargeType(String diskChargeType) {
        this.diskChargeType = diskChargeType;
    }

    public String getDiskChargeLength() {
        return diskChargeLength;
    }

    public void setDiskChargeLength(String diskChargeLength) {
        this.diskChargeLength = diskChargeLength;
    }

    public Integer getDiskNum() {
        return diskNum;
    }

    public void setDiskNum(Integer diskNum) {
        this.diskNum = diskNum;
    }

    public String getProviderEn() {
        return providerEn;
    }

    public void setProviderEn(String providerEn) {
        this.providerEn = providerEn;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDiskCategory() {
        return (diskCategory).trim();
    }

    public void setDiskCategory(String diskCategory) {
        this.diskCategory = diskCategory;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    @Override
    public String toString() {
        return "NewHdAction{" +
                "providerEn='" + providerEn + '\'' +
                ", regionId='" + regionId + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", diskSize='" + diskSize + '\'' +
                ", diskNum=" + diskNum +
                ", diskChargeType='" + diskChargeType + '\'' +
                ", diskChargeLength='" + diskChargeLength + '\'' +
                ", diskName='" + diskName + '\'' +
                ", appname='" + appname + '\'' +
                ", diskCategory='" + diskCategory + '\'' +
                '}';
    }
}