package com.appcloud.vm.action.vm.newvm;

import aliyun.openapi.client.AliInstanceClient;
import com.aliyuncs.ecs.model.v20140526.CreateInstanceResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.ResultUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于创建阿里云实例的类，保证单一原则，所有其他的操作均不许写在此类中
 *
 * @author 包鑫彤
 * @create 2016-07-28-8:38
 * @since 1.0.0
 */
public class NewAliYunVmAction extends BaseAction {

    private Logger logger = Logger.getLogger(NewAliYunVmAction.class);

    private Integer userId = this.getSessionUserID();
    private AppkeyManager appkeyManager = new AppkeyManager();
    private String appname;
    /**
     * 实例所属的Region ID
     */
    private String regionId = null;
    /**
     * 实例所属的可用区编号，空表示由系统选择，默认值：空。
     */
    private String zoneId = null;
    /**
     * 实例所属的可用区编号，空表示由系统选择，默认值：空。
     */
    private String imageId = null;
    /**
     * 实例的资源规则。取值参见附表实例资源规格对照表，也可以调用查询实例资源规格列表接口获得最新的规格表。
     */
    private String instanceType = null;
    /**
     * 指定新创建实例所属于的安全组代码，同一个安全组内的实例之间可以互相访问。
     */
    private String securityGroupId = null;
    /**
     * 实例的显示名称，[2,128]英文或中文字符，必须以大小字母或中文开头，可包含数字，“.”，“_”或“-”。 实例名称会显示在控制台。 如果没有指定该参数，默认值为实例的InstanceId 不能以http:// 和https:// 开头。
     */
    private String instanceName = null;
    /**
     * 实例的描述，[2,256]个字符，实例描述会显示在控制台。不填则为空，默认为空。不能以http:// 和https:// 开头。
     */
    private String description = null;
    /**
     * 网络计费类型，按流量计费还是按固定带宽计费。可选值：PayByBandwidth PayByTraffic 如用户不指定，默认是PayByBandwidth。
     */
    private String internetChargeType = null;
    /**
     * 公网入带宽最大值，单位为Mbps(Mega bit per second)，取值范围：[1,200]如果客户不指定，AliyunAPI将自动将入带宽设置成200Mbps。
     */
    private String internetMaxBandwidthIn = null;

    /**
     * 公网出带宽最大值，单位为Mbps(Mega bit per second)，取值范围：
     * 如果实例的 InstanceChargeType 是 PostPaid，则：
     * 按带宽计费：[0,100] 如果客户不指定，API将自动将出带宽设置成0Mbps。
     * 按流量计费：[1,100] 如果客户不指定，会报错。
     * 如果实例的 InstanceChargeType 是 PrePaid，则：
     * 按带宽计费：[0,200] 如果客户不指定，API将自动将出带宽设置成0Mbps。
     * 按流量计费：[1,100] 如果客户不指定，会报错。
     * 如果需要可以通过阿里云工单系统申请将带宽范围设置成1~200Mbps。
     */
    private String internetMaxBandwidthOut = null;

    /**
     * 表示云服务器的主机名，最少2字符，“.”和“-”是不能作为hostname的首尾字符，不能连续使用。 Windows平台最长为15字符，允许字母（不限制大小写）、数字和“-”组成，不支持点号（”.”），不能全是数字。
     * 其他（Linux等）平台最长为30字符，允许支持多个点号，点之间为一段，每段允许字母（不限制大小写）、数字和“-”组成。
     */
    private String hostname = null;
    /**
     * 实例的密码。8-30个字符，必须同时包含三项（大、小写字母，数字和特殊符号）。支持以下特殊字符：( ) ` ~ ! @ # $ % ^ & * - + = | { } [ ] : ; ‘ < > , . ? /
     * 如果传入Password参数，请务必使用HTTPS协议调用API以避免可能发生的密码泄露。
     */
    private String password = null;
    /**
     * IO优化。
     * 可选值：
     * none：非 IO 优化
     * optimized：IO 优化
     * 默认值：none。
     */
    private String ioIoOptimized = null;
    /**
     * 系统盘的磁盘种类
     * 可选值：
     * cloud – 普通云盘
     * cloud_efficiency – 高效云盘
     * cloud_ssd – SSD云盘
     * ephemeral_ssd - 本地 SSD 盘
     * 默认值：cloud
     */
    private String systemDiskCategory = null;
    /**
     * 系统盘大小，以GB为单位，取值范围为：
     * cloud – 40~500
     * cloud_efficiency – 40~500
     * cloud_ssd – 40~500
     * ephemeral_ssd - 40~500
     * 默认值：size=max{40，ImageSize}
     * 指定该参数后，size必须大于等于max{40, ImageSize}。
     */
    private String systemDiskSize = null;
    /**
     * 系统盘名称，不填则为空，默认值为空，[2,128]英文或中文字符，
     * 必须以大小字母或中文开头，可包含数字，”_”或”-”，磁盘名称会展示在控制台。
     * 不能以http:// 和https:// 开头。
     */
    private String systemDiskName = null;
    /**
     * 系统盘描述，不填则为空，默认值为空，[2,256]个字符，实例描述会显示在控制台。
     * 不填则为空，默认为空。不能以http:// 和https:// 开头。
     */
    private String systemDiskDescription = null;
    /**
     * 数据盘n的磁盘种类
     * 可选值：
     * cloud – 普通云盘
     * cloud_efficiency – 高效云盘
     * cloud_ssd – SSD云盘
     * ephemeral_ssd - 本地 SSD 盘
     * 默认值：cloud
     */
    private String dataDiskCategory = null;
    /**
     * 创建数据盘使用的快照
     * 指定该参数后DataDisk.n.Size会被忽略，实际创建的磁盘大小为指定快照的大小。
     * 若该快照创建于2013年7月15日（含）之前，该次调用会被拒绝，Response中返回InvalidSnapshot.TooOld
     */
    private String dataDiskSnapshotId = null;
    /**
     * 数据盘n的磁盘大小（n从1开始编号）。 以GB为单位，取值范围为：
     * cloud：5 ~ 2000
     * cloud_efficiency：20 ~ 32768
     * cloud_ssd：20 ~ 32768
     * ephemeral_ssd：5 ~ 800
     */
    private String dataDiskSize = null;
    /**
     * 数据盘名称，不填则为空，默认值为空，[2,128]英文或中文字符，
     * 必须以大小字母或中文开头，可包含数字，”_”或”-”，磁盘名称会展示在控制台。
     * 不能以http:// 和https:// 开头。
     */
    private String dataDiskName = null;
    /**
     * 数据盘描述，不填则为空，默认值为空，[2,256]个字符，磁盘描述会展示在控制台。不能以http:// 和https:// 开头。
     */
    private String dataDiskDescription = null;
    /**
     * 空表示由系统默认分配，/dev/xvdb 开始到 /dev/xvdz
     * 默认值：空
     */
    private String dataDiskDevice = null;
    /**
     * 表示数据盘是否随实例释放。
     * true表示实例释放时，这块磁盘随实例一起释放
     * false表示实例释放时，这块磁盘保留不释放
     * 默认值：true
     * 这个参数只对独立云磁盘即DataDisk.n.Category=cloud，cloud_efficiency和cloud_ssd 时有效，否则会报错。
     */
    private boolean dataDiskDeleteWithInstance = true;
    /**
     * 如果是创建VPC类型的实例，需要指定虚拟交换机的ID
     */
    private String vswitchId = null;
    /**
     * 实例私网IP地址，不能单独指定。
     */
    private String privateIpAddress = null;
    /**
     * 网络控制器Id。
     */
    private String nodeControllerId = null;
    /**
     * 虚拟局域网Id。
     */
    private String vlanId = null;
    /**
     * 实例的付费方式。
     * PrePaid：预付费，即包年包月
     * PostPaid：后付费，即按量付费
     * 默认为 PostPaid，即按量付费。
     */
    private String instanceChargeType = null;
    /**
     * 购买资源的时长，单位是月。
     * 当 InstanceChargeType 为 PrePaid 时，为必选。
     * 取值范围为：1 - 9,12,24,36
     */
    private String period;

    /**
     * 创建阿里云主机的基本逻辑（返回的值需要优化，做出错误处理）
     *
     * @return
     */
    public String createAliYunInstance() {
        Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        String appkeyId = appkey.getAppkeyId();
        String appkeySecret = appkey.getAppkeySecret();
        String userEmail = appkey.getUserEmail();
        AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
        CreateInstanceResponse createInstanceResponse;
        //根据result插入日志
        Map<String, String> mapLog = new HashMap<>();
        mapLog.put("userId", userId.toString());
        mapLog.put("device", "vm");
        mapLog.put("appkeyId", appkey.getAppkeyId());
        mapLog.put("provider", appkey.getProvider());
        mapLog.put("opratetionType", "create a instance");
        try {
            createInstanceResponse = aliInstanceClient.CreateInstance(regionId, imageId, instanceType, securityGroupId, zoneId, instanceName,
                    description, instanceChargeType, period, internetChargeType, internetMaxBandwidthOut, null, password,
                    systemDiskCategory, systemDiskSize, dataDiskCategory, dataDiskSize, dataDiskDeleteWithInstance, appkeyId,
                    appkeySecret, userEmail);
            ResultUtils.toAliYunJson(ServletActionContext.getResponse(), createInstanceResponse);
            mapLog.put("deviceId", createInstanceResponse.getInstanceId());
            mapLog.put("result", "success,"+toString());
            Log.INFO(mapLog, appkey, regionId);
        } catch (ClientException e) {
            ResultUtils.toAliYunJson(ServletActionContext.getResponse(), e);
            mapLog.put("result", "fail, " + e.getErrMsg());
            Log.ERROR(mapLog, appkey, regionId);
        }
        return null;
    }

    public String getPeriod() {
        return (period).trim();
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRegionId() {
        return (regionId).trim();
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getZoneId() {
        return (zoneId).trim();
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getImageId() {
        return (imageId).trim();
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getInstanceType() {
        return (instanceType).trim();
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getSecurityGroupId() {
        return (securityGroupId).trim();
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public String getInstanceName() {
        return (instanceName).trim();
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getDescription() {
        return (description).trim();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInternetChargeType() {
        return (internetChargeType).trim();
    }

    public void setInternetChargeType(String internetChargeType) {
        this.internetChargeType = internetChargeType;
    }

    public String getInternetMaxBandwidthIn() {
        return (internetMaxBandwidthIn).trim();
    }

    public void setInternetMaxBandwidthIn(String internetMaxBandwidthIn) {
        this.internetMaxBandwidthIn = internetMaxBandwidthIn;
    }

    public String getInternetMaxBandwidthOut() {
        String band = (internetMaxBandwidthOut).trim();
        if (getInternetChargeType().equals("PayByBandwidth")) {
            band = "[0," + band + "]";
        } else if (getInternetChargeType().equals("PayByTraffic")) {
            band = "[1," + band + "]";

        }
        return band;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public void setInternetMaxBandwidthOut(String internetMaxBandwidthOut) {
        this.internetMaxBandwidthOut = internetMaxBandwidthOut;
    }

    public String getHostname() {
        return (hostname).trim();
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPassword() {
        return (password).trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIoIoOptimized() {
        return (ioIoOptimized).trim();
    }

    public void setIoIoOptimized(String ioIoOptimized) {
        this.ioIoOptimized = ioIoOptimized;
    }

    public String getSystemDiskCategory() {
        return (systemDiskCategory).trim();
    }

    public void setSystemDiskCategory(String systemDiskCategory) {
        this.systemDiskCategory = systemDiskCategory;
    }

    public String getSystemDiskSize() {
        return (systemDiskSize).trim();
    }

    public void setSystemDiskSize(String systemDiskSize) {
        this.systemDiskSize = systemDiskSize;
    }

    public String getSystemDiskName() {
        return (systemDiskName).trim();
    }

    public void setSystemDiskName(String systemDiskName) {
        this.systemDiskName = systemDiskName;
    }

    public String getSystemDiskDescription() {
        return (systemDiskDescription).trim();
    }

    public void setSystemDiskDescription(String systemDiskDescription) {
        this.systemDiskDescription = systemDiskDescription;
    }

    public String getDataDiskCategory() {
        return (dataDiskCategory).trim();
    }

    public void setDataDiskCategory(String dataDiskCategory) {
        this.dataDiskCategory = dataDiskCategory;
    }

    public String getDataDiskSnapshotId() {
        return (dataDiskSnapshotId).trim();
    }

    public void setDataDiskSnapshotId(String dataDiskSnapshotId) {
        this.dataDiskSnapshotId = dataDiskSnapshotId;
    }

    public String getDataDiskSize() {
        return (dataDiskSize).trim();
    }

    public void setDataDiskSize(String dataDiskSize) {
        this.dataDiskSize = dataDiskSize;
    }

    public String getDataDiskName() {
        return (dataDiskName).trim();
    }

    public void setDataDiskName(String dataDiskName) {
        this.dataDiskName = dataDiskName;
    }

    public String getDataDiskDescription() {
        return (dataDiskDescription).trim();
    }

    public void setDataDiskDescription(String dataDiskDescription) {
        this.dataDiskDescription = dataDiskDescription;
    }

    public String getDataDiskDevice() {
        return (dataDiskDevice).trim();
    }

    public void setDataDiskDevice(String dataDiskDevice) {
        this.dataDiskDevice = dataDiskDevice;
    }

    public boolean isDataDiskDeleteWithInstance() {
        return dataDiskDeleteWithInstance;
    }

    public void setDataDiskDeleteWithInstance(boolean dataDiskDeleteWithInstance) {
        this.dataDiskDeleteWithInstance = dataDiskDeleteWithInstance;
    }

    public String getVswitchId() {
        return (vswitchId).trim();
    }

    public void setVswitchId(String vswitchId) {
        this.vswitchId = vswitchId;
    }

    public String getPrivateIpAddress() {
        return (privateIpAddress).trim();
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    public String getNodeControllerId() {
        return (nodeControllerId).trim();
    }

    public void setNodeControllerId(String nodeControllerId) {
        this.nodeControllerId = nodeControllerId;
    }

    public String getVlanId() {
        return (vlanId).trim();
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public String getInstanceChargeType() {
        return (instanceChargeType).trim();
    }

    public void setInstanceChargeType(String instanceChargeType) {
        this.instanceChargeType = instanceChargeType;
    }

    @Override
    public String toString() {
        return "NewAliYunVmAction{" +
                "regionId='" + regionId + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", imageId='" + imageId + '\'' +
                ", instanceType='" + instanceType + '\'' +
                ", securityGroupId='" + securityGroupId + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", internetChargeType='" + internetChargeType + '\'' +
                ", internetMaxBandwidthOut='" + internetMaxBandwidthOut + '\'' +
                ", ioIoOptimized='" + ioIoOptimized + '\'' +
                ", systemDiskCategory='" + systemDiskCategory + '\'' +
                ", systemDiskSize='" + systemDiskSize + '\'' +
                ", dataDiskCategory='" + dataDiskCategory + '\'' +
                ", dataDiskSize='" + dataDiskSize + '\'' +
                ", instanceChargeType='" + instanceChargeType + '\'' +
                ", period='" + period + '\'' +
                '}';
    }
}
