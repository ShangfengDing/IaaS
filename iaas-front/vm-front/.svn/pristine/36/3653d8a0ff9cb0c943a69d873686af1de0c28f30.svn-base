package com.appcloud.vm.action.commom;

import aliyun.openapi.client.AliRegionClient;
import appcloud.openapi.client.RegionClient;
import appcloud.openapi.datatype.RegionItem;
import com.aliyuncs.ecs.model.v20140526.DescribeRegionsResponse.Region;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.manager.AppkeyManager;
//import com.appcloud.vm.fe.manager.RegionManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.ResultUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionIdAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(RegionIdAction.class);
    private Integer userId = this.getSessionUserID();
    private AppkeyManager appkeyManager = new AppkeyManager();
//    private RegionManager regionManager = RegionManager.getInstance();
    private RegionClient regionClient = OpenClientFactory.getRegionClient();
    private AliRegionClient aliRegionClient = OpenClientFactory.getAliRegionClient();
    private TransformAttribute transform = new TransformAttribute();//地域的中英文转化
    private String appname;//列表中的appname
    private Map<String, String> regionResult = new HashMap<String, String>();


    /**
     * Modify By Rain
     * 由appname获得regionId的json
     * @return
     */
    public String execute() {
//        Appkey regiondAppkey = appkeyManager.getAppkeyByUserIdAndProvider(userId, providerEn.trim());
        Appkey regiondAppkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        String appkeyId = regiondAppkey.getAppkeyId();
        String appkeySecert = regiondAppkey.getAppkeySecret();
        String userEmail = regiondAppkey.getUserEmail();
        switch (regiondAppkey.getProvider()) {
            case Constants.YUN_HAI:
                List<RegionItem> regions = regionClient.DescribeRegions(appkeyId, appkeySecert, userEmail).getRegionItems();
//                List<com.appcloud.vm.fe.model.Region> regionList = regionManager.getRegionList();
                List<Region> yunRegions = new ArrayList<Region>();
//                if (regionList.size() > 0) {
//                    for (com.appcloud.vm.fe.model.Region region : regionList) {
//                        String regionId = region.getRegionId();
//                        Region regionTemp = new Region();
//                        regionTemp.setRegionId(regionId);
//                        regionTemp.setLocalName(transform.transformRegion(regionId));
//                        yunRegions.add(regionTemp);
//                    }
//                    ResultUtils.toJson(ServletActionContext.getResponse(), yunRegions);
//                }
                if (regions.size() > 0) {
                    for (RegionItem regionItem : regions) {
                        String regionId = regionItem.getRegionId();
                        Region regionTemp = new Region();
                        regionTemp.setRegionId(regionId);
                        regionTemp.setLocalName(transform.transformRegion(regionId));
                        yunRegions.add(regionTemp);
                    }
                    ResultUtils.toJson(ServletActionContext.getResponse(), yunRegions);
                }
                break;
            case Constants.ALI_YUN:
                List<Region> aliregions = aliRegionClient.DescribeRegions(appkeyId, appkeySecert, userEmail).getRegions();
                logger.info("AliRegion:" + aliregions);
                if (aliregions.size() > 0) {
                    ResultUtils.toJson(ServletActionContext.getResponse(), aliregions);
                }
                break;
            case Constants.AMAZON:
                break;
            default:
                break;
        }
        return null;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }
    
    
}
