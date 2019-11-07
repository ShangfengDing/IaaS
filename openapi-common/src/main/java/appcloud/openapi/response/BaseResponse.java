package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BaseResponse")
public class BaseResponse {
	
	private String RequestId;
	private String ErrorCode;
	private String Message;
	
	protected BaseResponse() {
	}

	public BaseResponse(String requestId) {
		RequestId = requestId;
	}
	
	public BaseResponse(String requestId, String errorCode, String message) {
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

}
