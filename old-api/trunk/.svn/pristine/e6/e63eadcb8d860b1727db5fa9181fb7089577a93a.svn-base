package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.springframework.aop.framework.ProxyFactory;

import appcloud.api.beans.AcGroup;
import appcloud.api.beans.Enterprise;
import appcloud.api.beans.Volume;
import appcloud.api.manager.EnterpriseManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.model.VmEnterprise;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmEnterpriseProxy;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;

public class RealEnterpriseManager implements EnterpriseManager {
	private static Logger logger = Logger.getLogger(RealAcGroupManager.class);
	private static LolLogUtil loler = LolHelper.getLolLogUtil(RealAcGroupManager.class);
	private BeansGenerator generator;
	private VmEnterpriseProxy enterpriseProxy;
	
	private static RealEnterpriseManager manager = new RealEnterpriseManager();
	public static RealEnterpriseManager getInstance() {
		return manager;
	}
	private RealEnterpriseManager () {
		super();
		generator = BeansGenerator.getInstance();
		enterpriseProxy = (VmEnterpriseProxy) ConnectionFactory.getTipProxy(VmEnterpriseProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	@Override
	public List<Enterprise> getList(String adminId) throws Exception {
		logger.info(String.format("Admin %s request to GET ENTERPRISES", adminId));
		List<VmEnterprise> vmEnterprises = (List<VmEnterprise>) enterpriseProxy.findAll();
		List<Enterprise> enterprises = new ArrayList<Enterprise>();
		for(VmEnterprise vmEnterprise : vmEnterprises) {
			enterprises.add(generator.vmEnterpriseToEnterprise(vmEnterprise));
		}
		return enterprises;
	}

	@Override
	public Enterprise get(String adminId, Integer enterpriseId)
			throws Exception {
		logger.info(String.format("Admin %s request to GET ENTERPRISES %s", adminId, enterpriseId));
		VmEnterprise vmEnterprises = enterpriseProxy.getById(enterpriseId);
		if(vmEnterprises != null) 
			return generator.vmEnterpriseToEnterprise(vmEnterprises);
		else 
			return new Enterprise();
	}

	@Override
	public Enterprise create(String adminId, Enterprise enterprise)
			throws Exception {
		logger.info(String.format("Admin %s request to CREATE ENTERPRISE %s", adminId, enterprise.name));
		VmEnterprise vmEnterprise = new VmEnterprise(
				enterprise.id, enterprise.ownerId, enterprise.isActive,
				enterprise.name, enterprise.description, enterprise.phone,
				enterprise.email, enterprise.address, enterprise.postcode,
				enterprise.homepage, enterprise.parentCompanyId);
		enterpriseProxy.save(vmEnterprise);
		
		return enterprise;
	}

	@Override
	public Enterprise update(String adminId, Integer enterpriseId,
			Enterprise enterprise) throws Exception {
		logger.info(String.format("Admin %s request to UPDATE ENTERPRISE %s, %s", 
				adminId, enterpriseId, enterprise));
		VmEnterprise vmEnterprise = new VmEnterprise
				(enterprise.id, enterprise.ownerId, enterprise.isActive,
				enterprise.name, enterprise.description, enterprise.phone,
				enterprise.email, enterprise.address, enterprise.postcode,
				enterprise.homepage, enterprise.parentCompanyId);
		enterpriseProxy.update(vmEnterprise);
		return generator.vmEnterpriseToEnterprise(enterpriseProxy.getByOwnerId(enterpriseId));
	}

	@Override
	public void delete(String adminId, Integer enterpriseId) throws Exception {
		logger.info(String.format("Admin %s request to DELETE enterprise %s", adminId, enterpriseId));
		enterpriseProxy.deleteById(enterpriseId);
	}
	
	@Override
	public List<Enterprise> searchByProperties(String adminId,
			Integer enterpriseId, Integer ownerId, Boolean isActive,
			String name, String description, String phone, String email,
			String address, String postcode, String homepage,
			Integer parentCompany, Integer page, Integer size) throws Exception {
		String logStr = String.format("User %s request to search enterprises", adminId);
		
		QueryObject<VmEnterprise> query = new QueryObject<VmEnterprise>();
		if(enterpriseId != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("id", enterpriseId, FilterBeanType.EQUAL));
			logStr += ", enterpriseId:" + enterpriseId;
		}
		if(ownerId != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("ownerId", ownerId, FilterBeanType.EQUAL));
			logStr += ", ownerId:" + ownerId;
		}
		if(isActive != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("active", isActive, FilterBeanType.EQUAL));
			logStr += ", isActive:" + isActive;
		}
		if(name != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("name", name, FilterBeanType.BOTH_LIKE));
			logStr += ", name:" + name;
		}
		if(description != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("description", description, FilterBeanType.BOTH_LIKE));
			logStr += ", description:" + description;
		}
		if(phone != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("phone", phone, FilterBeanType.BOTH_LIKE));
			logStr += ", phone:" + phone;
		}
		if(email != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("email", email, FilterBeanType.BOTH_LIKE));
			logStr += ", email:" + email;
		}
		if(address != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("address", address, FilterBeanType.BOTH_LIKE));
			logStr += ", address:" + address;
		}
		if(postcode != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("postcode", postcode, FilterBeanType.BOTH_LIKE));
			logStr += ", postcode:" + postcode;
		}
		if(homepage != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("homepage", homepage, FilterBeanType.BOTH_LIKE));
			logStr += ", homepage:" + homepage;
		}
		if(parentCompany != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("parentCompanyId", parentCompany, FilterBeanType.EQUAL));
			logStr += ", parentCompany:" + parentCompany;
		}
		
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page == null) 
			page = 0;
		else if(page != 0)
			page -= 1;
		
		if(size == null)
			size = 0;

		logger.info(logStr);
		List <Enterprise> apiEnterprises = new ArrayList<Enterprise>();
		List<? extends VmEnterprise> vmEnterprises = enterpriseProxy.searchAll(query, page, size);
		for(VmEnterprise vmEnterprise : vmEnterprises) {
			apiEnterprises.add(generator.vmEnterpriseToEnterprise(vmEnterprise));
		}
		return apiEnterprises;
	}
	
	@Override
	public String countByProperties(String adminId, Integer enterpriseId,
			Integer ownerId, Boolean isActive, String name, String description,
			String phone, String email, String address, String postcode,
			String homepage, Integer parentCompany) throws Exception {
		String logStr = String.format("User %s request to count enterprises", adminId);
		
		QueryObject<VmEnterprise> query = new QueryObject<VmEnterprise>();
		if(enterpriseId != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("id", enterpriseId, FilterBeanType.EQUAL));
			logStr += ", enterpriseId:" + enterpriseId;
		}
		if(ownerId != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("ownerId", ownerId, FilterBeanType.EQUAL));
			logStr += ", ownerId:" + ownerId;
		}
		if(isActive != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("active", isActive, FilterBeanType.EQUAL));
			logStr += ", isActive:" + isActive;
		}
		if(name != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("name", name, FilterBeanType.BOTH_LIKE));
			logStr += ", name:" + name;
		}
		if(description != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("description", description, FilterBeanType.BOTH_LIKE));
			logStr += ", description:" + description;
		}
		if(phone != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("phone", phone, FilterBeanType.BOTH_LIKE));
			logStr += ", phone:" + phone;
		}
		if(email != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("email", email, FilterBeanType.BOTH_LIKE));
			logStr += ", email:" + email;
		}
		if(address != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("address", address, FilterBeanType.BOTH_LIKE));
			logStr += ", address:" + address;
		}
		if(postcode != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("postcode", postcode, FilterBeanType.BOTH_LIKE));
			logStr += ", postcode:" + postcode;
		}
		if(homepage != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("homepage", homepage, FilterBeanType.BOTH_LIKE));
			logStr += ", homepage:" + homepage;
		}
		if(parentCompany != null){
			query.addFilterBean(new FilterBean<VmEnterprise>("parentCompanyId", parentCompany, FilterBeanType.EQUAL));
			logStr += ", parentCompany:" + parentCompany;
		}
		logger.info(logStr);
		
		long allCount = enterpriseProxy.countSearch(query);
		return String.valueOf(allCount);
	}
}
