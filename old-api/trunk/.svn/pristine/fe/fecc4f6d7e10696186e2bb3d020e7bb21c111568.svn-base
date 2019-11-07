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

import appcloud.api.beans.Volume;
import appcloud.api.manager.VolumeManager;

@Path("{tenantId}/volumes")
public class VolumeResource {
	private VolumeManager volumeManager;
	
	public VolumeManager getVolumeManager() {
		return volumeManager;
	}

	public void setVolumeManager(VolumeManager volumeManager) {
		this.volumeManager = volumeManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Volume> index(@PathParam("tenantId") String tenantId) throws Exception{
		return volumeManager.getList(tenantId, false);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Volume create(@PathParam("tenantId") String tenantId, Volume cReq) throws Exception{
		return volumeManager.create(tenantId, cReq);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("detail")
	public List<Volume> detail(@PathParam("tenantId") String tenantId) throws Exception{
		return volumeManager.getList(tenantId, true);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("{volumeId}")
	public Volume show(@PathParam("tenantId") String tenantId, @PathParam("volumeId") String volumeId) throws Exception{
		return volumeManager.get(tenantId, volumeId);
	}

	
	@DELETE
	@Path("{volumeId}")
	public void delete(@PathParam("tenantId") String tenantId, @PathParam("volumeId") String volumeId) throws Exception{
		volumeManager.delete(tenantId, volumeId);
	}
	
	@PUT
	@Path("{volumeId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Volume update(
			@PathParam("tenantId") String tenantId, 
			@PathParam("volumeId") String volumeId, 
			Volume volume) throws Exception{
		return volumeManager.update(tenantId, volumeId, volume);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search")
	public List<Volume> searchByProperties(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("user_id") String userId,
			@DefaultValue("") @QueryParam("uuid") String uuid,
			@DefaultValue("") @QueryParam("server_id") String serverId,
			@DefaultValue("") @QueryParam("usage_type") String usageType,
			@DefaultValue("") @QueryParam("status") String status,
			@DefaultValue("") @QueryParam("attach_status") String attachStatus,
			@DefaultValue("false") @QueryParam("is_backup") String isBackupStr,
			@DefaultValue("") @QueryParam("display_name") String displayName,
			@DefaultValue("") @QueryParam("zone_id") String zoneIdStr, 
			@DefaultValue("") @QueryParam("aggregate_id") String aggregateIdStr,
			@DefaultValue("") @QueryParam("host_id") String hostId, 
			@DefaultValue("") @QueryParam("page") String pageStr,
			@DefaultValue("") @QueryParam("size") String sizeStr) throws Exception{
		if(userId.equals(""))
			userId = null;
		if(uuid.equals(""))
			uuid = null;
		if(serverId.equals(""))
			serverId = null;
		if(usageType.equals(""))
			usageType = null;
		if(status.equals(""))
			status = null;
		if(attachStatus.equals(""))
			attachStatus = null;
		boolean isBackup = false;
		if(isBackupStr.equals("true"))
			isBackup = true;
		if(displayName.equals(""))
			displayName = null;
		Integer zoneId = null;
		if(!zoneIdStr.equals("")){
			try {
				zoneId = Integer.valueOf(zoneIdStr);
			}catch (Exception e){
				;
			}
		}
		Integer aggregateId = null;
		if(!aggregateIdStr.equals("")) {
			try {
				aggregateId = Integer.valueOf(aggregateIdStr);
			}catch (Exception e) {
				;
			}
		}

		if(hostId.equals(""))
			hostId = null;
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
		return volumeManager.searchByProperties(tenantId, uuid, userId, serverId, usageType, status, 
				attachStatus, isBackup, displayName, zoneId, aggregateId, hostId, page, size);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search/count")
	public String countByProperties(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("user_id") String userId,
			@DefaultValue("") @QueryParam("uuid") String uuid,
			@DefaultValue("") @QueryParam("server_id") String serverId,
			@DefaultValue("") @QueryParam("usage_type") String usageType,
			@DefaultValue("") @QueryParam("status") String status,
			@DefaultValue("") @QueryParam("attach_status") String attachStatus,
			@DefaultValue("false") @QueryParam("is_backup") String isBackupStr,
			@DefaultValue("") @QueryParam("display_name") String displayName,
			@DefaultValue("") @QueryParam("zone_id") String zoneIdStr, 
			@DefaultValue("") @QueryParam("aggregate_id") String aggregateIdStr, 
			@DefaultValue("") @QueryParam("host_id") String hostId) throws Exception {
		if(userId.equals(""))
			userId = null;
		if(uuid.equals(""))
			uuid = null;
		if(serverId.equals(""))
			serverId = null;
		if(usageType.equals(""))
			usageType = null;
		if(status.equals(""))
			status = null;
		if(attachStatus.equals(""))
			attachStatus = null;
		boolean isBackup = false;
		if(isBackupStr.equals("true"))
			isBackup = true;
		if(displayName.equals(""))
			displayName = null;
		Integer zoneId = null;
		if(!zoneIdStr.equals("")){
			try {
				zoneId = Integer.valueOf(zoneIdStr);
			}catch (Exception e){
				;
			}
		}
		Integer aggregateId = null;
		if(!aggregateIdStr.equals("")) {
			try {
				aggregateId = Integer.valueOf(aggregateIdStr);
			}catch (Exception e) {
				;
			}
		}

		if(hostId.equals(""))
			hostId = null;
		return volumeManager.countByProperties(tenantId, uuid, userId, serverId, usageType, status,
				attachStatus, isBackup, displayName, zoneId, aggregateId, hostId);
	}
}
