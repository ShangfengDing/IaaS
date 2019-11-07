package aliyun.openapi.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.CreateSnapshotRequest;
import com.aliyuncs.ecs.model.v20140526.CreateSnapshotResponse;
import com.aliyuncs.ecs.model.v20140526.DeleteSnapshotRequest;
import com.aliyuncs.ecs.model.v20140526.DeleteSnapshotResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeSnapshotsRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeSnapshotsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliSnapshotClient {
	public static Logger logger = Logger.getLogger(AliSnapshotClient.class);
	
	public CreateSnapshotResponse CreateSnapshot(String regionId, String diskId, String snapshotName, String description, 
			String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest();
		createSnapshotRequest.setDiskId(diskId);
		if (! StringUtils.isEmpty(snapshotName)) {
			createSnapshotRequest.setSnapshotName(snapshotName);
		}
		if (! StringUtils.isEmpty(description)) {
			createSnapshotRequest.setDescription(description);
		}
		
		createSnapshotRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		try {
			CreateSnapshotResponse response = client.getAcsResponse(createSnapshotRequest);
			return response;
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
			//return new CreateSnapshotResponse();
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
			//return new CreateSnapshotResponse();
		}
	}
	
	public DeleteSnapshotResponse DeleteSnapshot(String regionId, String snapshotId, String appkeyId, String appkeySecret, String userEmail) throws ClientException {
		DeleteSnapshotRequest deleteSnapshotRequest = new DeleteSnapshotRequest();
		deleteSnapshotRequest.setSnapshotId(snapshotId);
		deleteSnapshotRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		try {
			DeleteSnapshotResponse response = client.getAcsResponse(deleteSnapshotRequest);
			return response;
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getErrMsg();
			logger.warn(result,e);
			throw e;
		}
	}
	
	public DescribeSnapshotsResponse DescribeSnapshot(String regionId, String diskId, String snapshotIds, String snapshotName, String status, String description, 
			String instanceId, String sourceDiskType, String usage,String pageNumber, String pageSize,  
			String appkeyId, String appkeySecret, String userEmail) {
		DescribeSnapshotsRequest request = new DescribeSnapshotsRequest();
		request.setRegionId(regionId);
		if (! StringUtils.isEmpty(diskId))
			request.setDiskId(diskId);
		if (! StringUtils.isEmpty(snapshotIds))
			request.setSnapshotIds(snapshotIds);
		if (! StringUtils.isEmpty(snapshotName))
			request.setSnapshotName(snapshotName);
		/*if (! StringUtils.isEmpty(description))
			request.setDescription(description);*/
		if (! StringUtils.isEmpty(status)) {
			request.setStatus(status);
		}
		if (! StringUtils.isEmpty(pageNumber)) {
			request.setPageNumber(Integer.valueOf(pageNumber));
		}
		if (! StringUtils.isEmpty(pageSize)) {
			request.setPageSize(Integer.valueOf(pageSize));
		}
		
		//aliyun only
		if (! StringUtils.isEmpty(instanceId)) {
			request.setInstanceId(instanceId);
		}
		if (! StringUtils.isEmpty(sourceDiskType)) {
			request.setSourceDiskType(sourceDiskType);
		}
		if (! StringUtils.isEmpty(usage)) {
			request.setUsage(usage);
		}
		
		request.setAcceptFormat(FormatType.XML);
		
		IClientProfile profile = DefaultProfile.getProfile(regionId,appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeSnapshotsResponse response = null;
		
		try {
			response = client.getAcsResponse(request);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeSnapshotsResponse();
		} catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result, e);
			response = new DescribeSnapshotsResponse();
		}
		return response;
	}
}
