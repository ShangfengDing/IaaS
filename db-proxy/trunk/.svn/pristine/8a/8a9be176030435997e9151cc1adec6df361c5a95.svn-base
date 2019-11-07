package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmUserDAO;
import appcloud.dbproxy.mysql.model.VmUserTable;

/**
 * @author XuanJiaxing
 * 
 */
public class MySQLVmUserProxy implements VmUserProxy {

	private static VmUserDAO dao = new VmUserDAO();
	@Override
	public List<? extends VmUser> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends VmUser> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends VmUser> searchAll(QueryObject<VmUser> queryObject)
			throws Exception {
		return searchAll(queryObject, 0, 0);
	}

	@Override
	public List<? extends VmUser> searchAll(QueryObject<VmUser> queryObject,
			Integer page, Integer size) throws Exception {
		List<? extends VmUser> users = dao.findByProperties(queryObject, page, size);
		return users;
	}

	@Override
	public long countSearch(QueryObject<VmUser> queryObject) throws Exception {
		return dao.countByProperties(queryObject);
	}
	
	@Override
	public List<? extends VmUser> getByGroupId(Integer groupId){
		List <? extends VmUser> users = dao.findByProperty("groupId", groupId);
		return users;
	}
	
	@Override
	public List<? extends VmUser> getByGroupId(Integer groupId, Integer page, Integer size) {
		List <? extends VmUser> users = dao.findByProperty("groupId", groupId, page, size);
		return users;
	}

	@Override
	public VmUser getByAppKeyId(String appKeyId) throws Exception {
		List<? extends VmUser> users = dao.findByProperty("appKeyId", appKeyId);
		if(null!=users && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public List<? extends VmUser> getByEnterpriseId(Integer enterpriseId)
			throws Exception {
		List<? extends VmUser> users = dao.findByProperty("enterpriseId", enterpriseId);
		return users;
	}
	
	

	@Override
	public VmUser getById(Integer userId) throws Exception {
		return dao.findById(userId);
	}

	@Override
	public VmUser getByUserId(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		List<? extends VmUser> userList = dao.findByProperty("userId", userId);
		if(null==userList || userList.isEmpty()) {
			return null;
		}
		return userList.get(0);
	}

	@Override
	public void save(VmUser user) throws Exception {
		dao.save(new VmUserTable(user));
	}

	@Override
	public void update(VmUser user) throws Exception {
		dao.update(new VmUserTable(user));
	}

	@Override
	public void deleteById(Integer userId) throws Exception {
		dao.deleteByPrimaryKey(userId);
	}


}
