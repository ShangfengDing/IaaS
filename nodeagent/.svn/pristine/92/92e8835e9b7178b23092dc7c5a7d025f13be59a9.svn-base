package appcloud.nodeagent.adaptor;

import org.apache.log4j.Logger;
import appcloud.common.model.Instance;
import appcloud.common.model.Instance.InstanceTypeEnum;

/**
 * @author jianglei
 *
 */
public class JavaGCAdaptor {
	private static Logger logger = Logger.getLogger(JavaGCAdaptor.class);
	
	public Instance startJ2EEInstance(String appId, String uuid,
			String warLocation, int xms, int xmx) {
		Instance instance = null;
		
		String xml = "";
		try {
			xml = Adaptor.genStartJ2EEInstanceXML(appId, warLocation, xms, xmx);
		} catch (Exception e) {
			logger.warn("cannot generate xml for app:" + appId);
			return instance;
		}
		
		instance = Adaptor.handleStartRequest(xml, appId, uuid, InstanceTypeEnum.J2EE);
		
		return instance;
	}
	
	public int deleteJ2EEInstance(String appId, String uuid, int waitingTime) {
		int result = -1;
		
		String xml = "";
		try {
			xml = Adaptor.genDeleteJ2EEInstanceXML(appId, uuid, waitingTime);
		} catch (Exception e) {
			logger.warn("cannot generate xml for app:" + appId);
			return result;
		}
		
		result = Adaptor.handleRequest(xml, InstanceTypeEnum.J2EE);
		
		return result;		
	}
}
