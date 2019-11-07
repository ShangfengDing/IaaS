package appcloud.iaas.sdk.admin;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by rain on 2016/11/22.
 */
public class AdminOnlineMigrateRequest extends RpcYhaiRequest{
    public AdminOnlineMigrateRequest(){
        super(Constants.PRODUCT,Constants.VERSION, ActionConstants.ADMIN_ONLINE_MIGRATE);
    }

    private String regionId;
    private String zoneId;
    private String userId;
    private String hostId;
    private String instanceId;

    @Override
    public String getRegionId() {
        return regionId;
    }

    @Override
    public void setRegionId(String regionId) {
        this.regionId = regionId;
        putQueryParameters("RegionId",regionId);
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
        putQueryParameters("ZoneId",zoneId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        putQueryParameters("UserId",userId);
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
        putQueryParameters("HostId",hostId);
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        putQueryParameters("InstanceId",instanceId);
    }
}
