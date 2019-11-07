package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author XuanJiaxing
 * 
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmVolume {
    private Integer id;                            //自增主键
    private String volumeUuid;                     //全局唯一的标识
    private Integer userId;                        // FK
    
    /* for display */
    private Timestamp createdTime;                 //创建时间
    private Timestamp updatedTime;                 //上次更新时间
    private Timestamp deletedTime;                 //
    private String name;                           //名称
    private String displayName;                    //
    private String displayDescription;             //
    
    /* for create */
    private Integer availabilityZoneId;            // FK
    private Integer availabilityClusterId;         // FK
    private String hostUuid;                       // FK 
    
	private String imageUuid;                      // FK
	private String backupVolumeUuid;               // FK

    
    /**volume size in GB*/
    private Integer size;                          //大小
    /** raw ,qcow2 */
    private VmVolumeTypeEnum volumeType;           //
    /** system, data, iso, network ..etc **/
    private VmVolumeUsageTypeEnum usageType;
    
    /** relative path in storage pool */ 
    private String providerLocation;               //IP:/srv/nfs4/:volumes/a/z/uuid.img
    private String Ip;                             //not in database
    private String nfsRoot;                        //not in database
    private String imagefilePath;                  //not in database
    private String message;                        //not in database
    
    private VmVolumeStatusEnum status;             //
    
    /* for attach */
    private VmVolumeAttachStatusEnum attachStatus; //
    private String instanceUuid;                   //
    /** virtio , ide */
    private String bus;                            //
    /** vda, sda */
    private String mountPoint;                     //
    private Timestamp attachedTime;                //
    
    /* FK objects */
    private VmInstance vmInstance;                 //
    private VmImage image;
    private Host host;                             //
    private Cluster cluster;                       //
    private VmZone  zone;                         //
//	private VmVolume backupVolume;
    
    public VmVolume() {
        super();
    }

	public VmVolume(Integer id, String volumeUuid, Integer userId,
			Timestamp createdTime, Timestamp updatedTime,
			Timestamp deletedTime, String name, String displayName,
			String displayDescription, Integer availabilityZoneId,
			Integer availabilityClusterId, String hostUuid, String imageUuid,
			String backupVolumeUuid, Integer size, VmVolumeTypeEnum volumeType,
			VmVolumeUsageTypeEnum usageType, String providerLocation,
			String ip, String nfsRoot, String imagefilePath, String message,
			VmVolumeStatusEnum status, VmVolumeAttachStatusEnum attachStatus,
			String instanceUuid, String bus, String mountPoint,
			Timestamp attachedTime, VmInstance vmInstance, VmImage image,
			Host host, Cluster cluster, VmZone zone) {
		super();
		this.id = id;
		this.volumeUuid = volumeUuid;
		this.userId = userId;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.deletedTime = deletedTime;
		this.name = name;
		this.displayName = displayName;
		this.displayDescription = displayDescription;
		this.availabilityZoneId = availabilityZoneId;
		this.availabilityClusterId = availabilityClusterId;
		this.hostUuid = hostUuid;
		this.imageUuid = imageUuid;
		this.backupVolumeUuid = backupVolumeUuid;
		this.size = size;
		this.volumeType = volumeType;
		this.usageType = usageType;
		this.providerLocation = providerLocation;
		Ip = ip;
		this.nfsRoot = nfsRoot;
		this.imagefilePath = imagefilePath;
		this.message = message;
		this.status = status;
		this.attachStatus = attachStatus;
		this.instanceUuid = instanceUuid;
		this.bus = bus;
		this.mountPoint = mountPoint;
		this.attachedTime = attachedTime;
		this.vmInstance = vmInstance;
		this.image = image;
		this.host = host;
		this.cluster = cluster;
		this.zone = zone;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVolumeUuid() {
		return volumeUuid;
	}

	public void setVolumeUuid(String volumeUuid) {
		this.volumeUuid = volumeUuid;
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

	public String getHostUuid() {
		return hostUuid;
	}

	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public String getImageUuid() {
		return imageUuid;
	}

	public void setImageUuid(String imageUuid) {
		this.imageUuid = imageUuid;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public VmVolumeTypeEnum getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(VmVolumeTypeEnum volumeType) {
		this.volumeType = volumeType;
	}

	public String getProviderLocation() {
		return providerLocation;
	}

	public void setProviderLocation(String providerLocation) {
		if (providerLocation != null) {
			this.providerLocation = providerLocation;
			String[] strs = providerLocation.split(":");
			this.Ip = strs[0];
			this.nfsRoot = strs[1];
			this.imagefilePath = strs[2];
		} else {
			this.providerLocation = providerLocation;
			this.Ip = null;
			this.nfsRoot = null;
			this.imagefilePath = null;
		}
	}
	
	public void setProviderLocation(String ip, String nfsRoot, String imagefilePath) {
		this.Ip = ip;
		this.nfsRoot = nfsRoot;
		this.imagefilePath = imagefilePath;
		this.providerLocation = ip+":"+nfsRoot+":"+imagefilePath;
	}
	
	public String getIp() {
		return Ip;
	}

	public String getNfsRoot() {
		return nfsRoot;
	}

	public String getImagefilePath() {
		return imagefilePath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public VmVolumeStatusEnum getStatus() {
		return status;
	}

	public void setStatus(VmVolumeStatusEnum status) {
		this.status = status;
	}

	public VmVolumeAttachStatusEnum getAttachStatus() {
		return attachStatus;
	}

	public void setAttachStatus(VmVolumeAttachStatusEnum attachStatus) {
		this.attachStatus = attachStatus;
	}

	public String getInstanceUuid() {
		return instanceUuid;
	}

	public void setInstanceUuid(String instanceUuid) {
		this.instanceUuid = instanceUuid;
	}

	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public String getMountPoint() {
		return mountPoint;
	}

	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}

	public Timestamp getAttachedTime() {
		return attachedTime;
	}

	public void setAttachedTime(Timestamp attachedTime) {
		this.attachedTime = attachedTime;
	}

	public VmInstance getVmInstance() {
		return vmInstance;
	}

	public void setVmInstance(VmInstance vmInstance) {
		this.vmInstance = vmInstance;
	}

	public VmImage getImage() {
		return image;
	}

	public void setImage(VmImage image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "VmVolume [id=" + id + ", volumeUuid=" + volumeUuid
				+ ", userId=" + userId + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", deletedTime="
				+ deletedTime + ", name=" + name + ", displayName="
				+ displayName + ", displayDescription=" + displayDescription
				+ ", availabilityZoneId=" + availabilityZoneId
				+ ", availabilityClusterId=" + availabilityClusterId
				+ ", hostUuid=" + hostUuid + ", imageUuid=" + imageUuid
				+ ", backupVolumeUuid=" + backupVolumeUuid + ", size=" + size
				+ ", volumeType=" + volumeType + ", usageType=" + usageType
				+ ", providerLocation=" + providerLocation + ", Ip=" + Ip
				+ ", nfsRoot=" + nfsRoot + ", imagefilePath=" + imagefilePath
				+ ", message=" + message + ", status=" + status
				+ ", attachStatus=" + attachStatus + ", instanceUuid="
				+ instanceUuid + ", bus=" + bus + ", mountPoint=" + mountPoint
				+ ", attachedTime=" + attachedTime + ", vmInstance="
				+ vmInstance + ", image=" + image + ", host=" + host
				+ ", cluster=" + cluster + ", zone=" + zone + "]";
	}

	public Integer getAvailabilityZoneId() {
		return availabilityZoneId;
	}

	public void setAvailabilityZoneId(Integer availabilityZoneId) {
		this.availabilityZoneId = availabilityZoneId;
	}
	
    public Integer getAvailabilityClusterId() {
		return availabilityClusterId;
	}

	public void setAvailabilityClusterId(Integer availabilityClusterId) {
		this.availabilityClusterId = availabilityClusterId;
	}

	public VmZone getZone() {
		return zone;
	}

	public void setZone(VmZone zone) {
		this.zone = zone;
	}
	
    public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public String getBackupVolumeUuid() {
		return backupVolumeUuid;
	}

	public void setBackupVolumeUuid(String backupVolumeUuid) {
		this.backupVolumeUuid = backupVolumeUuid;
	}

	public VmVolumeUsageTypeEnum getUsageType() {
		return usageType;
	}

	public void setUsageType(VmVolumeUsageTypeEnum usageType) {
		this.usageType = usageType;
	}

	public static enum VmVolumeAttachStatusEnum {
        ATTACHED, DETACHED;

        public String toString() {
            switch (this) {
            case ATTACHED:
                return "attached";
            case DETACHED:
                return "detached";
            }
            return super.toString();
        }
    }

    public static enum VmVolumeStatusEnum {
        DEFINED, AVAILABLE, CREATING, DELETING, ERROR, ERROR_DELETING, DELETED;
//        , INUSE

        public String toString() {
            switch (this) {
            case DEFINED:
            	return "defined";
//            case INUSE:
//                return "inuse";
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
            case ERROR_DELETING:
                return "error_deleting";
            }

            return super.toString();
        }
    }
    
    public static enum VmVolumeTypeEnum {
    	QCOW2, RAW, ISO;//XXX
    	
    	public String toString() {
    		switch (this) {
    		case QCOW2:
    			return "qcow2";
    		case RAW:
    			return "raw";
    		case ISO:
    			return "iso";
    		}
    		return super.toString();
    	}
    }
    
    public static enum VmVolumeUsageTypeEnum {
    	SYSTEM, DATA, NETWORK, ISO ;//XXX
    	
    	public String toString() {
    		switch (this) {
    		case SYSTEM:
    			return "system";
    		case DATA:
    			return "data";
    		case NETWORK:
    			return "network";
    		case ISO:
    			return "iso";
    		}
    		return super.toString();
    	}
    }
}
