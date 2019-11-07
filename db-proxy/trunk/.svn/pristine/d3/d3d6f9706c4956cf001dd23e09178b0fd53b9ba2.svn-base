package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmVolume;

/**
 * @author XuanJiaxing
 * 
 */
@Entity
@Table(name = "vm_volume")
public class VmVolumeTable extends VmVolume {

    public VmVolumeTable() {
        super();
    }

    public VmVolumeTable(VmVolume volume) {
        this.setId(volume.getId());
        this.setVolumeUuid(volume.getVolumeUuid());
        this.setName(volume.getName());
        this.setUserId(volume.getUserId());
        this.setHostUuid(volume.getHostUuid());
        this.setAvailabilityClusterId(volume.getAvailabilityClusterId());
        this.setAvailabilityZoneId(volume.getAvailabilityZoneId());
        this.setCreatedTime(volume.getCreatedTime());
        this.setUpdatedTime(volume.getUpdatedTime());
        this.setDeletedTime(volume.getDeletedTime());
        this.setSize(volume.getSize());
        this.setVolumeType(volume.getVolumeType());
        this.setUsageType(volume.getUsageType());
        this.setAttachStatus(volume.getAttachStatus());
        this.setStatus(volume.getStatus());
        this.setDisplayName(volume.getDisplayName());
        this.setDisplayDescription(volume.getDisplayDescription());
        this.setProviderLocation(volume.getProviderLocation());
        this.setInstanceUuid(volume.getInstanceUuid());
        this.setMountPoint(volume.getMountPoint());
        this.setAttachedTime(volume.getAttachedTime());
        this.setBus(volume.getBus());
        this.setImageUuid(volume.getImageUuid());
        this.setBackupVolumeUuid(volume.getBackupVolumeUuid());
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

    public void setVolumeUuid(String uuid) {
        super.setVolumeUuid(uuid);
    }
    
    @Column(name = "backup_volume_uuid")
    public String getBackupVolumeUuid() {
        return super.getBackupVolumeUuid();
    }

    public void setBackupVolumeUuid(String backupUuid) {
        super.setBackupVolumeUuid(backupUuid);
    }

    @Column(name = "name")
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    @Column(name = "user_id")
    public Integer getUserId() {
        return super.getUserId();
    }

    public void setUserId(Integer userId) {
        super.setUserId(userId);
    }

    @Column(name = "host_uuid")
    public String getHostUuid() {
        return super.getHostUuid();
    }

    public void setHostUuid(String hostUuid) {
        super.setHostUuid(hostUuid);
    }

    @Column(name = "availability_cluster_id")
    public Integer getAvailabilityClusterId() {
        return super.getAvailabilityClusterId();
    }

    public void setAvailabilityClusterId(Integer availabilityClusterId) {
        super.setAvailabilityClusterId(availabilityClusterId);
    }
    
    @Column(name = "availability_zone_id")
    public Integer getAvailabilityZoneId() {
        return super.getAvailabilityZoneId();
    }

    public void setAvailabilityZoneId(Integer availabilityZoneId) {
        super.setAvailabilityZoneId(availabilityZoneId);
    }
    
    @Column(name = "created_time")
    public Timestamp getCreatedTime() {
        return super.getCreatedTime();
    }

    public void setCreatedTime(Timestamp createdTime) {
        super.setCreatedTime(createdTime);
    }

    @Column(name = "updated_time")
    public Timestamp getUpdatedTime() {
        return super.getUpdatedTime();
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        super.setUpdatedTime(updatedTime);
    }

    @Column(name = "deleted_time")
    public Timestamp getDeletedTime() {
        return super.getDeletedTime();
    }

    public void setDeletedTime(Timestamp deletedTime) {
        super.setDeletedTime(deletedTime);
    }

    @Column(name = "size")
    public Integer getSize() {
        return super.getSize();
    }

    public void setSize(Integer size) {
        super.setSize(size);
    }

    @Column(name = "volume_type")
    @Enumerated(EnumType.STRING)
    public VmVolumeTypeEnum getVolumeType() {
        return super.getVolumeType();
    }

    public void setVolumeType(VmVolumeTypeEnum volumeType) {
        super.setVolumeType(volumeType);
    }
    
    @Column(name = "usage_type")
    @Enumerated(EnumType.STRING)
    public VmVolumeUsageTypeEnum getUsageType() {
        return super.getUsageType();
    }

    public void setUsageType(VmVolumeUsageTypeEnum usageType) {
        super.setUsageType(usageType);
    }

    @Column(name = "attach_status")
    @Enumerated(EnumType.STRING)
    public VmVolumeAttachStatusEnum getAttachStatus() {
        return super.getAttachStatus();
    }

    public void setAttachStatus(VmVolumeAttachStatusEnum attachStatus) {
        super.setAttachStatus(attachStatus);
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public VmVolumeStatusEnum getStatus() {
        return super.getStatus();
    }

    public void setStatus(VmVolumeStatusEnum status) {
        super.setStatus(status);
    }

    @Column(name = "display_name")
    public String getDisplayName() {
        return super.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        super.setDisplayName(displayName);
    }

    @Column(name = "display_description")
    public String getDisplayDescription() {
        return super.getDisplayDescription();
    }

    public void setDisplayDescription(String displayDescription) {
        super.setDisplayDescription(displayDescription);
    }

    @Column(name = "provider_location")
    public String getProviderLocation() {
        return super.getProviderLocation();
    }

    public void setProviderLocation(String providerLocation) {
        super.setProviderLocation(providerLocation);
    }

    @Column(name = "instance_uuid")
    public String getInstanceUuid() {
        return super.getInstanceUuid();
    }

    public void setInstanceUuid(String uuid) {
        super.setInstanceUuid(uuid);
    }
    
    @Column(name = "image_uuid")
    public String getImageUuid() {
        return super.getImageUuid();
    }

    public void setImageUuid(String uuid) {
        super.setImageUuid(uuid);
    }

    @Column(name = "mount_point")
    public String getMountPoint() {
        return super.getMountPoint();
    }

    public void setMountPoint(String mountPoint) {
        super.setMountPoint(mountPoint);
    }

    @Column(name = "attached_time")
    public Timestamp getAttachedTime() {
        return super.getAttachedTime();
    }

    public void setAttachedTime(Timestamp attachedTime) {
        super.setAttachedTime(attachedTime);
    }

    @Column(name = "bus")
    public String getBus() {
        return super.getBus();
    }

    public void setBus(String bus) {
        super.setBus(bus);
    }

}
