package appcloud.api.manager;

import java.sql.Timestamp;
import java.util.List;

import appcloud.api.beans.AcHost;
import appcloud.api.beans.Load;
import appcloud.common.model.Host.HostStatusEnum;
import appcloud.common.model.Host.HostTypeEnum;
import appcloud.common.model.Service.ServiceTypeEnum;

public interface AcHostManager {
	public List<AcHost> getList(String tenantId, boolean detailed, Integer aggregateId, Integer zoneId)
			throws Exception;
	public AcHost get(String tenantId, String hostId) throws Exception;
	public List<Load> getMonitorData(String tenantId, String hostId,
			String type, Timestamp startTime, Timestamp endTime);
	
	public Load getCurrentLoad(String tenantId, String hostId) throws Exception;
	
	public long countByProperties(String tenantId, String ip, HostTypeEnum hostType, HostStatusEnum hostStatus, 
			   Integer aid, Integer zid, ServiceTypeEnum serviceType, 
			   int cpuOperator, Integer cpuCount,
			   int memoryOperator, Integer memoryCount,
			   int diskOperator, Integer diskCount) throws Exception;
	
	public List<AcHost> getHostsByProperties(String tenantId, String ip, HostTypeEnum type, HostStatusEnum status, 
			   Integer aid, Integer zid, ServiceTypeEnum serviceType, 
			   int cpuOperator, Integer cpuCount,
			   int memoryOperator, Integer memoryCount,
			   int diskOperator, Integer diskCount,
			   Integer page, Integer size) throws Exception;
}
