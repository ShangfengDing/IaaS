package appcloud.admin.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 预测数据
 * @author BZbyr
 */
@Entity
@Table(name = "raw_physical_node_predict")
public class HostPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "user_sys")
    private Integer userSys;

    @Column(name = "swap")
    private Integer swap;

    @Column(name = "io_wait")
    private Integer ioWait;

    @Column(name = "net")
    private Integer net;

    @Column(name = "disk")
    private Float disk;

    public HostPrediction() {}

    public HostPrediction(String uuid, LoadStatus cpuLoad, LoadStatus memLoad, LoadStatus diskLoad, LoadStatus netLoad, HostStatus status, Timestamp createdTime) {
        this.uuid = uuid;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", uuid=" + uuid +
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

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getUserSys() {
        return userSys;
    }

    public void setUserSys(Integer userSys) {
        this.userSys = userSys;
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

    public Float getDisk() {
        return disk;
    }

    public void setDisk(Float disk) {
        this.disk = disk;
    }
}