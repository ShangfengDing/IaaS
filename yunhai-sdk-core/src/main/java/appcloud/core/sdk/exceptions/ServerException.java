package appcloud.core.sdk.exceptions;

public class ServerException extends ClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7345371390798165336L;

	public ServerException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
		// TODO Auto-generated constructor stub
		this.setErrorType(ErrorType.Server);
	}
	
	public ServerException(String errorCode, String errorMessage, String requestId) {
		this(errorCode, errorMessage);
		this.setRequestId(requestId);
	}

}
