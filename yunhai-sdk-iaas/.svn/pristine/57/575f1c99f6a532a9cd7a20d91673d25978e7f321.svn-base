package appcloud.iaas.sdk.security;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class CreateSecurityGroupRequest extends RpcYhaiRequest{
	public CreateSecurityGroupRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.CREATE_SECURITY_GROUP);
	}
	
	private String regionId;
	private String securityGroupName;
	private String description;
	private String vpcId;
	
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
	
	public String getVpcId() {
		return vpcId;
	}

	public void setVpcId(String vpcId) {
		this.vpcId = vpcId;
		putQueryParameters("VpcId",vpcId);
	}
	
}
