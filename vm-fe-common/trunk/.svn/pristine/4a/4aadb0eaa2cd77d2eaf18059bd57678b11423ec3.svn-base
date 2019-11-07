package com.appcloud.vm.fe.help;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.billing.BillingHistory;
import com.appcloud.vm.fe.billing.HistoryByPage;
import com.appcloud.vm.fe.common.Constants;
import com.free4lab.utils.http.HttpCrawler;

public class HelpContentAPI {
	private static final Logger logger = Logger.getLogger(HelpContentAPI.class);
	
	
	public static HelpContent getHelpContent(String uuid){
		String param =Constants.FREEHELP_URL+"queryhelpcontent?uuid="+uuid;
		String result = null;
		int i = 0;//获取result，共测试MAX_ATTEMPTS次
		while ((result == null || "".equalsIgnoreCase(result)) &&
				i <= Constants.MAX_ATTEMPTS) {
			result = HttpCrawler.getHtmlDoc(param, null);
			logger.info("第"+i+"次尝试:"+result);
			i++;
		}
		
		try {
			JSONObject obj = new JSONObject(result);
			if(obj != null && obj.has("helpcontents") ){
				String helpcontents = obj.getString("helpcontents");
				JSONArray array = new JSONArray(helpcontents);
				JSONObject o = array.getJSONObject(0);
				HelpContent hc = new HelpContent(o);
				return hc;
			}
		} catch (NullPointerException npe) {
			logger.error("helpcontents : return result null");
			return null;
		} catch (JSONException e) {
			logger.warn("Exception occurs", e);
			return null;
		}
		return null;
		
	}
	
}
