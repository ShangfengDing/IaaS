package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceStatus;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.dbproxy.mysql.MySQLServiceProxy;

public class MySQLServiceProxyTest {

	private static ServiceProxy proxy;
	private static final String HOST_IP = "192.168.1.19";
	private static final String HOST_UUID = "00001111";
	private static final int HOST_PORT = 9009;
	
	@Before
	public void setUp() throws Exception {
		proxy = new MySQLServiceProxy();
	}
	

	Service newService(){
		Service service = new Service();
		service.setHostId(4);
		service.setHostUuid(HOST_UUID);
		service.setHostIp(HOST_IP);
		service.setMonitorPort(HOST_PORT);
		service.setLastStartTime(new Timestamp(System.currentTimeMillis()));
		service.setLastStopTime(new Timestamp(System.currentTimeMillis()));
		service.setZoneId(1);
		service.setServiceStatus(ServiceStatus.INIT);
		service.setType(ServiceTypeEnum.IMAGE_SERVER);
		service.setClusterId(1);
		return service;
	}
	
	@Test
	public void testInsert() {
		Service service = newService();
		proxy.save(service);
		service =  proxy.getUniqueService(HOST_IP, HOST_PORT, false);
		
		assertNotNull(service.getId());
		assertEquals(HOST_IP, service.getHostIp());
		
		proxy.delete(service.getId());
	}
	
	@Test
	public void testSelect() {
		Service service = newService();
		proxy.save(service);
		
		service =  proxy.getUniqueService(HOST_IP, HOST_PORT, false);
		assertNotNull(service);
		proxy.delete(service.getId());
	}

	@Test
	public void testHostService() {
		Service service = newService();
		proxy.save(service);
		assertTrue(proxy.getHostServicesById(service.getHostId()).size() > 0);
		assertTrue(proxy.getHostServicesByUuid(service.getHostUuid()).size() > 0);
	}
	
	@Test
	public void testSearch() {
		QueryObject<Service> queryObject = new QueryObject<Service>();
		FilterBean<Service> filterBean = new FilterBean<Service>();
		
		Service.ServiceTypeEnum type = Service.ServiceTypeEnum.IMAGE_SERVER;
		filterBean.setName("type");
		filterBean.setValue(type);
		filterBean.setRealType(type.getClass().getName());
		filterBean.setType(FilterBeanType.EQUAL);
		queryObject.addFilterBean(filterBean);
		List<Service> list = (List<Service>) proxy.searchAll(queryObject, false);
		
		for (Service service : list) {
			assertEquals(type, service.getType());
		}

	}
	
	@Test
	public void testGetLatest() {
		Service service = newService();
		proxy.save(service);
		service = proxy.getLatestHostService(service.getHostId());
		assertEquals(HOST_PORT, service.getMonitorPort().intValue());
		
		proxy.delete(service.getId());
	}
}
