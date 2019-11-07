package appcloud.iaas.sdk.admin;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class AdminDescribeMonitorRequest extends RpcYhaiRequest {

	public AdminDescribeMonitorRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.ADMIN_MONITOR_INSTANCE_DATA);
	}
	private String regionId;
	private String zoneId;
	private String hostId;
	private String instanceIds;
	private String startTime;
	private String endTime;

	@Override
	public String getRegionId() {
		return regionId;
	}

	@Override
	public void setRegionId(String regionId) {
		this.regionId = regionId;
		putQueryParameters("RegionId",regionId);
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
		putQueryParameters("ZoneId",zoneId);
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
		putQueryParameters("HostId",hostId);
	}

	public String getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(String instanceIds) {
		this.instanceIds = instanceIds;
		putQueryParameters("InstanceIds",instanceIds);
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
}
