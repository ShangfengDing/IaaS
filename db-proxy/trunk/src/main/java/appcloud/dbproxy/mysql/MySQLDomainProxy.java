/**
 * File: MYSQLDomainProxy.java
 * Author: weed
 * Create Time: 2012-11-22
 */
package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.Domain;
import appcloud.common.proxy.DomainProxy;
import appcloud.dbproxy.mysql.dao.DomainDAO;
import appcloud.dbproxy.mysql.model.DomainTable;

/**
 * @author weed
 *
 */
public class MySQLDomainProxy implements DomainProxy {
	static private DomainDAO dao= new DomainDAO();

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.DomainProxy#getAllModels()
	 */
	@Override
	public List<? extends Domain> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public long countAll() {
		try {
			return dao.countAll();
		} catch (Exception e) {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.DomainProxy#getPagedModels(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends Domain> findAll(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return dao.findAll(page, size);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.DomainProxy#save(appcloud.common.model.Domain)
	 */
	@Override
	public void save(Domain domain) {
		// TODO Auto-generated method stub
		List<DomainTable> gotDomainTables = dao.findByProperty2("prefix", domain.getPrefix(), "suffix", domain.getSuffix());
		if (!gotDomainTables.isEmpty()){
			return;
		}
		dao.save(new DomainTable(domain));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.DomainProxy#delete(appcloud.common.model.Domain)
	 */
	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		dao.deleteByPrimaryKey(id);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.DomainProxy#getByFullName(java.lang.String, java.lang.String)
	 */
	@Override
	public Domain getByFullName(String prefix, String suffix) {
		// TODO Auto-generated method stub
		List<DomainTable> gotDomainTables = dao.findByProperty2("prefix", prefix, "suffix", suffix);
		if (gotDomainTables.isEmpty()){
			return null;
		}
		else{
			return gotDomainTables.get(0);
		}
	}

	@Override
	public List<? extends Domain> getBySuffix(String suffix) {
		return dao.findByProperty("suffix", suffix);
	}
	
	/* (non-Javadoc)
	 * @see appcloud.common.proxy.DomainProxy#deleteByFullName(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteByFullName(String prefix, String suffix) {
		// TODO Auto-generated method stub
		List<DomainTable> gotDomainTables = dao.findByProperty2("prefix", prefix, "suffix", suffix);
		if (gotDomainTables.isEmpty()){
			return;
		}
		dao.deleteByPrimaryKey(gotDomainTables.get(0).getId());
	}
}
