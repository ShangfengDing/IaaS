package appcloud.openapi.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class InstanceMonitorDataType {
	public Date time;  //createdTime
    public Float cpuPercent; //cpu_rate
    public Float avgLoad;
    public Float memPercent;
    public Float diskPercent;
    public Float netInPercent; //net_write_rate
    public Float netOutPercent; //net_read_rate
    public Float diskReadRate;  //disk_read_rate
    public Float diskWriteRate; //disk_write_rate

	//admin账号查询增加字段
	public String instanceId;
	public String vm_ipaddress; //虚拟机的ip
	public String hostname; //主机的名称
	public String vm_mac;   //虚拟机的mac
	public String mac;   //物理机的mac
	public String ipaddress; // 物理的ip
    
    public InstanceMonitorDataType() {}
    
	public InstanceMonitorDataType(Date time, Float cpuPercent, Float avgLoad,
			Float memPercent, Float diskPercent, Float netInPercent,
			Float netOutPercent, Float diskReadRate, Float diskWriteRate) {
		super();
		this.time = time;
		this.cpuPercent = cpuPercent;
		this.avgLoad = avgLoad;
		this.memPercent = memPercent;
		this.diskPercent = diskPercent;
		this.netInPercent = netInPercent;
		this.netOutPercent = netOutPercent;
		this.diskReadRate = diskReadRate;
		this.diskWriteRate = diskWriteRate;
	}

	//admin 账号获取的数据
	public InstanceMonitorDataType(Date time, Float cpuPercent, Float netInPercent, Float netOutPercent, Float diskReadRate, Float diskWriteRate,
								   String vm_ipaddress, String hostname, String vm_mac, String mac, String ipaddress,String instanceId) {
		super();
		this.time = time;
		this.cpuPercent = cpuPercent;
		this.netInPercent = netInPercent;
		this.netOutPercent = netOutPercent;
		this.diskReadRate = diskReadRate;
		this.diskWriteRate = diskWriteRate;
		this.vm_ipaddress = vm_ipaddress;
		this.hostname = hostname;
		this.vm_mac = vm_mac;
		this.mac = mac;
		this.ipaddress = ipaddress;
		this.instanceId = instanceId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Float getCpuPercent() {
		return cpuPercent;
	}

	public void setCpuPercent(Float cpuPercent) {
		this.cpuPercent = cpuPercent;
	}

	public Float getAvgLoad() {
		return avgLoad;
	}

	public void setAvgLoad(Float avgLoad) {
		this.avgLoad = avgLoad;
	}

	public Float getMemPercent() {
		return memPercent;
	}

	public void setMemPercent(Float memPercent) {
		this.memPercent = memPercent;
	}

	public Float getDiskPercent() {
		return diskPercent;
	}

	public void setDiskPercent(Float diskPercent) {
		this.diskPercent = diskPercent;
	}

	public Float getNetInPercent() {
		return netInPercent;
	}

	public void setNetInPercent(Float netInPercent) {
		this.netInPercent = netInPercent;
	}

	public Float getNetOutPercent() {
		return netOutPercent;
	}

	public void setNetOutPercent(Float netOutPercent) {
		this.netOutPercent = netOutPercent;
	}

	public Float getDiskReadRate() {
		return diskReadRate;
	}

	public void setDiskReadRate(Float diskReadRate) {
		this.diskReadRate = diskReadRate;
	}

	public Float getDiskWriteRate() {
		return diskWriteRate;
	}

	public void setDiskWriteRate(Float diskWriteRate) {
		this.diskWriteRate = diskWriteRate;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getVm_ipaddress() {
		return vm_ipaddress;
	}

	public void setVm_ipaddress(String vm_ipaddress) {
		this.vm_ipaddress = vm_ipaddress;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getVm_mac() {
		return vm_mac;
	}

	public void setVm_mac(String vm_mac) {
		this.vm_mac = vm_mac;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	
}
