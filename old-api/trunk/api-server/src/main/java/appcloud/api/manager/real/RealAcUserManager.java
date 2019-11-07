package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.util.UuidUtil;
import org.apache.log4j.Logger;

import appcloud.api.beans.AcGroup;
import appcloud.api.beans.AcUser;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.AcUserManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.model.VmEnterprise;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.VmEnterpriseProxy;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;

public class RealAcUserManager implements AcUserManager {
	
	private static Logger logger = Logger.getLogger(RealAcUserManager.class);
	private static LolLogUtil loler = LolHelper.getLolLogUtil(RealAcUserManager.class);
	
	private VmUserProxy userProxy;
	private VmGroupProxy groupProxy;
	private VmEnterpriseProxy enterpriseProxy;
	private BeansGenerator  generator;
	
	private static RealAcUserManager manager = new RealAcUserManager();
	public static RealAcUserManager getInstance() {
		return manager;
	}
	private RealAcUserManager () {
		super();
		generator = BeansGenerator.getInstance();
		userProxy = (VmUserProxy) ConnectionFactory.getTipProxy(VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		groupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(VmGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	@Override
	public List<AcUser> getList(String adminId) throws Exception {
		logger.info(String.format("Admin %s request to GET USERS", adminId));
		List<VmUser> vmUsers = (List<VmUser>) userProxy.findAll();
		List<AcUser> acUsers = new ArrayList<AcUser>();
		for(VmUser vmUser : vmUsers)
			acUsers.add(generator.userToAcUser(vmUser));
		return acUsers;
	}

	@Override
	public AcUser get(String adminId, String userId) throws Exception {
		logger.info(String.format("Admin %s request to GET USER %s", adminId, userId));
		Integer userIdInt = null;
		try {
			userIdInt = Integer.valueOf(userId);
		} catch(Exception ex) {
			throw new OperationFailedException("user id illegal");
		}
		VmUser vmUser = getByUserId(userIdInt);
		if(vmUser == null)
			throw new ItemNotFoundException("user does not exist");
		AcUser acUser = generator.userToAcUser(vmUser);
		AcGroup acGroup = null;
		try {
			VmGroup vmGroup = groupProxy.getById(vmUser.getGroupId());
			acGroup = generator.groupToAcGroup(vmGroup);
		} catch (Exception ex) {
			logger.info("find group error");
		}
		acUser.group = acGroup;
		return acUser;
	}

	@Override
	public AcUser getAccount(String adminId, String userId) throws Exception {
		logger.info(String.format("Admin %s request to GET USER %s", adminId, userId));
		Integer userIdInt = null;
		try {
			userIdInt = Integer.valueOf(userId);
		} catch(Exception ex) {
			throw new OperationFailedException("user id illegal");
		}
		QueryObject<VmUser> userQuery = new QueryObject<VmUser>();
		userQuery.addFilterBean(new FilterBean<VmUser>("preId",userIdInt, FilterBeanType.EQUAL));
		List<VmUser> vmUsers = (List<VmUser>) userProxy.searchAll(userQuery);
		if(vmUsers.size()== 0)
			throw new ItemNotFoundException("user does not exist");
		VmUser vmUser = vmUsers.get(0);
		AcUser acUser = generator.userToAcUser(vmUser);
		AcGroup acGroup = null;
		try {
			VmGroup vmGroup = groupProxy.getById(vmUser.getGroupId());
			acGroup = generator.groupToAcGroup(vmGroup);
		} catch (Exception ex) {
			logger.info("find group error");
		}
		acUser.group = acGroup;
		return acUser;
	}

	@Override
	public AcUser create(String adminId, AcUser user) throws Exception {
		logger.info(String.format("Admin %s request to CREATE USER %s", adminId, user));
		
		Integer userIdInt = null;
		try {
			userIdInt = Integer.valueOf(user.userId);
		} catch(Exception ex) {
			throw new OperationFailedException("user id illegal");
		}
		
		VmUser vmUser0 = getByUserId(userIdInt);
		if(vmUser0 != null)
			throw new OperationFailedException("user exists");

		VmUser vmUser = generator.acUserToVmUser(user);
		logger.info(vmUser);
		userProxy.save(vmUser);
		return generator.userToAcUser(getByUserId(userIdInt));
	}

	@Override
	public AcUser update(String adminId, String userId, AcUser user) throws Exception {
		logger.info(String.format("Admin %s request to UPDATE USER %s, %s", adminId, userId, user));
		
		Integer userIdInt = null;
		try {
			userIdInt = Integer.valueOf(userId);
		} catch(Exception ex) {
			throw new OperationFailedException("user id illegal");
		}
		VmUser vmUser0 = getByUserId(userIdInt);
		if(vmUser0 == null)
			throw new ItemNotFoundException("user does not exist");
		VmUser vmUser = generator.acUserToVmUser(user);
		vmUser.setPreId(vmUser0.getPreId());
		userProxy.update(vmUser);
		
		return user;
	}

	@Override
	public void delete(String adminId, String userId) throws Exception {
		logger.info(String.format("Admin %s request to DELETE USER %s", adminId, userId));
		Integer userIdInt = null;
		try {
			userIdInt = Integer.valueOf(userId);
		} catch(Exception ex) {
			throw new OperationFailedException("user id illegal");
		}
		VmUser vmUser = getByUserId(userIdInt);
		if(vmUser != null)
			userProxy.deleteById(vmUser.getUserId());
	}

	@Override
	public AcUser addUserToGroup(String adminId, String userId, Integer groupId)
			throws Exception {
		logger.info(String.format("Admin %s request to UPDATE USER %s", adminId, userId));
		
		Integer userIdInt = null;
		try {
			userIdInt = Integer.valueOf(userId);
		} catch(Exception ex) {
			throw new OperationFailedException("user id illegal");
		}
		VmGroup vmGroup = groupProxy.getById(groupId);
		if(vmGroup == null) {
			throw new OperationFailedException("group id illegal");
		}
		VmUser vmUser = getByUserId(userIdInt);
		vmUser.setGroupId(groupId);
		userProxy.update(vmUser);
		
		return generator.userToAcUser(vmUser);
	}
	
	@Override
	public AcUser addUserToEnterprise(String adminId, String userId, Integer enterpriseId) 
			throws Exception {
		logger.info(String.format("Admin %s request to UPDATE USER %s", adminId, userId));
		
		Integer userIdInt = null;
		try {
			userIdInt = Integer.valueOf(userId);
		} catch(Exception ex) {
			throw new OperationFailedException("user id illegal");
		}
		VmEnterprise vmEnterprise = enterpriseProxy.getById(enterpriseId);
		if(vmEnterprise == null) {
			throw new OperationFailedException("group id illegal");
		}
		VmUser vmUser = getByUserId(userIdInt);
		vmUser.setEnterpriseId(enterpriseId);
		userProxy.update(vmUser);
		
		return generator.userToAcUser(vmUser);
	}

	private VmUser getByUserId(Integer userId) throws Exception{
		QueryObject<VmUser> query = new QueryObject<VmUser>();
		query.addFilterBean(new FilterBean<VmUser>("userId", userId, FilterBeanType.EQUAL));
		List<VmUser> vmUsers = (List<VmUser>) userProxy.searchAll(query);
		if(vmUsers == null || vmUsers.size() == 0)
			return null;
		return vmUsers.get(0);
	}
	
	@Override
	@Deprecated
	public List<AcUser> searchByProperties(String adminId, Integer groupId, String userEmail, 
			Boolean isActive, Integer enterpriseId, Integer page, Integer size) throws Exception {
		String logStr = null;
		logStr = String.format("Admin %s request to search User", adminId);
		QueryObject<VmUser> query = new QueryObject<VmUser>();
		if(groupId != null) {
			logStr += ", groupId:" + groupId;
			query.addFilterBean(new FilterBean<VmUser>("groupId", groupId, FilterBeanType.EQUAL));
		}
		if(userEmail != null) {
			logStr += ", userEmail:" + userEmail;
			query.addFilterBean(new FilterBean<VmUser>("userEmail", userEmail, FilterBeanType.BOTH_LIKE));
		}
		if(isActive != null) {
			logStr += ", isActive:" + isActive;
			query.addFilterBean(new FilterBean<VmUser>("active", isActive, FilterBeanType.EQUAL));
		}
		if(enterpriseId != null) {
			logStr += ", enterpriseId:" + enterpriseId;
			query.addFilterBean(new FilterBean<VmUser>("enterpriseId", enterpriseId, FilterBeanType.EQUAL));
		}
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page != 0)
			page -= 1;
		logger.info(logStr);
		
		List<VmUser> vmUsers = (List<VmUser>) userProxy.searchAll(query, page, size);
		List<AcUser> acUsers = new ArrayList<AcUser>();
		for(VmUser vmUser : vmUsers)
			acUsers.add(generator.userToAcUser(vmUser));
		return acUsers;
	}
	
	public List<AcUser> searchByProperties(String adminId, Integer groupId,
			String email, Boolean isActive, String enterpriseIdsStr,
			Integer page, Integer size) throws Exception {
		String logStr = null;
		logStr = String.format("Admin %s request to search User", adminId);
		QueryObject<VmUser> query = new QueryObject<VmUser>();
		if(groupId != null) {
			logStr += ", groupId:" + groupId;
			query.addFilterBean(new FilterBean<VmUser>("groupId", groupId, FilterBeanType.EQUAL));
		}
		if(email != null) {
			logStr += ", userEmail:" + email;
			query.addFilterBean(new FilterBean<VmUser>("userEmail", email, FilterBeanType.BOTH_LIKE));
		}
		if(isActive != null) {
			logStr += ", isActive:" + isActive;
			query.addFilterBean(new FilterBean<VmUser>("active", isActive, FilterBeanType.EQUAL));
		}
		if(enterpriseIdsStr != null) {
			logStr += ", enterpriseId:" + enterpriseIdsStr;
			List<Integer> enterpriseIds = new ArrayList<Integer>();
			String[] ids = enterpriseIdsStr.split(",");
			for(String id : ids) {
				if(id != null && !id.equals(""))
				enterpriseIds.add(new Integer(id));
			}
			query.addFilterBean(new FilterBean<VmUser>("enterpriseId", enterpriseIds, FilterBeanType.IN));
		}
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page != 0)
			page -= 1;
		logger.info(logStr);
		
		List<VmUser> vmUsers = (List<VmUser>) userProxy.searchAll(query, page, size);
		List<AcUser> acUsers = new ArrayList<AcUser>();
		for(VmUser vmUser : vmUsers)
			acUsers.add(generator.userToAcUser(vmUser));
		return acUsers;
	}
	
	
	@Override
	@Deprecated
	public Long countByProperties(String adminId, Integer groupId, String email,
			Boolean isActive, Integer enterpriseId) throws Exception {
		String logStr = null;
		logStr = String.format("Admin %s request to search User", adminId);
		QueryObject<VmUser> query = new QueryObject<VmUser>();
		if(groupId != null) {
			logStr += ", groupId:" + groupId;
			query.addFilterBean(new FilterBean<VmUser>("groupId", groupId, FilterBeanType.EQUAL));
		}
		if(email != null) {
			logStr += ", email:" + email;
			query.addFilterBean(new FilterBean<VmUser>("userEmail", email, FilterBeanType.BOTH_LIKE));
		}
		if(isActive != null) {
			logStr += ", isActive:" + isActive;
			query.addFilterBean(new FilterBean<VmUser>("active", isActive, FilterBeanType.EQUAL));
		}
		if(enterpriseId != null) {
			logStr += ", enterpriseId:" + enterpriseId;
			query.addFilterBean(new FilterBean<VmUser>("enterpriseId", enterpriseId, FilterBeanType.EQUAL));
		}
		logger.info(logStr);
		Long c = userProxy.countSearch(query);
		return c;
	}
	
	public Long countByProperties(String adminId, Integer groupId, String email,
			Boolean isActive, String enterpriseIdsStr) throws Exception {
		String logStr = null;
		logStr = String.format("Admin %s request to search User", adminId);
		QueryObject<VmUser> query = new QueryObject<VmUser>();
		if(groupId != null) {
			logStr += ", groupId:" + groupId;
			query.addFilterBean(new FilterBean<VmUser>("groupId", groupId, FilterBeanType.EQUAL));
		}
		if(email != null) {
			logStr += ", email:" + email;
			query.addFilterBean(new FilterBean<VmUser>("userEmail", email, FilterBeanType.BOTH_LIKE));
		}
		if(isActive != null) {
			logStr += ", isActive:" + isActive;
			query.addFilterBean(new FilterBean<VmUser>("active", isActive, FilterBeanType.EQUAL));
		}
		if(enterpriseIdsStr != null) {
			logStr += ", enterpriseId:" + enterpriseIdsStr;
			List<Integer> enterpriseIds = new ArrayList<Integer>();
			String[] ids = enterpriseIdsStr.split(",");
			for(String id : ids) {
				if(id != null && !id.equals(""))
				enterpriseIds.add(new Integer(id));
			}
			query.addFilterBean(new FilterBean<VmUser>("enterpriseId", enterpriseIds, FilterBeanType.IN));
		}
		logger.info(logStr);
		Long c = userProxy.countSearch(query);
		return c;
	}
	
	
}
