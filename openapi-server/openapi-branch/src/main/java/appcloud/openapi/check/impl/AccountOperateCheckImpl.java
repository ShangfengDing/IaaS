package appcloud.openapi.check.impl;

import appcloud.common.model.VmGroup;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.Filter;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.openapi.check.AccountOperateCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Idan on 2017/10/22.
 * 操作检查
 */
public class AccountOperateCheckImpl implements AccountOperateCheck {

    private static AccountOperateCheckImpl userOperateCheck = new AccountOperateCheckImpl();
    public static AccountOperateCheckImpl getInstance() {return userOperateCheck;}
    private final Logger logger = Logger.getLogger(AccountOperateCheckImpl.class);
    private static CommonGenerator commonGenerator  = new CommonGenerator();
    private static VmUserProxy vmUserProxy;
    private static VmGroupProxy vmGroupProxy;
    private AccountOperateCheckImpl() {
        vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
                VmUserProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmGroupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(
                VmGroupProxy.class,appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    }

    @Override
    public Map<String, String> checkCreateUser(Map<String, String> paramsMap) throws Exception {
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }

        String groupSecretKey = paramsMap.get(Constants.GROUP_SECRET_KEY);
        String userEmail = paramsMap.get(Constants.NEW_USER_EMAIL);

        QueryObject<VmGroup> query = new QueryObject<>();
        query.addFilterBean(new FilterBean<VmGroup>("secretKey",groupSecretKey, FilterBean.FilterBeanType.EQUAL));
        try {
            List<VmGroup> groups = (List<VmGroup>) vmGroupProxy.searchAll(query);
            if (groups==null || groups.size()==0) {
                logger.warn("the user has no authorization to the group. groupSecretKey =" + Constants.GROUP_SECRET_KEY);
            return commonGenerator.internalError(null, paramsMap.get(Constants.GROUP_SECRET_KEY));
            } else {

                //TODO 判断用户是否已经存在
//                Integer preUserId = Integer.valueOf(paramsMap.get(Constants.PRE_USER_ID));
                Integer groupId = groups.get(0).getId();
                QueryObject<VmUser> userQuery = new QueryObject<>();
                userQuery.addFilterBean(new FilterBean<VmUser>("userEmail",userEmail, FilterBean.FilterBeanType.EQUAL));
                userQuery.addFilterBean(new FilterBean<VmUser>("groupId",groupId, FilterBean.FilterBeanType.EQUAL), Filter.FilterLogic.AND);
                List<VmUser> vmUsers = (List<VmUser>) vmUserProxy.searchAll(userQuery);
                if (vmUsers.size()!=0) {
                    logger.warn("the user exist in the group!!");
                    return commonGenerator.internalError("the user exist in this group!!", userEmail);
                }

                paramsMap.put(Constants.GROUP_ID, groupId.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkCreateGroup(Map<String, String> paramsMap) throws Exception {
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }

        //TODO 操作权限检查

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkCreateUserForDis(Map<String, String> paramsMap) throws Exception {
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }

        String groupSecretKey = paramsMap.get(Constants.GROUP_SECRET_KEY);
        String userEmail = paramsMap.get(Constants.NEW_USER_EMAIL);

        QueryObject<VmGroup> query = new QueryObject<>();
        query.addFilterBean(new FilterBean<VmGroup>("secretKey",groupSecretKey, FilterBean.FilterBeanType.EQUAL));
        try {
            List<VmGroup> groups = (List<VmGroup>) vmGroupProxy.searchAll(query);
            if (groups==null || groups.size()==0) {
                logger.warn("the user has no authorization to the group. groupSecretKey =" + Constants.GROUP_SECRET_KEY);
                return commonGenerator.internalError(null, paramsMap.get(Constants.GROUP_SECRET_KEY));
            } else {

                //TODO 判断用户是否已经存在
                Integer groupId = groups.get(0).getId();

                paramsMap.put(Constants.GROUP_ID, groupId.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

}
