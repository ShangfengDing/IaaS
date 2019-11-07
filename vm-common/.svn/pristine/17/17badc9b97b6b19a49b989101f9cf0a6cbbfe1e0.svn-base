package appcloud.common.service;

import java.util.List;

import appcloud.common.errorcode.AREC;
import appcloud.common.model.Domain;
import appcloud.common.model.Nginx;
import appcloud.common.model.RoutingEntry;
import appcloud.rpc.tools.RpcException;

public interface AppRouterService {

	/**
	 * Add a new RoutingEntry to Routing Table
	 * 
	 * @param 	routingEntry	a new RoutingEntry, its "id" would be ignored, 
	 * 							and its "type" shouldn't be null 
	 * @return 	NO_ERROR if add a RoutingEntry successfully, or others if not
	 */
	AREC addRoutingEntry(RoutingEntry routingEntry) throws RpcException;
	
	/**
	 * Add new RoutingEntries to Routing Table
	 * 
	 * @param 	routingEntries	new RoutingEntries, each RoutingEntry's "id" would be ignored, 
	 * 							and RoutingEntry's "type" shouldn't be null 
	 * @return 	NO_ERROR if add all RoutingEntries successfully, or others if not
	 */
	AREC addRoutingEntries(List<RoutingEntry> routingEntries) throws RpcException;
	
	/**
	 * Update all RoutingEntries of the source domain to "routingEntries" in parameters
	 * 
	 * @param 	srcPrefix	prefix of the source domain
	 * @param 	srcSuffix	suffix of the source domain
	 * @param 	routingEntries	new RoutingEntries for this source domain, 
	 * 							each RoutingEntry's "id" would be ignored, 
	 * 							and RoutingEntry's "type" shouldn't be null 
	 * @return	NO_ERROR if update all RoutingEntries successfully, or others if not
	 */
	AREC updateRoutingEntries(List<RoutingEntry> routingEntries) throws RpcException;
	
	/**
	 * Delete a existed RoutingEntry in Routing Table
	 * 
	 * @param 	routingEntry	a existed RoutingEntry to be deleted, its "isValid" would be ignored, 
	 * 							and its "type" shouldn't be null 
	 * @return	NO_ERROR if delete a RoutingEntry successfully, or others if not
	 */
	AREC deleteRoutingEntry(RoutingEntry routingEntry) throws RpcException;
	
	/**
	 * Delete existed RoutingEntries in Routing Table 
	 * 
	 * @param 	routingEntries	existed RoutingEntries to be deleted, 
	 * 							each RoutingEntry's "isValid" would be ignored, 
	 * 							and RoutingEntry's "type" shouldn't be null 
	 * @return	NO_ERROR if delete a RoutingEntry successfully, or others if not
	 */
	AREC deleteRoutingEntries(List<RoutingEntry> routingEntries) throws RpcException;
	
	/**
	 * Delete all RoutingEntries of the source domain
	 * 
	 * @param 	srcPrefix	prefix of the source domain
	 * @param 	srcSuffix	suffix of the source domain
	 * @return	NO_ERROR if delete any RoutingEntries successfully, or others if not
	 */
	AREC deleteAppRoutingEntries(String srcPrefix, String srcSuffix) throws RpcException;
	
	/**
	 * Disable all RoutingEntries of the source domain
	 * 
	 * @param 	srcPrefix	prefix of the source domain
	 * @param 	srcSuffix	suffix of the source domain
	 * @return	NO_ERROR if disable any RoutingEntries successfully, or others if not
	 */
	AREC disableAppRoutingEntries(String srcPrefix, String srcSuffix) throws RpcException;
	
	/**
	 * Delete all RoutingEntries of the destine domain
	 * 
	 * @param 	destPrefix	prefix of the destine domain
	 * @param 	destSuffix	suffix of the destine domain
	 * @return	NO_ERROR if delete any RoutingEntries successfully, or others if not
	 */
	AREC deleteDestRoutingEntries(String destPrefix, String destSuffix) throws RpcException;
	
	/**
	 * Enable a routingEntry which was disabled("isValid" is false)
	 * 
	 * @param 	routingEntry	a disabled routingEntry, its "isValid" would be ignored,
	 * 							"isValid" would be set to true by default
	 * @return	NO_ERROR if enable a RoutingEntry successfully, or others if not
	 */
	AREC enableRoutingEntry(RoutingEntry routingEntry) throws RpcException;
	
	/*-------------------------------------Domain service-------------------------------------*/
	/**
	 * Apply a Domain which is unused
	 * 
	 * @param 	domain	a new Domain
	 * @return	NO_ERROR if apply a Domain successfully, or others if not
	 */
	AREC applyDomain(Domain domain) throws RpcException;
	
	/**
	 * Delete a existed Domain
	 * 
	 * @param 	domain	a existed Domain to be deleted
	 * @return	NO_ERROR if remove a Domain successfully, or others if not
	 */
	AREC removeDomain(Domain domain) throws RpcException;
	
	/**
	 * Delete a existed Domain
	 * 
	 * @param 	domainPrefix	prefix of the domain
	 * @param 	domainSuffix	suffix of the domain
	 * @return	NO_ERROR if remove a Domain successfully, or others if not
	 */
	AREC removeDomain(String domainPrefix, String domainSuffix) throws RpcException;
	
	/*----------------------------------Nginx Management-----------------------------------*/
	/**
	 * Update a existed Nginx
	 * 
	 * @param 	nginx	nginx to be updated
	 * @return	NO_ERROR if update a Nginx successfully, or others if not
	 */
	AREC updateNginx(Nginx nginx) throws RpcException;
	
	/*----------------------------------Nginx Management-----------------------------------*/
	/**
	 * Update a existed Nginx
	 * 
	 * @param 	nginx	nginx to be updated
	 * @return	NO_ERROR if update a Nginx successfully, or others if not
	 */
	AREC deleteNginx(Integer nginxId) throws RpcException;
}
