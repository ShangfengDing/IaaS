package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Idan on 2018/6/10.
 */

@Data
public class VmZone implements Serializable{

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String regionId;
    private String zoneId;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    public VmZone() {
        super();
    }

    public VmZone(Integer id, String name, String regionId, String zoneId) {
        super();
        this.id = id;
        this.name = name;
        this.regionId = regionId;
        this.zoneId = zoneId;
    }

}
