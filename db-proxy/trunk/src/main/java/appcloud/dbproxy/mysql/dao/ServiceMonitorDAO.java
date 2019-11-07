package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.ServiceMonitorTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class  ServiceMonitorDAO extends AbstractDAO<ServiceMonitorTable> {

    static final String PU_NAME = "AppcloudPU";

    @Override
    public Class<ServiceMonitorTable> getEntityClass(){ return ServiceMonitorTable.class;}

    @Override
    public String getPUName(){ return PU_NAME;}

    @Override
    public IEntityManagerHelper getEntityManagerHelper() { return new NoCacheEntityManagerHelper(); }

}
