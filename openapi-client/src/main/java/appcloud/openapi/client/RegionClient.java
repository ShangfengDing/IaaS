package appcloud.openapi.client;

import appcloud.core.sdk.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.profile.DefaultProfile;
import appcloud.core.sdk.profile.YhaiClientProfile;
import appcloud.core.sdk.utils.DefaultYhaiClient;
import appcloud.core.sdk.utils.YhaiClient;
import appcloud.iaas.sdk.region.DescribeRegionRequest;
import appcloud.iaas.sdk.region.DescribeZoneRequest;
import appcloud.openapi.response.DescribeRegionsResponse;
import appcloud.openapi.response.DescribeZonesResponse;

public class RegionClient {
	
	private static Logger logger = Logger.getLogger(RegionClient.class);
	
	public DescribeRegionsResponse DescribeRegions(String appkeyId, String appkeySecret, String userEmail) {
		DescribeRegionRequest regionRequest = new DescribeRegionRequest();
		regionRequest.setRegionId(Constants.REGION_ID);
		regionRequest.setZoneId(Constants.ZONE_ID);
		if (! StringUtils.isEmpty(userEmail)) {
			regionRequest.setUserEmail(userEmail);
		}
		
		regionRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(Constants.REGION_ID, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeRegionsResponse response = null;
		try {
 			response = client.getYhaiResponse(regionRequest, DescribeRegionsResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new DescribeRegionsResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new DescribeRegionsResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public DescribeZonesResponse DescribeZones(String regionId, String appkeyId, String appkeySecret, String userEmail) {
		DescribeZoneRequest zoneRequest = new DescribeZoneRequest();
		
		zoneRequest.setRegionId(regionId);
		zoneRequest.setZoneId(Constants.ZONE_ID);
		if (! StringUtils.isEmpty(userEmail)) {
			zoneRequest.setUserEmail(userEmail);
		}
		
		zoneRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeZonesResponse response = null;
		try {
			response = client.getYhaiResponse(zoneRequest, DescribeZonesResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new DescribeZonesResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new DescribeZonesResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
}
