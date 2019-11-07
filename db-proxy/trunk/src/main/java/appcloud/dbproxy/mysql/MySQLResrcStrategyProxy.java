package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.ResrcStrategy;
import appcloud.common.proxy.ResrcStrategyProxy;
import appcloud.dbproxy.mysql.dao.ResrcStrategyDAO;
import appcloud.dbproxy.mysql.model.ResrcStrategyTable;

public class MySQLResrcStrategyProxy implements ResrcStrategyProxy{
	private static ResrcStrategyDAO dao = new ResrcStrategyDAO();

	@Override
	public List<? extends ResrcStrategy> findAll() throws Exception{
		return dao.findAll();
	}

	@Override
	public ResrcStrategy getById(Integer resrcStrategyId) throws Exception{
		return dao.findByPrimaryKey(resrcStrategyId);
	}

	@Override
	public void save(ResrcStrategy resrcStrategy) throws Exception{
		dao.save(new ResrcStrategyTable(resrcStrategy));
	}

	@Override
	public void update(ResrcStrategy resrcStrategy) throws Exception{
		dao.update(new ResrcStrategyTable(resrcStrategy));
	}

	@Override
	public void deleteById(Integer resrcStrategyId) throws Exception{
		dao.deleteByPrimaryKey(resrcStrategyId);
	} 
}
