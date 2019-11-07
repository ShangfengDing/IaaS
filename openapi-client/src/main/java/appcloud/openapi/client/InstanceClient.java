package appcloud.openapi.client;


import appcloud.iaas.sdk.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.profile.DefaultProfile;
import appcloud.core.sdk.profile.YhaiClientProfile;
import appcloud.core.sdk.utils.DefaultYhaiClient;
import appcloud.core.sdk.utils.YhaiClient;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateInstanceResponse;
import appcloud.openapi.response.DescribeInstanceStatusResponse;
import appcloud.openapi.response.DescribeInstancesResponse;
import appcloud.openapi.response.InstancesMonitorInfoReponse;

public class InstanceClient {

	public static Logger logger = Logger.getLogger(InstanceClient.class);
	public InstanceClient(){};
	
	public CreateInstanceResponse CreateInstance(String regionId, String imageId, String instanceType, String securityGroupId,String zoneId, String instanceName, 
			String description, String instanceChargeType, String instanceChargeLength,	String internetChargeType, String maxBandwidthOut, 
			String hostName, String password, String systemDiskCategory, String dataDisk_1_Category, String dataDisk_1_Size, String dataDisk_1_DeleteWithInstance, 
			String appkeyId, String appkeySecret, String userEmail) {
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
		if(null!=instanceChargeType && instanceChargeType.length()>0) {
			createInstance.setInstanceChargeType(instanceChargeType);
		}
		if(null!=instanceChargeLength && instanceChargeLength.length()>0) {
			createInstance.setInstanceChargeLength(instanceChargeLength);
		}
		if(null!=internetChargeType && internetChargeType.length()>0) {
			createInstance.setInternetChargeType(internetChargeType);
		}
		if(null!=maxBandwidthOut && maxBandwidthOut.length()>0) {
			createInstance.setInternetMaxBandwidthOut(maxBandwidthOut);
		}
		if(null!=hostName && hostName.length()>0) {
			createInstance.setHostName(hostName);
		}
		if(null!=password && password.length()>0) {
			createInstance.setPassword(password);
		}
		if(null!=dataDisk_1_Size && dataDisk_1_Size.length()>0) {
			createInstance.setDataDisk_1_Size(dataDisk_1_Size);
		}
		if(null!=userEmail && userEmail.length()>0) {
			createInstance.setUserEmail(userEmail);
		}
		
		//only exist in Aliyun 
		if(null!=systemDiskCategory && systemDiskCategory.length()>0) {
			//createInstance.setSystemDiskCategory(systemDiskCategory);
		}
		if(null!=dataDisk_1_Category && dataDisk_1_Category.length()>0) {
			//createInstance.setDataDisk_1_Category(dataDisk_1_Category);
		}
		if(null!=dataDisk_1_DeleteWithInstance && dataDisk_1_DeleteWithInstance.length()>0) {
			//createInstance.setDataDisk_1_SizeWithInstance(dataDisk_1_SizeWithInstance);
		}
		
		createInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		try {
			CreateInstanceResponse response = client.getYhaiResponse(createInstance, CreateInstanceResponse.class);
			return response;
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new CreateInstanceResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new CreateInstanceResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
	}

	public CreateInstanceResponse RecoveryInstance(String regionId, String zoneId,  String imageBackId, String imageId, String instanceType, String securityGroupId,String instanceName,
												 String description, String instanceChargeType, String instanceChargeLength,String internetChargeType, String maxBandwidthOut,
												 String hostUuid, String password,  String dataDisk_1_Size, String priVmMac, String pubVmMac, Integer userId,
												 String appkeyId, String appkeySecret, String userEmail) {
		RecoveryInstanceRequest request = new RecoveryInstanceRequest();
		request.setRegionId(regionId);
		request.setImageId(imageId);
		request.setUserId(userId.toString());
		request.setInstanceType(instanceType);
		request.setSecurityGroupId(securityGroupId);
		if(null!=zoneId && zoneId.length()>0) {
			request.setZoneId(zoneId);
		}
		if(null!=instanceName && instanceName.length()>0) {
			request.setInstanceName(instanceName);
		}
		if(null!=description && description.length()>0) {
			request.setDescription(description);
		}
		if(null!=instanceChargeType && instanceChargeType.length()>0) {
			request.setInstanceChargeType(instanceChargeType);
		}
		if(null!=instanceChargeLength && instanceChargeLength.length()>0) {
			request.setInstanceChargeLength(instanceChargeLength);
		}
		if(null!=internetChargeType && internetChargeType.length()>0) {
			request.setInternetChargeType(internetChargeType);
		}
		if(null!=maxBandwidthOut && maxBandwidthOut.length()>0) {
			request.setInternetMaxBandwidthOut(maxBandwidthOut);
		}
		if (!StringUtils.isEmpty(hostUuid)) {
			request.setHostUuid(hostUuid);
		}
		if (!StringUtils.isEmpty(imageBackId)) {
			request.setImageBackId(imageBackId);
		}
		if(null!=password && password.length()>0) {
			request.setPassword(password);
		}
		if(null!=dataDisk_1_Size && dataDisk_1_Size.length()>0) {
			request.setDataDisk_1_Size(dataDisk_1_Size);
		}
		if(null!=userEmail && userEmail.length()>0) {
			request.setUserEmail(userEmail);
		}
		if (!StringUtils.isEmpty(priVmMac)) {
			request.setPriVmMac(priVmMac);
		}
		if (!StringUtils.isEmpty(pubVmMac)) {
			request.setPubVmMac(pubVmMac);
		}

		request.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		try {
			CreateInstanceResponse response = client.getYhaiResponse(request, CreateInstanceResponse.class);
			return response;
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new CreateInstanceResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new CreateInstanceResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
	}
	
	public BaseResponse StartInstance(String regionId, String zoneId, String instanceId, String appkeyId, String appkeySecret, String userEmail) {
		StartInstanceRequest startInstance = new StartInstanceRequest();
		startInstance.setInstanceId(instanceId);
		startInstance.setRegionId(regionId);
		startInstance.setZoneId(zoneId);
		if(null!=userEmail && userEmail.length()>0) {
			startInstance.setUserEmail(userEmail);
		}
		startInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(startInstance, BaseResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}

	public BaseResponse StopInstance(String regionId, String zoneId, String instanceId, Boolean forceStop, String appkeyId, String appkeySecret, String userEmail) {
		StopInstanceRequest stopInstance = new StopInstanceRequest();
		stopInstance.setInstanceId(instanceId);
		stopInstance.setRegionId(regionId);
		stopInstance.setZoneId(zoneId);
		if(null!=userEmail && userEmail.length()>0) {
			stopInstance.setUserEmail(userEmail);
		}
		if(null != forceStop) {
			stopInstance.setForceStop(forceStop.toString());
		}
		stopInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(stopInstance, BaseResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse RebootInstance(String regionId,String zoneId, String instanceId, String appkeyId, String appkeySecret, String userEmail) {
		RebootInstanceRequest rebootInstance = new RebootInstanceRequest();
		rebootInstance.setInstanceId(instanceId);
		rebootInstance.setRegionId(regionId);
		rebootInstance.setZoneId(zoneId);
		if(null!=userEmail && userEmail.length()>0) {
			rebootInstance.setUserEmail(userEmail);
		}
		rebootInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(rebootInstance, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public DescribeInstanceStatusResponse DescribeInstanceStatus(String regionId, String zoneId, String pageNumber,
			String pageSize, String appkeyId, String appkeySecret, String userEmail) {
		DescribeInstanceStatusRequest describeInstanceStatusRequest = new DescribeInstanceStatusRequest();
		
		describeInstanceStatusRequest.setRegionId(regionId);
		describeInstanceStatusRequest.setZoneId(zoneId);

		if (!StringUtils.isEmpty(zoneId))
			describeInstanceStatusRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(pageNumber))
			describeInstanceStatusRequest.setPageNumber(pageNumber);
		if (!StringUtils.isEmpty(pageSize))
			describeInstanceStatusRequest.setPageSize(pageSize);

		if (null != userEmail && userEmail.length() > 0) {
			describeInstanceStatusRequest.setUserEmail(userEmail);
		}
		describeInstanceStatusRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeInstanceStatusResponse response = null;
		try {
			response = client.getYhaiResponse(describeInstanceStatusRequest, DescribeInstanceStatusResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new DescribeInstanceStatusResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new DescribeInstanceStatusResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse SuspendInstance(String regionId, String zoneId, String instanceId, String appkeyId, String appkeySecret, String userEmail) {
		SuspendInstanceRequest suspendInstance = new SuspendInstanceRequest();
		suspendInstance.setInstanceId(instanceId);
		suspendInstance.setRegionId(regionId);
		suspendInstance.setZoneId(zoneId);
		if(null!=userEmail && userEmail.length()>0) {
			suspendInstance.setUserEmail(userEmail);
		}
		suspendInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(suspendInstance, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ResumeInstance(String regionId, String zoneId, String instanceId, String appkeyId, String appkeySecret, String userEmail) {
		ResumeInstanceRequest resumeInstance = new ResumeInstanceRequest();
		resumeInstance.setInstanceId(instanceId);
		resumeInstance.setRegionId(regionId);
		resumeInstance.setZoneId(zoneId);
		if(null!=userEmail && userEmail.length()>0) {
			resumeInstance.setUserEmail(userEmail);
		}
		resumeInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(resumeInstance, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse DeleteInstance(String regionId, String zoneId, String instanceId, Boolean forceDelete,String appkeyId, String appkeySecret, String userEmail) {
		DeleteInstanceRequest deleteInstance = new DeleteInstanceRequest();
		deleteInstance.setInstanceId(instanceId);
		deleteInstance.setRegionId(regionId);
		deleteInstance.setZoneId(zoneId);
		if(null!=userEmail && userEmail.length()>0) {
			deleteInstance.setUserEmail(userEmail);
		}
		if (null != forceDelete)
			deleteInstance.setForceDelete(forceDelete.toString());
		
		deleteInstance.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(deleteInstance, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ModifyInstanceAttribute(String regionId, String zoneId, String instanceId, String instanceName, String description,
			String password, String hostName, String appkeyId, String appkeySecret, String userEmail) {
		ModifyInstanceAttributeRequest modifyInstanceAttributeRequest = new ModifyInstanceAttributeRequest();
		modifyInstanceAttributeRequest.setInstanceId(instanceId);
		modifyInstanceAttributeRequest.setRegionId(regionId);
		modifyInstanceAttributeRequest.setZoneId(zoneId);
		if (null != userEmail && userEmail.length() > 0) {
			modifyInstanceAttributeRequest.setUserEmail(userEmail);
		}
		if (!StringUtils.isEmpty(instanceName))
			modifyInstanceAttributeRequest.setInstanceName(instanceName);
		if (!StringUtils.isEmpty(description))
			modifyInstanceAttributeRequest.setDescription(description);
		if (!StringUtils.isEmpty(password))
			modifyInstanceAttributeRequest.setPassword(password);
		if (!StringUtils.isEmpty(hostName))
			modifyInstanceAttributeRequest.setHostName(hostName);

		modifyInstanceAttributeRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(modifyInstanceAttributeRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ModifyInstanceResource(String regionId, String zoneId, String instanceId, String CPUNum, String RAMSize,
			String internetMaxBandwidthOut, String appkeyId, String appkeySecret, String userEmail) {
		ModifyInstanceResourceRequest modifyInstanceResourceRequest = new ModifyInstanceResourceRequest();
		modifyInstanceResourceRequest.setInstanceId(instanceId);
		modifyInstanceResourceRequest.setRegionId(regionId);
		modifyInstanceResourceRequest.setZoneId(zoneId);
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
			response = client.getYhaiResponse(modifyInstanceResourceRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ModifyInstanceChargeType(String regionId, String zoneId, String instanceId, String instanceChargeType, String instanceChargeLength,
			String appkeyId, String appkeySecret, String userEmail) {
		ModifyInstanceChargeTypeRequest instanceChargeTypeRequest = new ModifyInstanceChargeTypeRequest();
		instanceChargeTypeRequest.setInstanceId(instanceId);
		instanceChargeTypeRequest.setRegionId(regionId);
		instanceChargeTypeRequest.setZoneId(zoneId);
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
			response = client.getYhaiResponse(instanceChargeTypeRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ModifyInstanceSecurityGroup(String regionId, String zoneId, String instanceId, String securityGroupId,
			String appkeyId, String appkeySecret, String userEmail) {
		ModifyInstanceSecurityGroupRequest modifyInstanceSecurityGroupRequest = new ModifyInstanceSecurityGroupRequest();
		modifyInstanceSecurityGroupRequest.setInstanceId(instanceId);
		modifyInstanceSecurityGroupRequest.setRegionId(regionId);
		modifyInstanceSecurityGroupRequest.setZoneId(zoneId);
		if (null != userEmail && userEmail.length() > 0) {
			modifyInstanceSecurityGroupRequest.setUserEmail(userEmail);
		}
		if (! StringUtils.isEmpty(securityGroupId))
			modifyInstanceSecurityGroupRequest.setSecurityGroupId(securityGroupId);

		modifyInstanceSecurityGroupRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(modifyInstanceSecurityGroupRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse ResetInstance(String regionId,String zoneId, String instanceId,
			String appkeyId, String appkeySecret, String userEmail) {
		ResetInstanceRequest resetInstanceRequest = new ResetInstanceRequest();
		resetInstanceRequest.setInstanceId(instanceId);
		resetInstanceRequest.setRegionId(regionId);
		resetInstanceRequest.setZoneId(zoneId);
		if (null != userEmail && userEmail.length() > 0) {
			resetInstanceRequest.setUserEmail(userEmail);
		}

		resetInstanceRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(resetInstanceRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse IsoBoot(String regionId, String zoneId, String instanceId,String isoId,
			String appkeyId, String appkeySecret, String userEmail) {
		IsoBootRequest isoBootRequest = new IsoBootRequest();
		isoBootRequest.setInstanceId(instanceId);
		isoBootRequest.setRegionId(regionId);
		isoBootRequest.setZoneId(zoneId);
		isoBootRequest.setImageId(isoId);
		if (null != userEmail && userEmail.length() > 0) {
			isoBootRequest.setUserEmail(userEmail);
		}

		isoBootRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(isoBootRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse IsoDetach(String regionId, String zoneId, String instanceId,
			String appkeyId, String appkeySecret, String userEmail) {
		IsoDetachRequest isoDetachRequest = new IsoDetachRequest();
		isoDetachRequest.setInstanceId(instanceId);
		isoDetachRequest.setRegionId(regionId);
		isoDetachRequest.setZoneId(zoneId);
		if (null != userEmail && userEmail.length() > 0) {
			isoDetachRequest.setUserEmail(userEmail);
		}

		isoDetachRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(isoDetachRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public InstancesMonitorInfoReponse DescribeInstanceMonitorData(String regionId, String zoneId, String instanceId,String startTime, String endTime, String period,
			String appkeyId, String appkeySecret, String userEmail) {
		DescribeInstanceMonitorDataRequest describeInstanceMonitorDataRequest = new DescribeInstanceMonitorDataRequest();
		describeInstanceMonitorDataRequest.setInstanceId(instanceId);
		describeInstanceMonitorDataRequest.setRegionId(regionId);
		describeInstanceMonitorDataRequest.setZoneId(zoneId);
		describeInstanceMonitorDataRequest.setStartTime(startTime);
		describeInstanceMonitorDataRequest.setEndTime(endTime);
		
		if (! StringUtils.isEmpty(period)) {
			describeInstanceMonitorDataRequest.setPeriod(period);
		}
		
		if (null != userEmail && userEmail.length() > 0) {
			describeInstanceMonitorDataRequest.setUserEmail(userEmail);
		}

		describeInstanceMonitorDataRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		InstancesMonitorInfoReponse response = null;
		try {
			response = client.getYhaiResponse(describeInstanceMonitorDataRequest, InstancesMonitorInfoReponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new InstancesMonitorInfoReponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new InstancesMonitorInfoReponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
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
			request.setPageNumber(pageNumber);
		}
		if (! StringUtils.isEmpty(pageSize)) {
			request.setPageSize(pageSize);
		}
		
		if (null != userEmail && userEmail.length() > 0) {
			request.setUserEmail(userEmail);
		}

		request.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeInstancesResponse response = null;
		try {
			response = client.getYhaiResponse(request, DescribeInstancesResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new DescribeInstancesResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new DescribeInstancesResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	public BaseResponse RenewInstance(String regionId, String zoneId, String instanceId,String instanceChargeType,String instanceChargeLength,
			String appkeyId, String appkeySecret, String userEmail) {
		RenewInstanceRequest renewInstanceRequest = new RenewInstanceRequest();
		renewInstanceRequest.setInstanceId(instanceId);
		renewInstanceRequest.setRegionId(regionId);
		renewInstanceRequest.setZoneId(zoneId);
		if(null!=instanceChargeType && instanceChargeType.length()>0) {
			renewInstanceRequest.setInstanceChargeType(instanceChargeType);
		}
		if(null!=instanceChargeLength && instanceChargeLength.length()>0) {
			renewInstanceRequest.setInstanceChargeLength(instanceChargeLength);
		}
		if (null != userEmail && userEmail.length() > 0) {
			renewInstanceRequest.setUserEmail(userEmail);
		}
		renewInstanceRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(renewInstanceRequest, BaseResponse.class);
		} catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		} catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result, e);
			response = new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}
	
	/*public static void main(String[] args) {
//		String appkeyId = "f00577d2c1764688be5e483a21f4fe92";
//		String appkeySecret = "1KS2AMYX0UEjvQA6sVYK1AXfRpvrtArQ";
//		String instanceId = "11af8257cdb9440491098c9321bb6ce9";
//	    String userEmail = "free@free4lab.com";
		String appkeyId = "656acbb67965435a8ed083f4e4a98533";
		String appkeySecret = "G5JVZui0oYarWtStjcL5r4ePONgz6ZjR";
		String userEmail = "yumike18@126.com";
	    String regionId = "beijing";
	    String zoneId = "lab";
	    String version = "2015-10-20";
	    String imageId = "013bf40d2a9547d682447b4dd72363b6";
	    String instanceType = "free.t1.small";
	    String securityGroupId = "8ba37037af8a4083bff7a34f3c70481e";
	    String instanceId = "9dcd100841854671ad2a56cbc70de398";
	    String instanceChargeType = "PayByHour";
	    String instanceChargeLength = null;

		String imageBackId = "a5fee72f970b479cb9febf416c07a3cc";

		CreateInstanceResponse response = new InstanceClient().RecoveryInstance(regionId, zoneId, imageBackId, imageId, instanceType, "5b82166e8b7244b9b81cb78d8a90ba1c", "consul-1",
				"recovery", instanceChargeType, "1", null, null, "0026B94CDF54", null, "20", "00:10:47:99:8F:8A", "00:11:6F:3E:31:EA", appkeyId, appkeySecret, userEmail);
		System.out.println(response.getMessage());

	    *//*logger.info("create instance");
	    CreateInstanceResponse createInstanceResponse = new InstanceClient().CreateInstance(regionId,imageId, instanceType, securityGroupId, null,
	    		null, null, null, null, null, null, null, null, null, null, null, null,appkeyId, appkeySecret, userEmail);
		logger.info(createInstanceResponse);
	    logger.info("renew instance");
		BaseResponse reponse = new InstanceClient().RenewInstance(regionId, instanceId, instanceChargeType, instanceChargeLength, appkeyId, appkeySecret, userEmail);

	    logger.info("ModifyInstanceResource");
		BaseResponse reponse = new InstanceClient().ModifyInstanceResource(regionId, instanceId, "4", "4", null, appkeyId, appkeySecret, userEmail);

		BaseResponse baseResponse = null;
		logger.info("start instance");
		baseResponse = new InstanceClient().StartInstance(instanceId ,appkeyId, appkeySecret, userEmail);
		logger.info(baseResponse);

	    logger.info("stop instance");
		baseResponse = new InstanceClient().StopInstance(instanceId ,appkeyId, appkeySecret, userEmail);
		logger.info(baseResponse);

		logger.info("reboot instance");
		baseResponse = new InstanceClient().RebootInstance(instanceId ,appkeyId, appkeySecret, userEmail);
		logger.info(baseResponse); *//*


	}*/
	/*public static void main(String[] args) {
		*//*public CreateInstanceResponse CreateInstance(String regionId, String imageId, String instanceType, String securityGroupId,String zoneId, String instanceName,
				String description, String instanceChargeType, String instanceChargeLength,	String internetChargeType, String maxBandwidthOut,
				String hostName, String password, String systemDiskCategory, String dataDisk_1_Category, String dataDisk_1_Size, String dataDisk_1_DeleteWithInstance,
				String appkeyId, String appkeySecret, String userEmail)
		public CreateInstanceResponse RecoveryInstance(String regionId, String zoneId,  String imageBackId, String imageId, String instanceType, String securityGroupId,String instanceName,
				String description, String instanceChargeType, String instanceChargeLength,String internetChargeType, String maxBandwidthOut,
				String hostUuid, String password,  String dataDisk_1_Size, String priVmMac, String pubVmMac,
				String appkeyId, String appkeySecret, String userEmail)*//*
		String regionId = "beijing";
		String zoneId = "lab";
		String imageBackId = "a5fee72f970b479cb9febf416c07a3cc";
		String imageId = "013bf40d2a9547d682447b4dd72363b6";
		String instanceType = "10136-flavor";
		String securityGroupId = "171";
		String instanceName ="test-hao";
		String instanceChargeType = "PayByHour";
		String instanceChargeLength = null;
		String hostUuid ="0026";
		String appkeyId = "f00577d2c1764688be5e483a21f4fe92";
		String appkeySecret = "1KS2AMYX0UEjvQA6sVYK1AXfRpvrtArQ";
		String userEmail = "free@free4lab.com";

		logger.info("recovery instance");
		CreateInstanceResponse response = new InstanceClient().RecoveryInstance(regionId,zoneId,imageBackId,imageId,instanceType,securityGroupId,instanceName,"recovery",instanceChargeType,instanceChargeLength,"1",null,hostUuid,null,"20", "00:10:47:99:8F:8A", "00:11:6F:3E:31:EA", appkeyId, appkeySecret, userEmail);
		System.out.println(response.getMessage());
	}*/
}
