package appcloud.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import appcloud.api.beans.VolumeAttachment;

import com.sun.jersey.test.framework.JerseyTest;

public class TestVolumeAttachmentClient extends JerseySpringTest{
	
	public TestVolumeAttachmentClient(){
		super("appcloud.api.resources");
	}
	
	private VolumeAttachmentClient client = new VolumeAttachmentClient("http://127.0.0.1:9998/");

	
	@Test
	public void testGet() {
		VolumeAttachment attachment = client.get("xuan", "fe", "fenix");
		assertEquals("/dev/vdg", attachment.device);
		assertEquals("fe", attachment.serverId);
	}
	
	@Test
	public void testGetList() {
		List<VolumeAttachment> attachments = client.getVolumeAttachments("xuan","serverXuan", false);
		assertTrue(attachments.size() != 0);
		for(VolumeAttachment attachment : attachments){
			assertEquals("/dev/vda", attachment.device);
		}
	}
	
	
	@Test
	public void testCreate() {
		VolumeAttachment attachment1 = client.get("xuan", "serverCreate", "fenix");
		VolumeAttachment attachment = client.attachVolumeAttachment("xuan", "serverCreate", attachment1);
		assertEquals(attachment1.device, attachment.device);
		assertEquals(attachment1.volumeId, attachment.volumeId);
	}
	
	@Test
	public void testDelete() {
		client.detachVolumeAttachment("xuan", "server999", "attachment8888");
	}
}
