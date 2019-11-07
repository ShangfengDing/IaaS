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
		System.out.println("JLJLJL VolumeProviderServer starting, routingkey: " + routingkey);
		logger.info("VolumeProviderServer starting, routingkey: " + routingkey );
		VolumeProviderServer vps = new VolumeProviderServer(routingkey,VolumeProviderService.class,vp);
		vps.run();
		
//		VolumeProvider service = new VolumeProvider();
//		String uuid = "TODO";
//    	Host host = new Host();
//    	host.setUuid(uuid);
//    	host.setType(HostTypeEnum.STORAGE_NODE);
//		String routingkey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.VOLUME_PROVIDER_PRE,host);
//		try {
//			
//			BasicRPCServer server = new BasicRPCServer(ConnectionConfigs.AMPQ_SERVER_ADDRESS, "", routingkey, VolumeProviderService.class, service);
//			logger.info("server init SUCCESS!");
//			while (true) {
//				server.mainloop();
//				logger.info("Lost connection, try again");
//				try {
//					server = new BasicRPCServer(ConnectionConfigs.AMPQ_SERVER_ADDRESS, "", routingkey, VolumeProviderService.class, service);
//					logger.info("Connected to ampq server");
//				} catch (Exception e) {
//					logger.warn("Cannot connect to ampq server", e);
//					Thread.sleep(1000);
//				}
//			}
//		} catch (IOException e) {
//			logger.error("server init error!",e);
//	        System.exit(1);
//		}
	}
}
