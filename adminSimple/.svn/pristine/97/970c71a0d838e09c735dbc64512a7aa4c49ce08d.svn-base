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

public class EditZoneAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(EditZoneAction.class);
	
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private String name;
	private int zoneId;
	private String result = "fail";
	
	public String execute() {
		logger.info(zoneId);
		AvailabilityZone availabilityZone = aggregateClient.updateAvailabilityZone(zoneId, name);
		if(availabilityZone != null){
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "编辑数据中心", "名称为："+name, "EditZoneAction.class", 
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

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
