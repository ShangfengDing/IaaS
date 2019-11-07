package appcloud.dbproxy.mysql;

import appcloud.common.model.Load;
import appcloud.common.proxy.LoadProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.dbproxy.mysql.dao.LoadDAO;
import appcloud.dbproxy.mysql.model.LoadTable;

public class MySQLLoadProxy implements LoadProxy{
	private static LoadDAO dao = new LoadDAO();

	@Override
	public Load getCurLoadByUuid(String uuid) throws Exception {
		//return dao.getCurLoadByUuid(uuid);
		return ConnectionFactory.getLoadProxy().getCurLoadByUuid(uuid);
	}

	@Override
	public void save(Load load) throws Exception {
		//dao.save(new LoadTable(load));
		ConnectionFactory.getLoadProxy().save(load);
	}

	@Override
	public void deleteByUuid(String uuid) throws Exception {
		//dao.deleteByProperty("fatherUuid", uuid);
	}
}