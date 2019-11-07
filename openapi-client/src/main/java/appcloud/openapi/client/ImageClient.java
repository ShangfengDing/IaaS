package appcloud.openapi.client;

import appcloud.openapi.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.profile.DefaultProfile;
import appcloud.core.sdk.profile.YhaiClientProfile;
import appcloud.core.sdk.utils.DefaultYhaiClient;
import appcloud.core.sdk.utils.YhaiClient;
import appcloud.iaas.sdk.image.*;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateImageResponse;
import appcloud.openapi.response.GetImageListResponse;
import appcloud.openapi.response.ShowImageDetailResponse;

public class ImageClient {

	public static Logger logger = Logger.getLogger(ImageClient.class);

    /**
     * 创建镜像
     * //TODO 区分用户群组
     */
	public CreateImageResponse createImage(String regionId,String zoneId, String snapshotId,String serverId,String volumeId,String imageName,
			String imageVersion,String description,String distribution, String software, String account,
			String appkeyId,String appkeySecret,String userEmail) {
		CreateImageRequest createImageRequest = new CreateImageRequest();
		createImageRequest.setRegionId(regionId);
		createImageRequest.setZoneId(zoneId);
		//only exist in Aliyun
		if(!StringUtils.isEmpty(snapshotId)) {
			//createImageRequest.setSnapshotId(snapshotId);
		}
		createImageRequest.setServerId(serverId);
		createImageRequest.setVolumeId(volumeId);
		createImageRequest.setSoftware(software);
		createImageRequest.setAccount(account);
		if (!StringUtils.isEmpty(imageName))
			createImageRequest.setImageName(imageName);
		//only exist in Aliyun
		if(!StringUtils.isEmpty(imageVersion)) {
			//createImageRequest.setImageVersion(imageVersion);
		}
		if (!StringUtils.isEmpty(description))
			createImageRequest.setDescription(description);
		if (!StringUtils.isEmpty(distribution))
			createImageRequest.setDistribution(distribution);
		if (!StringUtils.isEmpty(userEmail))
			createImageRequest.setUserEmail(userEmail);
		createImageRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		CreateImageResponse response = null;
		try {
			response = client.getYhaiResponse(createImageRequest, CreateImageResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new CreateImageResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new CreateImageResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}

    /**
     * 修改镜像为群组的公共镜像
     */
    public BaseResponse authorizeImage (String regionId, String zoneId, String imageId,String appkeyId,String appkeySecret,String userEmail ) {
		AuthorizeImageRequest changeImageRequest = new AuthorizeImageRequest();
		//only exist in Aliyun
		if(!StringUtils.isEmpty(regionId)) {
			changeImageRequest.setRegionId(regionId);
		}
		changeImageRequest.setZoneId(zoneId);
		changeImageRequest.setImageId(imageId);
		if (!StringUtils.isEmpty(userEmail))
			changeImageRequest.setUserEmail(userEmail);
		changeImageRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		BaseResponse response = null;
		try {
			response = client.getYhaiResponse(changeImageRequest, BaseResponse.class);
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
     * 删除镜像
     */
	public BaseResponse DeleteImage(String regionId,String zoneId,String imageId,String appkeyId,String appkeySecret,String userEmail) {
		DeleteImageRequest deleteImageRequest = new DeleteImageRequest();
		//only exist in Aliyun
		if(!StringUtils.isEmpty(regionId)) {
			deleteImageRequest.setRegionId(regionId);
		}
		deleteImageRequest.setZoneId(zoneId);
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

	/**
	 * 修改镜像
	 */
	public BaseResponse ModifyImageAttribute(String regionId,String zoneId, String imageId,String imageName,String description,
											 String software, String account,String appkeyId,String appkeySecret,String userEmail) {
		ModifyImageAttributeRequest modifyImageAttributeRequest = new ModifyImageAttributeRequest();
		//only exist in Aliyun
		if(!StringUtils.isEmpty(regionId)) {
			modifyImageAttributeRequest.setRegionId(regionId);
		}
		modifyImageAttributeRequest.setZoneId(zoneId);
		modifyImageAttributeRequest.setImageId(imageId);
		modifyImageAttributeRequest.setImageName(imageName);
		modifyImageAttributeRequest.setDescription(description);
		modifyImageAttributeRequest.setSoftware(software);
		modifyImageAttributeRequest.setAccount(account);
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

	/**
	 * 查询镜像的详情
	 * @return
	 */
	public ShowImageDetailResponse GetImageDetail(String regionId,String zoneId,String imageId,String appkeyId,String appkeySecret,String userEmail) {
		GetImageDetailRequest getImageDetailRequest = new GetImageDetailRequest();
		getImageDetailRequest.setImageId(imageId);
		getImageDetailRequest.setRegionId(regionId);
		if (! StringUtils.isEmpty(zoneId)) {
			getImageDetailRequest.setZoneId(zoneId);
		}
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
	}

	/**
	 * 根据条件查询镜像
	 * imageType、status、pageNum、pageSize、userEmail、imageName
     * 新增type： all-查找全部的， PUBLIC-平台共享，GROUP-群组共享，PRIVATE-个人
	 * @return
	 */
	public GetImageListResponse DescribeImages(String regionId, String zoneId, String imageType,String imageId,String status,String snapshotId,
			String imageName,String imageDescription, String imageOwnerAlias,String type, String distribution, String software, String usage,String tag_1_Key,String tag_1_Value, String pageNumber,String pageSize,
			String appkeyId,String appkeySecret,String userEmail) {
		DescribeImagesRequest describeImagesRequest = new DescribeImagesRequest();
		//only exist in Aliyun
		if(!StringUtils.isEmpty(regionId)) {
			describeImagesRequest.setRegionId(regionId);
		}
		describeImagesRequest.setZoneId(zoneId);
		if (!StringUtils.isEmpty(imageType)) {
			describeImagesRequest.setImageType(imageType);
		} else {
			describeImagesRequest.setImageType(Constants.IMAGE_TYPE_IMAGE);
		}
		if (!StringUtils.isEmpty(distribution)) {
			describeImagesRequest.setDistribution(distribution);
		}
		if (!StringUtils.isEmpty(software)) {
			describeImagesRequest.setSoftware(software);
		}
		//only exist in Aliyun
		if(!StringUtils.isEmpty(imageId)) {
			//describeImagesRequest.setImageId(imageId);
		}
		if (!StringUtils.isEmpty(status))
			describeImagesRequest.setStatus(status);
		//only exist in Aliyun
		if(!StringUtils.isEmpty(snapshotId)) {
			//describeImagesRequest.setSnapshotId(snapshotId);
		}
		//only exist in Aliyun
		if(!StringUtils.isEmpty(imageOwnerAlias)) {
			//describeImagesRequest.setImageOwnerAlias(imageOwnerAlias);
		}
		//only exist in Aliyun
		if(!StringUtils.isEmpty(usage)) {
			//describeImagesRequest.setUsage(usage);
		}
		//only exist in Aliyun
		if(!StringUtils.isEmpty(tag_1_Key)) {
			//describeImagesRequest.setTag_1_Key(tag_1_Key);
		}
		//only exist in Aliyun
		if(!StringUtils.isEmpty(tag_1_Value)) {
			//describeImagesRequest.setTag_1_Value(tag_1_Value);
		}
		if (!StringUtils.isEmpty(type)) {
		    describeImagesRequest.setType(type);
        }
		if (!StringUtils.isEmpty(pageNumber))
			describeImagesRequest.setPageNumber(pageNumber);
		if (!StringUtils.isEmpty(pageSize))
			describeImagesRequest.setPageSize(pageSize);
		if (!StringUtils.isEmpty(userEmail))
			describeImagesRequest.setUserEmail(userEmail);
		if(!StringUtils.isEmpty(imageName)) {
			describeImagesRequest.setImageName(imageName);
		}
		if (!StringUtils.isEmpty(imageDescription)) {
			describeImagesRequest.setImageDescription(imageDescription);
		}
		describeImagesRequest.setAcceptFormat(FormatType.XML);
		YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
		YhaiClient client = new DefaultYhaiClient(profile);
		GetImageListResponse response = null;
		try {
			response = client.getYhaiResponse(describeImagesRequest, GetImageListResponse.class);
		}catch (ServerException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new GetImageListResponse(null, e.getErrorCode(), e.getErrorMessage());
		}catch (ClientException e) {
			String result = e.getErrorCode() + " : " + e.getErrorMessage();
			logger.warn(result,e);
			response = new GetImageListResponse(null, e.getErrorCode(), e.getErrorMessage());
		}
		return response;
	}


	public static void main(String[] args) {

		String regionId = "beijing";
		String zoneId = "bupt";
		String diskSize = "20";
		String diskName = "diskceshi";
		String diskCategory = null;
		String description = null;
		String snapshotId = "48edae17f23b424fa7bd8e3bd4569653";
		String appkeyId = "f00577d2c1764688be5e483a21f4fe92";
		String appkeySecret = "1KS2AMYX0UEjvQA6sVYK1AXfRpvrtArQ";
		String userEmail = "free@free4lab.com";
		String imageId = "0d3bf40d2a9547d682447b4dd72363b6";
		String diskId = "951434b2a49947e78528ebdf9e4a70aa";
		String instanceId = "6d16b0d3194c439a9069e40b69a4139a";
		String device = null;
		String deleteWithInstance = null;
		String diskIds = null;
		String diskType = null;
		String status = null;
		String diskAttachStatus = null;
		String diskChargeTyp = null;
		String pageSize = null;
		String pageNumber = null;
		String portable = null;




//		logger.info("getImageListResponse");
//		GetImageListResponse getImageListResponse = new ImageClient().DescribeImages(regionId, null, null, "deleting", null, null, null, null, null, null, null, null, appkeyId, appkeySecret, userEmail);
//		logger.info(getImageListResponse);
//
//		logger.info("getImageDetail");
//		ShowImageDetailResponse getImageDetail = new ImageClient().GetImageDetail(regionId, imageId, appkeyId, appkeySecret, userEmail);
//		logger.info(getImageDetail);
//
//		logger.info("modifyImageAttribute");
//		BaseResponse modifyImageAttribute = new ImageClient().ModifyImageAttribute(regionId, imageId, "hehe", "hehehe", appkeyId, appkeySecret, userEmail);
//		logger.info(modifyImageAttribute);

		CreateImageResponse createImageResponse = new ImageClient().createImage(regionId,null,null,"a7cda4141a2d4fa99384c96f7037e053","45bc3ef86bdf4d7ebaa654f02883a712","test",
				"description","test","centos_distribution","software", "account", "0c6ed430f7dc4de998fbbe6da69fbc62","LP9DHuGQJ09SnImaw1NXb8L5OVbGFVBH","wwssxxx@126.com");
		logger.info(createImageResponse);
	}

}
