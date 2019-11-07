package appcloud.nodeagent.bootstrap;

import org.junit.Assert;
import org.junit.Test;

import appcloud.common.model.Host;
import appcloud.nodeagent.util.TestUtils;

public class HostTest {

	@Test
	public void testHost() {
		Host host = (Host) TestUtils.getApplicationContext().getBean("host");
		System.out.println(host);
		
		Assert.assertEquals(host.getIp(), "192.168.1.19");
		Assert.assertEquals(host.getNetworkMb().intValue(), 100);
	}
}
