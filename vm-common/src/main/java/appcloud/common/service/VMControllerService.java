package appcloud.common.service;

import java.util.List;

import appcloud.common.model.HostLoad;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.rpc.ampq.annotation.RPCTimeout;
import appcloud.rpc.ampq.annotation.RoutingKeyAnnotation;
import appcloud.rpc.tools.RpcException;

/**
 * @author liyuan
 * 
 */

public interface VMControllerService {

	// ----------虚拟机功能----------

	/**
	 * 创建虚拟机
	 * 
	 * @param instance
	 *            虚拟机的信息 uuid name rootDeviceLocation 不能为null osType
	 *            osMode正常情况下也不能为null 目前如果为null,就从配置文件里读取
	 * @param instanceType
	 *            vcpus 不能为null,且不能大于8 memoryMb 不能为null
	 * @param instanceMetadata
	 * @param volume
	 * @param vmSecurityGroup
	 * @param vifs
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public void createVM(String routingKey, VmInstance instance,
			VmInstanceType instanceType,
			List<VmInstanceMetadata> instanceMetadata, List<VmVolume> volume,
			VmSecurityGroup vmSecurityGroup, List<VmVirtualInterface> vifs, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 启动虚拟机 参数检查同上
	 * 
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public void startVM(String routingKey, VmInstance instance,
			VmInstanceType instanceType,
			List<VmInstanceMetadata> instanceMetadata, List<VmVolume> volume,
			VmSecurityGroup vmSecurityGroup, List<VmVirtualInterface> vifs, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 删除虚拟机
	 * 
	 * @param instanceUUID
	 *            为uuid且非null
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public void deleteVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 停止虚拟机
	* @param instanceUUID
	 *            为uuid且非null
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 30)
	public void stopVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 强制停止虚拟机：强制关机(调用destroy)，但是不同于delete(不会调用undefine)
	 * 
	 * @param instanceUUID 为uuid且非null
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public void forceStopVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 重启虚拟机
	 * 
	 * @param instanceUUID 为uuid且非null
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 30)
	public void rebootVM(String routingKey, String instanceUUID, List<VmVirtualInterface> vifs, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 挂起虚拟机
	 * 
	  * @param instanceUUID 为uuid且非null
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public void suspendVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 恢复虚拟机
	 * 
	 * @param instanceUUID 为uuid且非null
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public void resumeVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 
	 * 重置虚拟机配置
	 * 
	 * @param routingKey
	 * @param instanceUUID 为uuid且非null
	 * @param instanceType
	 * 		vcpus memoryMb 非null
	 * 			
	 * @param instanceMetadata 可以为null
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public void resizeVM(String routingKey, String instanceUUID,
			VmInstanceType instanceType, VmInstanceMetadata instanceMetadata, List<VmVirtualInterface> vifs, VmSecurityGroup securityGroup, RpcExtra rpcExtra)
			throws RpcException;
	
	/**
	 * @param routingKey
	 * @param instanceUUID  被迁移的虚拟机UUID
	 * @param destURI		目标宿主机的URI路径（如 qemu+ssh://192.168.1.12/system）
	 * @param instanceName  虚拟机迁移到宿主机后的名称
	 * @param flags			见org.libvirt.Domain.MigrateFlags的说明
	 * @param rpcExtra	
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 300)
	public int migrateVM(String routingKey, String instanceUUID, String destURI, 
			String instanceName, long flags, RpcExtra rpcExtra) throws RpcException;

	// ----------监控功能----------
	/**
	 * 收集指定虚拟机的负载信息
	 * 
	 * @param instanceUUID
	 * @return
	 * @throws Exception
	 */
	@RoutingKeyAnnotation(index = 0)
	public HostLoad collectSpecificVMLoad(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 收集本机的全部虚拟机的负载信息
	 * 
	 * @param instanceUUID
	 * @return
	 * @throws Exception
	 */
	@RoutingKeyAnnotation(index = 0)
	public List<HostLoad> collectVMLoadList(String routingKey)
			throws RpcException;

	/**
	 * 获取虚拟机的 vnc port
	 * 
	 * @param instanceUUID 为uuid且非null
	 * @return port的字符串形式，如 5001,查询不到返回null
	 * @throws Exception
	 */
	@RoutingKeyAnnotation(index = 0)
	public String getVncPort(String routingkey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 获取虚拟机的状态
	 * 
	 * @param instanceUUID 为uuid且非null
	 * @return DomainInfo.DomainState的格式: ACTIVE, SUSPENDED, STOPPED, ERROR,
	 *         DELETED
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public VmStatusEnum getDomainState(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException;

	// ----------防火墙功能----------p
	
	/**
	 * 
	 * @param routingkey
	 * @param securityGroup  防火墙组策略，使用的是其name 
	 * 			uuid name 不能为null
	 * @param SGRules  具体的防火墙规则列表
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 20)
	public void defineNetworkFilter(String routingkey,
			VmSecurityGroup securityGroup, List<VmSecurityGroupRule> SGRules, RpcExtra rpcExtra)
			throws RpcException;

	/**
	 * 
	 * 取消定义 Network Filters
	 * 
	 * @param securityGroup
	 *     uuid name 不能为null
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	public void deleteNetworkFilter(String routingkey,
			VmSecurityGroup securityGroup, RpcExtra rpcExtra) throws RpcException;
	
	/**
	 * 离线迁移
	 * @param routingKey
	 * @param instance
	 * @param instanceType
	 * @param mds
	 * @param volumes
	 * @param securityGroup
	 * @param vifs
	 * @param hostUUID
	 * @param rpcExtra
	 * @throws RpcException
	 */
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 30)
	public void offlineMigrate(String routingKey, VmInstance instance,
			VmInstanceType instanceType, List<VmInstanceMetadata> mds,
			List<VmVolume> volumes, VmSecurityGroup securityGroup,
			List<VmVirtualInterface> vifs, RpcExtra rpcExtra) throws RpcException;
	
	@RoutingKeyAnnotation(index = 0)
	@RPCTimeout(timeout = 5)
	public String KeepAlive(String routingKey) throws Exception ;
}
