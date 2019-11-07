/**
 * File: NetworkService.java
 * Author: weed
 * Create Time: 2013-4-28
 */
package appcloud.resourcescheduler.service;

import java.util.Map;

import appcloud.common.model.FlowType;
import appcloud.common.model.NetSegment;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmIpSegMent;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public interface NetworkService {
	/**
	 * @param type 0 私网， 1 公网
	 * @param clusterId 集群Id
	 * @param ipsegment ip分段
	 */
	void createIpSegment(Integer type, Integer clusterId, VmIpSegMent ipSegment, RpcExtra rpcExtra) throws RpcException;
	
	 /**
     * 根据ipsegment的id删除某一段公网ip，如果这段中有ip被占用，删除失败
     * @param ipSegId
     * @throws RpcException
     */
    boolean delNetIpSegment(Integer ipSegId, RpcExtra rpcExtra) throws RpcException;

    /**
     * 设置VM指定网卡的最大带宽
     * @param vmUuid	
     * @param netType	网卡类型（private or public）
     * @param type	流量类型（IN or OUT）
     * @param maxBandwidth	最大带宽（单位MB）
     */
    int setVmMaxBandwidth(String vmUuid, Map<String, String> metadatas,boolean release ,RpcExtra rpcExtra) throws RpcException;
    
    /**
     * 取消VM的最大带宽限制
     * @param vmUuid	
     * @param netType	网卡类型（private or public）
     * @param type	流量类型（IN or OUT）
     * @param rpcExtra
     * @throws RpcException
     */
    boolean cancelVmMaxBandwidth(String vmUuid, NetSegment netType, FlowType type, RpcExtra rpcExtra) throws RpcException;

    public String KeepAlive() throws Exception ;
}
