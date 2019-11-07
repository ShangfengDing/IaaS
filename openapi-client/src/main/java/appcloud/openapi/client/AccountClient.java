package appcloud.openapi.client;

import appcloud.core.sdk.common.Constants;
import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.profile.DefaultProfile;
import appcloud.core.sdk.profile.YhaiClientProfile;
import appcloud.core.sdk.utils.DefaultYhaiClient;
import appcloud.core.sdk.utils.YhaiClient;
import appcloud.iaas.sdk.account.GroupCreateRequest;
import appcloud.iaas.sdk.account.UserCreateForDisRequest;
import appcloud.iaas.sdk.account.UserCreateRequest;
import appcloud.openapi.response.GroupCreateResponse;
import appcloud.openapi.response.UserCreateForDisResponse;
import appcloud.openapi.response.UserCreateResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by Idan on 2017/10/19.
 * 账户相关的client
 * 1. 添加用户
 * 2. 添加群组
 */
public class AccountClient {

    public static Logger logger = Logger.getLogger(AdminClient.class);

    public AccountClient(){}

    //新增用户接口
    public UserCreateResponse userCreate(String newUserEmail, String groupSecretKey, String appkeyId, String appkeySecret,String userEmail) {
        UserCreateRequest request = new UserCreateRequest();

        if (!StringUtils.isEmpty(newUserEmail)) {
            request.setNewUserEmail(newUserEmail);
        }

        if (!StringUtils.isEmpty(userEmail)) {
            request.setUserEmail(userEmail);
        }

        if (!StringUtils.isEmpty(groupSecretKey)) {
            request.setGroupSecretKey(groupSecretKey);
        }

        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(Constants.REGION_ID, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        UserCreateResponse response;

        try {
            response = client.getYhaiResponse(request,UserCreateResponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new UserCreateResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new UserCreateResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }

    //新增用户接口
    public UserCreateForDisResponse userCreateForDistributed(String regoinId, String zoneId, String newUserEmail, String groupSecretKey, String appkeyId, String appkeySecret, String userEmail, String accountName) {
        UserCreateForDisRequest request = new UserCreateForDisRequest();
        request.setRegionId(regoinId);
        if (!StringUtils.isEmpty(zoneId)) {
            request.setZoneId(zoneId);
        }
        if (!StringUtils.isEmpty(accountName)) {
            request.setAccountName(accountName);
        }
        if (!StringUtils.isEmpty(newUserEmail)) {
            request.setNewUserEmail(newUserEmail);
        }

        if (!StringUtils.isEmpty(userEmail)) {
            request.setUserEmail(userEmail);
        }

        if (!StringUtils.isEmpty(groupSecretKey)) {
            request.setGroupSecretKey(groupSecretKey);
        }

        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(Constants.REGION_ID, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        UserCreateForDisResponse response;

        try {
            response = client.getYhaiResponse(request,UserCreateForDisResponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new UserCreateForDisResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new UserCreateForDisResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }

    //新增群组接口
    public GroupCreateResponse groupCreate(String name, String description, String appkeyId, String appkeySecret, String userEmail) {
        GroupCreateRequest request = new GroupCreateRequest();

        if (!StringUtils.isEmpty(userEmail)) {
            request.setUserEmail(userEmail);
        }

        if (!StringUtils.isEmpty(name)) {
            request.setName(name);
        }

        if (!StringUtils.isEmpty(description)) {
            request.setDescription(description);
        }

        request.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(Constants.REGION_ID, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        GroupCreateResponse response = null;

        try {
            response = client.getYhaiResponse(request,GroupCreateResponse.class);
        } catch (ServerException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new GroupCreateResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String result = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(result, e);
            response = new GroupCreateResponse(e.getRequestId(), e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }

    public static void main (String args[]) {
        String name = "groupName";
        String description = "description";
        String appkeyId = "f00577d2c1764688be5e483a21f4fe92";
        String appkeySecret = "1KS2AMYX0UEjvQA6sVYK1AXfRpvrtArQ";
        String userEmail = "free@free4lab.com";
        String groupSecretKey = "328dd3acb6d5408e88affd2e792f2177";
        String newUserEmail = "yumike18@126.com";

//        GroupCreateResponse baseResponse = new AccountClient().groupCreate(name, description, appkeyId, appkeySecret, userEmail);
        UserCreateForDisResponse baseResponse = new AccountClient().userCreateForDistributed("BEIJING", "lab", newUserEmail, groupSecretKey, appkeyId, appkeySecret, userEmail,"accountname");
        System.out.println(baseResponse.getMessage());
    }

}
