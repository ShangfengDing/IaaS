package appcloud.iaas.sdk.security;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeSecurityGroupsRequest extends RpcYhaiRequest{
	public DescribeSecurityGroupsRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_SECURITY_GROUPS);
	}
	
	private String regionId;
	private String pageNumber;
	private String pageSize;
	private String vpcId;
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
		putQueryParameters("RegionId",regionId);
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
		putQueryParameters("PageNumber",pageNumber);
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
		putQueryParameters("PageSize",pageSize);
	}
	public String getVpcId() {
		return vpcId;
	}
	public void setVpcId(String vpcId) {
		this.vpcId = vpcId;
		putQueryParameters("VpcId",vpcId);
	}
	
}
