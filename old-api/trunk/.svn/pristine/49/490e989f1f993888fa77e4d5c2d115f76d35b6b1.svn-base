package appcloud.api.manager.fake;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import appcloud.api.beans.AcHost;
import appcloud.api.beans.Load;
import appcloud.api.enums.AcHostStatusEnum;
import appcloud.api.enums.AcHostTypeEnum;
import appcloud.api.manager.AcHostManager;
import appcloud.common.model.Host.HostStatusEnum;
import appcloud.common.model.Host.HostTypeEnum;
import appcloud.common.model.Service.ServiceTypeEnum;

public class FakeAcHostManager implements AcHostManager {
	public  List<AcHost> getList(String tenantId, boolean detailed, Integer aggregateId, Integer zoneId) {
		List<AcHost> Hosts = new ArrayList<AcHost>();	
		Hosts.add(new AcHost("host1", "192.168.1.110", AcHostTypeEnum.COMPUTE_NODE,
				AcHostStatusEnum.NORMAL_LOAD));
		Hosts.add(new AcHost("host2", "192.168.1.13", AcHostTypeEnum.FUNCTION_NODE, 
				AcHostStatusEnum.NORMAL_LOAD));
		return Hosts;
	}
	

	
	public AcHost get(String tenantId, String hostName){
		AcHost host = new AcHost("host_get", "192.168.1.1", AcHostTypeEnum.COMPUTE_NODE,
				AcHostStatusEnum.HIGH_LOAD);
		return host;
	}

	@Override
	public List<Load> getMonitorData(String tenantId, String hostId,
			String type, Timestamp startTime, Timestamp endTime) {
		return new FakeServerManager().getMonitorData(tenantId, hostId, type, startTime, endTime);
	}

	@Override
	public Load getCurrentLoad(String tenantId, String hostId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByProperties(String tenantId, String ip,
			HostTypeEnum hostType, HostStatusEnum hostStatus, Integer aid,
			Integer zid, ServiceTypeEnum serviceType, int cpuOperator,
			Integer cpuCount, int memoryOperator, Integer memoryCount,
			int diskOperator, Integer diskCount) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AcHost> getHostsByProperties(String tenantId, String ip,
			HostTypeEnum type, HostStatusEnum status, Integer aid, Integer zid,
			ServiceTypeEnum serviceType, int cpuOperator, Integer cpuCount,
			int memoryOperator, Integer memoryCount, int diskOperator,
			Integer diskCount, Integer page, Integer size) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
