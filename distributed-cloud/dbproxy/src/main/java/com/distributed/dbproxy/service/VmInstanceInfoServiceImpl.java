package com.distributed.dbproxy.service;

import com.distributed.common.entity.InstanceBackInfo;
import com.distributed.common.service.db.VmInstanceInfoService;
import com.distributed.common.utils.ModelUtil;
import com.distributed.dbproxy.DbProxyApplication;
import com.distributed.dbproxy.helper.BeanHelper;
import com.distributed.dbproxy.sql.basic.distribution.InstanceBackInfoEntity;
import com.distributed.dbproxy.sql.repository.distribution.InstanceBackInfoDao;
import com.distributed.common.utils.EntityReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Idan on 2017/12/17.
 */
public class VmInstanceInfoServiceImpl implements VmInstanceInfoService {

    private static final Logger logger = LoggerFactory.getLogger(VmInstanceInfoServiceImpl.class);

    private InstanceBackInfoDao instanceBackInfoDao = BeanHelper.getInstanceBackInfoDao();

    @Override
    public InstanceBackInfo findByUuid(String uuid) {
        InstanceBackInfoEntity entity = null;
        try {
            entity = instanceBackInfoDao.findByBackupUuid(uuid);
            if (entity == null) {
                logger.info("the instanceinfo not exist, uuid: "+ uuid);
                return null;
            }
            InstanceBackInfo result = new InstanceBackInfo();
            EntityReflectUtil.converJavaBean(result, entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean add(InstanceBackInfo vmBackInfo) {
        if (ModelUtil.isEmpty(vmBackInfo)) {
            return false;
        }
        try {
            InstanceBackInfoEntity entity = new InstanceBackInfoEntity();
            EntityReflectUtil.converJavaBean(entity, vmBackInfo);
            instanceBackInfoDao.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
