package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateSecurityGroupResponse")
public class CreateSecurityGroupResponse {
	
	private String RequestId;
	private String ErrorCode;
	private String Message;
	
	private String SecurityGroupId;
	
	public CreateSecurityGroupResponse() {
	}

	public CreateSecurityGroupResponse(String requestId, String errorCode, String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	public CreateSecurityGroupResponse(String requestId, String securityGroupId) {
		super();
		RequestId = requestId;
		SecurityGroupId = securityGroupId;
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

	public String getSecurityGroupId() {
		return SecurityGroupId;
	}

	public void setSecurityGroupId(String securityGroupId) {
		SecurityGroupId = securityGroupId;
	}

}
