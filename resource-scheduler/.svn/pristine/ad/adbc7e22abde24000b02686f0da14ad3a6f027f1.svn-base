/**
 * File: TestConnectionFactory.java
 * Author: weed
 * Create Time: 2013-5-7
 */
package appcloud.resourcescheduler.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author weed
 * 
 */
class TestConnectionFactory extends AbstractConnectionFactory {
	Map<String, Object> dbproxys = new HashMap<String, Object>();
	Map<String, Object> services = new HashMap<String, Object>();

	Map<String, String> dbproxyStubs = null;
	Map<String, String> serviceStubs = null;
	
	private static final String DBPROXY_STUB = "src/test/java/appcloud/resourcescheduler/stub/dbproxys/appcloud.common.proxy.stubs.json";
	private static final String SERVICE_STUB = "src/test/java/appcloud/resourcescheduler/stub/services/appcloud.common.service.stubs.json";

	public TestConnectionFactory() {
		super();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			dbproxyStubs = objectMapper.readValue(new FileInputStream(DBPROXY_STUB),
					Map.class);
			serviceStubs = objectMapper.readValue(new FileInputStream(SERVICE_STUB),
					Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getDBProxy(Class<?> clazz) {
		String className = clazz.getName();
		synchronized (TestConnectionFactory.class) {
			if (dbproxys.containsKey(className)) {
				return dbproxys.get(className);
			} else {
				 try {
					dbproxys.put(className, Class.forName(dbproxyStubs.get(className)).newInstance());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return dbproxys.get(className);
			}
		}
	}

	@Override
	public Object getAMQPService(Class<?> clazz, String routingKeyPrefix) {
		String classId = clazz.getName() + routingKeyPrefix;
		synchronized (TestConnectionFactory.class) {
			if (services.containsKey(classId)) {
				return services.get(classId);
			} else {
				 try {
					 services.put(classId, Class.forName(serviceStubs.get(clazz.getName())).newInstance());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return services.get(classId);
			}
		}
	}
}
