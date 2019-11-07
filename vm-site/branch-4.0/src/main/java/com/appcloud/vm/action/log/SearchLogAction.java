package com.appcloud.vm.action.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcMessageLog;
import appcloud.api.client.AcMessageLogClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class SearchLogAction extends BaseAction {
	/**
	 * 查找日志信息
	 */
	private static final long serialVersionUID = 2851570832024373103L;
	private static final Logger logger = Logger.getLogger(SearchLogAction.class);
	private AcMessageLogClient acMessageLogClient = ClientFactory.getAcMessageLogClient();
	private List<AcMessageLog> messageLogs = new ArrayList<AcMessageLog>();
	private HashMap<Long, String> timeMap = new HashMap<Long, String>();
	
	private static final int count = 20;
	private String startTime = "";
	private String endTime = "";
	private String level = "";
	private String operation = "";
	private String order = "";
	private String logContent = "";
	private String vmUuid = "";
	
	public String execute(){
		String userId = this.getSessionUserID().toString();
		//按条件查询
		Date startDate = null;//构造日期
		Date endDate = null;
		DateFormat time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
		try {
			if(!startTime.equalsIgnoreCase("")){
				startDate = time.parse(startTime); 
				logger.info(startDate.toString());
			}
			if(!endTime.equalsIgnoreCase("")){
				endDate = time.parse(endTime);
				logger.info(endDate.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();  
			logger.warn("获取时间失败");
		}
		logger.info(userId);
		//构造查询条件
		AcMessageLog logMessage = new AcMessageLog();
		logMessage.ipAddress = null;
		logMessage.module = AcModuleEnum.VM_FRONT;
		logMessage.logContent = logContent;
		if(!level.equalsIgnoreCase("")){
			logMessage.logLevel = AcLogLevelEnum.valueOf(level);
		}
		logMessage.operateDrpt = operation;
		logMessage.userId = userId;
		logMessage.logTime = null;
		logMessage.vmHdUuid = vmUuid;
		
		logger.info(logMessage);
		try {
			messageLogs = acMessageLogClient.searchLog(logMessage, startDate, endDate, count, Integer.valueOf(order));
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("获取日志失败");
		}
		//返回的list的时间显示
		for(AcMessageLog msg: messageLogs){
			if(!timeMap.containsKey(msg.logTime.getTime())){
				DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
				String dateStr = sdf.format(msg.logTime); 
				timeMap.put(msg.logTime.getTime(), dateStr);
			}
		}
		
		if(messageLogs.size() >= 1){
			int last = messageLogs.size() - 1;
			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
			if(order.equals("0")) {
				String dateStr = sdf.format(messageLogs.get(last).logTime.getTime()+1); 
				timeMap.put(messageLogs.get(last).logTime.getTime()+1, dateStr);
			} else {
				String dateStr = sdf.format(messageLogs.get(last).logTime.getTime()-1); 
				timeMap.put(messageLogs.get(last).logTime.getTime()-1, dateStr);
			}
			
		}
		
		logger.info(messageLogs.size());
		
		
		return SUCCESS;
	}

	public List<AcMessageLog> getMessageLogs() {
		return messageLogs;
	}

	public void setMessageLogs(List<AcMessageLog> messageLogs) {
		this.messageLogs = messageLogs;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public HashMap<Long, String> getTimeMap() {
		return timeMap;
	}

	public void setTimeMap(HashMap<Long, String> timeMap) {
		this.timeMap = timeMap;
	}

	public String getVmUuid() {
		return vmUuid;
	}

	public void setVmUuid(String vmUuid) {
		this.vmUuid = vmUuid;
	}
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
