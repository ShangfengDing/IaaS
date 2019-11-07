package appcloud.openapi.query;

import com.appcloud.vm.fe.common.ConfigurationUtil;
import com.free4lab.utils.log.LogOperation;
import com.free4lab.utils.log.LogOperationImpl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by rain on 2016/12/12.
 */
public class LolQuery {
    static String lolAppName;
    static String lolAppkey;
    static String lolSecretKey;
    static boolean lolEnable;

    public static String QueryLog (Map<String, String> query) {
        String temp="";
        if (lolEnable) {
            LogOperation logOperation = new LogOperationImpl(lolAppName,lolAppkey,lolSecretKey);
            temp = logOperation.getLog(lolAppName,query);
            System.out.print(temp.toString());
        }
        return temp;
    }

//    public static void main(String args[]) {
//        Map<String,String> query = new HashMap<>();
//        System.out.println("appname:" + lolAppName + "time:"+new Date().getTime());
//        query.put("vm_uuid","b5e91983b8414d0083da396870b4a768");
//        query.put("btime",(new Date().getTime()-3600000)+"");
//        query.put("etime",new Date().getTime()+"");
//        query.put("timeasc", "no");
//        query.put("size","10");
//        String result = QueryLog(query);
//        System.out.print(result);
//    }

    /**
     * 查询lol的配置，并返回判断值
     * @return
     */
    static  {
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
            String lolEnableStr = p.getProperty("lolEnable");
            if ("true".equals(lolEnableStr)) {
                lolAppName = p.getProperty("lolAppName");
                lolAppkey = p.getProperty("lolAppkey");
                lolSecretKey = p.getProperty("lolSecretKey");
                lolEnable = true;
            } else {
                lolEnable = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            lolEnable = false;
        }
    }
}
