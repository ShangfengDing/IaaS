package appcloud.iaas.sdk.security;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ModifySecurityGroupAttributeRequest extends RpcYhaiRequest{
	public ModifySecurityGroupAttributeRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.MODIFY_SECURITY_GROUP_ATTRIBUTE);
	}
	
	private String securityGroupId;
	private String regionId;
	private String securityGroupName;
	private String description;
	
	public String getSecurityGroupId() {
		return securityGroupId;
	}
	public void setSecurityGroupId(String securityGroupId) {
		this.securityGroupId = securityGroupId;
		putQueryParameters("SecurityGroupId",securityGroupId);
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
		putQueryParameters("RegionId",regionId);
	}
	public String getSecurityGroupName() {
		return securityGroupName;
	}
	public void setSecurityGroupName(String securityGroupName) {
		this.securityGroupName = securityGroupName;
		putQueryParameters("SecurityGroupName",securityGroupName);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		putQueryParameters("Description",description);
	}
	
}
