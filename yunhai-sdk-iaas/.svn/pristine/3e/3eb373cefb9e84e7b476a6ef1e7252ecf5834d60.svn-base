package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class BackUpVmRequest extends RpcYhaiRequest {

    public BackUpVmRequest() {
        super(Constants.PRODUCT, Constants.VERSION, ActionConstants.BACKUP_VM);
    }

    private String sourceDataCenter;
    private String instanceId;
    private String diskId;
    private boolean needToClean;
    private String accountName;


    public String getSourceDataCenter() {
        return sourceDataCenter;
    }

    public void setSourceDataCenter(String sourceDataCenter) {
        this.sourceDataCenter = sourceDataCenter;
        putQueryParameters("SourceDataCenter",sourceDataCenter);
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        putQueryParameters("InstanceId",instanceId);
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
        putQueryParameters("DiskId",diskId);
    }

    public boolean isNeedToClean() {
        return needToClean;
    }

    public void setNeedToClean(boolean needToClean) {
        this.needToClean = needToClean;
        putQueryParameters("NeedToClean",needToClean);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        putQueryParameters("AccountName",accountName);
    }
}
