/**
 * File: tmp.java
 * Author: weed
 * Create Time: 2012-11-9
 */
package appcloud.approuter.amqp.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import appcloud.common.errorcode.AREC;
import appcloud.common.model.Domain.DomainTypeEnum;
import appcloud.common.model.DomainSuffix;
import appcloud.common.model.Nginx;
import appcloud.common.model.RoutingEntry;
import appcloud.common.model.Domain;
import appcloud.common.proxy.DomainProxy;
import appcloud.common.proxy.NginxProxy;
import appcloud.common.proxy.RoutingEntryProxy;
import appcloud.common.service.AppRouterService;
import appcloud.common.service.NginxControllerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.rpc.tools.RpcException;


public class AppRouterServiceImpl implements AppRouterService {

	private static Logger logger = Logger.getLogger(AppRouterServiceImpl.class);
	
	public DomainProxy domainProxy = ConnectionFactory.getDomainProxy();
	public RoutingEntryProxy routingEntryProxy = ConnectionFactory.getRoutingEntryProxy();
	public NginxProxy nginxProxy = ConnectionFactory.getNginxProxy();
	
	public NginxControllerService nginxController = ConnectionFactory.getNginxController();
	
	/* (non-Javadoc)
	 * @see appcloud.routernginx.amqp.service.AppRouterService#addRoutingEntry(appcloud.common.model.RoutingEntry)
	 */
	public AREC addRoutingEntry(RoutingEntry routingEntry) {
		// TODO Auto-generated method stub
		logger.info("addRoutingEntry");
		logger.debug(routingEntry);

		if (!__getSrcDomain(routingEntry.getSrcPrefix(), routingEntry.getSrcSuffix())){
			return AREC.SRC_NOT_EXIST;
		}
		
		AREC ret = __checkRoutingEntry(routingEntry);
		if (0 != ret.compareTo(AREC.NO_ERROR)){
			return ret;
		}
		
		try {
			routingEntryProxy.save(routingEntry);
			__updateRoutingTable(routingEntry.getSrcSuffix());
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.RE_EXISTED;
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.routernginx.amqp.service.AppRouterService#addRoutingEntries(java.util.List)
	 */
	public AREC addRoutingEntries(List<RoutingEntry> routingEntries) {
		// TODO Auto-generated method stub
		logger.info("addRoutingEntries");
		logger.debug(routingEntries);
		
		if (!__hasSameSrcDomain(routingEntries)){
			return AREC.MULTI_SRC;
		}
		
		if (!__noDupedDstDomain(routingEntries)){
			return AREC.DUPED_DST;
		}
		
		RoutingEntry routingEntry0 = routingEntries.get(0);
		if (!__getSrcDomain(routingEntry0.getSrcPrefix(), routingEntry0.getSrcSuffix())){
			return AREC.SRC_NOT_EXIST;
		}
		
		for (RoutingEntry routingEntry: routingEntries){
			AREC ret = __checkRoutingEntry(routingEntry);
			if (0 != ret.compareTo(AREC.NO_ERROR)){
				return ret;
			}
		}
		
		boolean allSaved = true;
		Set<String> domainSuffixs = new HashSet<String>();
		for (RoutingEntry routingEntry: routingEntries){
			try {
				routingEntryProxy.save(routingEntry);
				domainSuffixs.add(routingEntry.getSrcSuffix());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				allSaved = false;
			}
		}

		if (allSaved){
			__updateRoutingTable(domainSuffixs);
			return AREC.NO_ERROR;
		}
		else{
			return AREC.RE_EXISTED;
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.routernginx.amqp.service.AppRouterService#updateRoutingEntries(java.lang.String, java.lang.String, java.util.List)
	 */
	public AREC updateRoutingEntries(List<RoutingEntry> routingEntries) {
		// TODO Auto-generated method stub
		logger.info("updateRoutingEntries");
		logger.debug(routingEntries);
		
		if (!__hasSameSrcDomain(routingEntries)){
			return AREC.MULTI_SRC;
		}
		
		if (!__noDupedDstDomain(routingEntries)){
			return AREC.DUPED_DST;
		}
		
		RoutingEntry routingEntry0 = routingEntries.get(0);
		if (!__getSrcDomain(routingEntry0.getSrcPrefix(), routingEntry0.getSrcSuffix())){
			return AREC.SRC_NOT_EXIST;
		}
		
		try {
			routingEntryProxy.deleteBySrc(routingEntry0.getSrcPrefix(), routingEntry0.getSrcSuffix());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		return addRoutingEntries(routingEntries);
	}

	/* (non-Javadoc)
	 * @see appcloud.routernginx.amqp.service.AppRouterService#deleteRoutingEntry(appcloud.common.model.RoutingEntry)
	 */
	public AREC deleteRoutingEntry(RoutingEntry routingEntry) {
		// TODO Auto-generated method stub
		logger.info("deleteRoutingEntry");
		logger.debug(routingEntry);
		
		try {
			routingEntryProxy.deleteById(routingEntry.getId());
			__updateRoutingTable(routingEntry.getSrcSuffix());
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.RE_NOT_EXIST;
		}
	}
	
	/* (non-Javadoc)
	 * @see appcloud.routernginx.amqp.service.AppRouterService#deleteRoutingEntries(java.util.List)
	 */
	public AREC deleteRoutingEntries(List<RoutingEntry> routingEntries) {
		// TODO Auto-generated method stub
		logger.info("deleteRoutingEntries");
		logger.debug(routingEntries);
		
		if (!__hasSameSrcDomain(routingEntries)){
			return AREC.MULTI_SRC;
		}
		
		if (!__noDupedDstDomain(routingEntries)){
			return AREC.DUPED_DST;
		}
		
		RoutingEntry routingEntry0 = routingEntries.get(0);
		if (!__getSrcDomain(routingEntry0.getSrcPrefix(), routingEntry0.getSrcSuffix())){
			return AREC.SRC_NOT_EXIST;
		}
		
		Set<String> domainSuffixs = new HashSet<String>();
		for (RoutingEntry routingEntry : routingEntries){
			try {
				routingEntryProxy.deleteById(routingEntry.getId());
				domainSuffixs.add(routingEntry.getSrcSuffix());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
		}
		
		__updateRoutingTable(domainSuffixs);
		
		return AREC.NO_ERROR;
	}

	/* (non-Javadoc)
	 * @see appcloud.routernginx.amqp.service.AppRouterService#enableRoutingEntry(appcloud.common.model.RoutingEntry)
	 */
	public AREC enableRoutingEntry(RoutingEntry routingEntry) {
		// TODO Auto-generated method stub
		if (routingEntry.getIsValid() != 1){
			return AREC.RE_NOT_EXIST;
		}
		
		try {
			if (null != routingEntryProxy.enable(routingEntry)){	
				__updateRoutingTable(routingEntry.getSrcSuffix());
				return AREC.NO_ERROR;
			}
			else{
				return AREC.RE_NOT_EXIST;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.RE_NOT_EXIST;
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.routernginx.amqp.service.AppRouterService#disableAppRoutingEntries(java.lang.String, java.lang.String)
	 */
	public AREC disableAppRoutingEntries(String srcPrefix, String srcSuffix) {
		// TODO Auto-generated method stub
		try {
			routingEntryProxy.disableBySrc(srcPrefix, srcSuffix);
			__updateRoutingTable(srcSuffix);
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.SRC_NOT_EXIST;
		}
	}
	
	/* (non-Javadoc)
	 * @see appcloud.routernginx.amqp.service.AppRouterService#deleteAppRoutingEntries(java.lang.String, java.lang.String)
	 */
	public AREC deleteAppRoutingEntries(String srcPrefix, String srcSuffix) {
		// TODO Auto-generated method stub
		logger.info("deleteAppRoutingEntries");
		logger.debug(srcPrefix + "." + srcSuffix);

		try {
			routingEntryProxy.deleteBySrc(srcPrefix, srcSuffix);
			__updateRoutingTable(srcSuffix);
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.SRC_NOT_EXIST;
		}
	}
	
	/* (non-Javadoc)
	 * @see appcloud.common.service.AppRouterService#deleteDestRoutingEntries(java.lang.String, java.lang.String)
	 */
	@Override
	public AREC deleteDestRoutingEntries(String destPrefix, String destSuffix) {
		// TODO Auto-generated method stub
		logger.info("deleteDestRoutingEntries");
		logger.debug(destPrefix + "." + destSuffix);

		Set<String> srcSuffix = null;
		try {
			srcSuffix = routingEntryProxy.deleteByDest(destPrefix, destSuffix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		if (!srcSuffix.isEmpty()){
			__updateRoutingTable(srcSuffix);
			return AREC.NO_ERROR;
		}
		else{
			return AREC.DST_NOT_EXIST;
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.AppRouterService#applyDomain(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	public AREC applyDomain(Domain domain) {
		// TODO Auto-generated method stub
		logger.info("applyDomain");
		logger.debug("Developer: " + domain.getDevId() + " apply " + domain.getPrefix() + "." + domain.getSuffix());
		
		try {
			domainProxy.save(domain);
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.DOMAIN_EXISTED;
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.AppRouterService#removeDomain(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	public AREC removeDomain(Domain domain) {
		// TODO Auto-generated method stub
		logger.info("removeDomain");
		logger.debug(domain);
		
		try {
			domainProxy.deleteById(domain.getId());
			routingEntryProxy.deleteBySrc(domain.getPrefix(), domain.getSuffix());
			routingEntryProxy.deleteByDest(domain.getPrefix(), domain.getSuffix());
			__updateRoutingTable(domain.getSuffix());
			
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.DOMAIN_NOT_EXIST;
		}
	}
	
	/* (non-Javadoc)
	 * @see appcloud.common.service.AppRouterService#removeDomain(java.lang.String, java.lang.String)
	 */
	@Override
	public AREC removeDomain(String domainPrefix, String domainSuffix) {
		// TODO Auto-generated method stub
		logger.info("removeDomain");
		logger.debug(domainPrefix + "." + domainSuffix);
		
		try {
			domainProxy.deleteByFullName(domainPrefix, domainSuffix);
			routingEntryProxy.deleteBySrc(domainPrefix, domainSuffix);
			routingEntryProxy.deleteByDest(domainPrefix, domainSuffix);
			__updateRoutingTable(domainSuffix);
			
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.DOMAIN_NOT_EXIST;
		}
	}
	
	/* (non-Javadoc)
	 * @see appcloud.common.service.AppRouterService#updateNginx(appcloud.common.model.Nginx)
	 */
	@Override
	public AREC updateNginx(Nginx nginx) {
		// TODO Auto-generated method stub
		Nginx oldNginx = null;
		try {
			oldNginx = nginxProxy.getById(true, nginx.getId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage(), e1);
		}
		if (null == oldNginx){
			return AREC.NGINX_NOT_EXIST;
		}
		
		try {
			nginxProxy.update(nginx);
			__noticeNginxController(nginx);
			// id
//			List<DomainSuffix> oldDSs = oldNginx.getDomainSuffixs();
//			List<DomainSuffix> newDSs = nginx.getDomainSuffixs();
//			if (){
//				
//			}
//			for (DomainSuffix newDS : newDSs){
//				if (!oldDSs.contains(newDS)){
//
//				}
//			}
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.NGINX_NOT_EXIST;
		}	
	}
	
	/* (non-Javadoc)
	 * @see appcloud.common.service.AppRouterService#deleteNginx(java.lang.Integer)
	 */
	@Override
	public AREC deleteNginx(Integer nginxId) throws RpcException {
		// TODO Auto-generated method stub
		try {
			nginxProxy.deleteById(nginxId);
			return AREC.NO_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return AREC.NGINX_NOT_EXIST;
		}	
	}
	
	private boolean __hasSameSrcDomain(List<RoutingEntry> routingEntries){
		Set<String> noDupSrcs = new HashSet<String>();
		for (RoutingEntry routingEntry : routingEntries){
			noDupSrcs.add(routingEntry.getSrcPrefix() + routingEntry.getSrcSuffix());
		}
		
		return 1 == noDupSrcs.size();
	}
	
	private boolean __noDupedDstDomain(List<RoutingEntry> routingEntries){
		Set<String> noDupSrcs = new HashSet<String>();
		for (RoutingEntry routingEntry : routingEntries){
			String dstDomain = routingEntry.getDestPrefix() + routingEntry.getDestSuffix();
			if (noDupSrcs.contains(dstDomain)){
				return false;
			}
			else{
				noDupSrcs.add(dstDomain);
			}
		}
		
		return true;
	}
	
	private boolean __getSrcDomain(String prefix, String suffix){
		try {
			return null != domainProxy.getByFullName(prefix, suffix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	private AREC __checkRoutingEntry(RoutingEntry routingEntry){
		if (routingEntry.getType().equals(RoutingEntry.RETypeEnum.IP)){	
			// need more
		}
		else if (routingEntry.getType().equals(RoutingEntry.RETypeEnum.DOMAIN)){
			if (!routingEntry.getSrcSuffix().equalsIgnoreCase(routingEntry.getDestSuffix())){
				return AREC.SUFFIX_CROSSED;
			}
			else {
				Domain destDomain = null;
				try {
					destDomain = domainProxy.getByFullName(routingEntry.getDestPrefix(), routingEntry.getDestSuffix());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (destDomain == null){
					return AREC.DST_NOT_EXIST;
				}
				else {
					if (0 == destDomain.getType().compareTo(DomainTypeEnum.OUTSIDE)){
						return AREC.NONAPP_DST;
					}
				}
			}
		}
		else {
			return AREC.TYPE_ERROR;
		}

		return AREC.NO_ERROR;
	}
	
	private void __updateRoutingTable(String domainSuffix){
		logger.info("__updateRoutingTable");
		logger.debug(domainSuffix);
		@SuppressWarnings("unchecked")
		List<Nginx> nginxs = null;
		try {
			nginxs = (List<Nginx>) nginxProxy.getByDomainSuffix(domainSuffix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		__noticeNginxController(nginxs);
	}
	
	private void __updateRoutingTable(Set<String> domainSuffixs){
		logger.info("__updateRoutingTable");
		logger.debug(domainSuffixs);
		
		if (domainSuffixs.isEmpty()){
			return;
		}
		
		Set<Nginx> noDupNginx = new HashSet<Nginx>();

		for (String domainSuffix : domainSuffixs){
			@SuppressWarnings("unchecked")
			List<Nginx> nginxs = null;
			try {
				nginxs = (List<Nginx>) nginxProxy.getByDomainSuffix(domainSuffix);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
			for (Nginx nginx : nginxs){
				noDupNginx.add(nginx);
			}
		}
		System.out.println(noDupNginx);
		__noticeNginxController(noDupNginx);
	}
	
	private void __noticeNginxController(Collection<Nginx> nginxs){
		if (nginxs.isEmpty()){
			return;
		}
		
		for (Nginx nginx : nginxs){
			logger.debug(nginx);  
			__noticeNginxController(nginx);
		}
	}
	
	private void __noticeNginxController(Nginx nginx){
		String routingKey = RoutingKeyGenerator.getNginxControllerRoutingKey(nginx);
		try {
			nginxController.updateRoutingTable(routingKey);
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
	}
}
