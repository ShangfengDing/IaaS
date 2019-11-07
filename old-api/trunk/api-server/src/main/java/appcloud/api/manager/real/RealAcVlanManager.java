package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcVlan;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.manager.AcVlanManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.common.model.VmVlan;
import appcloud.common.proxy.VmVlanProxy;
import appcloud.common.util.ConnectionFactory;

public class RealAcVlanManager implements AcVlanManager {
	private static Logger logger = Logger.getLogger(RealServerManager.class);
	private BeansGenerator generator;
	private VmVlanProxy vmVlanProxy;
	
	private static RealAcVlanManager manager = new RealAcVlanManager();;
	
	public static RealAcVlanManager getInstance() {
		return manager;
	}
	
	private RealAcVlanManager () {
		generator = BeansGenerator.getInstance();
		vmVlanProxy = (VmVlanProxy)ConnectionFactory.getTipProxy(VmVlanProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AcVlan> getList(String tenantId) throws Exception {
		logger.info(String.format("User %s request to get ACVLANS", tenantId));
		List<AcVlan> acVlans = new ArrayList<AcVlan>();
		List<VmVlan> vmVlans = (List<VmVlan>) vmVlanProxy.findAll();
		for(VmVlan vmVlan : vmVlans)
			acVlans.add(generator.vmVlanToAcVlan(vmVlan));
		return acVlans;
	}

	@Override
	public AcVlan get(String tenantId, Integer vlanId) throws Exception {
		logger.info(String.format("User %s request to get ACVLAN %s", tenantId, vlanId));
		VmVlan vmVlan = vmVlanProxy.getById(vlanId);
		if(vmVlan == null)
			throw new ItemNotFoundException("vlan does not exist");
		return generator.vmVlanToAcVlan(vmVlan);
	}

}
