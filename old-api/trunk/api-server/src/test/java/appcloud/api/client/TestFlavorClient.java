package appcloud.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import appcloud.api.beans.Flavor;

import com.sun.jersey.test.framework.JerseyTest;

public class TestFlavorClient  extends JerseySpringTest {
	public TestFlavorClient() {
		super("appcloud.api.resources");
	}
	
	private FlavorClient client = new FlavorClient("http://127.0.0.1:9998/");
	
	
	@Test
	public void testGetList() {
		List<Flavor> flavors = client.getFlavors("xuan", false);
		assertEquals(4, flavors.size());
	}
	
	@Test
	public void testGetListDetailed() {
		List<Flavor> flavors = client.getFlavors("xuan", true);
		assertEquals(4, flavors.size());
	}
	
	@Test
	public void testGet() {
		Flavor flavor = client.get("xuan", 100);
		assertTrue(100 == flavor.id);
		assertEquals("xuan", flavor.tenantId);
	}
	
	@Test
	public void testCreate() {
		Flavor f = new Flavor();
		Flavor flavor = client .createFlavor("xuan", f);
		assertTrue(123 == flavor.id);
		assertEquals("xuan", flavor.tenantId);
	}

}
