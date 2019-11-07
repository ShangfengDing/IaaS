package appcloud.iaas.sdk.other;

import appcloud.iaas.sdk.billing.BillingRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeInstanceTypesRequest extends BillingRequest {
	public DescribeInstanceTypesRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_INSTANCE_TYPES);
	}	
}
