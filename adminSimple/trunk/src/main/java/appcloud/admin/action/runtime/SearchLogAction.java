package appcloud.admin.action.runtime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import appcloud.api.beans.AcInstanceLog;
import appcloud.api.beans.AcOperateLog;
import appcloud.api.client.AcOperateLogClient;
import appcloud.api.client.InstanceLogClient;
import appcloud.common.model.InstanceLogType;
import com.appcloud.vm.fe.util.ClientFactory;
import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.client.AcMessageLogClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.users.UsersAPI;

public class SearchLogAction extends BaseAction {
	/**
	 * 查找日志信息
	 */
	private static final long serialVersionUID = 2851570832024373103L;
	private static final Logger logger = Logger.getLogger(SearchLogAction.class);
	private AcMessageLogClient acMessageLogClient = ClientFactory.getAcMessageLogClient();
	private InstanceLogClient instanceLogClient = ClientFactory.getInstanceLogClient();
	private AcOperateLogClient acOperateLogClient = ClientFactory.getAcOperateLogClient();
	private List<AcMessageLog> messageLogs = new ArrayList<AcMessageLog>();
	private HashMap<Long, String> timeMap = new HashMap<Long, String>();
	private List<AcInstanceLog> instanceLogList = new ArrayList<AcInstanceLog>();
	private List<AcOperateLog> acOperateLogList = new ArrayList<AcOperateLog>();
	
	private static final int count = 20;
	private String startTime = "";
	private String endTime = "";
	private String module = "";
	private String level = "";
	private String userId = "";
	private String transactionId = "";
	private String ip = "";
	private String operation = "";
	private String source = "";
	private String logContent = "";
	private String email = "";
	private String vmUuid = "";
	private String type = "";
	private String payType = "";
	private String groupId = "";
	private String vm = "";
	private String provider = "";
	private String result = "";
	private String operateType = "";
	private String timeasc = "";
	private int isEmail = 0;

	public String execute(){
		//查询全部
		Integer order = timeasc.equals("yes")? 0:1;
		if(startTime.equalsIgnoreCase("") && endTime.equalsIgnoreCase("")
				&& module.equalsIgnoreCase("") && level.equalsIgnoreCase("") 
				&& userId.equalsIgnoreCase("") && transactionId.equalsIgnoreCase("")
				&& ip.equalsIgnoreCase("") && operation.equalsIgnoreCase("") 
				&& source.equalsIgnoreCase("") && logContent.equalsIgnoreCase("")
				&& email.equalsIgnoreCase("") && vmUuid.equalsIgnoreCase("")){
			try {
				messageLogs = acMessageLogClient.searchLog(null, null, null, count, order);
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("获取null日志失败");
			}
		}else{//按条件查询
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
			
			if(!email.equalsIgnoreCase("")){//根据email查找用户userid
				Integer uid = UsersAPI.getUidByEmail(email);
				logger.info(uid);
				if(uid == -1){
					isEmail = 1;
					return SUCCESS;
				}else{
					userId = uid.toString();
				}
			}
			
			//构造查询条件
			AcMessageLog logMessage = new AcMessageLog();
			logMessage.ipAddress = ip;
			logMessage.logContent = logContent;
			if(!level.equalsIgnoreCase("")){
				logMessage.logLevel = AcLogLevelEnum.valueOf(level);
			}
			if(!module.equalsIgnoreCase("")) {
				logMessage.module = AcModuleEnum.valueOf(module);
			}

			logMessage.operateDrpt = operation;
			logMessage.sourceClass = source;
			logMessage.transactionId = transactionId;
			logMessage.userId = userId.toString();
			logMessage.logTime = null;
			logMessage.vmHdUuid = vmUuid;
			
			logger.info(logMessage);
			try {
				messageLogs = acMessageLogClient.searchLog(logMessage, startDate, endDate, 
						count, order);
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("获取日志失败");
			}
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
			Integer delta = timeasc.equals("yes")?1000:-1000;
			String dateStr = sdf.format(messageLogs.get(last).logTime.getTime()+delta);
			timeMap.put(messageLogs.get(last).logTime.getTime()+1, dateStr);
		}
		logger.info(messageLogs.size());
		return SUCCESS;
	}

	public String searchInstanceLog(){
		//查询全部
		Boolean order = timeasc.equals("yes");
		if(startTime.equalsIgnoreCase("") && endTime.equalsIgnoreCase("")
				&& userId.equalsIgnoreCase("") && email.equalsIgnoreCase("")
				&& vmUuid.equalsIgnoreCase("") && type.equalsIgnoreCase("")
				&& payType.equalsIgnoreCase("") && groupId.equalsIgnoreCase("")){
			try {
				instanceLogList = instanceLogClient.searchLog(null, null, null, count, order);
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("获取null日志失败");
			}
		}else{//按条件查询
			Timestamp startTimestamp = null;//构造日期
			Timestamp endTimestamp = null;
			try {
				DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
				if(!startTime.equalsIgnoreCase("")){
					Date startDate = sdf.parse(startTime);
					startTimestamp = new Timestamp(startDate.getTime());
					logger.info(startTimestamp.toString());
				}
				if(!endTime.equalsIgnoreCase("")){
					Date endDate = sdf.parse(endTime);
					endTimestamp = new Timestamp(endDate.getTime());
					logger.info(endTimestamp.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("获取时间失败");
			}

			if(!email.equalsIgnoreCase("")){//根据email查找用户userid
				Integer uid = UsersAPI.getUidByEmail(email);
				logger.info(uid);
				if(uid == -1){
					isEmail = 1;
					return SUCCESS;
				}else{
					userId = uid.toString();
				}
			}

			//构造查询条件
			AcInstanceLog acInstanceLog = new AcInstanceLog();
			if(!type.equalsIgnoreCase("")){
				acInstanceLog.type = InstanceLogType.Type.valueOf(type);
			}
			if(!payType.equalsIgnoreCase("")) {
				acInstanceLog.payType = InstanceLogType.PayType.valueOf(payType);
			}
			if (!groupId.equalsIgnoreCase(""))
				acInstanceLog.groupId = Integer.valueOf(groupId);
			if (!userId.equalsIgnoreCase(""))
				acInstanceLog.userId = Integer.valueOf(userId);
			if (!vmUuid.equalsIgnoreCase(""))
				acInstanceLog.uuid = vmUuid;

			logger.info(acInstanceLog);
			try {
				instanceLogList = instanceLogClient.searchLog(acInstanceLog, startTimestamp, endTimestamp,
						count, order);
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("获取日志失败");
			}
		}
		//返回的list的时间显示
		for(AcInstanceLog instanceLog: instanceLogList){
			if(!timeMap.containsKey(instanceLog.createdTime.getTime())){
				DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
				String dateStr = sdf.format(instanceLog.createdTime);
				timeMap.put(instanceLog.createdTime.getTime(), dateStr);
			}
		}

		if(instanceLogList.size() >= 1){
			int last = instanceLogList.size() - 1;
			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
			Integer delta = timeasc.equals("yes")?1000:-1000;
			String dateStr = sdf.format(instanceLogList.get(last).createdTime.getTime()+delta);
			timeMap.put(instanceLogList.get(last).createdTime.getTime()+1, dateStr);
		}
		logger.info(instanceLogList.size());
		return SUCCESS;
	}

	public String searchOperateLog(){
		Boolean order = timeasc.equals("yes");
	//查询全部
		if(startTime.equalsIgnoreCase("") && endTime.equalsIgnoreCase("")
				&& userId.equalsIgnoreCase("") && email.equalsIgnoreCase("")
				&& vmUuid.equalsIgnoreCase("") && level.equalsIgnoreCase("")
				&& result.equalsIgnoreCase("") && vm.equalsIgnoreCase("")
				&& provider.equalsIgnoreCase("")){
			try {
				acOperateLogList = acOperateLogClient.searchLog(null, null, null, count, order);
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("获取null日志失败");
			}
		}else{//按条件查询
			Timestamp startTimestamp = null;//构造日期
			Timestamp endTimestamp = null;
			try {
				DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
				if(!startTime.equalsIgnoreCase("")){
					Date startDate = sdf.parse(startTime);
					startTimestamp = new Timestamp(startDate.getTime());
					logger.info(startTimestamp.toString());
				}
				if(!endTime.equalsIgnoreCase("")){
					Date endDate = sdf.parse(endTime);
					endTimestamp = new Timestamp(endDate.getTime());
					logger.info(endTimestamp.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("获取时间失败");
			}

			if(!email.equalsIgnoreCase("")){//根据email查找用户userid
				Integer uid = UsersAPI.getUidByEmail(email);
				logger.info(uid);
				if(uid == -1){
					isEmail = 1;
					return SUCCESS;
				}else{
					userId = uid.toString();
				}
			}

			//构造查询条件
			AcOperateLog acOperateLog = new AcOperateLog();
			acOperateLog.result = result;
			acOperateLog.infoType = type;
			acOperateLog.deviceId = vmUuid;
			acOperateLog.device = vm;
			acOperateLog.provider = provider;
			acOperateLog.infoType = level;
			acOperateLog.userId = userId;
			acOperateLog.operateType = operateType;
			logger.info(acOperateLog);
			try {
				acOperateLogList = acOperateLogClient.searchLog(acOperateLog, startTimestamp, endTimestamp,
						count, order);
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("获取日志失败");
			}
		}
		//返回的list的时间显示
		for(AcOperateLog acOperateLog: acOperateLogList){
			if(!timeMap.containsKey(acOperateLog.createdTime.getTime())){
				DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
				String dateStr = sdf.format(acOperateLog.createdTime);
				timeMap.put(acOperateLog.createdTime.getTime(), dateStr);
			}
		}

		if(acOperateLogList.size() >= 1){
			int last = acOperateLogList.size() - 1;
			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss SSS");
			Integer delta = timeasc.equals("yes")?1000:-1000;
			String dateStr = sdf.format(acOperateLogList.get(last).createdTime.getTime()+delta);
			timeMap.put(acOperateLogList.get(last).createdTime.getTime()+1, dateStr);
		}
		logger.info(acOperateLogList.size());
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVmUuid() {
		return vmUuid;
	}

	public void setVmUuid(String vmUuid) {
		this.vmUuid = vmUuid;
	}

	public int getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<AcInstanceLog> getInstanceLogList() {
		return instanceLogList;
	}

	public void setInstanceLogList(List<AcInstanceLog> instanceLogList) {
		this.instanceLogList = instanceLogList;
	}

	public String getVm() {
		return vm;
	}

	public void setVm(String vm) {
		this.vm = vm;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<AcOperateLog> getAcOperateLogList() {
		return acOperateLogList;
	}

	public void setAcOperateLogList(List<AcOperateLog> acOperateLogList) {
		this.acOperateLogList = acOperateLogList;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getTimeasc() {
		return timeasc;
	}

	public void setTimeasc(String timeasc) {
		this.timeasc = timeasc;
	}

	public static void main(String[] args) {
		AcOperateLog acOperateLog = new AcOperateLog();

		System.out.println(ClientFactory.getAcOperateLogClient().searchLog(acOperateLog, null, null, null, false));
//		AcInstanceLog acInstanceLog = new AcInstanceLog();
//		instanceLogType.setUserId();
//		System.out.println(ClientFactory.getInstanceLogClient().searchLog(acInstanceLog, null, null, null, false).get(0));

//		System.out.println(AcModuleEnum.valueOf("IAAS_CHECK"));
//		AcModuleEnum aa = AcModuleEnum.valueOf("IAAS_CHECK");
//		System.out.println("end "+aa.toString());
//		AcMessageLog logMessage = new AcMessageLog();
//		String module = "IAAS_CHECK";
//
//
//		if(module.equals("IAAS_CHECK"))
//			System.out.println("yes");
//		if(!module.equalsIgnoreCase("")){
//			try{
//				logMessage.module = AcModuleEnum.valueOf(module);
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("nono");
//			}
//		}


	}
}
