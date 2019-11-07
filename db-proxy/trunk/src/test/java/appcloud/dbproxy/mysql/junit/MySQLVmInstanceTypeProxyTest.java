/**
 * File: MySQLVmInstanceTypeProxyTest.java
 * Author: weed
 * Create Time: 2013-4-17
 */
package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.VmInstanceType;
import appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy;

/**
 * @author weed
 *
 */
public class MySQLVmInstanceTypeProxyTest {

	MySQLVmInstanceTypeProxy proxy = new MySQLVmInstanceTypeProxy();
	
	Integer reservedId = 1;
	String reservedFlavorUuid = "TestReservedFlavorUuid1";
	String reservedName = "name1";
	
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
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#findAll()}.
	 */
	@Test
	public void testFindAll() {
		try {
			List<? extends VmInstanceType> vmInstanceTypes = proxy.findAll();
			assertTrue("findAll", vmInstanceTypes.size() != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#findAll(java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testFindAllIntegerInteger() {
		try {
			List<? extends VmInstanceType> vmInstanceTypes = proxy.findAll(0, 10);
			assertTrue("findAll", vmInstanceTypes.size() != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#countAll()}.
	 */
	@Test
	public void testCountAll() {
		try {
			proxy.countAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#searchAll(appcloud.common.util.query.QueryObject)}.
	 */
	@Test
	public void testSearchAllQueryObjectOfVmInstanceType() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#searchAll(appcloud.common.util.query.QueryObject, java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testSearchAllQueryObjectOfVmInstanceTypeIntegerInteger() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#countSearch(appcloud.common.util.query.QueryObject)}.
	 */
	@Test
	public void testCountSearch() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#getByUuid(java.lang.String)}.
	 */
	@Test
	public void testGetByUuid() {
		try {
			VmInstanceType vmInstanceType = proxy.getByUuid(reservedFlavorUuid);
			assertNotNull(vmInstanceType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#getById(java.lang.Integer)}.
	 */
	@Test
	public void testGetById() {
		try {
			VmInstanceType vmInstanceType = proxy.getById(reservedId);
			assertNotNull(vmInstanceType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#getByName(java.lang.String)}.
	 */
	@Test
	public void testGetByName() {
		try {
			VmInstanceType vmInstanceType = proxy.getByName(reservedName);
			assertNotNull(vmInstanceType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#save(appcloud.common.model.VmInstanceType)}.
	 */
	@Test
	public void testSave() {
		VmInstanceType instanceType = new VmInstanceType(null, "testFlavorUuid", "testName", 1, 20480, 20);
		try {
			proxy.save(instanceType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#update(appcloud.common.model.VmInstanceType)}.
	 */
	@Test
	public void testUpdate() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#deleteById(java.lang.Integer)}.
	 */
	@Test
	public void testDeleteById() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceTypeProxy#deleteByUuid(java.lang.String)}.
	 */
	@Test
	public void testDeleteByUuid() {
//		fail("Not yet implemented");
	}

}
