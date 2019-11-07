package appcloud.common.service.monitor;

import java.io.IOException;

import org.apache.log4j.Logger;

import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.rpc.ampq.BasicRPCServer;

import com.rabbitmq.client.ConsumerCancelledException;

/**
 * A base class for Yunhai Service runs both tip and ampq Services.
 * @author wangchao
 *
 */
public class BTYunhaiService extends AbstractYunhaiService{
	private static Logger logger = Logger.getLogger(BTYunhaiService.class);
	private static final String exchange = "yunhai35dev";
	private String routingkey;
	private Class<?> interfaceClazz;
	private Object instance;
	private BasicRPCServer rpcserver =  null;
	
	public BTYunhaiService(String routingkey,Class<?> interfaceClass,
            Object interfaceInstance) {
		this.routingkey = routingkey;
		this.interfaceClazz = interfaceClass;
		this.instance = interfaceInstance;
	}
	
	private void genRPCServer() throws IOException{
		rpcserver = new BasicRPCServer(
				ConnectionConfigs.AMPQ_SERVER_ADDRESS, "",
				routingkey, interfaceClazz,
				instance);
	}
	
	@Override
	public void initialize() {
		logger.info("initializing BTYunhaiService");
		super.initialize();
		try {
			genRPCServer();
		} catch (IOException e) {
			logger.error("Server init FAIL!",e);
		}
		logger.info("initialized BTYunhaiService");
	}
	
	@Override
	public void start() {
		logger.info("starting BTYunhaiService");
		super.start();
		logger.info("started BTYunhaiService");
	}
	
	@Override
	public void run() {
		start();
		while(isRunning()){
			try {
				rpcserver.mainloop();
				if(!isRunning())break;
				logger.info("Lost connection, try again");
				Thread.sleep(ConnectionConfigs.RUN_PERIOD);
				genRPCServer();
				logger.info("Connected to ampq server");
			} catch (ConsumerCancelledException e) {
				logger.warn("ConsumerCancelledException",e);
			} catch (InterruptedException e) {
				logger.warn("Interruptted",e);
			} catch (IOException e) {
				logger.warn("Cannot connect to ampq server",e);
			}
		}
		stop();
		logger.info("Service exit.");
	}
	
	@Override
	public void stop() {
		logger.info("stopping BTYunhaiService" );
		super.stop();
		try {
			rpcserver.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("stopped BTYunhaiService" );
		//TODO make rpcserver close
		System.exit(-1);
	}
	
	@Override
	public void markAsStopped() {
		super.markAsStopped();
		rpcserver.terminateMainloop();
	}
	

}
