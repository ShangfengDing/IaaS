package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "CreateSnapshotResponse")
public class CreateSnapshotResponse {
	
	private String RequestId;
	private String ErrorCode;
	private String Message;
	
	private String SnapshotId;
	
	public CreateSnapshotResponse() {
	}

	public CreateSnapshotResponse(String requestId, String errorCode, String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	public CreateSnapshotResponse(String requestId, String snapshotId) {
		super();
		RequestId = requestId;
		SnapshotId = snapshotId;
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
	public String getSnapshotId() {
		return SnapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		SnapshotId = snapshotId;
	}



	
}
