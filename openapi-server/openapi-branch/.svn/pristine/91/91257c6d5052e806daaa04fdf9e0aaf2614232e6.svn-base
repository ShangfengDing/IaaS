package appcloud.openapi.manager.util;

import appcloud.openapi.check.*;
import appcloud.openapi.check.impl.*;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.CommonManager;
import appcloud.openapi.manager.impl.CommonManagerImpl;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.model.AppkeyDAO;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhenhao on 2016/11/16.
 */
public class AdminUnifiedCheckAndAuth {
    /**
     * 该类主要用于在对管理员具体操作前进行统一的认证鉴权管理，主要检查请求参数的合理性和有效性
     */
    private CommonManager commonManager = CommonManagerImpl.getInstance();
    private CommonParamsCheck commonParamsCheck = CommonParamsCheckImpl.getInstance();
    private AdminOperateCheckImpl adminOperateCheck = AdminOperateCheckImpl.getInstance();
    private AdminParamsCheckImpl adminParamsCheck = AdminParamsCheckImpl.getInstance();
    private AppkeyDAO appkeyDAO = new AppkeyDAO();
    private final Logger logger = Logger.getLogger(AdminUnifiedCheckAndAuth.class);
    /**
     * 云主机操作统计检查管理
     * @param paramsMap  接口请求参数及其参数值
     * @param defaultParamsMap  除请求参数外，含有默认值的参数及其参数值，这些默认值不参与签名认证检查
     * @return
     */
    public Map<String, String> adminCheckAndAuth(Map<String, String> paramsMap, Map<String, String> defaultParamsMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            resultMap = commonParamsCheck.checkCommonParams(paramsMap);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("The request common params was not authenticated successfully!");
                return resultMap;
            }
            //检查是不是管理员
            List<Appkey> appkeys = appkeyDAO.findByProperty("appkeyId",paramsMap.get(Constants.APPKEY_ID));
            if (null==appkeys || "manager"==appkeys.get(0).getProvider()) {
                logger.warn("The user is not manager!");
                resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_UNAUTHORIZED);
                return resultMap;
            }
            //检查接口自身参数
            switch(paramsMap.get(Constants.ACTION)) {
                case ActionConstants.ADMIN_DESCRIBE_SERVICES:
                    resultMap = adminParamsCheck.checkServicesParams(paramsMap);
                    break;
                case ActionConstants.ADMIN_DESCRIBE_INSTANCES:
                    resultMap = adminParamsCheck.checkAdminInstanceDescribeParams(paramsMap);
                    break;
                case ActionConstants.ADMIN_DESCRIBE_DISKS:
                    resultMap = adminParamsCheck.checkAdminVolumeDescribeParams(paramsMap);
                    break;
                case ActionConstants.ADMIN_DESCRIBE_HOSTS:
                    resultMap = adminParamsCheck.checkAdminHostsParams(paramsMap);
                    break;
                case ActionConstants.ADMIN_ONLINE_MIGRATE:
                    resultMap = adminParamsCheck.checkAdminOnlineMigrateParams(paramsMap);
                    break;
                case ActionConstants.ADMIN_MONITOR_INSTANCE_DATA:
                    resultMap = adminParamsCheck.checkAdminDescribeMonitorDataParams(paramsMap);
                    break;
                case ActionConstants.ADMIN_AUTHORIZE_IMAGE:
                    resultMap = adminParamsCheck.checkAdminAuthorizeImageParams(paramsMap);
                    break;
                default :
                    resultMap = null;
                    break;
            }
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("The request speical params was not authenticated successfully!");
                return resultMap;
            }
            //签名认证
            resultMap = commonManager.getAuthenticate(paramsMap);
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("The request signature was not authenticated successfully!");
                return resultMap;
            }
            /**
             * 参数检查完毕后，要在创建云主机或者具体操作云主机前进行操作权限检查，
             * 如创建云主机时要检查当前云主机是否达到上限，关闭云主机前要检查当前云主机状态等；
             * 在检查时由于会涉及更多的参数，所以为了统一检查，要将defaultParamsMap中的值统一转移到paramsMap中。
             * 若某些操作不需要进行操作权限检查，则直接返回即可。
             */
            if(null!=defaultParamsMap && !defaultParamsMap.isEmpty()) {
                logger.info("Transfer default params to paramsMap.");
                for(Map.Entry<String, String> entry : defaultParamsMap.entrySet()) {
                    paramsMap.put(entry.getKey(), entry.getValue());
                }
                logger.info("Transfer default params successfully.");
            }
            switch(paramsMap.get(Constants.ACTION)) {
                case ActionConstants.ADMIN_DESCRIBE_INSTANCES :
                    resultMap = adminOperateCheck.checkAdminInstanceDescribeOperate(paramsMap);
                    break;
                case ActionConstants.ADMIN_DESCRIBE_DISKS :
                    resultMap = adminOperateCheck.checkAdminVolumeDescribeOperate(paramsMap);
                    break;
                case ActionConstants.ADMIN_DESCRIBE_SERVICES :
                    resultMap = adminOperateCheck.checkAdminServiceOperate(paramsMap);
                    break;
                case ActionConstants.ADMIN_DESCRIBE_HOSTS :
                    resultMap = adminOperateCheck.checkAdminHostOperate(paramsMap);
                    break;
                case ActionConstants.ADMIN_ONLINE_MIGRATE :
                    resultMap = adminOperateCheck.checkAdminOnlineMigrateOperate(paramsMap);
                    break;
                case ActionConstants.ADMIN_MONITOR_INSTANCE_DATA:
                    resultMap = adminOperateCheck.checkAdminDescribeMonitorOperate(paramsMap);
                    break;
                case ActionConstants.ADMIN_AUTHORIZE_IMAGE:
                    resultMap = adminOperateCheck.checkAdminAuthorizeImageOperate(paramsMap);
                    break;
                default :
                    resultMap = null;
                    break;
            }
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("Check the operate instance permission is failed!");
                return resultMap;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(),e);
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
            return resultMap;
        }
        logger.info("Check the params and the operation permission successfully, and result is OK!");
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }
}
