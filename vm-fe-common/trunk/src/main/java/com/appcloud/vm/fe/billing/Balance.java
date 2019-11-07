package com.appcloud.vm.fe.billing;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class Balance {

	private static final Logger logger = Logger.getLogger(Balance.class);
	int uid;
	int balance;
	String email;
	
	public Balance() {
	}

	public Balance(int uid, String email, int balance) {
		this.uid = uid;
		this.balance = balance;
		this.email = email;
	}
	
	public Balance(JSONObject o) {
		try {
			this.uid = Integer.parseInt( o.getString("uid") );
			this.email = o.getString("email");
			this.balance = Integer.parseInt( o.getString("balance") );
		} catch (JSONException e) {
			logger.debug("invalid Balance",e);
		}
	}

	public int getUid() {
		return uid;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
