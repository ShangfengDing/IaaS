package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class DescribeInstanceMonitorDataRequest extends RpcYhaiRequest {

	public DescribeInstanceMonitorDataRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.DESCRIBE_INSTANCE_MONITOR_DATA);
	}
	
	private String instanceId;
	private String startTime;
	private String endTime;
	private String period;
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
		putQueryParameters("StartTime",startTime);
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
		putQueryParameters("EndTime",endTime);
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
		putQueryParameters("Period",period);
	}
	
	
}
