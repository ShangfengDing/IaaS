package appcloud.iaas.sdk.model;

import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;
import appcloud.core.sdk.common.RpcYhaiRequest;

public class StopInstanceRequest extends RpcYhaiRequest{
	
	public StopInstanceRequest(){
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.STOP_INSTANCE);
	}
	
	private String instanceId;
	private String forceStop;
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	public String getForceStop() {
		return forceStop;
	}
	public void setForceStop(String forceStop) {
		this.forceStop = forceStop;
		putQueryParameters("ForceStop",forceStop);
	}

}
