package appcloud.openapi.manager.util;

import appcloud.openapi.check.CommonParamsCheck;
import appcloud.openapi.check.impl.CommonParamsCheckImpl;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zouji on 2017/11/1.
 */
@Component
public class OperateLogUnifiedCheckAndAuth {
    /**
     * 该类主要用于操作日志存取前进行统一的认证鉴权管理
     */
    private static final Logger logger = Logger.getLogger(OperateLogUnifiedCheckAndAuth.class);
    private CommonParamsCheck commonParamsCheck = CommonParamsCheckImpl.getInstance();

    public Map<String, String> checkAndAuth (Map<String, String> paramsMap, final Map<String, String> defaultParamsMap) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap = commonParamsCheck.checkCommonParams(paramsMap);
            if (null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("The request common params was not authenticated successfully!");
                return resultMap;
            }
            //检查接口自身参数

            //签名认证
            if (null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                logger.warn("The request signature was not authenticated successfully!");
                return resultMap;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE, "The request processing has failed due to some unknown error.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR + "");
            return resultMap;
        }
        logger.info("Check the params and the operation permission successfully, and result is OK!");
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }
}
