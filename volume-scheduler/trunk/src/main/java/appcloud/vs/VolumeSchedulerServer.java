package appcloud.vs;

import java.io.IOException;

import org.apache.log4j.Logger;

import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.service.monitor.AbstractYunhaiService;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.ampq.BasicRPCServer;
import appcloud.vs.impl.VolumeScheduler;

import com.rabbitmq.client.ConsumerCancelledException;

public class VolumeSchedulerServer extends BTYunhaiService{
	
	public VolumeSchedulerServer(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}

	public static void main(String args[]) {
		VolumeScheduler vs = new VolumeScheduler();
		VolumeSchedulerServer vss = new VolumeSchedulerServer(RoutingKeys.VOLUME_SCHEDULER,VolumeSchedulerService.class,vs);
		vss.run();
	}
}
