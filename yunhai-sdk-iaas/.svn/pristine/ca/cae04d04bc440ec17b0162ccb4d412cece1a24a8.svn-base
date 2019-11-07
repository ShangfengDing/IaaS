package appcloud.iaas.sdk.snapshot;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeSnapshotsRequest extends RpcYhaiRequest{
	public DescribeSnapshotsRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_SNAPSHOTS);
	}
	
	private String diskId;
	private String snapshotIds;
	private String instanceId;
	private String snapshotName;
	private String sourceDiskType;
	private String snapshotType;
	private String description;
	private String status;
	private String usage;
	private String pageNumber;
	private String pageSize;
	
	@Override
	public void setRegionId(String regionId) {
		super.setRegionId(regionId);
		putQueryParameters("RegionId",regionId);
	}
	public String getDiskId() {
		return diskId;
	}
	public void setDiskId(String diskId) {
		this.diskId = diskId;
		putQueryParameters("DiskId",diskId);
	}
	
	public String getSnapshotIds() {
		return snapshotIds;
	}
	public void setSnapshotIds(String snapshotIds) {
		this.snapshotIds = snapshotIds;
		putQueryParameters("SnapshotIds",snapshotIds);
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	
	public String getSourceDiskType() {
		return sourceDiskType;
	}
	public void setSourceDiskType(String sourceDiskType) {
		this.sourceDiskType = sourceDiskType;
		putQueryParameters("SourceDiskType",sourceDiskType);
	}
	public String getSnapshotType() {
		return snapshotType;
	}
	public void setSnapshotType(String snapshotType) {
		this.snapshotType = snapshotType;
		putQueryParameters("SnapshotType",snapshotType);
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
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
		putQueryParameters("Usage",usage);
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
	public String getSnapshotName() {
		return snapshotName;
	}
	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
		putQueryParameters("SnapshotName",snapshotName);
	}
	
	
}
