package appcloud.admin.action.hd;

import java.util.Date;
import java.util.UUID;

import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

public class HdListAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	private static final String tenantId = "admin";
	private static final String usageType = "network";
	private long total = 0;
	
	public String execute() throws Exception{
		total = volumeClient.countByProperties(tenantId, null, 
				null, null, usageType, null, null, false,
				null, null, null, null);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "查询硬盘总数", "硬盘总数为"+total, "HdListAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
}
