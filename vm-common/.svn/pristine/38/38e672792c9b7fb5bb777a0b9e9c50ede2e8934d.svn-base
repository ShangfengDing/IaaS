/**
 * File: AlertService.java
 * Author: weed
 * Create Time: 2013-6-20
 */
package appcloud.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import appcloud.common.model.AlertMsg;
import appcloud.common.model.AlertMsg.AlertMsgStatus;
import appcloud.common.proxy.AlertMsgProxy;

/**
 * @author weed
 *
 */
public class AlertUtil {
	private static AlertMsgProxy proxy = (AlertMsgProxy) ConnectionFactory.getDBProxy(AlertMsgProxy.class);
	private static Logger logger = Logger.getLogger(AlertUtil.class);
	private static String MODULE_NAME = "AppCloud";
	
	/**
	 * @param msg 告警信息
	 * @param detail 告警詳情
	 */
	public static void alert(String msg, String detail){
		alert(MODULE_NAME, msg, detail, null);
	}
	
	/**
	 * @param msg 告警信息
	 * @param t 告警异常, 异常信息直接作为告警详情
	 */
	public static void alert(String msg, Throwable t){
		alert(MODULE_NAME, msg, null, t);
	}
	
	/**
	 * @param msg 告警信息
	 * @param detail 告警詳情
	 * @param t 告警异常，异常信息接在告警详情的后面
	 */
	public static void alert(String msg, String detail, Throwable t){
		alert(MODULE_NAME, msg, detail, t);
	}
	
	/**
	 * @param module 告警模塊
	 * @param msg 告警信息
	 * @param detail 告警詳情
	 * @param t 告警异常，异常信息接在告警详情的后面
	 */
	public static void alert(String module, String msg, String detail, Throwable t){
		if (detail == null){
			detail = "";
		}
		
		if (t != null){
            StringWriter sw = new StringWriter();
            sw.append(detail).append('\n');
            PrintWriter pw = new PrintWriter(sw);  
			t.printStackTrace(pw);
			pw.flush();
			pw.close();
			
			detail = sw.getBuffer().toString();
		}
		
		AlertMsg alertMsg = new AlertMsg(module, msg, detail, new Timestamp(System.currentTimeMillis()), AlertMsgStatus.ALERTED);
		try {
			proxy.save(alertMsg);
		} catch (Exception e) {
			logger.error("Alert Message Failed: " + alertMsg);
		}
	}

	public static void setMODULE_NAME(String moduleName) {
		MODULE_NAME = moduleName;
	}
}
