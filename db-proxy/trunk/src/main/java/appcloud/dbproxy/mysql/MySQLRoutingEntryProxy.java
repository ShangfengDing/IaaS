/**
 * File: MYSQLRoutingEntryProxy.java
 * Author: weed
 * Create Time: 2012-11-22
 */
package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import appcloud.common.model.RoutingEntry;
import appcloud.common.model.RoutingEntry.RETypeEnum;
import appcloud.common.proxy.RoutingEntryProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.DomainDAO;
import appcloud.dbproxy.mysql.dao.RoutingEntryDAO;
import appcloud.dbproxy.mysql.model.DomainTable;
import appcloud.dbproxy.mysql.model.RoutingEntryTable;

/**
 * @author weed
 * 
 */
public class MySQLRoutingEntryProxy implements RoutingEntryProxy {
	static private RoutingEntryDAO __routingEntryDao = new RoutingEntryDAO();
	static private DomainDAO __domainDao = new DomainDAO();

	public List<? extends RoutingEntry> search(QueryObject<RoutingEntry> queryObject, int page, int size) {
		return __routingEntryDao.findByProperties(queryObject,0,0);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see appcloud.common.proxy.RoutingEntryProxy#getAllModels()
	 */
	@Override
	public List<? extends RoutingEntry> findAll() {
		// TODO Auto-generated method stub
		return __routingEntryDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see appcloud.common.proxy.RoutingEntryProxy#save(appcloud.common.model.
	 * RoutingEntry)
	 */
	@Override
	public void save(RoutingEntry routingEntry) {
		// TODO Auto-generated method stub
		if (!_check(routingEntry)){
			return;
		}

		__routingEntryDao.save(new RoutingEntryTable(routingEntry));
	}

//	/* (non-Javadoc)
//	 * @see appcloud.common.proxy.RoutingEntryProxy#saveInBatch(java.util.List)
//	 */
//	@Override
//	public boolean saveInBatch(List<RoutingEntry> routingEntries) {
//		// TODO Auto-generated method stub
//		boolean allSaved = true;
//		for (RoutingEntry routingEntry : routingEntries){
//			if (_check(routingEntry)){
//				try{
//					__routingEntryDao.save(new RoutingEntryTable(routingEntry));
//				}
//				catch (Exception e){
//					allSaved = false;
//				}
//			}
//			else{
//				allSaved = false;
//			}
//		}
//		
//		return allSaved;
//	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.RoutingEntryProxy#delete(appcloud.common.model.
	 * RoutingEntry)
	 */
	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		__routingEntryDao.deleteByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see appcloud.common.proxy.RoutingEntryProxy#getBySrc(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<? extends RoutingEntry> getBySrc(String srcPrefix,
			String srcSuffix) {
		// TODO Auto-generated method stub
		return __routingEntryDao.findByProperty2("srcPrefix", srcPrefix, "srcSuffix",
				srcSuffix);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.RoutingEntryProxy#disableBySrc(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean disableBySrc(String srcPrefix, String srcSuffix) {
		// TODO Auto-generated method stub
		List<RoutingEntryTable> routingEntryTables = __routingEntryDao.findByProperty2(
				"srcPrefix", srcPrefix, "srcSuffix", srcSuffix);
		if (routingEntryTables.isEmpty()){
			return false;
		}
		for (RoutingEntryTable routingEntryTable : routingEntryTables) {
			routingEntryTable.setIsValid(0);
		}
		
		try{
			__routingEntryDao.update(routingEntryTables);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.RoutingEntryProxy#deleteBySrc(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteBySrc(String srcPrefix, String srcSuffix) {
		// TODO Auto-generated method stub
		List<RoutingEntryTable> routingEntryTables = __routingEntryDao.findByProperty2(
				"srcPrefix", srcPrefix, "srcSuffix", srcSuffix);
		if (routingEntryTables.isEmpty()){
			return;
		}

		for (RoutingEntryTable routingEntryTable : routingEntryTables) {
			try{
				__routingEntryDao.deleteByPrimaryKey(routingEntryTable.getId());
			}
			catch(Exception e){
				continue;
			}
		}
		return;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.proxy.RoutingEntryProxy#deleteByDest(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<String> deleteByDest(String destPrefix, String destSuffix) {
		// TODO Auto-generated method stub
		Set<String> suffixs = new HashSet<String>();
		List<RoutingEntryTable> routingEntryTables = __routingEntryDao.findByProperty2(
				"destPrefix", destPrefix, "destSuffix", destSuffix);
		if (routingEntryTables.isEmpty()){
			return suffixs;
		}
		
		for (RoutingEntryTable routingEntryTable : routingEntryTables) {
			try{
				__routingEntryDao.deleteByPrimaryKey(routingEntryTable.getId());
				suffixs.add(routingEntryTable.getSrcSuffix());
			}
			catch(Exception e){
				continue;
			}
		}
		return suffixs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.RoutingEntryProxy#getBySrcSuffix(java.util.List)
	 */
	@Override
	public List<? extends RoutingEntry> getBySrcSuffix(List<String> srcSuffixs) {
		// TODO Auto-generated method stub
		List<RoutingEntryTable> routingEntryTables = new ArrayList<RoutingEntryTable>();
		
		for (String srcSuffix : srcSuffixs){
			routingEntryTables.addAll(__routingEntryDao.findByProperty("srcSuffix", srcSuffix));
		}
		
		return routingEntryTables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * appcloud.common.proxy.RoutingEntryProxy#enable(appcloud.common.model.
	 * RoutingEntry)
	 */
	@Override
	public RoutingEntry enable(RoutingEntry routingEntry) {
		// TODO Auto-generated method stub
		routingEntry.setIsValid(1);
		return __routingEntryDao.update(new RoutingEntryTable(routingEntry));
	}
	
	protected boolean _check(RoutingEntry routingEntry){
//		List<DomainTable> srcDomains = __domainDao.findByProperty2("prefix", routingEntry.getSrcPrefix(), "suffix", routingEntry.getSrcSuffix());
//		if (srcDomain.isEmpty()){
//			return true;
//		}
//
//		for (DomainTable ){
//			
//		}
		List<RoutingEntryTable> routingEntryTables = __routingEntryDao.findByProperty2(
				"srcPrefix", routingEntry.getSrcPrefix(), "srcSuffix", routingEntry.getSrcSuffix());
		
		for (RoutingEntryTable routingEntryTable : routingEntryTables){
			if (routingEntryTable.getDestPrefix().equalsIgnoreCase(routingEntry.getDestPrefix())
					&& routingEntryTable.getDestSuffix().equalsIgnoreCase(routingEntry.getDestSuffix())){
				return false;
			}
		}
		
		return true;
	}
}
