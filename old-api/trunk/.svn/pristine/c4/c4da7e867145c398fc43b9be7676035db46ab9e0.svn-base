package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.List;

import appcloud.api.beans.VolumeAttachment;
import appcloud.api.manager.VolumeAttachmentManager;

public class FakeVolumeAttachmentManager implements VolumeAttachmentManager {

	@Override
	public List<VolumeAttachment> getList(String tenantId, String serverId, boolean detailed)
			throws Exception {
		List<VolumeAttachment> attachments = new ArrayList<VolumeAttachment>();
		attachments.add(new VolumeAttachment("/dev/vda", serverId,
				"5f800cf0-324f-4234-bc6b-e12d5816e962_id_got1", "5f800cf0-324f-4234-bc6b-e12d5816e962_volumeId1"));
		
		attachments.add(new VolumeAttachment("/dev/vda", serverId,
				"5f800cf0-324f-4234-bc6b-e12d5816e962_id_got2", "5f800cf0-324f-4234-bc6b-e12d5816e962_volumeId2"));
		return attachments;
	}

	@Override
	public VolumeAttachment get(String tenantId, String serverId, String attachmentId)
			throws Exception {
		return new VolumeAttachment("/dev/vdg", serverId,
				"id_got", "volumeid_got");
	}

	@Override
	public VolumeAttachment attach(String tenantId, String serverId, VolumeAttachment attachment)
			throws Exception {

		return new VolumeAttachment(attachment.device, attachment.serverId,
				attachment.id + "_id_create", attachment.volumeId);
	}

	@Override
	public void detach(String tenantId, String serverId, String attachmentId) throws Exception {
		System.out.println("deleting " + tenantId + "'s volumeAttachment:" + attachmentId);
	}

}
