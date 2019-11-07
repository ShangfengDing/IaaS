package appcloud.openapi.client;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.profile.DefaultProfile;
import appcloud.core.sdk.profile.YhaiClientProfile;
import appcloud.core.sdk.utils.DefaultYhaiClient;
import appcloud.core.sdk.utils.YhaiClient;
import appcloud.iaas.sdk.volume.*;
import appcloud.openapi.response.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.EnumSet;

public class VolumeClient {

	public static Logger logger = Logger.getLogger(VolumeClient.class);
	
	public CreateDiskResponse CreateDisk(String regionId,String zoneId,String diskSize,String diskChargeType,String diskChargeLength,String diskName,String diskCategory,String description,String snapshotId,String appkeyId,String appkeySecret,String userEmail) {
		CreateDiskRequest createDiskRequest = new CreateDiskRequest();
		createDiskRequest.setRegionId(regionId);
		createDiskRequest.setDiskSize(diskSize);
		if (!StringUtils.isEmpty(zoneId))
			createDiskRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(diskChargeType)) 
			createDiskRequest.setDiskChargeType(diskChargeType);
		if (!StringUtils.isEmpty(diskChargeLength)) 
			createDiskRequest.setDiskChargeLength(diskChargeLength);
		if (!StringUtils.isEmpty(diskName)) 
			createDiskRequest.setDiskName(diskName);
		if (!StringUtils.isEmpty(diskCategory)) 
			createDiskRequest.setDiskCategory(diskCategory);
		if (!StringUtils.isEmpty(description)) 
			createDiskRequest.setDescription(description);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(snapshotId)) {
			//createDiskRequest.setSnapshotId(snapshotId);
		}
		if (!StringUtils.isEmpty(userEmail)) 
			createDiskRequest.setUserEmail(userEmail);
		createDiskRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		CreateDiskResponse response = null;
		try {
			response = client.getYhaiResponse(createDiskRequest, CreateDiskResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new CreateDiskResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new CreateDiskResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}


	public CreateDiskImageBackResponse CreateDiskImageBack(String regionId, String zoneId, String diskUuid, String diskName, String description, String appkeyId, String appkeySecret, String userEmail) {
		CreateDiskImageBackRequest imageBackRequest = new CreateDiskImageBackRequest();
		imageBackRequest.setRegionId(regionId);
		imageBackRequest.setZoneId(zoneId);
		imageBackRequest.setDiskUuid(diskUuid);
		if (!StringUtils.isEmpty(diskName))
			imageBackRequest.setDiskName(diskName);
		if (!StringUtils.isEmpty(description))
			imageBackRequest.setDescription(description);

		if (!StringUtils.isEmpty(userEmail))
			imageBackRequest.setUserEmail(userEmail);
		imageBackRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		CreateDiskImageBackResponse response = null;
		try {
			response = client.getYhaiResponse(imageBackRequest, CreateDiskImageBackResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new CreateDiskImageBackResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new CreateDiskImageBackResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}

	public BaseResponse AttachDisk(String regionId,String zoneId,String diskId,String instanceId,String device,String deleteWithInstance,String appkeyId,String appkeySecret,String userEmail) {
		AttachDiskRequest attachDiskRequest = new AttachDiskRequest();
		attachDiskRequest.setZoneId(zoneId);
		attachDiskRequest.setDiskId(diskId);
		attachDiskRequest.setInstanceId(instanceId);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(device)) {
			//attachDiskRequest.setDevice(device);
		}
		if(!StringUtils.isEmpty(deleteWithInstance)) {
			//attachDiskRequest.setDeleteWithInstance(deleteWithInstance);
		}
		if (!StringUtils.isEmpty(userEmail)) 
			attachDiskRequest.setUserEmail(userEmail);
		attachDiskRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(attachDiskRequest, BaseResponse.class);
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
	
	public BaseResponse DetachDisk(String regionId,String zoneId,String diskId,String instanceId,String appkeyId,String appkeySecret,String userEmail) {
		DetachDiskRequest detachDiskRequest = new DetachDiskRequest();
		detachDiskRequest.setDiskId(diskId);
		detachDiskRequest.setZoneId(zoneId);
		detachDiskRequest.setInstanceId(instanceId);
		if (!StringUtils.isEmpty(userEmail)) 
			detachDiskRequest.setUserEmail(userEmail);
		detachDiskRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(detachDiskRequest, BaseResponse.class);
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
	
	public BaseResponse DeleteDisk(String regionId,String zoneId,String diskId,String appkeyId,String appkeySecret,String userEmail) {
		DeleteDiskRequest deleteDiskRequest = new DeleteDiskRequest();
		deleteDiskRequest.setDiskId(diskId);
		deleteDiskRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(userEmail)) 
			deleteDiskRequest.setUserEmail(userEmail);
		deleteDiskRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(deleteDiskRequest, BaseResponse.class);
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
	
	public BaseResponse ResetDisk(String regionId, String zoneId, String diskId,String snapshotId,String appkeyId,String appkeySecret,String userEmail) {
		ResetDiskRequest resetDiskRequest = new ResetDiskRequest();
		resetDiskRequest.setDiskId(diskId);
		resetDiskRequest.setZoneId(zoneId);
		resetDiskRequest.setSnapshotId(snapshotId);
		if (!StringUtils.isEmpty(userEmail)) 
			resetDiskRequest.setUserEmail(userEmail);
		resetDiskRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(resetDiskRequest, BaseResponse.class);
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
	
	public DisksDetailReponse DescribeDisks(String regionId,String zoneId,String diskIds,String instanceId,String diskType,String diskName,String diskCategory,String description,String status,String diskAttachStatus,String diskChargeType,String pageNumber,String pageSize,String portable,String appkeyId,String appkeySecret,String userEmail) {
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
			//describeDisksRequest.setDiskCategory(diskCategory);
		}
		if (!StringUtils.isEmpty(description)) 
			describeDisksRequest.setDescription(description);
		if (!StringUtils.isEmpty(status)) 
			describeDisksRequest.setStatus(status);
		if (!StringUtils.isEmpty(diskAttachStatus)) 
			describeDisksRequest.setDiskAttachStatus(diskAttachStatus);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(diskChargeType)) {
			//describeDisksRequest.setDiskChargeType(diskChargeType);
		}
		if (!StringUtils.isEmpty(pageNumber)) 
			describeDisksRequest.setPageNumber(pageNumber);
		if (!StringUtils.isEmpty(pageSize)) 
			describeDisksRequest.setPageSize(pageSize);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(portable)) {
			//describeDisksRequest.setPortable(portable);
		}		
		if (!StringUtils.isEmpty(userEmail)) 
			describeDisksRequest.setUserEmail(userEmail);
		describeDisksRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DisksDetailReponse response = null;
		try {
			response = client.getYhaiResponse(describeDisksRequest, DisksDetailReponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DisksDetailReponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DisksDetailReponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}

	public DescribeDiskImageBackResponse DescribeDiskImageBack(String regionId, String zoneId,String instanceId,String volumeUuid, String activeVolumeUuid, String imageBackStatus, String diskType, String volumeUsageType, String isBackUp, String isTop, String diskName, String appkeyId, String appkeySecret, String userEmail) {
		DescribeDiskImageBackRequest describeDiskImageBackRequest = new DescribeDiskImageBackRequest();
		describeDiskImageBackRequest.setRegionId(regionId);
		if (!StringUtils.isEmpty(zoneId))
			describeDiskImageBackRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(instanceId))
			describeDiskImageBackRequest.setInstanceId(instanceId);
		if (!StringUtils.isEmpty(diskType))
			describeDiskImageBackRequest.setDiskType(diskType);
		if (!StringUtils.isEmpty(diskName))
			describeDiskImageBackRequest.setDiskName(diskName);
		if (!StringUtils.isEmpty(imageBackStatus))
			describeDiskImageBackRequest.setImageBackStatus(imageBackStatus);
		if (!StringUtils.isEmpty(volumeUuid)) {
			describeDiskImageBackRequest.setVolumeUuid(volumeUuid);
		}
		if (!StringUtils.isEmpty(activeVolumeUuid)) {
			describeDiskImageBackRequest.setActiveVolumeUuid(activeVolumeUuid);
		}
		if (!StringUtils.isEmpty(volumeUsageType)) {
			describeDiskImageBackRequest.setVolumeUsageType(volumeUsageType);
		}
		if (!StringUtils.isEmpty(isBackUp)) {
			describeDiskImageBackRequest.setBackUp(isBackUp);
		}
		if (!StringUtils.isEmpty(isTop)) {
			describeDiskImageBackRequest.setTop(isTop);
		}
		if (!StringUtils.isEmpty(userEmail))
			describeDiskImageBackRequest.setUserEmail(userEmail);
		describeDiskImageBackRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		DescribeDiskImageBackResponse response = null;
		try {
			response = client.getYhaiResponse(describeDiskImageBackRequest, DescribeDiskImageBackResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeDiskImageBackResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new DescribeDiskImageBackResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}

	public BaseResponse ModifyDiskAttributes(String regionId,String zoneId, String diskId,String diskName,String description,String appkeyId,String appkeySecret,String userEmail) {
		ModifyDiskAttributesRequest request = new ModifyDiskAttributesRequest();
		request.setDiskId(diskId);
		request.setZoneId(zoneId);
		
		if (!StringUtils.isEmpty(diskName)) {
			request.setDiskName(diskName);
		}
		
		if (!StringUtils.isEmpty(description)) {
			request.setDescription(description);
		}
		
		if (!StringUtils.isEmpty(userEmail)) 
			request.setUserEmail(userEmail);


		request.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(request, BaseResponse.class);
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

	/**
	 * @param isBackUp String的true或false
	 * @param isTop String的true或false
	 * @return
	 */
	public BaseResponse ModifyDiskImageBack(String regionId,String zoneId,String diskId,String imageBackStatus, String volumeType, String volumeUsageType, String isBackUp, String isTop, String diskName,String appkeyId,String appkeySecret,String userEmail) {
		ModifyDiskImageBackRequest request = new ModifyDiskImageBackRequest();
		request.setDiskId(diskId);
		request.setZoneId(zoneId);

		if (!StringUtils.isEmpty(diskName)) {
			request.setDiskName(diskName);
		}
		if (!StringUtils.isEmpty(imageBackStatus)) {
			request.setImageBackStatus(imageBackStatus);
		}
		if (!StringUtils.isEmpty(volumeType)) {
			request.setVolumeType(volumeType);
		}
		if (!StringUtils.isEmpty(volumeUsageType)) {
			request.setVolumeUsageType(volumeUsageType);
		}
		if (!StringUtils.isEmpty(isBackUp)) {
			request.setBackUp(isBackUp);
		}
		if (!StringUtils.isEmpty(isTop)) {
			request.setTop(isTop);
		}

		if (!StringUtils.isEmpty(userEmail))
			request.setUserEmail(userEmail);


		request.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(request, BaseResponse.class);
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
	
	public BaseResponse RenewDisk(String regionId,String zoneId,String diskId,String diskChargeType,String diskChargeLength,String appkeyId,String appkeySecret,String userEmail) {
		RenewDiskRequest renewDiskRequest = new RenewDiskRequest();
		renewDiskRequest.setDiskId(diskId);
		renewDiskRequest.setZoneId(zoneId);
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
		
	}


	public static void main(String[] args) {
		
		String regionId = "beijing";
		String zoneId = "lab";
		String diskSize = "20";
		String diskName = "diskceshi";
		String diskCategory = null;
		String description = null;
		String snapshotId = "d1fc36c5354a475f93b63deecee8a206";
		String appkeyId = "656acbb67965435a8ed083f4e4a98533";
		String appkeySecret = "G5JVZui0oYarWtStjcL5r4ePONgz6ZjR";
		String userEmail = "yumike18@126.com";
		String diskId = "a5fee72f970b479cb9febf416c07a3cc";
		String instanceId = "6d16b0d3194c439a9069e40b69a4139a";
		String device = null;
		String deleteWithInstance = null;
		String diskIds = null;
		String diskType = null;
		String status = null;
		String diskAttachStatus = null;
		String diskChargeType = "PayByHour";
		String diskChargeLength = "3";
		String pageSize = null;
		String pageNumber = null;
		String portable = null;
//		logger.info("renew disk");
//		BaseResponse renewResponse = new VolumeClient().RenewDisk(regionId, diskId, diskChargeType, diskChargeLength, appkeyId, appkeySecret, userEmail);
//		logger.info(renewResponse);
//		logger.info("create disk");
//		CreateDiskResponse createDiskResponse = new VolumeClient().CreateDisk(regionId, zoneId, diskSize, "PayByYear",null,diskName, diskCategory, description, snapshotId, appkeyId, appkeySecret, userEmail);
//		logger.info(createDiskResponse);
//		logger.info("attach disk");
//		BaseResponse attachDiskResponse = new VolumeClient().AttachDisk(diskId, instanceId, device, deleteWithInstance, appkeyId, appkeySecret, userEmail);
//		logger.info(attachDiskResponse);
//		logger.info("detach disk");
//		BaseResponse detachDiskResponse = new VolumeClient().DetachDisk(diskId, instanceId, appkeyId, appkeySecret, userEmail);
//		logger.info(detachDiskResponse);
//		logger.info("delete disk");
//		BaseResponse deleteDiskResponse = new VolumeClient().DeleteDisk(diskId, appkeyId, appkeySecret, userEmail);
//		logger.info(deleteDiskResponse);
//		logger.info("reset disk");
//		BaseResponse resetDiskResponse = new VolumeClient().ResetDisk(diskId, snapshotId, appkeyId, appkeySecret, userEmail);
//		logger.info(resetDiskResponse);
//		logger.info("disksDetail");
//		DisksDetailReponse disksDetail = new VolumeClient().DescribeDisks(regionId, zoneId, diskIds, instanceId, diskType, null, diskCategory, description, status, diskAttachStatus, diskType, pageSize, pageNumber, portable, appkeyId, appkeySecret, userEmail);
//		logger.info(disksDetail.getTotalCount());
//		System.out.println(diskId);
		CreateDiskImageBackResponse response = new VolumeClient().CreateDiskImageBack(regionId, zoneId, diskId, null, null, appkeyId, appkeySecret, userEmail);
		System.out.println(response.getDiskId());
	}
	
}
