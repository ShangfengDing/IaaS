package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;


/**
 * Created by lizhenhao on 2017/12/7.
 */
public class CreateDiskImageBackRequest extends RpcYhaiRequest {

    private String regionId;
    private String zoneId;
    private String diskUuid;
    private String diskName;
    private String description;

    public CreateDiskImageBackRequest() {
        super(Constants.PRODUCT, Constants.VERSION, ActionConstants.CREATE_DISK_IMAGEBACK);

    }

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
    public String getDiskUuid() {
        return diskUuid;
    }
    public void setDiskUuid(String diskUuid) {
        this.diskUuid = diskUuid;
        putQueryParameters("DiskId",diskUuid);
    }
    public String getDiskName() {
        return diskName;
    }
    public void setDiskName(String diskName) {
        this.diskName = diskName;
        putQueryParameters("DiskName",diskName);
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
        putQueryParameters("Description",description);
    }
}
