package appcloud.vmschduler.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class RSParamUtil {

	public static Integer getCpuWeight(String params) {
		try {
			JSONObject paramsJson = new JSONObject(params);
			return paramsJson.getInt("CPU");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Integer getMemoryWeight(String params) {
		try {
			JSONObject paramsJson = new JSONObject(params);
			return paramsJson.getInt("MEM");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
