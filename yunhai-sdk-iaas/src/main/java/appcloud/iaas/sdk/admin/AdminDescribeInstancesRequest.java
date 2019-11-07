package appcloud.iaas.sdk.admin;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by lizhenhao on 2016/11/17.
 */
public class AdminDescribeInstancesRequest extends RpcYhaiRequest {
    public AdminDescribeInstancesRequest() {
        super(Constants.PRODUCT,Constants.VERSION, ActionConstants.ADMIN_DESCRIBE_INSTANCES);
    }

    private String regionId;
    private String zoneId;
    private String instanceIds;
    private String instanceType;
    private String innerIpAddresses;
    private String publicIpAddresses;
    private String securityGroupId;
    private String instanceName;
    private String status;
    private String imageId;
    private String pageNumber;
    private String pageSize;
    private String userId;
    private String hostId;

    public String getRegionId() {
        return regionId;
    }

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

    public String getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(String instanceIds) {
        this.instanceIds = instanceIds;
        putQueryParameters("InstanceIds",instanceIds);
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
        putQueryParameters("InstanceType",instanceType);

    }

    public String getInnerIpAddresses() {
        return innerIpAddresses;
    }

    public void setInnerIpAddresses(String innerIpAddresses) {
        this.innerIpAddresses = innerIpAddresses;
        putQueryParameters("InnerIpAddresses",innerIpAddresses);

    }

    public String getPublicIpAddresses() {
        return publicIpAddresses;
    }

    public void setPublicIpAddresses(String publicIpAddresses) {
        this.publicIpAddresses = publicIpAddresses;
        putQueryParameters("PublicIpAddresses",publicIpAddresses);

    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
        putQueryParameters("SecurityGroupId",securityGroupId);

    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
        putQueryParameters("InstanceName",instanceName);

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        putQueryParameters("Status",status);

    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
        putQueryParameters("ImageId",imageId);

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
}
