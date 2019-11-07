package appcloud.api.client;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.sun.jersey.test.framework.JerseyTest;

import appcloud.api.beans.AcHost;
import appcloud.api.enums.AcHostStatusEnum;

public class TestAcHostClient extends JerseyTest {
	public TestAcHostClient() {
		super("appcloud.api.resources");
	}
	
	private AcHostClient client = new AcHostClient("http://127.0.0.1:9998/");
	
	
	@Test
	public void testGetList() {
		List<AcHost> achosts = client.getAcHosts();
		assertEquals(2, achosts.size());
		for(AcHost acHost: achosts){
			assertEquals(AcHostStatusEnum.NORMAL_LOAD, acHost.hostStatus);
		}
	}
	
	@Test
	public void testGet() {
		AcHost acHost = client.get("hhhhost");
		assertEquals("host_get", acHost.id);
		assertEquals("192.168.1.1", acHost.ip);
	}

}
