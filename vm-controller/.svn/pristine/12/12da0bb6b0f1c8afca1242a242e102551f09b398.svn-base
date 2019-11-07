package appcloud.vmcontroller.monitor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.libvirt.Domain;
import org.libvirt.DomainBlockStats;
import org.libvirt.DomainInterfaceStats;
import org.libvirt.LibvirtException;

import appcloud.common.model.HostLoad;
import appcloud.common.model.VmLoad;
import appcloud.vmcontroller.VMControllerServer;
import appcloud.vmcontroller.impl.VMControllerImpl;
import appcloud.vmcontroller.model.LoadConfig;
import appcloud.vmcontroller.virt.XMLHandler;


public class VMMonitor {
	private static Logger logger = Logger.getLogger(VMControllerServer.class);
	private static final long Nano_To_Mill = 1000000;
	private static final int b_To_B = 8;
	
	private static VMMonitor vmMonitor = null;
	
	/*
	 * 存虚拟机的负载信息，只存最近一次的负载信息
	 */
	private static HashMap<String, MonitorLoad> vmLoad = null;
	
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
			addDomainMonitor(uuid);
		}
	}
	
	public static VMMonitor getInstance() {
		if (vmMonitor == null) {
			/*
			 * 1. 创建vmLoad
			 */
			vmLoad = new HashMap<String, MonitorLoad>();
			
			/*
			 * 2. 初始化vmLoad
			 * 1）获取全部活动主机的UUID list
			 * 2）构造vmMonitor
			 */
			logger.info("start init vm monitor");
			
			List<String> list = vmService.getActiveDomainUUIDList();			
			logger.info("active domain uuid list: " + list);
			
			vmMonitor = new VMMonitor(list);
		}
		return vmMonitor;
	}
	
	/**
	 * 初始化
	 * 为指定domain d，构造负载信息VMMonitorLoad对象
	 * @return VMMonitorLoad
	 */
	private MonitorLoad createDomainMonitorLoad(Domain d) {
		logger.info("starting create domain monitor");
		
		/*
		 * 1. 初始创建domain monitor，其中monitorLoad的curLoad和lastLoad均为0
		 */
		MonitorLoad monitorLoad = new MonitorLoad();
		
		/*
		 * 2. 获取当前时刻的负载信息，并且赋值给monitorLoad的lastload
		 * 注意：
		 * 不能获取当前值时，处理方案：
		 * 把lastLoad的值赋给curLoad
		 */
		try {
			LoadConfig curLoad = collectCurLoad(d);
			monitorLoad.lastLoad.setLoadConfig(curLoad);
		} catch (LibvirtException e) {
			/*
			 * 不能获取当前值:
			 * 其余值均为0，getSystemTime赋初值
			 */
			monitorLoad.lastLoad.systemTime = System.currentTimeMillis();
		}
		
		//3. 初始化domain的load：设置monitorLoad中的loadResult均为0
		float initValue = 0;
		monitorLoad.setVmLoad(initValue, initValue, initValue, initValue, initValue);
		
		return monitorLoad;
	}
	
	/**
	 * 周期性调用
	 * 为domain d，构造负载信息VMMonitorLoad对象
	 * @return
	 * @throws LibvirtException 
	 */
	public void refreshMonitorLoad() {
		logger.info("[CALL refreshMonitorLoad] starting refresh monitor load");
		
		/*
		 * 值采集、值计算、值覆盖
		 * 遍历vmLoad监控数据：
		 */
		Iterator<Map.Entry<String, MonitorLoad>> iter = vmLoad.entrySet().iterator();
        
        while (iter.hasNext()) { 
        	Map.Entry<String, MonitorLoad> entry = iter.next();
        	String instanceUUID = entry.getKey();
        	MonitorLoad monitorLoad = entry.getValue();
        	
        	logger.info("calculate domain current load: " + instanceUUID);
        	
        	//首先判断域是否活着
        	Domain d = null;
    		try {
    			d = getDomain(instanceUUID);
			} catch (Exception e) {
				iter.remove();
				logger.info("can't get the domain with uuid = " + instanceUUID + ", use continue to jump into the next loop");
				continue;
			}
    		try {
			if (d.isActive() > 0) {
				/*
				 * 如果域活着：
		    	 * 1. 采集当前的值，赋值给curLoad
		    	 */
				LoadConfig curLoad = collectCurLoad(d);
				monitorLoad.curLoad.setLoadConfig(curLoad);
				
				/*
				 * 2. 计算本次间隔的值
				 */
				monitorLoad.calculateResult();
				logger.info(monitorLoad);
				
				/*
				 * 3. 用end值覆盖last值
				 */
				monitorLoad.revertLastLoad();
				
				/*
				 * 4. set entry
				 */
				entry.setValue(monitorLoad);					
			} else {
				/*
				 * 如果域当前没有活着，则删除该域的monitor数据
				 */
				iter.remove();
			}
			} catch (Exception e) {
				//当curLoad无法采集时，不需要继续计算
				logger.info("current load can not collect, still use last interval value");			
			}
        }
        
        //刷新列表
        List<String> list = vmService.getActiveDomainUUIDList();
        for (String uuid : list) {
        	/*
        	 * 如果该uuid不存在于当前的监控队列中，在当前队列中增加监控
        	 * 在deleteVM中，在load中删除该列表
        	 * 注意！
        	 * 此处需要添加反查，如果管理员手动删除，未通过调用deleteVM，则该vm会持续运行
        	 */
        	if (!vmLoad.containsKey(uuid)) {
        		addDomainMonitor(uuid);
        	}
        }
        
	}
	
	/**
	 * 添加：
	 * 添加一个新的monitor，到vmLoad中
	 * @return
	 * @throws LibvirtException 
	 */
	public void addDomainMonitor(String instanceUUID) {
		logger.info("add domain monitor: " + instanceUUID);
		
		/*
		 * 判断域是否存活，如果活着，则创建monitor load，监控该域的信息
		 */
		try {
			Domain d = getDomain(instanceUUID);
			
			if (d.isActive() > 0) {
				MonitorLoad monitorLoad = createDomainMonitorLoad(d);
				vmLoad.put(instanceUUID, monitorLoad);	
			}
		} catch (Exception e) {
			//当curLoad无法采集时，不需要继续计算
			logger.info("add domain monitor error", e);
		}
		
	}
	
	/**
	 * 删除：
	 * 删除一个新的monitor，从vmLoad中
	 * @return
	 */
	public void delDomainMonitor(String instanceUUID) {
		if (!vmLoad.containsKey(instanceUUID)) {
			vmLoad.remove(instanceUUID);
		} 
	}
	
	/**
	 * 获取：
	 * 删除一个新的monitor，从vmLoad中
	 * @return
	 */
	public HostLoad getDomainMonitor(String instanceUUID) {
		HostLoad result = new VmLoad();
		if (!vmLoad.containsKey(instanceUUID)) {
			MonitorLoad monitorLoad = vmLoad.get(instanceUUID);
			result.setCpuPercent(monitorLoad.vmLoadResult.getCpuPercent());
			result.setDiskReadRate(monitorLoad.vmLoadResult.getDiskReadRate());
			result.setDiskWriteRate(monitorLoad.vmLoadResult.getDiskWriteRate());
			result.setNetInPercent(monitorLoad.vmLoadResult.getNetInPercent());
			result.setNetOutPercent(monitorLoad.vmLoadResult.getNetOutPercent());
		} 
		return result;
	}
	
	/**
	 * 获取：
	 * 删除一个新的monitor，从vmLoad中
	 * @return
	 */
	public List<HostLoad> getDomainMonitorList() {
		List<HostLoad> result = new ArrayList<HostLoad>();
		
		//遍历vm load
        Iterator iter = vmLoad.entrySet().iterator();
        
        while (iter.hasNext()) { 
        	Map.Entry<String, MonitorLoad> entry = (Entry<String, MonitorLoad>) iter.next();
        	String instanceUUID = entry.getKey();
        	MonitorLoad monitorLoad = entry.getValue();
        	
        	//生成新的VmLoad
        	HostLoad load = new HostLoad();
        	load.setTime(new Timestamp(System.currentTimeMillis()));
        	load.setUuid(instanceUUID);
        	load.setCpuPercent(monitorLoad.vmLoadResult.getCpuPercent());
        	load.setDiskReadRate(monitorLoad.vmLoadResult.getDiskReadRate());
        	load.setDiskWriteRate(monitorLoad.vmLoadResult.getDiskWriteRate());
        	load.setNetInPercent(monitorLoad.vmLoadResult.getNetInPercent());
        	load.setNetOutPercent(monitorLoad.vmLoadResult.getNetOutPercent());
        	
        	result.add(load);
        }
		
		return result;
	}
	
	//重置
	public void resetVMMonitor() {
		
	}
	
	//根据域uuid，获取域domain对象
	private Domain getDomain(String uuid) throws Exception {
		Domain d = null;
		try {
			d = vmService.getDomainInstance(uuid);
			return d;
		} catch (Exception e) {
			logger.error("can not get domain: " + uuid, e);
			throw new Exception("can not get domain"); 
		}
	}
	
	//根据域domain对象，获取该域的系统盘的target dev属性
	private static String getSystemDiskPath(Domain d) {
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
	private static String getPublicVIFPath(Domain d) {
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
	
	//收集当前时刻的负载信息
	private static LoadConfig collectCurLoad(Domain d) throws LibvirtException {
		logger.debug("collect current load");
		
		/*
		 * 首先，需要判断该域是否正在运行！
		 * 如果正在运行，则继续下面的采集步骤，如果尚未运行，则本次暂不计算
		 */
		
		LoadConfig curLoad = new LoadConfig();
		
		//采集该domain下的数据
		/*
		 * 1）系统时间，单位微妙
		 */
		curLoad.systemTime = System.currentTimeMillis();
		logger.debug("collect current systemTime: " + curLoad.systemTime);
		
		/*
		 * 2）获取CPU时间，单位换算：纳秒转化为微妙
		 */
		try {
			curLoad.cpuTime = d.getInfo().cpuTime / VMMonitor.Nano_To_Mill;
			logger.debug("collect current CPUTime: " + curLoad.cpuTime);
		
		
			/*
			 * 3）获取磁盘读写
			 */
			String diskPath = getSystemDiskPath(d);
			DomainBlockStats blockStats = d.blockStats(diskPath);
			curLoad.diskWRBytes = blockStats.wr_bytes / VMMonitor.b_To_B;
			curLoad.diskRDBytes = blockStats.rd_bytes / VMMonitor.b_To_B;
			
			logger.debug("collect disk stats for: " + diskPath);
			logger.debug("disk stats wr: " + curLoad.diskWRBytes);
			logger.debug("disk stats rd: " + curLoad.diskRDBytes);

		
			/*
			 * 4）获取网卡读写
			 */
			String vifPath = getPublicVIFPath(d);
			if(!vifPath.endsWith("ERROR")){
				DomainInterfaceStats vifStats = d.interfaceStats(vifPath);
				curLoad.vifTXBytes = vifStats.tx_bytes / VMMonitor.b_To_B;
				curLoad.vifRXBytes = vifStats.rx_bytes / VMMonitor.b_To_B;
				
				logger.debug("collect vif stats for: " + vifPath);
				logger.debug("vif stats wr: " + curLoad.vifTXBytes);
				logger.debug("vif stats rd: " + curLoad.vifRXBytes);
			}
			
			
		} catch (LibvirtException e) {
			logger.error("collect cur load error! ");
			throw e;
		}
					
		return curLoad;	
	}
	
	
}
