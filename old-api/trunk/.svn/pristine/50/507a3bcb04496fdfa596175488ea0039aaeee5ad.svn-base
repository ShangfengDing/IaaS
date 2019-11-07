package appcloud.api.client;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import appcloud.api.beans.AcVlan;

public class TestAcVlanClient extends JerseySpringTest {
	public TestAcVlanClient() {
		super("appcloud.api.resources");
	}
	
	private AcVlanClient client = new AcVlanClient("http://127.0.0.1:9998/");
	
	@Test
	public void testGet() {
		AcVlan acVlan = client.get(5);
		assertEquals(new Integer(5), acVlan.id);
		assertEquals("vlan_got", acVlan.name);
	}
	
	@Test
	public void testGetList() {
		List<AcVlan> acVlans = client.getAcVlans();
		assertEquals(2, acVlans.size());
		for(AcVlan vlan : acVlans)
			assertEquals("it is got in list", vlan.description);
	}
}
