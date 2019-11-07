/*
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL.
 */

package appcloud.nodeagent;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import appcloud.common.model.Host;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.proxy.HostProxy;
import appcloud.nodeagent.info.NodeAgent;
import appcloud.nodeagent.util.AppUtils;
import appcloud.nodeagent.util.ServiceManager;
import appcloud.nodeagent.util.SystemUtils;


/**
 *
 * @author wylazy
 */
public class Main {

	private static int x = 0;
	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String [] args) throws Exception {
		NodeMonitorServer.main(new String[]{"start"});
		
		//System.out.println(SystemUtils.getFirstIp());
    }
}
