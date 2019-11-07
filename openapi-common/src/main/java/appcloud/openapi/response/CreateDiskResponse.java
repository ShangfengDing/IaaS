package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateDiskResponse")
public class CreateDiskResponse {
	
	private String RequestId;
	private String ErrorCode;
	private String Message;
	
	private String DiskId;
	
	public CreateDiskResponse() {
	}

	public CreateDiskResponse(String requestId, String errorCode, String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	public CreateDiskResponse(String requestId, String diskId) {
		super();
		RequestId = requestId;
		DiskId = diskId;
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

	public String getDiskId() {
		return DiskId;
	}

	public void setDiskId(String diskId) {
		DiskId = diskId;
	}

}
