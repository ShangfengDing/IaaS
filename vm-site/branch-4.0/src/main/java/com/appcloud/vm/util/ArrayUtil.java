package com.appcloud.vm.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * Created by 包鑫彤 on 2016/5/19.
 * @since 1。0.0
 */
public final class ArrayUtil {
    /**
     * 判断数组是否为空
     * @param fields
     * @return
     */
    public static boolean isNotEmpty(Object[] fields){
        return !ArrayUtil.isEmpty(fields);
    }

    /**
     * 判断数组是否为空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }
}
