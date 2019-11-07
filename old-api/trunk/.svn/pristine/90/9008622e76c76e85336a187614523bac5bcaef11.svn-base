package appcloud.api.client;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.AcHost;
import appcloud.api.beans.Load;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AcHostClient extends AbstractClient<AcHost>{
	
	public AcHostClient(){
		super();
		loadClient = new AcHostLoadClient();
	}
	
	public AcHostClient(String baseURI) {
		super(baseURI);
		loadClient = new AcHostLoadClient(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AcHost.class;
	}
	
	@Override
	protected GenericType<List<AcHost>> getGenericType() {		
		GenericType<List<AcHost>> type = new GenericType<List<AcHost>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-hosts";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(String hostId) {
		return String.format("%s/%s", buildPath(), hostId);
	}

	public AcHost get(String hostId) {
		return super.get(buildPathWithId(hostId));
	}
	
	public List<AcHost> getAcHosts() {
		return super.getList(buildPath(), null);
	}
	
	public List<AcHost> getAcHostOfAggregate(Integer aggregateId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(aggregateId != null)
			params.add("aggregate_id", String.valueOf(aggregateId));
		return super.getList(buildPath(), params);
	}
	
	public List<AcHost> getAcHostOfZone(Integer zoneId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(zoneId != null)
			params.add("zone_id", String.valueOf(zoneId));
		return super.getList(buildPath(), params);
	}
	
	private AcHostLoadClient loadClient;
	public List<Load> getLoads(String hostId, String type, Date sTime, Date eTime) {
		List<Load> loads = loadClient.getLoads(hostId, type, sTime, eTime);				
		return loads;
		
	}
	
	public Load getCurrentLoad(String hostId) {
		return loadClient.getCurrentLoad(hostId);
	}
	
	public List<AcHost> getHostsByProperties(String tenantId, String userId, String ip, 
			String type, String status, Integer zid, Integer aid, String service, 
			Integer cpuOperator, Integer cpuCount, Integer memoryOperator, 
			Integer memoryCount, Integer diskOperator, Integer diskCount,
			Integer page, Integer size){
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(tenantId != null)
			params.add("tenant_id", tenantId);
		if(userId != null)
			params.add("user_id", userId);
		if(ip != null)
			params.add("ip", ip);
		if(type != null)
			params.add("type", type);
		if(status != null)
			params.add("status", status); 
		if(zid != null)
			params.add("zid", String.valueOf(zid));
		if(aid != null)
			params.add("aid",String.valueOf(aid)); 
		if(service != null)
			params.add("service", service);
		if(cpuOperator != null) 
			params.add("cpu_operator", String.valueOf(cpuOperator));
		if(memoryOperator != null) 
			params.add("memory_operator", String.valueOf(memoryOperator));
		if(diskOperator != null) 
			params.add("disk_operator", String.valueOf(diskOperator));
		if(cpuCount != null) 
			params.add("cpu_count", String.valueOf(cpuCount));
		if(memoryCount != null) 
			params.add("memory_count", String.valueOf(memoryCount));
		if(diskCount != null) 
			params.add("disk_count", String.valueOf(diskCount));
		if(page != null) 
			params.add("page", String.valueOf(page));
		if(size != null)
			params.add("size", String.valueOf(size));
		return super.getList(String.format("%s/search", buildPath()), params);
	}
	
	public Long countByProperties(String tenantId, String userId, String ip, 
			String type, String status, Integer zid, Integer aid, String service, 
			Integer cpuOperator, Integer cpuCount, Integer memoryOperator, 
			Integer memoryCount, Integer diskOperator, Integer diskCount) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(tenantId != null)
			params.add("tenant_id", tenantId);
		if(userId != null)
			params.add("user_id", userId);
		if(ip != null)
			params.add("ip", ip);
		if(type != null)
			params.add("type", type);
		if(status != null)
			params.add("status", status); 
		if(zid != null)
			params.add("zid", String.valueOf(zid));
		if(aid != null)
			params.add("aid",String.valueOf(aid)); 
		if(service != null)
			params.add("service", service);
		if(cpuOperator != null) 
			params.add("cpu_operator", String.valueOf(cpuOperator));
		if(memoryOperator != null) 
			params.add("memory_operator", String.valueOf(memoryOperator));
		if(diskOperator != null) 
			params.add("disk_operator", String.valueOf(diskOperator));
		if(cpuCount != null) 
			params.add("cpu_count", String.valueOf(cpuCount));
		if(memoryCount != null) 
			params.add("memory_count", String.valueOf(memoryCount));
		if(diskCount != null) 
			params.add("disk_count", String.valueOf(diskCount));
		return super.count(String.format("%s/search/count", buildPath()), params);
	}
	
}
