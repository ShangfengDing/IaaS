package com.distributed.common.entity.query;

import lombok.Data;

/**
 * Created by Idan on 2017/12/25.
 * // TODO 暂时不支持join操作
 */
@Data
public class QueryObject {
    public enum QueryType {
        EQUAL, NOT_EQUAL, BOTH_LIKE, NOT_LIKE,
        GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL,
        BETWEEN
    }

    private Class<?>[] clazzs;  //查询的数据类型
    private QueryType queryType;
    private Object[] value; // 通常是一个，但是在用between时用两个值

    public QueryObject(Class<?>[] clazzs, QueryType queryType, Object[] value) {
        this.clazzs = clazzs;
        this.queryType = queryType;
        this.value = value;
    }
}
