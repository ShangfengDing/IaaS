package com.distributed.dbproxy.service;

import com.distributed.common.entity.Appkey;
import com.distributed.common.entity.VmUser;
import com.distributed.common.service.db.AccountService;
import com.distributed.common.utils.CollectionUtil;
import com.distributed.common.utils.EntityReflectUtil;
import com.distributed.common.utils.ReflectionUtil;
import com.distributed.dbproxy.DbProxyApplication;
import com.distributed.dbproxy.helper.BeanHelper;
import com.distributed.dbproxy.sql.basic.appcloud.VmUserEntity;
import com.distributed.dbproxy.sql.basic.appcloud_front.AppkeyEntity;
import com.distributed.dbproxy.sql.repository.appcloud.VmUserDao;
import com.distributed.dbproxy.sql.repository.appcloud_front.AppkeyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Idan on 2018/1/15.
 */
public class AccountServiceImpl implements AccountService {

    private VmUserDao vmUserDao = BeanHelper.getVmUserDao();
    private AppkeyDao appkeyDao = BeanHelper.getAppkeyDao();

    @Override
    public void addUser(VmUser vmUser) {
        VmUserEntity vmUserEntity = new VmUserEntity();
        EntityReflectUtil.converJavaBean(vmUserEntity, vmUser);
        vmUserDao.save(vmUserEntity);
    }

    @Override
    public void modifyUser(VmUser vmUser) {

    }

    @Override
    public void deleteUser(VmUser vmUser) {

    }

    @Override
    public void addAppkey(Appkey appkey) {
        AppkeyEntity appkeyEntity = new AppkeyEntity();
        EntityReflectUtil.converJavaBean(appkeyEntity, appkey);
        try {
            appkeyDao.save(appkeyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyAppkey(Appkey appkey) {

    }

    @Override
    public void deleteAppkey(Appkey appkey) {

    }

    @Override
    public Appkey findByZoneIdAndEmail(String zone, String email){
        List<AppkeyEntity> appkeyEntityList = appkeyDao.findByZoneAndEmail(zone, email);
        if (CollectionUtil.isEmpty(appkeyEntityList)) {
            return null;
        } else {
            AppkeyEntity entity = appkeyEntityList.get(0);
            Appkey result = new Appkey();
            EntityReflectUtil.converJavaBean(result, entity);
            return result;
        }
    }
}
