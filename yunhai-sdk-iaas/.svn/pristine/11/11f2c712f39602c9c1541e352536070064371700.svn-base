package appcloud.iaas.sdk.other;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class GainAppkeyPairRequest extends RpcYhaiRequest {
	public GainAppkeyPairRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.GAIN_APPKEY_PAIR);
	}
	
	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		putQueryParameters("AccessToken",accessToken);
	}
	
}
