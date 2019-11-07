package appcloud.vmcontroller.impl;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.util.io.CommandHelper;
import appcloud.common.util.io.CommandResult;
import appcloud.vmcontroller.model.NetworkConfig;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.DomainInfo.DomainState;
import org.libvirt.LibvirtException;
import org.libvirt.NetworkFilter;

import appcloud.common.model.MessageLog;
import appcloud.common.model.HostLoad;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.service.VMControllerService;
import appcloud.common.util.LolLogUtil;
import appcloud.rpc.tools.RpcException;
import appcloud.vmcontroller.VMControllerConf;
import appcloud.vmcontroller.VMControllerServer;
import appcloud.vmcontroller.monitor.VMMonitor;
import appcloud.vmcontroller.util.VMCUtil;
import appcloud.vmcontroller.virt.LibvirtConfig;
import appcloud.vmcontroller.virt.NetworkFilterConfig;
import appcloud.vmcontroller.virt.XMLHandler;

public class VMControllerImpl implements VMControllerService {
	private static VMControllerImpl service;
	private static Connect virtCon;

	private static final Logger logger = Logger.getLogger(VMControllerImpl.class);
	
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = VMControllerServer.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.VM_CONTROLLER, VMControllerImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}

	private VMControllerImpl() {
		if (virtCon == null) {
			String conStr = VMControllerConf.VIR_CON_STR;
			try {
				virtCon = new Connect(conStr);
			} catch (LibvirtException e) {
				logger.error("get virt connection error", e);
				logger.error(e.getMessage());
			}
		}
	}

	public static VMControllerImpl getInstance() {
		if (service == null) {
			service = new VMControllerImpl();
			logger.info("service init successful");
		}
		return service;
	}

	@Override
	public void createVM(String routingKey, VmInstance instance,
			VmInstanceType instanceType, List<VmInstanceMetadata> mds,
			List<VmVolume> volumes, VmSecurityGroup securityGroup,
			List<VmVirtualInterface> vifs, RpcExtra rpcExtra) throws RpcException {
		if (VMCUtil.isEmpty(mds)) {
			logger.warn("no vm metadata ");
			loller.warn(LolLogUtil.CREATE_VM, "no vm metadata ", rpcExtra);
		}
		logger.info("the routingKey is " + routingKey);
		logger.info("starting create VM: " + instance.getUuid());

		try {
			ErrorHandler.checkParam(instance, instanceType, mds, volumes, securityGroup, vifs);
			
			// 初始化LibvirtConfig
			LibvirtConfig domainXml = new LibvirtConfig(instance, instanceType);
 
			// 获取libvirt启动vm的配置文件
			String xml = domainXml.generateDesXML(mds, volumes, securityGroup, vifs);

			logger.info("CREATE: domian xml: " + xml);

			Domain d = virtCon.domainDefineXML(xml);

			int rs = d.create();
			if (rs==0) {
				logger.info("create  domian successful!");
				loller.info(LolLogUtil.CREATE_VM, "create domain successful! domain:" + d, rpcExtra);
				for (NetworkConfig networkConfig: domainXml.networkConfigList) {
                    logger.info("network mac:"+networkConfig.getNetworkMac());
                    if (networkConfig.getNetworkType().equals("private")) {
						defineBand(networkConfig.getNetworkMac());
						logger.info("limit the network successful!");
					}
				}
			} else {
				logger.info("create  domian failed!");
				loller.info(LolLogUtil.CREATE_VM, "create domain failed! domain:" + d, rpcExtra);
			}
		} catch (Exception e) {
			/*
			 * TODO：增加error handler，统一异常处理
			 */
			String why = "create error: " + instance.getUuid();
			logger.error(why);
			loller.error(LolLogUtil.CREATE_VM, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	@Override
	public void startVM(String routingKey, VmInstance instance,
			VmInstanceType instanceType, List<VmInstanceMetadata> mds,
			List<VmVolume> volumes, VmSecurityGroup securityGroup,
			List<VmVirtualInterface> vifs, RpcExtra rpcExtra) throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting start VM: " + instance.getUuid());

		try {
			ErrorHandler.checkParam(instance, instanceType, mds, volumes, securityGroup, vifs);
			Domain d = getDomainInstance(instance.getUuid());
			LibvirtConfig domainXml = new LibvirtConfig(instance, instanceType);
			String xml = domainXml.generateDesXML(mds, volumes, securityGroup, vifs);
			logger.info("CREATE: uuid: " + domainXml.vmInstance.getUuid());
			logger.info("CREATE: domian xml: " + xml);
			effectDomain(d, xml);
			logger.info("start domian successful!");
			loller.info(LolLogUtil.START_VM, "start domain successful, domain:" + d, rpcExtra);

			for (NetworkConfig networkConfig: domainXml.networkConfigList) {
			    logger.info("network mac:"+networkConfig.getNetworkMac());
				if (networkConfig.getNetworkType().equals("private")) {
					defineBand(networkConfig.getNetworkMac());
					logger.info("limit the network successful!");
				}
			}
		} catch (Exception e) {
			String why = "start domian error!";
			logger.info(why, e);
			loller.error(LolLogUtil.START_VM, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	@Override
	public void deleteVM(String routingKey, String instanceUUID, RpcExtra rpcExtra) throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting delete VM: " + instanceUUID);
		try {
			ErrorHandler.checkParam(instanceUUID);
		} catch (Exception e) {
			throw new RpcException(e);
		}
		try {
			Domain d = getDomainInstance(instanceUUID);
			destroyDomain(d);
			d.undefine();
			logger.info("delete domian successful!");
			// 删除该节点的监控
			VMMonitor.getInstance().delDomainMonitor(instanceUUID);
			logger.info("delete domian monitor!");
			loller.info(LolLogUtil.DELETE_VM, "delete domain successful, domain:" + d, rpcExtra);
		} catch (Exception e) {
			String why = "delete domian error!";
			logger.error(why, e);
			loller.error(LolLogUtil.DELETE_VM, why+e.getMessage(), rpcExtra);
		}
	}

	/**
	 * 停止虚拟机 shut down
	 * 
	 * @param instanceUUID
	 * @throws Exception
	 */
	@Override
	public void stopVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting stop VM: " + instanceUUID);

		try {
			ErrorHandler.checkParam(instanceUUID);
			Domain d = getDomainInstance(instanceUUID);
			/*
			 * stop VM：优雅关闭 
			 */
			shutdownDomain(d);
			logger.info("stop domian successful!");
			loller.info(LolLogUtil.STOP_VM, "stop domain successful! domain:" + d, rpcExtra);
		} catch (Exception e) {
			String why = "stop domain error!";
			logger.error(why, e);
			loller.error(LolLogUtil.STOP_VM, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	/**
	 * 强制停止虚拟机：强制关机(调用destroy)，但是不同于delete(不会调用undefine)
	 */
	@Override
	public void forceStopVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting force stop VM: " + instanceUUID);

		try {
			ErrorHandler.checkParam(instanceUUID);
			Domain d = getDomainInstance(instanceUUID);
			destroyDomain(d);
			logger.info("force stop domian successful!");
			loller.info(LolLogUtil.FORCE_STOP_VM, "force stop domain successful, domain:"+d, rpcExtra);
		} catch (Exception e) {
			String why = "force stop domian error!";
			logger.error(why, e);
			loller.error(LolLogUtil.FORCE_STOP_VM, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	@Override
	public void rebootVM(String routingKey, String instanceUUID, List<VmVirtualInterface> vifs, RpcExtra rpcExtra)
			throws RpcException {
		// 注意：reboot 目前尚不支持：Reboot is not supported without the JSON monitor
		logger.info("the routingKey is " + routingKey);
		logger.info("starting reboot VM: " + instanceUUID);

		try {
			ErrorHandler.checkParam(instanceUUID);
			Domain d = getDomainInstance(instanceUUID);
			//暂不可行 reboot
			 
//			 d.reboot(0);

			/*
			 * 方案：先shutdown，再start
			 */
			logger.info("start shutdown domain");
			shutdownDomain(d);
			logger.info("start create domain");
			int rs = d.create();

			if (rs==0) {
				logger.info("reboot  domian successful!");
				loller.info(LolLogUtil.REBOOT_DOMAIN, "reboot domain successful! domain:" + d, rpcExtra);
				for (VmVirtualInterface virtualInterface: vifs) {
                    logger.info("network mac:"+virtualInterface.getMac());
                    if (virtualInterface.getNetworkType().equals("private")) {
						defineBand(virtualInterface.getMac());
						logger.info("limit the network successful!");
					}
				}
			} else {
				logger.info("create  domian failed!");
				loller.info(LolLogUtil.REBOOT_DOMAIN, "reboot domain failed! domain:" + d, rpcExtra);
			}
		} catch (Exception e) {
			String why = "reboot domian error!";
			logger.error(why, e);
			loller.error(LolLogUtil.REBOOT_DOMAIN, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	@Override
	public void suspendVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting suspend VM: " + instanceUUID);

		try {
			ErrorHandler.checkParam(instanceUUID);
			Domain d = getDomainInstance(instanceUUID);
			d.suspend();
			logger.info("suspend domian successful!");
			loller.info(LolLogUtil.SUSPEND_VM, "suspend domain successful, domain:"+d, rpcExtra);
		} catch (Exception e) {
			String why = "suspend domian error!";
			logger.error(why, e);
			loller.error(LolLogUtil.SUSPEND_VM, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	@Override
	public void resumeVM(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting resume VM: " + instanceUUID);

		try {
			ErrorHandler.checkParam(instanceUUID);
			Domain d = getDomainInstance(instanceUUID);
			d.resume();
			logger.info("resume domain successful!");
			loller.info(LolLogUtil.RESUME_VM, "resume domain successful, domain:"+d, rpcExtra);
		} catch (Exception e) {
			String why = "resume domian error!";
			logger.error(why, e);
			loller.error(LolLogUtil.RESUME_VM, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	@Override
	public void resizeVM(String routingKey, String instanceUUID,
			VmInstanceType instanceType, VmInstanceMetadata instanceMetadata, List<VmVirtualInterface> vifs,VmSecurityGroup securityGroup, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting resize VM: " + instanceUUID);
		logger.info("instanceType: " + instanceType);
		logger.info("instanceMetadata: " + instanceMetadata);
		for(VmVirtualInterface vif : vifs){
		    logger.info(vif.getAddress());
		}

		try {
			ErrorHandler.checkParam(instanceUUID);
			Domain d = getDomainInstance(instanceUUID);
			
			XMLHandler xmlService = new XMLHandler(d.getXMLDesc(0));
			String desXml = xmlService.resizeMemory(instanceType.getMemoryMb());
			desXml = xmlService.resizeVcpus(instanceType.getVcpus());
			
			VmVirtualInterface vif = VMCUtil.getPublicVif(vifs);
			
			desXml = xmlService.resizeInterface(vif, securityGroup, instanceMetadata.getValue());
			
			if (instanceMetadata != null) {
			    logger.debug("instanceMetadata = " + instanceMetadata.getValue()); 
			    
				desXml = xmlService.resizeBandwidth(instanceMetadata.getValue());
			}
			// 重置虚拟机
			effectDomainWithOutStart(d, desXml);
			logger.info("resize domian successful!");
			logger.info("desXml" + desXml);
			loller.info(LolLogUtil.RESIZE_VM, "resize domain successful! domain:"+d, rpcExtra);
		} catch (Exception e) {
			String why = "rsize domain error!";
			logger.info(why);
			loller.info(LolLogUtil.RESIZE_VM, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}

	}

	/**
	 * 获取虚拟机的 vnc port
	 * 
	 * @param instanceUUID
	 * @return port的字符串形式，如 5001,查询不到返回null
	 * @throws Exception
	 */
	@Override
	public String getVncPort(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting get vnc port with instanceUUID = " + instanceUUID);
		
		try {
			ErrorHandler.checkParam(instanceUUID);
			Domain d = getDomainInstance(instanceUUID);

			/*
			 * 获取该domain的xml描述信息，并且将instance type中的描述修改为新的配置
			 * TODO：目前暂时使用字符串匹配，必要可以修改为dom4j
			 */
			XMLHandler xmlService = new XMLHandler(d.getXMLDesc(0));
			String vncPort = xmlService.getVNCPortXML();
			logger.info("get vnc port: " + vncPort);
			loller.info(LolLogUtil.GET_VNC_PORT, "get vnc port:" +  vncPort, rpcExtra);
			return vncPort;
		} catch (Exception e) {
			String why = "get vnc port error!";
			logger.error(why, e);
			loller.info(LolLogUtil.GET_VNC_PORT, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	@Override
	public VmStatusEnum getDomainState(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting get DomainState: " + instanceUUID);
		try {
			ErrorHandler.checkParam(instanceUUID);
			Domain d = getDomainInstance(instanceUUID);
			DomainState state = d.getInfo().state;
			logger.info("the domain state is " + state.toString());
			loller.info(LolLogUtil.GET_DOMAIN_STATE, "the domain state is " + state, rpcExtra);
			return ConvertDomainStateToVmStatus(state);
		} catch (Exception e) {
			String why = "get DomainState error" + instanceUUID;
			logger.error(why);
			logger.info("set vm Status to deleted");
			loller.error(LolLogUtil.GET_DOMAIN_STATE, why+e.getMessage(), rpcExtra);
			//TODO why?
			return VmStatusEnum.DELETED; 
		}
	}

	/**
	 * 收集指定虚拟机的负载信息
	 */
	@Override
	public HostLoad collectSpecificVMLoad(String routingKey, String instanceUUID, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		logger.info("starting collect VMLoad: " + instanceUUID);
		try{
			ErrorHandler.checkParam(instanceUUID);
			HostLoad load = VMMonitor.getInstance().getDomainMonitor(instanceUUID);
			return load;
		}catch(Exception e){
			String why = "collect VMLoad error" + instanceUUID;
			logger.error(why);
			throw new RpcException(why, e);
		}
	}

	/**
	 * 收集本机的全部虚拟机的负载信息
	 */
	@Override
	public List<HostLoad> collectVMLoadList(String routingKey)
			throws RpcException {
		logger.info("the routingKey is " + routingKey);
		try{
			List<HostLoad> result = VMMonitor.getInstance().getDomainMonitorList();
			return result;
		}catch(Exception e){
			String why = "collect VMLoadList error";
			logger.error(why);
			throw new RpcException(why, e);
		}
	}

	@Override
	public void defineNetworkFilter(String routingkey,
			VmSecurityGroup securityGroup, List<VmSecurityGroupRule> SGRules, RpcExtra rpcExtra)
			throws RpcException {
		logger.info("starting define NwFilter: " + securityGroup.getName());
		logger.info("security group rules: " + SGRules);
		try {
			ErrorHandler.checkParam(securityGroup);
			NetworkFilterConfig filterXml = new NetworkFilterConfig(
					securityGroup);

			// 2，xml
			String desXml = filterXml.generateFilterConf(SGRules);
			logger.info("define NwFilter: " + desXml);

			/*
			 * 3. create 1）有可能是修改正在应用的filter
			 */
			effectNetworkFilter(securityGroup, desXml);

			logger.info("define NwFilter successful: " + filterXml.getName());
			loller.info(LolLogUtil.DEFINE_NETWORK_FILTER, "define NwFilter successful:" + filterXml.getName(), rpcExtra);

		} catch (Exception e) {
			String why = "define NwFilter error!";
			logger.error(why);
			loller.error(LolLogUtil.DEFINE_NETWORK_FILTER, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	/**
	 * 取消定义 Network Filters TODO:
	 * 
	 * @param securityGroupName
	 *            防火墙规则组，使用是其name
	 * @throws Exception
	 */
	@Override
	public void deleteNetworkFilter(String routingkey,
			VmSecurityGroup securityGroup, RpcExtra rpcExtra) throws RpcException {
		logger.info("starting delete NwFilter: " + securityGroup.getName());

		try {

			NetworkFilter filter = getNetworkFilter(securityGroup);

			// undefine
			filter.undefine();
			loller.info(LolLogUtil.DELETE_NETWORK_FILTER, "delete network filter successfully! VmSecurityGroup:" + securityGroup, rpcExtra);

		} catch (Exception e) {
			String why = "del NetworkFilter error" + securityGroup.getName();
			logger.error(why);
			loller.error(LolLogUtil.DEFINE_NETWORK_FILTER, why+e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}

	@Override
	public void offlineMigrate(String routingKey, VmInstance instance,
			VmInstanceType instanceType, List<VmInstanceMetadata> mds,
			List<VmVolume> volumes, VmSecurityGroup securityGroup,
			List<VmVirtualInterface> vifs, RpcExtra rpcExtra) throws RpcException {
		logger.info("starting offline migrate：" + instance);

		try {
			ErrorHandler.checkParam(instance, instanceType, mds, volumes,
					securityGroup, vifs);

			LibvirtConfig domainXml = new LibvirtConfig(instance, instanceType);
			String xml = domainXml.generateDesXML(mds, volumes, securityGroup, vifs);
			logger.info("OFFLINE_MIGRATION: domian xml: " + xml);
			virtCon.domainDefineXML(xml);
			String why = "offline migration sucess!!!";
			logger.info(why);
			loller.info(LolLogUtil.OFFLINE_MIGRATION, why, rpcExtra);
		} catch (Exception e) {
			String why = "create error: " + instance.getUuid();
			logger.error(why);
			loller.error(LolLogUtil.OFFLINE_MIGRATION, why + e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}
	
	// --------------------------非接口定义的方法--------------------------------

	/**
	 * 获取所有活动主机的UUID
	 * 
	 * @return instance UUID list note: uuid是转换后的格式，不加-
	 */
	public List<String> getActiveDomainUUIDList() {
		List<String> domainList = new ArrayList<String>();
		try {
			for (int id : virtCon.listDomains()) {
				Domain d = virtCon.domainLookupByID(id);

				// 统一uuid格式，去除中间的-
				String instanceUUID = VMCUtil.convertUUID(d.getUUIDString());
				domainList.add(instanceUUID);
			}
		} catch (LibvirtException e) {
			e.printStackTrace();
		}
		return domainList;
	}

	/**
	 * 根据UUID，获取虚拟机domain实例
	 * 
	 * @param instanceUUID
	 * @return
	 * @throws Exception
	 */
	public Domain getDomainInstance(String instanceUUID) throws Exception {
		Domain d = null;

		if (instanceUUID == null) {
			throw new Exception("vm uuid is empty");
		}

		d = virtCon.domainLookupByUUIDString(instanceUUID);
		if (d == null) {
			throw new Exception("no domain with uuid: " + instanceUUID);
		}

		return d;
	}
	

	/**
	 * 关闭以前的虚拟机，开启新虚拟机
	 */
	private void effectDomain(Domain d, String desXml) throws Exception {
		logger.info("RESET: starting effect domain");

		destroyDomain(d);
		String oldDesXml = d.getXMLDesc(0);

		d.undefine();
		logger.debug("RESET: undefine old domain");

		try {
			Domain newDomain = virtCon.domainDefineXML(desXml);
			logger.debug("RESET: define new domain");
			newDomain.create();
		} catch (Exception e) {
			logger.info("RESET DOMAIN　ERROR, START THE　OLD　DOMAIN!!!");
			virtCon.domainDefineXML(oldDesXml).create();
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		
		logger.info("effect domian successful!");
	}
	
	
	/**
	 * 关闭以前的虚拟机，创建新虚拟机，但是不开机
	 */
	private void effectDomainWithOutStart(Domain d, String desXml) throws Exception {
		logger.info("RESET: starting effect domain");

		destroyDomain(d);
		String oldDesXml = d.getXMLDesc(0);

		d.undefine();
		logger.debug("RESET: undefine old domain");

		try {
			logger.debug("RESET: define new domain");
			virtCon.domainDefineXML(desXml);
		} catch (Exception e) {
			logger.info("RESET DOMAIN　ERROR, START THE　OLD　DOMAIN!!!");
			virtCon.domainDefineXML(oldDesXml).create();
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		
		logger.info("effect domian successful!");
	}
	

	private void shutdownDomain(Domain d) throws Exception {
		logger.info("start shutdown vm");
		if (d.isActive() > 0) {
			d.shutdown();
		}

		int waitLoop = 20;
		while (d.isActive() > 0 && waitLoop > 0) {
			waitLoop--;
			logger.debug("waitLoop = " + waitLoop);
			Thread.sleep(1000);
		}
		if (d.isActive() > 0) {
			logger.info("after 20 loops ,the vm is still alive, call destory funs");
			destroyDomain(d);
		}
		logger.debug("d.getInfo().state = " + d.getInfo().state);
	}

	private void destroyDomain(Domain d) throws Exception {
		logger.info("start destroy vm");

		if (d.isActive() > 0) {
			d.destroy();
		}

		int waitLoop = 5;
		while (d.isActive() > 0 && waitLoop > 0) {
			waitLoop--;
			Thread.sleep(1000);
		}

		if (d.isActive() > 0) {
			throw new Exception("destroy error!");
		}
	}

	/**
	 * 获取防火墙
	 * 
	 * @param securityGroupName
	 * @return
	 * @throws Exception
	 */
	private NetworkFilter getNetworkFilter(VmSecurityGroup securityGroup) {
		NetworkFilter filter = null;

		try {
			/*
			 * generate name
			 */
			String name = VMCUtil.generateName(securityGroup.getUuid());
			filter = virtCon.networkFilterLookupByName(name);

		} catch (LibvirtException e) {
			String why = "get error or NetworkFilter not exist: "
					+ securityGroup.getName();
			logger.info(why, e);
		}

		return filter;
	}

	/**
	 * 使防火墙生效
	 * 
	 * @param securityGroupName
	 * @param desXml
	 * @throws Exception
	 */
	private void effectNetworkFilter(VmSecurityGroup securityGroup,
			String desXml) throws Exception {
		logger.info("RESET: starting effect network filter");

		try {
			// define network filter
			virtCon.networkFilterDefineXML(desXml);
			logger.info("effect network filter successful!");

		} catch (LibvirtException e) {
			String why = "effect network filter error: "
					+ securityGroup.getName();
			logger.error(why);
			throw new Exception(why, e);
		}
	}

	private VmStatusEnum ConvertDomainStateToVmStatus(DomainState state) {
		VmStatusEnum status = null;
		switch (state) {
		case VIR_DOMAIN_RUNNING:
		case VIR_DOMAIN_BLOCKED:
			status = VmStatusEnum.ACTIVE;
			break;
		case VIR_DOMAIN_PAUSED:
			status = VmStatusEnum.SUSPENDED;
			break;
		case VIR_DOMAIN_SHUTOFF:
		case VIR_DOMAIN_SHUTDOWN:
			status = VmStatusEnum.STOPPED;
			break;
		case VIR_DOMAIN_CRASHED:
		case VIR_DOMAIN_NOSTATE:
			status = VmStatusEnum.ERROR;
			break;
		}
		logger.info("the vm status is " + status);
		return status;
	}

	// --------------------------待删除的方法--------------------------------

	public void getBlockStats(String uuid, String path) throws Exception {
		Domain d = getDomainInstance(uuid);
		logger.info(d.blockStats(path).errs);
		logger.info(d.blockStats(path).rd_bytes);
		logger.info(d.blockStats(path).rd_req);
		logger.info(d.blockStats(path).wr_bytes);
		logger.info(d.blockStats(path).wr_req);
		
	}

	public void save(String vmUuid, String path) throws Exception {
		long before = System.currentTimeMillis();
		Domain d = getDomainInstance(vmUuid);
		//使用d.save() domain 必须 是running状态
		d.save(path);
		long after = System.currentTimeMillis();
		logger.info("wait time = " + (after-before));
		
	}

	public void domainRestore(String path) throws LibvirtException {
		long before = System.currentTimeMillis();
		virtCon.restore(path);
		long after = System.currentTimeMillis();
		logger.info("wait time = " + (after-before));
	}
	
	public void doSnapshot(String string, Document doc) throws Exception {
		long before = System.currentTimeMillis();
		Domain d = getDomainInstance(string);
		long after = System.currentTimeMillis();
		logger.info("wait time = " + (after-before));
		logger.info(d.hasCurrentSnapshot());
		logger.info(d.snapshotCurrent());
	}

	public int migrateVM(String routingKey, String instanceUUID, String destURI, String instanceName, long flags, RpcExtra rpcExtra) 
			throws RpcException{
		logger.info(routingKey+" start migrate "+ instanceUUID +" to "+ destURI +
				", instanceName="+ instanceName + ", flags=" + flags);
		if(instanceUUID == null || destURI == null) {
			logger.error("params is null");
			return -1;
		}
		logger.info("migration vm="+instanceUUID+" to destURI="+destURI);
		Domain d;
		try {
			d = getDomainInstance(instanceUUID);
			if(d == null) {
				logger.error("no domain uuid="+instanceUUID);
				return -1;
			}
			VmStatusEnum status = ConvertDomainStateToVmStatus(d.getInfo().state);
			if(!status.equals(VmStatusEnum.ACTIVE)) {
				logger.error("domain="+instanceUUID +" status is not active");
				return -1;
			}
			/**
			 * 参考
			 * http://www.libvirt.org/html/libvirt-libvirt-domain.html#virDomainMigrateFlags
			 * http://www.libvirt.org/html/libvirt-libvirt-domain.html#virDomainMigrateToURI
			 * 
			 * 在迁移前需要确保目标宿主机和源宿主机之前可以免密钥登录（就是ssh登录时不用输入密码）
			 * @param destURI 目标宿主机地址 如：qemu+ssh://192.168.1.14/system
			 * @param flags 迁移过程的一些参数
			 * 	1 << 1 : 表示使用点对点的在线迁移，使用其他的在线迁移会报错
			 *  1 << 3 : 表示在目标宿主机上持久化这个VM
			 *  1 << 4 : 表示在源宿主机上undefine这个VM
			 *  1 << 9 : 表示忽略unsafe提醒
			 *  以上这些flags的组合结果是538，所以flags=538
			 *  1000011011
			 */
			int result = d.migrateToURI(destURI, flags, instanceName, 10);
			if(result != 0){
				logger.error("!!!!! instanceName = " + instanceName + " , onlineMigrate failed ! ");
			}else {
				logger.info("!!!!! instanceName = " + instanceName + " , onlineMigrate success ! ");
			}
			return result;
		} catch (Exception e) {
			String why = "onlineMirgate error: " + instanceUUID;
			logger.error(why);
			loller.error(LolLogUtil.ONLINE_MIGRATION, why + e.getMessage(), rpcExtra);
			throw new RpcException(why, e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		VMControllerImpl vmc = new VMControllerImpl();
		vmc.migrateVM("123", args[0], args[1], args[2], Long.valueOf(args[3]), new RpcExtra());
	}

	@Override
	public String KeepAlive(String routingKey) throws Exception {
		
		logger.info(String.format("---------------------keepalive-------------------"));
		logger.info(String.format("---------------------"+routingKey+":success-------------------"));
		
		return "success";
	}

	public CommandResult defineBand(String mac) throws RpcException {
		try {
			String ovsInterface = "v_"+mac.replace(":","");
			String command = "ovs-vsctl set interface "+ ovsInterface + " ingress_policing_rate=1000" +
					"&& ovs-vsctl set interface " + ovsInterface + " ingress_policing_burst=512";
			CommandHelper commandHelper = new CommandHelper();
			CommandResult result = commandHelper.exec(command);
			return result;
		} catch (Exception e) {
			String why = "volume-scheduler define network by ovs failed";
			logger.error(why);
			loller.error(LolLogUtil.DEFINE_NETWORK_FILTER, why);
			throw new RpcException(why);
		}
	}
}
