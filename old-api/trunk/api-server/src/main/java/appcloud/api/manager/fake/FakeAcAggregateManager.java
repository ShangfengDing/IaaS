package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.manager.AcAggregateManager;

public class FakeAcAggregateManager implements AcAggregateManager {
	public List<AcAggregate> getList(String tenantId, boolean detailed, Integer zoneId) {
		List<AcAggregate> aList = new ArrayList<AcAggregate>();
		aList.add(_genAggregate("cluster1"));
		aList.add(_genAggregate("cluster2"));
		AcAggregate a = _genAggregate("c3");
		//a.hosts.add("abcdedee");
		aList.add(a);
		
		return aList;
	}
	
	private AcAggregate _genAggregate(String name) {
		AcAggregate a = new AcAggregate();
		a.availabilityZoneName = "hehe";
		a.availabilityZoneId = 3;
		a.createdAt = new Date(System.currentTimeMillis()-1000);
		a.updatedAt = new Date(System.currentTimeMillis());
		a.deleted = false;
		a.name = name;
		
		return a;
	}

	@Override
	public AcAggregate get(String tenantId, Integer aggregateId) {
		AcAggregate a = _genAggregate(aggregateId+"nnn");
		
		return a;
	}

	@Override
	public AcAggregate create(String tenantId, AcAggregate aggregate) {
		AcAggregate a = _genAggregate(aggregate.name);
		a.availabilityZoneName = aggregate.availabilityZoneName;
		a.availabilityZoneId = aggregate.availabilityZoneId;
		
		return a;
	}

	@Override
	public AcAggregate update(String tenantId, Integer aggregateId, AcAggregate aggregate) {
		AcAggregate a = _genAggregate(aggregate.name);
		a.availabilityZoneName = aggregate.availabilityZoneName;
		a.availabilityZoneId = aggregate.availabilityZoneId;
		a.id = aggregateId;
		
		return a;
	}

	@Override
	public AcAggregate addHost(String tenantId, Integer aggregateId, String hostId) {
		AcAggregate a = _genAggregate("c3");
		/*
		a.hosts.add("abcdedee");
		a.hosts.add("dfsdfsd");
		a.hosts.add("dfsdfsd");
		a.hosts.add(hostId);
		*/
		a.id = aggregateId;
		
		return a;		
	}

	@Override
	public AcAggregate removeHost(String tenantId, Integer aggregateId,
			String hostId) {
		AcAggregate a = _genAggregate("c3");
		/*
		a.hosts.add("abcdedee");
		a.hosts.add("dfsdfsd");
		a.hosts.add("dfsdfsd");
		*/
		a.id = aggregateId;
		
		return a;
	}

	@Override
	public List<AvailabilityZone> listAVZones(String adminId) throws Exception {
		List<AvailabilityZone> zones = new ArrayList<AvailabilityZone>();
		
		zones.add(new AvailabilityZone("beijing", 1, null, null, new Date(), new Date()));
		zones.add(new AvailabilityZone("shanghai", 2, null, null, new Date(), new Date()));
		zones.add(new AvailabilityZone("hangzhou", 3, null, null, new Date(), new Date()));
		
		return zones;
	}

	@Override
	public AvailabilityZone getAvailabilityZoneById(String adminId,
			Integer zoneId) throws Exception {
		return new AvailabilityZone("hangzhou"+zoneId, zoneId, null, null, new Date(), new Date());
	}

	@Override
	public AvailabilityZone createAvailabilityZone(String adminId,
			AvailabilityZone zone) throws Exception {
		// TODO Auto-generated method stub
		return zone;
	}

	@Override
	public AvailabilityZone updateAvailabilityZone(String adminId, Integer zoneId, 
			AvailabilityZone zone) throws Exception {
		// TODO Auto-generated method stub
		return zone;
	}

	@Override
	public AcAggregate updateOverSell(String tenantId, Integer aggregateId,
			String overSell) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcAggregate updateRSUuid(String tenantId, Integer aggregateId,
			String rsUuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
