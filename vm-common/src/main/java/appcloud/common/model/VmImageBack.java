package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.Timestamp;

/**
 * Created by lizhenhao on 2017/11/30.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmImageBack {

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
    private Timestamp createdTime;					//创建时间
    private Timestamp updatedTime;					//上次更新时间
    private Timestamp deletedTime;
    private Integer volumeSize;
    private boolean isBackUp;
    private boolean isTop;
    private VmImageBackStatusTypeEnum imageStatus;
    /** raw ,qcow2 */
    private VmVolume.VmVolumeTypeEnum volumeType;           //
    /** system, data, iso, network ..etc **/
    private VmVolume.VmVolumeUsageTypeEnum usageType;

    /* FK objects */
    private VmInstance vmInstance;                 //
    private Host host;                             //
    private Cluster cluster;                       //
    private VmZone  zone;                         //

    public VmImageBack() {
        super();
    }

    public VmImageBack(Integer id, String volumeUuid, String displayName, Integer userId, Integer availabilityZoneId, Integer availabilityClusterId, String parentVolumeUuid, String sonVolumeUuid, String activeVolumeUuid, String instanceUuid, String providerLocation, String hostUuid, Timestamp createdTime, Timestamp updatedTime, Timestamp deletedTime, Integer volumeSize, boolean isBackUp, boolean isTop, VmImageBackStatusTypeEnum imageStatus, VmVolume.VmVolumeTypeEnum volumeType, VmVolume.VmVolumeUsageTypeEnum usageType) {
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
        this.isBackUp = isBackUp;
        this.isTop = isTop;
        this.imageStatus = imageStatus;
        this.volumeType = volumeType;
        this.usageType = usageType;
    }

    public static enum VmImageBackStatusTypeEnum  {
        AVALIABLE,
        CREATING,
        DEFINED,
        DELETED,
        ERROR;

        public String toString() {
            switch (this) {
                case AVALIABLE:
                    return "avaliable";
                case ERROR:
                    return "error";
                case CREATING:
                    return "creating";
                case DEFINED:
                    return "defined";
                case DELETED:
                    return "deleted";
            }
            return super.toString();
        }
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getParentVolumeUuid() {
        return parentVolumeUuid;
    }

    public void setParentVolumeUuid(String parentVolumeUuid) {
        this.parentVolumeUuid = parentVolumeUuid;
    }

    public String getSonVolumeUuid() {
        return sonVolumeUuid;
    }

    public void setSonVolumeUuid(String sonVolumeUuid) {
        this.sonVolumeUuid = sonVolumeUuid;
    }

    public String getInstanceUuid() {
        return instanceUuid;
    }

    public void setInstanceUuid(String instanceUuid) {
        this.instanceUuid = instanceUuid;
    }

    public String getProviderLocation() {
        return providerLocation;
    }

    public void setProviderLocation(String providerLocation) {
        this.providerLocation = providerLocation;
    }

    public void setProviderLocation(String ip, String nfsRoot, String imagefilePath) {
        this.providerLocation = ip+":"+nfsRoot+":"+imagefilePath;
    }

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
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

    public boolean isBackUp() {
        return isBackUp;
    }

    public void setBackUp(boolean backUp) {
        isBackUp = backUp;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public VmImageBackStatusTypeEnum getImageStatus() {
        return imageStatus;
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

    public String getActiveVolumeUuid() {
        return activeVolumeUuid;
    }

    public void setActiveVolumeUuid(String activeVolumeUuid) {
        this.activeVolumeUuid = activeVolumeUuid;
    }

    public void setImageStatus(VmImageBackStatusTypeEnum imageStatus) {
        this.imageStatus = imageStatus;
    }

    public VmVolume.VmVolumeTypeEnum getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(VmVolume.VmVolumeTypeEnum volumeType) {
        this.volumeType = volumeType;
    }

    public VmVolume.VmVolumeUsageTypeEnum getUsageType() {
        return usageType;
    }

    public void setUsageType(VmVolume.VmVolumeUsageTypeEnum usageType) {
        this.usageType = usageType;
    }
    //-----------------------

    public VmInstance getVmInstance() {
        return vmInstance;
    }

    public void setVmInstance(VmInstance vmInstance) {
        this.vmInstance = vmInstance;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public VmZone getZone() {
        return zone;
    }

    public void setZone(VmZone zone) {
        this.zone = zone;
    }
}
