package appcloud.openapi.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by zouji on 2017/11/1.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OperateLogItem {

    private String Result;
    private String AppkeyId;
    private String UserId;
    private String Device;
    private String Provider;
    private String OperateType;
    private String DeviceId;
    private String InfoType;
    private String CreatedTime;

    public OperateLogItem() {}

    public OperateLogItem(String result, String appkeyId, String userId, String device, String provider, String operateType, String deviceId, String infoType, String createdTime) {
        Result = result;
        AppkeyId = appkeyId;
        UserId = userId;
        Device = device;
        Provider = provider;
        OperateType = operateType;
        DeviceId = deviceId;
        InfoType = infoType;
        CreatedTime = createdTime;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getAppkeyId() {
        return AppkeyId;
    }

    public void setAppkeyId(String appkeyId) {
        AppkeyId = appkeyId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public String getOperateType() {
        return OperateType;
    }

    public void setOperateType(String operateType) {
        OperateType = operateType;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getInfoType() {
        return InfoType;
    }

    public void setInfoType(String infoType) {
        InfoType = infoType;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }
}
