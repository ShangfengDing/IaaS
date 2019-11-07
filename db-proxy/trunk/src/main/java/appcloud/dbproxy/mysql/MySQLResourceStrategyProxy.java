package appcloud.dbproxy.mysql;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import appcloud.common.model.ResourceStrategy;
import appcloud.common.model.ResourceStrategy.StrategyTypeEnum;
import appcloud.common.proxy.ResourceStrategyProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.ResourceStrategyDAO;
import appcloud.dbproxy.mysql.model.ResourceStrategyTable;

public class MySQLResourceStrategyProxy implements ResourceStrategyProxy {
	private static ResourceStrategyDAO dao = new ResourceStrategyDAO();

	/**
	 * 取得所有资源分配策略
	 * @return
	 */
	@Override
	public List<? extends ResourceStrategy> findAll() throws Exception {
		List<? extends ResourceStrategy> resourceStrategys = dao.findAll();
		return resourceStrategys;
	}

	@Override
	public List<? extends ResourceStrategy> searchALL(
			QueryObject<ResourceStrategy> queryObject) throws Exception {
		return dao.findByProperties(queryObject, 0, 0);
	}
	/**
	 * 根据id取得某个资源策略
	 * @param resourceStrategyId
	 * @return
	 */
	@Override
	public ResourceStrategy getById(Integer resourceStrategyId)
			throws Exception {
		List<? extends ResourceStrategy> resourceStrategys = dao.findByProperty("id", resourceStrategyId);
		if (resourceStrategys.size() != 0) {
			ResourceStrategy resourceStrategy = resourceStrategys.get(0);
			return resourceStrategy;
		} else {
			return null;
		}
	}
	
	/**
	 * 根据uuid取得某个资源策略
	 * @param resourceStrategyUuid
	 * @return
	 */
	@Override
	public List<? extends ResourceStrategy> getByUuid(String resourceStrategyUuid)
			throws Exception {
		List<? extends ResourceStrategy> resourceStrategys = dao.findByProperty("uuid", resourceStrategyUuid);
		if (resourceStrategys.size() != 0) {
			return resourceStrategys;
		} 
		return null;
	}

	/**
	 * 保存一个资源策略，前端暂时用不到
	 * @param resourceStrategy
	 * @return
	 */
	@Override
	public void save(ResourceStrategy resourceStrategy) throws Exception {
		dao.save(new ResourceStrategyTable(resourceStrategy));
		
	}

	/**
	 * 更新一个资源策略，前端暂时用不到
	 * @param resourceStrategy
	 * @return
	 */
	@Override
	public void update(ResourceStrategy resourceStrategy) throws Exception {
		dao.update(new ResourceStrategyTable(resourceStrategy));
		
	}

	/**
	 * 根据id删除一个资源策略，前端暂时用不到
	 * @param resrcStrategyId
	 * @return
	 */
	@Override
	public void deleteById(Integer resourceStrategyId) throws Exception {
		dao.deleteByProperty("id", resourceStrategyId);
		
	}

	/**
	 * 根据uuid删除一个资源策略，前端暂时用不到
	 * @param resourceStrategyUuid
	 * @return
	 */
	@Override
	public void deleteByUuid(String resourceStrategyUuid) throws Exception {
		dao.deleteByProperty("uuid", resourceStrategyUuid);
		
	}

	/**
	 * 根据type取得某个资源策略
	 * @param type
	 * @return
	 */
	@Override
	public List<? extends ResourceStrategy> getByType(StrategyTypeEnum type) throws Exception {
		List<? extends ResourceStrategy> resourceStrategys = dao.findByProperty("type", type);
		return resourceStrategys;
	}

	public static void main(String[] args) throws Exception {
	}
}
