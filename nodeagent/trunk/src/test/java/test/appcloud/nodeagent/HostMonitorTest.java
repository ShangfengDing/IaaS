package test.appcloud.nodeagent;

import java.io.File;

import org.springframework.context.ApplicationContext;

import appcloud.nodeagent.monitor.HostMonitor;
import appcloud.nodeagent.util.TestUtils;
import junit.framework.TestCase;

public class HostMonitorTest extends TestCase {
	
	public void testadd() throws InterruptedException {
		ApplicationContext ctx = TestUtils.getApplicationContext();
		HostMonitor hostMonitor = (HostMonitor) ctx.getBean("hostMonitor");
		Thread.sleep(1000);
		hostMonitor.run();

		
	//	assertEquals(10, hostMonitor.getLoadSummary().getCount());
		System.out.println("host load = " + hostMonitor.getLoadSummary().asHostLoad());
	}
	
}
