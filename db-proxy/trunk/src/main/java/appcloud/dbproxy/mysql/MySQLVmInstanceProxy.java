/**
 * File: MySQLVmInstanceProxy.java
 * Author: weed
 * Create Time: 2013-4-15
 */
package appcloud.dbproxy.mysql;

import java.util.LinkedList;
import java.util.List;

import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.ClusterDAO;
import appcloud.dbproxy.mysql.dao.HostDAO;
import appcloud.dbproxy.mysql.dao.VmInstanceDAO;
import appcloud.dbproxy.mysql.dao.VmInstanceMetadataDao;
import appcloud.dbproxy.mysql.dao.VmInstanceTypeDao;
import appcloud.dbproxy.mysql.dao.VmImageDAO;
import appcloud.dbproxy.mysql.dao.VmSecurityGroupDAO;
import appcloud.dbproxy.mysql.dao.VmVirtualInterfaceDAO;
import appcloud.dbproxy.mysql.dao.VmVolumeDAO;
import appcloud.dbproxy.mysql.model.ClusterTable;
import appcloud.dbproxy.mysql.model.HostTable;
import appcloud.dbproxy.mysql.model.VmInstanceMetadataTable;
import appcloud.dbproxy.mysql.model.VmInstanceTable;
import appcloud.dbproxy.mysql.model.VmInstanceTypeTable;
import appcloud.dbproxy.mysql.model.VmImageTable;
import appcloud.dbproxy.mysql.model.VmVirtualInterfaceTable;
import appcloud.dbproxy.mysql.model.VmVolumeTable;

/**
 * @author weed
 * 
 */
public class MySQLVmInstanceProxy implements VmInstanceProxy {

	private static VmInstanceDAO __vmInstanceDAO = new VmInstanceDAO();

	private static HostDAO __hostDAO = new HostDAO();
	private static ClusterDAO __clusterDAO = new ClusterDAO();
	private static VmImageDAO __vmPublicImageDAO = new VmImageDAO();
	private static VmInstanceTypeDao __vmInstanceTypeDao = new VmInstanceTypeDao();	
	private static VmInstanceMetadataDao __vmInstanceMetadataDao = new VmInstanceMetadataDao();
	private static VmVolumeDAO __vmVolumeDAO = new VmVolumeDAO();
	private static VmVirtualInterfaceDAO __vmVIFDAO = new VmVirtualInterfaceDAO();
	private static VmSecurityGroupDAO __vmSecurityGroupDAO = new VmSecurityGroupDAO();

	@Override
	public List<? extends VmInstance> findAll(boolean withHost,
			boolean withCluster, boolean withVmPublicImage,
			boolean withVmInstanceType, boolean withVmInstanceMetadata,
			boolean withVmVolume, boolean withVmNetWork,
			boolean withVmSecurityGroup) throws Exception {

		List<VmInstanceTable> vmInstanceTables = __vmInstanceDAO.findAll();

		fillInRefers(vmInstanceTables, withHost, withCluster,
				withVmPublicImage, withVmInstanceType, withVmInstanceMetadata,
				withVmVolume, withVmNetWork, withVmSecurityGroup);

		return vmInstanceTables;
	}

	@Override
	public List<? extends VmInstance> findAll(boolean withHost,
			boolean withCluster, boolean withVmPublicImage,
			boolean withVmInstanceType, boolean withVmInstanceMetadata,
			boolean withVmVolume, boolean withVmNetWork,
			boolean withVmSecurityGroup, Integer page, Integer size)
			throws Exception {
		List<VmInstanceTable> vmInstanceTables = __vmInstanceDAO.findAll(page,
				size);

		fillInRefers(vmInstanceTables, withHost, withCluster,
				withVmPublicImage, withVmInstanceType, withVmInstanceMetadata,
				withVmVolume, withVmNetWork, withVmSecurityGroup);
		
		return vmInstanceTables;
	}

	@Override
	public long countAll() throws Exception {
		// TODO Auto-generated method stub
		return __vmInstanceDAO.countAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.VmInstanceProxy#searchAll(appcloud.common.util.
	 * query.QueryObject, boolean, boolean, boolean, boolean, boolean, boolean)
	 */
	@Override
	public List<? extends VmInstance> searchAll(
			QueryObject<VmInstance> queryObject, boolean withHost,
			boolean withCluster, boolean withVmPublicImage,
			boolean withVmInstanceType, boolean withVmInstanceMetadata,
			boolean withVmVolume, boolean withVmNetWork,
			boolean withVmSecurityGroup) throws Exception {
		List<VmInstanceTable> vmInstanceTables = __vmInstanceDAO.findByProperties(queryObject, 0, 0);

		fillInRefers(vmInstanceTables, withHost, withCluster,
				withVmPublicImage, withVmInstanceType, withVmInstanceMetadata,
				withVmVolume, withVmNetWork, withVmSecurityGroup);
		
		return vmInstanceTables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.VmInstanceProxy#searchAll(appcloud.common.util.
	 * query.QueryObject, boolean, boolean, boolean, boolean, boolean, boolean,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends VmInstance> searchAll(
			QueryObject<VmInstance> queryObject, boolean withHost,
			boolean withCluster, boolean withVmPublicImage,
			boolean withVmInstanceType, boolean withVmInstanceMetadata,
			boolean withVmVolume, boolean withVmNetWork,
			boolean withVmSecurityGroup, Integer page, Integer size)
			throws Exception {
		List<VmInstanceTable> vmInstanceTables = __vmInstanceDAO.findByProperties(queryObject, page, size);

		fillInRefers(vmInstanceTables, withHost, withCluster,
				withVmPublicImage, withVmInstanceType, withVmInstanceMetadata,
				withVmVolume, withVmNetWork, withVmSecurityGroup);
		
		return vmInstanceTables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.VmInstanceProxy#countSearch(appcloud.common.util
	 * .query.QueryObject)
	 */
	@Override
	public long countSearch(QueryObject<VmInstance> queryObject)
			throws Exception {

		return __vmInstanceDAO.countByProperties(queryObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see appcloud.common.proxy.VmInstanceProxy#getByUuid(java.lang.String,
	 * boolean, boolean, boolean, boolean, boolean, boolean)
	 */
	@Override
	public VmInstance getByUuid(String uuid, boolean withHost,
			boolean withCluster, boolean withVmPublicImage,
			boolean withVmInstanceType, boolean withVmInstanceMetadata,
			boolean withVmVolume, boolean withVmNetWork,
			boolean withVmSecurityGroup) throws Exception {
		List<VmInstanceTable> vmInstanceTables = __vmInstanceDAO.findByProperty("uuid", uuid);
		
		if (vmInstanceTables.size() < 1){
			return null;
		}
		else{
			fillInRefers(vmInstanceTables, withHost, withCluster,
					withVmPublicImage, withVmInstanceType, withVmInstanceMetadata,
					withVmVolume, withVmNetWork, withVmSecurityGroup);
			return vmInstanceTables.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.VmInstanceProxy#save(appcloud.common.model.VmInstance
	 * )
	 */
	@Override
	public void save(VmInstance vmInstance) throws Exception {
		__vmInstanceDAO.save(new VmInstanceTable(vmInstance));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.VmInstanceProxy#update(appcloud.common.model.VmInstance
	 * )
	 */
	@Override
	public void update(VmInstance vmInstance) throws Exception {
		__vmInstanceDAO.update(new VmInstanceTable(vmInstance));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see appcloud.common.proxy.VmInstanceProxy#deleteByUuid(java.lang.String)
	 */
	@Override
	public void deleteByUuid(String uuid) throws Exception {
		__vmInstanceDAO.deleteByProperty("uuid", uuid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see appcloud.common.proxy.VmInstanceProxy#deleteById(java.lang.Integer)
	 */
	@Override
	public void deleteById(Integer VmInstanceId) throws Exception {
		__vmInstanceDAO.deleteByPrimaryKey(VmInstanceId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see appcloud.common.proxy.VmInstanceProxy#findAllUuid()
	 */
	@Override
	public List<String> findAllUuid() throws Exception {
		List<VmInstanceTable> vmInstanceTables = __vmInstanceDAO.findAll();

		List<String> uuids = new LinkedList<String>();
		for (VmInstanceTable vmInstanceTable : vmInstanceTables) {
			uuids.add(vmInstanceTable.getUuid());
		}
		return uuids;
	}

	private void fillInRefers(List<VmInstanceTable> vmInstanceTables,
			boolean withHost, boolean withCluster, boolean withVmPublicImage,
			boolean withVmInstanceType, boolean withVmInstanceMetadata,
			boolean withVmVolume, boolean withVmNetWork,
			boolean withVmSecurityGroup) {
		if (withHost) {
			fillInHost(vmInstanceTables);
		}

		if (withCluster) {
			fillInCluster(vmInstanceTables);
		}

		if (withVmPublicImage) {
			fillInVmPublicImage(vmInstanceTables);
		}

		if (withVmInstanceType) {
			fillInVmInstanceType(vmInstanceTables);
		}

		if (withVmInstanceMetadata) {
			fillInVmInstanceMetadata(vmInstanceTables);
		}

		if (withVmVolume) {
			fillInVmVolume(vmInstanceTables);
		}
		
		if (withVmNetWork) {
			fillInVmNetWork(vmInstanceTables);
		}

		if (withVmSecurityGroup) {
			fillInVmSecurityGroup(vmInstanceTables);
		}
	}

	private void fillInHost(List<VmInstanceTable> vmInstanceTables) {
		for (VmInstanceTable vmInstanceTable : vmInstanceTables) {
			List<HostTable> hosts = __hostDAO.findByProperty("uuid",
					vmInstanceTable.getHostUuid());
			if (hosts.size() == 1) {
				vmInstanceTable.setHost(hosts.get(0));
			} else if (hosts.size() < 1) {
				// no hosts
			} else {
				// repeated uuid
			}
		}
	}

	private void fillInCluster(List<VmInstanceTable> vmInstanceTables) {
		for (VmInstanceTable vmInstanceTable : vmInstanceTables) {
			ClusterTable cluster = __clusterDAO.findById(vmInstanceTable
					.getAvailabilityClusterId());
			vmInstanceTable.setCluster(cluster);
		}
	}

	private void fillInVmPublicImage(List<VmInstanceTable> vmInstanceTables) {
		for (VmInstanceTable vmInstanceTable : vmInstanceTables) {
			List<VmImageTable> images = __vmPublicImageDAO
					.findByProperty("uuid", vmInstanceTable.getImageUuid());
			if (images.size() == 1) {
				vmInstanceTable.setVmImage(images.get(0));
			} else if (images.size() < 1) {
				// no hosts
			} else {
				// repeated uuid
			}
		}
	}

	private void fillInVmInstanceType(List<VmInstanceTable> vmInstanceTables) {
		for (VmInstanceTable vmInstanceTable : vmInstanceTables) {
			List<VmInstanceTypeTable> instanceTypes = __vmInstanceTypeDao
					.findByProperty("id", vmInstanceTable.getInstanceTypeId());
			if (instanceTypes.size() == 1) {
				vmInstanceTable.setVmInstanceType(instanceTypes.get(0));
			} else if (instanceTypes.size() < 1) {
				// no hosts
			} else {
				// repeated uuid
			}
		}
	}

	private void fillInVmInstanceMetadata(List<VmInstanceTable> vmInstanceTables) {
		for (VmInstanceTable vmInstanceTable : vmInstanceTables) {
			List<VmInstanceMetadataTable> vmInstanceMetadatas = __vmInstanceMetadataDao.findByProperty("vmInstanceId", vmInstanceTable.getId());
			
			List<VmInstanceMetadata> tmp = new LinkedList<VmInstanceMetadata>();
			for (VmInstanceMetadataTable vmInstanceMetadata : vmInstanceMetadatas){
				tmp.add(vmInstanceMetadata);
			}
			vmInstanceTable.setVmInstanceMetadata(tmp);
		}
	}

	private void fillInVmVolume(List<VmInstanceTable> vmInstanceTables) {
		for (VmInstanceTable vmInstanceTable : vmInstanceTables) {
			List<VmVolumeTable> vmVolumes = __vmVolumeDAO.findByProperty("instanceUuid", vmInstanceTable.getImageUuid());
			
			List<VmVolume> tmps = new LinkedList<VmVolume>();
			for (VmVolumeTable vmVolume : vmVolumes){
				tmps.add(vmVolume);
			}
			vmInstanceTable.setVmVolumes(tmps);
		}
	}
	
	private void fillInVmNetWork(List<VmInstanceTable> vmInstanceTables) {
		for (VmInstanceTable vmInstanceTable : vmInstanceTables) {
			List<VmVirtualInterfaceTable> vmVirtualInterfaces = __vmVIFDAO.findByProperty("instanceUuid", vmInstanceTable.getUuid());
			
			List<VmVirtualInterface> tmps = new LinkedList<VmVirtualInterface>();
			for (VmVirtualInterfaceTable vmVolume : vmVirtualInterfaces){
				tmps.add(vmVolume);
			}
			vmInstanceTable.setVmVirtualInterfaces(tmps);
		}

	}

	private void fillInVmSecurityGroup(List<VmInstanceTable> vmInstanceTables) {
		for (VmInstanceTable vmInstancetable : vmInstanceTables) {
			vmInstancetable.setVmSecurityGroup(__vmSecurityGroupDAO.findById(vmInstancetable.getSecurityGroupId()));
		}
	}
}
