package appcloud.nodeagent.bootstrap;

import org.junit.Test;

import appcloud.common.service.monitor.BTYunhaiService;

public class DemoService extends BTYunhaiService {

	public DemoService(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
		System.out.println("hostUuid=" + getHostUuid(false));
		System.out.println("host=" + getHost());
	}

	public void testUuid() {
		System.out.println("hostUuid=" + getHostUuid(false));
	}
	
	public void testHost() {
		System.out.println("host=" + getHost());
	}
	
	public static void main(String [] args) {
		DemoService demo = new DemoService(null, null, null);
		demo.testHost();
		demo.testUuid();
	}
}
