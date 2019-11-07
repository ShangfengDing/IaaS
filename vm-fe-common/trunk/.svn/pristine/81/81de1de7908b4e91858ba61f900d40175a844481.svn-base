package com.appcloud.vm.fe.billing;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appcloud.vm.fe.common.Constants;
import com.free4lab.utils.account.AccountUtil;
import com.free4lab.utils.http.HttpCrawler;

public class BillingAPI {
	
	private static final Logger logger = Logger.getLogger(BillingAPI.class);

	/**
	 * 添加计费规则列表
	 * @param name
	 * @param description
	 * @param cpu
	 * @param memory
	 * @param harddisk
	 * @param bandwidth
	 * @param hourPrice
	 * @param dayPrice
	 * @param monthPrice
	 * @param yearPrice
	 * @param clusterid
	 * @return
	 */
	public static boolean addRate(String name, String description, Integer clusterid,
			String ptype, Integer cpu, Integer memory, Integer harddisk, Integer bandwidth,
			String hourPrice, String dayPrice, String monthPrice, String yearPrice) {
		JSONObject obj = null;
		String result = "fail";
		String action = Constants.BILLING_SERVER_ADDRATE;
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> nameList = new ArrayList<String>();
		nameList.add(name);
		params.put(Constants.BILLING_NAME, nameList);
		List<String> descriptionList = new ArrayList<String>();
		descriptionList.add(description);
		params.put(Constants.BILLING_DESCRIPTION, descriptionList);
		List<String> cpuList = new ArrayList<String>();
		cpuList.add(cpu.toString());
		params.put(Constants.BILLING_CPU, cpuList);
		List<String> memoryList = new ArrayList<String>();
		memoryList.add(memory.toString());
		params.put(Constants.BILLING_MEM, memoryList);
		List<String> harddiskList = new ArrayList<String>();
		harddiskList.add(harddisk.toString());
		params.put(Constants.BILLING_HD, harddiskList);
		List<String> bandwidthList = new ArrayList<String>();
		bandwidthList.add(bandwidth.toString());
		params.put(Constants.BILLING_BW, bandwidthList);
		List<String> hourPriceList = new ArrayList<String>();
		hourPriceList.add(hourPrice);
		params.put(Constants.BILLING_HOURPRICE, hourPriceList);
		List<String> dayPriceList = new ArrayList<String>();
		dayPriceList.add(dayPrice);
		params.put(Constants.BILLING_DAYPRICE, dayPriceList);
		List<String> monthPriceList = new ArrayList<String>();
		monthPriceList.add(monthPrice);
		params.put(Constants.BILLING_MONTHPRICE, monthPriceList);
		List<String> yearPriceList = new ArrayList<String>();
		yearPriceList.add(yearPrice);
		params.put(Constants.BILLING_YEARPRICE, yearPriceList);
		List<String> clusteridList = new ArrayList<String>();
		clusteridList.add(clusterid.toString());
		params.put(Constants.BILLING_CLUSTERID, clusteridList);
		List<String> ptypeList = new ArrayList<String>();
		ptypeList.add(ptype);
		params.put(Constants.BILLING_PTYPE, ptypeList);
		
		try {
			obj = getTargetValue(action, params);
			logger.info("addrate:"+obj);
			if(obj != null && obj.has(Constants.BILLING_RESULT)){
				result = obj.getString(Constants.BILLING_RESULT);
				if(result.equalsIgnoreCase("success")){
					logger.info("增加计费规则成功");
					return true;
				}
				logger.info(obj.getString(Constants.BILLING_MESSAGE));
			}
			logger.info(Constants.BILLING_RESULT+"="+result);
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
		}
		
		return false;
	}

	/**
	 * 获取计费规则
	 * @param item
	 * @param clusterid
	 * @param cpu
	 * @param memory
	 * @param harddisk
	 * @param bandwidth
	 * @return
	 */
	public static List<Billingrate> getRate(String item, Integer clusterid,
			Integer cpu, Integer memory, Integer harddisk, Integer bandwidth) {
		String action = Constants.BILLING_SERVER_GETRATE;
		JSONObject obj = null;
		String result = "fail";
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> itemList = new ArrayList<String>();
		itemList.add(item);
		params.put(Constants.BILLING_ITEM, itemList);
		List<String> cpuList = new ArrayList<String>();
		cpuList.add(cpu.toString());
		params.put(Constants.BILLING_CPU, cpuList);
		List<String> memoryList = new ArrayList<String>();
		memoryList.add(memory.toString());
		params.put(Constants.BILLING_MEM, memoryList);
		List<String> harddiskList = new ArrayList<String>();
		harddiskList.add(harddisk.toString());
		params.put(Constants.BILLING_HD, harddiskList);
		List<String> bandwidthList = new ArrayList<String>();
		bandwidthList.add(bandwidth.toString());
		params.put(Constants.BILLING_BW, bandwidthList);
		List<String> clusteridList = new ArrayList<String>();
		clusteridList.add(clusterid.toString());
		params.put(Constants.BILLING_CLUSTERID, clusteridList);
		try {
			obj = getTargetValue(action, params);
			logger.info("getrate:"+obj);
			if(obj != null && obj.has(Constants.BILLING_RESULT)){
				result = obj.getString(Constants.BILLING_RESULT);
				if(result.equalsIgnoreCase("success")){
					String billingrateString = obj.getString(Constants.BILLING_BILLINGRATES);
					List<Billingrate> billingrates = new ArrayList<Billingrate>();
					JSONArray array = new JSONArray(billingrateString);
					for(int j=0;j<array.length();j++){
						JSONObject o = array.getJSONObject(j);
						Billingrate br = new Billingrate(o);
						billingrates.add(br);
					}
					logger.info("获取计费规则成功");
					return billingrates;
				}
				logger.info(obj.getString(Constants.BILLING_MESSAGE));
			}
			logger.info(Constants.BILLING_RESULT+"="+result);
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
		}
		
		return Collections.emptyList();
	}

	/**
	 * 修改计费规则
	 * @param id
	 * @param name
	 * @param description
	 * @param clusterid
	 * @param cpu
	 * @param memory
	 * @param harddisk
	 * @param bandwidth
	 * @param hourPrice
	 * @param dayPrice
	 * @param monthPrice
	 * @param yearPrice
	 * @return
	 */
	public static boolean updateRate(Integer id, String name, String description, Integer clusterid,
			String ptype, Integer cpu, Integer memory, Integer harddisk, Integer bandwidth,
			String hourPrice, String dayPrice, String monthPrice, String yearPrice) {
		JSONObject obj = null;
		String result = "fail";
		String action = Constants.BILLING_SERVER_UPDATERATE;
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> idList = new ArrayList<String>();
		idList.add(id.toString());
		params.put(Constants.BILLING_ID, idList);
		List<String> nameList = new ArrayList<String>();
		nameList.add(name);
		params.put(Constants.BILLING_NAME, nameList);
		List<String> descriptionList = new ArrayList<String>();
		descriptionList.add(description);
		params.put(Constants.BILLING_DESCRIPTION, descriptionList);
		List<String> cpuList = new ArrayList<String>();
		cpuList.add(cpu.toString());
		params.put(Constants.BILLING_CPU, cpuList);
		List<String> memoryList = new ArrayList<String>();
		memoryList.add(memory.toString());
		params.put(Constants.BILLING_MEM, memoryList);
		List<String> harddiskList = new ArrayList<String>();
		harddiskList.add(harddisk.toString());
		params.put(Constants.BILLING_HD, harddiskList);
		List<String> bandwidthList = new ArrayList<String>();
		bandwidthList.add(bandwidth.toString());
		params.put(Constants.BILLING_BW, bandwidthList);
		List<String> hourPriceList = new ArrayList<String>();
		hourPriceList.add(hourPrice);
		params.put(Constants.BILLING_HOURPRICE, hourPriceList);
		List<String> dayPriceList = new ArrayList<String>();
		dayPriceList.add(dayPrice);
		params.put(Constants.BILLING_DAYPRICE, dayPriceList);
		List<String> monthPriceList = new ArrayList<String>();
		monthPriceList.add(monthPrice);
		params.put(Constants.BILLING_MONTHPRICE, monthPriceList);
		List<String> yearPriceList = new ArrayList<String>();
		yearPriceList.add(yearPrice);
		params.put(Constants.BILLING_YEARPRICE, yearPriceList);
		List<String> clusteridList = new ArrayList<String>();
		clusteridList.add(clusterid.toString());
		params.put(Constants.BILLING_CLUSTERID, clusteridList);
		List<String> ptypeList = new ArrayList<String>();
		ptypeList.add(ptype);
		params.put(Constants.BILLING_PTYPE, ptypeList);
		
		try {
			obj = getTargetValue(action, params);
			logger.info("updaterate:"+obj);
			if(obj != null && obj.has(Constants.BILLING_RESULT)){
				result = obj.getString(Constants.BILLING_RESULT);
				if(result.equalsIgnoreCase("success")){
					logger.info("修改计费规则成功");
					return true;
				}
				logger.info(obj.getString(Constants.BILLING_MESSAGE));
			}
			logger.info(Constants.BILLING_RESULT+"="+result);
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
		}
		
		return false;
	}

	/**
	 * 删除计费规则列表
	 * @param id
	 * @return
	 */
	public static boolean delRate(Integer id) {
		JSONObject obj = null;
		String result = "fail";
		String action = Constants.BILLING_SERVER_DELRATE;
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> idList = new ArrayList<String>();
		idList.add(id.toString());
		params.put(Constants.BILLING_ID, idList);
		
		try {
			obj = getTargetValue(action, params);
			logger.info("delrate:"+obj);
			if(obj != null && obj.has(Constants.BILLING_RESULT)){
				result = obj.getString(Constants.BILLING_RESULT);
				if(result.equalsIgnoreCase("success")){
					logger.info("删除计费规则成功");
					return true;
				}
				logger.info(obj.getString(Constants.BILLING_MESSAGE));
			}
			logger.info(Constants.BILLING_RESULT+"="+result);
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
		}
		
		return false;
	}
	/**
	 * 获取价格
	 * @param item  =  cpu/mem/hd/bw/vmpackage/flavor/charge/vm/all
	 * @param clusterid  集群
	 * @param cpu
	 * @param memory
	 * @param harddisk
	 * @param bandwidth
	 * @param uid
	 * @param payment 1为年，2为月，3为日，4为按需
	 * @param count
	 * @return
	 */
	public static Integer getPrice(String item, Integer clusterid,
			Integer cpu, Integer memory, Integer harddisk, Integer bandwidth,
			Integer uid, Integer payment, Double count) {
		logger.info("getPrice: " + item);
		String action = Constants.BILLING_SERVER_GETPRICE;
		JSONObject obj = null;
		String result = "fail";
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> itemList = new ArrayList<String>();
		itemList.add(item);
		params.put(Constants.BILLING_ITEM, itemList);
		List<String> cpuList = new ArrayList<String>();
		cpuList.add(cpu.toString());
		params.put(Constants.BILLING_CPU, cpuList);
		List<String> memoryList = new ArrayList<String>();
		memoryList.add(memory.toString());
		params.put(Constants.BILLING_MEM, memoryList);
		List<String> harddiskList = new ArrayList<String>();
		harddiskList.add(harddisk.toString());
		params.put(Constants.BILLING_HD, harddiskList);
		List<String> bandwidthList = new ArrayList<String>();
		bandwidthList.add(bandwidth.toString());
		params.put(Constants.BILLING_BW, bandwidthList);
		List<String> clusteridList = new ArrayList<String>();
		clusteridList.add(clusterid.toString());
		params.put(Constants.BILLING_CLUSTERID, clusteridList);
		List<String> paymentList = new ArrayList<String>();
		paymentList.add(payment.toString());
		params.put(Constants.BILLING_PAYMENT, paymentList);
		List<String> uidList = new ArrayList<String>();
		uidList.add(uid.toString());
		params.put(Constants.BILLING_UID, uidList);
		List<String> countList = new ArrayList<String>();
		countList.add(count.toString());
		params.put(Constants.BILLING_COUNT, countList);
		try {
			obj = getTargetValue(action, params);
			logger.info("getprice:"+obj);
			if(obj != null && obj.has(Constants.BILLING_RESULT)){
				result = obj.getString(Constants.BILLING_RESULT);
				if(result.equalsIgnoreCase("success")){
					Integer price = Integer.parseInt(obj.getString(Constants.BILLING_PRICE));
					logger.info("获取价格成功"+price);
					return price;
				}
				logger.info(obj.getString(Constants.BILLING_MESSAGE));
			}
			logger.info(Constants.BILLING_RESULT+"="+result);
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
		}
		
		return -1;
	}
	
	/**
	 * 扣费接口
	 * @param uid
	 * @param recid  uid给recid费用，uid为用户id,recid为系统id
	 * @param ptype  
	 * @param pname
	 * @param pid
	 * @param payment_type(按需、包日、包月、包年）
	 * @param times
	 * @param count(购买的份数)
	 * @param amount(退款金额)
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String pay (Integer uid, Integer recid, String name, String payment_type,  
		Integer times, double count, String ptype, String reason, Integer amount, 
		String accessToken) throws UnsupportedEncodingException {

		
		String param = Constants.BILLING_SERVER_PAY+"?uid="+uid+"&recid="+recid+
				"&name="+java.net.URLEncoder.encode(name,"utf8") +"&payment_type="+java.net.URLEncoder.encode(payment_type,"utf8")+
				"&times="+times+"&count="+count+"&product_type="+ptype+
				"&reason="+java.net.URLEncoder.encode(reason,"utf8")+"&amount="+amount;
		if(accessToken == null) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("uid", uid.toString());
			paramMap.put("recid", recid.toString());
			paramMap.put("name", /*name*/java.net.URLEncoder.encode(name,"utf8"));
			paramMap.put("payment_type", /*payment_type*/java.net.URLEncoder.encode(payment_type,"utf8"));
			paramMap.put("times", times.toString());
			paramMap.put("count", count+"");
			paramMap.put("product_type", /*ptype*/java.net.URLEncoder.encode(ptype,"utf8"));
			paramMap.put("reason", /*reason*/java.net.URLEncoder.encode(reason,"utf8"));
			paramMap.put("amount", amount.toString());
			paramMap.put("source", Constants.CLIENT_ID);
			String signature = AccountUtil.getSignature(paramMap, Constants.CLIENT_SECRET_KEY);
			param = Constants.BILLING_SERVER_PAY+"?amount="+amount
					+"&count="+count+"&name="+java.net.URLEncoder.encode(name,"utf8")
					+"&payment_type="+java.net.URLEncoder.encode(payment_type,"utf8")+
					"&product_type="+java.net.URLEncoder.encode(ptype,"utf8")+"&reason="+java.net.URLEncoder.encode(reason,"utf8")+
					"&recid="+recid+"&source="+Constants.CLIENT_ID+"&times="+times+"&uid="+uid
					+"&signature="+signature;
		} else {
			param += "&access_token="+accessToken;
		}
		logger.info("param: "+param);
		
		String result = null;
		String message = "";
		String status = "fail";
		int i = 0;//获取result，共测试MAX_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) &&
				i <= Constants.MAX_ATTEMPTS) {
			result = HttpCrawler.getHtmlDoc(param, null);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		
		try {
			JSONObject obj = new JSONObject(result);
			if(obj != null && obj.has("result") && obj.has("message")){
				status = obj.getString("result");
				message = obj.getString("message");
				logger.info("status:"+status);
				logger.info("message:"+message);
			}
		} catch (NullPointerException npe) {
			logger.error("billing/pay : return result null");
			return "CONNECT_ERROR";
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
			return "fail";
		}
		
		if(status.equalsIgnoreCase("success")){
			logger.info("扣费成功");
			return "success";
		}else if(status.equalsIgnoreCase("fail")){
			return message;
		}
		return "fail";
	}

	/**
	 * 充值接口，amount为正整数
	 * @param uid
	 * @param amount
	 * @return
	 */
	public static boolean recharge (Integer uid, Integer amount){
		String param = Constants.BILLING_SERVER_RECHARGE+"?uid="+uid+"&amount="+amount+"&source="+Constants.CLIENT_ID;
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("uid", uid+"");
		paraMap.put("amount", amount.toString());
		paraMap.put("source", Constants.CLIENT_ID);
		String signature = AccountUtil.getSignature(paraMap, Constants.CLIENT_SECRET_KEY);
		param += "&signature="+signature;
		logger.info("param:"+param);
		
		String result = null;
		String status = "fail";
		int i = 0;//获取result，共测试MAX_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) &&
				i <= Constants.MAX_ATTEMPTS) {
			result = HttpCrawler.getHtmlDoc(param, null);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		
		try {
			JSONObject obj = new JSONObject(result);
			if(obj != null && obj.has("result")){
				status = obj.getString("result");
				logger.info("status:"+status);
			}
		} catch (NullPointerException npe) {
			logger.error("billing/recharge : return result null");
			return false;
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
			return false;
		}
		
		if(status.equalsIgnoreCase("success")){
			logger.info("充值成功");
			return true;
		}
		
		return false;
	}
	
	/**
	 * 清空账户余额接口
	 * @param operator 操作人员ID
	 * @param uid 用户ID
	 */
	public static boolean cleanBalance(Integer operator, Integer uid) {
		String param = Constants.BILLING_SERVER_RESET+"?uid="+operator+"&recid="+uid+"&source="+Constants.CLIENT_ID;
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("uid", operator.toString());
		paraMap.put("recid", uid.toString());
		paraMap.put("source", Constants.CLIENT_ID);
		String signature = AccountUtil.getSignature(paraMap, Constants.CLIENT_SECRET_KEY);
		param += "&signature="+signature;
		logger.info("param:"+param);
		
		String result = null;
		String status = "fail";
		int i = 0;//获取result，共测试MAX_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) &&
				i <= Constants.MAX_ATTEMPTS) {
			result = HttpCrawler.getHtmlDoc(param, null);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		
		try {
			JSONObject obj = new JSONObject(result);
			if(obj != null && obj.has("result")){
				status = obj.getString("result");
				logger.info("status:"+status);
			}
		} catch (NullPointerException npe) {
			logger.error("billing/reset : return result null");
			return false;
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
			return false;
		}
		
		if(status.equalsIgnoreCase("success")){
			logger.info("清空成功");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 显示余额
	 * @param uid
	 * @return
	 */
	public static Integer balance(Integer uid, String accessToken, String signature, String source){
	
		String param = Constants.BILLING_SERVER_BALANCE+"?uid="+uid;
		logger.info("初始param:" + param);
		if(accessToken != null && !accessToken.equals("")) {
			param = param + "&access_token=" + accessToken;
			logger.info("access_token:" + accessToken);
		} else if(signature != null && !signature.equals("")) {
			param = param + "&source=" + source + "&signature=" + signature;
			logger.info("signature:" + signature);
		}
		logger.info("param " + param);

		
		String result = null;
		String status = "fail";
		Integer balance = -1;
		int i = 0;//获取result，共测试MAX_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) &&
				i <= Constants.MAX_ATTEMPTS) {
			result = HttpCrawler.getHtmlDoc(param, null);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		
		try {
			JSONObject obj = new JSONObject(result);
			logger.info(obj);
			if(obj != null && obj.has("result") && obj.has("balance")){
				status = obj.getString("result");
				balance = obj.getInt("balance");
				logger.info("balance:"+balance);
				logger.info("status:"+status);
			}
		} catch (NullPointerException npe) {
			logger.error("billing/balance : return result null");
			return balance;
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
			return balance;
		}
		
		if(status.equalsIgnoreCase("success")){
			logger.info("获取余额成功");
			return balance;
		}
		
		return balance;
	}
	
	
	public static HistoryByPage getBillingHistoriest(String ptype, String billingType,
			String starttime, String endtime, int uid, int page, int pageSize) throws UnsupportedEncodingException {
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("uid", String.valueOf(uid));
		params.put("product_type", ptype);
		params.put("billing_type", billingType);
		if(!starttime.equals("all"))
			params.put("begin_time", starttime);
		if(!endtime.equals("all"))
			params.put("end_time", endtime);
		params.put("source", Constants.CLIENT_ID);
		params.put("page", String.valueOf(page));
		params.put("page_size", String.valueOf(pageSize));
		String signature = AccountUtil.getSignature(params, Constants.CLIENT_SECRET_KEY);
		
		String param = Constants.BILLING_SERVER_GETHISTORY+"?" 
				+"uid="+uid 
				+"&product_type=" + ptype
				+"&billing_type=" + billingType
				+"&source=" + Constants.CLIENT_ID
				+"&signature=" + signature
				+"&page="+page
				+"&page_size="+pageSize;
		if(!starttime.equals("all"))
			param += "&begin_time=" + java.net.URLEncoder.encode(starttime,"utf8");
		if(!endtime.equals("all"))
			param += "&end_time=" + java.net.URLEncoder.encode(endtime,"utf8");
		
		logger.info("param "+param);
		
		String result = null;
		String status = "fail";
		int total = 0;
		HistoryByPage historyByPage = new HistoryByPage();
		List<BillingHistory> billingHistories = new ArrayList<BillingHistory>();
		int i = 0;//获取result，共测试MAX_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) &&
				i <= Constants.MAX_ATTEMPTS) {
			result = HttpCrawler.getHtmlDoc(param, null);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		
		try {
			JSONObject obj = new JSONObject(result);
			logger.info(obj);
			if(obj != null && obj.has("result") && obj.has("records")){
				status = obj.getString("result");
				total = Integer.parseInt(obj.getString("total"));
				String billingHistory = obj.getString("records");
				JSONArray array = null;
				if(total == 0)
					array = new JSONArray();
				else
					array = new JSONArray(billingHistory);
				for(int j = 0; j < array.length(); j++){
					JSONObject o = array.getJSONObject(j);
					BillingHistory bh = new BillingHistory(o);
					billingHistories.add(bh);
				}
				logger.info("status:"+status);
				logger.info("total:"+total);
				logger.info("billingHistories:"+billingHistories);
			}
		} catch (NullPointerException npe) {
			logger.error("billing/gethistory : return result null");
			return null;
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
			return null;
		}
		
		if(status.equalsIgnoreCase("success")){
			historyByPage.setBillingHistories(billingHistories);
			historyByPage.setTotal(total);
			logger.info("获取交易记录成功");
			return historyByPage;
		}
		return null;
	}
	
	/**
	 * 通过email获取用户id、email全称、余额
	 * @param itemProduct
	 * @return
	 */
	public static BalanceByPage getBalances(String email, int page, int pageSize) {
		String param = Constants.BILLING_SERVER_GETBALANCES+"?email="+email
				+"&page="+page+"&pagesize="+pageSize;
		logger.info("param"+param);
		
		String result = null;
		String status = "fail";
		int total = 0;
		List<Balance> balances = new ArrayList<Balance>();
		BalanceByPage balanceByPage = new BalanceByPage();
		int i = 0;//获取result，共测试MAX_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) &&
				i <= Constants.MAX_ATTEMPTS) {
			result = HttpCrawler.getHtmlDoc(param, null);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		
		try {
			JSONObject obj = new JSONObject(result);
			if(obj != null && obj.has("result") && obj.has("balances")){
				status = obj.getString("result");
				total = Integer.parseInt(obj.getString("total"));
				String balanceString = obj.getString("balances");
				JSONArray array = new JSONArray(balanceString);
				for(int j=0;j<array.length();j++){
					JSONObject o = array.getJSONObject(j);
					Balance b = new Balance(o);
					balances.add(b);
				}
				logger.info("status:"+status);
				logger.info("total:"+total);
				logger.info("Balances:"+balances);
			}
		} catch (NullPointerException npe) {
			logger.error("billing/getbalances : return result null");
			return null;
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
			return null;
		}
		
		if(status.equalsIgnoreCase("success")){
			balanceByPage.setBalances(balances);
			balanceByPage.setTotal(total);
			logger.info("通过email获取余额成功");
			return balanceByPage;
		}
		
		return null;
	}
	
	
	//根据action和参数返回json对象
	public static JSONObject getTargetValue(String url, Map<String, List<String>> params) {
		String result = null;
		int i = 0;//获取result，共测试MAX_FREEACCOUNT_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) && i <= Constants.MAX_ATTEMPTS) {
			logger.info(url+";"+params);
			result = HttpCrawler.getHtmlDoc(url, params);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		try {
			return new JSONObject(result);
		} catch (JSONException e) {
			logger.warn(url+"执行getTargetValue时Exception occurs", e);
			return new JSONObject();
		}
	}
}
