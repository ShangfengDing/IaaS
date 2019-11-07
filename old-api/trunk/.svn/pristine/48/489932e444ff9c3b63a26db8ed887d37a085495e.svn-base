package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.AddressPool;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.AddressPoolManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.Cluster;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmIpSegMent;
import appcloud.common.model.VmUsedIp;
import appcloud.common.proxy.VmIpSegMentProxy;
import appcloud.common.proxy.VmUsedIpProxy;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.rpc.tools.RpcException;

public class RealAddressPoolManager implements AddressPoolManager {
	private static Logger logger = Logger.getLogger(RealAddressPoolManager.class);
	
	private static RealAddressPoolManager manager =  new RealAddressPoolManager();
	
	private ResourceSchedulerService scheduler;
	private BeansGenerator generator = BeansGenerator.getInstance();
	private VmIpSegMentProxy ipsegProxy;
	private VmUsedIpProxy usedIpProxy;
	private ClusterProxy clusterProxy;
	
	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealAddressPoolManager.class);
	
	private RealAddressPoolManager() {
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		generator = BeansGenerator.getInstance();
		ipsegProxy = (VmIpSegMentProxy) ConnectionFactory.getTipProxy(
				VmIpSegMentProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		usedIpProxy = (VmUsedIpProxy) ConnectionFactory.getTipProxy(
				VmUsedIpProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		clusterProxy = (ClusterProxy) ConnectionFactory.getTipProxy(
				ClusterProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	public static RealAddressPoolManager getInstance() {
		return manager;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AddressPool> getList(String adminId, Integer zoneId, Integer aggregateId) throws Exception {
		String logStr = String.format("Administrator %s request to GET ADDRESSPOOLS", adminId);
		
		List<AddressPool> pools = new ArrayList<AddressPool>();
		List<Integer> aggregateIds = new ArrayList<Integer>();
		
		if(aggregateId != null) {
			logStr += ", aggregateId: " + aggregateId;
			if(zoneId != null) {
				logStr += ", zoneId: " + zoneId;
				Cluster cluster = clusterProxy.getById(aggregateId, false, false, false);
				if(!cluster.getAvailabilityZoneId().equals(zoneId)){
					logger.info(logStr);
					return null;
				}
			}
			aggregateIds.add(aggregateId);
		}
		else if(zoneId != null) {
			logStr += ", zoneId: " + zoneId;
			List<Cluster> clusters = (List<Cluster>) clusterProxy.findAll(false, false, false);
			for(Cluster cluster : clusters) {
				if(cluster.getAvailabilityZoneId().equals(zoneId))
					aggregateIds.add(cluster.getId());
			}
		}
		logger.info(logStr);
		List<VmIpSegMent> segments = ipsegProxy.findAll();
		
		for (VmIpSegMent segment : segments) {
			if(aggregateIds.contains(segment.getDhcpId()))
			pools.add(generator.ipSegmentToAddressPool(segment, null));
		}
		
		return pools;
	}

	@Override
	public AddressPool get(String adminId, Integer poolId) throws Exception {
		logger.info(String.format("Administrator %s request to GET ADDRESSPOOL %s", adminId, poolId));
		VmIpSegMent segment = ipsegProxy.getById(poolId);
		if (segment == null) {
			throw new ItemNotFoundException("ip pool does not exist");
		}
		List<VmUsedIp> usedIps = usedIpProxy.findAll(segment.getId());
		return generator.ipSegmentToAddressPool(segment, usedIps);
	}

	@Override
	public AddressPool create(String adminId, AddressPool createReq)
			throws Exception {
		logger.info(String.format("Administrator %s request to CREATE ADDRESSPOOL: %s to %s",
				adminId, createReq.ipStart, createReq.ipEnd));
		logger.debug(String.format("dns:%s, gw:%s, nm%s", createReq.dns,
				createReq.gateway, createReq.netmask));
		Integer type = 0;
		if (createReq.type.equalsIgnoreCase("private")) {
			type = 0;
		} else {
			type = 1;
		}
		
		VmIpSegMent segment = new VmIpSegMent(null, createReq.aggregateId,
				createReq.netmask, createReq.ipStart, createReq.ipEnd,
				createReq.gateway, createReq.dns, type);
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), adminId);
		loller.info(LolLogUtil.CREATE_IP_SEGMENT, 
				String.format("Administrator %s request to CREATE ADDRESSPOOL: %s to %s",
						adminId, createReq.ipStart, createReq.ipEnd), rpcExtra);
		
		try {
			scheduler.createIpSegment(type, createReq.aggregateId, segment, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_IP_SEGMENT, rpcExtra);
			return new AddressPool();
		}
		//FIXME check if succeed
		logger.info(String.format("ADDRESSPOOL created successfully: %s-%s", 
				createReq.ipStart, createReq.ipEnd));

		return createReq;
	}

	@Override
	public void delete(String adminId, Integer poolId) throws Exception {
		logger.info(String.format("Administrator %s request to DELETE ADDRESSPOOL %s", adminId, poolId));
		VmIpSegMent segment = ipsegProxy.getById(poolId);
		if (segment == null) {
			throw new ItemNotFoundException("ip pool does not exist");
		}
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), adminId);
		loller.info(LolLogUtil.DEL_NET_IP_SEGMENT, 
				String.format("Administrator %s request to DELETE ADDRESSPOOL %s", adminId, poolId),
				rpcExtra);
		Boolean succeed = false;
		try {
			succeed = scheduler.delNetIpSegment(poolId, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DEL_NET_IP_SEGMENT, rpcExtra);
			return;
		}
		if(succeed)
			logger.info(String.format("ADDRESSPOOL deleted successfully"));
		else{
			logger.info(String.format("ADDRESSPOOL %s not deleted", poolId));
			throw new OperationFailedException("delete address pool failed");
		}
	}

}
