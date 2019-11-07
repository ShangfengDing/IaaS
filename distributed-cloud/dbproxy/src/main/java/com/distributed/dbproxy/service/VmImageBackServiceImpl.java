package com.distributed.dbproxy.service;

import com.distributed.common.entity.VmImageBack;
import com.distributed.common.service.db.VmImageBackService;
import com.distributed.common.utils.CollectionUtil;
import com.distributed.common.utils.EntityReflectUtil;
import com.distributed.common.utils.ModelUtil;
import com.distributed.dbproxy.DbProxyApplication;
import com.distributed.dbproxy.helper.BeanHelper;
import com.distributed.dbproxy.sql.basic.appcloud.VmImageBackEntity;
import com.distributed.dbproxy.sql.repository.appcloud.VmImageBackDao;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Idan on 2017/12/21.
 */
public class VmImageBackServiceImpl implements VmImageBackService {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(VmImageBackServiceImpl.class);

    private VmImageBackDao vmImageBackDao = BeanHelper.getVmImageBackDao();

    @Override
    public List<VmImageBack> findAll() {
        return null;
    }

    @Override
    public VmImageBack getByUuid(String uuid) {
        VmImageBackEntity entity = vmImageBackDao.findByUuid(uuid);
        if (ModelUtil.isEmpty(entity)) {
            logger.info("the vmImageBack is null, uuid = "+uuid);
            return null;
        }
        VmImageBack result = new VmImageBack();
        EntityReflectUtil.converJavaBean(result, entity);
        return result;
    }

    @Override
    public VmImageBack getDetailsByUuid(String uuid, boolean withCluster, boolean withZone, boolean withInstance, boolean withHost) {
        return null;
    }

    @Override
    public VmImageBack getByActiveVolumeUuidAndTop(String activeVolumeUuid, boolean isTop) {
        return null;
    }

    @Override
    public VmImageBack getByInstanceUuidAndTop(String instanceUuid, boolean isTop) {
        List<VmImageBackEntity> vmImageBackEntityList = vmImageBackDao.findByInstanceUuidAndTop(instanceUuid, isTop);
        if (CollectionUtil.isEmpty(vmImageBackEntityList)) {
            return null;
        }
        VmImageBack vmImageBack = new VmImageBack();
        EntityReflectUtil.converJavaBean(vmImageBack, vmImageBackEntityList.get(0));
        return vmImageBack;
    }

    @Override
    public List<VmImageBack> getByInstanceUuid(String instanceUuid) {
        List<VmImageBackEntity> vmImageBackEntityList = vmImageBackDao.findByInstanceUuid(instanceUuid);
        List<VmImageBack> vmImageBackList = new ArrayList<>();
        for (VmImageBackEntity entity: vmImageBackEntityList) {
            VmImageBack imageBack = new VmImageBack();
            EntityReflectUtil.converJavaBean(imageBack, entity);
            vmImageBackList.add(imageBack);
        }
        return vmImageBackList;
    }

    @Override
    public List<VmImageBack> getByActiveVolumeUuid(String activeVolumeUuid) {
        return null;
    }

    @Override
    public Boolean save(VmImageBack vmImageBack) {
        if (ModelUtil.isEmpty(vmImageBack)) {
            return false;
        }
        VmImageBackEntity entity = new VmImageBackEntity();
        EntityReflectUtil.converJavaBean(entity, vmImageBack);
        try {
            vmImageBackDao.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(VmImageBack vmImageBack) {
        return null;
    }

    @Override
    public Boolean updateTop(String instanceUuid) {
        try {
            vmImageBackDao.updateTopByInstanceUuid(instanceUuid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Boolean deleteByUuid(String uuid) {
        return null;
    }

    @Override
    public Boolean deleteById(Integer id) {
        vmImageBackDao.delete(Integer.valueOf(id));
        return true;
    }
}
