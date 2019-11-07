package com.appcloud.vm.fe.users;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.appcloud.vm.fe.common.Constants;
import com.free4lab.utils.account.AccountUtil;
import com.free4lab.utils.http.HttpCrawler;

public class UsersAPI {
	private static final Logger logger = Logger.getLogger(UsersAPI.class);
	
	public static Integer getUidByEmail (String email){
		String param = Constants.USERS_SERVER_GETUIDBYEMAIL+"?email="+email+"&source="+Constants.CLIENT_ID+"&signature=";
		Map<String, String > params = new HashMap<String, String>();
		params.put("email", email);
		params.put("source", Constants.CLIENT_ID);
		String signature = AccountUtil.getSignature(params, Constants.CLIENT_SECRET_KEY);
		param += signature;
		logger.info(param);
		
		String result = null;
		String status = "";
		int uid = -1;
		int i = 0;//获取result，共测试MAX_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) &&
				i <= Constants.MAX_ATTEMPTS) {
			result = HttpCrawler.getHtmlDoc(param, null);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		
		try {
			JSONObject obj = new JSONObject(result);
			if(obj != null && obj.has("message")){
				status = obj.getString("message");
				logger.info("status:"+status);
				uid = obj.getInt("uid");
			}
		} catch (NullPointerException npe) {
			logger.error("users/getUidByEmail : return result null");
			return -1;
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
			return -1;
		}
		
		if(!status.equalsIgnoreCase("null")){
			logger.info("查询成功");
			return uid;
		}
		
		return -1;
	}
}
