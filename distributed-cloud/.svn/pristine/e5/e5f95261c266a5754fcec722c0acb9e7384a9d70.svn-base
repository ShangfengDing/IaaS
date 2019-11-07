package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lizhenhao on 2017/11/30.
 */
@Data
public class VmImageBack implements Serializable{

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private String volumeUuid;
    private String displayName;
    private Integer userId;
    private Integer availabilityZoneId;
    private Integer availabilityClusterId;
    private String parentVolumeUuid;
    private String sonVolumeUuid;
    private String activeVolumeUuid;
    private String instanceUuid;
    private String providerLocation;
    private String hostUuid;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Timestamp deletedTime;
    private Integer volumeSize;
    private boolean backUp;
    private boolean top;
    private String imageStatus;


    public VmImageBack() {}

    public VmImageBack(String volumeUuid, String displayName, Integer userId, Integer availabilityZoneId, Integer availabilityClusterId,
                       String parentVolumeUuid, String sonVolumeUuid, String activeVolumeUuid, String instanceUuid, String providerLocation,
                       String hostUuid, Timestamp createdTime, Timestamp updatedTime, Timestamp deletedTime, Integer volumeSize, boolean backUp,
                       boolean top, String imageStatus) {
        this.volumeUuid = volumeUuid;
        this.displayName = displayName;
        this.userId = userId;
        this.availabilityZoneId = availabilityZoneId;
        this.availabilityClusterId = availabilityClusterId;
        this.parentVolumeUuid = parentVolumeUuid;
        this.sonVolumeUuid = sonVolumeUuid;
        this.activeVolumeUuid = activeVolumeUuid;
        this.instanceUuid = instanceUuid;
        this.providerLocation = providerLocation;
        this.hostUuid = hostUuid;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.deletedTime = deletedTime;
        this.volumeSize = volumeSize;
        this.backUp = backUp;
        this.top = top;
        this.imageStatus = imageStatus;
    }
}
