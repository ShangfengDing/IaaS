package appcloud.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import appcloud.api.beans.Hypervisor;

import com.sun.jersey.test.framework.JerseyTest;

public class TestHypervisorClient  extends JerseySpringTest {
	public TestHypervisorClient() {
		super("appcloud.api.resources");
	}
	
	private HypervisorClient client = new HypervisorClient("http://127.0.0.1:9998/");
	
	
	@Test
	public void testGetList() {
		List <Hypervisor> hypervisors = client.getHypervisors("xuan");
		
		assertTrue(hypervisors.size() != 0);
		for(Hypervisor hypervisor : hypervisors) {
			assertTrue(hypervisor.id == 1 || hypervisor.id == 2);
		}
	}
	
	@Test
	public void testGet() {
		Hypervisor hypervisor = client.get("xuan", 1);
		assertTrue(hypervisor != null);
		assertEquals(2, hypervisor.servers.size());
	}

}
