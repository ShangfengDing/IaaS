/**
 * File: MYSQLNginxProxy.java
 * Author: weed
 * Create Time: 2012-11-24
 */
package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import appcloud.common.model.DomainSuffix;
import appcloud.common.model.Nginx;
import appcloud.common.model.NginxToSuffix;
import appcloud.common.proxy.NginxProxy;
import appcloud.dbproxy.mysql.dao.DomainSuffixDAO;
import appcloud.dbproxy.mysql.dao.NginxDAO;
import appcloud.dbproxy.mysql.dao.NginxDomainSuffixDAO;
import appcloud.dbproxy.mysql.model.DomainSuffixTable;
import appcloud.dbproxy.mysql.model.NginxToSuffixTable;
import appcloud.dbproxy.mysql.model.NginxTable;

/**
 * @author weed
 *
 */
public class MySQLNginxProxy implements NginxProxy {
	
	private static NginxDAO nginxDao = new NginxDAO();
	private static NginxDomainSuffixDAO nginxDSDao = new NginxDomainSuffixDAO();
	private static DomainSuffixDAO dsDao = new DomainSuffixDAO();
	

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.NginxProxy#getAllModels(boolean)
	 */
	@Override
	public List<? extends Nginx> findAll(boolean withDomainSuffixs) {
		// TODO Auto-generated method stub
		List<NginxTable> nginxTables = nginxDao.findAll();
		if (withDomainSuffixs){
			for (NginxTable nginxTable : nginxTables){
				nginxTable.setDomainSuffixs(_getDomainSuffixs(nginxTable.getId()));
			}
		}
		
		return nginxTables;
	}

	@Override
	public long countAll() {
		try {
			return nginxDao.countAll();
		} catch (Exception e) {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.NginxProxy#getPagedModels(boolean, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends Nginx> findAll(boolean withDomainSuffixs,
			Integer page, Integer size) {
		// TODO Auto-generated method stub
		List<NginxTable> nginxTables = nginxDao.findAll(page, size);
		if (withDomainSuffixs){
			for (NginxTable nginxTable : nginxTables){
				nginxTable.setDomainSuffixs(_getDomainSuffixs(nginxTable.getId()));
			}
		}
		
		return nginxTables;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.NginxProxy#getById(boolean, java.lang.Integer)
	 */
	@Override
	public Nginx getById(boolean withDomainSuffixs, Integer nginxId) {
		// TODO Auto-generated method stub
		NginxTable nginxTable = nginxDao.findById(nginxId);
		if (nginxTable != null && withDomainSuffixs){
			nginxTable.setDomainSuffixs(_getDomainSuffixs(nginxTable.getId()));
		}
		return nginxTable;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.NginxProxy#save(appcloud.common.model.Nginx)
	 */
	@Override
	public void save(Nginx nginx) {
		// TODO Auto-generated method stub
		List<NginxTable> existNginxs = nginxDao.findByProperty("innerIp", nginx.getInnerIp());
		if (!existNginxs.isEmpty()){
			return;
		}
		nginxDao.save(new NginxTable(nginx));
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.NginxProxy#update(appcloud.common.model.Nginx)
	 */
	@Override
	public void update(Nginx nginx) {
		// TODO Auto-generated method stub
		nginxDao.update(new NginxTable(nginx));
		_updateDomainSuffixs(nginx);
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.NginxProxy#deleteById(java.lang.Integer)
	 */
	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		nginxDao.deleteByPrimaryKey(id);
		_deleteDomainSuffixs(id);		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.NginxProxy#getByDomainSuffix(java.lang.String)
	 */
	@Override
	public List<? extends Nginx> getByDomainSuffix(String domainSuffix) {
		// TODO Auto-generated method stub
		List<NginxTable> nginxs = new ArrayList<NginxTable>();
		
		List<DomainSuffixTable> dsTables = dsDao.findByProperty("name", domainSuffix);
		if (dsTables.isEmpty()){
			return nginxs;
		}
		
		List<NginxToSuffixTable> nginxDSTables = nginxDSDao.findByProperty("domainSuffixId", dsTables.get(0).getId());
		if (nginxDSTables.isEmpty()){
			return nginxs;
		}
		
		for (NginxToSuffixTable nginxDSTable : nginxDSTables){
			nginxs.add(nginxDao.findById(nginxDSTable.getNginxId()));
		}
		
		return nginxs;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.NginxProxy#getDomainSuffixs(java.lang.String)
	 */
	@Override
	public List<? extends DomainSuffix> getDomainSuffixs(String innerIp) {
		// TODO Auto-generated method stub
		List<DomainSuffixTable> dsTables = new ArrayList<DomainSuffixTable>();
		
		List<NginxTable> nginxTables = nginxDao.findByProperty("innerIp", innerIp);
		if (nginxTables.isEmpty()){
			return dsTables;
		}
		
		return _getDomainSuffixs(nginxTables.get(0).getId());
	}

	protected List<DomainSuffix> _getDomainSuffixs(Integer id){
		List<DomainSuffix> dsTables = new ArrayList<DomainSuffix>();
		
		List<NginxToSuffixTable> nginxDSTables = nginxDSDao.findByProperty("nginxId", id);
		for (NginxToSuffixTable nginxDSTable : nginxDSTables){
			dsTables.add(dsDao.findById(nginxDSTable.getDomainSuffixId()));
		}
		
		return dsTables;
	}
	
	protected void _updateDomainSuffixs(Nginx nginx){
		Set<Integer> oldDomainIds = new HashSet<Integer>();
		List<NginxToSuffixTable> nginxDSTables = nginxDSDao.findByProperty("nginxId", nginx.getId());
		for (NginxToSuffixTable nginxDSTable : nginxDSTables){
			oldDomainIds.add(nginxDSTable.getDomainSuffixId());
		}
		
		List<DomainSuffix> domainSuffixs = nginx.getDomainSuffixs();
		Set<Integer> newDomainIds = new HashSet<Integer>();
		for (DomainSuffix domainSuffix : domainSuffixs){
			newDomainIds.add(domainSuffix.getId());
		}
		
		Set<Integer> commonDomainIds = new HashSet<Integer>(newDomainIds);
		commonDomainIds.retainAll(oldDomainIds);
		
		oldDomainIds.removeAll(commonDomainIds);
		for (Integer domainId : oldDomainIds){
			for (NginxToSuffixTable nginxDSTable : nginxDSTables){
				if (nginxDSTable.getDomainSuffixId().equals(domainId)){
					nginxDSDao.deleteByPrimaryKey(nginxDSTable.getId());
				}
			}
		}
		
		newDomainIds.removeAll(commonDomainIds);
		for (Integer domainId : newDomainIds){
			nginxDSDao.save(new NginxToSuffixTable(new NginxToSuffix(null, nginx.getId(), domainId)));
		}
	}
	
	protected void _deleteDomainSuffixs(Integer id){
		List<NginxToSuffixTable> nginxDSTables = nginxDSDao.findByProperty("nginxId", id);
		for (NginxToSuffixTable nginxDSTable : nginxDSTables){
			nginxDSDao.deleteByPrimaryKey(nginxDSTable.getId());
		}
	}
}
