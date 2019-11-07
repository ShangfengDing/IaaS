package appcloud.openapi.datatype;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DiskDetailItem {

	private String DiskId;
	private String DiskName;
	private String Description;
	private String Status;
	private Integer Size;
	private String DiskCategory;
	private String ZoneId;
	private String SnapshotId;
	private Date CreateTime;
	private String DiskType;
	
	private String Device;
	private String InstanceId;
	
	
	public DiskDetailItem(){}


	public String getDiskId() {
		return DiskId;
	}


	public void setDiskId(String diskId) {
		DiskId = diskId;
	}


	public String getDiskName() {
		return DiskName;
	}


	public void setDiskName(String diskName) {
		DiskName = diskName;
	}


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}


	public String getDiskCategory() {
		return DiskCategory;
	}


	public void setDiskCategory(String diskCategory) {
		DiskCategory = diskCategory;
	}


	public String getZoneId() {
		return ZoneId;
	}


	public void setZoneId(String zoneId) {
		ZoneId = zoneId;
	}


	public String getSnapshotId() {
		return SnapshotId;
	}


	public void setSnapshotId(String snapshotId) {
		SnapshotId = snapshotId;
	}

	
	public Integer getSize() {
		return Size;
	}


	public void setSize(Integer size) {
		Size = size;
	}


	public Date getCreateTime() {
		return CreateTime;
	}


	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}


	public String getDiskType() {
		return DiskType;
	}


	public void setDiskType(String diskType) {
		DiskType = diskType;
	}


	public String getDevice() {
		return Device;
	}


	public void setDevice(String device) {
		Device = device;
	}


	public String getInstanceId() {
		return InstanceId;
	}


	public void setInstanceId(String instanceId) {
		InstanceId = instanceId;
	}

}
