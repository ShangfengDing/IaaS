package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.AcAggregateManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.Cluster;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.rpc.tools.RpcException;

public class RealAcAggregateManager implements AcAggregateManager {
	private static Logger logger = Logger.getLogger(RealAggregateManager.class);
	
	private ClusterProxy clusterProxy;
	private VmZoneProxy zoneProxy;
	private HostProxy hostProxy;
	private BeansGenerator generator;
	private ResourceSchedulerService scheduler;
	
	private static RealAcAggregateManager manager = new RealAcAggregateManager();
	
	private static Map<Integer, AvailabilityZone> zoneCache =
			new ConcurrentHashMap<Integer, AvailabilityZone>();
	
	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealAcAggregateManager.class);
	
	public static RealAcAggregateManager getInstance() {
		return manager;
	}
	
	private RealAcAggregateManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		clusterProxy = (ClusterProxy) ConnectionFactory.getTipProxy(ClusterProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		zoneProxy = (VmZoneProxy) ConnectionFactory.getTipProxy(VmZoneProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		hostProxy = (HostProxy) ConnectionFactory.getTipProxy(HostProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		generator = BeansGenerator.getInstance();
	}

	@Override
	public List<AcAggregate> getList(String tenantId, boolean detailed, Integer zoneId) throws Exception {
		logger.info(String.format("Administrator %s request to GET ACAGGREGATES", tenantId));
		
		@SuppressWarnings("unchecked")
		List<Cluster> clusters = (List<Cluster>) clusterProxy.findAll(false, true, false);
		List<AcAggregate> agts = new ArrayList<AcAggregate>();
		for (Cluster cluster : clusters) {
			if(zoneId != null && (!cluster.getAvailabilityZoneId().equals(zoneId)))
				continue;
			AcAggregate aggregate = generator.clusterToAcAggregate(cluster);
			aggregate.availabilityZoneName = searchZoneCache(cluster.getAvailabilityZoneId()).name;
			aggregate.availabilityZoneId = searchZoneCache(cluster.getAvailabilityZoneId()).id;
			fillUpAggregateWithHosts(aggregate, cluster.getHosts());
			agts.add(aggregate);
		}
		
		return agts;
	}

	@Override
	public AcAggregate get(String tenantId, Integer aggregateId) throws Exception {
		logger.info(String.format("Administrator %s request to GET ACAGGREGATE %s, ", tenantId, aggregateId));

		Cluster cluster = clusterProxy.getById(aggregateId, false, true, false);
		if (cluster == null) {
			throw new ItemNotFoundException("aggregate does not exist");
		}
		AcAggregate aggregate = generator.clusterToAcAggregate(cluster);
		aggregate.availabilityZoneName = searchZoneCache(cluster.getAvailabilityZoneId()).name;
		aggregate.availabilityZoneId = searchZoneCache(cluster.getAvailabilityZoneId()).id;
		fillUpAggregateWithHosts(aggregate, cluster.getHosts());
		return aggregate;
	}

	@Override
	public AcAggregate create(String tenantId, AcAggregate aggregate) throws Exception {
		logger.info(String.format("Administrator %s request to CREATE ACAGGREGATE %s",tenantId, aggregate.name));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.CREATE_AGGREGATE, 
				String.format("Administrator %s request to CREATE ACAGGREGATE %s",tenantId, aggregate.name), rpcExtra);
		
		Integer acAggregateId = null;
		try {
			acAggregateId = scheduler.createAggregate(aggregate.name, aggregate.availabilityZoneId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_AGGREGATE, rpcExtra);
			return new AcAggregate();
		}
		if(acAggregateId == null)
			throw new OperationFailedException("create aggregate failed");
		Cluster cluster = clusterProxy.getById(acAggregateId, false, true, false);
		logger.info(String.format("ACAGGRETAGE created succefully, id %s", acAggregateId));
		return generator.clusterToAcAggregate(cluster);
	}

	@Override
	public AcAggregate update(String tenantId, Integer aggregateId,
			AcAggregate aggregate) throws Exception {
		logger.info(String.format("Administrator %s request to UPDATE ACAGGREGATE %s", tenantId, aggregateId));
		
		Cluster cluster = clusterProxy.getById(aggregateId, false, true, false);
		if(cluster == null)
			throw new ItemNotFoundException("aggregate does not exist");
		
		// TODO 
		return null;
	}

	/**
	 * 通过aggregateId仅更新resource_strategy_uuid,其它字段值不变
	 */
	@Override
	public AcAggregate updateRSUuid(String tenantId, Integer aggregateId,
			String rsUuid) throws Exception {
		logger.info(String.format("Administrator %s request to UPDATE resource_strategy_uuid of ACAGGREGATE %s", tenantId, aggregateId));

		Cluster cluster = clusterProxy.getById(aggregateId, false, false, false);
		cluster.setResrcStrategyUuid(rsUuid);
		clusterProxy.update(cluster);
		return generator.clusterToAcAggregate(cluster);
	}
	
	/**
	 * 通过aggregateId仅更新oversell,其它字段值不变
	 */
	@Override
	public AcAggregate updateOverSell(String tenantId, Integer aggregateId,
			String overSell) throws Exception {
		logger.info(String.format("Administrator %s request to UPDATE over_sell of ACAGGREGATE %s", tenantId, aggregateId));
		Cluster cluster = clusterProxy.getById(aggregateId, false, false, false);
		String[] overSellArray = overSell.split(",");
		cluster.setCpuOversell(Integer.valueOf(overSellArray[0]));
		cluster.setMemoryOversell(Integer.valueOf(overSellArray[1]));
		cluster.setDiskOversell(Integer.valueOf(overSellArray[2]));
		clusterProxy.update(cluster);
		return generator.clusterToAcAggregate(cluster);
	}
	
	@Override
	public AcAggregate addHost(String tenantId, Integer aggregateId, String hostId) throws Exception {
		logger.info(String.format("Administrator %s request to ADD ACHOST to ACAGGREGATE %s", tenantId, aggregateId));
		
		appcloud.common.model.Host vmHost = hostProxy.getByUuid(hostId, false, false, false);
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.ADD_HOST_TO_AGGREGATE, 
				String.format("Administrator %s request to ADD ACHOST to ACAGGREGATE %s", tenantId, aggregateId), rpcExtra);
		
		if(vmHost == null) {
			loller.warn(LolLogUtil.ADD_HOST_TO_AGGREGATE, 
					"add host to aggregate warn: host does not exist", rpcExtra);
			throw new ItemNotFoundException("host does not exist");
		}
		Cluster cluster = clusterProxy.getById(aggregateId, false, true, false);
		if(cluster == null){
			loller.warn(LolLogUtil.ADD_HOST_TO_AGGREGATE, 
					"add host to aggregate warn: cluster does not exist", rpcExtra);
			throw new ItemNotFoundException("aggregate does not exist");
		}
		Integer acAggregateId = null;
		try {
			acAggregateId = scheduler.addHostToAggregate(hostId, aggregateId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.ADD_HOST_TO_AGGREGATE, rpcExtra);
			return new AcAggregate();
		}
		if(acAggregateId == null)
			throw new OperationFailedException("add host to aggregate failed");
		cluster = clusterProxy.getById(acAggregateId, false, true, false);
		logger.info(String.format("ACHOST added to ACAGGREGATE %s succefully", acAggregateId));
		return generator.clusterToAcAggregate(cluster);
	}

	@Override
	public AcAggregate removeHost(String tenantId, Integer aggregateId,
			String hostId) throws Exception {
		logger.info(String.format("Administrator %s request to REMOVE ACHOST %s from ACAGGREGATE %s", tenantId, hostId, aggregateId));

		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.REMOVE_HOST_FROM_AGGREGATE, 
				String.format("Administrator %s request to REMOVE ACHOST %s from ACAGGREGATE %s", tenantId, hostId, aggregateId),
				rpcExtra);
		
		appcloud.common.model.Host vmHost = hostProxy.getByUuid(hostId, false, false, false);
		if(vmHost == null){
			loller.warn(LolLogUtil.REMOVE_HOST_FROM_AGGREGATE, 
					"remove host from aggregate warn: host does not exist", rpcExtra);
			throw new ItemNotFoundException("host does not exist");
		}
		else if(!vmHost.getClusterId().equals(aggregateId)){
			loller.warn(LolLogUtil.REMOVE_HOST_FROM_AGGREGATE, 
					"remove host from aggregate warn: host is not in the acaggregate", rpcExtra);
			throw new OperationFailedException("host is not in the acaggregate");
		}
		Cluster cluster = clusterProxy.getById(aggregateId, false, true, false);
		if(cluster == null) {
			loller.warn(LolLogUtil.REMOVE_HOST_FROM_AGGREGATE, 
				"remove host from aggregate warn: cluster does not exist", rpcExtra);
			throw new ItemNotFoundException("aggregate does not exist");
		}
		
		Integer acAggregateId = null;
		try {
			acAggregateId = scheduler.removeHostFromAggregate(hostId, aggregateId, rpcExtra);
		} catch (RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REMOVE_HOST_FROM_AGGREGATE, rpcExtra);
			return new AcAggregate();
		}
		
		cluster = clusterProxy.getById(acAggregateId, false, true, false);
		logger.info(String.format("ACHOST removed from ACAGGREGATE %s succefully", acAggregateId));
		return generator.clusterToAcAggregate(cluster);
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<AvailabilityZone> listAVZones(String adminId) throws Exception {

		logger.info(String.format("Administrator %s request to GET AVAILABILITYZONES", adminId));
		List<VmZone> zones = (List<VmZone>) zoneProxy.findAll();
		List<AvailabilityZone> aVZones = new ArrayList<AvailabilityZone>();
		
		for (VmZone zone : zones) {
			AvailabilityZone availabilityZone = generator.VMZoneToAVZone(zone);
			fillUpZoneWithAggregates(availabilityZone);
			aVZones.add(availabilityZone);
		}
		updateZoneCache(aVZones);
		return aVZones;
	}

	@Override
	public AvailabilityZone getAvailabilityZoneById(String adminId,
			Integer zoneId) throws Exception {
		logger.info(String.format("Administrator %s request to GET AVAILABILITYZONE with id %s", adminId, zoneId));
		VmZone zone = zoneProxy.getById(zoneId);
		if (zone == null) {
			throw new ItemNotFoundException("availabilityZone does not exist");
		}
		AvailabilityZone availabilityZone = generator.VMZoneToAVZone(zone);
		updateZoneCache(availabilityZone);
		fillUpZoneWithAggregates(availabilityZone);
		return availabilityZone;
	}
	
	@Override
	public AvailabilityZone createAvailabilityZone(String adminId, AvailabilityZone azone) throws Exception{
		logger.info(String.format("Administrator %s request to CREATE AVAILABILITYZONE: %s", adminId, azone.name));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), adminId);
		loller.info(LolLogUtil.REMOVE_HOST_FROM_AGGREGATE, 
				String.format("Administrator %s request to CREATE AVAILABILITYZONE: %s", adminId, azone.name),
				rpcExtra);
		
		Integer zoneId = null;
		try {
			zoneId = scheduler.createVmZone(azone.name, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_AVAILABILITY_ZONE, rpcExtra);
			return new AvailabilityZone();
		}
		
		VmZone zone = zoneProxy.getById(zoneId);
		if (zone == null) {
			throw new ItemNotFoundException("availabilityZone does not exist");
		}
		AvailabilityZone availabilityZone = generator.VMZoneToAVZone(zone);
		updateZoneCache(availabilityZone);
		fillUpZoneWithAggregates(availabilityZone);
		return availabilityZone;
		
	}
	

	@Override
	public AvailabilityZone updateAvailabilityZone(String adminId, Integer zoneId, 
			AvailabilityZone azone) throws Exception {
		logger.info(String.format("Administrator %s request to UPDATE AVAILABILITYZONE: %s, ",
				adminId, zoneId,  azone.name));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), adminId);
		loller.info(LolLogUtil.REMOVE_HOST_FROM_AGGREGATE, 
				String.format("Administrator %s request to CREATE AVAILABILITYZONE: %s", adminId, azone.name),
				rpcExtra);
		Integer updateZoneId = null;
		try {
			updateZoneId = scheduler.updateVmZone(zoneId, azone.name, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.UPDATE_AVAILABILITY_ZONE, rpcExtra);
			return new AvailabilityZone();
		}
		
		VmZone zone = zoneProxy.getById(updateZoneId);
		if (zone == null) {
			throw new ItemNotFoundException("availabilityZone does not exist");
		}
		AvailabilityZone availabilityZone = generator.VMZoneToAVZone(zone);
		updateZoneCache(availabilityZone);
		fillUpZoneWithAggregates(availabilityZone);
		return availabilityZone;
		
	}
	
	private void fillUpAggregateWithHosts(AcAggregate aggregate, List<appcloud.common.model.Host> vmHosts) {
		ArrayList<AcHost> acHosts = new ArrayList<AcHost>();
		for(appcloud.common.model.Host vmHost: vmHosts){
			acHosts.add(generator.hostToAcHost(vmHost));
		}
		aggregate.acHosts = acHosts;
	}
	
	private void fillUpZoneWithAggregates(AvailabilityZone zone) throws Exception {
		List<AcAggregate> aggregates = new ArrayList<AcAggregate>();
		@SuppressWarnings("unchecked")
		List<Cluster> clusters = (List<Cluster>) clusterProxy.findAll(false, false, false);
		for (Cluster cluster : clusters) {
			if(cluster.getAvailabilityZoneId().equals(zone.id)){
				AcAggregate aggregate = generator.clusterToAcAggregate(cluster);
				aggregate.availabilityZoneName = zone.name;
				aggregate.availabilityZoneId = zone.id;
				aggregates.add(aggregate);
			}
		}
		
		zone.aggregates = aggregates;
	}

	private AvailabilityZone searchZoneCache(Integer zoneId) throws Exception {
		if(zoneCache.containsKey(zoneId))
			return zoneCache.get(zoneId);
		AvailabilityZone zone = generator.VMZoneToAVZone(zoneProxy.getById(zoneId));
		if(zone == null){
			zone = new AvailabilityZone();
			zone.name = "NOT FOUND";
			zone.id = -1;
			return zone;
		}
		updateZoneCache(zone);
		return zone;
	}
	
	private  void updateZoneCache(AvailabilityZone zone) {
		zoneCache.put(zone.id, zone);
	}
	
	private void updateZoneCache(List <AvailabilityZone> zones) {
		for(AvailabilityZone zone : zones)
			updateZoneCache(zone);
	}

}
