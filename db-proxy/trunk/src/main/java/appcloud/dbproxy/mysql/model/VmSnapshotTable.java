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

import appcloud.common.model.VmSnapshot;
/**
 * @author XuanJiaxing
 *
 */

@Entity
@Table(name = "vm_snapshot")
public class VmSnapshotTable extends VmSnapshot{

	public VmSnapshotTable(){
		super();
	}
	
	public VmSnapshotTable(VmSnapshot snapshot){
		super.setId(snapshot.getId());
		super.setUuid(snapshot.getUuid());
		super.setName(snapshot.getName());
		super.setUserId(snapshot.getUserId());
		super.setVolumeId(snapshot.getVolumeId());
		super.setVolumeUuid(snapshot.getVolumeUuid());
		super.setCreatedTime(snapshot.getCreatedTime());
		super.setUpdatedTime(snapshot.getUpdatedTime());
		super.setDeletedTime(snapshot.getDeletedTime());
		super.setVolumeSize(snapshot.getVolumeSize());
		super.setStatus(snapshot.getStatus());
		super.setDisplayName(snapshot.getDisplayName());
		super.setDisplayDescription(snapshot.getDisplayDescription());
		super.setVmVolume(snapshot.getVmVolume());
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	public Integer getId() {
		return super.getId();
	}
	public void setId(Integer id) {
		super.setId(id);
	}
	
    @Column(name = "uuid")
    public String getUuid() {
        return super.getUuid();
    }

    public void setUuid(String uuid) {
        super.setUuid(uuid);
    }
    
    @Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}

    @Column(name="user_id")
	public Integer getUserId() {
		return super.getUserId();
	}
	public void setUserId(Integer userId) {
		super.setUserId(userId);
	}

    @Column(name="volume_id")
	public Integer getVolumeId() {
		return super.getVolumeId();
	}
	public void setVolumeId(Integer volumeId) {
		super.setVolumeId(volumeId);
	}
	
	@Column(name = "volume_uuid")
    public String getVolumeUuid() {
        return super.getVolumeUuid();
    }

    public void setVolumeUuid(String volumeUuid) {
        super.setVolumeUuid(volumeUuid);
    }
    
    @Column(name="created_time")
	public Timestamp getCreatedTime() {
		return super.getCreatedTime();
	}
	public void setCreatedTime(Timestamp createdTime) {
		super.setCreatedTime(createdTime);
	}

    @Column(name="updated_time")
	public Timestamp getUpdatedTime() {
		return super.getUpdatedTime();
	}
	public void setUpdatedTime(Timestamp updatedTime) {
		super.setUpdatedTime(updatedTime);
	}

    @Column(name="deleted_time")
	public Timestamp getDeletedTime() {
		return super.getDeletedTime();
	}
	public void setDeletedTime(Timestamp deletedTime) {
		super.setDeletedTime(deletedTime);
	}

    @Column(name="volume_size")
	public Integer getVolumeSize() {
		return super.getVolumeSize();
	}
	public void setVolumeSize(Integer volumeSize) {
		super.setVolumeSize(volumeSize);
	}

    @Column(name="status")
	@Enumerated(EnumType.STRING)
	public VmSnapshotStatusEnum getStatus() {
		return super.getStatus();
	}
	public void setStatus(VmSnapshotStatusEnum status) {
		super.setStatus(status);
	}

    @Column(name="display_name")
	public String getDisplayName() {
		return super.getDisplayName();
	}
	public void setDisplayName(String displayName) {
		super.setDisplayName(displayName);
	}

    @Column(name="display_description")
	public String getDisplayDescription() {
		return super.getDisplayDescription();
	}
	public void setDisplayDescription(String displayDescription) {
		super.setDisplayDescription(displayDescription);
	}

}
