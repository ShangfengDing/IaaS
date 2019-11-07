package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateResponse")
public class CreateInstanceResponse {
	
	private String RequestId;
	private String ErrorCode;
	private String Message;
	private String InstanceId;
	
	public CreateInstanceResponse() {
	}

	public CreateInstanceResponse(String requestId, String instanceId) {
		RequestId = requestId;
		InstanceId = instanceId;
	}
	
	public CreateInstanceResponse(String requestId, String errorCode, String message) {
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
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

	public String getInstanceId() {
		return InstanceId;
	}
	public void setInstanceId(String instanceId) {
		InstanceId = instanceId;
	}

}
