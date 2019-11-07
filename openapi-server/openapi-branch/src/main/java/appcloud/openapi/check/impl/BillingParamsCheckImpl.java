package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.openapi.check.BillingParamsCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;

@Component
public class BillingParamsCheckImpl implements BillingParamsCheck{

	private static Logger logger = Logger.getLogger(BillingParamsCheckImpl.class);
	@Override
	public Map<String, String> checkDescribeInstanceTypesParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe instance types params ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe instance types params successfully");
		return resultMap;
	}
	@Override
	public Map<String, String> checkDescribeDiskTypesParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe Disk types params ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe Disk types params successfully");
		return resultMap;
	}
	@Override
	public Map<String, String> checkDescribeInternetTypesParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe Internet types params ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe Internet types params successfully");
		return resultMap;
	}
	@Override
	public Map<String, String> checkDescribeCpuTypesParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe Cpu types params ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe Cpu types params successfully");
		return resultMap;
	}
	@Override
	public Map<String, String> checkDescribeMemoryTypesParams(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe Memory types params ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe Memory types params successfully");
		return resultMap;
	}
}
