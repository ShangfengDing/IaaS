package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import appcloud.openapi.datatype.InstanceMonitorDataType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeDiskMonitorDataResponse")
public class InstancesMonitorInfoReponse {
	
	@XmlElementWrapper(name="MonitorData")
	@XmlElement(name="DiskMonitorData")
	private List<InstanceMonitorDataType> MonitorData;
	private long TotalCount;
	private String RequestId;
	
	private String ErrorCode;
	private String Message;

	public InstancesMonitorInfoReponse() {
		super();
	}
	
	public InstancesMonitorInfoReponse(List<InstanceMonitorDataType> monitorData, 
			String requestId) {
		super();
		MonitorData = monitorData;
		TotalCount = monitorData.size();
		RequestId = requestId;
	}
	

	public InstancesMonitorInfoReponse(List<InstanceMonitorDataType> monitorData, long totalCount,
			String requestId) {
		super();
		MonitorData = monitorData;
		TotalCount = totalCount;
		RequestId = requestId;
	}

	public InstancesMonitorInfoReponse(String requestId, String errorCode,
			String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}

	public List<InstanceMonitorDataType> getMonitorData() {
		return MonitorData;
	}

	public void setMonitorData(List<InstanceMonitorDataType> monitorData) {
		MonitorData = monitorData;
	}

	public long getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(long totalCount) {
		TotalCount = totalCount;
	}

	public String getRequestId() {
		return RequestId;
	}

	public void setRequestId(String requestId) {
		RequestId = requestId;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
}
