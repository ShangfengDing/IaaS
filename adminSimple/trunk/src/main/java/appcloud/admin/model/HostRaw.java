package appcloud.admin.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 历史数据
 * @author BZbyr
 */
@Entity
@Table(name = "raw_physical_node")
public class HostRaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "user_sys")
    private float userSys;

    @Column(name = "swap")
    private float swap;

    @Column(name = "io_wait")
    private float ioWait;

    @Column(name = "net")
    private float net;

    @Column(name = "disk")
    private Float disk;

    public HostRaw() {}

    public HostRaw(String uuid, LoadStatus cpuLoad, LoadStatus memLoad, LoadStatus diskLoad, LoadStatus netLoad, HostStatus status, Timestamp createdTime) {
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


    public Float getDisk() {
        return disk;
    }

    public void setDisk(Float disk) {
        this.disk = disk;
    }

    public float getNet() {
        return net;
    }

    public void setNet(float net) {
        this.net = net;
    }

    public float getIoWait() {
        return ioWait;
    }

    public void setIoWait(float ioWait) {
        this.ioWait = ioWait;
    }

    public float getSwap() {
        return swap;
    }

    public void setSwap(float swap) {
        this.swap = swap;
    }

    public float getUserSys() {
        return userSys;
    }

    public void setUserSys(Float userSys) {
        this.userSys = userSys;
    }
}