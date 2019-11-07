package com.appcloud.vm.action.hd;

import aliyun.openapi.client.AliVolumeClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.client.VolumeClient;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.response.DescribeInstancesResponse;
import appcloud.openapi.response.DisksDetailReponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.AliYunInstanceSourceCompareTable;
import com.appcloud.vm.common.CompareDate;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.AliYunDisk;
import com.appcloud.vm.fe.entity.DiskStatus;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.*;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * modified by 包鑫彤 on 2016/7/31. 新增加了对于阿里云硬盘的显示,优化了请求逻辑和显示方式。云镜像，云快照，云硬盘显示方式与逻辑类似。
 * 之后的维护者请在注释中说明自己的操作，对每一个函数和字段均需要写注释（太过简单的可以不写）。
 */
public class HdListAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Logger LOGGER = Logger.getLogger(HdListAction.class);

    private Integer userId = this.getSessionUserID();
    private AppkeyManager appkeyManager = new AppkeyManager();

    private String provider;



    private String regionId;
    private String zoneId;
    private String appName;

    //分页的参数
    private double totalCount;//云硬盘总个数
    private String totalPage ;//云硬盘总的页数
    private Integer PageSize = 10;
    private String page = "1";
    private String result = "0";//resule值为0：成功，1：参数传入出错，2：搜索云硬盘名称出错或参数不匹配

    //云海磁盘列表搜索传递的参数

    /**
     * 自定义的用于包装云海磁盘参数的 实体类
     */
    private List<DiskStatus> diskServers = new ArrayList<>();
    /**
     * 自定义的用于进行从英文到汉字的转换的 实体类
     */
    private TransformAttribute transform = new TransformAttribute();
    /**
     * 用于在搜索的时候区分搜索类型
     */
    private String souType = "all";
    /**
     * 查找时在输入框中输入的关键字
     */
    private String yunHaiKeyword = null;
    /**
     * 磁盘的挂载状态
     */
    private String yunHaiDiskAttachStatus = null;
    /**
     * 磁盘的属性
     */
    private String yunHaiDiskType = null;

    //计算是否过期
    private Date nowDate = new Date();
    private CompareDate compareDate = new CompareDate();

    //阿里云磁盘列表及搜索传递的参数

    /**
     * 自定义的用于包装阿里云的实体类
     */
    private List<AliYunDisk> aliYunDisks = new ArrayList<>();
    /**
     * 用于区分搜索类型 判断具体是要精确搜索哪个字段
     */
    private String searchType;
    /**
     * 精确搜索时输入的关键字
     */
    private String searchKeyWord;
    /**
     * 阿里云的磁盘种类 可以取的值如果需要 请到官网API中查看
     */
    private String aliYunDiskCategory;
    /**
     * 阿里云的磁盘状态
     */
    private String aliYunDiskStatus;
    /**
     * 阿里云的磁盘是否可卸载
     */
    private boolean isPortable;
    private String portableStr;  //由于在从js传值 portable时传值只能传 true false 但是在查询全部时要求 传值 null，所以在这里加一个字段用来判断
    /**
     * 阿里云的磁盘属性
     */
    private String aliYunDiskType;


    /**
     * 用于获取磁盘列表，根据提供商和regionId来进行区分
     *
     * @return 返回的是查询的结果，具体返回的数据 是jsp 页面，在配置文件中查看 hd.xml
     */
    public String hdGetHdList() {
        LOGGER.info("提供者"+provider+" 应用名"+appName);
        Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appName);
        provider = appkey.getProvider();
        String result;
        switch (provider) {
            case Constants.YUN_HAI:
                result = getYunHaiDisk(appkey);
                break;
            case Constants.ALI_YUN:
                result = getAliYunDisk(appkey);
                break;
            case Constants.AMAZON:
                result = getAmazonDisk(appkey);
                break;
            default:
                result = Constants.QUERY_NO_DATA;
                break;
        }
        Integer total = (int) Math.ceil(totalCount / PageSize);  //向上取整
        totalPage = total.toString();
        return result;
    }


    /**
     *
     * @return
     */
    public String getHdProvider(){
        Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appName);
        provider = appkey.getProvider();
        ResultUtils.toJson(ServletActionContext.getResponse(),provider);
        return null;
    }

    /**
     * 通过云海的Appkey来加载云海的硬盘列表 （此函数逻辑需要再次优化）
     *
     * @param appkey
     * @return 返回的是查询的结果，具体返回的数据 是jsp 页面，在配置文件中查看 hd.xml
     */
    public String getYunHaiDisk(Appkey appkey) {
        if (null == appkey) return Constants.QUERY_NO_DATA;
        InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
        DisksDetailReponse disksDetailReponse = yunHaiDiskListBySouType(appkey);
        LOGGER.info("totalCount: ---"+disksDetailReponse.getTotalCount());
        if (disksDetailReponse == null) {
            return Constants.QUERY_NO_DATA;
        }
        List<DiskDetailItem> disks = disksDetailReponse.getDisks();
        if (CollectionUtil.isEmpty(disks)) {
            return Constants.QUERY_NO_DATA;
        }
        totalCount = disksDetailReponse.getTotalCount();
        for (DiskDetailItem disk : disks) {
            String attachStatusCn = null;
            String instanceName = null;
            String attachStatus = (null == disk.getAttachStatus()) ? "null" : disk.getAttachStatus();
            LOGGER.info("挂载：" + attachStatus);
            attachStatus = attachStatus.toLowerCase();
            switch (attachStatus) {
                case "attached":
                    attachStatusCn = "已挂载";
                    DescribeInstancesResponse instanceResponse = instanceClient.DescribeInstances(regionId, zoneId,
                            "[" + disk.getInstanceId() + "]", null, null, null, null, null, null, null, null, null,
                            appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
                    LOGGER.info(ToStringBuilder.reflectionToString(instanceResponse));
                    if (instanceResponse.getInstances().size() > 0) {
                        InstanceAttributes instanceAttributes = instanceResponse.getInstances().get(0);
                        instanceName = "(" + instanceAttributes.getInstanceName() + ")";
                    } else {
                        LOGGER.warn("instanceId:" + disk.getInstanceId() + " not found!");
                    }
                    break;
                default:
                    attachStatusCn = "未挂载";
                    break;
            }
            Integer expiring = 0;
            String expiredTime = null;
            if (null != disk.getExpiredTime()) {
                expiring = compareDate.compare_date(nowDate, disk.getExpiredTime());
                expiredTime = disk.getExpiredTime().substring(2, disk.getExpiredTime().length() - 3);
            }
            diskServers.add(new DiskStatus(disk.getDiskId(), disk.getDiskName(), Constants.YUN_HAI,
                    transform.transformProvider(Constants.YUN_HAI), regionId, transform.transformRegion(regionId),
                    transform.transformZone(disk.getZoneId()), attachStatus, attachStatusCn, instanceName,
                    disk.getDiskType(), disk.getSize(), disk.getDiskCategory(), disk.getChargeType(), expiredTime, appkey.getUserEmail()));
        }
        if (CollectionUtil.isEmpty(diskServers)) {
            return Constants.QUERY_NO_DATA;
        }
        return Constants.YUN_HAI;
    }

    /**
     * 根据soutype来确定查询的具体类型
     *
     * @param appkey
     * @return 云海的磁盘响应
     */
    public DisksDetailReponse yunHaiDiskListBySouType(Appkey appkey) {
        VolumeClient volumeClient = OpenClientFactory.getVolumeClient();
        DisksDetailReponse disksDetailReponse = null;
        switch (souType) {
            case "all":
                disksDetailReponse = volumeClient.DescribeDisks(regionId, zoneId,
                        null, null, "NETWORK", null, null, null, null, null, null, page, PageSize.toString(), null,
                        appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
                break;
            case "attached":
                disksDetailReponse = volumeClient.DescribeDisks(regionId, zoneId,
                        null, null, "NETWORK", null, null, null, null, "ATTACHED", null, page, PageSize.toString(), null,
                        appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
                break;

            case "unattached":
                disksDetailReponse = volumeClient.DescribeDisks(regionId, zoneId,
                        null, null, "NETWORK", null, null, null, null, "DETACHED", null, page, PageSize.toString(), null,
                        appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
                break;

            case "keyword":
                disksDetailReponse = volumeClient.DescribeDisks(regionId, zoneId,
                        null, null, yunHaiDiskType, yunHaiKeyword, null, null, null, yunHaiDiskAttachStatus, null, page, PageSize.toString(), null,
                        appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
                break;
        }
        return disksDetailReponse;
    }

    /**
     * 通过阿里云的Appkey来加载对应的列表
     *
     * @param appkey
     * @return 返回的是查询的结果，具体返回的数据 是jsp 页面，在配置文件中查看 hd.xml
     */
    public String getAliYunDisk(Appkey appkey) {
        AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
        DescribeDisksResponse describeDisksResponse;
        if (StringUtil.isNotEmpty(searchType) && StringUtil.isNotEmpty(searchKeyWord)) {
            describeDisksResponse = aliYunSearchBySearchType(appkey);
        } else if (StringUtil.isNotEmpty(portableStr)) {
            describeDisksResponse = aliVolumeClient.DescribeDisks(regionId, null, null, null, aliYunDiskType, null, aliYunDiskCategory, aliYunDiskStatus, null, page, PageSize.toString(),
                    isPortable, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
        } else {
            describeDisksResponse = aliVolumeClient.DescribeDisks(regionId, null, null, null, aliYunDiskType, null, aliYunDiskCategory, aliYunDiskStatus, null, page, PageSize.toString(),
                    null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
        }
        if (describeDisksResponse == null) {
            return Constants.QUERY_NO_DATA;
        }
        List<DescribeDisksResponse.Disk> disks = describeDisksResponse.getDisks();
        LOGGER.info("totalCount: ---"+describeDisksResponse.getTotalCount());
        totalCount = describeDisksResponse.getTotalCount();

        if (CollectionUtil.isNotEmpty(disks)) {
            transformToAliYunDisk(disks,appkey);
            return Constants.ALI_YUN;
        } else {
            return Constants.QUERY_NO_DATA;
        }
    }

    /**
     * 将阿里云API返回的磁盘列表信息 重新包装，将其中的需要部分取出，转换为汉语
     * @param disks
     */
    private void transformToAliYunDisk(List<DescribeDisksResponse.Disk> disks,Appkey appkey) {
        for (DescribeDisksResponse.Disk disk : disks) {
            if(disk==null){
                return;
            }
            AliYunDisk aliYunDisk = new AliYunDisk();
            aliYunDisk.setDiskId(disk.getDiskId());
            aliYunDisk.setRegionId(regionId);
            aliYunDisk.setProviderEn(Constants.ALI_YUN);
            aliYunDisk.setDiskName(disk.getDiskName());
            aliYunDisk.setUserEmail(appkey.getUserEmail());
            aliYunDisk.setInstanceId(disk.getInstanceId());
            aliYunDisk.setCategory(disk.getCategory());
            if(disk.getCategory()!=null){
                if(StringUtil.isNotEmpty(disk.getCategory().getStringValue())) {
                    String categoryName = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(disk.getCategory().getStringValue());
                    if (StringUtil.isNotEmpty(categoryName)) {
                        aliYunDisk.setCategoryCN(categoryName);
                    }
                }
            }

            aliYunDisk.setStatus(disk.getStatus());
            String statusName = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(disk.getStatus());
            if (StringUtil.isNotEmpty(statusName)) {
                aliYunDisk.setStatusCN(statusName);
            }
            aliYunDisk.setDiskChargeType(disk.getDiskChargeType());
            String diskChargeTypeName = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(disk.getDiskChargeType());
            if (StringUtil.isNotEmpty(diskChargeTypeName)) {
                aliYunDisk.setDiskChargeTypeCN(diskChargeTypeName);
            }
            aliYunDisk.setPortable(disk.getPortable());
            String portableStr = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(disk.getPortable().toString());
            if (StringUtil.isNotEmpty(portableStr)) {
                aliYunDisk.setPortableCN(portableStr);
            }
            aliYunDisk.setZoneId(disk.getZoneId());
            String zoneName = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(disk.getZoneId());
            if (StringUtil.isNotEmpty(zoneName)) {
                aliYunDisk.setZoneName(zoneName);
            }
            aliYunDisk.setDiskType(disk.getType());
            String diskTypeName = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(disk.getType().getStringValue());
            if (StringUtil.isNotEmpty(diskTypeName)) {
                aliYunDisk.setDiskTypeCN(diskTypeName);
            }
            aliYunDisks.add(aliYunDisk);
        }
    }

    /**
     * 在精确查找的时候，根据查找类型，返回不同的响应。
     *
     * @param appkey
     * @return 返回阿里云官网API的响应。
     */
    private DescribeDisksResponse aliYunSearchBySearchType(Appkey appkey) {
        AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
        DescribeDisksResponse describeDisksResponse = null;
        switch (searchType) {
            case "disk-name":
                describeDisksResponse = aliVolumeClient.DescribeDisks(regionId, null, null, null, aliYunDiskType, searchKeyWord, aliYunDiskCategory, aliYunDiskStatus, null, page, PageSize.toString(),
                        null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
                break;
            case "disk-id":
                describeDisksResponse = aliVolumeClient.DescribeDisks(regionId, null, formatAliDiskIds(searchKeyWord), null, aliYunDiskType, null, aliYunDiskCategory, aliYunDiskStatus, null, page, PageSize.toString(),
                        null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
                break;
            case "instance-id":
                describeDisksResponse = aliVolumeClient.DescribeDisks(regionId, null, null, searchKeyWord, aliYunDiskType, null, aliYunDiskCategory, aliYunDiskStatus, null, page, PageSize.toString(),
                        null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
                break;
            case "auto-shot-id":
                describeDisksResponse = aliVolumeClient.DescribeDisks(regionId, null, null, null, aliYunDiskType, null, aliYunDiskCategory, aliYunDiskStatus, null, page, PageSize.toString(),
                        null, searchKeyWord, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
                break;
        }
        return describeDisksResponse;
    }

    /**
     * 阿里云官网要求 磁盘 ID ，此函数用于格式化输入的字符串
     * 一个带有格式的 Json Array：[“d-xxxxxxxxx”, ”d-yyyyyyyyy”, … “d-zzzzzzzzz”]，最多 100 个 Id，用半角逗号字符隔开。
     *
     * @param diskIds
     * @return 格式化以后的 json字符串 如果以后经常会用到，请提取到公共工具类 JsonUtil中，并注明
     */
    private String formatAliDiskIds(String diskIds) {
        if (StringUtil.isNotEmpty(diskIds)) {
            String[] strings = diskIds.split(",");
            JSONArray jsonArray = null;
            try {
                if (ArrayUtil.isNotEmpty(strings)) {
                    List<String> stringList = Arrays.asList(strings);
                    jsonArray = new JSONArray(stringList);
                } else {
                    jsonArray = new JSONArray(diskIds);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonArray.toString();
        } else {
            return null;
        }
    }

    public String getAmazonDisk(Appkey appkey) {
        return Constants.QUERY_NO_DATA;
    }

    public List<DiskStatus> getDiskServers() {
        return diskServers;
    }

    public double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(double totalCount) {
        totalCount = totalCount;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSouType() {
        return souType;
    }

    public void setSouType(String souType) {
        this.souType = souType;
    }

    public String getYunHaiDiskType() {
        return (yunHaiDiskType).trim();
    }

    public void setYunHaiDiskType(String yunHaiDiskType) {
        this.yunHaiDiskType = yunHaiDiskType;
    }

    public String getYunHaiDiskAttachStatus() {
        return (yunHaiDiskAttachStatus).trim();
    }

    public void setYunHaiDiskAttachStatus(String yunHaiDiskAttachStatus) {
        this.yunHaiDiskAttachStatus = yunHaiDiskAttachStatus;
    }

    public String getYunHaiKeyword() {
        return (yunHaiKeyword).trim();
    }

    public void setYunHaiKeyword(String yunHaiKeyword) {
        this.yunHaiKeyword = yunHaiKeyword;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getProvider() {
        return (provider).trim();
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRegionId() {
        return (regionId).trim();
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public List<AliYunDisk> getAliYunDisks() {
        return aliYunDisks;
    }

    public void setAliYunDisks(List<AliYunDisk> aliYunDisks) {
        this.aliYunDisks = aliYunDisks;
    }


    public String getAliYunDiskType() {
        return (aliYunDiskType).trim();
    }

    public void setAliYunDiskType(String aliYunDiskType) {
        this.aliYunDiskType = aliYunDiskType;
    }

    public String getAliYunDiskStatus() {
        return (aliYunDiskStatus).trim();
    }

    public void setAliYunDiskStatus(String aliYunDiskStatus) {
        this.aliYunDiskStatus = aliYunDiskStatus;
    }

    public String getAliYunDiskCategory() {
        return (aliYunDiskCategory).trim();
    }

    public void setAliYunDiskCategory(String aliYunDiskCategory) {
        this.aliYunDiskCategory = aliYunDiskCategory;
    }

    public String getSearchKeyWord() {
        return (searchKeyWord).trim();
    }

    public void setSearchKeyWord(String searchKeyWord) {
        this.searchKeyWord = searchKeyWord;
    }

    public boolean isPortable() {
        return isPortable;
    }

    public void setPortable(boolean portable) {
        this.isPortable = portable;
    }

    public String getPortableStr() {
        return (portableStr).trim();
    }

    public void setPortableStr(String portableStr) {
        this.portableStr = portableStr;
    }

    public String getSearchType() {
        return (searchType).trim();
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
