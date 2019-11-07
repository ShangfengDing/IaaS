package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Idan on 2018/3/13.
 */
@Data
public class VmHost implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private String uuid;
    private String type;
    private String ip;
    private String location;

    private Integer publicVLAN; //公网VLAN号
    private Integer privateVLAN;//私网VLAN号
    private Integer availabilityZoneId;//所属数据中心 手动设定
    private Integer availabilityClusterId;	//计算节点所属集群id 在前台划归

    private String os;			//操作系统
    private Integer cpuMhz;		//cpu主频
    private Integer cpuCore;	//cpu核心数
    private Integer memoryMb;	//内存总大小
    private Integer diskGb;		//硬盘总大小
    private Integer networkMb;	//网络带宽

    private String status;
    private String extend;
    private String name;
    private String hypervisorType;
    private String hypervisorVersion;

    public VmHost() {
    }
}
