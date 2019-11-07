package appcloud.api.manager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import appcloud.api.beans.Load;
import appcloud.api.beans.Meta;
import appcloud.api.beans.Metadata;
import appcloud.api.beans.Server;
import appcloud.api.beans.server.ServerActionReboot;
import appcloud.api.beans.server.ServerActionRebuild;
import appcloud.api.beans.server.ServerCreateReq;
import appcloud.api.beans.server.ServerUpdateReq;

public interface ServerManager {
	
	public List<Server> getList(String tenantId, boolean detailed, boolean allTenants) throws Exception;
	
	public Server getDetail(String tenantId, String serverId) throws Exception;
	
	public Server create(String tenantId, ServerCreateReq req) throws Exception;
	
	public Server migrate(String tenantId, String serverId, String hostUuid) throws Exception;
	
	public Server onlineMigrate(String tenantId, String serverId, String hostUuid) throws Exception;
	
	public void osStart(String tenantId, String serverId) throws Exception;
	
	public void osStop(String tenantId, String serverId) throws Exception;

	public void resume(String tenantId, String serverId) throws Exception;

	public void reboot(String tenantId, String serverId, ServerActionReboot reboot) throws Exception;

	public Server rebuild(String tenantId, String serverId, ServerActionRebuild rebuild) throws Exception;
	
	public String reset(String tenantId, String serverId) throws Exception;

	public void resize(String tenantId, String serverId, String flavorRef) throws Exception;

	public Server update(String tenantId, String serverId, Server server) throws Exception;
	
	public void suspend(String tenantId, String serverId) throws Exception;
	
	public void forceDelete(String tenantId, String serverId) throws Exception;

	public void terminate(String tenantId, String serverId) throws Exception;

	public List<Load> getMonitorData(String tenantId, String serverId, String type, Timestamp startTime, Timestamp endTime);
	
	public Metadata getMetadata(String tenantId, String serverId) throws Exception;
	
	public Metadata updateMetadata(String tenantId, String serverId, Metadata metadata) throws Exception;
	
	public Metadata setMetadata(String tenantId, String serverId, Metadata metadata) throws Exception;

	
	public Meta getMetadataItem(String tenantId, String serverId, String key) throws Exception;
	
	public Meta updateMetadataItem(String tenantId, String serverId, String key,boolean release ,Meta meta) throws Exception;

	public Meta setMetadataItem(String tenantId, String serverId, String key, Meta meta) throws Exception;

	public void bootFromISO(String tenantId, String serverId, String imgRef) throws Exception;

	public void detachISO(String tenantId, String serverId) throws Exception;

	public void forceStop(String tenantId, String serverId) throws Exception;
	
	public String forceRefresh(String tenantId, String serverId) throws Exception;
	
	public List<Server> searchByProperties(String tenantId, String userId, String serverId,  String serverName, 
			String status, Integer zoneId, Integer aggregateId, String hostId, String serverIp, 
			Date startdate, Date endDate, Integer  page, Integer size) throws Exception;
	
	public List<Server> searchByProperties(String tenantId, List<Integer> userIds, String serverId,  String serverName, 
			String status, Integer zoneId, Integer aggregateId, String hostId, String serverIp, 
			Date startdate, Date endDate, Integer  page, Integer size) throws Exception;
	
	public Long countByProperties(String tenantId, String userId, String serverId, String serverName, 
			String status, Integer zoneId, Integer aggregateId, String hostId, String serverIp, 
			Date startDate, Date endDate) throws Exception;
	
	public Long countByProperties(String tenantId, List<Integer> userIds, String serverId, String serverName, 
			String status, Integer zoneId, Integer aggregateId, String hostId, String serverIp, 
			Date startDate, Date endDate) throws Exception;
	
	public void modPasswd(String tenantId, String serverId, String userName, String passwd, String type) throws Exception;

	public Server updateServerName(String tenantId, ServerUpdateReq uReq) throws Exception;
}
