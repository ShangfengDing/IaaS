package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.Host;



@Entity
@Table(name="vm_host")
public class HostTable extends Host {

    public HostTable() {
        super();
    }

    public HostTable(Host host) {
        this.setId(host.getId());
        this.setUuid(host.getUuid());
        this.setType(host.getType());
        this.setIp(host.getIp());
        this.setLocation(host.getLocation());
        this.setClusterId(host.getClusterId());
        this.setAvailabilityZoneId(host.getAvailabilityZoneId());
        this.setOs(host.getOs());
        this.setCpuMhz(host.getCpuMhz());
        this.setCpuCore(host.getCpuCore());
        this.setMemoryMb(host.getMemoryMb());
        this.setDiskMb(host.getDiskMb());
        this.setNetworkMb(host.getNetworkMb());
        this.setStatus(host.getStatus());
        this.setExtend(host.getExtend());
        this.setName(host.getName());
        this.setHypervisorType(host.getHypervisorType());
        this.setHypervisorVersion(host.getHypervisorVersion());
        this.setPrivateVLAN(host.getPrivateVLAN());
        this.setPublicVLAN(host.getPublicVLAN());
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public Integer getId() {
        return super.getId();
    }
    public void setId(Integer id) {
        super.setId(id);
    }
    
    @Column(name="uuid")
    public String getUuid() {
        return super.getUuid();
    }
    public void setUuid(String uuid) {
        super.setUuid(uuid);
    }
    
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    public HostTypeEnum getType() {
        return super.getType();
    }
    public void setType(HostTypeEnum type) {
        super.setType(type);
    }
    
    @Column(name="ip")
    public String getIp() {
        return super.getIp();
    }
    public void setIp(String ip) {
        super.setIp(ip);
    }
    
        
    @Column(name="location")
    public String getLocation() {
        return super.getLocation();
    }
    public void setLocation(String location) {
        super.setLocation(location);
    }
    
    @Column(name="availability_cluster_id")
    public Integer getClusterId() {
        return super.getClusterId();
    }
    public void setClusterId(Integer clusterId) {
        super.setClusterId(clusterId);
    }

    @Column(name="availability_zone_id")
	public Integer getAvailabilityZoneId() {
		return super.getAvailabilityZoneId();
	}
	public void setAvailabilityZoneId(Integer availabilityZoneId) {
		super.setAvailabilityZoneId(availabilityZoneId);
	}
	

    @Column(name="os")
    public String getOs() {
        return super.getOs();
    }
    public void setOs(String os) {
        super.setOs(os);
    }
    
    @Column(name="cpu_mhz")
    public Integer getCpuMhz() {
        return super.getCpuMhz();
    }
    public void setCpuMhz(Integer cpuMhz) {
        super.setCpuMhz(cpuMhz);
    }
    
    @Column(name="cpu_core")
    public Integer getCpuCore() {
        return super.getCpuCore();
    }
    public void setCpuCore(Integer cpuCore) {
        super.setCpuCore(cpuCore);
    }
    
    @Column(name="memory_mb")
    public Integer getMemoryMb() {
        return super.getMemoryMb();
    }
    public void setMemoryMb(Integer memoryMb) {
        super.setMemoryMb(memoryMb);
    }
    
    @Column(name="disk_gb")
    public Integer getDiskMb() {
        return super.getDiskMb();
    }
    public void setDiskMb(Integer diskMb) {
        super.setDiskMb(diskMb);
    }
    
    @Column(name="network_mb")
    public Integer getNetworkMb() {
        return super.getNetworkMb();
    }
    public void setNetworkMb(Integer networkMb) {
        super.setNetworkMb(networkMb);
    }
    
    
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    public HostStatusEnum getStatus() {
        return super.getStatus();
    }
    public void setStatus(HostStatusEnum status) {
        super.setStatus(status);
    }
    
    @Column(name="extend")
    public String getExtend() {
        return super.getExtend();
    }
    public void setExtend(String extend) {
        super.setExtend(extend);
    }
    
    @Column(name="name")
    public String getName() {
        return super.getName();
    }
    public void setName(String name) {
        super.setName(name);
    }
    
    @Column(name="hypervisor_type")
    public String getHypervisorType() {
        return super.getHypervisorType();
    }
    public void setHypervisorType(String hypervisorType) {
        super.setHypervisorType(hypervisorType);
    }
    
    @Column(name="hypervisor_version")
    public Integer getHypervisorVersion() {
        return super.getHypervisorVersion();
    }
    public void setHypervisorVersion(Integer hypervisorVersion) {
        super.setHypervisorVersion(hypervisorVersion);
    }
    
    @Column(name="public_vlan")
    public Integer getPublicVLAN() {
        return super.getPublicVLAN();
    }
    public void setPublicVLAN(Integer publicVLAN) {
        super.setPublicVLAN(publicVLAN);
    }
    
    @Column(name="private_vlan")
    public Integer getPrivateVLAN() {
        return super.getPrivateVLAN();
    }
    public void setPrivateVLAN(Integer privateVLAN) {
        super.setPrivateVLAN(privateVLAN);
    }
}
