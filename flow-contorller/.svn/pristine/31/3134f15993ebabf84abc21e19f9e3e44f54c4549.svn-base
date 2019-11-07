package appcloud.flowcontroller.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import appcloud.common.model.NetSegment;

public class FlowConfig {
	public static String SET_MAX_IN_BANDWIDTH;
	public static String CANCEL_MAX_IN_BANDWIDTH;
	public static String SET_MAX_OUT_BANDWIDTH;
	public static String CANCEL_MAX_OUT_BANDWIDTH;
	public static String SET_POLICING_BURST;
	public static String CANCEL_POLICING_BURST;
	public static String DELETE_PORT;
	public static String ADD_PORT;
	public static String CHECK_CONNECT;
	
	public static String ADD_FLOW;
	public static String DELETE_FLOW;
	
	public static String PUBLIC_NETMASK;
	public static String PUBLIC_SEGMENT;
	public static String PRIVATE_NETMASK;
	public static String PRIVATE_SEGMENT;
	
	public static Map<String, String> BRIDGES = new HashMap<String, String>();
	public static int BANDWIDTH_UPPER;
	
	
	private static Properties p = new Properties();
	private static InputStream is = FlowConfig.class.getClassLoader().getResourceAsStream("flowcontroller.properties");
	private static Logger logger = Logger.getLogger(FlowConfig.class);
	
	static {
		try {
			p.load(is);
			SET_MAX_IN_BANDWIDTH = p.getProperty("SET_MAX_IN_BANDWIDTH");
			logger.info("SET_MAX_IN_BANDWIDTH : " + SET_MAX_IN_BANDWIDTH);
			
			CANCEL_MAX_IN_BANDWIDTH = p.getProperty("CANCEL_MAX_IN_BANDWIDTH");
			logger.info("CANCEL_MAX_IN_BANDWIDTH : " + CANCEL_MAX_IN_BANDWIDTH);
			
			SET_MAX_OUT_BANDWIDTH = p.getProperty("SET_MAX_OUT_BANDWIDTH");
			logger.info("SET_MAX_OUT_BANDWIDTH : " + SET_MAX_OUT_BANDWIDTH);
			
			CANCEL_MAX_OUT_BANDWIDTH = p.getProperty("CANCEL_MAX_OUT_BANDWIDTH");
			logger.info("CANCEL_MAX_OUT_BANDWIDTH : " + CANCEL_MAX_OUT_BANDWIDTH);
			
			SET_POLICING_BURST = p.getProperty("SET_POLICING_BURST");
			logger.info("SET_POLICING_BURST : " + SET_POLICING_BURST);
			
			CANCEL_POLICING_BURST = p.getProperty("CANCEL_POLICING_BURST");
			logger.info("CANCEL_POLICING_BURST : " + CANCEL_POLICING_BURST);
			
			BANDWIDTH_UPPER = Integer.valueOf(p.getProperty("BANDWIDTH_UPPER"));
			logger.info("BANDWIDTH_UPPER : " + BANDWIDTH_UPPER + "Mbps");
			
			DELETE_PORT = p.getProperty("DELETE_PORT");
			logger.info("DELETE_PORT : " + DELETE_PORT);
			
			ADD_PORT = p.getProperty("ADD_PORT");
			logger.info("ADD_PORT : " + ADD_PORT);
			
			CHECK_CONNECT = p.getProperty("CHECK_CONNECT");
			logger.info("CHECK_CONNECT : " + CHECK_CONNECT);
			
			ADD_FLOW = p.getProperty("ADD_FLOW");
			logger.info("ADD_FLOW : " + ADD_FLOW);
			
			DELETE_FLOW = p.getProperty("DELETE_FLOW");
			logger.info("DELETE_FLOW : " + DELETE_FLOW);
			
			PUBLIC_NETMASK = p.getProperty("PUBLIC_NETMASK");
			logger.info("PUBLIC_NETMASK : " + PUBLIC_NETMASK);
			
			PUBLIC_SEGMENT = p.getProperty("PUBLIC_SEGMENT");
			logger.info("PUBLIC_SEGMENT : " + PUBLIC_SEGMENT);
			
			PRIVATE_NETMASK = p.getProperty("PRIVATE_NETMASK");
			logger.info("PRIVATE_NETMASK : " + PRIVATE_NETMASK);
			
			PRIVATE_SEGMENT = p.getProperty("PRIVATE_SEGMENT");
			logger.info("PRIVATE_SEGMENT : " + PRIVATE_SEGMENT);
			
			BRIDGES.put(NetSegment.PRIVATE.toString(), p.getProperty("PRIVATE_BRIDGE"));
			BRIDGES.put(NetSegment.PUBLIC.toString(), p.getProperty("PUBLIC_BRIDGE"));
			logger.info("BRIDGES : " + BRIDGES.get(NetSegment.PRIVATE.toString()) + ", " + BRIDGES.get(NetSegment.PUBLIC.toString()));
		} catch (IOException e) {
			logger.warn("Invalid flowcontroller.properties found", e);
		}
	}
}
