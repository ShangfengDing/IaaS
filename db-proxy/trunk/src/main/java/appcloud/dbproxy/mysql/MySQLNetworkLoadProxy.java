package appcloud.dbproxy.mysql;

import java.util.Calendar;

import appcloud.common.model.NetworkLoad;
import appcloud.common.proxy.NetworkLoadProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.NetworkLoadDAO;
import appcloud.dbproxy.mysql.model.NetworkLoadTable;

public class MySQLNetworkLoadProxy implements NetworkLoadProxy{
	private static NetworkLoadDAO dao = new NetworkLoadDAO();

	@Override
	public void save(NetworkLoad networkLoad) throws Exception {
		dao.save(new NetworkLoadTable(networkLoad));
	}

	@Override
	public void deleteByUuid(String uuid) throws Exception {
		dao.deleteByProperty("fatherUuid", uuid);
	}

	@Override
	public NetworkLoad getCurNetLoadByUuid(String uuid) throws Exception {
		return dao.getCurNetLoadByUuid(uuid);
	}
	
	@Override
	public NetworkLoad getNetLoadByUuid(String uuid, Calendar st, Calendar ed) throws Exception {
		return dao.getNetLoadByUuid(uuid, st, ed);
	}

	@Override
	public NetworkLoad getVersionNetLoad(String appUuid, Calendar st, Calendar ed) throws Exception {
		return dao.getVersionNetLoad(appUuid, st, ed);
	}

	@Override
	public NetworkLoad getAppNetLoad(Integer j2eeinfoId, Calendar st, Calendar ed) throws Exception {
		return dao.getAppNetLoad(j2eeinfoId, st, ed);
	}

	@Override
	public NetworkLoad getNetLoad(QueryObject<NetworkLoad> queryObject, Calendar st,
			Calendar ed) throws Exception {
		return dao.getNetLoad(queryObject, st, ed);
	}
	
	
}