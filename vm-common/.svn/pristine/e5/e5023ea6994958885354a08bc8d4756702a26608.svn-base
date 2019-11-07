package appcloud.common.service;

import java.util.List;

import appcloud.common.model.FlowType;
import appcloud.common.model.NetSegment;
import appcloud.common.model.NetworkAddress;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmIpSegMent;
import appcloud.rpc.ampq.annotation.RPCTimeout;
import appcloud.rpc.tools.RpcException;

/**
 * @ClassName: NetworkProviderService
 * @Description: TODO
 * @author wenchaoz361
 * @date 2013-4-12 下午4:41:02
 */
public interface NetworkProviderService {
    /**
    * @Title: addPublicNetIpSegment
    * @Description: 添加一个编号为id的公网集群
    * @param id
    * @param ip
    * @param netMask
    * @throws Exception
    */ 
	@RPCTimeout(timeout = 60)
    public void addPublicNetIpSegment(Integer id, VmIpSegMent ipsegment, RpcExtra rpcExtra) throws RpcException;
    
    /**
    * @Title: addPrivateNetIpSegment
    * @Description: 添加一个编号为id的私网集群
    * @param id
    * @param ip
    * @param netMask
    * @throws Exception
    */
	@RPCTimeout(timeout = 60)
    public void addPrivateNetIpSegment(Integer id, VmIpSegMent ipsegment, RpcExtra rpcExtra) throws RpcException;
    
	/**
    * @Title: getNewMacAddress
    * @Description: 为新虚拟机分配Mac地址
    * @param id
    * @return
    * @throws Exception
    */ 
	@RPCTimeout(timeout = 120)
    public List<String> getNewMacAddress(Integer id, RpcExtra rpcExtra) throws RpcException;
	
    /**
    * @Title: getNewPrivateIpAddress
    * @Description: 从id集群中分配一个私网地址
    * @param id
    * @return
    * @throws Exception
    */ 
	@RPCTimeout(timeout = 60)
    public NetworkAddress getNewPrivateIpAddress(Integer id, String macAddr, RpcExtra rpcExtra) throws RpcException;
    
    /**
    * @Title: getNewPublicIpAddress
    * @Description: 从id集群中获取一个公网地址
    * @param id
    * @return
    * @throws Exception
    */ 
	@RPCTimeout(timeout = 60)
    public NetworkAddress getNewPublicIpAddress(Integer id, String macAddr, RpcExtra rpcExtra) throws RpcException;
    
    /**
    * @Title: releasePrivateIpAddress
    * @Description: 从id集群中释放一个私网ip地址
    * @param id
    * @param ip
    * @throws Exception
    */ 
	@RPCTimeout(timeout = 60)
    public void releasePrivateIpAddress(Integer id, String ip, RpcExtra rpcExtra) throws RpcException;
    
    /**
    * @Title: releasePublicIpAddress
    * @Description: 从id集群中释放一个公网ip
    * @param id
    * @param ip
    * @throws Exception
    */ 
	@RPCTimeout(timeout = 60)
    public void releasePublicIpAddress(Integer id, String ip, RpcExtra rpcExtra) throws RpcException;
    
    /**
     * 根据ipsegment的id删除某一段公网ip，如果这段中有ip被占用，删除失败
     * @param ipSegId
     * @throws RpcException
     */
	@RPCTimeout(timeout = 60)
    public void delNetIpSegment(Integer ipSegId, RpcExtra rpcExtra) throws RpcException;

	/**
	 * @Description 给指定VM的指定tap设置流入或者流出带宽大小
	 * @param instanceUuid	虚拟机的UUID
	 * @param netType	指定的网络类型，目前只有private和public两种
	 * @param type	流量类型（IN or OUT）
	 * @param rpcExtra
	 */
	@RPCTimeout(timeout = 30)
	public int setMaxBandWidth(String instanceUuid, NetSegment netType, FlowType type, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * @Description 给指定VM的指定tap取消网络流量限制
	 * @param instanceUuid	虚拟机的UUID
	 * @param netType	指定的网络类型，目前只有private和public
	 * @param type	流量类型（IN or OUT）
	 * @param rpcExtra
	 * @throws RpcException
	 */
	@RPCTimeout(timeout = 30)
	public boolean cancelMaxBandWidth(String instanceUuid, NetSegment netType, FlowType type, RpcExtra rpcExtra) throws RpcException;

	/**
	 * @Description VM热迁移之后要将原有的流表信息删除
	 * @param instanceUuid	虚拟机的UUID
	 * @param netType	指定的网络类型，目前只有private和public两种
	 * @param rpcExtra
	 * @author hgm
	 */
	@RPCTimeout(timeout = 30)
	boolean deleteFlowTable(String instanceUuid, NetSegment netType, RpcExtra rpcExtra) throws Exception;

	@RPCTimeout(timeout = 5)
	public String KeepAlive() throws Exception ;
}
