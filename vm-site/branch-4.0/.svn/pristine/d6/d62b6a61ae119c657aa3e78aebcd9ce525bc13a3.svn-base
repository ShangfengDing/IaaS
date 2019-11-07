package com.appcloud.vm.common;


import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;

/**
 * 用于返回结果代码和结果信息的类
 *
 * @author 包鑫彤
 * @create 2016-08-02-16:23
 * @since 1.0.0
 */
public class ResultMessage<T> {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public   String resultCode;

    public  String resultMessage;

    private T data;

    private  boolean success;

    public ResultMessage() {

    }

    public ResultMessage(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public ResultMessage(T data, String resultCode) {
        this.data = data;
        this.resultCode = resultCode;
    }

    public ResultMessage(boolean success, String resultMessage) {
        this.success = success;
        this.resultMessage = resultMessage;
    }

    public static ResultMessage getYunHaiMessage(){
        ResultMessage resultMessage = null;
        return resultMessage;
    }
    public static ResultMessage getAliYunMessage(Object data) {
        ResultMessage resultMessage = null;
        if (data == null) {
            resultMessage = new ResultMessage(ERROR, "响应出现异常");
            return resultMessage;
        }
        if (data instanceof AcsResponse) {
            resultMessage = new ResultMessage(SUCCESS, "响应出现异常");
        } else if (data instanceof ClientException) {
            resultMessage = new ResultMessage(ERROR, ((ClientException) data).getErrMsg());
        } else if (data instanceof ServerException) {
            resultMessage = new ResultMessage(ERROR, ((ServerException) data).getErrMsg());

        }
        return resultMessage;
    }
}
