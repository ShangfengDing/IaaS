package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmVirtualInterface;

@Entity
@Table(name = "vm_virtual_interface")
public class VmVirtualInterfaceTable extends VmVirtualInterface {

    public VmVirtualInterfaceTable() {
        super();
    }

    public VmVirtualInterfaceTable(VmVirtualInterface vmVirtualInterface) {
        this.setId(vmVirtualInterface.getId());
        this.setInstanceUuid(vmVirtualInterface.getInstanceUuid());
        this.setAddress(vmVirtualInterface.getAddress());
        this.setMac(vmVirtualInterface.getMac());
        this.setNetworkType(vmVirtualInterface.getNetworkType());
        this.setIpPoolId(vmVirtualInterface.getIpPoolId());
        this.setVmInstance(vmVirtualInterface.getVmInstance());
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

    @Column(name = "instance_uuid")
    public String getInstanceUuid() {
        return super.getInstanceUuid();
    }

    public void setInstanceUuid(String instanceUuid) {
        super.setInstanceUuid(instanceUuid);
    }

    @Column(name = "address")
    public String getAddress() {
        return super.getAddress();
    }

    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Column(name = "mac")
    public String getMac() {
        return super.getMac();
    }

    public void setMac(String mac) {
        super.setMac(mac);
    }

    @Column(name = "network_type")
    public String getNetworkType() {
        return super.getNetworkType();
    }

    public void setNetworkType(String networkType) {
        super.setNetworkType(networkType);
    }

    @Column(name = "ip_pool_id")
    public Integer getIpPoolId() {
        return super.getIpPoolId();
    }

    public void setIpPoolId(Integer ipPoolId) {
        super.setIpPoolId(ipPoolId);
    }

}
