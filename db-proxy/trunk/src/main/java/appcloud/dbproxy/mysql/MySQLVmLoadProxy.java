package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import appcloud.common.model.HostLoad;
import appcloud.common.proxy.VmLoadProxy;
import appcloud.dbproxy.mysql.dao.CommonLoadDAO;
import appcloud.dbproxy.mysql.dao.VmLoadDao;
import appcloud.dbproxy.mysql.model.VmLoadTable;
import appcloud.dbproxy.mysql.model.VmMonthLoadTable;

public class MySQLVmLoadProxy extends MySQLCommonLoadProxy implements VmLoadProxy {

    private static VmLoadDao dao = new VmLoadDao();

    @Override
    public void save(HostLoad vmLoad) {
    	VmLoadTable table = new VmLoadTable(vmLoad);
        dao.save(table);
        vmLoad.setId(table.getId());
    }
	
    @Override
    public List<? extends HostLoad> getOneDayLoad(String uuid, Date date) {
    	return dao.getOneDayLoad(uuid, date);
    }
	
    @Override
    public HostLoad getLatestLoad(String uuid) {
        return dao.getLatestLoad(uuid);
    }

    @Override
    public List<? extends HostLoad> getLatestLoad(String uuid, int nums) {
        return dao.getLatestLoad(uuid, nums);
    }

    @Override
    public HostLoad getOneDayAvgLoad(String uuid, Date date) {
    	return dao.getOneDayAvgLoad(uuid, date);
    }

	@Override
	protected CommonLoadDAO<? extends HostLoad> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
