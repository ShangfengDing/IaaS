package appcloud.vmcontroller.monitor;

import appcloud.common.model.VmLoad;

public class VMMonitorLoad {
	/*
	 * 结算负载
	 */
	VmLoad vmLoad;
	
	/*
	 * 上次采集时的CPU时间，单位微妙
	 */
	public long lastCPUTime;
	
	public long lastGetSystemTime;
	
	/*
	 * 上次采集时的磁盘写字节、读字节
	 */
	public long lastDiskWRBytes;
	public long lastDiskRDBytes;
	
	/*
	 * 上次采集时的网卡写字节、读字节
	 */
	public long lastVIFTXBytes;
	public long lastVIFRXBytes;
	
	//构造
	public VMMonitorLoad() {
		vmLoad = new VmLoad();
	}
		 
	
}
