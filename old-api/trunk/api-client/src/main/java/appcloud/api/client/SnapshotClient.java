package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.Snapshot;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;


public class SnapshotClient extends AbstractClient<Snapshot> {
	private final static String AC_REVERT = XML_HEADER + "<ac-revert/>";

	public SnapshotClient() {
		super();
	}
	
	public SnapshotClient(String baseURI) {
		super(baseURI);
	}
	
	@Override
	protected Class<?> getType() {
		return Snapshot.class;
	}

	@Override
	protected GenericType<List<Snapshot>> getGenericType() {		
		GenericType<List<Snapshot>> type = new GenericType<List<Snapshot>>() {};
		return type;
	}
	
	private String getPath() {
		return  "snapshots";
	}

	//@Override
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	//@Override
	protected String buildPathWithId(String tenantId, String snapshotId) {
		return String.format("%s/%s", buildPath(tenantId), snapshotId);
	}
	
	protected String buildActionPath(String tenantId, String snapshotId) {
		return String.format("%s/%s", buildPathWithId(tenantId, snapshotId), "action");
	}
	
	public Snapshot get(String tenantId, String snapshotId) {
		return super.get(buildPathWithId(tenantId, snapshotId));
	}
	
	public List<Snapshot> getSnapshots(String tenantId) {
		return super.getList(buildPath(tenantId) + "/detail", null);
	}
	
	public List<Snapshot> getSnapshotsOfServer(String tenantId, String serverId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("server_id", serverId);
		return super.getList(buildPath(tenantId) + "/detail", params);
	}

	public Snapshot createSnapshot(String tenantId, Snapshot snapshot) {
		return super.postWithRet(buildPath(tenantId), snapshot);
	}
	
	public boolean deleteSnapshot(String tenantId, String snapshotId) {
		return super.delete(buildPathWithId(tenantId, snapshotId));
	}
	
	public boolean revertSnapshot(String tenantId, String snapshotId) {
		return super.postWithoutRet(buildActionPath(tenantId, snapshotId), AC_REVERT);
	}
	
	public Snapshot updateSnapshot(String tenantId, String snapshotId, String displayName,
			String displayDescription, String userId) {
		Snapshot req = new Snapshot();
		req.displayName = displayName;
		req.displayDescription = displayDescription;
		req.id = snapshotId;
		req.tenantId = userId;
		return super.update(buildPathWithId(tenantId, snapshotId), req);
	}
	
	public List<Snapshot> searchByProperties(String tenantId, String userId,
			String snapshotId, String volumeUuid, String displayName, String status,
			Integer page, Integer size) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(userId != null)
			params.add("user_id", userId);
		if(snapshotId != null)
			params.add("snapshot_id", snapshotId);
		if(volumeUuid != null)
			params.add("volume_uuid", volumeUuid);
		if(displayName != null)
			params.add("display_name", displayName);
		if(status != null)
			params.add("status", status); 
		if(page!= null)
			params.add("page", String.valueOf(page));
		if(size != null)
			params.add("size", String.valueOf(size));
		return super.getList(String.format("%s/search", buildPath(tenantId)), params);
	}
	
	public Long countByProperties(String tenantId, String userId,
			String snapshotId, String volumeUuid, String displayName, String status) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(userId != null)
			params.add("user_id", userId);
		if(snapshotId != null)
			params.add("snapshot_id", snapshotId);
		if(volumeUuid != null)
			params.add("volume_uuid", volumeUuid);
		if(displayName != null)
			params.add("display_name", displayName);
		if(status != null)
			params.add("status", status);
		return super.count(String.format("%s/search/count", buildPath(tenantId)), params);
	}
}
