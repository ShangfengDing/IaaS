package com.distributed.dbproxy.sql.basic.distribution;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Idan on 2017/12/7.
 */
@Entity
@Table(name = "vm_backup")
@Data
public class VmBackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Basic
    @Column
    private String uuid;
    @Basic
    @Column(name = "instance_uuid")
    private String instanceUuid;
    @Basic
    @Column(name = "source_data_center")
    private String sourceDataCenter;
    @Basic
    @Column(name = "dest_data_center")
    private String destDataCenter;
    @Basic
    @Column(name = "source_host_uuid")
    private String sourceHostUuid;
    @Basic
    @Column(name = "dest_host_uuid")
    private String destHostUuid;
    @Basic
    @Column(name = "backup_ip")
    private String backIp;
    @Basic
    @Column
    private String type;
    @Basic
    @Column
    private String status;
    @Basic
    @Column
    private Integer version;
    @Basic
    @Column(name = "create_time")
    private Timestamp createTime;
    @Basic
    @Column(name = "store_location")
    private String storeLocation;

}
