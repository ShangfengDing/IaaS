package com.appcloud.vm.manager.volume;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import appcloud.api.beans.Server;
import appcloud.api.beans.Volume;
import appcloud.api.beans.VolumeAttachment;
import appcloud.api.client.ServerClient;
import appcloud.api.client.VolumeAttachmentClient;
import appcloud.api.client.VolumeClient;

import com.appcloud.vm.action.shot.ShotListAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class VolumeManager{
	
	private Logger logger = Logger.getLogger(VolumeManager.class);
	
	/**
	 * 找到某个server的系统盘uuid
	 * @param devId
	 * @param serverId	server的uuid
	 * @return	系统盘uuid,返回null代表找系统盘失败
	 */
	public String getSysVolumeIdByServerId(String devId, String serverId) {
		VolumeAttachmentClient volumeAttachmentClient = ClientFactory.getVolumeAttachmentClient();
		String volumeId = null;
		
		List<VolumeAttachment> volumeAttachments = 
				volumeAttachmentClient.getVolumeAttachments(devId, serverId, false);
		Pattern pt = Pattern.compile("^.*a$");	//系统盘最后一个字符为a
		for (VolumeAttachment volumeAttachment: volumeAttachments){
			String device = volumeAttachment.device;
			logger.info("挂载点："+device);
			Matcher matcher = pt.matcher(device);
			if(matcher.matches()){
				volumeId = volumeAttachment.volumeId;
				logger.info("系统盘volumeId:"+volumeId);
				break;
			}
		}
		if(volumeId == null){
			logger.error("serverId："+serverId+",没有找到最后字符为a的系统盘！");
		}
		return volumeId;
	}
}
