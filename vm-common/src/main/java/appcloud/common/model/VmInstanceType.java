package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @ClassName: VmInstanceType
 * @Description: TODO
 * @author haowei.yu
 * @date 2013-4-4 下午1:19:17
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmInstanceType {
    private Integer id;
    private String flavorUuid;
    private String name;
    private Integer vcpus;
    private Integer memoryMb;
    private Integer localGb;

    public VmInstanceType() {
        super();
    }

    public VmInstanceType(Integer id,
                          String flavorUuid,
                          String name,
                          Integer vcpus,
                          Integer memoryMb,
                          Integer localGb) {
        super();
        this.id = id;
        this.flavorUuid = flavorUuid;
        this.name = name;
        this.vcpus = vcpus;
        this.memoryMb = memoryMb;
        this.localGb = localGb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlavorUuid() {
        return flavorUuid;
    }

    public void setFlavorUuid(String flavorUuid) {
        this.flavorUuid = flavorUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVcpus() {
        return vcpus;
    }

    public void setVcpus(Integer vcpus) {
        this.vcpus = vcpus;
    }

    public Integer getMemoryMb() {
        return memoryMb;
    }

    public void setMemoryMb(Integer memoryMb) {
        this.memoryMb = memoryMb;
    }

    public Integer getLocalGb() {
        return localGb;
    }

    public void setLocalGb(Integer localGb) {
        this.localGb = localGb;
    }

    @Override
    public String toString() {
        return "VmInstanceType[id="
               + id
               + ",flavorUuid"
               + flavorUuid
               + ",name="
               + name
               + ",vcpus="
               + vcpus
               + "memoryMb="
               + memoryMb
               + "localGb="
               + localGb
               + "]";

    }

}
