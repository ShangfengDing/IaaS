package appcloud.iaas.sdk.region;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeZoneRequest extends RpcYhaiRequest {

	public DescribeZoneRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_ZONES);
	}
	
	private String regionId;
	
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
		putQueryParameters("RegionId",regionId);
	}
}
