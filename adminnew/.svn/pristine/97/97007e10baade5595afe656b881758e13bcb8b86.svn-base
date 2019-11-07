package appcloud.admin.action.network;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.api.beans.AddressPool;
import appcloud.api.client.AddressPoolClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import appcloud.admin.action.base.BaseAction;

public class NewNetAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(NewNetAction.class);
	private AddressPoolClient addressPoolClient = ClientFactory.getAddressPoolClient();
	
	private Integer aggregateId;
	private String addressType;
	private String ipStart;
	private String ipEnd;
	private String netmask;
	private String gateway;
	private String dns;
	private int zoneId;
	
	//创建一个新的ip池
	public String execute() {
		AddressPool newAddressPool = new AddressPool();
		newAddressPool.aggregateId = aggregateId;
		newAddressPool.type = addressType;
		newAddressPool.ipStart = ipStart;
		newAddressPool.ipEnd = ipEnd;
		newAddressPool.netmask = netmask;
		newAddressPool.gateway = gateway;
		newAddressPool.dns = dns;
		
		addressPoolClient.createAddressPool(Constants.ADMIN_TENANT_ID, newAddressPool);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "新建Ip段", "新建Ip段: "+ipStart+"-"+ipEnd, "NewNetAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		logger.info("创建新ip池成功");
		return SUCCESS;
	}

	public Integer getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(Integer aggregateId) {
		this.aggregateId = aggregateId;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}

	public void setIpEnd(String ipEnd) {
		this.ipEnd = ipEnd;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

}
