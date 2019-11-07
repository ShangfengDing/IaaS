package appcloud.nodeagent.adaptor;

import org.apache.log4j.Logger;

import appcloud.common.model.Instance;
import appcloud.common.model.Instance.InstanceTypeEnum;

/**
 * @author jianglei
 *
 */
public class VmGCAdaptor {
	private static Logger logger = Logger.getLogger(VmGCAdaptor.class);
	
	public Instance createVMInstance(String uuid, Integer templateId,
			String mac, String ip) {
		Instance instance = null;
		
		String xml = "";
		try {
			xml = Adaptor.genStartVmInstanceXML(uuid, templateId, mac, ip);
		} catch (Exception e) {
			logger.warn("cannot generate xml for vm:" + uuid);
			return instance;
		}
		
		// for vm, uuid and appid is the same!!!
		instance = Adaptor.handleStartRequest(xml, uuid, uuid, InstanceTypeEnum.VM);
		
		return instance;
	}
	
	public int deleteVmInstance(String uuid) {
		int result = -1;
		
		String xml = "";
		try {
			xml = Adaptor.genDeleteVmInstanceXML(uuid);
		} catch (Exception e) {
			logger.warn("cannot generate xml for vm:" + uuid);
			return result;
		}
		
		result = Adaptor.handleRequest(xml, InstanceTypeEnum.VM);
		
		return result;
	}
	
	public int dealVmInstance(String uuid, String action, Integer templateId) {
		int result = -1;
		
		String xml = "";
		try {
			xml = Adaptor.genDealVmInstanceXML(uuid, action, templateId);
		} catch (Exception e) {
			logger.warn("cannot generate xml for vm:" + uuid);
			return result;
		}
		
		result = Adaptor.handleRequest(xml, InstanceTypeEnum.VM);
		
		return result;
		
	}
}
