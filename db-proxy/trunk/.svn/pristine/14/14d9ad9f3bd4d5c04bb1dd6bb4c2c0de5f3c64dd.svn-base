package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import appcloud.common.model.HostLoad;
import appcloud.common.proxy.HostLoadProxy;
import appcloud.dbproxy.mysql.dao.CommonLoadDAO;
import appcloud.dbproxy.mysql.dao.HostLoadDao;
import appcloud.dbproxy.mysql.model.HostLoadTable;

public class MySQLHostLoadProxy extends MySQLCommonLoadProxy implements HostLoadProxy {

    private static HostLoadDao dao = new HostLoadDao();
    
    @Override
    public void save(HostLoad hostLoad) {
    	HostLoadTable table = new HostLoadTable(hostLoad);
        dao.save(table);
        hostLoad.setId(table.getId());
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
	public List<? extends HostLoad> getLoads(String uuid, Date startTime, Date endTime) {
		return dao.getLoads(uuid, startTime, endTime);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteByPrimaryKey(id);
	}

	@Override
	protected CommonLoadDAO<? extends HostLoad> getDao() {
		return dao;
	}


}
