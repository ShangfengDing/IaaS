package appcloud.admin.action.system;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcServiceClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.api.enums.AcServiceTypeEnum;

import com.appcloud.vm.fe.util.ClientFactory;

public class HostOperateAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(HostOperateAction.class);
	
	private String hostId;			//要操作的节点id
	private Integer aggregateId;	//要加入的集群id
	private String serviceStr;		//要启动或停止的service列表
	
	private final static String START_SERVICE = "start";
	private final static String STOP_SERVICE = "stop";
	
	public String execute() {
		return SUCCESS;
	}
	
	//主机节点加入集群
	public String hostToCluster() {
		AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
		aggregateClient.addHost(aggregateId, hostId);
		logger.info("节点"+hostId+",加入集群"+aggregateId+"成功");
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "加入集群", "节点"+hostId+",加入集群"+aggregateId+"成功", "HostOperateAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	//节点启动服务
	public String startService() {
		return serviceOperate(START_SERVICE);
	}

	//节点停止服务
	public String stopService() {
		return serviceOperate(STOP_SERVICE);
	}
	
	private String serviceOperate(String type) {
		AcServiceClient serviceClient = ClientFactory.getServiceClient();
		logger.info("hostId:"+hostId+","+type+":"+serviceStr);
		String[] serviceArray = serviceStr.split(",");
		Set<AcServiceTypeEnum> services = new HashSet<AcServiceTypeEnum>();
		for (String service : serviceArray) {
			if (!service.equals("")) {
				try {
					services.add(AcServiceTypeEnum.valueOf(service.trim()));
				} catch (Exception e) {
					logger.error(service+"不能转换为AcServiceTypeEnum类型！！");
				}				
			}
		}
		if (type.equals(START_SERVICE)) {
			serviceClient.startServices(hostId, services);
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "启动服务", "启动 "+serviceStr+" 服务", "HostOperateAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			logger.info("启动服务成功");
		} else {
			serviceClient.stopServices(hostId, services);
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "停止服务", "停止"+serviceStr+" 服务", "HostOperateAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			logger.info("停止服务成功");
		}
		return SUCCESS;
	}
	
	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public Integer getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(Integer aggregateId) {
		this.aggregateId = aggregateId;
	}

	public String getServiceStr() {
		return serviceStr;
	}

	public void setServiceStr(String serviceStr) {
		this.serviceStr = serviceStr;
	}

}
