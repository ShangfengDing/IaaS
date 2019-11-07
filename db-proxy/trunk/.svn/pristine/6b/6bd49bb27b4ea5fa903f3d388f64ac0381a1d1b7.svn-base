package appcloud.dbproxy.mysql;

import java.util.Date;
import java.util.List;

import appcloud.common.model.HostLoad;
import appcloud.common.proxy.CommonLoadProxy;
import appcloud.dbproxy.mysql.dao.CommonLoadDAO;

public abstract class MySQLCommonLoadProxy implements CommonLoadProxy {
	
	@Override
	public List<? extends HostLoad> getLoads(String uuid, Date startTime, Date endTime) {
		return getDao().getLoads(uuid, startTime, endTime);
	}

	@Override
	public HostLoad getAvgLoad(String uuid, Date startTime, Date endTime) {
		return getDao().getAvgLoad(uuid, startTime, endTime);
	}
	
	@Override
	public List<? extends HostLoad> getAvgLoads(Date startTime, Date endTime) {
		return getDao().getAvgLoads(startTime, endTime);
	}
	
	@Override
	public int deleteBefore(String uuid, Date time) {
		return getDao().deleteBefore(uuid, time);
	}

	@Override
	public int deleteAllBefore(Date time) {
		return getDao().deleteBefore(time);
	}

	@Override
	public int deleteByDate(Date time) {
		return getDao().delete(time);
	}
	
	@Override
	public void delete(Integer id) {
		getDao().deleteByPrimaryKey(id);
	}
	
	
	protected abstract CommonLoadDAO<? extends HostLoad> getDao();

}
