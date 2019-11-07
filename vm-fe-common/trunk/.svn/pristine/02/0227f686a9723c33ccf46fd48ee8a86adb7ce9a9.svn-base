package com.appcloud.vm.fe.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Image {

	//云海镜像的属性
	private String ImageUuid;
	private String ImageName;
	private String appname;
	private String tenantId;
	private String status;
	private String osType;       //window或linux
	private String osArch;
	private String osMode;
	private Integer minDisk;
	private Integer minRam;
	private Integer progress;
	private Date created;
	private String md5sum;   
	private String distribution;  //操作系统的具体
	private String description;   //描述
	private String providerEn;    //提供商
	private String regionId;      //地域Id
	private String size;          //镜像大小
	private String diskFormat;    //只用image或iso
	private String software;
	private String account;
	private String type;

	public Image(){}

	public Image(String appname, String regionId,String ImageUuid,String ImageName,String status,String osType,Date created,
			String description,String distribution,String providerEn,String size,String diskFormat,String osArch, String software, String account, String type) {
		this.appname = appname;
		this.regionId = regionId;
		this.ImageUuid = ImageUuid;
		this.ImageName = ImageName;
		this.status = status;
		this.osType = osType;
		this.created = created;
		this.description = description;
		this.distribution = distribution;
		this.providerEn = providerEn;
		this.size = size;
		this.diskFormat = diskFormat;
		this.osArch = osArch;
		this.software = software;
		this.account = account;
		this.type = type;
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

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
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

    public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getOsArch() {
		return osArch;
	}

	public void setOsArch(String osArch) {
		this.osArch = osArch;
	}

	public String getOsMode() {
		return osMode;
	}

	public void setOsMode(String osMode) {
		this.osMode = osMode;
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
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProviderEn() {
		return providerEn;
	}

	public void setProviderEn(String providerEn) {
		this.providerEn = providerEn;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDiskFormat() {
		return diskFormat;
	}

	public void setDiskFormat(String diskFormat) {
		this.diskFormat = diskFormat;
	};

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
