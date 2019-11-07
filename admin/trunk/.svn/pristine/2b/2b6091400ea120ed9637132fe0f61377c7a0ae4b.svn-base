package appcloud.admin.action.price;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.common.Constants;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

public class CleanBalanceAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static final int OPERATOR = Integer.parseInt(Constants.ADMIN_TENANT_ID);
	private static final Logger logger = Logger.getLogger(CleanBalanceAction.class);
	private int uid;
	private String result;

	
	public String execute() throws Exception{
		
	    if(BillingAPI.cleanBalance(OPERATOR, uid)){
	        logger.info("operator " + OPERATOR + " clean balance of account " + uid);
	        this.result = "success";
	    }else{
	        this.result = "error";
	    }

		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "清空", "清空余额", "CleanBalanceAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
	    return SUCCESS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
}
