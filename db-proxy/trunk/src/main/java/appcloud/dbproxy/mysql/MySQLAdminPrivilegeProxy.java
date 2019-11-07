package appcloud.dbproxy.mysql;

import java.util.List;
import appcloud.common.model.AdminPrivilege;
import appcloud.common.proxy.AdminPrivilegeProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.AdminPrivilegeDAO;
import appcloud.dbproxy.mysql.model.AdminPrivilegeTable;

public class MySQLAdminPrivilegeProxy implements AdminPrivilegeProxy {
	AdminPrivilegeDAO dao = new AdminPrivilegeDAO();

	@Override
	public List<? extends AdminPrivilege> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends AdminPrivilege> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public List<? extends AdminPrivilege> searchAll(
			QueryObject<AdminPrivilege> queryObject) throws Exception {
		return searchAll(queryObject, 0, 0);
	}

	@Override
	public List<? extends AdminPrivilege> searchAll(
			QueryObject<AdminPrivilege> queryObject, Integer page, Integer size)
			throws Exception {
		List<? extends AdminPrivilege> adminPrivileges = dao.findByProperties(queryObject, page, size);
		return adminPrivileges;
	}
	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public long countSearch(QueryObject<AdminPrivilege> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public AdminPrivilege getById(Integer adminPrivilegeId) throws Exception {
		return dao.findById(adminPrivilegeId);
	}

	@Override
	public List<? extends AdminPrivilege> getByRoleId(Integer roleId) throws Exception {
		return dao.findByProperty("roleId", roleId);
	}

	@Override
	public void save(AdminPrivilege adminPrivilege) throws Exception {
		dao.save(new AdminPrivilegeTable(adminPrivilege));
	}

	@Override
	public void update(AdminPrivilege adminPrivilege) throws Exception {
		dao.update(new AdminPrivilegeTable(adminPrivilege));
	}

	@Override
	public void deleteById(Integer adminPrivilegeId) throws Exception {
		dao.deleteByPrimaryKey(adminPrivilegeId);
	}
	
	public static void main(String[] args) throws Exception {
		MySQLAdminPrivilegeProxy proxy = new MySQLAdminPrivilegeProxy();
//		AdminPrivilege adminPrivilege = new AdminPrivilege();
//		adminPrivilege.setRoleId(2);
//		adminPrivilege.setResourceId(2);
//		proxy.save(adminPrivilege);
//		System.out.println(proxy.countAll());
//		AdminPrivilege adminPrivilege = proxy.getById(4);
//		adminPrivilege.setResourceId(12);
//		proxy.update(adminPrivilege);
//		proxy.deleteById(4);
		List<? extends AdminPrivilege> adminPrivileges = proxy.getByRoleId(1);
		for (AdminPrivilege temp : adminPrivileges) {
			System.out.println(temp);
		}
	}

}
