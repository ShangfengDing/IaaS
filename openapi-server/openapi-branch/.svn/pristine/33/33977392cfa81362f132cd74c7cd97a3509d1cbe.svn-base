package appcloud.openapi.operate.impl;

import appcloud.common.model.Cluster;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.UuidUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.datatype.AppkeyItem;
import appcloud.openapi.datatype.GroupDetailItem;
import appcloud.openapi.datatype.UserDetailItem;
import appcloud.openapi.datatype.VmUserItem;
import appcloud.openapi.manager.util.BeansGenerator;
import appcloud.openapi.operate.AccountOperate;
import appcloud.openapi.response.GroupCreateResponse;
import appcloud.openapi.response.UserCreateForDisResponse;
import appcloud.openapi.response.UserCreateResponse;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import static com.free4lab.utils.enabler.AbstractAuthentication.getKeyPair;

/**
 * Created by Idan on 2017/10/22.
 */
public class AccountOperateImpl implements AccountOperate {

    private static AccountOperateImpl userOperate = new AccountOperateImpl();
    public static AccountOperateImpl getInstance() {return userOperate;}
    private static final Logger logger = Logger.getLogger(AccountOperateImpl.class);
    private BeansGenerator beansGenerator = BeansGenerator.getInstance();
    private static AppkeyManager appkeyManager = new AppkeyManager();

    private VmUserProxy userProxy;
    private VmGroupProxy vmGroupProxy;
    private ClusterProxy clusterProxy;

    private AccountOperateImpl() {
        userProxy = (VmUserProxy) ConnectionFactory.getTipProxy(VmUserProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmGroupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(VmGroupProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        clusterProxy = (ClusterProxy) ConnectionFactory.getTipProxy(ClusterProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    }

    @Override
    public UserCreateResponse UserCreate(Map<String, String> paramsMap, String requestId) throws Exception {
        String newUserEmail = paramsMap.get(Constants.NEW_USER_EMAIL);

        Integer groupId = Integer.valueOf(paramsMap.get(Constants.GROUP_ID));  //在前一步检查参数出获得groupId

        String[] appkeyPair = getKeyPair();
        String appkeyId = appkeyPair[0];
        String appkeySecret = appkeyPair[1];
        try {
            VmUser user = new VmUser(null,null,newUserEmail, true, groupId, appkeyId, appkeySecret,null);
             userProxy.save(user);
            UserDetailItem userDetailItem = beansGenerator.VmUserToUserDetailItem(user);
            Integer userId = userProxy.getByAppKeyId(appkeyId).getUserId();
            userDetailItem.setUserId(userId.toString());
            appkeyManager.addAppkey(userId, userDetailItem.getUserEmail(), userDetailItem.getAppkeyId(), userDetailItem.getAppkeySecret(), com.appcloud.vm.fe.common.Constants.YUN_HAI,com.appcloud.vm.fe.common.Constants.YUN_HAI);
            return new UserCreateResponse(requestId, HttpConstants.STATUS_OK, null, userDetailItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new UserCreateResponse(requestId, Constants.INTERNAL_ERROR, "the new user create failed");
        }

    }

    /**
     * 分布式云添加
     * @param paramsMap
     * @param requestId
     * @return
     * @throws Exception
     */
    //TODO 这里应该要保证是同一个userId，因为在列表选取的时候是根据userId和appname选择的
    @Override
    public UserCreateForDisResponse UserCreateForDis(Map<String, String> paramsMap, String requestId) throws Exception {
        String newUserEmail = paramsMap.get(Constants.NEW_USER_EMAIL);

        Integer groupId = Integer.valueOf(paramsMap.get(Constants.GROUP_ID));  //在前一步检查参数出获得groupId

        String[] appkeyPair = getKeyPair();
        String appkeyId = appkeyPair[0];
        String appkeySecret = appkeyPair[1];
        try {
            QueryObject<VmUser> userQuery = new QueryObject<>();
            userQuery.addFilterBean(new FilterBean<VmUser>("userEmail",newUserEmail, FilterBean.FilterBeanType.EQUAL));
            userQuery.addFilterBean(new FilterBean<VmUser>("groupId",groupId, FilterBean.FilterBeanType.EQUAL));
            userQuery.addFilterBean(new FilterBean<VmUser>("active",true, FilterBean.FilterBeanType.EQUAL));
            List<VmUser> vmUsers = (List<VmUser>) userProxy.searchAll(userQuery);
            VmUser vmUser = null;
            if (CollectionUtils.isEmpty(vmUsers)) {
                logger.info("用户不存在");
                VmUser user = new VmUser(null,null,newUserEmail, true, groupId, appkeyId, appkeySecret,null);
                userProxy.save(user);
                vmUser = userProxy.getByAppKeyId(appkeyId);
            } else {
                vmUser = vmUsers.get(0);
                logger.info("用户已存在，"+vmUser);
            }
            VmUserItem vmUserItem = beansGenerator.VmUserToVmUserItem(vmUser);
            if (vmUserItem == null) {
                return new UserCreateForDisResponse(requestId, Constants.INTERNAL_ERROR, "the new user create failed");
            }

            Integer userId = vmUserItem.getUserId();
//            Appkey preAppkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, paramsMap.get(Constants.ACCOUNT_NAME));
//            if (preAppkey != null) {
//                String why = "the appkey existed";
//                logger.error(why);
//                return new UserCreateForDisResponse(requestId, HttpConstants.STATUS_FORBIDDEN, why);
//            }

            List<Appkey> appkeyList = appkeyManager.getAppkeyByEmailAndZone(newUserEmail,paramsMap.get(Constants.REGION_ID),paramsMap.get(Constants.ZONE_ID));
            Appkey appkey = null;
            AppkeyItem appkeyItem = null;
            if(CollectionUtils.isEmpty(appkeyList)){
                String accountName = paramsMap.get(Constants.ACCOUNT_NAME);
                appkeyManager.addAppkey(userId, vmUserItem.getUserEmail(), vmUserItem.getAppkeyId(), vmUserItem.getAppkeySecret(), com.appcloud.vm.fe.common.Constants.YUN_HAI,accountName,paramsMap.get(Constants.REGION_ID),paramsMap.get(Constants.ZONE_ID),1);
                appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, accountName);
            } else {
                appkey = appkeyList.get(0);
            }
            appkeyItem = beansGenerator.AppkeyToAppkeyItem(appkey);
            if (appkeyItem == null) {
                return new UserCreateForDisResponse(requestId, Constants.INTERNAL_ERROR, "the new user create failed");
            }
            return new UserCreateForDisResponse(requestId, HttpConstants.STATUS_OK, null, vmUserItem, appkeyItem);


        } catch (Exception e) {
            e.printStackTrace();
            return new UserCreateForDisResponse(requestId, Constants.INTERNAL_ERROR, "the new user create failed");
        }
    }


    // TODO 麻烦
    @Override
    public GroupCreateResponse GroupCreate(Map<String, String> paramsMap, String requestId) throws Exception {
        String groupName = paramsMap.get(Constants.GROUP_NAME);
        String description = paramsMap.get(Constants.GROUP_DESCRIPTION);
        String clusterIds = null;

        try {
            List<Cluster> clusters = (List<Cluster>) clusterProxy.findAll(false, false, false);
            if( clusters == null || clusters.size() == 0)
                clusterIds = "";
            else if(clusters.size() == 1) {
                clusterIds = clusters.get(0).getId().toString();
            }
            else {
                clusterIds = clusters.get(0).getId().toString();
                for(int i = 1; i < clusters.size(); i ++)
                    clusterIds += "," + clusters.get(i).getId().toString();
            }

            String groupSecretKey = UuidUtil.getRandomUuid();

            VmGroup group = new VmGroup(null, groupName, Constants.SELECT_CLUSTER, Constants.PUBLISH_IMAGE, Constants.MAX_NUMBER_OF_INSTANCE, Constants.MAX_NUMBER_OF_DISK,
                    Constants.MAX_NUMBER_OF_BACKUP, Constants.MAX_NUMBER_OF_SNAPSHOT, clusterIds, description, groupSecretKey);
            vmGroupProxy.save(group);
            GroupDetailItem groupDetailItem = beansGenerator.VmGroupToGroupDetailItem(group);
            return new GroupCreateResponse(requestId, HttpConstants.STATUS_OK, null, groupDetailItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new GroupCreateResponse(requestId, Constants.INTERNAL_ERROR, "the new group create failed");
        }

    }
}
