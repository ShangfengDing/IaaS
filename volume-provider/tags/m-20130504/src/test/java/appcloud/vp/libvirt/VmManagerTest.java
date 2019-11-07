package appcloud.vp.libvirt;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.libvirt.LibvirtException;

public class VmManagerTest {
	static VmManager m =null;
	@BeforeClass 
	static public void initialize() throws LibvirtException {
		m = VmManager.getInstance();
	}
	@Test
	public void testGetDomainUuidList() {
		m.getDomainUuidList();
	}
	
	
	@Test
	public void test() {
		m.getStoragePoolsList();
		
	}
	
}
