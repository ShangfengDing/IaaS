package appcloud.vmcontroller.monitor;

import java.util.HashMap;
import java.util.List;

import org.libvirt.Domain;

import appcloud.common.model.VmLoad;
import appcloud.vmcontroller.impl.VMControllerImpl;


public class VMMonitor {
	private static VMMonitor vmMonitor = null;
	private static HashMap<Domain, VmLoad> vmLoad = null;
	private static VMControllerImpl vmService = VMControllerImpl.getInstance();
	
	private VMMonitor(List<String> list) {
		for (String uuid : list) {
			VmLoad load = new VmLoad();
			createDomainLoad(uuid);
		}
	}
	
	/**
	 * 初始化,获取当前active的域名称,然后每个域生成负载信息
	 * @return
	 */
	public static VMMonitor getInstance() {
		if (vmMonitor == null) {
			List<String> list = vmService.getActiveDomaimUUIDList();
			vmMonitor = new VMMonitor(list);
		}
		return vmMonitor;
	}
	
	private Domain createDomainLoad(String uuid) {
		Domain d = null;
		try {
			//1. 根据uuid，获取domain
			d = vmService.getActiveDomain(uuid);
			
			//2. 获取domain的信息
			VMMonitorLoad load = new VMMonitorLoad();
			load.lastCPUTime = d.getInfo().cpuTime / 1000000;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	
}
