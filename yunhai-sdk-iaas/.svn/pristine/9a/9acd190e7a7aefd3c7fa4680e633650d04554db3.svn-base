package appcloud.iaas.sdk.image;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DeleteImageRequest extends RpcYhaiRequest{

	public DeleteImageRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DELETE_IMAGE);
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
