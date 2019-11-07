package com.appcloud.vm.action.fw;

import aliyun.openapi.client.AliRegionClient;
import aliyun.openapi.client.AliSecurityGroupClient;
import appcloud.openapi.client.RegionClient;
import appcloud.openapi.client.SecurityGroupClient;
import appcloud.openapi.datatype.RegionItem;
import appcloud.openapi.datatype.SecurityGroupDetailItem;
import appcloud.openapi.datatype.SecurityGroupRuleDetailItem;
import appcloud.openapi.response.SecurityGroupsDetailReponse;
import com.aliyuncs.ecs.model.v20140526.DescribeRegionsResponse.Region;
import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupsResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupsResponse.SecurityGroup;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.action.vm.VmListAction;
import com.appcloud.vm.common.ResultMessage;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.Provider;
import com.appcloud.vm.fe.entity.RegionId;
import com.appcloud.vm.fe.entity.SecurityGroupItem;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.CollectionUtil;
import com.appcloud.vm.util.ResultUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.List;


public class FwListAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(VmListAction.class);
    private Integer userId = this.getSessionUserID();

    private RegionClient regionClient = OpenClientFactory.getRegionClient();
    private AliRegionClient aliRegionClient = OpenClientFactory.getAliRegionClient();
    private AliSecurityGroupClient aliyunSecurityGroupClient = OpenClientFactory.getAliSecurityGroupClient();
    private SecurityGroupClient securityGroupClient = OpenClientFactory.getSecurityGroupClient();

    private List<SecurityGroupItem> securityGroupDetailItems = new ArrayList<SecurityGroupItem>();
    private List<SecurityGroupRuleDetailItem> securityGroupRuleDetailItems = new ArrayList<>();
    private AppkeyManager appkeyManager = new AppkeyManager();
    private TransformAttribute transform = new TransformAttribute();//将英文转换成对应的中文
    private String zoneId=com.appcloud.vm.common.Constants.ZONE_ID;

    //获得列表的参数
    private List<Provider> providers = new ArrayList<Provider>();//提供商列表
    private List<RegionId> fwRegionIds = new ArrayList<RegionId>();//地域的列表
    private String selectRegionId;//选中的地域
    private String selectAppname = Constants.YUN_HAI;//选中的提供商
    private boolean fwflag = true;//true表示改变了提供商

    //分页的参数，不用考虑搜索
    private double TotalCount;//云快照总个数
    private Integer pageSize = 10;
    private String clickPage = "1";
    private String totalPage = "1";
    private String result = "success";//访问的结果，错误提示

    public String fwGetDivPage() {
        SelectFwList();
        return SUCCESS;
    }

    /**
     * 改变供应商或地域，不用遍历Appkey
     * @return
     */
    public String SelectFwList() {
        Appkey selectAppkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, selectAppname);
        switch (selectAppkey.getProvider()) {
            case Constants.YUN_HAI:
                queryYunHaiFwList(selectAppkey);
                break;
            case Constants.ALI_YUN:
                queryAliYunFwList(selectAppkey);
                break;
            case Constants.AMAZON:
                queryAmazonFwList();
                break;
            default:
                break;
        }
        return null;
    }

    public void queryYunHaiFwList(Appkey appkey) {
        zoneId=com.appcloud.vm.common.Constants.ZONE_ID;
        String appkeyId = appkey.getAppkeyId();
        String appkeySecert = appkey.getAppkeySecret();
        String userEmail = appkey.getUserEmail();
        List<RegionItem> regions = regionClient.DescribeRegions(appkeyId, appkeySecert, userEmail).getRegionItems();
        if (CollectionUtil.isNotEmpty(regions)) {
            if (fwflag) {
                for (RegionItem regionItem : regions) {
                    fwRegionIds.add(new RegionId(regionItem.getRegionId(), transform.transformRegion(regionItem.getRegionId())));
                }
                selectRegionId = regions.get(0).getRegionId();
            }
            SecurityGroupsDetailReponse securityGroupsDetailReponse = securityGroupClient.DescribeSecurityGroups(selectRegionId,zoneId, clickPage,
                    String.valueOf(pageSize), null, appkeyId, appkeySecert, userEmail);
            if (null == securityGroupsDetailReponse.getErrorCode()) {
                List<SecurityGroupDetailItem> sGroupDetailItems = securityGroupsDetailReponse.getSecurityGroups();
                for (SecurityGroupDetailItem sGDetailItem : sGroupDetailItems) {   //自己再次包装，这样可以加入自己参数
                    securityGroupDetailItems.add(new SecurityGroupItem(sGDetailItem.getSecurityGroupId(),
                            sGDetailItem.getSecurityGroupName(), sGDetailItem.getDescription(), Constants.YUN_HAI, selectRegionId, userEmail));
                }
                TotalCount = securityGroupsDetailReponse.getTotalCount();
                Integer total = (int) Math.ceil(TotalCount / pageSize);
                totalPage = total.toString();//获得总的页数
                result = "success";
            } else {
                result = securityGroupsDetailReponse.getMessage();
            }
        }
    }

    public String queryYunHaiFwListByReginId() {
        Appkey yunhaiAppkey = appkeyManager.getAppkeyByUserIdAndProvider(userId, Constants.YUN_HAI);
        SecurityGroupClient securityGroupClient = OpenClientFactory.getSecurityGroupClient();
        SecurityGroupsDetailReponse securityGroupsDetailReponse = securityGroupClient.DescribeSecurityGroups(selectRegionId,zoneId,
                null, null, null, yunhaiAppkey.getAppkeyId(), yunhaiAppkey.getAppkeySecret(),
                yunhaiAppkey.getUserEmail());
        if (securityGroupsDetailReponse == null) {
            ResultUtils.toYunHaiJson(ServletActionContext.getResponse(), new ResultMessage(ResultMessage.ERROR, "没有获取到安全组"));
        }
        if (CollectionUtil.isNotEmpty(securityGroupsDetailReponse.getSecurityGroups())) {
            ResultUtils.toJson(ServletActionContext.getResponse(), securityGroupsDetailReponse.getSecurityGroups());
        } else {
            ResultUtils.toYunHaiJson(ServletActionContext.getResponse(), new ResultMessage(ResultMessage.ERROR, "没有获取到安全组"));
        }
        return null;
    }

    public String queryAliYunFwListByRegionId() {
        Appkey appkey = appkeyManager.getAppkeyByUserIdAndProvider(userId, Constants.ALI_YUN);
        AliSecurityGroupClient aliSecurityGroupClient = OpenClientFactory.getAliSecurityGroupClient();
        try {
            DescribeSecurityGroupsResponse securityGroupsResponse = aliSecurityGroupClient.DescribeSecurityGroups(selectRegionId, null, null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
            if (securityGroupsResponse == null) {
                ResultUtils.toAliYunJson(ServletActionContext.getResponse(), securityGroupsResponse);
            } else {
                ResultUtils.toJson(ServletActionContext.getResponse(), securityGroupsResponse.getSecurityGroups());
            }
        } catch (ClientException e) {
            ResultUtils.toAliYunJson(ServletActionContext.getResponse(), e);
        }
        return null;
    }

    public void queryAliYunFwList(Appkey appkey) {
        String appkeyId = appkey.getAppkeyId();
        String appkeySecert = appkey.getAppkeySecret();
        String userEmail = appkey.getUserEmail();
        List<Region> regions = aliRegionClient.DescribeRegions(appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail()).getRegions();
        if (CollectionUtil.isNotEmpty(regions)) {
            if (fwflag) {
                for (Region regionItem : regions) {
                    fwRegionIds.add(new RegionId(regionItem.getRegionId(), transform.transformRegion(regionItem.getRegionId())));
                }
                selectRegionId = regions.get(0).getRegionId();
            }
            DescribeSecurityGroupsResponse aliyunSecurityGroupsDetailReponse;
            try {
                aliyunSecurityGroupsDetailReponse = aliyunSecurityGroupClient.DescribeSecurityGroups(selectRegionId, clickPage, String.valueOf(pageSize), null, appkeyId, appkeySecert, userEmail);
                List<SecurityGroup> sGDetailItems = aliyunSecurityGroupsDetailReponse.getSecurityGroups();
                for (SecurityGroup securityGroupItem : sGDetailItems) {
                    securityGroupDetailItems.add(new SecurityGroupItem(securityGroupItem.getSecurityGroupId(), securityGroupItem.getSecurityGroupName(), securityGroupItem.getDescription(),
                            Constants.ALI_YUN, selectRegionId, userEmail));
                }
                TotalCount = aliyunSecurityGroupsDetailReponse.getTotalCount();
                Integer total = (int) Math.ceil(TotalCount / pageSize);
                totalPage = total.toString();//获得总的页数
                result = "success";
                logger.info(aliyunSecurityGroupsDetailReponse);
            } catch (ClientException e) {
                // TODO Auto-generated catch block
                result = e.getErrMsg();
            }

        }
    }

    public void queryAmazonFwList() {}

    public List<SecurityGroupRuleDetailItem> getSecurityGroupRuleDetailItems() {
        return securityGroupRuleDetailItems;
    }

    public void setSecurityGroupRuleDetailItems(List<SecurityGroupRuleDetailItem> securityGroupRuleDetailItems) {
        this.securityGroupRuleDetailItems = securityGroupRuleDetailItems;
    }

    public List<SecurityGroupItem> getSecurityGroupDetailItems() {
        return securityGroupDetailItems;
    }

    public void setSecurityGroupDetailItems(
            List<SecurityGroupItem> securityGroupDetailItems) {
        this.securityGroupDetailItems = securityGroupDetailItems;
    }

    public String getClickPage() {
        return clickPage;
    }

    public void setClickPage(String clickPage) {
        this.clickPage = clickPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<RegionId> getFwRegionIds() {
        return fwRegionIds;
    }

    public void setFwRegionIds(List<RegionId> fwRegionIds) {
        this.fwRegionIds = fwRegionIds;
    }

    public String getSelectRegionId() {
        return selectRegionId;
    }

    public void setSelectRegionId(String selectRegionId) {
        this.selectRegionId = selectRegionId;
    }

    public String getSelectAppname() {
        return selectAppname;
    }

    public void setSelectAppname(String selectAppname) {
        this.selectAppname = selectAppname;
    }

    public boolean isFwflag() {
        return fwflag;
    }

    public void setFwflag(boolean fwflag) {
        this.fwflag = fwflag;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
