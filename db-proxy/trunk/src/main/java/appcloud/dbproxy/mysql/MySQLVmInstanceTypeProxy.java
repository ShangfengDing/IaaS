/**
 * File: MySQLVmInstanceTypeProxy.java
 * Author: weed
 * Create Time: 2013-4-15
 */
package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmInstanceType;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmInstanceTypeDao;
import appcloud.dbproxy.mysql.model.VmInstanceTypeTable;

/**
 * @author weed
 *
 */
public class MySQLVmInstanceTypeProxy implements VmInstanceTypeProxy {

	private static VmInstanceTypeDao __vmInstanceTypeDao = new VmInstanceTypeDao();
	
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#findAll()
	 */
	@Override
	public List<? extends VmInstanceType> findAll() throws Exception {
		return __vmInstanceTypeDao.findAll();
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#findAll(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends VmInstanceType> findAll(Integer page, Integer size)
			throws Exception {
		
		return __vmInstanceTypeDao.findAll(page, size);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#countAll()
	 */
	@Override
	public long countAll() throws Exception {
		return __vmInstanceTypeDao.countAll();
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#searchAll(appcloud.common.util.query.QueryObject)
	 */
	@Override
	public List<? extends VmInstanceType> searchAll(
			QueryObject<VmInstanceType> queryObject) throws Exception {
		
		return __vmInstanceTypeDao.findByProperties(queryObject, 0, 0);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#searchAll(appcloud.common.util.query.QueryObject, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends VmInstanceType> searchAll(
			QueryObject<VmInstanceType> queryObject, Integer page, Integer size)
			throws Exception {
		
		return __vmInstanceTypeDao.findByProperties(queryObject, page, size);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#countSearch(appcloud.common.util.query.QueryObject)
	 */
	@Override
	public long countSearch(QueryObject<VmInstanceType> queryObject)
			throws Exception {
		
		return __vmInstanceTypeDao.countByProperties(queryObject);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#getByUuid(java.lang.String)
	 */
	@Override
	public VmInstanceType getByUuid(String flavorUuid) throws Exception {
		List<VmInstanceTypeTable> vmInstanceTypes = __vmInstanceTypeDao.findByProperty("flavorUuid", flavorUuid);
		if (vmInstanceTypes.size() < 1){
			return null;
		}
		else{
			return vmInstanceTypes.get(0);
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#getById(java.lang.Integer)
	 */
	@Override
	public VmInstanceType getById(Integer instanceTypeId) throws Exception {
		return __vmInstanceTypeDao.findById(instanceTypeId);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#getByName(java.lang.String)
	 */
	@Override
	public VmInstanceType getByName(String name) throws Exception {
		List<VmInstanceTypeTable> vmInstanceTypes =  __vmInstanceTypeDao.findByProperty("name", name);
		if (vmInstanceTypes.size() < 1){
			return null;
		}
		else{
			return vmInstanceTypes.get(0);
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#save(appcloud.common.model.VmInstanceType)
	 */
	@Override
	public void save(VmInstanceType instanceType) throws Exception {
		__vmInstanceTypeDao.save(new VmInstanceTypeTable(instanceType));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#update(appcloud.common.model.VmInstanceType)
	 */
	@Override
	public void update(VmInstanceType instanceType) throws Exception {
		__vmInstanceTypeDao.update(new VmInstanceTypeTable(instanceType));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#deleteById(java.lang.Integer)
	 */
	@Override
	public void deleteById(Integer instanceTypeId) throws Exception {
		__vmInstanceTypeDao.deleteByPrimaryKey(instanceTypeId);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.VmInstanceTypeProxy#deleteByUuid(java.lang.String)
	 */
	@Override
	public void deleteByUuid(String uuid) throws Exception {
		__vmInstanceTypeDao.deleteByProperty("uuid", uuid);
	}

}
