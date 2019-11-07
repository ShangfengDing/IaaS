package com.distributed.dbproxy.helper;

import com.distributed.dbproxy.DbProxyApplication;
import com.distributed.dbproxy.sql.repository.appcloud.*;
import com.distributed.dbproxy.sql.repository.appcloud_front.AppkeyDao;
import com.distributed.dbproxy.sql.repository.distribution.InstanceBackInfoDao;
import com.distributed.dbproxy.sql.repository.distribution.VmBackupDao;

/**
 * Created by Idan on 2018/3/17.
 */
public class BeanHelper {

    private static volatile VmGroupDao vmGroupDao;
    private static volatile VmHostDao vmHostDao;
    private static volatile VmZoneDao vmZoneDao;
    private static volatile VmImageBackDao vmImageBackDao;
    private static volatile VmInstanceDao vmInstanceDao;
    private static volatile VmVirtualInterfaceDao vmVirtualInterfaceDao;
    private static volatile VmUserDao vmUserDao;
    private static volatile AppkeyDao appkeyDao;
    private static volatile VmBackupDao vmBackupDao;
    private static volatile InstanceBackInfoDao instanceBackInfoDao;

    /**
     * appcloud
     */
    public static VmGroupDao getVmGroupDao() {
        if (vmGroupDao == null) {
            synchronized (BeanHelper.class) {
                return (VmGroupDao) DbProxyApplication.getBean("vmGroupDao");

            }
        }
        return vmGroupDao;
    }

    public static VmHostDao getVmHostDao() {
        if (vmHostDao == null) {
            synchronized (BeanHelper.class) {
                return (VmHostDao) DbProxyApplication.getBean("vmHostDao");

            }
        }
        return vmHostDao;
    }

    public static VmZoneDao getVmZoneDao() {
        if (vmZoneDao == null) {
            synchronized (BeanHelper.class) {
                return (VmZoneDao) DbProxyApplication.getBean("vmZoneDao");

            }
        }
        return vmZoneDao;
    }

    public static VmImageBackDao getVmImageBackDao() {
        if (vmImageBackDao == null) {
            synchronized (BeanHelper.class) {
                return (VmImageBackDao) DbProxyApplication.getBean("vmImageBackDao");
            }
        }
        return vmImageBackDao;
    }


    public static VmInstanceDao getVmInstanceDao() {
        if (vmInstanceDao == null) {
            synchronized (BeanHelper.class) {
                return (VmInstanceDao) DbProxyApplication.getBean("vmInstanceDao");
            }
        }
        return vmInstanceDao;
    }


    public static VmUserDao getVmUserDao() {
        if (vmUserDao == null) {
            synchronized (BeanHelper.class) {
                return (VmUserDao) DbProxyApplication.getBean("vmUserDao");
            }
        }
        return vmUserDao;
    }

    public static VmVirtualInterfaceDao getVmVirtualInterfaceDao() {
        if (vmVirtualInterfaceDao == null) {
            synchronized (BeanHelper.class) {
                return (VmVirtualInterfaceDao) DbProxyApplication.getBean("vmVirtualInterfaceDao");
            }
        }
        return vmVirtualInterfaceDao;
    }

    /**
     * appcloud_front
     */
    public static AppkeyDao getAppkeyDao() {
        if (appkeyDao == null) {
            synchronized (BeanHelper.class) {
                return  (AppkeyDao) DbProxyApplication.getBean("appkeyDao");
            }
        }
        return appkeyDao;
    }

    /**
     * distributions
     */
    public static VmBackupDao getVmBackupDao() {
        if (vmBackupDao == null) {
            synchronized (BeanHelper.class) {
                return  (VmBackupDao) DbProxyApplication.getBean("vmBackupDao");
            }
        }
        return vmBackupDao;
    }

    public static InstanceBackInfoDao getInstanceBackInfoDao() {
        if (instanceBackInfoDao == null) {
            synchronized (BeanHelper.class) {
                return   (InstanceBackInfoDao) DbProxyApplication.getBean("instanceBackInfoDao");
            }
        }
        return instanceBackInfoDao;
    }

}
