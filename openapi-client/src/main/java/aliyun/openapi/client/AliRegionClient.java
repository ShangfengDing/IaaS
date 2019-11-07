package aliyun.openapi.client;

import org.apache.log4j.Logger;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeRegionsRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeRegionsResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeZonesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeZonesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.http.*;

public class AliRegionClient {
	
	private static Logger logger = Logger.getLogger(AliRegionClient.class);
	
	public DescribeRegionsResponse DescribeRegions(String appkeyId, String appkeySecret, String userEmail) {
		DescribeRegionsRequest regionRequest = new DescribeRegionsRequest();
		
		/*if (! StringUtils.isEmpty(userEmail)) {
			regionRequest.setOwnerAccount(userEmail);
		}*/
		regionRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile("cn-qingdao",appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeRegionsResponse response = null;
		try {
			response = client.getAcsResponse(regionRequest);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeRegionsResponse();
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeRegionsResponse();
		}
		return response;
	}
	
	public DescribeZonesResponse DescribeZones(String regionId, String appkeyId, String appkeySecret, String userEmail) {
		DescribeZonesRequest zoneRequest = new DescribeZonesRequest();
		
		zoneRequest.setRegionId(regionId);
		
		zoneRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile("cn-qingdao",appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeZonesResponse response = null;
		try {
			response = client.getAcsResponse(zoneRequest);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeZonesResponse();
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeZonesResponse();
		}
		return response;
	}
}
