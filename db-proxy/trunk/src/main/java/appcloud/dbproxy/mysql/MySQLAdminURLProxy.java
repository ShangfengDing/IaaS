package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.Admin;
import appcloud.common.model.AdminURL;
import appcloud.common.proxy.AdminURLProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.AdminURLDAO;
import appcloud.dbproxy.mysql.model.AdminURLTable;

public class MySQLAdminURLProxy implements AdminURLProxy {
	AdminURLDAO dao = new AdminURLDAO();

	@Override
	public List<? extends AdminURL> findAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public List<? extends AdminURL> findAll(Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.findAll(page, size);
	}

	@Override
	public List<? extends AdminURL> searchAll(QueryObject<AdminURL> queryObject)
			throws Exception {
		return searchAll(queryObject, 0, 0);
	}

	@Override
	public List<? extends AdminURL> searchAll(
			QueryObject<AdminURL> queryObject, Integer page, Integer size)
			throws Exception {
		return dao.findByProperties(queryObject, page, size);
	}
	@Override
	public long countAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.countAll();
	}

	@Override
	public AdminURL getById(Integer adminURLId) throws Exception {
		// TODO Auto-generated method stub
		return dao.findById(adminURLId);
	}

	@Override
	public List<? extends AdminURL> getByResourceId(Integer adminResourceId) throws Exception {
		return dao.findByProperty("resourceId", adminResourceId);
	}

	@Override
	public void save(AdminURL adminURL) throws Exception {
		dao.save(new AdminURLTable(adminURL));

	}

	@Override
	public void update(AdminURL adminURL) throws Exception {
		dao.update(new AdminURLTable(adminURL));

	}

	@Override
	public void deleteById(Integer adminURLId) throws Exception {
		dao.deleteByPrimaryKey(adminURLId);
	}
	public static void main(String[] args) throws Exception {
//		MySQLAdminURLProxy proxy = new MySQLAdminURLProxy();
//		AdminURL url = proxy.getById(2);
//		System.out.println(url);
//		url.setDescription("test");
//		proxy.update(url);
//		List<? extends AdminURL> adminURLs = proxy.getByResourceId(2);
//		for (AdminURL adminURL : adminURLs) {
//			System.out.println(adminURL);
//		}
	}

}
