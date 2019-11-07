package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author XuanJiaxing
 * 
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmGroup {

	private Integer id;
	private String name;
	private Boolean selectCluster;
	private Boolean publishiImage;
	private Integer maxNumberOfInstance;
	private Integer maxNumberOfDisk;
	private Integer maxNumberOfBackup;
	private Integer maxNumberOfSnapshot;
	private String availableClusters;
	private String description;
	private String secretKey;
	
	public VmGroup() {
		super();
	}

	public VmGroup(Integer id, String name, Boolean selectCluster, Boolean publishiImage,
			Integer maxNumberOfInstance, Integer maxNumberOfDisk,
			Integer maxNumberOfBackup, Integer maxNumberOfSnapshot,
			String availableClusters, String description, String secretKey) {
		super();
		this.id = id;
		this.name = name;
		this.selectCluster = selectCluster;
		this.publishiImage = publishiImage;
		this.maxNumberOfInstance = maxNumberOfInstance;
		this.maxNumberOfDisk = maxNumberOfDisk;
		this.maxNumberOfBackup = maxNumberOfBackup;
		this.maxNumberOfSnapshot = maxNumberOfSnapshot;
		this.availableClusters = availableClusters;
		this.description = description;
		this.secretKey = secretKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getSelectCluster() {
		return selectCluster;
	}

	public void setSelectCluster(Boolean selectCluster) {
		this.selectCluster = selectCluster;
	}

	public Boolean getPublishiImage() {
		return publishiImage;
	}

	public void setPublishiImage(Boolean publishiImage) {
		this.publishiImage = publishiImage;
	}

	public Integer getMaxNumberOfInstance() {
		return maxNumberOfInstance;
	}

	public void setMaxNumberOfInstance(Integer maxNumberOfInstance) {
		this.maxNumberOfInstance = maxNumberOfInstance;
	}

	public Integer getMaxNumberOfDisk() {
		return maxNumberOfDisk;
	}

	public void setMaxNumberOfDisk(Integer maxNumberOfDisk) {
		this.maxNumberOfDisk = maxNumberOfDisk;
	}

	public Integer getMaxNumberOfBackup() {
		return maxNumberOfBackup;
	}

	public void setMaxNumberOfBackup(Integer maxNumberOfBackup) {
		this.maxNumberOfBackup = maxNumberOfBackup;
	}

	public Integer getMaxNumberOfSnapshot() {
		return maxNumberOfSnapshot;
	}

	public void setMaxNumberOfSnapshot(Integer maxNumberOfSnapshot) {
		this.maxNumberOfSnapshot = maxNumberOfSnapshot;
	}

	public String getAvailableClusters() {
		return availableClusters;
	}

	public void setAvailableClusters(String availableClusters) {
		this.availableClusters = availableClusters;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	public String toString() {
		return "VmGroup [id=" + id + ", name=" + name + ", selectCluster=" + selectCluster
				+ ", publishiImage=" + publishiImage + ", maxNumberOfInstance="
				+ maxNumberOfInstance + ", maxNumberOfDisk=" + maxNumberOfDisk
				+ ", maxNumberOfBackup=" + maxNumberOfBackup
				+ ", maxNumberOfSnapshot=" + maxNumberOfSnapshot
				+ ", availableClusters=" + availableClusters + ", description="
				+ description + "]";
	}
	
}
