package appcloud.admin.action.runtime;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcMailConf;
import appcloud.api.client.AcMessageLogClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.util.ClientFactory;

public class SetMailConfAction extends BaseAction {
	/**
	 * 查找告警设置
	 */
	private static final long serialVersionUID = 2851570832024373103L;
	private static final Logger logger = Logger.getLogger(SetMailConfAction.class);
	private AcMessageLogClient acMessageLogClient = ClientFactory.getAcMessageLogClient();
	
	private String lolEmail;
	private String emailHost;
	private String emailFrom;
	private String emailPassword;
	private String emailSubject;
	private String moduleInChargeKey;
	private String moduleInChargeValue;
	private AcMailConf mailConf;
	
	public String execute(){
		AcMailConf acMailConf = new AcMailConf();
		acMailConf.lolEmail = lolEmail;
		acMailConf.emailFrom = emailFrom;
		acMailConf.emailHost = emailHost;
		acMailConf.emailPassword = emailPassword;
		acMailConf.emailSubject = emailSubject;
		HashMap<String, String> moduleMap = new HashMap<String, String>();
		logger.info(moduleInChargeValue);
		String[] keys = moduleInChargeKey.split("-");
		String[] values = moduleInChargeValue.split("-");
		for(int i = 0; i < keys.length; i++){
			logger.info(keys[i]);
			logger.info(values[i]);
			moduleMap.put(keys[i], values[i]);
		}
		acMailConf.moduleInCharge = moduleMap;
		logger.info(acMailConf);
		
		mailConf = acMessageLogClient.setMailConf(acMailConf);
		logger.info(mailConf);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "告警设置", "结果为："+mailConf, "SetMailConfAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public String getLolEmail() {
		return lolEmail;
	}

	public void setLolEmail(String lolEmail) {
		this.lolEmail = lolEmail;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getModuleInChargeKey() {
		return moduleInChargeKey;
	}

	public void setModuleInChargeKey(String moduleInChargeKey) {
		this.moduleInChargeKey = moduleInChargeKey;
	}

	public String getModuleInChargeValue() {
		return moduleInChargeValue;
	}

	public void setModuleInChargeValue(String moduleInChargeValue) {
		this.moduleInChargeValue = moduleInChargeValue;
	}

	public AcMailConf getMailConf() {
		return mailConf;
	}

	public void setMailConf(AcMailConf mailConf) {
		this.mailConf = mailConf;
	}

}
