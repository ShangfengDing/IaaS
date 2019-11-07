package appcloud.core.sdk.exceptions;

public class ClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 534996425110290578L;
	
	private String requestId;
	private String errorCode;
	private String errorMessage;
	private ErrorType errorType;
	
	public ClientException(String errorCode, String errorMessage, String requestId) {
		this(errorCode, errorMessage);
		this.requestId = requestId;
	}
	
	public ClientException(String errorCode, String errorMessage) {
		super(errorCode + " : " + errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.setErrorType(ErrorType.Client);
	}
	
	public ClientException(String message) {
		super(message);
	}
	
	public ClientException(Throwable cause) {
		super(cause);
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ErrorType getErrorType() {
		return errorType;
	}
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	@Override
    public String getMessage() {
        return super.getMessage() + (null == getRequestId() ? "" : "\r\nRequestId : " + getRequestId());
    }
}
