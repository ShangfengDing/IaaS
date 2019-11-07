package appcloud.approuter.amqp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.model.RoutingEntry;
import appcloud.common.service.AppRouterService;
import appcloud.rpc.ampq.BasicRPCClient;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AppRouterServiceImplTest {
	private BasicRPCClient __rpcClient = null;
	private AppRouterService __service= null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
//		__rpcClient = new BasicRPCClient(ConnectionConfigs.AMPQ_SERVER_ADDRESS, "jlmsg", "server-1",
//				10000); // 1
//		
//		__service = (AppRouterService) __rpcClient.createProxy(AppRouterService.class);
	}


//	@Test
//	public void testAddRoutingEntrys() {
//		List<RoutingEntry> routingEntries = new ArrayList<RoutingEntry>();
//		RoutingEntry rc= new RoutingEntry(null, "j2ee", "approutertest", "free4lab.com", "192.168.255.254", "65535", 0, 1);
//		routingEntries.add(rc);
//		
//		__service.addRoutingEntrys(routingEntries);
//	}

//	@Test
//	public void testUpdateRoutingEntrys() {
//		String srcPrefix = "approutertest";
//		String srcSuffix = "free4lab.com";
//		
//		List<RoutingEntry> routingConfs = new ArrayList<RoutingEntry>();
//		RoutingEntry rc= new RoutingEntry("j2ee", "approutertest", "free4lab.com", "192.168.255.254", "65535", 0);
//		routingConfs.add(rc);
//		
//		__service.updateRoutingEntrys(srcPrefix, srcSuffix, routingConfs);
//	}
//
//	@Test
//	public void testDeleteRoutingEntrys() {
//		List<RoutingEntry> routingConfs = new ArrayList<RoutingEntry>();
//		RoutingEntry rc= new RoutingEntry("j2ee", "approutertest", "free4lab.com", "192.168.255.254", "65535", 0);
//		routingConfs.add(rc);
//		
//		__service.deleteRoutingEntrys(routingConfs);
//	}
//
	@Test
	public void testDeleteAppRoutingEntrys() {
//		String srcPrefix = "approutertest";
//		String srcSuffix = "free4lab.com";
//		
//		__service.deleteAppRoutingEntrys(srcPrefix, srcSuffix);
	}
//
//	@Test
//	public void testDisableAppRoutingEntrys() {
//		String srcPrefix = "approutertest";
//		String srcSuffix = "free4lab.com";
//
//		__service.disableAppRoutingEntrys(srcPrefix, srcSuffix);
//	}
//
//	@Test
//	public void testApplyRoutingEntry() {
//		Integer userId = new Integer(125);
//		String srcPrefix = "approutertest";
//		String srcSuffix = "free4lab.com";
//		
//		__service.applyRoutingEntry(userId, srcPrefix, srcSuffix);
//	}

}
