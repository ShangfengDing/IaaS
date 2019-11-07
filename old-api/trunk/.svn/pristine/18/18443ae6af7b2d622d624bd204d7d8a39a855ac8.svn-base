package appcloud.api.exception;

import com.sun.jersey.api.Responses;

public class ItemNotFoundException extends ApiException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6455619733097267072L;
	
	public ItemNotFoundException() {
		this("Item Not Found");
	}
	
	public ItemNotFoundException (String msg) {
		super(Responses.NOT_FOUND, msg);
	}
}
