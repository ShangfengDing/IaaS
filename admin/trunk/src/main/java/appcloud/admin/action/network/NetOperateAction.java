package appcloud.admin.action.network;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AddressPool;
import appcloud.api.client.AddressPoolClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

public class NetOperateAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(NetOperateAction.class);
	private AddressPoolClient addressPoolClient = ClientFactory.getAddressPoolClient();
	private AddressPool addressPool = null;
	private Integer poolId;
	
	//获取某个ip段的使用详情
	public String execute() {
		addressPool = addressPoolClient.get(Constants.ADMIN_TENANT_ID, poolId);
		logger.info("获取网络地址段detail成功");
		return SUCCESS;
	}

	//删除某个ip段
	private String result = "success";
	public String delNet() {
		addressPool = addressPoolClient.get(Constants.ADMIN_TENANT_ID, poolId);
		if (addressPoolClient.deleteAddressPool(Constants.ADMIN_TENANT_ID, poolId)) {
			logger.info(addressPool.ipStart+", "+addressPool.ipEnd);
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "删除ip段", "删除ip段: "+addressPool.ipStart+"-"+addressPool.ipEnd, "NetOperateAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			result = "success";
		} else {
			result = "fail";
		}
		return SUCCESS;
	}
	public AddressPool getAddressPool() {
		return addressPool;
	}

	public void setAddressPool(AddressPool addressPool) {
		this.addressPool = addressPool;
	}

	public Integer getPoolId() {
		return poolId;
	}

	public void setPoolId(Integer poolId) {
		this.poolId = poolId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
