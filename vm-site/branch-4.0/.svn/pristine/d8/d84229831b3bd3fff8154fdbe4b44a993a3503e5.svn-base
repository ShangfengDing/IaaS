package com.appcloud.vm.util;/**
 * Created by dell on 2016/6/6.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;


import java.io.IOException;

/**
 * JSON工具类
 *
 * @author 包鑫彤
 * @create 2016-06-06-15:48
 * @since 1.0.0
 *
 */
public final class JsonUtil {
    private static final Logger LOGGER = Logger.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO转为JSON
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("convert POJO to JSON failure",e);
            throw new RuntimeException(e);
        }
        return json;
    }
    public static <T> T fromJson(String json,Class<T> type){
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json,type);
        } catch (IOException e) {
            LOGGER.error("convert JSON to POJO failure");
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
