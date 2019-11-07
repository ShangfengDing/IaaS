package appcloud.openapi.manager.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.response.BaseResponse;

@Component
public class ConstructResponse {

	public Integer getResponseStatus(Map<String, String> resultMap) {
		if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
				resultMap.get(Constants.HTTP_CODE).equals("")) {
			return Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR);
		}else {
			return Integer.parseInt(resultMap.get(Constants.HTTP_CODE));
		}
	}

	public BaseResponse getBaseResponse(String requestId, Map<String, String> resultMap) {
		if(null==requestId || requestId.length()<1 || null==resultMap) {
			return new BaseResponse(requestId, Constants.INTERNAL_ERROR, Constants.DEFAULT_ERROR_MESSAGE);
		}
		return new BaseResponse(requestId, resultMap.get(Constants.ERRORCODE),
				resultMap.get(Constants.ERRORMESSAGE));
	}
}
