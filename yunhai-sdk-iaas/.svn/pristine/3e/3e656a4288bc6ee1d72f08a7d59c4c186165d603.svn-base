package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ModifyInstanceResourceRequest extends RpcYhaiRequest {
	public ModifyInstanceResourceRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.MODIFY_INSTANCE_RESOURCE);
	}
	
	private String instanceId;
	private String cpuNum;
	private String ramSize;
	private String internetMaxBandwidthOut;
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	public String getCpuNum() {
		return cpuNum;
	}
	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
		putQueryParameters("CPUNum",cpuNum);
	}
	public String getRamSize() {
		return ramSize;
	}
	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
		putQueryParameters("RAMSize",ramSize);
	}
	public String getInternetMaxBandwidthOut() {
		return internetMaxBandwidthOut;
	}
	public void setInternetMaxBandwidthOut(String internetMaxBandwidthOut) {
		this.internetMaxBandwidthOut = internetMaxBandwidthOut;
		putQueryParameters("InternetMaxBandwidthOut",internetMaxBandwidthOut);
	}
	
}
