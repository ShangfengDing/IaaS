/**
 * File: MySQLVmInstanceMetadataProxy.java
 * Author: weed
 * Create Time: 2013-4-15
 */
package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.proxy.VmInstanceMetadataProxy;
import appcloud.dbproxy.mysql.dao.VmInstanceDAO;
import appcloud.dbproxy.mysql.dao.VmInstanceMetadataDao;
import appcloud.dbproxy.mysql.model.VmInstanceMetadataTable;
import appcloud.dbproxy.mysql.model.VmInstanceTable;

/**
 * @author weed
 *
 */
public class MySQLVmInstanceMetadataProxy implements VmInstanceMetadataProxy {

	private static VmInstanceMetadataDao __vmInstanceMetadataDao = new VmInstanceMetadataDao();
	
	private static VmInstanceDAO __vmInstanceDAO = new VmInstanceDAO();
	
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceMetadataProxy#getByVmInstanceMetadataId(java.lang.Integer, boolean)
	 */
	@Override
	public VmInstanceMetadata getByVmInstanceMetadataId(
			Integer vmInstanceMetadataId, boolean withVmInstance)
			throws Exception {
		VmInstanceMetadataTable vmInstanceMetadata = __vmInstanceMetadataDao.findById(vmInstanceMetadataId);
		fillIn(vmInstanceMetadata, withVmInstance);
		
		return vmInstanceMetadata;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceMetadataProxy#getByVmInstanceId(java.lang.Integer, boolean)
	 */
	@Override
	public List<? extends VmInstanceMetadata> getByVmInstanceId(
			Integer vmInstanceId, boolean withVmInstance) throws Exception {
		List<VmInstanceMetadataTable> vmInstanceMetadatas = __vmInstanceMetadataDao.findByProperty("vmInstanceId", vmInstanceId);
		for (VmInstanceMetadataTable vmInstanceMetadata : vmInstanceMetadatas){
			fillIn(vmInstanceMetadata, withVmInstance);
		}
		
		return vmInstanceMetadatas;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceMetadataProxy#save(appcloud.common.model.VmInstanceMetadata)
	 */
	@Override
	public void save(VmInstanceMetadata vmInstanceMetadata) throws Exception {
		__vmInstanceMetadataDao.save(new VmInstanceMetadataTable(vmInstanceMetadata));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceMetadataProxy#update(appcloud.common.model.VmInstanceMetadata)
	 */
	@Override
	public void update(VmInstanceMetadata vmInstanceMetadata) throws Exception {
		__vmInstanceMetadataDao.update(new VmInstanceMetadataTable(vmInstanceMetadata));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceMetadataProxy#deleteById(java.lang.Integer)
	 */
	@Override
	public void deleteById(Integer vmInstanceMetadataId) throws Exception {
		__vmInstanceMetadataDao.deleteByPrimaryKey(vmInstanceMetadataId);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceMetadataProxy#deleteByVmInstanceId(java.lang.Integer)
	 */
	@Override
	public void deleteByVmInstanceId(Integer vmInstanceId) throws Exception {
		__vmInstanceMetadataDao.deleteByProperty("vmInstanceId", vmInstanceId);
	}

	private void fillIn(VmInstanceMetadataTable vmInstanceMetadata, boolean withVmInstance){
		if (withVmInstance){
			fillInVmInstance(vmInstanceMetadata);
		}
	}
	
	private void fillInVmInstance(VmInstanceMetadataTable vmInstanceMetadata){
		VmInstanceTable vmInstanceTable = __vmInstanceDAO.findById(vmInstanceMetadata.getVmInstanceId());
		vmInstanceMetadata.setVmInstance(vmInstanceTable);
	}
}
