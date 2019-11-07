package appcloud.vmcontroller.model;

public class DiskConfig {
    private String diskName = null;
    private String diskType = null;  //磁盘格式 qcow2 or raw
    // private String diskSnapshot = null;
    private String sourceFile = null;
    private String targetDev = null;  
    private String targetBus = null;
    private String bootOrder = null; 
    private Boolean ioTune = false;

    public DiskConfig() {}

    public DiskConfig(String name, String type, String file, String dev, String bus, String order) {
        diskName = name;
        diskType = type;
        sourceFile = file;
        targetDev = dev;
        targetBus = bus;
        bootOrder = order;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
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
