package appcloud.nodeagent.adaptor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import tip.xml.XmlParser.Node;

import appcloud.common.control.ControlTipClient;
import appcloud.common.control.data.ControlRequest;
import appcloud.common.control.data.ControlRequests;
import appcloud.common.control.data.ControlResponse;
import appcloud.common.control.data.ControlResponseInstance;
import appcloud.common.control.data.ControlResponses;
import appcloud.common.control.xml.XMLParser;
import appcloud.common.model.Instance;
import appcloud.common.model.Instance.InstanceStatusEnum;
import appcloud.common.model.Instance.InstanceTypeEnum;
import appcloud.nodeagent.info.NodeAgent;

/**
 * @author jiangeli
 * 
 *         this method is used to provide adaptive method to gc
 * 
 */
public class Adaptor {
	
	private static Logger logger = Logger.getLogger(Adaptor.class);

	public static int getJ2eeMask() {
		return 64;
	}

	public static int getVmMask() {
		return 32;
	}
	
	public static List<Instance> buildInstancesInfo(InstanceTypeEnum type,
			ControlResponses resp, String appId) {
		List<Instance> instances = new ArrayList<Instance>();
        
		for (ControlResponseInstance instance : resp.get(0).get("gcList").getChilds()) {
			instances.add(buildInstanceInfo(instance, type, appId));
		}
		return instances;        
	}
	
	private static Instance buildInstanceInfo(ControlResponseInstance controlInstance,
			InstanceTypeEnum type, String appId) {
		Instance instance = new Instance();
        
        //FIXME: appid, how to
        instance.setAppUuid(appId);
        instance.setHostUuid(NodeAgent.getInstance().getUuid());
        String url = controlInstance.getUrl();
        String ip = "";
        String port = "";
        if (type.equals(InstanceTypeEnum.VM)) {
        	ip = url;
        } else {
        	String[] confs = url.split(":");
        	ip = confs[0];
        	port = confs[1];
        }
        
        instance.setServiceIP(ip);
        instance.setServicePort(port);
        instance.setType(type);
        instance.setStatus(InstanceStatusEnum.NORMAL_LOAD);
        instance.setUuid(controlInstance.getUuid());
        
        return instance;
	}

	public static String genStartJ2EEInstanceXML(String appId,
			String warLocation, int xms, int xmx) throws Exception {
		String xml = "";

		xml = genCreateJ2eeXML(appId, warLocation, xms, xmx);

		return xml;
	}

	public static String genDeleteJ2EEInstanceXML(String appId, String uuid,
			int waitingTime) throws Exception {
		String xml = "";

		xml = genDeleteJ2eeXML(appId, waitingTime, uuid);

		return xml;
	}
	
	private static ControlResponses buildAndSendTipRequest(String epStr, String xml, String uuid) throws Exception {
		ControlRequests req = null;
        
        try {
            Node node = XMLParser.parse(xml);
            req = new ControlRequests(node.get("requests"));
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.warn("Exception happend" + ex.getMessage());
        }

        logger.debug("ControlServer get ControlRequests : " + req.toString());

        ControlResponses resp = new ControlResponses();
        ControlResponse res = new ControlResponse();
        resp.add(res);

        ControlRequest cr = req.get(0);

        if (uuid != null) {
        	cr.get("app").addProperty("uuid", uuid);
        }
        
        ControlTipClient ctClient = new ControlTipClient();

        resp = ctClient.invoke(epStr, req);
        
        return resp;		
	}
	
	public static Instance handleStartRequest(String xml, String appId, String uuid, InstanceTypeEnum type){
		ControlResponses resp = null;
		
		String epStr = "";
		if (type.equals(InstanceTypeEnum.J2EE)) {
			epStr = NodeAgent.getInstance().getJ2eeEpStr();
		} else {
			epStr = NodeAgent.getInstance().getVmEpStr();
		}
		
		try {
			resp = buildAndSendTipRequest(epStr, xml, uuid);
		} catch (Exception e) {
			logger.warn("REQUEST send exception: " + e.getMessage());
			return null;
		}
            
        // build instance info and return
        if (resp.get(0).get("status").getValue().equalsIgnoreCase("SUCCESS")) {
        	logger.debug("SUCCESS handling start request");
        	List<Instance> instances = buildInstancesInfo(type, resp, appId);
        	if (instances.size() > 0 ) {
        		return instances.get(0);
        	} else {
        		logger.warn("server did not reply with instance info");
        	}
        }
        
        if (resp.get(0).get("status").getValue().equalsIgnoreCase("FAIL")) {
        	logger.warn(("创建实例失败：\n" + resp.toString()));
        	return null;
        }
        
        return null;
    }
	
	public static int handleRequest(String xml, InstanceTypeEnum type) {
		ControlResponses resp = null;
		
		String epStr = "";
		if (type.equals(InstanceTypeEnum.J2EE)) {
			epStr = NodeAgent.getInstance().getJ2eeEpStr();
		} else {
			epStr = NodeAgent.getInstance().getVmEpStr();
		}
		
		try {
			resp = buildAndSendTipRequest(epStr, xml, null);
		} catch (Exception e) {
			logger.warn("REQUEST send exception: " + e.getMessage());
			return -1;
		}
        
        logger.debug("ControlServer::response: " + resp.toString());
            
        // build instance info and return
        if (resp.get(0).get("status").getValue().equalsIgnoreCase("SUCCESS")) {
        	logger.debug("SUCCESS handling stop request");
        	return 0;
        } 
        
        if (resp.get(0).get("status").getValue().equalsIgnoreCase("FAIL")) {
        	logger.warn(("停止实例失败：\n" + resp.toString()));
        	return -1;
        }
        
        return -1;
    }

	/* the following methods are from old ControlUtils */
	/***************************************************/
	
	private static final String APP_ID = "APP_ID";
	private static final String ACTION = "ACTION";
	private static final String TEMP_ID = "TEMP_ID";
	private static final String CPU = "CPU";
	private static final String MEM = "MEM";
	private static final String WAITING_TIME = "WAITING_TIME";
	private static final String NUM = "NUM";
	private static final String XMS = "XMS";
	private static final String XMX = "XMX";
	private static final String CONFIG = "CONFIG";
	private static final String MAC = "MAC";
	private static final String IP = "IP";
	private static final String LOCATION = "LOCATION";
	private static final String UUID = "UUID";
	public static final String ACTION_START = "start";
	public static final String ACTION_STOP = "stop";
	public static final String ACTION_SUSPEND = "suspend";
	public static final String ACTION_RESUME = "resume";
	public static final String ACTION_BACKUP = "backup";
	public static final String ACTION_UPTEMP = "uptemp";
	public static final String REQ_TYPE_STARTAPP = "startapp";
	public static final String REQ_TYPE_STOPAPP = "stopapp";
	public static final String REQ_TYPE_DEALAPP = "dealapp";
	public static final String DST_NUM = "DSTNUM";
	public static final String IP_CONF = "IP";
	public static final String PORT_CONF = "PORT";
	public static final String WEIGHT_CONF = "WEIGHT";
	public static final String TYPE = "TYPE";
	public static final String URL_SRC = "URLSRC";
	public static final String URL_CONF = "URLCONF";
	public static final String DUPLICATION = "DUPLICATION";

	public static final Integer VM_TIMOUT = 1000 * 60 * 60 * 24;// 超时时间为一天

	/**
	 * 创建j2ee应用, 不用传war包位置。因为不同nodeagent其nfs挂载点不一定相同。由NA自行填充
	 * 
	 * @param appId
	 *            app id
	 * @param warLocation
	 *            war包位置
	 * @param xms
	 *            j2ee程序 内存栈初始化大小
	 * @param xmx
	 *            j2ee程序 最大内存量
	 * @return App Master传回的响应
	 * @throws Exception
	 */
	private static String genCreateJ2eeXML(String appId, String warLocation,
			int xms, int xmx) throws Exception {
		String xmlStr = getXmlContent("create_j2ee.xml");
		xmlStr = replaceXml(xmlStr, APP_ID, appId);
		xmlStr = replaceXml(xmlStr, LOCATION, warLocation);
		xmlStr = replaceXml(xmlStr, XMS, "" + xms);
		xmlStr = replaceXml(xmlStr, XMX, "" + xmx);
		xmlStr = xmlStr.replace("<!--", "");
		xmlStr = xmlStr.replace("-->", "");
		return xmlStr;
	}

	private static String genDeleteJ2eeXML(String appId, int waitingTime,
			String uuid) throws Exception {
		String xmlStr = getXmlContent("delete_j2ee.xml");
		xmlStr = replaceXml(xmlStr, APP_ID, appId);
		xmlStr = replaceXml(xmlStr, WAITING_TIME, "" + waitingTime);
		xmlStr = replaceXml(xmlStr, UUID, uuid);// added by zhangyj

		return xmlStr;
	}
	
	public static String genStartVmInstanceXML(String uuid, Integer templateId,
			String mac, String ip) throws Exception {

		String xmlStr = getXmlContent("create_vm.xml");

		xmlStr = replaceXml(xmlStr, UUID, uuid);
		xmlStr = replaceXml(xmlStr, CONFIG, "" + templateId);
		xmlStr = replaceXml(xmlStr, MAC, mac);
		xmlStr = replaceXml(xmlStr, IP, ip);

		return xmlStr;
	}

	/**
	 * xml参数填充函数
	 * 
	 * @param xmlString
	 * @param key
	 * @param value
	 * @return
	 */
	public static String replaceXml(String xmlString, String key, String value) {
		return xmlString.replace("###" + key + "###", value);
	}

	/**
	 * xml获取函数
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String getXmlContent(String name) throws Exception {
		InputStream inputStream = Adaptor.class.getResourceAsStream(name);
		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(inputStream));

		String xmlDef = "";
		while (bufferedreader.ready()) {
			xmlDef += bufferedreader.readLine() + "\n";
		}

		return xmlDef;
	}

	public static String genDeleteVmInstanceXML(String uuid)
			throws Exception {
		String xmlStr = getXmlContent("delete_vm.xml");
		
        xmlStr = replaceXml(xmlStr, UUID, uuid);
        
        return xmlStr;
	}

	public static String genDealVmInstanceXML(String uuid, String action,
			Integer templateId) throws Exception {
		String xmlStr = getXmlContent("deal_vm.xml");
		
		xmlStr = replaceXml(xmlStr, UUID, uuid);
		xmlStr = replaceXml(xmlStr, ACTION, action);
		xmlStr = replaceXml(xmlStr, TEMP_ID, "" + templateId);
		xmlStr = xmlStr.replaceAll("<!--[.]*-->", "");
	    
		return xmlStr;
	}

	

}
