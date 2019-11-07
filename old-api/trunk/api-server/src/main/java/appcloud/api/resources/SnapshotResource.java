package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import appcloud.api.beans.Snapshot;
import appcloud.api.manager.SnapshotManager;

@Path("{tenantId}/snapshots")
public class SnapshotResource {
	private SnapshotManager snapshotManager;
	
	public SnapshotManager getSnapshotManager() {
		return snapshotManager;
	}

	public void setSnapshotManager(SnapshotManager snapshotManager) {
		this.snapshotManager = snapshotManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Snapshot> index(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("server_id") String serverId) throws Exception{
		return snapshotManager.getList(tenantId, true, serverId);
	}

	@GET
	@Path("/detail")
	@Produces(MediaType.APPLICATION_XML)
	public List<Snapshot> detail(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("server_id") String serverId) throws Exception{
		return snapshotManager.getList(tenantId, true, serverId);
	}
	
	@GET
	@Path("/{snapshotId}")
	@Produces(MediaType.APPLICATION_XML)
	public Snapshot show(@PathParam("tenantId") String tenantId, @PathParam("snapshotId") String snapshotId) throws Exception{
		return snapshotManager.get(tenantId, snapshotId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Snapshot create(@PathParam("tenantId") String tenantId, Snapshot snapshot) throws Exception{
		return snapshotManager.create(tenantId, snapshot);
	}
	
	@POST
	@Path("/{snapshotId}/action")
	@Consumes(MediaType.APPLICATION_XML)
	public Response action(@PathParam("tenantId") String tenantId, @PathParam("snapshotId") String snapshotId, String action) throws Exception {
		Document document;
		System.out.println(action);
        document = DocumentHelper.parseText(action);
        Element root = document.getRootElement();
		if (root.getName().equals("ac-revert")) {
			snapshotManager.revert(tenantId, snapshotId);
			return Response.ok().build();
		} else{
			throw new NotFoundException();
		}
	}
	
	@DELETE
	@Path("/{snapshotId}")
	public void delete(@PathParam("tenantId") String tenantId, @PathParam("snapshotId") String snapshotId) throws Exception {
		snapshotManager.delete(tenantId, snapshotId);
	}
	
	@PUT
	@Path("/{snapshotId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Snapshot update(
			@PathParam("tenantId") String tenantId, 
			@PathParam("snapshotId") String snapshotId, 
			Snapshot snapshot) throws Exception {
		return snapshotManager.update(tenantId, snapshotId, snapshot);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search")
	public List<Snapshot> searchByProperties(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("user_id") String userId,
			@DefaultValue("") @QueryParam("snapshot_id") String snapshotId,
			@DefaultValue("") @QueryParam("volume_uuid") String volumeUuid,
			@DefaultValue("") @QueryParam("display_name") String displayName,
			@DefaultValue("") @QueryParam("status") String status,
			@DefaultValue("") @QueryParam("page") String pageStr,
			@DefaultValue("") @QueryParam("size") String sizeStr) throws Exception{
		
		if(userId.equals(""))
			userId = null;
		if(snapshotId.equals(""))
			snapshotId = null;
		if(volumeUuid.equals(""))
			volumeUuid = null;
		if(displayName.equals(""))
			displayName = null;
		if(status.equals(""))
			status = null;
		
		Integer page = 0;
		if(!pageStr.equals("")){
			try {
				page = Integer.valueOf(pageStr);
			}catch (Exception e){
				;
			}
		}
		Integer size = 0;
		if(!sizeStr.equals("")) {
			try {
				size = Integer.valueOf(sizeStr);
			}catch (Exception e) {
				;
			}
		}
		return snapshotManager.searchByProperties(tenantId, userId, snapshotId, volumeUuid, 
				displayName, status, page, size);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search/count")
	public String countByProperties(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("user_id") String userId,
			@DefaultValue("") @QueryParam("snapshot_id") String snapshotId,
			@DefaultValue("") @QueryParam("volume_uuid") String volumeUuid,
			@DefaultValue("") @QueryParam("display_name") String displayName,
			@DefaultValue("") @QueryParam("status") String status)
	throws Exception {
		if(userId.equals(""))
			userId = null;
		if(snapshotId.equals(""))
			snapshotId = null;
		if(volumeUuid.equals(""))
			volumeUuid = null;
		if(displayName.equals(""))
			displayName = null;
		if(status.equals(""))
			status = null;
		return snapshotManager.countByProperties(tenantId, userId, snapshotId, volumeUuid, displayName, status);
	}
}
