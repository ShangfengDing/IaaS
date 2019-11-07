package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.EnterpriseInvitation;
import appcloud.common.model.VmEnterprise;
import appcloud.common.model.VmEnterpriseInvitation;
import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;

public interface EnterpriseInvitationManage {
	public List<EnterpriseInvitation> getList(String adminId)throws Exception;
	
	public EnterpriseInvitation get(String adminId, Integer enterpriseInvitationId)throws Exception;
	
	public EnterpriseInvitation create(String adminId, EnterpriseInvitation enterpriseInvitation)throws Exception;
	
	public EnterpriseInvitation update(String adminId, Integer enterpriseInvitationId, EnterpriseInvitation enterpriseInvitation)throws Exception;
	
	public void delete(String adminId, Integer enterpriseInvitationId)throws Exception;
	
	public List<EnterpriseInvitation> searchByProperties(String adminId, Integer enterpriseInvitationId, Integer enterpriseId, 
			Integer userId, VmEnterpriseInvitationStatus status, Integer page, Integer size) throws Exception;
	
	public String countByProperties(String adminId, Integer enterpriseInvitationId, Integer enterpriseId, 
			Integer userId, VmEnterpriseInvitationStatus status) throws Exception;
}
