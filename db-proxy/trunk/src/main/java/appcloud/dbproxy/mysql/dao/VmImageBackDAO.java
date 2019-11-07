package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmImageBackTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * Created by lizhenhao on 2017/11/30.
 */
public class VmImageBackDAO extends AbstractDAO<VmImageBackTable>{
    static final String PU_NAME = "AppcloudPU";

    @Override
    public Class getEntityClass() {
        return VmImageBackTable.class;
    }

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }
}
