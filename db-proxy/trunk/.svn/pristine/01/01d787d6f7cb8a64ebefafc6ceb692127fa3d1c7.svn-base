/**
 * File: MySQLVmMacSequenceProxy.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmMacSequence;
import appcloud.common.proxy.VmMacSequenceProxy;
import appcloud.dbproxy.mysql.dao.VmMacSequenceDAO;
import appcloud.dbproxy.mysql.model.VmMacSequenceTable;

/**
 * @author weed
 *
 */
public class MySQLVmMacSequenceProxy implements VmMacSequenceProxy {

	VmMacSequenceDAO __vmMacSequenceDAO = new VmMacSequenceDAO();
	
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmMacSequenceProxy#findAll()
	 */
	@Override
	public List<? extends VmMacSequence> findAll() throws Exception {
		return __vmMacSequenceDAO.findAll();
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmMacSequenceProxy#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends VmMacSequence> findAll(Integer page, Integer size)
			throws Exception {
		return __vmMacSequenceDAO.findAll(page, size);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmMacSequenceProxy#getById(java.lang.Integer)
	 */
	@Override
	public VmMacSequence getById(Integer vmMacSequenceId) throws Exception {
		return __vmMacSequenceDAO.findById(vmMacSequenceId);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmMacSequenceProxy#getByClusterId(java.lang.Integer)
	 */
	@Override
	public VmMacSequence getByClusterId(Integer clusterId) throws Exception {
		List<VmMacSequenceTable> vmMacSequences = __vmMacSequenceDAO.findByProperty("clusterId", clusterId);
		if (vmMacSequences.size() < 1){
			return null;
		}
		else{
			return vmMacSequences.get(0);
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmMacSequenceProxy#save(appcloud.common.model.VmMacSequence)
	 */
	@Override
	public void save(VmMacSequence vmMacSequence) throws Exception {
		__vmMacSequenceDAO.save(new VmMacSequenceTable(vmMacSequence));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmMacSequenceProxy#deleteById(java.lang.Integer)
	 */
	@Override
	public void deleteById(Integer vmMacSequenceId) throws Exception {
		__vmMacSequenceDAO.deleteByPrimaryKey(vmMacSequenceId);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmMacSequenceProxy#deleteByClusterId(java.lang.Integer)
	 */
	@Override
	public void deleteByClusterId(Integer clusterId) throws Exception {
		__vmMacSequenceDAO.deleteByProperty("clusterId", clusterId);
	}

}
