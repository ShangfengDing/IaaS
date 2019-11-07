package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GainAppkeyPairResponse")
public class GainAppkeyPairResponse {
	
	private String RequestId;
	private String ErrorCode;
	private String Message;
	private String AppkeyId;
	private String AppkeySecret;
	private Integer userId;
	
	public GainAppkeyPairResponse() {
	}

	public GainAppkeyPairResponse(String requestId, String appkeyId, String appkeySecret, Integer userId,
			String errorCode, String message) {
		RequestId = requestId;
		AppkeyId = appkeyId;
		AppkeySecret = appkeySecret;
		this.userId = userId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	public GainAppkeyPairResponse(String requestId, String errorCode, String message) {
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

	public String getAppkeyId() {
		return AppkeyId;
	}
	public void setAppkeyId(String appkeyId) {
		AppkeyId = appkeyId;
	}

	public String getAppkeySecret() {
		return AppkeySecret;
	}
	public void setAppkeySecret(String appkeySecret) {
		AppkeySecret = appkeySecret;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
