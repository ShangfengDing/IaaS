package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmSecurityGroup;

@Entity
@Table(name = "vm_security_group")
public class VmSecurityGroupTable extends VmSecurityGroup {

	public VmSecurityGroupTable() {
		super();
	}

    public VmSecurityGroupTable(VmSecurityGroup group) {
        super();
        this.setId(group.getId());
        this.setName(group.getName());
        this.setUserId(group.getUserId());
        this.setDescription(group.getDescription());
        this.setUuid(group.getUuid());
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
    public void setUuid(String uuid) {
        super.setUuid(uuid);
    }
    
    @Column(name = "name")
    public String getName() {
        return super.getName();
    }
    public void setName(String name) {
        super.setName(name);
    }

    @Column(name = "description")
    public String getDescription() {
        return super.getDescription();
    }
    public void setDescription(String description) {
        super.setDescription(description);
    }


    @Column(name = "user_id")
    public Integer getUserId() {
        return super.getUserId();
    }
    public void setUserId(Integer userId) {
        super.setUserId(userId);
    }

}
