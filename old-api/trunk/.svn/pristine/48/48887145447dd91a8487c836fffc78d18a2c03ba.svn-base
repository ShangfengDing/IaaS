package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import appcloud.api.beans.Backup;

public class BackupClient extends AbstractClient<Backup>{
	private final static String RESTORE = XML_HEADER + "<ac-backup-restore/>";
	public BackupClient() {
		super();
	}
	
	public BackupClient(String baseURI) {
		super(baseURI);
	}
	
	@Override
	protected Class<?> getType() {
		return Backup.class;
	}
	
	@Override
	protected GenericType<List<Backup>> getGenericType() {		
		GenericType<List<Backup>> type = new GenericType<List<Backup>>() {};
		return type;
	}

	protected String getPath() {
		return   "ac-backups";
	}
	private String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	private String buildPathWithId(String tenantId, String backupId) {
		return String.format("%s/%s", buildPath(tenantId), backupId);
	}

	private String buildActionPath(String tenantId, String backupId) {
		return String.format("%s/%s", buildPathWithId(tenantId, backupId), "action");
	}
	
	public void revertBackup(String tenantId, String backupId) {
		super.postWithoutRet(buildActionPath(tenantId, backupId), RESTORE);
	}

	public Backup createBackup(String tenantId, Backup backup) {
		return super.postWithRet(buildPath(tenantId), backup);
	}
	
	public List<Backup> getBackupList(String tenantId) {
		return super.getList(buildPath(tenantId), null);
	}

	public boolean deleteBackup(String tenantId, String backupId) {
		return super.delete(buildPathWithId(tenantId, backupId));
	}
		
	public List<Backup> getBackupListOfServer(String tenantId, String serverId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("tenant_id", tenantId);
		params.add("server_id", serverId);
		return super.getList(buildPath(tenantId), params);
	}
	
	public Backup getBackup(String tenantId, String backupId) {
		return super.get(buildPathWithId(tenantId, backupId));
	}
	
}
