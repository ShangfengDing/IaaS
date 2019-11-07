package appcloud.api.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;

import appcloud.api.beans.AddressPool;

public class TestAddressPoolClient extends JerseySpringTest {
	public TestAddressPoolClient() {
		super("appcloud.api.resources");
	}
	
	private AddressPoolClient client = new AddressPoolClient("http://127.0.0.1:9998/");
	
	@Test
	public void testGet() {
		AddressPool pool = client.get("220", 250);
		assertEquals("192.168.1.22", pool.dns);
		assertEquals(250, pool.id.intValue());
		assertEquals(3, pool.ips.size());
	}
	
	@Test
	public void testGetList() {
		List<AddressPool> pools = client.getAddressPools("110");
		assertEquals(3, pools.size());
		assertEquals(1, pools.get(1).aggregateId.intValue());
	}
	
	@Test
	public void testCreate() {

		AddressPool pool0 = client.get("220", 250);
		AddressPool pool = client.createAddressPool("110", pool0);
		
		assertEquals(0, pool.ips.size());
		assertEquals( "255.255.255.0", pool.netmask);
	}
	
	@Test
	public void test() {
		System.out.println(client.buildPath("110"));
		System.out.println(client.buildPathWithId("110", 250));
	}
}
