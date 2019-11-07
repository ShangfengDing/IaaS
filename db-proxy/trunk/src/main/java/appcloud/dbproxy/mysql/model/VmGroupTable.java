package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmGroup;

@Entity
@Table(name="vm_group")
public class VmGroupTable extends VmGroup{
	
	public VmGroupTable() {
		super();
	}
	public VmGroupTable(VmGroup group) {
		this.setId(group.getId());
		this.setName(group.getName());
		this.setSelectCluster(group.getSelectCluster());
		this.setPublishiImage(group.getPublishiImage());
		this.setMaxNumberOfInstance(group.getMaxNumberOfInstance());
		this.setMaxNumberOfDisk(group.getMaxNumberOfDisk());
		this.setMaxNumberOfBackup(group.getMaxNumberOfBackup());
		this.setMaxNumberOfSnapshot(group.getMaxNumberOfSnapshot());
		this.setAvailableClusters(group.getAvailableClusters());
		this.setDescription(group.getDescription());
		this.setSecretKey(group.getSecretKey());
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
	
	@Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}
	
	@Column(name="select_cluster")
	public Boolean getSelectCluster() {
		return super.getSelectCluster();
	}
	public void setSelectCluster(Boolean selectCluster) {
		super.setSelectCluster(selectCluster);
	}

	@Column(name="publish_image")
	public Boolean getPublishiImage() {
		return super.getPublishiImage();
	}
	public void setPublishiImage(Boolean publishiImage) {
		super.setPublishiImage(publishiImage);
	}

	@Column(name="max_number_of_instance")
	public Integer getMaxNumberOfInstance() {
		return super.getMaxNumberOfInstance();
	}
	public void setMaxNumberOfInstance(Integer maxNumberOfInstance) {
		super.setMaxNumberOfInstance(maxNumberOfInstance);
	}

	@Column(name="max_number_of_disk")
	public Integer getMaxNumberOfDisk() {
		return super.getMaxNumberOfDisk();
	}
	public void setMaxNumberOfDisk(Integer maxNumberOfDisk) {
		super.setMaxNumberOfDisk(maxNumberOfDisk);
	}

	@Column(name="max_number_of_backup")
	public Integer getMaxNumberOfBackup() {
		return super.getMaxNumberOfBackup();
	}
	public void setMaxNumberOfBackup(Integer maxNumberOfBackup) {
		super.setMaxNumberOfBackup(maxNumberOfBackup);
	}

	@Column(name="max_number_of_snapshot")
	public Integer getMaxNumberOfSnapshot() {
		return super.getMaxNumberOfSnapshot();
	}

	public void setMaxNumberOfSnapshot(Integer maxNumberOfSnapshot) {
		super.setMaxNumberOfSnapshot(maxNumberOfSnapshot);
	}

	@Column(name="available_clusters")
	public String getAvailableClusters() {
		return super.getAvailableClusters();
	}
	public void setAvailableClusters(String availableClusters) {
		super.setAvailableClusters(availableClusters);;
	}

	@Column(name="description")
	public String getDescription() {
		return super.getDescription();
	}
	public void setDescription(String description) {
		super.setDescription(description);;
	}

	@Column(name="secret_key")
	public String getSecretKey() {
		return super.getSecretKey();
	}
	public void setSecretKey(String secretKey) {
		super.setSecretKey(secretKey);
	}
}
