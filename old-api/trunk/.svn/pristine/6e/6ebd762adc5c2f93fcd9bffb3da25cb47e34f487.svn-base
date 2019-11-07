package appcloud.api.client;

import java.util.List;

import appcloud.api.beans.AcVlan;

import com.sun.jersey.api.client.GenericType;

public class AcVlanClient  extends AbstractClient<AcVlan>{
	
	public AcVlanClient(){
		super();
	}
	
	public AcVlanClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AcVlan.class;
	}
	
	@Override
	protected GenericType<List<AcVlan>> getGenericType() {		
		GenericType<List<AcVlan>> type = new GenericType<List<AcVlan>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-vlan";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(Integer VlanId) {
		return String.format("%s/%s", buildPath(), VlanId);
	}

	public AcVlan get(Integer VlanId) {
		return super.get(buildPathWithId(VlanId));
	}
	
	public List<AcVlan> getAcVlans() {
		return super.getList(buildPath(), null);
	}
	
}
