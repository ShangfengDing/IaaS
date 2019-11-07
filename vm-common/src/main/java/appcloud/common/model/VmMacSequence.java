package appcloud.common.model;

public class VmMacSequence {
    private Integer id; 
    private Integer clusterId; //集群id
    private String ipSegment;  //IP段
    private Integer maxMac;    //最大的max

    public VmMacSequence() {
        super();
    }

    public VmMacSequence(Integer id, Integer clusterId, String ipSegment, Integer maxMac) {
        super();
        this.id = id;
        this.clusterId = clusterId;
        this.ipSegment = ipSegment;
        this.maxMac = maxMac;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public String getIpSegment() {
        return ipSegment;
    }

    public void setIpSegment(String ipSegment) {
        this.ipSegment = ipSegment;
    }

    public Integer getMaxMac() {
        return maxMac;
    }

    public void setMaxMac(Integer maxMac) {
        this.maxMac = maxMac;
    }

    @Override
    public String toString() {
        return "MacSequence [id="
               + id
               + ", clusterId="
               + clusterId
               + ", ipSegment="
               + ipSegment
               + ", maxMac="
               + maxMac
               + "]";
    }
    
    

}
