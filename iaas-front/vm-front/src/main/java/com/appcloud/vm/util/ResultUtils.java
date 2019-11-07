package com.appcloud.vm.util;


import com.appcloud.vm.common.ResultMessage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用于返回JSON数据
 *
 * @author 包鑫彤
 * @create 2016-05-31-22:16
 * @since 1.0.0
 */
public class ResultUtils {
    private static Logger LOGGER = Logger.getLogger(ResultUtils.class);

    public static void toJson(HttpServletResponse response, Object data) {
        responseJson(response, data);
    }

    public static void toAliYunJson(HttpServletResponse response, Object data) {
        ResultMessage resultMessage = ResultMessage.getAliYunMessage(data);
        responseJson(response, resultMessage);
    }

    public static void toYunHaiJson(HttpServletResponse response, ResultMessage resultMessage){
//        ResultMessage resultMessage = ResultMessage.getYunHaiMessage(data);
        responseJson(response, resultMessage);
    }

    private static void responseJson(HttpServletResponse response, Object data) {
        String result = JsonUtil.toJson(data);
        LOGGER.info("开始转换JSON");
        LOGGER.info(result);
        response.setContentType("text/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            LOGGER.error("转换JSON时出现异常", e);
        }
        out.print(result);
        out.flush();
        out.close();
    }


}
