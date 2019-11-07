package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author XuanJiaxing
 * 
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmImage {

	private Integer id;								//自增主键
	private String uuid;							//全局唯一的标识
	private String name;							//原始镜像文件名称，如CentOS 6.3.img
	private String displayName;						//显示名称，如centOS 6.3
	private String displayDescription;				//描述
	
	private Integer userId = 0;						//用户标识，管理员创建的系统模板此项设为0
	private String directory;						//镜像存储相对路径，如images/a/b/CentOS 6.3-uuid.img
	private String containerFormat = null;			//空
	private VmImageDiskFormatEnum diskFormat;		//image or iso
	private Integer size;							//大小，单位G
	private Timestamp createdTime;					//创建时间
	private Timestamp updatedTime;					//上次更新时间
	private Timestamp deletedTime;					//
	
	private VmImageStatusEnum status;				//状态
	//private Boolean isPublic;						//是否发布为共享
	private Integer groupId;						//群组Id
	private Integer minRam;							//所需最小内存
	private Integer minDisk;						//所需最小硬盘
	private VmImageOSTypeEnum osType;				//Linux/Windows
	private VmImageOSArchEnum osArch; 				//x86/i386
	private VmImageOSModeEnum osMode;				//hvm/xen/lxc/uml
	private String distribution;
	private String md5sum;							//镜像模板的MD5值
	private String account;          //镜像的默认密码
	private String software;						//软件
	private String type;							//类别, public, group, private
	private String signal;                        // 用来区分public,group,private判重的
	
	private List<Service> services;					//image所在的ImageServer列表：下载时列表只有一个值；上传保存时为多个
	
	private String clusters;                        //此image镜像所在的所有集群ID，该属性不在数据库中，只是用于发布镜像模板时的判断
	
	public VmImage() {
		super();
	}

	public VmImage(Integer id, String uuid, String name,
			String displayName, String displayDescription, Integer userId,
			String directory, String containerFormat,
			VmImageDiskFormatEnum diskFormat, Integer size,
			Timestamp createdTime, Timestamp updatedTime,
			Timestamp deletedTime, VmImageStatusEnum status,
			Integer groupId, Integer minRam, Integer minDisk,
			VmImageOSTypeEnum osType,
			VmImageOSArchEnum osArch,
			VmImageOSModeEnum osMode,
			String md5sum, String account, String software,String type, String signal) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.displayName = displayName;
		this.displayDescription = displayDescription;
		this.userId = userId;
		this.directory = directory;
		this.containerFormat = containerFormat;
		this.diskFormat = diskFormat;
		this.size = size;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.deletedTime = deletedTime;
		this.status = status;
		//this.isPublic = isPublic;
		this.groupId = groupId;
		this.minRam = minRam;
		this.minDisk = minDisk;
		this.osType = osType;
		this.osArch = osArch;
		this.osMode = osMode;
		this.md5sum = md5sum;
		this.account = account;
		this.software = software;
		this.type = type;
		this.signal = signal;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getContainerFormat() {
		return containerFormat;
	}

	public void setContainerFormat(String containerFormat) {
		this.containerFormat = containerFormat;
	}

	public VmImageDiskFormatEnum getDiskFormat() {
		return diskFormat;
	}

	public void setDiskFormat(VmImageDiskFormatEnum diskFormat) {
		this.diskFormat = diskFormat;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Timestamp getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Timestamp deletedTime) {
		this.deletedTime = deletedTime;
	}

	/*public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
*/
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getMinRam() {
		return minRam;
	}

	public void setMinRam(Integer minRam) {
		this.minRam = minRam;
	}

	public Integer getMinDisk() {
		return minDisk;
	}

	public void setMinDisk(Integer minDisk) {
		this.minDisk = minDisk;
	}

	public VmImageOSTypeEnum getOsType() {
		return osType;
	}

	public void setOsType(VmImageOSTypeEnum osType) {
		this.osType = osType;
	}

	public VmImageOSArchEnum getOsArch() {
		return osArch;
	}

	public void setOsArch(VmImageOSArchEnum osArch) {
		this.osArch = osArch;
	}

	public VmImageOSModeEnum getOsMode() {
		return osMode;
	}

	public void setOsMode(VmImageOSModeEnum osMode) {
		this.osMode = osMode;
	}

	public VmImageStatusEnum getStatus() {
		return status;
	}

	public void setStatus(VmImageStatusEnum status) {
		this.status = status;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public String getMd5sum() {
		return md5sum;
	}

	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public String getClusters() {
		return clusters;
	}

	public void setClusters(String clusters) {
		this.clusters = clusters;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	@Override
	public String toString() {
		return "VmImage{" +
				"id=" + id +
				", uuid='" + uuid + '\'' +
				", name='" + name + '\'' +
				", displayName='" + displayName + '\'' +
				", displayDescription='" + displayDescription + '\'' +
				", userId=" + userId +
				", directory='" + directory + '\'' +
				", containerFormat='" + containerFormat + '\'' +
				", diskFormat=" + diskFormat +
				", size=" + size +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", deletedTime=" + deletedTime +
				", status=" + status +
				", groupId=" + groupId +
				", minRam=" + minRam +
				", minDisk=" + minDisk +
				", osType=" + osType +
				", osArch=" + osArch +
				", osMode=" + osMode +
				", distribution='" + distribution + '\'' +
				", md5sum='" + md5sum + '\'' +
				", account='" + account + '\'' +
				", software='" + software + '\'' +
				", type='" + type + '\'' +
				", signal='" + signal + '\'' +
				", services=" + services +
				", clusters='" + clusters + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		VmImage temp = (VmImage) obj;
		return this.getSignal().equals(temp.getSignal());
	}

	public static enum VmImageDiskFormatEnum {
		IMAGE, ISO;

		public String toString() {
			switch (this) {
			case IMAGE:
				return "image";
			case ISO:
				 return "iso";
			}
			return super.toString();
		}
	}
	public static enum VmImageOSTypeEnum {
		LINUX, WINDOWS;

		public String toString() {
			switch (this) {
			case LINUX:
				return "linux";
			case WINDOWS:
				return "windows";
			}
			return super.toString();
		}
	}

	public static enum VmImageOSArchEnum {
		X86, I386;

		public String toString() {
			switch (this) {
			case X86:
				return "x86";
			case I386:
				return "i386";
			}
			return super.toString();
		}
	}
	
	public static enum VmImageOSModeEnum {
		HVM, XEN, LXC, UML;

		public String toString() {
			switch (this) {
			case HVM:
				return "hvm";
			case XEN:
				return "xen";
			case LXC:
				return "lxc";
			case UML:
				return "uml";
			}
			return super.toString();
		}
	}
	
	public static enum VmImageStatusEnum {
        AVAILABLE, CREATING, DELETING, ERROR, ERROR_DELETING;

        public String toString() {
            switch (this) {
            case AVAILABLE:
                return "available";
            case CREATING:
                return "creating";
            case DELETING:
                return "deleting";
            case ERROR:
                return "error";
            case ERROR_DELETING:
                return "error_deleting";
            }

            return super.toString();
        }
    }

    public static enum VmImageTypeEnum {
        PUBLIC, GROUP, PRIVATE;

        public String toString() {
            switch (this) {
                case PUBLIC:
                    return "PUBLIC";
                case GROUP:
                    return "GROUP";
                case PRIVATE:
                    return "PRIVATE";
            }

            return super.toString();
        }
    }

	public static enum VmImageDistributionEnum {
		WINDOWS, WINDOWS_SERVER, CENTOS, UBUNTU, RED_HAT, DEBIAN, FEDORA, OTHER;

		public String toString() {
			switch (this) {
				case WINDOWS:
					return "windows";
				case WINDOWS_SERVER:
					return "windows_server";
				case CENTOS:
					return "centos";
				case UBUNTU:
					return "ubuntu";
				case RED_HAT:
					return "red_hat";
				case DEBIAN:
					return "debian";
				case FEDORA:
					return "fedora";
				case OTHER:
					return "other";
			}

			return super.toString();
		}
	}
}
