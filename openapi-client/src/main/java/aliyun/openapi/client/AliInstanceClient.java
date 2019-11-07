package aliyun.openapi.client;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.CreateInstanceRequest;
import com.aliyuncs.ecs.model.v20140526.CreateInstanceResponse;
import com.aliyuncs.ecs.model.v20140526.DeleteInstanceRequest;
import com.aliyuncs.ecs.model.v20140526.DeleteInstanceResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceStatusRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceStatusResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceVncUrlRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceVncUrlResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.ecs.model.v20140526.JoinSecurityGroupRequest;
import com.aliyuncs.ecs.model.v20140526.JoinSecurityGroupResponse;
import com.aliyuncs.ecs.model.v20140526.LeaveSecurityGroupRequest;
import com.aliyuncs.ecs.model.v20140526.LeaveSecurityGroupResponse;
import com.aliyuncs.ecs.model.v20140526.ModifyInstanceAttributeRequest;
import com.aliyuncs.ecs.model.v20140526.ModifyInstanceAttributeResponse;
import com.aliyuncs.ecs.model.v20140526.RebootInstanceRequest;
import com.aliyuncs.ecs.model.v20140526.RebootInstanceResponse;
import com.aliyuncs.ecs.model.v20140526.StartInstanceRequest;
import com.aliyuncs.ecs.model.v20140526.StartInstanceResponse;
import com.aliyuncs.ecs.model.v20140526.StopInstanceRequest;
import com.aliyuncs.ecs.model.v20140526.StopInstanceResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliInstanceClient {

	public static Logger logger = Logger.getLogger(AliInstanceClient.class);
	public AliInstanceClient(){};
	
	public CreateInstanceResponse CreateInstance(String regionId, String imageId, String instanceType, String securityGroupId,String zoneId, String instanceName, 
			String description, String instanceChargeType, String instanceChargeLength,	String internetChargeType, String maxBandwidthOut, 
			String hostName, String password, String systemDiskCategory, String systemDiskSize, String dataDisk_1_Category, String dataDisk_1_Size, Boolean dataDisk_1_DeleteWithInstance, 
			String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		CreateInstanceRequest createInstance = new CreateInstanceRequest();
		createInstance.setRegionId(regionId);
		createInstance.setImageId(imageId);
		createInstance.setInstanceType(instanceType);
		createInstance.setSecurityGroupId(securityGroupId);
		if(null!=zoneId && zoneId.length()>0) {
			createInstance.setZoneId(zoneId);
		}
		if(null!=instanceName && instanceName.length()>0) {
			createInstance.setInstanceName(instanceName);
		}
		if(null!=description && description.length()>0) {
			createInstance.setDescription(description);
		}
		if(null!=systemDiskSize && systemDiskSize.length()>0) {
			createInstance.setSystemDiskSize(Integer.valueOf(systemDiskSize));
		}
		//TODO 暂时先只创建按需付费虚拟机
		/*if(null!=instanceChargeType && instanceChargeType.length()>0) {
			createInstance.setInstanceChargeType(instanceChargeType);
		}
		if(null!=instanceChargeLength && instanceChargeLength.length()>0) {
			createInstance.setPeriod(Integer.valueOf(instanceChargeLength));
		}*/
		if(null!=internetChargeType && internetChargeType.length()>0) {
			createInstance.setInternetChargeType(internetChargeType);
		}
		if(null!=maxBandwidthOut && maxBandwidthOut.length()>0) {
			createInstance.setInternetMaxBandwidthOut(Integer.valueOf(maxBandwidthOut));
		}
		if(null!=hostName && hostName.length()>0) {
			createInstance.setHostName(hostName);
		}
		if(null!=password && password.length()>0) {
			createInstance.setPassword(password);
		}
		if(null!=dataDisk_1_Size && dataDisk_1_Size.length()>0) {
			createInstance.setDataDisk1Size(Integer.valueOf(dataDisk_1_Size));
		}
		//only exist in Aliyun
		if(null!=systemDiskCategory && systemDiskCategory.length()>0) {
			createInstance.setSystemDiskCategory(systemDiskCategory);
		}
		if(null!=dataDisk_1_Category && dataDisk_1_Category.length()>0) {
			createInstance.setDataDisk1Category(dataDisk_1_Category);
		}
		if(null!=dataDisk_1_DeleteWithInstance) {
			createInstance.setDataDisk1DeleteWithInstance(dataDisk_1_DeleteWithInstance);
		}

		createInstance.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		CreateInstanceResponse response;
		try {
			response = client.getAcsResponse(createInstance);
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

	public StartInstanceResponse StartInstance(String regionId,String instanceId, String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		StartInstanceRequest startInstance = new StartInstanceRequest();
		startInstance.setInstanceId(instanceId);
		/*if(null!=userEmail && userEmail.length()>0) {
			startInstance.setUserEmail(userEmail);
		}*/
		startInstance.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		StartInstanceResponse response = null;
		try {
			response  = client.getAcsResponse(startInstance);
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

	public StopInstanceResponse StopInstance(String regionId,String instanceId, String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		StopInstanceRequest stopInstance = new StopInstanceRequest();
		stopInstance.setInstanceId(instanceId);
		/*if(null!=userEmail && userEmail.length()>0) {
			stopInstance.setUserEmail(userEmail);
		}*/
		stopInstance.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		StopInstanceResponse response = null;
		try {
			response = client.getAcsResponse(stopInstance);
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

	public RebootInstanceResponse RebootInstance(String regionId,String instanceId, String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		RebootInstanceRequest rebootInstance = new RebootInstanceRequest();
		rebootInstance.setInstanceId(instanceId);
		/*if(null!=userEmail && userEmail.length()>0) {
			rebootInstance.setUserEmail(userEmail);
		}*/
		rebootInstance.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		RebootInstanceResponse response = null;
		try {
			response = client.getAcsResponse(rebootInstance);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public DescribeInstanceStatusResponse DescribeInstanceStatus(String regionId, String zoneId, Integer pageNumber,
			Integer pageSize, String appkeyId, String appkeySecret, String userEmail) {
		DescribeInstanceStatusRequest describeInstanceStatusRequest = new DescribeInstanceStatusRequest();
		
		describeInstanceStatusRequest.setRegionId(regionId);

		if (!StringUtils.isEmpty(zoneId))
			describeInstanceStatusRequest.setZoneId(zoneId);
		if (null != pageNumber)
			describeInstanceStatusRequest.setPageNumber(pageNumber);
		if (null != pageSize)
			describeInstanceStatusRequest.setPageSize(pageSize);

		describeInstanceStatusRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeInstanceStatusResponse response = null;
		try {
			response = client.getAcsResponse(describeInstanceStatusRequest);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrorType();
			logger.warn(result, e);
			response = new DescribeInstanceStatusResponse();
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrorType();
			logger.warn(result, e);
			response = new DescribeInstanceStatusResponse();
		}
		return response;
	}
	
	/* public BaseResponse SuspendInstance(String regionId,String instanceId, String appkeyId, String appkeySecret, String userEmail) {
		SuspendInstanceRequest suspendInstance = new SuspendInstanceRequest();
		suspendInstance.setInstanceId(instanceId);
		if(null!=userEmail && userEmail.length()>0) {
			suspendInstance.setUserEmail(userEmail);
		}
		suspendInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getAcsResponse(suspendInstance, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ResumeInstance(String regionId,String instanceId, String appkeyId, String appkeySecret, String userEmail) {
		ResumeInstanceRequest resumeInstance = new ResumeInstanceRequest();
		resumeInstance.setInstanceId(instanceId);
		if(null!=userEmail && userEmail.length()>0) {
			resumeInstance.setUserEmail(userEmail);
		}
		resumeInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getAcsResponse(resumeInstance, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}*/
	
	public DeleteInstanceResponse DeleteInstance(String regionId,String instanceId, String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		DeleteInstanceRequest request = new DeleteInstanceRequest();
		request.setInstanceId(instanceId);
		request.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DeleteInstanceResponse response = null;
		try {
			response = client.getAcsResponse(request);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			response = new DeleteInstanceResponse();
			throw e;
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result,e);
			response = new DeleteInstanceResponse();
			throw e;
		}
		return response;
	}
	
	public ModifyInstanceAttributeResponse ModifyInstanceAttribute(String regionId,String instanceId, String instanceName, String description,
			String password, String hostName, String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		ModifyInstanceAttributeRequest modifyInstanceAttributeRequest = new ModifyInstanceAttributeRequest();
		modifyInstanceAttributeRequest.setInstanceId(instanceId);
		if (!StringUtils.isEmpty(instanceName))
			modifyInstanceAttributeRequest.setInstanceName(instanceName);
		if (!StringUtils.isEmpty(description))
			modifyInstanceAttributeRequest.setDescription(description);
		if (!StringUtils.isEmpty(password))
			modifyInstanceAttributeRequest.setPassword(password);
		if (!StringUtils.isEmpty(hostName))
			modifyInstanceAttributeRequest.setHostName(hostName);

		modifyInstanceAttributeRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		ModifyInstanceAttributeResponse response = null;
		try {
			response = client.getAcsResponse(modifyInstanceAttributeRequest);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			throw e;
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			throw e;
		}
		return response;
	}
	
	/*public BaseResponse ModifyInstanceResource(String regionId,String instanceId, String CPUNum, String RAMSize,
			String internetMaxBandwidthOut, String appkeyId, String appkeySecret, String userEmail) {
		ModifyInstanceResourceRequest modifyInstanceResourceRequest = new ModifyInstanceResourceRequest();
		modifyInstanceResourceRequest.setInstanceId(instanceId);
		if (null != userEmail && userEmail.length() > 0) {
			modifyInstanceResourceRequest.setUserEmail(userEmail);
		}
		if (!StringUtils.isEmpty(CPUNum))
			modifyInstanceResourceRequest.setCpuNum(CPUNum);
		if (!StringUtils.isEmpty(RAMSize))
			modifyInstanceResourceRequest.setRamSize(RAMSize);
		if (!StringUtils.isEmpty(internetMaxBandwidthOut))
			modifyInstanceResourceRequest.setInternetMaxBandwidthOut(internetMaxBandwidthOut);

		modifyInstanceResourceRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getAcsResponse(modifyInstanceResourceRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ModifyInstanceChargeType(String regionId,String instanceId, String instanceChargeType, String instanceChargeLength,
			String appkeyId, String appkeySecret, String userEmail) {
		ModifyInstanceChargeTypeRequest instanceChargeTypeRequest = new ModifyInstanceChargeTypeRequest();
		instanceChargeTypeRequest.setInstanceId(instanceId);
		if (null != userEmail && userEmail.length() > 0) {
			instanceChargeTypeRequest.setUserEmail(userEmail);
		}
		if (! StringUtils.isEmpty(instanceChargeType))
			instanceChargeTypeRequest.setInstanceChargeType(instanceChargeType);
		if (! StringUtils.isEmpty(instanceChargeLength))
			instanceChargeTypeRequest.setInstanceChargeLength(instanceChargeLength);

		instanceChargeTypeRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getAcsResponse(instanceChargeTypeRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}*/
	
	/**
	 * 替换安全组，originSecurityGroupId => securityGroupId
	 * 如果originSecurityGroupId为null则只是新增一个安全组而不替换，因为阿里云支持一个主机使用五个安全组！
	 * @param regionId
	 * @param instanceId
	 * @param securityGroupId
	 * @param originSecurityGroupId
	 * @param appkeyId
	 * @param appkeySecret
	 * @param userEmail
	 * @return
	 */
	public JoinSecurityGroupResponse ModifyInstanceSecurityGroup(String regionId,String instanceId, String securityGroupId,String originSecurityGroupId,
			String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		JoinSecurityGroupRequest request = new JoinSecurityGroupRequest();
		request.setInstanceId(instanceId);
		if (! StringUtils.isEmpty(securityGroupId))
			request.setSecurityGroupId(securityGroupId);

		request.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		JoinSecurityGroupResponse response = null;
		try {
			response = client.getAcsResponse(request);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			throw e;
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			throw e;
		}
		
		if (! StringUtils.isEmpty(originSecurityGroupId)) {
			LeaveSecurityGroup(regionId, instanceId, originSecurityGroupId, appkeyId, appkeySecret, userEmail);
		}
		
		return response;
	}
	
	public LeaveSecurityGroupResponse LeaveSecurityGroup(String regionId,String instanceId, String securityGroupId,
			String appkeyId, String appkeySecret, String userEmail) {
		LeaveSecurityGroupRequest request = new LeaveSecurityGroupRequest();
		request.setInstanceId(instanceId);
		if (! StringUtils.isEmpty(securityGroupId))
			request.setSecurityGroupId(securityGroupId);

		request.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		LeaveSecurityGroupResponse response = null;
		try {
			response = client.getAcsResponse(request);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new LeaveSecurityGroupResponse();
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new LeaveSecurityGroupResponse();
		}
		
		return response;
	}
	
	
	/*public BaseResponse ResetInstance(String regionId,String instanceId,
			String appkeyId, String appkeySecret, String userEmail) {
		ResetInstanceRequest resetInstanceRequest = new ResetInstanceRequest();
		resetInstanceRequest.setInstanceId(instanceId);
		if (null != userEmail && userEmail.length() > 0) {
			resetInstanceRequest.setUserEmail(userEmail);
		}

		resetInstanceRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getAcsResponse(resetInstanceRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse IsoBoot(String regionId,String instanceId,String isoId,
			String appkeyId, String appkeySecret, String userEmail) {
		IsoBootRequest isoBootRequest = new IsoBootRequest();
		isoBootRequest.setInstanceId(instanceId);
		isoBootRequest.setImageId(isoId);
		if (null != userEmail && userEmail.length() > 0) {
			isoBootRequest.setUserEmail(userEmail);
		}

		isoBootRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getAcsResponse(isoBootRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse IsoDetach(String regionId,String instanceId,
			String appkeyId, String appkeySecret, String userEmail) {
		IsoDetachRequest isoDetachRequest = new IsoDetachRequest();
		isoDetachRequest.setInstanceId(instanceId);
		if (null != userEmail && userEmail.length() > 0) {
			isoDetachRequest.setUserEmail(userEmail);
		}

		isoDetachRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getAcsResponse(isoDetachRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}*/
	
	public DescribeInstanceMonitorDataResponse DescribeInstanceMonitorData(String regionId,String instanceId,String startTime, String endTime, Integer period,
			String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		DescribeInstanceMonitorDataRequest describeInstanceMonitorDataRequest = new DescribeInstanceMonitorDataRequest();
		describeInstanceMonitorDataRequest.setInstanceId(instanceId);
		describeInstanceMonitorDataRequest.setStartTime(startTime);
		describeInstanceMonitorDataRequest.setEndTime(endTime);
		
		if (null != period ) {
			describeInstanceMonitorDataRequest.setPeriod(Integer.valueOf(period));
		}
		
		describeInstanceMonitorDataRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeInstanceMonitorDataResponse response = null;
		try {
			response = client.getAcsResponse(describeInstanceMonitorDataRequest);
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			throw e;
		}
		return response;
	}
	
	public DescribeInstancesResponse DescribeInstances(String regionId, String zoneId, String instanceIds,String instanceType, 
			String innerIpAddresses, String publicIpAddresses, String securityGroupId, String instanceName, String status, 
			String imageId, String pageNumber, String pageSize, 
			String appkeyId, String appkeySecret, String userEmail) {
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		request.setRegionId(regionId);
		
		if (! StringUtils.isEmpty(zoneId)) {
			request.setZoneId(zoneId);
		}
		if (! StringUtils.isEmpty(instanceIds)) {
			request.setInstanceIds(instanceIds);
		}
		if (! StringUtils.isEmpty(instanceType)) {
			request.setInstanceType(instanceType);
		}
		if (! StringUtils.isEmpty(innerIpAddresses)) {
			request.setInnerIpAddresses(innerIpAddresses);
		}
		if (! StringUtils.isEmpty(publicIpAddresses)) {
			request.setPublicIpAddresses(publicIpAddresses);
		}
		if (! StringUtils.isEmpty(securityGroupId)) {
			request.setSecurityGroupId(securityGroupId);
		}
		if (! StringUtils.isEmpty(instanceName)) {
			request.setInstanceName(instanceName);
		}
		if (! StringUtils.isEmpty(status)) {
			request.setStatus(status);
		}
		if (! StringUtils.isEmpty(imageId)) {
			request.setImageId(imageId);
		}
		if (! StringUtils.isEmpty(pageNumber)) {
			request.setPageNumber(Integer.parseInt(pageNumber));
		}
		if (! StringUtils.isEmpty(pageSize)) {
			request.setPageSize(Integer.parseInt(pageSize));
		}
		
		/*if (null != userEmail && userEmail.length() > 0) {
			request.setOwnerAccount(userEmail);
		}*/

		request.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeInstancesResponse response = null;
		try {
			response = client.getAcsResponse(request);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeInstancesResponse();
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeInstancesResponse();
		}
		return response;
	}
	
	public DescribeInstanceVncUrlResponse DescribeInstanceVncUrl(String regionId,String instanceId,String appkeyId, String appkeySecret) {
		DescribeInstanceVncUrlRequest request = new DescribeInstanceVncUrlRequest();
		request.setInstanceId(instanceId);
		request.setRegionId(regionId);
		
		request.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeInstanceVncUrlResponse response = null;
		try {
			response = client.getAcsResponse(request);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new DescribeInstanceVncUrlResponse();
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.warn(result, e);
			response = new DescribeInstanceVncUrlResponse();
		}
		return response;
	}
	
}
