package com.distributed.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by zouji on 2018/1/11.
 */
public class ConfigReader {
    private static final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static <T> List<T> readFromJson(URL url,TypeReference<List<T>> listType) throws IOException {
        System.out.println(url);
        return (List<T>)mapper.readValue(url, listType);
    }
}
