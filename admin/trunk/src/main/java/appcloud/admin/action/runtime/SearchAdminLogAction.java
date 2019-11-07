package appcloud.admin.action.runtime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.users.UsersAPI;
import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.client.AcMessageLogClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.Admin;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

public class SearchAdminLogAction extends BaseAction {
	private static final long serialVersionUID = 2851570832024373103L;
	private static final Logger logger = Logger.getLogger(SearchAdminLogAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AcMessageLogClient acMessageLogClient = ClientFactory.getAcMessageLogClient();
	private List<AcMessageLog> messageLogs = new ArrayList<AcMessageLog>();
	private HashMap<Long, String> timeMap = new HashMap<Long, String>();
	
	private static final int count = 20;
	private String startTime = "";
	private String endTime = "";
	private String module = "VM_ADMIN";//默认为admin的日志
	private String level = "";
	private String userId = "";
	private String transactionId = "";
	private String operation = "";
	private String source = "";
	private String logContent = "";
	private String email = "";
	private String vmUuid = "";
	private int isEmail = 0;
	
	public String execute(){
		AcMessageLog logMessage = new AcMessageLog();
		logMessage.module = AcModuleEnum.valueOf(module);
		//查询全部
		if(startTime.equalsIgnoreCase("") && endTime.equalsIgnoreCase("")
				&& level.equalsIgnoreCase("") && userId.equalsIgnoreCase("") 
				&& transactionId.equalsIgnoreCase("")  
				&& operation.equalsIgnoreCase("") && source.equalsIgnoreCase("") 
				&& logContent.equalsIgnoreCase("") && email.equalsIgnoreCase("") 
				&& vmUuid.equalsIgnoreCase("")){
			try {
				messageLogs = acMessageLogClient.searchLog(logMessage, null, null, count);
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
				QueryObject<Admin> queryObject = new QueryObject<Admin>();
				queryObject.addFilterBean(new FilterBean<Admin>("email", email, FilterBeanType.EQUAL));
				Integer uid = null;
				try {
					uid = adminProxy.searchAll(queryObject, false, false, false).get(0).getId();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info(uid);
				if(uid == -1){
					isEmail = 1;
					return SUCCESS;
				}else{
					userId = uid.toString();
				}
			}
			
			//构造查询条件
			logMessage.logContent = logContent;
			if(!level.equalsIgnoreCase("")){
				logMessage.logLevel = AcLogLevelEnum.valueOf(level);
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
						count);
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
			String dateStr = sdf.format(messageLogs.get(last).logTime.getTime()+1); 
			timeMap.put(messageLogs.get(last).logTime.getTime()+1, dateStr);
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

}