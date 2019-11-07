package appcloud.api.client;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import appcloud.api.beans.AcMailConf;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

public class TestAcMessageLogClient  extends JerseySpringTest {
	public TestAcMessageLogClient() {
		super("appcloud.api.resources");
	}
	
	private AcMessageLogClient client = new AcMessageLogClient("http://127.0.0.1:9998/");
	
	@Test
	public void testAdd() {
		AcMessageLog log = client.addLog(new AcMessageLog(AcModuleEnum.DHCP_CONTROLLER,
				"tranctionId2", "userId2", "uuid", "operateDrpt2", 
				"logContent2", "sourceClass2", "ipp", AcLogLevelEnum.WARN, new Date()));
		assertEquals("ipp", log.ipAddress);
	}
	
	@Test 
	public void testSearch() {
		List <AcMessageLog> logs = client.searchLog(new AcMessageLog(), 
				new Date(), new Date(), 5);
		assertEquals(2, logs.size());
	}
	
	@Test 
	public void testGetMailConf() {
		AcMailConf acMailConf = client.getMailConf();
		if(acMailConf == null)
			System.out.println("null");
		else
			System.out.println(acMailConf.emailHost);
		assertEquals("daye@126.com", acMailConf.emailFrom);
	}
	
	@Test
	public void testSetMailConf () {
		AcMailConf acMailConf = new AcMailConf("true", "smtp.free4lab.com", "daye@126.com",
				"dayenihao", "hello daye", null);
		assertEquals("smtp.free4lab.com", acMailConf.emailHost);
	}
}
