package appcloud.common.service;

import java.util.Map;

import appcloud.common.model.FlowType;
import appcloud.common.model.NetSegment;
import appcloud.common.model.RpcExtra;
import appcloud.common.constant.FlowTableEnum;
import appcloud.rpc.ampq.annotation.RPCTimeout;
import appcloud.rpc.ampq.annotation.RoutingKeyAnnotation;
import appcloud.rpc.tools.RpcException;


/**
 * 
 * @author linxiong
 * @Description 控制一个节点的网络流量
 * @date 2015/03/06 15:11
 */
public interface FLowControllerService {
	/**
	 * @Description 设置对端口的带宽限制
	 * @param tap	虚拟机网卡对应的标签（如vnet0）
	 * @param maxBandWidth	最大入口或出口带宽大小，单位是Mbps
	 * @param type 	入口或者出口
	 * @param mac
	 * @param ip
	 * @param rpcExtra
	 * @throws Exception
	 */
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 1000)
	public void setMaxBandwidth(String routingKey, String tap, int maxBandwidth, NetSegment netType, FlowType type, 
			String mac, String ip, RpcExtra rpcExtra) throws RpcException;

	/**
	 * @Description 取消对端口的带宽限制
	 * @param tap	虚拟机网卡对应的标签（如vnet0）
	 * @param type	入口或者出口
	 * @param mac
	 * @param ip
	 * @param rpcExtra
	 * @throws Exception
	 */
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 1000)
	public void cancelMaxBandwidth(String routingKey, String tap, FlowType type, 
			String mac, String ip, RpcExtra rpcExtra) throws RpcException;

	/**
	 * @Description 
	 * 删除流表规则。在虚拟机热迁移之后，要将所迁虚拟机原宿主机上的流表规则删除。
	 */
	@RoutingKeyAnnotation(index=0)
	@RPCTimeout(timeout = 1000)
	public void deleteFlowTable(String routingKey, String tap, NetSegment netType, 
			String mac, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @Description 从网桥上删除一个接口
	 * @param routingKey
	 * @param tap	虚拟机网卡对应的标签（如vnet0）
	 * @param rpcExtra
	 */
	public void deletePort(String tap, RpcExtra rpcExtra);
	
	/**
	 * @Description 添加一个端口到指定网桥
	 * @param routingKey
	 * @param tap	虚拟机网卡对应的标签（如vnet0）
	 * @param bridge	网桥名称
	 * @param rpxExtra
	 */
	public void addPort(String tap, NetSegment netType, RpcExtra rpxExtra);

	/**
	 * @Description 添加流表规则
	 * @param br0	待添加流表的网桥
	 * @param tableConfig	流表规则项
	 */
	public void addFlow(String bridge, Map<FlowTableEnum, String> tableConfig);
	
	/**
	 * @Description 添加流表规则
	 * @param br0	待添加流表的网桥
	 * @param tableConfig	流表规则项
	 */
	public void deleteFlow(String bridge, Map<FlowTableEnum, String> tableConfig);
	
	
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 5)
	public String KeepAlive(String routingKey) throws Exception ;
	
}