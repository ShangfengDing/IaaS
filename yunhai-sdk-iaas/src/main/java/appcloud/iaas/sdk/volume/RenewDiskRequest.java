package appcloud.iaas.sdk.volume;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class RenewDiskRequest extends RpcYhaiRequest {
	public RenewDiskRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.RENEW_DISK);
	}
	
	private String diskId;
	private String diskChargeType;
	private String diskChargeLength;

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
		putQueryParameters("DiskId",diskId);
	}

	public String getDiskChargeType() {
		return diskChargeType;
	}

	public void setDiskChargeType(String diskChargeType) {
		this.diskChargeType = diskChargeType;
		putQueryParameters("DiskChargeType",diskChargeType);
	}

	public String getDiskChargeLength() {
		return diskChargeLength;
	}

	public void setDiskChargeLength(String diskChargeLength) {
		this.diskChargeLength = diskChargeLength;
		putQueryParameters("DiskChargeLength",diskChargeLength);
	}
	
}
