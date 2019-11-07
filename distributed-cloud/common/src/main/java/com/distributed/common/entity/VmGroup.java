package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Idan on 2018/3/14.
 */

@Data
public class VmGroup implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Boolean selectCluster;
    private Boolean publishImage;
    private Integer maxNumberOfInstance;
    private Integer maxNumberOfDisk;
    private Integer maxNumberOfBackup;
    private Integer maxNumberOfSnapshot;
    private String availableClusters;
    private String description;
    private String secretKey;

    public VmGroup() {
    }
}
