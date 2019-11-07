package com.appcloud.vm.fe.entity;/**
 * Created by dell on 2016/8/2.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 经过包装的阿里云可用区类，将英语包装转换为汉字，并使结果更容易呈现
 *
 * @author 包鑫彤
 * @create 2016-08-02-10:14
 * @since 1.0.0
 */
public class AliYunZone {

    private String zoneId;
    private String localName;
    private List<DiskCategory> availableDiskCategories = new ArrayList<>();
    private List<InstanceType> availableInstanceTypes = new ArrayList<>();

    public class InstanceType{
        private String instanceTypeId;
        private String cpuCores;
        private String memory;

        public InstanceType(String instanceTypeId, String cpuCores, String memory) {
            this.instanceTypeId = instanceTypeId;
            this.cpuCores = cpuCores;
            this.memory = memory;
        }

        public String getInstanceTypeId() {
            return (instanceTypeId).trim();
        }

        public void setInstanceTypeId(String instanceTypeId) {
            this.instanceTypeId = instanceTypeId;
        }

        public String getCpuCores() {
            return (cpuCores).trim();
        }

        public void setCpuCores(String cpuCores) {
            this.cpuCores = cpuCores;
        }

        public String getMemory() {
            return (memory).trim();
        }

        public void setMemory(String memory) {
            this.memory = memory;
        }
    }
    public class DiskCategory{

        private String diskCategory;
        private String diskCategoryName;

        public DiskCategory(String diskCategory, String diskCategoryName) {
            this.diskCategory = diskCategory;
            this.diskCategoryName = diskCategoryName;
        }

        public String getDiskCategory() {
            return (diskCategory).trim();
        }

        public void setDiskCategory(String diskCategory) {
            this.diskCategory = diskCategory;
        }

        public String getDiskCategoryName() {
            return (diskCategoryName).trim();
        }

        public void setDiskCategoryName(String diskCategoryName) {
            this.diskCategoryName = diskCategoryName;
        }
    }

    public String getZoneId() {
        return (zoneId).trim();
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getLocalName() {
        return (localName).trim();
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public List<DiskCategory> getAvailableDiskCategories() {
        return availableDiskCategories;
    }

    public void setAvailableDiskCategories(List<DiskCategory> availableDiskCategories) {
        this.availableDiskCategories = availableDiskCategories;
    }

    public List<InstanceType> getAvailableInstanceTypes() {
        return availableInstanceTypes;
    }

    public void setAvailableInstanceTypes(List<InstanceType> availableInstanceTypes) {
        this.availableInstanceTypes = availableInstanceTypes;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AliYunZone that = (AliYunZone) object;

        if (zoneId != null ? !zoneId.equals(that.zoneId) : that.zoneId != null) return false;
        if (localName != null ? !localName.equals(that.localName) : that.localName != null) return false;
        if (availableDiskCategories != null ? !availableDiskCategories.equals(that.availableDiskCategories) : that.availableDiskCategories != null)
            return false;
        return availableInstanceTypes != null ? availableInstanceTypes.equals(that.availableInstanceTypes) : that.availableInstanceTypes == null;

    }

    @Override
    public int hashCode() {
        int result = zoneId != null ? zoneId.hashCode() : 0;
        result = 31 * result + (localName != null ? localName.hashCode() : 0);
        result = 31 * result + (availableDiskCategories != null ? availableDiskCategories.hashCode() : 0);
        result = 31 * result + (availableInstanceTypes != null ? availableInstanceTypes.hashCode() : 0);
        return result;
    }
}
