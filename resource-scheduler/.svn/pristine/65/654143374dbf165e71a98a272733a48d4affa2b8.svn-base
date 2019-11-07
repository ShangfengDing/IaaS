/**
 * File: NetworkServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-28
 */
package appcloud.resourcescheduler.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.FlowType;
import appcloud.common.model.MessageLog;
import appcloud.common.model.NetSegment;
import appcloud.common.model.NetworkAddress;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmIpSegMent;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.proxy.VmInstanceMetadataProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmVirtualInterfaceProxy;
import appcloud.common.service.NetworkProviderService;
import appcloud.common.util.AlertUtil;
import appcloud.common.util.LolLogUtil;
import appcloud.resourcescheduler.ResourceScheduler;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.resourcescheduler.service.NetworkService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class NetworkServiceImpl implements NetworkService {
	private static Logger logger = Logger.getLogger(NetworkServiceImpl.class);
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = ResourceScheduler.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.RESOURCE_SCHEDULER, NetworkServiceImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	private final static int PRIVATE_IP_TYPE = 0;
	private final static int PUBLIC_IP_TYPE = 1;
	private static VmVirtualInterfaceProxy vmVirtualInterfaceProxy = (VmVirtualInterfaceProxy) ConnectionManager
			.getInstance().getDBProxy(VmVirtualInterfaceProxy.class);
	private static VmInstanceMetadataProxy vmInstanceMetadataProxy = (VmInstanceMetadataProxy) ConnectionManager
			.getInstance().getDBProxy(VmInstanceMetadataProxy.class);
	private static VmInstanceProxy vmInstanceProxy = (VmInstanceProxy) ConnectionManager
			.getInstance().getDBProxy(VmInstanceProxy.class);
	NetworkProviderService networkProvider = (NetworkProviderService) ConnectionManager.getInstance()
			.getAMQPService(NetworkProviderService.class,
					RoutingKeys.NETWORK_SCHEDULER);
	
	@Override
	public void createIpSegment(Integer type, Integer clusterId,
			VmIpSegMent ipSegment, RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "createIpSegment: " + type + ", " + clusterId + ", " + ipSegment;
		logger.debug(paramInfos);
		try {
			switch (type){
			case PRIVATE_IP_TYPE:
				networkProvider.addPrivateNetIpSegment(clusterId, ipSegment, rpcExtra);
				break;
			case PUBLIC_IP_TYPE:
				networkProvider.addPublicNetIpSegment(clusterId, ipSegment, rpcExtra);
				break;
			default:
				throw new RpcException("Ip type error!");
			}
		} catch (RpcException e) {
			String error = "createIpSegment failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.CREATE_IP_SEGMENT, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("集群" + clusterId + "创建IP段失败", "输入参数为" + paramInfos, e);
		}
	}

	@Override
	public boolean delNetIpSegment(Integer ipSegId, RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "deleteIpSegment: " + ipSegId;
		logger.debug(paramInfos);
		try {
			networkProvider.delNetIpSegment(ipSegId,rpcExtra);
			return true;
		} catch (RpcException e) {
			String error = "deleteIpSegment failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.DEL_NETIP_SEGMENT, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("IP段" + ipSegId + "删除失败", "输入参数为" + paramInfos, e);
			return false;
		}
	}

	@Override
	public int setVmMaxBandwidth(String vmUuid, Map<String, String> metadatas, boolean release, RpcExtra rpcExtra) throws RpcException {
		String message = "set max bandwidth vm=%s";
		logger.info(String.format(message, vmUuid));
		int result = -1;
		VmInstance instance;
		try {
			instance = (VmInstance) vmInstanceProxy.getByUuid(vmUuid, false, false, false, false, true, false, true, false);
			List<VmInstanceMetadata> oldMetadatas = instance.getVmInstanceMetadatas();
			VmInstanceMetadata publicMeta = null;
			VmInstanceMetadata privateMeta = null;
			for(VmInstanceMetadata meta : oldMetadatas) {
				if(meta.getKey().equals("maxBandwidth"))
					publicMeta = meta;
				else if(meta.getKey().equals("priBandwidth"))
					privateMeta = meta;
			}
			logger.info("find old metadata : public="+publicMeta.toString() + "  private="+privateMeta.toString());
			
			List<VmVirtualInterface> oldVifs = instance.getVmVirtualInterfaces();
			VmVirtualInterface publicVif = null;
			VmVirtualInterface privateVif = null;
			for(VmVirtualInterface vif : oldVifs) {
				if(vif.getNetworkType().equals("private"))
					privateVif = vif;
				else if(vif.getNetworkType().equals("public"))
					publicVif = vif;
			}
			logger.info("find old VirtualInterface : public=" + publicVif.toString() + "  private="+privateVif.toString());
			
			//修改内网带宽
			if(metadatas.get("priBandwidth") != null) {
				int newband = Integer.valueOf(metadatas.get("priBandwidth"));
				int oldband = Integer.valueOf(privateMeta.getValue());
				logger.info("old priIP is:"+privateVif.getAddress());
				if(newband > 0 && oldband == 0) {//0 -> 有
					logger.info("try to change priBandwidth form 0 -> "+newband);
					if(privateVif.getAddress() != null){
						logger.info("vm has old priIp:"+privateVif.getAddress());
					}else{
						logger.info("old privateIp is :"+privateVif.getAddress()+". so vm need new IP!");
						NetworkAddress ip = networkProvider.getNewPrivateIpAddress(
								instance.getAvailabilityClusterId(), privateVif.getMac(), rpcExtra);
						logger.info("get private ip : " + ip.getIp());
						if(ip == null)
							throw new RpcException("get private ip failed");
						privateVif.setAddress(ip.getIp());
					}
				} else if(newband == 0 && oldband > 0) {//有 -> 0
					logger.info("try to change priBandwidth form "+oldband+" -> 0 and release:"+release);
					if(release == true){
						logger.info("release private ip : " + privateVif.getAddress());
						networkProvider.releasePrivateIpAddress(instance.getAvailabilityClusterId(), privateVif.getAddress(), rpcExtra);
						privateVif.setAddress(null);
					}else{
						logger.info("not release the old priIP");
					}
				}
				
				privateMeta.setValue(newband+"");
				logger.info("new privateMeta:"+privateMeta.toString());
				vmInstanceMetadataProxy.update(privateMeta);
				logger.info("update metadata success");
				vmVirtualInterfaceProxy.update(privateVif);//更新ip表
				logger.info("update vifs success");
			}
			
			//修改外网带宽
			if(metadatas.get("maxBandwidth") != null) {
				int newband = Integer.valueOf(metadatas.get("maxBandwidth"));
				int oldband = Integer.valueOf(publicMeta.getValue());
				logger.info("old MaxIP is:"+publicVif.getAddress());
				if(newband > 0 && oldband == 0) {// 0 -> 有
					logger.info("try to change maxBandwidth form 0 -> "+newband);
					if(publicVif.getAddress() != null){
						logger.info("vm has old maxIp:"+publicVif.getAddress());
					}else{
						logger.info("old maxIp is :"+publicVif.getAddress()+". so vm need new IP!");
						NetworkAddress ip = networkProvider.getNewPublicIpAddress(
								instance.getAvailabilityClusterId(), publicVif.getMac(), rpcExtra);
						logger.info("get public ip : " + ip.getIp());
						if(ip == null)
							throw new RpcException("get public ip failed");
						publicVif.setAddress(ip.getIp());//获取一个新的ip
					}
				} else if(newband == 0 && oldband > 0) {// 有 -> 0
					logger.info("try to change maxBandwidth form "+oldband+" -> 0 and release:"+release);
					if(release == true){
						logger.info("release public ip : " + publicVif.getAddress());
						networkProvider.releasePublicIpAddress(instance.getAvailabilityClusterId(), publicVif.getAddress(), rpcExtra);
						publicVif.setAddress(null);
					}else{
						logger.info("not release the old maxIP");
					}
				}

				publicMeta.setValue(newband+"");
				logger.info("new publicMeta:"+publicMeta.toString());
				vmInstanceMetadataProxy.update(publicMeta);
				logger.info("update metadata success");
				vmVirtualInterfaceProxy.update(publicVif);
				logger.info("update vifs success");
			}
			
			//通知network-provider修改带宽
			result = networkProvider.setMaxBandWidth(vmUuid, NetSegment.PRIVATE, FlowType.IN, rpcExtra);
			logger.info("set max IN bandwidth success!");
			result = networkProvider.setMaxBandWidth(vmUuid, NetSegment.PRIVATE, FlowType.OUT, rpcExtra);
			logger.info("set max OUT bandwidth success!");
	
			result = networkProvider.setMaxBandWidth(vmUuid, NetSegment.PUBLIC, FlowType.IN, rpcExtra);
			logger.info("set max IN bandwidth success!");
			result &= networkProvider.setMaxBandWidth(vmUuid, NetSegment.PUBLIC, FlowType.OUT, rpcExtra);
			logger.info("set max OUT bandwidth success!");
		} catch (Exception e1) {
			logger.error("set max bandwidth failed! Caused by " + e1.getMessage());
			loller.error(LolLogUtil.SET_VM_MAX_BANDWIDTH, 
					"set max bandwidth failed! Caused by " + e1.getMessage(), rpcExtra);
		}
		return result;
	}
	
	public int setVmMaxBandwidth(String vmUuid, NetSegment netType,
			FlowType type, RpcExtra rpcExtra) throws Exception {
		String message = "set max bandwidth vm=%s NetType=%s FlowType=%s ";
		logger.info(String.format(message, vmUuid, netType.toString(), type.toString()));
		int result = -1;
		try {
			result = networkProvider.setMaxBandWidth(vmUuid, netType, type, rpcExtra);
			logger.info("set max bandwidth success!");
		} catch(RpcException rpcEx) {
			logger.error("set max bandwidth failed! Caused by " + rpcEx.getMessage());
			loller.error(LolLogUtil.SET_VM_MAX_BANDWIDTH, 
					"set max bandwidth failed! Caused by " + rpcEx.getMessage(), rpcExtra);
		}
		return result;
	}

	@Override
	public boolean cancelVmMaxBandwidth(String vmUuid, NetSegment netType,
			FlowType type, RpcExtra rpcExtra) throws RpcException {
		String message = "cancel max bandwidth vm=%s NetType=%s FlowType=%s ";
		logger.info(String.format(message, vmUuid, netType.toString(), type.toString()));
		boolean result = false;
		try {
			result = networkProvider.cancelMaxBandWidth(vmUuid, netType, type, rpcExtra);
			logger.info("cancel max bandwidth success!");
		} catch(RpcException rpcEx) {
			logger.error("cancel max bandwidth failed! Caused by " + rpcEx.getMessage());
			loller.error(LolLogUtil.CANCEL_VM_MAX_BANDWIDTH, 
					"cancel max bandwidth failed! Caused by " + rpcEx.getMessage(), rpcExtra);
		}
		return result;
	}

	@Override
	public String KeepAlive() throws Exception {
//		try{
			return networkProvider.KeepAlive();
//		}
//		catch(RpcException rpcEx) {
//			return "fail";
//		}
	}
}
