package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmVlan;
import appcloud.common.proxy.VmVlanProxy;
import appcloud.dbproxy.mysql.dao.VmVlanDAO;
import appcloud.dbproxy.mysql.model.VmVlanTable;

public class MySQLVmVlanProxy implements VmVlanProxy{
	private static VmVlanDAO dao = new VmVlanDAO();

	@Override
	public List<? extends VmVlan> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<? extends VmVlan> findAll(Integer page, Integer size)
			throws Exception {
		return dao.findAll(0, 0);
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public VmVlan getById(Integer vlanId) throws Exception {
		return dao.findById(vlanId);
	}

	@Override
	public void save(VmVlan vlan) throws Exception {
		dao.save(new VmVlanTable(vlan));
	}

	@Override
	public void update(VmVlan vlan) throws Exception {
		dao.update(new VmVlanTable(vlan));
	}

	@Override
	public void deleteById(Integer vlanId) throws Exception {
		dao.deleteByPrimaryKey(vlanId);
	}

}
