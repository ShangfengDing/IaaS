package com.distributed.dbproxy.service;

import com.distributed.common.entity.VmHost;
import com.distributed.common.entity.query.QueryObject;
import com.distributed.common.service.db.VmHostService;
import com.distributed.common.utils.EntityReflectUtil;
import com.distributed.common.utils.ModelUtil;
import com.distributed.dbproxy.DbProxyApplication;
import com.distributed.dbproxy.helper.BeanHelper;
import com.distributed.dbproxy.sql.basic.appcloud.VmHostEntity;
import com.distributed.dbproxy.sql.basic.distribution.VmBackEntity;
import com.distributed.dbproxy.sql.repository.appcloud.VmHostDao;
import com.distributed.dbproxy.utils.SpecificationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Idan on 2018/3/13.
 */
public class VmHostServiceImpl implements VmHostService {

    private final static Logger logger = LoggerFactory.getLogger(VmHostServiceImpl.class);
    private SpecificationUtil<VmHostEntity> specificationUtil = new SpecificationUtil<>();

    private VmHostDao vmHostDao = BeanHelper.getVmHostDao();

    //TODO 方法暂时没有完成

    @Override
    public Boolean add(VmHost entity) {
        return null;
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        return null;
    }

    @Override
    public Boolean update(VmHost entity) {
        return null;
    }

    @Override
    public VmHost findByKey(Integer key) {
        return null;
    }

    @Override
    public List<VmHost> findAll() {
        return null;
    }


    @Override
    public VmHost findByUuid(String uuid) {
        VmHostEntity entity = vmHostDao.findByUuid(uuid);
        if (ModelUtil.isEmpty(entity)) {
            logger.info("the host is not existed, uuid = "+uuid);
            return null;
        }
        VmHost result = new VmHost();
        EntityReflectUtil.converJavaBean(result, entity);
        logger.info("the result is "+result.toString());
        return result;
    }

    @Override
    public List<VmHost> findByParams(String hostUuid, String type, String ip,Integer availabilityZoneId, Integer clusterId) {
        Map<String, QueryObject> searchMap = new HashMap<>();
        if (!StringUtils.isEmpty(hostUuid)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {hostUuid};
            searchMap.put("uuid", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!StringUtils.isEmpty(type)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {type};
            searchMap.put("type", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!StringUtils.isEmpty(ip)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {ip};
            searchMap.put("ip", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!ModelUtil.isEmpty(availabilityZoneId)) {
            Class<?>[] classes = {Integer.class};
            Object[] objects = {availabilityZoneId};
            searchMap.put("availabilityZoneId", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!ModelUtil.isEmpty(clusterId)) {
            Class<?>[] classes = {Integer.class};
            Object[] objects = {clusterId};
            searchMap.put("availabilityClusterId", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        Specification<VmHostEntity> where = specificationUtil.where(searchMap);
        List<VmHostEntity> searchResult = null;
        try {
            searchResult = vmHostDao.findAll(where);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        List<VmHost> vmHosts = new ArrayList<>();
        for (VmHostEntity entity: searchResult) {
            try {
                VmHost host = new VmHost();
                EntityReflectUtil.converJavaBean(host, entity);

                vmHosts.add(host);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return vmHosts;
    }

}
