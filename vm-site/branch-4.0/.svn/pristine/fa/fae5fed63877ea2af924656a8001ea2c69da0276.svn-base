package com.appcloud.vm.action.image;

import aliyun.openapi.client.AliImageClient;
import appcloud.openapi.client.ImageClient;
import appcloud.openapi.response.BaseResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rain on 2016/11/16.
 */
public class EditImageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EditImageAction.class);
    private Integer userId = this.getSessionUserID();

    private AppkeyManager appkeyManager = new AppkeyManager();
    private ImageClient imageClient = OpenClientFactory.getImageClient();
    private AliImageClient aliImageClient = OpenClientFactory.getAliImageClient();
    private Appkey appkey;

    //修改云镜像描述的参数
    private String appname;
    private String regionId;
    private String imageId;
    private String imageName;
    private String imageDes;
    private String imageAccount;
    private String imageSoftware;
    private String result;//修改的结果
    private String zoneId;

    public String execute() {
        logger.info(toString());
        appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname.trim());
        switch (appkey.getProvider()) {
            case Constants.YUN_HAI:
                EditYunImg(appkey);
                break;
            case Constants.ALI_YUN:
                break;
            case Constants.AMAZON:
                break;
        }
        return SUCCESS;
    }

    public String publishImage(){
        appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname.trim());
//        BaseResponse publishImgResponse = imageClient.authorizeImage(regionId,imageId,appkey.getAppkeyId(),appkey.getAppkeySecret(),appkey.getUserEmail());
        return SUCCESS;
    }

    private void EditYunImg(Appkey appkey) {
//        BaseResponse modifyImgResponse = imageClient.ModifyImageAttribute(regionId, imageId, imageName, imageDes,imageSoftware, imageAccount, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
        Map<String, String> mapLog = new HashMap<>();
        mapLog.put("userId", userId.toString());
        mapLog.put("device", "image");
        mapLog.put("deviceId", imageId);
        mapLog.put("provider", appkey.getProvider());
        mapLog.put("operate", "modify image attribute");
//        if (modifyImgResponse.getErrorCode()==null) {
//            result = "success";
//            mapLog.put("result", result);
//            Log.INFO(mapLog, appkey, regionId,zoneId);
//        }else {
//            result = modifyImgResponse.getMessage();
//            mapLog.put("result", result);
//            Log.ERROR(mapLog, appkey, regionId,zoneId);
//        }
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageDes() {
        return imageDes;
    }

    public void setImageDes(String imageDes) {
        this.imageDes = imageDes;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getImageAccount() {
        return imageAccount;
    }

    public void setImageAccount(String imageAccount) {
        this.imageAccount = imageAccount;
    }

    public String getImageSoftware() {
        return imageSoftware;
    }

    public void setImageSoftware(String imageSoftware) {
        this.imageSoftware = imageSoftware;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public String toString() {
        return "EditImageAction{" +
                "appname='" + appname + '\'' +
                ", regionId='" + regionId + '\'' +
                ", imageId='" + imageId + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageDes='" + imageDes + '\'' +
                '}';
    }
}
