package appcloud.vmscheduler.strategy;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.vmschduler.utils.Constants;
import appcloud.vmschduler.utils.RSParamUtil;
import appcloud.vmscheduler.impl.DBAgent;

public class HostSorter implements IHostSorter {
	private static Logger logger = Logger.getLogger(HostSorter.class);
	private static DBAgent dbAgent = DBAgent.getInstance();
	
	private String params = null;
	private Integer cpuWeight = 1;
	private Integer memWeight = 1;

	@Override
	public int scoreHost(Host host) throws Exception {
		try {
			Integer memory = dbAgent.getHostByUuid(host.getUuid()).getMemoryMb();
			float memoryUse = memory * dbAgent.getFreshHostLoad(host.getUuid()).getMemPercent() / 100;
			Integer cpuCore = dbAgent.getHostByUuid(host.getUuid()).getCpuCore();
			Integer cpuMhz = dbAgent.getHostByUuid(host.getUuid()).getCpuMhz();
			float cpuUse = cpuMhz * cpuCore * dbAgent.getFreshHostLoad(host.getUuid()).getCpuPercent() / 100;
			if((params != null) && (!params.equals(""))) {
				cpuWeight = RSParamUtil.getCpuWeight(params);
				memWeight = RSParamUtil.getMemoryWeight(params);
			}
			return (memory - (int) memoryUse) * memWeight + (cpuCore * cpuMhz - (int) cpuUse) * cpuWeight;
		} catch (Exception e) {
			logger.error("获取负载信息失败," + e.getMessage());
		}
		return 0;
	}

	@Override
	public void setHosts(List<Host> hosts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParams(String params) {
		this.params = params;
	}

}
