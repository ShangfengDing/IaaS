package appcloud.openapi.datatype;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Idan on 2017/10/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDetailItem {

    private String userId;
    private String userEmail;
    public Boolean isActive;
    private Integer groupId;
    private String appkeyId;
    private String appkeySecret;
    public Integer enterpriseId;

    public UserDetailItem() {
    }

    public UserDetailItem(String userId, String userEmail, Boolean isActive, Integer groupId, String appkeyId, String appkeySecret, Integer enterpriseId) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.isActive = isActive;
        this.groupId = groupId;
        this.appkeyId = appkeyId;
        this.appkeySecret = appkeySecret;
        this.enterpriseId = enterpriseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getAppkeyId() {
        return appkeyId;
    }

    public void setAppkeyId(String appkeyId) {
        this.appkeyId = appkeyId;
    }

    public String getAppkeySecret() {
        return appkeySecret;
    }

    public void setAppkeySecret(String appkeySecret) {
        this.appkeySecret = appkeySecret;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
