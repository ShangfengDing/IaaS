package appcloud.nodeagent;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.Service;
import appcloud.common.service.NodeMonitorService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.nodeagent.util.AppUtils;
import appcloud.rpc.tools.RpcException;

public class NodeMonitorServerTest {

	@Test
	public void test() throws RpcException {
		ApplicationContext ctx = AppUtils.getApplicationContext();
		String uuid = (String) ctx.getBean("hostUuid");
		String routingkey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.NODE_MONITOR_PRE, uuid);
		NodeMonitorService nms = (NodeMonitorService) ConnectionFactory.getAMQPService(NodeMonitorService.class, routingkey);
		String service = Service.ServiceTypeEnum.VOLUME_SCHEDULER.toString();
		nms.startService(routingkey, service);
		nms.stopService(routingkey, service);
	}

}
