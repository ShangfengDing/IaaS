/**
 * File: MySQLVmInstanceMetadataProxyTest.java
 * Author: weed
 * Create Time: 2013-4-17
 */
package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.VmInstanceMetadata;
import appcloud.dbproxy.mysql.MySQLVmInstanceMetadataProxy;

/**
 * @author weed
 *
 */
public class MySQLVmInstanceMetadataProxyTest {

	MySQLVmInstanceMetadataProxy proxy = new MySQLVmInstanceMetadataProxy();
	Integer reservedId = 20;
//	Integer testId = 20;
	Integer reservedInstanceId = 5;
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
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceMetadataProxy#getByVmInstanceMetadataId(java.lang.Integer, boolean)}.
	 */
	@Test
	public void testGetByVmInstanceMetadataId() {
		try {
			VmInstanceMetadata  vmInstanceMetadata = proxy.getByVmInstanceMetadataId(reservedId, true);
			assertNotNull(reservedId.toString(), vmInstanceMetadata);
			assertNotNull(vmInstanceMetadata.toString(), vmInstanceMetadata.getVmInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceMetadataProxy#getByVmInstanceId(java.lang.Integer, boolean)}.
	 */
	@Test
	public void testGetByVmInstanceId() {
		try {
			List<? extends VmInstanceMetadata>  vmInstanceMetadatas = proxy.getByVmInstanceId(reservedInstanceId, true);
			assertTrue(reservedInstanceId.toString(), vmInstanceMetadatas.size() > 0);
			for (VmInstanceMetadata vmInstanceMetadata : vmInstanceMetadatas){
				assertNotNull(vmInstanceMetadata.toString(), vmInstanceMetadata.getVmInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceMetadataProxy#save(appcloud.common.model.VmInstanceMetadata)}.
	 */
	@Test
	public void testSave() {
		VmInstanceMetadata vmInstanceMetadata = new VmInstanceMetadata(null, testInstanceId, "testkey", "testvalue");
		try {
			proxy.save(vmInstanceMetadata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceMetadataProxy#update(appcloud.common.model.VmInstanceMetadata)}.
	 */
	@Test
	public void testUpdate() {
		try {
			List<? extends VmInstanceMetadata> vmInstanceMetadatas = proxy.getByVmInstanceId(testInstanceId, false);
			for (VmInstanceMetadata vmInstanceMetadata : vmInstanceMetadatas){
				vmInstanceMetadata.setKey("testkey2");
				vmInstanceMetadata.setValue("testvalue2");
				proxy.update(vmInstanceMetadata);
				
				VmInstanceMetadata newVmInstanceMetadata = proxy.getByVmInstanceMetadataId(vmInstanceMetadata.getId(), false);
				assertTrue(newVmInstanceMetadata.getKey().equals("testkey2"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceMetadataProxy#deleteById(java.lang.Integer)}.
	 */
	@Test
	public void testDeleteById() {
//		fail("Not yet implemented");
		try {
			List<? extends VmInstanceMetadata> vmInstanceMetadatas = proxy.getByVmInstanceId(testInstanceId, false);
			proxy.deleteById(vmInstanceMetadatas.get(0).getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link appcloud.dbproxy.mysql.MySQLVmInstanceMetadataProxy#deleteByVmInstanceId(java.lang.Integer)}.
	 */
	@Test
	public void testDeleteByVmInstanceId() {
		try {
			proxy.deleteByVmInstanceId(testInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
