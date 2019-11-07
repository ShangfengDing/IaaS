package com.appcloud.vm.action.preview;

import appcloud.api.beans.AvailabilityZone;
import appcloud.api.beans.Image;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.ImageClient;
import appcloud.api.enums.ImageTypeEnum;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class  PreviewVmAction extends BaseAction{
	/**
	 * 申请虚拟机准备
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(PreviewVmAction.class);
	private ImageClient imageClient = ClientFactory.getImageClient();
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	
	
	private List<Image> images = new ArrayList<Image>();
	private List<AvailabilityZone> zones = new ArrayList<AvailabilityZone>();
	private List<SecurityGroup> securityGroups = new ArrayList<SecurityGroup>();
	private String domain;
	
	public String execute() {
		//String userId = this.getSessionUserID().toString();
		
		//查询所有数据中心
		zones = aggregateClient.getZones();
		
		//查询所有可用的系统镜像
		images = imageClient.getImages("0",true,"1",ImageTypeEnum.IMAGE,Constants.IMAGE_STATUS_AVAILABLE,null,null,null,null,null);
		logger.info("查询所有系统镜像成功");
		
		domain = com.appcloud.vm.common.Constants.DOMAIN;
		return SUCCESS;
	}

	public List<AvailabilityZone> getZones() {
		return zones;
	}

	public void setZones(List<AvailabilityZone> zones) {
		this.zones = zones;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
