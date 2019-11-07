package appcloud.openapi.query;

import java.util.Date;
import java.util.List;

import appcloud.common.model.VmInstance;

public interface InstanceQuery {

	public List<VmInstance> searchInstancesByProperties(Integer userId,	String serverId, 
			String serverName,	String status, Integer zoneId, Integer aggregateId, String hostId,
			String serverIp, Date startdate, Date endDate, Integer page, Integer size) throws Exception;
}
