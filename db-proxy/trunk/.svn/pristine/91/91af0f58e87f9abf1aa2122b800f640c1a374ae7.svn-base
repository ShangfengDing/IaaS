package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmEnterpriseInvitation;
import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;
import appcloud.common.proxy.VmEnterpriseInvitationProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmEnterpriseInvitationDao;
import appcloud.dbproxy.mysql.model.VmEnterpriseInvitationTable;

/**
 * @author LinXiong
 * 
 */
public class MySQLVmEnterpriseInvitationProxy implements VmEnterpriseInvitationProxy {
	private static VmEnterpriseInvitationDao dao = new VmEnterpriseInvitationDao();
	
	@Override
	public void save(VmEnterpriseInvitation vmEnterpriseInvitation)
			throws Exception {
		dao.save(new VmEnterpriseInvitationTable(vmEnterpriseInvitation));
		
	}

	@Override
	public void update(VmEnterpriseInvitation vmEnterpriseInvitation)
			throws Exception {
		dao.update(new VmEnterpriseInvitationTable(vmEnterpriseInvitation));
	}

	@Override
	public void deleteById(Integer vmEnterpriseInvitationId) throws Exception {
		dao.deleteByPrimaryKey(vmEnterpriseInvitationId);
	}

	@Override
	public List<? extends VmEnterpriseInvitation> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends VmEnterpriseInvitation> findAll(Integer page,
			Integer size) throws Exception {
		return dao.findAll(page, size);
	}

	@Override
	public List<? extends VmEnterpriseInvitation> searchAll(
			QueryObject<VmEnterpriseInvitation> queryObject) throws Exception {
		return dao.findByProperties(queryObject, 0, 0);
	}

	@Override
	public List<? extends VmEnterpriseInvitation> searchAll(
			QueryObject<VmEnterpriseInvitation> queryObject, Integer page,
			Integer size) throws Exception {
		return dao.findByProperties(queryObject, page, size);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public long countSearch(QueryObject<VmEnterpriseInvitation> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public List<? extends VmEnterpriseInvitation> getByEnterpriseId(
			Integer enterpriseId, Integer page, Integer size) throws Exception {
		return dao.findByProperty("enterpriseId", enterpriseId, page, size);
	}

	@Override
	public List<? extends VmEnterpriseInvitation> getByUserId(Integer userId,
			Integer page, Integer size) throws Exception {
		return dao.findByProperty("userId", userId, page, size);
	}

	@Override
	public List<? extends VmEnterpriseInvitation> getByStatus(
			VmEnterpriseInvitationStatus status, Integer page, Integer size)
			throws Exception {
		return dao.findByProperty("status", status, page, size);
	}

	@Override
	public VmEnterpriseInvitation getById(Integer id) throws Exception {
		return dao.findById(id);
	}

	

}
