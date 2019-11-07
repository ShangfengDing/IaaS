package com.appcloud.vm.fe.billing;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class BillingHistory {
	private static final Logger logger = Logger.getLogger(BillingHistory.class);

	private int id;

	private String userEmail;
	
	private String recEmail;
	
	private String pname;
	
	private String pid;
	
	private String paymentType;
	
	private int times;
	
	private double count;
	
	private int amount;
	
	private String time;

	private String log;

	public BillingHistory(JSONObject o) {
		try {
			this.id = Integer.parseInt( o.getString("id") );
			this.userEmail = o.getString("u_email");
			this.recEmail = o.getString("rec_email");
			this.pname = o.getString("name");
			this.pid = o.getString("reason");
			this.paymentType = o.getString("payment_type");
			this.times = Integer.parseInt( o.getString("times") );
			this.count = Double.parseDouble( o.getString("count") );
			this.amount = Integer.parseInt( o.getString("amount") );
			this.time = o.getString("time");
			this.log = o.getString("log");
		} catch (JSONException e) {
			logger.debug("invalid ShowBillingHistory",e);
		}
	}

	@Override
	public String toString() {
		String str;
		str = "BillingHistory[id:" + this.id + ", "
				+ "userEmail:" + this.userEmail + ", "
				+ "recEmail:" + this.recEmail + ", "
				+ "pname:" + this.pname + ", "
				+ "pid:" + this.pid + ", "
				+ "paymentType:" + this.paymentType + ", "
				+ "times:" + this.times + ", "
				+ "count:" + this.count + ", "
				+ "amount:" + this.amount + ", "
				+ "time:" + this.time + ", "
				+ "log:" + this.log
				+ "]";
		return str;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getRecEmail() {
		return recEmail;
	}

	public void setRecEmail(String recEmail) {
		this.recEmail = recEmail;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
}
