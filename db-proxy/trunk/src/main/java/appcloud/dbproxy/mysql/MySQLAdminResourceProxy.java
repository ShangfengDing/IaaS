package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.AdminResource;
import appcloud.common.proxy.AdminResourceProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.AdminResourceDAO;
import appcloud.dbproxy.mysql.model.AdminResourceTable;

public class MySQLAdminResourceProxy implements AdminResourceProxy {
	AdminResourceDAO dao = new AdminResourceDAO();
	@Override
	public List<? extends AdminResource> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends AdminResource> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public List<? extends AdminResource> searchAll(
			QueryObject<AdminResource> queryObject) throws Exception {
		return searchAll(queryObject, 0, 0);
	}

	@Override
	public List<? extends AdminResource> searchAll(
			QueryObject<AdminResource> queryObject, Integer page, Integer size)
			throws Exception {
		List<? extends AdminResource> adminResources = dao.findByProperties(queryObject, page, size);
		return adminResources;
	}
	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public AdminResource getById(Integer adminResourceId) throws Exception {
		return dao.findById(adminResourceId);
	}

	@Override
	public void save(AdminResource adminResource) throws Exception {
		dao.save(new AdminResourceTable(adminResource));
	}

	@Override
	public void update(AdminResource adminResource) throws Exception {
		dao.update(new AdminResourceTable(adminResource));
	}

	@Override
	public void deleteById(Integer adminResourceId) throws Exception {
		dao.deleteByPrimaryKey(adminResourceId);
	}
	public static void main(String[] args) throws Exception {
		MySQLAdminResourceProxy proxy = new MySQLAdminResourceProxy();
//		AdminResource adminResource = new AdminResource();
//		adminResource.setTopBarId(0);
//		adminResource.setLeftBarId(2);
//		adminResource.setTopBarName("系统管理");
//		adminResource.setLeftBarName("模板管理");
//		proxy.save(adminResource);
//		AdminResource adminResource = proxy.getById(5);
//		adminResource.setTopBarId(1);
//		adminResource.setLeftBarId(0);
//		adminResource.setTopBarName("test");
//		proxy.update(adminResource);
//		proxy.deleteById(5);
		System.out.println(proxy.countAll());
		List<? extends AdminResource> adminResources = proxy.findAll();
		for (AdminResource temp : adminResources) {
			System.out.println(temp);
		}
	}
}
