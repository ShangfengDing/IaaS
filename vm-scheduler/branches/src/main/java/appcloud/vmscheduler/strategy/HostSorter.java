package appcloud.vmscheduler.strategy;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.vmschduler.utils.Constants;
import appcloud.vmscheduler.impl.DBAgent;

public class HostSorter implements IHostSorter {
	private static Logger logger = Logger.getLogger(HostSorter.class);
	private static DBAgent dbAgent = DBAgent.getInstance();

	@Override
	public int scoreHost(Host host) throws Exception {
		try {
			Integer memory = dbAgent.getHostByUuid(host.getUuid())
					.getMemoryMb();
			float memoryUse = memory
					* dbAgent.getFreshHostLoad(host.getUuid()).getMemPercent()
					/ 100;
			Integer cpuCore = dbAgent.getHostByUuid(host.getUuid())
					.getCpuCore();
			Integer cpuMhz = dbAgent.getHostByUuid(host.getUuid()).getCpuMhz();
			float cpuUse = cpuMhz * cpuCore
					* dbAgent.getFreshHostLoad(host.getUuid()).getCpuPercent()
					/ 100;
			return (memory - (int) memoryUse)
					* Constants.config.getInt("memoryWeight")
					+ (cpuCore * cpuMhz - (int) cpuUse)
					* Constants.config.getInt("cpuWeight");
		} catch (Exception e) {
			logger.error("获取负载信息失败," + e.getMessage());
		}
		return 0;
	}

	@Override
	public void setHosts(List<Host> hosts) {
		// TODO Auto-generated method stub

	}

}
