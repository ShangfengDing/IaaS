package appcloud.iaas.sdk.billing;

import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

/**
 * Created by lizhenhao on 2017/9/23.
 */
public class PayVmBillingRequest extends BillingRequest{

    public PayVmBillingRequest() {
        super(Constants.PRODUCT, Constants.VERSION, ActionConstants.PAY_VM);
    }

    private String instanceType;
    private String instanceChargeType;
    private String instanceChargeLength;
    private String internetChargeType;
    private String internetMaxBandwidthOut;
    private String dataDisk_1_Size;



    public String getInstanceType() {
        return instanceType;
    }
    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
        putQueryParameters("InstanceType",instanceType);
    }

    public String getInstanceChargeType() {
        return instanceChargeType;
    }
    public void setInstanceChargeType(String instanceChargeType) {
        this.instanceChargeType = instanceChargeType;
        putQueryParameters("InstanceChargeType",instanceChargeType);
    }
    public String getInstanceChargeLength() {
        return instanceChargeLength;
    }
    public void setInstanceChargeLength(String instanceChargeLength) {
        this.instanceChargeLength = instanceChargeLength;
        putQueryParameters("InstanceChargeLength",instanceChargeLength);
    }
    public String getInternetChargeType() {
        return internetChargeType;
    }
    public void setInternetChargeType(String internetChargeType) {
        this.internetChargeType = internetChargeType;
        putQueryParameters("InternetChargeType",internetChargeType);
    }
    public String getInternetMaxBandwidthOut() {
        return internetMaxBandwidthOut;
    }
    public void setInternetMaxBandwidthOut(String maxBandwidthOut) {
        this.internetMaxBandwidthOut = maxBandwidthOut;
        putQueryParameters("InternetMaxBandwidthOut",maxBandwidthOut);
    }

    public String getDataDisk_1_Size() {
        return dataDisk_1_Size;
    }
    public void setDataDisk_1_Size(String dataDisk_1_Size) {
        this.dataDisk_1_Size = dataDisk_1_Size;
        putQueryParameters("DataDisk.1.Size",dataDisk_1_Size);
    }
    @Override
    public void setRegionId(String regionId) {
        putQueryParameters("RegionId",regionId);
        super.setRegionId(regionId);
    }

}
