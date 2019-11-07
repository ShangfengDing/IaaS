package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import appcloud.common.model.HostLoad;
import appcloud.common.model.Load;
import appcloud.common.model.VmLoad;
import appcloud.common.proxy.VmLoadProxy;
import appcloud.dbproxy.mysql.MySQLVmLoadProxy;
import appcloud.dbproxy.mysql.dao.CommonLoadDAO;
import static appcloud.dbproxy.mysql.junit.MySQLHostLoadProxyTest.ONE_DAY_MINISECONDS;


public class MySQLVmLoadProxyTest {

	private static final String HOST_UUID = "host-load";
	private static MySQLVmLoadProxy proxy;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		proxy = new MySQLVmLoadProxy();
		proxy.deleteAllBefore(new Timestamp(System.currentTimeMillis()));
	}
	
	@Before
	public void testSave() {

		VmLoad load1 = new VmLoad();
		load1.setUuid(HOST_UUID);
		load1.setCpuPercent((float) 10.9);
		load1.setDiskPercent((float) 99.8);
		load1.setDiskReadRate((float) 99.7);
		load1.setDiskWriteRate((float) 99.6);
		load1.setAvgLoad((float) 0.1);
		load1.setMemPercent((float) 99.4);
		load1.setNetInPercent((float) 99.3);
		load1.setNetOutPercent((float) 99.2);
		load1.setTime(new Timestamp(System.currentTimeMillis() - 5*ONE_DAY_MINISECONDS));
		proxy.save(load1);
		
		
		VmLoad load2 = new VmLoad();
		load2.setUuid(HOST_UUID);
		load2.setCpuPercent((float) 88.9);
		load2.setDiskPercent((float) 88.8);
		load2.setDiskReadRate((float) 88.7);
		load2.setDiskWriteRate((float) 88.6);
		load2.setAvgLoad((float) 0.5);
		load2.setMemPercent((float) 88.4);
		load2.setNetInPercent((float) 88.3);
		load2.setNetOutPercent((float) 88.2);
		load2.setTime(new Timestamp(System.currentTimeMillis() - 4*ONE_DAY_MINISECONDS));
		proxy.save(load2);

		assertNotNull(load1.getId());
		assertNotNull(load2.getId());
	}
	
	@Test
	public void testGetLatestLoadString() {
		HostLoad load = proxy.getLatestLoad(HOST_UUID);
		assertNotNull(load.getId());
		assertEquals(88.9, load.getCpuPercent(), 0.0001f);
	}
	
	@Test
	public void testGetLatestLoadStringInt() {
		List<VmLoad> list = (List<VmLoad>) proxy.getLatestLoad(HOST_UUID, 10);
		assertEquals(2, list.size());

	}
	
	@Test
	public void testGetAvgLoad() {
		
		Date startTime = new Date();
		
		HostLoad load = proxy.getOneDayAvgLoad(HOST_UUID, startTime);
		assertNull(load.getId());
		
		CommonLoadDAO.clearTime(startTime);
		startTime.setTime(startTime.getTime() - 5*ONE_DAY_MINISECONDS);
		Date endTime = new Date(startTime.getTime() + 5*ONE_DAY_MINISECONDS);
		List<? extends HostLoad> list = proxy.getAvgLoads(startTime, endTime);
		assertEquals(1, list.size());
		assertEquals(HOST_UUID, list.get(0).getUuid());
		assertEquals(0.3, list.get(0).getAvgLoad(), 0.001);
	}
	
	@Test
	public void testGetLoads() {
		List<VmLoad> list = (List<VmLoad>) proxy.getLoads(HOST_UUID, new Date(System.currentTimeMillis() - 6*ONE_DAY_MINISECONDS +30000), new Date(System.currentTimeMillis()));
		assertEquals(2, list.size());
		
		list = (List<VmLoad>) proxy.getLoads(HOST_UUID, new Date(System.currentTimeMillis() - 5*ONE_DAY_MINISECONDS +30000), new Date(System.currentTimeMillis()));
		assertEquals(1, list.size());
	}
	
	@Test
	public void testDeleteOne() {
		VmLoad load1 = new VmLoad();
		load1.setUuid(HOST_UUID);
		load1.setCpuPercent((float) 10.9);
		load1.setDiskPercent((float) 99.8);
		load1.setDiskReadRate((float) 99.7);
		load1.setDiskWriteRate((float) 99.6);
		load1.setAvgLoad((float) 99.5);
		load1.setMemPercent((float) 99.4);
		load1.setNetInPercent((float) 99.3);
		load1.setNetOutPercent((float) 99.2);
		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		load1.setTime(time);
		proxy.save(load1);
		assertEquals(1, proxy.deleteByDate(time));
		assertEquals(0, proxy.deleteByDate(time));
	}
	
	@After
	public void testDelete() {
		assertEquals(2, proxy.deleteBefore(HOST_UUID, new Timestamp(System.currentTimeMillis())));
	}
	
	
}
