package com.distributed.dbproxy.sql.basic.appcloud;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Idan on 2018/6/10.
 */

@Entity
@Table(name = "vm_zone")
@Data
public class VmZoneEntity{

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Basic
    @Column
    private String name;
    @Basic
    @Column(name = "region_id")
    private String regionId;
    @Basic
    @Column(name = "zone_id")
    private String zoneId;
    @Basic
    @Column(name = "created_time")
    private Timestamp createdTime;
    @Basic
    @Column(name = "updated_time")
    private Timestamp updatedTime;

    public VmZoneEntity() {
        super();
    }

    public VmZoneEntity(Integer id, String name, String regionId, String zoneId) {
        super();
        this.id = id;
        this.name = name;
        this.regionId = regionId;
        this.zoneId = zoneId;
    }

}
