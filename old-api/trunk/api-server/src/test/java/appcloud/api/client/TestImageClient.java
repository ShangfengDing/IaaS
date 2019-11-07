package appcloud.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import appcloud.api.beans.Image;

import com.sun.jersey.test.framework.JerseyTest;

public class TestImageClient  extends JerseyTest {
	public TestImageClient() {
		super("appcloud.api.resources");
	}
	
	private ImageClient client = new ImageClient("http://127.0.0.1:9998/");
	
	
	@Test
	public void testGetList() {
		List<Image> images = client.getImages("xuan", false);
		assertEquals(2, images.size());
		
		for (Image image : images) {
			assertEquals("xuan", image.tenantId);
		}
	}

	
	@Test
	public void testGetListDetail() {
		List<Image> images = client.getImages("xuan", true);
		assertEquals(3, images.size());
		
		for (Image image : images) {
			assertEquals("xuan", image.tenantId);
			assertTrue(image.metadata != null);
		}
	}
	@Test
	public void testGet() {
		Image image = client.get("xuan", "dfaf");
		assertEquals("dfaf", image.id);
		assertTrue(image.id != null);
		assertTrue(image.metadata != null);
	}
	
	@Test
	public void testCreate(){
	}

	/*@Test
	public void testDelete() {
		client.deleteImage("xuan", "image image image");
	}*/

	@Test 
	public void testUpdate() {
	}
}
