package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import appcloud.api.beans.Snapshot;
import appcloud.api.manager.SnapshotManager;

public class FakeSnapshotManager implements SnapshotManager {

	@Override
	public List<Snapshot> getList(String tenantId, boolean detailed, String serverId) {
		List<Snapshot> snapshots = new ArrayList<Snapshot>();
		snapshots.add(new Snapshot("id1", "name1", tenantId, "descrition1", "volumeId1", "staus1", 1, new Date(), null, null));
		snapshots.add(new Snapshot("id2", "name2", tenantId, "descrition2", "volumeId2", "staus2", 2, new Date(),null, null));
		return snapshots;
	}

	@Override
	public Snapshot get(String tenantId, String snapshotId) {
		return new Snapshot("id_get", "name_get", tenantId,
				"descrition_get", "volumeId_get", "staus_get", 3, new Date(),null, null);
	}

	@Override
	public Snapshot create(String tenantId, Snapshot snapshot) {
		return new Snapshot("id_create", "name_create", tenantId, 
				"descrition_create", "volumeId_create", "staus_create", 3, new Date(),null, null);
	}
	
	@Override
	public void delete(String tenantId, String snapshotId) {
		System.out.println("deleting " + tenantId + "'s snapshot " + snapshotId);
	}

	@Override
	public void revert(String tenantId, String snapshotId) throws Exception {
		System.out.println("revert " + tenantId + "'s snapshot " + snapshotId);
	}

	@Override
	public Snapshot update(String tenantId, String snapshotId, Snapshot snapshot)
			throws Exception {
		System.out.println("update " + tenantId + "'s snapshot " + snapshotId);
		Snapshot snapshotUpdated = new Snapshot(snapshotId, snapshot.displayName, tenantId,
				snapshot.displayDescription, "updated volumeId", "new",
				3, new Date(), false, null) ;
		return snapshotUpdated;
	}

	@Override
	public List<Snapshot> searchByProperties(String tenantId, String userId,
			String snapshotId, String volumeUuid, String displayName, String status,
			Integer page, Integer size) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String countByProperties(String tenantId, String userId,
			String snapshotId, String volumeUuid,String displayName,
			String staus) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
