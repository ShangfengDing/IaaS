package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmMacSequence;

@Entity
@Table(name = "vm_mac_sequence")
public class VmMacSequenceTable extends VmMacSequence {
    
    

    public VmMacSequenceTable() {
        super();
    }
    
    public VmMacSequenceTable(VmMacSequence vmMacSequence) {
        this.setId(vmMacSequence.getId());
        this.setClusterId(vmMacSequence.getClusterId());
        this.setIpSegment(vmMacSequence.getIpSegment());
        this.setMaxMac(vmMacSequence.getMaxMac());
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId(){
        return super.getId();
    }
    public void setId(Integer id){
        super.setId(id);
    }
    
    @Column(name = "cluster_id")
    public Integer getClusterId(){
        return super.getClusterId();
    }
    public void setClusterId(Integer clusterId){
        super.setClusterId(clusterId);
    }
    
    @Column(name = "ip_segment")
    public String getIpSegment(){
        return super.getIpSegment();
    }
    public void setIpSegment(String ipSegment){
        super.setIpSegment(ipSegment);
    }
    
    @Column(name = "max_mac")
    public Integer getMaxMac(){
        return super.getMaxMac();
    }
    public void setMaxMac(Integer maxMac){
        super.setMaxMac(maxMac);
    }
}
