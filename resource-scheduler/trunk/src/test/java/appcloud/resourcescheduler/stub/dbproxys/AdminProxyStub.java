// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.model.Admin;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.query.QueryObject;
;

public class AdminProxyStub implements AdminProxy{
	
	@Override
	public void save(Admin arg0) throws Exception{
	}
	@Override
	public void update(Admin arg0) throws Exception{
	}
	@Override
	public long countAll() throws Exception{
		return 0;
	}
	@Override
	public void deleteById(Integer arg0) throws Exception{
	}
	@Override
	public List<? extends Admin> findAll(boolean withRole,
			boolean withPrivilege, boolean withResource) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<? extends Admin> findAll(boolean withRole,
			boolean withPrivilege, boolean withResource, Integer page,
			Integer size) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<? extends Admin> searchAll(QueryObject<Admin> queryObject,
			boolean withRole, boolean withPrivilege, boolean withResource)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<? extends Admin> searchAll(QueryObject<Admin> queryObject,
			boolean withRole, boolean withPrivilege, boolean withResource,
			Integer page, Integer size) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long countSearch(QueryObject<Admin> queryObject) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Admin getById(Integer adminId, boolean withRole,
			boolean withPrivilege, boolean withResource) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Admin getByUsername(String username, boolean withRole,
			boolean withPrivilege, boolean withResource) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
