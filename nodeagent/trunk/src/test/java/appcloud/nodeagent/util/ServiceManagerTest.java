package appcloud.nodeagent.util;

import static org.junit.Assert.*;

import org.junit.Test;

import appcloud.rpc.tools.RpcException;

public class ServiceManagerTest {

	@Test
	public void test() {
		ServiceManager sm = ServiceManager.getInstance();
		String servicename = "hello";
		assertEquals(false, sm.startService(servicename));
		assertEquals(false, sm.isRunning(servicename));
		sm.stopService(servicename);
		assertEquals(false, sm.isRunning(servicename));
		
		servicename = "test";
		assertEquals(true, sm.startService(servicename));
		
//		servicename = "dbproxy";
//		assertEquals(true, sm.startService(servicename));
//		assertEquals(true, sm.isRunning(servicename));
//		sm.stopService(servicename);
//		assertEquals(false, sm.isRunning(servicename));
		
	}
	
	public static void main(String[] args) throws RpcException {
		ServiceManagerTest test = new ServiceManagerTest();
		test.test();
	}

}
