package appcloud.vmcontroller.monitor;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.libvirt.Domain;
import org.libvirt.DomainBlockStats;
import org.libvirt.DomainInterfaceStats;
import org.libvirt.LibvirtException;

import appcloud.common.model.VmLoad;
import appcloud.vmcontroller.VMControllerServer;
import appcloud.vmcontroller.impl.VMControllerImpl;
import appcloud.vmcontroller.virt.XMLHandler;


public class VMMonitor {
	private static Logger logger = Logger.getLogger(VMControllerServer.class);
	
	private static VMMonitor vmMonitor = null;
	
	/*
	 * 存虚拟机的负载信息，只存最近一次的负载信息
	 */
	private static HashMap<Domain, VMMonitorLoad> vmLoad = null;
	
	/*
	 * 使用controller service，获取宿主机上的机器信息
	 */
	private static VMControllerImpl vmService = VMControllerImpl.getInstance();
	
	/**
	 * 单例
	 * 初始化,获取当前active的域名称,然后生成每个域的负载信息
	 * @return
	 */
	private VMMonitor(List<String> list) {
		for (String uuid : list) {
			/*
			 * 获取域信息，以及负载统计信息，写进vmLoad统计信息中
			 */
			Domain d = getDomain(uuid);
			VMMonitorLoad monitorLoad = createDomainMonitorLoad(d);
			vmLoad.put(d, monitorLoad);			
		}
	}
	
	public static VMMonitor getInstance() {
		if (vmMonitor == null) {
			//获取全部活动主机的UUID list
			List<String> list = vmService.getActiveDomaimUUIDList();
			vmMonitor = new VMMonitor(list);
		}
		return vmMonitor;
	}
	
	//重置
	public void resetVMMonitor() {
		
	}
	
	//根据域uuid，获取域domain对象
	private Domain getDomain(String uuid) {
		Domain d = null;
		try {
			d = vmService.getActiveDomain(uuid);
		} catch (Exception e) {
			logger.error("can not get domain: " + uuid);
		}
		return d;
	}
	
	//根据域domain对象，获取该域的系统盘的target dev属性
	private String getSystemDiskPath(Domain d) {
		String devPath = null;
		try {
			//
			String xml = d.getXMLDesc(0);
			XMLHandler xmlHandler = new XMLHandler(xml);
			devPath = xmlHandler.getSystemDiskTarget();
		} catch (LibvirtException e) {
			logger.error("get xml des error");
		}
		
		return devPath;
	}
	
	//根据域domain对象，获取该域的公网网卡的interface块的 target dev属性： 如vnet xx
	private String getPublicVIFPath(Domain d) {
		String devPath = null;
		try {
			//
			String xml = d.getXMLDesc(0);
			XMLHandler xmlHandler = new XMLHandler(xml);
			devPath = xmlHandler.getPublicVIFTarget();
		} catch (LibvirtException e) {
			logger.error("get xml des error");
		}
		
		return devPath;
	}
	
	private void setVmLoad(VMMonitorLoad monitorLoad, 
						   Float cpuPercent, 
						   Float diskReadRate,
						   Float diskWriteRate,
						   Float netInPercent,
						   Float netOutPercent) {
		monitorLoad.vmLoad.setCpuPercent(cpuPercent);
		monitorLoad.vmLoad.setDiskReadRate(diskReadRate);
		monitorLoad.vmLoad.setDiskWriteRate(diskWriteRate);
		monitorLoad.vmLoad.setNetInPercent(netInPercent);
		monitorLoad.vmLoad.setNetOutPercent(netOutPercent);
		
	}
	
	private VMMonitorLoad createDomainMonitorLoad(Domain d) {
		VMMonitorLoad monitorLoad = null;
		try {
			/*
			 * step1: 获取domain的信息
			 * 1）系统时间，单位微妙
			 */
			monitorLoad = new VMMonitorLoad();
			monitorLoad.lastGetSystemTime = System.currentTimeMillis();
			
			/*
			 * 2）获取CPU时间，单位换算：纳秒转化为微妙
			 */
			monitorLoad.lastCPUTime = d.getInfo().cpuTime / 1000;
			
			/*
			 * 3）获取磁盘读写
			 */
			String diskPath = getSystemDiskPath(d);
			DomainBlockStats blockStats = d.blockStats(diskPath);
			monitorLoad.lastDiskWRBytes = blockStats.wr_bytes;
			monitorLoad.lastDiskRDBytes = blockStats.rd_bytes;
			
			/*
			 * 4）获取网卡读写
			 */
			String vifPath = getPublicVIFPath(d);
			DomainInterfaceStats vifStats = d.interfaceStats(vifPath);
			monitorLoad.lastVIFTXBytes = vifStats.tx_bytes;
			monitorLoad.lastVIFRXBytes = vifStats.rx_bytes;
			
			/*
			 * step2: 计算domain的load
			 * 1）设置初始值均为0
			 */
			float initValue = 0;
			setVmLoad(monitorLoad, initValue, initValue, initValue, initValue, initValue);
			
		} catch (Exception e) {
			logger.error("get load error!");
			e.printStackTrace();
		}
		
		return monitorLoad;
	}
	
	public void refreshMonitorLoad() {
		//计算阶段
		
		//值覆盖阶段
	}
	
}
