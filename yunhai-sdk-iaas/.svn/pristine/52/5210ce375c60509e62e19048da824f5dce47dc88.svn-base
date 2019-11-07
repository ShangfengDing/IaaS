package appcloud.iaas.sdk.admin;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by lizhenhao on 2016/11/17.
 */
public class AdminDescribeHostsRequest extends RpcYhaiRequest {
    public AdminDescribeHostsRequest(){
        super(Constants.PRODUCT,Constants.VERSION, ActionConstants.ADMIN_DESCRIBE_HOSTS);
    }

    private String regionId;
    private String zoneId;
    private String pageNumber;
    private String pageSize;

    private String hostIp;
    private String hostUuid;
    private String hostType;
    private String hostStatus;

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

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
        putQueryParameters("HostIp",hostIp);

    }

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
        putQueryParameters("HostUuid",hostUuid);

    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
        putQueryParameters("HostType",hostType);

    }

    public String getHostStatus() {
        return hostStatus;
    }

    public void setHostStatus(String hostStatus) {
        this.hostStatus = hostStatus;
        putQueryParameters("HostStatus",hostStatus);

    }
}
