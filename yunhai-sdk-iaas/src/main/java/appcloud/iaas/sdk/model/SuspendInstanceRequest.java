package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class SuspendInstanceRequest extends RpcYhaiRequest {

	public SuspendInstanceRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.SUSPEND_INSTANCE);
	}
	
	private String instanceId;
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	
}
