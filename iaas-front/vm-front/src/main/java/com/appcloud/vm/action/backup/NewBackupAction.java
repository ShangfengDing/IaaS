package com.appcloud.vm.action.backup;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcGroup;
import appcloud.api.beans.Backup;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;
import com.appcloud.vm.manager.volume.VolumeManager;

public class NewBackupAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(NewBackupAction.class);
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	
	private String displayName = "";
	private String displayDescription = "";
	private String serverId = "";
	private String result = "fail";
	private String serverName = "";
	
	public String execute() {
		String userId  = this.getSessionUserID().toString();
		
		AcGroup acGroup = this.getSessionGroupInfo();
		Long backupNum = volumeClient.countByProperties(userId, null, userId, null, null, null, 
				null, true, null, null, null, null);
		if(acGroup.maxNumberOfBackup <= backupNum){
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"创建备份", "由于达到备份数量最大值,创建云主机\""+serverName+"\"的备份\""+displayName+"\"失败", 
					"BackupOperateAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
			logger.info("申请的备份数量已达到最大值！");
			this.result = "申请的备份数量已达到最大值";
			return SUCCESS;
		}
		
		logger.info("serverId:"+serverId+",name:"+displayName+",description:"+displayDescription);
		String volumeId = new VolumeManager().getSysVolumeIdByServerId(userId, serverId);

		//查找虚拟机已有的备份个数，若达到最大限制，则删除最早创建的备份
		/*int maxBackupNum = this.getSessionBackupNum();
		List<Backup> allBackups = volumeClient.getBackupList(userId);
		List<Backup> vmBackups = new ArrayList<Backup>();
		for (Backup backup : allBackups) {
			if (backup.volumeId.equals(volumeId)) {
				vmBackups.add(backup);
			}
		}
		logger.info("最大备份个数："+maxBackupNum+",该虚拟机已有备份个数："+vmBackups.size());
		while (vmBackups.size()>=maxBackupNum) {
			Backup lastBackup = vmBackups.remove(0);
			volumeClient.deleteVolume(userId, lastBackup.id);
			logger.info("删除备份成功");
		}*/
		
		//创建新备份
		Backup backup = new Backup(null, displayName, userId, displayDescription, volumeId, 
				null, null, null, true, null);
		if(volumeClient.createBackup(userId, backup) != null) {
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"创建备份", this.getSessionUserEmail() + "创建云主机\""+serverName+"\"的备份\""+backup.displayName+"\"", 
					"NewBackupAction.class", null, AcLogLevelEnum.INFO,
					new Date(System.currentTimeMillis()));
			logger.info("备份创建成功");
			this.result = "success";
		}
		return SUCCESS;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
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
}
