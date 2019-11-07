package com.appcloud.vm.action.commom;/**
 * Created by dell on 2016/8/1.
 */

import aliyun.openapi.client.AliRegionClient;
import com.aliyuncs.ecs.model.v20140526.DescribeZonesResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.AliYunInstanceSourceCompareTable;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.entity.AliYunZone;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.ArrayUtil;
import com.appcloud.vm.util.CollectionUtil;
import com.appcloud.vm.util.ResultUtils;
import com.appcloud.vm.util.StringUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于根据provider和regionId来获取可用区列表
 *
 * @author 包鑫彤
 * @create 2016-08-01-17:04
 * @since 1.0.0
 */
public class GetZoneListAction extends BaseAction {

    private Logger LOGGER = Logger.getLogger(GetZoneListAction.class);
    private Integer userId = this.getSessionUserID();
    private AppkeyManager appkeyManager = new AppkeyManager();

    private String provider;
    private String regionId;

    @Override
    public String execute() throws Exception {
        Appkey appkey = appkeyManager.getAppkeyByUserIdAndProvider(userId, provider);
        switch (provider) {
            case Constants.YUN_HAI:
                break;
            case Constants.ALI_YUN:
                ResultUtils.toJson(ServletActionContext.getResponse(), getAliYunZones(appkey));
                break;
            case Constants.AMAZON:
                break;
        }
        return null;
    }

    /**
     * 根据提供的regionId获取可用zone列表，通过当前zone信息来获取可用的资源列表信息
     * 阿里云官方的API返回的只有实例规格型号，没有具体规格说明，所以将具体规格写到了配置文件中
     *
     * @return 返回该地域下的所有的Zone 列表以及可用资源信息
     */
    private List<AliYunZone> getAliYunZones(Appkey appkey) {
        AliRegionClient aliRegionClient = OpenClientFactory.getAliRegionClient();
        DescribeZonesResponse zonesResponse = aliRegionClient.DescribeZones(regionId, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
        List<DescribeZonesResponse.Zone> zones = zonesResponse.getZones();
        List<AliYunZone> aliYunZoneList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(zones)) {
            for (DescribeZonesResponse.Zone zone : zones) {
                AliYunZone aliYunZone = new AliYunZone();
                aliYunZone.setZoneId(zone.getZoneId());
                aliYunZone.setLocalName(zone.getLocalName());
                //获取阿里云可用实例的规格列表
                for (String instanceType : zone.getAvailableInstanceTypes()) {
                    //每个资源对应的CPU数量和内存数量 均在配置文件中，需要时请去resource/AliYunInstanceSourceCompareTableFile.properties查看
                    //逗号前面的是CPU数量，后面是内存大小，单位GB
                    String list = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(instanceType);
                    String[] resources = null;
                    if (StringUtil.isNotEmpty(list)) {
                        resources = list.split(",");
                    }
                    if (ArrayUtil.isNotEmpty(resources)) {
                        AliYunZone.InstanceType instance = aliYunZone.new InstanceType(instanceType, resources[0], resources[1]);
                        aliYunZone.getAvailableInstanceTypes().add(instance);
                    }
                }

                //获取阿里云可用磁盘类型
                for (String diskCategory : zone.getAvailableDiskCategories()) {
                    String diskCategoryName = AliYunInstanceSourceCompareTable.getInstance().getCompareTables().getProperty(diskCategory);
                    if (StringUtil.isNotEmpty(diskCategoryName)) {
                        AliYunZone.DiskCategory disk = aliYunZone.new DiskCategory(diskCategory, diskCategoryName);
                        aliYunZone.getAvailableDiskCategories().add(disk);
                    }
                }
                if(!aliYunZoneList.contains(aliYunZone)){
                    aliYunZoneList.add(aliYunZone);
                }
            }
            LOGGER.info("获取阿里云ECS Zone 列表成功");
            return aliYunZoneList;
        } else {
            LOGGER.info("阿里云ECS " + regionId + "没有可有地域");
        }
        return null;
    }


    public String getRegionId() {
        return (regionId).trim();
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getProvider() {
        return (provider).trim();
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
