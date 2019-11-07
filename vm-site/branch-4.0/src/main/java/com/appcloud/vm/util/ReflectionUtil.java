package com.appcloud.vm.util;



import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by dell on 2016/5/19.
 * @since 1.0.0
 */
public final class ReflectionUtil {
    private static final Logger LOGGER = Logger.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try{
            instance = cls.newInstance();
        }catch (Exception e){
            LOGGER.error("new instance failure",e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 类路径创建实例
     */
    public static Object newInstance(String className){
        Object instance;
        try{
            instance = Class.forName(className).newInstance();
        }catch (Exception e){
            LOGGER.error("new instance failure",e);
            throw new RuntimeException(e);
        }
        return instance;
    }
    /**
     * 使用方法
     */
    public static Object invokeMethod(Object obj, Method method,Object ...args){
        Object result;
        try{
            method.setAccessible(true);
            result = method.invoke(obj,args);
        }catch(Exception e){
            LOGGER.error("invoke method failure",e);
            throw new RuntimeException();
        }
        return result;
    }
    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field, Object value){
        try{
            field.setAccessible(true);
            field.set(obj,value);
        }catch(Exception e){
            LOGGER.error("set field failure",e);
            throw new RuntimeException(e);
        }
    }
}
