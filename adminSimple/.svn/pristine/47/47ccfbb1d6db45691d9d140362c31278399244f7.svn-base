package appcloud.admin.model;

import javax.persistence.*;

/**
 * Created by zouji on 2018/5/17.
 */
@Entity
@Table(name = "vm_host")
public class HostDetail {
    /**
     * 主机类型
     */
    public static enum HostType{
        COMPUTE_NODE, FUNCTION_NODE
    }

    /**
     * 主机状态
     */
    public static enum HostStatus{
        HIGH_LOAD, NORMAL_LOAD, LOW_LOAD, CRASH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "type")
    private HostType type;

    @Column(name = "ip")
    private String ipaddress;

    @Column(name = "location")
    private String location;

    @Column(name = "availability_cluster_id")
    private Integer availabilityClusterId;

    @Column(name = "availability_zone_id")
    private Integer availabilityZoneId;

    @Column(name = "private_vlan")
    private Integer privateVlan;

    @Column(name = "public_vlan")
    private Integer publicVlan;

    @Column(name = "os")
    private String os;

    @Column(name = "cpu_mhz")
    private Integer cpu;

    @Column(name = "cpu_core")
    private Integer cpuCore;

    @Column(name = "memory_mb")
    private Integer memory;

    @Column(name = "disk_gb")
    private Integer disk;

    @Column(name = "network_mb")
    private Integer network;

    @Column(name = "status")
    private HostStatus status;

    @Column(name = "extend")
    private String extend;

    @Column(name = "name")
    private String name;

    @Column(name = "hypervisor_type")
    private String hypervisorType;

    @Column(name = "hypervisor_version")
    private String hypervisorVersion;

    public HostDetail() {}

    public HostDetail(String uuid, HostType type, String ipaddress, String location, Integer availabilityClusterId, Integer availabilityZoneId, Integer privateVlan, Integer publicVlan, String os, Integer cpu, Integer cpuCore, Integer memory, Integer disk, Integer network, HostStatus status, String extend, String name, String hypervisorType, String hypervisorVersion) {
        this.uuid = uuid;
        this.type = type;
        this.ipaddress = ipaddress;
        this.location = location;
        this.availabilityClusterId = availabilityClusterId;
        this.availabilityZoneId = availabilityZoneId;
        this.privateVlan = privateVlan;
        this.publicVlan = publicVlan;
        this.os = os;
        this.cpu = cpu;
        this.cpuCore = cpuCore;
        this.memory = memory;
        this.disk = disk;
        this.network = network;
        this.status = status;
        this.extend = extend;
        this.name = name;
        this.hypervisorType = hypervisorType;
        this.hypervisorVersion = hypervisorVersion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HostType getType() {
        return type;
    }

    public void setType(HostType type) {
        this.type = type;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAvailabilityClusterId() {
        return availabilityClusterId;
    }

    public void setAvailabilityClusterId(Integer availabilityClusterId) {
        this.availabilityClusterId = availabilityClusterId;
    }

    public Integer getAvailabilityZoneId() {
        return availabilityZoneId;
    }

    public void setAvailabilityZoneId(Integer availabilityZoneId) {
        this.availabilityZoneId = availabilityZoneId;
    }

    public Integer getPrivateVlan() {
        return privateVlan;
    }

    public void setPrivateVlan(Integer privateVlan) {
        this.privateVlan = privateVlan;
    }

    public Integer getPublicVlan() {
        return publicVlan;
    }

    public void setPublicVlan(Integer publicVlan) {
        this.publicVlan = publicVlan;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getCpuCore() {
        return cpuCore;
    }

    public void setCpuCore(Integer cpuCore) {
        this.cpuCore = cpuCore;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }

    public Integer getNetwork() {
        return network;
    }

    public void setNetwork(Integer network) {
        this.network = network;
    }

    public HostStatus getStatus() {
        return status;
    }

    public void setStatus(HostStatus status) {
        this.status = status;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHypervisorType() {
        return hypervisorType;
    }

    public void setHypervisorType(String hypervisorType) {
        this.hypervisorType = hypervisorType;
    }

    public String getHypervisorVersion() {
        return hypervisorVersion;
    }

    public void setHypervisorVersion(String hypervisorVersion) {
        this.hypervisorVersion = hypervisorVersion;
    }
}
