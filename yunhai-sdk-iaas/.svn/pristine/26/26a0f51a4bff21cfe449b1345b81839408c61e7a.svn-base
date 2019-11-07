package appcloud.iaas.sdk.account;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by Idan on 2017/10/19.
 */
public class UserCreateRequest extends RpcYhaiRequest {

    public UserCreateRequest() {
        super(Constants.PRODUCT,Constants.VERSION, ActionConstants.USER_CREATE);
    }

    public String newUserEmail;
    public String groupSecretKey;

    public String getNewUserEmail() {
        return newUserEmail;
    }

    public void setNewUserEmail(String newUserEmail) {
        this.newUserEmail = newUserEmail;
        putQueryParameters("NewUserEmail",newUserEmail);
    }

    public String getGroupSecretKey() {
        return groupSecretKey;
    }

    public void setGroupSecretKey(String groupSecretKey) {
        this.groupSecretKey = groupSecretKey;
        putQueryParameters("GroupSecretKey",groupSecretKey);
    }
}
