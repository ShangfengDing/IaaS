package appcloud.common.model;

import java.sql.Timestamp;
import java.util.List;

import appcloud.common.model.VmImage.VmImageOSArchEnum;
import appcloud.common.model.VmImage.VmImageOSModeEnum;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author haowei.yu
 * @ClassName: VmInstance
 * @date 2013-4-3 上午11:38:08
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmInstance {
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
    private TaskStatusEnum taskStatus;
    private VmStatusEnum vmStatus;
    private String imageUuid; // 公共镜像的标识
    private Integer instanceTypeId;
    private String rootDeviceLocation;
    private Integer securityGroupId;
    private VmImageOSModeEnum osMode;
    private VmImageOSArchEnum osArch;
    private VmImageOSTypeEnum osType;
    private String vncPort;
    private String vncPassword;
    private Integer recovery;

    // 注意这个host跟云海本身host的异同
    private Host host;
    // 注意这个cluster跟云海本身cluster的异同
    private Cluster cluster;
    private VmZone vmZone;
    private VmImage VmImage;
    private VmInstanceType vmInstanceType;
    private List<VmVolume> vmVolumes;
    private List<VmSecurityGroupRule> vmSecurityGroupRules;
    private List<VmInstanceMetadata> vmInstanceMetadatas;
    private List<VmVirtualInterface> vmVirtualInterfaces;
    private VmSecurityGroup vmSecurityGroup;

    public VmInstance() {
        super();
    }

    public VmInstance(Integer id,
                      String uuid,
                      String name,
                      Integer userId,
                      String hostUuid,
                      Integer availabilityClusterId,
                      Integer availabilityZoneId,
                      Timestamp scheduledTime,
                      Timestamp launchedTime,
                      Timestamp updatedTime,
                      Integer progress,
                      VmStatusEnum vmStatus,
                      TaskStatusEnum taskStatus,
                      String imageUuid,
                      Integer instanceTypeId,
                      String rootDeviceLocation,
                      Integer securityGroupId,
                      VmImageOSModeEnum osMode,
                      VmImageOSArchEnum osArch,
                      VmImageOSTypeEnum osType,
                      String vncPort,
                      String vncPassword,
                      Integer recovery) {
        super();
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.userId = userId;
        this.hostUuid = hostUuid;
        this.availabilityClusterId = availabilityClusterId;
        this.availabilityZoneId = availabilityZoneId;
        this.scheduledTime = scheduledTime;
        this.launchedTime = launchedTime;
        this.updatedTime = updatedTime;
        this.progress = progress;
        this.vmStatus = vmStatus;
        this.taskStatus = taskStatus;
        this.imageUuid = imageUuid;
        this.instanceTypeId = instanceTypeId;
        this.rootDeviceLocation = rootDeviceLocation;
        this.securityGroupId = securityGroupId;
        this.osMode = osMode;
        this.osArch = osArch;
        this.osType = osType;
        this.vncPort = vncPort;
        this.vncPassword = vncPassword;
        this.recovery = recovery;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public Integer getAvailabilityClusterId() {
        return availabilityClusterId;
    }

    public void setAvailabilityClusterId(Integer availabilityClusterId) {
        this.availabilityClusterId = availabilityClusterId;
    }

    public Integer getAvailabilityZoneId() {
        return availabilityZoneId;
    }

    public void setAvailabilityZoneId(Integer availabilityZoneId) {
        this.availabilityZoneId = availabilityZoneId;
    }

    public Timestamp getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Timestamp getLaunchedTime() {
        return launchedTime;
    }

    public void setLaunchedTime(Timestamp launchedTime) {
        this.launchedTime = launchedTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public VmStatusEnum getVmStatus() {
        return vmStatus;
    }

    public void setVmStatus(VmStatusEnum vmStatus) {
        this.vmStatus = vmStatus;
    }

    public TaskStatusEnum getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatusEnum taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getImageUuid() {
        return imageUuid;
    }

    public void setImageUuid(String imageUuid) {
        this.imageUuid = imageUuid;
    }

    public Integer getInstanceTypeId() {
        return instanceTypeId;
    }

    public void setInstanceTypeId(Integer instanceTypeId) {
        this.instanceTypeId = instanceTypeId;
    }

    public String getRootDeviceLocation() {
        return rootDeviceLocation;
    }

    public void setRootDeviceLocation(String rootDeviceLocation) {
        this.rootDeviceLocation = rootDeviceLocation;
    }

    public Integer getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(Integer securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public VmImageOSModeEnum getOsMode() {
        return osMode;
    }

    public void setOsMode(VmImageOSModeEnum osMode) {
        this.osMode = osMode;
    }

    public VmImageOSArchEnum getOsArch() {
        return osArch;
    }

    public void setOsArch(VmImageOSArchEnum osArch) {
        this.osArch = osArch;
    }

    public VmImageOSTypeEnum getOsType() {
        return osType;
    }

    public void setOsType(VmImageOSTypeEnum osType) {
        this.osType = osType;
    }

    public String getVncPort() {
        return vncPort;
    }

    public void setVncPort(String vncPort) {
        this.vncPort = vncPort;
    }

    public String getVncPassword() {
        return vncPassword;
    }

    public void setVncPassword(String vncPassword) {
        this.vncPassword = vncPassword;
    }

    public Integer getRecovery() {
        return recovery;
    }

    public void setRecovery(Integer recovery) {
        this.recovery = recovery;
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

    public VmZone getVmZone() {
        return vmZone;
    }

    public void setVmZone(VmZone vmZone) {
        this.vmZone = vmZone;
    }

    public VmImage getVmImage() {
        return VmImage;
    }

    public void setVmImage(VmImage VmImage) {
        this.VmImage = VmImage;
    }

    public VmInstanceType getVmInstanceType() {
        return vmInstanceType;
    }

    public void setVmInstanceType(VmInstanceType vmInstanceType) {
        this.vmInstanceType = vmInstanceType;
    }

    public List<VmVolume> getVmVolumes() {
        return vmVolumes;
    }

    public void setVmVolumes(List<VmVolume> vmVolumes) {
        this.vmVolumes = vmVolumes;
    }

    public List<VmSecurityGroupRule> getVmSecurityGroupRules() {
        return vmSecurityGroupRules;
    }

    public void setVmSecurityGroupRules(List<VmSecurityGroupRule> vmSecurityGroupRules) {
        this.vmSecurityGroupRules = vmSecurityGroupRules;
    }

    public List<VmInstanceMetadata> getVmInstanceMetadatas() {
        return vmInstanceMetadatas;
    }

    public void setVmInstanceMetadata(List<VmInstanceMetadata> vmInstanceMetadatas) {
        this.vmInstanceMetadatas = vmInstanceMetadatas;
    }

    public List<VmVirtualInterface> getVmVirtualInterfaces() {
        return vmVirtualInterfaces;
    }

    public void setVmVirtualInterfaces(List<VmVirtualInterface> vmVirtualInterfaces) {
        this.vmVirtualInterfaces = vmVirtualInterfaces;
    }

    public VmSecurityGroup getVmSecurityGroup() {
        return vmSecurityGroup;
    }

    public void setVmSecurityGroup(VmSecurityGroup vmSecurityGroup) {
        this.vmSecurityGroup = vmSecurityGroup;
    }

    public void setVmInstanceMetadatas(List<VmInstanceMetadata> vmInstanceMetadatas) {
        this.vmInstanceMetadatas = vmInstanceMetadatas;
    }

    public static enum VmStatusEnum {
        ACTIVE, BUILDING, REBUILDING, PAUSED, SUSPENDED, RESCUED, DELETED, STOPPED,
        MIGRATING, RESIZING, ERROR;

        public String toString() {
            switch (this) {
                case ACTIVE:
                    return "active";
                case BUILDING:
                    return "building";
                case REBUILDING:
                    return "rebuilding";
                case PAUSED:
                    return "paused";
                case SUSPENDED:
                    return "suspended";
                case RESCUED:
                    return "rescued";
                case DELETED:
                    return "deleted";
                case STOPPED:
                    return "stopped";
                case MIGRATING:
                    return "migrating";
                case RESIZING:
                    return "resizing";
                case ERROR:
                    return "error";
            }
            return super.toString();
        }
    }

    public static enum TaskStatusEnum {
        READY, ATTACHING, DETACHING, SCHEDULING, SPAWNING, RESIZE_PREP, REBOOTING, SUSPENDING,
        RESUMING, DELETING, STOPPING, STARTING, BUILDING, REINSTALL, MIGRATING, FINISH;

        public String toString() {
            switch (this) {
                case READY:
                    return "ready";
                case ATTACHING:
                    return "attacting";
                case DETACHING:
                    return "detaching";
                case SCHEDULING:
                    return "scheduling";
                case SPAWNING:
                    return "spawning";
                case RESIZE_PREP:
                    return "resize_prep";
                case REBOOTING:
                    return "rebooting";
                case SUSPENDING:
                    return "suspending";
                case RESUMING:
                    return "resuming";
                case DELETING:
                    return "deleting";
                case STOPPING:
                    return "stopping";
                case STARTING:
                    return "starting";
                case BUILDING:
                    return "building";
                case REINSTALL:
                    return "reinstall";
                case MIGRATING:
                    return "migrating";
                case FINISH:
                    return "finish";
            }
            return super.toString();
        }
    }

    @Override
    public String toString() {
        return "VmInstance [id="
                + id
                + ", uuid="
                + uuid
                + ", name="
                + name
                + ", userId="
                + userId
                + ", hostUuid="
                + hostUuid
                + ", availabilityClusterId="
                + availabilityClusterId
                + ", availabilityZoneId="
                + availabilityZoneId
                + ", scheduledTime="
                + scheduledTime
                + ", launchedTime="
                + launchedTime
                + ", updatedTime="
                + updatedTime
                + ", progress="
                + progress
                + ", vmStatus="
                + vmStatus
                + ", taskStatus="
                + taskStatus
                + ", imageUuid="
                + imageUuid
                + ", instanceTypeId="
                + instanceTypeId
                + ", rootDeviceLocation="
                + rootDeviceLocation
                + ", securityGroupId="
                + securityGroupId
                + ", osMode="
                + osMode
                + ", osArch="
                + osArch
                + ", osType="
                + osType
                + ", vncPort="
                + vncPort
                + ", vncPassword="
                + vncPassword
                + ", recovery="
                + recovery
                + ", host="
                + host
                + ", cluster="
                + cluster
                + ", vmZone="
                + vmZone
                + ", VmImage="
                + VmImage
                + ", vmInstanceType="
                + vmInstanceType
                + ", vmVolumes="
                + vmVolumes
                + ", vmSecurityGroupRules="
                + vmSecurityGroupRules
                + ", vmInstanceMetadatas="
                + vmInstanceMetadatas
                + ", vmVirtualInterfaces="
                + vmVirtualInterfaces
                + "]";
    }


}
