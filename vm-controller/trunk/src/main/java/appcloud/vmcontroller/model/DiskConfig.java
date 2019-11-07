package appcloud.vmcontroller.model;

import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeTypeEnum;
import appcloud.vmcontroller.util.NFSManager;

public class DiskConfig {
    private String diskDevice = null;
    private String driverType = null;  //磁盘格式 qcow2 or raw
    private String sourceFile = null;
    private String targetDev = null;  
    private String targetBus = null;
    private String bootOrder = null; 
    private Boolean ioTune = false;

    /**
     * @param name	disk device:disk(一般盘), cdrom(iso启动)
     * @param type	disk driver type:qcow2，强制填入，否则会报错
     * @param file	source file:'/nfs/192.168.101.4/volumes/l/e/21a49b583640477fa8d22bb1bd5a280a.img'
     * @param dev	target dev:hda, hdb...
     * @param bus	target bus:ide, virtio
     * @param order	暂时不用
     */
    private DiskConfig(String device, String type, String file, String dev, String bus, String order) {
        diskDevice = device;
        driverType = type;
        sourceFile = file;
        targetDev = dev;
        targetBus = bus;
        bootOrder = order;
    }
    
    /**
     * 数据模型转换：系统数据模型volume --- vmc数据模型diskConfig
     * @param vo
     * @throws Exception 
     */
    public DiskConfig(VmVolume vo) throws Exception {
    	this(null, 
    		 vo.getVolumeType().toString(), 	//volume type:iso/qcow2
    		 null, 
    		 vo.getMountPoint(), 
    		 vo.getBus(), 
    		 null);
    	
		if (true == NFSManager.handleProviderLocation(vo.getProviderLocation())) {
			//source file
			String sourceFile = NFSManager.getSourceFile(vo.getProviderLocation());
		    this.setSourceFile(sourceFile);
		} else {
			throw new Exception("handleProviderLocation fail:挂载硬盘失败");
		}
    		 
		if (vo.getVolumeType().equals(VmVolumeTypeEnum.ISO)) {
			this.setDiskDevice("cdrom");
		} else {
			this.setDiskDevice("disk");
		}
    }
    
	public String toString() {
		return "DiskConfig " +
				"[diskDevice=" + diskDevice + " ], " +
				"[sourceFile=" + sourceFile + " ], " +
				"[targetDev=" + targetDev + " ], " +
				"[targetBus=" + targetBus + " ], " +
				"[driverType=" + driverType + " ], ";
	}

    public String getDiskDevice() {
        return diskDevice;
    }

    public void setDiskDevice(String diskName) {
        this.diskDevice = diskName;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getTargetDev() {
        return targetDev;
    }

    public void setTargetDev(String targetDev) {
        this.targetDev = targetDev;
    }

    public String getTargetBus() {
        return targetBus;
    }

    public void setTargetBus(String targetBus) {
        this.targetBus = targetBus;
    }

    public String getBootOrder() {
        return bootOrder;
    }

    public void setBootOrder(String bootOrder) {
        this.bootOrder = bootOrder;
    }

    public Boolean getIoTune() {
        return ioTune;
    }

    public void setIoTune(Boolean ioTune) {
        this.ioTune = ioTune;
    }
}
