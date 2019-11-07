package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AvailabilityZone;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AcAggregateClient extends AbstractClient<AcAggregate>{
	
	public AcAggregateClient(){
		super();
		zoneClient = new AvailabilityZoneClient();
	}
	
	public AcAggregateClient(String baseURI) {
		super(baseURI);
		zoneClient = new AvailabilityZoneClient(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AcAggregate.class;
	}
	
	@Override
	protected GenericType<List<AcAggregate>> getGenericType() {		
		GenericType<List<AcAggregate>> type = new GenericType<List<AcAggregate>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-aggregates";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(Integer aggregateId) {
		return String.format("%s/%s", buildPath(), aggregateId);
	}

	public AcAggregate get( Integer aggregateId) {
		return super.get(buildPathWithId(aggregateId));
	}
	
	private String buildActionPath(Integer aggregateId) {
		return String.format("admin/%s/%s/action", getPath(), aggregateId);
	}
	
	public List<AcAggregate> getAggregates( ) {
		return super.getList(buildPath(), null);
	}
	
	public List<AcAggregate> getAggregatesOfZone(Integer zoneId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(zoneId != null)
			params.add("zone_id", zoneId.toString());
		return super.getList(buildPath(), params);
	}
	public AcAggregate updateAggregate(Integer aggregateId, AcAggregate aggregate) {
		return super.update(buildPathWithId(aggregateId), aggregate);
	}
	
	public AcAggregate updateRSUuid(Integer aggregateId, String rsUuid) {
		return super.update(buildPathWithId(aggregateId) + "/updateRSUuid", rsUuid);
	}

	public AcAggregate updateOverSell(Integer aggregateId, Integer cpu_oversell,
			Integer memory_oversell, Integer disk_oversell) {
		String overSell = cpu_oversell + "," + memory_oversell + "," + disk_oversell;
		return super.update(buildPathWithId(aggregateId) + "/updateOverSell", overSell);
	}
	
	public AcAggregate createAggregate(AcAggregate aggregate) {
		return super.postWithRet(buildPath(), aggregate);
	}
	
	public AcAggregate addHost(Integer aggregateId, String hostName){
		String root = "<add_host host=\"" + hostName + "\"/>";
		return super.postWithRet(buildActionPath(aggregateId), XML_HEADER + root);
	}

	public AcAggregate removeHost(Integer aggregateId, String hostName){
		String root = "<remove_host host=\"" + hostName + "\"/>";
		return super.postWithRet(buildActionPath(aggregateId), XML_HEADER + root);	
	}
	
	private AvailabilityZoneClient zoneClient;
	public List<AvailabilityZone> getZones() {
		return zoneClient.getZones();
	}
	
	public AvailabilityZone getZoneById(Integer zoneId) {
		return zoneClient.getZoneById(zoneId);
	}
	
	public AvailabilityZone createAvailabilityZone(String name) {
		return zoneClient.create(name);
	}
	
	public AvailabilityZone updateAvailabilityZone(Integer zoneId, String name) {
		return zoneClient.update(zoneId, name);
	}

}
