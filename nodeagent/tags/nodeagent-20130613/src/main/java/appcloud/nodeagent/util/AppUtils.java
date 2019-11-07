package appcloud.nodeagent.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

import appcloud.common.model.Host;
import appcloud.common.proxy.HostProxy;
import appcloud.nodeagent.Main;

/**
 * 
 * @author wylazy
 *
 */
public class AppUtils {

	private static Logger logger = Logger.getLogger(AppUtils.class);
	private static ApplicationContext context = null;
	
	static {
		context = new FileSystemXmlApplicationContext(new String [] {
				"classpath:application*.xml"
		}, null);
	}
	
	/**
	 * 获取Spring ApplicationContext
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	public static String getHostUuid() {
		ApplicationContext ctx = getApplicationContext();
		String uuid = (String) ctx.getBean("hostUuid");
		if (TextHelper.isEmpty(uuid)) {
			return SystemUtils.getUuid();
		} else {
			return uuid;
		}
	}
	
	/**
	 * 将host信息更新到数据库
	 * @throws Exception
	 */
	public static void saveOrUpdateHost() {
		
		ApplicationContext ctx = getApplicationContext();
		HostProxy hostProxy = (HostProxy) ctx.getBean("hostProxy");
		String uuid = getHostUuid();
		
		try {
			//更新Host表
			Host host = hostProxy.getByUuid(uuid, false, false, false);
			Host confHost = (Host) ctx.getBean("host");
			updateHostProperties(confHost);
			
			if (host == null) {
				logger.debug("save host " + confHost);
				hostProxy.save(confHost);
				host = hostProxy.getByUuid(confHost.getUuid(), false, false, false);
				confHost.setId(host.getId());
			} else {
				confHost.setId(host.getId());
				logger.debug("upate host " + confHost);
				hostProxy.update(confHost);
			}
		} catch (Exception e) {
			logger.error("SaveOrUpdate Host error", e);
		}
    }
	
	public static void updateHostProperties(Host host) {
		if (TextHelper.isEmpty(host.getIp())) {
			host.setIp(SystemUtils.getFirstIp());
		}
		
		if (TextHelper.isEmpty(host.getUuid())) {
			host.setUuid(SystemUtils.getUuid());
		}
	}
	
	/**
	 * 启动监控程序，监控Service，采集host负载信息，vm负载信息
	 */
	public static void startMonitors() {
		
		TaskScheduler scheduler = (TaskScheduler) getApplicationContext().getBean("scheduler");
		List<ScheduledExecutorTask> tasks = (List<ScheduledExecutorTask>) getApplicationContext().getBean("tasks");
		
		for (ScheduledExecutorTask task : tasks) {
			scheduler.scheduleAtFixedRate(task.getRunnable(), task.getPeriod());
		}
	}
}
