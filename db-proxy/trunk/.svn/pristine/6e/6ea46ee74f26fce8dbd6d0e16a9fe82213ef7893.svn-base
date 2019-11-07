package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmVlan;

@Entity
@Table(name = "vm_vlan")
public class VmVlanTable extends VmVlan{
	
	public VmVlanTable() {
		super();
	}
	
	public VmVlanTable(VmVlan vlan) {
		super();
		this.setId(vlan.getId());
		this.setName(vlan.getName());
		this.setType(vlan.getType());
		this.setDescription(vlan.getDescription());
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
	
	@Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}

    @Column(name="type")
    @Enumerated(EnumType.STRING)
	public VmVlanTypeEnum getType() {
		return super.getType();
	}
	public void setType(VmVlanTypeEnum type) {
		super.setType(type);
	}

    @Column(name="description")
	public String getDescription() {
		return super.getDescription();
	}
	public void setDescription(String description) {
		super.setDescription(description);
	}
}
