package appcloud.openapi.resources;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import appcloud.common.util.UuidUtil;
import appcloud.openapi.check.CommonParamsCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.CommonManager;
import appcloud.openapi.manager.util.CommonGenerator;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.response.GainAppkeyPairResponse;

@RestController
public class CommonResource extends BaseController{

	@Autowired
	private CommonParamsCheck commonParamsCheck;
	@Autowired
	private CommonManager commonManager;
	@Autowired
	private CommonGenerator commonGenerator;
	
	private static Logger logger = Logger.getLogger(CommonResource.class);
	private ConstructResponse constructResponse = new ConstructResponse(); 
	/**
	 * 通过开放API获取appkeyPair
	 * @author hgm 
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET,  params="Action=GainAppkeyPair", produces = { "application/json","application/xml"})
	@ResponseBody
	public GainAppkeyPairResponse GainAppkeyPair(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.ACCESS_TOKEN) String accessToken,
			@RequestParam(Constants.USER_EMAIL) String userEmail) throws Exception {
		//检查接口参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constants.VERSION, version);
		paramsMap.put(Constants.ACCESS_TOKEN, accessToken);
		paramsMap.put(Constants.USER_EMAIL, userEmail);
		//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
		String requestId = UuidUtil.getRandomUuid();
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			resultMap = commonParamsCheck.checkGainAppkeyPairParams(paramsMap);
			logger.info(resultMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return new GainAppkeyPairResponse(requestId, 
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}
			//获取APPKey信息
			resultMap = commonManager.getAppKeyPair(paramsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				response.setStatus(constructResponse.getResponseStatus(resultMap));
				return new GainAppkeyPairResponse(requestId, 
						resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
			}
			return new GainAppkeyPairResponse(requestId, resultMap.get(Constants.APPKEY_ID),
					resultMap.get(Constants.APPKEY_SECRET), null, null);
		} catch (Exception e) {
			logger.warn("执行操作抛出异常",e);
			response.setStatus(constructResponse.getResponseStatus(null));
			return new GainAppkeyPairResponse(requestId, Constants.INTERNAL_ERROR, Constants.DEFAULT_ERROR_MESSAGE);
		}
	}
	public CommonParamsCheck getCommonParamsCheck() {
		return commonParamsCheck;
	}
	public void setCommonParamsCheck(CommonParamsCheck commonParamsCheck) {
		this.commonParamsCheck = commonParamsCheck;
	}
	public CommonGenerator getCommonGenerator() {
		return commonGenerator;
	}
	public void setCommonGenerator(CommonGenerator commonGenerator) {
		this.commonGenerator = commonGenerator;
	}
	
}
