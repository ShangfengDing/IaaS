package appcloud.dbproxy.mysql.junit;

import java.sql.Timestamp;

import org.junit.Test;

import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.Host.HostTypeEnum;
import appcloud.common.model.Service.ServiceStatus;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.dbproxy.mysql.MySQLHostProxy;
import junit.framework.TestCase;

/**
 * 
 * @author wangchao
 *
 */
public class MySQLHostProxyTest extends TestCase {

	private MySQLHostProxy proxy = new MySQLHostProxy();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	String uuid = "hostxxxxyyyy";
	Host newHost(){
		Host host = new Host();
		host.setIp("192.168.0.1");
		host.setCpuCore(4);
		host.setCpuMhz(1110);
		host.setAvailabilityZoneId(10);
		host.setUuid(uuid);
		
		host.setMemoryMb(512);
		host.setDiskMb(500);
		host.setNetworkMb(1000);
		host.setOs("Linux");
		host.setType(HostTypeEnum.COMPUTE_NODE);
		host.setLocation("物理机");
		host.setPrivateVLAN(1);
		host.setPublicVLAN(1);

		return host;
	}
	
	@Test
	public void testSave() throws Exception {///*
		
		Host host = newHost();
		proxy.save(host);
		
		host = proxy.getByUuid(uuid, false, false, false);
		assertNotNull(host.getId());
		assertTrue(host.getId() > 0);
		
		host.setIp("192.168.0.8");
		Host h = proxy.update(host);
		assertEquals("192.168.0.8", h.getIp());
		
		proxy.deleteByUuid(uuid);//*/
	}
}
