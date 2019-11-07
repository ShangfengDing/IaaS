package appcloud.openapi.datatype;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import appcloud.api.beans.Image;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImageDetailItem {

	private String ImageUuid;
	private String ImageName;
	private String tenantId;
	private String status;
	private Integer minDisk;
	private Integer minRam;
	private Integer progress;
	private Date created;
	private String md5sum;   
	private String distribution;

	public ImageDetailItem(){}
	
	public ImageDetailItem(Image image) {
	    ImageUuid = image.id;
	    ImageName = image.name;
	    tenantId = image.tenantId;
	    status = image.status;
	    minDisk = image.minDisk;
	    minRam = image.minRam;
	    progress = image.progress;
	    created = image.created;
	    md5sum = image.md5sum;   
	    distribution = image.distribution;
	}

    public String getImageUuid() {
        return ImageUuid;
    }

    public void setImageUuid(String imageUuid) {
        ImageUuid = imageUuid;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMinDisk() {
        return minDisk;
    }

    public void setMinDisk(Integer minDisk) {
        this.minDisk = minDisk;
    }

    public Integer getMinRam() {
        return minRam;
    }

    public void setMinRam(Integer minRam) {
        this.minRam = minRam;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    };
	


}
