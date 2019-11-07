package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.VolumeAttachment;
import appcloud.api.manager.VolumeAttachmentManager;

@Path("{tenantId}/servers/{serverId}/os-volume_attachments")
public class VolumeAttachmentResource {
	private VolumeAttachmentManager volumeAttachmentManager;
	
	public VolumeAttachmentManager getVolumeAttachmentManager() {
		return volumeAttachmentManager;
	}

	public void setVolumeAttachmentManager(
			VolumeAttachmentManager volumeAttachmentManager) {
		this.volumeAttachmentManager = volumeAttachmentManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<VolumeAttachment> index(@PathParam("tenantId") String tenantId, @PathParam("serverId") String serverId) throws Exception{
		return volumeAttachmentManager.getList(tenantId, serverId, false);
	}
	

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("{attachmentId}")
	public VolumeAttachment show(@PathParam("tenantId") String tenantId, @PathParam("serverId") String serverId, @PathParam("attachmentId") String attachment) throws Exception{
		return volumeAttachmentManager.get(tenantId, serverId, attachment);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public VolumeAttachment create(@PathParam("tenantId") String tenantId, @PathParam("serverId") String serverId, VolumeAttachment cReq) throws Exception{
		return volumeAttachmentManager.attach(tenantId, serverId, cReq);
	}
	
	@DELETE
	@Path("{attachmentId}")
	public void delete(@PathParam("tenantId") String tenantId, @PathParam("serverId") String serverId, @PathParam("attachmentId") String attachmentId) throws Exception{
		volumeAttachmentManager.detach(tenantId, serverId, attachmentId);
	}
}
