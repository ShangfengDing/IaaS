package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AvailabilityZone;

public interface AcAggregateManager {
public List<AcAggregate> getList(String tenantId, boolean detailed, Integer zoneId) throws Exception ;
	
	public AcAggregate get(String tenantId, Integer acAggregateId) throws Exception ;

	public AcAggregate create(String tenantId, AcAggregate acAggregate) throws Exception ;

	public AcAggregate update(String tenantId, Integer aggregateId, AcAggregate acAggregate) throws Exception ;

	public AcAggregate updateOverSell(String tenantId, Integer aggregateId, String overSell) throws Exception;
	
	public AcAggregate updateRSUuid(String tenantId, Integer aggregateId, String rsUuid) throws Exception;
	
	public AcAggregate addHost(String tenantId, Integer aggregateId, String hostId) throws Exception ;

	public AcAggregate removeHost(String tenantId, Integer aggregateId,
			String hostId) throws Exception ;
	
	public List<AvailabilityZone>listAVZones(String adminId) throws Exception;

	public AvailabilityZone getAvailabilityZoneById(String adminId,
			Integer zoneId) throws Exception;
	
	public AvailabilityZone createAvailabilityZone(String adminId, AvailabilityZone zone) throws Exception;
	
	public AvailabilityZone updateAvailabilityZone(String adminId, Integer zoneId, 
			AvailabilityZone zone) throws Exception;
	
}
