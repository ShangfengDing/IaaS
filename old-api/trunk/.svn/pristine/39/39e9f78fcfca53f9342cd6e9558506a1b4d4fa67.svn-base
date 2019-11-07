package appcloud.api.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="group")
public class AcGroup {
	@XmlAttribute(name="id")
	public Integer id;
	@XmlAttribute(name="name")
	public String name;
	@XmlAttribute(name="select_cluster")
	public Boolean selectCluster;
	@XmlAttribute(name="publish_image")
	public Boolean publishImage;
	@XmlAttribute(name="max_number_of_instance")
	public Integer maxNumberOfInstance;
	@XmlAttribute(name="max_numberof_disk")
	public Integer maxNumberOfDisk;
	@XmlAttribute(name="max_number_of_backup")
	public Integer maxNumberOfBackup;
	@XmlAttribute(name="max_number_of_snapshot")
	public Integer maxNumberOfSnapshot;
	
	@XmlElement(name="available_clusters")
	public List<Integer> availableClusters;
	@XmlElement(name="description")
	public String description;
	@XmlElement(name="secret_key")
	public String secretKey;
	public AcGroup() {
		super();
	}
	public AcGroup(Integer id, String name, Boolean selectCluster, Boolean publishImage,
			Integer maxNumberOfInstance, Integer maxNumberOfDisk,
			Integer maxNumberOfBackup, Integer maxNumberOfSnapshot,
			List<Integer> availableClusters, String description, String secretKey) {
		super();
		this.id = id;
		this.name = name;
		this.selectCluster = selectCluster;
		this.publishImage = publishImage;
		this.maxNumberOfInstance = maxNumberOfInstance;
		this.maxNumberOfDisk = maxNumberOfDisk;
		this.maxNumberOfBackup = maxNumberOfBackup;
		this.maxNumberOfSnapshot = maxNumberOfSnapshot;
		this.availableClusters = availableClusters;
		this.description = description;
		this.secretKey = secretKey;
	}
	@Override
	public String toString() {
		return "AcGroup [id=" + id + ", name=" + name + ", selectCluster=" + selectCluster
				+ ", publishImage=" + publishImage + ", maxNumberOfInstance="
				+ maxNumberOfInstance + ", maxNumberOfDisk=" + maxNumberOfDisk
				+ ", maxNumberOfBackup=" + maxNumberOfBackup
				+ ", maxNumberOfSnapshot=" + maxNumberOfSnapshot
				+ ", availableClusters=" + availableClusters + ", description="
				+ description + "]";
	}
	
}
