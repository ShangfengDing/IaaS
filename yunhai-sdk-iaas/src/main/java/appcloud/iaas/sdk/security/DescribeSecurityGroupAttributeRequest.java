package appcloud.iaas.sdk.security;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeSecurityGroupAttributeRequest extends RpcYhaiRequest{
	public DescribeSecurityGroupAttributeRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_SECURITY_GROUP_ATTRIBUTE);
	}
	
	private String securityGroupId;
	private String regionId;
	
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
	
}
