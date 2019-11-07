package appcloud.admin.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zouji on 2018/5/17.
 * 主机原始参数
 */
@Entity
@Table(name = "raw_physical_node")
public class HostMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "user_sys")
    private Integer cpu;

    @Column(name = "swap")
    private Integer swap;

    @Column(name = "io_wait")
    private Integer ioWait;

    @Column(name = "net")
    private Integer net;

    @Column(name = "disk")
    private Float diskUsed;

    @Column(name = "created_time")
    private Timestamp createdTime;

    public HostMetrics() {}

    public HostMetrics(String uuid, Integer cpu, Integer swap, Integer ioWait, Integer net, Float diskUsed, Timestamp createdTime) {
        this.uuid = uuid;
        this.cpu = cpu;
        this.swap = swap;
        this.ioWait = ioWait;
        this.net = net;
        this.diskUsed = diskUsed;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "HostMetrics{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", cpu=" + cpu +
                ", swap=" + swap +
                ", ioWait=" + ioWait +
                ", net=" + net +
                ", diskUsed=" + diskUsed +
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

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getSwap() {
        return swap;
    }

    public void setSwap(Integer swap) {
        this.swap = swap;
    }

    public Integer getIoWait() {
        return ioWait;
    }

    public void setIoWait(Integer ioWait) {
        this.ioWait = ioWait;
    }

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Float getDiskUsed() {
        return diskUsed;
    }

    public void setDiskUsed(Float diskUsed) {
        this.diskUsed = diskUsed;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
