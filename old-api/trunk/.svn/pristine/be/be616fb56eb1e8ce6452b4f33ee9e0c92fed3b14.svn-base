package appcloud.api.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import appcloud.api.beans.Backup;
import appcloud.api.beans.Volume;
import appcloud.api.enums.AcVolumeTypeEnum;

public class VolumeClient extends AbstractClient<Volume>{
	
	public VolumeClient() {
		super();
		backupClient = new BackupClient();
	}
	
	public VolumeClient(String baseURI) {
		super(baseURI);
		backupClient = new BackupClient(baseURI);
	}
	
	@Override
	protected Class<?> getType() {
		return Volume.class;
	}
	
	@Override
	protected GenericType<List<Volume>> getGenericType() {		
		GenericType<List<Volume>> type = new GenericType<List<Volume>>() {};
		return type;
	}
	
	protected String getPath() {
		return  "volumes";
	}
	
	//@Override
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	//@Override
	protected String buildPathWithId(String tenantId, String volumeId) {
		return String.format("%s/%s", buildPath(tenantId), (String)volumeId);
	}
	
	public Volume get(String tenantId, String volumeId) {
		return super.get(buildPathWithId(tenantId, volumeId));
	}
	
	public List<Volume> getVolumes(String tenantId, boolean detailed) {
		if(detailed)
			return super.getList(buildPath(tenantId) + "/detail", null);
		return super.getList(buildPath(tenantId), null);
	}
	
	/**
	 * 创建新硬盘
	 * @param tenantId
	 * @param volume	
	 * 		前端调用这个api时，该JavaBean必须设置的字段有：
	 * 		displayName,displayDescription,size,availabilityZone
	 * @return
	 */
	public Volume createVolume(String tenantId, Volume volume) {
		return super.postWithRet(buildPath(tenantId), volume);
	}
	
	public boolean deleteVolume(String tenantId, String volumeId) {
		return super.delete(buildPathWithId(tenantId, volumeId));
	}
	
	
	public Volume update(String tenantId, String volumeId, String userId, String displayName, String displayDescription) {
		Volume req = new Volume();
		if(volumeId == null)
			return req;
		req.id = volumeId;
		if(displayName != null && !displayName.equals(""))
			req.displayName = displayName;
		if(displayDescription != null && !displayDescription.equals(""))
			req.displayDescription = displayDescription;
		if(userId != null) {
			req.metadata = new HashMap<String, String>();
			req.metadata.put("userId", userId);
		}
		return super.update(buildPathWithId(tenantId, volumeId), req);
	}
	
	public List<Volume> getByAcVolumeType(String tenantId, AcVolumeTypeEnum acVolumeType) {
		List<Volume> volumes = getVolumes(tenantId, true);
		List<Volume> filteredVolumes = new ArrayList<Volume>();
		for(Volume volume : volumes){
			if(volume.acVolumeType != null && volume.acVolumeType.equals(acVolumeType))
				filteredVolumes.add(volume);
		}
		return filteredVolumes;
	}
	
	public List<Volume> searchByProperties(String tenantId, String uuid,String userId, 
			String serverId,String usageType, String status, String attachStatus, boolean isBackup,
			String displayName, Integer zoneId, Integer aggregateId, String hostId,
			Integer page, Integer size) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(userId != null)
			params.add("user_id", userId);
		if( uuid != null)
			params.add("uuid", uuid);
		if(serverId != null)
			params.add("server_id", serverId);
		if(usageType != null)
			params.add("usage_type", usageType);
		if(status != null)
			params.add("status", status); 
		if(attachStatus != null)
			params.add("attach_status", attachStatus);
		if(isBackup)
			params.add("is_backup", "true");
		if(displayName != null)
			params.add("display_name", displayName);
		if(zoneId != null)
			params.add("zone_id",String.valueOf(zoneId));
		if(aggregateId != null)
			params.add("aggregate_id",String.valueOf(aggregateId)); 
		if(hostId != null)
			params.add("host_id",String.valueOf(hostId)); 
		if(page!= null)
			params.add("page", String.valueOf(page));
		if(size != null)
			params.add("size", String.valueOf(size));
		return super.getList(String.format("%s/search", buildPath(tenantId)), params);
	}
	
	public Long countByProperties(String tenantId, String uuid, String userId, 
			String serverId, String usageType, String status,String attachStatus, boolean isBackup,
			String displayName, Integer zoneId, Integer aggregateId, String hostId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(userId != null)
			params.add("user_id", userId);
		if( uuid != null)
			params.add("uuid", uuid);
		if(serverId != null)
				params.add("server_id", serverId);
		if(usageType != null)
			params.add("usage_type", usageType);
		if(status != null)
			params.add("status", status); 
		if(attachStatus != null)
			params.add("attach_status", attachStatus);
		if(isBackup)
			params.add("is_backup", "true");
		if(displayName != null)
		if(zoneId != null)
			params.add("zone_id",String.valueOf(zoneId));
		if(aggregateId != null)
			params.add("aggregate_id",String.valueOf(aggregateId));
		if(hostId != null)
			params.add("host_id",String.valueOf(hostId)); 
		return super.count(String.format("%s/search/count", buildPath(tenantId)), params);
	}
	
	/**
	 * @param tenantId
	 * @param backup
	 * backup中，需要赋值的字段为:displayName,displayDescription,volumeId,force
	 * @return
	 */
	//backup 原来由VolumeClient获取,分离出去之后为了不影响前端代码,这里保留接口
	private BackupClient backupClient;
	public Backup createBackup(String tenantId, Backup backup) {
		return backupClient.createBackup(tenantId, backup);
	}
	
	public List<Backup> getBackupList(String tenantId) {
		return backupClient.getBackupList(tenantId);
	}

	public List<Backup> getBackupListOfServer(String tenantId, String serverId) {
		return backupClient.getBackupListOfServer(tenantId, serverId);
	}
	
	public Backup getBackup(String tenantId, String backupId) {
		return backupClient.getBackup(tenantId, backupId);
	}
	public void revertBackup(String tenantId, String backupId) {
		backupClient.revertBackup(tenantId, backupId);
	}
}
