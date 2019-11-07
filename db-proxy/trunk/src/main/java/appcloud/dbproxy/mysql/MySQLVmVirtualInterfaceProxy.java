/**
 * File: MySQLVmVirtualInterfaceProxy.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmVirtualInterface;
import appcloud.common.proxy.VmVirtualInterfaceProxy;
import appcloud.dbproxy.mysql.dao.VmVirtualInterfaceDAO;
import appcloud.dbproxy.mysql.model.VmUsedIpTable;
import appcloud.dbproxy.mysql.model.VmVirtualInterfaceTable;

/**
 * @author weed
 *
 */
public class MySQLVmVirtualInterfaceProxy implements VmVirtualInterfaceProxy {
	
	VmVirtualInterfaceDAO __vmVirtualInterfaceDAO = new VmVirtualInterfaceDAO();

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVirtualInterfaceProxy#findAll()
	 */
	@Override
	public List<? extends VmVirtualInterface> findAll() throws Exception {
		return __vmVirtualInterfaceDAO.findAll();
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVirtualInterfaceProxy#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends VmVirtualInterface> findAll(Integer page, Integer size)
			throws Exception {
		return __vmVirtualInterfaceDAO.findAll(page, size);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVirtualInterfaceProxy#getById(java.lang.Integer)
	 */
	@Override
	public VmVirtualInterface getById(Integer vmVirtualInterfaceId)
			throws Exception {
		return __vmVirtualInterfaceDAO.findById(vmVirtualInterfaceId);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVirtualInterfaceProxy#getByInstanceUuid(java.lang.String)
	 */
	@Override
	public List<? extends VmVirtualInterface> getByInstanceUuid(String instanceUuid)
			throws Exception {
		return __vmVirtualInterfaceDAO.findByProperty("instanceUuid", instanceUuid);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVirtualInterfaceProxy#save(appcloud.common.model.VmVirtualInterface)
	 */
	@Override
	public void save(VmVirtualInterface vmVirtualInterface) throws Exception {
		__vmVirtualInterfaceDAO.save(new VmVirtualInterfaceTable(vmVirtualInterface));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVirtualInterfaceProxy#save(appcloud.common.model.VmVirtualInterface)
	 */
	@Override
	public void update(VmVirtualInterface vmVirtualInterface) throws Exception {
		__vmVirtualInterfaceDAO.update(new VmVirtualInterfaceTable(vmVirtualInterface));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVirtualInterfaceProxy#deleteById(java.lang.Integer)
	 */
	@Override
	public void deleteById(Integer vmVirtualInterfaceId) throws Exception {
		__vmVirtualInterfaceDAO.deleteByPrimaryKey(vmVirtualInterfaceId);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmVirtualInterfaceProxy#deleteByInstanceUuid(java.lang.String)
	 */
	@Override
	public void deleteByInstanceUuid(String instanceUuid) throws Exception {
		__vmVirtualInterfaceDAO.deleteByProperty("instanceUuid", instanceUuid);
	}

	@Override
	public List<? extends VmVirtualInterface> getByIp(String ip)
			throws Exception {
		return __vmVirtualInterfaceDAO.findByProperty("address", ip);
	}

	/*@Override
	public void update(VmVirtualInterface vmVirtualInterface) throws Exception {
		// TODO Auto-generated method stub
		
	}*/

}
