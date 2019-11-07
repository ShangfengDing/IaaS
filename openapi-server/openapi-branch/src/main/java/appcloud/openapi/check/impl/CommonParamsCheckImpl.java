package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.free4lab.utils.account.AccountUtil;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.check.CommonParamsCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;

/**
 *	此类用于接口中公共参数的检查
 */
@Component
public class CommonParamsCheckImpl implements CommonParamsCheck{

	private static Logger logger = Logger.getLogger(CommonParamsCheckImpl.class);
	private static CommonParamsCheckImpl commonParamsCheck = new CommonParamsCheckImpl();
	public static CommonParamsCheckImpl getInstance() {
		return commonParamsCheck;
	}
	private CommonGenerator commonGenerator = new CommonGenerator();
	private VmUserProxy vmUserProxy;
	private CommonParamsCheckImpl() {
		super();
		vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
				VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	public Map<String, String> checkCommonParams(
			Map<String, String> commonParamsMap) throws Exception {
		// TODO Auto-generated method stub
		logger.info("check common params, Format=" + commonParamsMap.get(Constants.FORMAT) +
				"; Version=" + commonParamsMap.get(Constants.VERSION) +
				"; AppKeyId=" + commonParamsMap.get(Constants.APPKEY_ID) + 
				"; Signature=" + commonParamsMap.get(Constants.SIGNATURE) +
				"; Timestamp=" + commonParamsMap.get(Constants.TIMESTAMP) +	
				"; UserEmail=" + commonParamsMap.get(Constants.USER_EMAIL) );
		//check format
		String format = commonParamsMap.get(Constants.FORMAT);
		if(null!=format) {
			boolean isvalid = false;
			for(FormatType item : FormatType.values()) {
				logger.info("item = " + item);
				if(format.equals(item.toString())) {
					isvalid = true;
					break;
				}
			}
			if(!isvalid) {
				return commonGenerator.inValid(null, Constants.FORMAT);
			}
		}
		//check version
		if(null==commonParamsMap.get(Constants.VERSION)) {
			return commonGenerator.missing(null, Constants.VERSION);
		}else {
			if(commonParamsMap.get(Constants.VERSION).equals("") ) {
				return commonGenerator.inValid(null, Constants.VERSION);
			}else if( !commonParamsMap.get(Constants.VERSION).equals(Constants.OPENAPI_VERSION)) {
				return commonGenerator.notFound(null, Constants.VERSION);
			}
		}
		//check appkeyId
		if(null==commonParamsMap.get(Constants.APPKEY_ID)) {
			return commonGenerator.missing(null, Constants.APPKEY_ID);
		}else {
			if( commonParamsMap.get(Constants.APPKEY_ID).equals("") ) {
				return commonGenerator.inValid(null, Constants.APPKEY_ID);
			}else if( null==vmUserProxy.getByAppKeyId(commonParamsMap.get(Constants.APPKEY_ID))) {
				return commonGenerator.operationDeny(null, Constants.APPKEY_ID);
			}
		}
		//check signature
		if(null==commonParamsMap.get(Constants.SIGNATURE)) {
			return commonGenerator.missing(null, Constants.SIGNATURE);
		}else if(commonParamsMap.get(Constants.SIGNATURE).equals("")) {
			return commonGenerator.inValid(null, Constants.SIGNATURE);
		}
		//check timestamp
		if(!commonParamsMap.containsKey(Constants.TIMESTAMP)) {
			return commonGenerator.missing(null, Constants.TIMESTAMP);
		}else if(!Pattern.matches("\\d+", commonParamsMap.get(Constants.TIMESTAMP)) ) {
				return commonGenerator.inValid(null, Constants.TIMESTAMP);
		}
		//check userEmail
		if(null!=commonParamsMap.get(Constants.USER_EMAIL)) {
			if(!Pattern.matches("^[\\w]+([-_\\.][\\w]+)*@([\\w]+[-\\.])*[\\w]+\\.[\\w]+$", commonParamsMap.get(Constants.USER_EMAIL))) {
				return commonGenerator.inValid(null, Constants.USER_EMAIL);
			}/*else if( !vmUserProxy.getByAppKeyId(commonParamsMap.get(Constants.APPKEY_ID)).getUserEmail().equalsIgnoreCase(commonParamsMap.get(Constants.USER_EMAIL))) {
				return commonGenerator.operationDeny(null, Constants.USER_EMAIL);
			}*/
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check common params successfully!");
		return resultMap;
	}

	public Map<String, String> checkGainAppkeyPairParams(
			Map<String, String> paramsMap) throws Exception {
		// TODO Auto-generated method stub
		logger.info("check gainAppkeyId  params, Version=" + paramsMap.get(Constants.VERSION) +
				"; AccesssToken=" + paramsMap.get(Constants.ACCESS_TOKEN) );
		if(null==paramsMap.get(Constants.VERSION)) {
			return commonGenerator.missing(null,Constants.VERSION);
		}else {
			if(paramsMap.get(Constants.VERSION).equalsIgnoreCase("")) {
				return commonGenerator.inValid(null, Constants.VERSION);
			}else if( !(paramsMap.get(Constants.VERSION)).equals(Constants.OPENAPI_VERSION)) {
				return commonGenerator.notFound(null, Constants.VERSION);
			}
		}
		if(null==paramsMap.get(Constants.ACCESS_TOKEN)) {
			return commonGenerator.missing(null, Constants.ACCESS_TOKEN);
		}else {
			if(paramsMap.get(Constants.ACCESS_TOKEN).equalsIgnoreCase("")) {
				return commonGenerator.inValid(null, Constants.ACCESS_TOKEN);
			}else if(Long.parseLong(AccountUtil.getAccessTokenInfo(paramsMap.get(Constants.ACCESS_TOKEN)))<0 ) {
				return commonGenerator.operationDeny(null, Constants.ACCESS_TOKEN);
			}
		}
		if(! StringUtils.isEmpty(paramsMap.get(Constants.USER_EMAIL))) {
			JSONObject account = AccountUtil.getUserByAccessToken(paramsMap.get(Constants.ACCESS_TOKEN));
			if(null != account && !(account.getString(Constants.ACCOUNT_EMAIL)).equalsIgnoreCase(paramsMap.get(Constants.USER_EMAIL)) ) {
				return commonGenerator.operationDeny(null, Constants.USER_EMAIL);
			}
		}
		logger.info("Check gainAppkeyId  params successfully! ");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
}
