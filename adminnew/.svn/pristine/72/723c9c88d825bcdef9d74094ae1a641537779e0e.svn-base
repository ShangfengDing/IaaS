package appcloud.admin.dao;

import appcloud.admin.model.HostDetail;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * Created by zouji on 2018/5/17.
 */
public class HostDetailDAO extends AbstractDAO<HostDetail>{
    @Override
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class HostDetailDAOSingletoHolder {
        static HostDetailDAO instance = new HostDetailDAO();
    }

    public static HostDetailDAO getInstance() {
        return HostDetailDAO.HostDetailDAOSingletoHolder.instance;
    }

    @Override
    public Class<HostDetail> getEntityClass() {
        return HostDetail.class;
    }

    private static final String PU_NAME = "iaas_ai_monitor_PU";

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }



}
