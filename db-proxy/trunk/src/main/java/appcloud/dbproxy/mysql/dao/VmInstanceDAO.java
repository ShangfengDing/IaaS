package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmInstanceTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * 
* @ClassName: VmInstanceDAO
* @author haowei.yu
* @date 2013-4-4 下午2:55:49
 */
public class VmInstanceDAO extends AbstractDAO<VmInstanceTable>{

    static final String PU_NAME = "AppcloudPU";
    
    @Override
    public Class<VmInstanceTable> getEntityClass() {
        return VmInstanceTable.class;
    }

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        // TODO Auto-generated method stub
        return new NoCacheEntityManagerHelper();
    }
    

}
