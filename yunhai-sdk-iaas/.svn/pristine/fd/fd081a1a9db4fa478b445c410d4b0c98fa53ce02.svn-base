package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class AttachDiskRequest extends RpcYhaiRequest {
	public AttachDiskRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.ATTACH_DISK);
	}
	
	private String diskId;
	private String instanceId;
	
	public String getDiskId() {
		return diskId;
	}
	public void setDiskId(String diskId) {
		this.diskId = diskId;
		putQueryParameters("DiskId",diskId);
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}

}
