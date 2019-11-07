package com.appcloud.vm.action.vm;

import aliyun.openapi.client.AliInstanceClient;
import aliyun.openapi.client.AliVolumeClient;
import appcloud.api.client.ServerClient;
import appcloud.openapi.client.ImageClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.client.VolumeClient;
import appcloud.openapi.response.BaseResponse;
import com.aliyuncs.ecs.model.v20140526.ModifyDiskAttributeResponse;
import com.aliyuncs.ecs.model.v20140526.ModifyInstanceAttributeResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.ClientFactory;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class EditVmAction extends BaseAction {
	/**
	 * 修改云主机、云硬盘、云镜像的名称、描述
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditVmAction.class);
	private Integer userId = this.getSessionUserID();
	
	private AppkeyManager appkeyManager = new AppkeyManager();
	private ServerClient serverClient = ClientFactory.getServerClient();
	private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
	private AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
	private VolumeClient diskClient = OpenClientFactory.getVolumeClient();
	private AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
	private ImageClient imageClient = OpenClientFactory.getImageClient();
	private Appkey appkey;
	private final static String YUNHAI = Constants.YUN_HAI;
	private final static String ALIYUN = Constants.ALI_YUN;
	private String yunType="";//判断修改的对象，vm:云主机，hd:云硬盘，img:云镜像
	private String appname="";
	private String id = "";//各种的id
	private String solidContent = "";//公共的内容
	private String displayContent = "";//修改后的内容
	private String password;
	private String software;
	private String type;//是修改名字还是描述
	private String regionId;
	private String zoneId =com.appcloud.vm.common.Constants.ZONE_ID;
	private String result = "fail";
	
	
	public String execute() {
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appname.trim());
		logger.info("修改名字："+yunType+";"+appname+";"+id+";"+type+";"+displayContent+";"+regionId);
		logger.info("描述"+solidContent);
		switch (appkey.getProvider()) {
			case Constants.YUN_HAI:
				YunhaiEdit(appkey);
				break;
			case Constants.ALI_YUN:
				AliyunEdit(appkey);
				break;
			case Constants.AMAZON:
				AmazonEdit();
				break;
			default:
				break;
		}

		//根据result插入日志
		Map<String, String> mapLog = new HashMap<>();
		mapLog.put("userId", userId.toString());
		mapLog.put("device", yunType);
		mapLog.put("deviceId", id);
		mapLog.put("provider", appkey.getProvider());
		mapLog.put("operateType", "change "+ type);
		mapLog.put("result", result);
		//根据result判断日志类型
		if (result == "success") {
			Log.INFO(mapLog, appkey, regionId);
		} else {
			Log.ERROR(mapLog, appkey, regionId);
		}
		return SUCCESS;
	}

	/**
	 * 修改云海主机、硬盘等
	 * @param appkey
	 */
	public void YunhaiEdit(Appkey appkey){
		switch(yunType.trim()){
		case "vm":
			vmModify(appkey);
			break;
		case "hd":
			HdModify(appkey);
			break;
		case "shot":
			//shotModify();
			break;
		case "img":
			ImgModify(appkey);
			break;
		}
	}

	/**
	 * 修改阿里云的属性
	 * @param appkey
	 */
	public void AliyunEdit(Appkey appkey){
		switch(yunType.trim()){
		case "vm":
			vmModifyAli(appkey);
			break;
		case "hd":
			HdModifyAli(appkey);
			break;
		case "shot":
			//shotModify();
			break;
		case "img":
			ImgModify(appkey);
			break;
		}
	}

	public void AmazonEdit(){}

	//云海云主机的名称、描述的修改
	public void vmModify(Appkey appkey){
		if(type.equalsIgnoreCase("name")){
			BaseResponse modifyBaseResponse = instanceClient.ModifyInstanceAttribute(regionId,zoneId,id,displayContent,null,null,null,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			logger.info(ToStringBuilder.reflectionToString(modifyBaseResponse));
			if(modifyBaseResponse.getMessage()==null){
				result = "success";
			}else{
				result = modifyBaseResponse.getMessage();
			}
		}else if(type.equalsIgnoreCase("description")){
			BaseResponse modifyBaseResponse = instanceClient.ModifyInstanceAttribute(regionId,zoneId,id,null,displayContent,null,null,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			if(modifyBaseResponse.getMessage()==null){
				result = "success";
			}else{
				result = modifyBaseResponse.getMessage();
			}
		}
	}
	
	//阿里云云主机的名称、描述的修改
		public void vmModifyAli(Appkey appkey){
			if(type.equalsIgnoreCase("name")){
				ModifyInstanceAttributeResponse modifyBaseResponse = null;
				try {
					modifyBaseResponse = aliInstanceClient.ModifyInstanceAttribute(regionId,id,displayContent,null,null,null,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
					result = "success";
				} catch (Exception e) {
					result = e.getMessage();
				}
				logger.info(ToStringBuilder.reflectionToString(modifyBaseResponse));
			}else if(type.equalsIgnoreCase("description")){
				ModifyInstanceAttributeResponse modifyBaseResponse = null;
				try {
					modifyBaseResponse = aliInstanceClient.ModifyInstanceAttribute(regionId,id,null,displayContent,null,null,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
					result = "success";
				} catch (Exception e) {
					result = e.getMessage();
				}
				logger.info(ToStringBuilder.reflectionToString(modifyBaseResponse));
			}
		}
	
	//云海云硬盘的名称、描述修改
	public void HdModify(Appkey appkey){
		if(type.equalsIgnoreCase("name")){
			BaseResponse modifyBaseResponse = diskClient.ModifyDiskAttributes(regionId,zoneId,id,displayContent,null,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			logger.info(ToStringBuilder.reflectionToString(modifyBaseResponse));
			if (modifyBaseResponse.getErrorCode()==null) {
				result = "success";
			}else {
				result = modifyBaseResponse.getMessage();
			}
		}else if(type.equalsIgnoreCase("description")){
			BaseResponse modifyBaseResponse = diskClient.ModifyDiskAttributes(regionId,zoneId,id,null,displayContent,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			logger.info(ToStringBuilder.reflectionToString(modifyBaseResponse));
			if (modifyBaseResponse.getErrorCode()==null) {
				result = "success";
			}else {
				result = modifyBaseResponse.getMessage();
			}
		}
	}
	
	//阿里云云硬盘的名称、描述修改
		public void HdModifyAli(Appkey appkey){
			if(type.equalsIgnoreCase("name")){
				ModifyDiskAttributeResponse modifyBaseResponse;
				try {
					modifyBaseResponse = aliVolumeClient.ModifyDiskAttributes(regionId,id,displayContent,null,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
					result = "success";
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					result = e.getErrMsg();
				}
			}else if(type.equalsIgnoreCase("description")){
				ModifyDiskAttributeResponse modifyBaseResponse;
				try {
					modifyBaseResponse = aliVolumeClient.ModifyDiskAttributes(regionId,id,null,displayContent,appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
					result = "success";
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					result = e.getErrMsg();
				}
			}
		}

	//云海云镜像的名称、描述修改
	public void ImgModify(Appkey appkey) {
		if (type.equals("name")) {
			BaseResponse modifyImgResponse = imageClient.ModifyImageAttribute(regionId,zoneId, id, displayContent, solidContent,software,password, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			logger.info(ToStringBuilder.reflectionToString(modifyImgResponse));
			if (modifyImgResponse.getErrorCode()==null) {
				result = "success";
			}else {
				result = modifyImgResponse.getMessage();
			}
		}else if (type.equals("description")) {
			BaseResponse modifyImgResponse = imageClient.ModifyImageAttribute(regionId, zoneId,id, solidContent, displayContent, software,password, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			logger.info(ToStringBuilder.reflectionToString(modifyImgResponse));
			if (modifyImgResponse.getErrorCode()==null) {
				result = "success";
			}else {
				result = modifyImgResponse.getMessage();
			}
		}
	}

	public String getYunType() {
		return yunType;
	}

	public void setYunType(String yunType) {
		this.yunType = yunType;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayContent() {
		return displayContent;
	}

	public void setDisplayContent(String displayContent) {
		this.displayContent = displayContent;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSolidContent() {
		return solidContent;
	}

	public void setSolidContent(String solidContent) {
		this.solidContent = solidContent;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	@Override
	public String toString() {
		return "EditVmAction [yunType=" + yunType + ", appname="
				+ appname + ", id=" + id + ", solidContent=" + solidContent
				+ ", displayContent=" + displayContent + ", type=" + type
				+ ", regionId=" + regionId + ", result=" + result + "]";
	}

}
