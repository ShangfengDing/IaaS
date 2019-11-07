package appcloud.iaas.sdk.image;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class CreateImageRequest extends RpcYhaiRequest{

	public CreateImageRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.CREATE_IMAGE);
	}
	
	public String serverId;
	public String volumeId;
	public String imageName;
	public String software;
	public String account;
	public String description;
	public String distribution;
	
	@Override
	public void setRegionId(String regionId) {
		super.setRegionId(regionId);
		putQueryParameters("RegionId",regionId);
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
		putQueryParameters("ServerId",serverId);
	}
	public String getVolumeId() {
		return volumeId;
	}
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
		putQueryParameters("VolumeId",volumeId);
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
	public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
		putQueryParameters("Distribution",distribution);
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
