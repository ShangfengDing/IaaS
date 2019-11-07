package appcloud.api.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.Host;
import appcloud.api.beans.Load;
import appcloud.api.beans.Server;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class HostClient extends AbstractClient<Host>{
	
	public HostClient(){
		super();
	}
	
	public HostClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return Host.class;
	}
	
	@Override
	protected GenericType<List<Host>> getGenericType() {		
		GenericType<List<Host>> type = new GenericType<List<Host>>() {};
		return type;
	}
	
	private String getPath() {
		return  "os-hosts";
	}
	
	//@Override
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	//url "{tenantId}/os-hosts/{host_name}"
	//@Override
	protected String buildPathWithId(String tenantId, String hostName) {
		return String.format("%s/%s", buildPath(tenantId), hostName);
	}

	public Host get(String tenantId, String hostName) {
		return super.get(buildPathWithId(tenantId, hostName));
	}
	
	public List<Host> getHosts(String tenantId) {
		return this.getList(buildPath(tenantId), null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Load> getLoads(String tenantId, String hostName, String type, Date sTime, Date eTime) {
		@SuppressWarnings("rawtypes")
		MultivaluedMap queryParams = new MultivaluedMapImpl();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		queryParams.add("stime", dateFormat.format(sTime));
		queryParams.add("etime", dateFormat.format(eTime));
		queryParams.add("type", type);
		
		List<Load> loads = resource.path(buildMonitorPath(tenantId, hostName)).queryParams(queryParams).get(new GenericType<List<Load>>() {});
		
		return loads;
		
	}

	private String buildMonitorPath(String tenantId, String hostName) {
		return String.format("%s/%s", buildPathWithId(tenantId, hostName), "ac-monitors");
	}
	
}
