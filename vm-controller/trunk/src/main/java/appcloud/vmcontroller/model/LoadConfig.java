package appcloud.vmcontroller.model;

public class LoadConfig {
	/*
	 * 本次采集点：采集时间，单位微妙
	 */
	public long systemTime;
	
	/*
	 * 本次采集点：CPU时间，单位微妙
	 */
	public long cpuTime;
	
	/*
	 * 本次采集点：磁盘写字节、读字节(B)
	 */
	public long diskWRBytes;
	public long diskRDBytes;
	
	/*
	 * 本次采集点：网卡写字节、读字节
	 */
	public long vifTXBytes;
	public long vifRXBytes;
	
	public LoadConfig() {
		systemTime = 0;
		cpuTime = 0;
		diskWRBytes = 0;
		diskRDBytes = 0;
		vifTXBytes = 0;
		vifRXBytes = 0;
	}
	
	public void setLoadConfig(LoadConfig load) {
		this.systemTime = load.systemTime;
		this.cpuTime = load.cpuTime;
		this.diskRDBytes = load.diskRDBytes;
		this.diskWRBytes = load.diskWRBytes;
		this.vifRXBytes = load.vifRXBytes;
		this.vifTXBytes = load.vifTXBytes;
	}
	
	public String toString() {
		return "LoadConfig [" +
				"systemTime=" + systemTime + " , " +
				"cpuTime=" + cpuTime + " , " +
				"diskWRBytes=" + diskWRBytes + " , " +
				"diskRDBytes=" + diskRDBytes + " , " +
				"VIFTXBytes=" + vifTXBytes + " , " +
				"VIFRXBytes=" + vifRXBytes + " ]";
	}
	
	
}
