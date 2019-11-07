package appcloud.dbproxy.mysql.model;


import appcloud.common.model.VmSummary;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vm_summary")

public class VmSummaryTable extends VmSummary {

    public VmSummaryTable(){super();}

    public VmSummaryTable(VmSummary summary){
        this.setId(summary.getId());
        this.setUuid(summary.getUuid());
        this.setName(summary.getName());
        this.setType(summary.getType());
        this.setModel(summary.getModel());
        this.setPosition(summary.getPosition());
        this.setDescription(summary.getDescription());
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

    @Basic
    @Column(name = "uuid")
    public String getUuid() { return super.getUuid();}
    public void setUuid(String uuid) { super.setUuid(uuid);}

    @Column(name = "name")
    public String getName() {
        return super.getName();
    }
    public void setName(String name) {
        super.setName(name);
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public VmSummaryTypeEnum getType() {
        return super.getType();
    }
    public void setType(VmSummaryTypeEnum type) {
        super.setType(type);
    }

    @Column(name = "model")
    public String getModel() {
        return super.getModel();
    }
    public void setModel(String model) {
        super.setModel(model);
    }

    @Column(name = "position")
    public String getPosition() {
        return super.getPosition();
    }
    public void setPosition(String position) {
        super.setPosition(position);
    }

    @Column(name = "description")
    public String getDescription() {
        return super.getDescription();
    }
    public void setDescription(String description) {
        super.setDescription(description);
    }
}
