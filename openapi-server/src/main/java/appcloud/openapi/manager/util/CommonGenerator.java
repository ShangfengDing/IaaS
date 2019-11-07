package appcloud.openapi.manager.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
/**
 *	The generator for the error return
 *	@author hgm
 */
@Component
public class CommonGenerator {

	private static Logger logger = Logger.getLogger(CommonGenerator.class);
	private static CommonGenerator commonGenerator = new CommonGenerator();
	public static CommonGenerator getInstance() {
		return commonGenerator;
	}
	/**
	 *	Losing parameter in the request
	 *	@param message : Error message that is returned
	 *		   param : The parameter that result in error
	 *	@return Map<String, String> : include error code, error message
	 */
	public Map<String, String> missing(String message, String param) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==message || message.equals("")) {
			message = "The input parameter '" + param +
					"' that is mandatory for processing this request is not supplied.";
		}
		if(null==param || param.equals("")) {
			param = Constants.MISSING_PARAMETER;
		}else{
			param += "." + Constants.MISSING_PARAMETER;
		}
		resultMap.put(Constants.ERRORCODE, param);
		resultMap.put(Constants.ERRORMESSAGE, message);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_BAD_REQUEST);
		logger.info("Error : " + message);
		return resultMap;
	}
	/**
	 *	The parameter value is not found 
	 *	@param message : Error message that is returned
	 *		   param : The parameter that result in error
	 *	@return Map<String, String> : include error code, error message
	 */
	public Map<String, String> notFound(String message, String param) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==message || message.equals("")) {
			message = "The "+param+" provided does not exist in our records.";
		}
		if(null==param || param.equals("")) {
			param = Constants.NOT_FOUND_PARAMETER;
		}else{
			param += "." + Constants.NOT_FOUND_PARAMETER;
		}
		resultMap.put(Constants.ERRORCODE, param);
		resultMap.put(Constants.ERRORMESSAGE, message);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_NOT_FOUND);
		logger.info("Error : " + message);
		return resultMap;
	}
	/**
	 *	The parameter value is disabled or error result in operation is denied.
	 *	@param message : Error message that is returned
	 *		   param : The parameter that result in error
	 *	@return Map<String, String> : include error code, error message
	 */
	public Map<String, String> operationDeny(String message, String param) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==message || message.equals("")) {
			message = "The specified parameter '"+param+"' is disabled or is error.";
		}
		if(null==param || param.equals("")) {
			param = Constants.OPERATIONDENIED;
		}else{
			param += "." + Constants.OPERATIONDENIED;
		}
		resultMap.put(Constants.ERRORCODE, param);
		resultMap.put(Constants.ERRORMESSAGE, message);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN);
		logger.info("Error : " + message);
		return resultMap;
	}
	/**
	 *	The parameter value is invalid or is illegal.
	 *	@param message : Error message that is returned
	 *		   param : The parameter that result in error
	 *	@return Map<String, String> : include error code, error message
	 */
	public Map<String, String> inValid(String message, String param) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==message || message.equals("")) {
			message = "The specified parameter "+param+" is invalid or is illegal.";
		}
		if(null==param || param.equals("")) {
			param = Constants.INVALID_PARAMETER;
		}else{
			param += "." + Constants.INVALID_PARAMETER;
		}
		resultMap.put(Constants.ERRORCODE, param);
		resultMap.put(Constants.ERRORMESSAGE, message);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_BAD_REQUEST);
		logger.info("Error : " + message);
		return resultMap;
	}
	
	/**
	 *	The parameter value is invalid or is illegal.Add error msg
	 *	@param message : Error message that is returned
	 *		   param : The parameter that result in error
	 *	@return Map<String, String> : include error code, error message
	 */
	public Map<String, String> inValid(String message, String param, final Map<String, String> paramsMap) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==message || message.equals("")) {
			message = "The specified parameter "+param+" is invalid or is illegal.";
		}
		logger.info("Error : " + message+" ; value is "+paramsMap.get(param));
		if(null==param || param.equals("")) {
			param = Constants.INVALID_PARAMETER;
		}else{
			param += "." + Constants.INVALID_PARAMETER;
		}
		resultMap.put(Constants.ERRORCODE, param);
		resultMap.put(Constants.ERRORMESSAGE, message);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_BAD_REQUEST);
		return resultMap;
	}
	/**
	 *	The request processing has failed due to the specified parameter of some unknown error.
	 *	@param message : Error message that is returned
	 *		   param : The parameter that result in error
	 *	@return Map<String, String> : include error code, error message
	 * @
	 */
	public Map<String, String> internalError(String message, String param) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==message || message.equals("")) {
			message = "The request processing has failed due to ";
			if(null==param || param.equals("")) {
				message += "some unknown error.";
			}else{
				message += "the specified parameter " + param + ".";
			}
		}
		if(null==param || param.equals("")) {
			param = Constants.INTERNAL_ERROR;
		}else{
			param += "." + Constants.INTERNAL_ERROR;
		}
		resultMap.put(Constants.ERRORCODE, param);
		resultMap.put(Constants.ERRORMESSAGE, message);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR);
		logger.info("Error : " + message);
		return resultMap;
		
		
	}
	/**
	 *	The specified parameter is not authorized.
	 *	@param message : Error message that is returned
	 *		   param : The parameter that result in error
	 *	@return Map<String, String> : include error code, error message
	 */
	public Map<String, String> unAuthorized(String message, String param) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==message || message.equals("")) {
			message = "The specified "+ param +" is not authorized.";
		}
		if(null==param || param.equals("")) {
			param = Constants.NOT_AUTHORIZED;
		}else{
			param += "." + Constants.NOT_AUTHORIZED;
		}
		resultMap.put(Constants.ERRORCODE, param);
		resultMap.put(Constants.ERRORMESSAGE, message);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_UNAUTHORIZED);
		logger.info("Error : " + message);
		return resultMap;
	}
	/**
	 *	User's account does not have enough balance.
	 *	@param message : Error message that is returned
	 *		   param : The parameter that result in error
	 *	@return Map<String, String> : include error code, error message
	 */
	public Map<String, String> insufficientBalance(String message, String param) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null==message || message.equals("")) {
			message = "Your account does not have enough balance.";
		}
		if(null==param || param.equals("")) {
			param = Constants.INSUFFICIENT_BALANCE;
		}else{
			param += "." + Constants.INSUFFICIENT_BALANCE;
		}
		resultMap.put(Constants.ERRORCODE, param);
		resultMap.put(Constants.ERRORMESSAGE, message);
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN);
		logger.info("Error : " + message);
		return resultMap;
	}
}
