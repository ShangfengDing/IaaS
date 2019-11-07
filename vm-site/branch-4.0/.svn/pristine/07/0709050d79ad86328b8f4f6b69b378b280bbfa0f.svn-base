package com.appcloud.vm.action.vnc;


import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.response.DescribeInstancesResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.ConfigurationUtil;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class VncAction extends BaseAction{
	
    private static final long serialVersionUID = -2928293363187866416L;
    private Logger logger = Logger.getLogger(VncAction.class);
    private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
    private AppkeyManager appkeyManager = new AppkeyManager();
    private Integer userId = this.getSessionUserID();
    private String appname = "";
    private String regionId = "";
    private String token = "";
    private String host = "";//从配置文件中获取到host
	private String port = "";//从配置文件中获取到port
	private String password = "";
	private String serverName = "";

	private final String vncPath = "/home/vnc_file/";

	public String execute() {
	    logger.info(toString());
        Appkey appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
        if (null != regionId) {
            DescribeInstancesResponse instancesResponse = instanceClient.DescribeInstances(
                    regionId, null, "["+token+"]", null, null, null, null, null, null, null, null,
                    null, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
            if (null==instancesResponse.getInstances()){
                System.err.println("非法请求");
                return "invalid";
            } else {
                InstanceAttributes instanceAttributes = instancesResponse.getInstances().get(0);
                serverName = instanceAttributes.getInstanceName();
                getProperties(regionId);
        		password = instanceAttributes.getVncPassword();
				logger.info(toString());
				logger.info("vnc端口为：" + instanceAttributes.getVncPort());
				//判断vnc文件中是否有该文件信息
				try {
					File file = new File(vncPath+token);
					if (!file.exists()) {
						logger.info("文件不存在");
						file.createNewFile();
						String vncMessage = token + ":" + " " + instanceAttributes.getHostIp() + ":" + instanceAttributes.getVncPort();
						FileOutputStream fileOutputStream = new FileOutputStream(file);
						fileOutputStream.write(vncMessage.getBytes());
						fileOutputStream.flush();
						fileOutputStream.close();
					}
					else {
						logger.info("文件存在");
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}

			}
        }
        return SUCCESS;
	}

	//读取配置文件，由配置文件得到host和port
	public void getProperties(String regionId) {
	    String regionId_HOST = regionId + "_HOST";
        String regionId_PORT = regionId + "_PORT";
		try {
			Properties p = new ConfigurationUtil().getPropertyFileConfiguration("vnc.properties");
            host = p.getProperty(regionId_HOST,"newiass.free4inno.com");
            port = p.getProperty(regionId_PORT,"6080");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		VncAction vncAction = new VncAction();
		vncAction.getProperties("beijing");
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
	public String toString() {
		return "VncAction{" +
				"appname='" + appname + '\'' +
				", regionId='" + regionId + '\'' +
				", token='" + token + '\'' +
				", host='" + host + '\'' +
				", port='" + port + '\'' +
				", password='" + password + '\'' +
				", serverName='" + serverName + '\'' +
				'}';
	}
}
