package com.appcloud.vm.action.backup;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.Backup;
import appcloud.api.client.VolumeClient;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class BackupListAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(BackupListAction.class);
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	
	private List<Backup> backups = new ArrayList<Backup>();
	
	public String execute() {
		String devId = this.getSessionUserID().toString();
		backups = volumeClient.getBackupList(devId);
		logger.info("获取backups成功");
		return SUCCESS;
	}

	public List<Backup> getBackups() {
		return backups;
	}
}
