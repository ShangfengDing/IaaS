package com.appcloud.vm.action.vm;


import aliyun.openapi.client.AliImageClient;
import appcloud.openapi.client.ImageClient;
import appcloud.openapi.response.CreateImageResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

/**
 * 将某个云主机发布为模板，即为创建自定义镜像。
 *<table border="1">
 *     <tr>
 *         <td>
 *             名称
 *         </td>
 *          <td>
 *             类型
 *         </td>
 *          <td>
 *             描述
 *         </td>
 *           <td>
 *             阿里云(必填？)
 *         </td>
 *           <td>
 *             云海(必填？)
 *         </td>
 *     </tr>
 * </table>
 * 请求参数
 * 名称	         类型	描述	                             阿里云(必填？)云海(必填？)
 * Action	    String	操作接口名，系统规定参数，取值为CreateImage	Y	    Y
 * RegionId	    String	镜像所在的 Region ID	                    Y
 * ZoneId	    String	镜像所在的 Zone ID		                            Y
 * SnapshotId	String	快照 ID。从指定的快照创建自定义镜像。	    Y
 * ServerId	    String	硬盘所在实例的ID。		                            Y
 * VolumeId	    String	硬盘ID。从指定的硬盘创建镜像		                Y
 * ImageName	String	镜像名称，[2, 128] 英文或中文字符，
 * 必须以大小字母或中文开头，可包含数字，”_”或”-”。
 * 不能以 http:// 和 https:// 开头。	                            N	    N
 * ImageVersion	String	镜像版本号，长度限制在 1 ~ 40 个英文字符	N
 * Description	String	镜像的描述信息，长度限制在 0 ~ 256 个字符
 * ，不填则为空，默认为空。不能以 http:// 和 https:// 开头。	    N	    N
 * Distribution	String	我也不知道是干嘛的		                            N
 *
 * 返回参数
 * 名称	    类型	描述
 * ImageId	String	创建成功的镜像uuid
 *
 * @author 包鑫彤
 * @create 2016-07-09-10:57
 * @since 1.0.0
 */
public class NewImgAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(NewImgAction.class);
    private ImageClient imageClient = OpenClientFactory.getImageClient();	    //要发布模板的serverId
    private AliImageClient aliImageClient = OpenClientFactory.getAliImageClient();
    private Integer userId = this.getSessionUserID();

    private String appname;     //用来判断提供商
    private String regionId;     //镜像所在的 RegionID
    private String zoneId;       //镜像所在的 ZoneID
    private String snapshotId;    //快照 ID,从指定的快照创建自定义镜像。
    private String serverId;     //硬盘所在实例的ID
    private String volumeId;     //硬盘ID,从指定的硬盘创建镜像.
    private String imageName;    //镜像名称，[2, 128] 英文或中文字符，必须以大小字母或中文开头，可包含数字，”_”或”-”。不能以 http:// 和 https:// 开头
    private String imageVersion; //镜像版本号，长度限制在 1 ~ 40 个英文字符
    private String imageDescription;  //镜像的描述信息，长度限制在 0 ~ 256 个字符，不填则为空，默认为空。不能以 http:// 和 https:// 开头
    private String distribution; //待定
    private String userEmail;
    private String imageSoftware;
    private String imageAccount;

    private AppkeyManager appkeyManager = new AppkeyManager();
    private Appkey appkey;
    private String result;       //返回的结果信息
    private String isPublic;

    public String execute() {
        logger.info(toString());
        appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appname);
        switch (appkey.getProvider()){
            case Constants.YUN_HAI:
                createYunHaiImage(appkey);
                break;
            case Constants.ALI_YUN:
                createAliyunImage(appkey);
                break;
            case Constants.AMAZON:
                createAmazonImage(appkey);
                break;
        }
      return SUCCESS;
    }

    public String createYunHaiImage(Appkey appkey){
        CreateImageResponse createImageResponse = imageClient.createImage(regionId,zoneId,null,serverId,volumeId,imageName,
                imageVersion,imageDescription,distribution,imageSoftware, imageAccount, appkey.getAppkeyId(),appkey.getAppkeySecret(),appkey.getUserEmail());
        if (createImageResponse.getErrorCode()==null){
            result="success";
        }else {
            result = createImageResponse.getMessage();
        }
        return SUCCESS;
    }

    public String createAliyunImage(Appkey appkey){
        try {
            com.aliyuncs.ecs.model.v20140526.CreateImageResponse createAliImageResponse = aliImageClient.createAliImage(regionId,snapshotId,imageName,imageVersion,
                    imageDescription,appkey.getAppkeyId(),appkey.getAppkeySecret(), null);
            logger.info("aliImg"+ReflectionToStringBuilder.toString(createAliImageResponse));
            result = "success";
        } catch (ClientException e) {
            result = e.getErrMsg();
        }
        return SUCCESS;
    }
    public String createAmazonImage(Appkey appkey){return "";}

    public String getRegionId() {
        return regionId;
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

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageVersion() {
        return imageVersion;
    }

    public void setImageVersion(String imageVersion) {
        this.imageVersion = imageVersion;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getImageSoftware() {
        return imageSoftware;
    }

    public void setImageSoftware(String imageSoftware) {
        this.imageSoftware = imageSoftware;
    }

    public String getImageAccount() {
        return imageAccount;
    }

    public void setImageAccount(String imageAccount) {
        this.imageAccount = imageAccount;
    }

    @Override
    public String toString() {
        return "NewImgAction{" +
                "isPublic='" + isPublic + '\'' +
                ", appname='" + appname + '\'' +
                ", regionId='" + regionId + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", snapshotId='" + snapshotId + '\'' +
                ", serverId='" + serverId + '\'' +
                ", volumeId='" + volumeId + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageVersion='" + imageVersion + '\'' +
                ", imageDescription='" + imageDescription + '\'' +
                ", distribution='" + distribution + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", appkeyManager=" + appkeyManager +
                ", appkey=" + appkey +
                ", result='" + result + '\'' +
                ", imageClient=" + imageClient +
                '}';
    }
}

