package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ModifyInstanceSecurityGroupRequest extends RpcYhaiRequest{
	public ModifyInstanceSecurityGroupRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.MODIFY_INSTANCE_SECURITYGROUP);
	}
	
	private String instanceId;
	private String securityGroupId;
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	public String getSecurityGroupId() {
		return securityGroupId;
	}
	public void setSecurityGroupId(String securityGroupId) {
		this.securityGroupId = securityGroupId;
		putQueryParameters("SecurityGroupId",securityGroupId);
	}
	
}
