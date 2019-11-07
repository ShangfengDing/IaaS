package com.distributed.dbproxy.service;

import com.distributed.common.entity.InstanceBackInfo;
import com.distributed.common.entity.VmBack;
import com.distributed.common.entity.VmImageBack;
import com.distributed.common.entity.query.QueryObject;
import com.distributed.common.service.db.VmBackupService;
import com.distributed.common.utils.CollectionUtil;
import com.distributed.common.utils.EntityReflectUtil;
import com.distributed.common.utils.ModelUtil;
import com.distributed.dbproxy.helper.BeanHelper;
import com.distributed.dbproxy.sql.basic.appcloud.VmImageBackEntity;
import com.distributed.dbproxy.sql.basic.distribution.InstanceBackInfoEntity;
import com.distributed.dbproxy.sql.basic.distribution.VmBackEntity;
import com.distributed.dbproxy.sql.repository.appcloud.VmImageBackDao;
import com.distributed.dbproxy.sql.repository.distribution.InstanceBackInfoDao;
import com.distributed.dbproxy.sql.repository.distribution.VmBackupDao;
import com.distributed.dbproxy.utils.SpecificationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Idan on 2017/12/7.
 */
public class VmBackupServiceImpl implements VmBackupService {

    private final Logger logger = LoggerFactory.getLogger(VmBackupServiceImpl.class);
    private SpecificationUtil<VmBackEntity> specificationUtil = new SpecificationUtil<>();


    private VmBackupDao vmBackupDao = BeanHelper.getVmBackupDao();
    private InstanceBackInfoDao instanceBackInfoDao = BeanHelper.getInstanceBackInfoDao();
    private VmImageBackDao vmImageBackService = BeanHelper.getVmImageBackDao();

    @Override
    public VmBack findByUuid(String uuid) {
        try {
            VmBackEntity vmBackEntity = vmBackupDao.findByUuid(uuid);
            if (ModelUtil.isEmpty(vmBackEntity)) {
                logger.info("the vmback is not existed, uuid="+uuid);
                return null;
            }
            VmBack result = new VmBack();
            EntityReflectUtil.converJavaBean(result, vmBackEntity);
            logger.info("the result is "+result.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<VmBack> findByParams(String instanceUuid, String sourceHostUuid, String destHostUuid, String sourceDataCenter, String destDataCenter, String status, Boolean isDetailForInfo) {
        Map<String, QueryObject> searchMap = new HashMap<>();
        if (!StringUtils.isEmpty(instanceUuid)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {instanceUuid};
            searchMap.put("instanceUuid", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!StringUtils.isEmpty(sourceHostUuid)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {sourceHostUuid};
            searchMap.put("sourceHostUuid", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!StringUtils.isEmpty(destHostUuid)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {destHostUuid};
            searchMap.put("destHostUuid", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!StringUtils.isEmpty(sourceDataCenter)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {sourceDataCenter};
            searchMap.put("sourceDataCenter", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!StringUtils.isEmpty(destDataCenter)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {destDataCenter};
            searchMap.put("destDataCenter", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        if (!StringUtils.isEmpty(status)) {
            Class<?>[] classes = {String.class};
            Object[] objects = {status};
            searchMap.put("status", new QueryObject(classes, QueryObject.QueryType.EQUAL, objects));
        }
        Specification<VmBackEntity> where = specificationUtil.where(searchMap);
        if (where == null) {
            return null;
        }
        List<VmBackEntity> searchResult = null;
        try {
            searchResult = vmBackupDao.findAll(where);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        List<VmBack> result = new ArrayList<>();
        if (isDetailForInfo != null && isDetailForInfo) {
            for (VmBackEntity entity: searchResult) {
                try {
                    VmBack back = new VmBack();
                    EntityReflectUtil.converJavaBean(back, entity);

                    // 跟backId 查找虚拟机的具体信息
                    String uuid = entity.getUuid();
                    InstanceBackInfoEntity infoEntity = instanceBackInfoDao.findByBackupUuid(uuid);
                    InstanceBackInfo info = new InstanceBackInfo();
                    EntityReflectUtil.converJavaBean(info, infoEntity);
                    back.setInstanceBackInfo(info);

                    // top imageBack
                    List<VmImageBackEntity> imageBackEntityList = vmImageBackService.findByInstanceUuidAndTop(entity.getInstanceUuid(), true);
                    if (!CollectionUtil.isEmpty(imageBackEntityList)) {
                        VmImageBackEntity topImageBackEntity = imageBackEntityList.get(0);
                        VmImageBack topImageBack = new VmImageBack();
                        EntityReflectUtil.converJavaBean(topImageBack, topImageBackEntity);
                        back.setVmImageBack(topImageBack);
                    }

                    result.add(back);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (VmBackEntity entity: searchResult) {
                try {
                    VmBack back = new VmBack();
                    EntityReflectUtil.converJavaBean(back, entity);

                    result.add(back);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public List<VmBack> findBySrcDataCenter(String srcDataCenter) {
        try {
            List<VmBackEntity> vmBackEntities =vmBackupDao.findBySourceDataCenter(srcDataCenter);
            List<VmBack> vmBacks = new ArrayList<>();
            for (VmBackEntity entity : vmBackEntities) {
                VmBack vmBack = new VmBack();
                EntityReflectUtil.converJavaBean(vmBack, entity);
                vmBacks.add(vmBack);
            }
            return vmBacks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<VmBack> findByDestDataCenter(String destDataCenter) {
        try {
            List<VmBackEntity> vmBackEntities =vmBackupDao.findByDestDataCenter(destDataCenter);
            List<VmBack> vmBacks = new ArrayList<>();
            for (VmBackEntity entity : vmBackEntities) {
                VmBack vmBack = new VmBack();
                EntityReflectUtil.converJavaBean(vmBack, entity);
                vmBacks.add(vmBack);
            }
            return vmBacks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;    }


    @Override
    public Boolean updateVmBack(String vmBackUuid, String sourceHostUuid, String destHostUuid, String sourceDataCenter, String destDataCenter, String status) {
        try {
            if (StringUtils.isEmpty(status)) {
                logger.warn("the status is null");
                return false;
            }
            if (!StringUtils.isEmpty(vmBackUuid)) {
                vmBackupDao.updateByUuid(status, vmBackUuid);
            }
            if (!StringUtils.isEmpty(sourceHostUuid)) {
                vmBackupDao.updateBySourceHost(status, sourceHostUuid);
            }
            if (!StringUtils.isEmpty(destHostUuid)) {
                vmBackupDao.updateByDestHost(status, destHostUuid);
            }
            if (!StringUtils.isEmpty(sourceDataCenter)) {
                vmBackupDao.updateBySourceDataCenter(status, sourceDataCenter);
            }
            if (!StringUtils.isEmpty(destDataCenter)) {
                vmBackupDao.updateByDestDataCenter(status, destDataCenter);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteVmBack(Integer id) {
        try {
            vmBackupDao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean add(VmBack vmBack) {
        if (ModelUtil.isEmpty(vmBack)) {
            return false;
        }

        try {
            VmBackEntity entity = new VmBackEntity();
            EntityReflectUtil.converJavaBean(entity, vmBack);
            vmBackupDao.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
