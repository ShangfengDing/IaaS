package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmInstanceMetadata;


@Entity
@Table(name = "vm_instance_metadata")
public class VmInstanceMetadataTable extends VmInstanceMetadata{

    public VmInstanceMetadataTable() {
        super();
    }

    public VmInstanceMetadataTable(VmInstanceMetadata vmInstanceMetadata) {
        this.setId(vmInstanceMetadata.getId());
        this.setVmInstanceId(vmInstanceMetadata.getVmInstanceId());
        this.setKey(vmInstanceMetadata.getKey());
        this.setValue(vmInstanceMetadata.getValue());
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
    
    @Column(name = "vm_instance_id")
    public Integer getVmInstanceId(){
        return super.getVmInstanceId();
    }
    public void setVmInstanceId(Integer vmInstanceId){
        super.setVmInstanceId(vmInstanceId);
    }
    
    @Column(name = "meta_key")
    public String getKey(){
        return super.getKey();
    }
    public void setKey(String key){
        super.setKey(key);
    }
    
    @Column(name = "meta_value")
    public String getValue(){
        return super.getValue();
    }
    public void setValue(String value){
        super.setValue(value);
    }
    
}
