package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;

/**
 * Created by zouji on 2017/11/5.
 */
public class SearchOperateLogRequest extends RpcYhaiRequest{

    public SearchOperateLogRequest() {
        super(appcloud.iaas.sdk.common.Constants.PRODUCT, appcloud.iaas.sdk.common.Constants.VERSION, ActionConstants.SEARCH_OPERATE_LOG);
    }

    private String result;
    private String appkeyId;
    private String userId;
    private String device;
    private String provider;
    private String operateType;
    private String deviceId;
    private String infoType;
    private String createdTime;
    private String btime;
    private String etime;
    private String timeasc;
    private String size;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        this.putQueryParameters(Constants.LOG_RESULT, result);
    }

    public String getAppkeyId() {
        return appkeyId;
    }

    public void setAppkeyId(String appkeyId) {
        this.appkeyId = appkeyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        this.putQueryParameters(Constants.LOG_USERID, userId);
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
        this.putQueryParameters(Constants.LOG_DEVICE, device);
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
        this.putQueryParameters(Constants.LOG_PROVIDER, provider);
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
        this.putQueryParameters(Constants.LOG_OPERATETYPE, operateType);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        this.putQueryParameters(Constants.LOG_DEVICEID, deviceId);
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
        this.putQueryParameters(Constants.LOG_INFOTYPE, infoType);
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
        this.putQueryParameters(Constants.LOG_CREATEDTIME, createdTime);
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
        this.putQueryParameters(Constants.BEGINTIME, btime);
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
        this.putQueryParameters(Constants.ENDTIME, etime);
    }

    public String getTimeasc() {
        return timeasc;
    }

    public void setTimeasc(String timeasc) {
        this.timeasc = timeasc;
        this.putQueryParameters(Constants.TIMEASC, timeasc);
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
        this.putQueryParameters(Constants.SIZE, size);
    }
}
