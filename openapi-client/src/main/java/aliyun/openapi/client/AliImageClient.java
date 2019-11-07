package aliyun.openapi.client;

import appcloud.core.sdk.profile.YhaiClientProfile;
import com.aliyuncs.ecs.model.v20140526.CreateImageRequest;
import com.aliyuncs.ecs.model.v20140526.CreateImageResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeImagesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeImagesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliImageClient {

	public static Logger logger = Logger.getLogger(AliImageClient.class);

	/*
	旧版的sdk不支持对整个实例创建模板
	 */
	public CreateImageResponse createAliImage(String regionId,  String snapshotId, String imageName,
										   String imageVersion, String description, String appkeyId,
											  String appkeySecret, String userEmail) throws ClientException {
		CreateImageRequest createImageRequest = new CreateImageRequest();
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(regionId)) {
			createImageRequest.setRegionId(regionId);
		}
		//only exist in Aliyun
		if(!StringUtils.isEmpty(snapshotId)) {
			createImageRequest.setSnapshotId(snapshotId);
		}
		if (!StringUtils.isEmpty(imageName)) 
			createImageRequest.setImageName(imageName);
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(imageVersion)) {
			createImageRequest.setImageVersion(imageVersion);
		}		
		if (!StringUtils.isEmpty(description)) 
			createImageRequest.setDescription(description);
		if (!StringUtils.isEmpty(userEmail))
			createImageRequest.setOwnerAccount(userEmail);
		createImageRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		CreateImageResponse response = null;
		try {
			response = client.getAcsResponse(createImageRequest);
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
	
	/*public BaseResponse DeleteImage(String regionId,String imageId,String appkeyId,String appkeySecret,String userEmail) {
		DeleteImageRequest deleteImageRequest = new DeleteImageRequest();
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(regionId)) {
			//deleteImageRequest.setRegionId(regionId);
		}
		deleteImageRequest.setImageId(imageId);
		if (!StringUtils.isEmpty(userEmail)) 
			deleteImageRequest.setUserEmail(userEmail);
		deleteImageRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(deleteImageRequest, BaseResponse.class);
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
	
	public BaseResponse ModifyImageAttribute(String regionId,String imageId,String imageName,String description,String appkeyId,String appkeySecret,String userEmail) {
		ModifyImageAttributeRequest modifyImageAttributeRequest = new ModifyImageAttributeRequest();
		//only exist in Aliyun 
		if(!StringUtils.isEmpty(regionId)) {
			//modifyImageAttributeRequest.setRegionId(regionId);
		}
		modifyImageAttributeRequest.setImageId(imageId);
		modifyImageAttributeRequest.setImageName(imageName);
		modifyImageAttributeRequest.setDescription(description);
		if (!StringUtils.isEmpty(userEmail)) 
			modifyImageAttributeRequest.setUserEmail(userEmail);
		modifyImageAttributeRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(modifyImageAttributeRequest, BaseResponse.class);
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
	
	public ShowImageDetailResponse GetImageDetail(String regionId,String imageId,String appkeyId,String appkeySecret,String userEmail) {
		GetImageDetailRequest getImageDetailRequest = new GetImageDetailRequest();
		getImageDetailRequest.setImageId(imageId);
		if (!StringUtils.isEmpty(userEmail)) 
			getImageDetailRequest.setUserEmail(userEmail);
		getImageDetailRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		ShowImageDetailResponse response = null;
		try {
			response = client.getYhaiResponse(getImageDetailRequest, ShowImageDetailResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new ShowImageDetailResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new ShowImageDetailResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}*/
	
	public DescribeImagesResponse DescribeImages(String regionId,String imageId,String status,String snapshotId,
			String imageName,String imageOwnerAlias,String usage,String tag_1_Key,String tag_1_Value,String pageNumber,String pageSize,
			String appkeyId,String appkeySecret,String userEmail) {
		DescribeImagesRequest describeImagesRequest = new DescribeImagesRequest();
		if(!StringUtils.isEmpty(regionId)) {
			describeImagesRequest.setRegionId(regionId);
		}
		if(!StringUtils.isEmpty(imageId)) {
			describeImagesRequest.setImageId(imageId);
		}		
		if (!StringUtils.isEmpty(status)) {
			describeImagesRequest.setStatus(status);
		}
		if(!StringUtils.isEmpty(snapshotId)) {
			describeImagesRequest.setSnapshotId(snapshotId);
		}
		if(!StringUtils.isEmpty(imageName)) {
			describeImagesRequest.setImageName(imageName);
		}		
		if(!StringUtils.isEmpty(imageOwnerAlias)) {
			describeImagesRequest.setImageOwnerAlias(imageOwnerAlias);
		}		
		if(!StringUtils.isEmpty(usage)) {
			describeImagesRequest.setUsage(usage);
		}		
		if(!StringUtils.isEmpty(tag_1_Key)) {
			describeImagesRequest.setTag1Key(tag_1_Key);
		}
		if(!StringUtils.isEmpty(tag_1_Value)) {
			describeImagesRequest.setTag1Value(tag_1_Value);
		}		
		if (!StringUtils.isEmpty(pageNumber)) {
			describeImagesRequest.setPageNumber(Integer.parseInt(pageNumber));
		}
		if (!StringUtils.isEmpty(pageSize)) {
			describeImagesRequest.setPageSize(Integer.parseInt(pageSize));
		}
		if (!StringUtils.isEmpty(userEmail)) {
			describeImagesRequest.setOwnerAccount(userEmail);
		}
		describeImagesRequest.setAcceptFormat(FormatType.XML);
		IClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		DescribeImagesResponse response = null;
		try {
			response = client.getAcsResponse(describeImagesRequest);
		}catch (ServerException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result,e);
			response = new DescribeImagesResponse();
		}catch (ClientException e) {
			String result = e.getErrCode() + " : " + e.getMessage();
			logger.error(result,e);
			response = new DescribeImagesResponse();
		}
		return response;
	}

	public static void main(String args[]) {
		AliImageClient aliImageClient = new AliImageClient();
		try {
			aliImageClient.createAliImage("cn-qingdao","s-m5egabytb637c0kxu5ho","ali-test1",null,"tewa","JuoGD9uJhHCP29t8","i1OKq3vv6Nfz7PhTQVReGS5lghZ700",null);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}


