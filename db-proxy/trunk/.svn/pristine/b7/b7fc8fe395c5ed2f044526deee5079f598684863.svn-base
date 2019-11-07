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

import appcloud.common.model.VmImage.VmImageOSArchEnum;
import appcloud.common.model.VmImage.VmImageOSModeEnum;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmInstance;

/**
 * 
 * 
 * @ClassName: VmInstanceTable
 * @author haowei.yu
 * @date 2013-4-4 下午12:05:46
 */
@Entity
@Table(name = "vm_instance")
public class VmInstanceTable extends VmInstance {

    public VmInstanceTable() {
        super();
    }

    public VmInstanceTable(VmInstance vmInstance) {
        this.setId(vmInstance.getId());
        this.setUuid(vmInstance.getUuid());
        this.setName(vmInstance.getName());
        this.setUserId(vmInstance.getUserId());
        this.setHostUuid(vmInstance.getHostUuid());
        this.setAvailabilityClusterId(vmInstance.getAvailabilityClusterId());
        this.setAvailabilityZoneId(vmInstance.getAvailabilityZoneId());
        this.setScheduledTime(vmInstance.getScheduledTime());
        this.setLaunchedTime(vmInstance.getLaunchedTime());
        this.setUpdatedTime(vmInstance.getUpdatedTime());
        this.setProgress(vmInstance.getProgress());
        this.setVmStatus(vmInstance.getVmStatus());
        this.setTaskStatus(vmInstance.getTaskStatus());
        this.setImageUuid(vmInstance.getImageUuid());
        this.setInstanceTypeId(vmInstance.getInstanceTypeId());
        this.setRootDeviceLocation(vmInstance.getRootDeviceLocation());
        this.setSecurityGroupId(vmInstance.getSecurityGroupId());
        this.setOsMode(vmInstance.getOsMode());
        this.setOsArch(vmInstance.getOsArch());
        this.setOsType(vmInstance.getOsType());
        this.setVncPort(vmInstance.getVncPort());
        this.setVncPassword(vmInstance.getVncPassword());
        this.setRecovery(vmInstance.getRecovery());
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

    @Column(name = "uuid")
    public String getUuid() {
        return super.getUuid();
    }

    public void setUuid(String uuid) {
        super.setUuid(uuid);
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

    @Column(name = "scheduled_time")
    public Timestamp getScheduledTime() {
        return super.getScheduledTime();
    }

    public void setScheduledTime(Timestamp scheduledTime) {
        super.setScheduledTime(scheduledTime);
    }

    @Column(name = "launched_time")
    public Timestamp getLaunchedTime() {
        return super.getLaunchedTime();
    }

    public void setLaunchedTime(Timestamp launchedTime) {
        super.setLaunchedTime(launchedTime);
    }

    @Column(name = "updated_time")
    public Timestamp getUpdatedTime() {
        return super.getUpdatedTime();
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        super.setUpdatedTime(updatedTime);
    }

    @Column(name = "progress")
    public Integer getProgress() {
        return super.getProgress();
    }

    public void setProgress(Integer progress) {
        super.setProgress(progress);
    }

    @Column(name = "vm_status")
    @Enumerated(EnumType.STRING)
    public VmStatusEnum getVmStatus() {
        return super.getVmStatus();
    }

    public void setVmStatus(VmStatusEnum vmStatus) {
        super.setVmStatus(vmStatus);
    }
    
    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    public TaskStatusEnum getTaskStatus() {
        return super.getTaskStatus();
    }

    public void setTaskStatus(TaskStatusEnum taskStatus) {
        super.setTaskStatus(taskStatus);
    }

    @Column(name = "image_uuid")
    public String getImageUuid() {
        return super.getImageUuid();
    }

    public void setImageUuid(String imageUuid) {
        super.setImageUuid(imageUuid);
    }

    @Column(name = "instance_type_id")
    public Integer getInstanceTypeId() {
        return super.getInstanceTypeId();
    }

    public void setInstanceTypeId(Integer instanceTypeId) {
        super.setInstanceTypeId(instanceTypeId);
    }

    @Column(name = "root_device_location")
    public String getRootDeviceLocation() {
        return super.getRootDeviceLocation();
    }

    public void setRootDeviceLocation(String rootDeviceLocation) {
        super.setRootDeviceLocation(rootDeviceLocation);
    }

    @Column(name = "security_group_id")
    public Integer getSecurityGroupId() {
        return super.getSecurityGroupId();
    }

    public void setSecurityGroupId(Integer securityGroupId) {
        super.setSecurityGroupId(securityGroupId);
    }
    
    @Column(name = "os_mode")
    @Enumerated(EnumType.STRING)
    public VmImageOSModeEnum getOsMode(){
        return super.getOsMode();
    }
    public void setOsMode(VmImageOSModeEnum osMode){
        super.setOsMode(osMode);
    }
    
    @Column(name = "os_arch")
    @Enumerated(EnumType.STRING)
    public VmImageOSArchEnum getOsArch(){
        return super.getOsArch();
    }
    public void setOsArch(VmImageOSArchEnum osArch){
        super.setOsArch(osArch);
    }
    
    @Column(name = "os_type")
    @Enumerated(EnumType.STRING)
    public VmImageOSTypeEnum getOsType(){
        return super.getOsType();
    }
    public void setOsType(VmImageOSTypeEnum osType){
        super.setOsType(osType);
    }
    
    @Column(name = "vnc_port")
    public String getVncPort(){
        return super.getVncPort();
    }
    public void setVncPort(String vncPort){
        super.setVncPort(vncPort);
    }
    
    @Column(name = "vnc_password")
    public String getVncPassword(){
        return super.getVncPassword();
    }
    public void setVncPassword(String vncPassword){
        super.setVncPassword(vncPassword);
    }

    @Column(name = "recovery")
    public Integer getRecovery(){
        return super.getRecovery();
    }
    public void setRecovery(Integer recovery){
        super.setRecovery(recovery);
    }
}
