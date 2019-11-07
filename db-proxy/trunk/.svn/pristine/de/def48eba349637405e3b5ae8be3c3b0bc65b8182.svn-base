package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import appcloud.common.model.VmImage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author XuanJiaxing
 * 
 */
@Entity
@Table(name="vm_image_copy")
public class VmImageTable extends VmImage{
	
	public VmImageTable(){
		super();
	}
	
	public VmImageTable(VmImage image){
		this.setId(image.getId()) ;
		this.setUuid(image.getUuid());
		this.setName(image.getName());
		this.setUserId(image.getUserId());
		this.setDirectory(image.getDirectory());
		this.setContainerFormat(image.getContainerFormat());
		this.setDiskFormat(image.getDiskFormat());
		this.setSize(image.getSize());
		this.setCreatedTime(image.getCreatedTime());
		this.setUpdatedTime(image.getUpdatedTime());
		this.setDeletedTime(image.getDeletedTime());
		this.setStatus(image.getStatus());
		this.setGroupId(image.getGroupId());
		this.setMinRam(image.getMinRam());
		this.setMinDisk(image.getMinDisk());
		this.setDisplayName(image.getDisplayName());
		this.setDisplayDescription(image.getDisplayDescription());
		this.setOsType(image.getOsType());
		this.setOsArch(image.getOsArch());
		this.setOsMode(image.getOsMode());
		this.setDistribution(image.getDistribution());
		this.setMd5sum(image.getMd5sum());
		this.setAccount(image.getAccount());
		this.setSoftware(image.getSoftware());
		this.setType(image.getType());
		this.setSignal(image.getSignal());
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

	@Column(name="uuid")
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
	
	@Column(name="user_id")
	public Integer getUserId() {
		return super.getUserId();
	}
	public void setUserId(Integer userId) {
		super.setUserId(userId);
	}

	@Column(name="directory")
	public String getDirectory() {
		return super.getDirectory();
	}
	public void setDirectory(String directory) {
		super.setDirectory(directory);
	}
	
	@Column(name="container_format")
	public String getContainerFormat() {
		return super.getContainerFormat();
	}
	public void setContainerFormat(String containerFormat) {
		super.setContainerFormat(containerFormat);
	}

	@Column(name="disk_format")
	@Enumerated(EnumType.STRING)
	public VmImageDiskFormatEnum getDiskFormat() {
		return super.getDiskFormat();
	}
	public void setDiskFormat(VmImageDiskFormatEnum diskFormat) {
		super.setDiskFormat(diskFormat);
	}

	@Column(name="size")
	public Integer getSize() {
		return super.getSize();
	}
	public void setSize(Integer size) {
		super.setSize(size);
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

	@Column(name="status")
	@Enumerated(EnumType.STRING)
	public VmImageStatusEnum getStatus() {
		return super.getStatus();
	}
	public void setStatus(VmImageStatusEnum status) {
		super.setStatus(status);
	}

	@Column(name="group_id")
	public Integer getGroupId() {
		return super.getGroupId();
	}
	public void setGroupId(Integer groupId) {
		super.setGroupId(groupId);
	}

	@Column(name="min_ram")
	public Integer getMinRam() {
		return super.getMinRam();
	}
	public void setMinRam(Integer minRam) {
		super.setMinRam(minRam);
	}

	@Column(name="min_disk")
	public Integer getMinDisk() {
		return super.getMinDisk();
	}
	public void setMinDisk(Integer minDisk) {
		super.setMinDisk(minDisk);
	}

	@Column(name="os_type")
	@Enumerated(EnumType.STRING)
	public VmImageOSTypeEnum getOsType() {
		return super.getOsType();
	}
	public void setOsType(VmImageOSTypeEnum osType) {
		super.setOsType(osType);
	}
	
	@Column(name="os_arch")
	@Enumerated(EnumType.STRING)
	public VmImageOSArchEnum getOsArch() {
		return super.getOsArch();
	}
	public void setOsArch(VmImageOSArchEnum osArch) {
		super.setOsArch(osArch);
	}

	@Column(name="os_mode")
	@Enumerated(EnumType.STRING)
	public VmImageOSModeEnum getOsMode() {
		return super.getOsMode();
	}
	public void setOsMode(VmImageOSModeEnum osArch) {
		super.setOsMode(osArch);
	}
	
	@Column(name="distribution")
	public String getDistribution() {
		return super.getDistribution();
	}
	public void setDistribution(String distribution) {
		super.setDistribution(distribution);
	}
	
	@Column(name="md5sum")
	public String getMd5sum() {
		return super.getMd5sum();
	}
	public void setMd5sum(String md5sum) {
		super.setMd5sum(md5sum);
	}

	// 新增
	@Column(name="account")
	public String getAccount() {
		return super.getAccount();
	}

	public void setAccount(String account) {
		super.setAccount(account);
	}

	@Column(name="software")
	public String getSoftware() {
		return super.getSoftware();
	}

	public void setSoftware(String software) {
		super.setSoftware(software);
	}

	@Column(name="type")
	public String getType() {
		return super.getType();
	}

	public void setType(String type) {
		super.setType(type);
	}

	@Column(name="signal")
	public String getSignal() {
		return super.getSignal();
	}

	public void setSignal(String signal) {
		super.getSignal();
	}
}
