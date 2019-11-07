package appcloud.iaas.sdk.snapshot;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class CreateSnapshotRequest extends RpcYhaiRequest{
	public CreateSnapshotRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.CREATE_SNAPSHOT);
	}
	
	private String diskId;
	private String snapshotName;
	private String description;
	private String clientToken;
	
	public String getDiskId() {
		return diskId;
	}
	
	public void setDiskId(String diskId) {
		this.diskId = diskId;
		putQueryParameters("DiskId",diskId);
	}
	
	public String getSnapshotName() {
		return snapshotName;
	}
	
	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
		putQueryParameters("SnapshotName",snapshotName);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
		putQueryParameters("Description",description);
	}
	
	public String getClientToken() {
		return clientToken;
	}
	
	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
		putQueryParameters("ClientToken",clientToken);
	}
	
}
