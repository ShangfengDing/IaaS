package appcloud.api.manager.real;

import org.apache.log4j.Logger;
import appcloud.api.manager.KeepAliveManager;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;

/**
 * @author jianglei
 *
 */

public class RealKeepAliveManager implements KeepAliveManager {
	
	private static Logger logger = Logger.getLogger(RealKeepAliveManager.class);
	
	private ResourceSchedulerService scheduler;
	
	private static RealKeepAliveManager manager = new RealKeepAliveManager();
	
	//private static LolLogUtil loller = LolHelper.getLolLogUtil(RealKeepAliveManager.class);
	
	public static RealKeepAliveManager getInstance() {
		return manager;
	}
	
	private RealKeepAliveManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
	}

	
	public static void main(String[] args) throws Exception{
		
		getInstance().KeepAlive();
	}

	@Override
	public String KeepAlive() throws Exception {
		
		logger.info(String.format("--------------------keepalive-------------------"));
		logger.info(String.format("------------User request to KeepAlive-----------"));
		return scheduler.KeepAlive();
//		logger.info(scheduler.KeepAlive());
//		return "";
		
	}
	
	
	

}
