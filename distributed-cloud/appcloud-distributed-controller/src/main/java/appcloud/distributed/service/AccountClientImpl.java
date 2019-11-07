package appcloud.distributed.service;

import appcloud.distributed.CloudControllerClientWapper;
import appcloud.distributed.service.check.AccountChecker;
import appcloud.netty.remoting.common.RequestCode;
import appcloud.netty.remoting.protocol.RemoteCommand;
import com.distributed.common.response.BaseResponse;
import com.distributed.common.response.util.HttpConstants;
import com.distributed.common.service.controller.AccountClient;
import com.distributed.common.utils.ModelUtil;
import com.distributed.common.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Idan on 2018/1/15.
 */
public class AccountClientImpl implements AccountClient {

    private final Logger logger = LoggerFactory.getLogger(AccountClientImpl.class);

    private CloudControllerClientWapper clientWapper = CloudControllerClientWapper.getInstance();

    public AccountClientImpl() {}

    @Override
    public BaseResponse userCreate(String regionId, String zoneId, String newUserEmail, String groupSecretKey, String appkeyId, String appkeySecret, String userEmail,String accountName) {
        String requestId = UuidUtil.getRandomUuid();
        if (!AccountChecker.createUserCheck(newUserEmail, groupSecretKey, appkeyId)) {
            return new BaseResponse(requestId, HttpConstants.STATUS_FORBIDDEN, "传入参数不对");
        }
        logger.info("regionId: "+regionId+", zoneId: "+zoneId+", userEmail: "+newUserEmail+", groupSecretKey:"+groupSecretKey);
        RemoteCommand remoteCommand = clientWapper.sendCreateUserRequest(requestId, regionId, zoneId, newUserEmail, groupSecretKey, appkeyId, appkeySecret,accountName);
        logger.info("the requestId finish.");
        if (ModelUtil.isNotEmpty(remoteCommand) && remoteCommand.getCode()== RequestCode.SUCCESS) {
            return new BaseResponse(requestId, HttpConstants.STATUS_OK, null);
        } else {
            return new BaseResponse(requestId, HttpConstants.STATUS_INTERNAL_SERVER_ERROR, "服务器内部出错");
        }
    }
}
