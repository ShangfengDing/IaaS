package appcloud.distributed.process.operate;

import appcloud.distributed.configmanager.VersionInfo;
import appcloud.openapi.datatype.AppkeyItem;
import appcloud.openapi.response.UserCreateForDisResponse;

/**
 * Created by Idan on 2018/1/13.
 */
public interface AccountOperate {

    /**
     * 1. front 发过来的操作用户的请求,master操作用户的请求
     */
    UserCreateForDisResponse createUser(String requestId,String regionId, String zoneId, String newUserEmail, String groupSecret, String appkeyId, String appkeySecret, String accountName);

    /**
     * 2. 添加日志
     * @return
     */
    VersionInfo addUserVersion(String requestId,AppkeyItem appkey);


    // 3. master 将创建的消息分发
    Boolean dispatchAccount(String requestId,VersionInfo versionInfo);

}
