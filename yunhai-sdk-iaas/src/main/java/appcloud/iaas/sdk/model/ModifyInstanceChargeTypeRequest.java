package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ModifyInstanceChargeTypeRequest extends RpcYhaiRequest {
	public ModifyInstanceChargeTypeRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.MODIFY_INSTANCE_CHARGETYPE);
	}
	
	private String instanceId;
	private String instanceChargeType;
	private String InstanceChargeLength;
	
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
		return InstanceChargeLength;
	}
	public void setInstanceChargeLength(String instanceChargeLength) {
		InstanceChargeLength = instanceChargeLength;
		putQueryParameters("InstanceChargeLength",instanceChargeLength);
	}
	
}
