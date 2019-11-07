/**
 * File: DomainProxy.java
 * Author: weed
 * Create Time: 2012-11-9
 */
package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.Domain;

/**
 * @author weed
 *
 */
public interface DomainProxy {
	/**
	 * Get all Domains
	 * 
	 * @return	A list of all Domain instances, which type is Domain or Domain's subclass
	 */
	public List<? extends Domain> findAll() throws Exception;

	/**
	 * Get all Domains with specific page and size
	 * 
	 * @param 	page	page number
	 * @param 	size	size of list
	 * @return	A list of all Domain instances, which type is Domain or Domain's subclass
	 */
	public List<? extends Domain> findAll(Integer page, Integer size) throws Exception;

	/**
	 * Get the num of domains
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * Save a new Domain
	 * 
	 * @param 	domain	a new Domain
	 * @return
	 */
	public void save(Domain domain) throws Exception;
	
	/**
	 * Delete a existed Domain
	 * 
	 * @param 	id	id of a existed Domain
	 * @return
	 */
	public void deleteById(Integer id) throws Exception;
	
	/**
	 * Get the domain with specific prefix and suffix
	 * 
	 * @param 	domainPrefix	prefix of domain
	 * @param 	domainSuffix	suffix of domain
	 * @return	a existed Domain which matches the arguments 
	 */
	public Domain getByFullName(String prefix, String suffix) throws Exception;
	
	/**
	 * Get the domain with specific suffix
	 * 
	 * @param 	suffix	suffix of domain
	 * @return	list of Domains which matches the arguments 
	 */
	public List<? extends Domain> getBySuffix(String suffix) throws Exception;
	
	/**
	 * Delete a domain with specific prefix and suffix
	 * 
	 * @param 	domainPrefix	prefix of domain
	 * @param 	domainSuffix	suffix of domain
	 * @return
	 */
	public void deleteByFullName(String prefix, String suffix) throws Exception;
	

}
