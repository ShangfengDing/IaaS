package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.DomainSuffix;
import appcloud.common.model.Nginx;

/**
 * @author weed
 *
 */
public interface NginxProxy {

	/**
	 * Get all Nginxs
	 * 
	 * @param 	withDomainSuffixs	if get all models with their domain suffixes
	 * @return	A list of all Nginx instances, which type is Nginx or Nginx's subclass
	 */
	public List<? extends Nginx> findAll(boolean withDomainSuffixs) throws Exception;

	/**
	 * Get all Nginxs with specific page and size
	 * 
	 * @param 	withDomainSuffixs	if get all models with their domain suffixes
	 * @param 	page	page number
	 * @param 	size	size of list
	 * @return	A list of all Nginx instances, which type is Nginx or Nginx's subclass
	 */
	public List<? extends Nginx> findAll(boolean withDomainSuffixs, Integer page, Integer size) throws Exception;

	/**
	 * Get the num of Nginxs
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * Get a existed Nginx with specific nginxId
	 * 
	 * @param 	withDomainSuffixs	if get the Nginx with its domain suffixes
	 * @param 	nginxId		identification of Nginx
	 * @return	Nginx which matches the specific nginxId
	 */
	public Nginx getById(boolean withDomainSuffixs, Integer nginxId) throws Exception;
	
	/**
	 * Save a new Nginx
	 * 
	 * @param 	nginx	a new Nginx, its "id" would be ignored
	 * @return	true if save a Nginx successfully, or false if not
	 */
	public void save(Nginx nginx) throws Exception;
	
	/**
	 * Update a existed Nginx
	 * 
	 * @param 	nginx	a new Nginx, its "id" can not be null
	 * @return	true if update a Nginx successfully, or false if not
	 */
	public void update(Nginx nginx) throws Exception;
	
	/**
	 * Delete a existed Nginx
	 * 
	 * @param 	id	id of a existed Nginx
	 * @return	true if delete a Nginx successfully, or false if not
	 */
	public void deleteById(Integer id) throws Exception;
	
	/**
	 * Get all Nginxs with specific domain suffix
	 * 
	 * @param 	domainSuffix	suffix of domain
	 * @return	A list of all Nginx instances, which type is Nginx or Nginx's subclass
	 */
	public List<? extends Nginx> getByDomainSuffix(String domainSuffix) throws Exception;
	
	/**
	 * Get all DomainSuffixes with specific internal ip
	 * 
	 * @param 	innerIp		internal ip of Nginx
	 * @return	A list of all DomainSuffix instances, which type is DomainSuffix or DomainSuffix's subclass
	 */
	public List<? extends DomainSuffix> getDomainSuffixs(String innerIp) throws Exception;
	
//	public Boolean addNginxDomainSuffix(Integer id, DomainSuffix domainSuffix) throws Exception;
//	
//	public Boolean deleteNginxDomainSuffix(Integer id, DomainSuffix domainSuffix) throws Exception;
}
