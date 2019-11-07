package appcloud.iaas.sdk.account;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by Idan on 2017/10/19.
 */
public class UserCreateForDisRequest extends RpcYhaiRequest {

    public UserCreateForDisRequest() {
        super(Constants.PRODUCT,Constants.VERSION, ActionConstants.USER_CREATE_FOR_DIS);
    }

    public String regionId;
    public String accountName;
    public String zoneId;
    public String newUserEmail;
    public String groupSecretKey;

    @Override
    public String getRegionId() {
        return regionId;
    }

    @Override
    public void setRegionId(String regionId) {
        this.regionId = regionId;
        putQueryParameters("RegionId",regionId);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        putQueryParameters("AccountName",accountName);
    }

    @Override
    public String getZoneId() {
        return zoneId;
    }

    @Override
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
        putQueryParameters("ZoneId",zoneId);
    }

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
