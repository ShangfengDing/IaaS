package appcloud.vp;

import org.apache.log4j.Logger;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.VolumeProviderService;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.common.util.RoutingKeyGenerator;

public class VolumeProviderServer extends BTYunhaiService{
	public VolumeProviderServer(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}
	private static Logger logger = Logger.getLogger(VolumeProviderServer.class);
	public static void main(String[] args) throws InterruptedException {		
		VolumeProvider vp = new VolumeProvider();		
		String routingkey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.VOLUME_PROVIDER_PRE, VolumeProviderServer.getHostUuid(true));		
		logger.info("VolumeProviderServer starting, routingkey: " + routingkey );
		VolumeProviderServer vps = new VolumeProviderServer(routingkey,VolumeProviderService.class,vp);
		vps.run();
	}
}
