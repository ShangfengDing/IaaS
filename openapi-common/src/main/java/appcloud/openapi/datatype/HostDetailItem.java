package appcloud.openapi.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by lizhenhao on 2016/11/17.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class HostDetailItem {
    private String hostUuid;
    private String type;
    private String ip;
    private String cpu_mhz;
    private String cpu_core;
    private String memory_mb;
    private String disk_gb;
    private String network_mb;
    private String status;

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCpu_mhz() {
        return cpu_mhz;
    }

    public void setCpu_mhz(String cpu_mhz) {
        this.cpu_mhz = cpu_mhz;
    }

    public String getCpu_core() {
        return cpu_core;
    }

    public void setCpu_core(String cpu_core) {
        this.cpu_core = cpu_core;
    }

    public String getMemory_mb() {
        return memory_mb;
    }

    public void setMemory_mb(String memory_mb) {
        this.memory_mb = memory_mb;
    }

    public String getDisk_gb() {
        return disk_gb;
    }

    public void setDisk_gb(String disk_gb) {
        this.disk_gb = disk_gb;
    }

    public String getNetwork_mb() {
        return network_mb;
    }

    public void setNetwork_mb(String network_mb) {
        this.network_mb = network_mb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

