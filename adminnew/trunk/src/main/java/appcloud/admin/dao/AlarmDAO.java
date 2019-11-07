package appcloud.admin.dao;

import appcloud.admin.model.Alarm;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * Created by zouji on 2018/5/7.
 */
public class AlarmDAO extends AbstractDAO<Alarm> {

    @Override
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class AlarmDAOSingletoHolder {
        static AlarmDAO instance = new AlarmDAO();
    }

    public static AlarmDAO getInstance() {
        return AlarmDAO.AlarmDAOSingletoHolder.instance;
    }

    @Override
    public Class<Alarm> getEntityClass() {
        return Alarm.class;
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
