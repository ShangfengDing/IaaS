package com.distributed.common.response;


import lombok.Data;

/**
 * Created by Idan on 2017/4/17.
 */
@Data
public class EntityResponse<T> extends BaseResponse{
    private T entity;

    public EntityResponse() {}

    public EntityResponse(String requestId, String code, String message) {
        super(requestId, code, message);
    }

    public EntityResponse(String requestId,String code, String message, T entity) {
        super(requestId, code, message);
        this.entity = entity;
    }

}
