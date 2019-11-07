package appcloud.vmscheduler.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import appcloud.common.model.Host;
import appcloud.common.model.ResourceStrategy;
import appcloud.common.model.ResourceStrategy.StrategyTypeEnum;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.ResourceStrategyProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tools.RpcException;
import appcloud.vmscheduler.impl.DBAgent;
import appcloud.vmscheduler.impl.VMSchedulerImpl;
import appcloud.vmscheduler.strategy.filter.CoreFilter;
import appcloud.vmscheduler.strategy.filter.RamFilter;

public class TestSelector implements SelectorService {

	@Override
	public List<Host> selectNodes(int hostNum, VmInstance instance,
			VmInstanceType instanceType, Integer cluster) {
		System.out.println("hahahaha,success!!!");
		return null;
	}
	
	public static void main(String[] args) {
//		ResourceStrategyProxy resourceStrategyProxy = (ResourceStrategyProxy) ConnectionFactory.getDBProxy(ResourceStrategyProxy.class);
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("CPU", "1");
//		map.put("MEM", "1");
//		JSONObject paramsJson = new JSONObject(map);
//		ResourceStrategy resourceStrategy = new ResourceStrategy(null, "test", StrategyTypeEnum.CPU_MEMORY, "name", "des", "TestSelector", paramsJson.toString(), null);
//		try {
//			resourceStrategyProxy.save(resourceStrategy);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Set<String> filterSet = new LinkedHashSet<String>();
//		String [] filterNameList = new String[] { "AvailablityClusterFilter" , "CoreFilter", "RamFilter" };
//		for (int i = 0; i < filterNameList.length; i++) {
//			filterSet.add(filterNameList[i]);
//		}
//		HostFilterHandler hostFilterHandler = new HostFilterHandler(filterSet);
//		Host host = new Host();
//		List<Host> hosts = new ArrayList<Host>();
//		hosts.add(host);
//		
//		hostFilterHandler.getAvailHost(null, null, 1, ServiceTypeEnum.VM_CONTROLLER, hosts);
//		
//		VMSchedulerImpl impl = new VMSchedulerImpl();
//		try {
//			impl.selectHost("test", 1, 1, 1, null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		CoreFilter cf = new CoreFilter();
		RamFilter rf = new RamFilter();
		Host host = new Host();
		host.setUuid("782BCB435306");
		Integer ccc = cf.getUsedCpuCore(host);
		Integer rrr = rf.getUsedRamMb(host);
		System.out.println("used core is " + ccc);
		System.out.println("used ram is " + rrr);
	}

}
