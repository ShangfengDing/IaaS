package com.distributed.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

/**
 * Created by Idan on 2018/1/6.
 * 反射工具类
 */
public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 根据名字拿到class
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> getClass(String className) throws Exception {
        return Class.forName(className);
    }

    /**
     * 获得method
     * @param clazz
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Class<?> clazz, String methodName) throws Exception {
//        return clazz.getDeclaredMethod(methodName);
        //TODO 获得method
        return clazz.getDeclaredMethod(methodName);
    }

    /**
     * 创建实例
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls) {
        Object obj;
        try {
            obj = cls.newInstance();
        } catch (Exception e) {
            logger.error("new instance fail", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * 根据实例和方法，参数值，经过反射拿到结果值
     * Object... 不确定参数类型和个数，多态的表现
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("invoke method fail", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置变量的值
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            logger.error("set field fail", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得当前类名
     * @return
     */
    public static String getCurrentClassName() {
        return Thread.currentThread().getStackTrace()[1].getClassName();
    }

    /**
     * 获得当前方法名
     * @return
     */
    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }


    public static void main (String args[]) {
        String className = "com.distributed.common.utils.ReflectionUtil";
        try {
            Class<?> clazz = ReflectionUtil.getClass(className);
            Method method = ReflectionUtil.getMethod(clazz,"getClass");
            System.out.println(method.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
