package appcloud.api.client;

import java.util.List;

import appcloud.api.beans.Hypervisor;

import com.sun.jersey.api.client.GenericType;

public class HypervisorClient  extends AbstractClient<Hypervisor>{
	
	public HypervisorClient(){
		super();
	}
	
	public HypervisorClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return Hypervisor.class;
	}
	
	@Override
	protected GenericType<List<Hypervisor>> getGenericType() {		
		GenericType<List<Hypervisor>> type = new GenericType<List<Hypervisor>>() {};
		return type;
	}
	
	private String getPath() {
		return  "os-hypervisors";
	}
	
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	//查询详细信息的url: "{tenantId}/os-hypervisors/HypervisorHostname/servers"
	protected String buildPathWithId(String tenantId, Integer hypervisorHostname) {
		return String.format("%s/%s/servers", buildPath(tenantId), hypervisorHostname);
	}
	
	public Hypervisor get(String tenantId, Integer hypervisorHostname) {
		return super.get(buildPathWithId(tenantId, hypervisorHostname));
	}
	
	public List<Hypervisor> getHypervisors (String tenantId) {
		return super.getList(buildPath(tenantId), null);
	}
	
}
