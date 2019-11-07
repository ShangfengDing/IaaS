package com.distributed.dbproxy.sql.basic.distribution;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Idan on 2017/12/7.
 */
@Entity
@Table(name = "instance_back_info")
@Data
public class InstanceBackInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    /**
     * 这里是备份源主机的instanceUuid
     */
    @Basic
    @Column(name = "backup_uuid")
    private String backupUuid;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "image_uuid")
    private String imageUuid;
    @Basic
    @Column(name = "host_uuid")
    private String hostUuid;
    @Basic
    @Column(name = "instance_type")
    private String instanceType;
    @Basic
    @Column(name = "security_group_id")
    private Integer securityGroupId;
    @Basic
    @Column(name = "display_name")
    private String displayName;
    @Basic
    @Column(name = "display_description")
    private String displayDescription;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "pri_vm_mac")
    private String priVmMac;
    @Basic
    @Column(name = "pub_vm_mac")
    private String pubVmMac;
    @Basic
    @Column(name = "instance_charge_type")
    private String instanceChargeType;
    @Basic
    @Column(name = "instance_charge_length")
    private String instanceChargeLength;
    @Basic
    @Column(name = "internet_charge_type")
    private String internetChargeType;
    @Basic
    @Column(name = "max_bandwidth")
    private Integer maxBandwidth;
    @Basic
    @Column(name = "system_disk_category")
    private Integer systemDiskCategory;
    @Basic
    @Column(name = "system_disk_size")
    private Integer systemDiskSize;
    @Basic
    @Column(name = "expiration_day")
    private String expirationDay;
    @Basic
    @Column(name = "create_time")
    private Timestamp createTime;
    @Basic
    @Column(name = "appkey_id")
    private String appkeyId;
    @Basic
    @Column(name = "appkey_secret")
    private String appkeySecret;
}
