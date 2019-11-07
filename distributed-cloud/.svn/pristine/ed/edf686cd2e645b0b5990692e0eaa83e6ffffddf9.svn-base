package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Idan on 2017/12/7.
 */

@Data
public class InstanceBackInfo implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private String backupUuid;
    private Integer userId;
    private String imageUuid;
    private String hostUuid;
    private String instanceType;
    private Integer securityGroupId;
    private String displayName;
    private String displayDescription;
    private String password;
    private String priVmMac;
    private String pubVmMac;
    private String instanceChargeType;
    private String instanceChargeLength;
    private String internetChargeType;
    private Integer maxBandwidth;
    private String systemDiskCategory;
    private String systemDiskSize;
    private String expirationDay;
    private String appkeyId;
    private String appkeySecret;
    private Timestamp createTime;

    public InstanceBackInfo() {
    }

    public InstanceBackInfo(String backupUuid, Integer userId, String imageUuid, String instanceType, Integer securityGroupId, String displayName,
                            String displayDescription, String password, String priVmMac, String pubVmMac, String instanceChargeType,
                            String instanceChargeLength, String internetChargeType, Integer maxBandwidth, String systemDiskCategory,
                            String systemDiskSize, String expirationDay, String appkeyId, String appkeySecret, Timestamp createTime) {
        this.backupUuid = backupUuid;
        this.userId = userId;
        this.imageUuid = imageUuid;
        this.instanceType = instanceType;
        this.securityGroupId = securityGroupId;
        this.displayName = displayName;
        this.displayDescription = displayDescription;
        this.password = password;
        this.priVmMac = priVmMac;
        this.pubVmMac = pubVmMac;
        this.instanceChargeType = instanceChargeType;
        this.instanceChargeLength = instanceChargeLength;
        this.internetChargeType = internetChargeType;
        this.maxBandwidth = maxBandwidth;
        this.systemDiskCategory = systemDiskCategory;
        this.systemDiskSize = systemDiskSize;
        this.expirationDay = expirationDay;
        this.appkeyId = appkeyId;
        this.appkeySecret = appkeySecret;
        this.createTime = createTime;
    }
}
