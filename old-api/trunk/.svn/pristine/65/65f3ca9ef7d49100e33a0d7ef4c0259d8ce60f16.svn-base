package appcloud.api.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.Load;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AcHostLoadClient extends AbstractClient<Load>{
	
	public AcHostLoadClient(){
		super();
	}
	
	public AcHostLoadClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return Load.class;
	}
	
	@Override
	protected GenericType<List<Load>> getGenericType() {		
		GenericType<List<Load>> type = new GenericType<List<Load>>() {};
		return type;
	}
	
	protected String buildPathWithHostId(String hostId) {
		return String.format("admin/ac-hosts/%s/ac-monitors", hostId);
	}
	
	protected String currentLoadPath(String hostId) {
		return String.format("%s/current", buildPathWithHostId(hostId));
	}
	
	@SuppressWarnings("unchecked")
	public List<Load> getLoads(String hostId, String type, Date sTime, Date eTime) {
		@SuppressWarnings("rawtypes")
		MultivaluedMap queryParams = new MultivaluedMapImpl();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		queryParams.add("stime", dateFormat.format(sTime));
		queryParams.add("etime", dateFormat.format(eTime));
		queryParams.add("type", type);
		
		List<Load> loads = super.getList(buildPathWithHostId(hostId), queryParams);
		
		return loads;
		
	}
	
	public Load getCurrentLoad(String hostId) {
		Load load = super.get(currentLoadPath(hostId));
		return load;
	}
}
