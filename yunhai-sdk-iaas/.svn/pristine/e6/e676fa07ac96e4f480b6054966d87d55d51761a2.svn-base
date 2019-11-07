package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeDisksRequest extends RpcYhaiRequest {
	public DescribeDisksRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_DISKS);
	}
	
	private String zoneId;
	private String diskIds;
	private String instanceId;
	private String diskType;
	private String diskName;
	private String description;
	private String status;
	private String diskAttachStatus;
	private String pageNumber;
	private String pageSize;
	
	
	@Override
	public void setRegionId(String regionId) {
		super.setRegionId(regionId);
		putQueryParameters("RegionId",regionId);
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
		putQueryParameters("ZoneId",zoneId);
	}
	public String getDiskIds() {
		return diskIds;
	}
	public void setDiskIds(String diskIds) {
		this.diskIds = diskIds;
		putQueryParameters("DiskIds",diskIds);
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		putQueryParameters("Status",status);
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
	public String getDiskAttachStatus() {
		return diskAttachStatus;
	}
	public void setDiskAttachStatus(String diskAttachStatus) {
		this.diskAttachStatus = diskAttachStatus;
		putQueryParameters("DiskAttachStatus",diskAttachStatus);
	}
}
