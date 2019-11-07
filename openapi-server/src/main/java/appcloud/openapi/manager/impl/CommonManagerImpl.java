package appcloud.openapi.manager.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.CommonManager;
import appcloud.openapi.manager.util.CommonGenerator;
import appcloud.openapi.manager.util.LolHelper;
import com.free4lab.utils.account.AccountUtil;
import com.free4lab.utils.enabler.AbstractAuthentication;
/**
 *	此类用于跟认证鉴权相关的操作
 *	@author hgm
 */
@Component
public class CommonManagerImpl extends AbstractAuthentication implements CommonManager {

	private static Logger logger = Logger.getLogger(CommonManagerImpl.class);
	private static LolLogUtil loller = LolHelper.getLolLogUtil(CommonManagerImpl.class);
	private static CommonManagerImpl commonManager = new CommonManagerImpl();
	public static CommonManagerImpl getInstance(){
		return commonManager;
	}
	private static CommonGenerator commonGenerator = new CommonGenerator();
	private static VmUserProxy vmUserProxy;
	private CommonManagerImpl(){
		super();
		vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(
				VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	public Map<String, String> getAuthenticate(Map<String, String> paramsMap) throws Exception {
		//由于该函数的参数是一个对象应用，并且该函数体涉及到修改该参数，
		//为了不影响该参数应用的值，故首先将该参数里的值重新复制给一个新的对象应用
		Map<String, String> authMap = new HashMap<String, String>(paramsMap);
		String appKeyId = paramsMap.get(Constants.APPKEY_ID);
		String signature = paramsMap.get(Constants.SIGNATURE);
		Long timestamp = Long.valueOf(paramsMap.get(Constants.TIMESTAMP));
		authMap.remove(Constants.APPKEY_ID);
		authMap.remove(Constants.SIGNATURE);
		authMap.remove(Constants.TIMESTAMP);
		logger.info("start authentication : appKeyId="+ appKeyId + "; signature="+signature+
				"; timestamp="+ timestamp + "; otherParmas="+ authMap);
		
		int result = authenticate(appKeyId, signature, timestamp, authMap);
		if(1==result) {
			logger.info("The request is not authenticated successlly due to " + Constants.TIMESTAMP + ".");
			return commonGenerator.inValid(null, Constants.TIMESTAMP);
		}else if(2==result) {
			logger.info("The request is not authenticated successlly due to " + Constants.APPKEY_ID + ".");
			return commonGenerator.inValid(null, Constants.APPKEY_ID);
		}else if(3==result) {
			logger.info("The request is not authenticated successlly due to " + Constants.SIGNATURE + ".");
			return commonGenerator.inValid(null, Constants.SIGNATURE);
		}
		logger.info("The request is authenticated successfully.");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public String getSecretKey(String appKey) {
		// TODO Auto-generated method stub
		try {
			VmUser vmUser = vmUserProxy.getByAppKeyId(appKey);
			if(null==vmUser){
				logger.warn("Get vmUser failed by appkeyId : " + appKey);
				return "";
			}
			return vmUser.getAppKeySecret();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Get vmUser failed by appkeyId : " + appKey);
			return "";
		}
	}

	@Override
	public int getTimeOut() {
		// TODO Auto-generated method stub
		return Constants.MAX_TIME_OUT;
	}

	public Map<String, String> getAppKeyPair(Map<String, String> paramsMap) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		String userId = AccountUtil.getUserByAccessToken(paramsMap.get(Constants.ACCESS_TOKEN)).getString(Constants.ACCOUNT_USER_ID);
		String message = "";
		if(null==userId || Integer.parseInt(userId)<=0) {
			message = "Get userId failed by " + Constants.ACCESS_TOKEN + " : " + paramsMap.get(Constants.ACCESS_TOKEN);
			logger.warn(message);
			return commonGenerator.internalError(null, Constants.ACCESS_TOKEN);
		}
		VmUser vmUser = vmUserProxy.getByUserId(Integer.parseInt(userId));
		if(null==vmUser) {
			message = "User " + userId + " get appkeyPair failed by "+ Constants.ACCESS_TOKEN + " : " + paramsMap.get(Constants.ACCESS_TOKEN);
			logger.warn(message);
			return commonGenerator.internalError(null, Constants.ACCESS_TOKEN);
		}
		if(null==vmUser.getAppKeyId()|| vmUser.getAppKeyId().equals("") ||
				null==vmUser.getAppKeySecret() || vmUser.getAppKeySecret().equals("")) {
			logger.info("user " + userId + " appkeyPair is null in the mysql.");
			String[] appkeyPair = getKeyPair();
			resultMap.put(Constants.APPKEY_ID, appkeyPair[0]);
			resultMap.put(Constants.APPKEY_SECRET, appkeyPair[1]);
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
			vmUser.setAppKeyId(appkeyPair[0]);
			vmUser.setAppKeySecret(appkeyPair[1]);
			vmUserProxy.update(vmUser);
			message = "Create user " + userId + " appkeyPair successfully!";
			logger.info(message);
			System.out.println(message);
			return resultMap;
		}
		resultMap.put(Constants.APPKEY_ID, vmUser.getAppKeyId());
		resultMap.put(Constants.APPKEY_SECRET, vmUser.getAppKeySecret());
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		message = "User " + userId + " get appkeyPair successfully!";
		logger.info(message);

		return resultMap;
	}
}
