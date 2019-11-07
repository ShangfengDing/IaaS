package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.Backup;
import appcloud.api.beans.Volume;

public interface VolumeManager {

	public List<Volume> getList(String tenantId, boolean detailed) throws Exception;
	
	public Volume get(String tenantId, String volumeId) throws Exception;
	
	public Volume create(String tenantId, Volume cReq) throws Exception;
	
	public void delete(String tenantId, String volumeId) throws Exception;

	public Backup getBackup(String tenantId, String backupId) throws Exception;

	public List<Backup> getBackupList(String tenantId, boolean detailed, String serverId) throws Exception;

	public Backup createBackup(String tenantId, Backup backup) throws Exception;

	public void deleteBackup(String tenantId, String backupId) throws Exception;
	
	public Volume update(String tenantId, String volumeId, Volume volume) throws Exception;
	
	public void revertBackup(String tenantId, String backupId) throws Exception;
	
	
	public List<Volume> searchByProperties(String tenantId, String uuid, String userId, String serverId,
			String usageType, String status, String attachStatus, boolean isBackup, String displayName, 
			Integer zoneId, Integer aggregateId, String hostId, Integer page, Integer size) throws Exception ;
	
	public String countByProperties(String tenantId, String uuid, String userId, String serverId,
			String usageType, String status, String attachStatus, boolean isBackup, String displayName, 
			Integer zoneId, Integer aggregateId, String hostId)throws Exception ;
}
