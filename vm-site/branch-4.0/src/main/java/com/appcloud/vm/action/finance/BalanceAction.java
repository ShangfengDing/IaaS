package com.appcloud.vm.action.finance;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.billing.BillingAPI;

public class BalanceAction extends BaseAction{
	
    private static final long serialVersionUID = -2928293363187866416L;
    private Logger logger = Logger.getLogger(BalanceAction.class);
	private double balance;		//查询账户余额
	
	public String execute() {
	    balance = BillingAPI.balance(getSessionUserID(), getSessionAccessToken(), null,null)/100.0;
	    logger.info(getSessionUserEmail() + ", has" + balance + " 元");
		return SUCCESS;
	}

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
	
}
