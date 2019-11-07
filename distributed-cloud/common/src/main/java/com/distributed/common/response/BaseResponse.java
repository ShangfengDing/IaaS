package com.distributed.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by rain on 2017/1/24.
 */
@Data
public class BaseResponse implements Serializable {
    private static final Long serialVersionUID = 1L;

    private String RequestId;
    private String ErrorCode;
    private String Message;

    public BaseResponse() {}

    public BaseResponse(String requestId, String errorCode, String message) {
        RequestId = requestId;
        ErrorCode = errorCode;
        Message = message;
    }
}
