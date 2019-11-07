package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeDiskImageBackRequest extends RpcYhaiRequest {
	public DescribeDiskImageBackRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_DISK_IMAGEBACK);
	}
	
	private String zoneId;
	private String instanceId;
	private String volumeUuid;
	private String activeVolumeUuid;
	private String imageBackStatus;
	private String diskType;
	private String volumeUsageType;
	private String backUp;
	private String top;
	private String diskName;
	
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
		putQueryParameters("VolumeType",diskType);
	}
	public String getDiskName() {
		return diskName;
	}
	public void setDiskName(String diskName) {
		this.diskName = diskName;
		putQueryParameters("DiskName",diskName);
	}

	public String getVolumeUuid() {
		return volumeUuid;
	}

	public void setVolumeUuid(String volumeUuid) {
		this.volumeUuid = volumeUuid;
		putQueryParameters("VolumeId",volumeUuid);
	}

	public String getActiveVolumeUuid() {
		return activeVolumeUuid;
	}

	public void setActiveVolumeUuid(String activeVolumeUuid) {
		this.activeVolumeUuid = activeVolumeUuid;
		putQueryParameters("ActiveVolumeId",activeVolumeUuid);
	}

	public String getImageBackStatus() {
		return imageBackStatus;
	}

	public void setImageBackStatus(String imageBackStatus) {
		this.imageBackStatus = imageBackStatus;
		putQueryParameters("ImageBackStatus",imageBackStatus);
	}

	public String getBackUp() {
		return backUp;
	}

	public void setBackUp(String backUp) {
		this.backUp = backUp;
		putQueryParameters("IsBackUp",backUp);
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
		putQueryParameters("IsTop",top);
	}

	public String getVolumeUsageType() {
		return volumeUsageType;
	}

	public void setVolumeUsageType(String volumeUsageType) {
		this.volumeUsageType = volumeUsageType;
		putQueryParameters("VolumeUsageType",volumeUsageType);
	}
}
