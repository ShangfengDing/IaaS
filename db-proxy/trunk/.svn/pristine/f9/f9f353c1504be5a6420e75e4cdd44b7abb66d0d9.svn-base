package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import appcloud.common.model.HostLoad;
import appcloud.common.proxy.DailyLoadProxy;
import appcloud.dbproxy.mysql.dao.CommonLoadDAO;
import appcloud.dbproxy.mysql.dao.VmDailyLoadDao;
import appcloud.dbproxy.mysql.model.VmDailyLoadTable;

public class MySQLDailyLoadProxy extends MySQLCommonLoadProxy implements DailyLoadProxy {

    private static VmDailyLoadDao dao = new VmDailyLoadDao();

	@Override
	public void save(HostLoad hostLoad) {
		VmDailyLoadTable table = new VmDailyLoadTable(hostLoad);
	    dao.save(table);
	    hostLoad.setId(table.getId());
	}

    @Override
    public List<? extends HostLoad> getAllDayLoadOfMonth(String uuid, Date date) {
        return dao.findByProperty2("uuid", uuid, "data", date);
    }

    @Override
    public HostLoad getOneDayLoad(String uuid, Date date) {
       return dao.getOneDayLoad(uuid, date);
    }

	@Override
	protected CommonLoadDAO<? extends HostLoad> getDao() {
		return dao;
	}

}
