package com.appcloud.vm.action;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcGroup;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.client.AcMessageLogClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.common.SessionConstants;
import com.appcloud.vm.fe.util.ClientFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(BaseAction.class);
	/*
	 * 取得session中存储的登录用户信息
	 */
	public String getSessionUserEmail(){
		return (String)ActionContext.getContext().getSession().get(SessionConstants.UserEmail);
	}
	
	public String getAccessToken() {
		return (String)ActionContext.getContext().getSession().get(SessionConstants.AccessToken);
	}
	 
	public String getSessionAccessToken(){
		return (String)ActionContext.getContext().getSession().get(SessionConstants.AccessToken);
	}
	    
	public Integer getSessionUserID(){
		Object ob = ActionContext.getContext().getSession().get(SessionConstants.UserID);
		if(ob == null)
			return null;
		else 
			return (Integer)ob;
	}
	
	public Integer getSessionLoginUserId() {
		return (Integer)ActionContext.getContext().getSession().get(SessionConstants.LoginUserID);
	}
	
	/*
	 * 取得session中快照备份个数限制
	 */
	/*public Integer getSessionSnapshotNum(){
		return (Integer)ActionContext.getContext().getSession().get(SessionConstants.SnapshotNum);
	}
	
	public Integer getSessionBackupNum(){
		return (Integer)ActionContext.getContext().getSession().get(SessionConstants.BackupNum);
	}*/
	
	public Integer getSessionGroupID(){
		return (Integer)ActionContext.getContext().getSession().get(SessionConstants.GroupId);
	}
	
	public Boolean getSessionIsActived(){
		return (Boolean)ActionContext.getContext().getSession().get(SessionConstants.IsActived);
	}
	
	public AcGroup getSessionGroupInfo(){
		return (AcGroup)ActionContext.getContext().getSession().get(SessionConstants.GroupInfo);
	}
	
	public void addAcMessageLog(AcModuleEnum module, String transactionId,
			String userId, String vmHdUuid, String operateDrpt, String logContent,
			String sourceClass, String ipAddress, AcLogLevelEnum logLevel,
			Date logTime, Map<String, Object> session) {
		AcMessageLogClient acMessageLogClient = ClientFactory.getAcMessageLogClient();
		AcMessageLog acMessageLog = null;
		if(session != null) {
			AcMessageLog log = new AcMessageLog(module, transactionId,
					session.get(SessionConstants.UserID).toString(), vmHdUuid, operateDrpt, 
					session.get(SessionConstants.UserEmail)+":"+logContent, sourceClass, 
					ipAddress, logLevel, logTime);
			acMessageLog = acMessageLogClient.addLog(log);
			if(acMessageLog == null){
				logger.info("日志写入失败:"+operateDrpt+","+logContent+","+sourceClass+","
						+logLevel+","+logTime);
			}else{
				logger.info("写入日志成功:"+acMessageLog);
			}
		} else {
			addAcMessageLog(module,transactionId, userId, vmHdUuid, 
					operateDrpt, logContent, sourceClass, ipAddress, 
					logLevel, logTime);
		}
	}
	
	
	public void addAcMessageLog(AcModuleEnum module, String transactionId,
			String userId, String vmHdUuid, String operateDrpt, String logContent,
			String sourceClass, String ipAddress, AcLogLevelEnum logLevel,
			Date logTime){
		AcMessageLogClient acMessageLogClient = ClientFactory.getAcMessageLogClient();
		AcMessageLog log = new AcMessageLog(module, transactionId,
				this.getSessionUserID().toString(), vmHdUuid, operateDrpt, 
				this.getSessionUserEmail()+":"+logContent, sourceClass, 
				ipAddress, logLevel, logTime);
		AcMessageLog acMessageLog = acMessageLogClient.addLog(log);
		if(acMessageLog == null){
			logger.info("日志写入失败:"+operateDrpt+","+logContent+","+sourceClass+","
					+logLevel+","+logTime);
		}else{
			logger.info("写入日志成功:"+acMessageLog);
		}
	}
	
}