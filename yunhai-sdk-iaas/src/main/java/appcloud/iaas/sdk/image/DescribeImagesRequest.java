package appcloud.iaas.sdk.image;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeImagesRequest extends RpcYhaiRequest{

	public DescribeImagesRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.GET_IMAGE_LIST_WITH_DETAIL);
	}

	public String imageName;
	public String imageType;
	public String imageDescription;
	public String distribution;
	public String software;
	public String type;
	public String status;
	public String pageNumber;
	public String pageSize;

	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
		putQueryParameters("ImageType",imageType);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		putQueryParameters("Status",status);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		putQueryParameters("Type",type);
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

	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
		putQueryParameters("ImageName",imageName);
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

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
		putQueryParameters("Description",imageDescription);
	}
}
