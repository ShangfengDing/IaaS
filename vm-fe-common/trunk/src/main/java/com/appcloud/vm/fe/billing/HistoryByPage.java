package com.appcloud.vm.fe.billing;

import java.util.List;


public class HistoryByPage {
	
	List<BillingHistory> billingHistories;
	int total;
	
	public HistoryByPage() {
	}

	public HistoryByPage(List<BillingHistory> billingHistories, int total) {
		this.billingHistories = billingHistories;
		this.total = total;
	}

	public List<BillingHistory> getBillingHistories() {
		return billingHistories;
	}
	
	public void setBillingHistories(List<BillingHistory> billingHistories) {
		this.billingHistories = billingHistories;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
}
