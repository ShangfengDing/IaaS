package com.distributed.dbproxy.service;

import com.distributed.common.entity.VmZone;
import com.distributed.common.service.db.VmZoneService;
import com.distributed.common.utils.EntityReflectUtil;
import com.distributed.common.utils.ModelUtil;
import com.distributed.dbproxy.helper.BeanHelper;
import com.distributed.dbproxy.sql.basic.appcloud.VmHostEntity;
import com.distributed.dbproxy.sql.basic.appcloud.VmZoneEntity;
import com.distributed.dbproxy.sql.repository.appcloud.VmZoneDao;

/**
 * Created by Idan on 2018/6/10.
 */
public class VmZoneServiceImpl implements VmZoneService {

    private VmZoneDao vmZoneDao = BeanHelper.getVmZoneDao();

    @Override
    public Boolean add(VmZone zone) {
        try {
            VmZoneEntity vmZoneEntity = new VmZoneEntity();
            EntityReflectUtil.converJavaBean(vmZoneEntity, zone);
            vmZoneDao.save(vmZoneEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public VmZone findByZoneId(String zoneId) {
        VmZoneEntity vmZoneEntity = vmZoneDao.findByZoneId(zoneId);
        if (ModelUtil.isEmpty(vmZoneEntity)) {
            return null;
        } else {
            VmZone vmZone = new VmZone();
            EntityReflectUtil.converJavaBean(vmZone, vmZoneEntity);
            return vmZone;
        }
    }


}
