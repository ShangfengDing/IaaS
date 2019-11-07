package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.AddressPool;

public interface AddressPoolManager {

	public List<AddressPool> getList(String adminId, Integer zoneId, Integer aggregateId) throws Exception;
	
	public AddressPool get(String adminId, Integer poolId) throws Exception;
	
	public AddressPool create(String adminId, AddressPool createReq) throws Exception;
	
	public void delete(String adminId, Integer poolId) throws Exception;
}
