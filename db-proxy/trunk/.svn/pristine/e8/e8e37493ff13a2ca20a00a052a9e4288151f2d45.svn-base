package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmInstanceType;

@Entity
@Table(name = "vm_instance_type")
public class VmInstanceTypeTable extends VmInstanceType {

    public VmInstanceTypeTable() {
        super();
        // TODO Auto-generated constructor stub
    }

    public VmInstanceTypeTable(VmInstanceType vmInstanceType) {
       this.setId(vmInstanceType.getId());
       this.setFlavorUuid(vmInstanceType.getFlavorUuid());
       this.setName(vmInstanceType.getName());
       this.setVcpus(vmInstanceType.getVcpus());
       this.setMemoryMb(vmInstanceType.getMemoryMb());
       this.setLocalGb(vmInstanceType.getLocalGb());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return super.getId();
    }

    public void setId(Integer id) {
        super.setId(id);
    }

    @Column(name = "flavor_uuid")
    public String getFlavorUuid() {
        return super.getFlavorUuid();
    }

    public void setFlavorUuid(String flavorUuid) {
        super.setFlavorUuid(flavorUuid);
    }

    @Column(name = "name")
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    @Column(name = "vcpus")
    public Integer getVcpus() {
        return super.getVcpus();
    }

    public void setVcpus(Integer vcpus) {
        super.setVcpus(vcpus);
    }

    @Column(name = "memory_mb")
    public Integer getMemoryMb() {
        return super.getMemoryMb();
    }

    public void setMemoryMb(Integer memoryMb) {
        super.setMemoryMb(memoryMb);
    }

    @Column(name = "local_gb")
    public Integer getLocalGb() {
        return super.getLocalGb();
    }

    public void setLocalGb(Integer localGb) {
        super.setLocalGb(localGb);
    }

}
