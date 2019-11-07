package com.appcloud.vm.util;/**
 * Created by dell on 2016/6/6.
 */


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * 编码与解码操作工具类
 *
 * @author 包鑫彤
 * @create 2016-06-06-15:21
 * @since 1.0.0
 */
public final class CodeUtil {
    private static final Logger LOGGER = Logger.getLogger(CodeUtil.class);

    /**
     * 将URL编码
     * @param source
     * @return
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将URL解码
     * @param source
     * @return
     */
    public static String decodeURL(String source){
        String target;
        try {
            target = URLDecoder.decode(source,"utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode utl failure",e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String md5(String source){
        return DigestUtils.md5Hex(source);
    }
}
