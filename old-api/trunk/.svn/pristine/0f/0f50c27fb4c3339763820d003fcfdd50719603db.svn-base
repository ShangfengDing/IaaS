package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.Enterprise;
import appcloud.common.model.VmEnterprise;

public interface EnterpriseManager {	
	public List<Enterprise> getList(String adminId)throws Exception;
	
	public Enterprise get(String adminId, Integer enterpriseId)throws Exception;
	
	public Enterprise create(String adminId, Enterprise Enterprise)throws Exception;
	
	public Enterprise update(String adminId, Integer enterpriseId, Enterprise Enterprise)throws Exception;
	
	public void delete(String adminId, Integer enterpriseId) throws Exception;
	
	public List<Enterprise> searchByProperties(String adminId, Integer enterpriseId, Integer ownerId,
			Boolean isActive, String name, String description, String phone, String email, String address,
			String postcode, String homepage, Integer parentCompany, Integer page, Integer size) throws Exception;
	
	public String countByProperties(String adminId, Integer enterpriseId, Integer ownerId,
			Boolean isActive, String name, String description, String phone, String email, String address,
			String postcode, String homepage, Integer parentCompany) throws Exception;
}
