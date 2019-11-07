/**
 * File: MySQLVmVirtualInterfaceProxyTest.java
 * Author: weed
 * Create Time: 2013-4-17
 */
package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.Cluster;
import appcloud.common.model.VmVirtualInterface;
import appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy;

/**
 * @author weed
 *
 */
public class MySQLVmVirtualInterfaceProxyTest {

	MySQLVmVirtualInterfaceProxy proxy = new MySQLVmVirtualInterfaceProxy();
	Integer reservedId = 2;
//	Integer testId = 20;
	String reservedInstanceUuid = "ReservedVmTest1";
	Integer testInstanceId = 4;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy#findAll()}.
	 */
	@Test
	public void testFindAll() {
		try {
			List<? extends VmVirtualInterface> vmVirtualInterfaces = proxy.findAll();
			assertTrue("findAll", vmVirtualInterfaces.size() != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy#findAll(java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testFindAllIntegerInteger() {
		try {
			List<? extends VmVirtualInterface> vmVirtualInterfaces = proxy.findAll(0, 10);
			assertTrue("findAll", vmVirtualInterfaces.size() != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy#getById(java.lang.Integer)}.
	 */
	@Test
	public void testGetById() {
		try {
			VmVirtualInterface vmVirtualInterface = proxy.getById(reservedId);
			assertNotNull(vmVirtualInterface);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy#getByInstanceUuid(java.lang.String)}.
	 */
	@Test
	public void testGetByInstanceUuid() {
		try {
			List<? extends VmVirtualInterface> vmVirtualInterface = proxy.getByInstanceUuid(reservedInstanceUuid);
			assertNotNull(vmVirtualInterface);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy#save(appcloud.common.model.VmVirtualInterface)}.
	 */
	@Test
	public void testSave() {
		VmVirtualInterface vmVirtualInterface = new VmVirtualInterface(null, "testUuid", "127.0.0.1", "test_mac_address", "dhcp", 1);
		try {
			proxy.save(vmVirtualInterface);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy#deleteById(java.lang.Integer)}.
	 */
	@Test
	public void testDeleteById() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmVirtualInterfaceProxy#deleteByInstanceUuid(java.lang.String)}.
	 */
	@Test
	public void testDeleteByInstanceUuid() {
//		fail("Not yet implemented");
	}

}
