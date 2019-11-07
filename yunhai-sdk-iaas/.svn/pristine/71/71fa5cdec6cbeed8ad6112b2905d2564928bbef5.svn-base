package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ModifyDiskAttributesRequest extends RpcYhaiRequest {
	public ModifyDiskAttributesRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.MODIFY_DISK_ATTRIBUTE);
	}
	
	private String diskId;
	private String diskName;
	private String description;
	
	public String getDiskId() {
		return diskId;
	}
	public void setDiskId(String diskId) {
		this.diskId = diskId;
		putQueryParameters("DiskId",diskId);
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
