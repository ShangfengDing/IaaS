package appcloud.iaas.sdk.account;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by Idan on 2017/10/19.
 */
public class GroupCreateRequest extends RpcYhaiRequest {

    public GroupCreateRequest() {
        super(Constants.PRODUCT,Constants.VERSION, ActionConstants.GROUP_CREATE);
    }

    public String name;

    public String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        putQueryParameters("GroupName",name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        putQueryParameters("GroupDescription",description);
    }
}
