package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import appcloud.openapi.datatype.SecurityGroupDetailItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeSecurityGroupsResponse")
public class SecurityGroupsDetailReponse {
	
	@XmlElementWrapper(name="SecurityGroups")
	@XmlElement(name="SecurityGroup")
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
	
	public List<SecurityGroupDetailItem> getSecurityGroups() {
		return SecurityGroups;
	}

	public void setSecurityGroups(List<SecurityGroupDetailItem> securityGroups) {
		SecurityGroups = securityGroups;
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
