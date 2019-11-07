package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmInstanceTypeTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmInstanceTypeDao extends AbstractDAO<VmInstanceTypeTable>{

    static final String PU_NAME = "AppcloudPU";
    
    @Override
    public Class<VmInstanceTypeTable> getEntityClass() {
       return VmInstanceTypeTable.class;
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
