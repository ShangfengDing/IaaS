package appcloud.api.client;

import java.util.List;

import appcloud.api.beans.VolumeAttachment;

import com.sun.jersey.api.client.GenericType;

public class VolumeAttachmentClient extends AbstractClient<VolumeAttachment>{
	
	public VolumeAttachmentClient(){
		super();
	}
	
	public VolumeAttachmentClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return VolumeAttachment.class;
	}
	
	@Override
	protected GenericType<List<VolumeAttachment>> getGenericType() {		
		GenericType<List<VolumeAttachment>> type = new GenericType<List<VolumeAttachment>>() {};
		return type;
	}
	
	private String getPath() {
		return  "os-volume_attachments";
	}
	
	protected String buildPath(String tenantId, String serverId) {
		return String.format("%s/servers/%s/%s", tenantId, serverId, getPath());
	}
	
	protected String buildPathWithId(String tenantId, String serverId, String attachmentId) {
		return String.format("%s/%s", buildPath(tenantId, serverId), attachmentId);
	}

	public VolumeAttachment get(String tenantId, String serverId, String attachmentId) {
		return super.get(buildPathWithId(tenantId, serverId, attachmentId));
	}
	
	public List<VolumeAttachment> getVolumeAttachments(String tenantId, String serverId, boolean detailed) {
		return super.getList(buildPath(tenantId, serverId), null);
	}

	public VolumeAttachment attachVolumeAttachment(String tenantId, String serverId, VolumeAttachment attachment) {
		return super.postWithRet(buildPath(tenantId, serverId), attachment);
	}
	
	public boolean detachVolumeAttachment(String tenantId, String serverId, String attachmentId) {
		resource.path(buildPathWithId(tenantId, serverId, attachmentId)).delete();
		return super.delete(buildPathWithId(tenantId, serverId, attachmentId));
	}
}
