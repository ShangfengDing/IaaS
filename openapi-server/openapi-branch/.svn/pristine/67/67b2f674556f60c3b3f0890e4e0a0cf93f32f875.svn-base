package appcloud.openapi.check.impl;

import appcloud.openapi.check.AccountParamsCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Idan on 2017/10/22.
 * 参数检查
 */
public class AccountParamsCheckImpl implements AccountParamsCheck {

    private static AccountParamsCheckImpl userParamsCheck = new AccountParamsCheckImpl();
    public static AccountParamsCheckImpl getInstance() {return userParamsCheck;}
    private static CommonGenerator commonGenerator  = new CommonGenerator();

    private final Logger logger = Logger.getLogger(AccountParamsCheckImpl.class);


    @Override
    public Map<String, String> checkCreateUser(Map<String, String> paramsMap) throws Exception {
        logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check create user params, new user email=" +
                paramsMap.get(Constants.NEW_USER_EMAIL) );
        if(null==paramsMap.get(Constants.NEW_USER_EMAIL)) {
            return commonGenerator.missing(null, Constants.NEW_USER_EMAIL);
        }
        if (null == paramsMap.get(Constants.GROUP_SECRET_KEY)) {
            return commonGenerator.missing(null, Constants.GROUP_SECRET_KEY);
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        logger.info("Check create user params successfully!");
        return resultMap;
    }


    @Override
    public Map<String, String> checkCreateGroup(Map<String, String> paramsMap) throws Exception {
        logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check create group params, new group name=" +
                paramsMap.get(Constants.GROUP_NAME) );
        if(null!=paramsMap.get(Constants.GROUP_NAME) &&
                !Pattern.matches("((\\w|[\u4e00-\u9fa5])+(\\.|_|-|[\u4e00-\u9fa5])?){"+Constants.MIN_NAME+","+
                        Constants.MAX_NAME + "}", paramsMap.get(Constants.GROUP_NAME))) {
            return commonGenerator.inValid(null, Constants.GROUP_NAME);
        }
        if(null!=paramsMap.get(Constants.GROUP_DESCRIPTION) &&
                !Pattern.matches("(.|[\u4e00-\u9fa5]){"+ Constants.MIN_DESCRIPTION + ","+
                        Constants.MAX_DESCRIPTION+"}",paramsMap.get(Constants.GROUP_DESCRIPTION)) ) {
            return commonGenerator.inValid(null, Constants.GROUP_DESCRIPTION);
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        logger.info("Check create group params successfully!");
        return resultMap;
    }
}
