package appcloud.api.client;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import appcloud.api.beans.AcService;
import appcloud.api.enums.AcServiceTypeEnum;

public class TestAcServiceClient extends JerseySpringTest {
	public TestAcServiceClient() {
		super("appcloud.api.resources");
	}
	
	private AcServiceClient client = new AcServiceClient("http://127.0.0.1:9998/");
	
	
	@Test
	public void testGetAll() {
		List<AcService> allServices = client.getAllServices();
		assertEquals(3, allServices.size());
		for(AcService service : allServices)
			assertEquals(service.id, service.hostId);
	}
	
	@Test
	public void testGetHostServices() {
		List<AcService> hostServices = client.getHostService("xuan");
		assertEquals(2, hostServices.size());
		for(AcService service : hostServices)
			assertEquals("xuan", service.hostUuid);
	}
	
	@Test
	public void testStart() {
		Set<AcServiceTypeEnum> types = new HashSet<AcServiceTypeEnum>();
		types.add(AcServiceTypeEnum.DHCP_CONTROLLER);
		types.add(AcServiceTypeEnum.IMAGE_SERVER);
		types.add(AcServiceTypeEnum.NETWORK_PROVIDER);
		client.startServices("111", types);
	}
	

	@Test
	public void testStop() {
		Set<AcServiceTypeEnum> types = new HashSet<AcServiceTypeEnum>();
		types.add(AcServiceTypeEnum.RESOURCE_SCHEDULER);
		types.add(AcServiceTypeEnum.VM_CONTROLLER);
		types.add(AcServiceTypeEnum.VOLUME_PROVIDER);
		client.stopServices("111", types);
	}
}
