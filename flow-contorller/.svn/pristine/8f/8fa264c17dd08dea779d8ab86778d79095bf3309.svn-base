package appcloud.flowcontroller.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import appcloud.common.constant.FlowTableEnum;
import appcloud.common.model.FlowType;
import appcloud.common.model.NetSegment;
import appcloud.common.model.RpcExtra;
import appcloud.common.service.FLowControllerService;
import appcloud.flowcontroller.util.CommandHelper;
import appcloud.flowcontroller.util.CommandResult;
import appcloud.flowcontroller.util.FlowConfig;
import appcloud.rpc.tools.RpcException;

public class FlowControllerImpl implements FLowControllerService{
	private static Logger logger = Logger.getLogger(FlowControllerImpl.class);
	
	/**
	 * @Description 
	 * 如果带宽为0,只要断开与网桥的连接，删除流表规则即可；
	 * 入股带宽大于0，需要先确认连接建立，然后设置带宽大小；
	 */
	public void setMaxBandwidth(String routingKey, String tap, int maxBandwidth, NetSegment netType, FlowType type,
			String mac, String ip, RpcExtra rpcExtra) throws RpcException {
		if(tap == null) {
			logger.error("can not set a port not exist");
		}
		logger.info("tap=" + tap + "  maxBandwidth="+maxBandwidth+"  netType=" +netType +
				"  flowtype=" + type + "  mac="+mac + "  ip="+ip);
		
		String bridge = "";
		if(FlowConfig.BRIDGES.containsKey(netType.toString()))
			bridge = FlowConfig.BRIDGES.get(netType.toString());

		boolean isConnected = checkConnect(bridge, tap);
		
		if(maxBandwidth == 0) {
			if(isConnected) {
				deletePort(tap, rpcExtra);
			} 
			Map<FlowTableEnum, String> tableMap = new TreeMap<FlowTableEnum, String>();
			tableMap.put(FlowTableEnum.TABLE, "0");
			tableMap.put(FlowTableEnum.DL_SRC, mac);
			deleteFlow(bridge, tableMap);
			return;
		} 
		if(maxBandwidth > 0) {
			//先删除没用的流表规则
			Map<FlowTableEnum, String> tableMap = new TreeMap<FlowTableEnum, String>();
			tableMap.put(FlowTableEnum.TABLE, "0");
			tableMap.put(FlowTableEnum.DL_SRC, mac);
			deleteFlow(bridge, tableMap);

			//添加新的流表规则
			tableMap.put(FlowTableEnum.DL_TYPE, "0x0800");
			if(NetSegment.toInteger(netType) == 0)
				tableMap.put(FlowTableEnum.NW_SRC, ip);
			else if(NetSegment.toInteger(netType) == 1) 
				tableMap.put(FlowTableEnum.NW_SRC, ip);
			tableMap.put(FlowTableEnum.ACTIONS, "normal");
			addFlow(bridge, tableMap);

			if(NetSegment.toInteger(netType) == 0)
				tableMap.put(FlowTableEnum.NW_SRC, FlowConfig.PRIVATE_SEGMENT+"/"+FlowConfig.PRIVATE_NETMASK);
			else if(NetSegment.toInteger(netType) == 1) 
				tableMap.put(FlowTableEnum.NW_SRC, FlowConfig.PUBLIC_SEGMENT+"/"+FlowConfig.PUBLIC_NETMASK);
			tableMap.put(FlowTableEnum.ACTIONS, "drop");
			addFlow(bridge, tableMap);

			//如果没有链接到bridge
			if(!isConnected) {
				addPort(tap, netType, rpcExtra);
			}

			//设置带宽
			if(maxBandwidth > FlowConfig.BANDWIDTH_UPPER) {
				logger.warn("maxBandwidth " + maxBandwidth + " is more than limited bandwidth " + FlowConfig.BANDWIDTH_UPPER + "Mbps");
				maxBandwidth = FlowConfig.BANDWIDTH_UPPER;
			}
			if(type.equals(FlowType.IN)) {
				String qos = "qos_" + tap;
				String queue = "queue" + tap;
				maxBandwidth = maxBandwidth * 8 * 1000000;
				String command = String.format(FlowConfig.SET_MAX_IN_BANDWIDTH, tap, qos, qos, 
						maxBandwidth, queue, queue, maxBandwidth, maxBandwidth);
				execute(command);
			} else if(type.equals(FlowType.OUT)) {
				maxBandwidth = maxBandwidth * 8 * 1000;
				int burst = 0;
				if(maxBandwidth <= 100*8*1000) {
					burst = maxBandwidth / 10;
				} else {
					burst = 10*8*1000;
				}
				
				String command1 = String.format(FlowConfig.SET_MAX_OUT_BANDWIDTH, tap, maxBandwidth) + ", ";
				String command2 = String.format(FlowConfig.SET_POLICING_BURST, tap, burst);
				execute(command1);
				execute(command2);
			} else {
				logger.error("FlowType is wrong");
			}
		}
	}

	public void cancelMaxBandwidth(String routingKey, String tap, FlowType type, String mac, String ip, RpcExtra rpcExtra)
			throws RpcException {
//		String command = "";
//		if(type.equals(FlowType.IN)) {
//			command = String.format(FlowConfig.CANCEL_MAX_IN_BANDWIDTH, tap);
//		} else if(type.equals(FlowType.OUT)) {
//			command = String.format(FlowConfig.CANCEL_MAX_OUT_BANDWIDTH, tap) + ", ";
//			command = command + String.format(FlowConfig.CANCEL_POLICING_BURST, tap);
//		} else {
//			logger.error("FlowType is wrong");
//		}
//		execute(command);
	}
	
	/**
	 * @Description 
	 * 删除流表规则。在虚拟机热迁移之后，要将所迁虚拟机原宿主机上的流表规则删除。
	 */
	public void deleteFlowTable(String routingKey, String tap, NetSegment netType, 
			String mac, RpcExtra rpcExtra) throws RpcException {
		if(tap == null) {
			logger.error("can not delete a port not exist");
		}
		logger.info("delete flow table: tap=" + tap + "netType=" + netType + ";mac=" + mac);

		String bridge = "";
		if(FlowConfig.BRIDGES.containsKey(netType.toString()))
			bridge = FlowConfig.BRIDGES.get(netType.toString());

		boolean isConnected = checkConnect(bridge, tap);
		if(isConnected) {
			deletePort(tap, rpcExtra);
			logger.info("delete port success!");
		}

		Map<FlowTableEnum, String> tableMap = new TreeMap<FlowTableEnum, String>();
		tableMap.put(FlowTableEnum.TABLE, "0");
		tableMap.put(FlowTableEnum.DL_SRC, mac);
		deleteFlow(bridge, tableMap);
		logger.info("delete flow table successfully!");
		return;
	}

	private CommandResult execute(String command) {
		if(command == null || "".equals(command))
			return null;
		
		CommandHelper com = new CommandHelper();
		try {
			CommandResult result = com.exec(command);
			if(result.getExitValue() == CommandResult.EXIT_VALUE_TIMEOUT ||
					!isEmptyString(result.getError())) {
				logger.error("execute command " + command + " error, " + result.getError());
				return null;
			}
			logger.info("execute command \"" + command + "\" success.");
			return result;
		}catch(IOException ioe) {
			logger.error(ioe.getMessage());
			return null;
		}
	}
	
	private boolean isEmptyString(String str) {
		if(str == null) 
			return true;
		else if("".equals(str))
			return true;
		else 
			return false;
	}

	@Override
	public void deletePort(String tap, RpcExtra rpcExtra) {
		if(isEmptyString(tap))
			return;
		
		String command = String.format(FlowConfig.DELETE_PORT, tap);
		execute(command);
	}

	@Override
	public void addPort(String tap, NetSegment netType,
			RpcExtra rpxExtra) {
		if(isEmptyString(tap)) 
			return;
		
		String bridge = getBridge(netType);
		if(bridge == null)
			return;
		if(checkConnect(bridge, tap)) {
			logger.warn("tap " + tap + " has already connect to " + bridge);
			return;
		}
		
		String command = String.format(FlowConfig.ADD_PORT, bridge, tap);
		execute(command);
	}
	
	private String getBridge(NetSegment netType) {
		if(NetSegment.toInteger(netType) == -1)
			return null;
		if(!FlowConfig.BRIDGES.containsKey(netType.toString()))
			return null;
		return FlowConfig.BRIDGES.get(netType.toString());
	}
	
	private boolean checkConnect(String bridge, String iface) {
		String cmd = FlowConfig.CHECK_CONNECT;
		cmd = String.format(cmd, bridge, iface);
		CommandResult cr = execute(cmd);
		String result = cr.getOutput();
		if(isEmptyString(result))
			return false;
		result = result.trim();
		if(iface.equals(result))
			return true;
		return false;
	}
	

	@Override
	public void addFlow(String bridge, Map<FlowTableEnum, String> tableConfig) {
		if(isEmptyString(bridge) || tableConfig.size() == 0)
			return;
		
		StringBuffer table = new StringBuffer();
		for(Entry<FlowTableEnum, String> entry : tableConfig.entrySet()) {
			table.append(entry.getKey()+"="+entry.getValue()+",");
		}
		table.deleteCharAt(table.length()-1);
		String cmd = String.format(FlowConfig.ADD_FLOW, bridge, table.toString());
		execute(cmd);
	}

	@Override
	public void deleteFlow(String bridge, Map<FlowTableEnum, String> tableConfig) {
		if(isEmptyString(bridge) || tableConfig.size() == 0)
			return;
		StringBuffer table = new StringBuffer();
		for(Entry<FlowTableEnum, String> entry : tableConfig.entrySet()) {
			table.append(entry.getKey()+"="+entry.getValue().toString()+",");
		}
		table.deleteCharAt(table.length()-1);
		String cmd = String.format(FlowConfig.DELETE_FLOW, bridge, table.toString());
		execute(cmd);
	}

	@Override
	public String KeepAlive(String routingKey) throws Exception  {
		
		logger.info(String.format("------------------------keepalive------------------------"));
		logger.info(String.format("----------------"+routingKey+":success-------------------"));
		
		return "success";
	}
}


