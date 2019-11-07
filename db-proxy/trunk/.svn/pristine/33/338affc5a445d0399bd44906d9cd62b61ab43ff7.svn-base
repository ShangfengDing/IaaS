/**
 * File: TestMySQLVmVirtualInterfaceProxy.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmVirtualInterface;

/**
 * @author weed
 *
 */
public class TestMySQLVmVirtualInterfaceProxy {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		MySQLVmVirtualInterfaceProxy proxy = new MySQLVmVirtualInterfaceProxy();
		
		VmVirtualInterface vmVirtualInterface = new VmVirtualInterface();
		vmVirtualInterface.setInstanceUuid("InstanceUuid");
		vmVirtualInterface.setAddress("127.0.0.1");
		
		proxy.save(vmVirtualInterface);
		
		List<? extends VmVirtualInterface> vmVirtualInterfaces = proxy.findAll();
		System.out.println("proxy.findAll: " + vmVirtualInterfaces);
		
		VmVirtualInterface vmVirtualInterface0 = proxy.getById(vmVirtualInterfaces.get(0).getId());
		System.out.println("proxy.getById(0): " + vmVirtualInterface0);
		
		List<? extends VmVirtualInterface> vmVirtualInterface1 = proxy.getByInstanceUuid(vmVirtualInterface.getInstanceUuid());
		System.out.println("proxy.getByInstanceUuid: " + vmVirtualInterface1);
		
		proxy.deleteById(vmVirtualInterfaces.get(0).getId());
		
		proxy.deleteByInstanceUuid(vmVirtualInterface.getInstanceUuid());
	}

}
