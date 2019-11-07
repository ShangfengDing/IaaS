package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmEnterprise;
import appcloud.common.proxy.VmEnterpriseProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmEnterpriseDao;
import appcloud.dbproxy.mysql.model.VmEnterpriseTable;

/**
 * @author LinXiong
 * 
 */
public class MySQLVmEnterpriseProxy implements VmEnterpriseProxy {
	private static VmEnterpriseDao dao = new VmEnterpriseDao();
	
	@Override
	public void save(VmEnterprise enterprise) throws Exception {
		dao.save(new VmEnterpriseTable(enterprise));
	}

	@Override
	public void update(VmEnterprise enterprise) throws Exception {
		dao.update(new VmEnterpriseTable(enterprise));
		
	}

	@Override
	public void deleteById(Integer enterpriseId) throws Exception {
		dao.deleteByPrimaryKey(enterpriseId);
	}

	@Override
	public List<? extends VmEnterprise> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends VmEnterprise> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends VmEnterprise> searchAll(
			QueryObject<VmEnterprise> queryObject) throws Exception {
		return dao.findByProperties(queryObject, 0, 0);
	}

	@Override
	public List<? extends VmEnterprise> searchAll(
			QueryObject<VmEnterprise> queryObject, Integer page, Integer size)
			throws Exception {
		return dao.findByProperties(queryObject, page, size);
	}

	@Override
	public long countSearch(QueryObject<VmEnterprise> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public VmEnterprise getByOwnerId(Integer ownerId) throws Exception {
		List<VmEnterpriseTable> vmEnterpriseTables = dao.findByProperty("ownerId", ownerId);
		if(vmEnterpriseTables != null)
			return vmEnterpriseTables.get(0);
		else 
			return null;
	}

	@Override
	public List<? extends VmEnterprise> getByStatus(Boolean status,
			Integer page, Integer size) throws Exception {
		return dao.findByProperty("status", status, page, size);
	}

	@Override
	public List<? extends VmEnterprise> getByName(String name, Integer page,
			Integer size) throws Exception {
		return dao.findByProperty("name", name, page, size);
	}

	@Override
	public List<? extends VmEnterprise> getByPhone(String phone, Integer page,
			Integer size) throws Exception {
		return dao.findByProperty("phone", phone, page, size);
	}

	@Override
	public List<? extends VmEnterprise> getByEmail(String email, Integer page,
			Integer size) throws Exception {
		return dao.findByProperty("email", email, page, size);
	}

	@Override
	public List<? extends VmEnterprise> getByAddress(String address,
			Integer page, Integer size) throws Exception {
		return dao.findByProperty("address", address, page, size);
	}

	@Override
	public List<? extends VmEnterprise> getByPostcode(String postcode,
			Integer page, Integer size) throws Exception {
		return dao.findByProperty("postcode", postcode, page, size);
	}

	@Override
	public List<? extends VmEnterprise> getByHomepage(String homepage,
			Integer page, Integer size) throws Exception {
		return dao.findByProperty("homepage", homepage, page, size);
	}

	@Override
	public List<? extends VmEnterprise> getByParentCompanyId(
			Integer parentCompanyId, Integer page, Integer size)
			throws Exception {
		return dao.findByProperty("parentCompanyId", parentCompanyId, page, size);
	}

	@Override
	public VmEnterprise getById(Integer id) throws Exception {
		return dao.findById(id);
	}

}
