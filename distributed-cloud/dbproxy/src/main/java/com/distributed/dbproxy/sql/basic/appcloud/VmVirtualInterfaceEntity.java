package com.distributed.dbproxy.sql.basic.appcloud;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Idan on 2018/4/12.
 */
@Entity
@Table(name = "vm_virtual_interface")
@Data
public class VmVirtualInterfaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Basic
    @Column(name = "instance_uuid")
    private String instanceUuid;
    @Basic
    @Column()
    private String address;
    @Basic
    @Column(name = "mac")
    private String mac;
    @Basic
    @Column(name = "network_type")
    private String networkType;
    @Basic
    @Column(name = "ip_pool_id")
    private Integer ipPoolId;



}
