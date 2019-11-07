package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import appcloud.common.model.HostLoad;
import appcloud.common.proxy.MonthLoadProxy;
import appcloud.dbproxy.mysql.dao.CommonLoadDAO;
import appcloud.dbproxy.mysql.dao.VmMonthLoadDao;
import appcloud.dbproxy.mysql.model.VmDailyLoadTable;
import appcloud.dbproxy.mysql.model.VmMonthLoadTable;

public class MySQLMonthLoadProxy extends MySQLCommonLoadProxy implements MonthLoadProxy {

    private static VmMonthLoadDao dao = new VmMonthLoadDao();

    @Override
    public void save(HostLoad hostLoad) {
    	VmMonthLoadTable table = new VmMonthLoadTable(hostLoad);
        dao.save(table);
        hostLoad.setId(table.getId());
    }
	
	@Override
	protected CommonLoadDAO<? extends HostLoad> getDao() {
		return dao;
	}


}
