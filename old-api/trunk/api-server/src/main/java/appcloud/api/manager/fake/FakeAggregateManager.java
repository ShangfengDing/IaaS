package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import appcloud.api.beans.Aggregate;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.manager.AggregateManager;

public class FakeAggregateManager implements AggregateManager {
	public List<Aggregate> getList(String tenantId, boolean detailed) {
		List<Aggregate> aList = new ArrayList<Aggregate>();
		aList.add(_genAggregate("cluster1"));
		aList.add(_genAggregate("cluster2"));
		Aggregate a = _genAggregate("c3");
		a.hosts.add("abcdedee");
		aList.add(a);
		
		return aList;
	}
	
	private Aggregate _genAggregate(String name) {
		Aggregate a = new Aggregate();
		a.availabilityZone = "hehe";
		a.createdAt = new Date(System.currentTimeMillis()-1000);
		a.updatedAt = new Date(System.currentTimeMillis());
		a.deleted = false;
		a.name = name;
		
		return a;
	}

	@Override
	public Aggregate get(String tenantId, Integer aggregateId) {
		Aggregate a = _genAggregate(aggregateId+"nnn");
		
		return a;
	}

	@Override
	public Aggregate create(Aggregate aggregate) {
		Aggregate a = _genAggregate(aggregate.name);
		a.availabilityZone = aggregate.availabilityZone;
		
		return a;
	}

	@Override
	public Aggregate update(String tenantId, Integer aggregateId, Aggregate aggregate) {
		Aggregate a = _genAggregate(aggregate.name);
		a.availabilityZone = aggregate.availabilityZone;
		a.id = aggregateId;
		
		return a;
	}

	@Override
	public Aggregate addHost(String tenantId, Integer aggregateId, String hostId) {
		Aggregate a = _genAggregate("c3");
		a.hosts.add("abcdedee");
		a.hosts.add("dfsdfsd");
		a.hosts.add("dfsdfsd");
		a.hosts.add(hostId);
		a.id = aggregateId;
		
		return a;		
	}

	@Override
	public Aggregate removeHost(String tenantId, Integer aggregateId,
			String hostId) {
		Aggregate a = _genAggregate("c3");
		a.hosts.add("abcdedee");
		a.hosts.add("dfsdfsd");
		a.hosts.add("dfsdfsd");
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
}
