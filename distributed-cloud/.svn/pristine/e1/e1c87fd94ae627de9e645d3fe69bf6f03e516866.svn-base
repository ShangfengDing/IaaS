package com.distributed.dbproxy.sql.basic.appcloud;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Idan on 2018/3/12.
 */
@Entity
@Table(name = "vm_instance")
@Data
public class VmInstanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Basic
    @Column
    private String uuid; // 全局唯一标识
    @Basic
    @Column
    private String name; // 虚拟机名称
    @Basic
    @Column(name = "user_id")
    private Integer userId; // 使用者标识
    @Basic
    @Column(name = "host_uuid")
    private String hostUuid; // 所在主机的uuid
    @Basic
    @Column(name = "availability_cluster_id")
    private Integer availabilityClusterId; // 所在计算节点集群id
    @Basic
    @Column(name = "availability_zone_id")
    private Integer availabilityZoneId;
    @Basic
    @Column(name = "scheduled_time")
    private Timestamp scheduledTime; // 调度时间
    @Basic
    @Column(name = "launched_time")
    private Timestamp launchedTime; // 首次启动时间
    @Basic
    @Column(name = "updated_time")
    private Timestamp updatedTime; // 上次更新时间
    @Basic
    @Column
    private Integer progress;
    @Basic
    @Column(name = "task_status")
    private String taskStatus;
    @Basic
    @Column(name = "vm_status")
    private String vmStatus;
    @Basic
    @Column(name = "image_uuid")
    private String imageUuid; // 公共镜像的标识
    @Basic
    @Column(name = "instance_type_id")
    private Integer instanceTypeId;
    @Basic
    @Column(name = "root_device_location")
    private String rootDeviceLocation;
    @Basic
    @Column(name = "security_group_id")
    private Integer securityGroupId;
    @Basic
    @Column(name = "os_mode")
    private String osMode;
    @Basic
    @Column(name = "os_arch")
    private String osArch;
    @Basic
    @Column(name = "os_type")
    private String osType;
    @Basic
    @Column(name = "vnc_port")
    private String vncPort;
    @Basic
    @Column(name = "vnc_password")
    private String vncPassword;
}
