package appcloud.api.client;

import java.util.List;


import com.sun.jersey.api.client.GenericType;

public class KeepAliveClient extends AbstractClient<String>{
	
	public KeepAliveClient(){
		super();
	}
	
	public KeepAliveClient(String baseURI) {
		super(baseURI);
	}
	
	private String getPath() {
		return  "keepalive";
	}
	
	protected String buildPath() {
		return String.format("%s", getPath());
	}
	public String KeepAlive() {
		return super.get(buildPath());
	}
	
	public static void main(String[] args) {
		KeepAliveClient ic = new KeepAliveClient();
		ic.KeepAlive();
	}

	@Override
	protected Class<?> getType() {
		return String.class;
	}

	@Override
	protected GenericType<List<String>> getGenericType() {
		GenericType<List<String>> type = new GenericType<List<String>>() {};
		return type;
	}
}
