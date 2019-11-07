package com.appcloud.vm.fe.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 经过再次封装的阿里云某zone下的可用资源列表，
 * 包括IO是否优化，可用实例规格列表，可用实例规格族，可用实例的系列，可用系统磁盘类型，可用数据磁盘类型
 *
 * @author 包鑫彤
 * @create 2016-07-27-8:58
 * @since 1.0.0
 */
public class AliYunAvailableResource {
    private Boolean ioOptimized;
    private String instanceGeneration;
    private List<InstanceType> instanceTypes = new ArrayList<>();
    private List<String> instanceTypeFamilies = new ArrayList<>();
    private List<DiskCategory> systemDiskCategories = new ArrayList<>();
    private List<DiskCategory> dataDiskCategories = new ArrayList<>();
    public class InstanceType{
        private String instanceTypeId;
        private String cpuCores;
        private String memory;

        public InstanceType() {
        }

        public InstanceType(String instanceTypeId, String cpuCores, String memory) {
            this.instanceTypeId = instanceTypeId;
            this.cpuCores = cpuCores;
            this.memory = memory;
        }

        public String getInstanceTypeId() {
            return instanceTypeId;
        }

        public void setInstanceTypeId(String instanceTypeId) {
            this.instanceTypeId = instanceTypeId;
        }

        public String getCpuCores() {
            return cpuCores;
        }

        public void setCpuCores(String cpuCores) {
            this.cpuCores = cpuCores;
        }

        public String getMemory() {
            return memory;
        }

        public void setMemory(String memory) {
            this.memory = memory;
        }
    }

    public class DiskCategory{
        private String diskId;
        private String diskName;

        public DiskCategory(String diskId, String diskName) {
            this.diskId = diskId;
            this.diskName = diskName;
        }

        public String getDiskId() {
            return diskId;
        }

        public void setDiskId(String diskId) {
            this.diskId = diskId;
        }

        public String getDiskName() {
            return diskName;
        }

        public void setDiskName(String diskName) {
            this.diskName = diskName;
        }
    }

    public Boolean getIoOptimized() {
        return ioOptimized;
    }

    public void setIoOptimized(Boolean ioOptimized) {
        this.ioOptimized = ioOptimized;
    }

    public String getInstanceGeneration() {
        return instanceGeneration;
    }

    public void setInstanceGeneration(String instanceGeneration) {
        this.instanceGeneration = instanceGeneration;
    }

    public List<InstanceType> getInstanceTypes() {
        return instanceTypes;
    }

    public void setInstanceTypes(List<InstanceType> instanceTypes) {
        this.instanceTypes = instanceTypes;
    }

    public List<String> getInstanceTypeFamilies() {
        return instanceTypeFamilies;
    }

    public void setInstanceTypeFamilies(List<String> instanceTypeFamilies) {
        this.instanceTypeFamilies = instanceTypeFamilies;
    }

    public List<DiskCategory> getSystemDiskCategories() {
        return systemDiskCategories;
    }

    public void setSystemDiskCategories(List<DiskCategory> systemDiskCategories) {
        this.systemDiskCategories = systemDiskCategories;
    }

    public List<DiskCategory> getDataDiskCategories() {
        return dataDiskCategories;
    }

    public void setDataDiskCategories(List<DiskCategory> dataDiskCategories) {
        this.dataDiskCategories = dataDiskCategories;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AliYunAvailableResource that = (AliYunAvailableResource) object;

        if (!ioOptimized.equals(that.ioOptimized)) return false;
        return instanceGeneration.equals(that.instanceGeneration);

    }

    @Override
    public int hashCode() {
        int result = ioOptimized.hashCode();
        result = 31 * result + instanceGeneration.hashCode();
        return result;
    }
}
