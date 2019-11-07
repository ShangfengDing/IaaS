package appcloud.resourcescheduler.model;

public class Resource {
	private Integer cpuMhz = 0;		//cpu主频
	private Integer cpuCore = 0;	//cpu核心数
	private Integer memoryMb = 0;	//内存总大小
	private Integer diskMb = 0;		//硬盘总大小
	private Integer networkMb = 0;	//网络带宽
	
	public Integer getCpuMhz() {
		return cpuMhz;
	}
	public void setCpuMhz(Integer cpuMhz) {
		this.cpuMhz = cpuMhz;
	}
	public Integer getCpuCore() {
		return cpuCore;
	}
	public void setCpuCore(Integer cpuCore) {
		this.cpuCore = cpuCore;
	}
	public Integer getMemoryMb() {
		return memoryMb;
	}
	public void setMemoryMb(Integer memoryMb) {
		this.memoryMb = memoryMb;
	}
	public Integer getDiskMb() {
		return diskMb;
	}
	public void setDiskMb(Integer diskMb) {
		this.diskMb = diskMb;
	}
	public Integer getNetworkMb() {
		return networkMb;
	}
	public void setNetworkMb(Integer networkMb) {
		this.networkMb = networkMb;
	}
}
