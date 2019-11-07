package appcloud.iaas.sdk.billing;

import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by lizhenhao on 2017/9/23.
 */
public class PayHdBillingRequest extends BillingRequest{

    public PayHdBillingRequest() {
        super(Constants.PRODUCT, Constants.VERSION, ActionConstants.PAY_HD);
    }

    private String diskSize;
    private String diskChargeType;
    private String diskChargeLength;
    private String diskCategory;


    public String getDiskSize() {
        return diskSize;
    }
    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
        putQueryParameters("Size",diskSize);
    }

    public String getDiskCategory() {
        return diskCategory;
    }
    public void setDiskCategory(String diskCategory) {
        this.diskCategory = diskCategory;
        putQueryParameters("DiskCategory",diskCategory);
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
