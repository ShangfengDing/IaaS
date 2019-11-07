package com.distributed.dbproxy.sql.basic.appcloud;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Idan on 2018/3/14.
 */
@Entity
@Table(name = "vm_group")
@Data
public class VmGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Basic
    @Column
    private String name;
    @Basic
    @Column(name = "select_cluster")
    private Boolean selectCluster;
    @Basic
    @Column(name = "publish_image")
    private Boolean publishImage;
    @Basic
    @Column(name = "max_number_of_instance")
    private Integer maxNumberOfInstance;
    @Basic
    @Column(name = "max_number_of_disk")
    private Integer maxNumberOfDisk;
    @Basic
    @Column(name = "max_number_of_backup")
    private Integer maxNumberOfBackup;
    @Basic
    @Column(name = "max_number_of_snapshot")
    private Integer maxNumberOfSnapshot;
    @Basic
    @Column(name = "available_clusters")
    private String availableClusters;
    @Basic
    @Column
    private String description;
    @Basic
    @Column(name = "secret_key")
    private String secretKey;

}
