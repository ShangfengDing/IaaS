package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmIpSegMent;

@Entity
@Table(name = "vm_ip_segment")
public class VmIpSegmentTable  {
    private VmIpSegMent vmIpSegMent = new VmIpSegMent() ;
    public VmIpSegmentTable() {
        super();
    }

    public VmIpSegmentTable(VmIpSegMent ipSegment) {
        //this.setId(ipSegment.getId());
        this.setDhcpId(ipSegment.getDhcpId());
        this.setNetmask(ipSegment.getNetMask());
        this.setRouter(ipSegment.getRouter());
        this.setDns(ipSegment.getDns());
        this.setSegment(ipSegment.getSegment());
        this.setStartIpRange(ipSegment.getIpStartRange());
        this.setEndIpRange(ipSegment.getIpEndRange());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        Integer ret = vmIpSegMent.getId();
        if(ret == null)
            return 0;
        else 
            return ret;
    }

    public void setId(Integer id) {
        vmIpSegMent.setId(id);
    }

    @Column(name = "cluster_id")
    public Integer getDhcpId() { //FIXME the column name is not proper
        return vmIpSegMent.getDhcpId();
    }

    public void setDhcpId(Integer dhcpId) {
        vmIpSegMent.setDhcpId(dhcpId);
    }

    @Column(name = "ipRangeStart")
    public String getStartIpRange() {
        return vmIpSegMent.getIpStartRange();
    }
    public void setStartIpRange(String startIp) {
        vmIpSegMent.setIpStartRange(startIp);
    }
    
    @Column(name = "ipRangeEnd")
    public String getEndIpRange() {
        return vmIpSegMent.getIpEndRange();
    }
    public void setEndIpRange(String endIp) {
        vmIpSegMent.setIpEndRange(endIp);
    }
    

    @Column(name = "netmask")
    public String getNetmask() {
        return vmIpSegMent.getNetMask();
    }

    public void setNetmask(String netmask) {
        vmIpSegMent.setNetMask(netmask);
    }


    @Column(name = "router")
	public String getRouter() {
		return vmIpSegMent.getRouter();
	}

	public void setRouter(String router) {
	    vmIpSegMent.setRouter(router);
	}

    @Column(name = "dns")
	public String getDns() {
		return vmIpSegMent.getDns();
	}
	public void setDns(String dns) {
	    vmIpSegMent.setDns(dns);
	}
    
    @Column(name = "segment")
    public Integer getSegment() {
        return vmIpSegMent.getSegment();
    }

    public void setSegment(Integer segment) {
        vmIpSegMent.setSegment(segment);
    }

}
