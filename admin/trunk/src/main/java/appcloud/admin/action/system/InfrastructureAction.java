package appcloud.admin.action.system;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.action.network.NetListAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcService;
import appcloud.api.client.*;
import appcloud.api.enums.AcServiceTypeEnum;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InfrastructureAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(InfrastructureAction.class);
	private AcServiceClient serviceClient = ClientFactory.getServiceClient();
	private List<AcService> acServices = new ArrayList<AcService>();
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private AcUserClient acuserClient = ClientFactory.getAcUserClient();
	private ServerClient serverClient = ClientFactory.getServerClient();
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	private ImageClient imageClient = ClientFactory.getImageClient();
	private List<AcAggregate> aggregates= new ArrayList<AcAggregate>();
	private AcHostClient hostClient = ClientFactory.getHostClient();
	private EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
	private List<Integer> ipNumList = new ArrayList<Integer>();
	private List<Integer> imgList = new ArrayList<Integer>();
	private Integer imgNum;
	private Integer aggNum;
	private Long hostNum;
	private Long userNum;
	private Integer serverNum;
	private Long volNum;
	private Integer enNum;
	
	public String execute() {
		List<AcService> allServices = serviceClient.getAllServices();
		for (AcService service : allServices) {
			if(AcServiceTypeEnum.RESOURCE_SCHEDULER.equals(service.type)){
				acServices.add(service);
			}
			if(AcServiceTypeEnum.VOLUME_SCHEDULER.equals(service.type)){
				acServices.add(service);
			}
			if(AcServiceTypeEnum.VM_SCHEDULER.equals(service.type)){
				acServices.add(service);
			}
			if(AcServiceTypeEnum.IMAGE_SERVER.equals(service.type)){
				acServices.add(service);
			}
			if(AcServiceTypeEnum.NETWORK_PROVIDER.equals(service.type)){
				acServices.add(service);
			}
			if(AcServiceTypeEnum.LOL_SERVER.equals(service.type)){
				acServices.add(service);
			}
		}
		/*
		 * 获取集群，服务器，Ip，模板，云主机，云硬盘，用户数量,企业用户数量
		 */
		aggNum = aggregateClient.getAggregatesOfZone(1).size();
		hostNum = Long.valueOf(hostClient.countByProperties("admin", null, "", "", "", null, null, "", 0, null, 0, null, 0, null));
		ipNumList = new NetListAction().countAllIpNum();//获取IP数量
		imgNum = countImages();//获取镜像数量
		userNum = acuserClient.count(null, null ,null, null);
		serverNum = serverClient.getAllTenantsServers(Constants.ADMIN_TENANT_ID, true).size();
		volNum =  volumeClient.countByProperties("admin", null, 
				null, null, "network", null, null, false,
				null, null, null, null);
		enNum = enterpriseClient.getByProperties(null, null, null, null, null, null, null, null, null, null, null, null, null).size();
		return SUCCESS;
	}

	public Integer countImages(){
		// 统计模板数量，md5sum值相同的算一个模板
//		List<Image> images = imageClient.getImages(Constants.ADMIN_TENANT_ID, true, "all", ImageTypeEnum.IMAGE, Constants.QUERY_ALL);
		HashSet<String> md5sums = new HashSet<String>();
//		for(Image img : images) {
//			md5sums.add(img.md5sum);
//		}
		return md5sums.size();
	} 
	
	public List<AcService> getAcServices() {
		return acServices;
	}
	public void setAcServices(List<AcService> acServices) {
		this.acServices = acServices;
	}
	public List<Integer> getIpNumList() {
		return ipNumList;
	}
	public void setIpNumList(List<Integer> ipNumList) {
		this.ipNumList = ipNumList;
	}
	public List<Integer> getImgList() {
		return imgList;
	}
	public void setImgList(List<Integer> imgList) {
		this.imgList = imgList;
	}
	public Integer getAggNum() {
		return aggNum;
	}
	public void setAggNum(Integer aggNum) {
		this.aggNum = aggNum;
	}
	public Long getHostNum() {
		return hostNum;
	}
	public void setHostNum(Long hostNum) {
		this.hostNum = hostNum;
	}
	public Long getUserNum() {
		return userNum;
	}
	public void setUserNum(Long userNum) {
		this.userNum = userNum;
	}
	public Integer getServerNum() {
		return serverNum;
	}
	public void setServerNum(Integer serverNum) {
		this.serverNum = serverNum;
	}
	public Long getVolNum() {
		return volNum;
	}
	public void setVolNum(Long volNum) {
		this.volNum = volNum;
	}

	public Integer getEnNum() {
		return enNum;
	}

	public void setEnNum(Integer enNum) {
		this.enNum = enNum;
	}

	public Integer getImgNum() {
		return imgNum;
	}

	public void setImgNum(Integer imgNum) {
		this.imgNum = imgNum;
	}
	
}
