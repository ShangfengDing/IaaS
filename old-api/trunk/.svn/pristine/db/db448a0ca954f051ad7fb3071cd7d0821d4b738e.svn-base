package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.Enterprise;
import appcloud.api.beans.EnterpriseInvitation;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.manager.EnterpriseInvitationManage;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.model.VmEnterprise;
import appcloud.common.model.VmEnterpriseInvitation;
import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;
import appcloud.common.proxy.VmEnterpriseInvitationProxy;
import appcloud.common.proxy.VmEnterpriseProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;

public class RealEnterpriseInvitationManager implements EnterpriseInvitationManage {
	private static Logger logger = Logger.getLogger(RealAcGroupManager.class);
	private static LolLogUtil loler = LolHelper.getLolLogUtil(RealAcGroupManager.class);
	private BeansGenerator generator;
	private VmEnterpriseInvitationProxy enterpriseInvitationProxy;
	
	private static RealEnterpriseInvitationManager manager = new RealEnterpriseInvitationManager();
	public static RealEnterpriseInvitationManager getInstance() {
		return manager;
	}
	private RealEnterpriseInvitationManager() {
		super();
		generator = BeansGenerator.getInstance();
		enterpriseInvitationProxy = (VmEnterpriseInvitationProxy) ConnectionFactory.getTipProxy(
				VmEnterpriseInvitationProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	@Override
	public List<EnterpriseInvitation> getList(String adminId)throws Exception {
		logger.info(String.format("user %s request to GET ENTERPRISEINVITATIONS", adminId));
		List<VmEnterpriseInvitation> vmEnterpriseInvitations = (List<VmEnterpriseInvitation>) enterpriseInvitationProxy.findAll();
		if(vmEnterpriseInvitations == null) {
			return null;
		}
		List<EnterpriseInvitation> enterpriseInvitations = new ArrayList<EnterpriseInvitation>();
		for(VmEnterpriseInvitation vmEnterpriseInvitation : vmEnterpriseInvitations) {
			enterpriseInvitations.add(generator.vmEnterpriseInvitationToEnterpriseInvitation(vmEnterpriseInvitation));
		}
		return enterpriseInvitations;
	}

	@Override
	public EnterpriseInvitation get(String adminId,
			Integer enterpriseInvitationId) throws Exception {
		logger.info(String.format("user %s request to GET ENTERPRISEINVITATION %s", adminId, enterpriseInvitationId));
		VmEnterpriseInvitation vmEnterpriseInvitation = (VmEnterpriseInvitation) enterpriseInvitationProxy.getById(enterpriseInvitationId);
		if(vmEnterpriseInvitation == null) 
			throw new ItemNotFoundException("Do not find this item");
		return generator.vmEnterpriseInvitationToEnterpriseInvitation(vmEnterpriseInvitation);
	}

	@Override
	public EnterpriseInvitation create(String adminId,
			EnterpriseInvitation enterpriseInvitation) throws Exception {
		logger.info(String.format("user %s request to CREATE ENTERPRISEINVITATION", adminId));
		if(enterpriseInvitation != null) {
			VmEnterpriseInvitation vmEnterpriseInvitation = new VmEnterpriseInvitation(
					enterpriseInvitation.id, enterpriseInvitation.enterpriseId, 
					enterpriseInvitation.userId, enterpriseInvitation.information,
					enterpriseInvitation.status);
			enterpriseInvitationProxy.save(vmEnterpriseInvitation);
		}
		return enterpriseInvitation;
	}

	@Override
	public EnterpriseInvitation update(String adminId,
			Integer enterpriseInvitationId,
			EnterpriseInvitation enterpriseInvitation) throws Exception {
		logger.info(String.format("user %s request to UPDATE ENTERPRISEINVITATION %s", adminId, enterpriseInvitationId));
		if(enterpriseInvitation != null) {
			VmEnterpriseInvitation vmEnterpriseInvitation = new VmEnterpriseInvitation(
					enterpriseInvitationId, enterpriseInvitation.enterpriseId, 
					enterpriseInvitation.userId, enterpriseInvitation.information,
					enterpriseInvitation.status);
			enterpriseInvitationProxy.update(vmEnterpriseInvitation);
		}
		return generator.vmEnterpriseInvitationToEnterpriseInvitation(enterpriseInvitationProxy.getById(enterpriseInvitationId));
	}

	@Override
	public void delete(String adminId, Integer enterpriseInvitationId)
			throws Exception {
		logger.info(String.format("user %s request to DELETE ENTERPRISEINVITATION %s", adminId, enterpriseInvitationId));
		enterpriseInvitationProxy.deleteById(enterpriseInvitationId);
	}
	
	@Override
	public List<EnterpriseInvitation> searchByProperties(String adminId,
			Integer enterpriseInvitationId, Integer enterpriseId,
			Integer userId, VmEnterpriseInvitationStatus status, Integer page, Integer size)
			throws Exception {
		String logStr = String.format("User %s request to search enterpriseInvitation", adminId);
		
		QueryObject<VmEnterpriseInvitation> query = new QueryObject<VmEnterpriseInvitation>();
		if(enterpriseInvitationId != null){
			query.addFilterBean(new FilterBean<VmEnterpriseInvitation>("id", enterpriseInvitationId, FilterBeanType.EQUAL));
			logStr += ", enterpriseInvitationId:" + enterpriseInvitationId;
		}
		if(enterpriseId != null) {
			query.addFilterBean(new FilterBean<VmEnterpriseInvitation>("enterpriseId", enterpriseId, FilterBeanType.EQUAL));
			logStr += ", enterpriseId:" + enterpriseId;
		}
		if(userId != null) {
			query.addFilterBean(new FilterBean<VmEnterpriseInvitation>("userId", userId, FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if(status != null) {
			query.addFilterBean(new FilterBean<VmEnterpriseInvitation>("status", status, FilterBeanType.EQUAL));
			logStr += ", status:" + status;
		}
		
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page == null) {
			page = 0;
			size = 0;
		}
			
		if(page != 0)
			page -= 1;

		logger.info(logStr);
		List <EnterpriseInvitation> apiEnterpriseInvitations = new ArrayList<EnterpriseInvitation>();
		List<? extends VmEnterpriseInvitation> vmEnterpriseInvitations = enterpriseInvitationProxy.searchAll(query, page, size);
		for(VmEnterpriseInvitation vmEnterpriseInvitation : vmEnterpriseInvitations) {
			apiEnterpriseInvitations.add(generator.vmEnterpriseInvitationToEnterpriseInvitation(vmEnterpriseInvitation));
		}
		return apiEnterpriseInvitations;
	}
	@Override
	public String countByProperties(String adminId,
			Integer enterpriseInvitationId, Integer enterpriseId,
			Integer userId, VmEnterpriseInvitationStatus status) throws Exception {
		String logStr = String.format("User %s request to count enterpriseInvitation", adminId);
		
		QueryObject<VmEnterpriseInvitation> query = new QueryObject<VmEnterpriseInvitation>();
		if(enterpriseInvitationId != null){
			query.addFilterBean(new FilterBean<VmEnterpriseInvitation>("id", enterpriseInvitationId, FilterBeanType.EQUAL));
			logStr += ", enterpriseInvitationId:" + enterpriseInvitationId;
		}
		if(enterpriseId != null) {
			query.addFilterBean(new FilterBean<VmEnterpriseInvitation>("enterpriseId", enterpriseId, FilterBeanType.EQUAL));
			logStr += ", enterpriseId:" + enterpriseId;
		}
		if(userId != null) {
			query.addFilterBean(new FilterBean<VmEnterpriseInvitation>("userId", userId, FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if(status != null) {
			query.addFilterBean(new FilterBean<VmEnterpriseInvitation>("status", status, FilterBeanType.EQUAL));
			logStr += ", status:" + status;
		}
		logger.info(logStr);
		long allCount = enterpriseInvitationProxy.countSearch(query);
		return String.valueOf(allCount);
	}
	
}
