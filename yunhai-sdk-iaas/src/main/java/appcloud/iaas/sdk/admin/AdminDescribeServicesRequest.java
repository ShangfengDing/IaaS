package appcloud.iaas.sdk.admin;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by lizhenhao on 2016/11/17.
 */
public class AdminDescribeServicesRequest extends RpcYhaiRequest{
    public AdminDescribeServicesRequest(){
        super(Constants.PRODUCT,Constants.VERSION, ActionConstants.ADMIN_DESCRIBE_SERVICES);
    }

    private String regionId;
    private String zoneId;
    private String pageNumber;
    private String pageSize;

    private String hostIp;
    private String hostUuids;
    private String serviceType;
    private String serviceStatus;


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

    public String getHostUuids() {
        return hostUuids;
    }

    public void setHostUuids(String hostUuids) {
        this.hostUuids = hostUuids;
        putQueryParameters("HostUuids",hostUuids);

    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
        putQueryParameters("ServiceType",serviceType);

    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
        putQueryParameters("ServiceStatus",serviceStatus);

    }

}
