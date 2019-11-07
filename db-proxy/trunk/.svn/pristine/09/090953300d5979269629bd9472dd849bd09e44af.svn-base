package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.model.Admin;
import appcloud.common.model.Admin.AdminRoleEnum;
import appcloud.common.model.AdminPrivilege;
import appcloud.common.model.AdminResource;
import appcloud.common.model.AdminRole;
import appcloud.common.model.AdminURL;

import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.AdminDAO;
import appcloud.dbproxy.mysql.dao.AdminPrivilegeDAO;
import appcloud.dbproxy.mysql.dao.AdminResourceDAO;
import appcloud.dbproxy.mysql.dao.AdminRoleDAO;
import appcloud.dbproxy.mysql.dao.AdminURLDAO;
import appcloud.dbproxy.mysql.model.AdminTable;

public class MySQLAdminProxy implements AdminProxy{
	private static AdminDAO dao = new AdminDAO();

	@Override
	public List<? extends Admin> findAll(boolean withRole,
			boolean withPrivilege, boolean withResource) throws Exception {
		List<? extends Admin> admins = dao.findAll();
		for (Admin admin : admins) {
			fillUpAdmin(admin, withRole, withPrivilege, withResource);
		}
		return admins;
	}

	@Override
	public List<? extends Admin> findAll(boolean withRole,
			boolean withPrivilege, boolean withResource, Integer page,
			Integer size) throws Exception {
		List<? extends Admin> admins = dao.findAll(page, size);
		for (Admin admin : admins) {
			fillUpAdmin(admin, withRole, withPrivilege, withResource);
		}
		return admins;
	}

	@Override
	public List<? extends Admin> searchAll(QueryObject<Admin> queryObject,
			boolean withRole, boolean withPrivilege, boolean withResource)
			throws Exception {
		return searchAll(queryObject, withRole, withPrivilege, withResource, 0, 0);
	}

	@Override
	public List<? extends Admin> searchAll(QueryObject<Admin> queryObject,
			boolean withRole, boolean withPrivilege, boolean withResource,
			Integer page, Integer size) throws Exception {
		List<? extends Admin> admins = dao.findByProperties(queryObject, page, size);
		for (Admin admin : admins) {
			fillUpAdmin(admin, withRole, withPrivilege, withResource);
		}
		return admins;
	}
	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}
	@Override
	public long countSearch(QueryObject<Admin> queryObject) throws Exception{
		return dao.countByProperties(queryObject);
	}
	@Override
	public Admin getById(Integer adminId, boolean withRole,
			boolean withPrivilege, boolean withResource) throws Exception {
		Admin admin = dao.findById(adminId);
		fillUpAdmin(admin, withRole, withPrivilege, withResource);
		return admin;
	}

	@Override
	public Admin getByUsername(String username, boolean withRole,
			boolean withPrivilege, boolean withResource) throws Exception {
		Admin admin = dao.findByProperty("username", username).get(0);
		fillUpAdmin(admin, withRole, withPrivilege, withResource);
		return admin;
	}

	@Override
	public void save(Admin admin) throws Exception {
		dao.save(new AdminTable(admin));
		
	}

	@Override
	public void update(Admin admin) throws Exception {
		dao.update(new AdminTable(admin));
		
	}

	@Override
	public void deleteById(Integer adminId) throws Exception {
		dao.deleteByPrimaryKey(adminId);
		
	}
	private void fillUpAdmin(Admin admin, boolean withRole, boolean withPrivilege, boolean withResource) {
		if (admin == null)
			return;
		if (withRole && admin.getRoleId() != null) {
			AdminRole adminRole = (new AdminRoleDAO()).findByPrimaryKey(admin.getRoleId());
			admin.setAdminRole(adminRole);
		}
		if (withPrivilege && admin.getRoleId() != null) {
			List<? extends AdminPrivilege> adminPrivileges = (new AdminPrivilegeDAO()).findByProperty("roleId", admin.getRoleId());
			admin.setAdminPrivileges((List<AdminPrivilege>) adminPrivileges);
		}
		
		if (withResource) {
			List<? extends AdminPrivilege> adminPrivileges = null;
			List<AdminResource> adminResources = new ArrayList<AdminResource>();
			if (withPrivilege) {
				adminPrivileges = admin.getAdminPrivileges();
			} else {
				adminPrivileges = (new AdminPrivilegeDAO()).findByProperty("roleId", admin.getRoleId());
			}
			for (AdminPrivilege adminPrivilege : adminPrivileges) {
				AdminResource adminResource = (new AdminResourceDAO()).findByPrimaryKey(adminPrivilege.getResourceId());
				List<? extends AdminURL> adminURLs = (new AdminURLDAO()).findByProperty("resourceId", adminResource.getId());
				adminResource.setAdminURLs((List<AdminURL>) adminURLs);
				adminResources.add(adminResource);
			}
			admin.setAdminResources(adminResources);
		}
	}
	
	public static void main(String[] args) throws Exception {
		MySQLAdminProxy proxy = new MySQLAdminProxy();
//		Admin admin = new Admin();
//		admin.setUsername("test1");
//		admin.setPassword("test1");
//		admin.setDisplayName("test1");
//		admin.setType(AdminRoleEnum.PLATADMIN);
//		admin.setRoleId(1);
//		proxy.save(admin);
//		System.out.println(proxy.countAll());
//		List<? extends Admin> admins = proxy.findAll(false, false, false);
//		for (Admin temp : admins) {
//			System.out.println(temp);
//		}
//		Admin admin = proxy.getById(11, false, false, false);
//		admin.setUsername("test2");
//		admin.setPassword("test2");
//		proxy.update(admin);
//		Admin admin = proxy.getById(10, false, true, true);
//		System.out.println(admin);
//		QueryObject<Admin> queryObject = new QueryObject<Admin>();
//		queryObject.addFilterBean(new FilterBean<Admin>("username", "root", FilterBeanType.BOTH_LIKE));
//		queryObject.addFilterBean(new FilterBean<Admin>("email", null, FilterBeanType.BOTH_LIKE));
//		List<Admin> admins = (List<Admin>) proxy.searchAll(queryObject, true, true, true);
//		for (Admin admin : admins) {
//			System.out.println(admin);
//		}

		Admin admin = proxy.getByUsername("root", true, true, true);
		System.out.println(admin);
	}
}