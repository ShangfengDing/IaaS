package com.appcloud.vm.common;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rain on 2016/11/9.
 * 将实体类转换成Map类型
 */
public class TransBeanToMap {
    private static Logger logger = LoggerFactory.getLogger(TransBeanToMap.class);

    public static Map<String, String> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    String value = (String)getter.invoke(obj);

                    map.put(key, value);
                }

            }
            logger.info(map.toString());
        } catch (Exception e) {
            logger.error("transBean2Map Error {}" ,e);
        }
        return map;

    }

    public static void main(String args[]) {
//        NewIaaSLog lolLogEntity = new NewIaaSLog("10", "vm", "123465", "465612", "reset", "success");
//        TransBeanToMap.transBean2Map(lolLogEntity);
    }
}
