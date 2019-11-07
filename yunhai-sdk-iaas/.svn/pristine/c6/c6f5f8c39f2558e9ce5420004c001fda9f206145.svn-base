package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ModifyDiskImageBackRequest extends RpcYhaiRequest {
	public ModifyDiskImageBackRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.MODIFY_DISK_IMAGEBACK);
	}
	
	private String diskId;
	private String diskName;
	private String volumeType;
    private String volumeUsageType;
	private String imageBackStatus;
	private String backUp;
	private String top;

	public String getDiskId() {
		return diskId;
	}
	public void setDiskId(String diskId) {
		this.diskId = diskId;
		putQueryParameters("VolumeId",diskId);
	}
	public String getDiskName() {
		return diskName;
	}
	public void setDiskName(String diskName) {
		this.diskName = diskName;
		putQueryParameters("DiskName",diskName);
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
		putQueryParameters("VolumeType",volumeType);
	}

	public String getVolumeUsageType() {
		return volumeUsageType;
	}

	public void setVolumeUsageType(String volumeUsageType) {
		this.volumeUsageType = volumeUsageType;
		putQueryParameters("VolumeUsageType",volumeUsageType);
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
}
