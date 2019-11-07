package appcloud.api.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import appcloud.api.beans.Volume;

public class TestVolumeClient extends JerseySpringTest{
	
	public TestVolumeClient(){
		super("appcloud.api.resources");
	}
	
	private VolumeClient client = new VolumeClient("http://127.0.0.1:9998/");

	
	@Test
	public void testGet() {
		Volume volume = client.get("xuan", "fenix");
		assertEquals("fenix", volume.id);
	}
	
	@Test
	public void testGetList() {
		List<Volume> volumes = client.getVolumes("xuan", false);
		assertTrue(volumes.size() != 0);
		for(Volume volume : volumes){
			assertTrue(volume.metadata != null);
		}
	}
	
	@Test
	public void testGetListDetail() {
		List<Volume> volumes = client.getVolumes("xuan", true);
		assertTrue(volumes.size() != 0);
		for(Volume volume : volumes){
			assertTrue(volume.metadata != null);
		}
	}
	
	@Test
	public void testCreate() {
		Volume volume1 = client.get("xuan", "fenix");
		Volume volume = client.createVolume("xuan", volume1);
		assertEquals("ID5", volume.id);
		assertEquals(volume1.displayName, volume.displayName);
	}
	
	@Test
	public void testDelete() {
		client.deleteVolume("xuan", "test");
	}
	
	@Test
	public void testGetBackupList() {		
		Assert.assertTrue(client.getBackupList("123").size() > 0);
	}
	
	@Test
	public void testGetBackup() {
		Assert.assertTrue(client.getBackup("123", "abc").displayName != null);
	}
	
	@Test
	public void testUpdate() {
		Volume volume = client.update("110", "7890", "UPDATEname", "UP",null);
		assertEquals("7890", volume.id);
		assertEquals("UPUPUPUPUPUP", volume.displayDescription);
		assertEquals("updated volume type", volume.volumeType);
	}

}
