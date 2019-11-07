package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import appcloud.openapi.datatype.SecurityGroupDetailItem;

@XmlRootElement(name = "DescribeSecurityGroupsResponse")
public class SecurityGroupsDetailReponse {
	
	private List<SecurityGroupDetailItem> SecurityGroups;
	private long TotalCount;
	private String RequestId;
	private String PageNumber;
	private String PageSize;
	
	private String ErrorCode;
	private String Message;

	public SecurityGroupsDetailReponse() {
		super();
	}

	public SecurityGroupsDetailReponse(String requestId, String errorCode, String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	
	
	public SecurityGroupsDetailReponse(String requestId, List<SecurityGroupDetailItem> securityGroup, 
			long totalCount, String pageNum, String pageSize) {
		super();
		RequestId = requestId;
		SecurityGroups = securityGroup;
		TotalCount = totalCount;
		PageNumber = pageNum;
		PageSize = pageSize;
	}
	

	@XmlElementWrapper(name="SecurityGroups")
	@XmlElement(name="SecurityGroup")
	public List<SecurityGroupDetailItem> getSecurityGroups() {
		return SecurityGroups;
	}

	public void setSecurityGroups(List<SecurityGroupDetailItem> securityGroups) {
		SecurityGroups = securityGroups;
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
