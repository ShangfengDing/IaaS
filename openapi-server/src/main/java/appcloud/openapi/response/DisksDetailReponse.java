package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.openapi.datatype.DiskDetailItem;

@XmlRootElement(name = "DescribeDisksReponse")
public class DisksDetailReponse {
	
	private List<DiskDetailItem> Disks;
	private long TotalCount;
	private String RequestId;
	private String PageNumber;
	private String PageSize;
	
	private String ErrorCode;
	private String Message;

	public DisksDetailReponse() {
		super();
	}

	public DisksDetailReponse(String requestId, String errorCode, String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	
	
	public DisksDetailReponse(String requestId, List<DiskDetailItem> disks, 
			long totalCount, String pageNum, String pageSize) {
		super();
		RequestId = requestId;
		Disks = disks;
		TotalCount = totalCount;
		PageNumber = pageNum;
		PageSize = pageSize;
	}
	

	@XmlElementWrapper(name="Disks")
	@XmlElement(name="Disk")
	public List<DiskDetailItem> getDisks() {
		return Disks;
	}

	public void setDisks(List<DiskDetailItem> disks) {
		Disks = disks;
	}
	
	@XmlElement
	public long getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(long totalCount) {
		TotalCount = totalCount;
	}

	@XmlElement
	public String getRequestId() {
		return RequestId;
	}

	public void setRequestId(String requestId) {
		RequestId = requestId;
	}

	@XmlElement
	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	@XmlElement
	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	@XmlElement
	public String getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(String pageNumber) {
		PageNumber = pageNumber;
	}

	@XmlElement
	public String getPageSize() {
		return PageSize;
	}

	public void setPageSize(String pageSize) {
		PageSize = pageSize;
	} 
	
	
	
	
}
