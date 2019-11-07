package appcloud.nodeagent.info;

import java.sql.Timestamp;

import appcloud.common.model.HostLoad;
import appcloud.nodeagent.util.SystemUtils;
import appcloud.nodeagent.util.TextHelper;

public class LoadSummary {

    private String uuid;
    private double cpuPercent;
    private double load;
    private double memPercent;
    private double diskPercent;
    private double netInPercent;
    private double netOutPercent;
    private double diskReadRate;
    private double diskWriteRate;
    
    private int count;
    
    public LoadSummary() {
	}

    public LoadSummary(String uuid) {
    	this.uuid = uuid;
    	cpuPercent = 0;
	}
    
	public LoadSummary(String uuid, double cpuPercent, double load,
			double memPercent, double diskPercent, double netInPercent,
			double netOutPercent, double diskReadRate, double diskWriteRate,
			int count) {
		super();
		this.uuid = uuid;
		this.cpuPercent = cpuPercent;
		this.load = load;
		this.memPercent = memPercent;
		this.diskPercent = diskPercent;
		this.netInPercent = netInPercent;
		this.netOutPercent = netOutPercent;
		this.diskReadRate = diskReadRate;
		this.diskWriteRate = diskWriteRate;
		this.count = count;
	}

	public void addLoad(HostLoad load) {
		this.count++;
		this.cpuPercent += load.getCpuPercent();
		this.diskPercent += load.getDiskPercent();
		this.diskReadRate += load.getDiskReadRate();
		this.diskWriteRate += load.getDiskWriteRate();
		this.load += load.getAvgLoad();
		this.memPercent += load.getMemPercent();
		this.netInPercent += load.getNetInPercent();
		this.netOutPercent += load.getNetOutPercent();
	}
	
	public void clear() {
		this.cpuPercent = 0;
		this.load = 0;
		this.memPercent = 0;
		this.diskPercent = 0;
		this.netInPercent = 0;
		this.netOutPercent = 0;
		this.diskReadRate = 0;
		this.diskWriteRate = 0;
		this.count = 0;
	}
	
	public HostLoad asHostLoad() {
		HostLoad hostLoad = new HostLoad();
		hostLoad.setUuid(getUuid());
		hostLoad.setCpuPercent(getAvg(cpuPercent));
		hostLoad.setAvgLoad(getAvg(load));
		hostLoad.setTime(new Timestamp(System.currentTimeMillis()));
		hostLoad.setMemPercent(getAvg(memPercent));
		hostLoad.setDiskPercent(getAvg(diskPercent));
		hostLoad.setNetInPercent(getAvg(netInPercent));
		hostLoad.setNetOutPercent(getAvg(netOutPercent));
		hostLoad.setDiskReadRate(getAvg(diskReadRate));
		hostLoad.setDiskWriteRate(getAvg(diskWriteRate));
		return hostLoad;
	}
	
	public float getAvg(double d) {
		if (count == 0) {
			return 0;
		} else {
			return (float) (d/count);
		}
	}
	
	public String getUuid() {
		if (TextHelper.isEmpty(uuid)) {
			this.uuid = SystemUtils.getUuid();
		}
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
		
		
	}

	public double getCpuPercent() {
		return cpuPercent;
	}

	public void setCpuPercent(double cpuPercent) {
		this.cpuPercent = cpuPercent;
	}

	public double getLoad() {
		return load;
	}

	public void setLoad(double load) {
		this.load = load;
	}

	public double getMemPercent() {
		return memPercent;
	}

	public void setMemPercent(double memPercent) {
		this.memPercent = memPercent;
	}

	public double getDiskPercent() {
		return diskPercent;
	}

	public void setDiskPercent(double diskPercent) {
		this.diskPercent = diskPercent;
	}

	public double getNetInPercent() {
		return netInPercent;
	}

	public void setNetInPercent(double netInPercent) {
		this.netInPercent = netInPercent;
	}

	public double getNetOutPercent() {
		return netOutPercent;
	}

	public void setNetOutPercent(double netOutPercent) {
		this.netOutPercent = netOutPercent;
	}

	public double getDiskReadRate() {
		return diskReadRate;
	}

	public void setDiskReadRate(double diskReadRate) {
		this.diskReadRate = diskReadRate;
	}

	public double getDiskWriteRate() {
		return diskWriteRate;
	}

	public void setDiskWriteRate(double diskWriteRate) {
		this.diskWriteRate = diskWriteRate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "LoadSummary [uuid=" + uuid + ", cpuPercent=" + cpuPercent
				+ ", load=" + load + ", memPercent=" + memPercent
				+ ", diskPercent=" + diskPercent + ", netInPercent="
				+ netInPercent + ", netOutPercent=" + netOutPercent
				+ ", diskReadRate=" + diskReadRate + ", diskWriteRate="
				+ diskWriteRate + ", count=" + count + "]";
	}

}
