package appcloud.api.client;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import appcloud.api.beans.Host;
import appcloud.api.beans.Resource;

import com.sun.jersey.test.framework.JerseyTest;

public class TestHostClient  extends JerseySpringTest {
	public TestHostClient() {
		super("appcloud.api.resources");
	}
	
	private HostClient client = new HostClient("http://127.0.0.1:9998/");
	
	
	@Test
	public void testGetList() {
		List<Host> hosts = client.getHosts("xuan");
		assertEquals(2, hosts.size());
		for(Host host: hosts){
			assertEquals("zone1", host.zone);
		}
	}
	
	@Test
	public void testGet() {
		Host host = client.get("xuan", "hhhhost");
		assertEquals(3, host.resource.size());
		for(Resource r : host.resource) {
			System.out.println(r.project);
		}
	}

}
