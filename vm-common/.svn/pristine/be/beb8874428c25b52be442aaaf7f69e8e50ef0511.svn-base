package appcloud.common.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 请使用VmLoad
 * @ClassName: VmDailyLoad
 * @author haowei.yu
 * @date 2013-4-9 下午2:59:30
 */

@Deprecated
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmDailyLoad {
    private Integer id;
    private String uuid;
    private Timestamp time;
    private Float cpuPercent;
    private Float avgLoad;
    private Float memPercent;
    private Float diskPercent;
    private Float netInPercent;
    private Float netOutPercent;
    private Float diskReadRate;
    private Float diskWriteRate;

    public VmDailyLoad() {
        super();
    }

    public VmDailyLoad(Integer id,
                       String uuid,
                       Timestamp time,
                       Float cpuPercent,
                       Float avgLoad,
                       Float memPercent,
                       Float diskPercent,
                       Float netInPercent,
                       Float netOutPercent,
                       Float diskReadRate,
                       Float diskWriteRate) {
        super();
        this.id = id;
        this.uuid = uuid;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
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

    @Override
    public String toString() {
        return "VmLoad [id="
               + id
               + ", uuid="
               + uuid
               + ", time="
               + time
               + ", cpuPercent="
               + cpuPercent
               + ", avgLoad="
               + avgLoad
               + ", memPercent="
               + memPercent
               + ", diskPercent="
               + diskPercent
               + ", netInPercent="
               + netInPercent
               + ", netOutPercent="
               + netOutPercent
               + ", diskReadRate="
               + diskReadRate
               + ", diskWriteRate="
               + diskWriteRate
               + "]";
    }

}
