package com.distributed.common.response.util;

import com.distributed.common.response.BaseResponse;

/**
 * Created by Idan on 2018/1/22.
 */
public class ResponseGenerator {

    public static BaseResponse unKownError() {
        return new BaseResponse(null, HttpConstants.STATUS_INTERNAL_SERVER_ERROR, null);
    }



}
