package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DeleteInstanceRequest extends RpcYhaiRequest {

	public DeleteInstanceRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DELETE_INSTANCE);
	}
	
	private String instanceId;
	private String forceDelete;
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	public String getForceDelete() {
		return forceDelete;
	}
	public void setForceDelete(String forceDelete) {
		this.forceDelete = forceDelete;
		putQueryParameters("ForceDelete",forceDelete);
	}
	
}
