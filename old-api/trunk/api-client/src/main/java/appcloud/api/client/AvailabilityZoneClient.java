package appcloud.api.client;

import java.util.List;

import appcloud.api.beans.AvailabilityZone;

import com.sun.jersey.api.client.GenericType;

public class AvailabilityZoneClient  extends AbstractClient<AvailabilityZone>{
	
	public AvailabilityZoneClient(){
		super();
	}
	
	public AvailabilityZoneClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AvailabilityZone.class;
	}
	
	@Override
	protected GenericType<List<AvailabilityZone>> getGenericType() {		
		GenericType<List<AvailabilityZone>> type = new GenericType<List<AvailabilityZone>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-availability_zones";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(Integer zoneId) {
		return String.format("%s/%s", buildPath(), zoneId);
	}
	
	public List<AvailabilityZone> getZones() {
		return super.getList(buildPath(), null);
	}
	
	public AvailabilityZone getZoneById(Integer zoneId) {
		return super.get(buildPathWithId(zoneId));
	}

	public AvailabilityZone create(String name) {
		AvailabilityZone zone = new AvailabilityZone();
		if(name == null)
			return null;
		zone.name = name;
		return super.postWithRet(buildPath(), zone);
	}
	
	public AvailabilityZone update(Integer zoneId, String name) {
		if(zoneId == null || name == null)
			return null;

		AvailabilityZone zone = new AvailabilityZone();
		zone.name = name;
		return super.update(buildPathWithId(zoneId), zone);
	}
}
