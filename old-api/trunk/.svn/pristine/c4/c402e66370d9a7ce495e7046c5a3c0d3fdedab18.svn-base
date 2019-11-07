package appcloud.api.client;

import java.util.List;

import appcloud.api.beans.AcMailConf;

import com.sun.jersey.api.client.GenericType;

public class AcMailConfClient extends AbstractClient<AcMailConf>{
	
	public AcMailConfClient(){
		super();
	}
	
	public AcMailConfClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AcMailConf.class;
	}
	
	@Override
	protected GenericType<List<AcMailConf>> getGenericType() {		
		GenericType<List<AcMailConf>> type = new GenericType<List<AcMailConf>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-message-log/mail-conf";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	

	public AcMailConf getMailConf() {
		return super.get(buildPath());
	}
	
	public AcMailConf setMailConf(AcMailConf acMailConf) {
		return super.postWithRet(buildPath(), acMailConf);
	}
}
