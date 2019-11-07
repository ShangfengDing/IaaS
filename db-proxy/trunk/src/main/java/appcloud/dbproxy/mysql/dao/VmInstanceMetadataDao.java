package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmInstanceMetadataTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * 
* @ClassName: VmInstanceMetadataDao
* @Description: TODO
* @author haowei.yu
* @date 2013-4-4 下午3:02:11
 */
public class VmInstanceMetadataDao extends AbstractDAO<VmInstanceMetadataTable>{

    static final String PU_NAME = "AppcloudPU";
    
    @Override
    public Class<VmInstanceMetadataTable> getEntityClass() {
       return VmInstanceMetadataTable.class;
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
