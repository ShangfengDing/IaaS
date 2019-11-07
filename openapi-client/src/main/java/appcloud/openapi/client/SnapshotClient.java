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
import appcloud.iaas.sdk.snapshot.CreateSnapshotRequest;
import appcloud.iaas.sdk.snapshot.DeleteSnapshotRequest;
import appcloud.iaas.sdk.snapshot.DescribeSnapshotsRequest;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateSnapshotResponse;
import appcloud.openapi.response.SnapshotsDetailReponse;

public class SnapshotClient {
	public static Logger logger = Logger.getLogger(SnapshotClient.class);
	
	public CreateSnapshotResponse CreateSnapshot(String regionId, String zoneId, String diskId, String snapshotName, String description,
			String appkeyId, String appkeySecret, String userEmail) {
		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest();
		createSnapshotRequest.setDiskId(diskId);
		if (!StringUtils.isEmpty(zoneId)){
			createSnapshotRequest.setZoneId(zoneId);
		}
		if (! StringUtils.isEmpty(snapshotName)) {
			createSnapshotRequest.setSnapshotName(snapshotName);
		}
		if (! StringUtils.isEmpty(description)) {
			createSnapshotRequest.setDescription(description);
		}
		
		createSnapshotRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		try {
			CreateSnapshotResponse response = client.getYhaiResponse(createSnapshotRequest, CreateSnapshotResponse.class);
			return response;
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new CreateSnapshotResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new CreateSnapshotResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
	}
	
	public BaseResponse DeleteSnapshot(String regionId, String zoneId, String snapshotId, String appkeyId, String appkeySecret, String userEmail) {
		DeleteSnapshotRequest deleteSnapshotRequest = new DeleteSnapshotRequest();
		deleteSnapshotRequest.setSnapshotId(snapshotId);
		deleteSnapshotRequest.setZoneId(zoneId);
		deleteSnapshotRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		try {
			BaseResponse response = client.getYhaiResponse(deleteSnapshotRequest, BaseResponse.class);
			return response;
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new BaseResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
	}
	
	public SnapshotsDetailReponse DescribeSnapshot(String regionId, String zoneId, String diskId, String snapshotIds, String snapshotName, String status, String description,
			String instanceId, String sourceDiskType, String usage,String pageNumber, String pageSize,  
			String appkeyId, String appkeySecret, String userEmail) {
		DescribeSnapshotsRequest request = new DescribeSnapshotsRequest();
		request.setRegionId(regionId);
		request.setZoneId(zoneId);
		if (! StringUtils.isEmpty(diskId))
			request.setDiskId(diskId);
		if (! StringUtils.isEmpty(snapshotIds))
			request.setSnapshotIds(snapshotIds);
		if (! StringUtils.isEmpty(snapshotName))
			request.setSnapshotName(snapshotName);
		if (! StringUtils.isEmpty(description))
			request.setDescription(description);
		if (! StringUtils.isEmpty(status)) {
			request.setStatus(status);
		}
		if (! StringUtils.isEmpty(pageNumber)) {
			request.setPageNumber(pageNumber);
		}
		if (! StringUtils.isEmpty(pageSize)) {
			request.setPageSize(pageSize);
		}
		
		//aliyun only
		if (! StringUtils.isEmpty(instanceId)) {
			
		}
		if (! StringUtils.isEmpty(sourceDiskType)) {
			
		}
		if (! StringUtils.isEmpty(usage)) {
			
		}
		
		request.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		try {
			SnapshotsDetailReponse response = client.getYhaiResponse(request, SnapshotsDetailReponse.class);
			return response;
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new SnapshotsDetailReponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			return new SnapshotsDetailReponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
		}
	}
}
