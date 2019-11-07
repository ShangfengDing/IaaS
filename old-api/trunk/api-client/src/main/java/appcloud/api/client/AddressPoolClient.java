package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.AddressPool;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AddressPoolClient extends AbstractClient<AddressPool>{
	
	public AddressPoolClient(){
		super();
	}
	
	public AddressPoolClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AddressPool.class;
	}
	
	@Override
	protected GenericType<List<AddressPool>> getGenericType() {		
		GenericType<List<AddressPool>> type = new GenericType<List<AddressPool>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-address_pools";
	}
	
	protected String buildPath(String adminId) {
		return String.format("%s/%s", adminId, getPath());
	}
	
	protected String buildPathWithId(String adminId, Integer poolId) {
		return String.format("%s/%s", buildPath(adminId), poolId);
	}

	
	public AddressPool get(String adminId, Integer addressPoolId) {
		return super.get(buildPathWithId(adminId, addressPoolId));
	}
	
	public List<AddressPool> getAddressPools(String adminId) {
		return super.getList(buildPath(adminId), null);
	}
	
	public AddressPool createAddressPool(String adminId,  AddressPool createReq) {
		return super.postWithRet(buildPath(adminId), createReq);
	}
	
	public boolean deleteAddressPool(String adminId, Integer addressPoolId) {
		return super.delete(buildPathWithId(adminId, addressPoolId));
	}
	
	public List<AddressPool> getAddressPoolsOfAggregate(String adminId, Integer aggregateId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(aggregateId != null)
			params.add("aggregate_id", String.valueOf(aggregateId));
		return super.getList(buildPath(adminId), params);
	}
	
	public List<AddressPool> getAddressPoolsOfZone(String adminId, Integer zoneId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(zoneId != null)
			params.add("zone_id", String.valueOf(zoneId));
		return super.getList(buildPath(adminId), params);
	}
}
