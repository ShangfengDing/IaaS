package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmSummaryTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmSummaryDAO extends AbstractDAO<VmSummaryTable> {

    static final String PU_NAME = "AppcloudPU";

    @Override
    public Class<VmSummaryTable> getEntityClass(){ return VmSummaryTable.class;}

    @Override
    public String getPUName(){ return PU_NAME;}

    @Override
    public IEntityManagerHelper getEntityManagerHelper() { return new NoCacheEntityManagerHelper(); }
}
