package com.appcloud.vm.util;/**
 * Created by dell on 2016/6/5.
 */

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @author 包鑫彤
 * @create 2016-06-05-22:10
 * @since 1.0.0
 */
public final class StringUtil {

    /**
     * 字符串分隔符
     */
    public static final  String SEPARATOR = String.valueOf((char)29);

    public static boolean isNullOrEmpty(String str){
        return str==null||str.length()==0;
    }
    public static String[] splitString(String str,String regx){
        return str.split(regx);
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str!=null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }
}
