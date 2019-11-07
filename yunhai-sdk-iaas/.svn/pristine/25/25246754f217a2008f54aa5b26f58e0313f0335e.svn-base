package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;


public class RenewInstanceRequest extends RpcYhaiRequest{
	public RenewInstanceRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.RENEW_INSTANCE);
	}
	
	private String instanceId;
	private String instanceChargeType;
	private String instanceChargeLength;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}

	public String getInstanceChargeType() {
		return instanceChargeType;
	}

	public void setInstanceChargeType(String instanceChargeType) {
		this.instanceChargeType = instanceChargeType;
		putQueryParameters("InstanceChargeType",instanceChargeType);
	}

	public String getInstanceChargeLength() {
		return instanceChargeLength;
	}

	public void setInstanceChargeLength(String instanceChargeLength) {
		this.instanceChargeLength = instanceChargeLength;
		putQueryParameters("InstanceChargeLength",instanceChargeLength);
	}
	
}
