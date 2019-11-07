package appcloud.openapi.datatype;

import lombok.Data;

/**
 * Created by Idan on 2018/1/15.
 */
@Data
public class VmUserItem {
    private Integer preId;
    private Integer userId;
    private String userEmail;
    private Boolean active;
    private Integer groupId;
    private String appkeyId;
    private String appkeySecret;
    private Integer enterpriseId;

    public VmUserItem() {}

    public VmUserItem(Integer preId, Integer userId, String userEmail, Boolean active, Integer groupId, String appkeyId, String appkeySecret, Integer enterpriseId) {
        this.preId = preId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.active = active;
        this.groupId = groupId;
        this.appkeyId = appkeyId;
        this.appkeySecret = appkeySecret;
        this.enterpriseId = enterpriseId;
    }
}
