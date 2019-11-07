package com.distributed.common.response;


import com.distributed.common.entity.query.Page;
import lombok.Data;

/**
 * Created by rain on 2017/2/8.
 * 基本的查询消息返回类，带了数据
 */
@Data
public class PageBaseRestResponse<T> extends BaseResponse{

    private Page<T> result;

    public PageBaseRestResponse(String requestId,String code, String message) {
        super(requestId, code,message);
    }

    public PageBaseRestResponse(String requestId,String code, String message, Page<T> result) {
        super(requestId, code, message);
        this.result = result;
    }
}
