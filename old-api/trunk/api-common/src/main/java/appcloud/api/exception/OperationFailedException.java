package appcloud.api.exception;

import com.sun.jersey.api.Responses;

public class OperationFailedException extends ApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4785847224122005707L;
	public OperationFailedException() {
		this("Operation Failed");
	}
	
	public OperationFailedException (String msg) {
		super(Responses.CONFLICT, msg);
	}
	
}
