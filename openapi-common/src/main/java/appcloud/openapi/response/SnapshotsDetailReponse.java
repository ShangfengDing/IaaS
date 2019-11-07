package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.openapi.datatype.SnapshotDetailItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeSnapshotsReponse")
public class SnapshotsDetailReponse {
	
	@XmlElementWrapper(name="Snapshots")
	@XmlElement(name="Snapshot")
	private List<SnapshotDetailItem> Snapshots;
	private long TotalCount;
	private String RequestId;
	private String PageNumber;
	private String PageSize;
	
	private String ErrorCode;
	private String Message;

	public SnapshotsDetailReponse() {
		super();
	}

	public SnapshotsDetailReponse(String requestId, String errorCode, String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	
	
	public SnapshotsDetailReponse(String requestId, List<SnapshotDetailItem> snapshots, 
			long totalCount, String pageNum, String pageSize) {
		super();
		RequestId = requestId;
		Snapshots = snapshots;
		TotalCount = totalCount;
		PageNumber = pageNum;
		PageSize = pageSize;
	}
	

	public List<SnapshotDetailItem> getSnapshots() {
		return Snapshots;
	}

	public void setSnapshots(List<SnapshotDetailItem> snapshots) {
		Snapshots = snapshots;
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

	public String getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(String pageNumber) {
		PageNumber = pageNumber;
	}

	public String getPageSize() {
		return PageSize;
	}

	public void setPageSize(String pageSize) {
		PageSize = pageSize;
	} 
	
	
	
	
}
