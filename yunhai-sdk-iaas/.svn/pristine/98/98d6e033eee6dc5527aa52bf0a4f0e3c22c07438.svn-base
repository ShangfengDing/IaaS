package appcloud.iaas.sdk.snapshot;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DeleteSnapshotRequest extends RpcYhaiRequest{
	public DeleteSnapshotRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DELETE_SNAPSHOT);
	}
	
	private String snapshotId;

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
		putQueryParameters("SnapshotId",snapshotId);
	}
	
}
