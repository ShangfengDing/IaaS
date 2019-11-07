package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmGroup;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmGroupDAO;
import appcloud.dbproxy.mysql.model.VmGroupTable;

/**
 * @author XuanJiaxing
 * 
 */
public class MySQLVmGroupProxy implements VmGroupProxy{

	private static VmGroupDAO  dao = new VmGroupDAO();
	@Override
	public List<? extends VmGroup> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends VmGroup> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends VmGroup> searchAll(QueryObject<VmGroup> queryObject)
			throws Exception {
		return searchAll(queryObject, 0, 0);
	}

	@Override
	public List<? extends VmGroup> searchAll(QueryObject<VmGroup> queryObject,
			Integer page, Integer size) throws Exception {
		List <? extends VmGroup> groups = dao.findByProperties(queryObject, page, size);
		return groups;
	}

	@Override
	public long countSearch(QueryObject<VmGroup> queryObject) throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public VmGroup getById(Integer groupId) throws Exception {
		return dao.findById(groupId);
	}

	@Override
	public void save(VmGroup group) throws Exception {
		dao.save(new VmGroupTable(group));
	}

	@Override
	public void update(VmGroup group) throws Exception {
		dao.update(new VmGroupTable(group));
	}

	@Override
	public void deleteById(Integer groupId) throws Exception {
		dao.deleteByPrimaryKey(groupId);
	}

}
