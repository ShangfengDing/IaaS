package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.Snapshot;

public interface SnapshotManager {

	public List<Snapshot> getList(String tenantId, boolean detailed, String serverId) throws Exception;
	
	public Snapshot get(String tenantId, String snapshotId) throws Exception;
	
	public Snapshot create(String tenantId, Snapshot snapshot) throws Exception;
	
	public void delete(String tenantId, String snapshotId) throws Exception;
	
	public void revert(String tenantId, String snapshotId) throws Exception;
	
	public Snapshot update(String tenantId, String snapshotId, Snapshot snapshot) throws Exception;
	
	public List <Snapshot> searchByProperties(String tenantId, String userId, String snapshotId,
			String volumeUuid, String displayName, String status, Integer page, Integer size) throws Exception;
	
	public String countByProperties(String tenantId, String userId, String snapshotId,
			String volumeUuid, String displayName, String status) throws Exception;
}
