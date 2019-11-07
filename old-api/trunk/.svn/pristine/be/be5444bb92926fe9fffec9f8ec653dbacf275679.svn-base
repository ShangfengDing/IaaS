package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.AcGroup;

public interface AcGroupManager {

	public List<AcGroup> getList(String adminId)throws Exception;
	
	public AcGroup get(String adminId, Integer groupId)throws Exception;
	
	public AcGroup create(String adminId, AcGroup group)throws Exception;
	
	public AcGroup update(String adminId, Integer groupId, AcGroup group)throws Exception;
	
	public void delete(String adminId, Integer groupId)throws Exception;

	public List<AcGroup> getByClusterId(String adminId,
			Integer clusterId) throws Exception;

}
