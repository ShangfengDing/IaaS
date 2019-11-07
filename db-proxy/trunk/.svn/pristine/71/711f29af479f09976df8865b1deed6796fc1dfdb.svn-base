package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmUsedIp;

@Entity
@Table(name = "vm_used_ip")
public class VmUsedIpTable {
    private VmUsedIp vmUsedIp = new VmUsedIp();
    
    public VmUsedIpTable() {
        super();
    }

    public VmUsedIpTable(VmUsedIp VmUsedIp) {
        this.setIpSegmentId(VmUsedIp.getIpSegment());
        this.setIpAddr(VmUsedIp.getIpAddr());
        this.setMac(VmUsedIp.getMac());
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId(){
        return vmUsedIp.getId();
    }
    public void setId(Integer id){
        vmUsedIp.setId(id);
    }
    
    @Column(name = "ip_address")
    public String getIpAddr(){
        return vmUsedIp.getIpAddr();
    }
    public void setIpAddr(String ipAddr){
        vmUsedIp.setIpAddr(ipAddr);
    }
    
    @Column(name = "mac")
    public String getMac(){
        return vmUsedIp.getMac();
    }
    public void setMac(String mac){
        vmUsedIp.setMac(mac);
    }

    @Column(name = "ip_segment_id")
    public Integer getIpSegmentId() {
    	return vmUsedIp.getIpSegment();
    }
    public void setIpSegmentId(Integer ipSegmentId) {
    	vmUsedIp.setIpSegment(ipSegmentId);
    }
    
}
