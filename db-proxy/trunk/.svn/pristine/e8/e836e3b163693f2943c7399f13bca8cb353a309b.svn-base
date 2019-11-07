package appcloud.dbproxy;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import appcloud.common.service.monitor.AbstractYunhaiService;
import appcloud.dbproxy.util.ConfigReader;
import appcloud.dbproxy.util.ProxyUnit;
import org.apache.log4j.Logger;

public class ProxyServer extends AbstractYunhaiService{

	private final static Logger logger = Logger.getLogger(ProxyServer.class);

	public static void main(String[] args) throws Exception {
		ProxyServer ps = new ProxyServer();
		ps.run();
	}
	
	public void initialize() {
		super.initialize();
		
		List<ProxyUnit> services;
		try {

			services = ConfigReader.readFromJson(ProxyServer.class.getClassLoader().getResource("services.json"));
			for (ProxyUnit service : services) {
				logger.info("proxy:"+service.getProxyClass()+", class:"+service.getImplClass());
				tipserver.addService(Class.forName(service.getProxyClass()), Class.forName(service.getImplClass()).newInstance());
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
