package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.Aggregate;
import appcloud.api.beans.AvailabilityZone;

public interface AggregateManager {
	public List<Aggregate> getList(String tenantId, boolean detailed) throws Exception ;
	
	public Aggregate get(String tenantId, Integer aggregateId) throws Exception ;

	public Aggregate create(Aggregate aggregate) throws Exception ;

	public Aggregate update(String tenantId, Integer aggregateId, Aggregate aggregate) throws Exception ;

	public Aggregate addHost(String tenantId, Integer aggregateId, String hostId) throws Exception ;

	public Aggregate removeHost(String tenantId, Integer aggregateId,
			String hostId) throws Exception ;
	
	public List<AvailabilityZone>listAVZones(String adminId) throws Exception;

	public AvailabilityZone getAvailabilityZoneById(String adminId,
			Integer zoneId) throws Exception;
}
