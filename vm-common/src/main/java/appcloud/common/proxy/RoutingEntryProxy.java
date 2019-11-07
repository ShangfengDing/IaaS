/**
 * File: RoutingEntryProxy.java
 * Author: weed
 * Create Time: 2012-11-9
 */
package appcloud.common.proxy;

import java.util.List;
import java.util.Set;

import appcloud.common.model.RoutingEntry;
import appcloud.common.util.query.QueryObject;

/**
 * @author weed
 *
 */
public interface RoutingEntryProxy {
	public List<? extends RoutingEntry> search(QueryObject<RoutingEntry> queryObject, int page, int size) throws Exception;
	
	/**
	 * Get all RoutingEntries
	 * 
	 * @return	A list of all RoutingEntry instances, which type is RoutingEntry or RoutingEntry's subclass
	 */
	public List<? extends RoutingEntry> findAll() throws Exception;
	
	/**
	 * Save a new RoutingEntry
	 * 
	 * @param 	routingEntry	a new RoutingEntry, its "id" would be generated automatically
	 * @return	true if save a RoutingEntry successfully, or false if not
	 */
	public void save(RoutingEntry routingEntry) throws Exception;
	
	/**
	 * Delete a existed RoutingEntry
	 * 
	 * @param 	id	id of a existed RoutingEntry
	 * @return	true if delete a RoutingEntry successfully, or false if not
	 */
	public void deleteById(Integer id) throws Exception;
	
	/**
	 * Get all RoutingEntries of the specific source domain
	 * 
	 * @param 	srcPrefix	prefix of the source domain
	 * @param 	srcSuffix	suffix of the source domain
	 * @return	A list of RoutingEntry instances, which source domain matches with arguments,
	 * 			which type is RoutingEntry or RoutingEntry's subclass
	 */
	public List<? extends RoutingEntry> getBySrc(String srcPrefix, String srcSuffix) throws Exception;
	
	/**
	 * Disable all RoutingEntries of the specific source domain
	 * 
	 * @param 	srcPrefix	prefix of the source domain
	 * @param 	srcSuffix	suffix of the source domain
	 * @return	true if disable a RoutingEntry successfully, or false if not
	 */
	public boolean disableBySrc(String srcPrefix, String srcSuffix) throws Exception;
	
	/**
	 * Delete all RoutingEntries of the specific source domain
	 * 
	 * @param 	srcPrefix	prefix of the source domain
	 * @param 	srcSuffix	suffix of the source domain
	 * @return	true if delete a RoutingEntry successfully, or false if not
	 */
	public void deleteBySrc(String srcPrefix, String srcSuffix) throws Exception;
	
	/**
	 * Delete all RoutingEntries of the specific designate domain
	 * 
	 * @param 	destPrefix	prefix of the designate domain
	 * @param 	destSuffix	prefix of the designate domain
	 * @return	list of source suffixes
	 */
	public Set<String> deleteByDest(String destPrefix, String destSuffix) throws Exception;
	
	/**
	 * Get all RoutingEntries of the specific source domain suffixes
	 * 
	 * @param 	srcSuffixs	a list of source domain suffixes
	 * @return	A list of RoutingEntry instances, which source domain suffix matches with arguments,
	 * 			which type is RoutingEntry or RoutingEntry's subclass
	 */
	public List<? extends RoutingEntry> getBySrcSuffix(List<String> srcSuffixs) throws Exception;
	
	/**
	 * Enable a existed RoutingEntry
	 * 
	 * @param 	routingEntry	a existed RoutingEntry, its "id" can not be null, 
	 * 							other members would be ignored,
	 * 							"isValid" would be set to true by default
	 * @return	new RoutingEntry if enable a RoutingEntry successfully, or null if not
	 */
	public RoutingEntry enable(RoutingEntry routingEntry) throws Exception;
}
