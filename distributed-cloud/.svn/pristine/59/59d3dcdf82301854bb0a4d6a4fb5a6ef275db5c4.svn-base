package com.distributed.dbproxy.sql.basic.appcloud;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Idan on 2018/3/13.
 */
@Entity
@Table(name = "vm_host")
@Data
public class VmHostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Basic
    @Column
    private String uuid;
    @Basic
    @Column
    private String type;
    @Basic
    @Column
    private String ip;
    @Basic
    @Column
    private String location;

    @Basic
    @Column(name = "public_vlan")
    private Integer publicVLAN; //公网VLAN号
    @Basic
    @Column(name = "private_vlan")
    private Integer privateVLAN;//私网VLAN号
    @Basic
    @Column(name = "availability_zone_id")
    private Integer availabilityZoneId;//所属数据中心 手动设定
    @Basic
    @Column(name = "availability_cluster_id")
    private Integer availabilityClusterId;	//计算节点所属集群id 在前台划归

    @Basic
    @Column
    private String os;			//操作系统
    @Basic
    @Column(name = "cpu_mhz")
    private Integer cpuMhz;		//cpu主频
    @Basic
    @Column(name = "cpu_core")
    private Integer cpuCore;	//cpu核心数
    @Basic
    @Column(name = "memory_mb")
    private Integer memoryMb;	//内存总大小
    @Basic
    @Column(name = "disk_gb")
    private Integer diskGb;		//硬盘总大小
    @Basic
    @Column(name = "network_mb")
    private Integer networkMb;	//网络带宽

    @Basic
    @Column
    private String status;
    @Basic
    @Column
    private String extend;
    @Basic
    @Column
    private String name;
    @Basic
    @Column(name = "hypervisor_type")
    private String hypervisorType;
    @Basic
    @Column(name = "hypervisor_version")
    private String hypervisorVersion;

}
