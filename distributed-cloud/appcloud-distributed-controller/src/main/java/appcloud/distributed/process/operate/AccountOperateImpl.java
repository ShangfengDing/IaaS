package appcloud.distributed.process.operate;

import appcloud.distributed.CloudControllerClientWapper;
import appcloud.distributed.annotation.SynMethod;
import appcloud.distributed.annotation.Operate;
import appcloud.distributed.configmanager.SynInfo;
import appcloud.distributed.configmanager.VersionInfo;
import appcloud.distributed.configmanager.VersionInfoManager;
import appcloud.openapi.client.AccountClient;
import appcloud.openapi.datatype.AppkeyItem;
import appcloud.openapi.response.UserCreateForDisResponse;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.distributed.common.entity.Appkey;
import com.distributed.common.utils.EntityReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Idan on 2018/1/15.
 */
@Operate
public class AccountOperateImpl implements AccountOperate {

    private static final Logger logger = LoggerFactory.getLogger(AccountOperateImpl.class);
    private AccountClient accountClient = OpenClientFactory.getAccountClient();
    private VersionInfoManager versionInfoManager = VersionInfoManager.getInstance();
    private CloudControllerClientWapper clientWapper = CloudControllerClientWapper.getInstance();
    private static AccountOperateImpl accountOperate = new AccountOperateImpl();

    public static AccountOperateImpl getInstance() {
        return accountOperate;
    }

    @Override
    public UserCreateForDisResponse createUser(String requestId,String regionId, String zoneId, String newUserEmail, String groupSecret, String appkeyId, String appkeySecret,String accountName) {
        //1. 调用client创建用户
        logger.info("requestId: "+requestId+",调用iaas api创建用户.\n"+"regionId: "+regionId+", zoneId: "+zoneId+", userEmail: "+newUserEmail+", groupSecretKey:"+groupSecret);
        UserCreateForDisResponse response = accountClient.userCreateForDistributed(regionId, zoneId, newUserEmail, groupSecret, appkeyId, appkeySecret, null,accountName);
        return response;
    }

    @Override
    @SynMethod(value = {"com.distributed.common.service.db.AccountService addAppkey"})
    public VersionInfo addUserVersion(String requestId,AppkeyItem appkeyItem) {
        logger.info("开始存储创建用户的日志");
        Appkey appkey = new Appkey();
        EntityReflectUtil.converJavaBean(appkey, appkeyItem);
        SynInfo synInfo = new SynInfo(appkey.getZone(), "com.distributed.common.service.db.AccountService", "addAppkey", appkey);
        VersionInfo newInfo = versionInfoManager.addOne(synInfo);
        logger.info("requestId: "+requestId+",添加日志。"+newInfo.toString());
        logger.info("开始同步恢复master上的用户信息");
        versionInfoManager.recoveryByInfo(synInfo);
        return newInfo;
    }


    @Override
    public Boolean dispatchAccount(String requestId,VersionInfo versionInfo) {
        try {
            clientWapper.dispatchVersion(requestId, versionInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("分发版本出错");
            return false;
        }
    }


}
