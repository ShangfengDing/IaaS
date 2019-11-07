package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.AdminRole;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.AdminRoleDAO;
import appcloud.dbproxy.mysql.model.AdminRoleTable;

public class MySQLAdminRoleProxy implements AdminRoleProxy {
	private static AdminRoleDAO dao = new AdminRoleDAO(); 
	@Override
	public List<? extends AdminRole> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends AdminRole> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public List<? extends AdminRole> searchAll(
			QueryObject<AdminRole> queryObject) throws Exception {
		return searchAll(queryObject, 0, 0);
	}

	@Override
	public List<? extends AdminRole> searchAll(
			QueryObject<AdminRole> queryObject, Integer page, Integer size)
			throws Exception {
		return dao.findByProperties(queryObject, page, size);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}
	
	@Override
	public long countSearch(QueryObject<AdminRole> queryObject) {
		return dao.countByProperties(queryObject);
	}
	@Override
	public AdminRole getById(Integer adminRoleId) throws Exception {
		return dao.findById(adminRoleId);
	}

	@Override
	public void save(AdminRole adminRole) throws Exception {
		dao.save(new AdminRoleTable(adminRole));

	}

	@Override
	public void update(AdminRole adminRole) throws Exception {
		dao.update(new AdminRoleTable(adminRole));

	}

	@Override
	public void deleteById(Integer adminRoleId) throws Exception {
		dao.deleteByPrimaryKey(adminRoleId);
	}
	
	public static void main(String[] args) throws Exception {
		AdminRoleProxy proxy = new MySQLAdminRoleProxy();
//		AdminRole adminRole = new AdminRole();
//		adminRole.setRolename("test4");
//		adminRole.setZoneId("3, 4");
//		adminRole.setClusterId("5");
//		proxy.save(adminRole);
//		AdminRole adminRole = proxy.getById(5);
//		adminRole.setClusterId("8, 9");
//		proxy.update(adminRole);
//		proxy.deleteById(5);
		System.out.println(proxy.countAll());
		List<? extends AdminRole> adminRoles = proxy.findAll();
		for (AdminRole temp : adminRoles) {
			System.out.println(temp);
		}
	}

}
