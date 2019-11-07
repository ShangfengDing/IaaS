package appcloud.vmcontroller.monitor;

import org.apache.log4j.Logger;

import appcloud.common.model.HostLoad;
import appcloud.vmcontroller.model.LoadConfig;

public class MonitorLoad {
	private static final int PERCENT = 100;

	private static final int Mill_TO_SEC = 1000;

	private static Logger logger = Logger.getLogger(MonitorLoad.class);
	
	/*
	 * 结算负载
	 */
	public HostLoad vmLoadResult;
	
	/*
	 * 上次采集点的负载
	 */
	public LoadConfig lastLoad;
	
	/*
	 * 本次采集点的负载
	 */
	public LoadConfig curLoad;
	
	
	//构造
	public MonitorLoad() {
		vmLoadResult = new HostLoad();
		lastLoad = new LoadConfig();
		curLoad = new LoadConfig();
	}
	
	public void setVmLoad(Float cpuPercent, 
			   			  Float diskReadRate,
			   			  Float diskWriteRate,
			   			  Float netInPercent,
			   			  Float netOutPercent) {
		
		vmLoadResult.setCpuPercent(cpuPercent);
		vmLoadResult.setDiskReadRate(diskReadRate);
		vmLoadResult.setDiskWriteRate(diskWriteRate);
		vmLoadResult.setNetInPercent(netInPercent);
		vmLoadResult.setNetOutPercent(netOutPercent); 
	}
	
	public void calculateResult() {
		float interval = (float) (curLoad.systemTime - lastLoad.systemTime);
		logger.info("calculate result, interval: " + interval);
		
		/*
		 * 1）计算CPU时间，单位换算：微妙
		 */
		Float cpuPercent = (float)(curLoad.cpuTime - lastLoad.cpuTime) / interval * MonitorLoad.PERCENT; 
		vmLoadResult.setCpuPercent(cpuPercent);			
		logger.info("calculate result, cpuPercent: " + vmLoadResult.getCpuPercent());
		
		/*
		 * 2）计算磁盘读
		 */
		Float diskReadRateInByte = (float)(curLoad.diskRDBytes - lastLoad.diskRDBytes) / ( interval  / MonitorLoad.Mill_TO_SEC);
		vmLoadResult.setDiskReadRate(diskReadRateInByte);	
		logger.info("calculate result, diskReadRate: " + vmLoadResult.getDiskReadRate());
		
		/*
		 * 3）计算磁盘写
		 */
		Float diskWriteRateInByte = (float)(curLoad.diskWRBytes - lastLoad.diskWRBytes)  / ( interval  / MonitorLoad.Mill_TO_SEC) ;
		vmLoadResult.setDiskWriteRate(diskWriteRateInByte);	
		logger.info("calculate result, diskWriteRate: " + vmLoadResult.getDiskWriteRate());
		
		/*
		 * 4）计算网卡读
		 */
		Float netInPercentInByte = (float)(curLoad.vifRXBytes - lastLoad.vifRXBytes) / ( interval  / MonitorLoad.Mill_TO_SEC);
		vmLoadResult.setNetInPercent(netInPercentInByte);	
		logger.info("calculate result, netInPercent: " + vmLoadResult.getNetInPercent());
		
		/*
		 * 5）计算网卡写
		 */
		Float netOutPercentInByte = (float)(curLoad.vifTXBytes - lastLoad.vifTXBytes) / ( interval  / MonitorLoad.Mill_TO_SEC);
		vmLoadResult.setNetOutPercent(netOutPercentInByte);	
		logger.info("calculate result, netOutPercent: " + vmLoadResult.getNetOutPercent());
		
	}
	
	//将当前采集点的数据，赋值给上次采集点
	public void revertLastLoad() {
		lastLoad.systemTime = curLoad.systemTime;
		lastLoad.cpuTime = curLoad.cpuTime;
		lastLoad.diskRDBytes = curLoad.diskRDBytes;
		lastLoad.diskWRBytes = curLoad.diskWRBytes;	
		lastLoad.vifRXBytes = curLoad.vifRXBytes;
		lastLoad.vifTXBytes = curLoad.vifTXBytes;
	}
	
	public String toString() {
		return "MonitorLoad " +
				"[vmLoadResult=" + vmLoadResult + " ], " +
				"[lastLoad=" + lastLoad + " ], " +
				"[curLoad=" + curLoad + " ], ";
	}
	
}
