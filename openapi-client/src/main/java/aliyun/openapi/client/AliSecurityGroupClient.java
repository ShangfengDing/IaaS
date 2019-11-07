package aliyun.openapi.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.AuthorizeSecurityGroupRequest;
import com.aliyuncs.ecs.model.v20140526.AuthorizeSecurityGroupResponse;
import com.aliyuncs.ecs.model.v20140526.CreateSecurityGroupRequest;
import com.aliyuncs.ecs.model.v20140526.CreateSecurityGroupResponse;
import com.aliyuncs.ecs.model.v20140526.DeleteSecurityGroupRequest;
import com.aliyuncs.ecs.model.v20140526.DeleteSecurityGroupResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupAttributeRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupAttributeResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupsRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupsResponse;
import com.aliyuncs.ecs.model.v20140526.ModifySecurityGroupAttributeRequest;
import com.aliyuncs.ecs.model.v20140526.ModifySecurityGroupAttributeResponse;
import com.aliyuncs.ecs.model.v20140526.RevokeSecurityGroupEgressResponse;
import com.aliyuncs.ecs.model.v20140526.RevokeSecurityGroupRequest;
import com.aliyuncs.ecs.model.v20140526.RevokeSecurityGroupResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliSecurityGroupClient {

	private static Logger logger = Logger.getLogger(AliSecurityGroupClient.class);
	
	public CreateSecurityGroupResponse CreateSecurityGroup (String regionId,
			String securityGroupName,String description,String vpcId,
			String appkeyId,String appkeySecret,String userEmail) throws ClientException{
		CreateSecurityGroupRequest createSecurityGroupRequest = new CreateSecurityGroupRequest();
		createSecurityGroupRequest.setRegionId(regionId);
		if(!StringUtils.isEmpty(securityGroupName))
			createSecurityGroupRequest.setSecurityGroupName(securityGroupName);
		if(!StringUtils.isEmpty(description))
			createSecurityGroupRequest.setDescription(description);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(vpcId)) {
			createSecurityGroupRequest.setVpcId(vpcId);
		}
		createSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile("cn-qingdao",appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		CreateSecurityGroupResponse response = null;
		try {
			response = client.getAcsResponse(createSecurityGroupRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public DeleteSecurityGroupResponse DeleteSecurityGroup(String regionId,
			String securityGroupId,
			String appkeyId,String appkeySecret,String userEmail) throws ClientException{
		DeleteSecurityGroupRequest deleteSecurityGroupRequest = new DeleteSecurityGroupRequest();
		deleteSecurityGroupRequest.setSecurityGroupId(securityGroupId);
		deleteSecurityGroupRequest.setRegionId(regionId);
		/*if (!StringUtils.isEmpty(userEmail)) 
			deleteSecurityGroupRequest.setUserEmail(userEmail);*/
		deleteSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile("cn-qingdao",appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DeleteSecurityGroupResponse response = null;
		try {
			response = client.getAcsResponse(deleteSecurityGroupRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public AuthorizeSecurityGroupResponse AuthorizeSecurityGroup(String regionId,
			String securityGroupId,String ipProtocol,String portRange,String sourceCidrIp,
			String policy,String priority,String sourceGroupId,String sourceGroupOwnerAccount,
			String appkeyId,String appkeySecret,String userEmail) throws ClientException {
		AuthorizeSecurityGroupRequest authorizeSecurityGroupRequest = new AuthorizeSecurityGroupRequest();
		authorizeSecurityGroupRequest.setSecurityGroupId(securityGroupId);
		authorizeSecurityGroupRequest.setRegionId(regionId);
		authorizeSecurityGroupRequest.setIpProtocol(ipProtocol);
		authorizeSecurityGroupRequest.setPortRange(portRange);
		if (!StringUtils.isEmpty(sourceCidrIp)) 
			authorizeSecurityGroupRequest.setSourceCidrIp(sourceCidrIp);
		if (!StringUtils.isEmpty(policy)) 
			authorizeSecurityGroupRequest.setPolicy(policy);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(priority)) {
			authorizeSecurityGroupRequest.setPriority(priority);
		}
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(sourceGroupId)) {
			authorizeSecurityGroupRequest.setSourceGroupId(sourceGroupId);
		}
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(sourceGroupOwnerAccount)) {
			authorizeSecurityGroupRequest.setSourceGroupOwnerAccount(sourceGroupOwnerAccount);
		}
		/*if (!StringUtils.isEmpty(userEmail)) 
			authorizeSecurityGroupRequest.setUserEmail(userEmail);*/
		authorizeSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile("cn-qingdao",appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		AuthorizeSecurityGroupResponse response = null;
		try {
			response = client.getAcsResponse(authorizeSecurityGroupRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public DescribeSecurityGroupsResponse DescribeSecurityGroups(String regionId,
			String pageNumber,String pageSize,String vpcId,
			String appkeyId,String appkeySecret,String userEmail) throws ClientException{
		DescribeSecurityGroupsRequest describeSecurityGroupsRequest = new DescribeSecurityGroupsRequest();
		describeSecurityGroupsRequest.setRegionId(regionId);
		if (!StringUtils.isEmpty(pageNumber)) 
			describeSecurityGroupsRequest.setPageNumber(Integer.valueOf(pageNumber));
		if (!StringUtils.isEmpty(pageSize)) 
			describeSecurityGroupsRequest.setPageSize(Integer.valueOf(pageSize));
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(vpcId)) {
			describeSecurityGroupsRequest.setVpcId(vpcId);
		}
		describeSecurityGroupsRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeSecurityGroupsResponse response = null;
		try {
			response = client.getAcsResponse(describeSecurityGroupsRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public RevokeSecurityGroupResponse RevokeSecurityGroup(String regionId,
			String securityGroupId,String ipProtocol,String portRange,String sourceCidrIp,
			String policy,String priority,String sourceGroupId,String sourceGroupOwnerAccount,
			String appkeyId,String appkeySecret,String userEmail) throws ClientException {
		RevokeSecurityGroupRequest revokeSecurityGroupRequest = new RevokeSecurityGroupRequest();
		revokeSecurityGroupRequest.setSecurityGroupId(securityGroupId);
		revokeSecurityGroupRequest.setRegionId(regionId);
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
			revokeSecurityGroupRequest.setSourceGroupId(sourceGroupId);
		}
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(sourceGroupOwnerAccount)) {
			revokeSecurityGroupRequest.setSourceGroupOwnerAccount(sourceGroupOwnerAccount);
		}
		/*if (!StringUtils.isEmpty(userEmail)) 
			revokeSecurityGroupRequest.setUserEmail(userEmail);*/
		revokeSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile("cn-qingdao",appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		RevokeSecurityGroupResponse response = null;
		try {
			response = client.getAcsResponse(revokeSecurityGroupRequest);
		}catch (ServerException e) {
			String result =  e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result =  e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public DescribeSecurityGroupAttributeResponse DescribeSecurityGroupAttribute(String regionId,
			String securityGroupId,
			String appkeyId,String appkeySecret,String userEmail){
		DescribeSecurityGroupAttributeRequest describeSecurityGroupAttributeRequest = new DescribeSecurityGroupAttributeRequest();
		describeSecurityGroupAttributeRequest.setSecurityGroupId(securityGroupId);
		describeSecurityGroupAttributeRequest.setRegionId(regionId);
		describeSecurityGroupAttributeRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeSecurityGroupAttributeResponse response = null;
		try {
			response = client.getAcsResponse(describeSecurityGroupAttributeRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			response = new DescribeSecurityGroupAttributeResponse();
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			response = new DescribeSecurityGroupAttributeResponse();
		}
		return response;
	}
	
	public ModifySecurityGroupAttributeResponse ModifySecurityGroupAttribute(String regionId,
			String securityGroupId,String securityGroupName,String description,
			String appkeyId,String appkeySecret,String userEmail) throws ClientException{
		ModifySecurityGroupAttributeRequest modifySecurityGroupAttributeRequest = new ModifySecurityGroupAttributeRequest();
		modifySecurityGroupAttributeRequest.setSecurityGroupId(securityGroupId);
		modifySecurityGroupAttributeRequest.setRegionId(regionId);
		if (!StringUtils.isEmpty(securityGroupName)) 
			modifySecurityGroupAttributeRequest.setSecurityGroupName(securityGroupName);
		if (!StringUtils.isEmpty(description)) 
			modifySecurityGroupAttributeRequest.setDescription(description);
		modifySecurityGroupAttributeRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		ModifySecurityGroupAttributeResponse response = null;
		try {
			response = client.getAcsResponse(modifySecurityGroupAttributeRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
}
