package com.appcloud.vm.util;/**
 * Created by dell on 2016/6/8.
 */



import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * 属性文件工具类
 *
 * @author 包鑫彤
 * @create 2016-06-08-15:44
 * @since 1.0.0
 */
public final class PropsUtil {
    private static final Logger LOGGER = Logger.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName),"UTF-8");
            if (is == null) {
                throw new FileNotFoundException(fileName + "file is not found");
            }
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }
        return properties;
    }

    /**
     * 获取字符型属性（默认值为空字符串）
     *
     * @param properties
     * @param key
     * @return
     */
    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    /**
     * 获取字符型属性（可指定默认值）
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties properties,String key,String defaultValue){
        String value = defaultValue;
        if(properties.contains(key)){
            value = properties.getProperty(key);
        }
        return value;
    }

    /**
     * 获取数值型属性（默认值为 0 ）
     * @param properties
     * @param key
     * @return
     */
    public static int getInt(Properties properties,String key){
        return getInt(properties,key,0);
    }

    /**
     * 获取数值型属性（可指定默认值）
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Properties properties,String key,int defaultValue){
        int value = defaultValue;
        if(properties.contains(key)){
            value = CastUtil.castInt(properties.getProperty(key));
        }
        return value;
    }

    /**
     * 获取布尔型属性（默认值 为false）
     * @param properties
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties properties,String key){
        return getBoolean(properties,key,false);
    }

    /**
     * 获取布尔型属性（可指定默认值）
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Properties properties,String key,Boolean defaultValue){
        boolean value = defaultValue;
        if(properties.contains(key)){
            value = CastUtil.castBoolean(properties.getProperty(key));
        }
        return value;
    }

}
