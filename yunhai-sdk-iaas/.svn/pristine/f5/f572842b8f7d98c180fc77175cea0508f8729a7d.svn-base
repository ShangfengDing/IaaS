package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class IsoBootRequest extends RpcYhaiRequest {
	public IsoBootRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.ISO_BOOT);
	}
	
	private String instanceId;
	private String imageId;
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
		putQueryParameters("ImageId",imageId);
	}
	
}
