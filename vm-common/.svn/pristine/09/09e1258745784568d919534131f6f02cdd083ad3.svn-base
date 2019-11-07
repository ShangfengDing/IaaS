package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.sql.Timestamp;

/**
 * @author XuanJiaxing
 * 
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmSnapshot {
	private Integer id;                            //自增主键
	private String name;                           //
	private String uuid;                           //
	private Integer userId;
	private Integer volumeId;
	private String volumeUuid;
	private Timestamp createdTime;                 //
	private Timestamp updatedTime;                 //XXX no update?
	private Timestamp deletedTime;
	private Integer volumeSize;
	private VmSnapshotStatusEnum status;
	private String displayName;
	private String displayDescription;

	private VmVolume vmVolume;

	public VmSnapshot() {
		super();
	}

	public VmSnapshot(Integer id, String uuid, String name, Integer userId,
			Integer volumeId, String volumeUuid, Timestamp createdTime, Timestamp updatedTime,
			Timestamp deletedTime, Integer volumeSize, VmSnapshotStatusEnum status,
			String displayName, String displayDescription, VmVolume vmVolume) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.userId = userId;
		this.volumeId = volumeId;
		this.volumeUuid = volumeUuid;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.deletedTime = deletedTime;
		this.volumeSize = volumeSize;
		this.status = status;
		this.displayName = displayName;
		this.displayDescription = displayDescription;
		this.vmVolume = vmVolume;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(Integer volumeId) {
		this.volumeId = volumeId;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Timestamp getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Timestamp deletedTime) {
		this.deletedTime = deletedTime;
	}

	public Integer getVolumeSize() {
		return volumeSize;
	}

	public void setVolumeSize(Integer volumeSize) {
		this.volumeSize = volumeSize;
	}

	public VmSnapshotStatusEnum getStatus() {
		return status;
	}

	public void setStatus(VmSnapshotStatusEnum status) {
		this.status = status;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}

	public VmVolume getVmVolume() {
		return vmVolume;
	}

	public void setVmVolume(VmVolume vmVolume) {
		this.vmVolume = vmVolume;
	}

	@Override
	public String toString() {
		return "VmSnapshot [id=" + id + ", uuid= " + uuid 
				+ ", name=" + name + ", userId=" + userId
				+ ", volumeId=" + volumeId + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", deletedTime="
				+ deletedTime + ", volumeSize=" + volumeSize + ", status="
				+ status + ", displayName=" + displayName
				+ ", displayDescription=" + displayDescription + ", vmVolume="
				+ vmVolume + "]";
	}
	
    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getVolumeUuid() {
		return volumeUuid;
	}

	public void setVolumeUuid(String volumeUuid) {
		this.volumeUuid = volumeUuid;
	}

	public static enum VmSnapshotStatusEnum {
        DEFINED, CREATING, AVAILABLE, DELETING, ERROR, DELETED;

        public String toString() {
            switch (this) {
            case DEFINED:
                return "defined";
            case AVAILABLE:
                return "available";
            case CREATING:
                return "creating";
            case DELETING:
                return "deleting";
            case ERROR:
                return "error";
            case DELETED:
            	return "deleted";
            }

            return super.toString();
        }
    }
}
