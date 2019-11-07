package com.appcloud.vm.action.backup;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.client.BackupClient;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class BackupOperateAction extends BaseAction {
	private static final long serialVersionUID = -4303546504851605505L;
	private Logger logger = Logger.getLogger(BackupOperateAction.class);
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	private BackupClient backupClient = ClientFactory.getBackupClient();
	//private VolumeAttachmentClient attachmentClient = ClientFactory.getVolumeAttachmentClient();
	
	private final static String ROLLBACK = "rollback";
	private final static String DELETE = "delete";
	
	private String operation = "";		//操作类型
	private String backupId = "";		//操作的备份uuid
	private String serverId = "";		//备份所属的主机uuid
	private String volumeId = "";		//备份所属的volume uuid
	private String serverName = "";		//备份所属的主机名称，为写日志
	private String backupName = "";		//操作的备份名称，为写日志
	private String result = "success";

	public String execute() {
		String userId = this.getSessionUserID().toString();
		logger.info("backupId:"+backupId+",operation:"+operation);
		if (operation.equals(ROLLBACK)) {
			volumeClient.revertBackup(userId, backupId);
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"回滚备份", "回滚云主机\""+serverName+"\"的备份\""+backupName+"\"", 
					"BackupOperateAction.class", null, AcLogLevelEnum.INFO,
					new Date(System.currentTimeMillis()));
			logger.info("备份恢复成功");
		} else if (operation.equals(DELETE)){
			backupClient.deleteBackup(userId, backupId);
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"删除备份", "删除云主机\""+serverName+"\"的备份\""+backupName+"\"", 
					"BackupOperateAction.class", null, AcLogLevelEnum.INFO,
					new Date(System.currentTimeMillis()));
			logger.info("备份删除成功");
		}
		return SUCCESS;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getBackupId() {
		return backupId;
	}

	public void setBackupId(String backupId) {
		this.backupId = backupId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getBackupName() {
		return backupName;
	}

	public void setBackupName(String backupName) {
		this.backupName = backupName;
	}

}
