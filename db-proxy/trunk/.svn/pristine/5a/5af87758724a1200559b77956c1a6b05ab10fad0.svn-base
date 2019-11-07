package appcloud.dbproxy.mysql.junit;

import static appcloud.dbproxy.mysql.junit.MySQLHostLoadProxyTest.ONE_DAY_MINISECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.HostLoad;
import appcloud.common.model.VmLoad;
import appcloud.dbproxy.mysql.MySQLMonthLoadProxy;
import appcloud.dbproxy.mysql.dao.CommonLoadDAO;
import appcloud.dbproxy.mysql.dao.VmLoadDao;

public class MySQLVmMonthLoadProxyTest {

	private static final String HOST_UUID = "host-load";
	private static MySQLMonthLoadProxy proxy;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		proxy = new MySQLMonthLoadProxy();
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
		load1.setAvgLoad(0.1f);
		load1.setMemPercent((float) 99.4);
		load1.setNetInPercent((float) 99.3);
		load1.setNetOutPercent((float) 99.2);

		Date date = new Date();
		VmLoadDao.clearTime(date);
		
		load1.setTime(new Timestamp(date.getTime() - 5*ONE_DAY_MINISECONDS));
		proxy.save(load1);
		
		
		VmLoad load2 = new VmLoad();
		load2.setUuid(HOST_UUID);
		load2.setCpuPercent((float) 88.9);
		load2.setDiskPercent((float) 88.8);
		load2.setDiskReadRate((float) 88.7);
		load2.setDiskWriteRate((float) 88.6);
		load2.setAvgLoad((float) 0.5f);
		load2.setMemPercent((float) 88.4);
		load2.setNetInPercent((float) 88.3);
		load2.setNetOutPercent((float) 88.2);
		load2.setTime(new Timestamp(date.getTime() - 4*ONE_DAY_MINISECONDS));
		proxy.save(load2);

		assertNotNull(load1.getId());
		assertNotNull(load2.getId());
	}
	
	@Test
	public void testGetLoads() {
		Date date = new Date();

		Date startTime = new Date(date.getTime() - 80*ONE_DAY_MINISECONDS);
		Date endTime = new Date(date.getTime() + 50000);
		
		List<? extends HostLoad> list = proxy.getLoads(HOST_UUID, startTime, endTime);
		assertEquals(2, list.size());
		
		endTime = new Date(date.getTime() - 5*ONE_DAY_MINISECONDS + 50000);
		
		list = proxy.getLoads(HOST_UUID, startTime, endTime);
		assertEquals(1, list.size());
		assertEquals(0.1, list.get(0).getAvgLoad(), 0.001f);
	}
	
	@Test
	public void testGetAvgLoad() {
		
		Date startTime = new Date();
		
		CommonLoadDAO.clearTime(startTime);
		startTime.setTime(startTime.getTime() - 5*ONE_DAY_MINISECONDS);
		Date endTime = new Date(startTime.getTime() + 5*ONE_DAY_MINISECONDS);
		List<? extends HostLoad> list = proxy.getAvgLoads(startTime, endTime);
		assertEquals(1, list.size());
		assertEquals(HOST_UUID, list.get(0).getUuid());
		assertEquals(0.3, list.get(0).getAvgLoad(), 0.001);
	}
	
	@After
	public void testDelete() {
		assertEquals(2, proxy.deleteBefore(HOST_UUID, new Timestamp(System.currentTimeMillis())));
	}
}
