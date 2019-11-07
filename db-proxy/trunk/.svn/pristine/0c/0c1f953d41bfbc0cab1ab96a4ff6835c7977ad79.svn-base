package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import appcloud.common.model.HostLoad;

/**
 * 
* @ClassName: HostLoadTable
* @author haowei.yu
* @date 2013-4-9 下午4:18:39
 */

@Entity
@Table(name = "host_load")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class HostLoadTable extends HostLoad{

    public HostLoadTable() {
        super();
    }
    public HostLoadTable(HostLoad vmLoad) {
        this.setId(vmLoad.getId());
        this.setUuid(vmLoad.getUuid());
        this.setTime(vmLoad.getTime());
        this.setCpuPercent(vmLoad.getCpuPercent());
        this.setAvgLoad(vmLoad.getAvgLoad());
        this.setMemPercent(vmLoad.getMemPercent());
        this.setDiskPercent(vmLoad.getDiskPercent());
        this.setNetInPercent(vmLoad.getNetInPercent());
        this.setNetOutPercent(vmLoad.getNetOutPercent());
        this.setDiskReadRate(vmLoad.getDiskReadRate());
        this.setDiskWriteRate(vmLoad.getDiskWriteRate());
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
    
    @Column(name = "uuid")
    public String getUuid() {
        return super.getUuid();
    }
    public void setUuid(String uuid){
        super.setUuid(uuid);
    }
    
    @Column(name="time")
    public Timestamp getTime() {
        return super.getTime();
    }
    public void setTime(Timestamp time) {
        super.setTime(time);
    }
    
    @Column(name = "cpu_percent")
    public Float getCpuPercent(){
        return super.getCpuPercent();
    }
    public void setCpuPercent(Float cpuPercent){
        super.setCpuPercent(cpuPercent);
    }
    
    @Column(name = "avg_load")
    public Float getAvgLoad(){
        return super.getAvgLoad();
    }
    public void setAvgLoad(Float load){
        super.setAvgLoad(load);
    }
    
    @Column(name = "mem_percent")
    public Float getMemPercent(){
        return super.getMemPercent();
    }
    public void setMemPercent(Float memPercent){
        super.setMemPercent(memPercent);
    }
    
    @Column(name = "disk_percent")
    public Float getDiskPercent(){
        return super.getDiskPercent();
    }
    public void setDiskPercent(Float diskPercent){
        super.setDiskPercent(diskPercent);
    }
    
    @Column(name = "net_in_percent")
    public Float getNetInPercent(){
        return super.getNetInPercent();
    }
    public void setNetInPercent(Float netInPercent){
        super.setNetInPercent(netInPercent);
    }
    
    @Column(name = "net_out_percent")
    public Float getNetOutPercent(){
        return super.getNetOutPercent();
    }
    public void setNetOutPercent(Float netOutPercent){
        super.setNetOutPercent(netOutPercent);
    }
    
    @Column(name = "disk_read_rate")
    public Float getDiskReadRate(){
        return super.getDiskReadRate();
    }
    public void setDiskReadRate(Float diskReadRate){
        super.setDiskReadRate(diskReadRate);
    }
    
    @Column(name = "disk_write_rate")
    public Float getDiskWriteRate(){
        return super.getDiskWriteRate();
    }
    public void setDiskWriteRate(Float diskWriteRate){
        super.setDiskWriteRate(diskWriteRate);
    }
}
