package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Idan on 2018/3/12.
 */
@Data
public class VmInstance implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private String uuid; // 全局唯一标识
    private String name; // 虚拟机名称
    private Integer userId; // 使用者标识
    private String hostUuid; // 所在主机的uuid
    private Integer availabilityClusterId; // 所在计算节点集群id
    private Integer availabilityZoneId;
    private Timestamp scheduledTime; // 调度时间
    private Timestamp launchedTime; // 首次启动时间
    private Timestamp updatedTime; // 上次更新时间
    private Integer progress;
    private String taskStatus;
    private String vmStatus;
    private String imageUuid; // 公共镜像的标识
    private Integer instanceTypeId;
    private String rootDeviceLocation;
    private Integer securityGroupId;
    private String osMode;
    private String osArch;
    private String osType;
    private String vncPort;
    private String vncPassword;

    // 详细信息
    //TODO 缺少参数，需要补充，不然恢复的时候会参数不对
    private String displayDescription;
    private String instanceChargeType;
    private String instanceChargeLength;
    private String internetChargeType;
    private Integer maxBandwidth;
    private String systemDiskCategory;
    private String systemDiskSize;
    private String priVmMac; //必须要有的
    private String pubVmMac; //必须要有的
    private String appkeyId; //必须要有的
    private String appkeySecret; //必须要有的


    public VmInstance() {
    }
}
