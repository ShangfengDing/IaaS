package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DeleteDiskRequest extends RpcYhaiRequest {
	public DeleteDiskRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DELETE_DISK);
	}
	
	private String diskId;

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
		putQueryParameters("DiskId",diskId);
	}
	
}
