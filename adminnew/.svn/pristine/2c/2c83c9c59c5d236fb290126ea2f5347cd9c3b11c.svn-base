package appcloud.admin.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zouji on 2018/4/11.
 * 主机健康状态
 */
@Entity
@Table(name = "physical_node")
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "cpu_load")
    private LoadStatus cpuLoad;

    @Column(name = "mem_load")
    private LoadStatus memLoad;

    @Column(name = "disk_load")
    private LoadStatus diskLoad;

    @Column(name = "net_load")
    private LoadStatus netLoad;

    @Column(name = "status")
    private HostStatus status;

    @Column(name = "created_time")
    private Timestamp createdTime;

    public Host() {}

    public Host(String uuid, LoadStatus cpuLoad, LoadStatus memLoad, LoadStatus diskLoad, LoadStatus netLoad, HostStatus status, Timestamp createdTime) {
        this.uuid = uuid;
        this.cpuLoad = cpuLoad;
        this.memLoad = memLoad;
        this.diskLoad = diskLoad;
        this.netLoad = netLoad;
        this.status = status;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", cpuLoad=" + cpuLoad +
                ", memLoad=" + memLoad +
                ", diskLoad=" + diskLoad +
                ", netLoad=" + netLoad +
                ", status=" + status +
                ", createdTime=" + createdTime +
                "}";
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

    public LoadStatus getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(LoadStatus cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public LoadStatus getMemLoad() {
        return memLoad;
    }

    public void setMemLoad(LoadStatus memLoad) {
        this.memLoad = memLoad;
    }

    public LoadStatus getDiskLoad() {
        return diskLoad;
    }

    public void setDiskLoad(LoadStatus diskLoad) {
        this.diskLoad = diskLoad;
    }

    public LoadStatus getNetLoad() {
        return netLoad;
    }

    public void setNetLoad(LoadStatus netLoad) {
        this.netLoad = netLoad;
    }

    public HostStatus getStatus() {
        return status;
    }

    public void setStatus(HostStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
