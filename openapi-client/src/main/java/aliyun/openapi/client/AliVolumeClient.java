package aliyun.openapi.client;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class AliVolumeClient {

	public static Logger logger = Logger.getLogger(AliVolumeClient.class);
	
	public CreateDiskResponse CreateDisk(String regionId,String zoneId,String diskSize,String diskChargeType,String diskChargeLength,String diskName,String diskCategory,String description,String snapshotId,String appkeyId,String appkeySecret,String userEmail) throws ClientException {
		CreateDiskRequest createDiskRequest = new CreateDiskRequest();
		createDiskRequest.setRegionId(regionId);
		createDiskRequest.setZoneId(zoneId);
		createDiskRequest.setSize(Integer.valueOf(diskSize));
		//only in yunhai
		/*if (!StringUtils.isEmpty(diskChargeType)) 
			createDiskRequest.setDiskChargeType(diskChargeType);
		if (!StringUtils.isEmpty(diskChargeLength)) 
			createDiskRequest.setDiskChargeLength(diskChargeLength);*/
		if (!StringUtils.isEmpty(diskName)) 
			createDiskRequest.setDiskName(diskName);
		if (!StringUtils.isEmpty(diskCategory)) 
			createDiskRequest.setDiskCategory(diskCategory);
		if (!StringUtils.isEmpty(description)) 
			createDiskRequest.setDescription(description);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(snapshotId)) {
			createDiskRequest.setSnapshotId(snapshotId);
		}
		/*if (!StringUtils.isEmpty(userEmail)) 
			createDiskRequest.setUserEmail(userEmail);*/
		createDiskRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		CreateDiskResponse response = null;
		try {
			response = client.getAcsResponse(createDiskRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			throw e;

		}
		return response;
	}
	
	public AttachDiskResponse AttachDisk(String regionId,String diskId,String instanceId,String device,String deleteWithInstance,String appkeyId,String appkeySecret,String userEmail) throws ClientException {
		AttachDiskRequest attachDiskRequest = new AttachDiskRequest();
		attachDiskRequest.setDiskId(diskId);
		attachDiskRequest.setInstanceId(instanceId);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(device)) {
			//attachDiskRequest.setDevice(device);
		}
		if(!StringUtils.isEmpty(deleteWithInstance)) {
			//attachDiskRequest.setDeleteWithInstance(deleteWithInstance);
		}
		attachDiskRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		AttachDiskResponse response = null;
		try {
			response = client.getAcsResponse(attachDiskRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public DetachDiskResponse DetachDisk(String regionId,String diskId,String instanceId,String appkeyId,String appkeySecret,String userEmail) throws ClientException {
		DetachDiskRequest detachDiskRequest = new DetachDiskRequest();
		detachDiskRequest.setDiskId(diskId);
		detachDiskRequest.setInstanceId(instanceId);
		detachDiskRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DetachDiskResponse response = null;
		try {
			response = client.getAcsResponse(detachDiskRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public DeleteDiskResponse DeleteDisk(String regionId,String diskId,String appkeyId,String appkeySecret,String userEmail) throws ClientException {
		DeleteDiskRequest deleteDiskRequest = new DeleteDiskRequest();
		deleteDiskRequest.setDiskId(diskId);
		deleteDiskRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DeleteDiskResponse response = null;
		try {
			response = client.getAcsResponse(deleteDiskRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	public ResetDiskResponse ResetDisk(String regionId,String diskId,String snapshotId,String appkeyId,String appkeySecret,String userEmail) throws ClientException {
		ResetDiskRequest resetDiskRequest = new ResetDiskRequest();
		resetDiskRequest.setDiskId(diskId);
		resetDiskRequest.setSnapshotId(snapshotId);
		resetDiskRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		ResetDiskResponse response = null;
		try {
			response = client.getAcsResponse(resetDiskRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}
		return response;
	}
	
	/**
	 * 和云海接口有一些出入，比如多了SnapshotId，少了Description和DiskAttachStatus
	 * @param regionId
	 * @param zoneId
	 * @param diskIds
	 * @param instanceId
	 * @param diskType
	 * @param diskName
	 * @param diskCategory
	 * @param status
	 * @param diskChargeType
	 * @param pageNumber
	 * @param pageSize
	 * @param portable
	 * @param appkeyId
	 * @param appkeySecret
	 * @param userEmail
	 * @return
	 */
	public DescribeDisksResponse DescribeDisks(String regionId,String zoneId,String diskIds,String instanceId,String diskType,String diskName,String diskCategory,String status,String diskChargeType,String pageNumber,String pageSize,Boolean portable,String SnapshotId, String appkeyId,String appkeySecret,String userEmail) {
		DescribeDisksRequest describeDisksRequest = new DescribeDisksRequest();
		describeDisksRequest.setRegionId(regionId);
		if (!StringUtils.isEmpty(zoneId)) 
			describeDisksRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(diskIds)) 
			describeDisksRequest.setDiskIds(diskIds);
		if (!StringUtils.isEmpty(instanceId)) 
			describeDisksRequest.setInstanceId(instanceId);
		if (!StringUtils.isEmpty(diskType)) 
			describeDisksRequest.setDiskType(diskType);
		if (!StringUtils.isEmpty(diskName)) 
			describeDisksRequest.setDiskName(diskName);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(diskCategory)) {
			describeDisksRequest.setCategory(diskCategory);
		}
		if (!StringUtils.isEmpty(status)) 
			describeDisksRequest.setStatus(status);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(diskChargeType)) {
			describeDisksRequest.setDiskChargeType(diskChargeType);
		}
		if (!StringUtils.isEmpty(pageNumber)) 
			describeDisksRequest.setPageNumber(Integer.valueOf(pageNumber));
		if (!StringUtils.isEmpty(pageSize)) 
			describeDisksRequest.setPageSize(Integer.valueOf(pageSize));
		//only exist in Aliyun 
		if(null != portable) {
			describeDisksRequest.setPortable(portable);
		}
		if(!StringUtils.isEmpty(SnapshotId)){
			describeDisksRequest.setSnapshotId(SnapshotId);
		}
		describeDisksRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeDisksResponse response = null;
		try {
			response = client.getAcsResponse(describeDisksRequest);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeDisksResponse();
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeDisksResponse();
		}
		return response;
	}
	
	public ModifyDiskAttributeResponse ModifyDiskAttributes(String regionId,String diskId,String diskName,String description,String appkeyId,String appkeySecret,String userEmail) throws ClientException {
		ModifyDiskAttributeRequest request = new ModifyDiskAttributeRequest();
		request.setDiskId(diskId);
		
		if (!StringUtils.isEmpty(diskName)) {
			request.setDiskName(diskName);
		}
		
		if (!StringUtils.isEmpty(description)) {
			request.setDescription(description);
		}
		
		request.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		ModifyDiskAttributeResponse response = null;
		try {
			response = client.getAcsResponse(request);
		} catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			throw e;
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			throw e;
		}
		return response;
		
	}
	
	/*public BaseResponse RenewDisk(String regionId,String diskId,String diskChargeType,String diskChargeLength,String appkeyId,String appkeySecret,String userEmail) {
		RenewDiskRequest renewDiskRequest = new RenewDiskRequest();
		renewDiskRequest.setDiskId(diskId);
		if (!StringUtils.isEmpty(diskChargeType)) 
			renewDiskRequest.setDiskChargeType(diskChargeType);
		if (!StringUtils.isEmpty(diskChargeLength)) 
			renewDiskRequest.setDiskChargeLength(diskChargeLength);
		
		if (!StringUtils.isEmpty(userEmail)) 
			renewDiskRequest.setUserEmail(userEmail);
		
		
		renewDiskRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(renewDiskRequest, BaseResponse.class);
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
		
	}*/
	
}
