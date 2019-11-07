package aliyun.openapi.client;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceTypeFamiliesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceTypeFamiliesResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceTypesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceTypesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliCommonClient {
	private static Logger logger = Logger.getLogger(AliCommonClient.class);
	
	public DescribeInstanceTypesResponse DescribeInstanceTypes (String regionId, String instanceTypeFamily,String appkeyId,String appkeySecret,String userEmail)
	{
		DescribeInstanceTypesRequest describeInstanceTypesRequest = new DescribeInstanceTypesRequest();
		if(!StringUtils.isEmpty(instanceTypeFamily)) {
			describeInstanceTypesRequest.setInstanceTypeFamily(instanceTypeFamily);
		}
		describeInstanceTypesRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeInstanceTypesResponse response = null;
		try {
			response = client.getAcsResponse(describeInstanceTypesRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse();
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			response = new DescribeInstanceTypesResponse();
		}
		return response;
	}
	
	public DescribeInstanceTypeFamiliesResponse DescribeInstanceTypeFamilies (String regionId,String generation,String appkeyId,String appkeySecret,String userEmail)
	{
		DescribeInstanceTypeFamiliesRequest describeInstanceTypesRequest = new DescribeInstanceTypeFamiliesRequest();
		describeInstanceTypesRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeInstanceTypeFamiliesResponse response = null;
		try {
			response = client.getAcsResponse(describeInstanceTypesRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			response = new DescribeInstanceTypeFamiliesResponse();
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			response = new DescribeInstanceTypeFamiliesResponse();
		}
		return response;
	}
}
