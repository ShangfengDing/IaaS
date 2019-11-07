package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.AcUser;

public interface AcUserManager {
	
	public List<AcUser> getList(String adminId) throws Exception;
	
	public AcUser get(String adminId, String userId) throws Exception;

	public AcUser getAccount(String adminId, String userId) throws Exception;

	public AcUser create(String adminId, AcUser user) throws Exception;
	
	public AcUser update(String adminId, String userId, AcUser user) throws Exception;
	
	public void delete(String adminId, String userId) throws Exception;
	
	public AcUser addUserToGroup(String adminId, String userId, Integer groupId)throws Exception;
	
	public AcUser addUserToEnterprise(String adminId, String userId, Integer enterpriseId) throws Exception;

	@Deprecated
	public List<AcUser> searchByProperties(String adminId, Integer groupId, String email, Boolean isActive, 
			Integer enterpriseId, Integer page, Integer size)throws Exception;
	
	public List<AcUser> searchByProperties(String adminId, Integer groupId, String email, Boolean isActive, 
			String enterpriseId, Integer page, Integer size)throws Exception;
	
	@Deprecated
	public Long countByProperties(String adminId, Integer groupId, String email, Boolean isActive, 
			Integer enterpriseId) throws Exception;
	
	public Long countByProperties(String adminId, Integer groupId, String email, Boolean isActive, 
			String enterpriseIds) throws Exception;
}
