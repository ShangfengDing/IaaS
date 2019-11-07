package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.jersey.api.NotFoundException;

import appcloud.api.beans.Aggregate;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.manager.AggregateManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.common.model.Cluster;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;

/**
 * @author jianglei
 *
 */
public class RealAggregateManager implements AggregateManager {
	private static Logger logger = Logger.getLogger(RealAggregateManager.class);
	
	private ClusterProxy clusterProxy;
	private VmZoneProxy zoneProxy;
	private BeansGenerator generator;
	
	private static RealAggregateManager manager = new RealAggregateManager();
	
	public static RealAggregateManager getInstance() {
		return manager;
	}
	
	private RealAggregateManager() {
		super();
		clusterProxy = (ClusterProxy) ConnectionFactory.getTipProxy(ClusterProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		zoneProxy = (VmZoneProxy) ConnectionFactory.getTipProxy(VmZoneProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		generator = BeansGenerator.getInstance();
	}

	@Override
	public List<Aggregate> getList(String tenantId, boolean detailed) throws Exception {
		logger.info(String.format("GET AGGRETAGES request got: %s", tenantId));
		return getAggregates();
	}

	@Override
	public Aggregate get(String tenantId, Integer aggregateId) throws Exception {
		logger.info(String.format("GET AGGRETAGE request got: %s, %s", tenantId, aggregateId));
		return getSingleAggregate(aggregateId);
	}

	@Override
	public Aggregate create(Aggregate aggregate) {
		// TODO Auto-generated method stub
		logger.info(String.format("CREATE AGGRETAGE request got: %s", aggregate.name));
		
		logger.info(String.format("AGGRETAGE CREATED: %s", "uuid"));
		return null;
	}

	@Override
	public Aggregate update(String tenantId, Integer aggregateId,
			Aggregate aggregate) {
		// TODO Auto-generated method stub
		logger.info(String.format("UPDATE AGGRETAGE request got: %s, %s", tenantId, aggregateId));
		return null;
	}

	@Override
	public Aggregate addHost(String tenantId, Integer aggregateId, String hostId) {
		// TODO Auto-generated method stub
		logger.info(String.format("ADD HOST request got: %s, %s", tenantId, aggregateId));
		return null;
	}

	@Override
	public Aggregate removeHost(String tenantId, Integer aggregateId,
			String hostId) {
		// TODO Auto-generated method stub
		logger.info(String.format("REMOVE HOST request got: %s, %s", tenantId, aggregateId));
		return null;
	}
	
	protected List<Aggregate> getAggregates() throws Exception {
		@SuppressWarnings("unchecked")
		List<Cluster> clusters = (List<Cluster>) clusterProxy.findAll(false, false, false);
		List<Aggregate> agts = new ArrayList<Aggregate>();
		for (Cluster cluster : clusters) {
			agts.add(generator.clusterToAggregate(cluster, false));
		}
		
		return agts;
	}
	
	protected Aggregate getSingleAggregate(Integer aggregateId) throws Exception {
		Cluster cluster = clusterProxy.getById(aggregateId, false, true, false);
		if (cluster == null) {
			throw new NotFoundException();
		}
		return generator.clusterToAggregate(cluster, true);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvailabilityZone> listAVZones(String adminId) throws Exception {
		List<VmZone> zones = (List<VmZone>) zoneProxy.findAll();
		List<AvailabilityZone> aVZones = new ArrayList<AvailabilityZone>();
		
		for (VmZone zone : zones) {
			aVZones.add(generator.VMZoneToAVZone(zone));
		}
		
		return aVZones;
	}

	@Override
	public AvailabilityZone getAvailabilityZoneById(String adminId,
			Integer zoneId) throws Exception {
		VmZone zone = zoneProxy.getById(zoneId);
		
		return generator.VMZoneToAVZone(zone);
	}

}
