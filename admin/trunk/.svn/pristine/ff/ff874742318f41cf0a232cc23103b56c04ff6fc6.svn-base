package appcloud.admin.action.price;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.billing.BillingAPI;
import com.free4lab.utils.account.AccountUtil;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

public class RechargeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RechargeAction.class);
	
	private int uid;
	private double recharge;
	private String balance;
	

	private String result;
    
	public String execute() throws Exception {
	    int rechargeInt = Double.valueOf(recharge*100).intValue();
	    if(BillingAPI.recharge(uid, rechargeInt)){
	        logger.info(uid + "--account recharge" + rechargeInt + " 元");
	        this.result = "success";
	    }else{
	        this.result = "error";
	    }
	    
	    HashMap<String, String> params = new HashMap<String, String>();
		params.put("uid", String.valueOf(uid));
		params.put("source", Constants.CLIENT_ID);
	    Integer balanceInt = BillingAPI.balance(Integer.valueOf(uid), null, AccountUtil.getSignature(params, Constants.CLIENT_SECRET_KEY), Constants.CLIENT_ID);
		BigDecimal bd = new BigDecimal(String.valueOf((double)(balanceInt/100.0)));
		bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
		balance = bd.toString();
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "充值", "充值: "+ recharge, "RechargeAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
	    return SUCCESS;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public double getRecharge() {
		return recharge;
	}

	public void setRecharge(double recharge) {
		this.recharge = recharge;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
