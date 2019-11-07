package com.appcloud.vm.action.fw;

import aliyun.openapi.client.AliSecurityGroupClient;
import appcloud.openapi.client.SecurityGroupClient;
import appcloud.openapi.response.BaseResponse;

import com.aliyuncs.ecs.model.v20140526.AuthorizeSecurityGroupResponse;
import com.aliyuncs.ecs.model.v20140526.RevokeSecurityGroupResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.appcloud.vm.util.ResultUtils;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全组规操作类，包括新建删除
 *
 * @author 包鑫彤
 * @create 2016-07-13-14:23
 * @since 1.0.0
 */
public class FwRuleOperateAction extends BaseAction {

    private Logger logger = Logger.getLogger(FwRuleOperateAction.class);
    private static String REGIONID = "beijing";  //需要修改
    private AppkeyManager appkeyManager = new AppkeyManager();
    private Integer userId = this.getSessionUserID();
    private List<Appkey> appkeyList = appkeyManager.getAppkeyByUserId(userId);

    private SecurityGroupClient securityGroupClient = OpenClientFactory.getSecurityGroupClient();
    private AliSecurityGroupClient aliSecurityGroupClient = OpenClientFactory.getAliSecurityGroupClient();

    //操作的参数
    private String appname;
    private String regionId;
    private String zoneId;
    private String securityGroupId = null;
    private String ipProtocol = null;
    private String portRange = null;
    private String sourceCidrIp = null;
    private String policy = null;
    private String priority = null;
    private String sourceGroupId = null;
    private String sourceGroupOwnerAccount = null;
    /**
     * 新建安全组中的规则规则
     * @return
     * @throws IOException
     */
    public String newSecurityGroupRule(){
    	Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname.trim());
        String appkeyId = appkey.getAppkeyId();
        String appkeySecret = appkey.getAppkeySecret();
        String userEmail = appkey.getUserEmail();
        logger.info("test operate");
        switch (appkey.getProvider()) {
            case Constants.YUN_HAI:
                BaseResponse newSecurityGroupRuleResponse = securityGroupClient.AuthorizeSecurityGroup(
                        regionId.trim(),zoneId, securityGroupId, ipProtocol, portRange, sourceCidrIp, sourceGroupId,
                        policy, sourceGroupId, sourceGroupOwnerAccount,appkeyId,appkeySecret,userEmail );
                fwRuleLogInfo(appkey, "create new security group rule", newSecurityGroupRuleResponse.getMessage());
                ResultUtils.toJson(ServletActionContext.getResponse(), newSecurityGroupRuleResponse);
                break;
            case Constants.ALI_YUN:
                AuthorizeSecurityGroupResponse aliAuthorizeSecurityGroupResponse = null;
                try {
                    aliAuthorizeSecurityGroupResponse = aliSecurityGroupClient.AuthorizeSecurityGroup(
                            regionId.trim(), securityGroupId, ipProtocol, portRange, sourceCidrIp, policy, priority, sourceGroupId,
                            sourceGroupOwnerAccount, appkeyId, appkeySecret, userEmail);
                    fwRuleLogInfo(appkey, "create new security group rule", null);
                    ResultUtils.toJson(ServletActionContext.getResponse(), aliAuthorizeSecurityGroupResponse);
                } catch (ClientException e) {
                    fwRuleLogInfo(appkey, "create new security group rule", e.getErrMsg());
                    ResultUtils.toJson(ServletActionContext.getResponse(), e.getErrMsg());
                }
                break;
            case Constants.AMAZON:
                break;

        }
        return null;
    }

    /**
     * 撤销安全组的访问权限。
     * 采用指定协议通过指定端口访问本安全组，只有调用授权接口授权的权限条目才可以删除（参数值跟授权时的参数相同）
     * 安全组规则由SourceCidrIp 、 IpProtocol 、 PortRange 、 Policy 组成。如匹配的规则找不到将会报错
     * @return
     * @throws IOException
     */
    public String deleteSecurityGroupRule() {
    	Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname.trim());
        String appkeyId = appkey.getAppkeyId();
        String appkeySecret = appkey.getAppkeySecret();
        String userEmail = appkey.getUserEmail();
        switch (appkey.getProvider()) {
            case Constants.YUN_HAI:
                BaseResponse revokeSecurityGroupRuleResponse = securityGroupClient.RevokeSecurityGroup
                        (regionId.trim(),zoneId,securityGroupId,ipProtocol,portRange,sourceCidrIp,policy,priority,sourceGroupId,
                        		sourceGroupOwnerAccount,appkeyId,appkeySecret,userEmail);
                fwRuleLogInfo(appkey, "delete security group rule", revokeSecurityGroupRuleResponse.getMessage());
                ResultUtils.toJson(ServletActionContext.getResponse(), revokeSecurityGroupRuleResponse);
                break;
            case Constants.ALI_YUN:
                RevokeSecurityGroupResponse aliRevokeSecurityGroupResponse = null;
                try {
                    aliRevokeSecurityGroupResponse = aliSecurityGroupClient.RevokeSecurityGroup(
                            regionId.trim(), securityGroupId, ipProtocol, portRange, sourceCidrIp, policy, priority, securityGroupId,
                            sourceGroupOwnerAccount, appkeyId, appkeySecret, userEmail);
                    fwRuleLogInfo(appkey, "delete security group rule", null);
                    ResultUtils.toJson(ServletActionContext.getResponse(), aliRevokeSecurityGroupResponse);
                } catch (ClientException e) {
                    fwRuleLogInfo(appkey, "delete security group rule", e.getErrMsg());
                    ResultUtils.toJson(ServletActionContext.getResponse(), e.getErrMsg());
                }
            	break;
            case Constants.AMAZON:
                break;

        }
        return null;
    }

    //将操作日志传到乐志
    public void fwRuleLogInfo (Appkey appkey, String operation, String message) {
        Map<String, String> mapLog = new HashMap<>();
        mapLog.put("userId", userId.toString());
        mapLog.put("device", "fw");
        mapLog.put("deviceId", securityGroupId);
        mapLog.put("provider", appkey.getProvider());
        mapLog.put("operateType", operation);
        if (null == message) {
            mapLog.put("result", "success");
            Log.INFO(mapLog, appkey, regionId,zoneId);
        } else {
            mapLog.put("result", message);
            Log.ERROR(mapLog, appkey, regionId,zoneId);
        }
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public String getIpProtocol() {
        return ipProtocol;
    }

    public void setIpProtocol(String ipProtocol) {
        this.ipProtocol = ipProtocol.trim();
    }

    public String getPortRange() {
        return portRange;
    }

    public void setPortRange(String portRange) {
        this.portRange = portRange;
    }

    public String getSourceCidrIp() {
        return sourceCidrIp;
    }

    public void setSourceCidrIp(String sourceCidrIp) {
        this.sourceCidrIp = sourceCidrIp;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSourceGroupId() {
        return sourceGroupId;
    }

    public void setSourceGroupId(String sourceGroupId) {
        this.sourceGroupId = sourceGroupId;
    }

    public String getSourceGroupOwnerAccount() {
        return sourceGroupOwnerAccount;
    }

    public void setSourceGroupOwnerAccount(String sourceGroupOwnerAccount) {
        this.sourceGroupOwnerAccount = sourceGroupOwnerAccount;
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

    @Override
	public String toString() {
		return "FwRuleOperateAction [aliSecurityGroupClient="
				+ aliSecurityGroupClient + ", appname=" + appname
				+ ", regionId=" + regionId + ", securityGroupId="
				+ securityGroupId + ", ipProtocol=" + ipProtocol
				+ ", portRange=" + portRange + ", sourceCidrIp=" + sourceCidrIp
				+ ", policy=" + policy + ", priority=" + priority
				+ ", sourceGroupId=" + sourceGroupId
				+ ", sourceGroupOwnerAccount=" + sourceGroupOwnerAccount + "]";
	}

    
}
