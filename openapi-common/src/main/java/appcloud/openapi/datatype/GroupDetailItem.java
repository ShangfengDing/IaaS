package appcloud.openapi.datatype;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Idan on 2017/10/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupDetailItem {

    private String name;
    public Integer maxNumberOfInstance;
    public Integer maxNumberOfDisk;
    public Integer maxNumberOfBackup;
    public Integer maxNumberOfSnapshot;
    public String description;
    public String groupSecretKey;

    public GroupDetailItem() {
    }

    public GroupDetailItem(String name, Integer maxNumberOfInstance, Integer maxNumberOfDisk, Integer maxNumberOfBackup, Integer maxNumberOfSnapshot, String description, String groupSecretKey) {
        this.name = name;
        this.maxNumberOfInstance = maxNumberOfInstance;
        this.maxNumberOfDisk = maxNumberOfDisk;
        this.maxNumberOfBackup = maxNumberOfBackup;
        this.maxNumberOfSnapshot = maxNumberOfSnapshot;
        this.description = description;
        this.groupSecretKey = groupSecretKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupSecretKey() {
        return groupSecretKey;
    }

    public void setGroupSecretKey(String groupSecretKey) {
        this.groupSecretKey = groupSecretKey;
    }
}
