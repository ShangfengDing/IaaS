package appcloud.iaas.sdk.image;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ModifyImageAttributeRequest extends RpcYhaiRequest{

	public ModifyImageAttributeRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.UPDATE_IMAGE);
	}
	
	public String imageId;
	public String imageName;
	public String description;
	public String software;
	public String account;
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
		putQueryParameters("ImageId",imageId);
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
		putQueryParameters("ImageName",imageName);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		putQueryParameters("Description",description);
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
		putQueryParameters("Software",software);
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
		putQueryParameters("Account",account);
	}
}
