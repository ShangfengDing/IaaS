package com.appcloud.vm.action.commom;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.util.ResultUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rain on 2016/9/9.
 */
public class AppNameAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(AppNameAction.class);
    private Integer userId = this.getSessionUserID();

    private AppkeyManager appkeyManager = new AppkeyManager();
    private TransformAttribute transformAttribute = new TransformAttribute(); //中英文装换的
    private List<AppName> appName = new ArrayList<>();

    /**
     * 获得list的appName
     */
    public String execute(){
        List<Appkey> appkeysList = appkeyManager.getAppkeyByUserId(userId);
        for (Appkey appkey: appkeysList){
            String providerEn = appkey.getProvider();
            String providerCn = transformAttribute.transformProvider(providerEn);
            AppName appNameTemp = new AppName(providerEn,providerCn,appkey.getAppname(), appkey.getZone());
            appName.add(appNameTemp);
        }
        ResultUtils.toJson(ServletActionContext.getResponse(),appName);
        return null;
    }

    //
    public static class AppName {
        private String providerEn; //提供商英文
        private String providerCn; //提供商中文
        private String appName;  //appname
        private String zone;

        public AppName(String providerEn, String providerCn, String appName, String zone) {
            this.providerEn = providerEn;
            this.providerCn = providerCn;
            this.appName = appName;
            this.zone = zone;
        }
        public String getProviderEn() {
            return providerEn;
        }
        public void setProviderEn(String providerEn) {
            this.providerEn = providerEn;
        }
        public String getAppName() {
            return appName;
        }
        public void setAppName(String appName) {
            this.appName = appName;
        }
        public String getProviderCn() {
            return providerCn;
        }
        public void setProviderCn(String providerCn) {
            this.providerCn = providerCn;
        }
        public String getZone() {
            return zone;
        }
        public void setZone(String zone) {
            this.zone = zone;
        }
    }
}
