package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import appcloud.api.beans.Aggregate;
import appcloud.api.beans.AvailabilityZone;

import com.sun.jersey.api.client.GenericType;

public class AggregateClient extends AbstractClient<Aggregate>{
	//private final static String ADD_HOST = XML_HEADER + "<add_host/>";
	//private final static String REMOVE_HOST = XML_HEADER + "<remove_host/>";
	
	private final static String AC_ZONE = "ac-availability_zones";
	
	public AggregateClient(){
		super();
	}
	
	public AggregateClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return Aggregate.class;
	}
	
	@Override
	protected GenericType<List<Aggregate>> getGenericType() {		
		GenericType<List<Aggregate>> type = new GenericType<List<Aggregate>>() {};
		return type;
	}
	
	private String getPath() {
		return  "os-aggregates";
	}
	
	//@Override
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	//@Override
	protected String buildPathWithId(String tenantId, Integer aggregateId) {
		return String.format("%s/%s", buildPath(tenantId), aggregateId);
	}

	public Aggregate get(String tenantId, Integer aggregateId) {
		return super.get(buildPathWithId(tenantId, aggregateId));
	}
	
	private String buildActionPath(String tenantId, Integer aggregateId) {
		return String.format("%s/%s/%s/action", tenantId, getPath(), aggregateId);
	}
	
	public List<Aggregate> getAggregates(String tenantId) {
		return this.getList(buildPath(tenantId), null);
	}

	public Aggregate updateAggregate(String tenantId, Integer aggregateId, Aggregate aggregate) {
		return (Aggregate)resource.path(buildPathWithId(tenantId, aggregateId)).put(getType(), aggregate);
	}
	
	public Aggregate createAggregate(String tenantId, Aggregate aggregate) {
		return (Aggregate)resource.path(buildPath(tenantId)).type(MediaType.APPLICATION_XML).post(getType(), aggregate);
	}
	
	public Aggregate addHost(String tenantId, Integer aggregateId, String hostName){
		String root = "<add_host host=\"" + hostName + "\"/>";
		return (Aggregate)resource.path(buildActionPath(tenantId, aggregateId)).
				type(MediaType.APPLICATION_XML).post(getType(), XML_HEADER + root);	
	}

	public Aggregate removeHost(String tenantId, Integer aggregateId, String hostName){
		String root = "<remove_host host=\"" + hostName + "\"/>";
		return (Aggregate)resource.path(buildActionPath(tenantId, aggregateId)).
				type(MediaType.APPLICATION_XML).post(getType(), XML_HEADER + root);	
	}
	
	public List<AvailabilityZone> getZones() {
		return resource.path(buildZonePath()).get(new GenericType<List<AvailabilityZone>>() {});
	}
	
	public AvailabilityZone getZoneById(Integer zoneId) {
		return resource.path(buildZonePath(zoneId)).get(AvailabilityZone.class);
	}
	
	private String buildZonePath() {
		return String.format("%s/%s", "admin", AC_ZONE);
	}

	private String buildZonePath(Integer zoneId) {
		return String.format("%s/%s/%d", "admin", AC_ZONE, zoneId);
	}
}
