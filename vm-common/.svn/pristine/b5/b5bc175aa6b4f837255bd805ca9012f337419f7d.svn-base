package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
* @ClassName: VmInstanceMetadata
* @author haowei.yu
* @date 2013-4-4 下午1:19:01
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmInstanceMetadata {
    private Integer id;
    private Integer vmInstanceId;
    private String key;
    private String value;
    
    private VmInstance vmInstance;

    public VmInstanceMetadata() {
        super();
    }

    public VmInstanceMetadata(Integer id, Integer vmInstanceId, String key, String value) {
        super();
        this.id = id;
        this.vmInstanceId = vmInstanceId;
        this.key = key;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVmInstanceId() {
        return vmInstanceId;
    }

    public void setVmInstanceId(Integer vmInstanceId) {
        this.vmInstanceId = vmInstanceId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VmInstance getVmInstance() {
        return vmInstance;
    }

    public void setVmInstance(VmInstance vmInstance) {
        this.vmInstance = vmInstance;
    }

    @Override
    public String toString() {
        return "VmInstanceMetadata[" + "id=" + id + ",vmInstanceId=" + vmInstanceId 
                + ",key=" + key + ",value="+ value + "]";
    }

}
