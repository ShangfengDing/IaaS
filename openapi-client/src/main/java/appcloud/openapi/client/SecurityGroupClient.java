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
import appcloud.iaas.sdk.security.AuthorizeSecurityGroupRequest;
import appcloud.iaas.sdk.security.CreateSecurityGroupRequest;
import appcloud.iaas.sdk.security.DeleteSecurityGroupRequest;
import appcloud.iaas.sdk.security.DescribeSecurityGroupAttributeRequest;
import appcloud.iaas.sdk.security.DescribeSecurityGroupsRequest;
import appcloud.iaas.sdk.security.ModifySecurityGroupAttributeRequest;
import appcloud.iaas.sdk.security.RevokeSecurityGroupRequest;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateSecurityGroupResponse;
import appcloud.openapi.response.SecurityGroupRulesReponse;
import appcloud.openapi.response.SecurityGroupsDetailReponse;

public class SecurityGroupClient {

	private static Logger logger = Logger.getLogger(SecurityGroupClient.class);
	
	public CreateSecurityGroupResponse CreateSecurityGroup (String regionId, String zoneId,
			String securityGroupName,String description,String vpcId,
			String appkeyId,String appkeySecret,String userEmail){
		CreateSecurityGroupRequest createSecurityGroupRequest = new CreateSecurityGroupRequest();
		createSecurityGroupRequest.setRegionId(regionId);
		if (!StringUtils.isEmpty(zoneId)){
			createSecurityGroupRequest.setZoneId(zoneId);
		}
		if(!StringUtils.isEmpty(securityGroupName))
			createSecurityGroupRequest.setSecurityGroupName(securityGroupName);
		if(!StringUtils.isEmpty(description))
			createSecurityGroupRequest.setDescription(description);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(vpcId)) {
			//createSecurityGroupRequest.setVpcId(vpcId);
		}
		if (!StringUtils.isEmpty(userEmail)) 
			createSecurityGroupRequest.setUserEmail(userEmail);
		createSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		CreateSecurityGroupResponse response = null;
		try {
			response = client.getYhaiResponse(createSecurityGroupRequest, CreateSecurityGroupResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new CreateSecurityGroupResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new CreateSecurityGroupResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse DeleteSecurityGroup(String regionId, String zoneId,
			String securityGroupId,
			String appkeyId,String appkeySecret,String userEmail){
		DeleteSecurityGroupRequest deleteSecurityGroupRequest = new DeleteSecurityGroupRequest();
		deleteSecurityGroupRequest.setSecurityGroupId(securityGroupId);
		deleteSecurityGroupRequest.setRegionId(regionId);
		deleteSecurityGroupRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(userEmail)) 
			deleteSecurityGroupRequest.setUserEmail(userEmail);
		deleteSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(deleteSecurityGroupRequest, BaseResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse AuthorizeSecurityGroup(String regionId, String zoneId,
			String securityGroupId,String ipProtocol,String portRange,String sourceCidrIp,
			String policy,String priority,String sourceGroupId,String sourceGroupOwnerAccount,
			String appkeyId,String appkeySecret,String userEmail){
		AuthorizeSecurityGroupRequest authorizeSecurityGroupRequest = new AuthorizeSecurityGroupRequest();
		authorizeSecurityGroupRequest.setSecurityGroupId(securityGroupId);
		authorizeSecurityGroupRequest.setRegionId(regionId);
		authorizeSecurityGroupRequest.setZoneId(zoneId);
		authorizeSecurityGroupRequest.setIpProtocol(ipProtocol);
		authorizeSecurityGroupRequest.setPortRange(portRange);
		if (!StringUtils.isEmpty(sourceCidrIp)) 
			authorizeSecurityGroupRequest.setSourceCidrIp(sourceCidrIp);
		if (!StringUtils.isEmpty(policy)) 
			authorizeSecurityGroupRequest.setPolicy(policy);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(priority)) {
			//authorizeSecurityGroupRequest.setPriority(priority);
		}
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(sourceGroupId)) {
			//authorizeSecurityGroupRequest.setSourceGroupId(sourceGroupId);
		}
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(sourceGroupOwnerAccount)) {
			//authorizeSecurityGroupRequest.setSourceGroupOwnerAccount(sourceGroupOwnerAccount);
		}
		if (!StringUtils.isEmpty(userEmail)) 
			authorizeSecurityGroupRequest.setUserEmail(userEmail);
		authorizeSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(authorizeSecurityGroupRequest, BaseResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public SecurityGroupsDetailReponse DescribeSecurityGroups(String regionId, String zoneId,
			String pageNumber,String pageSize,String vpcId,
			String appkeyId,String appkeySecret,String userEmail){
		DescribeSecurityGroupsRequest describeSecurityGroupsRequest = new DescribeSecurityGroupsRequest();
		describeSecurityGroupsRequest.setRegionId(regionId);
		describeSecurityGroupsRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(pageNumber)) 
			describeSecurityGroupsRequest.setPageNumber(pageNumber);
		if (!StringUtils.isEmpty(pageSize)) 
			describeSecurityGroupsRequest.setPageSize(pageSize);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(vpcId)) {
			//describeSecurityGroupsRequest.setVpcId(vpcId);
		}
		if (!StringUtils.isEmpty(userEmail)) 
			describeSecurityGroupsRequest.setUserEmail(userEmail);
		describeSecurityGroupsRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		SecurityGroupsDetailReponse response = null;
		try {
			response = client.getYhaiResponse(describeSecurityGroupsRequest, SecurityGroupsDetailReponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new SecurityGroupsDetailReponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new SecurityGroupsDetailReponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse RevokeSecurityGroup(String regionId, String zoneId,
			String securityGroupId,String ipProtocol,String portRange,String sourceCidrIp,
			String policy,String priority,String sourceGroupId,String sourceGroupOwnerAccount,
			String appkeyId,String appkeySecret,String userEmail){
		RevokeSecurityGroupRequest revokeSecurityGroupRequest = new RevokeSecurityGroupRequest();
		revokeSecurityGroupRequest.setSecurityGroupId(securityGroupId);
		revokeSecurityGroupRequest.setRegionId(regionId);
		revokeSecurityGroupRequest.setZoneId(zoneId);
		revokeSecurityGroupRequest.setIpProtocol(ipProtocol);
		revokeSecurityGroupRequest.setPortRange(portRange);
		if (!StringUtils.isEmpty(sourceCidrIp)) 
			revokeSecurityGroupRequest.setSourceCidrIp(sourceCidrIp);
		if (!StringUtils.isEmpty(policy)) 
			revokeSecurityGroupRequest.setPolicy(policy);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(priority)) {
			//revokeSecurityGroupRequest.setPriority(priority);
		}
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(sourceGroupId)) {
			//revokeSecurityGroupRequest.setSourceGroupId(sourceGroupId);
		}
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(sourceGroupOwnerAccount)) {
			//revokeSecurityGroupRequest.setSourceGroupOwnerAccount(sourceGroupOwnerAccount);
		}
		if (!StringUtils.isEmpty(userEmail)) 
			revokeSecurityGroupRequest.setUserEmail(userEmail);
		revokeSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(revokeSecurityGroupRequest, BaseResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
				String result = e.getErrorCode() + " : " + e.getErrorMessage();
				logger.warn(result,e);
			response = new BaseResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public SecurityGroupRulesReponse DescribeSecurityGroupAttribute(String regionId, String zoneId,
			String securityGroupId,
			String appkeyId,String appkeySecret,String userEmail){
		DescribeSecurityGroupAttributeRequest describeSecurityGroupAttributeRequest = new DescribeSecurityGroupAttributeRequest();
		describeSecurityGroupAttributeRequest.setSecurityGroupId(securityGroupId);
		describeSecurityGroupAttributeRequest.setRegionId(regionId);
		describeSecurityGroupAttributeRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(userEmail)) 
			describeSecurityGroupAttributeRequest.setUserEmail(userEmail);
		describeSecurityGroupAttributeRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		SecurityGroupRulesReponse response = null;
		try {
			response = client.getYhaiResponse(describeSecurityGroupAttributeRequest, SecurityGroupRulesReponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new SecurityGroupRulesReponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new SecurityGroupRulesReponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ModifySecurityGroupAttribute(String regionId, String zoneId,
			String securityGroupId,String securityGroupName,String description,
			String appkeyId,String appkeySecret,String userEmail){
		ModifySecurityGroupAttributeRequest modifySecurityGroupAttributeRequest = new ModifySecurityGroupAttributeRequest();
		modifySecurityGroupAttributeRequest.setSecurityGroupId(securityGroupId);
		modifySecurityGroupAttributeRequest.setRegionId(regionId);
		modifySecurityGroupAttributeRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(securityGroupName)) 
			modifySecurityGroupAttributeRequest.setSecurityGroupName(securityGroupName);
		if (!StringUtils.isEmpty(description)) 
			modifySecurityGroupAttributeRequest.setDescription(description);
		if (!StringUtils.isEmpty(userEmail)) 
			modifySecurityGroupAttributeRequest.setUserEmail(userEmail);
		modifySecurityGroupAttributeRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(modifySecurityGroupAttributeRequest, BaseResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public static void main(String[] args) {

		String regionId = "beijing";
		String appkeyId = "a1bf4b64dd0c47c29f0910070cc66198";
		String appkeySecret = "a91yEZKgnoONjV3Ox2G5zQTMR96bjQIC";
		String userEmail = "bxt@hljdx.net";
		String securityGroupId = "bf85e9cbd3ce45b3bd872de00407dd61";
		String sourceGroupOwnerAccount = null;
		String sourceGroupId = "1dec146d493c4ece81310ea65843212e";
		String priority = null;
		String policy = null;
		String sourceCidrIp = "193.2.3.1/16";
		String portRange = "22/22";
		String ipProtocol = "TCP";
		String pageNumber = "1";
		String pageSize = "5";

//		CreateSecurityGroupResponse response = new SecurityGroupClient().CreateSecurityGroup(regionId, securityGroupName, description, vpcId, appkeyId, appkeySecret, userEmail);
//		logger.info(response);
//
//		BaseResponse response1 = new SecurityGroupClient().DeleteSecurityGroup(regionId, securityGroupId, appkeyId, appkeySecret, userEmail);
//		logger.info(response1);
//
//		BaseResponse response2 = new SecurityGroupClient().AuthorizeSecurityGroup(regionId, securityGroupId, ipProtocol, portRange, sourceCidrIp, policy, priority, sourceGroupId, sourceGroupOwnerAccount, appkeyId, appkeySecret, userEmail);
//		logger.info(response2);
//
//		SecurityGroupsDetailReponse response3 = new SecurityGroupClient().DescribeSecurityGroups(regionId, pageNumber, pageSize, vpcId, appkeyId, appkeySecret, userEmail);
//		logger.info(response3);

//		BaseResponse response4 = new SecurityGroupClient().RevokeSecurityGroup(regionId, securityGroupId, ipProtocol, portRange, sourceCidrIp, policy, priority, sourceGroupId, sourceGroupOwnerAccount, appkeyId, appkeySecret, userEmail);
//		logger.info(response4);
//
//		SecurityGroupRulesReponse response5 = new SecurityGroupClient().DescribeSecurityGroupAttribute(regionId, securityGroupId, appkeyId, appkeySecret, userEmail);
//		logger.info(response5);
//
//		BaseResponse response6 = new SecurityGroupClient().ModifySecurityGroupAttribute(regionId, securityGroupId, "haha", "ahah", appkeyId, appkeySecret, userEmail);
//		logger.info(response6);
	}
}
