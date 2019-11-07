package appcloud.api.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sun.jersey.api.client.GenericType;

import appcloud.api.beans.AcService;
import appcloud.api.beans.acservice.AcServiceAction;
import appcloud.api.enums.AcServiceActionEnum;
import appcloud.api.enums.AcServiceTypeEnum;

public class AcServiceClient extends AbstractClient<AcService> {

	public AcServiceClient () {
		super();
	}
	
	public AcServiceClient(String baseURI) {
		super(baseURI);
	}
	
	@Override
	protected Class<?> getType() {
		return AcService.class;
	}

	
	@Override
	protected GenericType<List<AcService>> getGenericType() {		
		GenericType<List<AcService>> type = new GenericType<List<AcService>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-service";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithHostId(String hostId) {
		return String.format("%s/%s", buildPath(), hostId);
	}
	
	protected String buildActionPath(String hostId) {
		return String.format("%s/action", buildPathWithHostId(hostId));
	}

	public List<AcService> getHostService(String hostId) {
		return super.getList(buildPathWithHostId( hostId), null);
	}
	
	public List<AcService> getAllServices() {
		//return super.getList("10/ac-service", null);
		return super.getList(buildPath(), null);
	}
	public static void main(String[] args) {
		System.out.println(new AcServiceClient().getAllServices());
	}
	public boolean startServices(String hostId, Set<AcServiceTypeEnum> typeSet) {
		AcServiceAction serviceAction = new AcServiceAction();
		serviceAction.action = AcServiceActionEnum.AC_SERVICE_START;
		List<AcServiceTypeEnum> types = new ArrayList<AcServiceTypeEnum>(typeSet);
		serviceAction.types = types;
		return super.postWithoutRet(buildActionPath(hostId), serviceAction);
	}

	public boolean stopServices(String hostId, Set<AcServiceTypeEnum> typeSet) {
		AcServiceAction serviceAction = new AcServiceAction();
		serviceAction.action = AcServiceActionEnum.AC_SERVICE_STOP;
		List<AcServiceTypeEnum> types = new ArrayList<AcServiceTypeEnum>(typeSet);
		serviceAction.types = types;
		return super.postWithoutRet(buildActionPath(hostId), serviceAction);
	}
}
