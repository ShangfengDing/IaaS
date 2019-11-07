package appcloud.iaas.sdk.image;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class GetImageDetailRequest extends RpcYhaiRequest{

	public GetImageDetailRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.GET_IMAGE_DETAIL);
	}
	
	public String imageId;
	
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
		putQueryParameters("ImageId",imageId);
	}
}
