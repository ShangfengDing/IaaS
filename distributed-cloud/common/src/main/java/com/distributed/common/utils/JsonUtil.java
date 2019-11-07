package com.distributed.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by lizhenhao on 2017/11/8.
 */
public class JsonUtil {

    public final static Charset CHAR_SET = Charset.forName("UTF-8");
    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * fastjson
     */
    public static byte[] jsonBytes(Object obj) {

        final String json = toJson(obj,false);

        if (json != null) {
            return json.getBytes();
        }
        return null;
    }

    public static String toJson(Object obj, boolean prettyFormat) {
        SerializerFeature[] features = new SerializerFeature[] {
                SerializerFeature.WriteClassName, SerializerFeature.WriteMapNullValue};
        if (obj != null) {
            return JSON.toJSONString(obj, features);
        } else {
            return null;
        }
    }

    public static <T> T jsonObject(byte[] bytes,Class<T> tClass) {
        String json = null;
        try {
            json = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fromJson(json,tClass);
    }

    public static <T> T fromJson(String json,Class<T> tClass) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        return JSON.parseObject(json,tClass);
    }



    /**
     * jackson
     * @return
     */
    public static byte[] jacksonToBytes(Object obj) {
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (json != null) {
            return json.getBytes();
        }
        return null;
    }

    public static String jacksonToString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T jackJsonToObject(byte[] bytes,Class<T> tClass) {
        String json = null;
        try {
            json = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T jackJsonToObject(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * gson
     * @return
     */
    public static byte[] gsonToBytes(Object obj) {
        Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json != null) {
            return json.getBytes();
        }
        return null;
    }

    public static <T> T gsonToObject(byte[] bytes,Class<T> tClass) {
        Gson gson = new Gson();
        String json = null;
        try {
            json = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            return gson.fromJson(json, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T gsonToObject(String str,Class<T> tClass) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(str, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * java 原声 object stream
     */
    public static <T> T ByteToObject(byte[] bytes) {
        T obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            obj = (T)oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }
}
