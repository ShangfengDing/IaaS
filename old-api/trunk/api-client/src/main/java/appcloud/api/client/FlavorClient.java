package appcloud.api.client;

import java.util.List;

import appcloud.api.beans.Flavor;

import com.sun.jersey.api.client.GenericType;

public class FlavorClient  extends AbstractClient<Flavor>{
	
	public FlavorClient(){
		super();
	}
	
	public FlavorClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return Flavor.class;
	}
	
	@Override
	protected GenericType<List<Flavor>> getGenericType() {		
		GenericType<List<Flavor>> type = new GenericType<List<Flavor>>() {};
		return type;
	}
	
	private String getPath() {
		return  "flavors";
	}
	
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	protected String buildPathWithId(String tenantId, Integer flavorId) {
		return String.format("%s/%s", buildPath(tenantId), flavorId);
	}

	public Flavor get(String tenantId, Integer flavorId) {
		return super.get(buildPathWithId(tenantId, flavorId));
	}
	
	public List<Flavor> getFlavors(String tenantId, boolean detailed) {
		if(detailed)
			return this.getList(buildPath(tenantId) + "/detail", null);
		return super.getList(buildPath(tenantId), null);
	}

	public Flavor createFlavor(String tenantId, Flavor flavor) {
		return super.postWithRet(buildPath(tenantId), flavor);
	}
}
