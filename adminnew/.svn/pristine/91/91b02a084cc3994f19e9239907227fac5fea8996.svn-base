package appcloud.admin.action.system;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.util.ClientFactory;

public class NewZoneAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(NewZoneAction.class);
	
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private String name;
	private String result = "fail";
	
	public String execute() {
		AvailabilityZone zone = aggregateClient.createAvailabilityZone(name);
		logger.info(zone);
		if(zone != null){
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "添加数据中心", "添加数据中心"+zone.name, "NewZoneAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			result = "success";
		}
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
