package appcloud.dbproxy.mysql.junit;

import static appcloud.dbproxy.mysql.junit.MySQLHostLoadProxyTest.ONE_DAY_MINISECONDS;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.HostLoad;
import appcloud.dbproxy.mysql.MySQLHostLoadProxy;
import appcloud.dbproxy.mysql.dao.CommonLoadDAO;
import appcloud.dbproxy.mysql.dao.VmLoadDao;


public class MySQLHostLoadProxyTest {
	
	private static final String HOST_UUID = "host-load";
	public static final long ONE_DAY_MINISECONDS = 3600*24*1000;
	
	static MySQLHostLoadProxy proxy = null;
	static String testName = "testHostLoad";
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		proxy = new MySQLHostLoadProxy();
		proxy.deleteAllBefore(new Timestamp(System.currentTimeMillis()));
	}
	
	@Before
	public void testSave() {
		HostLoad load1 = new HostLoad();
		load1.setUuid(HOST_UUID);
		load1.setCpuPercent((float) 99.9);
		load1.setDiskPercent((float) 99.8);
		load1.setDiskReadRate((float) 99.7);
		load1.setDiskWriteRate((float) 99.6);
		load1.setAvgLoad((float) 0.1);
		load1.setMemPercent((float) 99.4);
		load1.setNetInPercent((float) 99.3);
		load1.setNetOutPercent((float) 99.2);
		load1.setTime(new Timestamp(System.currentTimeMillis() - 5*ONE_DAY_MINISECONDS));
		proxy.save(load1);
		
		HostLoad load2 = new HostLoad();
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
		assertEquals(0.5, load.getAvgLoad(), 0.001);
	}

	@Test
	public void testGetLatestLoadStringInt() {
		List<HostLoad> list = (List<HostLoad>) proxy.getLatestLoad(HOST_UUID, 10);
		assertEquals(2, list.size());
	}
	
	@Test
	public void testGetLoads() {
		
		Date date = new Date();
		CommonLoadDAO.clearTime(date);
		
		Date startTime = new Date(date.getTime() - 5*ONE_DAY_MINISECONDS);
		
		Date endTime = new Date(date.getTime() - 4*ONE_DAY_MINISECONDS);
		List<HostLoad> list = (List<HostLoad>) proxy.getLoads(HOST_UUID, startTime, endTime);
		assertEquals(1, list.size());
		
		endTime = new Date(date.getTime() - 3*ONE_DAY_MINISECONDS);
		list = (List<HostLoad>) proxy.getLoads(HOST_UUID, startTime, endTime);
		assertEquals(2, list.size());
	}
	
	@After
	public void deleteAll() {
		assertEquals(2, proxy.deleteBefore(HOST_UUID, new Timestamp(System.currentTimeMillis() + 30000)));
	}

}
