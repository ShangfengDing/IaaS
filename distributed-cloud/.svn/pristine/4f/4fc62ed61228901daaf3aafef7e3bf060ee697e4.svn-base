package com.distributed.dbproxy.service;

import com.distributed.common.entity.VmGroup;
import com.distributed.common.service.db.VmGroupService;
import com.distributed.common.utils.EntityReflectUtil;
import com.distributed.dbproxy.DbProxyApplication;
import com.distributed.dbproxy.helper.BeanHelper;
import com.distributed.dbproxy.sql.basic.appcloud.VmGroupEntity;
import com.distributed.dbproxy.sql.repository.appcloud.VmGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Idan on 2018/3/14.
 */
public class VmGroupServiceImpl implements VmGroupService {

    private VmGroupDao vmGroupDao = BeanHelper.getVmGroupDao();

    @Override
    public Boolean add(VmGroup entity) {
        return null;
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        return null;
    }

    @Override
    public Boolean update(VmGroup entity) {
        return null;
    }

    @Override
    public VmGroup findByKey(Integer key) {
        return null;
    }

    @Override
    public List<VmGroup> findAll() {
        List<VmGroupEntity> entityList = vmGroupDao.findAll();
        List<VmGroup> groups = new ArrayList<>();
        for (VmGroupEntity entity: entityList) {
            VmGroup group = new VmGroup();
            EntityReflectUtil.converJavaBean(group, entity);
            groups.add(group);
        }
        return groups;
    }
}
