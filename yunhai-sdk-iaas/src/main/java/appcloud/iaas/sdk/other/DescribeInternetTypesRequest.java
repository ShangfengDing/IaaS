package appcloud.iaas.sdk.other;

import appcloud.iaas.sdk.billing.BillingRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeInternetTypesRequest extends BillingRequest {
	public DescribeInternetTypesRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_INTERNET_TYPES);
	}	
}
