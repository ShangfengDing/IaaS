package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class CreateDiskRequest extends RpcYhaiRequest {
	public CreateDiskRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.CREATE_DISK);
	}
	
	private String regionId;
	private String zoneId;
	private String diskSize;
	private String diskChargeType;
	private String diskChargeLength;
	private String diskName;
	private String diskCategory;
	private String description;
	
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
	public String getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
		putQueryParameters("Size",diskSize);
	}
	public String getDiskName() {
		return diskName;
	}
	public void setDiskName(String diskName) {
		this.diskName = diskName;
		putQueryParameters("DiskName",diskName);
	}
	public String getDiskCategory() {
		return diskCategory;
	}
	public void setDiskCategory(String diskCategory) {
		this.diskCategory = diskCategory;
		putQueryParameters("DiskCategory",diskCategory);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		putQueryParameters("Description",description);
	}
	public String getDiskChargeType() {
		return diskChargeType;
	}
	public void setDiskChargeType(String diskChargeType) {
		this.diskChargeType = diskChargeType;
		putQueryParameters("DiskChargeType",diskChargeType);
	}
	public String getDiskChargeLength() {
		return diskChargeLength;
	}
	public void setDiskChargeLength(String diskChargeLength) {
		this.diskChargeLength = diskChargeLength;
		putQueryParameters("DiskChargeLength",diskChargeLength);
	}

}
