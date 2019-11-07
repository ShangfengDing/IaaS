package appcloud.common.service.monitor;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import appcloud.common.model.Host;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.UuidUtil;
import appcloud.rpc.tip.TipRPCServer;

/**
 * a base class for Yunhai Service only offer tip service
 * @author wangchao
 *
 */
public class AbstractYunhaiService implements YunhaiService{
	private static Logger logger = Logger.getLogger(AbstractYunhaiService.class);
	
	protected TipRPCServer tipserver = null;
	private long startTime = 0L;
	private boolean running = false;
	private boolean initialized = false;
	
	@Override
	public void initialize() {
		logger.info("initializing AbstractYunhaiService" );
		startTime = System.currentTimeMillis();
		tipserver = new TipRPCServer("namenotimportant");
		tipserver.addService(YunhaiService.class, this);
		initialized = true;
		logger.info("initialized AbstractYunhaiService");
	}
	public void start(){
		logger.info("starting AbstractYunhaiService");
		if (running)
			return;
		
		if (!initialized)
			initialize();
		tipserver.start();
		running = true;
		logger.info("started AbstractYunhaiService");
	}
	@Override
	public void run(){
		start();
		while(running){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop();
	}

	@Override
	public long uptime() {
		return System.currentTimeMillis() - startTime;
	}

	@Override
	public void stop() {
		logger.info("stopping AbstractYunhaiService" );
		markAsStopped();
		tipserver.stop();
		ConnectionFactory.close();
		logger.info("stopped AbstractYunhaiService" );
	}

	@Override
	public boolean isRunning() {
		return running;
	}
	
	@Override
	public void markAsStopped() {
		logger.info("mark as stopped");
		running = false;
		tipserver.markAsStopped();
	}
	
	/**
	 * 获取主机uuid
	 * @param generateDefault 当设置为true的时候，如果找不到uuid，返回一个随机的字符串。当设置为false的时候，如果找不到uuid，返回null
	 * @return uuid
	 */
	public static String getHostUuid(boolean generateDefault) {
		
		String uuid = System.getProperty("host.uuid", null);
		if (uuid == null && generateDefault) {
			uuid = UuidUtil.getRandomUuid();
			logger.warn("Could not find hostUuid, now it was generated randomly");
		}
		
		return uuid;
	}
	
	/**
	 * 获取service所在的host
	 * @return
	 */
	public static Host getHost() {
		ObjectMapper mapper = new ObjectMapper();
		String baseStr = System.getProperty("host.all", null);
		
		Host host = null;
		try {
			String hostJson = new String(Base64.decodeBase64(baseStr), "utf-8");
			host = mapper.readValue(hostJson, Host.class);
		} catch (Exception e) {
			logger.error("Cannot get host info" + baseStr, e);
		}
		
		return host;
	}
}
