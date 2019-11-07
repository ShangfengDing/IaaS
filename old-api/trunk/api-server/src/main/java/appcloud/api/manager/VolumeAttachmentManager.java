package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.VolumeAttachment;

public interface VolumeAttachmentManager {

	public List<VolumeAttachment> getList(String tenantId, String serverId, boolean detailed) throws Exception;
	
	public VolumeAttachment get(String tenantId, String serverId, String attachmentId) throws Exception;
	
	public VolumeAttachment attach(String tenantId, String serverId, VolumeAttachment attachment) throws Exception;
	
	public void detach(String tenantId, String serverId,String attachmentId) throws Exception;
}
