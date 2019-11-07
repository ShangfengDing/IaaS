package appcloud.api.manager;

import java.sql.Timestamp;
import java.util.List;
import appcloud.api.beans.Host;
import appcloud.api.beans.Load;

public interface HostManager {
	
	public List<Host> getList(String tenantId, boolean detailed) throws Exception;
	public Host get(String tenantId, String hostName) throws Exception;
	public List<Load> getMonitorData(String tenantId, String hostName,
			String type, Timestamp startTime, Timestamp endTime);
}
