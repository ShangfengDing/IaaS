package com.appcloud.vm.action.fw;


import aliyun.openapi.client.AliSecurityGroupClient;
import appcloud.openapi.client.RegionClient;
import appcloud.openapi.client.SecurityGroupClient;
import appcloud.openapi.datatype.RegionItem;
import appcloud.openapi.datatype.SecurityGroupRuleDetailItem;
import appcloud.openapi.response.SecurityGroupRulesReponse;

import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupAttributeResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeSecurityGroupAttributeResponse.Permission;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.CollectionUtil;
import com.appcloud.vm.util.ResultUtils;
import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通过安全组ID来请求规则列表
 *
 * @author 包鑫彤
 * @create 2016-07-12-10:07
 * @since 1.0.0
 */
public class RuleListAction extends BaseAction implements ApplicationAware{

    private  Logger LOGGER = org.apache.log4j.Logger.getLogger(RuleListAction.class);

    private Integer userId = this.getSessionUserID();
    private RegionClient regionClient = OpenClientFactory.getRegionClient();
    private SecurityGroupClient securityGroupClient = OpenClientFactory.getSecurityGroupClient();
    private AliSecurityGroupClient aliSecurityGroupClient = OpenClientFactory.getAliSecurityGroupClient();
    private List<SecurityGroupRuleDetailItem> securityGroupRuleDetailItems = new ArrayList<>();
    private AppkeyManager appkeyManager = new AppkeyManager();
    private Map<String, Object> application;
    private String securityGroupId;//安全组Id
    private String appname;//提供商
    private String regionId;//地域Id
    private String zoneId;
    @Override
    public String execute() throws Exception {
        Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        switch (appkey.getProvider()) {
            case Constants.YUN_HAI:
                queryYunHaiRules(appkey);
                break;
            case Constants.ALI_YUN:
                queryAliYunFwList(appkey);
                break;
            case Constants.AMAZON:
                //queryAmazonFwList();
                break;
            default:
                break;
        }
        return null;
    }

    public void queryYunHaiRules(Appkey appkey) {
        String appkeyId = appkey.getAppkeyId();
        String appkeySecert = appkey.getAppkeySecret();
        String userEmail = appkey.getUserEmail();        
        SecurityGroupRulesReponse securityGroupRulesReponse = securityGroupClient.DescribeSecurityGroupAttribute(regionId,zoneId, securityGroupId, appkeyId, appkeySecert, userEmail);
        securityGroupRuleDetailItems = securityGroupRulesReponse.getRules();
        ResultUtils.toJson(ServletActionContext.getResponse(),securityGroupRuleDetailItems);
    }

    public void queryAliYunFwList(Appkey appkey) {
        String appkeyId = appkey.getAppkeyId();
        String appkeySecert = appkey.getAppkeySecret();
        String userEmail = appkey.getUserEmail();
        DescribeSecurityGroupAttributeResponse securityGroupRulesReponse = aliSecurityGroupClient.DescribeSecurityGroupAttribute(regionId, securityGroupId, appkeyId, appkeySecert, userEmail);
        List<Permission> aliPermissions = securityGroupRulesReponse.getPermissions();
        ResultUtils.toJson(ServletActionContext.getResponse(),aliPermissions);
    }

    public List<SecurityGroupRuleDetailItem> getSecurityGroupRuleDetailItems() {
        return securityGroupRuleDetailItems;
    }

    public void setSecurityGroupRuleDetailItems(List<SecurityGroupRuleDetailItem> securityGroupRuleDetailItems) {
        this.securityGroupRuleDetailItems = securityGroupRuleDetailItems;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    @Override
    public void setApplication(Map<String, Object> map) {
        this.application = map;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}