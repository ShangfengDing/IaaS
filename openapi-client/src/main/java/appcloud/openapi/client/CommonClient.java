package appcloud.openapi.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.profile.DefaultProfile;
import appcloud.core.sdk.profile.YhaiClientProfile;
import appcloud.core.sdk.utils.DefaultYhaiClient;
import appcloud.core.sdk.utils.YhaiClient;
import appcloud.iaas.sdk.other.*;
import appcloud.openapi.response.DescribeInstanceTypesResponse;
import appcloud.openapi.response.GainAppkeyPairResponse;

public class CommonClient {
	public static Logger logger = Logger.getLogger(CommonClient.class);
	
	public DescribeInstanceTypesResponse DescribeInstanceTypes (String regionId,String zoneId, String appkeyId,String appkeySecret,String userEmail)
	{
		DescribeInstanceTypesRequest describeInstanceTypesRequest = new DescribeInstanceTypesRequest();
		describeInstanceTypesRequest.setRegionId(regionId);
		if (!StringUtils.isEmpty(zoneId)) {
			describeInstanceTypesRequest.setZoneId(zoneId);
		}
		if (!StringUtils.isEmpty(userEmail)) 
			describeInstanceTypesRequest.setUserEmail(userEmail);
		describeInstanceTypesRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeInstanceTypesResponse response = null;
		try {
			response = client.getYhaiResponse(describeInstanceTypesRequest, DescribeInstanceTypesResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	public DescribeInstanceTypesResponse DescribeDiskTypes (String regionId, String zoneId, String appkeyId,String appkeySecret,String userEmail)
	{
		DescribeDiskTypesRequest describeDiskTypesRequest = new DescribeDiskTypesRequest();
		describeDiskTypesRequest.setRegionId(regionId);
		if (!StringUtils.isEmpty(zoneId)) {
			describeDiskTypesRequest.setZoneId(zoneId);
		}
		if (!StringUtils.isEmpty(userEmail)) 
			describeDiskTypesRequest.setUserEmail(userEmail);
		describeDiskTypesRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeInstanceTypesResponse response = null;
		try {
			response = client.getYhaiResponse(describeDiskTypesRequest, DescribeInstanceTypesResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	public DescribeInstanceTypesResponse DescribeInternetTypes (String regionId, String zoneId, String appkeyId,String appkeySecret,String userEmail)
	{
		DescribeInternetTypesRequest describeInternetTypesRequest = new DescribeInternetTypesRequest();
		describeInternetTypesRequest.setRegionId(regionId);
		describeInternetTypesRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(userEmail)) 
			describeInternetTypesRequest.setUserEmail(userEmail);
		describeInternetTypesRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeInstanceTypesResponse response = null;
		try {
			response = client.getYhaiResponse(describeInternetTypesRequest, DescribeInstanceTypesResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	public DescribeInstanceTypesResponse DescribeCpuTypes (String regionId, String zoneId, String appkeyId,String appkeySecret,String userEmail)
	{
		DescribeCpuTypesRequest describeCpuTypesRequest = new DescribeCpuTypesRequest();
		describeCpuTypesRequest.setRegionId(regionId);
		describeCpuTypesRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(userEmail)) 
			describeCpuTypesRequest.setUserEmail(userEmail);
		describeCpuTypesRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeInstanceTypesResponse response = null;
		try {
			response = client.getYhaiResponse(describeCpuTypesRequest, DescribeInstanceTypesResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	public DescribeInstanceTypesResponse DescribeMemoryTypes (String regionId, String zoneId,String appkeyId,String appkeySecret,String userEmail)
	{
		DescribeMemoryTypesRequest describeMemoryTypesRequest = new DescribeMemoryTypesRequest();
		describeMemoryTypesRequest.setRegionId(regionId);
		describeMemoryTypesRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(userEmail)) 
			describeMemoryTypesRequest.setUserEmail(userEmail);
		describeMemoryTypesRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeInstanceTypesResponse response = null;
		try {
			response = client.getYhaiResponse(describeMemoryTypesRequest, DescribeInstanceTypesResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	public GainAppkeyPairResponse GainAppkeyPair(String regionId,String zoneId, String accessToken,String userEmail)
	{
		GainAppkeyPairRequest gainAppkeyPairRequest = new GainAppkeyPairRequest();
		gainAppkeyPairRequest.setAccessToken(accessToken);
		if (!StringUtils.isEmpty(userEmail)) 
			gainAppkeyPairRequest.setUserEmail(userEmail);
		gainAppkeyPairRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId,zoneId);
		YhaiClient client = new DefaultYhaiClient(profile);
		GainAppkeyPairResponse response = null;
		try {
			response = client.getYhaiResponse(gainAppkeyPairRequest, GainAppkeyPairResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new GainAppkeyPairResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new GainAppkeyPairResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
//	public static void main(String[] args) {
//		String regionId = "beijing";
////		String accessToken = "a52b1ee93e83448181af2868a3c4fb1c";
////		String userEmail = "free@free4lab.com";
//		String appkeyId = "f00577d2c1764688be5e483a21f4fe92";
//		String appkeySecret = "1KS2AMYX0UEjvQA6sVYK1AXfRpvrtArQ";
////		String instanceId = "11af8257cdb9440491098c9321bb6ce9";
//	    String userEmail = "free@free4lab.com";
//		//System.out.println(new CommonClient().GainAppkeyPair(regionId,accessToken, userEmail).getAppkeyId());
////	    System.out.println(new CommonClient().DescribeInstanceTypes(regionId, appkeyId, appkeySecret, userEmail).getInstanceTypes().getInstanceTypeItems().get(1).getCpuCoreCount());
//	}
}
