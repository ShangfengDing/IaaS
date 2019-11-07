package appcloud.admin.action.system;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.util.ClientFactory;

public class NewClusterAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(NewClusterAction.class);
	
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();

	private String name;	//集群名称
	private Integer zoneId;	//数据中心id
	private Integer privateVlanId;
	private Integer publicVlanId;
	
	public String execute() {
		logger.info("name:"+name+",zoneId:"+zoneId);
		AcAggregate aggregate = new AcAggregate();
		aggregate.availabilityZoneId = zoneId;
		aggregate.name = name;
		aggregate.privateVlanId = privateVlanId;
		aggregate.publicVlanId = publicVlanId;
		aggregateClient.createAggregate(aggregate);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "添加集群", "添加集群"+aggregate.name, "NewClusterAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		logger.info("新建集群成功");
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public Integer getPrivateVlanId() {
		return privateVlanId;
	}

	public void setPrivateVlanId(Integer privateVlanId) {
		this.privateVlanId = privateVlanId;
	}

	public Integer getPublicVlanId() {
		return publicVlanId;
	}

	public void setPublicVlanId(Integer publicVlanId) {
		this.publicVlanId = publicVlanId;
	}
}
