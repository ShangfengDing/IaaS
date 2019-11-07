package appcloud.dbproxy.mysql.model;

import appcloud.common.model.VmImageBack;
import appcloud.common.model.VmVolume;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lizhenhao on 2017/11/30.
 */
@Entity
@Table(name = "vm_image_backing")
public class VmImageBackTable extends VmImageBack {

    public VmImageBackTable() {
        super();
    }
    public VmImageBackTable(VmImageBack imageBack) {
        this.setId(imageBack.getId());
        this.setVolumeUuid(imageBack.getVolumeUuid());
        this.setDisplayName(imageBack.getDisplayName());
        this.setUserId(imageBack.getUserId());
        this.setAvailabilityZoneId(imageBack.getAvailabilityZoneId());
        this.setAvailabilityClusterId(imageBack.getAvailabilityClusterId());
        this.setParentVolumeUuid(imageBack.getParentVolumeUuid());
        this.setSonVolumeUuid(imageBack.getSonVolumeUuid());           this.setInstanceUuid(imageBack.getInstanceUuid());
        this.setActiveVolumeUuid(imageBack.getActiveVolumeUuid());
        this.setInstanceUuid(imageBack.getInstanceUuid());
        this.setProviderLocation(imageBack.getProviderLocation());
        this.setHostUuid(imageBack.getHostUuid());
        this.setCreatedTime(imageBack.getCreatedTime());
        this.setUpdatedTime(imageBack.getUpdatedTime());
        this.setDeletedTime(imageBack.getDeletedTime());
        this.setVolumeSize(imageBack.getVolumeSize());
        this.setBackUp(imageBack.isBackUp());
        this.setTop(imageBack.isTop());
        this.setImageStatus(imageBack.getImageStatus());
        this.setVolumeType(imageBack.getVolumeType());
        this.setUsageType(imageBack.getUsageType());
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return super.getId();
    }

    public void setId(Integer id) {
        super.setId(id);
    }

    @Column(name = "volume_uuid")
    public String getVolumeUuid() {
        return super.getVolumeUuid();
    }

    public void setVolumeUuid(String volumeUuid) {
        super.setVolumeUuid(volumeUuid);
    }

    @Column(name = "display_name")
    public String getDisplayName() {
        return super.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        super.setDisplayName(displayName);
    }


    @Column(name = "user_id")
    public Integer getUserId() {
        return super.getUserId();
    }

    public void setUserId(Integer userId) {
        super.setUserId(userId);
    }


    @Column(name = "availability_zone_id")
    public Integer getAvailabilityZoneId() {
        return super.getAvailabilityZoneId();
    }

    public void setAvailabilityZoneId(Integer zoneId) {
        super.setAvailabilityZoneId(zoneId);
    }

    @Column(name = "availability_cluster_id")
    public Integer getAvailabilityClusterId() {
        return super.getAvailabilityClusterId();
    }

    public void setAvailabilityClusterId(Integer clusterId) {
        super.setAvailabilityClusterId(clusterId);
    }


    @Column(name = "parent_volume_uuid")
    public String getParentVolumeUuid() {
        return super.getParentVolumeUuid();
    }

    public void setParentVolumeUuid(String parentVolumeUuid) {
        super.setParentVolumeUuid(parentVolumeUuid);
    }

    @Column(name = "son_volume_uuid")
    public String getSonVolumeUuid() {
        return super.getSonVolumeUuid();
    }

    public void setSonVolumeUuid(String sonVolumeUuid) {
        super.setSonVolumeUuid(sonVolumeUuid);
    }

    @Column(name = "active_volume_uuid")
    public String getActiveVolumeUuid() {
        return super.getActiveVolumeUuid();
    }

    public void setActiveVolumeUuid(String activeVolumeUuid) {
        super.setActiveVolumeUuid(activeVolumeUuid);
    }


    @Column(name = "instance_uuid")
    public String getInstanceUuid() {
        return super.getInstanceUuid();
    }

    public void setInstanceUuid(String instanceUuid) {
        super.setInstanceUuid(instanceUuid);
    }

    @Column(name = "provider_location")
    public String getProviderLocation() {
        return super.getProviderLocation();
    }

    public void setProviderLocation(String providerLocation) {
        super.setProviderLocation(providerLocation);
    }

    @Column(name = "host_uuid")
    public String getHostUuid() {
        return super.getHostUuid();
    }

    public void setHostUuid(String hostUuid) {
        super.setHostUuid(hostUuid);
    }

    @Column(name="created_time")
    public Timestamp getCreatedTime() {
        return super.getCreatedTime();
    }

    public void setCreatedTime(Timestamp createdTime) {
        super.setCreatedTime(createdTime);
    }


    @Column(name="update_time")
    public Timestamp getUpdatedTime() {
        return super.getUpdatedTime();
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        super.setUpdatedTime(updatedTime);
    }

    @Column(name="delete_time")
    public Timestamp getDeletedTime() {
        return super.getDeletedTime();
    }

    public void setDeletedTime(Timestamp deletedTime) {
        super.setDeletedTime(deletedTime);
    }

    @Column(name = "volume_size")
    public Integer getVolumeSize() {
        return super.getVolumeSize();
    }

    public void setVolumeSize(Integer volumeSize) {
        super.setVolumeSize(volumeSize);
    }

    @Column(name="is_backup")
    public boolean isBackUp() {
        return super.isBackUp();
    }

    public void setBackUp(boolean backUp) {
        super.setBackUp(backUp);
    }

    @Column(name = "is_top")
    public boolean isTop() {
        return super.isTop();
    }

    public void setTop(boolean top) {
        super.setTop(top);
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public VmImageBackStatusTypeEnum getImageStatus() {
        return super.getImageStatus();
    }

    public void setImageStatus(VmImageBackStatusTypeEnum imageStatus) {
        super.setImageStatus(imageStatus);
    }

    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    public VmVolume.VmVolumeTypeEnum getVolumeType() {
        return super.getVolumeType();
    }

    public void setVolumeType(VmVolume.VmVolumeTypeEnum volumeType) {
        super.setVolumeType(volumeType);
    }

    @Column(name = "usage_type")
    @Enumerated(EnumType.STRING)
    public VmVolume.VmVolumeUsageTypeEnum getUsageType() {
        return super.getUsageType();
    }

    public void setUsageType(VmVolume.VmVolumeUsageTypeEnum usageType) {
        super.setUsageType(usageType);
    }

}
