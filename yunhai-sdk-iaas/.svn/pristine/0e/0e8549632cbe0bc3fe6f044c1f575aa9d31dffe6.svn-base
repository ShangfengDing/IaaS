package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ResetDiskRequest extends RpcYhaiRequest {
	public ResetDiskRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.RESET_DISK);
	}
	
	private String diskId;
	private String snapshotId;
	
	public String getDiskId() {
		return diskId;
	}
	public void setDiskId(String diskId) {
		this.diskId = diskId;
		putQueryParameters("DiskId",diskId);
	}
	public String getSnapshotId() {
		return snapshotId;
	}
	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
		putQueryParameters("SnapshotId",snapshotId);
	}
}
