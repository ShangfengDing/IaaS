package appcloud.admin.action.price;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.billing.BillingAPI;

public class DelRuleAction extends BaseAction {
	/**
	 * 删除计费规则
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(DelRuleAction.class);
	
	private Integer pid;
	private String result = "fail";
	
	public String execute() throws Exception{
		if(BillingAPI.delRate(pid)){
			this.setResult(SUCCESS);
			LOGGER.info("删除计费规则成功！");
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "删除计费规则", "删除计费规则，id为: "+ pid, "DelRuleAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			return SUCCESS;
		}else{
			LOGGER.info("删除计费规则失败！");
			return SUCCESS;
		}
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}