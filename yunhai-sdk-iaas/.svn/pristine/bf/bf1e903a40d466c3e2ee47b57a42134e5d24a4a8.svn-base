package appcloud.iaas.sdk.admin;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by lizhenhao on 2016/11/17.
 */
public class AdminDescribeDisksRequest extends RpcYhaiRequest {
    public AdminDescribeDisksRequest(){
        super(Constants.PRODUCT,Constants.VERSION, ActionConstants.ADMIN_DESCRIBE_DISKS);
    }

    private String regionId;
    private String zoneId;
    private String pageNumber;
    private String pageSize;

    private String hostId;
    private String userId;
    private String diskIds;
    private String instanceId;
    private String diskType;
    private String diskName;
    private String description;
    private String diskStatus;
    private String diskAttachStatus;

    @Override
    public String getRegionId() {
        return regionId;

    }

    @Override
    public void setRegionId(String regionId) {
        this.regionId = regionId;
        putQueryParameters("RegionId",regionId);
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
        putQueryParameters("HostId",hostId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        putQueryParameters("UserId",userId);
    }

    public String getDiskIds() {
        return diskIds;

    }

    public void setDiskIds(String diskIds) {
        this.diskIds = diskIds;
        putQueryParameters("DiskIds",diskIds);

    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
        putQueryParameters("PageNumber",pageNumber);

    }

    public String getPageSize() {
        return pageSize;

    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
        putQueryParameters("PageSize",pageSize);

    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
        putQueryParameters("ZoneId",zoneId);

    }

    public String getInstanceId() {
        return instanceId;

    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        putQueryParameters("InstanceId",instanceId);

    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
        putQueryParameters("DiskType",diskType);

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

    public String getDiskStatus() {
        return diskStatus;
    }

    public void setDiskStatus(String diskStatus) {
        this.diskStatus = diskStatus;
        putQueryParameters("DiskStatus",diskStatus);

    }

    public String getDiskAttachStatus() {
        return diskAttachStatus;
    }

    public void setDiskAttachStatus(String diskAttachStatus) {
        this.diskAttachStatus = diskAttachStatus;
        putQueryParameters("DiskAttachStatus",diskAttachStatus);

    }
}
