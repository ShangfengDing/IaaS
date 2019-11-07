package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sun.jersey.api.NotFoundException;

import appcloud.api.beans.Backup;
import appcloud.api.manager.VolumeManager;

@Path("{tenantId}/ac-backups")
public class BackupResource {
	private VolumeManager volumeManager;
	
	public VolumeManager getVolumeManager() {
		return volumeManager;
	}

	public void setVolumeManager(VolumeManager volumeManager) {
		this.volumeManager = volumeManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Backup> index(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("server_id") String serverId) throws Exception{
		return volumeManager.getBackupList(tenantId, true, serverId);
	}

	@GET
	@Path("/detail")
	@Produces(MediaType.APPLICATION_XML)
	public List<Backup> detail(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("server_id") String serverId) throws Exception{
		return volumeManager.getBackupList(tenantId, true, serverId);
	}
	
	@GET
	@Path("/{backupId}")
	@Produces(MediaType.APPLICATION_XML)
	public Backup show(@PathParam("tenantId") String tenantId, @PathParam("backupId") String backupId) throws Exception{
		return volumeManager.getBackup(tenantId, backupId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Backup create(@PathParam("tenantId") String tenantId, Backup backup) throws Exception{
		return volumeManager.createBackup(tenantId, backup);
	}
	
	@DELETE
	@Path("/{backupId}")
	public void delete(@PathParam("tenantId") String tenantId, @PathParam("backupId") String backupId) throws Exception {
		volumeManager.deleteBackup(tenantId, backupId);
	}
	
	@POST
	@Path("/{backupId}/action")
	@Consumes(MediaType.APPLICATION_XML)
	public Response action(@PathParam("tenantId") String tenantId, @PathParam("backupId") String backupId,
			String action) throws Exception {
		Document document;
        document = DocumentHelper.parseText(action);
        Element root = document.getRootElement();
		if (root.getName().equals("ac-backup-restore")) {
			volumeManager.revertBackup(tenantId, backupId);
			return Response.ok().build();
		} else{
			throw new NotFoundException();
		}
	}
}
