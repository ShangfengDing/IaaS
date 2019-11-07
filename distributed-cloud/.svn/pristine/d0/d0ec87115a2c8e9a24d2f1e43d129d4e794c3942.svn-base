package com.distributed.common.constant;

import com.distributed.common.utils.ConfigReader;
import com.distributed.common.utils.ConfigurationUtil;
import com.distributed.common.utils.ProxyUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Idan on 2017/12/12.
 */
public class Constants {
    private static final Logger logger = LoggerFactory.getLogger(Constants.class);

    /**
     * 数据库服务的地址
     */
    public static String DB_HOST;
    /**
     * 数据库服务的端口
     */
    public static Integer DB_PORT = 9001;

    /**
     * RPC服务器地址和端口
     */
    public static String RPC_HOST = "127.0.0.1";
    public static Integer RPC_PORT = 8118;
    public final static int REQUEST_WORKER_THREAD_NUMBER = 3;

    /**
     * rpc的接口与实现类
     */
    public static ConcurrentHashMap<String, String> serviceHashMap = new ConcurrentHashMap<String, String>();

    static {
        try {
            TypeReference<List<ProxyUnit>> listType =  new TypeReference<List<ProxyUnit>>() {};
            List<ProxyUnit> services = ConfigReader.readFromJson(Constants.class.getClassLoader().getResource("services.json"),listType);
            for (ProxyUnit proxyUnit : services) {
                logger.info(proxyUnit.toString());
                System.out.println(proxyUnit.toString());
                serviceHashMap.put(proxyUnit.getProxyClass(), proxyUnit.getImplClass());
                logger.info("the routeInfo class loader finished...");
            }

            //加载配置文件，得到数据库db的地址和controller的地址
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
            if (p!=null) {
                DB_HOST = p.getProperty("DB_HOST", "127.0.0.1");
                System.out.println("the db host: "+DB_HOST);

                DB_PORT = Integer.valueOf(p.getProperty("DB_PORT", "9001"));
                System.out.println("the db port: "+DB_PORT);

                RPC_HOST = p.getProperty("RPC_HOST", "127.0.0.1");
                System.out.println("the controller host: "+RPC_HOST);

                RPC_PORT = Integer.valueOf(p.getProperty("RPC_PORT", "8118"));
                System.out.println("the controller port: "+RPC_PORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
