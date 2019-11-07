package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import appcloud.openapi.check.BillingOperateCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;

@Component
public class BillingOperateCheckImpl implements BillingOperateCheck{

	private static Logger logger = Logger.getLogger(BillingOperateCheckImpl.class);
	@Override
	public Map<String, String> checkDescribeInstanceTypes(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe instance types operate ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe instance types operate successfully");
		return resultMap;
	}
	@Override
	public Map<String, String> checkDescribeDiskTypes(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe Disk types operate ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe Disk types operate successfully");
		return resultMap;
	}
	@Override
	public Map<String, String> checkDescribeInternetTypes(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe Internet types operate ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe Internet types operate successfully");
		return resultMap;
	}
	@Override
	public Map<String, String> checkDescribeCpuTypes(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe Cpu types operate ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe Cpu types operate successfully");
		return resultMap;
	}
	@Override
	public Map<String, String> checkDescribeMemoryTypes(
			Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check describe Memory types operate ." );
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check describe Memory types operate successfully");
		return resultMap;
	}
}
