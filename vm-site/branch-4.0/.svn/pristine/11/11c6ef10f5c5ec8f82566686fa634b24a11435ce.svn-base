package com.appcloud.vm.action.vm;

import aliyun.openapi.client.AliInstanceClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.response.CreateInstanceResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewVmAction extends BaseAction {
    private Logger logger = Logger.getLogger(NewVmAction.class);
    private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
    private AppkeyManager appkeyManager = new AppkeyManager();
    private Integer userId = this.getSessionUserID();
    private Appkey appkey;

    //输入的参数
    private String appname;
    private String provider;
    private String regionId;
    private String zoneId;
    private String instanceTypeId;
    private String imageId;
    private String securityGroupId;
    private String instanceName;
    private String instanceChargeLength;
    private String instanceChargeType;
    private String bandWidth;
    private String hostName;
    private String password;
    private String dataDisk;


    //输出的参数
    private Map<String, String> message = new HashMap<>();

    public String newInstance() {
        appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        switch (appkey.getProvider()) {
            case Constants.YUN_HAI:
                yunhai(appkey);
                break;
            case Constants.AMAZON:
                amazon();
                break;
            case Constants.ALI_YUN:
                aliyun();
                break;
            default:
                yunhai(appkey);
        }
        return SUCCESS;
    }

    public String newInstancePage(){
        Integer userId = getSessionUserID();
        List<Appkey> appkeylist = appkeyManager.getAppkeyByUserId(userId);
        return SUCCESS;
    }

    private void amazon() {
    }

    private void aliyun() {
        AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
    }

    private void yunhai(Appkey appkey) {
        CreateInstanceResponse response = instanceClient.CreateInstance(regionId, imageId, instanceTypeId, securityGroupId, zoneId, instanceName,
                null, getPayType(instanceChargeType), instanceChargeLength, null, bandWidth, hostName, password, null, null,
                dataDisk, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
        //根据result插入日志
        Map<String, String> mapLog = new HashMap<>();
        mapLog.put("userId", userId.toString());
        mapLog.put("device", "vm");
        mapLog.put("provider", appkey.getProvider());
        mapLog.put("operateType", "create a instance");
        if (response.getErrorCode() == null) {
            message.put("result", "success");
            message.put("info", "创建主机成功");
            mapLog.put("deviceId", response.getInstanceId());
            mapLog.put("result", "success");
            Log.INFO(mapLog, appkey, regionId,zoneId);
        } else {
            message.put("result", "fail");
            message.put("info", response.getMessage());
            mapLog.put("result", "fail, "+response.getMessage());
            Log.ERROR(mapLog, appkey, regionId,zoneId);
        }
    }

    private String getPayType(String type) {
        String newType;
        switch (type) {
            case "year":
                newType = "PayByYear";
                break;
            case "month":
                newType = "PayByMonth";
                break;
            case "day":
                newType = "PayByDay";
                break;
            default:
                newType = "";//此时创建失败
        }
        return newType;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public void setInstanceTypeId(String instanceTypeId) {
        this.instanceTypeId = instanceTypeId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setInstanceChargeLength(String instanceChargeLength) {
        this.instanceChargeLength = instanceChargeLength;
    }

    public void setInstanceChargeType(String instanceChargeType) {
        this.instanceChargeType = instanceChargeType;
    }

    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDataDisk(String dataDisk) {
        this.dataDisk = dataDisk;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    @Override
    public String toString() {
        return "NewVmAction{" +
                "appname='" + appname + '\'' +
                ", regionId='" + regionId + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", instanceTypeId='" + instanceTypeId + '\'' +
                ", imageId='" + imageId + '\'' +
                ", securityGroupId='" + securityGroupId + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", instanceChargeLength='" + instanceChargeLength + '\'' +
                ", instanceChargeType='" + instanceChargeType + '\'' +
                ", bandWidth='" + bandWidth + '\'' +
                ", hostName='" + hostName + '\'' +
                ", dataDisk='" + dataDisk + '\'' +
                '}';
    }
}