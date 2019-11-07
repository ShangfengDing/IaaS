package com.distributed.dbproxy.sql.basic.appcloud;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lizhenhao on 2017/11/30.
 */
@Entity
@Table(name = "vm_image_backing")
@Data
public class VmImageBackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Basic
    @Column(name = "volume_uuid")
    private String volumeUuid;
    @Basic
    @Column(name = "display_name")
    private String displayName;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "availability_zone_id")
    private Integer availabilityZoneId;
    @Basic
    @Column(name = "availability_cluster_id")
    private Integer availabilityClusterId;
    @Basic
    @Column(name = "parent_volume_uuid")
    private String parentVolumeUuid;
    @Basic
    @Column(name = "son_volume_uuid")
    private String sonVolumeUuid;
    @Basic
    @Column(name = "active_volume_uuid")
    private String activeVolumeUuid;
    @Basic
    @Column(name = "instance_uuid")
    private String instanceUuid;
    @Basic
    @Column(name = "provider_location")
    private String providerLocation;
    @Basic
    @Column(name = "host_uuid")
    private String hostUuid;
    @Basic
    @Column(name = "created_time")
    private Timestamp createdTime;
    @Basic
    @Column(name = "update_time")
    private Timestamp updatedTime;
    @Basic
    @Column(name = "delete_time")
    private Timestamp deletedTime;
    @Basic
    @Column(name = "volume_size")
    private Integer volumeSize;
    @Basic
    @Column(name = "is_backup")
    private boolean backUp;
    @Basic
    @Column(name = "is_top")
    private boolean top;
    @Basic
    @Column(name = "status")
    private String imageStatus;
    @Basic
    @Column(name = "usage_type")
    private String usageType;
    @Basic
    @Column(name = "image_type")
    private String imageType;


    public VmImageBackEntity() {
        super();
    }

    public VmImageBackEntity(Integer id, String volumeUuid, String displayName, Integer userId, Integer availabilityZoneId, Integer availabilityClusterId,
                             String parentVolumeUuid, String sonVolumeUuid, String activeVolumeUuid, String instanceUuid, String providerLocation,
                             String hostUuid, Timestamp createdTime, Timestamp updatedTime, Timestamp deletedTime, Integer volumeSize, boolean isBackUp,
                             boolean isTop, String imageStatus) {
        this.id = id;
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
        this.backUp = isBackUp;
        this.top = isTop;
        this.imageStatus = imageStatus;
    }
}
