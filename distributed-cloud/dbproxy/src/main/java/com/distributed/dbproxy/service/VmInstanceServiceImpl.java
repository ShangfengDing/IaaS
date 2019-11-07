package com.distributed.dbproxy.service;

import com.distributed.common.entity.VmInstance;
import com.distributed.common.service.db.VmInstanceService;
import com.distributed.common.utils.EntityReflectUtil;
import com.distributed.common.utils.ModelUtil;
import com.distributed.dbproxy.DbProxyApplication;
import com.distributed.dbproxy.helper.BeanHelper;
import com.distributed.dbproxy.sql.basic.appcloud.VmInstanceEntity;
import com.distributed.dbproxy.sql.basic.appcloud.VmUserEntity;
import com.distributed.dbproxy.sql.basic.appcloud.VmVirtualInterfaceEntity;
import com.distributed.dbproxy.sql.repository.appcloud.VmInstanceDao;
import com.distributed.dbproxy.sql.repository.appcloud.VmUserDao;
import com.distributed.dbproxy.sql.repository.appcloud.VmVirtualInterfaceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Idan on 2018/3/12.
 */
public class VmInstanceServiceImpl implements VmInstanceService {

    private final Logger logger = LoggerFactory.getLogger(VmInstanceServiceImpl.class);

    private VmInstanceDao vmInstanceDao = BeanHelper.getVmInstanceDao();
    private VmVirtualInterfaceDao vmVirtualInterfaceDao = BeanHelper.getVmVirtualInterfaceDao();
    private VmUserDao vmUserDao = BeanHelper.getVmUserDao();

    @Override
    public VmInstance findByUuid(String uuid, Boolean isDetail) {
        VmInstanceEntity entity = vmInstanceDao.findByUuid(uuid);
        if (ModelUtil.isEmpty(entity)) {
            logger.info("the instance is not existed, uuid = "+uuid);
            return null;
        }
        VmInstance result = new VmInstance();
        EntityReflectUtil.converJavaBean(result, entity);

        String instanceUuid = entity.getUuid();
        if (isDetail) {
            List<VmVirtualInterfaceEntity> interfaceEntityList = vmVirtualInterfaceDao.findByInstanceUuid(instanceUuid);
            for (VmVirtualInterfaceEntity interfaceEntity: interfaceEntityList) {
                if ("private".equals(interfaceEntity.getNetworkType())) {
                    String priMac = interfaceEntity.getMac();
                    result.setPriVmMac(priMac);
                    logger.info("the vm : "+instanceUuid+", private mac: "+priMac);
                } else {
                    String pubMac = interfaceEntity.getMac();
                    result.setPubVmMac(pubMac);
                    logger.info("the vm : "+instanceUuid+", public mac: "+pubMac);
                }
            }

            VmUserEntity user = vmUserDao.findOne(entity.getUserId());
            result.setAppkeyId(user.getAppkeyId());
            result.setAppkeySecret(user.getAppkeySecret());
        }
        logger.info("the result is "+result);
        return result;
    }
}
