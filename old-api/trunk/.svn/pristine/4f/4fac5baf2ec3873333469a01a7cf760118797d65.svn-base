package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.Flavor;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.FlavorManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstanceType;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.rpc.tools.RpcException;

/**
 * @author jianglei
 *
 */
public class RealFlavorManager implements FlavorManager {
	private static Logger logger = Logger.getLogger(RealFlavorManager.class);
	
	private VmInstanceTypeProxy typeProxy;
	private BeansGenerator generator = BeansGenerator.getInstance();
	private ResourceSchedulerService scheduler;
	
	private static RealFlavorManager manager =  new RealFlavorManager();
	
	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealFlavorManager.class);
	
	public static RealFlavorManager getInstance() {
		return manager;
	}
	
	private RealFlavorManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		generator = BeansGenerator.getInstance();
		typeProxy = (VmInstanceTypeProxy) ConnectionFactory.getTipProxy(
				VmInstanceTypeProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Flavor> getList(String tenantId, boolean detailed) throws Exception {
		if(detailed)
			logger.info(String.format("User %s request to get FLAVORS, detailed", tenantId));
		logger.info(String.format("User %s request to get FLAVORS", tenantId));

		List<VmInstanceType> types = (List<VmInstanceType>) typeProxy.findAll();
		List<Flavor> flavors = new ArrayList<Flavor>();
		for (VmInstanceType type : types) {
			flavors.add(generator.instanceTypeToFlavor(type));
		}
		
		return flavors;
	}

	@Override
	public Flavor get(String tenantId, Integer flavorId) throws Exception {
		logger.info(String.format("User %s request to GET FLAVOR %s", tenantId, flavorId));
		return getSingleFlavor(flavorId);
	}

	@Override
	public Flavor create(String tenantId, Flavor flavor) throws Exception {
		logger.info(String.format("User %s request to CREATE FLAVOR, flavor disk is %s", tenantId, flavor.disk));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.CREATE_FLAVOR, 
				String.format("User %s request to CREATE FLAVOR, flavor disk is %s", tenantId, flavor.disk),
				rpcExtra);
		
		Integer id = null;
		try {
			id = scheduler.createFlavor(flavor.name, flavor.ram, flavor.disk, flavor.vcpus, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_FLAVOR, rpcExtra);
			return new Flavor();
		}
		if (id == null)
			throw new OperationFailedException("create flavor failed");
		logger.info(String.format("FLAVOR created successfully, id is %s", id));
		return getSingleFlavor(id);
	}
	
	protected Flavor getSingleFlavor(Integer flavorId) throws Exception {
		VmInstanceType type = typeProxy.getById(flavorId);
		if (type == null) {
			throw new ItemNotFoundException("flavor does not exist");
		}
		return generator.instanceTypeToFlavor(type);
	}

}
