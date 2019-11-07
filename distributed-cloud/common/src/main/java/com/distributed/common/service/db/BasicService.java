package com.distributed.common.service.db;

import com.distributed.common.entity.query.QueryObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Idan on 2018/3/13.
 */
public interface BasicService<T, K> {

    /**
     * 增加
     * @param entity
     * @return
     */
    Boolean add(T entity);

    Boolean deleteByKey(K key);

    Boolean update(T entity);

    T findByKey(K key);

    List<T> findAll();

}
